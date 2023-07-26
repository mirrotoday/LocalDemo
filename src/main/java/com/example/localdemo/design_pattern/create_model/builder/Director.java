package com.example.localdemo.design_pattern.create_model.builder;

/**
 * @author xieteng
 * @date 2023/7/26 ❤12:43
 * @description TODO 指挥者
 */
public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public void construct() {
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
    }

}
