package com.spring.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AOP 注解版 Java Config
 *
 * @Configuration           声明配置类
 * @ComponentScan           扫描指定包下的 @Component
 * @EnableAspectJAutoProxy  启用 @AspectJ 注解驱动的 AOP 代理
 *
 * proxyTargetClass = true  → 使用 CGLIB 代理（基于类）
 * proxyTargetClass = false → 使用 JDK 动态代理（基于接口，默认）
 */
@Configuration
@ComponentScan(basePackages = "com.spring.demo")
@EnableAspectJAutoProxy(proxyTargetClass = false)
public class AopConfig {
}
