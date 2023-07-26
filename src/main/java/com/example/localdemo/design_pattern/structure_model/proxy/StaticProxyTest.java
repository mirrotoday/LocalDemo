package com.example.localdemo.design_pattern.structure_model.proxy;

import org.junit.jupiter.api.Test;

/**
 * @author xieteng
 * @date 2023/7/26 ❤15:13
 * @description TODO
 */
public class StaticProxyTest {
    @Test
    public void test(){
        //创新建最开始的品尝美食的实现方法
        FoodieImpl foodie = new FoodieImpl();
        //对美食品尝接口进行静态代理，扩展品尝美食的全过程
        Foodie staticProxy = new StaticProxy(foodie);
        //开始调用
        staticProxy.eat();
    }
}
