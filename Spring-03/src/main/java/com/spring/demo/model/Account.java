package com.spring.demo.model;

import java.math.BigDecimal;

/**
 * 账户实体——用于演示事务管理（转账场景）
 */
public class Account {

    private Long id;
    private String accountName;
    private BigDecimal balance;

    public Account() {
    }

    public Account(Long id, String accountName, BigDecimal balance) {
        this.id = id;
        this.accountName = accountName;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{id=" + id + ", accountName='" + accountName + "', balance=" + balance + "}";
    }
}
