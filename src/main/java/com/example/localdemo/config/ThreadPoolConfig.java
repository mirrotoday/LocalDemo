package com.example.localdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * @author xieteng
 * @date 2023/7/21 ❤10:43
 * @description TODO
 */
@Slf4j
@Configuration
@EnableAsync
public class ThreadPoolConfig implements AsyncConfigurer {
    /**
     * 发送短信
     * 频率高将队列容量设置100
     * @return
     */
    @Bean("smsSendTask")
    public ThreadPoolTaskExecutor smsSendTask() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // 1.设置核心线程数 8
        threadPoolTaskExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        // 2.设置最大线程数 16
        threadPoolTaskExecutor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2);

        // 4.设置线程活跃时间（秒）非核心线程数 还回去的时间  如果空闲超过10秒就被回收
        threadPoolTaskExecutor.setKeepAliveSeconds(1);
        // 3.设置队列容量,当没有足够的线程去处理任务时，可以将任务放进队列中，以队列先进先出的特性来执行工作任务
        threadPoolTaskExecutor.setQueueCapacity(100);

        //空闲时间(10秒)
        threadPoolTaskExecutor.setAwaitTerminationSeconds(10);
        // 设置默认线程名称(线程工厂)
        threadPoolTaskExecutor.setThreadNamePrefix("[SmsSendPool]-");
        //用来设置线程池关闭的时候等待所有任务都完成(可以设置时间)
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        //自定义任务装饰器
        threadPoolTaskExecutor.setTaskDecorator(new CustomTaskDecorator());
        /**
         * 拒绝策略:线程池都忙不过来的时候，可以适当选择拒绝
         * ThreadPoolExecutor.AbortPolicy();//默认，队列满了丢任务抛出异常
         * ThreadPoolExecutor.DiscardPolicy();//队列满了丢任务不异常
         * ThreadPoolExecutor.DiscardOldestPolicy();//将最早进入队列的任务删，之后再尝试加入队列
         * ThreadPoolExecutor.CallerRunsPolicy();//如果添加到线程池失败，那么主线程会自己去执行该任务
         */
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    // 可选的自定义任务装饰器
    private static class CustomTaskDecorator implements TaskDecorator {
        @Override
        public Runnable decorate(Runnable runnable) {
            // 可以在这里添加任务执行前后的逻辑
            return () -> {
                long startTime = System.currentTimeMillis();
                runnable.run();
                long executionTime = System.currentTimeMillis() - startTime;
                log.info("TaskName:{},Task execution time: {}ms",Thread.currentThread().getName(),executionTime);
            };
        }
    }
}
