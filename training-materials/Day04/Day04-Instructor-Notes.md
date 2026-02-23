# Day 4 — Collections + Searching Algorithms

## Instructor Guide

> **Duration**: Full day
> **Pre-requisite**: Day 3 BankApp (Account hierarchy + custom exceptions)
> **Case Study**: Replace fixed arrays with ArrayList, add HashMap for instant lookup, implement search algorithms
> **Goal by end of day**: Trainees can use ArrayList, HashMap, and implement Linear/Binary search on object collections

---

## Part A: Collections (Topics 1–4)

---

### Topic 1: Collection Framework Overview

#### Key Points (10 min)

- **The problem from Day 3**: We used `Account[10]` — fixed size, manual tracking with `accountCount`, can't easily remove accounts.
- **Collections** = Java's built-in dynamic data structures. They grow/shrink automatically.
- **Collection framework hierarchy** (simplified):

```
          Collection (interface)
         /          \
      List          Set
   (ordered,     (unique,
    duplicates    no order)
    allowed)
      |              |
  ArrayList       HashSet
  LinkedList      TreeSet

          Map (separate interface)
          |
       HashMap
       TreeMap
```

- **Key differences**:

| | List | Set | Map |
|---|---|---|---|
| Duplicates? | Yes | No | Keys: No, Values: Yes |
| Order? | Insertion order | No guarantee (HashSet) | No guarantee (HashMap) |
| Access by? | Index | Iteration | Key |
| Banking use | List of transactions | Unique account types | Account lookup by number |

- **Generics** — type safety: `ArrayList<Account>` means "a list that only holds Account objects."

#### Teaching Tip

