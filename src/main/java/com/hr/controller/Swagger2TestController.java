package com.hr.controller;

import com.hr.model.Account;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by hr on 2017/08/04.
 */
@RestController
@RequestMapping("/swagger2")
@Api(value = "swagger2Api", description = "swagger2测试接口")
public class Swagger2TestController {

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ApiOperation(value = "测试", notes = "/swagger2/test")
    public Account test(@RequestBody Account request){
        System.out.println(request);

        Account account = new Account();
        account.setAccountId(100000000000001L);
        account.setName("测试");
        account.setCreateTime(new Date());
        account.setMobile("12345679800");
        return account;
    }
}
