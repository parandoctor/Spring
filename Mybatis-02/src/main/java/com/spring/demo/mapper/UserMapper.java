package com.spring.demo.mapper;

import com.spring.demo.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户 Mapper 接口（XML 方式）
 *
 * 演示内容：
 *  1. 一对多关联查询（User → Orders）
 *  2. 多对多关联查询（User → Roles）
 *  3. 延迟加载 vs 立即加载
 */
public interface UserMapper {

    // ==================== 基础 CRUD ====================
    int insert(User user);

    User findById(Long id);

    List<User> findAll();

    // ==================== 关联查询（XML 中定义） ====================

    /**
     * 查询用户及其所有订单（一对多 —— 嵌套结果映射）
     */
    User findUserWithOrders(Long id);

    /**
     * 查询用户及其所有角色（多对多 —— 嵌套结果映射）
     */
    User findUserWithRoles(Long id);

    /**
     * 查询用户、订单、角色（嵌套查询 —— 分步加载，支持延迟加载）
     */
    User findUserWithOrdersAndRoles(Long id);

    /**
     * 根据角色ID查询所有用户（反向多对多）
     */
    List<User> findUsersByRoleId(@Param("roleId") Long roleId);
}
