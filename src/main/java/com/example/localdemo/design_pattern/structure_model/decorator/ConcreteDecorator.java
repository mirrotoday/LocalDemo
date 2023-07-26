package com.example.localdemo.design_pattern.structure_model.decorator;

/**
 * @author xieteng
 * @date 2023/7/26 ❤14:06
 * @description TODO
 */
public class ConcreteDecorator extends Decorator{
    public ConcreteDecorator(Component component) {
        super(component);
    }
    public void operation() {
        super.operation();
        addedBehavior();
    }

    public void addedBehavior() {
        System.out.println("使用装饰模式，在不修改原来的类的情况下，添加额外的行为和方法");
    }

}
