package com.example.localdemo.design_pattern.behaviour_model.strategy;

/**
 * @author xieteng
 * @date 2023/7/26 ❤13:10
 * @description TODO 上下文类，持有一个策略接口的引用，用于调用具体策略类中的算法
 */
public class Context {
    private Strategy strategy;
    //有参构造，对Strategy接口的不同策略算法进行默认赋值
    public Context(Strategy strategy) {
        this.strategy = strategy;
    }
    //无参构造
    public Context() {}
    //方法可以动态地改变策略，达到不同的行为。
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
    //执行策略
    public void  doStrategy() {
        strategy.execute();
    }

}
