package com.example.localdemo.autoscheduled;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
@Slf4j
@Component
public class Scheduling {
    /**
     * 开启一个自动执行的后台事务
     * 如果数据库里面的开始时间小于等于今天，且事务没有启动。那么根据编码获取到类路径以及执行周期
     */
    @Scheduled(cron = "0/1 * * * * ?")
    public void auto(){
        //log.info("开始执行："+ DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
    }

    @Scheduled(cron = "0/1 * * * * ?")
    public void begin(){
       // log.info("执行结束："+ DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
    }
}
