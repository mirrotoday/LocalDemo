package com.example.localdemo.controller;

import com.example.localdemo.result.ApiResult;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xieteng
 * @date 2023/8/25 ❤ 16:30
 * @description TODO
 */
@RestController
@RequestMapping("/wsAndRedis")
public class WsAndRedisController {
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/sendRedisMsg/{msg}")
    public ApiResult<?> sendMessage(@PathVariable("msg") String msg){
        redisTemplate.convertAndSend("ws", msg);
        return new ApiResult<>().success("Redis 消息订阅模式发送成功");
    }
}
