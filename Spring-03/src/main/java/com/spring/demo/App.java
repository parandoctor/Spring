package com.spring.demo;

import com.spring.demo.config.DataSourceConfig;
import com.spring.demo.config.TransactionConfig;
import com.spring.demo.model.Account;
import com.spring.demo.model.User;
import com.spring.demo.service.AccountService;
import com.spring.demo.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.List;

/**
 * Spring-03 主程序
 *
 * 演示：
 * 1. JdbcTemplate CRUD 操作
 * 2. 声明式事务（转账场景）
 */
public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(DataSourceConfig.class, TransactionConfig.class);

        // ==================== 1. JDBC CRUD 演示 ====================
        System.out.println("========== JDBC CRUD 演示 ==========");

        UserService userService = ctx.getBean(UserService.class);

        // 新增
        User user1 = new User(null, "张三", "zhangsan@example.com", 25);
        userService.createUser(user1);
        System.out.println("新增用户: " + user1.getUsername());

        User user2 = new User(null, "李四", "lisi@example.com", 30);
        userService.createUser(user2);
        System.out.println("新增用户: " + user2.getUsername());

        // 查询所有
        List<User> allUsers = userService.getAllUsers();
        System.out.println("\n所有用户 (" + allUsers.size() + " 条):");
        allUsers.forEach(u -> System.out.println("  " + u));

        // 根据 ID 查询
        if (!allUsers.isEmpty()) {
            User first = allUsers.get(0);
            User found = userService.getUserById(first.getId());
            System.out.println("\n查询 ID=" + first.getId() + ": " + found);
        }

        System.out.println();

        // ==================== 2. 事务管理演示 ====================
        System.out.println("========== 事务管理演示 ==========");

        AccountService accountService = ctx.getBean(AccountService.class);

        // 初始化账户表
        accountService.initAccounts();

        // 转账前查询
        Account fromBefore = accountService.getAccount("张三");
        Account toBefore = accountService.getAccount("李四");
        System.out.println("转账前: " + fromBefore);
        System.out.println("转账前: " + toBefore);

        // 执行转账：张三 → 李四 500.00
        try {
            accountService.transfer("张三", "李四", new BigDecimal("500.00"));
        } catch (Exception e) {
            System.err.println("转账异常: " + e.getMessage());
        }

        // 转账后查询
        Account fromAfter = accountService.getAccount("张三");
        Account toAfter = accountService.getAccount("李四");
        System.out.println("转账后: " + fromAfter);
        System.out.println("转账后: " + toAfter);

        ctx.close();
    }
}
