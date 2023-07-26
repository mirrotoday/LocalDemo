package com.example.localdemo.annotation.aspect;

import com.alibaba.fastjson.JSON;
import com.example.localdemo.entity.LogMessage;
import com.example.localdemo.utils.CodeUtils;
import com.example.localdemo.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
 * @author xieteng
 * @date 2023/7/15 10:38
 * @description TODO
 */
@Slf4j
@Aspect
@Component
public class TakeTimeAspect {
    @Resource
    RabbitTemplate rabbitTemplate;

    ThreadLocal<Long> startTime = new ThreadLocal<>();
    ThreadLocal<Long> endTime = new ThreadLocal<>();
    ThreadLocal<LogMessage> LogSendToMq = new ThreadLocal<>();
    @Pointcut("@annotation(com.example.localdemo.annotation.TakeTime)")
    public void takeTimePointCut(){

    }

    /**
     * 方法请求之前，获取以下参数
     * 1.定义一个开始时间
     * 2.获取请求的URL
     * 3.获取请求的Method
     * 4.获取请求的参数
     * 5.获取请求的IP地址
     * 6.获取请求的浏览器类型标识
     */
    @Before("takeTimePointCut()")
    public void takeTimeBeforMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        startTime.set(System.currentTimeMillis());
        LogMessage logMessage = new LogMessage();
        logMessage.setStartTime(new Date());
        log.info("[{}]方法开始时间：{}",methodName,DateUtils.getNow());
        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String type = request.getMethod();
        StringBuffer requestURL = request.getRequestURL();
        String ipAddr = CodeUtils.getIpAddr(request);
        String userAgent = request.getHeader("user-agent");
        log.info("[{}]方法,请求的时间为：{},请求的介质为：{},当前请求的URL为：{},请求类型为：{},用户请求的IP地址为：{}",methodName, DateUtils.getNow(),userAgent,requestURL,type,ipAddr);
        logMessage.setRequestStyle(userAgent);
        logMessage.setRequestUrl(String.valueOf(requestURL));
        logMessage.setRequestType(type);
        logMessage.setRequestIp(ipAddr);
        LogSendToMq.set(logMessage);
    }
    @AfterReturning(value = "takeTimePointCut()",returning = "result")
    public void takeTimeAfterMethod(JoinPoint joinPoint,Object result){
        LogMessage logMessage = LogSendToMq.get();
        log.info("请求返回的结果为：{}", JSON.toJSONString(result));
        endTime.set(System.currentTimeMillis());
        log.info("[{}]方法结束时间：{}",joinPoint.getSignature().getName(),DateUtils.getNow());
        log.info("[{}]方法消耗时间：{} ms",joinPoint.getSignature().getName(),endTime.get() - startTime.get());
       // logMessage.setRequestResult(JSON.toJSONString(result));
        logMessage.setEndTime(new Date());
        logMessage.setExpendTime(endTime.get() - startTime.get());
        //发送消息到队列
        rabbitTemplate.convertAndSend("log-queue",logMessage);
        //发送到消息队列中后，清理threadlocal的缓存数据
        LogSendToMq.remove();
    }
}
