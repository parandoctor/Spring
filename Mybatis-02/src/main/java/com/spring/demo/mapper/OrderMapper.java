package com.spring.demo.mapper;

import com.spring.demo.model.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单 Mapper 接口（XML 方式）
 *
 * 演示内容：
 *  1. 多对一关联查询（Order → User）
 *  2. 一对多关联查询（Order → OrderItems）
 */
public interface OrderMapper {

    // ==================== 基础 CRUD ====================
    int insert(Order order);

    Order findById(Long id);

    List<Order> findAll();

    List<Order> findByUserId(@Param("userId") Long userId);

    // ==================== 关联查询（XML 中定义） ====================

    /**
     * 查询订单及其所属用户（多对一 —— 嵌套结果/嵌套查询）
     */
    Order findOrderWithUser(Long id);

    /**
     * 查询订单及其订单项（一对多）
     */
    Order findOrderWithItems(Long id);

    /**
     * 查询订单、用户、订单项（完整关联）
     */
    Order findOrderFull(Long id);
}
