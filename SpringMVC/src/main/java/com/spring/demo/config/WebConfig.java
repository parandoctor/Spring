package com.spring.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Spring MVC 核心配置类（替代 spring-mvc.xml）。
 *
 * 关键注解：
 *   @Configuration      — 声明这是一个配置类
 *   @EnableWebMvc       — 启用 Spring MVC 注解驱动（等价于 XML 中的 <mvc:annotation-driven/>）
 *   @ComponentScan      — 扫描指定包下的 @Controller、@Service、@Repository、@Component
 *
 * @see WebMvcConfigurer  — 实现该接口可自定义 MVC 配置（视图解析器、拦截器、静态资源等）
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.spring.demo.controller")
public class WebConfig implements WebMvcConfigurer {

    /**
     * 配置视图解析器（InternalResourceViewResolver）
     *
     * 作用：将 Controller 返回的逻辑视图名拼接为完整的 JSP 路径。
     *
     * 示例：
     *   Controller 返回 "hello"
     *   → prefix + "hello" + suffix
     *   → /WEB-INF/views/hello.jsp
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");   // 前缀：视图文件所在目录
        resolver.setSuffix(".jsp");              // 后缀：视图文件扩展名
        return resolver;
    }

    /**
     * 配置静态资源处理。
     * 因为 DispatcherServlet 拦截了 "/"，默认会把静态资源（CSS/JS/图片）也拦截，
     * 通过 addResourceHandlers 告诉 Spring MVC 哪些路径的请求直接交给容器默认 Servlet。
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/static/");
    }
}
