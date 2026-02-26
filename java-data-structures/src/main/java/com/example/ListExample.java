package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ListExample {

    public static void main(String[] args) {

        /*
         * 
         * Vector -> dynamic array, synchronized, thread-safe, legacy class
         * ArrayList -> dynamic array, not synchronized, not thread-safe, introduced in
         * LinkedList -> doubly linked list, not synchronized, not thread-safe,
         * 
         * 
         * ArrayList
         * 
         * add() -> O(1) amortized, O(n) worst case
         * add(index, element) -> O(n) because of shifting elements
         * 
         * LinkedList
         * 
         * add() -> O(1) if adding at the end, O(n) if adding at a specific index
         * add(index, element) -> O(n) because of traversal to the index
         * 
         * 
         */
        // compare(new Vector<>());
        // compare(new ArrayList<>());
        // compare(new java.util.LinkedList<>());

        List<String> list = new ArrayList<>();

        // add() -> O(1) amortized, O(n) worst case
        list.add("Hello");

        // add(index, element) -> O(n) because of shifting elements
        list.add(0, "World");

        // get(index) -> O(1) for ArrayList, O(n) for LinkedList
        String element = list.get(0);
        System.out.println(element);

        // remove(index) -> O(n) for ArrayList because of shifting elements, O(1) for
        // LinkedList
        list.remove(0);

        // remove(object) -> O(n) for both ArrayList and LinkedList because of traversal
        // to find the object
        list.remove("Hello");

        // size() -> O(1) for both ArrayList and LinkedList
        int size = list.size();
        System.out.println("Size: " + size);

        // clear() -> O(n) for ArrayList because it sets all elements to null, O(1) for
        // LinkedList
        list.clear();

        // isEmpty() -> O(1) for both ArrayList and LinkedList
        boolean isEmpty = list.isEmpty();
        System.out.println("Is empty: " + isEmpty);

        // contains(object) -> O(n) for both ArrayList and LinkedList because of
        // traversal to find the object
        boolean contains = list.contains("Hello");
        System.out.println("Contains 'Hello': " + contains);

        // iterator() -> O(1) for both ArrayList and LinkedList
        for (String item : list) {
            System.out.println(item);
        }

        // toArray() -> O(n) for both ArrayList and LinkedList because it creates a new
        // array and copies elements
        Object[] array = list.toArray();

        // ....

    }

    private static void compare(List<String> list) {
        long startTime = System.nanoTime();
        // 1M elements
        for (int i = 0; i < 1000000; i++) {
            list.add("Element " + i);
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Time taken: " + duration + " nanoseconds");
    }

}
