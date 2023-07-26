package com.example.localdemo.design_pattern.structure_model.proxy;

/**
 * @author xieteng
 * @date 2023/7/26 ❤15:02
 * @description TODO
 */
public class StaticProxy implements Foodie {
    private FoodieImpl foodieImpl;
    public StaticProxy(FoodieImpl foodieImpl){
        this.foodieImpl = foodieImpl;
    }
    @Override
    public void eat() {
        //开始扩展功能
        System.out.println("已经下单。。。。。。");
        System.out.println("开始等待。。。。。。");
        System.out.println("开始配送。。。。。。");
        foodieImpl.eat();
        System.out.println("品尝后的评价。。。。。。");
    }
}
