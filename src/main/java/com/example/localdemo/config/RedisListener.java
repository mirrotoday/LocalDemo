package com.example.localdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * @author xieteng
 * @date 2023/8/25 ❤ 16:41
 * @description TODO
 */
@Slf4j
@Component
public class RedisListener implements MessageListener {
    @Resource
    RedisTemplate redisTemplate;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("【Redis发布订阅模式】当前监听器绑定的pattern: {}",new String(pattern));
        log.info("|消息内容:{}",redisTemplate.getValueSerializer().deserialize(message.getBody()));

    }

}
