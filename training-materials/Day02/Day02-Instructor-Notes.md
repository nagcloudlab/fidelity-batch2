# Day 2 — OOP Basics in Java + Inheritance & Polymorphism

## Instructor Guide

> **Duration**: Full day
> **Pre-requisite**: Day 1 BankApp (procedural version with methods)
> **Case Study**: Convert procedural BankApp → OOP, then add SavingsAccount / CurrentAccount via inheritance
> **Goal by end of day**: Trainees understand classes, objects, encapsulation, inheritance, polymorphism, and abstract classes — all through the banking domain

---

## Part A: OOP Basics (Topics 1–7)

---

### Topic 1: Why Object-Oriented Design

#### Key Points (5 min)

- **Start with the problem from Day 1**:
  - We had loose variables: `accountHolder`, `accountNumber`, `balance` — all floating in `main`
  - What if we need 100 accounts? 100 separate variables? What if we add a new field (e.g., `email`)?
  - What if two developers work on deposit logic — where does it live?
- **OOP solves this** by bundling **data + behavior** into a single unit (a class).
- **4 pillars** (just name them, we cover each today/later):
  - **Encapsulation** — hide data, expose through methods (today)
  - **Inheritance** — reuse common behavior (today)
  - **Polymorphism** — same action, different behavior (today)
  - **Abstraction** — hide complex details (today, intro)

#### Teaching Tip

> Write on the board:
> ```
> Day 1: Variables scattered in main → hard to manage
> Day 2: Variables + methods grouped in a CLASS → organized, reusable
> ```
> "Think of a class as a **form template**. An object is a **filled form**."

---

### Topic 2: Class and Object

#### Key Points (10 min)

- **Class** = blueprint/template. Defines **what** an account looks like and **what** it can do.
- **Object** = an actual instance created from that blueprint. A real account with real data.
- One class → many objects. Like one `Account` class → Ravi's account, Priya's account, etc.
- Class defines: fields (data) + methods (behavior)
- Object holds: actual values for those fields

#### Micro Example

```java
// Blueprint
class Account {
    String holderName;
    double balance;
}

// Creating objects (filled forms)
Account ravi = new Account();
ravi.holderName = "Ravi Kumar";
ravi.balance = 50000;

Account priya = new Account();
priya.holderName = "Priya Sharma";
priya.balance = 75000;
```

> **Talking point**: "Both Ravi and Priya are Account objects, but each has its own data. Changing Ravi's balance doesn't affect Priya's."

---

### Topic 3: Structure of a Java Class

#### Key Points (10 min)

- **Fields (attributes)** = the data a class holds. Describe the "state" of an object.
- **Methods (behavior)** = the actions a class can perform. Describe what an object "does".
- Fields are declared outside methods but inside the class.
- Methods operate on the fields.

#### Case Study Step 1 — First Account class

```java
public class Account {

    // Fields (attributes) — what an account HAS
    String holderName;
    long accountNumber;
    double balance;
    boolean isActive;

    // Methods (behavior) — what an account DOES
    void displayInfo() {
        System.out.println("Account #" + accountNumber);
        System.out.println("Holder  : " + holderName);
        System.out.println("Balance : Rs." + balance);
        System.out.println("Active  : " + isActive);
    }

    void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited Rs." + amount + " | New Balance: Rs." + balance);
    }

    void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return;
        }
        balance -= amount;
        System.out.println("Withdrawn Rs." + amount + " | New Balance: Rs." + balance);
    }
}
```

> **Key insight for trainees**: Notice `deposit` and `withdraw` are NOT static anymore. They operate on `this` object's balance. No need to pass balance as a parameter — the object owns its own balance.

---

### Topic 4: Creating Objects

#### Key Points (10 min)

- **`new` keyword** allocates memory and creates an instance.
- **Reference variable** holds the address (not the object itself).
- Multiple references can point to the same object.
- Default field values: `int` → 0, `double` → 0.0, `boolean` → false, `String` → null.

#### Case Study Step 2 — Using the Account class

