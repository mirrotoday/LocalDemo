package com.example.localdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.localdemo.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author xieteng
 * @date 2023/3/26 17:55
 * @description TODO
 */
@Mapper
public interface DbLockMapper extends BaseMapper<Message> {
    Message selectRowById(String id);
    Message selectByNumber(@Param("code") String code);
    Integer updateByNumber(@Param("code") String code);
    Integer updateByid(String id);
}
