package com.example.localdemo.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xieteng
 * @date 2023/3/6 15:33
 * @description TODO
 */
@Configuration
public class DirectConfig {

    //配置消息队列
    @Bean
    Queue directQueue(){
        return new Queue("mail-queue");
    }

    @Bean
    Queue logQueue(){
        return new Queue("log-queue");
    }

    //如果使用的是direct模式，则下面两个bean可以省略

    //配置交换机
    @Bean
    DirectExchange directExchange(){
        //参数1：交换机的名称  参数2：队列重启后是否删除  参数3：队列长期不用是否自动删除
        return new DirectExchange("fanle-direct",true,false);
    }
    @Bean
    DirectExchange logExchange(){
        //参数1：交换机的名称  参数2：队列重启后是否删除  参数3：队列长期不用是否自动删除
        return new DirectExchange("log-direct",true,false);
    }

    //将消息队列和交换机绑定
    @Bean
    Binding directBinding(){
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("direct");
    }

}

