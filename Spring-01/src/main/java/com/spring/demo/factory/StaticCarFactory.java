package com.spring.demo.factory;

/**
 * 静态工厂 — 演示通过静态工厂方法实例化 Bean
 * 适用于：创建复杂对象、第三方库对象、需要额外初始化逻辑的场景
 */
public class StaticCarFactory {

    /**
     * 静态工厂方法 — Spring 通过此方法获取 Bean 实例
     * 配置: <bean id="car" class="...StaticCarFactory" factory-method="createCar"/>
     */
    public static Car createCar() {
        System.out.println("[StaticCarFactory] 静态工厂方法 createCar() 被调用");
        // 模拟复杂初始化逻辑
        Car car = new Car();
        car.setBrand("BMW");
        car.setColor("Black");
        car.setPrice(450000.0);
        return car;
    }
}
