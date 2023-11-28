package com.example.localdemo.service.impl;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.localdemo.entity.ClassManage;
import com.example.localdemo.result.ApiResult;
import com.example.localdemo.service.ClassManageService;
import com.example.localdemo.mapper.ClassManageMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

/**
* @author siyu
* @description 针对表【class_manage】的数据库操作Service实现
* @createDate 2023-08-15 09:56:56
*/
@Service
public class ClassManageServiceImpl extends ServiceImpl<ClassManageMapper, ClassManage>
    implements ClassManageService{
    
    /**
     * 班级信息添加
     * @param classManage
     * @return
     */
    @SneakyThrows
    @Override
    public ApiResult<?> addClass(ClassManage classManage) {
        String classSimple = PinyinUtil.getPinyin(classManage.getClassname());   // zhong wen
        classManage.setClasssimple(classSimple.toUpperCase());
        // 判断班级编码是否存在
        QueryWrapper<ClassManage> filter = new QueryWrapper<>();
        filter.eq("classcode",classManage.getClasscode());
        ClassManage isExit = getOne(filter,false);

        if(isExit != null){
            return new ApiResult<>().error("班级编码信息已存在");
        }

        boolean isSave = save(classManage);
        if(isSave){
            return new ApiResult<>().success("班级信息添加成功");
        }else{
            return new ApiResult<>().error("班级信息添加失败");
        }
    }

    /**
     * 班级信息修改
     * @param classManage
     * @return
     */
    @Override
    public ApiResult<?> editClass(ClassManage classManage) {
        boolean isUpdate = updateById(classManage);
        if(isUpdate){
            return new ApiResult<>().success("班级信息修改成功");
        }else{
            return new ApiResult<>().error("班级信息修改失败");
        }
    }

    @Override
    public ApiResult<?> deleteClass(Long classId) {
        boolean isRemove = removeById(classId);
        if(isRemove){
            return new ApiResult<>().success("班级删除成功");
        }else{
            return new ApiResult<>().error("班级删除失败");
        }
    }
}




