# Day 5 — Data Structures: Stack, Queue, LinkedList

## Student Handout

> **What you'll build today**: Add transaction undo (Stack), service request processing (Queue), and understand when LinkedList beats ArrayList.

---

## 1. Why Data Structures Matter

On Day 4 we used ArrayList (ordered list) and HashMap (key-value lookup). But not every problem fits these:

| Scenario | What you need | Best structure |
|----------|--------------|---------------|
| Undo last action | Last in, first out | **Stack** |
| Process requests in order | First in, first out | **Queue** |
| Frequent insert/remove at start | Efficient re-linking | **LinkedList** |

**Choosing the right data structure = choosing the right tool for the job.**

- **Algorithm** = the steps to solve a problem
- **Data structure** = how you organize data so the algorithm is efficient

---

## 2. Stack — LIFO (Last In, First Out)

A **Stack** is like a stack of plates — you always add and remove from the **top**.

```
push(A)  push(B)  push(C)     pop()      peek()

                  ┌───┐
         ┌───┐   │ C │ ← top            ┌───┐
┌───┐    │ B │   ├───┤       ┌───┐      │ B │ ← returns B
│ A │    ├───┤   │ B │       │ B │      ├───┤    (not removed)
└───┘    │ A │   ├───┤       ├───┤      │ A │
         └───┘   │ A │       │ A │      └───┘
                  └───┘       └───┘
                            returns C
```

**Core operations:**

| Operation | What it does | Returns |
|-----------|-------------|---------|
| `push(item)` | Add to top | — |
| `pop()` | Remove and return top item | The item |
| `peek()` | Look at top without removing | The item |
| `isEmpty()` | Check if stack is empty | boolean |
| `size()` | Number of items | int |

**Java usage:**

```java
import java.util.Stack;

Stack<String> history = new Stack<>();

history.push("Opened file");
history.push("Typed 'Hello'");
history.push("Deleted line");

System.out.println(history.peek());    // "Deleted line"
System.out.println(history.pop());     // "Deleted line" (removed)
System.out.println(history.pop());     // "Typed 'Hello'" (removed)
System.out.println(history.size());    // 1
System.out.println(history.isEmpty()); // false
```

