package com.spring.demo.model;

/**
 * 角色实体类
 *
 * 关联关系:
 *  - users: 多对多（一个角色可分配给多个用户，通过 user_role 中间表）
 */
public class Role {

    private Long id;
    private String roleName;
    private String description;

    public Role() {}

    public Role(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }

    // ==================== getter/setter ====================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Role{id=" + id + ", roleName='" + roleName + "', description='" + description + "'}";
    }
}
