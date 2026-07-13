package com.spring.demo.aopxml;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * LoggingAspect — 基于 XML 配置的切面类
 *
 * XML 方式不需要任何注解，通过 applicationContext-aop.xml 配置切入点表达式和通知绑定。
 *
 * 通知方法签名需与 XML 中的配置对应：
 *   - 前置/后置通知：可选 JoinPoint 参数
 *   - 环绕通知：必须 ProceedingJoinPoint 参数
 */
public class LoggingAspect {

    // ==================== 前置通知 ====================
    public void beforeLog(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.printf("[XML前置通知] 即将执行方法: %s, 参数: %s%n",
                methodName, java.util.Arrays.toString(args));
    }

    // ==================== 后置通知（最终都会执行，类似 finally） ====================
    public void afterLog(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.printf("[XML后置通知] 方法执行完毕: %s%n", methodName);
    }

    // ==================== 返回通知（成功返回后执行） ====================
    public void afterReturningLog(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.printf("[XML返回通知] 方法: %s, 返回值: %s%n", methodName, result);
    }

    // ==================== 异常通知 ====================
    public void afterThrowingLog(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        System.out.printf("[XML异常通知] 方法: %s, 异常信息: %s%n", methodName, ex.getMessage());
    }

    // ==================== 环绕通知（最强大的通知类型） ====================
    public Object aroundLog(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        long startTime = System.currentTimeMillis();

        System.out.printf("[XML环绕-前] 开始执行: %s%n", methodName);

        // 执行目标方法
        Object result = joinPoint.proceed();

        long elapsed = System.currentTimeMillis() - startTime;
        System.out.printf("[XML环绕-后] 执行完毕: %s, 耗时: %dms%n", methodName, elapsed);

        return result;
    }
}
