package com.example.localdemo.design_pattern.create_model.singleton;


/**
 * @author xieteng
 * @date 2023/7/26 ❤11:25
 * @description TODO 单例模式-懒汉式（延迟加载）
 * 单例模式是（Singleton Pattern）Java中最常用的设计模式之一，它保证一个类仅有一个实例，并提供一个全局访问点。
 * 实现单例模式的核心是将类的构造方法私有化，以防止外部直接通过构造函数创建实例。
 * 同时，类内部需要提供一个静态方法或变量来获取该类的唯一实例。
 */
public class SingletonPatternLazy {
    /**
     * @description 懒汉式是典型的时间换空间，
     * 也就是每次获取实例都会进行判断，看是否需要创建实例。如果一直没有人使用的话，那就不会创建实例，节约内存空间。
     * 延迟加载：开始不加载资源或者数据，等到马上就要使用这个资源或数据了才加载，所以也称LazyLoad（延迟加载）
     * 面试：懒汉式（可能会出问题的单例模式） 线程不安全(必须加锁 synchronized 才能保证单例,但是影响效率)
     * 懒加载（延迟加载）
     * 线程安全问题
     *      是否多线程环境：是
     *      是否有共享数据：是
     *      是否有多条语句操作共享数据 ：是
     */
    private static SingletonPatternLazy instance = null;

    // 私有构造方法
    private SingletonPatternLazy() {
    }

    // 静态方法，获取唯一实例
    public  static synchronized SingletonPatternLazy getInstance() {
        if (instance == null) {
            instance = new SingletonPatternLazy();
        }
        return instance;
    }

}
