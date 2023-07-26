package com.example.localdemo.design_pattern.structure_model.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Method;

/**
 * @author xieteng
 * @date 2023/7/26 ❤15:04
 * @description TODO
 */
public class CGlibForExtendsProxy implements MethodInterceptor {
    //定义代理的对象
    private Object target;
    public CGlibForExtendsProxy(Object target){
        this.target = target;
    }
    /**
     * 创建代理对象
     */
    public Object createInstance()
    {
        //1.设置工具类
        Enhancer enhancer=new Enhancer();
        //2.设置父类
        enhancer.setSuperclass(target.getClass());
        //3.设置回调函数
        enhancer.setCallback(this);
        //4.创建代理对象
        return enhancer.create();
    }
    /**
     * 写入拦截扩展代码
     * @param o 目标对象
     * @param method 所扩展目标对象的方法
     * @param objects 方法参数
     * @param methodProxy 代理对象
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("CGLIB 动态代理：已经下单。。。。。。");
        System.out.println("CGLIB 动态代理：开始等待。。。。。。");
        System.out.println("CGLIB 动态代理：开始配送。。。。。。");
        Object invoke = methodProxy.invokeSuper(o,objects);
        System.out.println("CGLIB 动态代理：品尝后的评价。。。。。。");
        return invoke;
    }
}
