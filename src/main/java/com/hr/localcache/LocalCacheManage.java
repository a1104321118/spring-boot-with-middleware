package com.hr.localcache;
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

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存管理器
 */
public class LocalCacheManage {

    //可以看作是缓存池的概念，存储了本系统所有的缓存
    private static Map<String, AbstractCache> caches = new HashMap<>();

    static {
        Thread refreshThread = new Thread(new RefreshThread());
        refreshThread.setName("cache_refresh_thread");
        //refreshThread.setDaemon(true);
        // 在这里不设为守护进程，因为main方法执行完了，如果剩下的所有线程都是守护线程，那么jvm会直接退出！
        // 但是在web服务中可以设置为守护线程
        refreshThread.start();
    }

    //把缓存添加到缓存池
    public static void add(String cacheName, AbstractCache abstractCache) {
        caches.put(cacheName,abstractCache);
    }

    //获取缓存池
    public static Map<String, AbstractCache> get(){
        return caches;
    }


    //线程动态刷新
    static class RefreshThread implements Runnable{

        @Override
        public void run() {

            while (true){
                try {
                    //实验起见，设置成1S，实际项目中至少1分钟以上刷新一次，否则数据库压力暴增，导致服务挂掉
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //遍历刷新缓存池中的所有缓存
                if(null != caches && caches.size() > 0){
                    for(Map.Entry<String, AbstractCache> cacheEntry : caches.entrySet()){
                        cacheEntry.getValue().refresh();//这就是模板方法的好处
                    }
                }
            }
        }
    }

}
