package com.example.localdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.localdemo.entity.QuartzJob;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xieteng
 * @date 2023/3/9 21:24
 * @description TODO
 */
@Mapper
public interface QuartzJobMapper extends BaseMapper<QuartzJob> {
}
