package com.example.localdemo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author xieteng
 * @date 2023/7/17 22:01
 * @description TODO
 */
@Data
public class LoginArgs {
    /**
     * 用户名
     */
    @Pattern(regexp = "1[3456789][0-9]{9}",message = "用户名为手机号且不符合格式")
    private String userName;
    /**
     * 密码
     */
    @NotNull
    private String passWord;
    /**
     * 验证码
     */
    @NotNull
    private String smsCode;

}
