package com.example.localdemo.authentication;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.RegisteredPayload;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xieteng
 * @date 2023/7/21 ❤15:01
 * @description TODO
 */
public class UtilJwt  {
    /**
     * jwt 签名秘钥，可以更换其他不常用的自定义签名器： JWTSignerUtil
     */
    static final JWTSigner jwtSigner = JWTSignerUtil.hs256("!34ADASfhsrujwstrfhwdg@#$$@#efh3".getBytes());
    /**
     * 自定义二重认证 token key
     */
    static final String AUTHTOKEN_KEY = "at";


    /**
     * 生成安全的 jwt token
     * 理论上:内部系统交互：每隔3个月，更新 appkey，appsecret
     * 外部系统交互：不用更新 appkey，appsecret，但必须要求其验证负载 ts 和 at（authToken）
     * <p>
     * 时间校验具有多种方法:生效时间、失效时间、签发时间
     * 此处只用失效时间校验：https://www.hutool.cn/docs/#/jwt/JWT验证-JWTValidator
     * <p>
     * 负载签名时间：iat（签发时间-强制）、exp（过期时间校验-强制）、at（内嵌再次token校验），已被占用
     * 可能使用的高级占用参数：详见 hutool 的RegisteredPayload 接口
     *
     * @param payload   jwt负载
     * @param appkey    签名key
     * @param appsecret 签名秘钥
     * @return
     */
    public static String createToken(Map<String, String> payload, String appkey, String appsecret) {

        Date dateNow = new Date();
        //1.构建自定义的签名摘要（安全2）
        System.out.println(dateNow.getTime());
        String authToken = SecureUtil.md5(SecureUtil.sha256(appkey + "_" + DateUtil.formatDateTime(dateNow) + "_" + appsecret));
        payload.put(AUTHTOKEN_KEY, authToken);

        //3.创建 jwt 签名，存放参数；2.签名时间 3。设置过期时间为当天结束（validateToken方法：容忍校验2分钟），4.hash256签名为（安全4）
        return JWT.create()
                .addPayloads(payload)
                .setIssuedAt(dateNow)
                .setExpiresAt(DateUtil.endOfDay(dateNow))
                .sign(jwtSigner);
    }

    /**
     * jwt 验证，1）hash256签名，2）失效时间，3）自定义二重安全校验
     *
     * @param token     生成的jwt token
     * @param appkey    签名key
     * @param appsecret 签名秘钥
     * @return map :当error = "ok"，会额外多出 payload
     */
    public static JwtVerifyResult validateToken(String token, String appkey, String appsecret) {

        try {
            JWT jwt = JWT.of(token);
            jwt.setSigner(jwtSigner);

            //1.验证hash256签名
            Boolean hs256Bool = jwt.verify();
            if (!hs256Bool) {
                return new JwtVerifyResult(false, "hash256签名校验失败");
            }

            //2.验证签名的时间，容忍度为2分钟
            Boolean validateBool = jwt.validate(7200L);
            if (!validateBool) {
                return new JwtVerifyResult(false, "token有限期已过2小时，请重新获取！");
            }

            //3.获取二重 authToken 认证校验
            JWTPayload jwtPayload = jwt.getPayload();
            Date subAtDate = jwtPayload.getClaimsJson().getDate(RegisteredPayload.ISSUED_AT);

            String atScource = String.valueOf(jwtPayload.getClaim(AUTHTOKEN_KEY));
            String authToken = SecureUtil.md5(SecureUtil.sha256(appkey + "_" +DateUtil.formatDateTime(subAtDate) + "_" + appsecret));

            if (StrUtil.isEmpty(atScource) || !atScource.equals(authToken)) {
                return new JwtVerifyResult(false, "二重身份认证校验失败");
            }

            //4.将负载参数转换成map返回
            JSONObject payloadJson = jwtPayload.getClaimsJson();
            payloadJson.remove(AUTHTOKEN_KEY);
            JwtVerifyResult jwtVerifyResult = new JwtVerifyResult(true, "签名有效");
            jwtVerifyResult.setPayload(JSONUtil.toBean(payloadJson, new TypeReference<Map<String, String>>() {
            }, true));
            return jwtVerifyResult;

        } catch (ValidateException e) {
            return new JwtVerifyResult(true, "无效的签名");
        }
    }
}
