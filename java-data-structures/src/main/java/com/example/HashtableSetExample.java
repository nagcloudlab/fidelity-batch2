package com.example;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class HashtableSetExample {

    public static void main(String[] args) {

        Set<Account> accounts = new java.util.HashSet<>(); // hash table implementation of Set
        //Set<Account> accounts=new LinkedHashSet<>(); // hash table + DLL

        Account account1 = new Account("213123", 5000);
        Account account2 = new Account("123123", 3000);
        Account account3 = new Account("345345", 2000);
        Account account4 = new Account("567567", 4000);
        Account account5 = new Account("567567", 4000);

        System.out.println(account4.hashCode());
        System.out.println(account5.hashCode());

        System.out.println(account4.equals(account5));

        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        accounts.add(account4);
        accounts.add(account5);


        System.out.println("Number of accounts in the set: " + accounts.size());
        for (Account account : accounts) {
            System.out.println(account.toString());
        }
    }

}
