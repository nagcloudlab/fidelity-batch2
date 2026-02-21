# Day 3 — Exception Handling + Assessment

## Instructor Guide

> **Duration**: Exception Handling (~1 hr) + Programming Fundamentals & OOPs Assessment (remaining time)
> **Pre-requisite**: Day 2 BankApp (Account hierarchy with SavingsAccount, CurrentAccount)
> **Case Study**: Add robust error handling — custom exceptions, try-catch in BankApp
> **Goal by end of session**: Trainees understand exception hierarchy, can write try-catch-finally, create custom exceptions, and apply them to real business rules

---

## Exception Handling (Topics 1–5)

---

### Topic 1: Exception Hierarchy (Intro)

#### Key Points (10 min)

- **Exception** = an unexpected event that disrupts normal program flow.
- **Start with a live crash** — run the Day 2 BankApp, type "abc" when asked for a number. Show the crash.

```
Enter choice: abc
Exception in thread "main" java.util.InputMismatchException
    at java.util.Scanner.nextInt(...)
    at BankApp.main(BankApp.java:25)
```

- "The program died. In a real banking app, this is unacceptable."
- **Java's exception hierarchy**:

```
            Throwable
           /         \
       Error        Exception
    (don't catch)   /         \
                 Checked    Unchecked (RuntimeException)
              (must handle)  (optional to handle)
```

- **Error** — serious JVM problems (OutOfMemoryError). Don't catch these.
- **Checked exceptions** — compiler forces you to handle (IOException, SQLException). Must use try-catch or declare with `throws`.
- **Unchecked exceptions** (RuntimeException) — programming bugs (NullPointerException, ArrayIndexOutOfBoundsException, InputMismatchException). Compiler doesn't force handling.
- **Common exceptions trainees will see**:

| Exception | When it happens |
|-----------|----------------|
| `NullPointerException` | Calling a method on `null` |
| `ArrayIndexOutOfBoundsException` | Invalid array index |
| `InputMismatchException` | Scanner gets wrong input type |
| `NumberFormatException` | `Integer.parseInt("abc")` |
| `ArithmeticException` | Division by zero |

#### Teaching Tip

> Trigger each of these live in a small program. Let trainees see the stack trace and learn to read it bottom-to-top.

---

### Topic 2: try – catch – finally

#### Key Points (15 min)

- **try** — wrap the risky code
- **catch** — what to do if that specific exception occurs
- **finally** — runs ALWAYS, whether exception happened or not (cleanup code)

```
try {
    // risky code
} catch (ExceptionType e) {
    // handle the error
} finally {
    // always runs — cleanup
}
```

- **Flow**:
  - No exception → try completes → finally runs → continues after
  - Exception occurs → jumps to matching catch → finally runs → continues after
  - No matching catch → finally runs → exception propagates up (crash)

#### Micro Example

```java
Scanner sc = new Scanner(System.in);
try {
    System.out.print("Enter amount: ");
    double amount = sc.nextDouble();
    System.out.println("You entered: " + amount);
} catch (InputMismatchException e) {
    System.out.println("Invalid input. Please enter a number.");
    sc.nextLine();   // clear the bad input from scanner
} finally {
    System.out.println("This always runs.");
}
System.out.println("Program continues normally.");
```

#### Case Study Step 1 — Protect the menu input

```java
// In BankApp.main(), wrap the menu choice:
int choice = 0;
do {
    displayMenu();
    try {
        choice = sc.nextInt();
    } catch (InputMismatchException e) {
        System.out.println("Please enter a number.");
        sc.nextLine();   // clear invalid input
        continue;        // restart the loop
    }

    switch (choice) {
        // ... cases ...
    }
} while (choice != 5);
```

> **Teaching point**: "Without try-catch, typing 'abc' crashes the app. With it, we show a friendly message and let the user retry. The program never crashes."

#### finally use case

```java
// finally is typically used for resource cleanup
Scanner fileSc = null;
try {
    fileSc = new Scanner(new File("accounts.txt"));
    // process file
} catch (FileNotFoundException e) {
    System.out.println("File not found.");
} finally {
    if (fileSc != null) {
        fileSc.close();    // always close the resource
    }
}
```

