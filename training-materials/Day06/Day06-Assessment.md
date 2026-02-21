# Day 6 — Java and Algorithms Assessment

## Student Assessment Paper

> **Duration**: ~3.5 hours
> **Total Marks**: 120
> **Coverage**: Days 1–5 (Fundamentals, OOP, Exceptions, Collections, Data Structures, Algorithms)
> **Instructions**: Answer all questions. For coding sections, write compilable Java code.

---

## Section A: Multiple Choice (20 marks)

*Choose the single best answer. 1 mark each.*

---

**Q1.** Which of the following is NOT a primitive data type in Java?

a) `int`
b) `boolean`
c) `String`
d) `double`

---

**Q2.** What is the output?

```java
double x = 7.9;
int y = (int) x;
System.out.println(y);
```

a) 8
b) 7.9
c) 7
d) Compile error

---

**Q3.** Which loop is guaranteed to execute its body at least once?

a) `for`
b) `while`
c) `do-while`
d) enhanced `for`

---

**Q4.** What does `private` mean for a class field?

a) Accessible from any class
b) Accessible from the same package
c) Accessible only within the same class
d) Accessible from subclasses

---

**Q5.** What is the output?

```java
String a = new String("hello");
String b = new String("hello");
System.out.println(a == b);
System.out.println(a.equals(b));
```

a) `true true`
b) `false false`
c) `false true`
d) `true false`

---

**Q6.** Which keyword is used to inherit from a parent class?

a) `implements`
b) `super`
c) `extends`
d) `inherits`

---

**Q7.** What is the difference between method overriding and overloading?

a) Overriding: same name, different params. Overloading: same name, same params.
b) Overriding: same name, same params in parent-child. Overloading: same name, different params in same class.
c) They are the same thing.
d) Overriding happens at compile time, overloading at runtime.

---

**Q8.** Which statement about abstract classes is TRUE?

a) An abstract class cannot have constructors
b) An abstract class cannot have concrete methods
c) You cannot create objects directly from an abstract class
d) An abstract class must have at least one abstract method

---

**Q9.** What does `super(name, accNo, balance)` do in a child class constructor?

a) Creates a new parent object
b) Calls the parent class constructor with those arguments
c) Calls a static method in the parent class
d) Creates a copy of the parent

---

**Q10.** Given `Account acc = new SavingsAccount(...)`, which `withdraw()` runs when you call `acc.withdraw(1000)`?

a) Account's withdraw (reference type determines)
b) SavingsAccount's withdraw (actual object type determines)
c) Both — Account's first, then SavingsAccount's
d) Compile error — can't call withdraw on Account reference

---

**Q11.** Which block always executes in a try-catch-finally?

a) `try`
b) `catch`
c) `finally`
d) All three

---

**Q12.** What happens if you catch `Exception` before `NullPointerException`?

a) Works fine — most specific match wins
b) Compile error — unreachable catch block
c) Runtime error
d) `NullPointerException` handler runs first

---

**Q13.** What is the difference between `throw` and `throws`?

a) `throw` is used in method signature; `throws` creates an exception
b) `throw` creates and raises an exception; `throws` declares a method may throw one
c) They are interchangeable
d) `throw` is for checked exceptions; `throws` is for unchecked

---

**Q14.** What is the main advantage of `ArrayList` over a regular array?

a) Faster access by index
b) Uses less memory
c) Grows and shrinks dynamically
d) Supports primitive types directly

---

**Q15.** What does `HashMap.get(key)` return if the key does not exist?

a) 0
b) An empty string
c) `null`
d) Throws `KeyNotFoundException`

---

**Q16.** Can a `HashMap` have duplicate keys?

a) Yes — both key and value can be duplicated
b) No — duplicate key replaces the old value
c) Yes — but only if the values are different
d) No — it throws an exception

---

**Q17.** What is the time complexity of `HashMap.get(key)`?

a) O(n)
b) O(log n)
c) O(1)
d) O(n²)

---

**Q18.** What is the pre-condition for binary search to work?

