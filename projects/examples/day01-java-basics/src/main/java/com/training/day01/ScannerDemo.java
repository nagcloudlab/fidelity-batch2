package com.training.day01;

import java.util.Scanner;  // import the Scanner class

/**
 * Day 1 - Scanner Demo
 *
 * Demonstrates:
 * - Creating a Scanner object for reading console input
 * - Reading different types: String (nextLine), int (nextInt), double (nextDouble)
 * - The nextInt/nextDouble newline gotcha and how to fix it
 * - Command-line arguments (args[])
 *
 * NOTE: This program reads from the console. Run it and type your inputs.
 */
public class ScannerDemo {

    public static void main(String[] args) {

        // =============================================
        // 1. COMMAND-LINE ARGUMENTS
        // =============================================
        System.out.println("===== COMMAND-LINE ARGUMENTS =====");
        if (args.length > 0) {
            // Run with: java ScannerDemo Ravi 50000
            System.out.println("Argument 0: " + args[0]);
            if (args.length > 1) {
                double balance = Double.parseDouble(args[1]);  // convert String to double
                System.out.println("Argument 1 (as double): " + balance);
            }
        } else {
            System.out.println("No command-line arguments provided.");
            System.out.println("Try running with: java ScannerDemo YourName 50000");
        }

        // =============================================
        // 2. CREATING A SCANNER
        // =============================================
        System.out.println("\n===== SCANNER INPUT =====");

        // Scanner reads input from System.in (the keyboard/console)
        Scanner sc = new Scanner(System.in);

        // =============================================
        // 3. READING A STRING
        // =============================================
        System.out.print("Enter your name: ");
        String name = sc.nextLine();  // reads the entire line of text
        System.out.println("Hello, " + name + "!");

        // =============================================
        // 4. READING AN INTEGER
        // =============================================
        System.out.print("Enter your age: ");
        int age = sc.nextInt();  // reads only the integer, leaves newline in buffer
        System.out.println("You are " + age + " years old.");

        // =============================================
        // 5. THE NEWLINE GOTCHA
        // =============================================
        // After nextInt() or nextDouble(), there's a leftover '\n' in the buffer.
        // If we call nextLine() immediately, it reads that empty newline.
        // Fix: consume the leftover newline first.
        sc.nextLine();  // <-- consume the leftover newline character

        // =============================================
        // 6. READING A DOUBLE
        // =============================================
        System.out.print("Enter deposit amount: ");
        double amount = sc.nextDouble();  // reads a decimal number
        System.out.println("You entered: Rs." + amount);

        // Consume leftover newline again
        sc.nextLine();

        // =============================================
        // 7. READING ANOTHER STRING AFTER A NUMBER
        // =============================================
        System.out.print("Enter a remark for this transaction: ");
        String remark = sc.nextLine();  // this works correctly because we consumed the newline
        System.out.println("Remark: " + remark);

        // =============================================
        // 8. SUMMARY
        // =============================================
        System.out.println("\n===== SUMMARY =====");
        System.out.println("Name   : " + name);
        System.out.println("Age    : " + age);
        System.out.println("Amount : Rs." + amount);
        System.out.println("Remark : " + remark);

        // Always close the Scanner when done
        sc.close();
    }
}
