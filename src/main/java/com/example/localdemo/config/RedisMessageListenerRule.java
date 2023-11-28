package com.example.localdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;


import java.util.Arrays;
import java.util.List;

/**
 * @author xieteng
 * @date 2023/8/25 ❤ 16:55
 * @description TODO 监听规则
 */
@Configuration
public class RedisMessageListenerRule {
    @Autowired
    private RedisListener lifeRedisMessageListener;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 配置订阅关系
     */
    @Bean
    public RedisMessageListenerContainer container() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        //订阅频道
        List<PatternTopic> topicList = Arrays.asList(new PatternTopic("ws"),new PatternTopic("*.life"));
        container.addMessageListener(lifeRedisMessageListener, topicList);

        return container;
    }
}
