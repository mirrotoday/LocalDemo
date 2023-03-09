package com.example.localdemo.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.localdemo.entity.Message;
import com.example.localdemo.mq.config.RedisKeyConstant;
import com.example.localdemo.service.MessageService;
import com.example.localdemo.mapper.MessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;

/**
* @author siyu
* @description 针对表【message】的数据库操作Service实现
* @createDate 2023-03-07 15:11:19
*/
@Service
@Slf4j
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService{
    @Resource
    RabbitTemplate rabbitTemplate;

    @Value("${spring.mail.username}")
    private String sender;

    @Resource
    private MessageMapper messageMapper;

    @Override
    public int sendMessageMail(List<String> mail) {
        int count = 0;
        //发送邮件
        for (String mailaddr:mail) {
//            MailVO buildMail = new MailVO();
//            buildMail.setSender(sender);
//            buildMail.setReceiver(mailaddr);
            Message buildMail = new Message();
            buildMail.setSender(sender);
            buildMail.setReceiver(mailaddr);
            String mailData = JSON.toJSONString(buildMail);
            //放进消息队列
            rabbitTemplate.convertAndSend("mail-queue",mailData.toString());
            log.info("消息发送成功");
            count ++;
        }
        return count;
    }

    @Override
    public List<Message> selectAll() {
//        QueryWrapper<Message> query = new QueryWrapper<Message>();
//        query.orderByAsc("createTiem");
        List<Message> messages = messageMapper.selectallData();
        return messages;
    }

    @Override
    public List<Message> selectOneById(String id) {
        List<Message> message = messageMapper.selectOneById(id);
        return message;
    }
}




