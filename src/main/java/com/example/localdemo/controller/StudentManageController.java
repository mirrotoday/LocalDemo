package com.example.localdemo.controller;

import com.example.localdemo.entity.StudentManage;
import com.example.localdemo.result.ApiResult;
import com.example.localdemo.service.StudentManageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xieteng
 * @date 2023/8/15 ❤ 9:35
 * @description TODO
 */
@RestController
@RequestMapping("/api/student")
public class StudentManageController {

    @Resource
    private StudentManageService  studentManageService;
    //添加学生
    @PostMapping("/addStudent")
    public ApiResult<?> addStudent(@RequestBody @Validated StudentManage studentManage){
        return studentManageService.addStudent(studentManage);
    }
    //删除学生
    @DeleteMapping("/deleteStudent/{id}")
    public ApiResult<?> editStudent(@PathVariable("id") Long id){
        return studentManageService.deleteStudent(id);
    }


    //修改学生
    @PostMapping("/editStudent")
    public ApiResult<?> editStudent(@RequestBody @Validated StudentManage studentManage){
        return studentManageService.editStudent(studentManage);
    }


}
