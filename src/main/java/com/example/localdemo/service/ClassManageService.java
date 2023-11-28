package com.example.localdemo.service;

import com.example.localdemo.entity.ClassManage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.localdemo.result.ApiResult;

/**
* @author siyu
* @description 针对表【class_manage】的数据库操作Service
* @createDate 2023-08-15 09:56:56
*/
public interface ClassManageService extends IService<ClassManage> {

    ApiResult<?> addClass(ClassManage classManage);

    ApiResult<?> editClass(ClassManage classManage);

    ApiResult<?> deleteClass(Long classManage);
}
