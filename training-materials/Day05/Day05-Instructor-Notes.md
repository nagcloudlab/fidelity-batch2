# Day 5 — Data Structures: Stack, Queue, LinkedList

## Instructor Guide

> **Duration**: Full day (~4 hrs)
> **Pre-requisite**: Day 4 BankApp (ArrayList + HashMap + search)
> **Case Study**: Add transaction undo (Stack), service request queue (Queue), and explore LinkedList as a flexible structure
> **Goal by end of day**: Trainees understand when and why to choose Stack, Queue, or LinkedList over ArrayList, and can implement each in the banking context

---

## Topic 1: Why Data Structures in Real Applications

#### Key Points (10 min)

- **Day 4 gave us ArrayList and HashMap** — great general-purpose structures. But not every problem is best solved with them.
- **Data structure = a way to organize data that matches how you USE the data.**
- **Algorithm = the steps to process that data.**
- The right structure makes the algorithm simple. The wrong structure makes it painful.

#### Real-world examples

| Real Scenario | Access Pattern | Best Structure |
|--------------|---------------|---------------|
| Undo last action | Last in, first out | **Stack** |
| Print queue / service requests | First in, first out | **Queue** |
| Frequent insert/delete in middle | Node-based linking | **LinkedList** |
| Random access by index | Direct position access | **ArrayList** |
| Lookup by key | Key-value pairs | **HashMap** |

#### Teaching Tip

> "Choosing the right data structure is like choosing the right vehicle:
> - Need to carry one person fast? → Bike (Stack — quick, simple, one direction)
> - Need a line of people served in order? → Queue at a counter
> - Need to frequently add/remove people from the middle? → LinkedList (easy rearranging)
> - Need to jump to any person instantly? → ArrayList (direct index)
>
> The data doesn't change — how you ACCESS it does."

---

## Topic 2: Stack — LIFO Principle

#### Key Points (20 min)

- **LIFO** = Last In, First Out. The most recently added item is the first one removed.
- Think of a **stack of plates** — you always take from the top.
- **Three core operations**:

| Operation | What it does | Returns |
|-----------|-------------|---------|
| `push(item)` | Add to top | void |
| `pop()` | Remove & return top | the item |
| `peek()` | Look at top without removing | the item |

- Also useful: `isEmpty()`, `size()`
- **Common uses in software**:
  - **Undo/Redo** (text editors, transactions)
  - **Back button** in browsers
  - **Call stack** in method execution
  - **Expression evaluation** (parsing brackets)
  - **Rollback** in databases

#### Visual

```
push(A)  push(B)  push(C)    pop()     peek()

         ┌───┐   ┌───┐               ┌───┐
         │ B │   │ C │  ← top        │ B │  ← returns B
┌───┐    ├───┤   ├───┤      ┌───┐    ├───┤     (not removed)
│ A │    │ A │   │ B │      │ B │    │ A │
└───┘    └───┘   ├───┤      ├───┤    └───┘
                 │ A │      │ A │
                 └───┘      └───┘
```

#### Java Implementation

```java
import java.util.Stack;

Stack<String> history = new Stack<>();

history.push("Opened file");
history.push("Typed 'Hello'");
history.push("Deleted line");

System.out.println(history.peek());   // "Deleted line" (still on stack)
System.out.println(history.pop());    // "Deleted line" (removed)
System.out.println(history.pop());    // "Typed 'Hello'" (removed)
System.out.println(history.size());   // 1 ("Opened file" remains)
```

#### Case Study Step 1 — Transaction class (enhanced from Day 4)

```java
public class Transaction {
    private String type;       // "DEPOSIT", "WITHDRAW"
    private double amount;
    private long accountNumber;
    private double balanceBefore;
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

    // Getters
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public long getAccountNumber() { return accountNumber; }
    public double getBalanceBefore() { return balanceBefore; }
    public double getBalanceAfter() { return balanceAfter; }
    public String getTimestamp() { return timestamp; }

    public void display() {
        System.out.printf("  [%s] %s Rs.%.2f | Account #%d | %.2f → %.2f%n",
                timestamp, type, amount, accountNumber, balanceBefore, balanceAfter);
    }
}
```

#### Case Study Step 2 — Undo with Stack