**Real-world uses:**
- Undo/Redo in text editors
- Browser back button (stack of visited pages)
- Method call stack (Java's own execution model)
- Transaction rollback in banking

### Banking App — Step 1: Transaction with undo support

First, we enhance the `Transaction` class to store the balance before and after:

```java
public class Transaction {
    private String type;           // "DEPOSIT" or "WITHDRAW"
    private double amount;
    private long accountNumber;
    private double balanceBefore;  // for undo!
    private double balanceAfter;
    private String timestamp;

    public Transaction(String type, double amount, long accountNumber,
                       double balanceBefore, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    public String getType() { return type; }
    public double getAmount() { return amount; }
    public long getAccountNumber() { return accountNumber; }
    public double getBalanceBefore() { return balanceBefore; }
    public double getBalanceAfter() { return balanceAfter; }
    public String getTimestamp() { return timestamp; }

    public void display() {
        System.out.printf("  [%s] %-10s Rs.%10.2f | Acc#%d | Rs.%.2f → Rs.%.2f%n",
                timestamp, type, amount, accountNumber, balanceBefore, balanceAfter);
    }
}
```

### Banking App — Step 2: Undo with Stack

```java
// In BankApp — declare the stack
static Stack<Transaction> transactionStack = new Stack<>();

// After a successful deposit or withdrawal, record it:
static void recordTransaction(String type, double amount, long accNo,
                               double balBefore, double balAfter) {
    Transaction txn = new Transaction(type, amount, accNo, balBefore, balAfter);
    transactionStack.push(txn);     // push onto undo stack
}

// In handleDeposit:
double balBefore = acc.getBalance();
acc.deposit(amount);
recordTransaction("DEPOSIT", amount, acc.getAccountNumber(), balBefore, acc.getBalance());

// Undo the last transaction:
static void handleUndo() {
    if (transactionStack.isEmpty()) {
        System.out.println("Nothing to undo.");
        return;
    }

    Transaction lastTxn = transactionStack.pop();    // get the most recent
    Account acc = accountMap.get(lastTxn.getAccountNumber());

    if (acc == null) {
        System.out.println("Account no longer exists.");
        return;
    }

    // Restore the balance to what it was before the transaction
    acc.restoreBalance(lastTxn.getBalanceBefore());

    System.out.println("UNDO: Reversed " + lastTxn.getType()
            + " of Rs." + lastTxn.getAmount()
            + " on Account #" + lastTxn.getAccountNumber());
    System.out.println("Balance restored to Rs." + acc.getBalance());
}
```

**How it works:**

```
1. Deposit Rs.5000 to Ravi (balance: 50000 → 55000)
   transactionStack: [DEPOSIT 5000 (50000→55000)]

2. Withdraw Rs.2000 from Ravi (balance: 55000 → 53000)
   transactionStack: [DEPOSIT 5000, WITHDRAW 2000 (55000→53000)]

3. UNDO!
   pop() → WITHDRAW 2000 (55000→53000)
   Restore balance to 55000 (balanceBefore)
   transactionStack: [DEPOSIT 5000]

4. UNDO again!
   pop() → DEPOSIT 5000 (50000→55000)
   Restore balance to 50000
   transactionStack: [] (empty)
```

---

## 3. Queue — FIFO (First In, First Out)

A **Queue** is like a bank counter queue — first person in line gets served first.

```
offer(A)  offer(B)  offer(C)     poll()        peek()

Front                Back
┌───┐
│ A │    A → B      A → B → C    B → C         B → C
└───┘                            returns A     returns B
                                 (removed)     (not removed)
```

**Core operations:**

| Operation | What it does | Returns |
|-----------|-------------|---------|
| `offer(item)` | Add to back of queue | true/false |
| `poll()` | Remove and return front item | Item or null if empty |
| `peek()` | Look at front without removing | Item or null if empty |
| `isEmpty()` | Check if empty | boolean |
| `size()` | Number of items | int |

> **Prefer `offer`/`poll`/`peek`** — they return null on failure instead of throwing exceptions.

**Java usage:**

```java
import java.util.Queue;
import java.util.LinkedList;

Queue<String> serviceQueue = new LinkedList<>();    // LinkedList implements Queue

serviceQueue.offer("Request #1 — Open account");
serviceQueue.offer("Request #2 — Issue checkbook");
serviceQueue.offer("Request #3 — Loan inquiry");

System.out.println(serviceQueue.peek());    // "Request #1"
System.out.println(serviceQueue.poll());    // "Request #1" (removed)
System.out.println(serviceQueue.peek());    // "Request #2"
System.out.println(serviceQueue.size());    // 2
```

**Real-world uses:**
- Customer service queue (first come, first served)
- Print job queue
- Request processing in web servers
- Task scheduling

### Banking App — Step 3: Service Request class

```java
public class ServiceRequest {
    private static int nextId = 1;

    private int requestId;
    private long accountNumber;
    private String requestType;
    private String description;
    private String timestamp;

    public ServiceRequest(long accountNumber, String requestType, String description) {
        this.requestId = nextId++;
        this.accountNumber = accountNumber;
        this.requestType = requestType;
        this.description = description;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    public int getRequestId() { return requestId; }
    public long getAccountNumber() { return accountNumber; }
    public String getRequestType() { return requestType; }
    public String getDescription() { return description; }

    public void display() {
        System.out.printf("  [#%d] Acc#%d | %-12s | %s | %s%n",
                requestId, accountNumber, requestType, description, timestamp);
    }
}
```

### Banking App — Step 4: Service Request Queue

```java
// In BankApp
static Queue<ServiceRequest> serviceQueue = new LinkedList<>();

// Customer submits a request
static void handleSubmitRequest(Scanner sc) {
    Account acc = selectAccount(sc);
    if (acc == null) return;

    sc.nextLine();
    System.out.print("Type (CHECKBOOK/STATEMENT/CARD/LOAN/OTHER): ");
    String type = sc.nextLine().trim().toUpperCase();
    System.out.print("Description: ");
    String desc = sc.nextLine().trim();

    ServiceRequest request = new ServiceRequest(acc.getAccountNumber(), type, desc);
    serviceQueue.offer(request);    // add to back of queue
    System.out.println("Request #" + request.getRequestId()
            + " submitted. Position: " + serviceQueue.size());
}

// Staff processes the next request
static void handleProcessRequest() {
    if (serviceQueue.isEmpty()) {
        System.out.println("No pending requests.");
        return;
    }
    ServiceRequest req = serviceQueue.poll();    // remove from front
    System.out.println("Processing:");
    req.display();
    System.out.println("Completed! Remaining: " + serviceQueue.size());
}

// View all pending (without removing)
static void viewPendingRequests() {
    if (serviceQueue.isEmpty()) {
        System.out.println("No pending requests.");
        return;
    }
    System.out.println("--- Pending Requests (" + serviceQueue.size() + ") ---");
    for (ServiceRequest req : serviceQueue) {    // iterating doesn't remove!
        req.display();
    }
}
```

**Flow example:**

```
1. Ravi submits CHECKBOOK request    → Queue: [#1-CHECKBOOK]
2. Priya submits LOAN request        → Queue: [#1-CHECKBOOK, #2-LOAN]
3. Amit submits STATEMENT request    → Queue: [#1-CHECKBOOK, #2-LOAN, #3-STATEMENT]
4. Process next request              → Processes #1 (CHECKBOOK — first in)
                                       Queue: [#2-LOAN, #3-STATEMENT]
5. Process next request              → Processes #2 (LOAN)
                                       Queue: [#3-STATEMENT]
```

---

## 4. LinkedList as a Data Structure

### How it works internally

**ArrayList**: elements stored in a contiguous block of memory (like a row of lockers).

```
ArrayList: [Ravi | Priya | Amit | Sneha]
            0       1      2      3
```

**LinkedList**: each element (node) stores its data + a pointer to the next node.

```
LinkedList:
┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│ data: Ravi   │     │ data: Priya  │     │ data: Amit   │
│ next: ──────────→  │ next: ──────────→  │ next: null   │
└──────────────┘     └──────────────┘     └──────────────┘
  head                                       tail
```

### Singly vs Doubly Linked List

**Singly linked**: each node points to the next only (one direction)

```
[A] → [B] → [C] → null
```

**Doubly linked**: each node points to both next AND previous

```
null ← [A] ⇄ [B] ⇄ [C] → null
```

Java's `LinkedList` is **doubly linked** — you can traverse in both directions.

### Why LinkedList?

**Inserting at the beginning:**

```
ArrayList: Insert "Zara" at index 0
  Before: [Ravi, Priya, Amit]
  Shift:  [    , Ravi, Priya, Amit]     ← shift ALL elements right (O(n))
  Insert: [Zara, Ravi, Priya, Amit]

LinkedList: Insert "Zara" at head
  Before: [Ravi] → [Priya] → [Amit]
  After:  [Zara] → [Ravi] → [Priya] → [Amit]    ← just change 1 pointer (O(1))
```

| Operation | ArrayList | LinkedList |
|-----------|----------|------------|
| Get by index `get(i)` | **O(1)** fast | O(n) slow |
| Add at end | **O(1)** | **O(1)** |
| Add at beginning | O(n) slow | **O(1)** fast |
| Remove from beginning | O(n) slow | **O(1)** fast |
| Memory per element | Less | More (extra pointers) |

---

## 5. Java Implementation Overview

### Stack

```java
import java.util.Stack;

Stack<Transaction> undoStack = new Stack<>();
undoStack.push(txn);                  // add to top
Transaction last = undoStack.pop();   // remove from top
Transaction top = undoStack.peek();   // look at top
boolean empty = undoStack.isEmpty();
```

### Queue

```java
import java.util.Queue;
import java.util.LinkedList;

Queue<ServiceRequest> queue = new LinkedList<>();   // LinkedList implements Queue
queue.offer(request);                    // add to back
ServiceRequest next = queue.poll();      // remove from front
ServiceRequest front = queue.peek();     // look at front
```

### LinkedList — dual role

`LinkedList` can act as both a **List** and a **Queue**:

```java
import java.util.LinkedList;

// As a List
LinkedList<String> list = new LinkedList<>();
list.add("A");
list.add("B");
list.addFirst("Z");      // [Z, A, B]  — O(1) at start
list.addLast("C");       // [Z, A, B, C]
list.removeFirst();      // [A, B, C]  — O(1)
String second = list.get(1);  // "B"   — O(n), must traverse

// As a Queue
Queue<String> queue = new LinkedList<>();
queue.offer("first");
queue.offer("second");
String next = queue.poll();   // "first"
```

### When to use what

| Need | Use | Why |
|------|-----|-----|
| Undo / rollback | `Stack` | LIFO — reverse last action |
| Process in order | `Queue` via `LinkedList` | FIFO — first come first served |
| General list, access by index | `ArrayList` | O(1) random access |
| Frequent add/remove at start | `LinkedList` | O(1) at head |
| Key-value lookup | `HashMap` | O(1) by key |

---

## 6. Performance — LinkedList vs ArrayList

| Operation | ArrayList | LinkedList | Winner |
|-----------|----------|------------|--------|
| `get(index)` | O(1) | O(n) | ArrayList |
| `add(end)` | O(1) | O(1) | Tie |
| `add(0, item)` — at start | O(n) | O(1) | LinkedList |
| `remove(0)` — at start | O(n) | O(1) | LinkedList |
| Memory per element | ~4 bytes overhead | ~24 bytes overhead | ArrayList |
| Cache performance | Better | Worse | ArrayList |

**Practical guideline:**
- **Default to ArrayList** — it's faster for most common operations
- **Use LinkedList when**:
  - You frequently add/remove at the beginning
  - You use it as a Queue (FIFO)
  - You never access elements by index

---

## Complete Day 5 BankApp

### New files to create:

**Transaction.java** — (see Step 1 above for full code)

**ServiceRequest.java** — (see Step 3 above for full code)

### Add to Account.java:

```java
// Package-private method for undo support
void restoreBalance(double previousBalance) {
    this.setBalance(previousBalance);
}
```

### Updated BankApp.java:

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class BankApp {

    // Day 4 structures
    static List<Account> accounts = new ArrayList<>();
    static Map<Long, Account> accountMap = new HashMap<>();

    // Day 5 structures
    static Stack<Transaction> transactionStack = new Stack<>();
    static List<Transaction> allTransactions = new ArrayList<>();
    static Map<Long, List<Transaction>> transactionsByAccount = new HashMap<>();
    static Queue<ServiceRequest> serviceQueue = new LinkedList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

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
                case 1:  viewAllAccounts(); break;
                case 2:  handleDeposit(sc); break;
                case 3:  handleWithdrawal(sc); break;
                case 4:  handleUndo(); break;
                case 5:  handleViewTransactions(sc); break;
                case 6:  handleSubmitRequest(sc); break;
                case 7:  handleProcessRequest(); break;
                case 8:  viewPendingRequests(); break;
                case 9:  handleSearchByNumber(sc); break;
                case 10: System.out.println("Thank you for banking with us!"); break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 10);

        sc.close();
    }

    // ==================== MENU ====================

    static void displayMenu() {
        System.out.println("\n===== BANK APPLICATION (Day 5) =====");
        System.out.println("--- Accounts ---");
        System.out.println(" 1. View All Accounts");
        System.out.println(" 2. Deposit");
        System.out.println(" 3. Withdraw");
        System.out.println("--- Transactions ---");
        System.out.println(" 4. Undo Last Transaction");
        System.out.println(" 5. View Transaction History");
        System.out.println("--- Service Requests ---");
        System.out.println(" 6. Submit Service Request");
        System.out.println(" 7. Process Next Request");
        System.out.println(" 8. View Pending Requests");
        System.out.println("--- Other ---");
        System.out.println(" 9. Search Account by Number");
        System.out.println("10. Exit");
        System.out.print("Enter choice: ");
    }

    // ==================== ACCOUNT MANAGEMENT ====================

    static void addAccount(Account account) {
        if (accountMap.containsKey(account.getAccountNumber())) {
            System.out.println("Account #" + account.getAccountNumber() + " already exists.");
            return;
        }
        accounts.add(account);
        accountMap.put(account.getAccountNumber(), account);
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

    static void handleSearchByNumber(Scanner sc) {
        System.out.print("Enter account number: ");
        try {
            long accNo = sc.nextLong();
            Account acc = accountMap.get(accNo);
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

    // ==================== TRANSACTIONS ====================

    static void recordTransaction(String type, double amount, long accNo,
                                   double balBefore, double balAfter) {
        Transaction txn = new Transaction(type, amount, accNo, balBefore, balAfter);
        transactionStack.push(txn);         // for undo (Stack)
        allTransactions.add(txn);           // for full history (List)
        transactionsByAccount               // for per-account history (Map)
                .computeIfAbsent(accNo, k -> new ArrayList<>())
                .add(txn);
    }

    static void handleDeposit(Scanner sc) {
        Account acc = selectAccount(sc);
        if (acc == null) return;
        System.out.print("Enter deposit amount: ");
        try {
            double amount = sc.nextDouble();
            double balBefore = acc.getBalance();
            acc.deposit(amount);
            recordTransaction("DEPOSIT", amount, acc.getAccountNumber(),
                    balBefore, acc.getBalance());
        } catch (InvalidAmountException | AccountInactiveException e) {
            System.out.println("ERROR: " + e.getMessage());
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
            double balBefore = acc.getBalance();
            acc.withdraw(amount);
            recordTransaction("WITHDRAW", amount, acc.getAccountNumber(),
                    balBefore, acc.getBalance());
        } catch (InsufficientBalanceException | InvalidAmountException | AccountInactiveException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }

    // ==================== STACK — UNDO ====================

    static void handleUndo() {
        if (transactionStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }

        Transaction lastTxn = transactionStack.pop();
        Account acc = accountMap.get(lastTxn.getAccountNumber());

        if (acc == null) {
            System.out.println("Account no longer exists. Cannot undo.");
            return;
        }

        acc.restoreBalance(lastTxn.getBalanceBefore());
        System.out.println("UNDO: Reversed " + lastTxn.getType()
                + " of Rs." + lastTxn.getAmount()
                + " on Account #" + lastTxn.getAccountNumber());
        System.out.println("Balance restored to Rs." + acc.getBalance());
    }

    // ==================== TRANSACTION HISTORY ====================

    static void handleViewTransactions(Scanner sc) {
        System.out.print("Enter account number (0 for all): ");
        try {
            long accNo = sc.nextLong();

            if (accNo == 0) {
                if (allTransactions.isEmpty()) {
                    System.out.println("No transactions recorded.");
                } else {
                    System.out.println("\n--- All Transactions (" + allTransactions.size() + ") ---");
                    for (Transaction txn : allTransactions) {
                        txn.display();
                    }
                }
            } else {
                List<Transaction> txns = transactionsByAccount.get(accNo);
                if (txns == null || txns.isEmpty()) {
                    System.out.println("No transactions for Account #" + accNo);
                } else {
                    System.out.println("\n--- Transactions for Account #" + accNo
                            + " (" + txns.size() + ") ---");
                    for (Transaction txn : txns) {
                        txn.display();
                    }
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }

    // ==================== QUEUE — SERVICE REQUESTS ====================

    static void handleSubmitRequest(Scanner sc) {
        Account acc = selectAccount(sc);
        if (acc == null) return;

        sc.nextLine();
        System.out.print("Type (CHECKBOOK/STATEMENT/CARD/LOAN/OTHER): ");
        String type = sc.nextLine().trim().toUpperCase();
        System.out.print("Description: ");
        String desc = sc.nextLine().trim();

        ServiceRequest request = new ServiceRequest(acc.getAccountNumber(), type, desc);
        serviceQueue.offer(request);
        System.out.println("Request #" + request.getRequestId()
                + " submitted. Queue position: " + serviceQueue.size());
    }

    static void handleProcessRequest() {
        if (serviceQueue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }
        ServiceRequest req = serviceQueue.poll();
        System.out.println("Processing request:");
        req.display();
        System.out.println("Request #" + req.getRequestId() + " completed.");
        System.out.println("Remaining in queue: " + serviceQueue.size());
    }

    static void viewPendingRequests() {
        if (serviceQueue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }
        System.out.println("\n--- Pending Requests (" + serviceQueue.size() + ") ---");
        for (ServiceRequest req : serviceQueue) {
            req.display();
        }
    }
}
```

### Test scenarios:

| # | Test | Expected |
|---|------|----------|
| 1 | Deposit Rs.5000 to Ravi | Balance: 55000, transaction recorded |
| 2 | Withdraw Rs.3000 from Ravi | Balance: 52000, transaction recorded |
| 3 | Undo | Reverses withdrawal, balance back to 55000 |
| 4 | Undo again | Reverses deposit, balance back to 50000 |
| 5 | Undo again | "Nothing to undo" |
| 6 | View transaction history (account 1001) | Shows both transactions |
| 7 | Submit 3 service requests | Added to queue in order |
| 8 | View pending requests | Shows all 3 in FIFO order |
| 9 | Process next request | Processes #1 (first submitted) |
| 10 | Process next request | Processes #2 |

---

## Exercises

### Exercise 1: Undo All

**Problem**: Add an "Undo All Transactions" option that reverses every transaction on the stack until it's empty. Print a summary of how many transactions were reversed.

<details>
<summary><strong>Solution</strong></summary>

```java
static void handleUndoAll() {
    if (transactionStack.isEmpty()) {
        System.out.println("Nothing to undo.");
        return;
    }

    int count = 0;
    while (!transactionStack.isEmpty()) {
        Transaction txn = transactionStack.pop();
        Account acc = accountMap.get(txn.getAccountNumber());
        if (acc != null) {
            acc.restoreBalance(txn.getBalanceBefore());
            count++;
        }
    }
    System.out.println("Reversed " + count + " transaction(s).");
    System.out.println("All balances restored to their original state.");
}
```
</details>

---

### Exercise 2: Redo Stack

**Problem**: Implement redo functionality:
- When a transaction is undone, push it onto a `redoStack`
- Add a "Redo" menu option that re-applies the last undone transaction
- If the user performs a NEW transaction (deposit/withdraw), clear the redo stack

<details>
<summary><strong>Solution</strong></summary>

```java
static Stack<Transaction> redoStack = new Stack<>();

// Modified handleUndo:
static void handleUndo() {
    if (transactionStack.isEmpty()) {
        System.out.println("Nothing to undo.");
        return;
    }
    Transaction lastTxn = transactionStack.pop();
    Account acc = accountMap.get(lastTxn.getAccountNumber());
    if (acc == null) {
        System.out.println("Account no longer exists.");
        return;
    }

    acc.restoreBalance(lastTxn.getBalanceBefore());
    redoStack.push(lastTxn);    // save for redo
    System.out.println("UNDO: Reversed " + lastTxn.getType()
            + " of Rs." + lastTxn.getAmount());
}

// Redo:
static void handleRedo() {
    if (redoStack.isEmpty()) {
        System.out.println("Nothing to redo.");
        return;
    }
    Transaction txn = redoStack.pop();
    Account acc = accountMap.get(txn.getAccountNumber());
    if (acc == null) {
        System.out.println("Account no longer exists.");
        return;
    }

    // Re-apply the transaction
    if (txn.getType().equals("DEPOSIT")) {
        acc.deposit(txn.getAmount());
    } else if (txn.getType().equals("WITHDRAW")) {
        acc.withdraw(txn.getAmount());
    }
    transactionStack.push(txn);    // back on undo stack
    System.out.println("REDO: Re-applied " + txn.getType()
            + " of Rs." + txn.getAmount());
}

// In recordTransaction — clear redo stack on new transaction:
static void recordTransaction(String type, double amount, long accNo,
                               double balBefore, double balAfter) {
    // ... existing code ...
    redoStack.clear();    // new action invalidates redo history
}
```
</details>

---

### Exercise 3: Priority Service Queue

**Problem**: Use `PriorityQueue` to process LOAN requests before other types. Create a comparator that prioritizes by request type: LOAN > CARD > CHECKBOOK > STATEMENT > OTHER.

<details>
<summary><strong>Solution</strong></summary>

```java
import java.util.PriorityQueue;
import java.util.Comparator;

// Priority order: lower number = higher priority
static int getPriority(String type) {
    switch (type) {
        case "LOAN":      return 1;
        case "CARD":      return 2;
        case "CHECKBOOK": return 3;
        case "STATEMENT": return 4;
        default:          return 5;
    }
}

// Replace LinkedList with PriorityQueue:
static Queue<ServiceRequest> serviceQueue = new PriorityQueue<>(
    Comparator.comparingInt(r -> getPriority(r.getRequestType()))
);

// Now when you poll(), LOAN requests come out first regardless of when they were added
```
</details>

---

### Exercise 4: Recent Transactions (LinkedList)

**Problem**: Use a `LinkedList` to maintain a "recent transactions" list that holds at most the 10 most recent transactions. When a new transaction arrives and the list is full, remove the oldest (first). Display in reverse order (most recent first).

<details>
<summary><strong>Solution</strong></summary>

```java
static LinkedList<Transaction> recentTransactions = new LinkedList<>();
static final int MAX_RECENT = 10;

// Call this when recording a transaction:
static void addToRecent(Transaction txn) {
    if (recentTransactions.size() >= MAX_RECENT) {
        recentTransactions.removeFirst();    // remove oldest — O(1) for LinkedList
    }
    recentTransactions.addLast(txn);
}

// Display most recent first:
static void displayRecentTransactions() {
    if (recentTransactions.isEmpty()) {
        System.out.println("No recent transactions.");
        return;
    }
    System.out.println("--- Recent Transactions (newest first) ---");
    // Iterate in reverse
    for (int i = recentTransactions.size() - 1; i >= 0; i--) {
        recentTransactions.get(i).display();
    }
}
```
</details>

---

### Exercise 5: Mini Statement (Challenge)

**Problem**: Add a "Mini Statement" feature that:
- Takes an account number
- Shows the last 5 transactions for that account (most recent first)
- Displays: opening balance → each transaction → closing balance
- Format it like a real bank mini statement

<details>
<summary><strong>Solution</strong></summary>

```java
static void handleMiniStatement(Scanner sc) {
    System.out.print("Enter account number: ");
    long accNo = sc.nextLong();

    Account acc = accountMap.get(accNo);
    if (acc == null) {
        System.out.println("Account not found.");
        return;
    }

    List<Transaction> txns = transactionsByAccount.get(accNo);
    if (txns == null || txns.isEmpty()) {
        System.out.println("No transactions for this account.");
        return;
    }

    // Get last 5 (or fewer)
    int start = Math.max(0, txns.size() - 5);
    List<Transaction> recent = txns.subList(start, txns.size());

    // Use a stack to reverse the order (most recent first)
    Stack<Transaction> reversed = new Stack<>();
    for (Transaction txn : recent) {
        reversed.push(txn);
    }

    System.out.println("\n╔═══════════════════════════════════════════════╗");
    System.out.println("║           MINI STATEMENT                      ║");
    System.out.println("║  Account #" + accNo + " | " + acc.getHolderName());
    System.out.println("╠═══════════════════════════════════════════════╣");
    System.out.printf("║  Current Balance: Rs.%.2f%n", acc.getBalance());
    System.out.println("╠═══════════════════════════════════════════════╣");
    System.out.println("║  Recent Transactions (newest first):          ║");

    while (!reversed.isEmpty()) {
        Transaction txn = reversed.pop();
        String sign = txn.getType().equals("DEPOSIT") ? "+" : "-";
        System.out.printf("║  %s %-10s %sRs.%.2f%n",
                txn.getTimestamp().substring(0, 10), txn.getType(), sign, txn.getAmount());
    }

    System.out.println("╚═══════════════════════════════════════════════╝");
}
```
</details>

---

## Quick Quiz

1. What does LIFO stand for? Give a real-world example.
2. What does FIFO stand for? Give a real-world example.
3. What does `pop()` do on an empty Stack?
4. What does `poll()` do on an empty Queue?
5. What is the difference between `push`/`pop` (Stack) and `offer`/`poll` (Queue)?
6. In a singly linked list, how do you access the 5th element?
7. What is the time complexity of `add(0, item)` for ArrayList vs LinkedList?
8. When should you choose LinkedList over ArrayList?
9. Why did we store `balanceBefore` in the Transaction class?
10. Why is Stack correct for undo (not Queue)?

<details>
<summary><strong>Answers</strong></summary>

1. **Last In, First Out**. Example: stack of plates, browser back button, Ctrl+Z undo.
2. **First In, First Out**. Example: bank counter queue, print job queue, customer service line.
3. It throws `EmptyStackException`. Always check `isEmpty()` first.
4. It returns **`null`**. This is safer — no exception thrown. (This is why `poll` is preferred over `remove`.)
5. `push`/`pop` work on the **top** (LIFO). `offer`/`poll` work on **back/front** (FIFO). Different ends of the structure.
6. Start at the head and follow `next` pointers 4 times. There's no direct index access — you must traverse. That's O(n).
7. **ArrayList**: O(n) — must shift all existing elements right. **LinkedList**: O(1) — just re-link the head pointer.
8. When you frequently add/remove at the **beginning** of the list, or when you use it as a **Queue**. If you need random access by index, stick with ArrayList.
9. To support **undo**. When we undo a transaction, we need to know what the balance was BEFORE the transaction so we can restore it exactly.
10. Undo reverses the **most recent** action first (LIFO). If you used a Queue, you'd undo the **oldest** action first (FIFO), which makes no sense — you'd be undoing something from an hour ago while the last action stays.
</details>

---

## What We Built Today

| Step | Feature | Data Structure | Pattern |
|------|---------|---------------|---------|
| Step 1-2 | Transaction undo | **Stack** | LIFO — reverse most recent |
| Step 3-4 | Service request queue | **Queue** | FIFO — first come first served |
| Step 5 | Understood LinkedList internals | **LinkedList** | Node-based, efficient at ends |

### All Data Structures in Our Banking App

| Structure | What it stores | Why this structure |
|-----------|---------------|-------------------|
| `ArrayList<Account>` | All accounts | Ordered display, random access |
| `HashMap<Long, Account>` | Account lookup | O(1) by account number |
| `Stack<Transaction>` | Undo history | LIFO — most recent first |
| `List<Transaction>` | Full transaction log | Chronological record |
| `Map<Long, List<Transaction>>` | Per-account transactions | Fast lookup by account |
| `Queue<ServiceRequest>` | Pending requests | FIFO — process in order |

## What's Next

**Day 6** is **Java and Algorithms Assessment** — review everything from Days 1-5:
- OOP: classes, inheritance, polymorphism, abstract classes
- Exception handling: try-catch, custom exceptions
- Collections: ArrayList, HashMap
- Data structures: Stack, Queue, LinkedList
- Searching: linear search, binary search, Big-O

**Day 7** will introduce **File Handling** — we'll save our accounts and transactions to files so they persist after the program exits.
