package com.piggy.quincy.service.impl;

import com.piggy.quincy.component.MessageSender;
import com.piggy.quincy.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author IMNOTHD
 * @date 2020/6/29 15:27
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageSender messageSender;

    @Override
    public void messageSend(String message) {
        messageSender.sendMessage(message);
    }
}
