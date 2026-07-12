package com.spring.demo.beans;

/**
 * UserService Bean — 演示 ref 引用注入 (DI 依赖注入)
 */
public class UserService {

    private UserDao userDao;

    public UserService() {
        System.out.println("[UserService] 无参构造器被调用");
    }

    /**
     * Setter 方式注入 UserDao（DI 核心：将依赖从外部注入）
     */
    public void setUserDao(UserDao userDao) {
        System.out.println("[UserService] setUserDao() — 注入依赖");
        this.userDao = userDao;
    }

    public void saveUser(User user) {
        System.out.println("[UserService] 正在保存用户: " + user);
        userDao.insert(user);
    }
}