a) Data must be unique
b) Data must be sorted
c) Data must be in an array (not a list)
d) Data must be numeric

---

**Q19.** A Stack follows which principle?

a) FIFO — First In, First Out
b) LIFO — Last In, First Out
c) Random access
d) Priority-based

---

**Q20.** What does `queue.poll()` return when the queue is empty?

a) Throws `EmptyQueueException`
b) Returns 0
c) Returns `null`
d) Returns the last element

---

### Section A — Answer Key

| Q | Answer | Explanation |
|---|--------|-------------|
| 1 | c | `String` is a reference type (class), not a primitive |
| 2 | c | Explicit cast truncates decimal — `(int) 7.9` = `7` |
| 3 | c | `do-while` checks condition AFTER the body executes |
| 4 | c | `private` restricts access to the declaring class only |
| 5 | c | `==` compares references (different objects), `.equals()` compares content |
| 6 | c | `extends` creates inheritance relationship |
| 7 | b | Override: same signature in parent-child. Overload: same name, different params |
| 8 | c | Abstract classes cannot be instantiated with `new` |
| 9 | b | `super(...)` calls the parent's matching constructor |
| 10 | b | Runtime polymorphism — actual object type (SavingsAccount) determines which method runs |
| 11 | c | `finally` always runs (except `System.exit()`) |
| 12 | b | Broader exception catches everything — specific catch becomes unreachable |
| 13 | b | `throw` raises; `throws` declares |
| 14 | c | ArrayList resizes automatically; arrays are fixed |
| 15 | c | Returns `null` — always check before using |
| 16 | b | Duplicate key replaces old value silently |
| 17 | c | HashMap provides O(1) average-case lookup |
| 18 | b | Binary search requires sorted data |
| 19 | b | Stack = LIFO (Last In, First Out) |
| 20 | c | `poll()` returns `null` on empty queue (safe). `remove()` would throw. |

---

## Section B: Short Answer (30 marks)

*Answer in 2–4 sentences. 3 marks each.*

---

**Q1.** Explain the difference between JDK, JRE, and JVM.

**Q2.** Why should `balance` in the Account class not have a public setter method?

**Q3.** What is the purpose of the `this` keyword? Give an example from the banking app.

**Q4.** Explain the IS-A relationship with an example from the banking app.

**Q5.** Why do we make the Account class `abstract`?

**Q6.** What is the purpose of `@Override`? What happens if you misspell the method name without it?

**Q7.** Explain the difference between checked and unchecked exceptions. Give one example of each.

**Q8.** Why did we create custom exceptions like `InsufficientBalanceException` instead of using `IllegalArgumentException` everywhere?

**Q9.** Why do we store accounts in BOTH an ArrayList and a HashMap? What does each provide?

**Q10.** In our banking app, why is Stack the right data structure for undo (not Queue)?

---

### Section B — Answer Key

**Q1.**
- **JDK** (Java Development Kit) = tools for developers (compiler `javac`, debugger) + JRE
- **JRE** (Java Runtime Environment) = JVM + standard libraries needed to run Java programs
- **JVM** (Java Virtual Machine) = executes bytecode on the specific platform
- JDK ⊃ JRE ⊃ JVM

**Q2.**
A public `setBalance()` would allow anyone to set the balance to any value, including negative numbers, bypassing all business rules. Balance should only change through validated operations like `deposit()` and `withdraw()`, which enforce rules (positive amounts, minimum balance, overdraft limits). This is encapsulation — protecting data integrity.

**Q3.**
`this` refers to the current object. It's used to distinguish between a field and a parameter with the same name. Example: `this.holderName = holderName;` in the Account constructor — `this.holderName` is the field, `holderName` is the parameter.

**Q4.**
IS-A means a child class is a specialized type of the parent. In our app: "SavingsAccount IS-A Account" — a savings account is a type of account. It inherits all common features (holderName, balance, deposit) and adds its own (interestRate, minimum balance rule in withdraw).

