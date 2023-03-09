package com.example.localdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.localdemo.entity.QuartzJob;
import com.example.localdemo.result.ApiResult;
import org.quartz.SchedulerException;

/**
 * @author xieteng
 * @date 2023/3/9 21:02
 * @description TODO
 * IService是BaseMapper的扩展
 * Mapper(DAO)—>继承BaseMapper< T > —>Service(接口) —> 继承IService< T >—>ServiceImpl(实现类)—>继承ServiceImpl<M extends BaseMapper< T >, T> —>实现自己的Service(接口)
 * BaseMapper:提供基本的增删查改，分页，以及少量批量操作，满足基本的业务需求
 * IService：是BaseMapper的扩展，满足其基本业务操作的同时，提供批量操作如【批量插入】和【批量更新】
 */
public interface IQuartzJobService extends IService<QuartzJob> {
       //保存后台事务数据，并启动
       ApiResult<?> saveAndScheduleJob(QuartzJob quartzJob) throws Exception;
}