```java
// In BankApp — add a transaction stack
static Stack<Transaction> transactionStack = new Stack<>();

// After a successful deposit:
static void handleDeposit(Scanner sc) {
    Account acc = selectAccount(sc);
    if (acc == null) return;
    System.out.print("Enter deposit amount: ");
    try {
        double amount = sc.nextDouble();
        double balBefore = acc.getBalance();
        acc.deposit(amount);
        double balAfter = acc.getBalance();

        Transaction txn = new Transaction("DEPOSIT", amount,
                acc.getAccountNumber(), balBefore, balAfter);
        transactionStack.push(txn);
    } catch (InvalidAmountException e) {
        System.out.println("INVALID: " + e.getMessage());
    } catch (AccountInactiveException e) {
        System.out.println("BLOCKED: " + e.getMessage());
    } catch (InputMismatchException e) {
        System.out.println("Invalid input.");
        sc.nextLine();
    }
}

// Similarly for withdrawal — push after success

// Undo last transaction:
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

    // Reverse the transaction
    if (lastTxn.getType().equals("DEPOSIT")) {
        // Undo deposit = withdraw the deposited amount
        acc.setBalance(lastTxn.getBalanceBefore());
        System.out.println("UNDO: Reversed deposit of Rs." + lastTxn.getAmount()
                + " on Account #" + lastTxn.getAccountNumber());
    } else if (lastTxn.getType().equals("WITHDRAW")) {
        // Undo withdrawal = restore the withdrawn amount
        acc.setBalance(lastTxn.getBalanceBefore());
        System.out.println("UNDO: Reversed withdrawal of Rs." + lastTxn.getAmount()
                + " on Account #" + lastTxn.getAccountNumber());
    }

    System.out.println("Balance restored to Rs." + acc.getBalance());
}
```

> **Teaching point**: "Notice we use `setBalance(balanceBefore)` — we don't call deposit/withdraw to undo. We directly restore the previous state. This is why we saved `balanceBefore` in the Transaction."

> **Important**: `setBalance` is `protected` in Account — BankApp can't call it directly unless it's in the same package. Discuss package access or add a package-private `restoreBalance()` method.

#### Quick fix — add restoreBalance to Account

```java
// In Account.java — package-private method for undo operations
void restoreBalance(double balance) {
    this.setBalance(balance);
}
```

---

## Topic 3: Queue — FIFO Principle

#### Key Points (20 min)

- **FIFO** = First In, First Out. The earliest added item is processed first.
- Think of a **bank counter queue** — first person in line gets served first.
- **Core operations**:

| Operation | What it does | Returns | On failure |
|-----------|-------------|---------|------------|
| `offer(item)` | Add to back | true/false | Returns false (preferred) |
| `add(item)` | Add to back | true | Throws exception |
| `poll()` | Remove & return front | the item / null | Returns null if empty |
| `remove()` | Remove & return front | the item | Throws exception if empty |
| `peek()` | Look at front | the item / null | Returns null if empty |

- **Prefer `offer`/`poll`/`peek`** over `add`/`remove`/`element` — they return null instead of throwing exceptions.
- **Common uses**:
  - **Request processing** (service tickets, API requests)
  - **Print queue**
  - **BFS** (Breadth-First Search in trees/graphs)
  - **Task scheduling**

#### Visual

```
offer(A)  offer(B)  offer(C)    poll()      peek()

Front → Back         Front → Back
┌───┐
│ A │    A → B      A → B → C   B → C       B → C
└───┘                           returns A    returns B
                                             (not removed)
```

#### Java Implementation

- `Queue` is an **interface** in Java. Common implementations:
  - `LinkedList` — most common
  - `ArrayDeque` — faster for most cases
  - `PriorityQueue` — sorted by priority

```java
import java.util.LinkedList;
import java.util.Queue;

Queue<String> serviceQueue = new LinkedList<>();

serviceQueue.offer("Request #1 — Open account");
serviceQueue.offer("Request #2 — Issue checkbook");
serviceQueue.offer("Request #3 — Loan inquiry");

System.out.println("Next: " + serviceQueue.peek());    // Request #1
System.out.println("Processing: " + serviceQueue.poll()); // Request #1 (removed)
System.out.println("Next: " + serviceQueue.peek());    // Request #2
System.out.println("Remaining: " + serviceQueue.size()); // 2
```

