# Day 4 — Collections + Searching Algorithms

## Student Handout

> **What you'll build today**: Replace the fixed-size array with dynamic ArrayList, add HashMap for instant account lookup, and implement search features (by name, by balance, by account number).

---

## Part A: Collections

---

### 1. Collection Framework Overview

**The Problem (from Day 3):**

```java
static Account[] accounts = new Account[10];   // fixed size!
static int accountCount = 0;                     // manual tracking!
```

What if we need 100 accounts? What if we want to remove one from the middle? Arrays make this painful.

**The Solution — Java Collections:**

Collections are dynamic data structures that grow/shrink automatically.

```
        Collection (interface)
       /          \
    List          Set
  (ordered,     (unique,
   duplicates    no duplicates)
   allowed)
     |              |
  ArrayList      HashSet

        Map (separate interface — key-value pairs)
         |
      HashMap
```

| | List | Set | Map |
|---|---|---|---|
| Duplicates? | Yes | No | Keys: No, Values: Yes |
| Ordered? | Yes (insertion order) | No guarantee | No guarantee |
| Access by | Index (position) | Iteration only | Key |
| Banking use | All accounts in order | Unique account types | Account lookup by number |

---

### 2. List vs Set vs Map — Quick Comparison

```java
import java.util.*;

// List — ordered, allows duplicates
List<String> transactions = new ArrayList<>();
transactions.add("DEPOSIT");
transactions.add("WITHDRAW");
transactions.add("DEPOSIT");     // duplicate — allowed
// [DEPOSIT, WITHDRAW, DEPOSIT]

// Set — unique elements, no guaranteed order
Set<String> accountTypes = new HashSet<>();
accountTypes.add("SAVINGS");
accountTypes.add("CURRENT");
accountTypes.add("SAVINGS");     // duplicate — ignored
// [SAVINGS, CURRENT]

// Map — key-value pairs, keys are unique
Map<Long, String> accountNames = new HashMap<>();
accountNames.put(1001L, "Ravi Kumar");
accountNames.put(1002L, "Priya Sharma");
String name = accountNames.get(1001L);    // "Ravi Kumar"
```

Today we focus on **ArrayList** and **HashMap** — the two most commonly used collections.

---

### 3. ArrayList

**ArrayList** = a resizable list backed by an array internally. It grows automatically.

```java
import java.util.ArrayList;
import java.util.List;

// Best practice: declare as List (interface), instantiate as ArrayList
List<Account> accounts = new ArrayList<>();
```

**Key methods:**

| Method | What it does | Time |
|--------|-------------|------|
| `add(item)` | Add to end | O(1) |
| `add(index, item)` | Insert at position | O(n) |
| `get(index)` | Get by position | O(1) |
| `set(index, item)` | Replace at position | O(1) |
| `remove(index)` | Remove by position | O(n) |
| `remove(item)` | Remove by object | O(n) |
| `size()` | Number of elements | O(1) |
| `isEmpty()` | Check if empty | O(1) |
| `contains(item)` | Check if present | O(n) |

**Example:**

```java
List<Account> accounts = new ArrayList<>();

// Add
Account ravi = new SavingsAccount("Ravi Kumar", 1001L, 50000, 4.5);
Account priya = new CurrentAccount("Priya Sharma", 1002L, 30000, 10000);
accounts.add(ravi);
accounts.add(priya);

// Size
System.out.println("Total accounts: " + accounts.size());    // 2

// Get by index
Account first = accounts.get(0);    // ravi
first.displayInfo();

// Iterate (enhanced for loop)
for (Account acc : accounts) {
    acc.displayInfo();
}

// Iterate with index
for (int i = 0; i < accounts.size(); i++) {
    System.out.println((i + 1) + ". " + accounts.get(i).getHolderName());
}

// Remove
accounts.remove(0);           // remove by index
accounts.remove(priya);       // remove by object
```

**ArrayList vs Array:**

| | Array | ArrayList |
|---|---|---|
| Size | Fixed: `new Account[10]` | Dynamic: grows as needed |
| Add/Remove | Manual: shift elements yourself | Built-in: `.add()`, `.remove()` |
| Type safety | Can hold primitives | Objects only (use `Integer` for `int`) |
| Length | `array.length` | `list.size()` |
| Performance | Slightly faster (no overhead) | Slightly slower (but negligible) |

#### Banking App — Step 1: Replace array with ArrayList

