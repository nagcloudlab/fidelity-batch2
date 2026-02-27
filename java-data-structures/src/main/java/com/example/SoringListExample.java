package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * sorting algorithms:
 * 
 * 1. Bubble Sort
 * 2. Selection Sort
 * 3. Insertion Sort
 * 4. Merge Sort
 * 5. Quick Sort
 * 6. Heap Sort
 * 7. Radix Sort
 * 8. Counting Sort
 * 9. Bucket Sort
 * 10. Shell Sort
 * 
 * 
 * ---------------
 * 
 * - compare
 * - swap
 * 
 * ---------------
 */

public class SoringListExample {

    public static void main(String[] args) {

        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account("213123", 5000));
        accounts.add(new Account("123123", 3000));
        accounts.add(new Account("345345", 2000));
        accounts.add(new Account("567567", 7000));
        accounts.add(new Account("454656", 2000));
        accounts.add(new Account("454656", 2000));

        displayAccounts(accounts);
        //Collections.sort(accounts);
        System.out.println("After sorting:");
        displayAccounts(accounts);

        // author: foo, sort by balance
        // BalanceComparator balanceComparator = new BalanceComparator();
        Collections.sort(accounts, (a1, a2) -> Double.compare(a1.getBalance(), a2.getBalance()));
        System.out.println("After sorting by balance:");
        displayAccounts(accounts);

    }

    private static void displayAccounts(List<Account> accounts) {
        System.out.println("Accounts:");
        for (Account account : accounts) {
            System.out.println(account.toString());
        }
    }

}

class BalanceComparator implements java.util.Comparator<Account> {
    @Override
    public int compare(Account a1, Account a2) {
        if (a1.getBalance() < a2.getBalance()) {
            return -1;
        } else if (a1.getBalance() > a2.getBalance()) {
            return 1;
        } else {
            return 0;
        }
    }
}
