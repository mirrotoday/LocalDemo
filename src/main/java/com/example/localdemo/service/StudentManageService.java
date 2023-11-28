package com.example.localdemo.service;

import com.example.localdemo.entity.StudentManage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.localdemo.result.ApiResult;

/**
* @author siyu
* @description 针对表【student_manage】的数据库操作Service
* @createDate 2023-08-15 09:58:22
*/
public interface StudentManageService extends IService<StudentManage> {

    ApiResult<?> addStudent(StudentManage studentManage);

    ApiResult<?> editStudent(StudentManage studentManage);

    ApiResult<?> deleteStudent(Long id);
}
