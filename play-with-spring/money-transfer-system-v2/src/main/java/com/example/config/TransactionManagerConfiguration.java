package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class TransactionManagerConfiguration {

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        org.springframework.jdbc.datasource.DataSourceTransactionManager transactionManager = new org.springframework.jdbc.datasource.DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

}
