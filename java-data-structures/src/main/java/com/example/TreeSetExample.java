package com.example;

import java.util.Set;

public class TreeSetExample {
    public static void main(String[] args) {


        Set<Account> accounts = new java.util.TreeSet<>(
                (a1,a2)-> a2.getAccountNumber().compareTo(a1.getAccountNumber())
        ); // tree implementation of Set ( b-tree -> red-black tree) // sorted set
        accounts.add(new Account("567567", 4000));
        accounts.add(new Account("213123", 5000));
        accounts.add(new Account("123123", 3000));
        accounts.add(new Account("345345", 2000));
        accounts.add(new Account("567567", 4000));

        System.out.println("Number of accounts in the set: " + accounts.size());
        for (Account account : accounts) {
            System.out.println(account.toString());
        }

    }
}
