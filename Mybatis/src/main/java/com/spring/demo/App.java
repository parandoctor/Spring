package com.spring.demo;

import com.spring.demo.config.MyBatisConfig;
import com.spring.demo.model.User;
import com.spring.demo.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;

/**
 * MyBatis 数据库开发 —— 主程序
 *
 * 演示内容：
 * 1. MyBatis 与 Spring 集成配置
 * 2. 查增删改（CRUD）操作
 * 3. 动态 SQL（条件查询 + 选择性更新）
 * 4. 批量操作
 */
public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(MyBatisConfig.class);

        UserService userService = ctx.getBean(UserService.class);

        // ==================== 1. 新增 (INSERT) ====================
        System.out.println("========== 1. 新增用户 ==========");

        User u1 = new User("张三", "zhangsan@example.com", 25);
        User u2 = new User("李四", "lisi@example.com", 30);
        User u3 = new User("王五", "wangwu@example.com", 28);
        User u4 = new User("赵六", "zhaoliu@example.com", 22);

        userService.createUser(u1);
        userService.createUser(u2);
        userService.createUser(u3);
        userService.createUser(u4);

        System.out.println("插入后 ID: " + u1.getId() + ", " + u2.getId()
                + ", " + u3.getId() + ", " + u4.getId());
        System.out.println("当前用户总数: " + userService.getUserCount());

        // ==================== 2. 查询 (SELECT) ====================
        System.out.println("\n========== 2. 查询用户 ==========");

        // 查询所有
        List<User> allUsers = userService.getAllUsers();
        System.out.println("所有用户 (" + allUsers.size() + " 条):");
        allUsers.forEach(u -> System.out.println("  " + u));

        // 根据 ID 查询
        User found = userService.getUserById(u1.getId());
        System.out.println("\n查询 ID=" + u1.getId() + ": " + found);

        // 模糊查询
        System.out.println("\n模糊查询 '张':");
        userService.searchByUsername("张")
                .forEach(u -> System.out.println("  " + u));

        // ==================== 3. 动态 SQL 查询 ====================
        System.out.println("\n========== 3. 动态条件查询 ==========");
        System.out.println("条件: 年龄在 25~30 之间:");
        userService.searchByCondition(null, null, 25, 30)
                .forEach(u -> System.out.println("  " + u));

        System.out.println("\n条件: 邮箱 = lisi@example.com:");
        userService.searchByCondition(null, "lisi@example.com", null, null)
                .forEach(u -> System.out.println("  " + u));

        // ==================== 4. 更新 (UPDATE) ====================
        System.out.println("\n========== 4. 更新用户 ==========");

        // 全字段更新
        User toUpdate = userService.getUserById(u1.getId());
        toUpdate.setUsername("张三丰");
        toUpdate.setAge(100);
        userService.updateUser(toUpdate);
        System.out.println("全字段更新后: " + userService.getUserById(u1.getId()));

        // 动态更新（只更新非空字段）
        User selectiveUpdate = new User();
        selectiveUpdate.setId(u2.getId());
        selectiveUpdate.setEmail("new_lisi@example.com");  // 只改邮箱
        userService.updateUserSelective(selectiveUpdate);
        System.out.println("动态更新后: " + userService.getUserById(u2.getId()));

        // ==================== 5. 批量操作 ====================
        System.out.println("\n========== 5. 批量操作 ==========");

        // 批量新增
        List<User> batchUsers = Arrays.asList(
                new User("批量A", "batchA@test.com", 20),
                new User("批量B", "batchB@test.com", 21)
        );
        userService.batchCreateUsers(batchUsers);
        System.out.println("批量新增后总数: " + userService.getUserCount());

        // 批量删除
        userService.deleteUsers(Arrays.asList(u3.getId(), u4.getId()));
        System.out.println("批量删除后总数: " + userService.getUserCount());

        // ==================== 6. 删除 (DELETE) ====================
        System.out.println("\n========== 6. 单条删除 ==========");
        userService.deleteUser(u1.getId());
        System.out.println("删除 ID=" + u1.getId() + " 后总数: " + userService.getUserCount());

        // 最终结果
        System.out.println("\n========== 最终用户列表 ==========");
        userService.getAllUsers().forEach(u -> System.out.println("  " + u));

        ctx.close();
    }
}
