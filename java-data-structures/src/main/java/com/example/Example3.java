package com.example;

import java.util.Iterator;

import com.example.util.LinkedList;

public class Example3 {
    public static void main(String[] args) {

        LinkedList<Integer> numbers = new LinkedList<>();
        numbers.add(1); // auto-boxing: int to Integer
        numbers.add(3);
        numbers.add(1, 2);

        System.out.println(numbers.get(0));
        System.out.println(numbers.get(1));
        System.out.println(numbers.get(2));

        LinkedList<String> names = new LinkedList<>();
        names.add("Alice");
        names.add("Bob");
        names.add(1, "Charlie");

        System.out.println(names.get(0));
        System.out.println(names.get(1));
        System.out.println(names.get(2));

        LinkedList<Account> accounts = new LinkedList<>();
        accounts.add(new Account("Alice", 1000));
        accounts.add(new Account("Charlie", 3000));
        accounts.add(1, new Account("Bob", 2000));

        // -----------------------------------------
        // Traversing/Iterating the list
        // -----------------------------------------
        display(accounts);

    }

    public static void display(LinkedList<Account> accounts) {
        // way-1: Traditional For Loop
        // for (int i = 0; i < accounts.size(); i++) {
        // Account account = accounts.get(i);
        // System.out.println(account.getAccountNumber() + ": " + account.getBalance());
        // }
        // way-2: Iterator
        // Iterator<Account> iterator = accounts.iterator();
        // while (iterator.hasNext()) {
        // Account account = iterator.next();
        // System.out.println(account.getAccountNumber() + ": " + account.getBalance());
        // }
        // way-3: For-Each Loop (Enhanced For Loop) ( 1.5 + )
        for (Account account : accounts) {
            System.out.println(account.getAccountNumber() + ": " + account.getBalance());
        }
    }
}
