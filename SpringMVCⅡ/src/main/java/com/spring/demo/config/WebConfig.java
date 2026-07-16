package com.spring.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spring.demo.interceptor.LoggingInterceptor;
import com.spring.demo.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Spring MVC 核心配置类（替代 spring-mvc.xml）。
 *
 * 配置内容：
 *   - @EnableWebMvc：启用 MVC 注解驱动
 *   - @ComponentScan：组件扫描
 *   - Jackson JSON 消息转换器（支持 Java 8 时间）
 *   - 文件上传解析器（MultipartResolver）
 *   - 拦截器注册（登录拦截器 + 日志拦截器）
 *   - 静态资源处理
 *
 * @see WebMvcConfigurer  Spring MVC 配置定制接口
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.spring.demo")
public class WebConfig implements WebMvcConfigurer {

    // ==================== JSON 消息转换器 ====================

    /**
     * 扩展 Spring MVC 默认的消息转换器，添加 Java 8 时间模块支持。
     * 不加此配置时，LocalDateTime 等类型序列化为 JSON 会失败。
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 找到 Jackson 转换器并配置
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter jsonConverter) {
                ObjectMapper objectMapper = jsonConverter.getObjectMapper();
                // 注册 Java 8 时间模块：支持 LocalDate、LocalDateTime 等
                objectMapper.registerModule(new JavaTimeModule());
                // 禁用将日期写为时间戳（返回 ISO-8601 格式字符串）
                objectMapper.findAndRegisterModules();
            }
        }
    }

    // ==================== 文件上传 ====================

    /**
     * 配置文件上传解析器。
     *
     * StandardServletMultipartResolver 基于 Servlet 3.0 标准，
     * 配合 AppInitializer 中的 MultipartConfigElement 使用。
     *
     * 注意：Bean 名称必须为 "multipartResolver"，Spring MVC 按名称查找。
     */
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    // ==================== 拦截器 ====================

    /**
     * 注册拦截器。
     *
     * 拦截器执行顺序：按 addInterceptor 的注册顺序执行 preHandle，
     * postHandle 和 afterCompletion 则按相反顺序执行。
     *
     * 路径匹配规则：
     *   - /**  : 匹配所有路径
     *   - /api/** : 匹配 /api/ 开头的所有路径
     *   - /*    : 匹配一级路径
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 日志拦截器：拦截所有请求
        registry.addInterceptor(new LoggingInterceptor())
                .addPathPatterns("/**")
                .order(0);  // 第一个执行

        // 登录拦截器：仅拦截 /api/** 的 REST 接口
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/users/login",    // 登录接口不拦截
                        "/index.html",         // 静态页面不拦截
                        "/uploads/**",         // 上传文件访问不拦截
                        "/files/**"            // 文件下载不拦截
                )
                .order(1);  // 第二个执行
    }

    // ==================== 静态资源 ====================

    /**
     * 配置静态资源处理。
     * DispatcherServlet 拦截了 "/"，静态资源需要显式放行。
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/index.html")
                .addResourceLocations("/index.html");
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("/uploads/");
    }
}
