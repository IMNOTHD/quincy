package com.piggy.quincy.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.piggy.quincy.config.RabbitMQConfig.QUEUE_NAME;

/**
 * 消息处理
 *
 * @author IMNOTHD
 * @date 2020/5/5 0:35
 */
@Component
@RabbitListener(queues = QUEUE_NAME, concurrency = "25")
public class MessageHandler {
    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);

    @RabbitHandler
    public void messageHandle(String message) throws InterruptedException {
        LOGGER.info("handle message: {}", message);
    }
}
