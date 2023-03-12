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
     * @return ApiResult
     */
    //抛出异常后进行数据库回滚
    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class,SchedulerException.class})
    public ApiResult<?> saveAndScheduleJob(QuartzJob quartzJob){
        boolean saveStatus = this.save(quartzJob);
        log.info("新增的后台事务存储状态："+saveStatus);
        if(saveStatus && quartzJob.getStatus() == 0) {
            executeJob(quartzJob.getId(),quartzJob.getJobClassName(),quartzJob.getCronExpression(),quartzJob.getParameter());
        }
        return new ApiResult<>().result(quartzJob);
    }

    /**
     * 停止后台事务
     * @param quartzJob
     * @return ApiResult
     */
    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class,SchedulerException.class})
    public ApiResult<?> stopJob(QuartzJob quartzJob) {
        stopJob(quartzJob.getId());
        quartzJob.setStatus(-1);
        this.updateById(quartzJob);
        return new ApiResult<>().success("后台事务："+quartzJob.getJobClassName()+"停止成功！");
    }

    /**
     * 手动启动后台事务
     * 更新事务状态
     * @param quartzJob
     * @return ApiResult
     */
    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class,SchedulerException.class})
    public ApiResult<?> execute(QuartzJob quartzJob) {
        String id = quartzJob.getId();
        String classNasme = quartzJob.getJobClassName();
        String cronExpression = quartzJob.getCronExpression();
        String parameter = quartzJob.getParameter();
        executeJob(id,classNasme,cronExpression,parameter);
        quartzJob.setStatus(0);
        this.updateById(quartzJob);
        return new ApiResult<>().success("后台事务："+classNasme+"启动成功！");
    }

    @Override
    @Transactional(rollbackFor={RuntimeException.class, Exception.class,SchedulerException.class})
    public ApiResult<?> edit(QuartzJob quartzJob) throws SchedulerException{
        if(quartzJob.getStatus() == 0){
            String id = quartzJob.getId();
            String classNasme = quartzJob.getJobClassName();
            String cronExpression = quartzJob.getCronExpression();
            String parameter = quartzJob.getParameter();
            stopJob(id);
            executeJob(id,classNasme,cronExpression,parameter);
        }
        this.updateById(quartzJob);
        return new ApiResult<>().success("修改成功");
    }

    private static Job getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (Job) class1.newInstance();
    }

    /**
     * 执行事务公共方法
     * @param id
     * @param classNasme
     * @param cronExpression
     * @param paramter
     * @return ApiResult
     */
    private ApiResult<?> executeJob(String id,String classNasme,String cronExpression,String paramter){
        try {
            // 启动调度器
            scheduler.start();
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(getClass(classNasme.trim()).getClass()).withIdentity(id).usingJobData("parameter", paramter).build();
            // 表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            // 按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(id).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            return new ApiResult<>().error(e.getMessage());
        } catch (RuntimeException e) {
            return new ApiResult<>().error(e.getMessage());
        } catch (Exception e) {
            return new ApiResult<>().error("后台找不到该类名：" + classNasme);
        }
        return null;
    }

    /**
     * 停止事务公共方法
     * @param id
     */
    private void stopJob(String id){
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(id));
            scheduler.unscheduleJob(TriggerKey.triggerKey(id));
            scheduler.deleteJob(JobKey.jobKey(id));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
