package com.spring.demo;

import com.spring.demo.config.MyBatisAdvanceConfig;
import com.spring.demo.model.Order;
import com.spring.demo.model.User;
import com.spring.demo.service.OrderService;
import com.spring.demo.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * MyBatis 进阶开发 —— 主程序
 *
 * 演示内容：
 *  1. 关联映射：一对多（User → Orders）、多对一（Order → User）、多对多（User ↔ Roles）
 *  2. 注解开发：@Select/@Insert/@Results/@One/@Many
 *  3. XML vs 注解 对比调用
 *  4. 延迟加载验证
 */
public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(MyBatisAdvanceConfig.class);

        UserService userService = ctx.getBean(UserService.class);
        OrderService orderService = ctx.getBean(OrderService.class);

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║     MyBatis 进阶 —— 关联映射 + 注解开发    ║");
        System.out.println("╚══════════════════════════════════════════╝");

        // ==================== 一、一对多（User → Orders）====================
        System.out.println("\n━━━ 一、一对多关联映射（User → Orders）━━━");

        System.out.println("\n--- XML 方式（嵌套结果映射） ---");
        User user1 = userService.getUserWithOrders(1L);
        System.out.println("用户: " + user1.getUsername() + " (ID=" + user1.getId() + ")");
        if (user1.getOrders() != null) {
            user1.getOrders().forEach(o ->
                System.out.println("  └─ 订单: " + o.getOrderNo() +
                        " | 金额: ¥" + o.getTotalAmount() +
                        " | 状态: " + o.getStatus()));
        }

        System.out.println("\n--- 注解方式（@Many） ---");
        User user1Anno = userService.getUserWithOrdersAnno(1L);
        System.out.println("用户: " + user1Anno.getUsername());
        if (user1Anno.getOrders() != null) {
            user1Anno.getOrders().forEach(o ->
                System.out.println("  └─ 订单: " + o.getOrderNo() + " | ¥" + o.getTotalAmount()));
        }

        // ==================== 二、多对一（Order → User）====================
        System.out.println("\n━━━ 二、多对一关联映射（Order → User）━━━");

        System.out.println("\n--- XML 方式（嵌套结果映射） ---");
        Order order1 = orderService.getOrderWithUser(1L);
        System.out.println("订单: " + order1.getOrderNo() + " | ¥" + order1.getTotalAmount());
        if (order1.getUser() != null) {
            System.out.println("  └─ 所属用户: " + order1.getUser().getUsername() +
                    " (" + order1.getUser().getEmail() + ")");
        }

        System.out.println("\n--- 注解方式（@One） ---");
        Order order1Anno = orderService.getOrderWithUserAnno(1L);
        System.out.println("订单: " + order1Anno.getOrderNo());
        if (order1Anno.getUser() != null) {
            System.out.println("  └─ 所属用户: " + order1Anno.getUser().getUsername());
        }

        // ==================== 三、一对多（Order → OrderItems）====================
        System.out.println("\n━━━ 三、一对多关联映射（Order → OrderItems）━━━");

        System.out.println("\n--- XML 方式（嵌套结果映射） ---");
        Order orderWithItems = orderService.getOrderWithItems(1L);
        System.out.println("订单: " + orderWithItems.getOrderNo());
        if (orderWithItems.getOrderItems() != null) {
            orderWithItems.getOrderItems().forEach(item ->
                System.out.println("  └─ 商品: " + item.getProductName() +
                        " | 数量: " + item.getQuantity() +
                        " | 单价: ¥" + item.getPrice()));
        }

        System.out.println("\n--- 注解方式（@Many） ---");
        Order orderWithItemsAnno = orderService.getOrderWithItemsAnno(1L);
        System.out.println("订单: " + orderWithItemsAnno.getOrderNo());
        if (orderWithItemsAnno.getOrderItems() != null) {
            orderWithItemsAnno.getOrderItems().forEach(item ->
                System.out.println("  └─ 商品: " + item.getProductName() + " | ¥" + item.getPrice()));
        }

        // ==================== 四、多对多（User ↔ Roles）====================
        System.out.println("\n━━━ 四、多对多关联映射（User ↔ Roles）━━━");

        System.out.println("\n--- XML 方式（嵌套结果映射） ---");
        User userWithRoles = userService.getUserWithRoles(1L);
        System.out.println("用户: " + userWithRoles.getUsername());
        if (userWithRoles.getRoles() != null) {
            userWithRoles.getRoles().forEach(r ->
                System.out.println("  └─ 角色: " + r.getRoleName() +
                        " (" + r.getDescription() + ")"));
        }

        System.out.println("\n--- 注解方式（@Many） ---");
        User userWithRolesAnno = userService.getUserWithRolesAnno(1L);
        System.out.println("用户: " + userWithRolesAnno.getUsername());
        if (userWithRolesAnno.getRoles() != null) {
            userWithRolesAnno.getRoles().forEach(r ->
                System.out.println("  └─ 角色: " + r.getRoleName()));
        }

        // ==================== 五、延迟加载演示 ====================
        System.out.println("\n━━━ 五、延迟加载演示（嵌套查询）━━━");
        System.out.println("注意观察控制台 SQL 日志：关联数据只在访问时才加载");

        User lazyUser = userService.getUserWithOrdersAndRoles(2L);
        System.out.println(">>> 获取用户基本信息（此时不应触发订单和角色查询）");
        System.out.println("用户: " + lazyUser.getUsername());

        System.out.println(">>> 第一次访问订单（此时触发订单查询）");
        if (lazyUser.getOrders() != null) {
            System.out.println("订单数: " + lazyUser.getOrders().size());
        }

        System.out.println(">>> 第一次访问角色（此时触发角色查询）");
        if (lazyUser.getRoles() != null) {
            lazyUser.getRoles().forEach(r -> System.out.println("  └─ " + r.getRoleName()));
        }

        // ==================== 六、完整关联 ====================
        System.out.println("\n━━━ 六、完整关联（订单 + 用户 + 订单项）━━━");
        Order fullOrder = orderService.getOrderFull(4L);
        System.out.println("订单: " + fullOrder.getOrderNo() + " | ¥" + fullOrder.getTotalAmount());
        if (fullOrder.getUser() != null) {
            System.out.println("  └─ 用户: " + fullOrder.getUser().getUsername());
        }
        if (fullOrder.getOrderItems() != null) {
            fullOrder.getOrderItems().forEach(item ->
                System.out.println("  └─ 商品: " + item.getProductName() +
                        " × " + item.getQuantity() + " = ¥" +
                        item.getPrice().multiply(new java.math.BigDecimal(item.getQuantity()))));
        }

        // ==================== 七、注解方式基础 CRUD ====================
        System.out.println("\n━━━ 七、注解方式 CRUD 验证 ━━━");
        List<User> allUsersAnno = userService.getAllUsersAnno();
        System.out.println("注解方式查询所有用户（共 " + allUsersAnno.size() + " 条）:");
        allUsersAnno.forEach(u -> System.out.println("  " + u));

        System.out.println("\n✅ 所有演示完成！");
        ctx.close();
    }
}
