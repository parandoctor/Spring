package com.spring.demo.mapper;

import com.spring.demo.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * 用户 Mapper 接口（纯注解方式）
 *
 * 演示内容：
 *  1. @Select / @Insert / @Update / @Delete —— 四大注解
 *  2. @Results / @Result —— 结果映射
 *  3. @One —— 一对一/多对一关联查询
 *  4. @Many —— 一对多/多对多关联查询
 *  5. @ResultMap —— 复用已定义的映射
 */
public interface UserAnnoMapper {

    // ==================== 基础操作 ====================

    @Insert("INSERT INTO users(username, email, age) VALUES(#{username}, #{email}, #{age})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Select("SELECT id, username, email, age, create_time FROM users WHERE id = #{id}")
    @Results(id = "userResult", value = {
        @Result(property = "id",         column = "id", id = true),
        @Result(property = "username",   column = "username"),
        @Result(property = "email",      column = "email"),
        @Result(property = "age",        column = "age"),
        @Result(property = "createTime", column = "create_time")
    })
    User findById(Long id);

    @Select("SELECT id, username, email, age, create_time FROM users")
    @ResultMap("userResult")
    List<User> findAll();

    @Update("UPDATE users SET username=#{username}, email=#{email}, age=#{age} WHERE id=#{id}")
    int update(User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteById(Long id);

    // ==================== 关联查询 ====================

    /**
     * 一对多: 查询用户及其订单（@Many + 嵌套 select）
     * fetchType = FetchType.LAZY 表示延迟加载
     */
    @Select("SELECT id, username, email, age, create_time FROM users WHERE id = #{id}")
    @Results(id = "userWithOrders", value = {
        @Result(property = "id",         column = "id", id = true),
        @Result(property = "username",   column = "username"),
        @Result(property = "email",      column = "email"),
        @Result(property = "age",        column = "age"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "orders",     column = "id",
                many = @Many(select = "com.spring.demo.mapper.OrderAnnoMapper.findByUserId",
                             fetchType = FetchType.LAZY))
    })
    User findUserWithOrders(Long id);

    /**
     * 多对多: 查询用户及其角色（@Many + 嵌套 select）
     */
    @Select("SELECT id, username, email, age, create_time FROM users WHERE id = #{id}")
    @Results(id = "userWithRoles", value = {
        @Result(property = "id",         column = "id", id = true),
        @Result(property = "username",   column = "username"),
        @Result(property = "email",      column = "email"),
        @Result(property = "age",        column = "age"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "roles",      column = "id",
                many = @Many(select = "com.spring.demo.mapper.RoleAnnoMapper.findByUserId",
                             fetchType = FetchType.LAZY))
    })
    User findUserWithRoles(Long id);
}
