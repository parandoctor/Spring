package com.spring.demo.model;

/**
 * 用户实体类。
 *
 * 用于演示 RESTful CRUD 的 JSON 序列化/反序列化。
 * Jackson 默认会序列化所有 public getter 方法的属性。
 */
public class User {

    private Long id;
    private String username;
    private String email;
    private Integer age;

    public User() {}

    public User(Long id, String username, String email, Integer age) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.age = age;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', email='" + email + "', age=" + age + '}';
    }
}
