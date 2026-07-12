package com.spring.demo.beans;

/**
 * UserDao Bean — 模拟数据访问层
 */
public class UserDao {

    public UserDao() {
        System.out.println("[UserDao] 无参构造器被调用");
    }

    public void insert(User user) {
        System.out.println("[UserDao] 模拟数据库插入: " + user.getName());
    }
}
