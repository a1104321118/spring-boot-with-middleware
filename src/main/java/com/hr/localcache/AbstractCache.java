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

/**
 * 抽象缓存类
 */
public abstract class AbstractCache<V> {

    //用于存储数据
    private V data;

    //在构造方法中，就把该缓存交给缓存管理器管理
    public AbstractCache(String cacheName){
        LocalCacheManage.add(cacheName,this);
    }

    //模板方法,final 不能改
    protected final V get(){
        if(null == this.data){
            this.data = load();
        }

        return this.data;
    }

    //模板方法
    protected final void refresh(){
        this.data = load();
    }

    //各子系统自行实现该抽象方法
    protected abstract V load();
}
