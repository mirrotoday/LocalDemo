package com.example.localdemo.design_pattern.behaviour_model.strategy.other;

import com.example.localdemo.result.ApiResult;

/**
 * 支付逻辑
 * @author xieteng
 * @date 2023/9/17 ❤ 20:12
 * @description TODO
 */
public interface PayService {

     default ApiResult<?> pay(PayParam payParam){
       throw new RuntimeException("支付业务异常，请稍后重试！");
    }

}
