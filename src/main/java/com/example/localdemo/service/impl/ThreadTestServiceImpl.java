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
    ThreadPoolConfig threadPoolConfig;
    @SneakyThrows
    @Override
    public Object execOne() {
        int a = 1/0;
        List<String> execData = new ArrayList<>();
        Future<List<String>> smsThread = (Future<List<String>>) threadPoolConfig.smsSendTask().submit(() -> {
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

    }
}
