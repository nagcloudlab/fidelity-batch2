package com.training.day04;

import java.util.ArrayList;
import java.util.List;

/**
 * Day 4 - ArrayList Demo
 *
 * Demonstrates:
 * - Creating an ArrayList (declare as List interface, instantiate as ArrayList)
 * - add(), get(), set(), remove(), size(), contains(), isEmpty()
 * - Iteration: for loop with index, enhanced for-each loop
 * - ArrayList vs Array comparison
 *
 * ArrayList is a RESIZABLE list backed by an array internally.
 * It grows automatically when you add elements — no fixed size limit.
 */
public class ArrayListDemo {

    public static void main(String[] args) {

        // =============================================
        // 1. CREATING AN ARRAYLIST
        // =============================================
        System.out.println("===== CREATING AN ARRAYLIST =====");

        // Best practice: declare as List (interface), instantiate as ArrayList (implementation)
        // The <String> is a "generic type" — it tells Java what type of objects the list holds
        List<String> accounts = new ArrayList<>();

        System.out.println("Empty list created. Size: " + accounts.size());
        System.out.println("Is empty? " + accounts.isEmpty());

        // =============================================
        // 2. ADDING ELEMENTS
        // =============================================
        System.out.println("\n===== ADDING ELEMENTS =====");

        // add(item) — appends to the end of the list
        accounts.add("Ravi Kumar");
        accounts.add("Priya Sharma");
        accounts.add("Amit Verma");
        accounts.add("Sneha Reddy");

        System.out.println("After adding 4 names: " + accounts);
        System.out.println("Size: " + accounts.size());

        // add(index, item) — inserts at a specific position, shifts others right
        accounts.add(1, "Zara Khan");  // insert at index 1
        System.out.println("After inserting Zara at index 1: " + accounts);

        // =============================================
        // 3. GETTING ELEMENTS
        // =============================================
        System.out.println("\n===== GETTING ELEMENTS =====");

        // get(index) — retrieves the element at the given position (0-based)
        String first = accounts.get(0);   // first element
        String third = accounts.get(2);   // third element
        System.out.println("First: " + first);
        System.out.println("Third: " + third);

        // =============================================
        // 4. UPDATING ELEMENTS
        // =============================================
        System.out.println("\n===== UPDATING ELEMENTS =====");

        // set(index, item) — replaces the element at the given position
        System.out.println("Before update: " + accounts.get(0));
        accounts.set(0, "Ravi K. Kumar");
        System.out.println("After update:  " + accounts.get(0));

        // =============================================
        // 5. REMOVING ELEMENTS
        // =============================================
        System.out.println("\n===== REMOVING ELEMENTS =====");

        System.out.println("Before removal: " + accounts);

        // remove(index) — removes by position
        accounts.remove(1);  // removes "Zara Khan" (index 1)
        System.out.println("After remove(1): " + accounts);

        // remove(object) — removes by value (first occurrence)
        accounts.remove("Amit Verma");
        System.out.println("After remove(\"Amit Verma\"): " + accounts);

        // =============================================
        // 6. CHECKING CONTENTS
        // =============================================
        System.out.println("\n===== CHECKING CONTENTS =====");

        // contains(item) — checks if the list has the given element
        System.out.println("Contains 'Priya Sharma'? " + accounts.contains("Priya Sharma"));
        System.out.println("Contains 'Amit Verma'?   " + accounts.contains("Amit Verma"));
        System.out.println("Is empty? " + accounts.isEmpty());
        System.out.println("Size: " + accounts.size());

        // =============================================
        // 7. ITERATION — FOR LOOP WITH INDEX
        // =============================================
        System.out.println("\n===== ITERATION: FOR LOOP WITH INDEX =====");

        for (int i = 0; i < accounts.size(); i++) {
            System.out.println((i + 1) + ". " + accounts.get(i));
        }

        // =============================================
        // 8. ITERATION — ENHANCED FOR-EACH
        // =============================================
        System.out.println("\n===== ITERATION: FOR-EACH =====");

        // Simpler syntax when you don't need the index
        for (String name : accounts) {
            System.out.println("Account holder: " + name);
        }

        // =============================================
        // 9. ARRAYLIST WITH NUMBERS
        // =============================================
        System.out.println("\n===== ARRAYLIST WITH NUMBERS =====");

        // For primitives, use wrapper classes: int -> Integer, double -> Double
        List<Double> balances = new ArrayList<>();
        balances.add(50000.00);
        balances.add(75000.00);
        balances.add(30000.00);
        balances.add(120000.00);

        // Calculate total using for-each
        double total = 0;
        for (double bal : balances) {
            total += bal;
        }
        System.out.println("Balances: " + balances);
        System.out.println("Total: Rs." + total);
        System.out.println("Average: Rs." + (total / balances.size()));
    }
}
