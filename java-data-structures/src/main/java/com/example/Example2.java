package com.example;

public class Example2 {

    public static void main(String[] args) {

        int[] numbers = { 1, 2, 3, 4, 5 };
        int[] moreNumbers = new int[5];
        numbers[0] = 10;
        moreNumbers[0] = 20;
        System.out.println("First number: " + numbers[0]);
        System.out.println("First more number: " + moreNumbers[0]);

        Account[] accounts = new Account[2];
        accounts[0] = new Account("12345", 1000.0);
        accounts[1] = new Account("67890", 2000.0);

        // Limitations of Array structure
        // ----------------------------------
        // 1. Fixed Size: Once an array is created, its size cannot be changed.
        // 2. Homogeneous Data: Arrays can only store elements of the same type.
        // 3. Contiguous Memory: Arrays require contiguous memory allocation, which can
        // lead to fragmentation and inefficient use of memory.
        // 5. Insetion and Deletion: Inserting or deleting elements in an array can be
        // inefficient, as it may require shifting elements to maintain the order of the
        // array. -> O(n)

    }

}
