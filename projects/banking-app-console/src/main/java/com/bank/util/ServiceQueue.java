package com.bank.util;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ServiceQueue wraps java.util.Queue (backed by LinkedList) to manage
 * customer service requests in FIFO (First In, First Out) order.
 *
 * Key concepts demonstrated:
 * - Queue (FIFO): first customer in line gets served first
 * - LinkedList as Queue implementation: efficient add-to-back, remove-from-front
 * - Wrapper pattern: encapsulates Queue and adds banking-specific behavior
 *
 * Day 5: Created for service request processing
 *
 * Real-world analogy:
 *   Like a bank counter queue — customers are served in the order they arrive.
 *   offer() = customer joins the back of the line
 *   poll()  = next customer is called to the counter (removed from front)
 *   peek()  = see who's next without calling them
 */
public class ServiceQueue {

    // Internal Queue data structure, backed by LinkedList
    // LinkedList implements Queue — efficient O(1) operations at both ends
    private Queue<String> queue;

    /**
     * Create an empty service request queue.
     */
    public ServiceQueue() {
        this.queue = new LinkedList<>();
    }

    /**
     * Add a new service request to the back of the queue.
     * Uses offer() instead of add() — offer() returns false on failure
     * instead of throwing an exception.
     *
     * @param request Description of the service request
     */
    public void addRequest(String request) {
        queue.offer(request);
        System.out.println("Request added: \"" + request + "\"");
        System.out.println("Queue position: " + queue.size());
    }

    /**
     * Process the next service request (remove from front of queue).
     * Uses poll() instead of remove() — poll() returns null on empty queue
     * instead of throwing an exception.
     *
     * @return The processed request description, or null if queue is empty
     */
    public String processNext() {
        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return null;
        }

        String request = queue.poll();   // remove from front — FIFO
        System.out.println("Processing: \"" + request + "\"");
        System.out.println("Remaining in queue: " + queue.size());
        return request;
    }

    /**
     * Process ALL pending requests in order.
     * Keeps polling until the queue is empty.
     */
    public void processAll() {
        if (queue.isEmpty()) {
            System.out.println("No pending requests to process.");
            return;
        }

        System.out.println("\n--- Processing All Requests ---");
        int count = 0;
        while (!queue.isEmpty()) {
            String request = queue.poll();
            count++;
            System.out.println("  " + count + ". Processed: \"" + request + "\"");
        }
        System.out.println("All " + count + " request(s) processed.");
    }

    /**
     * View all pending requests without removing them.
     * Iterating over a Queue does NOT remove items.
     */
    public void viewPending() {
        if (queue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }
        System.out.println("\n--- Pending Requests (" + queue.size() + ") ---");
        int i = 1;
        for (String request : queue) {
            System.out.println("  " + i + ". " + request);
            i++;
        }
    }

    /**
     * Get the number of pending requests.
     *
     * @return Number of requests in the queue
     */
    public int getPendingCount() {
        return queue.size();
    }

    /**
     * Check if there are any pending requests.
     *
     * @return true if the queue is empty
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
