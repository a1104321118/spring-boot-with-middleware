package com.hr.conf.db;
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

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * Created by hr on 2017/09/07.
 * druid监控视图配置
 */
@Configuration
@ServletComponentScan //@ServletComponentScan 注解，否则扫不到。。。
@WebServlet(urlPatterns = "/druid/*", initParams={
        @WebInitParam(name="allow",value="192.168.3.108,127.0.0.1"),// IP白名单 (没有配置或者为空，则允许所有访问)
        @WebInitParam(name="deny",value="192.168.16.111"),// IP黑名单 (存在共同时，deny优先于allow)
        @WebInitParam(name="loginUsername",value="admin"),// 用户名
        @WebInitParam(name="loginPassword",value="admin"),// 密码
        @WebInitParam(name="resetEnable",value="false")// 禁用HTML页面上的“Reset All”功能
})
public class DruidStatViewServlet extends StatViewServlet {
}