**Q5.**
Because a plain "Account" is too generic — in our banking system, every account must be a specific type (Savings, Current). Making Account abstract prevents `new Account()` and forces developers to create concrete types. It also lets us declare `withdraw()` as abstract, forcing every subclass to implement its own withdrawal rules.

**Q6.**
`@Override` tells the compiler "I intend to override a parent method." If you misspell the method name WITHOUT it, Java treats it as a NEW method — no error, but it won't be called polymorphically. WITH `@Override`, the compiler catches the typo immediately.

**Q7.**
**Checked** exceptions must be handled (try-catch) or declared (throws) — compiler enforces it. Example: `IOException` when reading a file. **Unchecked** exceptions (extend `RuntimeException`) are optional to handle. Example: `NullPointerException`. Unchecked exceptions typically indicate programming errors.

**Q8.**
Custom exceptions are self-documenting — `InsufficientBalanceException` tells you exactly what went wrong. They can also carry domain-specific data (currentBalance, withdrawalAmount) for better error messages. With generic exceptions, every catch block looks the same and you can't distinguish between different business failure scenarios.

**Q9.**
**ArrayList** maintains insertion order — used for displaying all accounts in sequence. **HashMap** provides O(1) lookup by account number — used for instant search. Different access patterns need different data structures. (In real apps, a database handles both.)

**Q10.**
Undo reverses the **most recent** action first (LIFO). Stack's `pop()` gives you the last pushed item. If we used a Queue (FIFO), undo would reverse the **oldest** action first, which is wrong — you'd be undoing something from an hour ago while the most recent action remains.

---

## Section C: Code Reading & Debugging (25 marks)

*Read the code and answer the questions. 5 marks each.*

---

### Q1. What is the output?

```java
public class Test {
    public static void main(String[] args) {
        int x = 10;
        changeValue(x);
        System.out.println("x = " + x);
    }

    public static void changeValue(int num) {
        num = 99;
    }
}
```

---

### Q2. Find and fix the bug (2 bugs):

```java
public class Account {
    private String name;
    private double balance;

    public Account(String name, double balance) {
        name = name;
        balance = balance;
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient");
        }
        balance -= amount;
    }
}
```

---

### Q3. What is the output?

```java
Account acc1 = new SavingsAccount("Ravi", 1001, 50000, 4.5);
Account acc2 = new CurrentAccount("Priya", 1002, 30000, 10000);

Account[] accounts = {acc1, acc2};

for (Account acc : accounts) {
    acc.withdraw(45000);
}
```

Assume:
- SavingsAccount.withdraw() has minimum balance of Rs.1000
- CurrentAccount.withdraw() allows overdraft up to limit

---

### Q4. What is the output?

```java
Stack<Integer> stack = new Stack<>();
stack.push(10);
stack.push(20);
stack.push(30);

System.out.println(stack.pop());
System.out.println(stack.peek());
System.out.println(stack.pop());
System.out.println(stack.size());
```

---

### Q5. Find and fix the bug:

```java
public Account findAccount(long accNo) {
    Account acc = accountMap.get(accNo);
    acc.displayInfo();
    return acc;
}
```

---

### Section C — Answer Key

**Q1.** Output: `x = 10`
Java is pass-by-value for primitives. `changeValue` receives a copy of `x`. Changing `num` inside the method does not affect the original `x`. (3 marks for correct output, 2 marks for explanation)

**Q2.** Two bugs:

Bug 1 — Constructor: Missing `this.` — assigns parameter to itself, fields stay null/0.
```java
// WRONG: name = name;
// FIX:
this.name = name;
this.balance = balance;
```

Bug 2 — withdraw: Missing `else` or `return` — `balance -= amount` runs even when insufficient.
```java
// FIX:
if (amount > balance) {
    System.out.println("Insufficient");
    return;     // <-- must stop here
}
balance -= amount;
```

(2.5 marks per bug)

**Q3.**
- `acc1.withdraw(45000)`: SavingsAccount — blocked. 45000 > 50000 - 1000 = 49000? No, 45000 <= 49000, so it succeeds. Balance = 5000.
  Wait — actually 45000 <= 49000, so withdrawal is allowed. Balance = 50000 - 45000 = 5000.
