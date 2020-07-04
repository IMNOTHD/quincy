package com.piggy.quincy.component;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

/**
 * @author IMNOTHD
 * @date 2020/7/4 12:26
 */
@Component
public class MessageBuilderComponent {
    /**
     * 消息源
     *
     * @param id   消息的识别号，用于引用回复（Source类型永远为chain的第一个元素）
     * @param time 时间戳
     * @return message
     */
    public static JSONObject source(int id, int time) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "Source");
        jsonObject.put("id", id);
        jsonObject.put("time", time);

        return jsonObject;
    }

    /**
     * 引用回复
     *
     * @param id       被引用回复的原消息的messageId
     * @param groupId  被引用回复的原消息所接收的群号，当为好友消息时为0
     * @param senderId 被引用回复的原消息的发送者的QQ号
     * @param targetId 被引用回复的原消息的接收者者的QQ号（或群号）
     * @param origin   被引用回复的原消息的消息链对象
     * @return message
     */
    public static JSONObject quote(int id, Long groupId, Long senderId, Long targetId, Object origin) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "Quote");
        jsonObject.put("id", id);
        jsonObject.put("groupId", groupId);
        jsonObject.put("senderId", senderId);
        jsonObject.put("targetId", targetId);
        jsonObject.put("origin", origin);

        return jsonObject;
    }

    /**
     * At个人
     *
     * @param target  群员QQ号
     * @param display At时显示的文字，发送消息时无效，自动使用群名片
     * @return message
     */
    public static JSONObject at(Long target, String display) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "At");
        jsonObject.put("target", target);
        jsonObject.put("display", display);

        return jsonObject;
    }

    /**
     * At全体成员
     *
     * @return message
     */
    public static JSONObject atAll() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "AtAll");

        return jsonObject;
    }

    /**
     * 表情
     *
     * @param faceId QQ表情编号，可选，优先高于name
     * @param name   QQ表情拼音，可选
     * @return message
     */
    public static JSONObject face(int faceId, String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "Face");
        jsonObject.put("faceId", faceId);
        jsonObject.put("name", name);

        return jsonObject;
    }

    /**
     * 文字消息
     *
     * @param text 文字消息
     * @return message
     */
    public static JSONObject plain(String text) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "Plain");
        jsonObject.put("text", text);

        return jsonObject;
    }

    /**
     * 图片
     * 三个参数任选其一，出现多个参数时，按照imageId > url > file的优先级
     * 注意: 好友图片格式如"f8f1ab55-bf8e-4236-b55e-955848d7069f", 群图片格式如"{01E9451B-70ED-EAE3-B37C-101F1EEBF5B5}.mirai"
     *
     * @param imageId 图片的imageId，群图片与好友图片格式不同。不为空时将忽略url属性
     * @param url     图片的URL，发送时可作网络图片的链接；接收时为腾讯图片服务器的链接，可用于图片下载
     * @param path    图片的路径，发送本地图片，相对路径于plugins/MiraiAPIHTTP/images
     * @return message
     */
    public static JSONObject image(String imageId, String url, String path) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "Image");
        jsonObject.put("imageId", imageId);
        jsonObject.put("url", url);
        jsonObject.put("path", path);

        return jsonObject;
    }

    /**
     * 动图
     * 三个参数任选其一，出现多个参数时，按照imageId > url > file的优先级
     * 注意: 好友图片格式如"f8f1ab55-bf8e-4236-b55e-955848d7069f", 群图片格式如"{01E9451B-70ED-EAE3-B37C-101F1EEBF5B5}.mirai"
     *
     * @param imageId 图片的imageId，群图片与好友图片格式不同。不为空时将忽略url属性
     * @param url     图片的URL，发送时可作网络图片的链接；接收时为腾讯图片服务器的链接，可用于图片下载
     * @param path    图片的路径，发送本地图片，相对路径于plugins/MiraiAPIHTTP/images
     * @return message
     */
    public static JSONObject flashImage(String imageId, String url, String path) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "FlashImage");
        jsonObject.put("imageId", imageId);
        jsonObject.put("url", url);
        jsonObject.put("path", path);

        return jsonObject;
    }

    /**
     * Xml
     * @param xml XML文本
     * @return message
     */
    public static JSONObject xml(String xml) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "Xml");
        jsonObject.put("xml", xml);

        return jsonObject;
    }

    /**
     * Json
     * @param json Json文本
     * @return messsage
     */
    public static JSONObject json(String json) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "Json");
        jsonObject.put("json", json);

        return jsonObject;
    }

    /**
     * App
     * @param content 内容
     * @return message
     */
    public static JSONObject app(String content) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "App");
        jsonObject.put("content", content);

        return jsonObject;
    }

    /**
     * Poke
     * @param name 戳一戳的类型
     * @return message
     */
    public static JSONObject poke(Poke name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "Poke");
        jsonObject.put("name", name.getName());

        return jsonObject;
    }

    /**
     * 戳一戳类型
     */
    public enum Poke {
        // 戳一戳
        POKE("Poke"),
        // 比心
        SHOW_LOVE("ShowLove"),
        // 点赞
        LIKE("Like"),
        // 心碎
        HEART_BROKEN("Heartbroken"),
        // 666
        SIX_SIX_SIX("SixSixSix"),
        // 放大招
        FANG_DA_ZHAO("FangDaZhao");

        private String name;
        private Poke(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
