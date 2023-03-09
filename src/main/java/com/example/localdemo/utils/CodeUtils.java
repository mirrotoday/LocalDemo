package com.example.localdemo.utils;

import cn.hutool.core.util.RandomUtil;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import java.util.Date;

/**
 * @author xieteng
 * @date 2023/3/7 10:56
 * @description TODO
 */
public class CodeUtils {
    private static final String BASECHECKCODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";

    /**
     * 验证码，自定义位数
     * @param length
     * @return
     */
    public static String getMailCode(int length) {
        String code = RandomUtil.randomString(BASECHECKCODES,length);
        return code.toLowerCase();
    }

    /**
     * NanoId+时间戳
     * @return ID
     */
    public static String getNanoId() {
        return NanoIdUtils.randomNanoId() + new Date().getTime();
    }
}
