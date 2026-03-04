package com.example.repository;

import com.example.entity.Account;
import com.example.entity.AccountType;
import org.slf4j.Logger;

/**
 * authors: foo
 */

public class JdbcAccountRepository implements AccountRepository {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(JdbcAccountRepository.class);

    public JdbcAccountRepository() {
        logger.info("JdbcAccountRepository created");
    }

    @Override
    public Account findById(String accountNumber) {
        logger.info("JdbcAccountRepository findById called with accountNumber: {}", accountNumber);
        return new Account(accountNumber,"Unknown",1000.00, AccountType.SAVINGS); // Dummy account for demonstration
    }

    @Override
    public void save(Account account) {
        logger.info("JdbcAccountRepository save called with account: {}", account);
    }

}
