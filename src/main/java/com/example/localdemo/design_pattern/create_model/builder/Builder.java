package com.example.localdemo.design_pattern.create_model.builder;

/**
 * @author xieteng
 * @date 2023/7/26 ❤12:40
 * @description TODO 抽象建造者类
 */
public abstract class Builder {
    public abstract void buildPartA();

    public abstract void buildPartB();

    public abstract void buildPartC();

    public abstract Product getResult();

}
