package com.piggy.quincy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.piggy.quincy.component.MessageBuilderComponent;
import com.piggy.quincy.component.MessageCheckerComponent;
import com.piggy.quincy.component.MiraiApiHttpComponent;
import com.piggy.quincy.component.TaskComponent;
import com.piggy.quincy.config.BotConfig;
import com.piggy.quincy.service.EventService;
import com.piggy.quincy.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

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
    @Value("${redis.database}")
    private String redisDatabase;


    @Override
    public void groupMessage(JSONObject jsonObject) {

    }

    @Override
    public void friendMessage(JSONObject jsonObject) throws IOException {
        String key = redisDatabase + ":sessionKey";
        if (MessageCheckerComponent.hasMessage(JSON.parseArray(jsonObject.getString("messageChain"), JSONObject.class), "解除口球")) {
            miraiApiHttpComponent.sendFriendMessage(redisService.get(key).toString(), 1162719199L, null, null, new ArrayList<JSONObject>(){{
                add(MessageBuilderComponent.plain("Working on it!"));
            }});
        }


        try {
            miraiApiHttpComponent.sendFriendMessage(redisService.get(key).toString(), 1162719199L, null, null, new ArrayList<JSONObject>(){{
                add(MessageBuilderComponent.plain(jsonObject.getString("sender")));
            }});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tempMessage(JSONObject jsonObject) throws IOException {
        String key = redisDatabase + ":sessionKey";
        if (MessageCheckerComponent.hasMessage(JSON.parseArray(jsonObject.getString("messageChain"), JSONObject.class), "解除口球")) {
            miraiApiHttpComponent.sendFriendMessage(redisService.get(key).toString(), 1162719199L, null, null, new ArrayList<JSONObject>(){{
                add(MessageBuilderComponent.plain("Working on it!"));
            }});
        }

        try {
            miraiApiHttpComponent.sendFriendMessage(redisService.get(key).toString(), 1162719199L, null, null, new ArrayList<JSONObject>(){{
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
