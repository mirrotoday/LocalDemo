package com.example.localdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.localdemo.entity.QuartzJob;
import com.example.localdemo.mapper.QuartzJobMapper;
import com.example.localdemo.result.ApiResult;
import com.example.localdemo.service.IQuartzJobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author xieteng
 * @date 2023/3/9 21:22
 * @description TODO ServiceImpl中要依赖BassMapper的子类
 * 业务的具体实现，调用Mapper中的方法操作数据库
 */
@Slf4j
@Service
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobMapper, QuartzJob> implements IQuartzJobService {
    @Resource
    private QuartzJobMapper quartzJobMapper;
    @Resource
    private Scheduler scheduler;

    /**
     * 添加后台事务并启动
     * @param quartzJob
     * @return
     */
    //抛出异常后进行数据库回滚
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResult<?> saveAndScheduleJob(QuartzJob quartzJob) throws Exception {
        boolean saveStatus = this.save(quartzJob);
        if(saveStatus) {
            String jobClassName = null;
            try {
                String jobId = quartzJob.getId();
                jobClassName = quartzJob.getJobClassName().trim();
                // 启动调度器
                scheduler.start();
                // 构建job信息
                JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass())
                        .withIdentity(jobId).usingJobData("parameter", quartzJob.getParameter()).build();
                // Cron表达式调度构建器(即任务执行的时间)
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression().trim());
                // 按新的cronExpression表达式构建一个新的trigger
                CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobId).withSchedule(scheduleBuilder).build();

                scheduler.scheduleJob(jobDetail, trigger);
            } catch (SchedulerException e) {
                throw new SchedulerException(e);
            } catch (RuntimeException e) {
                throw new RuntimeException(e.getMessage(), e);
            } catch (Exception e) {
                throw new Exception("后台找不到该类名：" + jobClassName, e);
            }
        }
        return null;
    }
    private static Job getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (Job) class1.newInstance();
    }
}
