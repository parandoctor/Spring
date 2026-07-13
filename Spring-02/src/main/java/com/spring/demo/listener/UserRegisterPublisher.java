package com.spring.demo.listener;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * UserRegisterPublisher — 事件发布器
 *
 * 实现 ApplicationEventPublisherAware 获取 Publisher，用于发布自定义事件。
 * 或者直接注入 ApplicationEventPublisher。
 */
@Component
public class UserRegisterPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    /**
     * 模拟用户注册 → 发布事件 → 触发所有监听器
     */
    public void register(String username, String email) {
        System.out.printf("[事件发布] 用户 %s 注册成功%n", username);

        // 发布自定义事件 → Spring 自动分发给所有匹配的监听器
        publisher.publishEvent(new UserRegisterEvent(this, username, email));
    }
}
