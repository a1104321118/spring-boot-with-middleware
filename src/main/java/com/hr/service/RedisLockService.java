package com.hr.service;

/**
 * Created by huangrui on 2018/1/2.
 * 使用 redis 来实现分布式锁
 */
public interface RedisLockService {

    boolean tryLock(String key);

    boolean tryLockSafe(String key);

    void unlock(String key);
}
