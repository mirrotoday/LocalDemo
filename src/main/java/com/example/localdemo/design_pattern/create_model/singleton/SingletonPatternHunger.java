package com.example.localdemo.design_pattern.create_model.singleton;

/**
 * @author xieteng
 * @date 2023/7/26 ❤11:50
 * @description TODO 单例模式-饿汉式
 * 饿汉式是典型的空间换时间，
 * 当类装载的时候就会创建类实例，不管你用不用，先创建出来，然后每次调用的时候，就不需要再判断，节省运行时间。
 */
public class SingletonPatternHunger {
    /**
     * 类，只会加载一次，所以这种写法可以保证对象的唯一性
     * 容易产生垃圾对象
     */
    private static  SingletonPatternHunger singletonPatternHunger = new SingletonPatternHunger();
    public SingletonPatternHunger(){

    }
    public static SingletonPatternHunger getSingletonHunger(){
        return singletonPatternHunger;
    }
}
