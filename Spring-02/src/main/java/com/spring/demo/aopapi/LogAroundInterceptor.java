package com.spring.demo.aopapi;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 环绕通知 — 实现 AOP Alliance 的 MethodInterceptor 接口
 * 功能最强大，可完全控制目标方法的执行
 */
public class LogAroundInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String methodName = invocation.getMethod().getName();
        long start = System.currentTimeMillis();

        System.out.printf("[API环绕-前] 开始执行: %s%n", methodName);

        // 执行目标方法
        Object result = invocation.proceed();

        long elapsed = System.currentTimeMillis() - start;
        System.out.printf("[API环绕-后] 执行完毕: %s, 耗时: %dms, 返回值: %s%n",
                methodName, elapsed, result);

        return result;
    }
}
