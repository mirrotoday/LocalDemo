package com.example.localdemo.utils;

import cn.hutool.core.util.RandomUtil;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @author xieteng
 * @date 2023/3/7 10:56
 * @description TODO
 */
@Slf4j
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

    public static String getMD5String(String source) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(source.getBytes("utf-8"));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.info(" ################ UnsupportedEncodingException ###  " + e.getMessage());
        }
        if (null == md5) {
            return null;
        }
        byte[] byteArray = md5.digest();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; ++i) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                buffer.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else {
                buffer.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return buffer.toString();
    }
    public static String getIpAddr(HttpServletRequest request)
    {
        if (request == null)
        {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

}
