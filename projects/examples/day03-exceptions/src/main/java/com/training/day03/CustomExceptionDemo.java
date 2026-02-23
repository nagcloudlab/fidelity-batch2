package com.training.day03;

/**
 * Day 3 - Custom Exception Demo
 *
 * Demonstrates:
 * - Using a custom checked exception (InvalidAgeException)
 * - throw keyword: manually trigger an exception when a business rule is violated
 * - throws keyword: declare in method signature that a checked exception might be thrown
 * - The caller is FORCED to handle checked exceptions with try-catch
 * - Accessing custom fields from the exception object
 */
public class CustomExceptionDemo {

    public static void main(String[] args) {

        // =============================================
        // 1. USING THE CUSTOM EXCEPTION
        // =============================================
        System.out.println("===== CUSTOM CHECKED EXCEPTION =====");

        // Because InvalidAgeException is a CHECKED exception (extends Exception),
        // the compiler FORCES us to handle it with try-catch.

        // Test case 1: Valid age
        System.out.println("\n--- Test: Valid age (25) ---");
        try {
            validateAge(25);
            System.out.println("Age 25 is valid!");
        } catch (InvalidAgeException e) {
            System.out.println("REJECTED: " + e.getMessage());
            System.out.println("  Invalid age was: " + e.getAge());
        }

        // Test case 2: Negative age
        System.out.println("\n--- Test: Negative age (-5) ---");
        try {
            validateAge(-5);
            System.out.println("This line won't execute.");
        } catch (InvalidAgeException e) {
            System.out.println("REJECTED: " + e.getMessage());
            System.out.println("  Invalid age was: " + e.getAge());
        }

        // Test case 3: Too old
        System.out.println("\n--- Test: Age too high (200) ---");
        try {
            validateAge(200);
            System.out.println("This line won't execute.");
        } catch (InvalidAgeException e) {
            System.out.println("REJECTED: " + e.getMessage());
            System.out.println("  Invalid age was: " + e.getAge());
        }

        // Test case 4: Underage for account
        System.out.println("\n--- Test: Underage (15) ---");
        try {
            validateAge(15);
            System.out.println("This line won't execute.");
        } catch (InvalidAgeException e) {
            System.out.println("REJECTED: " + e.getMessage());
            System.out.println("  Invalid age was: " + e.getAge());
        }

        // =============================================
        // 2. THROW vs THROWS
        // =============================================
        System.out.println("\n===== THROW vs THROWS =====");
        System.out.println("throw  -> used INSIDE method body to actually raise an exception");
        System.out.println("throws -> used in method SIGNATURE to declare what might be thrown");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  void validateAge(int age) throws InvalidAgeException {");
        System.out.println("      if (age < 0) {");
        System.out.println("          throw new InvalidAgeException(\"Age cannot be negative\", age);");
        System.out.println("      }");
        System.out.println("  }");

        // =============================================
        // 3. PATTERN: METHOD THROWS, CALLER CATCHES
        // =============================================
        System.out.println("\n===== DESIGN PATTERN =====");
        System.out.println("Business methods:  Define rules -> throw exceptions when violated");
        System.out.println("Calling code:      Call methods -> catch exceptions -> show user-friendly messages");
        System.out.println();

        // Real-world pattern: register multiple users, handle each failure individually
        int[] ages = {25, -3, 17, 30, 150};
        System.out.println("--- Processing multiple registrations ---");
        for (int age : ages) {
            try {
                validateAge(age);
                System.out.println("Age " + age + ": Registration successful!");
            } catch (InvalidAgeException e) {
                System.out.println("Age " + age + ": FAILED - " + e.getMessage());
            }
        }

        System.out.println("\nProgram completed — no crashes despite multiple invalid ages.");
    }

    /**
     * Validates that an age is within acceptable range for opening a bank account.
     *
     * 'throws InvalidAgeException' in the signature declares that this method
     * MIGHT throw a checked exception. The caller MUST handle it.
     *
     * @param age the age to validate
     * @throws InvalidAgeException if age is negative, under 18, or over 120
     */
    public static void validateAge(int age) throws InvalidAgeException {
        if (age < 0) {
            // 'throw' actually creates and throws the exception
            throw new InvalidAgeException("Age cannot be negative.", age);
        }
        if (age < 18) {
            throw new InvalidAgeException("Must be at least 18 years old to open an account.", age);
        }
        if (age > 120) {
            throw new InvalidAgeException("Age exceeds maximum allowed value of 120.", age);
        }
        // If we reach here, the age is valid — no exception thrown
    }
}
