package com.piggy.quincy.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.piggy.quincy.service.RedisService;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
    @Value("${redis.database}")
    private String redisDatabase;
    @Value("${redis.database}:sessionKey")
    private String redisSessionKey;

    public void sendGroupMessage(Long group, Integer quote, List<JSONObject> messageChain) throws IOException {
        String sessionKey = redisService.get(redisSessionKey).toString();

        Response response = miraiApiHttpComponent.sendGroupMessage(sessionKey, group, null, quote, messageChain);

        JSONObject jsonObject = JSON.parseObject(Objects.requireNonNull(response.body()).string());

        // 推入群对应的消息栈
        if (jsonObject.getInteger("code") == 0) {
            String groupKey = redisDatabase + ":sendMessage:" + String.valueOf(group);
            redisService.listRightPush(groupKey, jsonObject.getInteger("messageId"));
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            for (JSONObject message : messageChain) {
                stringBuffer.append(message.toJSONString());
            }
            LOGGER.error("SendGroupMessageError: {} -> Group{} -> {}", stringBuffer, String.valueOf(group), jsonObject.toJSONString());
        }
    }

    /**
     * 获取赦免概率
     * @return 赦免概率
     */
    public double getUnbanProbability() {
        String unbanProbabilityKey = redisDatabase + ":unbanProbability";

        return Double.parseDouble(redisService.get(unbanProbabilityKey).toString()) / 100;
    }

    /**
     * 是否赦免
     * @return true -> 赦免
     */
    public boolean isUnban() {
        double unbanProbability = this.getUnbanProbability();
        double randomNum = Math.random() * 100;

        return randomNum <= unbanProbability;
    }

    /**
     * 检查是否包含某个单词
     * @param jsonObjectList 消息链
     * @param message 需要被检测的单词
     * @return 检测结果
     */
    public boolean hasMessage(List<JSONObject> jsonObjectList, String message) {
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