```java
public class BankApp {
    public static void main(String[] args) {
        // Creating objects with 'new'
        Account ravi = new Account();
        ravi.holderName = "Ravi Kumar";
        ravi.accountNumber = 1001L;
        ravi.balance = 50000;
        ravi.isActive = true;

        Account priya = new Account();
        priya.holderName = "Priya Sharma";
        priya.accountNumber = 1002L;
        priya.balance = 75000;
        priya.isActive = true;

        // Each object has its own state
        ravi.displayInfo();
        System.out.println();
        priya.displayInfo();

        // Operations happen on the specific object
        ravi.deposit(10000);    // Ravi's balance: 60000
        priya.withdraw(5000);   // Priya's balance: 70000

        // Ravi's balance is unaffected by Priya's withdrawal
        ravi.displayInfo();
    }
}
```

#### Teaching Tip — Reference demo

```java
Account a = new Account();
a.holderName = "Ravi";
a.balance = 50000;

Account b = a;          // b points to the SAME object as a
b.balance = 99999;

System.out.println(a.balance);  // 99999 — both reference the same object!
```

> Draw this on the board with boxes and arrows. This is a critical concept.

---

### Topic 5: Encapsulation

#### Key Points (15 min)

- **Problem with current code**: Anyone can directly write `ravi.balance = -5000;` — no validation!
- **Encapsulation** = make fields `private`, provide `public` methods to access them.
- This gives you **control** — you can validate before setting, format before getting.
- **Data hiding** — the outside world doesn't know (or care) how data is stored internally.
- Think of it like an ATM: you interact through buttons (methods), not by opening the machine (fields).

#### Micro Example — The Problem

```java
// Without encapsulation — DANGEROUS
Account ravi = new Account();
ravi.balance = -50000;     // negative balance! No one stopped us.
ravi.holderName = "";      // empty name! No validation.
```

#### Case Study Step 3 — Encapsulated Account

```java
public class Account {

    // Private fields — not accessible from outside
    private String holderName;
    private long accountNumber;
    private double balance;
    private boolean isActive;

    // balance is now protected — can only change through deposit/withdraw
    // No setBalance() on purpose — balance changes only through transactions

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return;
        }
        balance += amount;
        System.out.println("Deposited Rs." + amount + " | New Balance: Rs." + balance);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return;
        }
        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return;
        }
        balance -= amount;
        System.out.println("Withdrawn Rs." + amount + " | New Balance: Rs." + balance);
    }

    public double getBalance() {
        return balance;
    }

    public void displayInfo() {
        System.out.println("Account #" + accountNumber + " | " + holderName + " | Rs." + balance);
    }
}
```

> **Talking point**: "Notice there's NO `setBalance()`. The only way to change balance is through `deposit()` and `withdraw()`, which validate the amount. This is encapsulation in action."

---

### Topic 6: Getter and Setter Methods

#### Key Points (10 min)

- **Getter** — returns the field value. Convention: `getFieldName()`
- **Setter** — sets the field value (with optional validation). Convention: `setFieldName(value)`
- For `boolean` fields, getter convention is `isFieldName()`
- Not every field needs both getter and setter. Design choice:
  - `balance` → getter only (changed through transactions)
  - `holderName` → getter + setter (can be updated)
  - `accountNumber` → getter only (never changes after creation)

#### Case Study Step 4 — Add getters/setters

```java
// Add to Account class:

// Getter only — account number never changes after creation
public long getAccountNumber() {
    return accountNumber;
}

// Getter and setter — name can be updated
public String getHolderName() {
    return holderName;
}

public void setHolderName(String holderName) {
    if (holderName == null || holderName.trim().isEmpty()) {
        System.out.println("Name cannot be empty.");
        return;
    }
    this.holderName = holderName;  // 'this' distinguishes field from parameter
}

// Boolean getter uses 'is' prefix
public boolean isActive() {
    return isActive;
}

public void setActive(boolean active) {
    this.isActive = active;
}
```

> **Explain `this` keyword**: When parameter name matches the field name, `this.holderName` refers to the field, `holderName` (without this) refers to the parameter.

---

### Topic 7: Constructor

#### Key Points (15 min)

