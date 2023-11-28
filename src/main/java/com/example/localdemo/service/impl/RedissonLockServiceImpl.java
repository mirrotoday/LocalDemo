package com.example.localdemo.service.impl;

import com.example.localdemo.service.RedissonLockService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author xieteng
 * @date 2023/8/13 ❤ 16:22
 * @description TODO
 */
@Service
@Slf4j
public class RedissonLockServiceImpl implements RedissonLockService {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 读锁
     */
    @Override
    public void readLock() {
        RReadWriteLock lock = redissonClient.getReadWriteLock("rwLock");
        lock.readLock().lock(10, TimeUnit.SECONDS);
        // TODO 疯狂的读
//        lock.readLock().unlock();
    }

    /**
     * 写锁
     */
    @Override
    public void writeLock() {
        RReadWriteLock lock = redissonClient.getReadWriteLock("rwLock");
        lock.writeLock().lock(10, TimeUnit.SECONDS);
        // TODO 疯狂的写
//        lock.writeLock().unlock();

    }

    /**
     * 公平锁
     * 公平锁遵循先到先得的原则
     * @param id
     */
    @Override
    public void fairLock(Long id) {
        RLock fairLock = redissonClient.getFairLock("fairLock");
        String threadName = Thread.currentThread().getName();
        //不指定锁的过期时间时，看门狗机制才会生效。不指定过期时间（默认30秒），设置睡眠超过30s执行业务，则锁自动续期
        //指定过期时间时，不会自动续期
        fairLock.lock();
        try {
            //尝试获取锁
            if(fairLock.tryLock()){
                TimeUnit.SECONDS.sleep(3);
                log.info("-----测试公平锁"+id+"-----");
            }else{
                throw new RuntimeException("加锁失败");
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 判断锁是否存在
            boolean locked = fairLock.isLocked();
            // 判断锁是否被当前线程保持
            boolean heldByCurrentThread = fairLock.isHeldByCurrentThread();
            log.info("{}：获取锁状态：{} 是否当前线程保留：{}", threadName, locked, heldByCurrentThread);
            if (locked && heldByCurrentThread) {
                fairLock.unlock();
                log.info("{}：释放锁", threadName);
            } else {
                log.info("{}：未获得锁不用释放", threadName);
            }
        }
    }

    /**
     * 非公平锁
     * @param id
     */
    @Override
    public void Lock(Long id) {
        RLock Lock = redissonClient.getLock("fairLock");
        Lock.lock();
        try {
            TimeUnit.SECONDS.sleep(3);
            log.info("-----测试非公平锁，可重入锁"+id+"-----");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            Lock.unlock();
        }
    }

    /**
     * 红锁
     * 可以使用红锁来解决主从架构锁失效问题：就是说在主从架构系统中，线程A从master中获取到分布式锁，数据还未同步到slave中时master就挂掉了，slave成为新的master，其它线程从新的master获取锁也成功了，就会出现并发安全问题
     *
     * 红锁算法：
     *  应用程序获取系统当前时间，毫秒级
     *  应用程序使用相同的key、value值依次从多个Redis实例中获取锁，如果某一个节点超过一定时间仍然没有获取到锁则直接放弃，尽快尝试从下一个Redis节点获取锁，以避免被宕机的节点阻塞
     * 计算获取锁的消耗时间=客户端程序当前时间-step1中的时间，获取锁的消耗时间小于总的锁定时间（例如30s）并且半数以上节点（假如有5个节点，则至少有3个节点）获取锁成功，才认为获取锁成功
     * 计算剩余锁定时间=总的锁定时间-step3中的消耗时间
     * 如果获取锁失败，对所有的Redis节点释放锁（无论加锁是否成功）
     */
    @Override
    public void redLock(Long id) {
        String threadName = Thread.currentThread().getName();
        RLock one = redissonClient.getLock("one_" + id);
        RLock two = redissonClient.getLock("two_" + id);
        RLock three = redissonClient.getLock("three_" + id);
        RedissonMultiLock redLock = new RedissonRedLock(one, two, three);
        try {
            redLock.lock();
            log.info("{}：获得锁，开始执行业务", threadName);
            TimeUnit.SECONDS.sleep(2);
            log.info("{}：执行结束", threadName);
            //return ResultUtils.success();
        } catch (Exception e) {
            log.error("testRedLock exception:", e);
           // return ResultUtils.sysError();
        } finally {
            // 注意：不能使用isLocked()和isHeldByCurrentThread()方法，会抛出UnsupportedOperationException异常
            redLock.unlock();
            log.info("{}：释放锁成功", threadName);
        }
    }

    /**
     * 联锁
     * 联锁（RedissonMultiLock）对象可以将多个RLock对象关联为一个联锁，实现加锁和解锁功能。
     * 每个RLock对象实例可以来自于不同的Redisson实例。
     */
    @Override
    public void multLock(Long id) {
        String threadName = Thread.currentThread().getName();
        RLock one = redissonClient.getLock("one_" + id);
        RLock two = redissonClient.getLock("two_" + id);
        RLock three = redissonClient.getLock("three_" + id);
        RedissonMultiLock multiLock = new RedissonMultiLock(one, two, three);
        try {
            // 所有的锁都上锁成功才算成功
            multiLock.lock();
            log.info("{}：获得锁，开始执行业务", threadName);
            TimeUnit.SECONDS.sleep(3);
            log.info("{}：执行结束", threadName);
           // return ResultUtils.success();
        } catch (Exception e) {
            log.error("testMultLock exception:", e);
            //return ResultUtils.sysError();
        } finally {
            // 注意：不能使用isLocked()和isHeldByCurrentThread()方法，会抛出UnsupportedOperationException异常
            multiLock.unlock();
            log.info("{}：释放锁成功", threadName);
        }
    }
}
