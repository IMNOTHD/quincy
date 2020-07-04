package com.piggy.quincy.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.piggy.quincy.service.EventService;
import com.piggy.quincy.utils.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.*;

import static com.piggy.quincy.config.RabbitMQConfig.QUEUE_NAME;

/**
 * 消息处理
 *
 * @author IMNOTHD
 * @date 2020/5/5 0:35
 */
@Component
@RabbitListener(queues = QUEUE_NAME, concurrency = "64")
public class MessageHandler {
    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);
    @Autowired
    private EventService eventService;

    @RabbitHandler
    public void messageHandle(String message) throws NoSuchMethodException {
        LOGGER.info("handle message: {}", message);

        JSONObject jsonObject;
        try {
            jsonObject = JSON.parseObject(message);
        } catch (JSONException e) {
            return;
        }
        if (jsonObject == null) {
            return;
        }
        String type = jsonObject.getString("type");

        // type首字母转小写, 否则反射找不到Method
        char[] chars = type.toCharArray();
        chars[0] += 32;
        type = String.valueOf(chars);

        Class<?> eventServiceClass = eventService.getClass();

        // 反射对应的Event处理方法
        Method method = eventServiceClass.getMethod(type, JSONObject.class);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            // 这里不能使用eventServiceClass.newInstance(),
            // 在调用invoke反射方法时, Class是直接使用newInstance静态方法来实例化对象,
            // 导致对应@value、@Autowired等注解失效
            method.invoke(SpringBeanUtil.getBean(eventServiceClass), jsonObject);
            return "success";
        });
        executorService.execute(futureTask);

        try {
            // 单个线程设置5分钟超时, 超时则打断
            String result = futureTask.get(5, TimeUnit.MINUTES);
        } catch (TimeoutException e) {
            LOGGER.info("Handle Time Out: {}", message);
        } catch (InterruptedException | ExecutionException ignore) {
        }
    }
}
