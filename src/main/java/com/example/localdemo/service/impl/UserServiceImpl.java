package com.example.localdemo.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.localdemo.authentication.UtilJwt;
import com.example.localdemo.entity.User;
import com.example.localdemo.mapper.UserMapper;
import com.example.localdemo.mq.config.RedisKeyConstant;
import com.example.localdemo.request.LoginArgs;
import com.example.localdemo.request.RegisterUserArgs;
import com.example.localdemo.request.ResetUserArgs;
import com.example.localdemo.result.ApiResult;
import com.example.localdemo.service.UserService;
import com.example.localdemo.utils.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author xieteng
 * @date 2023/7/17 23:40
 * @description TODO
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserMapper userMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    UserService userService;

    @Value("${token.appkey}")
    private String appKey;

    @Value("${token.appsecret}")
    private String appsecret;
    @Override
    public User getPass(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public boolean reSetPass(ResetUserArgs resetUserArgs) {
        String newPass = MD5Util.md5(resetUserArgs.getNewPassWord());
        return userMapper.reSetUserPass(resetUserArgs.getUserName(),newPass);
    }

    @Override
    public boolean register(RegisterUserArgs registerUserArgs) {
        String redisKey = RedisKeyConstant.verify_code.getKey() + registerUserArgs.getUsername();
        User isExit = getPass(registerUserArgs.getUsername());
        if(isExit != null){
            throw new RuntimeException("用户已存在");
        }
        String codeValue = stringRedisTemplate.opsForValue().get(redisKey);
        if(StringUtils.isEmpty(codeValue) || !registerUserArgs.getSmsCode().equals(codeValue)){
            throw new RuntimeException("验证码错误");
        }
        String pass = MD5Util.md5(registerUserArgs.getPassword());
        User createUser = new User();
            createUser.setUsername(registerUserArgs.getUsername());
            createUser.setPassword(pass);
            createUser.setEmail(registerUserArgs.getEmail());
            createUser.setAddress(registerUserArgs.getAddress());
            createUser.setIdCard(registerUserArgs.getIdCard());
            createUser.setPhone(Long.parseLong(createUser.getUsername()));

        return save(createUser);
    }

    @Override
    public ApiResult<?> login(LoginArgs loginArgs, HttpServletRequest res) {
        String redisKey = RedisKeyConstant.verify_code.getKey()+ loginArgs.getUserName();//
        String codeValue = Objects.requireNonNull(stringRedisTemplate.opsForValue().get(redisKey)).toUpperCase();
        if(StringUtils.isEmpty(codeValue) || !loginArgs.getSmsCode().toUpperCase().equals(codeValue)){
            throw new RuntimeException("验证码错误,请重新获取");
        }

        User userInfo = userService.getPass(loginArgs.getUserName());
        if(!MD5Util.verify(loginArgs.getPassWord(),userInfo.getPassword())){
            throw new RuntimeException("用户不存在或密码错误，请重试");
        }
        //生成用户token，使用hutool工具的jwt
        Map<String,String> payload = new HashMap<>();
        payload.put("uuid", String.valueOf(userInfo.getId()));//可以设置成数据库员工的唯一主键ID
        payload.put("ts", DateUtil.current()+"");
        String createToken = UtilJwt.createToken(payload, appKey,appsecret);
        //String tokenId = TokenConstruct.tokenId(String.valueOf(userInfo.getId()));
        stringRedisTemplate.opsForValue().set("Token:"+createToken, JSON.toJSONString(userInfo), Duration.ofHours(3));
        return new ApiResult<>().success(createToken);
        }


}
