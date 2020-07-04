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
import java.util.Arrays;
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
    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);

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
     * @param target 发送对象的QQ号或群号，可能存在歧义
     * @param qq 发送对象的QQ号
     * @param group 发送对象的群号
     * @param urls 是一个url字符串构成的数组
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
     * @param sessionKey 已经激活的Session
     * @param type "friend" 或 "group" 或 "temp"
     * @param img 图片文件
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
     * @param sessionKey 已经激活的Session
     * @param target 需要撤回的消息的messageId
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
}
