package com.spring.demo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器：演示认证/授权逻辑。
 *
 * 拦截器是 AOP 思想的体现，在 Controller 方法执行前后织入横切逻辑。
 * 与 Filter 的区别：Interceptor 是 Spring MVC 层面的，可以访问 Spring 容器中的 Bean。
 *
 * 执行流程：
 *   Filter → DispatcherServlet → Interceptor.preHandle → Controller
 *       → Interceptor.postHandle → Interceptor.afterCompletion → 响应
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * preHandle：Controller 方法执行前调用。
     *
     * @return true  = 放行，继续执行后续拦截器和 Controller
     *         false = 拦截，请求到此结束（通常配合 response 写出错误信息）
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // 模拟从请求头获取 Token，实际项目中应校验 JWT 或 Session
        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty()) {
            // 未登录：返回 401 状态码和 JSON 错误信息
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(
                "{\"code\":401,\"message\":\"未登录或 Token 已过期，请先登录\"}"
            );
            return false;  // 拦截请求
        }

        // 实际项目应在此处解析 Token、查询用户信息、设置到 ThreadLocal 或 request 属性中
        System.out.println("[LoginInterceptor] Token 验证通过: " + token);
        request.setAttribute("currentUser", "模拟用户");

        return true;  // 放行
    }

    /**
     * postHandle：Controller 方法执行后、视图渲染前调用。
     * 可以在此处修改 ModelAndView 或添加公共响应头。
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           org.springframework.web.servlet.ModelAndView modelAndView) {
        // 添加公共响应头
        response.setHeader("X-App-Version", "1.0.0");
    }

    /**
     * afterCompletion：整个请求完成后调用（视图已渲染）。
     * 适合做资源清理、请求耗时统计等。
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        // 记录请求耗时（示例）
        long startTime = (long) request.getAttribute("startTime");
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("[LoginInterceptor] 请求完成: " + request.getRequestURI()
                + ", 耗时: " + duration + "ms");
    }
}
