/*
 *   编写日期：2022/6/8 11:52
 *   作者：张赵
 *   说明：
 */

package com.example.localdemo.authentication;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Base64;

public class Encrypt {
    /**
     * @param obj 要转成base64的原文
     * @return 返回base64编码
     */
    public static String toBase64(Object obj) {
        try {
            return Base64.getEncoder().encodeToString(obj.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @param obj 密文
     * @return 返回从base64解码后的原文
     */
    public static byte[] fromBase64(Object obj) {
        return Base64.getDecoder().decode(obj.toString());
    }

    private final static String salt = "zhzh523";

    /**
     * @param obj 明文
     * @return md5密文
     */
    @SuppressWarnings("unckecked")
    public static String toMD5(Object obj) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update((obj + salt).getBytes());
            byte b[] = digest.digest();

            int i;
            StringBuffer buffer = new StringBuffer();
            for (int k = 0; k < b.length; k++) {
                i = b[k];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buffer.append("0");
                buffer.append(Integer.toHexString(i));
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return new String();
        }
    }
}
