package com.example.localdemo.design_pattern.structure_model.adapter;

import org.junit.jupiter.api.Test;

/**
 * @author xieteng
 * @date 2023/7/26 ❤14:20
 * @description TODO 对象适配器
 */
public class ObjectTest {
    @Test
    public void test(){
        ShapeImpl shape = new ShapeImpl();
        //通过适配器的有参构造，给原对象赋值
        ObjectAdapter objectAdapter = new ObjectAdapter(shape);
        //通过适配器的有参构造，给原对象赋值后通过适配器自定义的私有方法，调用变量中动态赋值对象的方法
        objectAdapter.draw();
    }
}
