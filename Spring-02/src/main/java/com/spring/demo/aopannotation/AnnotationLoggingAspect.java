package com.spring.demo.aopannotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * AnnotationLoggingAspect — 基于 @AspectJ 注解的切面
 *
 * 这是 Spring Boot 时代最推荐的 AOP 方式。
 *
 * @Aspect     声明该类为切面
 * @Component  让 Spring 扫描并管理该 Bean
 * @Pointcut   定义切入点表达式（可复用）
 * @Before     前置通知
 * @After      后置通知（finally）
 * @AfterReturning  返回通知
 * @AfterThrowing   异常通知
 * @Around     环绕通知
 */
@Aspect
@Component
public class AnnotationLoggingAspect {

    /**
     * 切入点表达式 — 复用
     * 匹配 com.spring.demo.service 包下所有方法
     */
    @Pointcut("execution(* com.spring.demo.service.*.*(..))")
    public void serviceLayer() {}

    /**
     * 切入点：仅匹配 UserService 的方法
     */
    @Pointcut("execution(* com.spring.demo.service.UserService.*(..))")
    public void userServiceOnly() {}

    // ==================== 前置通知 ====================
    @Before("serviceLayer()")
    public void beforeLog(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.printf("[注解前置] 即将执行: %s, 参数: %s%n",
                methodName, Arrays.toString(args));
    }

    // ==================== 后置通知（finally，无论成功/异常都会执行） ====================
    @After("serviceLayer()")
    public void afterLog(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.printf("[注解后置] 方法执行完毕: %s%n", methodName);
    }

    // ==================== 返回通知（仅成功返回后执行） ====================
    @AfterReturning(pointcut = "serviceLayer()", returning = "result")
    public void afterReturningLog(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.printf("[注解返回] 方法: %s, 返回值: %s%n", methodName, result);
    }

    // ==================== 异常通知 ====================
    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void afterThrowingLog(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        System.out.printf("[注解异常] 方法: %s, 异常信息: %s%n", methodName, ex.getMessage());
    }

    // ==================== 环绕通知（最强大：可控制方法是否执行、修改返回值等） ====================
    @Around("userServiceOnly()")
    public Object aroundLog(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        long start = System.currentTimeMillis();

        System.out.printf("[注解环绕-前] 开始执行: %s%n", methodName);

        Object result;
        try {
            // 执行目标方法
            result = joinPoint.proceed();
        } catch (Exception e) {
            System.out.printf("[注解环绕-异常] 方法: %s, 异常: %s%n", methodName, e.getMessage());
            // 环绕通知可以吞掉异常或重新抛出
            throw e;
        }

        long elapsed = System.currentTimeMillis() - start;
        System.out.printf("[注解环绕-后] 执行完毕: %s, 耗时: %dms%n", methodName, elapsed);

        return result;
    }
}
