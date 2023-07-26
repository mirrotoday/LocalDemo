package com.example.localdemo.design_pattern.create_model.factory;

/**
 * @author xieteng
 * @date 2023/7/26 ❤12:18
 * @description TODO
 */
public class Factory1 implements AbstractFactory{
    @Override
    public AbstractProductA createProductA() {
        return new ProductA1();
    }

    @Override
    public AbstractProductB createProductB() {
        return new ProductB1();
    }
}
