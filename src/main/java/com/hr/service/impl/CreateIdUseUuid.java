package com.hr.service.impl;
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

import com.hr.service.CreateIdService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by hr on 2017/09/13.
 *
 * uuid  16个字节，一个字节8位，共128位
 * 组成部分 : 当前日期和时间+时钟序列+全局唯一网卡MAC地址
 *
 * 很多版本 MD5, SHA1, 随机数
 *
 */
@Service
public class CreateIdUseUuid implements CreateIdService{


    public String getId(String systemName){
        // java 对于uuid 的支持
        return systemName + "_" +UUID.randomUUID().toString().replaceAll("-", "");
    }

}
