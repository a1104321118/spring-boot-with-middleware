package com.hr.service.impl;

import com.hr.service.RedisLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.TimeUnit;

/**
 * Created by huangrui on 2018/1/2.
 */
@Service
public class RedisLockServiceImpl implements RedisLockService{

    private static long defaultTimeOut = 60;
    private long defaultTryTime = 3000;
    private String defaultValue = "redis";
    /**
     *  设置锁的lua脚本,防止出现 tryLock中的情况
     *  keys:
     *  1. key
     *  2. value
     *  3. timeout
     */
    private static final String SETNX_EXPIRE_SCRIPT = "if redis.call('setnx', KEYS[1], KEYS[2]) == 1 then\n"
            + "return redis.call('expire', KEYS[1], KEYS[3]);\n" + "end\n" + "return nil;";

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JedisPool jedisPool;

    @Override
    public boolean tryLock(String key){

        String newKey = generateKey(key);
        long now = System.currentTimeMillis();

        while (System.currentTimeMillis() - now < defaultTryTime){

            Boolean result = redisTemplate.opsForValue().setIfAbsent(newKey, defaultValue);
            if(result){
                //为了防止各种意外情况出现的死锁，所以加上超时时间
                //注意：该超时时间要设置的长一点，比接口耗费时间要长
                //如果太短，接口还没走完，那么就没用了
                //长一点无所谓，因为提供了unlock方法，接口完成了调用unlock即可
                //这种方法有一个问题，如果上一步完成了，宕机了，那么就死锁了
                redisTemplate.expire(newKey, defaultTimeOut, TimeUnit.SECONDS);
                return true;
            }else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    @Override
    public boolean tryLockSafe(String key) {
        Jedis jedis = jedisPool.getResource();
        String newKey = generateKey(key);
        long now = System.currentTimeMillis();

        while (System.currentTimeMillis() - now < defaultTryTime){
            //执行脚本
            Object eval = jedis.eval(SETNX_EXPIRE_SCRIPT, 3, newKey, defaultValue, String.valueOf(defaultTimeOut));
            if(null != eval){
                jedis.close();
                return true;
            }else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;

    }

    @Override
    public void unlock(String key) {
        //如果已经不存在这个key了，也不会报错
        redisTemplate.delete(generateKey(key));
    }

    private String generateKey(String key){
        String suffix = "_lock";
        return key + suffix;
    }
}