- **Constructor** = special method that runs when an object is created with `new`.
- Same name as the class, **no return type** (not even void).
- Used to initialize fields at creation time — no more setting fields one by one.
- **Default constructor** — no parameters. Java provides one automatically if you write none.
- **Parameterized constructor** — takes values to initialize with.
- If you write ANY constructor, Java stops providing the default one. Write it explicitly if needed.

#### Case Study Step 5 — Constructors for Account

```java
public class Account {

    private String holderName;
    private long accountNumber;
    private double balance;
    private boolean isActive;

    // Default constructor
    public Account() {
        this.isActive = true;
        this.balance = 0;
    }

    // Parameterized constructor
    public Account(String holderName, long accountNumber, double initialBalance) {
        this.holderName = holderName;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.isActive = true;
    }

    // ... getters, setters, deposit, withdraw, displayInfo ...
}
```

#### Updated BankApp — Clean object creation

```java
public class BankApp {
    public static void main(String[] args) {
        // Before (Day 1 / Topic 4 style):
        // Account ravi = new Account();
        // ravi.holderName = "Ravi";     // was public — bad!
        // ravi.balance = 50000;         // was public — bad!

        // After (with constructor + encapsulation):
        Account ravi = new Account("Ravi Kumar", 1001L, 50000);
        Account priya = new Account("Priya Sharma", 1002L, 75000);

        ravi.displayInfo();
        priya.displayInfo();

        ravi.deposit(10000);
        priya.withdraw(5000);

        // ravi.balance = -5000;  // COMPILE ERROR — balance is private!
    }
}
```

> **Milestone check**: Have trainees create 3 Account objects using the parameterized constructor, perform operations, and display info. Everyone should have this working before Part B.

---

## Part B: Inheritance & Polymorphism (Topics 8–13)

> **Transition**: "Our Account class works. But in real banking, there are different types of accounts — Savings, Current, Fixed Deposit. They share common features (holder, balance, deposit) but each has unique rules. Do we copy-paste the Account class 3 times? No — we use inheritance."

---

### Topic 8: Why Inheritance in Object-Oriented Design

#### Key Points (5 min)

- **Real-world observation**: SavingsAccount and CurrentAccount both have holder name, account number, balance, deposit(), withdraw(). That's a lot of duplication.
- **Inheritance** = put common stuff in a **parent class**, let specialized classes **extend** it.
- **Benefits**:
  - Code reuse — write common logic once
  - Consistency — all accounts share the same core behavior
  - Easy maintenance — fix a bug in the parent, all children benefit

#### Teaching Tip — Draw on board

```
           Account (parent)
          /         \
  SavingsAccount   CurrentAccount
  - interestRate   - overdraftLimit
  - addInterest()  - overdraft logic
```

> "Everything above the line is shared. Everything below is specialized."

---

### Topic 9: Inheritance Basics

#### Key Points (10 min)

- **`extends`** keyword — `class SavingsAccount extends Account`
- **IS-A relationship** — A SavingsAccount IS-A Account (passes the "is a" test)
- Child class inherits all non-private fields and methods from parent.
- Child can **add** new fields/methods specific to it.
- Java supports **single inheritance** only (one parent per class).
- **`super`** keyword — refers to the parent class. Used to call parent's constructor or methods.

#### Case Study Step 6 — SavingsAccount

```java
public class SavingsAccount extends Account {

    private double interestRate;

    public SavingsAccount(String holderName, long accountNumber,
                          double initialBalance, double interestRate) {
        super(holderName, accountNumber, initialBalance);  // calls Account's constructor
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void addInterest() {
        double interest = getBalance() * interestRate / 100;
        deposit(interest);    // reuses parent's deposit method
        System.out.println("Interest Rs." + interest + " added at " + interestRate + "%");
    }
}
```

#### Case Study Step 7 — CurrentAccount

```java
public class CurrentAccount extends Account {

    private double overdraftLimit;

    public CurrentAccount(String holderName, long accountNumber,
                          double initialBalance, double overdraftLimit) {
        super(holderName, accountNumber, initialBalance);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }
}
```

> **Talking point**: "We didn't write `deposit()`, `getBalance()`, `displayInfo()` in either subclass — they're inherited from Account. We only added what's new."

