package com.piggy.quincy.service;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

/**
 * @author IMNOTHD
 * @date 2020/7/3 17:16
 */
public interface EventService {

    /**
     * 群消息
     *
     * @param jsonObject 参数
     */
    void groupMessage(JSONObject jsonObject);

    /**
     * 好友消息
     *
     * @param jsonObject 参数
     */
    void friendMessage(JSONObject jsonObject) throws IOException;

    /**
     * 临时消息
     *
     * @param jsonObject 参数
     */
    void tempMessage(JSONObject jsonObject) throws IOException;

    /**
     * Bot登录成功
     *
     * @param jsonObject 参数
     */
    void botOnlineEvent(JSONObject jsonObject);

    /**
     * Bot主动离线
     *
     * @param jsonObject 参数
     */
    void botOfflineEventActive(JSONObject jsonObject);

    /**
     * Bot被挤下线
     *
     * @param jsonObject 参数
     */
    void botOfflineEventForce(JSONObject jsonObject);

    /**
     * Bot被服务器断开或因网络问题而掉线
     *
     * @param jsonObject 参数
     */
    void botOfflineEventDropped(JSONObject jsonObject);

    /**
     * Bot主动重新登录
     *
     * @param jsonObject 参数
     */
    void botReloginEvent(JSONObject jsonObject);

    /**
     * 群消息撤回
     *
     * @param jsonObject 参数
     */
    void groupRecallEvent(JSONObject jsonObject);

    /**
     * 好友消息撤回
     *
     * @param jsonObject 参数
     */
    void friendRecallEvent(JSONObject jsonObject);

    /**
     * Bot在群里的权限被改变, 操作人一定是群主
     *
     * @param jsonObject 参数
     */
    void botGroupPermissionChangeEvent(JSONObject jsonObject);

    /**
     * Bot被禁言
     *
     * @param jsonObject 参数
     */
    void botMuteEvent(JSONObject jsonObject);

    /**
     * Bot被取消禁言
     *
     * @param jsonObject 参数
     */
    void botUnmuteEvent(JSONObject jsonObject);

    /**
     * Bot加入了一个新群
     *
     * @param jsonObject 参数
     */
    void botJoinGroupEvent(JSONObject jsonObject);

    /**
     * Bot主动退出一个群
     *
     * @param jsonObject 参数
     */
    void botLeaveEventActive(JSONObject jsonObject);

    /**
     * Bot被踢出一个群
     *
     * @param jsonObject 参数
     */
    void botLeaveEventKick(JSONObject jsonObject);

    /**
     * 某个群名改变
     *
     * @param jsonObject 参数
     */
    void groupNameChangeEvent(JSONObject jsonObject);

    /**
     * 某群入群公告改变
     *
     * @param jsonObject 参数
     */
    void groupEntranceAnnouncementChangeEvent(JSONObject jsonObject);

    /**
     * 全员禁言
     *
     * @param jsonObject 参数
     */
    void groupMuteAllEvent(JSONObject jsonObject);

    /**
     * 匿名聊天
     *
     * @param jsonObject 参数
     */
    void groupAllowAnonymousChatEvent(JSONObject jsonObject);

    /**
     * 坦白说
     *
     * @param jsonObject 参数
     */
    void groupAllowConfessTalkEvent(JSONObject jsonObject);

    /**
     * 允许群员邀请好友加群
     *
     * @param jsonObject 参数
     */
    void groupAllowMemberInviteEvent(JSONObject jsonObject);

    /**
     * 新人入群的事件
     *
     * @param jsonObject 参数
     */
    void memberJoinEvent(JSONObject jsonObject);

    /**
     * 成员被踢出群（该成员不是Bot）
     *
     * @param jsonObject 参数
     */
    void memberLeaveEventKick(JSONObject jsonObject);

    /**
     * 成员主动离群（该成员不是Bot）
     *
     * @param jsonObject 参数
     */
    void memberLeaveEventQuit(JSONObject jsonObject);

    /**
     * 群名片改动
     *
     * @param jsonObject 参数
     */
    void memberCardChangeEvent(JSONObject jsonObject);

    /**
     * 群头衔改动（只有群主有操作限权）
     *
     * @param jsonObject 参数
     */
    void memberSpecialTitleChangeEvent(JSONObject jsonObject);

    /**
     * 成员权限改变的事件（该成员不可能是Bot，见BotGroupPermissionChangeEvent）
     *
     * @param jsonObject 参数
     */
    void memberPermissionChangeEvent(JSONObject jsonObject);

    /**
     * 群成员被禁言事件（该成员不可能是Bot，见BotMuteEvent）
     *
     * @param jsonObject 参数
     */
    void memberMuteEvent(JSONObject jsonObject);

    /**
     * B群成员被取消禁言事件（该成员不可能是Bot，见BotUnmuteEvent）
     *
     * @param jsonObject 参数
     */
    void memberUnmuteEvent(JSONObject jsonObject);

    /**
     * 添加好友申请
     *
     * @param jsonObject 参数
     */
    void newFriendRequestEvent(JSONObject jsonObject);

    /**
     * 用户入群申请（Bot需要有管理员权限）
     *
     * @param jsonObject 参数
     */
    void memberJoinRequestEvent(JSONObject jsonObject);

    /**
     * Bot被邀请入群申请
     *
     * @param jsonObject 参数
     */
    void botInvitedJoinGroupRequestEvent(JSONObject jsonObject);
}