#### Case Study Step 3 — Service Request Queue

```java
// ServiceRequest.java
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
        System.out.printf("  [#%d] Account #%d | %s | %s | %s%n",
                requestId, accountNumber, requestType, description, timestamp);
    }
}
```

```java
// In BankApp
static Queue<ServiceRequest> serviceQueue = new LinkedList<>();

// Submit a service request
static void handleSubmitRequest(Scanner sc) {
    Account acc = selectAccount(sc);
    if (acc == null) return;

    sc.nextLine();
    System.out.println("Request types: CHECKBOOK, STATEMENT, CARD, LOAN, OTHER");
    System.out.print("Request type: ");
    String type = sc.nextLine().trim().toUpperCase();

    System.out.print("Description: ");
    String desc = sc.nextLine().trim();

    ServiceRequest request = new ServiceRequest(acc.getAccountNumber(), type, desc);
    serviceQueue.offer(request);
    System.out.println("Request #" + request.getRequestId() + " submitted. Position in queue: " + serviceQueue.size());
}

// Process next request (bank staff side)
static void handleProcessRequest() {
    if (serviceQueue.isEmpty()) {
        System.out.println("No pending service requests.");
        return;
    }

    ServiceRequest request = serviceQueue.poll();
    System.out.println("Processing request:");
    request.display();
    System.out.println("Request #" + request.getRequestId() + " completed.");
    System.out.println("Remaining in queue: " + serviceQueue.size());
}

// View pending requests
static void viewPendingRequests() {
    if (serviceQueue.isEmpty()) {
        System.out.println("No pending requests.");
        return;
    }
    System.out.println("--- Pending Requests (" + serviceQueue.size() + ") ---");
    for (ServiceRequest req : serviceQueue) {
        req.display();
    }
}
```

> **Teaching point**: "Notice we iterate over the queue to view without removing. `poll()` removes — iteration doesn't. This is how a real service desk would work: staff can view the queue and process one at a time."

---

## Topic 4: LinkedList as a Data Structure

#### Key Points (20 min)

- **Concept**: Instead of storing elements in contiguous memory (like an array), each element (**node**) stores its data AND a reference to the next node.
- **Node structure**:

```
┌──────────────────┐    ┌──────────────────┐    ┌──────────────────┐
│ data: "Ravi"     │    │ data: "Priya"    │    │ data: "Amit"     │
│ next: ──────────────→ │ next: ──────────────→ │ next: null       │
└──────────────────┘    └──────────────────┘    └──────────────────┘
 head                                             tail
```

- **Singly linked** — each node points to the next only (one direction)
- **Doubly linked** — each node points to both next AND previous (two directions)

```
Doubly linked:
null ← [Ravi] ⇄ [Priya] ⇄ [Amit] → null
```

- **Java's `LinkedList`** is **doubly linked**.

#### Why LinkedList?

| Operation | ArrayList | LinkedList |
|-----------|----------|------------|
| Access by index | O(1) — fast | O(n) — slow (must traverse) |
| Add/remove at end | O(1) | O(1) |
| Add/remove at beginning | O(n) — shift all | O(1) — just re-link |
| Add/remove in middle | O(n) — shift elements | O(1)* — just re-link (*if you have the node) |

> **Key insight**: LinkedList is better when you frequently insert/remove at the beginning or middle but rarely access by index.

#### Teaching Tip — Physical demo

> "Imagine each of you is a node. You hold a piece of paper (data) and point to the person next to you (next reference).
> - Insert at beginning: new person stands at the front, points to the old first person. No one else moves.
> - Insert in middle: new person points to the next person, previous person now points to the new one. Only 2 links change.
> - With ArrayList: everyone after the insertion point would have to shift one seat down."

---

## Topic 5: Java Implementation Overview

#### Key Points (15 min)

#### Stack class

```java
import java.util.Stack;

Stack<Transaction> history = new Stack<>();
history.push(txn);
Transaction last = history.pop();
Transaction top = history.peek();
boolean empty = history.isEmpty();
```