---

### Topic 10: Method Overriding

#### Key Points (15 min)

- **Overriding** = child class provides its **own version** of a method already defined in the parent.
- **Rules**:
  - Same method name, same parameters, same return type (or covariant)
  - Access modifier can be same or wider (not narrower)
  - Use **`@Override`** annotation — it tells the compiler "I intend to override". Catches typos.
- **Overriding vs Overloading**:
  - Overriding: same signature, different class (parent-child)
  - Overloading: same name, different parameters, same class

#### Case Study Step 8 — Override withdraw in CurrentAccount

```java
public class CurrentAccount extends Account {

    private double overdraftLimit;

    public CurrentAccount(String holderName, long accountNumber,
                          double initialBalance, double overdraftLimit) {
        super(holderName, accountNumber, initialBalance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }
        // Current account allows overdraft up to the limit
        if (amount > getBalance() + overdraftLimit) {
            System.out.println("Exceeds overdraft limit. Max withdrawal: Rs."
                    + (getBalance() + overdraftLimit));
            return;
        }
        // Call parent's internal logic or handle directly
        double newBalance = getBalance() - amount;
        // We need a way to set balance — add protected setter or handle in parent
        // For now, let's use a different approach:
        System.out.println("Withdrawn Rs." + amount + " from Current Account");
        if (newBalance < 0) {
            System.out.println("WARNING: Account in overdraft. Balance: Rs." + newBalance);
        }
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }
}
```

> **Teaching tip**: This exposes a design issue — we need a way for the child to modify balance. Options:
> 1. Make `balance` `protected` (accessible by children)
> 2. Add a `protected void setBalance(double)` method
>
> This is a great teaching moment for access modifiers and design trade-offs. Use option 2 (protected setter):

#### Refine Account class — add protected setter for balance

```java
// Add to Account class:
protected void setBalance(double balance) {
    this.balance = balance;
}
```

#### Cleaner CurrentAccount override

```java
@Override
public void withdraw(double amount) {
    if (amount <= 0) {
        System.out.println("Invalid amount.");
        return;
    }
    if (amount > getBalance() + overdraftLimit) {
        System.out.println("Exceeds overdraft limit.");
        return;
    }
    setBalance(getBalance() - amount);
    System.out.println("Withdrawn Rs." + amount + " | Balance: Rs." + getBalance());
    if (getBalance() < 0) {
        System.out.println("WARNING: Account in overdraft by Rs." + Math.abs(getBalance()));
    }
}
```

#### Override withdraw in SavingsAccount (minimum balance rule)

```java
// Add to SavingsAccount:
private static final double MINIMUM_BALANCE = 1000.0;

@Override
public void withdraw(double amount) {
    if (amount <= 0) {
        System.out.println("Invalid amount.");
        return;
    }
    if (amount > getBalance() - MINIMUM_BALANCE) {
        System.out.println("Cannot withdraw. Must maintain minimum balance of Rs." + MINIMUM_BALANCE);
        System.out.println("Max withdrawal: Rs." + (getBalance() - MINIMUM_BALANCE));
        return;
    }
    setBalance(getBalance() - amount);
    System.out.println("Withdrawn Rs." + amount + " | Balance: Rs." + getBalance());
}
```

---

### Topic 11: Runtime Polymorphism

#### Key Points (15 min)

- **Parent reference, child object**: `Account acc = new SavingsAccount(...);`
- The reference type is `Account`, but the actual object is `SavingsAccount`.
- When you call `acc.withdraw()`, Java calls the **child's version** (overridden method) — decided at **runtime**.
- This is called **dynamic method dispatch**.
- **Why it matters**: You can write code that works with `Account` and it automatically handles any type of account.

#### Case Study Step 9 — Polymorphism in action

