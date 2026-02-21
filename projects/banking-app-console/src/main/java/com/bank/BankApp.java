package com.bank;

import com.bank.exception.AccountNotFoundException;
import com.bank.exception.InsufficientFundsException;
import com.bank.model.Account;
import com.bank.model.CurrentAccount;
import com.bank.model.SavingsAccount;
import com.bank.model.Transaction;
import com.bank.service.AccountService;
import com.bank.util.FileManager;
import com.bank.util.ServiceQueue;
import com.bank.util.TransactionStack;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * BankApp — Main class with Scanner-based interactive menu loop.
 *
 * This is the console-based Banking Application case study from Days 1-7.
 * It integrates all concepts learned throughout the training:
 *
 * Day 1: Variables, control flow, methods, Scanner input
 * Day 2: OOP — classes, inheritance, polymorphism, abstract classes
 * Day 3: Exception handling — try-catch, custom exceptions
 * Day 4: Collections — ArrayList + HashMap for account management
 * Day 5: Data structures — Stack (undo), Queue (service requests)
 * Day 7: File I/O — CSV and serialization persistence
 *
 * Menu options:
 *  1. Create Account
 *  2. View All Accounts
 *  3. Deposit
 *  4. Withdraw
 *  5. Search by Name
 *  6. Undo Last Transaction
 *  7. Process Service Queue
 *  8. Save to File
 *  9. Load from File
 * 10. Exit
 */
public class BankApp {

    // File paths for persistence
    private static final String ACCOUNTS_CSV = "data/accounts.csv";
    private static final String ACCOUNTS_DAT = "data/accounts.dat";

    // Core services and data structures
    private static AccountService accountService = new AccountService();
    private static TransactionStack transactionStack = new TransactionStack();
    private static ServiceQueue serviceQueue = new ServiceQueue();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("   Welcome to the Banking Application!   ");
        System.out.println("==========================================");

        // Load sample accounts on first run
        loadSampleAccounts();

        int choice = 0;
        do {
            displayMenu();
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                sc.nextLine();   // clear the invalid input from scanner buffer
                continue;        // restart the loop — show menu again
            }

