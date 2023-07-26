package com.example.localdemo.thread;

import org.springframework.scheduling.annotation.Async;

import javax.annotation.Resource;

/**
 * @author xieteng
 * @date 2023/7/21 ❤10:06
 * @description TODO
 */
public class ExThread {

    @Async("executor")
    public void main(String[] args) {
        for (int i = 0; i < 10; i++) {

        }

    }
}
