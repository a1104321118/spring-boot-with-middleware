package com.hr.controller;

import com.hr.service.CreateIdService;
import com.hr.service.impl.CreateIdUseJUC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by hr on 2017/08/21.
 */
@RestController
@RequestMapping("/id")
public class CreateIdController {

    @Autowired
    @Qualifier("createIdUseRedis")
    private CreateIdService redis;
    @Autowired
    @Qualifier("createIdUseJUC")
    private CreateIdService juc;
    @Autowired
    @Qualifier("createIdUseUuid")
    private CreateIdService uuid;

    @RequestMapping(value = "/{service}/{systemName}", method = RequestMethod.GET)
    public String get(@PathVariable("service") final String service, @PathVariable("systemName") final String systemName){

        String result;
        switch (service){
            case "redis":
                result = redis.getId(systemName);
                break;
            case "juc":
                result = juc.getId(systemName);
                break;
            case "uuid":
                result = uuid.getId(systemName);
                break;
            default:
                result = null;
        }
        return result;
    }

    //查看map的信息
    /*@RequestMapping(value = "print", method = RequestMethod.GET)
    public void print(){
        ConcurrentMap<String, LinkedBlockingQueue<Long>> idMaps = createIdUseJUC.getIdMaps();
        for (String key : idMaps.keySet()){
            System.out.println(key + ":" + idMaps.get(key).toString());
        }
    }*/

    //测试多线程情况
    /*@RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(){

        final String systemName = "marketing";

        final CountDownLatch c = new CountDownLatch(100);

        final List<String> idList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 103; j++) {

                        String id = createIdUseJUC.getId(systemName);
                        idList.add(id);
                    }
                    c.countDown();

                }
            }).start();
        }

        try {
            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(idList);
    }*/

}
