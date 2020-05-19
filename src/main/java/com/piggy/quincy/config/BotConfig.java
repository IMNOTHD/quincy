package com.piggy.quincy.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author IMNOTHD
 * @date 2020/5/19 13:54
 */
@Component
public class BotConfig {
    @Value("${mirai.http.authKey}")
    @Getter
    private String authKey;

    @Value("${mirai.http.qq}")
    @Getter
    private Long qq;
}
