package com.training.day01;

/**
 * Day 1 - Variables & Data Types Demo
 *
 * Demonstrates:
 * - Primitive types (int, long, double, boolean, char)
 * - Reference type (String)
 * - Type casting (implicit and explicit)
 * - Arithmetic, relational, logical, and assignment operators
 */
public class VariablesDemo {

    public static void main(String[] args) {

        // =============================================
        // 1. PRIMITIVE DATA TYPES
        // =============================================
        System.out.println("===== PRIMITIVE DATA TYPES =====");

        // int — 4 bytes — stores whole numbers
        int age = 25;
        System.out.println("int age       = " + age);

        // long — 8 bytes — stores large whole numbers (note the L suffix)
        long accountNumber = 1001L;
        System.out.println("long accNo    = " + accountNumber);

        // double — 8 bytes — stores decimal numbers
        double balance = 50000.00;
        System.out.println("double balance= " + balance);

        // boolean — 1 bit — stores true or false
        boolean isActive = true;
        System.out.println("boolean active= " + isActive);

        // char — 2 bytes — stores a single character (use single quotes)
        char grade = 'A';
        System.out.println("char grade    = " + grade);

        // =============================================
        // 2. REFERENCE TYPE: String
        // =============================================
        System.out.println("\n===== STRING (REFERENCE TYPE) =====");

        // String — holds text (use double quotes)
        String name = "Ravi Kumar";
        System.out.println("String name   = " + name);

        // String concatenation with the + operator
        System.out.println("Account Holder: " + name + ", Account #" + accountNumber);

        // =============================================
        // 3. TYPE CASTING
        // =============================================
        System.out.println("\n===== TYPE CASTING =====");

        // Implicit casting (widening) — small type to large type — safe, automatic
        int a = 100;
        double b = a;  // int automatically becomes double
        System.out.println("Implicit: int " + a + " -> double " + b);

        // Explicit casting (narrowing) — large type to small type — may lose data
        double x = 9.99;
        int y = (int) x;  // decimal part is truncated (NOT rounded)
        System.out.println("Explicit: double " + x + " -> int " + y + " (truncated, not rounded)");

        // =============================================
        // 4. ARITHMETIC OPERATORS
        // =============================================
        System.out.println("\n===== ARITHMETIC OPERATORS =====");

        double deposit = 5000;
        double updatedBalance = balance + deposit;  // addition
        System.out.println(balance + " + " + deposit + " = " + updatedBalance);

        double withdrawal = 2000;
        updatedBalance = updatedBalance - withdrawal;  // subtraction
        System.out.println("After withdrawing " + withdrawal + ": " + updatedBalance);

        double interestRate = 4.5;
        double interest = balance * interestRate / 100;  // multiplication and division
        System.out.println("Interest at " + interestRate + "% on " + balance + " = " + interest);

        int remainder = 10 % 3;  // modulus — gives the remainder
        System.out.println("10 % 3 = " + remainder + " (remainder)");

        // =============================================
        // 5. RELATIONAL OPERATORS (result is always boolean)
        // =============================================
        System.out.println("\n===== RELATIONAL OPERATORS =====");

        System.out.println("balance > 0        : " + (balance > 0));
        System.out.println("balance == 50000   : " + (balance == 50000));
        System.out.println("balance != 0       : " + (balance != 0));
        System.out.println("balance >= 100000  : " + (balance >= 100000));
        System.out.println("balance < 100000   : " + (balance < 100000));

        // =============================================
        // 6. LOGICAL OPERATORS
        // =============================================
        System.out.println("\n===== LOGICAL OPERATORS =====");

        // && (AND) — both conditions must be true
        System.out.println("isActive && balance > 0      : " + (isActive && balance > 0));

        // || (OR) — at least one condition must be true
        System.out.println("balance > 100000 || isActive : " + (balance > 100000 || isActive));

        // ! (NOT) — flips the boolean value
        System.out.println("!isActive                    : " + (!isActive));

        // =============================================
        // 7. ASSIGNMENT SHORTCUTS
        // =============================================
        System.out.println("\n===== ASSIGNMENT SHORTCUTS =====");

        double bal = 50000;
        System.out.println("Starting balance: " + bal);

        bal += 5000;  // same as: bal = bal + 5000
        System.out.println("bal += 5000 -> " + bal);

        bal -= 2000;  // same as: bal = bal - 2000
        System.out.println("bal -= 2000 -> " + bal);

        // =============================================
        // 8. INCREMENT / DECREMENT
        // =============================================
        System.out.println("\n===== INCREMENT / DECREMENT =====");

        int count = 0;
        System.out.println("Initial count: " + count);
        count++;  // increment by 1
        System.out.println("After count++: " + count);
        count--;  // decrement by 1
        System.out.println("After count--: " + count);
    }
}
