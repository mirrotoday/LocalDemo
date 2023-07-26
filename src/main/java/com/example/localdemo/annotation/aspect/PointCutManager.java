package com.example.localdemo.annotation.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author xieteng
 * @date 2023/7/15 0:52
 * @description TODO
 * 切面管理器，可设置多种类型的切面
 */
@Aspect
@Component
public class PointCutManager {

    @Pointcut("execution(public * com.example.localdemo.service.impl.*.*(..))")
    public void implPointCut(){

    }
    /**
     * 重用切入点表达式
     */
    @Pointcut("execution(public * com.example.localdemo.controller.*.*(..))")
    public void controllerPointCut(){

    }
}
