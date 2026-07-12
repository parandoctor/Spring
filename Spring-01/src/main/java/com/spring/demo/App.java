package com.spring.demo;

import com.spring.demo.beans.Order;
import com.spring.demo.beans.User;
import com.spring.demo.beans.UserDao;
import com.spring.demo.beans.UserService;
import com.spring.demo.config.AppConfig;
import com.spring.demo.factory.Car;
import com.spring.demo.lifecycle.LifecycleBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring-01 入门程序主入口
 *
 * 演示核心知识点：
 *   1. ApplicationContext 的两种创建方式（XML / 注解）
 *   2. Bean 的获取与使用
 *   3. Setter 注入 vs 构造器注入
 *   4. DI 依赖注入（ref 引用）
 *   5. Bean 作用域（singleton vs prototype）
 *   6. 工厂方式实例化（静态工厂 / 实例工厂 / FactoryBean）
 *   7. Bean 生命周期
 */
public class App {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("   Spring-01 入门程序 — 核心容器演示");
        System.out.println("========================================\n");

        // ============================================================
        // 一、XML 方式创建 ApplicationContext（经典方式）
        // ClassPathXmlApplicationContext 从类路径加载 XML 配置
        // ============================================================
        System.out.println(">>> 正在加载 applicationContext.xml 并初始化 IoC 容器...\n");

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("\n>>> IoC 容器初始化完成！\n");

        // -------------------- 1. 基本 Bean（Setter 注入） --------------------
        System.out.println("┌──────────────────────────────────────┐");
        System.out.println("│  1. 基本 Bean — Setter 注入          │");
        System.out.println("└──────────────────────────────────────┘");
        User user = context.getBean("user", User.class);
        System.out.println("  获取到: " + user + "\n");

        // -------------------- 2. 构造器注入 --------------------
        System.out.println("┌──────────────────────────────────────┐");
        System.out.println("│  2. 构造器注入                        │");
        System.out.println("└──────────────────────────────────────┘");
        Order order = context.getBean("order", Order.class);
        System.out.println("  获取到: " + order + "\n");

        // -------------------- 3. DI 依赖注入（ref 引用） --------------------
        System.out.println("┌──────────────────────────────────────┐");
        System.out.println("│  3. DI 依赖注入（ref 引用）           │");
        System.out.println("└──────────────────────────────────────┘");
        UserService userService = context.getBean("userService", UserService.class);
        userService.saveUser(user);
        System.out.println();

        // -------------------- 4. Bean 作用域 --------------------
        System.out.println("┌──────────────────────────────────────┐");
        System.out.println("│  4. Bean 作用域                       │");
        System.out.println("└──────────────────────────────────────┘");
        User s1 = context.getBean("singletonBean", User.class);
        User s2 = context.getBean("singletonBean", User.class);
        System.out.println("  singleton: s1 == s2 → " + (s1 == s2) + "（同一实例）");

        User p1 = context.getBean("prototypeBean", User.class);
        User p2 = context.getBean("prototypeBean", User.class);
        System.out.println("  prototype: p1 == p2 → " + (p1 == p2) + "（不同实例）\n");

        // -------------------- 5. 工厂方式实例化 --------------------
        System.out.println("┌──────────────────────────────────────┐");
        System.out.println("│  5. 工厂方式实例化                    │");
        System.out.println("└──────────────────────────────────────┘");
        Car car1 = context.getBean("carByStaticFactory", Car.class);
        System.out.println("  静态工厂 → " + car1);

        Car car2 = context.getBean("carByInstanceFactory", Car.class);
        System.out.println("  实例工厂 → " + car2);

        // FactoryBean：getBean("carByFactoryBean") 获取的是 FactoryBean 生产的 Car，
        // 若要获取 FactoryBean 本身，需加 "&" 前缀 → getBean("&carByFactoryBean")
        Car car3 = context.getBean("carByFactoryBean", Car.class);
        System.out.println("  FactoryBean → " + car3 + "\n");

        // -------------------- 6. Bean 生命周期 --------------------
        System.out.println("┌──────────────────────────────────────┐");
        System.out.println("│  6. Bean 生命周期（已在启动时执行）    │");
        System.out.println("└──────────────────────────────────────┘");
        LifecycleBean lb = context.getBean("lifecycleBean", LifecycleBean.class);
        System.out.println("  获取 LifecycleBean: data=" + lb.getData() + "\n");

        // ============================================================
        // 二、注解方式创建 ApplicationContext（Spring Boot 风格）
        // ============================================================
        System.out.println(">>> 正在通过 Java Config 创建注解容器...\n");

        ApplicationContext annoContext = new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("\n>>> 注解容器初始化完成！\n");

        UserDao dao = annoContext.getBean(UserDao.class);
        System.out.println("  注解容器 → UserDao: " + dao);

        User proto1 = annoContext.getBean("prototypeUser", User.class);
        User proto2 = annoContext.getBean("prototypeUser", User.class);
        System.out.println("  注解容器 → prototypeUser: proto1 == proto2 → " + (proto1 == proto2) + "\n");

        // ============================================================
        // 三、显示容器中所有 Bean 的名称
        // ============================================================
        System.out.println("┌──────────────────────────────────────┐");
        System.out.println("│  XML 容器中所有 Bean 名称:            │");
        System.out.println("└──────────────────────────────────────┘");
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println("    → " + name);
        }

        // 关闭容器以触发 destroy 回调
        System.out.println("\n>>> 关闭容器，触发销毁回调...\n");
        ((ClassPathXmlApplicationContext) context).close();
        ((AnnotationConfigApplicationContext) annoContext).close();

        System.out.println("\n========================================");
        System.out.println("   入门程序演示完毕！");
        System.out.println("========================================");
    }
}
