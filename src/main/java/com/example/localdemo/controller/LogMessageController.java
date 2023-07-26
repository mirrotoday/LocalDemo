package com.example.localdemo.controller;

import com.example.localdemo.annotation.Log;
import com.example.localdemo.annotation.TakeTime;
import com.example.localdemo.entity.LogMessage;
import com.example.localdemo.result.ApiResult;
import com.example.localdemo.service.LogMessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xieteng
 * @date 2023/7/15 13:16
 * @description TODO
 */

@RestController
@RequestMapping("/api")
public class LogMessageController {
    @Resource
    LogMessageService logMessageService;
    @TakeTime
    @RequestMapping("/getlog")
    public ApiResult<?> getLogMessageList(){
        return logMessageService.getLogMessageList();
    }
}
