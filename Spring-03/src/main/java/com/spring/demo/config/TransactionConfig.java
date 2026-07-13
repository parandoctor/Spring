package com.spring.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 事务配置类
 *
 * @EnableTransactionManagement 开启 Spring 声明式事务管理。
 * 等价于 XML 中的 <tx:annotation-driven />。
 *
 * 开启后，Spring 会扫描所有 @Transactional 注解并为之创建 AOP 代理。
 */
@Configuration
@EnableTransactionManagement
public class TransactionConfig {
    // 配置本身为空——所有事务行为通过 @Transactional 注解声明
}
