package com.example.localdemo.design_pattern.create_model.builder;


import org.junit.jupiter.api.Test;

/**
 * @author xieteng
 * @date 2023/7/26 ❤ 12:44
 * @description TODO 建造者模式
 */
public class CreateProductTest {
    @Test
    public  void productTest() {
        //ConcreteBuilder 继承了 Builder ,实现了建造者接口中的所有方法，实现了对产品的创建过程
        Builder builder = new ConcreteBuilder();
        //将构建的产品类型对象，交给指挥者进行类型赋值
        Director director = new Director(builder);
        //指挥者开始构建不同的产品类型
        director.construct();
        //此时，给ABC类型都赋值成功，最终返回产品类型的整个对象
        Product product = builder.getResult();
        System.out.println(product);

    }
}
