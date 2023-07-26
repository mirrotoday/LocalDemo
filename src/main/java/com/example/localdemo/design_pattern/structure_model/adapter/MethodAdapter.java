package com.example.localdemo.design_pattern.structure_model.adapter;

/**
 * @author xieteng
 * @date 2023/7/26 ❤14:25
 * @description TODO
 */
public class MethodAdapter extends Method implements Shape{

    @Override
    public void draw() {
        super.draw2();
    }
}
