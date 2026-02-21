# Day 2 — OOP Basics in Java + Inheritance & Polymorphism

## Student Handout

> **What you'll build today**: Convert yesterday's procedural BankApp into a proper object-oriented system with Account types (Savings, Current), encapsulation, inheritance, and polymorphism.

---

## Part A: OOP Basics

---

### 1. Why Object-Oriented Design?

**The Problem (from Day 1):**

Yesterday, our account data was scattered as loose variables:

```java
String accountHolder = "Ravi Kumar";
long accountNumber = 1001L;
double balance = 50000;
```

What if we need 100 accounts? 100 separate variables for each field?
What if we add a new field like `email`? We'd have to change code everywhere.

**The OOP Solution:**

Bundle **data** (what an account has) and **behavior** (what an account does) into a single unit called a **class**.

```
Day 1:  Variables + Methods scattered in main     → hard to manage
Day 2:  Variables + Methods grouped in a CLASS     → organized, reusable
```

**4 Pillars of OOP** (we cover all today):

| Pillar | What it means | Banking example |
|--------|--------------|-----------------|
| Encapsulation | Hide data, control access | Balance can only change via deposit/withdraw |
| Inheritance | Reuse common behavior | SavingsAccount inherits from Account |
| Polymorphism | Same action, different behavior | withdraw() works differently for each type |
| Abstraction | Hide complex details | Account defines the "what", children define the "how" |

---

### 2. Class and Object

