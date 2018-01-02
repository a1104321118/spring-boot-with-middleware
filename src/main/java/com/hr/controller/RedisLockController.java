package com.hr.controller;

import com.hr.service.RedisLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * Created by huangrui on 2018/1/2.
 */
@RestController
@RequestMapping("/redisLock")
public class RedisLockController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisLockService redisLockService;

    @RequestMapping("/test")
    public String test(){
        String key = "test";

        try {
            if(redisLockService.tryLock(key)){
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "SUCC";
            }else {
                return "FAIL";
            }
        } finally {
            redisLockService.unlock(key);
        }

    }
}
