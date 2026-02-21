package com.training.day04;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Day 4 - Iteration Patterns Demo
 *
 * Demonstrates:
 * - Traditional for loop with index
 * - Enhanced for-each loop
 * - Iterator (safe removal during iteration)
 * - removeIf (Java 8+ — concise conditional removal)
 * - Stream API basics (filter, map, forEach, collect)
 */
public class IterationDemo {

    public static void main(String[] args) {

        // Create a sample list of account balances
        List<Double> balances = new ArrayList<>();
        balances.add(50000.00);
        balances.add(15000.00);
        balances.add(75000.00);
        balances.add(5000.00);
        balances.add(120000.00);
        balances.add(800.00);
        balances.add(30000.00);

        // =============================================
        // 1. TRADITIONAL FOR LOOP (with index)
        // =============================================
        System.out.println("===== FOR LOOP WITH INDEX =====");

        // Use when you need the index (position number)
        for (int i = 0; i < balances.size(); i++) {
            System.out.println("Account " + (i + 1) + ": Rs." + balances.get(i));
        }

        // =============================================
        // 2. ENHANCED FOR-EACH LOOP
        // =============================================
        System.out.println("\n===== ENHANCED FOR-EACH LOOP =====");

        // Simpler syntax — use when you don't need the index
        double total = 0;
        for (double bal : balances) {
            total += bal;
        }
        System.out.println("Total balance: Rs." + total);
        System.out.println("Average balance: Rs." + (total / balances.size()));

        // =============================================
        // 3. ITERATOR — safe removal during iteration
        // =============================================
        System.out.println("\n===== ITERATOR (SAFE REMOVAL) =====");

        // Problem: you CANNOT modify a list during a for-each loop (ConcurrentModificationException)
        // Solution: use an Iterator, which has a safe remove() method

        List<Double> balancesCopy = new ArrayList<>(balances);  // work on a copy
        System.out.println("Before removal: " + balancesCopy);

        Iterator<Double> iterator = balancesCopy.iterator();
        while (iterator.hasNext()) {
            double bal = iterator.next();
            if (bal < 10000) {
                iterator.remove();  // safely removes during iteration
                System.out.println("Removed low balance: Rs." + bal);
            }
        }
        System.out.println("After removing balances < 10000: " + balancesCopy);

        // =============================================
        // 4. removeIf — concise conditional removal (Java 8+)
        // =============================================
        System.out.println("\n===== removeIf (JAVA 8+) =====");

        List<Double> balancesCopy2 = new ArrayList<>(balances);
        System.out.println("Before: " + balancesCopy2);

        // removeIf takes a condition (lambda) — removes all elements that match
        balancesCopy2.removeIf(bal -> bal < 10000);
        System.out.println("After removeIf(bal < 10000): " + balancesCopy2);

        // =============================================
        // 5. STREAM API — filter, map, forEach
        // =============================================
        System.out.println("\n===== STREAM API BASICS =====");

        // Streams let you process collections in a declarative (functional) style
        // stream() -> filter/map/sort -> collect/forEach

        // 5a. Filter: find balances above 50000
        System.out.println("--- Filter: balances > 50000 ---");
        balances.stream()
                .filter(bal -> bal > 50000)  // keep only balances > 50000
                .forEach(bal -> System.out.println("  Rs." + bal));

        // 5b. Map: transform each balance (e.g., apply 4.5% interest)
        System.out.println("\n--- Map: apply 4.5% interest ---");
        balances.stream()
                .map(bal -> bal + (bal * 4.5 / 100))  // add interest to each
                .forEach(bal -> System.out.printf("  Rs.%.2f%n", bal));

        // 5c. Collect: gather stream results into a new list
        System.out.println("\n--- Collect: high-value accounts into a new list ---");
        List<Double> highValue = balances.stream()
                .filter(bal -> bal >= 50000)
                .collect(Collectors.toList());
        System.out.println("High-value balances: " + highValue);

        // 5d. Stream summary operations
        System.out.println("\n--- Stream summary operations ---");
        System.out.println("Count:   " + balances.stream().count());
        System.out.println("Sum:     Rs." + balances.stream().mapToDouble(Double::doubleValue).sum());
        System.out.println("Average: Rs." + balances.stream().mapToDouble(Double::doubleValue).average().orElse(0));
        System.out.println("Max:     Rs." + balances.stream().mapToDouble(Double::doubleValue).max().orElse(0));
        System.out.println("Min:     Rs." + balances.stream().mapToDouble(Double::doubleValue).min().orElse(0));

        // =============================================
        // 6. ITERATING OVER STRINGS
        // =============================================
        System.out.println("\n===== ITERATING OVER STRINGS =====");

        List<String> names = new ArrayList<>();
        names.add("Ravi Kumar");
        names.add("Priya Sharma");
        names.add("Amit Verma");
        names.add("Sneha Reddy");

        // Filter names containing "a" (case-insensitive)
        System.out.println("Names containing 'a':");
        names.stream()
                .filter(n -> n.toLowerCase().contains("a"))
                .forEach(n -> System.out.println("  " + n));
    }
}
