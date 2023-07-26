package com.example.localdemo.authentication;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * @author xieteng
 * @date 2023/7/21 ❤15:04
 * @description TODO
 */
@Data
@ToString
public class JwtVerifyResult {
    /** 1 是否成功 **/
    private Boolean success;
    /** 2 返回消息 **/
    private String msg;
    /** 3 jwt负载 **/
    private Map<String,String> payload;

    public JwtVerifyResult() {
    }

    public JwtVerifyResult(Boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }


}
