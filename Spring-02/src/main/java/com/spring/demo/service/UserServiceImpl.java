package com.spring.demo.service;

/**
 * UserServiceImpl — 目标对象（Target），将被各种 AOP 方式增强
 */
public class UserServiceImpl implements UserService {

    @Override
    public void addUser(String name, String email) {
        System.out.println("  [业务] 执行 addUser: name=" + name + ", email=" + email);
    }

    @Override
    public void updateUser(String name) {
        System.out.println("  [业务] 执行 updateUser: name=" + name);
    }

    @Override
    public void deleteUser(String name) {
        System.out.println("  [业务] 执行 deleteUser: name=" + name);
    }

    @Override
    public String getUser(String name) {
        System.out.println("  [业务] 执行 getUser: name=" + name);
        return "用户信息: " + name;
    }

    @Override
    public void throwException() throws RuntimeException {
        System.out.println("  [业务] 执行 throwException");
        throw new RuntimeException("模拟业务异常！");
    }
}
