package com.spring.demo.model;

import java.math.BigDecimal;

/**
 * 订单项实体类
 *
 * 关联关系:
 *  - order: 多对一（多个订单项属于一个订单）
 */
public class OrderItem {

    private Long id;
    private Long orderId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;

    /** 多对一: 所属订单 */
    private Order order;

    public OrderItem() {}

    public OrderItem(String productName, Integer quantity, BigDecimal price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    // ==================== getter/setter ====================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    @Override
    public String toString() {
        return "OrderItem{id=" + id + ", productName='" + productName +
               "', quantity=" + quantity + ", price=" + price + "}";
    }
}