> "We'll see a cleaner way (try-with-resources) on Day 7 when we cover file handling."

---

### Topic 3: Multiple Catch Blocks

#### Key Points (5 min)

- One `try` can have **multiple `catch` blocks** for different exception types.
- Java checks them **top to bottom** — first match wins.
- **Order matters**: put specific exceptions before general ones.
- Catching `Exception` (the parent) catches everything — use as a safety net at the end.

#### Micro Example

```java
try {
    Account acc = accounts[index];
    acc.withdraw(amount);
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Invalid account selection.");
} catch (NullPointerException e) {
    System.out.println("Account not initialized.");
} catch (Exception e) {
    System.out.println("Unexpected error: " + e.getMessage());
}
```

#### Common Mistake

```java
// WRONG ORDER — compiler error!
try {
    // ...
} catch (Exception e) {           // catches everything — blocks below are unreachable
    System.out.println("Error");
} catch (NullPointerException e) { // COMPILE ERROR — already caught above
    System.out.println("Null");
}
```

> Always: specific → general (child → parent).

#### Case Study Step 2 — Multiple catches in selectAccount

```java
public static Account selectAccount(Scanner sc, Account[] accounts, int accountCount) {
    System.out.println("Select account:");
    for (int i = 0; i < accountCount; i++) {
        System.out.println((i + 1) + ". " + accounts[i].getHolderName()
                + " (#" + accounts[i].getAccountNumber() + ")");
    }
    System.out.print("Enter choice: ");

    try {
        int index = sc.nextInt() - 1;
        if (index < 0 || index >= accountCount) {
            System.out.println("Invalid selection.");
            return null;
        }
        return accounts[index];
    } catch (InputMismatchException e) {
        System.out.println("Please enter a number.");
        sc.nextLine();
        return null;
    }
}
```

---

### Topic 4: throw Keyword

#### Key Points (10 min)

- **`throw`** — you manually trigger an exception. You decide when something is "exceptional."
- Syntax: `throw new ExceptionType("message");`
- Used for **business rule violations** — the program is working fine technically, but a business rule is broken.
- Difference:
  - `throw` — used in method body to raise an exception
  - `throws` — used in method signature to declare that a method might throw a checked exception

#### Micro Example

```java
public void withdraw(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("Amount must be positive. Got: " + amount);
    }
    if (amount > balance) {
        throw new IllegalArgumentException("Insufficient balance. Available: " + balance);
    }
    balance -= amount;
}
```

#### Case Study Step 3 — throw in Account methods

```java
// In Account.java — deposit:
public void deposit(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("Deposit amount must be positive. Got: " + amount);
    }
    this.balance += amount;
    System.out.println("Deposited Rs." + amount + " | New Balance: Rs." + this.balance);
}

// In SavingsAccount.java — withdraw:
@Override
public void withdraw(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("Amount must be positive.");
    }
    if (amount > getBalance() - MINIMUM_BALANCE) {
        throw new IllegalArgumentException(
            "Insufficient balance. Must maintain Rs." + MINIMUM_BALANCE
            + ". Max withdrawal: Rs." + (getBalance() - MINIMUM_BALANCE));
    }
    setBalance(getBalance() - amount);
    System.out.println("Withdrawn Rs." + amount + " | Balance: Rs." + getBalance());
}
```

> **Teaching point**: "Now our Account classes THROW exceptions when rules are broken. The caller (BankApp) CATCHES them. Clean separation — Account defines the rules, BankApp handles the user experience."

#### Updated BankApp catch:

```java
case 3:
    Account withdrawAcc = selectAccount(sc, accounts, accountCount);
    if (withdrawAcc == null) break;
    System.out.print("Enter withdrawal amount: ");
    try {
        double amount = sc.nextDouble();
        withdrawAcc.withdraw(amount);
    } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
    } catch (InputMismatchException e) {
        System.out.println("Invalid input.");
        sc.nextLine();
    }
    break;
```

---

### Topic 5: Creating Custom Exception Class (Intro)

#### Key Points (15 min)

- Built-in exceptions like `IllegalArgumentException` are generic. The name doesn't tell you the business context.
- **Custom exceptions** make errors self-documenting:
  - `InsufficientBalanceException` — immediately clear what went wrong
  - `InvalidAmountException` — no ambiguity
  - `AccountInactiveException` — domain-specific
