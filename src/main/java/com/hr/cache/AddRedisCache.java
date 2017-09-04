package com.hr.cache;

import java.lang.annotation.*;

/**
 * Created by hr on 2017/08/07.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AddRedisCache {

    String group();

    String key();

    boolean pojoGenerator() default true;

    long expireTime() default -1;

}