- `acc2.withdraw(45000)`: CurrentAccount — 45000 > 30000 + 10000 = 40000? Yes. **Blocked** — exceeds overdraft limit.

Output:
```
Withdrawn Rs.45000.0 | Balance: Rs.5000.0
Exceeds overdraft limit.
Max withdrawal: Rs.40000.0
```

(3 marks for correct behavior of each, 2 marks if partially correct — e.g. got one right)

**Q4.**
```
30        ← pop() removes and returns top
20        ← peek() returns top without removing
20        ← pop() removes and returns the (same) top
1         ← only 10 remains
```

(1 mark per line, 1 mark for all correct)

**Q5.**
Bug: `accountMap.get(accNo)` may return `null` if the account doesn't exist. Calling `acc.displayInfo()` on `null` throws `NullPointerException`.

Fix:
```java
public Account findAccount(long accNo) {
    Account acc = accountMap.get(accNo);
    if (acc == null) {
        System.out.println("Account #" + accNo + " not found.");
        return null;
    }
    acc.displayInfo();
    return acc;
}
```

(2 marks for identifying null issue, 3 marks for correct fix)

---

## Section D: Coding Assignments (45 marks)

*Write complete, compilable Java code. You may use an IDE.*

---

### D1. Employee Management System (15 marks)

Build a small system that demonstrates OOP concepts:

**Requirements:**

1. Create an abstract class `Employee` with:
   - Private fields: `name` (String), `id` (int), `baseSalary` (double)
   - A parameterized constructor
   - Getters for all fields
   - An abstract method: `double calculatePay()`
   - A concrete method: `void displayInfo()` that prints name, id, and pay

2. Create `FullTimeEmployee extends Employee` with:
   - Additional field: `bonus` (double)
   - `calculatePay()` returns `baseSalary + bonus`

3. Create `ContractEmployee extends Employee` with:
   - Additional field: `hoursWorked` (int)
   - `calculatePay()` returns `baseSalary * hoursWorked / 160` (pro-rata based on 160 hrs/month)

4. In `main`:
   - Create an array of `Employee` with 2 full-time and 1 contract employee
   - Loop through and call `displayInfo()` on each (demonstrate polymorphism)
   - Calculate and print total payroll

**Grading rubric:**
- Abstract class correctly defined: 3 marks
- Both subclasses with correct constructors and override: 4 marks
- Polymorphic usage in main: 3 marks
- Total payroll calculation: 2 marks
- Clean code and naming conventions: 3 marks

---

### D2. Inventory with Collections and Search (15 marks)

Build an inventory lookup system:

**Requirements:**

1. Create a `Product` class with: `id` (int), `name` (String), `price` (double), `quantity` (int)

2. In `main`:
   - Store products in an `ArrayList<Product>`
   - Also store in a `HashMap<Integer, Product>` (key = product id)
   - Pre-load at least 5 products

3. Implement these search methods:
   - `findById(int id)` — use HashMap, O(1)
   - `searchByName(String keyword)` — linear search, case-insensitive partial match, return `List<Product>`
   - `findInPriceRange(double min, double max)` — linear search, return `List<Product>`

4. Implement binary search:
   - Sort products by price
   - Write `binarySearchByPrice(double targetPrice)` — find a product with exact price match

5. Print the Big-O complexity as a comment above each search method

**Grading rubric:**
- Product class with encapsulation: 2 marks
- ArrayList + HashMap setup: 2 marks
- findById with HashMap: 2 marks
- searchByName (linear): 3 marks
- findInPriceRange (linear): 2 marks
- binarySearchByPrice: 3 marks
- Big-O comments correct: 1 mark

---

### D3. Banking Operations with Stack and Queue (15 marks)

Extend the banking app with these features:

**Requirements:**

