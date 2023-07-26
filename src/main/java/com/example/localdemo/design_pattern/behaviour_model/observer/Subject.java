package com.example.localdemo.design_pattern.behaviour_model.observer;

/**
 * @author xieteng
 * @date 2023/7/26 ❤14:43
 * @description TODO 主题接口
 */
public interface Subject {
    //注册观察者
    void registerObserver(Observer observer);
    //移除观察者
    void removeObserver(Observer observer);
    //通知观察者
    void notifyObserver(String message);
}
