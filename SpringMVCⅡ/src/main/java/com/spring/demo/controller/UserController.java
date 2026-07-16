package com.spring.demo.controller;

import com.spring.demo.model.ApiResult;
import com.spring.demo.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户管理 RESTful API 控制器。
 *
 * RESTful 规范要点：
 *   - URL 使用复数名词（/api/users 而非 /api/user）
 *   - HTTP 方法对应 CRUD：GET=查, POST=增, PUT=改, DELETE=删
 *   - 无状态：每次请求包含全部认证信息（Token 在 Header 中）
 *   - 统一响应格式：使用 ApiResult 封装
 *
 * 注解说明：
 *   @RestController = @Controller + @ResponseBody（所有方法返回 JSON）
 *   @RequestMapping("/api/users") 类级别 URL 前缀
 *   @PathVariable  绑定 URL 路径变量
 *   @RequestBody    将请求体 JSON → Java 对象
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    /** 模拟数据库（生产环境应使用真正的数据库） */
    private final Map<Long, User> userMap = new ConcurrentHashMap<>();
    private long nextId = 1;

    // 初始化模拟数据
    {
        userMap.put(1L, new User(1L, "张三", "zhangsan@example.com", 25));
        userMap.put(2L, new User(2L, "李四", "lisi@example.com", 30));
        nextId = 3;
    }

    // ==================== 查询：GET ====================

    /**
     * GET /api/users — 查询全部用户列表
     *
     * 使用 Postman 测试：
     *   GET http://localhost:8080/spring-mvc-advanced/api/users
     *   Header: Authorization: demo-token-123
     */
    @GetMapping
    public ApiResult<List<User>> list() {
        List<User> users = new ArrayList<>(userMap.values());
        return ApiResult.success(users);
    }

    /**
     * GET /api/users/{id} — 按 ID 查询单个用户
     *
     * @param id 路径参数，@PathVariable 自动绑定
     *
     * 使用 Postman 测试：
     *   GET http://localhost:8080/spring-mvc-advanced/api/users/1
     *   Header: Authorization: demo-token-123
     */
    @GetMapping("/{id}")
    public ApiResult<User> getById(@PathVariable Long id) {
        User user = userMap.get(id);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在: id=" + id);
        }
        return ApiResult.success(user);
    }

    // ==================== 新增：POST ====================

    /**
     * POST /api/users — 创建用户
     *
     * @param user 请求体中的 JSON 自动反序列化为 User 对象（@RequestBody）
     * @return 创建成功的用户（含自动生成的 ID）
     *
     * 使用 Postman 测试：
     *   POST http://localhost:8080/spring-mvc-advanced/api/users
     *   Header: Authorization: demo-token-123
     *   Header: Content-Type: application/json
     *   Body (raw JSON):
     *   {
     *     "username": "王五",
     *     "email": "wangwu@example.com",
     *     "age": 28
     *   }
     */
    @PostMapping
    public ApiResult<User> create(@RequestBody User user) {
        user.setId(nextId++);
        userMap.put(user.getId(), user);
        System.out.println("[UserController] 创建用户: " + user);
        return ApiResult.success("用户创建成功", user);
    }

    // ==================== 更新：PUT ====================

    /**
     * PUT /api/users/{id} — 全量更新用户
     *
     * RESTful 语义：PUT 是全量替换，PATCH 是局部更新。
     *
     * 使用 Postman 测试：
     *   PUT http://localhost:8080/spring-mvc-advanced/api/users/1
     *   Header: Authorization: demo-token-123
     *   Body: { "username": "张三三", "email": "new@example.com", "age": 26 }
     */
    @PutMapping("/{id}")
    public ApiResult<User> update(@PathVariable Long id, @RequestBody User user) {
        if (!userMap.containsKey(id)) {
            throw new IllegalArgumentException("用户不存在: id=" + id);
        }
        user.setId(id);
        userMap.put(id, user);
        System.out.println("[UserController] 更新用户: " + user);
        return ApiResult.success("用户更新成功", user);
    }

    // ==================== 删除：DELETE ====================

    /**
     * DELETE /api/users/{id} — 删除用户
     *
     * 使用 Postman 测试：
     *   DELETE http://localhost:8080/spring-mvc-advanced/api/users/1
     *   Header: Authorization: demo-token-123
     */
    @DeleteMapping("/{id}")
    public ApiResult<Void> delete(@PathVariable Long id) {
        User removed = userMap.remove(id);
        if (removed == null) {
            throw new IllegalArgumentException("用户不存在: id=" + id);
        }
        System.out.println("[UserController] 删除用户: " + removed);
        return ApiResult.success("用户删除成功");
    }

    // ==================== 局部更新：PATCH ====================

    /**
     * PATCH /api/users/{id} — 局部更新用户
     *
     * PATCH 只更新提供的字段，未提供的字段保留原值。
     *
     * 使用 Postman 测试：
     *   PATCH http://localhost:8080/spring-mvc-advanced/api/users/2
     *   Body: { "age": 31 }
     */
    @PatchMapping("/{id}")
    public ApiResult<User> patch(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        User user = userMap.get(id);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在: id=" + id);
        }

        // 只更新传入的字段
        if (updates.containsKey("username")) {
            user.setUsername((String) updates.get("username"));
        }
        if (updates.containsKey("email")) {
            user.setEmail((String) updates.get("email"));
        }
        if (updates.containsKey("age")) {
            user.setAge((Integer) updates.get("age"));
        }

        return ApiResult.success("局部更新成功", user);
    }

    // ==================== 登录接口（不受拦截器限制） ====================

    /**
     * POST /api/users/login — 模拟登录
     *
     * 此接口在 WebConfig 中被 excludePathPatterns 排除，无需 Token。
     *
     * 使用 Postman 测试：
     *   POST http://localhost:8080/spring-mvc-advanced/api/users/login
     *   Body: { "username": "admin", "password": "123456" }
     */
    @PostMapping("/login")
    public ApiResult<Map<String, String>> login(@RequestBody Map<String, String> loginInfo) {
        String username = loginInfo.get("username");
        String password = loginInfo.get("password");

        // 模拟用户名密码验证
        if ("admin".equals(username) && "123456".equals(password)) {
            // 实际项目应返回 JWT Token
            return ApiResult.success("登录成功", Map.of("token", "demo-token-123"));
        }
        throw new IllegalArgumentException("用户名或密码错误");
    }
}
