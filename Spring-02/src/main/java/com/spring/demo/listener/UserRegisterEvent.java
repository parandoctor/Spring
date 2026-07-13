package com.spring.demo.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * 自定义事件 — 用户注册事件
 * 继承 ApplicationEvent，携带业务数据
 */
public class UserRegisterEvent extends ApplicationEvent {

    private final String username;
    private final String email;

    public UserRegisterEvent(Object source, String username, String email) {
        super(source);
        this.username = username;
        this.email = email;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
}
