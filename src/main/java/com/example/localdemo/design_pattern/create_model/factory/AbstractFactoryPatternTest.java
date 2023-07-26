package com.example.localdemo.design_pattern.create_model.factory;

import org.junit.jupiter.api.Test;

/**
 * @author xieteng
 * @date 2023/7/26 ❤12:06
 * @description TODO 1.简单工厂模式、2.工厂方法模式 3.抽象工厂模式
 * 抽象工厂模式（Abstract Factory Pattern）是Java中常用的一种创建型设计模式
 * 它提供了一种创建一系列相关或相互依赖的对象接口，而无需指定它们具体的类。
 */
public class AbstractFactoryPatternTest {
    @Test
    public  void est() {
        AbstractFactory proA = new Factory1();
        AbstractProductA productA1 = proA.createProductA();
        AbstractProductB productB1 = proA.createProductB();
        productA1.use();// 输出：使用ProductA1
        productB1.consume();// 输出：消费ProductB1

        AbstractFactory proB = new Factory2();
        AbstractProductA productA2 = proB.createProductA();
        AbstractProductB productB2 = proB.createProductB();
        productA2.use();// 输出：使用ProductA2
        productB2.consume();// 输出：消费ProductB2
    }
}
