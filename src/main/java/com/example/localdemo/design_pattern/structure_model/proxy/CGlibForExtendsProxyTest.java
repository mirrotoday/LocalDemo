package com.example.localdemo.design_pattern.structure_model.proxy;

import org.junit.jupiter.api.Test;

/**
 * @author xieteng
 * @date 2023/7/26 ❤15:48
 * @description TODO
 */
public class CGlibForExtendsProxyTest {
    @Test
    public void test(){
        FoodieImpl foodieImpl = new FoodieImpl();
        CGlibForExtendsProxy cGlibForExtendsProxy = new CGlibForExtendsProxy(foodieImpl);
        Foodie foodie = (Foodie) cGlibForExtendsProxy.createInstance();
        foodie.eat();
    }
}
