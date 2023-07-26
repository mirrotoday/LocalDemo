package com.example.localdemo.authentication;

import com.example.localdemo.result.ApiResult;
import com.example.localdemo.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xieteng
 * @date 2023/7/17 21:18
 * @description TODO
 */
@Aspect
@Component
@Slf4j
public class TokenAspect {
    private static HttpServletRequest httpServletRequest;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    public void setRequest(HttpServletRequest httpServletRequest) {
        TokenAspect.httpServletRequest = httpServletRequest;
    }
    @Value("${token.appkey}")
    private String appKey;

    @Value("${token.appsecret}")
    private String appSecret;
    @Pointcut("execution(public * *(..)) && @annotation(com.example.localdemo.authentication.Token)")
    public void point() {
    }

    @Around("point()")
    public Object Interceptor(ProceedingJoinPoint joinPoint) {
        try {
            MethodSignature sign = (MethodSignature) joinPoint.getSignature();
            Method method = sign.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (null != annotation && annotation.validate()) {
                //接口要求必须校验token的
                ApiResult<?> apiResult = new ApiResult<>();
                String token = httpServletRequest.getHeader("token");
                if (StringUtils.isEmpty(token)) {
                    apiResult.message = "该接口需传递token 请在headers中传递!";
                    apiResult.timeStamp = DateUtils.getTick();
                    apiResult.code = 0;
                    return apiResult;
                } else {
                    //校验token是否有效
//                    if (!TokenConstruct.deToken(token) || !Boolean.TRUE.equals(stringRedisTemplate.hasKey("Token:" + token))) {
//                        apiResult.message = "token已失效，请重新登录";
//                        apiResult.timeStamp = DateUtils.getTick();
//                        apiResult.code = 0;
//                        return apiResult;
//                    }
                    Map<String,String> payload = new HashMap<>();
                    JwtVerifyResult jwtVerifyResult = UtilJwt.validateToken(token,appKey,appSecret);
                    if(jwtVerifyResult.getSuccess()){
                        payload = jwtVerifyResult.getPayload();
                        System.out.println("负载："+payload.toString());
                    }else{
                        System.out.println("验证失败："+jwtVerifyResult.toString());
                        apiResult.message = "验证失败，请重新登录";
                        apiResult.timeStamp = DateUtils.getTick();
                        apiResult.code = 0;
                        return apiResult;
                    }
                }
            }
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.info(e.getMessage());
            throw new RuntimeException(e.getMessage());

        }
        return result;
    }
}
