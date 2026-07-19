package com.spring.demo.service;

import com.spring.demo.mapper.OrderMapper;
import com.spring.demo.mapper.OrderAnnoMapper;
import com.spring.demo.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单服务 —— 演示多对一和一对多关联查询
 */
@Service
public class OrderService {

    private final OrderMapper orderMapper;           // XML 方式
    private final OrderAnnoMapper orderAnnoMapper;   // 注解方式

    public OrderService(OrderMapper orderMapper, OrderAnnoMapper orderAnnoMapper) {
        this.orderMapper = orderMapper;
        this.orderAnnoMapper = orderAnnoMapper;
    }

    // ==================== XML 方式 ====================

    public List<Order> getAllOrders() {
        return orderMapper.findAll();
    }

    /** 多对一：订单及其用户 */
    public Order getOrderWithUser(Long id) {
        return orderMapper.findOrderWithUser(id);
    }

    /** 一对多：订单及其订单项 */
    public Order getOrderWithItems(Long id) {
        return orderMapper.findOrderWithItems(id);
    }

    /** 完整关联：订单 + 用户 + 订单项 */
    public Order getOrderFull(Long id) {
        return orderMapper.findOrderFull(id);
    }

    // ==================== 注解方式 ====================

    public List<Order> getAllOrdersAnno() {
        return orderAnnoMapper.findAll();
    }

    /** 注解方式 - 多对一：订单及其用户 */
    public Order getOrderWithUserAnno(Long id) {
        return orderAnnoMapper.findOrderWithUser(id);
    }

    /** 注解方式 - 一对多：订单及其订单项 */
    public Order getOrderWithItemsAnno(Long id) {
        return orderAnnoMapper.findOrderWithItems(id);
    }
}