**Class** = Blueprint / Template (like a blank account opening form)
**Object** = Instance / Actual thing (like Ravi's filled account form)

```
┌────────────────────────┐
│  Class: Account        │ ← Blueprint (one)
│  - holderName          │
│  - accountNumber       │
│  - balance             │
│  - deposit()           │
│  - withdraw()          │
└────────────────────────┘
         │
    ┌────┴─────┐
    ▼          ▼
┌──────────┐ ┌──────────┐
│ Object 1 │ │ Object 2 │ ← Instances (many)
│ Ravi     │ │ Priya    │
│ #1001    │ │ #1002    │
│ Rs.50000 │ │ Rs.75000 │
└──────────┘ └──────────┘
```

One class → many objects. Each object has its own data.

---

### 3. Structure of a Java Class

A class has two parts:

- **Fields (attributes)** — the data. What an account **has**.
- **Methods (behavior)** — the actions. What an account **does**.

#### Banking App — Step 1: Basic Account class

```java
public class Account {

    // Fields — what an account HAS
    String holderName;
    long accountNumber;
    double balance;
    boolean isActive;

    // Methods — what an account DOES
    void displayInfo() {
        System.out.println("Account #" + accountNumber);
        System.out.println("Holder  : " + holderName);
        System.out.println("Balance : Rs." + balance);
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

**Key difference from Day 1**: `deposit()` and `withdraw()` are no longer `static`. They operate on **this specific object's** balance. No need to pass balance as a parameter.

---

### 4. Creating Objects

Use the **`new`** keyword to create an object from a class:

```java
public class BankApp {
    public static void main(String[] args) {
        // Create objects
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

        // Each object has its own data
        ravi.deposit(10000);     // Ravi's balance: 60000
        priya.withdraw(5000);    // Priya's balance: 70000

        // Ravi's balance is NOT affected by Priya's withdrawal
        ravi.displayInfo();      // Still shows 60000
    }
}
```

**Understanding references:**

```java
Account a = new Account();
a.holderName = "Ravi";
a.balance = 50000;

Account b = a;          // b points to the SAME object as a (not a copy!)
b.balance = 99999;

System.out.println(a.balance);  // 99999 — both a and b refer to the same object
```

```
  a ──────┐
          ▼
       ┌──────────┐
       │ Ravi     │  ← ONE object in memory
       │ Rs.99999 │
       └──────────┘
          ▲
  b ──────┘
```

---

### 5. Encapsulation

**The problem**: Right now, anyone can directly break our data:

```java
ravi.balance = -50000;     // negative balance — no one stopped us!
ravi.holderName = "";      // empty name — no validation!
```

**The fix**: Make fields **private** and provide **public methods** with validation.

```
BEFORE (no encapsulation):         AFTER (encapsulated):
┌──────────────────────┐           ┌──────────────────────┐
│  balance (public)    │           │  balance (private)   │
│  anyone can write    │    →      │  deposit() validates │
│  any value           │           │  withdraw() validates│
│  no rules            │           │  getBalance() reads  │
└──────────────────────┘           └──────────────────────┘
```

#### Banking App — Step 2: Encapsulated Account

```java
public class Account {

    private String holderName;       // private — can't access directly from outside
    private long accountNumber;
    private double balance;
    private boolean isActive;

    public void deposit(double amount) {
        if (amount <= 0) {                    // validation!
            System.out.println("Invalid deposit amount.");
            return;
        }
        balance += amount;
        System.out.println("Deposited Rs." + amount + " | New Balance: Rs." + balance);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount.");
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
        System.out.println("Account #" + accountNumber + " | " + holderName
                + " | Balance: Rs." + balance);
    }
}
```

Now this is impossible:
```java
ravi.balance = -50000;  // COMPILE ERROR — balance is private!
```

The **only** way to change balance is through `deposit()` and `withdraw()`, which enforce the rules.

---

### 6. Getter and Setter Methods

Since fields are private, we provide controlled access:

- **Getter** — reads the value: `getFieldName()`
- **Setter** — writes the value (with validation): `setFieldName(value)`

```java
// Getter — anyone can read the name
public String getHolderName() {
    return holderName;
}

// Setter — validates before setting
public void setHolderName(String holderName) {
    if (holderName == null || holderName.trim().isEmpty()) {
        System.out.println("Name cannot be empty.");
        return;
    }
    this.holderName = holderName;   // 'this' refers to the object's field
}

// Account number — getter only (never changes)
public long getAccountNumber() {
    return accountNumber;
}

// Boolean fields use 'is' prefix
public boolean isActive() {
    return isActive;
}
```

**The `this` keyword**: When a parameter has the same name as a field, `this.holderName` means "the object's field" and `holderName` (without this) means "the parameter".

**Design decision — not every field needs both:**

| Field | Getter | Setter | Why |
|-------|--------|--------|-----|
| holderName | Yes | Yes | Name can be updated |
| accountNumber | Yes | No | Never changes after creation |
| balance | Yes | No (public) | Only changes via deposit/withdraw |
| isActive | Yes | Yes | Account can be deactivated |

---

### 7. Constructor

A **constructor** is a special method that runs automatically when you create an object with `new`.

**Rules:**
- Same name as the class
- No return type (not even `void`)
- Called automatically with `new`

#### Banking App — Step 3: Constructors

```java
public class Account {

    private String holderName;
    private long accountNumber;
    private double balance;
    private boolean isActive;

    // Default constructor — no parameters
    public Account() {
        this.isActive = true;
        this.balance = 0;
    }

    // Parameterized constructor — set all fields at once
    public Account(String holderName, long accountNumber, double initialBalance) {
        this.holderName = holderName;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.isActive = true;
    }

    // ... methods ...
}
```

**Before (ugly):**
```java
Account ravi = new Account();
ravi.holderName = "Ravi";       // won't work now — it's private!
ravi.balance = 50000;
```

**After (clean):**
```java
Account ravi = new Account("Ravi Kumar", 1001L, 50000);   // one line!
Account priya = new Account("Priya Sharma", 1002L, 75000);
```

**Important**: If you write ANY constructor, Java stops providing the automatic default one. If you still need a no-arg constructor, write it explicitly.

---

## Part B: Inheritance & Polymorphism

---

### 8. Why Inheritance?

In real banking, there are different account types:

| | Savings Account | Current Account |
|---|---|---|
| holderName | Yes | Yes |
| accountNumber | Yes | Yes |
| balance | Yes | Yes |
| deposit() | Yes | Yes |
| withdraw() | Min balance rule | Overdraft allowed |
| interestRate | Yes | No |
| overdraftLimit | No | Yes |

Most features are **the same**. Only a few differ. Do we copy-paste everything? **No — we use inheritance.**

```
         Account (common stuff)
         ├── holderName
         ├── accountNumber
         ├── balance
         ├── deposit()
         └── withdraw()
              │
     ┌────────┴─────────┐
     ▼                  ▼
SavingsAccount     CurrentAccount
- interestRate     - overdraftLimit
- addInterest()    - overdraft in withdraw()
- min balance
  in withdraw()
```

**Write common code once in the parent. Add specialized code in children.**

---

### 9. Inheritance Basics

Use the **`extends`** keyword:

```java
public class SavingsAccount extends Account {
    // SavingsAccount IS-A Account
    // It inherits: holderName, accountNumber, balance, deposit(), withdraw(), displayInfo()
    // It adds: interestRate, addInterest()
}
```

**IS-A relationship**: "A SavingsAccount IS-A Account" — true.
**`super`** keyword: calls the parent's constructor or methods.

#### Banking App — Step 4: SavingsAccount

```java
public class SavingsAccount extends Account {

    private double interestRate;

    public SavingsAccount(String holderName, long accountNumber,
                          double initialBalance, double interestRate) {
        super(holderName, accountNumber, initialBalance);   // calls Account's constructor
        this.interestRate = interestRate;
    }

    public void addInterest() {
        double interest = getBalance() * interestRate / 100;
        deposit(interest);    // reuses Account's deposit method!
        System.out.println("Interest Rs." + interest + " added at " + interestRate + "%");
    }

    public double getInterestRate() {
        return interestRate;
    }
}
```

#### Banking App — Step 5: CurrentAccount

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

**We didn't write** `deposit()`, `getBalance()`, or `displayInfo()` in either subclass — they're inherited from `Account`.

---

### 10. Method Overriding

**Overriding** = a child class provides its own version of a method that already exists in the parent.

Why? Because `withdraw()` works differently for each account type:
- Savings: must maintain minimum balance
- Current: can go into overdraft

```java
// In Account (parent):
public void withdraw(double amount) {
    // basic withdrawal
}

// In SavingsAccount (child) — OVERRIDE with minimum balance rule:
@Override
public void withdraw(double amount) {
    // enforce Rs.1000 minimum balance
}

// In CurrentAccount (child) — OVERRIDE with overdraft:
@Override
public void withdraw(double amount) {
    // allow overdraft up to limit
}
```

**`@Override` annotation**: Tells the compiler "I'm intentionally overriding a parent method." If you misspell the method name, the compiler catches it.

**Rules for overriding:**
- Same method name and parameters as the parent
- Access modifier must be same or wider (not narrower)
- Always use `@Override` annotation

#### Banking App — Step 6: Override withdraw

**SavingsAccount** — minimum balance rule:

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

**CurrentAccount** — overdraft allowed:

```java
// Add to CurrentAccount:
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
```

**Overriding vs Overloading:**

| | Overriding | Overloading |
|---|---|---|
| Where | Parent + child class | Same class |
| Method name | Same | Same |
| Parameters | Same | Different |
| Decided at | Runtime | Compile time |
| Example | `withdraw()` in Account vs SavingsAccount | `deposit(amount)` vs `deposit(amount, remark)` |

---

### 11. Runtime Polymorphism

**Key idea**: A parent reference can hold a child object.

```java
Account acc = new SavingsAccount("Ravi", 1001L, 50000, 4.5);
//  ↑ reference type            ↑ actual object type
```

When you call `acc.withdraw()`, Java looks at the **actual object type** (SavingsAccount), not the reference type (Account). This is called **dynamic method dispatch**.

#### Banking App — Step 7: Polymorphism in action

```java
// Parent reference, child objects
Account acc1 = new SavingsAccount("Ravi Kumar", 1001L, 50000, 4.5);
Account acc2 = new CurrentAccount("Priya Sharma", 1002L, 30000, 10000);

// Same method call — DIFFERENT behavior
acc1.withdraw(48000);   // SavingsAccount rules: BLOCKED (minimum balance)
acc2.withdraw(35000);   // CurrentAccount rules: ALLOWED (overdraft kicks in)

// Process all accounts in a loop — no need to know the specific type
Account[] accounts = { acc1, acc2 };
for (Account acc : accounts) {
    acc.displayInfo();      // each account displays its own way
}
```

**Why this is powerful:**

```java
// This method works for ANY account type — now or in the future
public static void printSummary(Account account) {
    account.displayInfo();
    System.out.println("Balance: Rs." + account.getBalance());
}

printSummary(acc1);   // works with SavingsAccount
printSummary(acc2);   // works with CurrentAccount
// If we add FixedDepositAccount tomorrow — this still works!
```

---

### 12. Abstract Class

**Question**: Should we ever create a plain `Account` object?

```java
Account generic = new Account("???", 0, 0);   // What type is this? Makes no sense.
```

In our banking system, every account **must** be a specific type (Savings or Current). A plain `Account` is too generic.

**Abstract class** = a class that **cannot be instantiated**. It exists only to be extended.

```java
public abstract class Account {
    // ...

    // Abstract method — no body. Children MUST implement it.
    public abstract void withdraw(double amount);

    // Concrete method — fully implemented. Children inherit it.
    public void deposit(double amount) {
        // ... works the same for all
    }
}
```

```java
Account acc = new Account(...);            // COMPILE ERROR — Account is abstract
Account acc = new SavingsAccount(...);     // OK — SavingsAccount is concrete
```

**Abstract methods:**
- Declared with `abstract` keyword, no method body (no `{ }`)
- Every non-abstract child class **must** override them
- Forces children to provide their own implementation

---

### 13. Designing the Base Account Class

Our final design:

```
        Account (abstract)
        ├── Fields: holderName, accountNumber, balance, isActive
        ├── deposit()        ← concrete (same for all types)
        ├── getBalance()     ← concrete
        ├── displayInfo()    ← concrete (children can override)
        └── withdraw()       ← ABSTRACT (children MUST override)
               │
       ┌───────┴────────┐
  SavingsAccount    CurrentAccount
  + interestRate    + overdraftLimit
  + addInterest()
  + withdraw()      + withdraw()
    (min balance)     (with overdraft)
  + displayInfo()   + displayInfo()
```

---

## Complete Day 2 Code

### Account.java

```java
public abstract class Account {

    private String holderName;
    private long accountNumber;
    private double balance;
    private boolean isActive;

    public Account(String holderName, long accountNumber, double initialBalance) {
        this.holderName = holderName;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.isActive = true;
    }

    // Concrete — same for all types
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return;
        }
        this.balance += amount;
        System.out.println("Deposited Rs." + amount + " | New Balance: Rs." + this.balance);
    }

    // Abstract — each type must define its own rules
    public abstract void withdraw(double amount);

    // Concrete — children can override for custom display
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

    // Protected — children can modify balance through this
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
            System.out.println("Cannot withdraw. Must maintain min balance of Rs." + MINIMUM_BALANCE);
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

