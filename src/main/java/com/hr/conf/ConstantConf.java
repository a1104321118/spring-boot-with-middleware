package com.hr.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by hr on 2017/08/04.
 * 常量
 */
@Component
public class ConstantConf {

    @Value("${rabbit.business.exchange}")
    private String rabbitExchange;

    @Value("${rabbit.business.routingKey}")
    private String businessRoutingKey;

    @Value("${rabbit.test.routingKey}")
    private String testRoutingKey;

    public String getRabbitExchange() {
        return rabbitExchange;
    }

    public void setRabbitExchange(String rabbitExchange) {
        this.rabbitExchange = rabbitExchange;
    }

    public String getBusinessRoutingKey() {
        return businessRoutingKey;
    }

    public void setBusinessRoutingKey(String businessRoutingKey) {
        this.businessRoutingKey = businessRoutingKey;
    }

    public String getTestRoutingKey() {
        return testRoutingKey;
    }

    public void setTestRoutingKey(String testRoutingKey) {
        this.testRoutingKey = testRoutingKey;
    }
}
