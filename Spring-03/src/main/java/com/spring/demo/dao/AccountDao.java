package com.spring.demo.dao;

import com.spring.demo.model.Account;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * 账户 DAO——用于事务管理演示（转账场景）
 *
 * 关键方法：
 * - outMoney：转出（余额减少）
 * - inMoney：转入（余额增加）
 * 这两个方法通常在同一事务中成对调用。
 */
@Repository
public class AccountDao {

    private final JdbcTemplate jdbcTemplate;

    public AccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 转出——扣减余额
     */
    public int outMoney(String accountName, BigDecimal amount) {
        String sql = "UPDATE accounts SET balance = balance - ? WHERE account_name = ?";
        return jdbcTemplate.update(sql, amount, accountName);
    }

    /**
     * 转入——增加余额
     */
    public int inMoney(String accountName, BigDecimal amount) {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE account_name = ?";
        return jdbcTemplate.update(sql, amount, accountName);
    }

    /**
     * 查询账户
     */
    public Account findByAccountName(String accountName) {
        String sql = "SELECT id, account_name, balance FROM accounts WHERE account_name = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Account.class), accountName);
    }

    /**
     * 初始化账户表并插入测试数据
     */
    public void initTable() {
        // 建表（如果不存在）
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS accounts (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "account_name VARCHAR(50) NOT NULL, " +
                "balance DECIMAL(10,2) NOT NULL DEFAULT 0.00" +
                ")");

        // 检查是否已有数据
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM accounts", Integer.class);
        if (count != null && count == 0) {
            jdbcTemplate.update("INSERT INTO accounts(account_name, balance) VALUES (?, ?)", "张三", new BigDecimal("10000.00"));
            jdbcTemplate.update("INSERT INTO accounts(account_name, balance) VALUES (?, ?)", "李四", new BigDecimal("5000.00"));
            System.out.println("[AccountDao] 账户表初始化完成，已插入测试数据");
        }
    }
}
