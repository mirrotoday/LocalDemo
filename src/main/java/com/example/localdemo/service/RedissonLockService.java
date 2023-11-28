package com.example.localdemo.service;

/**
 * @author xieteng
 * @date 2023/8/13 ❤ 16:20
 * @description TODO
 */
public interface RedissonLockService {
      void readLock();

      void writeLock();

    void fairLock(Long id);

    void Lock(Long id);

    void redLock(Long id);

    void multLock(Long id);
}
