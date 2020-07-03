package com.piggy.quincy.component;

import com.piggy.quincy.config.BotConfig;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 封装Mirai操作, 如发送信息等
 *
 * @author IMNOTHD
 * @date 2020/6/29 17:13
 */
@Component
public class MiraiApiHttpComponent {
    private final OkHttpClient okHttpClient = new OkHttpClient();
    @Autowired
    private BotConfig botConfig;
    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);

    public String about() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(botConfig.getApiUrl() + "/about")
                .build();
        Response response = okHttpClient.newCall(request).execute();

        return response.body().string();
    }
}
