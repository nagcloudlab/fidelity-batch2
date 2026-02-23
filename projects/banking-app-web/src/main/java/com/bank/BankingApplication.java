package com.bank;

import com.bank.config.DatabaseManager;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingApplication.class, args);
    }

    /**
     * Initialize the H2 database with tables and sample data at startup.
     * @PostConstruct runs after the Spring context is initialized.
     */
    @PostConstruct
    public void init() {
        DatabaseManager.initializeDatabase();
    }
}
