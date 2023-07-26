package com.example.localdemo.design_pattern.behaviour_model.observer;

import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.drawingml.x2006.main.CTEffectStyleItem;

/**
 * @author xieteng
 * @date 2023/7/26 ❤14:47
 * @description TODO 观察者模式（发布-订阅模式）
 */
public class ObserverTest {
    @Test
    public void test(){
        SubjectImpl subjectimpl = new SubjectImpl();
        //创建一个观察者1并实现观察者接口，update方法
        Observer observer1 = new ObserverImpl1();
        //创建一个观察者2并实现观察者接口，update方法
        Observer observer2 = new ObserverImpl2();

        subjectimpl.registerObserver(observer1); // 注册观察者1
        subjectimpl.registerObserver(observer2); // 注册观察者2
        subjectimpl.notifyObserver("观察者模式测试"); // 通知所有观察者

    }
}
