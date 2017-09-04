package com.hr.controller;

import com.hr.model.Account;
import com.hr.publisher.RabbitPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by hr on 2017/08/04.
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitController {

    @Autowired
    private RabbitPublisher rabbitPublisher;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public void sendMsg(){
        Account account = new Account();
        account.setAccountId(100000000000001L);
        account.setName("测试");
        account.setCreateTime(new Date());
        account.setMobile("12345679800");

        for (int i=0; i<100; i++){
            rabbitPublisher.registeSuccess(account);
        }
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(){

        for (int i=0; i<100; i++){
            rabbitPublisher.test("哈哈哈");
        }
    }
}
