package com.example.localdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.localdemo.entity.Timequartz;
import com.example.localdemo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xieteng
 * @date 2023/7/17 23:42
 * @description TODO
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    boolean reSetUserPass(String userName, String newPass);
}
