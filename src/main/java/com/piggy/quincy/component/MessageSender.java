package com.piggy.quincy.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.piggy.quincy.config.RabbitMQConfig.QUEUE_NAME;

/**
 * 消息发送者
 * @author IMNOTHD
 * @date 2020/5/5 0:34
 */
@Component
public class MessageSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private static Logger logger = LoggerFactory.getLogger(MessageSender.class);

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(QUEUE_NAME, message);
        logger.info("receive message: {}", message);
    }
}
