package com.example.localdemo.controller;

import com.example.localdemo.service.ThreadTestService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * @author xieteng
 * @date 2023/7/21 ❤11:22
 * @description TODO
 */
@RestController
@RequestMapping("/thread")
public class ThreadTestController {
    @Resource
    ThreadTestService threadTestService;
    /**
     * 异步参数回调
     * @return
     */
    @GetMapping("/getOne")
    public String execThread(){
        Object res = threadTestService.execOne();
        return String.valueOf(res);
    }
    @GetMapping("/execList")
    public String execListThread(){
        threadTestService.execList();
        return "执行成功";
    }
}
