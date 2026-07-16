package com.spring.demo.config;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 替代 web.xml 的纯 Java 配置（Servlet 3.0+ SPI 机制）。
 *
 * 职责：
 *   1. 创建 Spring 根容器（AnnotationConfigWebApplicationContext）
 *   2. 注册 DispatcherServlet（前端控制器）
 *   3. 配置 DispatcherServlet 的拦截路径和启动顺序
 *   4. 配置文件上传的 MultipartConfig
 *
 * 这等价于传统 web.xml 中的整套 <servlet> + <servlet-mapping> + <multipart-config> 配置。
 */
public class AppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // 1. 创建基于注解的 Spring Web 应用上下文
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);  // 注册 MVC 配置类

        // 2. 创建 DispatcherServlet 并与 Spring 容器关联
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);

        // 3. 动态注册 DispatcherServlet 到 Servlet 容器
        ServletRegistration.Dynamic registration =
                servletContext.addServlet("dispatcher", dispatcherServlet);
        registration.setLoadOnStartup(1);   // 容器启动时立即初始化（值越小越优先）
        registration.addMapping("/");       // 拦截所有请求（"/" 而非 "/*"）

        /* 4. 配置文件上传支持
         *    - location:      临时文件存储目录（空字符串 = 容器默认）
         *    - maxFileSize:   单个文件最大 10MB
         *    - maxRequestSize:整个请求最大 20MB
         *    - fileSizeThreshold: 超过 1MB 写入磁盘
         */
        registration.setMultipartConfig(
                new MultipartConfigElement(
                        "",                  // 临时目录
                        10 * 1024 * 1024,    // maxFileSize: 10MB
                        20 * 1024 * 1024,    // maxRequestSize: 20MB
                        1024 * 1024          // fileSizeThreshold: 1MB
                )
        );
    }
}
