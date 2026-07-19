package com.spring.demo.service;

import com.spring.demo.mapper.UserMapper;
import com.spring.demo.mapper.UserAnnoMapper;
import com.spring.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务 —— 同时演示 XML 方式和注解方式的 Mapper 调用
 */
@Service
public class UserService {

    private final UserMapper userMapper;           // XML 方式
    private final UserAnnoMapper userAnnoMapper;   // 注解方式

    public UserService(UserMapper userMapper, UserAnnoMapper userAnnoMapper) {
        this.userMapper = userMapper;
        this.userAnnoMapper = userAnnoMapper;
    }

    // ==================== XML 方式调用 ====================

    public User getUserById(Long id) {
        return userMapper.findById(id);
    }

    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    public int createUser(User user) {
        return userMapper.insert(user);
    }

    /** 一对多：查询用户及其订单（嵌套结果） */
    public User getUserWithOrders(Long id) {
        return userMapper.findUserWithOrders(id);
    }

    /** 多对多：查询用户及其角色（嵌套结果） */
    public User getUserWithRoles(Long id) {
        return userMapper.findUserWithRoles(id);
    }

    /** 延迟加载：嵌套查询方式 */
    public User getUserWithOrdersAndRoles(Long id) {
        return userMapper.findUserWithOrdersAndRoles(id);
    }

    /** 根据角色ID查用户 */
    public List<User> getUsersByRoleId(Long roleId) {
        return userMapper.findUsersByRoleId(roleId);
    }

    // ==================== 注解方式调用 ====================

    public User getUserByIdAnno(Long id) {
        return userAnnoMapper.findById(id);
    }

    public List<User> getAllUsersAnno() {
        return userAnnoMapper.findAll();
    }

    public int createUserAnno(User user) {
        return userAnnoMapper.insert(user);
    }

    /** 注解方式 - 一对多 */
    public User getUserWithOrdersAnno(Long id) {
        return userAnnoMapper.findUserWithOrders(id);
    }

    /** 注解方式 - 多对多 */
    public User getUserWithRolesAnno(Long id) {
        return userAnnoMapper.findUserWithRoles(id);
    }
}
