package com.hr.service.impl;

import com.hr.service.RedisLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by huangrui on 2018/1/2.
 */
@Service
public class RedisLockServiceImpl implements RedisLockService{

    private static long defaultTimeOut = 60;
    private String suffix = "_lock";

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean tryLock(String key){

        int tryTimes = 3;
        String newKey = key + suffix;

        while (tryTimes > 0){
            Boolean result = redisTemplate.opsForValue().setIfAbsent(newKey, key);
            if(result){
                //为了防止各种意外情况出现的死锁，所以加上超时时间
                //注意：该超时时间要设置的长一点，比接口耗费时间要长
                //如果太短，接口还没走完，那么就没用了
                //长一点无所谓，因为提供了unlock方法，接口完成了调用unlock即可
                redisTemplate.expire(newKey, defaultTimeOut, TimeUnit.SECONDS);
                return true;
            }else {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tryTimes--;
            }
        }

        return false;
    }

    @Override
    public void unlock(String key) {
        //如果已经不存在这个key了，也不会报错
        redisTemplate.delete(key + suffix);
    }
}
