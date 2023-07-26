package com.example.localdemo.thread;

import java.util.Collections;
import java.util.concurrent.FutureTask;

/**
 * @author xieteng
 * @date 2023/7/21 ❤10:06
 * @description TODO
 */
public class RuThread implements Runnable{
    @Override
    public void run() {

    }

    public static void main(String[] args) {
        Runnable runnable = () ->{
            System.out.println("lambda的方式创建t1线程...");
        };
        // 线程启动的唯一方法是通过 Thread 类的 start()
        new Thread(runnable).start();
        System.out.println("main线程也在运行中......");

        Runnable cc = () -> {

        };
        new Thread(cc).start();

    }
}
