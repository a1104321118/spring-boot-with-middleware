package com.hr.cache;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * Created by hr on 2017/08/07.
 * redis key策略枚举
 */
public enum CacheKeyGeneratorEnum {

    /**
     * redis key生成策略
     *
     * pojoKeyGenerator:true
     * group-#key1-#key2-...(从入参获得,表示入参的字段名，此种方法只适用于一个pojo参数的情况)
     *
     * pojoKeyGenerator:false
     * group-0-1...（从入参获得，表示参数的下标地址，不允许使用pojo对象）
     *
     * 如果无参，自行扩展，或改变生成策略
     *
     */

    POJO_GENERATOR("POJO入参key生成策略") {
        @Override
        public String generateKey(Method method, Object... args) {
            AddRedisCache addCache = method.getAnnotation(AddRedisCache.class);
            DelRedisCache delCache = method.getAnnotation(DelRedisCache.class);

            String group = null;
            String cacheKey = null;

            if(null == addCache){
                group = delCache.group();
                cacheKey = delCache.key();
            }else {
                group = addCache.group();
                cacheKey = addCache.key();
            }
            StringBuilder result = new StringBuilder("#");
            result.append(group);

            String[] keys = cacheKey.split("-");
            Object pojo = args[0];
            Field[] pojoFields = pojo.getClass().getDeclaredFields();
            for (String key : keys){
                key = key.substring(1);
                for (Field field : pojoFields){
                    if(key.equals(field.getName())){
                        field.setAccessible(true);
                        try {
                            result.append("-").append(field.get(pojo));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            return result.toString();
        }
    },

    NORMAL_GENERATOR("非POJO入参key生成策略") {
        @Override
        public String generateKey(Method method, Object... args) {
            AddRedisCache addCache = method.getAnnotation(AddRedisCache.class);
            DelRedisCache delCache = method.getAnnotation(DelRedisCache.class);

            String group = null;
            String cacheKey = null;

            if(null == addCache){
                group = delCache.group();
                cacheKey = delCache.key();
            }else {
                group = addCache.group();
                cacheKey = addCache.key();
            }

            StringBuilder result = new StringBuilder("#");
            result.append(group);
            String[] index = cacheKey.split("-");
            for (String s : index){
                Object arg = args[Integer.valueOf(s)];
                result.append("-").append(arg);
            }

            return result.toString();
        }
    }
    ;

    private String desc;

    CacheKeyGeneratorEnum(String desc) {
        this.desc = desc;
    }

    public abstract String generateKey(Method method, Object... args);
}
