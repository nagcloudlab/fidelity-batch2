package com.training.day04;

import java.util.HashMap;
import java.util.Map;

/**
 * Day 4 - HashMap Demo
 *
 * Demonstrates:
 * - Creating a HashMap (key-value pairs)
 * - put(), get(), containsKey(), getOrDefault(), remove()
 * - Iterating: keySet(), values(), entrySet()
 * - Key rules: unique keys, null on missing key, duplicate key replaces value
 *
 * HashMap provides O(1) instant lookup by key — much faster than searching a List.
 */
public class HashMapDemo {

    public static void main(String[] args) {

        // =============================================
        // 1. CREATING A HASHMAP
        // =============================================
        System.out.println("===== CREATING A HASHMAP =====");

        // Map<KeyType, ValueType>
        // Think of it like a dictionary: key = word, value = definition
        Map<Long, String> accountNames = new HashMap<>();

        System.out.println("Empty map. Size: " + accountNames.size());

        // =============================================
        // 2. PUTTING KEY-VALUE PAIRS
        // =============================================
        System.out.println("\n===== PUT (ADD / UPDATE) =====");

        // put(key, value) — adds a new entry or updates an existing key
        accountNames.put(1001L, "Ravi Kumar");
        accountNames.put(1002L, "Priya Sharma");
        accountNames.put(1003L, "Amit Verma");
        accountNames.put(1004L, "Sneha Reddy");

        System.out.println("Map after 4 puts: " + accountNames);
        System.out.println("Size: " + accountNames.size());

        // =============================================
        // 3. GETTING VALUES BY KEY
        // =============================================
        System.out.println("\n===== GET (RETRIEVE) =====");

        // get(key) — returns the value for the given key, or null if not found
        String name = accountNames.get(1001L);
        System.out.println("Account 1001: " + name);

        // Getting a key that doesn't exist returns null
        String missing = accountNames.get(9999L);
        System.out.println("Account 9999: " + missing);  // null

        // =============================================
        // 4. getOrDefault — safer alternative to get
        // =============================================
        System.out.println("\n===== getOrDefault =====");

        // Returns the default value if the key doesn't exist (avoids null)
        String result = accountNames.getOrDefault(9999L, "Account not found");
        System.out.println("Account 9999: " + result);

        // =============================================
        // 5. CHECKING IF A KEY EXISTS
        // =============================================
        System.out.println("\n===== containsKey =====");

        // containsKey(key) — returns true if the key is in the map
        System.out.println("Contains 1001? " + accountNames.containsKey(1001L));
        System.out.println("Contains 9999? " + accountNames.containsKey(9999L));

        // =============================================
        // 6. DUPLICATE KEY REPLACES THE OLD VALUE
        // =============================================
        System.out.println("\n===== DUPLICATE KEY =====");

        System.out.println("Before: Account 1001 = " + accountNames.get(1001L));
        accountNames.put(1001L, "Ravi K. Kumar");  // replaces the old value
        System.out.println("After:  Account 1001 = " + accountNames.get(1001L));

        // =============================================
        // 7. REMOVING ENTRIES
        // =============================================
        System.out.println("\n===== REMOVE =====");

        System.out.println("Before removal: " + accountNames);
        accountNames.remove(1003L);
        System.out.println("After remove(1003): " + accountNames);

        // =============================================
        // 8. ITERATING — keySet() (all keys)
        // =============================================
        System.out.println("\n===== ITERATE: keySet() =====");

        for (Long key : accountNames.keySet()) {
            System.out.println("Key: " + key);
        }

        // =============================================
        // 9. ITERATING — values() (all values)
        // =============================================
        System.out.println("\n===== ITERATE: values() =====");

        for (String value : accountNames.values()) {
            System.out.println("Value: " + value);
        }

        // =============================================
        // 10. ITERATING — entrySet() (key-value pairs)
        // =============================================
        System.out.println("\n===== ITERATE: entrySet() =====");

        // This is the most common way to iterate over a Map
        for (Map.Entry<Long, String> entry : accountNames.entrySet()) {
            System.out.println("Account #" + entry.getKey() + " -> " + entry.getValue());
        }

        // =============================================
        // 11. PRACTICAL EXAMPLE: Account Balance Lookup
        // =============================================
        System.out.println("\n===== PRACTICAL: BALANCE LOOKUP =====");

        Map<Long, Double> balanceMap = new HashMap<>();
        balanceMap.put(1001L, 50000.00);
        balanceMap.put(1002L, 75000.00);
        balanceMap.put(1004L, 120000.00);

        // O(1) instant lookup — no looping needed
        long searchAccNo = 1002L;
        if (balanceMap.containsKey(searchAccNo)) {
            System.out.println("Account #" + searchAccNo + " balance: Rs." + balanceMap.get(searchAccNo));
        } else {
            System.out.println("Account #" + searchAccNo + " not found.");
        }

        // Calculate total balance across all accounts
        double total = 0;
        for (double bal : balanceMap.values()) {
            total += bal;
        }
        System.out.println("Total balance: Rs." + total);
    }
}
