package com.example.localdemo.mq.receiver;

import cn.hutool.json.JSONUtil;
import com.example.localdemo.mq.entity.MailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

/**
 * @author xieteng
 * @date 2023/3/6 15:39
 * @description TODO
 */
@Component
@Slf4j
public class DirectReceiver {
    //从配置文件中获取发送人账号
    @Value("${spring.mail.username}")
    private String sender;
    @Resource
    private JavaMailSender mailSender;
    //监听消息队列
    @RabbitListener(queues = "mail-queue")
    public void directHandler(String msg){
        log.info("开始消费接口：msg为 = " + msg);
        MailVO emailDTO = JSONUtil.toBean(new String(msg), MailVO.class);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(emailDTO.getReceiver());
        message.setSubject(emailDTO.getType());
        message.setText(emailDTO.getContent());
        mailSender.send(message);
        log.info("邮件已经发送至："+emailDTO.getReceiver());
    }
}