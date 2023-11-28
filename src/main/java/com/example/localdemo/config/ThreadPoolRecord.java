package com.example.localdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author xieteng
 * @date 2023/11/16 ❤ 15:14
 * @description TODO
 */
@Slf4j
public class ThreadPoolRecord extends ThreadPoolExecutor {
    //线程执行时间
    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();
    //线程任务数
    private static final AtomicLong taskCount = new AtomicLong();
    //线程运行总时间
    private static final AtomicLong totalTime = new AtomicLong();

    public ThreadPoolRecord(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }


    /**
     * 任务执行前调用
     * @param t the thread that will run task {@code r}
     * @param r the task that will be executed
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        log.info("线程：{}开始执行任务", t.getName());
        startTime.set(System.currentTimeMillis());
    }

    /**
     * 任务执行后调用
     * @param r the runnable that has completed
     * @param t the exception that caused termination, or null if
     * execution completed normally
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try{
            long now = System.currentTimeMillis();
            long spendTime = now - startTime.get();
            //增加一次调用次数
            taskCount.incrementAndGet();
            //当前线程运行总时间
            totalTime.set(spendTime);
        }finally {
            super.afterExecute(r, t);
        }

    }

    /**
     * 线程关闭
     */
    @Override
    protected void terminated() {
        try{
            log.info("线程池关闭，总任务数：{} 个，总耗时：{} ms，平均耗时：{}ms", taskCount.get(), totalTime.get(), totalTime.get() / taskCount.get());
        }finally {
            super.terminated();
        }
    }
}
