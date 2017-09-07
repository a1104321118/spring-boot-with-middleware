package com.hr.conf.db;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dell on 2017/3/6.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)

@Import(RepositoryConf.class)
public @interface EnableRepository {
}
