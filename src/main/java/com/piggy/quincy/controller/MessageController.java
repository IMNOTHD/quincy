package com.piggy.quincy.controller;

import com.piggy.quincy.common.CommonResult;
import com.piggy.quincy.config.BotConfig;
import com.piggy.quincy.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 接收消息上报Controller类
 *
 * @author IMNOTHD
 * @date 2020/5/3 16:59
 */
@Controller
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private BotConfig botConfig;

    /**
     * 收到消息后, 向消息队列里发送消息
     *
     * @param message 收到的消息
     * @return 状态码
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<String> send(@RequestBody String message, HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader("Authorization");
        if (authorization == null || authorization.equals(botConfig.getAuthKey())) {
            return CommonResult.forbidden();
        }

        messageService.messageSend(message);
        return CommonResult.success();
    }
}
