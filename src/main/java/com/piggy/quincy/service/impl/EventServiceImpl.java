package com.piggy.quincy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.piggy.quincy.component.MessageBuilderComponent;
import com.piggy.quincy.component.CommonUtilComponent;
import com.piggy.quincy.component.MiraiApiHttpComponent;
import com.piggy.quincy.component.TaskComponent;
import com.piggy.quincy.config.BotConfig;
import com.piggy.quincy.service.EventService;
import com.piggy.quincy.service.RedisService;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 严禁修改方法名, 否则会导致反射失效
 *
 * @author IMNOTHD
 * @date 2020/7/3 17:15
 */
@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private MiraiApiHttpComponent miraiApiHttpComponent;
    @Autowired
    private BotConfig botConfig;
    @Autowired
    private TaskComponent taskComponent;
    @Autowired
    private CommonUtilComponent commonUtilComponent;
    @Value("${redis.database}")
    private String redisDatabase;
    @Value("${redis.database}:sessionKey")
    private String redisSessionKey;


    @Override
    public void groupMessage(JSONObject jsonObject) throws IOException {
        List<JSONObject> messageChain = JSON.parseArray(jsonObject.getString("messageChain"), JSONObject.class);
        Long group = ((JSONObject) ((JSONObject) jsonObject.get("sender")).get("group")).getLong("id");
        Long senderQQ = ((JSONObject) jsonObject.get("sender")).getLong("id");

        String sessionKey = redisService.get(redisSessionKey).toString();

        // 抽取口球
        if (commonUtilComponent.equalMessage(messageChain, "小口球抽奖")) {
            // 迷你的口球, 时长为[1, 5)分钟
            int randomBanTime = (int) (Math.random() * 5 + 1);

            if (commonUtilComponent.isUnban()) {
                commonUtilComponent.sendGroupMessage(group, null, new ArrayList<JSONObject>() {{
                    add(MessageBuilderComponent.at(senderQQ));
                    add(MessageBuilderComponent.plain(String.format("抽中了%d分钟的口球，但是幸运的被赦免了", randomBanTime)));
                }});
            } else {
                miraiApiHttpComponent.mute(sessionKey, group, senderQQ, randomBanTime * 60);

                commonUtilComponent.sendGroupMessage(group, null, new ArrayList<JSONObject>() {{
                    add(MessageBuilderComponent.at(senderQQ));
                    add(MessageBuilderComponent.plain(String.format("抽中了%d分钟的口球", randomBanTime)));
                }});
            }
        } else if (commonUtilComponent.equalMessage(messageChain, "大口球抽奖")) {
            // 巨大的口球, 时长为[1分钟, 30天)
            int randomBanTime = (int) (Math.random() * 43199 + 1);

            if (commonUtilComponent.isUnban()) {
                commonUtilComponent.sendGroupMessage(group, null, new ArrayList<JSONObject>() {{
                    add(MessageBuilderComponent.at(senderQQ));
                    add(MessageBuilderComponent.plain(String.format("抽中了%d天%d小时%d分钟的口球，但是幸运的被赦免了", randomBanTime / 60 / 24, randomBanTime / 60 % 24, randomBanTime % 60)));
                }});
            } else {
                miraiApiHttpComponent.mute(sessionKey, group, senderQQ, randomBanTime * 60);

                commonUtilComponent.sendGroupMessage(group, null, new ArrayList<JSONObject>() {{
                    add(MessageBuilderComponent.at(senderQQ));
                    add(MessageBuilderComponent.plain(String.format("抽中了%d天%d小时%d分钟的口球", randomBanTime / 60 / 24, randomBanTime / 60 % 24, randomBanTime % 60)));
                }});
            }
        } else if (commonUtilComponent.equalMessage(messageChain, "口球抽奖")) {
            // 普通的口球, 时长为[1, 60)分钟
            int randomBanTime = (int) (Math.random() * 60 + 1);

            if (commonUtilComponent.isUnban()) {
                commonUtilComponent.sendGroupMessage(group, null, new ArrayList<JSONObject>() {{
                    add(MessageBuilderComponent.at(senderQQ));
                    add(MessageBuilderComponent.plain(String.format("抽中了%d分钟的口球，但是幸运的被赦免了", randomBanTime)));
                }});
            } else {
                miraiApiHttpComponent.mute(sessionKey, group, senderQQ, randomBanTime * 60);

                commonUtilComponent.sendGroupMessage(group, null, new ArrayList<JSONObject>() {{
                    add(MessageBuilderComponent.at(senderQQ));
                    add(MessageBuilderComponent.plain(String.format("抽中了%d分钟的口球", randomBanTime)));
                }});
            }
        }

        // 8小时精致睡眠
        if (commonUtilComponent.equalMessage(messageChain, "sleep")) {
            miraiApiHttpComponent.mute(sessionKey, group, senderQQ, 28800);

            commonUtilComponent.sendGroupMessage(group, null, new ArrayList<JSONObject>() {{
                add(MessageBuilderComponent.at(senderQQ));
                add(MessageBuilderComponent.plain("晚安"));
            }});
        }

        // 赦免概率
        if (commonUtilComponent.equalMessage(messageChain, "赦免概率")) {
            commonUtilComponent.sendGroupMessage(group, null, new ArrayList<JSONObject>() {{
                add(MessageBuilderComponent.at(senderQQ));
                add(MessageBuilderComponent.plain(String.format("当前赦免概率为：%.2f%%", commonUtilComponent.getUnbanProbability())));
            }});
        }

        // 撤回bot发送的最后一条消息
        if (commonUtilComponent.isAt(messageChain) && commonUtilComponent.hasMessage(messageChain, "撤回")) {
            String groupKey = redisDatabase + ":sendMessage:" + String.valueOf(group);
            Integer messageId = (Integer) redisService.listRightPop(groupKey);
            if (messageId != null) {
                Response response = miraiApiHttpComponent.recall(sessionKey, messageId);
                JSONObject responseJson = JSONObject.parseObject(response.body().string());
                if (responseJson.getInteger("code") != 0) {
                    miraiApiHttpComponent.sendGroupMessage(sessionKey, group, null, null, new ArrayList<JSONObject>() {{
                        add(MessageBuilderComponent.plain("撤回失败"));
                    }});
                }
            }
        }
    }

    @Override
    public void friendMessage(JSONObject jsonObject) throws IOException {
        List<JSONObject> messageChain = JSON.parseArray(jsonObject.getString("messageChain"), JSONObject.class);
        Long senderQQ = ((JSONObject) jsonObject.get("sender")).getLong("id");

        if (commonUtilComponent.equalMessage(messageChain, "赦免概率")) {
            double unbanProbability = Math.log(Math.pow(commonUtilComponent.getUnbanProbability() + 1, 5));
            miraiApiHttpComponent.sendFriendMessage(redisService.get(redisSessionKey).toString(), senderQQ, null, null, new ArrayList<JSONObject>() {{
                add(MessageBuilderComponent.plain(String.format("%.2f%%", unbanProbability)));
            }});
        }

        if (commonUtilComponent.equalMessage(messageChain, "解除口球")) {
            miraiApiHttpComponent.sendFriendMessage(redisService.get(redisSessionKey).toString(), senderQQ, null, null, new ArrayList<JSONObject>() {{
                add(MessageBuilderComponent.plain("Working on it!"));
            }});
        }


        try {
            miraiApiHttpComponent.sendFriendMessage(redisService.get(redisSessionKey).toString(), 1162719199L, null, null, new ArrayList<JSONObject>() {{
                add(MessageBuilderComponent.plain(jsonObject.getString("sender")));
            }});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tempMessage(JSONObject jsonObject) throws IOException {
        List<JSONObject> messageChain = JSON.parseArray(jsonObject.getString("messageChain"), JSONObject.class);
        Long group = ((JSONObject) ((JSONObject) jsonObject.get("sender")).get("group")).getLong("id");
        Long senderQQ = ((JSONObject) jsonObject.get("sender")).getLong("id");

        if (commonUtilComponent.equalMessage(messageChain, "解除口球")) {
            commonUtilComponent.unbanFromPrivateMessage(senderQQ, group);
        }

        if (commonUtilComponent.equalMessage(messageChain, "赦免概率")) {
            double unbanProbability = Math.log(Math.pow(commonUtilComponent.getUnbanProbability() + 1, 5));
            miraiApiHttpComponent.sendTempMessage(redisService.get(redisSessionKey).toString(), senderQQ, null, null, new ArrayList<JSONObject>() {{
                add(MessageBuilderComponent.plain(String.format("%.2f%%", unbanProbability)));
            }});
        }

        try {
            miraiApiHttpComponent.sendFriendMessage(redisService.get(redisSessionKey).toString(), 1162719199L, null, null, new ArrayList<JSONObject>() {{
                add(MessageBuilderComponent.plain(jsonObject.getString("sender")));
            }});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void botOnlineEvent(JSONObject jsonObject) {
        // 更新sessionKey
        try {
            taskComponent.refreshSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void botOfflineEventActive(JSONObject jsonObject) {

    }

    @Override
    public void botOfflineEventForce(JSONObject jsonObject) {

    }

    @Override
    public void botOfflineEventDropped(JSONObject jsonObject) {

    }

    @Override
    public void botReloginEvent(JSONObject jsonObject) {

    }

    @Override
    public void groupRecallEvent(JSONObject jsonObject) {

    }

    @Override
    public void friendRecallEvent(JSONObject jsonObject) {

    }

    @Override
    public void botGroupPermissionChangeEvent(JSONObject jsonObject) {

    }

    @Override
    public void botMuteEvent(JSONObject jsonObject) {

    }

    @Override
    public void botUnmuteEvent(JSONObject jsonObject) {

    }

    @Override
    public void botJoinGroupEvent(JSONObject jsonObject) {

    }

    @Override
    public void botLeaveEventActive(JSONObject jsonObject) {

    }

    @Override
    public void botLeaveEventKick(JSONObject jsonObject) {

    }

    @Override
    public void groupNameChangeEvent(JSONObject jsonObject) {

    }

    @Override
    public void groupEntranceAnnouncementChangeEvent(JSONObject jsonObject) {

    }

    @Override
    public void groupMuteAllEvent(JSONObject jsonObject) {

    }

    @Override
    public void groupAllowAnonymousChatEvent(JSONObject jsonObject) {

    }

    @Override
    public void groupAllowConfessTalkEvent(JSONObject jsonObject) {

    }

    @Override
    public void groupAllowMemberInviteEvent(JSONObject jsonObject) {

    }

    @Override
    public void memberJoinEvent(JSONObject jsonObject) {

    }

    @Override
    public void memberLeaveEventKick(JSONObject jsonObject) {

    }

    @Override
    public void memberLeaveEventQuit(JSONObject jsonObject) {

    }

    @Override
    public void memberCardChangeEvent(JSONObject jsonObject) {

    }

    @Override
    public void memberSpecialTitleChangeEvent(JSONObject jsonObject) {

    }

    @Override
    public void memberPermissionChangeEvent(JSONObject jsonObject) {

    }

    @Override
    public void memberMuteEvent(JSONObject jsonObject) {

    }

    @Override
    public void memberUnmuteEvent(JSONObject jsonObject) {

    }

    @Override
    public void newFriendRequestEvent(JSONObject jsonObject) {

    }

    @Override
    public void memberJoinRequestEvent(JSONObject jsonObject) {

    }

    @Override
    public void botInvitedJoinGroupRequestEvent(JSONObject jsonObject) {

    }
}
