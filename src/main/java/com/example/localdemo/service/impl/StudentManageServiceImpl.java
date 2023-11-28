package com.example.localdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.localdemo.entity.StudentManage;
import com.example.localdemo.result.ApiResult;
import com.example.localdemo.service.StudentManageService;
import com.example.localdemo.mapper.StudentManageMapper;
import org.springframework.stereotype.Service;

/**
* @author siyu
* @description 针对表【student_manage】的数据库操作Service实现
* @createDate 2023-08-15 09:58:22
*/
@Service
public class StudentManageServiceImpl extends ServiceImpl<StudentManageMapper, StudentManage>
    implements StudentManageService{

    @Override
    public ApiResult<?> addStudent(StudentManage studentManage) {
        //校验身份证和电话重复
        QueryWrapper<StudentManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("idcard",studentManage.getIdcard());
        queryWrapper.eq("phone",studentManage.getPhone());
        StudentManage check = getOne(queryWrapper,false);
        if(check!=null){
            return new ApiResult<>().error("身份证或者电话重复");
        }
        boolean save = save(studentManage);
        if(save){
            return new ApiResult<>().success("添加成功");
        }
       return new ApiResult<>().error("添加失败");
    }



    @Override
    public ApiResult<?> editStudent(StudentManage studentManage) {
        boolean isUpdate = updateById(studentManage);
        if (isUpdate){
            return new ApiResult<>().success("修改成功");
        }
       return new ApiResult<>().error("修改失败");
    }

    @Override
    public ApiResult<?> deleteStudent(Long id) {
        boolean isDelete = removeById(id);
        if (isDelete){
            return new ApiResult<>().success("删除成功");
        }
        return new ApiResult<>().error("删除失败");
    }

}




