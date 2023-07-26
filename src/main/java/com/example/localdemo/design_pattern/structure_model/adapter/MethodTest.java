package com.example.localdemo.design_pattern.structure_model.adapter;

import org.junit.jupiter.api.Test;

/**
 * @author xieteng
 * @date 2023/7/26 ❤14:27
 * @description TODO
 */
public class MethodTest {
    @Test
    public void test(){
        Shape methodAdapter = new MethodAdapter();
        methodAdapter.draw();
    }
}