```java
public class BankApp {
    public static void main(String[] args) {
        // Parent reference, child objects
        Account acc1 = new SavingsAccount("Ravi Kumar", 1001L, 50000, 4.5);
        Account acc2 = new CurrentAccount("Priya Sharma", 1002L, 30000, 10000);

        // Same method call — different behavior
        acc1.withdraw(48000);   // SavingsAccount: blocked (min balance rule)
        acc2.withdraw(35000);   // CurrentAccount: allowed (overdraft)

        // Process multiple accounts uniformly
        Account[] accounts = { acc1, acc2 };
        for (Account acc : accounts) {
            acc.displayInfo();   // works for any Account type
        }
    }
}
```

#### Teaching Tip

> "This is the power of polymorphism — you write ONE loop that handles ALL account types. Tomorrow if we add FixedDepositAccount, this loop still works without any change."

#### Method to demonstrate polymorphism

```java
// This method works for ANY Account type — now or in the future
public static void printAccountSummary(Account account) {
    System.out.println("--- Account Summary ---");
    account.displayInfo();
    System.out.println("Balance: Rs." + account.getBalance());
}

// Call with any subtype:
printAccountSummary(acc1);   // SavingsAccount
printAccountSummary(acc2);   // CurrentAccount
```

---

### Topic 12: Abstract Class

#### Key Points (10 min)

- **Abstract class** = a class that **cannot be instantiated** directly. Only its children can be.
- Declared with `abstract` keyword.
- Can have:
  - **Concrete methods** — fully implemented (e.g., `deposit()`, `getBalance()`)
  - **Abstract methods** — declared but NOT implemented. Children MUST override them.
- **When to use**: When the parent class is too generic to create objects from.
  - You never create a plain "Account" — it's always a Savings or Current account.
  - But Account defines the common structure all types share.

#### Micro Example

```java
// Can't do: new Account() — it's abstract
// Must do: new SavingsAccount() or new CurrentAccount()

abstract class Shape {
    abstract double area();    // no body — children must implement

    void describe() {          // concrete — children inherit as-is
        System.out.println("I am a shape with area: " + area());
    }
}

class Circle extends Shape {
    double radius;

    Circle(double radius) { this.radius = radius; }

    @Override
    double area() { return Math.PI * radius * radius; }
}
```

---

### Topic 13: Designing Base Account Class

#### Key Points (10 min)

- Let's now make `Account` abstract — because in our banking system, every account must be a specific type.
- **Common properties** (in Account): holderName, accountNumber, balance, isActive
- **Common operations** (in Account): deposit(), getBalance(), displayInfo()
- **Abstract operation**: withdraw() — because each type has different rules

#### Case Study Step 10 — Final Account hierarchy

> This is the Day 2 final design. Walk through it end-to-end.

```
        Account (abstract)
        ├── holderName, accountNumber, balance
        ├── deposit()          ← concrete (same for all)
        ├── getBalance()       ← concrete
        ├── displayInfo()      ← concrete (can be overridden)
        └── withdraw()         ← abstract (MUST be overridden)
               │
       ┌───────┴────────┐
  SavingsAccount    CurrentAccount
  - interestRate    - overdraftLimit
  - addInterest()   - overdraft logic
  - withdraw()      - withdraw()
    (min balance)     (with overdraft)
```

---

## Case Study — Complete Day 2 Code

### Account.java (abstract base)

```java
public abstract class Account {

    private String holderName;
    private long accountNumber;
    private double balance;
    private boolean isActive;

    // Constructor
    public Account(String holderName, long accountNumber, double initialBalance) {
        this.holderName = holderName;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.isActive = true;
    }

    // Concrete method — same for all account types
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return;
        }
        this.balance += amount;
        System.out.println("Deposited Rs." + amount + " | New Balance: Rs." + this.balance);
    }

    // Abstract method — each account type MUST implement its own rules
    public abstract void withdraw(double amount);

    // Concrete display — children can override if needed
    public void displayInfo() {
        System.out.println("Account #" + accountNumber + " | " + holderName
                + " | Balance: Rs." + balance + " | Active: " + isActive);
    }

    // Getters
    public String getHolderName() { return holderName; }
    public long getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public boolean isActive() { return isActive; }

    // Setters
    public void setHolderName(String holderName) {
        if (holderName == null || holderName.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        this.holderName = holderName;
    }

    public void setActive(boolean active) { this.isActive = active; }

    // Protected — only children can use this to modify balance
    protected void setBalance(double balance) { this.balance = balance; }
}
```

