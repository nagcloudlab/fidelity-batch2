package com.bank.service;

import com.bank.exception.AccountNotFoundException;
import com.bank.exception.InsufficientFundsException;
import com.bank.model.Account;
import com.bank.model.CurrentAccount;
import com.bank.model.SavingsAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AccountService manages all account operations in the banking system.
 *
 * Key concepts demonstrated:
 * - ArrayList for ordered storage (display all accounts, iterate)
 * - HashMap for O(1) lookup by account number (instant search)
 * - Both structures store references to the SAME Account objects
 * - Auto-generated account numbers starting from 1001
 * - Custom exceptions for business rule violations
 *
 * Day 4: Created with ArrayList + HashMap dual storage
 * Day 5: Enhanced with search and filter capabilities
 *
 * Why both List AND Map?
 * - List  -> for ordered display ("show all accounts")
 * - Map   -> for instant lookup ("find account #1001")
 * In a real application, a database handles both needs.
 */
public class AccountService {

    // Dual data structures for different access patterns
    private List<Account> accountList;           // ordered storage for display
    private Map<Long, Account> accountMap;       // O(1) lookup by account number

    // Auto-incrementing account number generator
    private long nextAccountNumber = 1001;

    /**
     * Initialize empty collections for account storage.
     */
    public AccountService() {
        this.accountList = new ArrayList<>();
        this.accountMap = new HashMap<>();
    }

    // ==================== ACCOUNT CREATION ====================

    /**
     * Create a new Savings Account with auto-generated account number.
     *
     * @param holderName   Name of the account holder
     * @param balance      Initial deposit amount
     * @param interestRate Annual interest rate (e.g., 4.5 for 4.5%)
     * @return The created Account
     */
    public Account createSavingsAccount(String holderName, double balance, double interestRate) {
        long accNo = nextAccountNumber++;
        SavingsAccount account = new SavingsAccount(accNo, holderName, balance, interestRate);
        accountList.add(account);
        accountMap.put(accNo, account);
        System.out.println("Savings Account created: #" + accNo + " for " + holderName);
        return account;
    }

    /**
     * Create a new Current Account with auto-generated account number.
     *
     * @param holderName     Name of the account holder
     * @param balance        Initial deposit amount
     * @param overdraftLimit Maximum overdraft allowed
     * @return The created Account
     */
    public Account createCurrentAccount(String holderName, double balance, double overdraftLimit) {
        long accNo = nextAccountNumber++;
        CurrentAccount account = new CurrentAccount(accNo, holderName, balance, overdraftLimit);
        accountList.add(account);
        accountMap.put(accNo, account);
        System.out.println("Current Account created: #" + accNo + " for " + holderName);
        return account;
    }

    /**
     * Add an existing Account object (e.g., loaded from file).
     * Checks for duplicate account numbers.
     *
     * @param account The account to add
     */
    public void addAccount(Account account) {
        if (accountMap.containsKey(account.getAccountNumber())) {
            System.out.println("Account #" + account.getAccountNumber() + " already exists.");
            return;
        }
        accountList.add(account);
        accountMap.put(account.getAccountNumber(), account);

        // Keep nextAccountNumber ahead of all existing account numbers
        if (account.getAccountNumber() >= nextAccountNumber) {
            nextAccountNumber = account.getAccountNumber() + 1;
        }
    }

    // ==================== ACCOUNT LOOKUP ====================

    /**
     * Find an account by its account number.
     * Uses HashMap for O(1) constant-time lookup.
     *
     * @param accountNumber The account number to search for
     * @return The Account object
     * @throws AccountNotFoundException if no account exists with the given number
     */
    public Account getByAccountNumber(long accountNumber) throws AccountNotFoundException {
        Account account = accountMap.get(accountNumber);   // O(1) lookup
        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        return account;
    }

    /**
     * Get all accounts as an unmodifiable list.
     *
     * @return List of all accounts
     */
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accountList);   // return a copy to prevent external modification
    }

    // ==================== DEPOSIT / WITHDRAW ====================

    /**
     * Deposit money into an account.
     *
     * @param accountNumber The account to deposit into
     * @param amount        The amount to deposit
     * @throws AccountNotFoundException if account doesn't exist
     */
    public void deposit(long accountNumber, double amount) throws AccountNotFoundException {
        Account account = getByAccountNumber(accountNumber);
        account.deposit(amount);
    }

    /**
     * Withdraw money from an account.
     *
     * @param accountNumber The account to withdraw from
     * @param amount        The amount to withdraw
     * @throws AccountNotFoundException    if account doesn't exist
     * @throws InsufficientFundsException  if insufficient balance
     */
    public void withdraw(long accountNumber, double amount)
            throws AccountNotFoundException, InsufficientFundsException {
        Account account = getByAccountNumber(accountNumber);
        account.withdraw(amount);
    }

    // ==================== SEARCH OPERATIONS ====================

    /**
     * Search for accounts by holder name (partial, case-insensitive).
     * Uses linear search — O(n) because we must check every account.
     *
     * @param keyword The name (or part of name) to search for
     * @return List of matching accounts
     */
    public List<Account> searchByName(String keyword) {
        List<Account> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (Account acc : accountList) {
            if (acc.getHolderName().toLowerCase().contains(lowerKeyword)) {
                results.add(acc);
            }
        }
        return results;
    }

    /**
     * Filter accounts by type: "SAVINGS" or "CURRENT".
     * Uses instanceof to check the actual runtime type — polymorphism in action.
     *
     * @param type "SAVINGS" or "CURRENT"
     * @return List of accounts matching the type
     */
    public List<Account> filterByType(String type) {
        List<Account> results = new ArrayList<>();
        for (Account acc : accountList) {
            if (type.equalsIgnoreCase("SAVINGS") && acc instanceof SavingsAccount) {
                results.add(acc);
            } else if (type.equalsIgnoreCase("CURRENT") && acc instanceof CurrentAccount) {
                results.add(acc);
            }
        }
        return results;
    }

    // ==================== DELETE / COUNT ====================

    /**
     * Delete an account by account number.
     * Removes from both the List and the Map to keep them in sync.
     *
     * @param accountNumber The account number to remove
     * @throws AccountNotFoundException if account doesn't exist
     */
    public void deleteAccount(long accountNumber) throws AccountNotFoundException {
        Account account = accountMap.remove(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        accountList.remove(account);
        System.out.println("Account #" + accountNumber + " (" + account.getHolderName() + ") removed.");
    }

    /**
     * Get the total number of accounts.
     *
     * @return Account count
     */
    public int getAccountCount() {
        return accountList.size();
    }

    /**
     * Clear all accounts (used when loading from file to avoid duplicates).
     */
    public void clearAll() {
        accountList.clear();
        accountMap.clear();
    }
}
