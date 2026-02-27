package com.example.service;

import org.slf4j.Logger;

import com.example.model.Account;
import com.example.repository.AccountRepository;
// import com.example.repository.SqlAccountRepository;
import com.example.repository.AccountRepositoryFactory;

/**
 * 
 * author: Smit
 * 
 */

/*
 * 
 * design issue
 * --------------
 * 
 * -> tight coupling between UpiTransferService and SqlAccountRepository
 * 
 * causes several issues:
 * ----------------------
 * => can't extend UpiTransferService to support other types of account
 * repositories (e.g. NoSqlAccountRepository, InMemoryAccountRepository, etc.)
 * => unit testing of UpiTransferService is difficult because it directly
 * depends on SqlAccountRepository which interacts with the database
 * 
 * 
 * performance issue
 * -----------------
 * 
 * -> on each transfer, creating a new instance of SqlAccountRepository which is
 * expensive
 * causes several issues:
 * -----------------------
 * => latency: creating a new instance of SqlAccountRepository involves
 * establishing a
 * database connection which is time-consuming
 * => resource consumption: each instance of SqlAccountRepository consumes
 * memory and other resources
 * 
 * 
 * 
 * why these issues arise?
 * ----------------------
 * 
 * => dependent managing its own dependency lifecycle: UpiTransferService is
 * responsible for creating and managing the lifecycle of SqlAccountRepository
 * which leads to tight coupling and performance issues
 * 
 * solition-1: using Factory Pattern
 * -> we fixed design , but performance issue still exists because we are still
 * creating a new instance of SqlAccountRepository on each transfer
 * 
 * 
 * solution-2: don't create / find dependency from dependent class, instead
 * inject the dependency from outside
 * aka Inversion of Control (IoC) principle
 * aka Dependency Injection (DI) principle
 * 
 * types of Dependency Injection:
 * --------------------------------
 * 1. Constructor Injection -> required dependencies are provided through the
 * constructor of the dependent class
 * 2. Setter Injection -> optional dependencies are provided through setter
 * methods of the dependent class
 * 
 * 
 * 
 * -------------------------------------------------------
 * // SOLID Principles
 * -------------------------------------------------------
 * 
 * S -> Single Responsibility Principle (SRP)
 * O -> Open/Closed Principle (OCP)
 * L -> Liskov Substitution Principle (LSP)
 * I -> Interface Segregation Principle (ISP)
 * D -> Dependency Inversion Principle (DIP)
 * 
 * --------------------------------------------------------
 * 
 */

public class UpiTransferService implements TransferService {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(UpiTransferService.class);

    private AccountRepository accountRepository; // HAS-A relationship

    public UpiTransferService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        logger.info("UpiTransferService instance created!");
    }

    public void transfer(long fromAccountNumber, long toAccountNumber, double amount) {

        logger.info("Initiating UPI transfer of {} from account #{} to account #{}", amount, fromAccountNumber,
                toAccountNumber);

        // SqlAccountRepository accountRepository = new SqlAccountRepository();
        // AccountRepository accountRepository =
        // AccountRepositoryFactory.getAccountRepository("sql");

        // step-1: Load the from account details from database
        Account fromAccount = accountRepository.loadAccount(fromAccountNumber);
        // step-3: Load the to account details from database
        Account toAccount = accountRepository.loadAccount(toAccountNumber);
        // step-5: Debit the from account
        fromAccount.withdraw(amount);
        // step-6: Credit the to account
        toAccount.deposit(amount);
        // step-7: Update the from account details in database
        accountRepository.updateAccount(fromAccount);
        // step-8: Update the to account details in database
        accountRepository.updateAccount(toAccount);

        logger.info("UPI transfer of {} from account #{} to account #{} completed successfully!", amount,
                fromAccountNumber, toAccountNumber);

    }

}
