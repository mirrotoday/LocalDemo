package com.example.localdemo.design_pattern.create_model.factory;

/**
 * @author xieteng
 * @date 2023/7/26 ❤12:13
 * @description TODO 抽象工厂类
 */
public interface AbstractFactory {
    AbstractProductA createProductA();

    AbstractProductB createProductB();
}
