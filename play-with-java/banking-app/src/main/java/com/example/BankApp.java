package com.example;

import org.slf4j.Logger;

import com.example.repository.AccountRepository;
import com.example.repository.AccountRepositoryFactory;
import com.example.service.UpiTransferService;

/**
 * author: Nag
 */

public class BankApp {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(BankApp.class);

    public static void main(String[] args) {

        logger.info("Welcome to the Banking App!"); // console log to indicate the application has started
        // ---------------------------------------------
        // Init/bootstrap phase
        // ---------------------------------------------
        logger.info("-".repeat(50));
        logger.info("Initializing the application..."); // console log to indicate the initialization phase has started
        // based on configuration, we create the necessary components and wire them
        // together
        AccountRepository sqlAccountRepository = AccountRepositoryFactory.getAccountRepository("sql");
        AccountRepository nosqlAccountRepository = AccountRepositoryFactory.getAccountRepository("nosql");
        UpiTransferService transferService = new UpiTransferService(nosqlAccountRepository);
        logger.info("-".repeat(50));
        // ---------------------------------------------
        // Use phase
        // ---------------------------------------------
        // we use the components to perform the necessary operations
        transferService.transfer(1L, 2L, 100.0);
        logger.info("-".repeat(25));
        transferService.transfer(1L, 2L, 100.0);

        logger.info("-".repeat(50));
        // ---------------------------------------------
        // Cleanup phase
        // ---------------------------------------------
        // we clean up any resources that were used during the use phase
        // In this example, there are no resources to clean up, but in a real
        // application, we might need to close database connections, release file
        // handles, etc.
        logger.info("-".repeat(50));
        logger.info("-".repeat(50));

    }

}