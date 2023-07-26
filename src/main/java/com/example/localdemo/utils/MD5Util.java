package com.example.localdemo.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author xieteng
 * @date 2023/7/17 23:18
 * @description TODO
 */
public class MD5Util {
    private final static String key = "Siyu20230717";
    /**

     * MD5方法

     *

     * @param text 明文


     * @return 密文

     * @throws Exception

     */

    public static String md5(String text)  {

        //加密后的字符串

        String encodeStr= DigestUtils.md5Hex(text + key);

        System.out.println("MD5加密后的字符串为:encodeStr="+encodeStr);

        return encodeStr;

    }

    /**

     * MD5验证方法

     *

     * @param text 明文


     * @param md5 密文

     * @return true/false

     * @throws Exception

     */
    public static boolean verify(String text, String md5){

        //根据传入的密钥进行验证

        String md5Text = md5(text);

        if(md5Text.equalsIgnoreCase(md5))

        {

            System.out.println("MD5验证通过");

            return true;

        }

        return false;

    }
    public static boolean verifyMd5(String text, String md5){

        //根据传入的密钥进行验证

        String md5Text = md5(text);

        if(md5Text.equalsIgnoreCase(md5))

        {

            System.out.println("MD5验证通过");

            return true;

        }

        return false;

    }
}
