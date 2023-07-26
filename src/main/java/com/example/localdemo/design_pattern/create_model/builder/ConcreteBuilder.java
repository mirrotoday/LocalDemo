package com.example.localdemo.design_pattern.create_model.builder;

/**
 * @author xieteng
 * @date 2023/7/26 ❤12:41
 * @description TODO
 */
public class ConcreteBuilder extends Builder{
    private Product product = new Product();
    @Override
    public void buildPartA() {
        product.setPartA("PartA");
    }

    @Override
    public void buildPartB() {
        product.setPartB("PartB");
    }

    @Override
    public void buildPartC() {
        product.setPartC("PartC");
    }

    @Override
    public Product getResult() {
        return product;
    }
}
