# Day 3 — Exception Handling

## Student Handout

> **What you'll build today**: Add robust error handling to the Banking App so it never crashes — custom exceptions for business rules like insufficient balance, invalid amounts, and inactive accounts.

---

### The Problem

Run the Day 2 BankApp and type "abc" when asked for a menu choice:

```
Enter choice: abc
Exception in thread "main" java.util.InputMismatchException
    at java.util.Scanner.nextInt(Scanner.java:...)
    at BankApp.main(BankApp.java:25)
```

The program crashed. In a real banking system, this is unacceptable. Today we'll fix this.

---

## 1. Exception Hierarchy

An **exception** is an unexpected event that disrupts normal program flow.

```
                  Throwable
                 /         \
            Error          Exception
         (don't catch)    /          \
                    Checked      Unchecked (RuntimeException)
                  (must handle)   (optional to handle)
```

| Category | Examples | When to handle |
|----------|---------|---------------|
| **Error** | `OutOfMemoryError`, `StackOverflowError` | Don't catch — JVM problems |
| **Checked** | `IOException`, `SQLException` | Must handle — compiler forces you |
| **Unchecked** | `NullPointerException`, `InputMismatchException` | Optional — but you should for user-facing code |

**Common exceptions you'll encounter:**

| Exception | What causes it |
|-----------|---------------|
| `NullPointerException` | Calling a method on a `null` reference |
| `ArrayIndexOutOfBoundsException` | Accessing an invalid array index |
| `InputMismatchException` | Scanner receives wrong input type |
| `NumberFormatException` | `Integer.parseInt("abc")` |
| `ArithmeticException` | Division by zero |
| `IllegalArgumentException` | Method receives an invalid argument |

**Reading a stack trace** (read bottom-to-top):

```
Exception in thread "main" java.util.InputMismatchException
    at java.util.Scanner.nextInt(Scanner.java:2283)     ← where it happened inside Java
    at BankApp.main(BankApp.java:25)                     ← YOUR code that triggered it
```

Line 25 of BankApp.java called `nextInt()`, and the user typed something that wasn't a number.

---

## 2. try – catch – finally

Wrap risky code in `try`, handle errors in `catch`, cleanup in `finally`.

```java
try {
    // code that might throw an exception
} catch (ExceptionType e) {
    // what to do if that exception happens
} finally {
    // ALWAYS runs — whether exception occurred or not
    // used for cleanup (closing files, connections, etc.)
}
```

**How it flows:**

```
No exception:       try (completes) → finally → continues
Exception caught:   try (fails) → catch → finally → continues
Exception NOT caught: try (fails) → finally → program crashes
```

### Example — Protecting user input:

```java
import java.util.InputMismatchException;
import java.util.Scanner;

Scanner sc = new Scanner(System.in);

try {
    System.out.print("Enter amount: ");
    double amount = sc.nextDouble();
    System.out.println("You entered: " + amount);
} catch (InputMismatchException e) {
    System.out.println("Invalid input. Please enter a number.");
    sc.nextLine();   // clear the bad input from the scanner buffer
}
System.out.println("Program continues normally!");  // no crash
```

### Banking App — Step 1: Protect menu input

```java
int choice = 0;
do {
    displayMenu();
    try {
        choice = sc.nextInt();
    } catch (InputMismatchException e) {
        System.out.println("Please enter a number.");
        sc.nextLine();   // clear invalid input
        continue;        // restart the loop — show menu again
    }

    switch (choice) {
        // ... handle cases ...
    }
} while (choice != 6);
```

Now typing "abc" shows a friendly message instead of crashing.

### When to use `finally`:

```java
Scanner fileScanner = null;
try {
    fileScanner = new Scanner(new File("data.txt"));
    // process file...
} catch (FileNotFoundException e) {
    System.out.println("File not found.");
} finally {
    // Always close the file — even if an exception occurred
    if (fileScanner != null) {
        fileScanner.close();
    }
}
```

---

## 3. Multiple Catch Blocks

