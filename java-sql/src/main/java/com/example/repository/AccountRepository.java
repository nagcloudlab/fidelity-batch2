package com.example.repository;

import com.example.entity.Account;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll();
    Account findByAccountNumber(String accountNumber);
    void updateAccount(Account account);
}
