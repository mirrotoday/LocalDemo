package com.example.localdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.localdemo.entity.LogMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xieteng
 * @date 2023/7/15 11:58
 * @description TODO
 */
@Mapper
public interface LogMessageMapper extends BaseMapper<LogMessage> {
}
