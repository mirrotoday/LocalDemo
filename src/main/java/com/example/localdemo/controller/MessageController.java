package com.example.localdemo.controller;

import com.example.localdemo.entity.Message;
import com.example.localdemo.result.ApiResult;
import com.example.localdemo.service.MessageService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xieteng
 * @date 2023/3/7 15:15a
 * @description TODO
 */
@RestController
@RequestMapping("/sys")
public class MessageController {
    @Resource
    private MessageService messageService;

    @PostMapping("/sendBatchMail")
     public ApiResult<T> sendBatchMail(@RequestBody List<String> mail) {
         int messageCount = messageService.sendMessageMail(mail);
         return new ApiResult<T>().success("邮件发送成功，共计："+messageCount);
     }
    @GetMapping("/getMailLog")
    public ApiResult<List<Message>> getMailLog() {
        List<Message> data = messageService.selectAll();
        return new ApiResult<List<Message>>().result(data,data.size());
    }
    @GetMapping("/getMailLogById/{id}")
    public ApiResult<List<Message>> getMailLogById(@PathVariable("id") String id) {
        List<Message> data =  messageService.selectOneById(id);
        return new ApiResult<List<Message>>().result(data);
    }
}
