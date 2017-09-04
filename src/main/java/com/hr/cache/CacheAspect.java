package com.hr.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by hr on 2017/08/07.
 */
@Component
@Aspect
public class CacheAspect {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private KeyGenerator keyGenerator;

    @Pointcut("@annotation(com.hr.cache.AddRedisCache)")
    public void addCache(){}

    @Pointcut("@annotation(com.hr.cache.DelRedisCache)")
    public void delCache(){}

    @Around("addCache()")
    public Object cacheableProxy(ProceedingJoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AddRedisCache cacheable = method.getAnnotation(AddRedisCache.class);

        String cacheKey = (String)keyGenerator.generate(null, method, joinPoint.getArgs());
        CachePojo cache = (CachePojo)redisTemplate.opsForValue().get(cacheKey);
        if(null != cache){
            return cache.getResult();
        }

        // 执行原方法
        Object methodResult = null;
        try {
            methodResult = joinPoint.proceed();
        }catch (Exception e) {
            e.printStackTrace();
        }catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        try {
            addCache(methodResult, cacheKey, cacheable.expireTime());
            return methodResult;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return methodResult;
    }

    @Around("delCache()")
    public Object cacheEvict(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            String key = (String)keyGenerator.generate(null, method, joinPoint.getArgs());
            redisTemplate.delete(key);
        }
        return null;
    }

    /**
     * 添加缓存
     *
     * @param cacheObj  待添加的对象
     * @param cacheKey  缓存key
     * @param cacheTime 缓存失效时间
     */
    private void addCache(Object cacheObj, String cacheKey, long cacheTime) {
        CachePojo cachePojo = new CachePojo();
        cachePojo.setResult(cacheObj);
        cachePojo.setCreateTime(new Date());

        // 添加缓存，默认失效时间单位——分钟
        if (cacheTime < 0) {
            redisTemplate.opsForValue().set(cacheKey, cachePojo);
        } else {
            redisTemplate.opsForValue().set(cacheKey, cachePojo, cacheTime, TimeUnit.MINUTES);
        }
    }

}
