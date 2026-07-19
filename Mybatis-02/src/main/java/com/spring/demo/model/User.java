package com.spring.demo.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户实体类
 *
 * 关联关系:
 *  - orders: 一对多（一个用户有多个订单）
 *  - roles:  多对多（一个用户有多个角色，通过 user_role 中间表）
 */
public class User {

    private Long id;
    private String username;
    private String email;
    private Integer age;
    private LocalDateTime createTime;

    // ==================== 关联属性 ====================
    /** 一对多: 用户的订单列表 */
    private List<Order> orders;
    /** 多对多: 用户的角色列表 */
    private List<Role> roles;

    public User() {}

    public User(String username, String email, Integer age) {
        this.username = username;
        this.email = email;
        this.age = age;
    }

    // ==================== getter/setter ====================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }

    public List<Role> getRoles() { return roles; }
    public void setRoles(List<Role> roles) { this.roles = roles; }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', email='" + email +
               "', age=" + age + ", orders=" + (orders != null ? orders.size() : 0) +
               ", roles=" + (roles != null ? roles.size() : 0) + "}";
    }
}
