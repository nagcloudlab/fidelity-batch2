package com.example.service;


import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * authors: bar
 */

// SRP -> Single Responsibility Principle
// OCP -> Open/Closed Principle
// LSP -> Liskov Substitution Principle
// ISP -> Interface Segregation Principle
// DIP -> Dependency Inversion Principle

@Component("upiTransferService")
public class UpiTransferService implements TransferService {

    private static final Logger logger = LoggerFactory.getLogger(UpiTransferService.class);

    private AccountRepository accountRepository;

    // Constructor injection of the AccountRepository dependency
    @Autowired
    public UpiTransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        logger.info("UpiTransferService created with AccountRepository: {}", accountRepository.getClass().getSimpleName());
    }

    @Override
    @Transactional(
            transactionManager = "transactionManager"
    )
    public void transfer(String fromAccountId, String toAccountId, double amount) {

        logger.info("Transferring {} from account {} to account {}", amount, fromAccountId, toAccountId);

        // Fetch accounts using the repository
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> {;
                    logger.error("From account {} not found", fromAccountId);
                    return new RuntimeException("From account not found");
                });
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> {
                    logger.error("To account {} not found", toAccountId);
                    return new RuntimeException("To account not found");
                });

        // Perform transfer logic (dummy implementation)
        if (fromAccount.getBalance() < amount) {
            logger.error("Insufficient funds in account {}", fromAccountId);
            throw new RuntimeException("Insufficient funds");
        }

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        // Save updated accounts back to the repository
        accountRepository.save(fromAccount);

        boolean simulateError = false; // Change to true to simulate an error after withdrawing from the fromAccount
        if (simulateError) {
            logger.error("Simulating an error after withdrawing from account {}", fromAccountId);
            throw new RuntimeException("Simulated error during transfer");
        }

        accountRepository.save(toAccount);

    }
}
