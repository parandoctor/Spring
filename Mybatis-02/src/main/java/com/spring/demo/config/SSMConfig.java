package com.spring.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * SSM 集成总配置类（Spring + SpringMVC + MyBatis）
 *
 * ==================== SSM 三层架构说明 ====================
 *
 * ┌─────────────────────────────────────────────┐
 * │                浏览器 / 客户端                  │
 * └──────────────┬──────────────────────────────┘
 *                │ HTTP 请求
 * ┌──────────────▼──────────────────────────────┐
 * │        SpringMVC（表现层）                      │
 * │  - DispatcherServlet（前端控制器）               │
 * │  - @Controller（控制器）                        │
 * │  - 视图解析、参数绑定、数据校验                     │
 * └──────────────┬──────────────────────────────┘
 *                │
 * ┌──────────────▼──────────────────────────────┐
 * │        Spring（业务层 + IoC 容器）               │
 * │  - @Service（服务层）                           │
 * │  - @Transactional（事务管理）                    │
 * │  - 依赖注入、AOP、Bean 生命周期管理                │
 * └──────────────┬──────────────────────────────┘
 *                │
 * ┌──────────────▼──────────────────────────────┐
 * │        MyBatis（持久层）                        │
 * │  - Mapper 接口（数据访问）                       │
 * │  - SQL 映射 / 注解                              │
 * │  - 关联映射、动态 SQL、延迟加载                    │
 * └──────────────┬──────────────────────────────┘
 *                │
 * ┌──────────────▼──────────────────────────────┐
 * │             MySQL 数据库                       │
 * └─────────────────────────────────────────────┘
 *
 * SSM 集成的关键：
 * 1. Spring 作为 IoC 容器，管理所有 Bean（Controller、Service、Mapper）
 * 2. SpringMVC 处理 Web 层（请求分发、参数绑定、视图解析）
 * 3. MyBatis-Spring 将 Mapper 接口注册为 Spring Bean
 * 4. Spring TX 管理数据库事务（@Transactional）
 *
 * 本类演示 SSM 整合的完整 Java Config 配置方式（零 XML）。
 */
@Configuration
@ComponentScan(basePackages = {"com.spring.demo.service", "com.spring.demo.controller"})
@EnableWebMvc           // 启用 Spring MVC 注解驱动
@EnableTransactionManagement  // 启用事务管理
@Import(MyBatisAdvanceConfig.class)  // 导入 MyBatis 配置
public class SSMConfig {

    /*
     * SSM 配置整合指南：
     *
     * 1. web.xml 替代方案（Servlet 3.0+）：
     *    创建 WebAppInitializer 实现 WebApplicationInitializer
     *    - 注册 DispatcherServlet
     *    - 加载 SSMConfig（Spring + SpringMVC 根配置）
     *
     * 2. 视图解析器配置（可选）：
     *    @Bean
     *    public InternalResourceViewResolver viewResolver() {
     *        InternalResourceViewResolver vr = new InternalResourceViewResolver();
     *        vr.setPrefix("/WEB-INF/views/");
     *        vr.setSuffix(".jsp");
     *        return vr;
     *    }
     *
     * 3. 静态资源处理：
     *    实现 WebMvcConfigurer，重写 addResourceHandlers()
     *
     * @Override
     * public void addResourceHandlers(ResourceHandlerRegistry registry) {
     *     registry.addResourceHandler("/static/**")
     *             .addResourceLocations("/static/");
     * }
     */
}