- **How to create**: Extend `Exception` (checked) or `RuntimeException` (unchecked).
- **For business rules, use unchecked** (extend RuntimeException) — keeps code clean, no forced try-catch everywhere.
- Minimum needed: a constructor that calls `super(message)`.

#### Case Study Step 4 — Custom exceptions for the Banking App

**InsufficientBalanceException.java**:
```java
public class InsufficientBalanceException extends RuntimeException {

    private double currentBalance;
    private double withdrawalAmount;

    public InsufficientBalanceException(String message, double currentBalance, double withdrawalAmount) {
        super(message);
        this.currentBalance = currentBalance;
        this.withdrawalAmount = withdrawalAmount;
    }

    public double getCurrentBalance() { return currentBalance; }
    public double getWithdrawalAmount() { return withdrawalAmount; }
}
```

**InvalidAmountException.java**:
```java
public class InvalidAmountException extends RuntimeException {

    private double amount;

    public InvalidAmountException(String message, double amount) {
        super(message);
        this.amount = amount;
    }

    public double getAmount() { return amount; }
}
```

**AccountInactiveException.java**:
```java
public class AccountInactiveException extends RuntimeException {

    private long accountNumber;

    public AccountInactiveException(long accountNumber) {
        super("Account #" + accountNumber + " is inactive. Operation denied.");
        this.accountNumber = accountNumber;
    }

    public long getAccountNumber() { return accountNumber; }
}
```

#### Updated Account methods — using custom exceptions

```java
// In Account.java:
public void deposit(double amount) {
    if (!isActive()) {
        throw new AccountInactiveException(getAccountNumber());
    }
    if (amount <= 0) {
        throw new InvalidAmountException("Deposit amount must be positive.", amount);
    }
    this.balance += amount;
    System.out.println("Deposited Rs." + amount + " | New Balance: Rs." + this.balance);
}

// In SavingsAccount.java:
@Override
public void withdraw(double amount) {
    if (!isActive()) {
        throw new AccountInactiveException(getAccountNumber());
    }
    if (amount <= 0) {
        throw new InvalidAmountException("Withdrawal amount must be positive.", amount);
    }
    if (amount > getBalance() - MINIMUM_BALANCE) {
        throw new InsufficientBalanceException(
            "Must maintain minimum balance of Rs." + MINIMUM_BALANCE,
            getBalance(), amount);
    }
    setBalance(getBalance() - amount);
    System.out.println("Withdrawn Rs." + amount + " | Balance: Rs." + getBalance());
}

// In CurrentAccount.java:
@Override
public void withdraw(double amount) {
    if (!isActive()) {
        throw new AccountInactiveException(getAccountNumber());
    }
    if (amount <= 0) {
        throw new InvalidAmountException("Withdrawal amount must be positive.", amount);
    }
    if (amount > getBalance() + overdraftLimit) {
        throw new InsufficientBalanceException(
            "Exceeds overdraft limit of Rs." + overdraftLimit,
            getBalance(), amount);
    }
    setBalance(getBalance() - amount);
    System.out.println("Withdrawn Rs." + amount + " | Balance: Rs." + getBalance());
    if (getBalance() < 0) {
        System.out.println("WARNING: Account in overdraft by Rs." + Math.abs(getBalance()));
    }
}
```

#### Updated BankApp — catching custom exceptions

```java
case 3:
    Account withdrawAcc = selectAccount(sc, accounts, accountCount);
    if (withdrawAcc == null) break;
    System.out.print("Enter withdrawal amount: ");
    try {
        double amount = sc.nextDouble();
        withdrawAcc.withdraw(amount);
    } catch (InsufficientBalanceException e) {
        System.out.println("DECLINED: " + e.getMessage());
        System.out.println("  Your balance: Rs." + e.getCurrentBalance());
        System.out.println("  Requested:    Rs." + e.getWithdrawalAmount());
    } catch (InvalidAmountException e) {
        System.out.println("INVALID: " + e.getMessage());
    } catch (AccountInactiveException e) {
        System.out.println("BLOCKED: " + e.getMessage());
    } catch (InputMismatchException e) {
        System.out.println("Please enter a valid number.");
        sc.nextLine();
    }
    break;
```