> **Note**: `java.util.Stack` extends `Vector` (legacy, synchronized). For modern code, `ArrayDeque` used as a stack is preferred, but `Stack` is clearer for teaching.

#### Queue interface

```java
import java.util.Queue;
import java.util.LinkedList;

Queue<ServiceRequest> queue = new LinkedList<>();
queue.offer(request);
ServiceRequest next = queue.poll();
ServiceRequest front = queue.peek();
```

> `Queue` is an interface. `LinkedList` implements it. `ArrayDeque` also implements it.

#### LinkedList class — dual role

```java
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// As a List (same methods as ArrayList)
List<Account> accountList = new LinkedList<>();
accountList.add(acc);
Account first = accountList.get(0);    // works but O(n)

// As a Queue
Queue<ServiceRequest> requestQueue = new LinkedList<>();
requestQueue.offer(req);
ServiceRequest next = requestQueue.poll();

// LinkedList-specific methods (not on List or Queue interface)
LinkedList<String> linked = new LinkedList<>();
linked.addFirst("A");
linked.addLast("C");
linked.add(1, "B");     // [A, B, C]
linked.removeFirst();   // [B, C]
linked.removeLast();    // [B]
```

#### When to use what

| Need | Use | Why |
|------|-----|-----|
| Undo/rollback | `Stack` | LIFO — most recent first |
| Request processing | `Queue` (via `LinkedList`) | FIFO — first come first served |
| General list with random access | `ArrayList` | O(1) index access |
| Frequent insert/remove at start | `LinkedList` | O(1) at head vs O(n) for ArrayList |
| Key-value lookup | `HashMap` | O(1) by key |

---

## Topic 6: Performance — LinkedList vs ArrayList

#### Key Points (10 min)

| Operation | ArrayList | LinkedList |
|-----------|----------|------------|
| `get(index)` | **O(1)** | O(n) |
| `add(end)` | **O(1)** amortized | **O(1)** |
| `add(0, item)` (start) | O(n) | **O(1)** |
| `add(mid, item)` | O(n) | O(n)* |
| `remove(0)` (start) | O(n) | **O(1)** |
| `remove(mid)` | O(n) | O(n)* |
| Memory per element | Less (just the data) | More (data + 2 pointers) |
| Cache performance | Better (contiguous) | Worse (scattered) |

> *LinkedList insert/remove in middle is O(1) for the re-linking, but O(n) to FIND the position first.

#### Teaching Tip

> "In practice, **ArrayList is the default choice** for most situations. LinkedList wins only when:
> 1. You frequently add/remove at the beginning
> 2. You use it as a Queue (FIFO operations)
> 3. You never access by index
>
> If in doubt, use ArrayList."

#### Quick benchmark demo (optional)

```java
// Insert 100,000 items at index 0
List<Integer> arrayList = new ArrayList<>();
List<Integer> linkedList = new LinkedList<>();

long start = System.currentTimeMillis();
for (int i = 0; i < 100_000; i++) {
    arrayList.add(0, i);     // O(n) each time — shifts everything
}
System.out.println("ArrayList add(0): " + (System.currentTimeMillis() - start) + "ms");

start = System.currentTimeMillis();
for (int i = 0; i < 100_000; i++) {
    linkedList.add(0, i);    // O(1) each time — just re-link
}
System.out.println("LinkedList add(0): " + (System.currentTimeMillis() - start) + "ms");
```

> Results will show LinkedList is much faster for add-at-beginning. This makes the concept concrete.

---

## Case Study — Complete Day 5 Additions

### Transaction.java (enhanced)

```java
public class Transaction {
    private String type;
    private double amount;
    private long accountNumber;
    private double balanceBefore;
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

### ServiceRequest.java

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
    public String getTimestamp() { return timestamp; }

    public void display() {
        System.out.printf("  [#%d] Acc#%d | %-12s | %s | %s%n",
                requestId, accountNumber, requestType, description, timestamp);
    }
}
```

### Account.java — add restoreBalance

```java
// Add to Account.java — package-private for undo support
void restoreBalance(double previousBalance) {
    this.setBalance(previousBalance);
}
```

### BankApp.java — Day 5 additions (add to existing Day 4 code)

