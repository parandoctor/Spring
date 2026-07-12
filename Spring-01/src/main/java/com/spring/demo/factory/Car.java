package com.spring.demo.factory;

/**
 * Car Bean — 由工厂方法创建
 */
public class Car {

    private String brand;
    private String color;
    private Double price;

    public Car() {
        System.out.println("[Car] 无参构造器被调用");
    }

    public void setBrand(String brand) { this.brand = brand; }
    public void setColor(String color) { this.color = color; }
    public void setPrice(Double price) { this.price = price; }

    public String getBrand() { return brand; }
    public String getColor() { return color; }
    public Double getPrice() { return price; }

    @Override
    public String toString() {
        return "Car{brand='" + brand + "', color='" + color + "', price=" + price + "}";
    }
}
