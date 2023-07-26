package com.example.localdemo.controller;

import com.alibaba.fastjson.JSON;
import com.example.localdemo.annotation.TakeTime;
import com.example.localdemo.authentication.Token;
import com.example.localdemo.authentication.TokenConstruct;
import com.example.localdemo.entity.User;
import com.example.localdemo.exception.GlobalExceptionHandler;
import com.example.localdemo.mq.config.RedisKeyConstant;
import com.example.localdemo.request.LoginArgs;
import com.example.localdemo.request.RegisterUserArgs;
import com.example.localdemo.request.ResetUserArgs;
import com.example.localdemo.result.ApiResult;
import com.example.localdemo.service.UserService;
import com.example.localdemo.utils.CaptchaCode;
import com.example.localdemo.utils.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author xieteng
 * @date 2023/7/17 21:41
 * @description TODO
 */
@Api(tags = "登录模块")
@RestController
@RequestMapping("/api/user")
public class LoginController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    UserService userService;

    @TakeTime
    @ApiOperation("登录")
    @PostMapping("/login")
    public ApiResult<?> validate(@RequestBody @Validated LoginArgs loginArgs,HttpServletRequest res) throws Exception{
        return userService.login(loginArgs,res);
    }
    @TakeTime
    @ApiOperation("注册")
    @PostMapping("/adduser")
    public ApiResult<?> addUser(@RequestBody @Validated RegisterUserArgs registerUserArgs) throws Exception{
        boolean isRegister = userService.register(registerUserArgs);
        if (!isRegister){
           throw new Exception("注册失败");
        }
        return new ApiResult<>().success("注册成功");
    }
    @Token
    @TakeTime
    @ApiOperation("密码重置")
    @PostMapping("/reset")
    public ApiResult<?> reSet(@RequestBody @Validated ResetUserArgs resetUserArgs) throws Exception{
        User userInfo = userService.getPass(resetUserArgs.getUserName());
        if(!MD5Util.verify(resetUserArgs.getOldPassWord(),userInfo.getPassword())){
            throw new Exception("原密码不正确，请重试");
        }
        boolean isSuccess = userService.reSetPass(resetUserArgs);
        if(isSuccess){
            return new ApiResult<>().success("密码重置成功");
        }
        return new ApiResult<>().error("密码重置失败");
    }
    @Token
    @PostMapping("/exit")
    @ApiOperation(value = "注销登录")
    public void exit(HttpServletRequest res) throws Exception{
        String token = "Token:" + res.getHeader("token");
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(token))) {
            stringRedisTemplate.expire(token,0, TimeUnit.SECONDS);
        }
    }
    @GetMapping("/getValidateCode/{userName}")
    @ApiOperation(value = "获取验证码")
    public void getValidateCode(@PathVariable("userName") String userName,HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store,no-cache");
        response.setContentType("image/jpeg");
        CaptchaCode.drawCode(response, stringRedisTemplate,userName);
    }
}
