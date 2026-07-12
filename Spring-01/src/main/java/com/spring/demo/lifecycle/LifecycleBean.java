package com.spring.demo.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

/**
 * LifecycleBean — 完整演示 Spring Bean 的 10 个生命周期阶段
 *
 * 【完整生命周期流程】
 *   1. 实例化（构造器）
 *   2. 属性赋值（Setter 注入）
 *   3. BeanNameAware.setBeanName()
 *   4. BeanFactoryAware.setBeanFactory()
 *   5. ApplicationContextAware.setApplicationContext()
 *   6. BeanPostProcessor.postProcessBeforeInitialization()
 *   7. @PostConstruct / InitializingBean.afterPropertiesSet()
 *   8. 自定义 init-method
 *   9. BeanPostProcessor.postProcessAfterInitialization()
 *  10. @PreDestroy / DisposableBean.destroy() / 自定义 destroy-method
 */
public class LifecycleBean implements BeanNameAware, BeanFactoryAware,
        InitializingBean, DisposableBean {

    private String data;

    public LifecycleBean() {
        System.out.println("  [1] 构造器 — Bean 实例化");
    }

    public void setData(String data) {
        this.data = data;
        System.out.println("  [2] setData() — 属性注入: " + data);
    }

    // ============= Aware 系列接口 =============
    @Override
    public void setBeanName(String name) {
        System.out.println("  [3] BeanNameAware — Bean 名称: " + name);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("  [4] BeanFactoryAware — 获得 BeanFactory");
    }

    // ============= 初始化 =============
    @PostConstruct
    public void postConstruct() {
        System.out.println("  [5] @PostConstruct — 注解初始化方法");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("  [6] InitializingBean.afterPropertiesSet() — 接口初始化方法");
    }

    /**
     * XML 中配置的 init-method
     */
    public void customInit() {
        System.out.println("  [7] customInit() — 自定义 init-method");
    }

    // ============= 销毁 =============
    @PreDestroy
    public void preDestroy() {
        System.out.println("  [8] @PreDestroy — 注解销毁方法");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("  [9] DisposableBean.destroy() — 接口销毁方法");
    }

    /**
     * XML 中配置的 destroy-method
     */
    public void customDestroy() {
        System.out.println("  [10] customDestroy() — 自定义 destroy-method");
    }

    public String getData() {
        return data;
    }
}
