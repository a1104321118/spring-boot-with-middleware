package com.hr.service.impl;

import com.hr.dao.IdTestMapper;
import com.hr.model.IdTest;
import com.hr.service.CreateIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by hr on 2017/08/21.
 */
@Service
public class CreateIdUseJUC implements CreateIdService{

    @Autowired
    private IdTestMapper idTestMapper;

    private static ConcurrentMap<String, LinkedBlockingQueue<Long>> idMaps;

    //这个线程池的意思是：保证一个任务只由一个线程执行，执行完之后60S内无任务就销毁
    private ExecutorService executorService = Executors.newCachedThreadPool();

    @PostConstruct
    public void init(){
        //在这里把 idmaps 给初始化
        idMaps = new ConcurrentHashMap<>();
        List<IdTest> idTestList = idTestMapper.getAll();

        for (IdTest idTest : idTestList){
            LinkedBlockingQueue<Long> queue = new LinkedBlockingQueue<>((int) idTest.getStepSize());
            idMaps.put(idTest.getSystemName(), queue);
            executorService.submit(new LoadIdRunnable(idTest.getSystemName(), queue));
        }

    }

    public String getId(String systemName){
        LinkedBlockingQueue<Long> idQueue = idMaps.get(systemName);

        if (null == idQueue){//防止未初始化完成
            return null;
        }

        Long result = null;
        try {
            result = idQueue.poll(6, TimeUnit.SECONDS);//等待队列，6S足够从数据库中取出数据
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return String.valueOf(result);
    }

    public ConcurrentMap<String, LinkedBlockingQueue<Long>> getIdMaps() {
        return idMaps;
    }

    //从数据库加载ID线程
    private class LoadIdRunnable implements Runnable{

        private String systemName;
        private BlockingQueue queue;

        public LoadIdRunnable(String systemName, BlockingQueue queue) {
            this.systemName = systemName;
            this.queue = queue;
        }

        @Override
        public void run() {
            List<Long> idList = getIdFromDB();
            while (true){
                if(null == idList || idList.size() == 0){
                    idList = getIdFromDB();
                }
                try {
                    queue.put(idList.remove(0)); //阻塞等待,注意，这里不能用offer，offer会直接返回
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        private List<Long> getIdFromDB(){
            IdTest idTest = idTestMapper.getBySystemName(systemName);

            long currentNo = idTest.getCurrentNo() + idTest.getStepSize();
            long origNo = idTest.getCurrentNo();

            //乐观锁更新
            int k = idTestMapper.updateBySystemName(systemName, currentNo, origNo);

            //如果更新到
            if(k == 1){
                List<Long> idList = new ArrayList<>();
                for(long i=origNo; i<currentNo; i++){
                    idList.add(i);
                }
                return idList;
            }

            //如果没更新到，递归
            return getIdFromDB();

        }
    }
}
