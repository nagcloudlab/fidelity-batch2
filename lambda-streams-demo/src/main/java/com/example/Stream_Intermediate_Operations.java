package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Stream_Intermediate_Operations {
    public static void main(String[] args) {

        /*

            1. filtering

            a. content based filtering
             - filter,dropWhile, takeWhile
            b. count based filtering
             - limit, skip
            c. unique based filtering
             - distinct

         */

        // Example of content based filtering
        List<String> names = List.of("Alice", "Bob", "Charlie", "David", "Eve");
        names.stream()
                .filter(name -> name.startsWith("A"))
                .forEach(System.out::println); // Output: Alice

        // Create 1M accounts in List sorted by balance
        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            accounts.add(new Account("Account" + i,i*10));
        }

        // find accounts with balance < 5000
        accounts.stream()
                .filter(account -> account.getBalance() < 5000)
                .forEach(System.out::println); // Output: Account0, Account1, ..., Account499

        // problem with above code is that it will process all 1M accounts even after finding 5000 accounts with balance < 5000.
        // To optimize this we can use takeWhile which will stop processing once the condition is false.

        accounts.stream()
                .takeWhile(account -> account.getBalance() < 5000)
                .forEach(System.out::println); // Output: Account0, Account1, ..., Account499


        // Example of count based filtering
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        numbers.stream()
                .limit(5)
                .forEach(System.out::println); // Output: 1, 2, 3, 4, 5

        // Example of unique based filtering
        List<String> namesWithDuplicates = List.of("Alice", "Bob", "Charlie", "Alice", "Bob");
        namesWithDuplicates.stream()
                .distinct()
                .forEach(System.out::println); // Output: Alice, Bob, Charlie


        /*

            transformation aka mapping
            -----------------------------

            map, flatMap, mapToInt, mapToDouble, mapToLong, boxed

         */

        // Example of transformation
        List<String> names2 = List.of("Alice", "Bob", "Charlie");
        names2.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println); // Output: ALICE, BOB, CHARLIE


        String[] menu={
            "Pasta, Tomato Sauce, Cheese",
            "Pizza, Tomato Sauce, Cheese, Pepperoni",
            "Salad, Lettuce, Tomato, Cucumber"
        };

        // Example of flatMap
        Arrays.stream(menu)
                .map(line-> line.split(", "))
                .flatMap(parts->Arrays.stream(parts))
                .distinct()
                .forEach(System.out::println);

    }
}
