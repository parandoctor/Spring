package com.spring.demo.service;

import com.spring.demo.dao.AccountDao;
import com.spring.demo.model.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 账户服务——演示 Spring 声明式事务
 *
 * @Transactional 核心属性：
 * - propagation:  传播行为（默认 REQUIRED：有事务则加入，无则新建）
 * - isolation:    隔离级别（默认 DEFAULT，即数据库默认级别）
 * - timeout:      超时秒数（默认 -1 不超时）
 * - readOnly:     是否只读（默认 false）
 * - rollbackFor:  触发回滚的异常类型（默认 RuntimeException 和 Error）
 * - noRollbackFor:不触发回滚的异常类型
 */
@Service
public class AccountService {

    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * 转账——核心事务演示
     *
     * @Transactional 确保 outMoney 和 inMoney 在同一事务中：
     * 如果任何一步失败（抛出 RuntimeException），全部回滚。
     *
     * 传播行为 REQUIRED：如果调用者已有事务，则加入；否则新建。
     * 隔离级别 READ_COMMITTED：防止脏读，允许不可重复读 & 幻读。
     */
    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            timeout = 5,
            rollbackFor = Exception.class
    )
    public void transfer(String from, String to, BigDecimal amount) {
        System.out.println("[转账开始] " + from + " → " + to + " 金额: " + amount);

        // 步骤1：转出
        int out = accountDao.outMoney(from, amount);
        System.out.println("  ① 转出结果: " + (out > 0 ? "成功" : "失败"));

        // 模拟异常——用于演示回滚（取消注释下面一行即可看到回滚效果）
        // int i = 1 / 0;

        // 步骤2：转入
        int in = accountDao.inMoney(to, amount);
        System.out.println("  ② 转入结果: " + (in > 0 ? "成功" : "失败"));

        System.out.println("[转账完成]");
    }

    /**
     * 查询账户余额（只读事务——性能优化）
     *
     * readOnly = true 提示底层数据库该操作不涉及写，
     * 某些数据库可据此优化（如 MySQL 的 read-only 事务）。
     */
    @Transactional(readOnly = true)
    public Account getAccount(String accountName) {
        return accountDao.findByAccountName(accountName);
    }

    /**
     * 初始化账户表（非事务方法）
     */
    public void initAccounts() {
        accountDao.initTable();
    }

    /**
     * 演示 Propagation.REQUIRES_NEW——挂起当前事务，开启新事务
     *
     * 场景：转账后记录日志，即使日志记录失败也不影响转账。
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logTransfer(String from, String to, BigDecimal amount) {
        // 模拟写入日志表（此处省略具体实现）
        System.out.println("[日志] " + from + " → " + to + " ￥" + amount);
    }
}
