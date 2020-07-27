package com.piggy.quincy.component;

import com.alibaba.fastjson.JSONObject;
import com.piggy.quincy.config.BotConfig;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

/**
 * 封装Mirai操作, 如发送信息等
 *
 * @author IMNOTHD
 * @date 2020/6/29 17:13
 */
@Component
public class MiraiApiHttpComponent {
    private final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .callTimeout(Duration.ofMinutes(5))
            .connectTimeout(Duration.ofMinutes(5))
            .writeTimeout(Duration.ofMinutes(5))
            .readTimeout(Duration.ofMinutes(5))
            .build();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType FORM_DATA = MediaType.parse("multipart/form-data");
    @Autowired
    private BotConfig botConfig;

    private static final Logger LOGGER = LoggerFactory.getLogger(MiraiApiHttpComponent.class);

    /**
     * 获取插件信息
     *
     * @return Response
     * @throws IOException
     */
    public Response about() throws IOException {
        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/about")
                .get()
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 开始会话-认证(Authorize)
     *
     * @param authKey 创建Mirai-Http-Server时生成的key
     * @return Response
     * @throws IOException
     */
    public Response auth(String authKey) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authKey", authKey);

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/auth")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 校验Session
     *
     * @param sessionKey 你的session key
     * @param qq         Session将要绑定的Bot的qq号
     * @return Response
     * @throws IOException
     */
    public Response verify(String sessionKey, Long qq) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey", sessionKey);
        jsonObject.put("qq", qq);

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/verify")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 释放Session
     *
     * @param sessionKey 你的session key
     * @param qq         与该Session绑定Bot的QQ号码
     * @return Response
     * @throws IOException
     */
    public Response release(String sessionKey, Long qq) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey", sessionKey);
        jsonObject.put("qq", qq);

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/release")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 发送好友消息
     *
     * @param sessionKey   已经激活的Session
     * @param target       可选，发送消息目标好友的QQ号
     * @param qq           可选，target与qq中需要有一个参数不为空，当target不为空时qq将被忽略，同target
     * @param quote        引用一条消息的messageId进行回复
     * @param messageChain 消息链，是一个消息对象构成的数组
     * @return Response
     * @throws IOException
     */
    public Response sendFriendMessage(String sessionKey, Long target, Long qq, Integer quote, List<JSONObject> messageChain) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey", sessionKey);
        jsonObject.put("target", target);
        jsonObject.put("qq", qq);
        jsonObject.put("quote", quote);
        jsonObject.put("messageChain", messageChain);

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/sendFriendMessage")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 发送临时会话消息
     *
     * @param sessionKey   已经激活的Session
     * @param qq           临时会话对象QQ号
     * @param group        临时会话群号
     * @param quote        引用一条消息的messageId进行回复
     * @param messageChain 消息链，是一个消息对象构成的数组
     * @return Response
     * @throws IOException
     */
    public Response sendTempMessage(String sessionKey, Long qq, Long group, Integer quote, List<JSONObject> messageChain) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey", sessionKey);
        jsonObject.put("qq", qq);
        jsonObject.put("group", group);
        jsonObject.put("quote", quote);
        jsonObject.put("messageChain", messageChain);

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/sendTempMessage")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 发送群消息
     *
     * @param sessionKey   已经激活的Session
     * @param target       可选，发送消息目标群的群号
     * @param group        可选，target与group中需要有一个参数不为空，当target不为空时group将被忽略，同target
     * @param quote        引用一条消息的messageId进行回复
     * @param messageChain 消息链，是一个消息对象构成的数组
     * @return Response
     * @throws IOException
     */
    public Response sendGroupMessage(String sessionKey, Long target, Long group, Integer quote, List<JSONObject> messageChain) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey", sessionKey);
        jsonObject.put("target", target);
        jsonObject.put("group", group);
        jsonObject.put("quote", quote);
        jsonObject.put("messageChain", messageChain);

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/sendGroupMessage")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 发送图片消息（通过URL）
     * 使用此方法向指定对象（群或好友）发送图片消息 除非需要通过此手段获取imageId，否则不推荐使用该接口
     *
     * @param sessionKey 已经激活的Session
     * @param target     发送对象的QQ号或群号，可能存在歧义
     * @param qq         发送对象的QQ号
     * @param group      发送对象的群号
     * @param urls       是一个url字符串构成的数组
     * @return Response
     * @throws IOException
     */
    public Response sendImageMessage(String sessionKey, Long target, Long qq, Long group, List<String> urls) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey", sessionKey);
        jsonObject.put("target", target);
        jsonObject.put("qq", qq);
        jsonObject.put("group", group);
        jsonObject.put("urls", urls);

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/sendImageMessage")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 图片文件上传
     *
     * @param sessionKey 已经激活的Session
     * @param type       "friend" 或 "group" 或 "temp"
     * @param img        图片文件
     * @return Response
     * @throws IOException
     */
    public Response uploadImage(String sessionKey, ImageType type, File img) throws IOException {
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), img);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("sessionKey", sessionKey)
                .addFormDataPart("type", type.getType())
                .addFormDataPart("img", img.getName(), fileBody)
                .build();

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/uploadImage")
                .header("Content-Type", "multipart/form-data")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 撤回消息
     *
     * @param sessionKey 已经激活的Session
     * @param target     需要撤回的消息的messageId
     * @return Response
     * @throws IOException
     */
    public Response recall(String sessionKey, Long target) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey", sessionKey);
        jsonObject.put("target", target);

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/recall")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 响应 -> 添加好友申请
     *
     * @param sessionKey session key
     * @param eventId    响应申请事件的标识
     * @param fromId     事件对应申请人QQ号
     * @param groupId    事件对应申请人的群号，可能为0
     * @param operate    响应的操作类型
     * @param message    回复的信息
     * @return Response
     * @throws IOException
     */
    public Response respNewFriendRequestEvent(String sessionKey, Long eventId, Long fromId, Long groupId, FriendRequestOperate operate, String message) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey", sessionKey);
        jsonObject.put("eventId", eventId);
        jsonObject.put("fromId", fromId);
        jsonObject.put("groupId", groupId);
        jsonObject.put("operate", operate.getOperate());
        jsonObject.put("message", message);

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/resp/memberJoinRequestEvent")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 响应 -> 用户入群申请（Bot需要有管理员权限）
     *
     * @param sessionKey session key
     * @param eventId    响应申请事件的标识
     * @param fromId     事件对应申请人QQ号
     * @param groupId    事件对应申请人的群号
     * @param operate    响应的操作类型
     * @param message    回复的信息
     * @return Response
     * @throws IOException
     */
    public Response respMemberJoinRequestEvent(String sessionKey, Long eventId, Long fromId, Long groupId, MemberJoinRequestOperate operate, String message) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey", sessionKey);
        jsonObject.put("eventId", eventId);
        jsonObject.put("fromId", fromId);
        jsonObject.put("groupId", groupId);
        jsonObject.put("operate", operate.getOperate());
        jsonObject.put("message", message);

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/resp/memberJoinRequestEvent")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 响应 -> Bot被邀请入群申请
     *
     * @param sessionKey session key
     * @param eventId    响应申请事件的标识
     * @param fromId     事件对应申请人QQ号
     * @param groupId    事件对应申请人的群号
     * @param operate    响应的操作类型
     * @param message    回复的信息
     * @return Response
     * @throws IOException
     */
    public Response respBotInvitedJoinGroupRequestEvent(String sessionKey, Long eventId, Long fromId, Long groupId, BotInvitedJoinGroupRequestOperate operate, String message) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey", sessionKey);
        jsonObject.put("eventId", eventId);
        jsonObject.put("fromId", fromId);
        jsonObject.put("groupId", groupId);
        jsonObject.put("operate", operate.getOperate());
        jsonObject.put("message", message);

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/resp/botInvitedJoinGroupRequestEvent")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 获取bot的好友列表
     *
     * @param sessionKey session key
     * @return Response
     * @throws IOException
     */
    public Response friendList(String sessionKey) throws IOException {
        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + String.format("/friendList?sessionKey=%s", sessionKey))
                .get()
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 获取bot的群列表
     *
     * @param sessionKey session key
     * @return Response
     * @throws IOException
     */
    public Response groupList(String sessionKey) throws IOException {
        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + String.format("/groupList?sessionKey=%s", sessionKey))
                .get()
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 获取bot指定群种的成员列表
     *
     * @param sessionKey session key
     * @param target     指定群的群号
     * @return Response
     * @throws IOException
     */
    public Response memberList(String sessionKey, Long target) throws IOException {
        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + String.format("/groupList?sessionKey=%s&target=%d", sessionKey, target))
                .get()
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 令指定群进行全体禁言（需要有相关限权）
     *
     * @param sessionKey session key
     * @param target     指定群的群号
     * @return Response
     * @throws IOException
     */
    public Response muteAll(String sessionKey, Long target) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey", sessionKey);
        jsonObject.put("target", target);

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/muteAll")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 令指定群解除全体禁言（需要有相关限权）
     *
     * @param sessionKey session key
     * @param target     指定群的群号
     * @return Response
     * @throws IOException
     */
    public Response unmuteAll(String sessionKey, Long target) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey", sessionKey);
        jsonObject.put("target", target);

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/unmuteAll")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 指定群禁言指定群员（需要有相关限权）
     *
     * @param sessionKey session key
     * @param target     指定群的群号
     * @param memberId   指定群员QQ号
     * @param time       禁言时长，单位为秒，最多30天，默认为0
     * @return Response
     * @throws IOException
     */
    public Response mute(String sessionKey, Long target, Long memberId, Integer time) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey", sessionKey);
        jsonObject.put("target", target);
        jsonObject.put("memberId", memberId);
        jsonObject.put("time", time);

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/mute")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 令指定群解除全体禁言（需要有相关限权）
     *
     * @param sessionKey session key
     * @param target     指定群的群号
     * @param memberId   指定群员QQ号
     * @return Response
     * @throws IOException
     */
    public Response unmute(String sessionKey, Long target, Long memberId) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey", sessionKey);
        jsonObject.put("target", target);
        jsonObject.put("memberId", memberId);

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/unmute")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 移除指定群成员（需要有相关限权）
     *
     * @param sessionKey session key
     * @param target     指定群的群号
     * @param memberId   指定群员QQ号
     * @param msg        信息
     * @return Response
     * @throws IOException
     */
    public Response kick(String sessionKey, Long target, Long memberId, String msg) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey", sessionKey);
        jsonObject.put("target", target);
        jsonObject.put("memberId", memberId);
        jsonObject.put("msg", msg);

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/kick")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 使Bot退出群聊
     *
     * @param sessionKey session key
     * @param target     指定群的群号
     * @return
     * @throws IOException
     */
    public Response quit(String sessionKey, Long target) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey", sessionKey);
        jsonObject.put("target", target);

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/quit")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 修改群设置（需要有相关限权）
     *
     * @param sessionKey        session key
     * @param target            指定群的群号
     * @param name              群名
     * @param announcement      群公告
     * @param confessTalk       是否开启坦白说
     * @param allowMemberInvite 是否运行群员邀请
     * @param autoApprove       是否开启自动审批入群
     * @param anonymousChat     是否允许匿名聊天
     * @return Response
     * @throws IOException
     */
    public Response groupConfig(String sessionKey, Long target, String name, boolean announcement, boolean confessTalk, boolean allowMemberInvite, boolean autoApprove, boolean anonymousChat) throws IOException {
        JSONObject config = new JSONObject();
        config.put("name", name);
        config.put("announcement", announcement);
        config.put("confessTalk", confessTalk);
        config.put("allowMemberInvite", allowMemberInvite);
        config.put("autoApprove", autoApprove);
        config.put("anonymousChat", anonymousChat);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey", sessionKey);
        jsonObject.put("target", target);
        jsonObject.put("config", config);


        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/groupConfig")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 获取群设置
     *
     * @param sessionKey session key
     * @param target     指定群的群号
     * @return Response
     * @throws IOException
     */
    public Response groupConfig(String sessionKey, Long target) throws IOException {
        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + String.format("/groupConfig?sessionKey=%s&target=%d", sessionKey, target))
                .get()
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 修改群员资料（需要有相关限权）
     *
     * @param sessionKey   session key
     * @param target       指定群的群号
     * @param memberId     群员QQ号
     * @param name         群名片，即群昵称
     * @param specialTitle 群头衔
     * @return Response
     * @throws IOException
     */
    public Response memberInfo(String sessionKey, Long target, Long memberId, String name, String specialTitle) throws IOException {
        JSONObject info = new JSONObject();
        info.put("name", name);
        info.put("specialTitle", specialTitle);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey", sessionKey);
        jsonObject.put("target", target);
        jsonObject.put("memberId", memberId);
        jsonObject.put("info", info);


        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/memberInfo")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 获取群员资料
     *
     * @param sessionKey session key
     * @param target     指定群的群号
     * @return Response
     * @throws IOException
     */
    public Response memberInfo(String sessionKey, Long target, Long memberId) throws IOException {
        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + String.format("/memberInfo?sessionKey=%s&target=%d&memberId=%s", sessionKey, target, memberId))
                .get()
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 获取指定Session的配置信息，注意该配置是Session范围有效
     *
     * @param sessionKey session key
     * @return Response
     * @throws IOException
     */
    public Response config(String sessionKey) throws IOException {
        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + String.format("/config?sessionKey=%s", sessionKey))
                .get()
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 设置指定Session的配置信息，注意该配置是Session范围有效
     *
     * @param sessionKey      session key
     * @param cacheSize       缓存大小
     * @param enableWebsocket 是否开启Websocket
     * @return Response
     * @throws IOException
     */
    public Response config(String sessionKey, int cacheSize, boolean enableWebsocket) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey", sessionKey);
        jsonObject.put("cacheSize", cacheSize);
        jsonObject.put("enableWebsocket", enableWebsocket);

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toJSONString());

        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + "/config")
                .post(requestBody)
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 获取Mangers
     *
     * @param qq qq
     * @return Response
     * @throws IOException
     */
    public Response managers(String qq) throws IOException {
        Request request = new Request.Builder()
                .url(botConfig.getApiUrl() + String.format("/managers?qq=%s", qq))
                .get()
                .build();

        return okHttpClient.newCall(request).execute();
    }

