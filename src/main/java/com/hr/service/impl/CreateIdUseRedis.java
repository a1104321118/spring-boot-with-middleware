package com.hr.service.impl;


import com.hr.service.CreateIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * Created by hr on 2017/09/13.
 *
 * 性能爆炸
 * 不算网络开销，单节点秒级别上百万
 *
 * 年份（4位）+今年的第几天(3位)+小时（2位）+redis自增值（6位，可以做成配置化，秒杀的时候可以调大） = 15位
 *
 * 想要每个小时都清零，思路，key上再加上小时，设置超时时间为1小时即可
 */
@Service
public class CreateIdUseRedis implements CreateIdService{

    @Autowired
    private RedisTemplate redisTemplate;

    public String getId(String prefix){
        String key = "id-service-" + prefix;
        long id = redisTemplate.opsForValue().increment(key,1) % 1000000;
        Calendar c = Calendar.getInstance();
        String year = String.valueOf(c.get(Calendar.YEAR));
        String day = String.format("%1$03d", c.get(Calendar.DAY_OF_YEAR));
        String hour = String.format("%1$02d", c.get(Calendar.HOUR_OF_DAY));

        return year+day+hour+String.format("%1$06d", id);
    }

}