### BankApp.java

```java
import java.util.Scanner;

public class BankApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Polymorphic references — parent type, child objects
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
                        acc.displayInfo();     // polymorphic call
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
                    withdrawAcc.withdraw(sc.nextDouble());   // polymorphic!
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

**Test these scenarios:**
1. View all accounts — see different display formats for Savings vs Current
2. Withdraw Rs.48,000 from Savings (balance 50,000) — blocked by minimum balance rule
3. Withdraw Rs.35,000 from Current (balance 30,000) — allowed with overdraft warning
4. Add interest — only Savings accounts get interest

---

## Exercises

### Exercise 1: FixedDepositAccount

**Problem**: Create a `FixedDepositAccount extends Account` with:
- A `maturityMonths` field (e.g., 12 months)
- `withdraw()` that blocks ALL withdrawals: "Cannot withdraw before maturity"
- `displayInfo()` that also shows maturity period
- Add a FixedDeposit account to the `accounts` array in BankApp and test

<details>
<summary><strong>Solution</strong></summary>

```java
public class FixedDepositAccount extends Account {

    private int maturityMonths;

    public FixedDepositAccount(String holderName, long accountNumber,
                                double initialBalance, int maturityMonths) {
        super(holderName, accountNumber, initialBalance);
        this.maturityMonths = maturityMonths;
    }

