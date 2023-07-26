package com.example.localdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.localdemo.entity.User;
import com.example.localdemo.request.LoginArgs;
import com.example.localdemo.request.RegisterUserArgs;
import com.example.localdemo.request.ResetUserArgs;
import com.example.localdemo.result.ApiResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xieteng
 * @date 2023/7/17 22:29
 * @description TODO
 */
public interface UserService extends IService<User> {

     User getPass(String md5Pass);

     boolean reSetPass(ResetUserArgs resetUserArgs);

     boolean register(RegisterUserArgs registerUserArgs);

     ApiResult<?> login(LoginArgs loginArgs, HttpServletRequest res);
}
