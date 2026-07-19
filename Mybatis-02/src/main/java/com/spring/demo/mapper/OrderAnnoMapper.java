package com.spring.demo.mapper;

import com.spring.demo.model.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 订单 Mapper 接口（纯注解方式）
 *
 * 演示内容：
 *  1. @One —— 多对一关联查询
 *  2. @Many —— 一对多关联查询
 */
public interface OrderAnnoMapper {

    @Insert("INSERT INTO orders(order_no, user_id, total_amount, status) VALUES(#{orderNo}, #{userId}, #{totalAmount}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Order order);

    @Select("SELECT id, order_no, user_id, total_amount, status, create_time FROM orders WHERE id = #{id}")
    @Results(id = "orderResult", value = {
        @Result(property = "id",          column = "id", id = true),
        @Result(property = "orderNo",     column = "order_no"),
        @Result(property = "userId",      column = "user_id"),
        @Result(property = "totalAmount", column = "total_amount"),
        @Result(property = "status",      column = "status"),
        @Result(property = "createTime",  column = "create_time")
    })
    Order findById(Long id);

    @Select("SELECT id, order_no, user_id, total_amount, status, create_time FROM orders")
    @ResultMap("orderResult")
    List<Order> findAll();

    /**
     * 根据用户ID查询订单（用于 @Many 嵌套调用）
     */
    @Select("SELECT id, order_no, user_id, total_amount, status, create_time FROM orders WHERE user_id = #{userId}")
    @ResultMap("orderResult")
    List<Order> findByUserId(Long userId);

    /**
     * 多对一: 查询订单及其所属用户（@One 嵌套查询）
     */
    @Select("SELECT id, order_no, user_id, total_amount, status, create_time FROM orders WHERE id = #{id}")
    @Results(id = "orderWithUser", value = {
        @Result(property = "id",          column = "id", id = true),
        @Result(property = "orderNo",     column = "order_no"),
        @Result(property = "userId",      column = "user_id"),
        @Result(property = "totalAmount", column = "total_amount"),
        @Result(property = "status",      column = "status"),
        @Result(property = "createTime",  column = "create_time"),
        @Result(property = "user",        column = "user_id",
                one = @One(select = "com.spring.demo.mapper.UserAnnoMapper.findById",
                           fetchType = FetchType.LAZY))
    })
    Order findOrderWithUser(Long id);

    /**
     * 一对多: 查询订单及其订单项（@Many 嵌套查询）
     */
    @Select("SELECT id, order_no, user_id, total_amount, status, create_time FROM orders WHERE id = #{id}")
    @Results(id = "orderWithItems", value = {
        @Result(property = "id",          column = "id", id = true),
        @Result(property = "orderNo",     column = "order_no"),
        @Result(property = "userId",      column = "user_id"),
        @Result(property = "totalAmount", column = "total_amount"),
        @Result(property = "status",      column = "status"),
        @Result(property = "createTime",  column = "create_time"),
        @Result(property = "orderItems",  column = "id",
                many = @Many(select = "com.spring.demo.mapper.OrderItemAnnoMapper.findByOrderId",
                             fetchType = FetchType.LAZY))
    })
    Order findOrderWithItems(Long id);
}