    @Override
    public void withdraw(double amount) {
        System.out.println("Cannot withdraw from Fixed Deposit before maturity ("
                + maturityMonths + " months).");
    }

    @Override
    public void displayInfo() {
        System.out.print("[FIXED DEPOSIT] ");
        super.displayInfo();
        System.out.println("  Maturity: " + maturityMonths + " months");
    }

    public int getMaturityMonths() { return maturityMonths; }
}
```

In BankApp:
```java
Account fd = new FixedDepositAccount("Amit Verma", 1003L, 100000, 12);
Account[] accounts = { savings, current, fd };
```
</details>

---

### Exercise 2: Encapsulation Drill

**Problem**: Fix this class — apply proper encapsulation:

```java
public class Employee {
    public String name;
    public double salary;
    public String password;
}
```

Requirements:
- Make all fields private
- `name`: getter + setter (reject empty names)
- `salary`: getter + setter (reject negative values)
- `password`: setter only, NO getter (write-only for security)
- Add a parameterized constructor

<details>
<summary><strong>Solution</strong></summary>

```java
public class Employee {
    private String name;
    private double salary;
    private String password;

    public Employee(String name, double salary, String password) {
        this.name = name;
        this.salary = salary;
        this.password = password;
    }

    public String getName() { return name; }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        this.name = name;
    }

    public double getSalary() { return salary; }

    public void setSalary(double salary) {
        if (salary < 0) {
            System.out.println("Salary cannot be negative.");
            return;
        }
        this.salary = salary;
    }

    // No getter for password — write-only
    public void setPassword(String password) {
        if (password == null || password.length() < 6) {
            System.out.println("Password must be at least 6 characters.");
            return;
        }
        this.password = password;
    }
}
```
</details>

---

### Exercise 3: Constructor Chaining

**Problem**: Create `PremiumSavingsAccount extends SavingsAccount` that:
- Has a `rewardPoints` field (starts at 0)
- Overrides `deposit()` to add 1 reward point per Rs.1000 deposited (then calls parent's deposit)
- Has a method `redeemPoints(int points)` that converts points to balance (1 point = Rs.10)
- Properly chains constructors using `super(...)`

<details>
<summary><strong>Solution</strong></summary>

```java
public class PremiumSavingsAccount extends SavingsAccount {

