package com.example;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.repository.JdbcAccountRepository;
import com.example.repository.JpaAccountRepository;

public class Application {

    public static void main(String[] args) {

        //-------------------------------------------------
        // Init / boot phase
        //--------------------------------------------------

        AccountRepository accountRepository = new JpaAccountRepository();
        accountRepository.findAll()
                .forEach(System.out::println);

        Account account = accountRepository.findByAccountNumber("1234567890");
        System.out.println(account);

        account.withdraw(500);

        accountRepository.updateAccount(account);

        //-------------------------------------------------
        // Run phase
        //--------------------------------------------------


        //-------------------------------------------------
        // Shutdown phase
        //--------------------------------------------------

    }

}
