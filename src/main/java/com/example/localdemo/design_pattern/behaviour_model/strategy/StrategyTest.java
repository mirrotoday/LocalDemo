package com.example.localdemo.design_pattern.behaviour_model.strategy;


import org.junit.jupiter.api.Test;

/**
 * @author xieteng
 * @date 2023/7/26 ❤13:14
 * @description TODO 策略模式
 */
public  class StrategyTest {
    @Test
    public void testStrategy(){
        //使用有参构造的方法给上下文中赋值策略的类型
        Strategy strategyA = new StrategyA();
        Context context = new Context(strategyA);
        context.doStrategy(); // 输出：执行策略A
        //使用上下文中setStrategy()方法进行策略算法类型的动态赋值
        Strategy strategyB = new StrategyB();
        context.setStrategy(strategyB);
        context.doStrategy(); // 输出：执行策略B
    }


}
