package com.spring.demo.factory;

/**
 * 实例工厂 — 演示通过实例工厂方法实例化 Bean
 * 工厂自身也是一个 Bean，然后通过其实例方法创建目标 Bean
 * 配置: 先定义工厂 Bean，再通过 factory-bean + factory-method 引用
 */
public class InstanceCarFactory {

    public InstanceCarFactory() {
        System.out.println("[InstanceCarFactory] 工厂实例已创建");
    }

    /**
     * 实例工厂方法
     */
    public Car createCar() {
        System.out.println("[InstanceCarFactory] 实例工厂方法 createCar() 被调用");
        Car car = new Car();
        car.setBrand("Audi");
        car.setColor("White");
        car.setPrice(380000.0);
        return car;
    }
}
