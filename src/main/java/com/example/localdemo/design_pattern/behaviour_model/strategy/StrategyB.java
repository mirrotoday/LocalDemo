package com.example.localdemo.design_pattern.behaviour_model.strategy;

/**
 * @author xieteng
 * @date 2023/7/26 ❤13:09
 * @description TODO 定义具体策略类B
 */
public class StrategyB implements Strategy{
    @Override
    public void execute() {
        System.out.println("StrategyB 执行算法B");
    }
}
