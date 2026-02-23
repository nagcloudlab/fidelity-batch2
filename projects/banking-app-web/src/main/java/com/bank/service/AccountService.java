package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Service layer for banking operations.
 * Delegates to AccountRepository and TransactionRepository for JDBC access.
 * Wraps checked SQLExceptions into unchecked RuntimeExceptions.
 */
@Service
public class AccountService {

    private final AccountRepository accountRepository = new AccountRepository();
    private final TransactionRepository transactionRepository = new TransactionRepository();

    // ──── CREATE ────

    public Account createAccount(Account account) {
        try {
            if (accountRepository.findByAccountNumber(account.getAccountNumber()) != null) {
                return null;    // duplicate account number
            }
            return accountRepository.insert(account);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating account", e);
        }
    }

    // ──── READ (all) ────

    public List<Account> getAllAccounts() {
        try {
            return accountRepository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching accounts", e);
        }
    }

    // ──── READ (by account number) ────

    public Account getByAccountNumber(long accountNumber) {
        try {
            return accountRepository.findByAccountNumber(accountNumber);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching account", e);
        }
    }

    // ──── DEPOSIT ────

    public Account deposit(long accountNumber, double amount) {
        try {
            Account acc = accountRepository.findByAccountNumber(accountNumber);
            if (acc == null) return null;
            if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");

            double newBalance = acc.getBalance() + amount;
            accountRepository.updateBalance(accountNumber, newBalance);

            // Record the transaction
            Transaction txn = new Transaction();
            txn.setAccountNumber(accountNumber);
            txn.setTransactionType("DEPOSIT");
            txn.setAmount(amount);
            transactionRepository.insert(txn);

            acc.setBalance(newBalance);
            return acc;
        } catch (SQLException e) {
            throw new RuntimeException("Error depositing", e);
        }
    }

    // ──── WITHDRAW ────

    public Account withdraw(long accountNumber, double amount) {
        try {
            Account acc = accountRepository.findByAccountNumber(accountNumber);
            if (acc == null) return null;
            if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
            if (amount > acc.getBalance()) throw new IllegalArgumentException("Insufficient balance");

            double newBalance = acc.getBalance() - amount;
            accountRepository.updateBalance(accountNumber, newBalance);

            // Record the transaction
            Transaction txn = new Transaction();
            txn.setAccountNumber(accountNumber);
            txn.setTransactionType("WITHDRAW");
            txn.setAmount(amount);
            transactionRepository.insert(txn);

            acc.setBalance(newBalance);
            return acc;
        } catch (SQLException e) {
            throw new RuntimeException("Error withdrawing", e);
        }
    }

    // ──── UPDATE (full) ────

    public Account updateAccount(long accountNumber, Account updated) {
        try {
            return accountRepository.update(accountNumber, updated);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating account", e);
        }
    }

    // ──── DELETE ────

    public boolean deleteAccount(long accountNumber) {
        try {
            // Delete associated transactions first (FK constraint)
            transactionRepository.deleteByAccountNumber(accountNumber);
            return accountRepository.delete(accountNumber);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting account", e);
        }
    }

    // ──── SEARCH (by name) ────

    public List<Account> searchByName(String keyword) {
        try {
            return accountRepository.searchByName(keyword);
        } catch (SQLException e) {
            throw new RuntimeException("Error searching accounts", e);
        }
    }

    // ──── FILTER (by type) ────

    public List<Account> filterByType(String type) {
        try {
            return accountRepository.filterByType(type);
        } catch (SQLException e) {
            throw new RuntimeException("Error filtering accounts", e);
        }
    }
}
