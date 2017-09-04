package com.hr.controller;

import com.hr.dao.AccountMapper;
import com.hr.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by hr on 2017/08/04.
 */
@RestController
@RequestMapping("/mybatis")
public class MybatisController {

    @Autowired
    private AccountMapper accountMapper; //方便起见，不写Service层了

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(){
        Account account = new Account();
        account.setAccountId(100000000000001L);
        account.setName("测试");
        account.setCreateTime(new Date());
        account.setMobile("12345679800");
        accountMapper.insert(account);
    }
}
