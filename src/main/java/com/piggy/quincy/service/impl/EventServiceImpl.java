package com.piggy.quincy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.piggy.quincy.component.MiraiApiHttpComponent;
import com.piggy.quincy.service.EventService;
import com.piggy.quincy.service.RedisService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author IMNOTHD
 * @date 2020/7/3 17:15
 */
@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private MiraiApiHttpComponent miraiApiHttpComponent;

    @Override
    public void groupMessage(JSONObject jsonObject) {

    }

    @Override
    public void friendMessage(JSONObject jsonObject) {

    }

    @Override
    public void tempMessage(JSONObject jsonObject) {

    }

    @Override
    public void botOnlineEvent(JSONObject jsonObject) {

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