> **Teaching moment**: "Look how readable this is. Each catch block handles a specific business scenario with a meaningful error message. Compare this to catching a generic `Exception` and showing a vague 'Something went wrong' message."

---

## Case Study — Complete Day 3 Code

### Exception Classes

**InsufficientBalanceException.java**
```java
public class InsufficientBalanceException extends RuntimeException {
    private double currentBalance;
    private double withdrawalAmount;

    public InsufficientBalanceException(String message, double currentBalance, double withdrawalAmount) {
        super(message);
        this.currentBalance = currentBalance;
        this.withdrawalAmount = withdrawalAmount;
    }

    public double getCurrentBalance() { return currentBalance; }
    public double getWithdrawalAmount() { return withdrawalAmount; }
}
```

**InvalidAmountException.java**
```java
public class InvalidAmountException extends RuntimeException {
    private double amount;

    public InvalidAmountException(String message, double amount) {
        super(message);
        this.amount = amount;
    }

    public double getAmount() { return amount; }
}
```

**AccountInactiveException.java**
```java
public class AccountInactiveException extends RuntimeException {
    private long accountNumber;

    public AccountInactiveException(long accountNumber) {
        super("Account #" + accountNumber + " is inactive. Operation denied.");
        this.accountNumber = accountNumber;
    }

    public long getAccountNumber() { return accountNumber; }
}
```

