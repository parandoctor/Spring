package com.spring.demo.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JdkLogInvocationHandler — JDK 动态代理核心
 *
 * JDK 动态代理基于接口。通过 Proxy.newProxyInstance() 在运行时生成代理类。
 * Spring AOP 的默认代理方式（目标对象实现了接口时）。
 *
 * 与 CGLIB 对比：
 *   - JDK 代理：基于接口，只能代理接口中的方法
 *   - CGLIB 代理：基于继承，可代理任意 public 方法（final 类/方法除外）
 */
public class JdkLogInvocationHandler implements InvocationHandler {

    /**
     * 目标对象（被代理的真实对象）
     */
    private final Object target;

    public JdkLogInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * 创建代理对象的静态工厂方法
     *
     * @param target      目标对象
     * @param interfaceType 代理的接口类型
     * @param <T>         泛型
     * @return 代理对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Object target, Class<T> interfaceType) {
        return (T) Proxy.newProxyInstance(
                interfaceType.getClassLoader(),      // 类加载器
                new Class<?>[]{interfaceType},        // 代理的接口列表
                new JdkLogInvocationHandler(target)   // InvocationHandler
        );
    }

    /**
     * 代理方法调用的核心入口
     *
     * @param proxy  代理对象自身
     * @param method 被调用的方法
     * @param args   方法参数
     * @return 方法返回值
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        long start = System.currentTimeMillis();

        // ============ 前置增强 ============
        System.out.printf("[JDK代理-前置] 方法: %s, 参数: %s%n",
                methodName,
                args != null ? java.util.Arrays.toString(args) : "null");

        Object result;
        try {
            // ============ 调用目标方法 ============
            result = method.invoke(target, args);

            // ============ 返回增强 ============
            System.out.printf("[JDK代理-返回] 方法: %s, 返回值: %s%n", methodName, result);

        } catch (Exception e) {
            // ============ 异常增强 ============
            System.out.printf("[JDK代理-异常] 方法: %s, 异常: %s%n", methodName, e.getCause().getMessage());
            throw e.getCause();
        } finally {
            // ============ 后置增强（类似 @After / finally） ============
            long elapsed = System.currentTimeMillis() - start;
            System.out.printf("[JDK代理-后置] 方法: %s, 耗时: %dms%n", methodName, elapsed);
        }

        return result;
    }
}
