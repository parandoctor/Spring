-- =============================================
-- MyBatis 进阶演示项目 —— 数据库初始化脚本
-- 数据库名: mybatis_advance_demo
-- 
-- 表关系:
--   users  1 ──── N  orders        (一对多)
--   orders 1 ──── N  order_items   (一对多)
--   users  N ──── M  roles          (多对多, 通过 user_role)
-- =============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS mybatis_advance_demo
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE mybatis_advance_demo;

-- ==================== 删除旧表（按外键依赖顺序） ====================
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;

-- ==================== 1. 用户表 ====================
CREATE TABLE users (
    id          BIGINT       AUTO_INCREMENT PRIMARY KEY  COMMENT '主键ID',
    username    VARCHAR(50)  NOT NULL                     COMMENT '用户名',
    email       VARCHAR(100)                              COMMENT '邮箱',
    age         INT                                       COMMENT '年龄',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP    COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ==================== 2. 订单表（多对一: 多个订单 → 一个用户） ====================
CREATE TABLE orders (
    id           BIGINT         AUTO_INCREMENT PRIMARY KEY  COMMENT '主键ID',
    order_no     VARCHAR(50)    NOT NULL                     COMMENT '订单编号',
    user_id      BIGINT         NOT NULL                     COMMENT '用户ID（外键）',
    total_amount DECIMAL(10,2)                               COMMENT '订单总金额',
    status       VARCHAR(20)    DEFAULT 'PENDING'            COMMENT '状态: PENDING/SHIPPED/COMPLETED',
    create_time  DATETIME       DEFAULT CURRENT_TIMESTAMP    COMMENT '创建时间',
    FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ==================== 3. 订单项表（多对一: 多个订单项 → 一个订单） ====================
CREATE TABLE order_items (
    id           BIGINT         AUTO_INCREMENT PRIMARY KEY  COMMENT '主键ID',
    order_id     BIGINT         NOT NULL                     COMMENT '订单ID（外键）',
    product_name VARCHAR(100)   NOT NULL                     COMMENT '商品名称',
    quantity     INT            NOT NULL                     COMMENT '数量',
    price        DECIMAL(10,2)  NOT NULL                     COMMENT '单价',
    FOREIGN KEY (order_id) REFERENCES orders(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单项表';

-- ==================== 4. 角色表 ====================
CREATE TABLE roles (
    id          BIGINT       AUTO_INCREMENT PRIMARY KEY  COMMENT '主键ID',
    role_name   VARCHAR(50)  NOT NULL                     COMMENT '角色名称',
    description VARCHAR(200)                              COMMENT '角色描述'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ==================== 5. 用户-角色关联表（多对多中间表） ====================
CREATE TABLE user_role (
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- ==================== 插入测试数据 ====================
-- 用户
INSERT INTO users(username, email, age) VALUES
    ('张三', 'zhangsan@example.com', 25),
    ('李四', 'lisi@example.com', 30),
    ('王五', 'wangwu@example.com', 28);

-- 订单
INSERT INTO orders(order_no, user_id, total_amount, status) VALUES
    ('ORD-2024001', 1, 299.00, 'COMPLETED'),
    ('ORD-2024002', 1, 150.50, 'SHIPPED'),
    ('ORD-2024003', 2, 89.90,  'PENDING'),
    ('ORD-2024004', 3, 520.00, 'COMPLETED');

-- 订单项
INSERT INTO order_items(order_id, product_name, quantity, price) VALUES
    (1, 'Java编程思想',   1, 99.00),
    (1, 'MySQL实战45讲',  2, 100.00),
    (2, 'Spring实战',     1, 150.50),
    (3, '算法导论',       1, 89.90),
    (4, '设计模式',       2, 150.00),
    (4, '重构',           1, 220.00);

-- 角色
INSERT INTO roles(role_name, description) VALUES
    ('ADMIN',   '系统管理员'),
    ('USER',    '普通用户'),
    ('VIP',     'VIP会员');

-- 用户-角色关联
INSERT INTO user_role(user_id, role_id) VALUES
    (1, 1), (1, 2),
    (2, 2), (2, 3),
    (3, 2);
