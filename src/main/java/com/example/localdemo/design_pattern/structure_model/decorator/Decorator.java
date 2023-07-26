package com.example.localdemo.design_pattern.structure_model.decorator;

/**
 * @author xieteng
 * @date 2023/7/26 ❤14:05
 * @description TODO 抽线装饰器
 */
public class Decorator implements Component{
    protected Component component;

    public Decorator(Component component) {
        this.component = component;
    }
    @Override
    public void operation() {
        component.operation();
    }
}
