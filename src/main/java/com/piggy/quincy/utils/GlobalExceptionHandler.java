package com.piggy.quincy.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

/**
 * 全局异常处理, 打log
 * @author IMNOTHD
 * @date 2020/7/7 22:38
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    public void exception(Exception e) {
        LOGGER.error(Arrays.toString(e.getStackTrace()));
    }
}
