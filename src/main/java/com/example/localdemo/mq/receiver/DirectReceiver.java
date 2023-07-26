package com.example.localdemo.mq.receiver;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.localdemo.entity.Message;
import com.example.localdemo.mapper.MessageMapper;
import com.example.localdemo.mq.config.RedisKeyConstant;
import com.example.localdemo.mq.entity.MailVO;
import com.example.localdemo.result.ApiResult;
import com.example.localdemo.utils.CodeUtils;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import javax.mail.AuthenticationFailedException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.Duration;

/**
 * @author xieteng
 * @date 2023/3/6 15:39
 * @description TODO
 */
//@Component
@Slf4j
public class DirectReceiver {
    @Resource
    private JavaMailSender mailSender;

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //监听消息(消费者1)
    @RabbitListener(queues = "mail-queue")
    public void directHandlerOne(String msg, Channel channel) throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("directHandler【One】开始消费接口：msg为 = " + msg);
        sendMail(msg,"directHandlerOne",channel);
        stopWatch.stop();
        log.info("directHandlerOne消费耗时："+stopWatch.getTotalTimeMillis()+"ms");
    }
    //监听消息(消费者2)
    @RabbitListener(queues = "mail-queue")
    public void directHandlerTwo(String msg, Channel channel) throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("directHandler【Two】开始消费接口：msg为 = " + msg);
        sendMail(msg,"directHandlerTwo",channel);
        stopWatch.stop();
        log.info("directHandlerTwo消费耗时："+stopWatch.getTotalTimeMillis()+"ms");
    }
    @Transactional
    public void sendMail(String msg,String type,Channel channel) throws IOException {
        Message emailVo = JSONUtil.toBean(msg, Message.class);
        //设置消费者
        emailVo.setQueuesname(type);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailVo.getSender());
        message.setTo(emailVo.getReceiver());
        message.setSubject(emailVo.getTitle());
        message.setText(emailVo.getContent());
        try{
            mailSender.send(message);
        }catch (Exception e){
            channel.basicCancel(msg);
            throw new RuntimeException(e.getMessage());
        }
        log.info("邮件已经发送至："+emailVo.getReceiver());
        messageMapper.insert(emailVo);
        log.info("数据存储成功："+emailVo.getId());

        //将验证码存储到Redis
        String key = RedisKeyConstant.verify_code.getKey() + emailVo.getReceiver();
        stringRedisTemplate.opsForValue().set(key,emailVo.getCode(), Duration.ofMinutes(5));
        String mailCode = stringRedisTemplate.opsForValue().get(key);
        log.info("验证码存储Redis成功："+mailCode);
    }
}