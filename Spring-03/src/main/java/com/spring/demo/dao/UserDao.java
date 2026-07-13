package com.spring.demo.dao;

import com.spring.demo.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户 DAO——使用 JdbcTemplate 进行数据库操作
 *
 * JdbcTemplate 帮我们做了：
 * 1. 连接获取与释放
 * 2. PreparedStatement 创建
 * 3. ResultSet 遍历与映射
 * 4. SQLException 转换（转为 Spring 的 DataAccessException）
 */
@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    // 构造器注入（Spring 自动装配）
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ==================== 增 ====================

    /**
     * 新增用户
     */
    public int insert(User user) {
        String sql = "INSERT INTO users(username, email, age) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getAge());
    }

    /**
     * 批量新增
     */
    public int[] batchInsert(List<User> users) {
        String sql = "INSERT INTO users(username, email, age) VALUES (?, ?, ?)";
        return jdbcTemplate.batchUpdate(sql, users, users.size(),
                (ps, user) -> {
                    ps.setString(1, user.getUsername());
                    ps.setString(2, user.getEmail());
                    ps.setInt(3, user.getAge());
                });
    }

    // ==================== 删 ====================

    /**
     * 根据 ID 删除
     */
    public int deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // ==================== 改 ====================

    /**
     * 更新用户
     */
    public int update(User user) {
        String sql = "UPDATE users SET username = ?, email = ?, age = ? WHERE id = ?";
        return jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getAge(), user.getId());
    }

    // ==================== 查 ====================

    /**
     * 查询总数
     */
    public int count() {
        String sql = "SELECT COUNT(*) FROM users";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count : 0;
    }

    /**
     * 根据 ID 查询单个用户
     */
    public User findById(Long id) {
        String sql = "SELECT id, username, email, age FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
    }

    /**
     * 查询所有用户
     */
    public List<User> findAll() {
        String sql = "SELECT id, username, email, age FROM users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    /**
     * 根据用户名模糊查询
     */
    public List<User> findByUsernameLike(String keyword) {
        String sql = "SELECT id, username, email, age FROM users WHERE username LIKE ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), "%" + keyword + "%");
    }

    /**
     * 使用 RowMapper 自定义映射（演示手动映射）
     */
    public User findByIdWithRowMapper(Long id) {
        String sql = "SELECT id, username, email, age FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setAge(rs.getInt("age"));
            return user;
        }, id);
    }
}
