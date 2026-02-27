package com.example;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class ProducerConsumerExample {

    public static void main(String[] args) {


        //Queue<String> queue = new LinkedList<>();
        ArrayBlockingQueue<String> keys = new ArrayBlockingQueue<>(5); // Bounded queue with a capacity of 5

        Runnable produceTask = () -> {
            // Code for producing items
            while (true) {
                System.out.println("Producing an item...");
                try {
                    Thread.sleep(1000); // Simulate time taken to produce an item
                    // Add produced item to the queue
                    keys.put("Item " + System.currentTimeMillis());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        };

        Runnable consumeTask = () -> {
            // Code for consuming items
            while (true) {
                try {
                    Thread.sleep(8000); // Simulate time taken to consume an item
                    // Remove an item from the queue if available
                    String item = keys.poll();
                    System.out.println("Consuming an item...");
                    if (item != null) {
                        System.out.println("Consumed: " + item);
                    } else {
                        System.out.println("No items to consume.");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        };

        Thread producerThread = new Thread(produceTask);
        Thread consumerThread = new Thread(consumeTask);

        producerThread.start();
        consumerThread.start();


    }


}
