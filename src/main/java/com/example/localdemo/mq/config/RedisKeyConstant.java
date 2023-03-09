package com.example.localdemo.mq.config;

import lombok.Getter;

/**
 * @author xieteng
 * @date 2023/3/7 10:06
 * @description Redis key枚举，存储短信/邮箱的验证码
 */
@Getter
public enum RedisKeyConstant {

    verify_code("verify_code:", "验证码");

    private String key;
    private String desc;

    RedisKeyConstant(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }
}
