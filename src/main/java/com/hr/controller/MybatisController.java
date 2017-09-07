package com.hr.controller;

import com.hr.dao.AccountMapper;
import com.hr.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
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
    @Autowired
    private TransactionTemplate transactionTemplate;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(){
        Account account = new Account();
        account.setAccountId(100000000000001L);
        account.setName("测试");
        account.setCreateTime(new Date());
        account.setMobile("12345679800");
        accountMapper.insert(account);
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public void test2(){
        accountMapper.updateNameByAccountId("test1", 100000000000001L);
        //该用户不存在，但是上面那条记录会更新成功，下面这条不会
        accountMapper.updateNameByAccountId("test1", 100000000000005L);
    }

    @RequestMapping(value = "/test3", method = RequestMethod.GET)
    public void test3(){
        transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {

                int i1 = accountMapper.updateNameByAccountId("test1", 100000000000001L);
                //该用户不存在，但是上面那条记录会更新成功，下面这条不会
                int i2 = accountMapper.updateNameByAccountId("test1", 100000000000005L);

                //但是可以通过如下办法来手工 rollback
                if(i1 != 1 || i2 != 1){
                    transactionStatus.setRollbackOnly();
                    //除了手工rollback 之外，抛错也可以达到同样的效果
                    //throw new RuntimeException("没更新到数据！");
                }
                return null;
            }
        });

    }
}
