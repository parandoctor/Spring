package com.spring.demo.mapper;

import com.spring.demo.model.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 订单项 Mapper 接口（纯注解方式）
 */
public interface OrderItemAnnoMapper {

    @Select("SELECT id, order_id, product_name, quantity, price FROM order_items WHERE order_id = #{orderId}")
    @Results(id = "orderItemResult", value = {
        @Result(property = "id",          column = "id", id = true),
        @Result(property = "orderId",     column = "order_id"),
        @Result(property = "productName", column = "product_name"),
        @Result(property = "quantity",    column = "quantity"),
        @Result(property = "price",       column = "price")
    })
    List<OrderItem> findByOrderId(Long orderId);
}
