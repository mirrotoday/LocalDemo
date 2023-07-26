package com.example.localdemo.design_pattern.structure_model.proxy;



import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author xieteng
 * @date 2023/7/26 ❤15:03
 * @description TODO 该类用于处理代理方法逻辑及或利用反射创建代理对象:
 */
public class JdkForInterfaceProxy implements InvocationHandler {
    //定义需要动态代理的对象
    private Object target;

    public JdkForInterfaceProxy(Object target){
        this.target = target;
    }

    /**
     * 创建代理工厂
     * @return
     */
    public Object getProxyInstance(){
        /**
         * ClassLoader loader, 目标对象的类加载器
         * Class<?>[] interfaces, 目标对象所实现的所有接口
         * InvocationHandler h  代理的方法逻辑自动调用重写的invoke
         */
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new JdkForInterfaceProxy(target));
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("JDK动态代理：已经下单。。。。。。");
        System.out.println("JDK动态代理：开始等待。。。。。。");
        System.out.println("JDK动态代理：开始配送。。。。。。");
        Object invoke = method.invoke(target, args);
        System.out.println("JDK动态代理：品尝后的评价。。。。。。");
        return invoke;
    }
}
