package com.example.localdemo.annotation;

import java.lang.annotation.*;

/**
 * @author xieteng
 * @date 2023/7/15 10:35
 * @description TODO
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TakeTime {
}
