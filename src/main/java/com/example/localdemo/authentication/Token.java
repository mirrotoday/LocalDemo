package com.example.localdemo.authentication;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author xieteng
 * @date 2023/7/17 20:55
 * @description TODO
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Token {

    boolean validate() default true;
}
