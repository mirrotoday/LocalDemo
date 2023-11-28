package com.example.localdemo.controller;

import com.example.localdemo.entity.ClassManage;
import com.example.localdemo.result.ApiResult;
import com.example.localdemo.service.ClassManageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xieteng
 * @date 2023/8/15 ❤ 9:34
 * @description TODO
 */
@RestController
@RequestMapping("/api/classManage")
public class ClassManageController {
    @Resource
    private ClassManageService classManageService;

    //添加班级
    @PostMapping("/addClass")
    public ApiResult<?> addClass(@RequestBody @Validated ClassManage classManage){
        return classManageService.addClass(classManage);
    }
    //编辑班级信息
    @PostMapping("/editClass")
    public ApiResult<?> editClass(@RequestBody @Validated ClassManage classManage){

        return classManageService.editClass(classManage);
    }
    //删除班级信息
    @DeleteMapping("/deleteClass/{classId}")
    public ApiResult<?> deleteClass(@PathVariable("classId")  Long classId){

        return classManageService.deleteClass(classId);
    }
}