### Updated Account.java (abstract base)

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

    public void deposit(double amount) {
        if (!isActive) {
            throw new AccountInactiveException(accountNumber);
        }
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive.", amount);
        }
        this.balance += amount;
        System.out.println("Deposited Rs." + amount + " | New Balance: Rs." + this.balance);
    }

    public abstract void withdraw(double amount);

    public void displayInfo() {
        String status = isActive ? "Active" : "INACTIVE";
        System.out.println("Account #" + accountNumber + " | " + holderName
                + " | Balance: Rs." + balance + " | " + status);
    }

    // Getters
    public String getHolderName() { return holderName; }
    public long getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public boolean isActive() { return isActive; }

    // Setters
    public void setHolderName(String holderName) {
        if (holderName == null || holderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.holderName = holderName;
    }

    public void setActive(boolean active) { this.isActive = active; }
    protected void setBalance(double balance) { this.balance = balance; }
}
```

### Updated SavingsAccount.java

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
        if (!isActive()) {
            throw new AccountInactiveException(getAccountNumber());
        }
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive.", amount);
        }
        if (amount > getBalance() - MINIMUM_BALANCE) {
            throw new InsufficientBalanceException(
                    "Must maintain minimum balance of Rs." + MINIMUM_BALANCE,
                    getBalance(), amount);
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

### Updated CurrentAccount.java

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
        if (!isActive()) {
            throw new AccountInactiveException(getAccountNumber());
        }
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive.", amount);
        }
        if (amount > getBalance() + overdraftLimit) {
            throw new InsufficientBalanceException(
                    "Exceeds overdraft limit of Rs." + overdraftLimit,
                    getBalance(), amount);
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

### Updated BankApp.java (with full exception handling)

```java
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankApp {

    static Account[] accounts = new Account[10];
    static int accountCount = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Pre-load accounts
        accounts[0] = new SavingsAccount("Ravi Kumar", 1001L, 50000, 4.5);
        accounts[1] = new CurrentAccount("Priya Sharma", 1002L, 30000, 10000);
        accountCount = 2;

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
                case 1:
                    viewAllAccounts();
                    break;

                case 2:
                    handleDeposit(sc);
                    break;

                case 3:
                    handleWithdrawal(sc);
                    break;

                case 4:
                    handleAddInterest();
                    break;

                case 5:
                    handleDeactivateAccount(sc);
                    break;

                case 6:
                    System.out.println("Thank you for banking with us!");
                    break;

                default:
                    System.out.println("Invalid choice. Please select 1-6.");
            }
        } while (choice != 6);

        sc.close();
    }

    static void displayMenu() {
        System.out.println("\n===== BANK APPLICATION =====");
        System.out.println("1. View All Accounts");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Add Interest (Savings)");
        System.out.println("5. Deactivate Account");
        System.out.println("6. Exit");
        System.out.print("Enter choice: ");
    }

    static void viewAllAccounts() {
        System.out.println("\n--- All Accounts ---");
        for (int i = 0; i < accountCount; i++) {
            accounts[i].displayInfo();
            System.out.println();
        }
    }

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
            System.out.println("Please enter a valid number.");
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
            System.out.println("  Your balance: Rs." + e.getCurrentBalance());
            System.out.println("  Requested:    Rs." + e.getWithdrawalAmount());
        } catch (InvalidAmountException e) {
            System.out.println("INVALID: " + e.getMessage());
        } catch (AccountInactiveException e) {
            System.out.println("BLOCKED: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid number.");
            sc.nextLine();
        }
    }

    static void handleAddInterest() {
        boolean found = false;
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i] instanceof SavingsAccount) {
                try {
                    ((SavingsAccount) accounts[i]).addInterest();
                    found = true;
                } catch (AccountInactiveException e) {
                    System.out.println("Skipped: " + e.getMessage());
                }
            }
        }
        if (!found) {
            System.out.println("No savings accounts found.");
        }
    }

    static void handleDeactivateAccount(Scanner sc) {
        Account acc = selectAccount(sc);
        if (acc == null) return;
        acc.setActive(false);
        System.out.println("Account #" + acc.getAccountNumber() + " has been deactivated.");
    }

    static Account selectAccount(Scanner sc) {
        System.out.println("Select account:");
        for (int i = 0; i < accountCount; i++) {
            System.out.println("  " + (i + 1) + ". " + accounts[i].getHolderName()
                    + " (#" + accounts[i].getAccountNumber() + ")");
        }
        System.out.print("Enter choice: ");
        try {
            int index = sc.nextInt() - 1;
            if (index < 0 || index >= accountCount) {
                System.out.println("Invalid selection.");
                return null;
            }
            return accounts[index];
        } catch (InputMismatchException e) {
            System.out.println("Please enter a number.");
            sc.nextLine();
            return null;
        }
    }
}
```

> **Demo scenario for the class**: Walk through these cases:
> 1. Type "abc" at menu → graceful recovery
> 2. Deposit Rs.-500 → InvalidAmountException caught
> 3. Withdraw Rs.48000 from Savings (50000 balance) → InsufficientBalanceException with details
> 4. Deactivate an account, then try to deposit → AccountInactiveException
> 5. Withdraw Rs.35000 from Current (30000 balance, 10000 overdraft) → succeeds with warning

---

## Day 3 Exercises

### Exercise 1: Reading Stack Traces
**Problem**: Given this stack trace, answer the questions:
```
Exception in thread "main" java.lang.NullPointerException: Cannot invoke "Account.deposit(double)"
    at BankApp.handleDeposit(BankApp.java:58)
    at BankApp.main(BankApp.java:30)
