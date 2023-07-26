package com.example.localdemo.design_pattern.behaviour_model.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xieteng
 * @date 2023/7/26 ❤14:44
 * @description TODO
 */
public class SubjectImpl implements Subject{
    private List<Observer> list = new ArrayList<>();
    /**
     * 注册观察者
     * @param observer
     */
    @Override
    public void registerObserver(Observer observer) {
        list.add(observer);
    }

    /**
     * 移除观察者
     * @param observer
     */
    @Override
    public void removeObserver(Observer observer) {
        list.remove(observer);
    }

    /**
     * 通知观察者
     * @param message
     */
    @Override
    public void notifyObserver(String message) {
        for (Observer observer : list) {
            observer.update(message);
        }
    }
}
