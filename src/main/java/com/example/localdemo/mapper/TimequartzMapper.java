package com.example.localdemo.mapper;

import com.example.localdemo.entity.Person;
import com.example.localdemo.entity.Timequartz;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Time;
import java.util.Collection;
import java.util.List;

/**
* @author siyu
* @description 针对表【timequartz】的数据库操作Mapper
* @createDate 2023-01-16 14:21:35
* @Entity com.example.localdemo.entity.Timequartz
*/
@Mapper
public interface TimequartzMapper extends BaseMapper<Timequartz> {
    @Override
    int insert(Timequartz entity);

    List<Timequartz> selectList();

    int deleteBatchIds(List<String> idList);

    String getId(String number);

    List<Person> getPersonList();
}




