package com.spring.demo.mapper;

import com.spring.demo.model.Role;
import org.apache.ibatis.annotations.*;

/**
 * 角色 Mapper 接口（纯注解方式）
 */
public interface RoleAnnoMapper {

    @Select("SELECT r.id, r.role_name, r.description FROM roles r " +
            "INNER JOIN user_role ur ON r.id = ur.role_id WHERE ur.user_id = #{userId}")
    @Results(id = "roleResult", value = {
        @Result(property = "id",          column = "id", id = true),
        @Result(property = "roleName",    column = "role_name"),
        @Result(property = "description", column = "description")
    })
    java.util.List<Role> findByUserId(Long userId);

    @Select("SELECT id, role_name, description FROM roles")
    @ResultMap("roleResult")
    java.util.List<Role> findAll();
}
