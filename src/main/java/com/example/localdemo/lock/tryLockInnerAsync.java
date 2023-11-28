package com.example.localdemo.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import javax.annotation.Resource;

/**
 * @author xieteng
 * @date 2023/8/13 ❤ 16:06
 * @description TODO
 */
public class tryLockInnerAsync {
    @Resource
    private RedissonClient redissonClient;
    public void tryLock() {
        RLock lock = redissonClient.getLock("lock");
        lock.lock();
    }
}
