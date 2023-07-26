package com.example.localdemo.mq.receiver;

import com.example.localdemo.entity.LogMessage;
import com.example.localdemo.mapper.LogMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xieteng
 * @date 2023/7/15 11:55
 * @description TODO
 */
@Slf4j
@Component
public class DirectLogReceiver {
    @Resource
    LogMessageMapper logMessageMapper;

    @RabbitListener(bindings = {@QueueBinding(
        value = @Queue("log-queue"),
        exchange = @Exchange(value = "log-direct",type = ExchangeTypes.DIRECT)
        )
    })
    public void logHandler(LogMessage logMessage){
        log.info("获取到log-queue队列的消息数据为："+logMessage);
        int row = logMessageMapper.insert(logMessage);
        if (row == 1){
            log.info("log-queue队列的消息数据存储成功");
            return;
        }
        log.info("log-queue队列的消息数据存储失败");
    }
}
