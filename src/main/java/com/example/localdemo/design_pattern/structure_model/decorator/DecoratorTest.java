package com.example.localdemo.design_pattern.structure_model.decorator;


import org.junit.jupiter.api.Test;

/**
 * @author xieteng
 * @date 2023/7/26 ❤14:08
 * @description TODO
 */
public class DecoratorTest {

    @Test
    public void deTest() {
        //调用原始的方法，且未修改
        Component com = new ComponentImpl();
        com.operation();


        //调用原始的方法不修改的前提下，添加额外的行为
        Decorator dec = new ConcreteDecorator(com);
        dec.operation();
    }
}
