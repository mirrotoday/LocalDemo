package com.example.localdemo.service;

import cn.hutool.http.server.HttpServerResponse;
import com.example.localdemo.entity.Person;
import com.example.localdemo.entity.Timequartz;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author siyu
* @description 针对表【timequartz】的数据库操作Service
* @createDate 2023-01-16 14:21:35
*/
public interface TimequartzService extends IService<Timequartz> {

    int insert(Timequartz timequartz);

    List<Timequartz> getList();

    int delete(List<String> number);

    int updateData(Timequartz timequartz);

    List<Person> getPersonList();

    void export(HttpServletResponse response);
}