            switch (choice) {
                case 1:
                    handleCreateAccount(sc);
                    break;
                case 2:
                    handleViewAllAccounts();
                    break;
                case 3:
                    handleDeposit(sc);
                    break;
                case 4:
                    handleWithdraw(sc);
                    break;
                case 5:
                    handleSearchByName(sc);
                    break;
                case 6:
                    handleUndoTransaction(sc);
                    break;
                case 7:
                    handleServiceQueue(sc);
                    break;
                case 8:
                    handleSaveToFile(sc);
                    break;
                case 9:
                    handleLoadFromFile(sc);
                    break;
                case 10:
                    System.out.println("\nThank you for banking with us!");
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-10.");
            }
        } while (choice != 10);

        sc.close();
    }

    // ==================== MENU DISPLAY ====================

    /**
     * Display the main menu.
     * Uses a do-while loop in main() — the menu runs at least once.
     */
    private static void displayMenu() {
        System.out.println("\n========== BANK APPLICATION ==========");
        System.out.println("--- Account Operations ---");
        System.out.println(" 1. Create Account");
        System.out.println(" 2. View All Accounts");
        System.out.println(" 3. Deposit");
        System.out.println(" 4. Withdraw");
        System.out.println(" 5. Search by Name");
        System.out.println("--- Transaction & Service ---");
        System.out.println(" 6. Undo Last Transaction");
        System.out.println(" 7. Process Service Queue");
        System.out.println("--- File Operations ---");
        System.out.println(" 8. Save to File");
        System.out.println(" 9. Load from File");
        System.out.println("---");
        System.out.println("10. Exit");
        System.out.println("======================================");
        System.out.print("Enter choice: ");
    }

    // ==================== 1. CREATE ACCOUNT ====================

    /**
     * Handle account creation — asks for type (Savings/Current) and details.
     * Auto-generates account number via AccountService.
     */
    private static void handleCreateAccount(Scanner sc) {
        try {
            sc.nextLine();   // consume leftover newline after nextInt()

            System.out.print("Account type (S=Savings, C=Current): ");
            String type = sc.nextLine().trim().toUpperCase();

            System.out.print("Account holder name: ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
                return;
            }

            System.out.print("Initial balance: ");
            double balance = sc.nextDouble();
            if (balance < 0) {
                System.out.println("Initial balance cannot be negative.");
                return;
            }

            if (type.equals("S")) {
                System.out.print("Interest rate (%, default 4.0): ");
                double rate = sc.nextDouble();
                accountService.createSavingsAccount(name, balance, rate);
            } else if (type.equals("C")) {
                System.out.print("Overdraft limit (default 10000): ");
                double limit = sc.nextDouble();
                accountService.createCurrentAccount(name, balance, limit);
            } else {
                System.out.println("Invalid account type. Use S for Savings or C for Current.");
                return;
            }

            System.out.println("Total accounts: " + accountService.getAccountCount());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid numbers.");
            sc.nextLine();
        }
    }

    // ==================== 2. VIEW ALL ACCOUNTS ====================

    /**
     * Display all accounts using polymorphic displayInfo() call.
     * Each account type prints its own format — runtime polymorphism in action.
     */
    private static void handleViewAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts registered.");
            return;
        }

        System.out.println("\n--- All Accounts (" + accounts.size() + ") ---");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.print((i + 1) + ". ");
            accounts.get(i).displayInfo();   // polymorphic call
            System.out.println();
        }
    }

    // ==================== 3. DEPOSIT ====================

    /**
     * Handle deposit — finds account by number and deposits.
     * Records the transaction on the stack for undo capability.
     */
    private static void handleDeposit(Scanner sc) {
        try {
            System.out.print("Enter account number: ");
            long accNo = sc.nextLong();

            Account acc = accountService.getByAccountNumber(accNo);

            System.out.print("Enter deposit amount: ");
            double amount = sc.nextDouble();

            // Record balance before the transaction (for undo)
            double balanceBefore = acc.getBalance();

            acc.deposit(amount);

            // Push transaction onto the stack for undo support
            Transaction txn = new Transaction(accNo, "DEPOSIT", amount,
                    balanceBefore, acc.getBalance());
            transactionStack.push(txn);

        } catch (AccountNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid numbers.");
            sc.nextLine();
        }
    }

    // ==================== 4. WITHDRAW ====================

    /**
     * Handle withdrawal — finds account by number and withdraws.
     * Catches InsufficientFundsException with detailed error info.
     */
    private static void handleWithdraw(Scanner sc) {
        try {
            System.out.print("Enter account number: ");
            long accNo = sc.nextLong();

            Account acc = accountService.getByAccountNumber(accNo);

            System.out.print("Enter withdrawal amount: ");
            double amount = sc.nextDouble();

            // Record balance before the transaction (for undo)
            double balanceBefore = acc.getBalance();

            acc.withdraw(amount);

            // Push transaction onto the stack for undo support
            Transaction txn = new Transaction(accNo, "WITHDRAW", amount,
                    balanceBefore, acc.getBalance());
            transactionStack.push(txn);

        } catch (AccountNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            // Custom exception gives us detailed information
            System.out.println("DECLINED: " + e.getMessage());
            System.out.println("  Requested: Rs." + e.getRequestedAmount());
            System.out.println("  Available: Rs." + e.getCurrentBalance());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid numbers.");
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    // ==================== 5. SEARCH BY NAME ====================

    /**
     * Search for accounts by name (partial match, case-insensitive).
     * Uses linear search — O(n) because we must check every account.
     */
    private static void handleSearchByName(Scanner sc) {
        sc.nextLine();   // consume newline
        System.out.print("Enter name (or part of name) to search: ");
        String keyword = sc.nextLine().trim();

        if (keyword.isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
            return;
        }

        List<Account> results = accountService.searchByName(keyword);
        if (results.isEmpty()) {
            System.out.println("No accounts matching '" + keyword + "'.");
        } else {
            System.out.println("\nFound " + results.size() + " account(s):");
            for (Account acc : results) {
                acc.displayInfo();
                System.out.println();
            }
        }
    }

    // ==================== 6. UNDO TRANSACTION ====================

    /**
     * Undo the last transaction using the TransactionStack.
     * Stack is LIFO — the most recent transaction is undone first.
     */
    private static void handleUndoTransaction(Scanner sc) {
        if (transactionStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }

        // Peek at the transaction to find which account to restore
        Transaction lastTxn = transactionStack.peek();
        if (lastTxn == null) {
            System.out.println("Nothing to undo.");
            return;
        }

        try {
            Account acc = accountService.getByAccountNumber(lastTxn.getAccountNumber());
            transactionStack.undoLast(acc);
        } catch (AccountNotFoundException e) {
            System.out.println("Cannot undo: " + e.getMessage());
            // Pop the transaction anyway since the account no longer exists
        }

        // Show remaining transaction history
        System.out.println("Transactions remaining on stack: " + transactionStack.size());
    }

    // ==================== 7. SERVICE QUEUE ====================

    /**
     * Handle the service queue — submit requests, process next, or process all.
     * Queue is FIFO — first request submitted is processed first.
     */
    private static void handleServiceQueue(Scanner sc) {
        System.out.println("\n--- Service Queue ---");
        System.out.println("1. Submit a Service Request");
        System.out.println("2. Process Next Request");
        System.out.println("3. Process All Requests");
        System.out.println("4. View Pending Requests");
        System.out.print("Choose option: ");

        try {
            int option = sc.nextInt();
            sc.nextLine();   // consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter your service request: ");
                    String request = sc.nextLine().trim();
                    if (!request.isEmpty()) {
                        serviceQueue.addRequest(request);
                    } else {
                        System.out.println("Request cannot be empty.");
                    }
                    break;
                case 2:
                    serviceQueue.processNext();
                    break;
                case 3:
                    serviceQueue.processAll();
                    break;
                case 4:
                    serviceQueue.viewPending();
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }

    // ==================== 8. SAVE TO FILE ====================

    /**
     * Save accounts to file — offers both CSV and serialization formats.
     *
     * CSV: Human-readable, can be opened in Excel or any text editor
     * Serialization: Binary format, quick Java-only persistence
     */
    private static void handleSaveToFile(Scanner sc) {
        System.out.println("\n--- Save Data ---");
        System.out.println("1. Save as CSV (human-readable)");
        System.out.println("2. Save as Serialized (binary)");
        System.out.println("3. Save Both");
        System.out.print("Choose format: ");

        try {
            int option = sc.nextInt();
            List<Account> accounts = accountService.getAllAccounts();

            switch (option) {
                case 1:
                    FileManager.saveAccountsCSV(accounts, ACCOUNTS_CSV);
                    break;
                case 2:
                    FileManager.serializeAccounts(accounts, ACCOUNTS_DAT);
                    break;
                case 3:
                    FileManager.saveAccountsCSV(accounts, ACCOUNTS_CSV);
                    FileManager.serializeAccounts(accounts, ACCOUNTS_DAT);
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }

    // ==================== 9. LOAD FROM FILE ====================

    /**
     * Load accounts from file — offers both CSV and deserialization.
     * Clears existing accounts before loading to avoid duplicates.
     */
    private static void handleLoadFromFile(Scanner sc) {
        System.out.println("\n--- Load Data ---");
        System.out.println("1. Load from CSV");
        System.out.println("2. Load from Serialized (binary)");
        System.out.print("Choose format: ");

        try {
            int option = sc.nextInt();
            List<Account> loaded;

            switch (option) {
                case 1:
                    loaded = FileManager.loadAccountsCSV(ACCOUNTS_CSV);
                    if (!loaded.isEmpty()) {
                        accountService.clearAll();
                        for (Account acc : loaded) {
                            accountService.addAccount(acc);
                        }
                        System.out.println("Loaded " + loaded.size() + " accounts from CSV.");
                    }
                    break;
                case 2:
                    loaded = FileManager.deserializeAccounts(ACCOUNTS_DAT);
                    if (!loaded.isEmpty()) {
                        accountService.clearAll();
                        for (Account acc : loaded) {
                            accountService.addAccount(acc);
                        }
                        System.out.println("Loaded " + loaded.size() + " accounts from serialized file.");
                    }
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }

    // ==================== SAMPLE DATA ====================

    /**
     * Load sample accounts for demonstration.
     * These are pre-loaded so the app has data to work with on first run.
     */
    private static void loadSampleAccounts() {
        accountService.createSavingsAccount("Ravi Kumar", 50000, 4.5);
        accountService.createCurrentAccount("Priya Sharma", 30000, 10000);
        accountService.createSavingsAccount("Amit Verma", 75000, 5.0);
        accountService.createCurrentAccount("Sneha Reddy", 120000, 25000);

        System.out.println("\nSample accounts loaded. Total: " + accountService.getAccountCount());
    }
}
