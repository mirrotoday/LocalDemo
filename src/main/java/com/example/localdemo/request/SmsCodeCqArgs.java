package com.example.localdemo.request;

import com.example.localdemo.utils.CodeUtils;
import lombok.Data;

/**
 * @author xieteng
 * @date 2023/11/15 ❤ 19:40
 * @description TODO
 */
@Data
public class SmsCodeCqArgs {
    private String clientId;
    private String clientSecret;
    private String url;
    private String smsCode;
    private String code;
    private String countryCode;
    private String signature;

    public SmsCodeCqArgs(){
        this.clientId = "201230";
        this.clientSecret = "87d7c0ca9ee7ea84b15e68f1fad10c47";
        this.url = "http://portal.app.mykingdee.com/api/sms/post.action";
        this.smsCode = "10725";
        this.code = CodeUtils.getMailCode(4);
        this.countryCode = "86";
        this.signature = "思宇定制";

    }
}
