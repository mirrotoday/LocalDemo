package com.example.localdemo.design_pattern.behaviour_model.observer;

/**
 * @author xieteng
 * @date 2023/7/26 ❤14:42
 * @description TODO 具体观察者2
 */
public class ObserverImpl2 implements Observer{
    @Override
    public void update(String message) {
        System.out.println("ObserverImpl2：" + message);
    }
}
