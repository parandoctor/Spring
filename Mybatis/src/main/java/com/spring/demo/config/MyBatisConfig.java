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
 * MyBatis + Spring 集成配置类
 *
 * 核心 Bean：
 * 1. DataSource         —— 数据库连接池
 * 2. SqlSessionFactory  —— MyBatis 核心工厂（替代原生 mybatis-config.xml 中的环境配置）
 * 3. MapperScanner      —— 自动扫描 Mapper 接口并注册为 Spring Bean
 * 4. TransactionManager —— 事务管理器
 */
@Configuration
public class MyBatisConfig {

    /**
     * 数据源配置 —— HikariCP 连接池
     */
    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/mybatis_demo?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true");
        ds.setUsername("root");
        ds.setPassword("123456");

        // 连接池参数
        ds.setMaximumPoolSize(10);
        ds.setMinimumIdle(2);
        ds.setIdleTimeout(30000);
        ds.setConnectionTimeout(30000);
        return ds;
    }

    /**
     * SqlSessionFactoryBean —— MyBatis 与 Spring 集成的核心
     *
     * 它负责：
     * - 创建 SqlSessionFactory
     * - 加载 MyBatis 主配置文件（mybatis-config.xml）
     * - 加载映射文件（mapper/*.xml）
     * - 设置数据源、事务工厂
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        // 加载 MyBatis 主配置文件（settings、typeAliases）
        Resource configLocation = new ClassPathResource("mybatis-config.xml");
        factoryBean.setConfigLocation(configLocation);

        // 加载映射文件（也可以用 MapperScannerConfigurer 自动发现）
        // 这里显式指定 mapper XML 路径
        Resource[] mapperLocations = new Resource[]{
                new ClassPathResource("mapper/UserMapper.xml")
        };
        factoryBean.setMapperLocations(mapperLocations);

        return factoryBean;
    }

    /**
     * MapperScannerConfigurer —— 自动扫描 Mapper 接口
     *
     * 将指定包下的 Mapper 接口自动注册为 Spring Bean，
     * 无需再逐个配置 <bean> 或 @MapperScan 注解。
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.spring.demo.mapper");
        // SqlSessionFactory 的 Bean 名（默认就是 sqlSessionFactory）
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return configurer;
    }

    /**
     * 事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