### SavingsAccount.java

```java
public class SavingsAccount extends Account {

    private double interestRate;
    private static final double MINIMUM_BALANCE = 1000.0;

    public SavingsAccount(String holderName, long accountNumber,
                          double initialBalance, double interestRate) {
        super(holderName, accountNumber, initialBalance);
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }
        if (amount > getBalance() - MINIMUM_BALANCE) {
            System.out.println("Cannot withdraw. Must maintain minimum balance of Rs." + MINIMUM_BALANCE);
            System.out.println("Max withdrawal: Rs." + (getBalance() - MINIMUM_BALANCE));
            return;
        }
        setBalance(getBalance() - amount);
        System.out.println("Withdrawn Rs." + amount + " | Balance: Rs." + getBalance());
    }

    public void addInterest() {
        double interest = getBalance() * interestRate / 100;
        deposit(interest);
        System.out.println("Interest of Rs." + interest + " added at " + interestRate + "% rate");
    }

    public double getInterestRate() { return interestRate; }

    @Override
    public void displayInfo() {
        System.out.print("[SAVINGS] ");
        super.displayInfo();
        System.out.println("  Interest Rate: " + interestRate + "% | Min Balance: Rs." + MINIMUM_BALANCE);
    }
}
```

### CurrentAccount.java

```java
public class CurrentAccount extends Account {

    private double overdraftLimit;

    public CurrentAccount(String holderName, long accountNumber,
                          double initialBalance, double overdraftLimit) {
        super(holderName, accountNumber, initialBalance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }
        if (amount > getBalance() + overdraftLimit) {
            System.out.println("Exceeds overdraft limit.");
            System.out.println("Max withdrawal: Rs." + (getBalance() + overdraftLimit));
            return;
        }
        setBalance(getBalance() - amount);
        System.out.println("Withdrawn Rs." + amount + " | Balance: Rs." + getBalance());
        if (getBalance() < 0) {
            System.out.println("WARNING: Account in overdraft by Rs." + Math.abs(getBalance()));
        }
    }

    public double getOverdraftLimit() { return overdraftLimit; }

    @Override
    public void displayInfo() {
        System.out.print("[CURRENT] ");
        super.displayInfo();
        System.out.println("  Overdraft Limit: Rs." + overdraftLimit);
    }
}
```

### BankApp.java (Day 2 version)

```java
import java.util.Scanner;

public class BankApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Create accounts — polymorphic references
        Account savings = new SavingsAccount("Ravi Kumar", 1001L, 50000, 4.5);
        Account current = new CurrentAccount("Priya Sharma", 1002L, 30000, 10000);

        Account[] accounts = { savings, current };

        int choice;
        do {
            System.out.println("\n===== BANK APPLICATION =====");
            System.out.println("1. View All Accounts");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Add Interest (Savings only)");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n--- All Accounts ---");
                    for (Account acc : accounts) {
                        acc.displayInfo();
                        System.out.println();
                    }
                    break;

                case 2:
                    Account depositAcc = selectAccount(sc, accounts);
                    System.out.print("Enter deposit amount: ");
                    depositAcc.deposit(sc.nextDouble());
                    break;

                case 3:
                    Account withdrawAcc = selectAccount(sc, accounts);
                    System.out.print("Enter withdrawal amount: ");
                    withdrawAcc.withdraw(sc.nextDouble());   // polymorphic call!
                    break;

                case 4:
                    for (Account acc : accounts) {
                        if (acc instanceof SavingsAccount) {
                            ((SavingsAccount) acc).addInterest();
                        }
                    }
                    break;

                case 5:
                    System.out.println("Thank you for banking with us!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 5);

        sc.close();
    }

    public static Account selectAccount(Scanner sc, Account[] accounts) {
        System.out.println("Select account:");
        for (int i = 0; i < accounts.length; i++) {
            System.out.println((i + 1) + ". " + accounts[i].getHolderName()
                    + " (#" + accounts[i].getAccountNumber() + ")");
        }
        System.out.print("Enter choice: ");
        int index = sc.nextInt() - 1;
        return accounts[index];
    }
}
```

