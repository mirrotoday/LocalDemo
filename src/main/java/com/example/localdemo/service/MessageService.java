package com.example.localdemo.service;

import com.example.localdemo.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author siyu
* @description 针对表【message】的数据库操作Service
* @createDate 2023-03-07 15:11:19
*/
public interface MessageService extends IService<Message> {
    int sendMessageMail(List<String> mail);
    List<Message> selectAll();

    List<Message> selectOneById(String id);
}
