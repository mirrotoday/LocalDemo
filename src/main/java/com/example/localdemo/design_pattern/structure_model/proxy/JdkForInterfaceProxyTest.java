package com.example.localdemo.design_pattern.structure_model.proxy;

import org.junit.jupiter.api.Test;

/**
 * @author xieteng
 * @date 2023/7/26 ❤15:27
 * @description TODO
 */
public class JdkForInterfaceProxyTest {
    @Test
    public void test(){
        FoodieImpl foodieImpl = new FoodieImpl();
        JdkForInterfaceProxy jdkForInterfaceProxy = new JdkForInterfaceProxy(foodieImpl);
        Foodie proxyInstance = (Foodie) jdkForInterfaceProxy.getProxyInstance();
        proxyInstance.eat();
    }
}
