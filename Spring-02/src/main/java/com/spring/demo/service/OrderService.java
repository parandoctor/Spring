package com.spring.demo.service;

/**
 * OrderService — 另一个目标对象，演示切点表达式的包级匹配
 */
public class OrderService {

    public void createOrder(String product, int quantity) {
        System.out.println("  [业务] 执行 createOrder: product=" + product + ", quantity=" + quantity);
    }

    public void cancelOrder(String orderId) {
        System.out.println("  [业务] 执行 cancelOrder: orderId=" + orderId);
    }

    public int getOrderCount() {
        System.out.println("  [业务] 执行 getOrderCount");
        return 42;
    }
}
