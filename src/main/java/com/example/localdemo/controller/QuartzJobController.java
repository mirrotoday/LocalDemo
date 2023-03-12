package com.example.localdemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.localdemo.entity.QuartzJob;
import com.example.localdemo.result.ApiResult;
import com.example.localdemo.service.IQuartzJobService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Result;
import org.quartz.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xieteng
 * @date 2023/3/9 20:17
 * @description TODO
 * 1.添加后台事务 2.修改后台事务 3.根据ID删除后他事务 4.分页查询所有后台事务 5.启动后台事务 6.停止后台事务
 */
@Api(tags = "后台事务")
@Slf4j
@RestController
@RequestMapping("/sys/quartzJob")
public class QuartzJobController {

    @Resource
    private IQuartzJobService QuartzJobService;
    /**
     * 根据ID获取事务信息
     * @param id
     * 返回整个对象
     */
    @Operation(summary ="后台事务-根据ID查询")
    @GetMapping("/queryById")
    public void queryById(@RequestParam(name = "id", required = true) String id){

    }

    /**
     * 分页查询列表数据
     * @param quartzJob
     * @param pageNo 页码
     * @param pageSize 每一页多少条数据
     * @param req
     * @return
     */
    @Operation(summary ="后台事务-分页查询")
    @GetMapping("/list")
    public ApiResult<?> queryPageList(QuartzJob quartzJob, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        LambdaQueryWrapper<QuartzJob> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(QuartzJob::getId);
        Page<QuartzJob> page = new Page<QuartzJob>(pageNo, pageSize);
        IPage<QuartzJob> pageList = QuartzJobService.page(page,queryWrapper);
        return new ApiResult<>().result(pageList);
    }
    /**
     * 新增后台事务s
     * @param quartzJob
     * @Validated 注解进行校验数据 https://blog.csdn.net/sj13074480550/article/details/103399503
     */
    @Operation(summary ="后台事务-新增")
    @PostMapping("/add")
    public ApiResult<?> addJob(@Validated @RequestBody QuartzJob quartzJob) {
        try {
            return  QuartzJobService.saveAndScheduleJob(quartzJob);
        }catch(Exception e){
            log.info("新增后台事务出错",e);
        }
        return new ApiResult<>().error("新增后台事务出错");
    }
    /**
     * 启动后台事务
     * @param quartzJob
     */
    @Operation(summary ="后台事务-执行")
    @PostMapping("/execute")
    public ApiResult<?> executeJob(@RequestBody QuartzJob quartzJob){
        return QuartzJobService.execute(quartzJob);
    }
    /**
     * 修改后台事务
     * @param quartzJob
     */
    @Operation(summary ="后台事务-编辑")
    @PostMapping("/edit")
    public ApiResult<?> editJob(@RequestBody QuartzJob quartzJob){
        try{
            return QuartzJobService.edit(quartzJob);
        } catch (SchedulerException e) {
            return new ApiResult<>().error(e.getMessage());
        }
    }

    /**
     * 根据ID对事务进行删除
     * @param id 必填
     * /sys/quartzJob/deleteJob?id="ID"
     */
    @Operation(summary ="后台事务-删除")
    @DeleteMapping("/delete")
    public void deleteJob(@RequestParam(name = "id", required = true) String id){

    }
    /**
     * 暂停后台事务
     * @param quartzJob
     */
    @Operation(summary ="后台事务-停止")
    @PostMapping("/pause")
    public ApiResult<?> pauseJob(@RequestBody QuartzJob quartzJob){
        return QuartzJobService.stopJob(quartzJob);
    }


}
