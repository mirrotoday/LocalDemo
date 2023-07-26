package com.example.localdemo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author xieteng
 * @date 2023/7/18 2:05
 * @description TODO
 */
@RestController
@RequestMapping("/api")
public class TestController {
    @RequestMapping("/get")
    public String get(Model model) throws Exception {
        HashMap<String,Object> reMap=new HashMap<>();
        reMap.put("userName", "张三");
        reMap.put("userAge", 20);
        model.addAllAttributes(reMap);
        return "index";
    }
}
