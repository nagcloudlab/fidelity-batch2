package com.training.day01;

/**
 * Day 1 - Control Flow Demo
 *
 * Demonstrates:
 * - if / else-if / else
 * - switch statement
 * - for loop
 * - while loop
 * - do-while loop
 * - enhanced for loop (for-each)
 * - break and continue
 */
public class ControlFlowDemo {

    public static void main(String[] args) {

        // =============================================
        // 1. IF / ELSE-IF / ELSE
        // =============================================
        System.out.println("===== IF / ELSE-IF / ELSE =====");

        double balance = 50000;
        double withdrawal = 60000;
        double minimumBalance = 1000;

        // Check multiple conditions in order — first true branch executes
        if (withdrawal <= 0) {
            System.out.println("Invalid amount.");
        } else if (withdrawal > balance - minimumBalance) {
            System.out.println("Insufficient balance. Max withdrawal: Rs." + (balance - minimumBalance));
        } else {
            balance -= withdrawal;
            System.out.println("Success. New balance: Rs." + balance);
        }

        // =============================================
        // 2. SWITCH STATEMENT
        // =============================================
        System.out.println("\n===== SWITCH STATEMENT =====");

        int choice = 2;

        // switch compares the value against each case
        switch (choice) {
            case 1:
                System.out.println("You chose: Check Balance");
                break;  // without break, execution "falls through" to the next case
            case 2:
                System.out.println("You chose: Deposit");
                break;
            case 3:
                System.out.println("You chose: Withdraw");
                break;
            default:
                System.out.println("Invalid choice");
        }

        // =============================================
        // 3. FOR LOOP — use when you know how many iterations
        // =============================================
        System.out.println("\n===== FOR LOOP =====");

        // for (initialization; condition; update)
        for (int i = 1; i <= 5; i++) {
            System.out.println("Transaction #" + i);
        }

        // =============================================
        // 4. WHILE LOOP — use when you don't know how many iterations
        // =============================================
        System.out.println("\n===== WHILE LOOP =====");

        int attempts = 0;
        // condition is checked BEFORE each iteration
        while (attempts < 3) {
            System.out.println("Login attempt " + (attempts + 1));
            attempts++;
        }

        // =============================================
        // 5. DO-WHILE LOOP — always runs at least once
        // =============================================
        System.out.println("\n===== DO-WHILE LOOP =====");

        int menuChoice;
        int iteration = 0;
        do {
            // The body runs FIRST, then the condition is checked
            System.out.println("Showing menu (iteration " + iteration + ")...");
            menuChoice = 4;  // simulate user choosing "Exit"
            iteration++;
        } while (menuChoice != 4);
        System.out.println("Exited menu loop.");

        // =============================================
        // 6. ENHANCED FOR LOOP (for-each)
        // =============================================
        System.out.println("\n===== ENHANCED FOR LOOP (FOR-EACH) =====");

        // Works with arrays and collections
        String[] accountHolders = {"Ravi", "Priya", "Amit", "Sneha"};

        // for (Type variable : collection) — reads each element one by one
        for (String holder : accountHolders) {
            System.out.println("Account holder: " + holder);
        }

        // Also works with number arrays
        double[] balances = {50000, 75000, 30000, 120000};
        double total = 0;
        for (double bal : balances) {
            total += bal;
        }
        System.out.println("Total balance across all accounts: Rs." + total);

        // =============================================
        // 7. BREAK AND CONTINUE
        // =============================================
        System.out.println("\n===== BREAK AND CONTINUE =====");

        for (int i = 1; i <= 10; i++) {
            if (i == 3) {
                continue;  // skip iteration 3 — jump to next iteration
            }
            if (i > 5) {
                break;  // stop the loop entirely when i exceeds 5
            }
            System.out.println("i = " + i);  // prints: 1, 2, 4, 5
        }

        // =============================================
        // 8. BANKING MENU EXAMPLE (combining do-while + switch)
        // =============================================
        System.out.println("\n===== BANKING MENU (DO-WHILE + SWITCH) =====");

        double bankBalance = 50000.00;
        // Simulated choices to avoid needing Scanner input
        int[] simulatedChoices = {1, 2, 3, 4};

        for (int simChoice : simulatedChoices) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Check Balance  2. Deposit  3. Withdraw  4. Exit");
            System.out.println("Choice: " + simChoice);

            switch (simChoice) {
                case 1:
                    System.out.println("Balance: Rs." + bankBalance);
                    break;
                case 2:
                    bankBalance += 5000;
                    System.out.println("Deposited Rs.5000. New Balance: Rs." + bankBalance);
                    break;
                case 3:
                    bankBalance -= 2000;
                    System.out.println("Withdrawn Rs.2000. New Balance: Rs." + bankBalance);
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