```
1. What type of exception occurred?
2. Which method caused it?
3. What likely went wrong?
4. How would you fix it?

### Exercise 2: Add a Transfer Feature with Exception Handling
**Problem**: Add a `transfer(Account from, Account to, double amount)` method that:
- Validates both accounts are active (throw AccountInactiveException)
- Validates amount is positive (throw InvalidAmountException)
- Withdraws from source (may throw InsufficientBalanceException)
- Deposits to target
- If deposit fails after withdrawal, roll back the withdrawal
- Add menu option in BankApp with appropriate try-catch

### Exercise 3: DailyWithdrawalLimitException
**Problem**: Create a custom exception `DailyWithdrawalLimitException` that:
- Stores the daily limit, amount attempted, and total withdrawn today
- Modify SavingsAccount to track daily withdrawals and throw this exception when limit (Rs.25000/day) is exceeded

### Exercise 4: Checked vs Unchecked
**Problem**: Create a checked exception `AccountCreationException extends Exception` and use it when creating an account with invalid data (e.g., negative initial balance). Show how the calling code MUST handle it (vs unchecked which is optional).

---

## Day 3 Quiz (5 questions — shorter day)

1. What is the difference between checked and unchecked exceptions?
2. In a try-catch with multiple catch blocks, what order should they be in?
3. When does the `finally` block NOT run?
4. What is the difference between `throw` and `throws`?
5. Why create custom exceptions instead of using `IllegalArgumentException` everywhere?

---

## Day 3 Summary

| Step | What Changed | Topics Covered |
|------|-------------|----------------|
| Step 1 | Added try-catch for Scanner input in menu | try-catch, InputMismatchException |
| Step 2 | Added multiple catch blocks in selectAccount | Multiple catch, ordering |
| Step 3 | Used `throw` for business rule violations | throw keyword |
| Step 4 | Created InsufficientBalanceException, InvalidAmountException, AccountInactiveException | Custom exceptions |
| Final | Full BankApp with no crash scenarios | All exception topics |

### Before vs After Day 3

| Scenario | Before (Day 2) | After (Day 3) |
|----------|----------------|---------------|
| User types "abc" | CRASH | "Please enter a number" |
| Withdraw > balance | Print message, return | Throw + catch with details |
| Negative deposit | Print message, return | Throw InvalidAmountException |
| Inactive account | No concept | Throw AccountInactiveException |
| Invalid array index | CRASH | Caught, friendly message |

> **Preview for Day 4**: "Our app handles one or two accounts in an array. But arrays are fixed-size and clunky. What if we need to add/remove accounts dynamically? Search by account number? Tomorrow we'll learn Collections — ArrayList, HashMap — and proper searching algorithms."

---

## Programming Fundamentals & OOPs Assessment

> **Duration**: Remaining time after exception handling
> **Format**: Suggested mix — adjust based on your preference

### Section A: Multiple Choice (10 questions, 1 mark each)

1. Which of these is NOT a primitive type?
   a) int  b) String  c) boolean  d) double
   **Answer: b) String**

2. What is the output?
   ```java
   int x = 10;
   double y = x;
   System.out.println(y);
   ```
   a) 10  b) 10.0  c) Compile error  d) Runtime error
   **Answer: b) 10.0**

3. Which loop always executes at least once?
   a) for  b) while  c) do-while  d) enhanced for
   **Answer: c) do-while**

4. What does `private` mean for a field?
   a) Accessible everywhere  b) Accessible in same package  c) Accessible only in same class  d) Accessible in subclasses
   **Answer: c) Accessible only in same class**

5. Which keyword creates an object?
   a) this  b) class  c) new  d) static
   **Answer: c) new**

6. What is method overriding?
   a) Same name, different params in same class  b) Same name, same params in parent-child  c) Different name, same params  d) None
   **Answer: b)**

7. An abstract class can have:
   a) Only abstract methods  b) Only concrete methods  c) Both abstract and concrete  d) No methods
   **Answer: c) Both**

8. What does `super()` call?
   a) Current class constructor  b) Parent class constructor  c) Static method  d) Main method
   **Answer: b) Parent class constructor**

9. What happens with `Account acc = new SavingsAccount(...)` then `acc.withdraw()`?
   a) Account's withdraw runs  b) SavingsAccount's withdraw runs  c) Compile error  d) Runtime error
   **Answer: b) SavingsAccount's withdraw runs (polymorphism)**

10. Which block always runs in try-catch-finally?
    a) try  b) catch  c) finally  d) none
    **Answer: c) finally**

### Section B: Short Answer (5 questions, 2 marks each)

1. Explain the difference between `==` and `.equals()` for Strings.
2. Why should `balance` not have a public setter?
3. What is the IS-A relationship? Give an example from our banking app.
4. Write a custom exception class `NegativeAmountException` with a field to store the amount.
5. What is the purpose of `@Override`? What happens if you misspell the method name without it?

### Section C: Coding (3 questions, 5 marks each)

1. Write a `Transaction` class with fields: `type` (String: "DEPOSIT"/"WITHDRAW"), `amount` (double), `timestamp` (String). Include a parameterized constructor, getters (no setters — transactions are immutable), and `displayInfo()`.

2. Write a method `findHighestBalance(Account[] accounts, int count)` that returns the Account with the highest balance. Handle the case where the array is empty (throw an appropriate exception).

3. Write a `transfer(Account from, Account to, double amount)` static method that:
   - Validates amount > 0
   - Withdraws from source
   - Deposits to destination
   - Handles exceptions so that if deposit fails, the withdrawal is rolled back