> **Key demo**: When case 3 calls `withdrawAcc.withdraw()`, the actual method that runs depends on the object type — SavingsAccount checks minimum balance, CurrentAccount allows overdraft. Same code, different behavior.

---

## Day 2 Exercises

### Exercise 1: Create a FixedDepositAccount
**Problem**: Create `FixedDepositAccount extends Account` with:
- A `maturityMonths` field
- `withdraw()` that blocks ALL withdrawals with message "Cannot withdraw before maturity"
- `displayInfo()` that shows maturity period
- Add it to the accounts array in BankApp

### Exercise 2: Encapsulation Drill
**Problem**: Given this broken class, fix the encapsulation issues:
```java
public class Employee {
    public String name;
    public double salary;
    public String password;
}
```
- Make fields private
- Add appropriate getters/setters
- `password` should have a setter but NO getter (write-only)
- `salary` setter should reject negative values

### Exercise 3: Constructor Chaining
**Problem**: Create a `PremiumSavingsAccount extends SavingsAccount` that:
- Has a `rewardPoints` field (starts at 0)
- Adds 1 reward point for every Rs.1000 deposited (override deposit)
- Has a method `redeemPoints(int points)` that converts points to balance (1 point = Rs.10)
- Uses constructor chaining (`super(...)`)

### Exercise 4: Polymorphism Challenge
**Problem**: Write a method `processMonthEnd(Account[] accounts)` that:
- For each account, prints the account info
- If it's a SavingsAccount, adds interest
- If it's a CurrentAccount, charges Rs.500 maintenance fee (use withdraw)
- Works correctly regardless of how many account types exist

### Exercise 5: Full Challenge — Mini Banking System
**Problem**: Extend the BankApp to support:
- Creating new accounts at runtime (user chooses Savings or Current, enters details)
- Store up to 10 accounts in an array
- Add a "Search Account" option (by account number)
- Add a "Transfer" option between two accounts

---

## Day 2 Quiz (10 questions)

1. What is the relationship between a class and an object?
2. What does `private` access modifier prevent?
3. Why is there no `setBalance()` in our public API, but `deposit()` and `withdraw()` exist?
4. What is the purpose of `this` keyword?
5. What happens if you define a parameterized constructor but NOT a default constructor?
6. What does `extends` mean?
7. What is the difference between method overriding and method overloading?
8. What does `@Override` annotation do?
9. If `Account acc = new SavingsAccount(...)`, which `withdraw()` runs — Account's or SavingsAccount's?
10. When should you make a class `abstract`?

---

## Day 2 Summary — What We Built

| Step | What Changed | Topics Covered |
|------|-------------|----------------|
| Step 1 | Created basic Account class with fields + methods | Class, fields, methods |
| Step 2 | Created objects with `new`, accessed fields directly | Objects, `new`, references |
| Step 3 | Made fields `private`, added validation in methods | Encapsulation, data hiding |
| Step 4 | Added getters/setters with `this` | Getters, setters, `this` |
| Step 5 | Added constructors for clean initialization | Default + parameterized constructors |
| Step 6–7 | Created SavingsAccount and CurrentAccount | Inheritance, `extends`, `super` |
| Step 8 | Overrode `withdraw()` with different rules per type | Method overriding, `@Override` |
| Step 9 | Used parent reference for child objects | Polymorphism, dynamic dispatch |
| Step 10 | Made Account abstract | Abstract class, abstract method |

### Day 1 vs Day 2 Comparison

| Aspect | Day 1 (Procedural) | Day 2 (OOP) |
|--------|-------------------|-------------|
| Data | Loose variables in main | Fields inside Account class |
| Behavior | Static methods | Instance methods |
| Multiple accounts | Would need duplicate variables | Create multiple objects |
| Account types | Not possible | Inheritance + polymorphism |
| Data protection | None | Encapsulation (private + getters/setters) |

> **Preview for Day 3**: "What happens if someone enters 'abc' when we expect a number? Or if the account array index is out of bounds? The program crashes. Tomorrow we'll learn how to handle these errors gracefully with Exception Handling."
