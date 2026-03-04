package com.example;

import com.example.service.TransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.example")
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        //---------------------------------------------
        // Init / boot phase
        //---------------------------------------------
        logger.info("Starting application...");
        logger.info("-".repeat(100));

        logger.info("Creating components and wiring dependencies...");
        // In a real application, this would be done by a DI container like Spring,
        ConfigurableApplicationContext context = null;
        try {
            context = SpringApplication.run(Application.class, args);
            logger.info("Components created and dependencies wired successfully.");
        } catch (Exception e) {
            logger.error("Error during component creation and wiring: ", e);
            return;
        }

        logger.info("-".repeat(100));

        //---------------------------------------------


    }
}