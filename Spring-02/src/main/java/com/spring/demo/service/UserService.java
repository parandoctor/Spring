package com.spring.demo.service;

/**
 * 用户服务接口 — AOP 切面拦截的目标
 * 面向接口编程，便于 JDK 动态代理
 */
public interface UserService {

    void addUser(String name, String email);

    void updateUser(String name);

    void deleteUser(String name);

    String getUser(String name);

    void throwException() throws RuntimeException;
}
