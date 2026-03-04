package com.example.service;


import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.repository.JdbcAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * authors: bar
 */

// SRP -> Single Responsibility Principle
// OCP -> Open/Closed Principle
// LSP -> Liskov Substitution Principle
// ISP -> Interface Segregation Principle
// DIP -> Dependency Inversion Principle

public class UpiTransferService implements TransferService {

    private static final Logger logger = LoggerFactory.getLogger(UpiTransferService.class);

    private AccountRepository accountRepository;

    // Constructor injection of the AccountRepository dependency
    public UpiTransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        logger.info("UpiTransferService created with AccountRepository: {}", accountRepository.getClass().getSimpleName());
    }

    @Override
    public void transfer(String fromAccountId, String toAccountId, double amount) {

        logger.info("Transferring {} from account {} to account {}", amount, fromAccountId, toAccountId);

        // Fetch accounts using the repository
        Account fromAccount = accountRepository.findById(fromAccountId);
        Account toAccount = accountRepository.findById(toAccountId);

        // Perform transfer logic (dummy implementation)
        if (fromAccount.getBalance() < amount) {
            logger.error("Insufficient funds in account {}", fromAccountId);
            throw new RuntimeException("Insufficient funds");
        }

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        // Save updated accounts back to the repository
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

    }
}
