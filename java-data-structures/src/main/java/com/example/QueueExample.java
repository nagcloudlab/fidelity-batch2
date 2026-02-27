package com.example;

import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class QueueExample {

    public static void main(String[] args) {

//        Queue<String> queue = new LinkedList<>();
//        queue.offer("First");
//        queue.offer("Second");
//        queue.offer("Third");
//
//        System.out.println("Queue size: " + queue.size());
//        //System.out.println("Head of the queue: " + queue.peek());
//
//        while (!queue.isEmpty()) {
//            String element = queue.poll();
//            System.out.println("Processing: " + element);
//        }
//
//        System.out.println("Queue size after processing: " + queue.size());

        //--------------------------------------------------------

        PriorityQueue<Account> priorityQueue=new PriorityQueue<>(
                (a1,a2)-> Double.compare(a1.getBalance(), a2.getBalance())
        );
        priorityQueue.offer(new Account("567567", 4000));
        priorityQueue.offer(new Account("213123", 5000));
        priorityQueue.offer(new Account("123123", 3000));
        priorityQueue.offer(new Account("345345", 2000));

        while (!priorityQueue.isEmpty()) {
            Account account = priorityQueue.poll();
            System.out.println("Processing: " + account.toString());
        }

        //--------------------------------------------------------

        Deque<Account> deque=new LinkedList<>();
        deque.offerFirst(new Account("567567", 4000));
        deque.offerFirst(new Account("213123", 5000));
        deque.offerLast(new Account("123123", 3000));
        deque.offerLast(new Account("345345", 2000));

        while (!deque.isEmpty()) {
            Account account = deque.pollFirst();
            System.out.println("Processing from front: " + account.toString());
        }

        //--------------------------------------------------------

    }

}