1. **Transaction Stack with Undo** (7 marks):
   - Create a `Transaction` class with: `type`, `amount`, `accountId`, `balanceBefore`
   - After each deposit/withdraw, push a Transaction onto a Stack
   - Implement `undo()` that pops the last transaction and restores the balance
   - Implement `undoAll()` that reverses all transactions
   - Test with at least 3 transactions, then undo them one by one

2. **Service Request Queue** (5 marks):
   - Create a `ServiceRequest` class with: `id` (auto-increment), `customerName`, `type`
   - Add 4+ requests to a Queue
   - Process them one at a time (FIFO), printing each as it's processed
   - Show queue size after each processing step

3. **Mini Statement** (3 marks):
   - Keep a list of all transactions for an account
   - Display the last 5 transactions in reverse order (most recent first)
   - Use a Stack to reverse the order for display

**Grading rubric:**
- Transaction class: 1 mark
- Push on deposit/withdraw: 2 marks
- Undo (single): 2 marks
- Undo all: 2 marks
- ServiceRequest class: 1 mark
- Queue processing (FIFO): 2 marks
- Queue size tracking: 2 marks
- Mini statement with Stack reversal: 3 marks

---

## Bonus Questions (optional, 10 extra marks)

*For trainees who finish early.*

### Bonus 1 (5 marks): Transfer with Rollback

Write a `transfer(Account from, Account to, double amount)` method that:
- Withdraws from source
- Deposits to destination
- If the deposit fails (destination is inactive), rolls back the withdrawal
- Records the transfer as two transactions on the stack (one for each account)
- Both should be undoable independently

### Bonus 2 (5 marks): Explain the Output

```java
List<Integer> list = new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50));

for (int i = 0; i < list.size(); i++) {
    if (list.get(i) == 30) {
        list.remove(i);
    }
}

System.out.println(list);
```

1. What is the output?
2. Is there a bug? If so, what is it and how would you fix it?

---

### Bonus Answers

**Bonus 1:** (Model solution — accept any correct implementation)

```java
public static void transfer(Account from, Account to, double amount) {
    double fromBalBefore = from.getBalance();
    from.withdraw(amount);

    try {
        double toBalBefore = to.getBalance();
        to.deposit(amount);

        transactionStack.push(new Transaction("TRANSFER_OUT", amount,
                from.getAccountNumber(), fromBalBefore));
        transactionStack.push(new Transaction("TRANSFER_IN", amount,
                to.getAccountNumber(), toBalBefore));
    } catch (Exception e) {
        from.restoreBalance(fromBalBefore);
        System.out.println("Transfer failed. Rolled back. Reason: " + e.getMessage());
    }
}
```

**Bonus 2:**
1. Output: `[10, 20, 40, 50]` — 30 is correctly removed.
2. There IS a subtle bug, though it doesn't manifest with this specific data. After removing index 2 (value 30), element 40 shifts to index 2, but `i` increments to 3 — so 40 is skipped and never checked. If the condition were checking consecutive elements (e.g., remove all items > 25), 40 would be skipped.

Fix: iterate backwards, or use an Iterator:
```java
list.removeIf(n -> n == 30);
// or
Iterator<Integer> it = list.iterator();
while (it.hasNext()) {
    if (it.next() == 30) it.remove();
}
```

---

## Assessment Summary Checklist (for instructor)

After grading, tally how trainees performed per topic area:

| Topic Area | Related Questions | Class Average |
|-----------|------------------|---------------|
| Java Basics (types, operators, flow) | A1-A3, C1 | ___% |
| OOP (classes, encapsulation, `this`) | A4, A5, B2-B3, C2 | ___% |
| Inheritance & Polymorphism | A6-A10, B4-B6, C3 | ___% |
| Exception Handling | A11-A13, B7-B8, C5 | ___% |
| Collections (ArrayList, HashMap) | A14-A17, B9, D2 | ___% |
| Data Structures (Stack, Queue) | A19-A20, B10, C4, D3 | ___% |
| Searching & Big-O | A18, D2 (binary search) | ___% |

Use this to decide which topics need quick revision before proceeding to Day 7.
