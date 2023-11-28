package com.example.localdemo.design_pattern.behaviour_model.strategy.other;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 支付策略工厂
 * @author xieteng
 * @date 2023/9/17 ❤ 20:14
 * @description TODO
 */
public class PayFactory {
    private static Map<Integer,PayService> map = new ConcurrentHashMap<>();

    /**
     * 动态从Map中获取当前的支付方式服务
     * @param style
     * @return
     */
    public static PayService getPayStyle(Integer style){
        return map.get(style);
    }

    /**
     * 动态注册支付服务到Map
     * @param style
     * @param payService
     */
    public static void register(Integer style,PayService payService){
        map.put(style,payService);
    }
}
