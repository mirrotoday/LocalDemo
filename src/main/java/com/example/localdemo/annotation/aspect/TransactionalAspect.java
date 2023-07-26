package com.example.localdemo.annotation.aspect;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author xieteng
 * @date 2023/7/15 1:03
 * @description TODO
 */
@Slf4j
//@Aspect
//@Component
public class TransactionalAspect {
    @Around(value = "com.example.localdemo.annotation.aspect.PointCutManager.implPointCut()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("===================[这是一个事务环绕通知]===================");
        Object result = null;
        try {
            log.info("事务环绕通知=====目标方法之前执行,，其的方法名为："+methodName+",参数为："+ Arrays.toString(args)+"");
            result = joinPoint.proceed();
            log.info("事务环绕通知=====目标方法返回后执行,返回值为："+ JSON.toJSONString(result));
        }catch(Throwable ex){
            ex.printStackTrace();
            log.error("事务环绕通知=====目标方法出现异常后执行，异常信息为："+ex.getMessage()+"");
        }finally {
            log.info("事务环绕通知=====目标方法执行完毕");
        }
        return result;
    }
}
