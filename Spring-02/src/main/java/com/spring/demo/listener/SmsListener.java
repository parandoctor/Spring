package com.spring.demo.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * SmsListener — 使用 @EventListener 注解监听事件
 *
 * Spring 4.2+ 推荐方式：无需实现接口，方法签名更灵活。
 * 支持异步（@Async）、条件过滤（condition 属性）等高级特性。
 */
@Component
public class SmsListener {

    /**
     * @EventListener 标注的方法自动注册为事件监听器
     * 参数类型决定监听的事件类型
     */
    @EventListener
    public void handleUserRegister(UserRegisterEvent event) {
        System.out.printf("[监听器-短信] 发送注册短信给: %s%n", event.getUsername());
    }

    /**
     * 监听 Spring 内置的 ContextRefreshedEvent（IoC 容器初始化完成）
     */
    @EventListener
    public void handleContextRefresh(org.springframework.context.event.ContextRefreshedEvent event) {
        System.out.println("[监听器-系统] IoC 容器初始化完成！");
    }
}
