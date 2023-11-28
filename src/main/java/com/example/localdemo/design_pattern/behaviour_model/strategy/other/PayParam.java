package com.example.localdemo.design_pattern.behaviour_model.strategy.other;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xieteng
 * @date 2023/9/17 ❤ 20:09
 * @description TODO
 */
@Data
@ToString
public class PayParam implements Serializable {
    private static  final long serialVersionUID = 1L;


    private Integer patStyle;

    private Long orderId;

    private Long userId;
}
