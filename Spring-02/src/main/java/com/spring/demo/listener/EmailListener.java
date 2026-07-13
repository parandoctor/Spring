package com.spring.demo.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * EmailListener — 监听用户注册事件（实现 ApplicationListener 接口）
 *
 * 当 UserRegisterEvent 被发布时，自动触发 onApplicationEvent()
 * 模拟：注册成功后发送欢迎邮件
 */
@Component
public class EmailListener implements ApplicationListener<UserRegisterEvent> {

    @Override
    public void onApplicationEvent(UserRegisterEvent event) {
        System.out.printf("[监听器-邮件] 发送欢迎邮件给: %s (%s)%n",
                event.getUsername(), event.getEmail());
    }
}
