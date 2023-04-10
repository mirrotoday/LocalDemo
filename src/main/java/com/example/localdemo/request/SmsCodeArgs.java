package com.example.localdemo.request;

import com.example.localdemo.utils.CodeUtils;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author xieteng
 * @date 2023/4/10 21:30
 * @description TODO
 */
@Data
public class SmsCodeArgs {
    String code;

    String url;

    String time;

    String token;

    String sys;

    @Pattern(regexp = "1[3456789][0-9]{9}",message = "手机号不符合格式")
    @NotBlank(message = "手机号不能为空！")
    String mobile;

    public SmsCodeArgs(){
        this.code = CodeUtils.getMailCode(4);
        this.url = "http://portal.app.mykingdee.com/api/sms/post.action";
        this.time = String.valueOf(System.currentTimeMillis());
        this.token = "k32!03@#3qp";
        this.sys = "shr";
    }
}
