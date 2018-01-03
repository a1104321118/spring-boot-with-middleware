package com.hr.controller;

import com.hr.service.RedisLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by huangrui on 2018/1/2.
 */
@RestController
@RequestMapping("/redisLock")
public class RedisLockController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private AtomicInteger unlock = new AtomicInteger(0);
    private AtomicInteger lock = new AtomicInteger(0);

    @Autowired
    private RedisLockService redisLockService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/test")
    public String test() throws InterruptedException {
        String key = "test";

        try {
            if(redisLockService.tryLockSafe(key)){
                TimeUnit.SECONDS.sleep(5);
                return "SUCC";
            }else {
                return "FAIL";
            }
        } finally {
            redisLockService.unlock(key);
        }

    }

    //模拟高并发
    @RequestMapping("/test2")
    public void test2() {
        CountDownLatch c = new CountDownLatch(1);
        int count = 500;
        CountDownLatch c2 = new CountDownLatch(count);
        for (int i=0; i<count; i++){
            new Thread(new TestThread(c, c2)).start();
        }
        c.countDown();
        try {
            c2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(lock);
        System.out.println(unlock);
        System.out.println(lock.get() + unlock.get() == count);//如果一致，表示测试成功

    }

    private class TestThread implements Runnable {

        private CountDownLatch c;
        private CountDownLatch c2;


        public TestThread(CountDownLatch c, CountDownLatch c2) {
            this.c = c;
            this.c2 = c2;
        }

        @Override
        public void run() {
            try {
                c.await();
                boolean test = redisLockService.tryLockSafe("test");
                if(test){
                    lock.incrementAndGet();
                    Thread.sleep(200);//模拟业务操作
                }else {
                    unlock.incrementAndGet();
                }
                c2.countDown();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                redisLockService.unlock("test");
            }
        }
    }
}
