package com.training.day01;

/**
 * Day 1 - Methods Demo
 *
 * Demonstrates:
 * - Static methods (void and with return values)
 * - Parameters and arguments
 * - Return types (void, double, boolean)
 * - Pass-by-value for primitives
 * - Method overloading (same name, different parameters)
 */
public class MethodsDemo {

    // A constant — declared at class level with static final
    static final double MINIMUM_BALANCE = 1000.00;

    public static void main(String[] args) {

        // =============================================
        // 1. CALLING A VOID METHOD (no return value)
        // =============================================
        System.out.println("===== VOID METHODS =====");
        greet();  // calling a method with no parameters

        // =============================================
        // 2. METHOD WITH PARAMETERS
        // =============================================
        System.out.println("\n===== METHODS WITH PARAMETERS =====");
        printReceipt(1001L, 5000.00);  // passing arguments to parameters
        printReceipt(1002L, 10000.00);

        // =============================================
        // 3. METHOD WITH RETURN VALUE
        // =============================================
        System.out.println("\n===== METHODS WITH RETURN VALUES =====");

        // calculateInterest returns a double — we capture it in a variable
        double interest = calculateInterest(50000, 4.5);
        System.out.println("Interest earned: Rs." + interest);

        // =============================================
        // 4. BOOLEAN RETURN — useful for validation
        // =============================================
        System.out.println("\n===== BOOLEAN RETURN (VALIDATION) =====");

        System.out.println("Is 500 valid?  " + isValidAmount(500));
        System.out.println("Is -100 valid? " + isValidAmount(-100));
        System.out.println("Is 0 valid?    " + isValidAmount(0));

        // Using validation in a real scenario
        double balance = 50000;
        double withdrawAmt = 48000;

        if (!isValidAmount(withdrawAmt)) {
            System.out.println("Invalid amount.");
        } else if (!hasSufficientBalance(balance, withdrawAmt, MINIMUM_BALANCE)) {
            System.out.println("Insufficient balance.");
        } else {
            balance -= withdrawAmt;
            System.out.println("Withdrawn Rs." + withdrawAmt + ", new balance: Rs." + balance);
        }

        // =============================================
        // 5. PASS-BY-VALUE FOR PRIMITIVES
        // =============================================
        System.out.println("\n===== PASS-BY-VALUE =====");

        double originalBalance = 50000;
        tryToChange(originalBalance);
        // The original variable is NOT affected — Java passes a copy
        System.out.println("After tryToChange(), balance is still: " + originalBalance);

        // =============================================
        // 6. METHOD OVERLOADING
        // =============================================
        System.out.println("\n===== METHOD OVERLOADING =====");

        balance = 50000;

        // Version 1: deposit(balance, amount) — two parameters
        balance = deposit(balance, 5000);

        // Version 2: deposit(balance, amount, description) — three parameters
        balance = deposit(balance, 10000, "Salary credit");

        System.out.println("Final balance: Rs." + balance);
    }

    // ----- VOID method: does something but returns nothing -----
    public static void greet() {
        System.out.println("Welcome to Simple Bank!");
    }

    // ----- Method with parameters -----
    //        parameter types: long, double
    public static void printReceipt(long accountNo, double amount) {
        System.out.println("Account: " + accountNo + " | Amount: Rs." + amount);
    }

    // ----- Method that returns a double -----
    public static double calculateInterest(double balance, double rate) {
        return balance * rate / 100;  // computed value is returned to the caller
    }

    // ----- Method that returns boolean -----
    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    // ----- Validation method with multiple parameters -----
    public static boolean hasSufficientBalance(double balance, double amount, double minBal) {
        return amount <= (balance - minBal);
    }

    // ----- Demonstrates pass-by-value -----
    public static void tryToChange(double bal) {
        bal = 0;  // changes only the LOCAL copy, not the original
        System.out.println("Inside tryToChange(), bal = " + bal);
    }

    // ----- Overloaded deposit: Version 1 (two parameters) -----
    public static double deposit(double balance, double amount) {
        if (!isValidAmount(amount)) {
            System.out.println("Invalid deposit amount.");
            return balance;
        }
        balance += amount;
        System.out.println("Deposited Rs." + amount + " | New Balance: Rs." + balance);
        return balance;
    }

    // ----- Overloaded deposit: Version 2 (three parameters) -----
    // Same name, different parameter list — Java picks the right one automatically
    public static double deposit(double balance, double amount, String description) {
        System.out.println("Note: " + description);
        return deposit(balance, amount);  // reuses Version 1
    }
}
