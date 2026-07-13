package com.spring.demo.aopapi;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 前置通知 — 实现 Spring API 的 MethodBeforeAdvice 接口
 * 在目标方法执行前触发
 */
public class LogBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.printf("[API前置通知] 方法: %s.%s, 参数: %s%n",
                target.getClass().getSimpleName(),
                method.getName(),
                Arrays.toString(args));
    }
}
