package com.piggy.quincy.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.piggy.quincy.config.BotConfig;
import com.piggy.quincy.service.RedisService;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author IMNOTHD
 * @date 2020/7/4 17:56
 */
@Component
public class TaskComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskComponent.class);
    @Autowired
    private RedisService redisService;
    @Autowired
    private MiraiApiHttpComponent miraiApiHttpComponent;
    @Autowired
    private BotConfig botConfig;
    @Value("${redis.database}")
    private String redisDatabase;

    /**
     * 每28分钟刷新一次Session, 启动后立即刷新
     */
    @Scheduled(fixedRate = 1000 * 60 * 28)
    public void refreshSession() throws IOException {
        Response authResponse = miraiApiHttpComponent.auth(botConfig.getAuthKey());
        JSONObject jsonObject = (JSONObject) JSON.parse(authResponse.body().string());
        if (jsonObject.getInteger("code") != 0) {
            LOGGER.info("Wrong Auth Key: {}", botConfig.getAuthKey());
            return;
        }
        String sessionKey = jsonObject.getString("session");
        if (sessionKey == null) {
            throw new NullPointerException();
        }

        Response verifyResponse = miraiApiHttpComponent.verify(sessionKey, botConfig.getQq());
        jsonObject = JSON.parseObject(verifyResponse.body().string());
        if (jsonObject.getInteger("code") != 0) {
            LOGGER.info("Something Wrong during verify session key: {}", jsonObject.getString("msg"));
            return;
        }

        String key = redisDatabase + ":sessionKey";
        redisService.set(key, sessionKey);
        LOGGER.info("Session Refreshed: {}", sessionKey);
    }

    /**
     * 每5分钟执行一次的普通任务
     */
    @Scheduled(fixedRate = 1000 * 60 * 5)
    private void commonTask() {
        // 赦免概率, 存入的是乘100后的概率, 避免精度问题
        int unbanProbability = (int) Math.floor(Math.random() * botConfig.getRandomUnbanProbabilityLimit() * 100);
        String unbanProbabilityKey = redisDatabase + ":unbanProbability";
        redisService.set(unbanProbabilityKey, String.valueOf(unbanProbability));
    }
}
