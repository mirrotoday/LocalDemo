package com.example.localdemo.design_pattern.structure_model.adapter;

/**
 * @author xieteng
 * @date 2023/7/26 ❤14:17
 * @description TODO 适配器类
 */
public class ObjectAdapter implements Shape{
    private ShapeImpl shapeImpl;
    public ObjectAdapter(ShapeImpl shapeImpl){
        this.shapeImpl = shapeImpl;
    }
    public void draw() {
        shapeImpl.draw();
    }
}
