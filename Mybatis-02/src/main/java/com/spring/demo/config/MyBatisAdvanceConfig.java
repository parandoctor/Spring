package com.spring.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * MyBatis 进阶配置类
 *
 * 与 Mybatis-01 的区别：
 *  - 加载多个 Mapper XML（UserMapper + OrderMapper）
 *  - 配置延迟加载（在 mybatis-config.xml 中）
 *  - 事务管理器与 Spring JDBC 集成
 */
@Configuration
public class MyBatisAdvanceConfig {

    /**
     * HikariCP 数据源
     * 连接 MySQL 数据库 mybatis_advance_demo
     */
    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/mybatis_advance_demo" +
                "?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true");
        ds.setUsername("root");
        ds.setPassword("123456");

        ds.setMaximumPoolSize(10);
        ds.setMinimumIdle(2);
        ds.setIdleTimeout(30000);
        ds.setConnectionTimeout(30000);
        return ds;
    }

    /**
     * SqlSessionFactory —— MyBatis 核心
     *
     * 加载：
     *  - mybatis-config.xml（全局设置 + 类型别名）
     *  - UserMapper.xml + OrderMapper.xml（SQL 映射文件）
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        // 加载 MyBatis 主配置
        factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));

        // 加载所有映射文件
        Resource[] mapperLocations = new Resource[]{
                new ClassPathResource("mapper/UserMapper.xml"),
                new ClassPathResource("mapper/OrderMapper.xml")
        };
        factoryBean.setMapperLocations(mapperLocations);

        return factoryBean;
    }

    /**
     * MapperScanner —— 自动扫描 Mapper 接口
     *
     * 扫描范围：
     *  - com.spring.demo.mapper 包下的所有接口
     *  包括：UserMapper、UserAnnoMapper、OrderMapper、OrderAnnoMapper 等
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer scanner = new MapperScannerConfigurer();
        scanner.setBasePackage("com.spring.demo.mapper");
        scanner.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return scanner;
    }

    /**
     * 事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
