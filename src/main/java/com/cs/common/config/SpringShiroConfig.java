package com.cs.common.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class SpringShiroConfig {
    @Bean
    public SecurityManager securityManager(Realm realm){
        DefaultWebSecurityManager sm = new DefaultWebSecurityManager();
        sm.setRealm(realm);
        return sm;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean sfBean = new ShiroFilterFactoryBean();
        sfBean.setSecurityManager(securityManager);
        sfBean.setLoginUrl("/doLoginUI");
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        //静态资源允许匿名访问:"anon"
        map.put("/bower_components/**","anon");
        map.put("/build/**","anon");
        map.put("/dist/**","anon");
        map.put("/plugins/**","anon");
        map.put("/user/doLogin","anon");
        map.put("/doLogout","logout");
        //除了匿名访问的资源,其它都要认证("authc")后访问
        map.put("/**","authc");
        sfBean.setFilterChainDefinitionMap(map);
        return sfBean;
    }
}