```java
// Day 3:
static Account[] accounts = new Account[10];
static int accountCount = 0;

// Day 4:
static List<Account> accounts = new ArrayList<>();
// No more accountCount — use accounts.size()
// No more fixed size — just accounts.add(newAccount)
```

---

### 4. Map (HashMap)

**HashMap** = stores key-value pairs with instant lookup by key.

```java
import java.util.HashMap;
import java.util.Map;

Map<Long, Account> accountMap = new HashMap<>();
//   ↑ key type   ↑ value type
```

Think of it like a dictionary: the **key** is the word, the **value** is the definition.
In our case: the **key** is the account number, the **value** is the Account object.

**Key methods:**

| Method | What it does | Time |
|--------|-------------|------|
| `put(key, value)` | Add or update | O(1) |
| `get(key)` | Get value by key | O(1) |
| `containsKey(key)` | Check if key exists | O(1) |
| `remove(key)` | Remove by key | O(1) |
| `size()` | Number of entries | O(1) |
| `keySet()` | Get all keys | O(1) |
| `values()` | Get all values | O(1) |
| `entrySet()` | Get all key-value pairs | O(1) |

**Example:**

```java
Map<Long, Account> accountMap = new HashMap<>();

// Put
Account ravi = new SavingsAccount("Ravi Kumar", 1001L, 50000, 4.5);
accountMap.put(1001L, ravi);
accountMap.put(1002L, new CurrentAccount("Priya Sharma", 1002L, 30000, 10000));

// Get — instant lookup, no looping!
Account found = accountMap.get(1001L);     // ravi
found.displayInfo();

// Check existence
if (accountMap.containsKey(1003L)) {
    System.out.println("Account exists");
} else {
    System.out.println("Account not found");    // this runs
}

// Get returns null if key not found
Account missing = accountMap.get(9999L);    // null

// Remove
accountMap.remove(1001L);

// Iterate over all entries
for (Map.Entry<Long, Account> entry : accountMap.entrySet()) {
    System.out.println("Account #" + entry.getKey());
    entry.getValue().displayInfo();
}
```

**Key rules:**
- Keys must be **unique**. Putting a duplicate key **replaces** the old value.
- `get()` returns **null** if the key doesn't exist.
- Keys should be **immutable** types (Long, String, Integer).

#### Banking App — Step 2: Add HashMap for instant lookup

```java
static List<Account> accounts = new ArrayList<>();        // for ordered display
static Map<Long, Account> accountMap = new HashMap<>();   // for instant lookup

// Add account — maintain both structures
public static void addAccount(Account account) {
    if (accountMap.containsKey(account.getAccountNumber())) {
        System.out.println("Account #" + account.getAccountNumber() + " already exists.");
        return;
    }
    accounts.add(account);
    accountMap.put(account.getAccountNumber(), account);
}

// Find by account number — O(1) instant!
public static Account findByAccountNumber(long accountNumber) {
    return accountMap.get(accountNumber);   // null if not found
}

// Remove account — remove from both
public static void removeAccount(long accountNumber) {
    Account acc = accountMap.remove(accountNumber);
    if (acc != null) {
        accounts.remove(acc);
        System.out.println("Account removed: " + acc.getHolderName());
    } else {
        System.out.println("Account not found.");
    }
}
```

**Why both List AND Map?**
- **List** → for ordered display ("show all accounts")
- **Map** → for instant lookup ("find account #1001")

In a real application, a database handles both needs. But this teaches the concept.

---

## Part B: Searching Algorithms

---

### 5. Why Searching Algorithms

HashMap gives us instant lookup by **key** (account number). But what if we need to search by something that's NOT the key?

- Find accounts by **name** (partial match)
- Find accounts with **balance > 50000**
- Find a specific **transaction** in a sorted list

These require searching algorithms.

---

### 6. Linear Search

**Strategy**: Check every element, one by one, from start to end.

```
Looking for "Priya" in: [Ravi, Amit, Priya, Sneha]

Step 1: Check Ravi  → not a match
Step 2: Check Amit  → not a match
Step 3: Check Priya → MATCH! Found at index 2
```

**Works on**: Any list — sorted or unsorted.

**Implementation:**

```java
// Simple linear search on an array
public static int linearSearch(int[] arr, int target) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] == target) {
            return i;       // found — return index
        }
    }
    return -1;              // not found
}
```

**Performance:**
- **Best case**: O(1) — found at first position
- **Worst case**: O(n) — found at last position or not found
- **Average**: O(n)

