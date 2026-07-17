package com.spring.demo.service;

import com.spring.demo.mapper.UserMapper;
import com.spring.demo.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户服务 —— 封装业务逻辑，调用 UserMapper
 *
 * 与 JDBC 的区别：
 * - JDBC: 调用 DAO（自己写实现），需要手动处理 ResultSet 映射
 * - MyBatis: 调用 Mapper（框架生成代理），只需定义接口+XML，自动映射
 */
@Service
public class UserService {

    private final UserMapper userMapper;

    // 构造器注入
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // ==================== 增 ====================

    public int createUser(User user) {
        return userMapper.insert(user);
    }

    @Transactional
    public int batchCreateUsers(List<User> users) {
        return userMapper.batchInsert(users);
    }

    // ==================== 删 ====================

    public int deleteUser(Long id) {
        return userMapper.deleteById(id);
    }

    @Transactional
    public int deleteUsers(List<Long> ids) {
        return userMapper.deleteByIds(ids);
    }

    // ==================== 改 ====================

    public int updateUser(User user) {
        return userMapper.update(user);
    }

    public int updateUserSelective(User user) {
        return userMapper.updateSelective(user);
    }

    // ==================== 查 ====================

    public User getUserById(Long id) {
        return userMapper.findById(id);
    }

    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    public List<User> searchByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    /**
     * 动态条件查询
     */
    public List<User> searchByCondition(String username, String email,
                                         Integer minAge, Integer maxAge) {
        return userMapper.findByCondition(username, email, minAge, maxAge);
    }

    public long getUserCount() {
        return userMapper.count();
    }
}
