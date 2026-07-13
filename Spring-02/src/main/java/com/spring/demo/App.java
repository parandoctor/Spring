package com.spring.demo;

import com.spring.demo.jdkproxy.JdkLogInvocationHandler;
import com.spring.demo.listener.UserRegisterPublisher;
import com.spring.demo.service.OrderService;
import com.spring.demo.service.UserService;
import com.spring.demo.service.UserServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring-02 AOP 面向切面编程 — 主入口
 *
 * 演示三大主题：
 *   1. AOP 三种实现方式（XML / Spring API 接口 / @AspectJ 注解）
 *   2. JDK 动态代理底层原理
 *   3. Spring 事件监听器
 */
public class App {

    public static void main(String[] args) {

        System.out.println("============================================");
        System.out.println("  Spring-02 AOP 面向切面编程 — 完整演示");
        System.out.println("============================================\n");

        // ================================================================
        // 一、基于 XML 的 AOP 实现
        // ================================================================
        demoXmlAop();

        // ================================================================
        // 二、基于接口（Spring API）的 AOP 实现
        // ================================================================
        demoApiAop();

        // ================================================================
        // 三、基于注解（@AspectJ）的 AOP 实现
        // ================================================================
        demoAnnotationAop();

        // ================================================================
        // 四、JDK 动态代理（手动实现，理解底层原理）
        // ================================================================
        demoJdkProxy();

        // ================================================================
        // 五、Spring 事件监听器
        // ================================================================
        demoListener();
    }

    // ==================== 一、XML 方式 AOP ====================
    private static void demoXmlAop() {
        System.out.println("┌──────────────────────────────────────────┐");
        System.out.println("│  一、XML 方式 AOP                        │");
        System.out.println("└──────────────────────────────────────────┘\n");

        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("applicationContext-aop.xml");

        UserService userService = ctx.getBean("userService", UserService.class);
        OrderService orderService = ctx.getBean("orderService", OrderService.class);

        System.out.println("--- 测试 UserService ---");
        userService.addUser("张三", "zhangsan@spring.com");
        userService.getUser("张三");

        System.out.println("\n--- 测试 OrderService ---");
        orderService.createOrder("iPhone 15", 2);
        orderService.getOrderCount();

        // 测试异常通知
        System.out.println("\n--- 测试异常通知 ---");
        try {
            userService.throwException();
        } catch (Exception e) {
            System.out.println("  [捕获] 异常被业务代码捕获: " + e.getMessage());
        }

        ctx.close();
    }

    // ==================== 二、接口方式 AOP（Spring API） ====================
    private static void demoApiAop() {
        System.out.println("\n\n┌──────────────────────────────────────────┐");
        System.out.println("│  二、接口方式 AOP（Spring API）           │");
        System.out.println("└──────────────────────────────────────────┘\n");

        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("applicationContext-api.xml");

        // 注意：获取的是代理对象，不是原始对象
        UserService userService = ctx.getBean("userServiceProxy", UserService.class);

        System.out.println("代理对象类型: " + userService.getClass().getName());

        userService.addUser("李四", "lisi@spring.com");
        userService.updateUser("李四");
        userService.getUser("李四");

        System.out.println("\n--- 测试异常通知 ---");
        try {
            userService.throwException();
        } catch (Exception e) {
            System.out.println("  [捕获] " + e.getMessage());
        }

        ctx.close();
    }

    // ==================== 三、注解方式 AOP（@AspectJ） ====================
    private static void demoAnnotationAop() {
        System.out.println("\n\n┌──────────────────────────────────────────┐");
        System.out.println("│  三、注解方式 AOP（@AspectJ）            │");
        System.out.println("└──────────────────────────────────────────┘\n");

        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(
                        com.spring.demo.config.AopConfig.class);

        UserService userService = ctx.getBean(UserService.class);
        OrderService orderService = ctx.getBean(OrderService.class);

        System.out.println("UserService 代理类型: " + userService.getClass().getName());
        System.out.println("OrderService 代理类型: " + orderService.getClass().getName());

        System.out.println("\n--- 测试 UserService（环绕通知生效） ---");
        userService.addUser("王五", "wangwu@spring.com");
        userService.getUser("王五");

        System.out.println("\n--- 测试 OrderService（无环绕通知） ---");
        orderService.createOrder("MacBook Pro", 1);

        System.out.println("\n--- 测试异常通知 ---");
        try {
            userService.throwException();
        } catch (Exception e) {
            System.out.println("  [捕获] " + e.getMessage());
        }

        ctx.close();
    }

    // ==================== 四、JDK 动态代理 ====================
    private static void demoJdkProxy() {
        System.out.println("\n\n┌──────────────────────────────────────────┐");
        System.out.println("│  四、JDK 动态代理（底层原理）            │");
        System.out.println("└──────────────────────────────────────────┘\n");

        // 手动使用 JDK 动态代理
        UserService rawService = new UserServiceImpl();
        UserService proxyService = JdkLogInvocationHandler.createProxy(rawService, UserService.class);

        System.out.println("代理对象类型: " + proxyService.getClass().getName());
        System.out.println("代理对象是否为 Proxy 子类: " +
                java.lang.reflect.Proxy.isProxyClass(proxyService.getClass()));

        System.out.println();
        proxyService.addUser("赵六", "zhaoliu@spring.com");
        proxyService.getUser("赵六");

        System.out.println("\n--- 测试异常 ---");
        try {
            proxyService.throwException();
        } catch (Exception e) {
            System.out.println("  [捕获] " + e.getMessage());
        }
    }

    // ==================== 五、Spring 事件监听器 ====================
    private static void demoListener() {
        System.out.println("\n\n┌──────────────────────────────────────────┐");
        System.out.println("│  五、Spring 事件监听器                   │");
        System.out.println("└──────────────────────────────────────────┘\n");

        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(
                        com.spring.demo.config.AopConfig.class);

        UserRegisterPublisher publisher = ctx.getBean(UserRegisterPublisher.class);

        // 模拟用户注册 — 发布事件 → EmailListener + SmsListener 自动响应
        publisher.register("孙七", "sunqi@spring.com");

        ctx.close();
    }
}
