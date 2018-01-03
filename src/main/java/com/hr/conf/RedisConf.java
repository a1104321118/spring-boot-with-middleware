package com.hr.conf;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hr.cache.AddRedisCache;
import com.hr.cache.CacheKeyGeneratorEnum;
import com.hr.cache.DelRedisCache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hr on 2017/08/07.
 */
@Configuration
@EnableCaching
public class RedisConf extends CachingConfigurerSupport {

    /**
     * redis key生成策略
     *
     * pojoKeyGenerator:true
     * group-#key1-#key2-...(从入参获得,表示入参的字段名，此种方法只适用于一个pojo参数的情况)
     *
     * pojoKeyGenerator:false
     * group-0-1...（从入参获得，表示参数的下标地址，不允许使用pojo对象）
     *
     */

    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {

                AddRedisCache cache = method.getAnnotation(AddRedisCache.class);
                if(null != cache){
                    if(cache.pojoGenerator()){
                        return CacheKeyGeneratorEnum.POJO_GENERATOR.generateKey(method, params);
                    }
                    return CacheKeyGeneratorEnum.NORMAL_GENERATOR.generateKey(method, params);
                }

                DelRedisCache delRedisCache = method.getAnnotation(DelRedisCache.class);
                if(delRedisCache.pojoGenerator()){
                    return CacheKeyGeneratorEnum.POJO_GENERATOR.generateKey(method, params);
                }
                return CacheKeyGeneratorEnum.NORMAL_GENERATOR.generateKey(method, params);

            }
        };
    }


    /**
     * 管理缓存
     *
     * @param redisTemplate
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        //设置缓存过期时间
        // rcm.setDefaultExpiration(60);//秒
        //设置value的过期时间
//        Map<String, Long> map = new HashMap();
//        map.put("test", 600L);
//        rcm.setExpires(map);
        return rcm;
    }

    /**
     * RedisTemplate配置
     *
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * jedis 池配置
     * @return
     */
    @Bean
    public JedisPool redisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMaxWaitMillis(10000);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379, 0, null);

        return jedisPool;
    }

}