```java
import java.util.Stack;
import java.util.Queue;
// ... existing imports ...

public class BankApp {

    // Existing from Day 4
    static List<Account> accounts = new ArrayList<>();
    static Map<Long, Account> accountMap = new HashMap<>();

    // NEW — Day 5
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
            try { choice = sc.nextInt(); }
            catch (InputMismatchException e) { System.out.println("Enter a number."); sc.nextLine(); continue; }

            switch (choice) {
                case 1: viewAllAccounts(); break;
                case 2: handleDeposit(sc); break;
                case 3: handleWithdrawal(sc); break;
                case 4: handleUndo(); break;
                case 5: handleViewTransactions(sc); break;
                case 6: handleSubmitRequest(sc); break;
                case 7: handleProcessRequest(); break;
                case 8: viewPendingRequests(); break;
                case 9: handleSearchByNumber(sc); break;
                case 10: System.out.println("Thank you!"); break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 10);

        sc.close();
    }

    static void displayMenu() {
        System.out.println("\n===== BANK APPLICATION (Day 5) =====");
        System.out.println("--- Accounts ---");
        System.out.println("1. View All Accounts");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("--- Transactions ---");
        System.out.println("4. Undo Last Transaction");
        System.out.println("5. View Transaction History");
        System.out.println("--- Service Requests ---");
        System.out.println("6. Submit Service Request");
        System.out.println("7. Process Next Request");
        System.out.println("8. View Pending Requests");
        System.out.println("--- Search ---");
        System.out.println("9. Search Account by Number");
        System.out.println("10. Exit");
        System.out.print("Enter choice: ");
    }

    // --- Transaction Recording ---

    static void recordTransaction(String type, double amount, long accNo,
                                   double balBefore, double balAfter) {
        Transaction txn = new Transaction(type, amount, accNo, balBefore, balAfter);
        transactionStack.push(txn);
        allTransactions.add(txn);
        transactionsByAccount.computeIfAbsent(accNo, k -> new ArrayList<>()).add(txn);
    }

    // --- Deposit / Withdraw (updated to record transactions) ---

    static void handleDeposit(Scanner sc) {
        Account acc = selectAccount(sc);
        if (acc == null) return;
        System.out.print("Enter deposit amount: ");
        try {
            double amount = sc.nextDouble();
            double balBefore = acc.getBalance();
            acc.deposit(amount);
            recordTransaction("DEPOSIT", amount, acc.getAccountNumber(), balBefore, acc.getBalance());
        } catch (InvalidAmountException | AccountInactiveException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input."); sc.nextLine();
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
            recordTransaction("WITHDRAW", amount, acc.getAccountNumber(), balBefore, acc.getBalance());
        } catch (InsufficientBalanceException | InvalidAmountException | AccountInactiveException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input."); sc.nextLine();
        }
    }

    // --- STACK — Undo ---

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
        System.out.println("UNDO: Reversed " + lastTxn.getType() + " of Rs."
                + lastTxn.getAmount() + " on Account #" + lastTxn.getAccountNumber());
        System.out.println("Balance restored to Rs." + acc.getBalance());
    }

    // --- Transaction History ---

    static void handleViewTransactions(Scanner sc) {
        System.out.print("Enter account number (0 for all): ");
        try {
            long accNo = sc.nextLong();
            if (accNo == 0) {
                if (allTransactions.isEmpty()) {
                    System.out.println("No transactions recorded.");
                } else {
                    System.out.println("--- All Transactions (" + allTransactions.size() + ") ---");
                    for (Transaction txn : allTransactions) { txn.display(); }
                }
            } else {
                List<Transaction> txns = transactionsByAccount.get(accNo);
                if (txns == null || txns.isEmpty()) {
                    System.out.println("No transactions for Account #" + accNo);
                } else {
                    System.out.println("--- Transactions for Account #" + accNo + " ---");
                    for (Transaction txn : txns) { txn.display(); }
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input."); sc.nextLine();
        }
    }

    // --- QUEUE — Service Requests ---

    static void handleSubmitRequest(Scanner sc) {
        Account acc = selectAccount(sc);
        if (acc == null) return;
        sc.nextLine();
        System.out.print("Request type (CHECKBOOK/STATEMENT/CARD/LOAN/OTHER): ");
        String type = sc.nextLine().trim().toUpperCase();
        System.out.print("Description: ");
        String desc = sc.nextLine().trim();

        ServiceRequest request = new ServiceRequest(acc.getAccountNumber(), type, desc);
        serviceQueue.offer(request);
        System.out.println("Request #" + request.getRequestId() + " submitted. Queue position: " + serviceQueue.size());
    }

    static void handleProcessRequest() {
        if (serviceQueue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }
        ServiceRequest req = serviceQueue.poll();
        System.out.println("Processing request:");
        req.display();
        System.out.println("Request #" + req.getRequestId() + " completed. Remaining: " + serviceQueue.size());
    }

    static void viewPendingRequests() {
        if (serviceQueue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }
        System.out.println("--- Pending Requests (" + serviceQueue.size() + ") ---");
        for (ServiceRequest req : serviceQueue) { req.display(); }
    }

    // --- Existing helpers (from Day 4, kept as-is) ---
    // addAccount, viewAllAccounts, selectAccount, handleSearchByNumber...
}
```

