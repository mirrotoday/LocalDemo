package com.example.localdemo.annotation.aspect;

import com.alibaba.fastjson2.JSON;
import com.example.localdemo.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.Arrays;

/**
 * @author xieteng
 * @date 2023/7/14 21:15
 * @description TODO
 * 1.设置切入点
 * 2.设置通知类型： 前置通知[@Before()]，返回通知[@AfterReturning]，后置通知[@After()]，异常通知[@AfterThrowing]，环绕通知[@Around()]
 * 3.调用非同一个切面的切入点表达式  @After("com.example.localdemo.annotation.aspect.LogAspect.pointCut()")
 */
@Slf4j
//@Order(1) //数字越小优先级别越高，数字越大优先级别越低
@Aspect
@Component
public class LogAspect {


    @Pointcut("@annotation(com.example.localdemo.annotation.Log)")
    public void logPointCut(){

    }
    /**
     * 切入点表达式
     * 1.execution() 固定格式
     * 2.public/private 访问修饰符(用*表示权限修饰符和返回值任意)
     * 3.int 返回类型             (用*表示权限修饰符和返回值任意)
     * 4.增强方法所在类的全路径 com.example.localdemo
     * 5.方法名                   (用*表示所有方法，比如*Service对所有Service类起作用，*Impl对所有实现类起作用，*Controller 对所有前台控制器起作用，等,)
     * 6.参数列表
     */
//     @Before("execution(* com.example.localdemo.*(..) )")
//     @Before("pointCut()")
     public void beforMethod(JoinPoint joinPoint){
        log.info("===================[这是一个前置通知]===================");
         String methodName = joinPoint.getSignature().getName();
         Object[] args = joinPoint.getArgs();
         log.info("===================[前置通知的方法名为："+methodName+",参数为："+ Arrays.toString(args)+"]===================");
     }

    /**
     * 返回通知
     * @param joinPoint
     * @param result 获取到增强方法的返回值
     */
//    @AfterReturning(value = "execution(* com.example.localdemo.*(..) )",returning = "result")
//    @AfterReturning(value = "pointCut()",returning = "result")
    public void afterReturningMethod(JoinPoint joinPoint,Object result) {
        log.info("===================[这是一个返回通知]===================");
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("===================[返回通知的方法名为："+methodName+",参数为："+ Arrays.toString(args)+"]===================");
        log.info("===================[返回通知的返回值为："+result+"]===================");
    }

    /**
     * 后置通知
     * @param joinPoint
     */
//     @After("execution(* com.example.localdemo.*(..) )")
//     @After("pointCut()")
     public void AfterMethod(JoinPoint joinPoint) {
         log.info("===================[这是一个后置通知]===================");
         String methodName = joinPoint.getSignature().getName();
         Object[] args = joinPoint.getArgs();
         log.info("===================[后置通知的方法名为："+methodName+",参数为："+ Arrays.toString(args)+"]===================");
     }

    /**
     * 异常通知
     * @param joinPoint
     * @param ex 获取到的异常
     */
//    @AfterThrowing(value = "execution(* com.example.localdemo.*(..) )",throwing = "ex")
//    @AfterThrowing(value = "pointCut()",throwing = "ex")
    public void afterThrowingMethod(JoinPoint joinPoint,Throwable ex) {
        log.info("===================[这是一个异常通知]===================");
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("===================[异常通知的方法名为："+methodName+",参数为："+ Arrays.toString(args)+",异常信息为："+ex.getMessage()+"]===================");
    }

    /**
     * 环绕通知
     * @param joinPoint
     */
//    @Around(value = "execution(* com.example.localdemo.*(..) )")
//    @Around(value = "com.example.localdemo.annotation.aspect.PointCutManager.controllerPointCut()")
    @Around(value = "logPointCut()")
    public ApiResult<?> aroundMethod(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("===================[这是一个环绕通知]===================");
        Object result = null;
        try {
            log.info("环绕通知=====目标方法之前执行,，其的方法名为："+methodName+",参数为："+ Arrays.toString(args)+"");
             result = joinPoint.proceed();
            log.info("环绕通知=====目标方法返回后执行,返回值为："+ JSON.toJSONString(result));
        }catch(Throwable ex){
            log.error("环绕通知=====目标方法出现异常后执行，异常信息为："+ex.getMessage()+"");
            throw new RuntimeException(ex.getMessage());
        }finally {
            log.info("环绕通知=====目标方法执行完毕");
        }
        return new ApiResult<>().success(result);
    }
}
