package com.hr.controller;
//━━━━━━━━━━━━━━神兽保佑━━━━━━━━━━━━━━━━
//   ┏┛ ┻━━━━━┛ ┻┓  
//   ┃　　　　　　 ┃  
//   ┃　　　━　　　┃ ++ + + +
//  ████━████　  ┃ 
//   ┃　　　　　　 ┃ 
//   ┃　　　┻　　　┃ 
//   ┃　　　　　　 ┃  +
//   ┗━┓　　　┏━━━┛  
//     ┃　　　┃
//     ┃　　　┃  
//     ┃　　　┗━━━━━━━━━┓  + +
//     ┃　　　　　　　    ┣┓  
//     ┃　　　　         ┏┛  
//     ┗━┓ ┓ ┏━━━┳ ┓ ┏━┛  + + + +
//       ┃ ┫ ┫   ┃ ┫ ┫  
//       ┗━┻━┛   ┗━┻━┛+ + + +
//━━━━━━━━━━━━━━永无BUG━━━━━━━━━━━━━━━━

import com.hr.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by hr on 2017/09/07.
 * slf4j 日志测试
 * 主要是 resource/logback.xml 的配置
 *
 */
@RestController
@RequestMapping("log")
public class LogController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public void testInfo(){

        long start = System.currentTimeMillis();

        Account account = new Account();
        account.setAccountId(100000000000001L);
        account.setName("测试");
        account.setCreateTime(new Date());
        account.setMobile("12345679800");

        logger.info("test, result={},耗时={}ms", account, System.currentTimeMillis()-start);
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public void testError(){

        long start = System.currentTimeMillis();

        Account account = new Account();
        account.setAccountId(100000000000001L);
        account.setName("测试");
        account.setCreateTime(new Date());
        account.setMobile("12345679800");

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.error("#1[error], result=[{}],耗时=[{}]ms", account, System.currentTimeMillis()-start);
    }
}
