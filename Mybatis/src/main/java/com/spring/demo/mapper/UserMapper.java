package com.spring.demo.mapper;

import com.spring.demo.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户 Mapper 接口
 *
 * MyBatis 的核心设计：接口 + XML 映射（或注解）实现 SQL 绑定。
 * 不需要实现类，MyBatis 通过 JDK 动态代理自动生成实现。
 *
 * 方法名与 XML 中的 <select>/<insert>/<update>/<delete> id 一一对应。
 */
public interface UserMapper {

    // ==================== 增 (INSERT) ====================

    /**
     * 新增用户，返回受影响行数。
     * useGeneratedKeys="true" 会将数据库自增 ID 回填到 user.id 中。
     */
    int insert(User user);

    /**
     * 批量新增用户
     */
    int batchInsert(@Param("users") List<User> users);

    // ==================== 删 (DELETE) ====================

    /**
     * 根据 ID 删除用户
     */
    int deleteById(Long id);

    /**
     * 批量删除
     */
    int deleteByIds(@Param("ids") List<Long> ids);

    // ==================== 改 (UPDATE) ====================

    /**
     * 根据 ID 更新用户（全字段更新）
     */
    int update(User user);

    /**
     * 动态更新——只更新非空字段（配合动态 SQL）
     */
    int updateSelective(User user);

    // ==================== 查 (SELECT) ====================

    /**
     * 根据 ID 查询
     */
    User findById(Long id);

    /**
     * 查询所有用户
     */
    List<User> findAll();

    /**
     * 根据用户名模糊查询
     */
    List<User> findByUsername(@Param("username") String username);

    /**
     * 动态条件查询（配合动态 SQL <where> + <if>）
     */
    List<User> findByCondition(@Param("username") String username,
                               @Param("email") String email,
                               @Param("minAge") Integer minAge,
                               @Param("maxAge") Integer maxAge);

    /**
     * 统计用户总数
     */
    long count();
}
