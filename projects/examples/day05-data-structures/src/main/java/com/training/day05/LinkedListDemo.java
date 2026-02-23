package com.training.day05;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Day 5 - LinkedList Demo
 *
 * Demonstrates:
 * - addFirst(), addLast(), removeFirst(), removeLast()
 * - Iteration over a LinkedList
 * - LinkedList vs ArrayList performance comparison
 * - LinkedList as both a List and a Queue
 *
 * Key insight:
 * - ArrayList: fast random access by index O(1), slow insert/remove at beginning O(n)
 * - LinkedList: slow random access O(n), fast insert/remove at beginning O(1)
 */
public class LinkedListDemo {

    public static void main(String[] args) {

        // =============================================
        // 1. BASIC LINKED LIST OPERATIONS
        // =============================================
        System.out.println("===== BASIC LINKED LIST OPERATIONS =====");

        LinkedList<String> list = new LinkedList<>();

        // add() — appends to the end (same as ArrayList)
        list.add("Priya");
        list.add("Amit");
        list.add("Sneha");
        System.out.println("After add(): " + list);

        // addFirst() — inserts at the beginning — O(1) for LinkedList!
        list.addFirst("Ravi");
        System.out.println("After addFirst(Ravi): " + list);

        // addLast() — appends to the end (same as add)
        list.addLast("Vikram");
        System.out.println("After addLast(Vikram): " + list);

        // =============================================
        // 2. REMOVING FROM ENDS
        // =============================================
        System.out.println("\n===== REMOVING FROM ENDS =====");

        System.out.println("Before: " + list);

        // removeFirst() — removes from the beginning — O(1)
        String removed = list.removeFirst();
        System.out.println("removeFirst(): " + removed + " | List: " + list);

        // removeLast() — removes from the end — O(1)
        removed = list.removeLast();
        System.out.println("removeLast():  " + removed + " | List: " + list);

        // =============================================
        // 3. ACCESSING ELEMENTS
        // =============================================
        System.out.println("\n===== ACCESSING ELEMENTS =====");

        System.out.println("getFirst(): " + list.getFirst());
        System.out.println("getLast():  " + list.getLast());
        System.out.println("get(1):     " + list.get(1));  // O(n) — must traverse!

        // =============================================
        // 4. ITERATION
        // =============================================
        System.out.println("\n===== ITERATION =====");

        // For-each works the same as ArrayList
        System.out.println("For-each:");
        for (String name : list) {
            System.out.println("  - " + name);
        }

        // For loop with index (works but is O(n) per access — avoid for LinkedList!)
        System.out.println("For loop with index (slow for LinkedList):");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("  [" + i + "] " + list.get(i));
        }

        // =============================================
        // 5. RECENT ITEMS BUFFER (practical use case)
        // =============================================
        System.out.println("\n===== RECENT ITEMS BUFFER =====");
        System.out.println("Keep only the 3 most recent transactions:");

        LinkedList<String> recentTransactions = new LinkedList<>();
        int maxRecent = 3;

        String[] transactions = {"Deposit Rs.5000", "Withdraw Rs.2000", "Deposit Rs.10000",
                                 "Withdraw Rs.3000", "Deposit Rs.8000"};

        for (String txn : transactions) {
            if (recentTransactions.size() >= maxRecent) {
                String oldest = recentTransactions.removeFirst();  // O(1) — remove oldest
                System.out.println("  Evicted oldest: " + oldest);
            }
            recentTransactions.addLast(txn);  // O(1) — add newest to end
            System.out.println("  Added: " + txn + " | Recent: " + recentTransactions);
        }

        System.out.println("Final recent: " + recentTransactions);

        // =============================================
        // 6. PERFORMANCE COMPARISON: ArrayList vs LinkedList
        // =============================================
        System.out.println("\n===== PERFORMANCE COMPARISON =====");
        System.out.println("Inserting 100,000 elements at the BEGINNING...\n");

        // ArrayList — inserting at beginning requires shifting all elements each time
        List<Integer> arrayList = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            arrayList.add(0, i);  // insert at index 0 — O(n) each time
        }
        long arrayListTime = System.currentTimeMillis() - start;
        System.out.println("ArrayList addFirst: " + arrayListTime + " ms");

        // LinkedList — inserting at beginning just re-links the head pointer
        LinkedList<Integer> linkedList = new LinkedList<>();
        start = System.currentTimeMillis();
        for (int i = 0; i < 100_000; i++) {
            linkedList.addFirst(i);  // O(1) each time
        }
        long linkedListTime = System.currentTimeMillis() - start;
        System.out.println("LinkedList addFirst: " + linkedListTime + " ms");

        System.out.println("\nLinkedList is much faster for insertions at the beginning.");

        // =============================================
        // 7. WHEN TO USE WHAT
        // =============================================
        System.out.println("\n===== WHEN TO USE WHAT =====");
        System.out.println("ArrayList:  fast get(index) O(1), default choice for most cases");
        System.out.println("LinkedList: fast addFirst/removeFirst O(1), good for queues/deques");
        System.out.println("HashMap:    fast key lookup O(1), for key-value data");
        System.out.println("Stack:      LIFO (undo/rollback)");
        System.out.println("Queue:      FIFO (process in order)");
    }
}
