package com.hr.controller;

import com.hr.cache.AddRedisCache;
import com.hr.cache.DelRedisCache;
import com.hr.model.Account;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by hr on 2017/08/07.
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    /**
     * 新增缓存
     * 测试 redis 生成策略，使用NORMAL策略
     * 以 accountId 和 mobile为key
     * 生成的key 应该是 #account-accountId-mobile，其中 accountId和mobile为传过来的参数
     * 例如：传过来的accountId是 123，mobile是123456
     * key 就是 #account-123-123456
     */
    @AddRedisCache(group = "account", key = "0-2", expireTime = 60, pojoGenerator = false)
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Account test(long accountId, String name, String mobile){
        Account account = new Account();
        account.setAccountId(accountId);
        account.setName(name);
        account.setCreateTime(new Date());
        account.setMobile(mobile);
        return account;
    }

    /**
     * 删除缓存
     * 测试 redis 生成策略，使用NORMAL策略
     * 以 accountId 和 mobile为key
     * 生成的key 应该是 #account-accountId-mobile，其中 accountId和mobile为传过来的参数
     * 例如：传过来的accountId是 123，mobile是123456
     * key 就是 #account-123-123456
     */
    @DelRedisCache(group = "account", key = "0-2" , pojoGenerator = false)
    @RequestMapping(value = "/test3", method = RequestMethod.POST)
    public Account test3(long accountId, String name, String mobile){
        Account account = new Account();
        account.setAccountId(accountId);
        account.setName(name);
        account.setCreateTime(new Date());
        account.setMobile(mobile);
        return account;
    }

    /**
     * 新增缓存
     *
     * 测试 redis 生成策略，使用POJO策略
     * 以 accountId 和 name为key
     * 生成的key 应该是 #account2-accountId-name，其中 accountId和mobile为传过来的account里面的参数
     * 例如：传过来的accountId是 123，name是 hahaha（注意，这里是示例，实际应用中不要把有中文的东西当作key）
     * key 就是 #account2-123-hahaha
     *
     */
    @AddRedisCache(group = "account2", key = "#accountId-#name", expireTime = 10, pojoGenerator = true)
    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    public Account test2(@RequestBody Account account){
        System.out.println(account);
        return account;
    }


    /**
     * 删除缓存
     *
     * 测试 redis 生成策略，使用POJO策略
     * 以 accountId 和 name为key
     * 生成的key 应该是 #account2-accountId-name，其中 accountId和mobile为传过来的account里面的参数
     * 例如：传过来的accountId是 123，name是 hahaha（注意，这里是示例，实际应用中不要把有中文的东西当作key）
     * key 就是 #account2-123-hahaha
     */
    @DelRedisCache(group = "account2", key = "#accountId-#name", pojoGenerator = true)
    @RequestMapping(value = "/test4", method = RequestMethod.POST)
    public Account test4(@RequestBody Account account){
        System.out.println(account);
        return account;
    }
}
