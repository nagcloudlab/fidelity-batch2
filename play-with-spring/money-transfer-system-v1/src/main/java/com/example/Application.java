package com.example;

import com.example.repository.AccountRepository;
import com.example.repository.JdbcAccountRepository;
import com.example.service.TransferService;
import com.example.service.UpiTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {


        //---------------------------------------------
        // Init / boot phase
        //---------------------------------------------
        logger.info("Starting application...");
        logger.info("-".repeat(100));

        // based on configuration, we need  create components and wire them together
        // for example, we need to create an instance of JdbcAccountRepository and inject it into UpiTransferService
        logger.info("Creating components and wiring dependencies...");
        // In a real application, this would be done by a DI container like Spring, but here we will do it manually for demonstration purposes
        AccountRepository jdbcAccountRepository = new JdbcAccountRepository();
        TransferService upiTransferService = new UpiTransferService(jdbcAccountRepository); // constructor injection

        logger.info("-".repeat(100));

        //----------------------------------------------
        // Use case execution phase
        //----------------------------------------------
        logger.info("Executing use case...");
        logger.info("-".repeat(100));

        // Execute some use case, for example, transferring money from one account to another
        String fromAccountId = "12345";
        String toAccountId = "67890";
        double amount = 100.0;
        upiTransferService.transfer(fromAccountId, toAccountId, amount);
        logger.info("-".repeat(50));
        upiTransferService.transfer(fromAccountId, toAccountId, amount);

        logger.info("-".repeat(100));

        //----------------------------------------------
        // Shutdown phase
        //----------------------------------------------
        logger.info("Shutting down application...");
        logger.info("-".repeat(100));
        logger.info("-".repeat(100));


    }
}