One `try` can have multiple `catch` blocks for different exceptions:

```java
try {
    Account acc = accounts[index];    // might throw ArrayIndexOutOfBounds
    acc.withdraw(amount);             // might throw other exceptions
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Invalid account selection.");
} catch (NullPointerException e) {
    System.out.println("Account not initialized.");
} catch (Exception e) {
    System.out.println("Unexpected error: " + e.getMessage());
}
```

**Order matters — specific before general:**

```java
// CORRECT: specific first, general last
catch (ArrayIndexOutOfBoundsException e) { ... }
catch (NullPointerException e) { ... }
catch (Exception e) { ... }           // safety net — catches anything else

// WRONG: compiler error!
catch (Exception e) { ... }           // catches everything — blocks below are unreachable
catch (NullPointerException e) { ... } // COMPILE ERROR — never reached
```

**Rule**: Child exceptions before parent exceptions (specific → general).

### Banking App — Step 2: Multiple catches in account selection

```java
public static Account selectAccount(Scanner sc, Account[] accounts, int count) {
    System.out.println("Select account:");
    for (int i = 0; i < count; i++) {
        System.out.println("  " + (i + 1) + ". " + accounts[i].getHolderName()
                + " (#" + accounts[i].getAccountNumber() + ")");
    }
    System.out.print("Enter choice: ");
    try {
        int index = sc.nextInt() - 1;
        if (index < 0 || index >= count) {
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

## 4. throw Keyword

So far we've been **catching** exceptions that Java throws. But you can also **throw your own**.

**`throw`** = manually trigger an exception when a business rule is violated.

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

**`throw` vs `throws`:**

| | `throw` | `throws` |
|---|---|---|
| Where | Inside method body | In method signature |
| Purpose | Actually throws an exception | Declares that a method *might* throw |
| Example | `throw new Exception("msg");` | `void read() throws IOException` |

### Banking App — Step 3: throw in Account methods

```java
// Account.java — deposit now throws on bad input
public void deposit(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("Deposit amount must be positive. Got: " + amount);
    }
    this.balance += amount;
    System.out.println("Deposited Rs." + amount + " | New Balance: Rs." + this.balance);
}
```

The caller catches it:
```java
try {
    account.deposit(-500);
} catch (IllegalArgumentException e) {
    System.out.println("Error: " + e.getMessage());
    // Output: Error: Deposit amount must be positive. Got: -500.0
}
```

**Design pattern — Account throws, BankApp catches:**

```
Account classes:    Define rules → throw exceptions when violated
BankApp:            Calls account methods → catches exceptions → shows user-friendly messages
```

This keeps business logic separate from user interface logic.

---

## 5. Creating Custom Exception Classes

`IllegalArgumentException` is generic. When you read this:

```java
catch (IllegalArgumentException e)
```

You don't know if it's a balance issue, an invalid amount, or something else. **Custom exceptions** make errors self-documenting.

### How to create a custom exception:

```java
// Extend RuntimeException for unchecked (no forced try-catch)
// Extend Exception for checked (forces try-catch)

public class InsufficientBalanceException extends RuntimeException {

    private double currentBalance;
    private double withdrawalAmount;

    public InsufficientBalanceException(String message, double currentBalance, double withdrawalAmount) {
        super(message);    // pass message to parent
        this.currentBalance = currentBalance;
        this.withdrawalAmount = withdrawalAmount;
    }

