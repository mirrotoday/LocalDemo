package com.example.localdemo.mq.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xieteng
 * @date 2023/7/15 12:05
 * @description TODO
 */
@Configuration
public class RabbitMqConfig {
    @Bean
    public MessageConverter jsonMessageCoverter(){
        return new Jackson2JsonMessageConverter();
    }
}
