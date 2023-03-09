package com.example.localdemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.localdemo.entity.QuartzJob;
import com.example.localdemo.result.ApiResult;
import org.apache.ibatis.annotations.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xieteng
 * @date 2023/3/9 20:17
 * @description TODO
 * 1.添加后台事务 2.修改后台事务 3.根据ID删除后他事务 4.分页查询所有后台事务 5.启动后台事务 6.停止后台事务
 */
@RestController
@RequestMapping("/sys/quartzJob")
public class QuartzJobController {
    /**
     * 根据ID获取事务信息
     * @param id
     * 返回整个对象
     */
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
    @GetMapping("/list")
    public ApiResult<?> queryPageList(QuartzJob quartzJob, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
//        QueryWrapper<QuartzJob> queryWrapper = QueryGenerator.initQueryWrapper(quartzJob, req.getParameterMap());
//        Page<QuartzJob> page = new Page<QuartzJob>(pageNo, pageSize);
//        IPage<QuartzJob> pageList = quartzJobService.page(page, queryWrapper);
        return new ApiResult<>().result(null);

    }
    /**
     * 新增后台事务
     * @param quartzJob
     * @Validated 注解进行校验数据 https://blog.csdn.net/sj13074480550/article/details/103399503
     */
    @PostMapping("/add")
    public void addJob(@Validated @RequestBody QuartzJob quartzJob){

    }

    /**
     * 修改后台事务
     * @param quartzJob
     */
    @PostMapping("/edit")
    public void editJob(@RequestBody QuartzJob quartzJob){

    }

    /**
     * 根据ID对事务进行删除
     * @param id 必填
     * /sys/quartzJob/deleteJob?id="ID"
     */
    @DeleteMapping("/delete")
    public void deleteJob(@RequestParam(name = "id", required = true) String id){

    }

    /**
     * 启动后台事务
     * @param id
     */
    @GetMapping("/execute")
    public void executeJob(@RequestParam(name = "id", required = true) String id){

    }

    /**
     * 暂停后台事务
     * @param id
     */
    @GetMapping("/pause")
    public void pauseJob(@RequestParam(name = "id", required = true) String id){

    }


}