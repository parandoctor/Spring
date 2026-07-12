package com.spring.demo.beans;

/**
 * User Bean — 演示最基本的 Bean 定义
 * 通过 XML 配置注入属性
 */
public class User {

    private Long id;
    private String name;
    private String email;

    // Spring 需要无参构造器
    public User() {
        System.out.println("[User] 无参构造器被调用 — Bean 正在实例化");
    }

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // ==================== Setter 注入 ====================
    public void setId(Long id) {
        System.out.println("[User] setId() → " + id);
        this.id = id;
    }

    public void setName(String name) {
        System.out.println("[User] setName() → " + name);
        this.name = name;
    }

    public void setEmail(String email) {
        System.out.println("[User] setEmail() → " + email);
        this.email = email;
    }

    // ==================== Getter ====================
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}