    private int rewardPoints;

    public PremiumSavingsAccount(String holderName, long accountNumber,
                                  double initialBalance, double interestRate) {
        super(holderName, accountNumber, initialBalance, interestRate);
        this.rewardPoints = 0;
    }

    @Override
    public void deposit(double amount) {
        super.deposit(amount);   // do the actual deposit first
        if (amount > 0) {
            int pointsEarned = (int) (amount / 1000);
            rewardPoints += pointsEarned;
            System.out.println("Earned " + pointsEarned + " reward points. Total: " + rewardPoints);
        }
    }

    public void redeemPoints(int points) {
        if (points <= 0 || points > rewardPoints) {
            System.out.println("Invalid points. Available: " + rewardPoints);
            return;
        }
        double cashback = points * 10;
        deposit(cashback);
        rewardPoints -= points;
        System.out.println("Redeemed " + points + " points for Rs." + cashback);
    }

    public int getRewardPoints() { return rewardPoints; }

    @Override
    public void displayInfo() {
        System.out.print("[PREMIUM] ");
        super.displayInfo();
        System.out.println("  Reward Points: " + rewardPoints);
    }
}
```
</details>

---

### Exercise 4: Polymorphism — Month-End Processing

**Problem**: Write a static method `processMonthEnd(Account[] accounts)` that:
- Loops through all accounts
- Prints each account's info
- If it's a SavingsAccount → adds interest
- If it's a CurrentAccount → charges Rs.500 maintenance fee (use `withdraw`)
- Test with mixed account types

<details>
<summary><strong>Solution</strong></summary>

```java
public static void processMonthEnd(Account[] accounts) {
    System.out.println("====== MONTH-END PROCESSING ======");
    for (Account acc : accounts) {
        System.out.println("\nProcessing: " + acc.getHolderName());
        acc.displayInfo();

        if (acc instanceof SavingsAccount) {
            ((SavingsAccount) acc).addInterest();
        } else if (acc instanceof CurrentAccount) {
            System.out.println("Charging maintenance fee...");
            acc.withdraw(500);
        }
    }
    System.out.println("====== PROCESSING COMPLETE ======");
}
```
</details>

---

### Exercise 5: Full Challenge — Dynamic Account Creation

**Problem**: Extend BankApp with a "Create Account" option that:
- Asks user: Savings (S) or Current (C)?
- Takes holder name, account number, initial balance
- For Savings: asks interest rate
- For Current: asks overdraft limit
- Adds the new account to the array (use a larger array or counter)
- All existing features still work with the new account

<details>
<summary><strong>Solution</strong></summary>

```java
// Replace the fixed array with a dynamic approach:
public class BankApp {

