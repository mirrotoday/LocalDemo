package com.example.localdemo.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author xieteng
 * @date 2023/7/21 ❤10:29
 * @description TODO
 */
public class FutThread {
    public static void main(String[] args) {
        Task task = new Task();
        java.util.concurrent.FutureTask<Integer> futureTask = new java.util.concurrent.FutureTask<>(task);

        new Thread(futureTask).start();

        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


        // 创建具有返回值的线程
        FutureTask<String> futureTask1 = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("t1线程进行运行处理了");
                return "哈哈哈，我有返回值了";
            }
        });
        // 线程启动的唯一方法是通过 Thread 类的 start()
        Thread t1 = new Thread(futureTask1);
        t1.start();
        try{
            // 获取返回值
            String s = futureTask1.get();
            System.out.println("主线程获取到的返回值："+s);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
    static class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("子线程正在计算");
            Thread.sleep(3000);
            int sum = 0;
            for (int i = 0; i < 100; i++) {
                sum+=i;
            }
            return sum;
        }
    }

}
