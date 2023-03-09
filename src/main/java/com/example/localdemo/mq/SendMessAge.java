package com.example.localdemo.mq;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSON;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.example.localdemo.mq.config.RedisKeyConstant;
import com.example.localdemo.mq.entity.MailVO;
import com.example.localdemo.utils.CodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xieteng
 * @date 2023/3/6 15:42
 * @description TODO
 */
@RequestMapping("/sys")
@RestController
@Slf4j
public class SendMessAge {
    @Resource
    RabbitTemplate rabbitTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    //从配置文件中获取发送人账号
    @Value("${spring.mail.username}")
    private String sender;

    @GetMapping("/sendMail/{Remail}")
    public String SendMail(@PathVariable String Remail) {
        String nanoId = CodeUtils.getNanoId();
        System.out.println("生成的ID为："+nanoId+"，长度："+nanoId.length());
        //将生成的验证代存入redis,并设置5分钟后失效
        MailVO buildMail = new MailVO();
        buildMail.setSender(sender);
        buildMail.setReceiver(Remail);
        String mailData = JSON.toJSONString(buildMail);
        //将验证码存储到Redis
        String key = RedisKeyConstant.verify_code.getKey() + buildMail.getReceiver();
        stringRedisTemplate.opsForValue().set(key,buildMail.getCode(), Duration.ofMinutes(5));
        String mailCode = stringRedisTemplate.opsForValue().get(key);
        log.info("redis中的验证码为："+mailCode);
        //放进消息队列
        rabbitTemplate.convertAndSend("mail-queue",mailData.toString());
        return mailData;
    }
    @PostMapping("/sendBachMail")
    public List<MailVO> SendBatchMail(@RequestBody List<String> mail){
        List<MailVO> allData = new ArrayList<>();
        if (mail.size() != 0){

        }
        return allData;
    }
}