    static Account[] accounts = new Account[10];   // max 10 accounts
    static int accountCount = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Pre-load sample accounts
        accounts[0] = new SavingsAccount("Ravi Kumar", 1001L, 50000, 4.5);
        accounts[1] = new CurrentAccount("Priya Sharma", 1002L, 30000, 10000);
        accountCount = 2;

        int choice;
        do {
            System.out.println("\n===== BANK APPLICATION =====");
            System.out.println("1. View All Accounts");
            System.out.println("2. Create Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    for (int i = 0; i < accountCount; i++) {
                        accounts[i].displayInfo();
                        System.out.println();
                    }
                    break;
                case 2:
                    createAccount(sc);
                    break;
                case 3:
                    Account dAcc = selectAccount(sc);
                    System.out.print("Amount: ");
                    dAcc.deposit(sc.nextDouble());
                    break;
                case 4:
                    Account wAcc = selectAccount(sc);
                    System.out.print("Amount: ");
                    wAcc.withdraw(sc.nextDouble());
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid.");
            }
        } while (choice != 5);

        sc.close();
    }

    public static void createAccount(Scanner sc) {
        if (accountCount >= accounts.length) {
            System.out.println("Maximum accounts reached.");
            return;
        }
        sc.nextLine();   // consume newline
        System.out.print("Account type (S=Savings, C=Current): ");
        String type = sc.nextLine().trim().toUpperCase();

        System.out.print("Holder name: ");
        String name = sc.nextLine();
        System.out.print("Account number: ");
        long accNo = sc.nextLong();
        System.out.print("Initial balance: ");
        double balance = sc.nextDouble();

        if (type.equals("S")) {
            System.out.print("Interest rate (%): ");
            double rate = sc.nextDouble();
            accounts[accountCount] = new SavingsAccount(name, accNo, balance, rate);
        } else if (type.equals("C")) {
            System.out.print("Overdraft limit: ");
            double limit = sc.nextDouble();
            accounts[accountCount] = new CurrentAccount(name, accNo, balance, limit);
        } else {
            System.out.println("Invalid type.");
            return;
        }
        accountCount++;
        System.out.println("Account created successfully!");
    }

    public static Account selectAccount(Scanner sc) {
        for (int i = 0; i < accountCount; i++) {
            System.out.println((i + 1) + ". " + accounts[i].getHolderName()
                    + " (#" + accounts[i].getAccountNumber() + ")");
        }
        System.out.print("Select: ");
        return accounts[sc.nextInt() - 1];
    }
}
```
</details>

---

## Quick Quiz

1. What is the relationship between a class and an object?
2. What does `private` prevent?
3. Why do we have `deposit()` and `withdraw()` but no public `setBalance()`?
4. What is the purpose of the `this` keyword?
5. What happens if you write a parameterized constructor but not a default constructor, then try `new Account()`?
6. What does `extends` mean?
7. What is the difference between overriding and overloading?
8. What does the `@Override` annotation do?
9. If `Account acc = new SavingsAccount(...)`, which `withdraw()` runs?
10. When should you make a class abstract?

<details>
<summary><strong>Answers</strong></summary>

1. A **class** is a blueprint; an **object** is an instance of that blueprint with actual data.
2. `private` prevents access from outside the class. Only methods within the same class can access private fields.
3. Because balance should only change through validated business operations (deposit/withdraw), not arbitrary assignment. This is encapsulation.
4. `this` refers to the current object. Used to distinguish between a field and a parameter with the same name (e.g., `this.name = name`).
5. **Compile error** — once you write any constructor, Java no longer provides the default. You must write `public Account() {}` explicitly if you need it.
6. `extends` creates an inheritance relationship. The child class inherits fields and methods from the parent class.
7. **Overriding**: same method signature, parent-child classes, decided at runtime. **Overloading**: same name but different parameters, same class, decided at compile time.
8. It tells the compiler you intend to override a parent method. If the method doesn't exist in the parent (e.g., typo), you get a compile error instead of accidentally creating a new method.
9. **SavingsAccount's** `withdraw()` — Java uses the actual object type at runtime (dynamic method dispatch), not the reference type.
10. When the class is too generic to create objects from directly, and it exists only to provide common structure for its children. Example: You never create a plain "Account" — it's always a specific type.

</details>

---

## What We Built Today

| Step | What Changed | Topics |
|------|-------------|--------|
| Step 1 | Basic Account class with fields + methods | Class, fields, methods |
| Step 2 | Created objects with `new` | Objects, references |
| Step 3 | Made fields private, added getters/setters/constructors | Encapsulation, constructors |
| Step 4 | Created SavingsAccount extending Account | Inheritance, `extends`, `super` |
| Step 5 | Created CurrentAccount extending Account | Inheritance |
| Step 6 | Overrode withdraw() with different rules per type | Method overriding, `@Override` |
| Step 7 | Used parent references for child objects | Polymorphism |
| Final | Made Account abstract | Abstract class |

### Day 1 vs Day 2

| | Day 1 (Procedural) | Day 2 (OOP) |
|---|---|---|
| Data | Loose variables | Fields in Account class |
| Functions | Static methods in main | Instance methods on objects |
| Multiple accounts | Duplicate variables | Create multiple objects |
| Account types | Not possible | Inheritance + polymorphism |
| Data protection | None | Private fields + getters/setters |
| Code reuse | Copy-paste | Inherit from parent |

## What's Next (Day 3 Preview)

What happens if someone types "abc" when we ask for a number? Or picks account #99 when only 2 exist? Right now — the program **crashes**.

Tomorrow we'll learn **Exception Handling** — how to catch errors gracefully, create custom exceptions like `InsufficientBalanceException`, and keep our banking app running no matter what users throw at it.
