package com.spring.demo.factory;

/**
 * 实现 FactoryBean 接口 — Spring 内置的工厂 Bean 机制
 * 适用于：创建代理对象（如 MyBatis Mapper）、需要复杂初始化且想融入 Spring 生命周期的场景
 */
import org.springframework.beans.factory.FactoryBean;

public class CarFactoryBean implements FactoryBean<Car> {

    private String brand;
    private String color;
    private Double price;

    // 通过 Spring 注入工厂参数
    public void setBrand(String brand) { this.brand = brand; }
    public void setColor(String color) { this.color = color; }
    public void setPrice(Double price) { this.price = price; }

    /**
     * 返回工厂生产的 Bean 实例
     */
    @Override
    public Car getObject() throws Exception {
        System.out.println("[CarFactoryBean] getObject() — 创建 Car 实例");
        Car car = new Car();
        car.setBrand(this.brand);
        car.setColor(this.color);
        car.setPrice(this.price);
        return car;
    }

    /**
     * 返回 Bean 的类型
     */
    @Override
    public Class<?> getObjectType() {
        return Car.class;
    }

    /**
     * 是否为单例（默认 true）
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
