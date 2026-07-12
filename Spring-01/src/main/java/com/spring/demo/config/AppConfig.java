package com.spring.demo.config;

import com.spring.demo.beans.User;
import com.spring.demo.beans.UserDao;
import com.spring.demo.beans.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Java Config 配置类 — 替代 XML 配置（Spring Boot 推荐方式）
 * @Configuration 表明这是一个配置类，相当于一个 XML 配置文件
 * @Bean 将方法返回值注册到 IoC 容器
 */
@Configuration
public class AppConfig {

    /**
     * 默认作用域为 singleton
     */
    @Bean
    public UserDao userDao() {
        return new UserDao();
    }

    /**
     * 构造器注入 → 直接在 @Bean 方法中调用
     */
    @Bean
    public UserService userService() {
        UserService service = new UserService();
        service.setUserDao(userDao());  // 手动注入，Spring 会代理确保单例
        return service;
    }

    /**
     * @Scope 指定作用域
     */
    @Bean
    @Scope("prototype")
    public User prototypeUser() {
        User user = new User();
        user.setName("原型Bean-JavaConfig");
        user.setEmail("prototype@config.com");
        return user;
    }
}
