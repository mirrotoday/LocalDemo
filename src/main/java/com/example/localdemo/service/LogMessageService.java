package com.example.localdemo.service;

import com.example.localdemo.entity.LogMessage;
import com.example.localdemo.result.ApiResult;

import java.util.List;

/**
 * @author xieteng
 * @date 2023/7/15 13:18
 * @description TODO
 */
public interface LogMessageService {
    ApiResult<?> getLogMessageList();
}
