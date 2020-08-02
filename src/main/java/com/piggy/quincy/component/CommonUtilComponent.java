package com.piggy.quincy.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.piggy.quincy.config.BotConfig;
import com.piggy.quincy.service.RedisService;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author IMNOTHD
 * @date 2020/7/12 13:00
 */
@Component
public class CommonUtilComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtilComponent.class);
    @Autowired
    private RedisService redisService;
    @Autowired
    private MiraiApiHttpComponent miraiApiHttpComponent;
    @Autowired
    private BotConfig botConfig;
    @Value("${redis.database}")
    private String redisDatabase;
    @Value("${redis.database}:sessionKey")
    private String redisSessionKey;

    /**
     * TempMessage unban
     * @param qq 请求QQ
     * @param fromGroup 来源group
     */
    public void unbanFromPrivateMessage(Long qq, Long fromGroup) throws IOException {
        String sessionKey = redisService.get(redisSessionKey).toString();
        String unbanTimesKey = MessageFormat.format("{0}:unbanTimes", redisDatabase);
        Integer unbanTimes = (Integer) redisService.hashGet(unbanTimesKey, String.valueOf(qq));

        if (unbanTimes != null && unbanTimes >= botConfig.getUnbanTimesLimit()) {
            miraiApiHttpComponent.sendTempMessage(sessionKey, qq, fromGroup, null, new ArrayList<JSONObject>(){{
                add(MessageBuilderComponent.plain("解除次数已达上限，5分钟增加一次"));
            }});
            return;
        }

        double unbanProbability = Math.log(Math.pow(this.getUnbanProbability() + 1, 5));
        double randomNum = Math.random() * 100;

        if (randomNum > unbanProbability) {
            miraiApiHttpComponent.sendTempMessage(sessionKey, qq, fromGroup, null, new ArrayList<JSONObject>(){{
                add(MessageBuilderComponent.plain(String.format("解除失败，请再试一次", unbanProbability, randomNum)));
            }});
            return;
        }

        Response response = miraiApiHttpComponent.unmute(sessionKey, fromGroup, qq);
        miraiApiHttpComponent.sendTempMessage(sessionKey, qq, fromGroup, null, new ArrayList<JSONObject>(){{
            add(MessageBuilderComponent.plain("解除成功"));
        }});

        JSONObject jsonObject = JSON.parseObject(Objects.requireNonNull(response.body()).string());

        // 记录unban次数
        if (jsonObject.getInteger("code") == 0) {
            if (unbanTimes == null) {
                unbanTimes = 1;
            } else {
                unbanTimes++;
            }

            redisService.hashSet(unbanTimesKey, String.valueOf(qq), unbanTimes);

            LOGGER.info("Unban {} from Group {}", qq, fromGroup);
        }
    }

    /**
     * FriendMessage unban, 这类unban无次数限制
     * @param qq 请求QQ
     */
    public void unbanFromPrivateMessage(Long qq) {

    }

    /**
     * 判断消息中是否at了bot
     *
     * @param messageChain 消息链
     * @return 判断结果
     */
    public boolean isAt(List<JSONObject> messageChain) {
        for (JSONObject message : messageChain) {
            if ("At".equals(message.getString("type")) && botConfig.getQq().equals(message.getLong("target"))) {
                return true;
            }
        }

        return false;
    }

    /**
     * 发送群消息封装
     *
     * @param group        发送的目标QQ群
     * @param quote        引用的消息, 没有填null
     * @param messageChain 发送的消息链
     * @throws IOException
     */
    public void sendGroupMessage(Long group, Integer quote, List<JSONObject> messageChain) throws IOException {
        String sessionKey = redisService.get(redisSessionKey).toString();

        Response response = miraiApiHttpComponent.sendGroupMessage(sessionKey, group, null, quote, messageChain);

        JSONObject jsonObject = JSON.parseObject(Objects.requireNonNull(response.body()).string());

        // 打log用
        StringBuffer stringBuffer = new StringBuffer();
        for (JSONObject message : messageChain) {
            stringBuffer.append(message.toJSONString());
        }

        // 推入群对应的消息栈
        if (jsonObject.getInteger("code") == 0) {
            String groupKey = redisDatabase + ":sendMessage:" + String.valueOf(group);
            redisService.listRightPush(groupKey, jsonObject.getInteger("messageId"));
            LOGGER.info("SendGroupMessage: {} -> Group{} -> {}", stringBuffer, String.valueOf(group), jsonObject.toJSONString());
        } else {
            LOGGER.error("SendGroupMessageError: {} -> Group{} -> {}", stringBuffer, String.valueOf(group), jsonObject.toJSONString());
        }
    }

    /**
     * 获取赦免概率
     *
     * @return 赦免概率
     */
    public double getUnbanProbability() {
        String unbanProbabilityKey = redisDatabase + ":unbanProbability";

        return Double.parseDouble(redisService.get(unbanProbabilityKey).toString()) / 100;
    }

    /**
     * 是否赦免
     *
     * @return true -> 赦免
     */
    public boolean isUnban() {
        double unbanProbability = this.getUnbanProbability();
        double randomNum = Math.random() * 100;

        return randomNum <= unbanProbability;
    }

    /**
     * 检查是否完全相等某个单词
     *
     * @param jsonObjectList 消息链
     * @param message        需要被检测的单词
     * @return 检测结果
     */
    public boolean equalMessage(List<JSONObject> jsonObjectList, @NotNull String message) {
        if (jsonObjectList.size() != 2) {
            return false;
        }

        JSONObject jsonObject = jsonObjectList.get(1);

        if ("Plain".equals(jsonObject.getString("type"))) {
            String text = jsonObject.getString("text");

            return message.equals(text);
        }

        return false;
    }

    /**
     * 检查是否包含某个单词
     *
     * @param jsonObjectList 消息链
     * @param message        需要被检测的单词
     * @return 检测结果
     */
    public boolean hasMessage(List<JSONObject> jsonObjectList, @NotNull String message) {
        for (JSONObject jsonObject : jsonObjectList) {
            if ("Plain".equals(jsonObject.getString("type"))) {
                String text = jsonObject.getString("text");

                // kmp
                char[] p = text.toCharArray();
                int pLen = p.length;
                int[] next = new int[pLen];
                int k = -1;
                int j = 0;
                next[0] = -1;
                while (j < pLen - 1) {
                    if (k == -1 || p[j] == p[k]) {
                        k++;
                        j++;
                        next[j] = k;
                    } else {
                        k = next[k];
                    }
                }
                int i = 0;
                j = 0;
                char[] src = text.toCharArray();
                char[] ptn = message.toCharArray();
                int srcLen = src.length;
                int ptnLen = ptn.length;
                while (i < srcLen && j < pLen) {
                    // 如果j = -1,或者当前字符匹配成功(src[i] = ptn[j]),都让i++,j++
                    if (j == -1 || src[i] == ptn[j]) {
                        i++;
                        j++;
                    } else {
                        // 如果j!=-1且当前字符匹配失败,则令i不变,j=next[j],即让pattern模式串右移j-next[j]个单位
                        j = next[j];
                    }
                }
                if (j == ptnLen) {
                    return true;
                }
            }
        }
        return false;
    }

    public String fullString(List<JSONObject> jsonObjectList) {
        StringBuffer stringBuffer = new StringBuffer();
        for (JSONObject jsonObject : jsonObjectList) {
            String type = jsonObject.getString("type");
            switch (type) {
                case "Source":
                    break;
                case "Quote":
                    break;
                case "At":
                    break;
                case "AtAll":
                    break;
                case "Face":
                    break;
                case "Plain":
                    break;
                case "Image":
                    break;
                case "FlashImage":
                    break;
                case "Xml":
                    break;
                case "Json":
                    break;
                case "App":
                    break;
                case "Poke":
                    break;
                default:
                    break;
            }
        }
        return "";
    }
}
