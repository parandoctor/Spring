package com.spring.demo.aopapi;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * 返回通知 — 实现 Spring API 的 AfterReturningAdvice 接口
 * 在目标方法成功返回后触发
 */
public class LogAfterReturningAdvice implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.printf("[API返回通知] 方法: %s.%s, 返回值: %s%n",
                target.getClass().getSimpleName(),
                method.getName(),
                returnValue);
    }
}
