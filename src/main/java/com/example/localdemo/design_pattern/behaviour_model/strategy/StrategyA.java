package com.example.localdemo.design_pattern.behaviour_model.strategy;

/**
 * @author xieteng
 * @date 2023/7/26 ❤13:08
 * @description TODO 定义具体策略类A
 */
public class StrategyA implements Strategy{
    @Override
    public void execute() {
        System.out.println("StrategyA，执行算法A");
    }
}
