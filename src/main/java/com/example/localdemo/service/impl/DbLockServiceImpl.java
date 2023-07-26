package com.example.localdemo.service.impl;

import com.example.localdemo.entity.Message;
import com.example.localdemo.mapper.DbLockMapper;
import com.example.localdemo.result.ApiResult;
import com.example.localdemo.service.DbLockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author xieteng
 * @date 2023/3/26 17:54
 * @description TODO
 */
@Service
@Slf4j
public class DbLockServiceImpl implements DbLockService {
    @Resource
    DbLockMapper dbLockMapper;

    @Transactional() //设置只读。只能查询
    @Override
    public ApiResult<?> getOneRowData(String id) throws InterruptedException {
        Message message = dbLockMapper.selectRowById(id);
        log.info("getOneRowData开始等待30秒");
        Thread.sleep(30000);
        log.info("getOneRowData结束等待30秒");
        return new ApiResult<>().result(message);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ApiResult<?> getOneTableData(String code) throws InterruptedException {
        Message message = dbLockMapper.selectByNumber(code);
        log.info("getOneTableData开始等待30秒");
        Thread.sleep(30000);
        log.info("getOneTableData结束等待30秒");
        return new ApiResult<>().result(message);
    }
    @Transactional
    @Override
    public ApiResult<?> updateOneRowData(String id) {
        try {
            int data = dbLockMapper.updateByid(id);
            if(data != 0 )
                return new ApiResult<>().success("更新成功！");
            else
                return new ApiResult<>().error("更新失败！");
        }catch(Exception e){
            log.info(e.getMessage());
            return new ApiResult<>().error(e.getMessage());
        }
    }

    @Override
    public ApiResult<?> updateOneTableData(String code) {
        try {
            int data = dbLockMapper.updateByNumber(code);
            if (data != 0)
                return new ApiResult<>().success("更新成功！");
            else
                return new ApiResult<>().error("更新失败！");
        }catch(Exception e){
            log.info(e.getMessage());
            return new ApiResult<>().error(e.getMessage());
        }
    }

    @Override
    public ApiResult<?> deleteOneRowData(String id) {
        try {
            int data = dbLockMapper.deleteById(id);
            if(data != 0 )
                return new ApiResult<>().success("删除成功！");
            else
                return new ApiResult<>().error("删除失败！");
        }catch(Exception e){
            log.info(e.getMessage());
            return new ApiResult<>().error(e.getMessage());
        }
    }
}
