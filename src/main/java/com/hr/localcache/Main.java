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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hr on 2017/09/11.
 */
public class Main {

    public static void main(String[] args) {
        //==================第一个缓存==================
        AbstractCache<List<String>> conf1 = new AbstractCache<List<String>>("confCache1") {
            @Override
            protected List<String> load() {
                List<String> result = new ArrayList<String>();

                //模拟从数据库获取数据，实际开发中可以进行数据库操作
                System.out.println("我从数据库获得最新数据啦1");
                result.add("conf1");
                result.add("conf2");
                return result;
            }
        };
        //查看缓存中的数据
        AbstractCache confCache1 = LocalCacheManage.get().get("confCache1");
        List<String> list1 = (List<String>) confCache1.get();
        System.out.println(list1.get(0));

        //========================另一个缓存===============
        AbstractCache<Map<String, Object>> conf2 = new AbstractCache<Map<String, Object>>("confCache2") {
            @Override
            protected Map<String, Object> load() {
                Map<String, Object> map = new HashMap<>();

                //模拟从数据库获取数据，实际开发中可以进行数据库操作
                System.out.println("我从数据库获得最新数据啦2");
                map.put("conf3","conf3333");
                map.put("conf4","conf4444");
                return map;
            }
        };
        AbstractCache confCache2 = LocalCacheManage.get().get("confCache2");
        Map<String, Object> map = (Map<String, Object>) confCache2.get();
        System.out.println(map.get("conf4"));


    }
}
