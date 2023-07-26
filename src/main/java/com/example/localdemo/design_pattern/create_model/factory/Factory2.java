package com.example.localdemo.design_pattern.create_model.factory;

/**
 * @author xieteng
 * @date 2023/7/26 ❤12:26
 * @description TODO
 */
public class Factory2 implements AbstractFactory{
    @Override
    public AbstractProductA createProductA() {
        return new ProductA2();
    }

    @Override
    public AbstractProductB createProductB() {
        return new ProductB2();
    }
}