#### Banking App — Step 3: Search by name (linear search)

```java
// O(n) — must check every account
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

// Usage
List<Account> found = searchByName("kumar");
System.out.println("Found " + found.size() + " account(s).");
for (Account acc : found) {
    acc.displayInfo();
}
```

#### Banking App — Search by balance threshold

```java
// O(n) — must check every account
public static List<Account> findAccountsAboveBalance(double threshold) {
    List<Account> results = new ArrayList<>();
    for (Account acc : accounts) {
        if (acc.getBalance() >= threshold) {
            results.add(acc);
        }
    }
    return results;
}
```

---

### 7. Binary Search

**Strategy**: Divide and conquer. Check the middle, eliminate half each time.

**Pre-condition**: The data **must be sorted**.

```
Looking for account #1007 in sorted list:

[1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010]
                         ↑ mid = 1005
                         1007 > 1005 → search RIGHT half

                              [1006, 1007, 1008, 1009, 1010]
                                          ↑ mid = 1008
                                          1007 < 1008 → search LEFT half

                              [1006, 1007]
                                    ↑ mid = 1007
                                    FOUND! (3 comparisons vs 7 for linear)
```

**Implementation (iterative):**

```java
public static int binarySearch(long[] sortedArr, long target) {
    int low = 0;
    int high = sortedArr.length - 1;

    while (low <= high) {
        int mid = low + (high - low) / 2;    // avoids integer overflow

        if (sortedArr[mid] == target) {
            return mid;                 // found
        } else if (sortedArr[mid] < target) {
            low = mid + 1;             // target is in right half
        } else {
            high = mid - 1;            // target is in left half
        }
    }
    return -1;   // not found
}
```

**Recursive version (concept):**

```java
public static int binarySearchRecursive(long[] arr, long target, int low, int high) {
    if (low > high) return -1;      // base case — not found

    int mid = low + (high - low) / 2;

    if (arr[mid] == target) return mid;
    else if (arr[mid] < target) return binarySearchRecursive(arr, target, mid + 1, high);
    else return binarySearchRecursive(arr, target, low, mid - 1);
}
```

**Performance**: O(log n) — dramatically faster for large datasets.

| n (items) | Linear Search (worst) | Binary Search (worst) |
|-----------|----------------------|----------------------|
| 10 | 10 comparisons | 4 comparisons |
| 1,000 | 1,000 | 10 |
| 1,000,000 | 1,000,000 | 20 |

**Limitation**: Data MUST be sorted. If it's not sorted, you must sort it first (which costs O(n log n)).

#### Banking App — Step 4: Binary search on accounts

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

> **In practice**, for exact key lookup, HashMap (O(1)) beats binary search (O(log n)). Binary search is useful for range queries on sorted data or when you can't use extra memory for a Map.

---

### 8. Big-O Notation (Intro)

**Big-O** describes how performance scales as data grows. It's the "speed rating" of an algorithm.

| Notation | Name | What it means | Example |
|----------|------|-------------|---------|
| O(1) | Constant | Same speed regardless of size | `HashMap.get()`, `ArrayList.get(index)` |
| O(log n) | Logarithmic | Halves the work each step | Binary search |
| O(n) | Linear | Touches every element once | Linear search, `ArrayList.contains()` |
| O(n log n) | Log-linear | Sort, then process | `Collections.sort()` |
| O(n²) | Quadratic | Nested loops over data | Compare every pair (avoid!) |

**Visual:**

```
Performance (operations) vs Data Size

ops
 │            O(n²)
 │          ╱
 │        ╱       O(n)
 │      ╱       ╱
 │    ╱       ╱
 │  ╱     ╱       O(log n)
 │╱   ╱ ─────────────── O(1)
 └──────────────────── n (data size)
```

**In our banking app:**

| Operation | Complexity | Why |
|-----------|-----------|-----|
| `accountMap.get(1001L)` | O(1) | HashMap — instant |
| `accounts.get(3)` | O(1) | ArrayList — direct index |
| Search by name | O(n) | Must check every account |
| Binary search (sorted) | O(log n) | Halves each step |
| `accounts.contains(acc)` | O(n) | Scans entire list |

**Simple rule of thumb:**
- O(1) = instant (use when possible)
- O(log n) = very fast (binary search)
- O(n) = acceptable (one loop)
- O(n²) = slow for large data (nested loops — avoid if possible)

