package com.spring.demo.aopapi;

import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * 异常通知 — 实现 Spring API 的 ThrowsAdvice 接口（标记接口，无方法）
 * 方法签名必须为: afterThrowing(Method, Object[], Object, Exception) 或其变体
 */
public class LogThrowsAdvice implements ThrowsAdvice {

    /**
     * 当目标方法抛出异常时触发
     *
     * @param method    目标方法
     * @param args      方法参数
     * @param target    目标对象
     * @param ex        抛出的异常
     */
    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
        System.out.printf("[API异常通知] 方法: %s.%s, 异常: %s%n",
                target.getClass().getSimpleName(),
                method.getName(),
                ex.getMessage());
    }
}