    public double getCurrentBalance() { return currentBalance; }
    public double getWithdrawalAmount() { return withdrawalAmount; }
}
```

### Banking App — Step 4: Custom exceptions

**Create three exception classes:**

**InsufficientBalanceException.java:**
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

**InvalidAmountException.java:**
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

**AccountInactiveException.java:**
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

**Use them in SavingsAccount.withdraw():**

```java
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
```

**Catch them in BankApp with specific messages:**

```java
try {
    double amount = sc.nextDouble();
    account.withdraw(amount);
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
```

**How readable!** Each `catch` handles a specific business scenario with a meaningful message.

---

## Complete Day 3 BankApp

### Exception classes (create each as a separate .java file):

See Step 4 above for `InsufficientBalanceException`, `InvalidAmountException`, and `AccountInactiveException`.

### Updated Account.java

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

    public String getHolderName() { return holderName; }
    public long getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public boolean isActive() { return isActive; }

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

### Updated BankApp.java

```java
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankApp {

    static Account[] accounts = new Account[10];
    static int accountCount = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

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
                case 1: viewAllAccounts(); break;
                case 2: handleDeposit(sc); break;
                case 3: handleWithdrawal(sc); break;
                case 4: handleAddInterest(); break;
                case 5: handleDeactivateAccount(sc); break;
                case 6: System.out.println("Thank you for banking with us!"); break;
                default: System.out.println("Invalid choice. Select 1-6.");
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
        if (!found) System.out.println("No savings accounts found.");
    }

    static void handleDeactivateAccount(Scanner sc) {
        Account acc = selectAccount(sc);
        if (acc == null) return;
        acc.setActive(false);
        System.out.println("Account #" + acc.getAccountNumber() + " deactivated.");
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

### Test these scenarios:

| Test | Input | Expected Result |
|------|-------|----------------|
| Bad menu input | Type "abc" at menu | "Please enter a number" → menu shows again |
| Invalid deposit | Deposit Rs.-500 | "INVALID: Deposit amount must be positive." |
| Insufficient balance (Savings) | Withdraw Rs.48000 (balance 50000) | "DECLINED: Must maintain minimum balance of Rs.1000" |
| Overdraft (Current) | Withdraw Rs.35000 (balance 30000) | Success with overdraft warning |
| Over overdraft limit | Withdraw Rs.50000 from Current | "DECLINED: Exceeds overdraft limit" |
| Inactive account | Deactivate → try deposit | "BLOCKED: Account #1001 is inactive" |

---

## Exercises

### Exercise 1: Reading Stack Traces

**Problem**: Given this stack trace, answer the questions below:

```
Exception in thread "main" java.lang.NullPointerException: Cannot invoke "Account.deposit(double)"
    at BankApp.handleDeposit(BankApp.java:58)
    at BankApp.main(BankApp.java:30)
```

1. What exception occurred?
2. In which method did it happen?
3. What likely caused it?
4. How would you fix it?

<details>
<summary><strong>Solution</strong></summary>

1. `NullPointerException`
2. `handleDeposit()` at line 58 of BankApp.java
3. The `Account` object is `null` — likely `selectAccount()` returned `null` (user entered an invalid selection) and `handleDeposit` didn't check for null before calling `deposit()`.
4. Add a null check: `if (acc == null) return;` before calling `acc.deposit()`.
</details>

---

### Exercise 2: Transfer with Exception Handling

**Problem**: Add a `transfer` feature to the BankApp:

Write a static method `transfer(Account from, Account to, double amount)` that:
1. Validates both accounts are active
2. Validates amount is positive
3. Withdraws from the source account
4. Deposits to the destination account
5. If the deposit fails after a successful withdrawal, **roll back** the withdrawal

Add a menu option "Transfer Between Accounts" and catch all possible exceptions.

<details>
<summary><strong>Solution</strong></summary>

```java
public static void transfer(Account from, Account to, double amount) {
    if (!from.isActive()) {
        throw new AccountInactiveException(from.getAccountNumber());
    }
    if (!to.isActive()) {
        throw new AccountInactiveException(to.getAccountNumber());
    }
    if (amount <= 0) {
        throw new InvalidAmountException("Transfer amount must be positive.", amount);
    }

    // Withdraw from source
    from.withdraw(amount);

    // Deposit to destination — if this fails, roll back
    try {
        to.deposit(amount);
    } catch (Exception e) {
        // Roll back: put money back into source
        from.deposit(amount);
        throw new RuntimeException("Transfer failed. Rolled back. Reason: " + e.getMessage());
    }

    System.out.println("Transfer of Rs." + amount + " completed.");
    System.out.println("  From: " + from.getHolderName() + " (Rs." + from.getBalance() + ")");
    System.out.println("  To:   " + to.getHolderName() + " (Rs." + to.getBalance() + ")");
}

// In BankApp menu:
// case 7:
//     System.out.println("--- Transfer ---");
//     System.out.println("Select source account:");
//     Account from = selectAccount(sc);
//     if (from == null) break;
//     System.out.println("Select destination account:");
//     Account to = selectAccount(sc);
//     if (to == null) break;
//     System.out.print("Enter transfer amount: ");
//     try {
//         double amt = sc.nextDouble();
//         transfer(from, to, amt);
//     } catch (InsufficientBalanceException e) {
//         System.out.println("DECLINED: " + e.getMessage());
//     } catch (AccountInactiveException e) {
//         System.out.println("BLOCKED: " + e.getMessage());
//     } catch (Exception e) {
//         System.out.println("ERROR: " + e.getMessage());
//     }
//     break;
```
</details>

---

### Exercise 3: DailyWithdrawalLimitException

**Problem**: Create a custom exception `DailyWithdrawalLimitException` with fields:
- `dailyLimit` (double)
- `attemptedAmount` (double)
- `totalWithdrawnToday` (double)

Then modify `SavingsAccount` to:
- Track `totalWithdrawnToday` (new field)
- Set daily limit to Rs.25,000
- Throw `DailyWithdrawalLimitException` if a withdrawal would exceed the daily limit
- Add a `resetDailyLimit()` method (called at end of day)

<details>
<summary><strong>Solution</strong></summary>

```java
// DailyWithdrawalLimitException.java
public class DailyWithdrawalLimitException extends RuntimeException {
    private double dailyLimit;
    private double attemptedAmount;
    private double totalWithdrawnToday;

    public DailyWithdrawalLimitException(double dailyLimit, double attemptedAmount, double totalWithdrawnToday) {
        super("Daily withdrawal limit of Rs." + dailyLimit + " exceeded. "
                + "Already withdrawn: Rs." + totalWithdrawnToday
                + ", Attempted: Rs." + attemptedAmount);
        this.dailyLimit = dailyLimit;
        this.attemptedAmount = attemptedAmount;
        this.totalWithdrawnToday = totalWithdrawnToday;
    }

    public double getDailyLimit() { return dailyLimit; }
    public double getAttemptedAmount() { return attemptedAmount; }
    public double getTotalWithdrawnToday() { return totalWithdrawnToday; }
}

// Modifications in SavingsAccount:
private double totalWithdrawnToday = 0;
private static final double DAILY_WITHDRAWAL_LIMIT = 25000.0;

@Override
public void withdraw(double amount) {
    // ... existing validations ...

    if (totalWithdrawnToday + amount > DAILY_WITHDRAWAL_LIMIT) {
        throw new DailyWithdrawalLimitException(DAILY_WITHDRAWAL_LIMIT, amount, totalWithdrawnToday);
    }

    setBalance(getBalance() - amount);
    totalWithdrawnToday += amount;
    System.out.println("Withdrawn Rs." + amount + " | Balance: Rs." + getBalance());
}

public void resetDailyLimit() {
    totalWithdrawnToday = 0;
    System.out.println("Daily withdrawal limit reset for account #" + getAccountNumber());
}
```
</details>

---

### Exercise 4: Checked vs Unchecked Exception

**Problem**: Create a **checked** exception `AccountCreationException extends Exception`. Use it in an `AccountFactory` class:

```java
public class AccountFactory {
    public static Account createSavingsAccount(String name, long accNo, double balance, double rate)
            throws AccountCreationException {
        // Validate: name not empty, accNo > 0, balance >= 0, rate > 0 and <= 15
        // Throw AccountCreationException with details for any invalid input
        // Return new SavingsAccount if all valid
    }
}
```

Show how the caller is **forced** to handle this with try-catch (because it's checked).

<details>
<summary><strong>Solution</strong></summary>

```java
// AccountCreationException.java
public class AccountCreationException extends Exception {
    public AccountCreationException(String message) {
        super(message);
    }
}

// AccountFactory.java
public class AccountFactory {

    public static Account createSavingsAccount(String name, long accNo,
                                                double balance, double rate)
            throws AccountCreationException {

        if (name == null || name.trim().isEmpty()) {
            throw new AccountCreationException("Account holder name is required.");
        }
        if (accNo <= 0) {
            throw new AccountCreationException("Account number must be positive.");
        }
        if (balance < 0) {
            throw new AccountCreationException("Initial balance cannot be negative.");
        }
        if (rate <= 0 || rate > 15) {
            throw new AccountCreationException("Interest rate must be between 0 and 15%. Got: " + rate);
        }

        return new SavingsAccount(name, accNo, balance, rate);
    }
}

// Caller is FORCED to handle (compiler error without try-catch):
try {
    Account acc = AccountFactory.createSavingsAccount("Ravi", 1001L, 50000, 4.5);
    System.out.println("Account created!");
} catch (AccountCreationException e) {
    System.out.println("Failed to create account: " + e.getMessage());
}
```
</details>

---

## Quick Quiz

1. What is the difference between checked and unchecked exceptions?
2. In a try-catch with multiple catch blocks, what order should they be in?
3. When does the `finally` block NOT run?
4. What is the difference between `throw` and `throws`?
5. Why create custom exceptions like `InsufficientBalanceException` instead of using `IllegalArgumentException` for everything?

<details>
<summary><strong>Answers</strong></summary>

1. **Checked** exceptions must be handled (try-catch) or declared (throws) — the compiler enforces it. Examples: IOException, SQLException. **Unchecked** exceptions (RuntimeException and its children) are optional to handle. Examples: NullPointerException, IllegalArgumentException.

2. **Specific before general** (child before parent). Put `NullPointerException` before `RuntimeException` before `Exception`. The first matching catch block wins.

3. The `finally` block almost always runs. The only case it doesn't run is if the JVM itself exits during the try/catch (e.g., `System.exit(0)` is called, or the JVM crashes).

4. **`throw`** is used inside a method body to actually raise an exception: `throw new Exception("msg")`. **`throws`** is used in the method signature to declare that the method might throw a checked exception: `void read() throws IOException`.

5. Custom exceptions are **self-documenting**. `InsufficientBalanceException` tells you exactly what went wrong. You can also add domain-specific fields (currentBalance, withdrawalAmount) for better error reporting. With `IllegalArgumentException`, every catch block looks the same and you can't distinguish business scenarios.
</details>

---

## What We Built Today

| Step | What Changed | Topics |
|------|-------------|--------|
| Step 1 | Protected menu input with try-catch | try-catch, finally |
| Step 2 | Added multiple catches in selectAccount | Multiple catch blocks |
| Step 3 | Account methods throw on rule violations | throw keyword |
| Step 4 | Created custom exception classes | Custom exceptions |
| Final | BankApp handles every error scenario gracefully | All topics combined |

### Before vs After

| Scenario | Day 2 (no handling) | Day 3 (with handling) |
|----------|--------------------|-----------------------|
| Type "abc" at menu | CRASH | "Please enter a number" → retry |
| Negative deposit | `println` + return | `throw InvalidAmountException` → caught with details |
| Insufficient balance | `println` + return | `throw InsufficientBalanceException` → shows balance & requested amount |
| Inactive account | No concept | `throw AccountInactiveException` → operation blocked |

## What's Next (Day 4 Preview)

Our accounts live in a fixed-size array (`Account[10]`). What if we need more? What if we want to quickly look up an account by its number instead of looping through all of them?

Tomorrow we learn **Collections** (ArrayList, HashMap) for flexible data storage, and **Searching Algorithms** (Linear Search, Binary Search) for efficient lookups.
