package com.spring.demo.service;

import com.spring.demo.dao.UserDao;
import com.spring.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务——封装业务逻辑，调用 UserDao
 *
 * 这里暂未添加事务注解，每个 DAO 方法独立提交。
 * 适合单条操作场景——每次操作自动提交。
 */
@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 新增用户
     */
    public int createUser(User user) {
        return userDao.insert(user);
    }

    /**
     * 根据 ID 查询
     */
    public User getUserById(Long id) {
        return userDao.findById(id);
    }

    /**
     * 查询所有用户
     */
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    /**
     * 更新用户
     */
    public int updateUser(User user) {
        return userDao.update(user);
    }

    /**
     * 删除用户
     */
    public int deleteUser(Long id) {
        return userDao.deleteById(id);
    }
}
