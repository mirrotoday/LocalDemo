package com.example.localdemo.mapper;

import com.example.localdemo.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author siyu
* @description 针对表【message】的数据库操作Mapper
* @createDate 2023-03-07 15:11:19
* @Entity com.example.localdemo.entity.Message
*/
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    List<Message> selectallData();
    List<Message> selectOneById(String id);
}




