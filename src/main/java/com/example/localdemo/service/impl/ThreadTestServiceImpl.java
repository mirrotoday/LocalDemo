package com.example.localdemo.service.impl;

import com.example.localdemo.config.ThreadPoolConfig;
import com.example.localdemo.service.ThreadTestService;
import com.example.localdemo.utils.DateUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;

/**
 * @author xieteng
 * @date 2023/7/21 ❤11:46
 * @description TODO
 */
@Slf4j
@Service
public class ThreadTestServiceImpl implements ThreadTestService {
    @Resource
    private ThreadPoolConfig threadPoolConfig;
    @SneakyThrows
    @Override
    public Object execOne() {
        List<String> execData = new ArrayList<>();
        Future<List<String>> smsThread = threadPoolConfig.smsSendTask().submit(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("调用sms线程:"+DateUtils.getNow());
                execData.add("调用sms线程:"+DateUtils.getNow());
            }
            return execData;
        });
        return smsThread.get();
    }

    @Override
    public void execList() {
        String[] phone = {"15520884591","15520884591","15520884591","15520884591","15520884591","15520884591","15520884591","15520884591","15520884591","15520884591",
                          "15520884591","15520884591","15520884591","15520884591","15520884591","15520884591","15520884591","15520884591","15520884591","15520884591"};
        for (String phoneNumber : phone) {
            Runnable  smsService = new smsService(phoneNumber);
            threadPoolConfig.smsSendTask().execute(smsService);
        }
    }

    @Override
    public void otherThread() {
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        map.put("key",Thread.activeCount());

        ConcurrentLinkedQueue<Object> cc = new ConcurrentLinkedQueue<>();
    }

    class smsService implements Runnable{
        private final String phoneNumber;
        public smsService(String phone){
            this.phoneNumber =  phone;
        }
        @Override
        public void run() {
            log.info("核心线程数：{},线程名称：{}，调用时间:{},电话号码：{}，消息发送成功！",
                    Runtime.getRuntime().availableProcessors(),Thread.currentThread().getName(),DateUtils.getNow(),phoneNumber);
        }
    }

}
