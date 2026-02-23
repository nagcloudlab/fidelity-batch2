# Day 6 — Java and Algorithms Assessment

## Assessment Paper

> **Duration**: ~3.5 hours
> **Total Marks**: 120
> **Coverage**: Days 1–5 (Fundamentals, OOP, Exceptions, Collections, Data Structures, Algorithms)
> **Instructions**: Answer all questions. Coding sections must be compilable Java.

---

## Section A: Multiple Choice (20 marks)

*Choose the single best answer. 1 mark each.*

---

**Q1.** Which of the following is NOT a primitive data type in Java?

a) `int`  &emsp; b) `boolean`  &emsp; c) `String`  &emsp; d) `double`

---

**Q2.** What is the output?

```java
double x = 7.9;
int y = (int) x;
System.out.println(y);
```

a) 8  &emsp; b) 7.9  &emsp; c) 7  &emsp; d) Compile error

---

**Q3.** Which loop is guaranteed to execute its body at least once?

a) `for`  &emsp; b) `while`  &emsp; c) `do-while`  &emsp; d) enhanced `for`

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

a) `true true`  &emsp; b) `false false`  &emsp; c) `false true`  &emsp; d) `true false`

---

**Q6.** Which keyword is used to inherit from a parent class?

a) `implements`  &emsp; b) `super`  &emsp; c) `extends`  &emsp; d) `inherits`

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

a) `try`  &emsp; b) `catch`  &emsp; c) `finally`  &emsp; d) All three

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

a) 0  &emsp; b) An empty string  &emsp; c) `null`  &emsp; d) Throws `KeyNotFoundException`

---

**Q16.** Can a `HashMap` have duplicate keys?

a) Yes — both key and value can be duplicated
b) No — duplicate key replaces the old value
c) Yes — but only if the values are different
d) No — it throws an exception

---

**Q17.** What is the time complexity of `HashMap.get(key)`?

a) O(n)  &emsp; b) O(log n)  &emsp; c) O(1)  &emsp; d) O(n²)

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

## Section B: Short Answer (30 marks)

*Answer in 2–4 sentences. 3 marks each.*

---

**Q1.** Explain the difference between JDK, JRE, and JVM.

**Q2.** Why should `balance` in the Account class not have a public setter method?

**Q3.** What is the purpose of the `this` keyword? Give an example from the banking app.

**Q4.** Explain the IS-A relationship with an example from our banking app.

**Q5.** Why do we make the Account class `abstract`?

**Q6.** What is the purpose of `@Override`? What happens if you misspell the method name without it?

**Q7.** Explain the difference between checked and unchecked exceptions. Give one example of each.

**Q8.** Why did we create custom exceptions like `InsufficientBalanceException` instead of using `IllegalArgumentException` everywhere?

**Q9.** Why do we store accounts in BOTH an ArrayList and a HashMap? What does each provide?

**Q10.** In our banking app, why is Stack the right data structure for undo (not Queue)?

---

## Section C: Code Reading & Debugging (25 marks)

*Read the code carefully and answer the questions. 5 marks each.*

---

### Q1. What is the output? Explain why.

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

### Q2. Find and fix the TWO bugs:

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

### Q3. What happens when each `withdraw()` is called? Explain the polymorphic behavior.

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

### Q5. Find the bug and fix it:

```java
public Account findAccount(long accNo) {
    Account acc = accountMap.get(accNo);
    acc.displayInfo();
    return acc;
}
```

---

## Section D: Coding Assignments (45 marks)

*Write complete, compilable Java code. You may use an IDE.*

---

### D1. Employee Management System (15 marks)

Build a small system that demonstrates OOP concepts:

1. Create an **abstract class** `Employee` with:
   - Private fields: `name` (String), `id` (int), `baseSalary` (double)
   - A parameterized constructor
   - Getters for all fields
   - An abstract method: `double calculatePay()`
   - A concrete method: `void displayInfo()` that prints name, id, and calculated pay

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

---

### D2. Inventory with Collections and Search (15 marks)

Build an inventory lookup system:

1. Create a `Product` class with: `id` (int), `name` (String), `price` (double), `quantity` (int). Include a constructor, getters, and a `display()` method.

2. In `main`:
   - Store at least 5 products in an `ArrayList<Product>`
   - Also store in a `HashMap<Integer, Product>` (key = product id)

3. Implement these search methods (print Big-O as a comment above each):
   - `findById(int id)` — use HashMap
   - `searchByName(String keyword)` — linear search, case-insensitive, partial match, return `List<Product>`
   - `findInPriceRange(double min, double max)` — linear search, return `List<Product>`

4. Implement binary search:
   - Sort products by price
   - Write `binarySearchByPrice(double targetPrice)` — return the Product with exact price match (or null)

---

### D3. Banking Operations with Stack and Queue (15 marks)

**Part 1 — Transaction Stack with Undo (7 marks):**
- Create a `Transaction` class with: `type` (String), `amount` (double), `accountId` (long), `balanceBefore` (double)
- Simulate: create an account with Rs.50000, perform 3 transactions (deposit, withdraw, deposit)
- Push each transaction onto a Stack
- Implement and demonstrate `undo()` — reverses the last transaction, restoring the balance
- Implement and demonstrate `undoAll()` — reverses all remaining transactions

**Part 2 — Service Request Queue (5 marks):**
- Create a `ServiceRequest` class with: `id` (auto-increment), `customerName` (String), `type` (String)
- Add 4+ requests to a Queue
- Process them one at a time (FIFO), printing each as it's processed
- Print queue size after each processing step

**Part 3 — Mini Statement (3 marks):**
- Store all transactions in a `List` for a specific account
- Display the last 5 in reverse order (most recent first)
- Use a Stack to reverse the display order

---

## Bonus Questions (optional, 10 extra marks)

*Attempt if you finish early.*

### Bonus 1 (5 marks): Transfer with Rollback

Write a `transfer(Account from, Account to, double amount)` method that:
- Withdraws from source, deposits to destination
- If deposit fails (e.g., destination is inactive), rolls back the withdrawal
- Records two transactions on the stack (one per account)

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
2. Is there a subtle bug? If so, what is it and how would you fix it?

---

*End of Assessment*
