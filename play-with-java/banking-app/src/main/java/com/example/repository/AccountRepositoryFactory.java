package com.example.repository;

import org.slf4j.Logger;

public class AccountRepositoryFactory {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(AccountRepositoryFactory.class);

    public static AccountRepository getAccountRepository(String type) {
        if ("sql".equalsIgnoreCase(type)) {
            return new SqlAccountRepository();
        } else if ("nosql".equalsIgnoreCase(type)) {
            return new NoSqlAccountRepository();
        } else {
            logger.error("Invalid repository type: {}", type);
            throw new IllegalArgumentException("Invalid repository type: " + type);
        }

    }

}
