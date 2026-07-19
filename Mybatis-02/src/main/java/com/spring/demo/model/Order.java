package com.spring.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单实体类
 *
 * 关联关系:
 *  - user:       多对一（多个订单属于一个用户）
 *  - orderItems: 一对多（一个订单包含多个订单项）
 */
public class Order {

    private Long id;
    private String orderNo;
    private Long userId;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createTime;

    // ==================== 关联属性 ====================
    /** 多对一: 订单所属的用户 */
    private User user;
    /** 一对多: 订单包含的订单项 */
    private List<OrderItem> orderItems;

    public Order() {}

    public Order(String orderNo, Long userId, BigDecimal totalAmount, String status) {
        this.orderNo = orderNo;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // ==================== getter/setter ====================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }

    @Override
    public String toString() {
        return "Order{id=" + id + ", orderNo='" + orderNo + "', totalAmount=" + totalAmount +
               ", status='" + status + "', user=" + (user != null ? user.getUsername() : "null") +
               ", items=" + (orderItems != null ? orderItems.size() : 0) + "}";
    }
}
