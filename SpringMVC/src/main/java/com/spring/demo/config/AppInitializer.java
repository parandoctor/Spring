package com.spring.demo.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 替代 web.xml 的纯 Java 配置类。
 * Servlet 3.0+ 容器启动时会自动扫描 WebApplicationInitializer 实现类并调用 onStartup()。
 *
 * 这等价于传统 web.xml 中：
 *   1. 注册 ContextLoaderListener（加载 Spring 根容器）
 *   2. 注册 DispatcherServlet（Spring MVC 前端控制器）
 *   3. 配置 servlet-mapping（拦截哪些请求）
 *
 * @see WebApplicationInitializer
 */
public class AppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // 1. 创建 Spring 容器（基于注解配置）
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);  // 注册 Spring MVC 配置类

        // 2. 创建并注册 DispatcherServlet（前端控制器）
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", dispatcherServlet);
        registration.setLoadOnStartup(1);          // 容器启动时立即初始化
        registration.addMapping("/");              // 拦截所有请求（包括静态资源）
    }
}
