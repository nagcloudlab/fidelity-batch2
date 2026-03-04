package com.example.repository;

import com.example.entity.Account;

public interface AccountRepository {
    Account findById(String accountNumber);
    void save(Account account); // insert or update
}
