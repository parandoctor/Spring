-- =============================================
-- MyBatis 演示项目数据库初始化脚本
-- 数据库名: mybatis_demo
-- =============================================

-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS mybatis_demo
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE mybatis_demo;

-- 删除旧表（如果存在）
DROP TABLE IF EXISTS users;

-- 创建用户表
CREATE TABLE users (
    id          BIGINT       AUTO_INCREMENT PRIMARY KEY  COMMENT '主键ID',
    username    VARCHAR(50)  NOT NULL                     COMMENT '用户名',
    email       VARCHAR(100)                              COMMENT '邮箱',
    age         INT                                       COMMENT '年龄',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP    COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 可选：插入测试数据
-- INSERT INTO users(username, email, age) VALUES
--     ('测试用户1', 'test1@example.com', 20),
--     ('测试用户2', 'test2@example.com', 25);
