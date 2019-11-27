package com.example.shiro.config;



import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.LinkedHashMap;
import java.util.Map;
@Configuration
public class ShiroConfig {
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("Manager")DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl("/toLogin");
        bean.setSuccessUrl("/success");
        bean.setUnauthorizedUrl("/no");
        Map<String,String> map = new LinkedHashMap<>();
        //认证的权限要放在最上面
        map.put("/test","perms[user:add]");
        map.put("/test1","perms[user:add1]");


        map.put("/login","anon");
        map.put("/*","authc");

        bean.setFilterChainDefinitionMap(map);
        return bean;
    }

    @Bean(name = "Manager")
    //本来该写SecurityManager,不知道为什么报错，猜测可能是把方法重新写了。
    public DefaultWebSecurityManager securityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }
    @Bean(name = "userRealm")
    public UserRealm userRealm(){
        return new UserRealm();
    }

    /**
     *
     * 配置ShiroDialect配合thymleaf生成动态标签
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

}
