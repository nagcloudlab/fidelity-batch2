package com.example.repository;

import org.slf4j.Logger;

import com.example.BankApp;
import com.example.model.Account;
import com.example.model.SavingsAccount;

/**
 * 
 * author: Varshini
 * 
 */

public class NoSqlAccountRepository implements AccountRepository {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(SqlAccountRepository.class);

    public NoSqlAccountRepository() {
        logger.info("NoSqlAccountRepository instance created!");
    }

    public Account loadAccount(long accountNumber) {
        // Implementation to load account from database
        logger.info("Loading account details for account #{} from database...", accountNumber);
        Account account = new SavingsAccount("foo", accountNumber, 10000.0, 4.5);
        return account;
    }

    public void updateAccount(Account account) {
        // Implementation to update account in database
        logger.info("Updating account details for account #{} in database...", account.getAccountNumber());
    }

}
