package com.example.localdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.localdemo.entity.LogMessage;
import com.example.localdemo.mapper.LogMessageMapper;
import com.example.localdemo.result.ApiResult;
import com.example.localdemo.service.LogMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xieteng
 * @date 2023/7/15 13:19
 * @description TODO
 */
@Service
public class LogMessageServiceImpl implements LogMessageService {
    @Resource
    LogMessageMapper logMessageMapper;
    @Override
    public ApiResult<?> getLogMessageList() {
        QueryWrapper<LogMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(LogMessage.class,f -> !f.getColumn().equals("requestresult"));
        queryWrapper.orderByDesc("expendtime");
        List<LogMessage> logMessages = logMessageMapper.selectList(queryWrapper);
        return new ApiResult<>().success(logMessages);
    }
}
