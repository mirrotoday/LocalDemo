package com.example.localdemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.localdemo.mq.config.RedisKeyConstant;
import com.example.localdemo.request.SmsCodeArgs;
import com.example.localdemo.result.ApiResult;
import com.example.localdemo.utils.CodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.time.Duration;

/**
 * @author xieteng
 * @date 2023/4/10 17:25
 * @description TODO
 */
@Slf4j
@Api(tags = "短信集成")
@RestController
@Validated
@RequestMapping("/sys")
public class SendSmsCodeController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation("发送短信并存储到Redis")
    @GetMapping("/sendCode")
    public ApiResult<T> sendTelCode(@Pattern(regexp = "1[3456789][0-9]{9}",message = "手机号不符合格式")  @NotBlank(message = "手机号不能为空！") String mobile) throws IOException {
        SmsCodeArgs smsCode = new SmsCodeArgs();
        smsCode.setMobile(mobile);
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(smsCode.getUrl());
        method.setParameter("mobiles",smsCode.getMobile());
        method.setParameter("ct",smsCode.getCode());
        method.setParameter("sys",smsCode.getSys());
        method.setParameter("ts",smsCode.getTime());
        String source = smsCode.getCode() + smsCode.getSys() + smsCode.getMobile() + smsCode.getTime() + smsCode.getToken();
        String md5String = CodeUtils.getMD5String(source);
        method.setParameter("key",md5String);
        int statusCode = client.executeMethod(method);
        if (200 == statusCode){
            byte[] responseBody = method.getResponseBody();
            String str = new String(responseBody,"utf-8");
            JSONObject jsonObject = JSON.parseObject(str);
            if("fail".equals(jsonObject.get("result"))) {
                log.info("请求结果为：" + str);
                return new ApiResult<T>().error("发送失败");
            }else {
                log.info("请求结果为：" + str);
                String redisKey = RedisKeyConstant.verify_code.getKey() + smsCode.getMobile();
                stringRedisTemplate.opsForValue().set(redisKey, smsCode.getCode(), Duration.ofMinutes(5));
                String mailCode = stringRedisTemplate.opsForValue().get(redisKey);
                log.info("验证码为：" + mailCode);
                return new ApiResult<T>().success(str);
            }
        }
        return new ApiResult<T>().error("发送失败,返回的结果码为："+statusCode);
    }


}