---

## Day 5 Exercises

### Exercise 1: Multi-level Undo
**Problem**: Allow unlimited undo — keep undoing until the stack is empty. Add a "Undo All" menu option that reverses all transactions.

### Exercise 2: Redo Stack
**Problem**: When the user undoes a transaction, push it onto a separate `redoStack`. Add a "Redo" menu option that re-applies the last undone transaction. If the user performs a new transaction, clear the redo stack.

### Exercise 3: Priority Service Queue
**Problem**: Create a priority queue where LOAN requests are processed before others. Use `PriorityQueue` with a custom comparator. Test with a mix of CHECKBOOK, LOAN, and CARD requests.

### Exercise 4: LinkedList — Recent Transactions Display
**Problem**: Use a `LinkedList<Transaction>` to keep the 10 most recent transactions. When a new transaction arrives and the list has 10 items, remove the oldest (first). Display them in reverse order (most recent first).

### Exercise 5: Full Challenge — Mini Statement
**Problem**: Add a "Mini Statement" feature:
- Shows last 5 transactions for a specific account
- Uses a `Stack` to reverse the order (most recent first)
- Shows opening balance, each transaction, and closing balance
- Format like a real bank statement

---

## Day 5 Quiz (10 questions)

1. What does LIFO stand for? Give a real-world example.
2. What does FIFO stand for? Give a real-world example.
3. What is the difference between `push` and `offer`?
4. What does `pop()` return on an empty Stack?
5. What does `poll()` return on an empty Queue?
6. In a singly linked list, how do you access the 5th element?
7. What is the time complexity of inserting at the beginning of an ArrayList vs LinkedList?
8. When should you prefer LinkedList over ArrayList?
9. Why did we store `balanceBefore` in the Transaction class?
10. In our banking app, why is Stack the right choice for undo (not Queue)?

---

## Day 5 Summary

| Step | What Changed | Data Structure | Use Case |
|------|-------------|---------------|----------|
| Step 1 | Enhanced Transaction class with before/after balance | — | Foundation for undo |
| Step 2 | Added undo via transaction stack | **Stack** (LIFO) | Reverse last action |
| Step 3 | Added service request queue | **Queue** (FIFO) | Process in order |
| Step 4-6 | Understood LinkedList internals | **LinkedList** | List + Queue dual role |

### All Data Structures in Our App

| Structure | Purpose | Access Pattern |
|-----------|---------|---------------|
| `ArrayList<Account>` | All accounts (ordered display) | Sequential, by index |
| `HashMap<Long, Account>` | Account lookup by number | By key — O(1) |
| `Stack<Transaction>` | Undo last transaction | LIFO — most recent first |
| `List<Transaction>` | Full transaction history | Sequential |
| `Map<Long, List<Transaction>>` | Transactions grouped by account | By key → list |
| `Queue<ServiceRequest>` | Pending service requests | FIFO — first come first served |

> **Preview for Day 6**: "Tomorrow is assessment day — Java and Algorithms. Review all concepts from Days 1-5. Focus on: OOP (classes, inheritance, polymorphism), Collections (ArrayList, HashMap), Data Structures (Stack, Queue), and Searching (Linear, Binary, Big-O)."
