package com.example.localdemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.localdemo.annotation.TakeTime;
import com.example.localdemo.mq.config.RedisKeyConstant;
import com.example.localdemo.request.SmsCodeArgs;
import com.example.localdemo.request.SmsCodeCqArgs;
import com.example.localdemo.result.ApiResult;
import com.example.localdemo.utils.CodeUtils;
import com.example.localdemo.utils.SnowFlakeId;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xieteng
 * @date 2023/4/10 17:25
 * @description TODO
 */
@Slf4j
//@Api(tags = "短信集成")
@RestController
@TakeTime
@Validated
@RequestMapping("/sys")
public class SendSmsCodeController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

//    @ApiOperation("发送短信并存储到Redis")
    @GetMapping("/sendCode")
    public ApiResult<T> sendTelCode(@Pattern(regexp = "1[3456789][0-9]{9}",message = "手机号不符合格式")  @NotBlank(message = "手机号不能为空！") String mobile) throws IOException {
        SmsCodeArgs smsCode = new SmsCodeArgs();
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(smsCode.getUrl());
        method.setParameter("mobiles",mobile);
        method.setParameter("ct",smsCode.getCode());
        method.setParameter("sys",smsCode.getSys());
        method.setParameter("ts",smsCode.getTime());
        String source = smsCode.getCode() + smsCode.getSys() + mobile + smsCode.getTime() + smsCode.getToken();
        String md5String = CodeUtils.getMD5String(source);
        method.setParameter("key",md5String);
        int statusCode = client.executeMethod(method);
        if (200 == statusCode){
            byte[] responseBody = method.getResponseBody();
            String str = new String(responseBody, StandardCharsets.UTF_8);
            JSONObject jsonObject = JSON.parseObject(str);
            if("fail".equals(jsonObject.get("result"))) {
                log.info("请求结果为：" + str);
                return new ApiResult<T>().error("发送失败");
            }else {
                log.info("请求结果为：" + str);
                String redisKey = RedisKeyConstant.verify_code.getKey() + mobile;
                stringRedisTemplate.opsForValue().set(redisKey, smsCode.getCode(), Duration.ofMinutes(5));
               // String mailCode = stringRedisTemplate.opsForValue().get(redisKey);
                log.info("验证码为：" + smsCode.getCode());
                return new ApiResult<T>().success(str);
            }
        }
        return new ApiResult<T>().error("发送失败,返回的结果码为："+statusCode);
    }
    @SneakyThrows
    @GetMapping("/sendMessage")
    public ApiResult<?>  sendMessage(@Pattern(regexp = "1[3456789][0-9]{9}",message = "手机号不符合格式")  @NotBlank(message = "手机号不能为空！") String mobile){
        SmsCodeCqArgs smsCodeCq = new SmsCodeCqArgs();
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(smsCodeCq.getUrl());
        method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        method.setParameter("client_id", smsCodeCq.getClientId());
        method.setParameter("client_secret", smsCodeCq.getClientSecret());
        method.setParameter("phone", mobile);
        method.setParameter("smscode", smsCodeCq.getSmsCode());
        method.setParameter("content", "你的验证码为:"+ smsCodeCq.getCode());
        method.setParameter("country_code", smsCodeCq.getCountryCode());
        method.setParameter("signature", smsCodeCq.getSignature());

        int statusCode = client.executeMethod(method);
        if (200 == statusCode){
            byte[] responseBody = method.getResponseBody();
            String str = new String(responseBody, StandardCharsets.UTF_8);
            JSONObject jsonObject = JSON.parseObject(str);
            log.info("请求结果为{}：" , str);
            log.info("请求参数为{}：",Arrays.toString(method.getParameters()));
            if(0==jsonObject.getInteger("code")) {
                log.info("请求结果为：" + str);
                return new ApiResult<T>().error("发送失败");
            }else {
                log.info("请求结果为：" + str);
                String redisKey = RedisKeyConstant.verify_code.getKey() + mobile;
                stringRedisTemplate.opsForValue().set(redisKey, smsCodeCq.getCode(), Duration.ofMinutes(5));
                //String smsCode = stringRedisTemplate.opsForValue().get(redisKey);
                log.info("验证码为：" + smsCodeCq.getCode());
                return new ApiResult<T>().success(str);
            }
        }
        return new ApiResult<>().success("短信服务异常,返回的结果码为："+statusCode);
    }
}
