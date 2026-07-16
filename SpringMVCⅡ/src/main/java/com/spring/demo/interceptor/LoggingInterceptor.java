package com.spring.demo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 日志拦截器：记录每个请求的方法、URI、参数和耗时。
 *
 * 典型用途：
 *   - 接口调用日志（审计）
 *   - 请求耗时监控（性能分析）
 *   - 请求参数脱敏记录
 */
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        // 记录请求开始时间，存入 request 属性，供 afterCompletion 使用
        request.setAttribute("startTime", System.currentTimeMillis());

        System.out.println("========================================");
        System.out.println("[Logging] 请求开始");
        System.out.println("[Logging] 方法: " + request.getMethod());
        System.out.println("[Logging] URI: " + request.getRequestURI());
        System.out.println("[Logging] 参数: " + request.getQueryString());
        System.out.println("[Logging] 客户端IP: " + request.getRemoteAddr());

        return true;  // 总是放行
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        long startTime = (long) request.getAttribute("startTime");
        long duration = System.currentTimeMillis() - startTime;

        System.out.println("[Logging] 状态码: " + response.getStatus());
        System.out.println("[Logging] 耗时: " + duration + "ms");
        if (ex != null) {
            System.out.println("[Logging] 异常: " + ex.getMessage());
        }
        System.out.println("========================================");
    }
}