    /**
     * 图片接受者类型
     */
    public enum ImageType {
        // 接受者类型
        FRIEND("friend"),
        GROUP("group"),
        TEMP("temp");

        private String type;

        private ImageType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * 好友申请同意类型
     */
    public enum FriendRequestOperate {
        // 好友申请同意类型
        ACCEPT(0),
        REJECT(1),
        REJECT_AND_BLOCK(2);

        private Integer operate;

        private FriendRequestOperate(Integer operate) {
            this.operate = operate;
        }

        public Integer getOperate() {
            return operate;
        }
    }

    /**
     * 用户入群申请同意类型
     */
    public enum MemberJoinRequestOperate {
        // 用户入群申请同意类型
        ACCEPT(0),
        REJECT(1),
        IGNORE(2),
        REJECT_AND_BLOCK(3),
        IGNORE_AND_BLOCK(4);

        private Integer operate;

        private MemberJoinRequestOperate(Integer operate) {
            this.operate = operate;
        }

        public Integer getOperate() {
            return operate;
        }
    }

    /**
     * 用户入群申请同意类型
     */
    public enum BotInvitedJoinGroupRequestOperate {
        // 用户入群申请同意类型
        ACCEPT(0),
        REJECT(1);

        private Integer operate;

        private BotInvitedJoinGroupRequestOperate(Integer operate) {
            this.operate = operate;
        }

        public Integer getOperate() {
            return operate;
        }
    }
}
