package com.training.day05;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Day 5 - Queue Demo (FIFO — First In, First Out)
 *
 * Demonstrates:
 * - offer(), poll(), peek(), isEmpty(), size()
 * - FIFO processing order: first added = first served
 * - Practical example: service request queue
 *
 * Queue is like a bank counter — first person in line gets served first.
 * LinkedList implements the Queue interface.
 *
 * Prefer offer/poll/peek over add/remove/element:
 * - offer/poll/peek return null on failure (safe)
 * - add/remove/element throw exceptions on failure
 */
public class QueueDemo {

    public static void main(String[] args) {

        // =============================================
        // 1. BASIC QUEUE OPERATIONS
        // =============================================
        System.out.println("===== BASIC QUEUE OPERATIONS =====");

        // Queue is an interface — LinkedList is the most common implementation
        Queue<String> serviceQueue = new LinkedList<>();

        // offer(item) — add to the BACK of the queue
        serviceQueue.offer("Request #1 - Open account");
        serviceQueue.offer("Request #2 - Issue checkbook");
        serviceQueue.offer("Request #3 - Loan inquiry");

        System.out.println("Queue: " + serviceQueue);
        System.out.println("Size: " + serviceQueue.size());

        // peek() — look at the FRONT item without removing it
        System.out.println("Front (peek): " + serviceQueue.peek());
        System.out.println("Size after peek: " + serviceQueue.size() + " (unchanged)");

        // poll() — remove and return the FRONT item
        String served = serviceQueue.poll();
        System.out.println("Served (poll): " + served);
        System.out.println("Queue after poll: " + serviceQueue);

        // =============================================
        // 2. PROCESSING ORDER (FIFO)
        // =============================================
        System.out.println("\n===== FIFO PROCESSING ORDER =====");
        System.out.println("Processing all remaining requests...\n");

        // Process until the queue is empty — first in = first out
        while (!serviceQueue.isEmpty()) {
            String request = serviceQueue.poll();  // removes from front
            System.out.println("Processing: " + request);
        }
        System.out.println("\nQueue empty. All requests processed.");
        System.out.println("poll() on empty queue: " + serviceQueue.poll());  // returns null

        // =============================================
        // 3. PRACTICAL EXAMPLE: CUSTOMER SERVICE QUEUE
        // =============================================
        System.out.println("\n===== CUSTOMER SERVICE SIMULATION =====");

        Queue<String> customerQueue = new LinkedList<>();

        // Customers arrive
        System.out.println("--- Customers arriving ---");
        addCustomer(customerQueue, "Ravi");
        addCustomer(customerQueue, "Priya");
        addCustomer(customerQueue, "Amit");

        // Serve first customer
        System.out.println("\n--- Serving customers ---");
        serveCustomer(customerQueue);

        // More customers arrive
        System.out.println("\n--- More customers arriving ---");
        addCustomer(customerQueue, "Sneha");
        addCustomer(customerQueue, "Vikram");

        // Serve remaining customers
        System.out.println("\n--- Serving all remaining ---");
        while (!customerQueue.isEmpty()) {
            serveCustomer(customerQueue);
        }

        // =============================================
        // 4. ITERATING WITHOUT REMOVING
        // =============================================
        System.out.println("\n===== ITERATING WITHOUT REMOVING =====");

        Queue<String> pendingQueue = new LinkedList<>();
        pendingQueue.offer("Task A");
        pendingQueue.offer("Task B");
        pendingQueue.offer("Task C");

        // Iterating with for-each does NOT remove elements
        System.out.println("Pending tasks:");
        for (String task : pendingQueue) {
            System.out.println("  - " + task);
        }
        System.out.println("Queue still has " + pendingQueue.size() + " items (not removed).");
    }

    /**
     * Adds a customer to the queue and shows their position.
     */
    public static void addCustomer(Queue<String> queue, String name) {
        queue.offer(name);
        System.out.println(name + " joined the queue. Position: " + queue.size());
    }

    /**
     * Serves the next customer in line (FIFO — first in, first out).
     */
    public static void serveCustomer(Queue<String> queue) {
        if (queue.isEmpty()) {
            System.out.println("No customers waiting.");
            return;
        }
        String customer = queue.poll();  // removes from front
        System.out.println("Now serving: " + customer + " | Remaining: " + queue.size());
    }
}
