package com.hr.publisher;

import com.hr.conf.ConstantConf;
import com.hr.model.Account;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by hr on 2017/08/04.
 */
@Component
public class RabbitPublisher {

    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;
    @Autowired
    private ConstantConf constantConf;

    public void registeSuccess(Account account){

        try {
            rabbitMessagingTemplate.convertAndSend(constantConf.getRabbitExchange(),
                    constantConf.getBusinessRoutingKey(), account);
        }catch (Exception e){
            System.out.println("发送消息失败");
        }

    }

    public void test(String msg){

        try {
            rabbitMessagingTemplate.convertAndSend(constantConf.getRabbitExchange(),
                    constantConf.getTestRoutingKey(), msg);
        }catch (Exception e){
            System.out.println("发送消息失败");
        }

    }
}
