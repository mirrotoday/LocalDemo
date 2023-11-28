package com.example.localdemo.controller;

import com.example.localdemo.service.RedissonLockService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xieteng
 * @date 2023/8/13 ❤ 16:16
 * @description TODO
 */
@RestController
@RequestMapping("/redisson")
public class RedissonLockController {
    @Resource
    RedissonLockService  redissonLockService;


    /**
     * 公平锁
     * @param id
     * @return
     */
    @GetMapping("/fairLock/{id}")
    public String fairLock(@PathVariable("id") Long id) {
        redissonLockService.fairLock(id);
        return "fail Lock";
    }

    /**
     * 非公平锁，默认可重入锁
     * @param id
     * @return
     */
    @GetMapping("/Lock/{id}")
    public String Lock(@PathVariable("id") Long id) {
        redissonLockService.Lock(id);
        return "RLock";
    }
    @GetMapping("/read/lock")
    public String readLock(){
        redissonLockService.readLock();
        return "read read lock";
    }

    @GetMapping("/write/lock")
    public String writeLock(){
        redissonLockService.writeLock();
        return "write write lock";
    }
    // 用于Redis集群架构下，这些节点是完全独立的，所以不使用复制或任何其他隐式协调系统
    // 该对象可以用来将多个RLock对象关联为一个红锁，每个RLock对象实例可以来自于不同的Redisson实例
    @GetMapping("/testRedLock")
    public String testRedLock(@RequestParam Long id) {
        redissonLockService.redLock(id);
        return "RedLock";
    }
    @GetMapping("/testMultLock")
    public String test(@RequestParam Long id) {
        redissonLockService.multLock(id);
        return "MultLock";
    }
}
