package com.spring.demo.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 自定义 BeanPostProcessor — 拦截所有 Bean 的初始化过程
 * 在 Bean 初始化前后插入自定义逻辑
 */
public class CustomBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        if ("lifecycleBean".equals(beanName)) {
            System.out.println("  [BP-Before] BeanPostProcessor.postProcessBeforeInitialization() → " + beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if ("lifecycleBean".equals(beanName)) {
            System.out.println("  [BP-After] BeanPostProcessor.postProcessAfterInitialization() → " + beanName);
        }
        return bean;
    }
}
