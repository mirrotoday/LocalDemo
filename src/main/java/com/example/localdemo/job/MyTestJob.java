package com.example.localdemo.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author xieteng
 * @date 2023/3/9 21:53
 * @description TODO
 */
@Slf4j
public class MyTestJob implements Job {
    /**
     * 执行后台事务
     * @param jobExecutionContext
     * @throws JobExecutionException
     * 这个parameter是怎么被传递过来的？
     */
    private String parameter;

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("接收到的参数为："+parameter);
    }
}
