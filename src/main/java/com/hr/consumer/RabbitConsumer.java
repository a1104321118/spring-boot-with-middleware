package com.hr.consumer;

import com.hr.model.Account;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by hr on 2017/08/04.
 */
@Component
public class RabbitConsumer {

    @RabbitListener(queues = "business_queue")
    public void register(Account account){
        System.out.println(account);
    }

    @RabbitListener(queues = "test_queue")
    public void test(String str){
        System.out.println(str);
    }
}
