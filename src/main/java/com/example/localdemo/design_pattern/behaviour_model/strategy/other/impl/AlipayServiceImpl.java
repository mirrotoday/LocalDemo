package com.example.localdemo.design_pattern.behaviour_model.strategy.other.impl;

import cn.hutool.core.lang.Console;
import com.example.localdemo.design_pattern.behaviour_model.strategy.other.PayFactory;
import com.example.localdemo.design_pattern.behaviour_model.strategy.other.PayParam;
import com.example.localdemo.design_pattern.behaviour_model.strategy.other.PayService;
import com.example.localdemo.result.ApiResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;


/**
 * InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet一个方法，凡是实现该接口的类，在初始化bean的时候都会执行该方法。
 * @author xieteng
 * @date 2023/9/17 ❤ 20:22
 * @description TODO
 */
@Service
public class AlipayServiceImpl implements PayService, InitializingBean {
    /**
     * 注册支付宝支付方式到支付工厂
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("支付宝支付初注册初始化");
        PayFactory.register(1,this);
    }

    @Override
    public ApiResult<?> pay(PayParam payParam) {
        Console.log("当前业务逻辑为：[支付宝支付]");
        return new ApiResult<>().success(payParam);
    }
}