---

### 9. Searching in Object Collections

When searching objects, you compare specific **fields** rather than the whole object.

**For numbers** (`long`, `int`, `double`): use `==`, `<`, `>`

```java
if (acc.getAccountNumber() == targetAccNo)     // exact match
if (acc.getBalance() >= threshold)             // range check
```

**For Strings**: use `.equals()` (exact) or `.contains()` / `.equalsIgnoreCase()` (flexible)

```java
if (acc.getHolderName().equals("Ravi Kumar"))                     // exact match
if (acc.getHolderName().equalsIgnoreCase("ravi kumar"))           // case-insensitive
if (acc.getHolderName().toLowerCase().contains("kumar"))          // partial match
```

**Warning — `==` vs `.equals()` for Strings:**

```java
String a = "hello";
String b = new String("hello");

a == b           // false — compares references (different objects in memory)
a.equals(b)      // true  — compares content (same characters)
```

Always use `.equals()` for String comparison.

---

## Complete Day 4 BankApp

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
                System.out.println("Removed: " + acc.getHolderName() + " (#" + accNo + ")");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }

    // --- Search Operations ---

    static void handleSearchByNumber(Scanner sc) {
        System.out.print("Enter account number: ");
        try {
            long accNo = sc.nextLong();
            Account acc = accountMap.get(accNo);    // O(1)
            if (acc != null) {
                System.out.println("\n--- Account Found ---");
                acc.displayInfo();
            } else {
                System.out.println("Account #" + accNo + " not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }

    static void handleSearchByName(Scanner sc) {
        sc.nextLine();
        System.out.print("Enter name (or part of name): ");
        String keyword = sc.nextLine().trim();

        List<Account> results = searchByName(keyword);    // O(n)
        if (results.isEmpty()) {
            System.out.println("No accounts matching '" + keyword + "'.");
        } else {
            System.out.println("\nFound " + results.size() + " account(s):");
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

    static void handleHighBalanceSearch(Scanner sc) {
        System.out.print("Enter minimum balance: ");
        try {
            double threshold = sc.nextDouble();
            List<Account> results = new ArrayList<>();
            for (Account acc : accounts) {                // O(n)
                if (acc.getBalance() >= threshold) {
                    results.add(acc);
                }
            }
            if (results.isEmpty()) {
                System.out.println("No accounts with balance >= Rs." + threshold);
            } else {
                System.out.println("\n" + results.size() + " account(s) >= Rs." + threshold + ":");
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
            Account acc = accountMap.get(accNo);     // O(1) lookup
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

**Test these scenarios:**

| Test | What to do | Expected |
|------|-----------|----------|
| View all | Option 1 | Shows all 4 pre-loaded accounts |
| Create | Option 2, type S | New savings account added |
| Search by number | Option 5, enter 1001 | Instantly finds Ravi's account |
| Search by name | Option 6, enter "kumar" | Finds "Ravi Kumar" |
| High balance | Option 7, enter 60000 | Finds Amit (75000) and Sneha (120000) |
| Remove | Option 8, enter 1003 | Amit removed from both List and Map |
| Duplicate check | Create with existing number | "Account already exists" |

---

## Exercises

### Exercise 1: Count by Account Type

**Problem**: Write `countByType()` that prints how many Savings vs Current accounts exist.

<details>
<summary><strong>Solution</strong></summary>

```java
public static void countByType() {
    int savingsCount = 0;
    int currentCount = 0;

    for (Account acc : accounts) {
        if (acc instanceof SavingsAccount) {
            savingsCount++;
        } else if (acc instanceof CurrentAccount) {
            currentCount++;
        }
    }

    System.out.println("Savings accounts : " + savingsCount);
    System.out.println("Current accounts : " + currentCount);
    System.out.println("Total            : " + accounts.size());
}
```
</details>

---

### Exercise 2: Account Summary Report

**Problem**: Write `printSummaryReport()` that displays:
- Total number of accounts
- Total balance across all accounts
- Average balance
- Highest balance (account name + amount)
- Lowest balance (account name + amount)

<details>
<summary><strong>Solution</strong></summary>

```java
public static void printSummaryReport() {
    if (accounts.isEmpty()) {
        System.out.println("No accounts to report.");
        return;
    }

    double totalBalance = 0;
    Account highest = accounts.get(0);
    Account lowest = accounts.get(0);

    for (Account acc : accounts) {
        totalBalance += acc.getBalance();
        if (acc.getBalance() > highest.getBalance()) {
            highest = acc;
        }
        if (acc.getBalance() < lowest.getBalance()) {
            lowest = acc;
        }
    }

    double average = totalBalance / accounts.size();

    System.out.println("===== ACCOUNT SUMMARY REPORT =====");
    System.out.println("Total accounts : " + accounts.size());
    System.out.printf("Total balance  : Rs.%.2f%n", totalBalance);
    System.out.printf("Average balance: Rs.%.2f%n", average);
    System.out.println("Highest balance: " + highest.getHolderName()
            + " (Rs." + highest.getBalance() + ")");
    System.out.println("Lowest balance : " + lowest.getHolderName()
            + " (Rs." + lowest.getBalance() + ")");
}
```
</details>

---

### Exercise 3: Binary Search Implementation

**Problem**: Write `binarySearchByAccountNumber(long target)` that:
- Assumes the accounts list is sorted by account number
- Returns the Account if found, null if not
- Prints the number of comparisons made
- Test and compare with linear search (also count comparisons)

<details>
<summary><strong>Solution</strong></summary>

```java
public static Account binarySearchByAccountNumber(long target) {
    int low = 0;
    int high = accounts.size() - 1;
    int comparisons = 0;

    while (low <= high) {
        comparisons++;
        int mid = low + (high - low) / 2;
        long midAccNo = accounts.get(mid).getAccountNumber();

        if (midAccNo == target) {
            System.out.println("Binary search: found in " + comparisons + " comparison(s).");
            return accounts.get(mid);
        } else if (midAccNo < target) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }
    System.out.println("Binary search: not found after " + comparisons + " comparison(s).");
    return null;
}

public static Account linearSearchByAccountNumber(long target) {
    int comparisons = 0;
    for (Account acc : accounts) {
        comparisons++;
        if (acc.getAccountNumber() == target) {
            System.out.println("Linear search: found in " + comparisons + " comparison(s).");
            return acc;
        }
    }
    System.out.println("Linear search: not found after " + comparisons + " comparison(s).");
    return null;
}
```
</details>

---

### Exercise 4: Multi-Criteria Search

**Problem**: Write a method that searches by multiple optional criteria:

```java
public static List<Account> searchAccounts(String name, Double minBalance,
                                            Double maxBalance, String type)
```

- Each parameter can be `null` (meaning "don't filter by this")
- Returns accounts matching ALL non-null criteria
- Examples:
  - `searchAccounts("kumar", null, null, null)` → accounts with "kumar" in name
  - `searchAccounts(null, 50000.0, null, null)` → accounts with balance >= 50000
  - `searchAccounts(null, 10000.0, 80000.0, "SAVINGS")` → savings accounts with balance 10000-80000

<details>
<summary><strong>Solution</strong></summary>

```java
public static List<Account> searchAccounts(String name, Double minBalance,
                                            Double maxBalance, String type) {
    List<Account> results = new ArrayList<>();

    for (Account acc : accounts) {
        boolean matches = true;

        if (name != null && !acc.getHolderName().toLowerCase().contains(name.toLowerCase())) {
            matches = false;
        }
        if (minBalance != null && acc.getBalance() < minBalance) {
            matches = false;
        }
        if (maxBalance != null && acc.getBalance() > maxBalance) {
            matches = false;
        }
        if (type != null) {
            if (type.equalsIgnoreCase("SAVINGS") && !(acc instanceof SavingsAccount)) {
                matches = false;
            } else if (type.equalsIgnoreCase("CURRENT") && !(acc instanceof CurrentAccount)) {
                matches = false;
            }
        }

        if (matches) {
            results.add(acc);
        }
    }
    return results;
}
```
</details>

---

### Exercise 5: Transaction History (Challenge)

**Problem**: Add transaction tracking to the BankApp:

1. Create a `Transaction` class with: `type` (String), `amount` (double), `accountNumber` (long), `timestamp` (String — use `LocalDateTime.now().toString()`)
2. Store all transactions in a `List<Transaction>`
3. Use a `Map<Long, List<Transaction>>` to group transactions by account number
4. Add menu options:
   - "View transactions for account" — enter account number, see its history
   - "View all transactions" — see everything

<details>
<summary><strong>Solution</strong></summary>

```java
import java.time.LocalDateTime;

// Transaction.java
public class Transaction {
    private String type;
    private double amount;
    private long accountNumber;
    private String timestamp;

    public Transaction(String type, double amount, long accountNumber) {
        this.type = type;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.timestamp = LocalDateTime.now().toString();
    }

    public String getType() { return type; }
    public double getAmount() { return amount; }
    public long getAccountNumber() { return accountNumber; }
    public String getTimestamp() { return timestamp; }

    public void display() {
        System.out.printf("  [%s] %s Rs.%.2f (Account #%d)%n",
                timestamp, type, amount, accountNumber);
    }
}

// In BankApp — add these fields:
static List<Transaction> allTransactions = new ArrayList<>();
static Map<Long, List<Transaction>> transactionsByAccount = new HashMap<>();

// Helper to record a transaction:
static void recordTransaction(String type, double amount, long accountNumber) {
    Transaction txn = new Transaction(type, amount, accountNumber);
    allTransactions.add(txn);
    transactionsByAccount
        .computeIfAbsent(accountNumber, k -> new ArrayList<>())
        .add(txn);
}

// Call recordTransaction after successful deposit/withdraw:
// In handleDeposit, after acc.deposit(amount):
//   recordTransaction("DEPOSIT", amount, acc.getAccountNumber());

// View transactions for an account:
static void viewTransactionsForAccount(Scanner sc) {
    System.out.print("Enter account number: ");
    long accNo = sc.nextLong();
    List<Transaction> txns = transactionsByAccount.get(accNo);
    if (txns == null || txns.isEmpty()) {
        System.out.println("No transactions for account #" + accNo);
    } else {
        System.out.println("Transactions for account #" + accNo + ":");
        for (Transaction txn : txns) {
            txn.display();
        }
    }
}
```
</details>

---

## Quick Quiz

1. What is the main advantage of ArrayList over a regular array?
2. What happens when you call `map.put(key, value)` with a key that already exists?
3. What does `map.get(key)` return if the key doesn't exist?
4. Can a HashMap have duplicate keys? Duplicate values?
5. What is the time complexity of `HashMap.get()`?
6. What is the time complexity of linear search?
7. What must be true about data before you can use binary search?
8. How many comparisons does binary search need for 1,000,000 items?
9. Should you use `==` or `.equals()` to compare Strings? Why?
10. Why do we keep both a List and a Map for accounts?

<details>
<summary><strong>Answers</strong></summary>

1. ArrayList **grows dynamically** — no fixed size limit, no manual tracking of element count.
2. The old value is **replaced** with the new value. The key remains the same.
3. **`null`** — always check for null before using the result.
4. **Keys**: No (duplicate key replaces old value). **Values**: Yes (multiple keys can map to the same value).
5. **O(1)** — constant time, instant lookup regardless of map size.
6. **O(n)** — in the worst case, you check every element.
7. The data must be **sorted** by the field you're searching on.
8. About **20 comparisons** (log₂(1,000,000) ≈ 20).
9. Use **`.equals()`** — `==` compares references (memory addresses), `.equals()` compares actual content.
10. **List** maintains insertion order for display ("show all accounts"). **Map** provides O(1) lookup by key (account number). Different data structures serve different access patterns.
</details>

---

## What We Built Today

| Step | What Changed | Topics |
|------|-------------|--------|
| Step 1 | Replaced `Account[]` with `ArrayList<Account>` | ArrayList, generics, iteration |
| Step 2 | Added `HashMap<Long, Account>` for instant lookup | HashMap, put/get/containsKey |
| Step 3 | Search by name (partial, case-insensitive) | Linear search O(n) |
| Step 4 | Binary search by account number | Binary search O(log n) |
| All | Performance comparison of approaches | Big-O notation |

### Data Structure Choices

| What we need | Structure | Complexity |
|-------------|-----------|-----------|
| Display all accounts in order | ArrayList | O(n) iterate |
| Find by account number | HashMap | O(1) instant |
| Search by name (partial) | Linear search on List | O(n) |
| Search on sorted data | Binary search | O(log n) |
| Prevent duplicate account numbers | HashMap.containsKey() | O(1) |

## What's Next (Day 5 Preview)

What if we want to **undo** the last transaction? That's a LIFO (Last In, First Out) operation — perfect for a **Stack**.

What if we want to process service requests in order? That's a FIFO (First In, First Out) operation — perfect for a **Queue**.

Tomorrow we learn **Data Structures** — Stack, Queue, and LinkedList — and add transaction history with undo capability to our banking app.
