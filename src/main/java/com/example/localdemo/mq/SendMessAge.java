package com.example.localdemo.mq;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.localdemo.mq.entity.MailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * @author xieteng
 * @date 2023/3/6 15:42
 * @description TODO
 */
@RequestMapping("/sys")
@RestController
@Slf4j
public class SendMessAge {
    private final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";
    @Resource
    RabbitTemplate rabbitTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    //从配置文件中获取发送人账号
    @Value("${spring.mail.username}")
    private String sender;

    public RabbitTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }
    @GetMapping("/sendmail")
    public String SendMail() {
        String code = RandomUtil.randomString(BASE_CHECK_CODES,6);
        //转换成小写
        String lowerCaseCaptcha = code.toLowerCase();
        //将生成的验证代存入redis,并设置5分钟后失效
        stringRedisTemplate.opsForValue().set("MailCode",lowerCaseCaptcha, Duration.ofMinutes(5));
        String mailCode = stringRedisTemplate.opsForValue().get("MailCode");
        log.info("redis中的验证码为："+mailCode);
        MailVO buildMail = new MailVO();
        buildMail.setSender(sender);
        buildMail.setReceiver("xpossess@gmail.com");
        buildMail.setType("验证码");
        buildMail.setContent("您的验证码为 " + code + " 有效期5分钟，请不要告诉他人哦！");
        JSONObject mailData = JSONUtil.parseObj(buildMail);
        rabbitTemplate.convertAndSend("mail-queue",mailData.toString());
        return "消息发送成功";
    }
}