> "Think of it this way:
> - **List** = a numbered queue at the bank (position matters, same person can queue twice)
> - **Set** = a VIP membership list (each person appears once)
> - **Map** = a locker room (each locker number maps to one person's belongings)"

---

### Topic 2: List vs Set vs Map

#### Key Points (5 min)

- Just reinforce with a quick comparison. Don't deep-dive into Set since the TOC focuses on ArrayList and HashMap.
- Mention `Set` exists for completeness — students may encounter it in interviews.

#### Quick Demo

```java
// List — ordered, allows duplicates
List<String> transactions = new ArrayList<>();
transactions.add("DEPOSIT");
transactions.add("WITHDRAW");
transactions.add("DEPOSIT");    // duplicate allowed
// [DEPOSIT, WITHDRAW, DEPOSIT]

// Set — unique, no order guarantee
Set<String> accountTypes = new HashSet<>();
accountTypes.add("SAVINGS");
accountTypes.add("CURRENT");
accountTypes.add("SAVINGS");    // ignored — duplicate
// [SAVINGS, CURRENT]

// Map — key-value pairs
Map<Long, String> accountNames = new HashMap<>();
accountNames.put(1001L, "Ravi Kumar");
accountNames.put(1002L, "Priya Sharma");
String name = accountNames.get(1001L);  // "Ravi Kumar"
```

---

### Topic 3: ArrayList

#### Key Points (20 min)

- **ArrayList** = a resizable array. Backed by an array internally but grows automatically.
- Import: `import java.util.ArrayList;` (or `import java.util.List;` for the interface)
- **Best practice**: Declare as `List<T>` (interface), instantiate as `ArrayList<T>`:
  ```java
  List<Account> accounts = new ArrayList<>();
  ```
- **Key methods**:

| Method | What it does | Example |
|--------|-------------|---------|
| `add(item)` | Add to end | `accounts.add(ravi)` |
| `add(index, item)` | Insert at position | `accounts.add(0, ravi)` |
| `get(index)` | Get by position | `accounts.get(0)` |
| `set(index, item)` | Replace at position | `accounts.set(0, newAcc)` |
| `remove(index)` | Remove by position | `accounts.remove(0)` |
| `remove(item)` | Remove by object | `accounts.remove(ravi)` |
| `size()` | Number of elements | `accounts.size()` |
| `isEmpty()` | Check if empty | `accounts.isEmpty()` |
| `contains(item)` | Check if present | `accounts.contains(ravi)` |
| `indexOf(item)` | Find position | `accounts.indexOf(ravi)` |
| `clear()` | Remove all | `accounts.clear()` |

- **Iterating**:

```java
// Enhanced for loop (most common)
for (Account acc : accounts) {
    acc.displayInfo();
}

// Traditional for loop (when you need index)
for (int i = 0; i < accounts.size(); i++) {
    System.out.println((i + 1) + ". " + accounts.get(i).getHolderName());
}
```

- **ArrayList vs Array**:

| | Array | ArrayList |
|---|---|---|
| Size | Fixed at creation | Grows/shrinks dynamically |
| Type | Primitives + Objects | Objects only (use wrappers for primitives) |
| Syntax | `Account[] arr = new Account[10]` | `List<Account> list = new ArrayList<>()` |
| Add/Remove | Manual index management | Built-in methods |
| Length | `arr.length` | `list.size()` |

#### Case Study Step 1 — Replace array with ArrayList

```java
// Day 3 (array):
static Account[] accounts = new Account[10];
static int accountCount = 0;

// Day 4 (ArrayList):
static List<Account> accounts = new ArrayList<>();

// No more accountCount — use accounts.size()
// No more fixed size — just add()
```

#### Teaching Tip

> Demonstrate removing an account from the middle of the list. With an array, you'd have to shift elements manually. With ArrayList, it's just `accounts.remove(index)`.

---

### Topic 4: Map (HashMap)

#### Key Points (20 min)

- **HashMap** = stores key-value pairs. Instant lookup by key.
- Import: `import java.util.HashMap;` (or `import java.util.Map;`)
- **Use case in banking**: Look up an account by its account number instantly, without looping.
- **Key methods**:

| Method | What it does | Example |
|--------|-------------|---------|
| `put(key, value)` | Add/update entry | `map.put(1001L, ravi)` |
| `get(key)` | Get value by key | `map.get(1001L)` → ravi |
| `getOrDefault(key, default)` | Get or fallback | `map.getOrDefault(9999L, null)` |
| `containsKey(key)` | Check key exists | `map.containsKey(1001L)` |
| `containsValue(value)` | Check value exists | `map.containsValue(ravi)` |
| `remove(key)` | Remove entry | `map.remove(1001L)` |
| `size()` | Number of entries | `map.size()` |
| `keySet()` | All keys | `map.keySet()` |
| `values()` | All values | `map.values()` |
| `entrySet()` | All key-value pairs | `map.entrySet()` |

- **Iterating**:

```java
// Iterate over entries
for (Map.Entry<Long, Account> entry : accountMap.entrySet()) {
    System.out.println("Key: " + entry.getKey());
    entry.getValue().displayInfo();
}

// Iterate over values only
for (Account acc : accountMap.values()) {
    acc.displayInfo();
}
```

- **Key rules**:
  - Keys must be unique. Putting a duplicate key **replaces** the old value.
  - `get()` returns `null` if key not found.
  - Keys should be immutable (Long, String, Integer — not mutable objects).

#### Case Study Step 2 — Add HashMap for instant lookup

```java
// Store in both List (for ordered display) and Map (for fast lookup)
static List<Account> accounts = new ArrayList<>();
static Map<Long, Account> accountMap = new HashMap<>();

// When adding an account:
public static void addAccount(Account account) {
    accounts.add(account);
    accountMap.put(account.getAccountNumber(), account);
    System.out.println("Account added: " + account.getHolderName());
}

// Instant lookup by account number:
public static Account findByAccountNumber(long accountNumber) {
    Account acc = accountMap.get(accountNumber);
    if (acc == null) {
        System.out.println("Account #" + accountNumber + " not found.");
    }
    return acc;
}
```

> **Talking point**: "With the array approach, finding account #1002 out of 10,000 accounts means looping through up to 10,000 entries. With HashMap, it's instant — O(1). This is why banks use hashmaps (or database indexes, which work similarly)."

#### Case Study Step 3 — Removing an account

```java
public static boolean removeAccount(long accountNumber) {
    Account acc = accountMap.remove(accountNumber);
    if (acc == null) {
        System.out.println("Account #" + accountNumber + " not found.");
        return false;
    }
    accounts.remove(acc);   // also remove from list
    System.out.println("Account #" + accountNumber + " removed.");
    return true;
}
```

> **Teaching point**: "We keep both structures in sync. The List gives us ordered display, the Map gives us fast lookup. In real apps, a database handles both, but this teaches the concept."

---

## Part B: Searching Algorithms (Topics 5–9)

> **Transition**: "HashMap gives O(1) lookup by key. But what if you need to search by something that's NOT the key — like finding all accounts with balance > 50000, or searching a sorted list? That's where searching algorithms come in."

---

### Topic 5: Why Searching Algorithms in Real Systems

#### Key Points (5 min)

- Collections give us data structures. Searching algorithms give us strategies to find data.
- **In-memory search** — scanning through collections when no index/map is available.
- Real scenarios:
  - Search by name (partial match)
  - Find accounts above a certain balance
  - Find a specific transaction in history
- Two fundamental approaches: **Linear Search** (simple, works on anything) and **Binary Search** (fast, requires sorted data).

---

### Topic 6: Linear Search

#### Key Points (10 min)

- **Strategy**: Check every element, one by one, from start to end.
- **Works on**: Any collection — sorted or unsorted.
- **Steps**: Start at index 0, compare, if match return, else move to next, if end reached → not found.
- **Performance**:
  - Best case: O(1) — found at first position
  - Worst case: O(n) — found at last position or not found
  - Average case: O(n/2) → simplified to O(n)

#### Micro Example — Linear search on primitives

```java
public static int linearSearch(int[] arr, int target) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == target) {
            return i;      // found — return index
        }
    }
    return -1;             // not found
}
```

#### Case Study Step 4 — Linear search on accounts by name

```java
public static List<Account> searchByName(String keyword) {
    List<Account> results = new ArrayList<>();
    String lowerKeyword = keyword.toLowerCase();

    for (Account acc : accounts) {
        if (acc.getHolderName().toLowerCase().contains(lowerKeyword)) {
            results.add(acc);
        }
    }
    return results;
}

// Usage:
List<Account> found = searchByName("kumar");
System.out.println("Found " + found.size() + " account(s):");
for (Account acc : found) {
    acc.displayInfo();
}
```

#### Linear search — find accounts above a balance threshold

```java
public static List<Account> findAccountsAboveBalance(double threshold) {
    List<Account> results = new ArrayList<>();
    for (Account acc : accounts) {
        if (acc.getBalance() > threshold) {
            results.add(acc);
        }
    }
    return results;
}
```

---

### Topic 7: Binary Search

#### Key Points (15 min)

- **Strategy**: Divide and conquer. Check the middle element, eliminate half the data each time.
- **Pre-condition**: Data MUST be **sorted**.
- **Steps**:
  1. Find middle element
  2. If match → return
  3. If target < middle → search left half
  4. If target > middle → search right half
  5. Repeat until found or range is empty

- **Performance**: O(log n) — dramatically faster for large datasets.
  - 1,000 elements → max 10 comparisons (vs 1,000 for linear)
  - 1,000,000 elements → max 20 comparisons (vs 1,000,000 for linear)

#### Teaching Tip — Draw on board

```
Searching for account #1005 in sorted list:

[1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010]
                         ↑ mid = 1005 → FOUND! (1 comparison)

Searching for account #1008:

[1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010]
                         ↑ mid = 1005 → 1008 > 1005 → go right

                              [1006, 1007, 1008, 1009, 1010]
                                          ↑ mid = 1008 → FOUND! (2 comparisons)
```

#### Micro Example — Iterative binary search

```java
public static int binarySearch(long[] sortedArr, long target) {
    int low = 0;
    int high = sortedArr.length - 1;

    while (low <= high) {
        int mid = low + (high - low) / 2;    // avoids overflow

        if (sortedArr[mid] == target) {
            return mid;
        } else if (sortedArr[mid] < target) {
            low = mid + 1;      // search right half
        } else {
            high = mid - 1;     // search left half
        }
    }
    return -1;    // not found
}
```

#### Recursive idea (concept only — don't deep-dive)

```java
public static int binarySearchRecursive(long[] arr, long target, int low, int high) {
    if (low > high) return -1;

    int mid = low + (high - low) / 2;

    if (arr[mid] == target) return mid;
    else if (arr[mid] < target) return binarySearchRecursive(arr, target, mid + 1, high);
    else return binarySearchRecursive(arr, target, low, mid - 1);
}
```

> **Mention**: "Recursion is when a method calls itself. We show this for awareness — the iterative version is preferred for clarity."

#### Limitation on List of Objects

> "Binary search works great on arrays of primitives or simple types. For a List of objects (like Account), the list must be sorted by the field you're searching on. If accounts are sorted by accountNumber, you can binary-search by accountNumber. But you can't binary-search by name unless you re-sort by name first."

---

### Topic 8: Big-O Notation (Intro Level)

#### Key Points (10 min)

- **Big-O** = a way to describe how an algorithm's performance scales with input size.
- We focus on **time complexity** (how many operations).
- **Simplified rules**: Drop constants, keep the dominant term.

| Notation | Name | Example | 10 items | 1M items |
|----------|------|---------|----------|----------|
| O(1) | Constant | HashMap.get() | 1 op | 1 op |
| O(log n) | Logarithmic | Binary search | ~3 ops | ~20 ops |
| O(n) | Linear | Linear search, loop | 10 ops | 1M ops |
| O(n log n) | Log-linear | Sorting (Arrays.sort) | ~33 ops | ~20M ops |
| O(n²) | Quadratic | Nested loops | 100 ops | 1T ops |

#### Banking context

| Operation | Time Complexity |
|-----------|----------------|
| `accountMap.get(1001L)` | O(1) — instant |
| `accounts.get(3)` | O(1) — direct index access |
| Linear search by name | O(n) — scan all accounts |
| Binary search by sorted acc# | O(log n) — fast |
| `accounts.contains(acc)` | O(n) — scans the list |

#### Teaching Tip

> "You don't need to memorize formulas. Just think:
> - O(1) = instant (HashMap lookup)
> - O(log n) = halving each step (binary search)
> - O(n) = touch every item once (linear search)
> - O(n²) = nested loops (avoid for large data)"

---

### Topic 9: Searching in Object Collections

#### Key Points (10 min)

- Searching by a field: loop + compare the field value.
- For `long`/`int` comparisons: use `==`.
- For `String` comparisons: use `.equals()` (exact) or `.contains()` / `.equalsIgnoreCase()` (flexible).
- **equals vs custom comparison**:
  - `equals()` on objects: by default compares references (same object in memory).
  - Override `equals()` to compare by business key (e.g., account number).
  - For now, compare specific fields — we'll cover `equals()` override later.

#### Case Study Step 5 — Binary search on sorted accounts by account number

```java
// Assumes accounts list is sorted by accountNumber
public static Account binarySearchByAccountNumber(long targetAccNo) {
    int low = 0;
    int high = accounts.size() - 1;

    while (low <= high) {
        int mid = low + (high - low) / 2;
        long midAccNo = accounts.get(mid).getAccountNumber();

        if (midAccNo == targetAccNo) {
            return accounts.get(mid);
        } else if (midAccNo < targetAccNo) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }
    return null;   // not found
}
```

> **Talking point**: "In practice, HashMap lookup is preferred for exact key search (O(1) vs O(log n)). Binary search shines when you need range searches (all accounts between #1000 and #2000) or when you can't use extra memory for a HashMap."

---

## Case Study — Complete Day 4 Code

### BankApp.java (Day 4 version — with Collections + Search)

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BankApp {

    static List<Account> accounts = new ArrayList<>();
    static Map<Long, Account> accountMap = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Pre-load accounts
        addAccount(new SavingsAccount("Ravi Kumar", 1001L, 50000, 4.5));
        addAccount(new CurrentAccount("Priya Sharma", 1002L, 30000, 10000));
        addAccount(new SavingsAccount("Amit Verma", 1003L, 75000, 5.0));
        addAccount(new CurrentAccount("Sneha Reddy", 1004L, 120000, 25000));

        int choice = 0;
        do {
            displayMenu();
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number.");
                sc.nextLine();
                continue;
            }

            switch (choice) {
                case 1: viewAllAccounts(); break;
                case 2: handleCreateAccount(sc); break;
                case 3: handleDeposit(sc); break;
                case 4: handleWithdrawal(sc); break;
                case 5: handleSearchByNumber(sc); break;
                case 6: handleSearchByName(sc); break;
                case 7: handleHighBalanceSearch(sc); break;
                case 8: handleRemoveAccount(sc); break;
                case 9: System.out.println("Thank you for banking with us!"); break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 9);

        sc.close();
    }

    // --- Account Management ---

    static void addAccount(Account account) {
        if (accountMap.containsKey(account.getAccountNumber())) {
            System.out.println("Account #" + account.getAccountNumber() + " already exists.");
            return;
        }
        accounts.add(account);
        accountMap.put(account.getAccountNumber(), account);
    }

    static void handleCreateAccount(Scanner sc) {
        try {
            sc.nextLine();
            System.out.print("Type (S=Savings, C=Current): ");
            String type = sc.nextLine().trim().toUpperCase();

            System.out.print("Holder name: ");
            String name = sc.nextLine();

            System.out.print("Account number: ");
            long accNo = sc.nextLong();

            System.out.print("Initial balance: ");
            double balance = sc.nextDouble();

            Account newAcc;
            if (type.equals("S")) {
                System.out.print("Interest rate (%): ");
                double rate = sc.nextDouble();
                newAcc = new SavingsAccount(name, accNo, balance, rate);
            } else if (type.equals("C")) {
                System.out.print("Overdraft limit: ");
                double limit = sc.nextDouble();
                newAcc = new CurrentAccount(name, accNo, balance, limit);
            } else {
                System.out.println("Invalid account type.");
                return;
            }

            addAccount(newAcc);
            System.out.println("Account created successfully!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }

    static void handleRemoveAccount(Scanner sc) {
        System.out.print("Enter account number to remove: ");
        try {
            long accNo = sc.nextLong();
            Account acc = accountMap.remove(accNo);
            if (acc == null) {
                System.out.println("Account #" + accNo + " not found.");
            } else {
                accounts.remove(acc);
                System.out.println("Account #" + accNo + " (" + acc.getHolderName() + ") removed.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }

    // --- Search Methods ---

    // O(1) — HashMap lookup
    static void handleSearchByNumber(Scanner sc) {
        System.out.print("Enter account number: ");
        try {
            long accNo = sc.nextLong();
            Account acc = accountMap.get(accNo);
            if (acc != null) {
                acc.displayInfo();
            } else {
                System.out.println("Account #" + accNo + " not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }

    // O(n) — Linear search by name
    static void handleSearchByName(Scanner sc) {
        sc.nextLine();
        System.out.print("Enter name (or part of name): ");
        String keyword = sc.nextLine().trim();

        List<Account> results = searchByName(keyword);
        if (results.isEmpty()) {
            System.out.println("No accounts found matching '" + keyword + "'.");
        } else {
            System.out.println("Found " + results.size() + " account(s):");
            for (Account acc : results) {
                acc.displayInfo();
                System.out.println();
            }
        }
    }

    static List<Account> searchByName(String keyword) {
        List<Account> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Account acc : accounts) {
            if (acc.getHolderName().toLowerCase().contains(lowerKeyword)) {
                results.add(acc);
            }
        }
        return results;
    }

    // O(n) — Linear search by balance threshold
    static void handleHighBalanceSearch(Scanner sc) {
        System.out.print("Enter minimum balance: ");
        try {
            double threshold = sc.nextDouble();
            List<Account> results = new ArrayList<>();
            for (Account acc : accounts) {
                if (acc.getBalance() >= threshold) {
                    results.add(acc);
                }
            }
            if (results.isEmpty()) {
                System.out.println("No accounts with balance >= Rs." + threshold);
            } else {
                System.out.println(results.size() + " account(s) with balance >= Rs." + threshold + ":");
                for (Account acc : results) {
                    acc.displayInfo();
                    System.out.println();
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }

    // --- Transaction Operations ---

    static void handleDeposit(Scanner sc) {
        Account acc = selectAccount(sc);
        if (acc == null) return;
        System.out.print("Enter deposit amount: ");
        try {
            double amount = sc.nextDouble();
            acc.deposit(amount);
        } catch (InvalidAmountException e) {
            System.out.println("INVALID: " + e.getMessage());
        } catch (AccountInactiveException e) {
            System.out.println("BLOCKED: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }

    static void handleWithdrawal(Scanner sc) {
        Account acc = selectAccount(sc);
        if (acc == null) return;
        System.out.print("Enter withdrawal amount: ");
        try {
            double amount = sc.nextDouble();
            acc.withdraw(amount);
        } catch (InsufficientBalanceException e) {
            System.out.println("DECLINED: " + e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println("INVALID: " + e.getMessage());
        } catch (AccountInactiveException e) {
            System.out.println("BLOCKED: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }

    // --- Helpers ---

    static void displayMenu() {
        System.out.println("\n===== BANK APPLICATION (Day 4) =====");
        System.out.println("1. View All Accounts");
        System.out.println("2. Create Account");
        System.out.println("3. Deposit");
        System.out.println("4. Withdraw");
        System.out.println("5. Search by Account Number");
        System.out.println("6. Search by Name");
        System.out.println("7. Find High-Balance Accounts");
        System.out.println("8. Remove Account");
        System.out.println("9. Exit");
        System.out.print("Enter choice: ");
    }

    static void viewAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts registered.");
            return;
        }
        System.out.println("\n--- All Accounts (" + accounts.size() + ") ---");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.print((i + 1) + ". ");
            accounts.get(i).displayInfo();
            System.out.println();
        }
    }

    static Account selectAccount(Scanner sc) {
        System.out.print("Enter account number: ");
        try {
            long accNo = sc.nextLong();
            Account acc = accountMap.get(accNo);
            if (acc == null) {
                System.out.println("Account #" + accNo + " not found.");
            }
            return acc;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
            return null;
        }
    }
}
```

> **Key changes from Day 3**:
> 1. `Account[]` + `accountCount` → `List<Account>` + `Map<Long, Account>`
> 2. `selectAccount` now uses HashMap lookup instead of listing all and picking by index
> 3. New features: Create, Remove, Search by Number, Search by Name, High-Balance filter
> 4. Duplicate account number prevention via `accountMap.containsKey()`

---

## Day 4 Exercises

### Exercise 1: Count by Account Type
**Problem**: Write a method `countByType()` that prints how many Savings vs Current accounts exist. Use `instanceof` and loop through the list.

### Exercise 2: Account Summary Report
**Problem**: Write `printSummaryReport()` that displays:
- Total number of accounts
- Total balance across all accounts
- Average balance
- Highest balance account (name + balance)
- Lowest balance account (name + balance)

### Exercise 3: Binary Search Implementation
**Problem**: Assuming accounts are sorted by account number, write:
- `binarySearchByAccountNumber(long target)` that returns the Account (or null)
- Test it with the pre-loaded data
- Compare the number of comparisons with linear search (add a counter)

### Exercise 4: Search by Multiple Criteria
**Problem**: Write `searchAccounts(String name, Double minBalance, Double maxBalance, String type)`:
- All parameters are optional (null = ignore that filter)
- Returns accounts matching ALL non-null criteria
- Example: `searchAccounts(null, 50000.0, null, "SAVINGS")` → savings accounts with balance >= 50000

### Exercise 5: Full Challenge — Transaction History
**Problem**: Add transaction history to the BankApp:
- Create a `Transaction` class: `type`, `amount`, `dateTime` (String for now), `accountNumber`
- Store all transactions in a `List<Transaction>` in BankApp
- Add a menu option to view transactions for a specific account
- Add a menu option to view all transactions
- Use a `Map<Long, List<Transaction>>` to group transactions by account number for fast lookup

---

## Day 4 Quiz (10 questions)

1. What is the main advantage of ArrayList over a regular array?
2. What happens when you `add()` to an ArrayList that's at full internal capacity?
3. What does `Map.get(key)` return if the key doesn't exist?
4. Can a HashMap have duplicate keys? Duplicate values?
5. What is the time complexity of `HashMap.get()`?
6. What is the time complexity of linear search on an unsorted list of n elements?
7. What is the pre-condition for binary search?
8. How many comparisons does binary search need for 1,000,000 elements?
9. What is the difference between `accounts.contains(acc)` and `accountMap.containsKey(1001L)`?
10. Why do we maintain both a List and a Map for accounts?

---

## Day 4 Summary

| Step | What Changed | Topics |
|------|-------------|--------|
| Step 1 | Replaced `Account[]` with `ArrayList<Account>` | ArrayList, generics |
| Step 2 | Added `HashMap<Long, Account>` for instant lookup | HashMap, key-value |
| Step 3 | Added remove account (from both List and Map) | Collection removal |
| Step 4 | Search by name (linear), filter by balance | Linear search, O(n) |
| Step 5 | Binary search on sorted accounts | Binary search, O(log n) |
| All | Big-O comparison of search approaches | Big-O notation |

### Data Structure Choices in Our App

| Need | Structure | Why | Complexity |
|------|-----------|-----|-----------|
| Ordered display of all accounts | `ArrayList` | Maintains insertion order | O(n) to iterate |
| Lookup by account number | `HashMap` | Instant access by key | O(1) |
| Search by name | Linear search on List | Name is not a key | O(n) |
| Search on sorted data | Binary search | Efficient for sorted fields | O(log n) |

> **Preview for Day 5**: "We've been adding and removing accounts from a list. What about LIFO operations (undo the last transaction)? Or FIFO operations (process requests in order)? Tomorrow we learn Stacks, Queues, and LinkedLists — and add transaction history with undo to our banking app."
