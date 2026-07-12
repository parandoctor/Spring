package com.spring.demo.beans;

/**
 * Order Bean — 演示构造器注入
 */
public class Order {

    private String orderId;
    private Double amount;
    private String product;

    public Order() {
        System.out.println("[Order] 无参构造器被调用");
    }

    /**
     * 构造器注入 — Spring 通过此构造器创建 Bean 并注入依赖
     */
    public Order(String orderId, Double amount, String product) {
        this.orderId = orderId;
        this.amount = amount;
        this.product = product;
        System.out.println("[Order] 有参构造器 → " + this);
    }

    public String getOrderId() { return orderId; }
    public Double getAmount() { return amount; }
    public String getProduct() { return product; }

    @Override
    public String toString() {
        return "Order{orderId='" + orderId + "', amount=" + amount + ", product='" + product + "'}";
    }
}
