# Day 1 — Programming Fundamentals & Functions

## Student Handout

> **What you'll build today**: A menu-driven Banking Application using Java fundamentals and methods.
> This application will grow every day throughout the training.

---

## Part A: Programming Fundamentals

---

### 1. Java Platform Overview

**What is Java made of?**

```
┌─────────────────────────────────┐
│           JDK                   │
│  (Java Development Kit)         │
│  javac, jar, javadoc, etc.      │
│                                 │
│  ┌───────────────────────────┐  │
│  │         JRE               │  │
│  │  (Java Runtime Env)       │  │
│  │  Standard libraries       │  │
│  │                           │  │
│  │  ┌─────────────────────┐  │  │
│  │  │       JVM           │  │  │
│  │  │  (Java Virtual      │  │  │
│  │  │   Machine)          │  │  │
│  │  │  Runs bytecode      │  │  │
│  │  └─────────────────────┘  │  │
│  └───────────────────────────┘  │
└─────────────────────────────────┘
```

**How Java code runs:**

```
YourCode.java  →  javac (compiler)  →  YourCode.class (bytecode)  →  JVM  →  Runs on machine
```

- **JDK**: For developers. Includes compiler (`javac`) + everything else.
- **JRE**: For running Java programs. Includes JVM + standard libraries.
- **JVM**: The engine. Reads bytecode and translates it to machine code.
- **Key idea**: You write code once. The bytecode runs on any machine that has a JVM.

**Verify your setup:**
```bash
java -version
javac -version
```

---

### 2. Java Program Structure

Every Java program has this structure:

```java
package com.training.day01;      // optional: organizes your class into a folder

public class HelloBank {         // class name must match file name
    public static void main(String[] args) {   // entry point
        System.out.println("Welcome to Simple Bank!");
    }
}
```

**Rules to remember:**
- File name must exactly match the public class name: `HelloBank.java`
- `main` method is where execution starts — its signature never changes
- Statements end with a semicolon `;`
- Code blocks use curly braces `{ }`

**Naming conventions:**

| Element | Convention | Example |
|---------|-----------|---------|
| Class | PascalCase | `BankAccount` |
| Method | camelCase | `checkBalance()` |
| Variable | camelCase | `accountNumber` |
| Package | lowercase | `com.bank.app` |
| Constant | UPPER_SNAKE | `MAX_BALANCE` |

---

### 3. Variables & Data Types

A **variable** is a named container that holds a value.

**Primitive types** (built into Java — hold actual values):

| Type | Size | What it stores | Example |
|------|------|---------------|---------|
| `int` | 4 bytes | Whole numbers | `int age = 25;` |
| `long` | 8 bytes | Large whole numbers | `long accNo = 1001L;` |
| `double` | 8 bytes | Decimal numbers | `double balance = 50000.00;` |
| `boolean` | 1 bit | true or false | `boolean isActive = true;` |
| `char` | 2 bytes | Single character | `char grade = 'A';` |

**Non-primitive / Reference types** (hold address to data):
- `String` — text: `String name = "Ravi";`
- Arrays, Objects (covered later)

**Type Casting:**

```java
// Implicit (safe — small to large)
int a = 100;
double b = a;          // automatically becomes 100.0

// Explicit (risky — large to small, may lose data)
double x = 9.99;
int y = (int) x;       // becomes 9 (decimal part is lost, NOT rounded)
```

#### Banking App — Step 1: Declare account variables

```java
public class BankApp {
    public static void main(String[] args) {
        String accountHolder = "Ravi Kumar";
        long accountNumber = 1001L;
        double balance = 50000.00;
        boolean isActive = true;

        System.out.println("Account Holder : " + accountHolder);
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Balance        : " + balance);
        System.out.println("Active         : " + isActive);
    }
}
```

**Output:**
```
Account Holder : Ravi Kumar
Account Number : 1001
Balance        : 50000.0
Active         : true
```

---

### 4. Operators

Operators perform actions on values.

**Arithmetic** — math operations:
```java
double balance = 50000;
double deposit = 5000;
balance = balance + deposit;   // 55000  (or use: balance += deposit)
```

**Relational** — comparisons (result is always `boolean`):
```java
balance > 0          // true
balance == 50000     // true
balance != 0         // true
balance >= 100000    // false
```

**Logical** — combine conditions:
```java
// && (AND) — both must be true
isActive && balance > 0            // true only if BOTH are true

// || (OR) — at least one must be true
balance > 100000 || isActive       // true if EITHER is true

// ! (NOT) — flips the value
!isActive                          // false (flips true to false)
```

**Assignment shortcuts:**
```java
balance += 5000;    // same as: balance = balance + 5000
balance -= 2000;    // same as: balance = balance - 2000
```

**Increment/Decrement:**
```java
int count = 0;
count++;    // count becomes 1
count--;    // count becomes 0
```

---

### 5. Control Statements

#### if / else-if / else — Making decisions

```java
double balance = 50000;
double withdrawal = 60000;
double minimumBalance = 1000;

if (withdrawal <= 0) {
    System.out.println("Invalid amount.");
} else if (withdrawal > balance - minimumBalance) {
    System.out.println("Insufficient balance.");
} else {
    balance -= withdrawal;
    System.out.println("Success. New balance: " + balance);
}
```

#### switch — Choosing from a list

```java
int choice = 2;

switch (choice) {
    case 1:
        System.out.println("Check Balance");
        break;          // without break, it "falls through" to the next case
    case 2:
        System.out.println("Deposit");
        break;
    case 3:
        System.out.println("Withdraw");
        break;
    default:
        System.out.println("Invalid choice");
}
```

#### Loops — Repeating actions

**for loop** — when you know how many times:
```java
for (int i = 1; i <= 5; i++) {
    System.out.println("Transaction #" + i);
}
```

**while loop** — when you don't know how many times (check first):
```java
int attempts = 0;
while (attempts < 3) {
    System.out.println("Attempt " + (attempts + 1));
    attempts++;
}
```

**do-while loop** — always runs at least once (check after):
```java
int choice;
do {
    // show menu and get choice
    choice = 4; // placeholder
} while (choice != 4);
```

**break & continue:**
```java
for (int i = 1; i <= 10; i++) {
    if (i == 3) continue;   // skip iteration 3
    if (i > 5) break;       // stop the loop entirely
    System.out.println(i);  // prints: 1, 2, 4, 5
}
```

#### Banking App — Step 2: Add menu loop

```java
double balance = 50000.00;
int choice;

do {
    System.out.println("\n===== SIMPLE BANK =====");
    System.out.println("1. Check Balance");
    System.out.println("2. Deposit");
    System.out.println("3. Withdraw");
    System.out.println("4. Exit");
    System.out.print("Enter choice: ");
    choice = 4; // hardcoded for now

    switch (choice) {
        case 1: System.out.println("Balance: Rs." + balance); break;
        case 2: System.out.println("Deposit — coming soon"); break;
        case 3: System.out.println("Withdraw — coming soon"); break;
        case 4: System.out.println("Goodbye!"); break;
        default: System.out.println("Invalid choice.");
    }
} while (choice != 4);
```

---

### 6. Input Handling

#### Scanner class — reading user input

```java
import java.util.Scanner;       // add this at the top of your file

Scanner sc = new Scanner(System.in);

System.out.print("Enter your name: ");
String name = sc.nextLine();          // reads a full line of text

System.out.print("Enter amount: ");
double amount = sc.nextDouble();      // reads a decimal number

System.out.print("Enter choice: ");
int choice = sc.nextInt();            // reads a whole number
```

**Important gotcha:** After `nextInt()` or `nextDouble()`, there's a leftover newline character. If you call `nextLine()` right after, it reads that empty newline. Fix:

```java
int choice = sc.nextInt();
sc.nextLine();                  // consume the leftover newline
String name = sc.nextLine();   // now this works correctly
```

#### Command line arguments

```java
public static void main(String[] args) {
    // Run with: java BankApp Ravi 50000
    String name = args[0];                  // "Ravi"
    double balance = Double.parseDouble(args[1]);  // 50000.0
}
```

#### Banking App — Step 3: Full interactive version

```java
import java.util.Scanner;

public class BankApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String accountHolder = "Ravi Kumar";
        long accountNumber = 1001L;
        double balance = 50000.00;
        double minimumBalance = 1000.00;
        int choice;

        System.out.println("Welcome, " + accountHolder + "!");

        do {
            System.out.println("\n===== SIMPLE BANK =====");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Current Balance: Rs." + balance);
                    break;

                case 2:
                    System.out.print("Enter deposit amount: ");
                    double deposit = sc.nextDouble();
                    if (deposit <= 0) {
                        System.out.println("Invalid amount.");
                    } else {
                        balance += deposit;
                        System.out.println("Deposited Rs." + deposit);
                        System.out.println("New Balance: Rs." + balance);
                    }
                    break;

                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawal = sc.nextDouble();
                    if (withdrawal <= 0) {
                        System.out.println("Invalid amount.");
                    } else if (withdrawal > balance - minimumBalance) {
                        System.out.println("Insufficient balance.");
                        System.out.println("Max withdrawal: Rs." + (balance - minimumBalance));
                    } else {
                        balance -= withdrawal;
                        System.out.println("Withdrawn Rs." + withdrawal);
                        System.out.println("New Balance: Rs." + balance);
                    }
                    break;

                case 4:
                    System.out.println("Thank you for banking with us!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 4);

        sc.close();
    }
}
```

**Try it**: Compile and run this program. Test all 4 options. Try edge cases like negative amounts, withdrawing more than balance, etc.

---

## Part B: Functions & Modular Programming

> **The problem**: Our BankApp works, but all the logic is packed inside `main`. If we add more features (transfer, mini-statement, interest calculation), `main` will become hundreds of lines long and hard to manage. **Solution: methods.**

---

### 7. Method Basics

A **method** is a reusable block of code that does one specific job.

**Structure:**
```
accessModifier returnType methodName(parameters) {
    // body
    return value;   // if non-void
}
```

**Example:**
```java
public static void greet() {
    System.out.println("Welcome to Simple Bank!");
}

// Calling it:
greet();
```

- **Method signature** = name + parameter types. Example: `deposit(double, double)`.
- The signature is what makes a method unique.

---

### 8. Parameters and Arguments

- **Parameter** = the variable in the method declaration (acts as a placeholder)
- **Argument** = the actual value you pass when calling the method

```java
//           parameter ↓        parameter ↓
public static void printReceipt(long accountNo, double amount) {
    System.out.println("Account: " + accountNo + " | Amount: " + amount);
}

//              argument ↓   argument ↓
printReceipt(1001L, 5000.00);
```

**Order matters:** arguments must match parameters in type and position.

**Java is pass-by-value for primitives** — the method gets a copy:
```java
public static void tryToChange(double bal) {
    bal = 0;   // changes only the local copy
}

double balance = 50000;
tryToChange(balance);
System.out.println(balance);   // still 50000 — original is unchanged
```

---

### 9. Return Values

**void** — the method does something but gives nothing back:
```java
public static void printBalance(double balance) {
    System.out.println("Balance: Rs." + balance);
}
```

**non-void** — the method computes and returns a result:
```java
public static double calculateInterest(double balance, double rate) {
    return balance * rate / 100;
}

// Catch the return value:
double interest = calculateInterest(50000, 4.5);   // 2250.0
```

**Returning boolean** — great for yes/no checks:
```java
public static boolean isValidAmount(double amount) {
    return amount > 0;
}

if (isValidAmount(500)) {
    System.out.println("Amount is valid");
}
```

---

### 10. Validation Logic Using Methods

Extract validation checks into their own methods. This keeps your main logic clean and validations reusable.

```java
public static boolean isValidAmount(double amount) {
    return amount > 0;
}

public static boolean hasSufficientBalance(double balance, double amount, double minBal) {
    return amount <= (balance - minBal);
}

// Usage — clean and readable:
if (!isValidAmount(withdrawal)) {
    System.out.println("Invalid amount.");
} else if (!hasSufficientBalance(balance, withdrawal, 1000)) {
    System.out.println("Insufficient balance.");
} else {
    balance -= withdrawal;
}
```

---

### 11. Static vs Non-Static Methods

| Static | Non-Static |
|--------|-----------|
| Belongs to the **class** | Belongs to an **object** |
| Called without creating an object | Needs an object to call |
| Use for utility/helper functions | Use for object behavior (Day 2) |
| `Math.sqrt(25)` | `scanner.nextInt()` |

Today, all our methods are `static` because we're working inside `main` (which is static itself).

**Rule**: A static method can only directly call other static methods in the same class.

```java
public class BankApp {
    public static void main(String[] args) {
        greet();           // OK — both are static
    }

    public static void greet() {
        System.out.println("Welcome!");
    }
}
```

---

### 12. Method Overloading

Same method name, **different parameters**. Java figures out which one to call based on the arguments.

```java
// Version 1: simple deposit
public static double deposit(double balance, double amount) {
    return balance + amount;
}

// Version 2: deposit with a description
public static double deposit(double balance, double amount, String description) {
    System.out.println("Note: " + description);
    return balance + amount;
}

// Java picks the right one automatically:
balance = deposit(balance, 5000);                    // calls Version 1
balance = deposit(balance, 10000, "Salary credit");  // calls Version 2
```

You've already seen this — `System.out.println()` is overloaded. It accepts `int`, `double`, `String`, `boolean`, etc.

---

### 13. Scope of Variables

**Scope** = where a variable is accessible.

```java
public static void demo() {
    int x = 10;                    // accessible anywhere in this method

    if (x > 5) {
        int y = 20;               // accessible only inside this if-block
        System.out.println(y);    // OK
    }
    // System.out.println(y);     // ERROR — y is out of scope

    for (int i = 0; i < 3; i++) {
        int temp = i * 2;         // accessible only inside this loop
    }
    // System.out.println(i);     // ERROR — i is out of scope
    // System.out.println(temp);  // ERROR — temp is out of scope
}
```

**Rule of thumb**: A variable lives only inside the `{ }` block where it's declared.

---

### Banking App — Final Version: Refactored with Methods

This is the same program from Step 3, but now organized with methods:

```java
import java.util.Scanner;

public class BankApp {

    static final double MINIMUM_BALANCE = 1000.00;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String accountHolder = "Ravi Kumar";
        long accountNumber = 1001L;
        double balance = 50000.00;

        System.out.println("Welcome, " + accountHolder + "!");

        int choice;
        do {
            displayMenu();
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    displayBalance(accountNumber, balance);
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    double depositAmt = sc.nextDouble();
                    balance = deposit(balance, depositAmt);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmt = sc.nextDouble();
                    balance = withdraw(balance, withdrawAmt);
                    break;
                case 4:
                    System.out.println("Thank you for banking with us!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 4);

        sc.close();
    }

    public static void displayMenu() {
        System.out.println("\n===== SIMPLE BANK =====");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");
        System.out.print("Enter choice: ");
    }

    public static void displayBalance(long accountNo, double balance) {
        System.out.println("Account #" + accountNo + " | Balance: Rs." + balance);
    }

    public static double deposit(double balance, double amount) {
        if (!isValidAmount(amount)) {
            System.out.println("Invalid amount. Must be greater than 0.");
            return balance;
        }
        balance += amount;
        System.out.println("Deposited Rs." + amount + " | New Balance: Rs." + balance);
        return balance;
    }

    public static double withdraw(double balance, double amount) {
        if (!isValidAmount(amount)) {
            System.out.println("Invalid amount. Must be greater than 0.");
            return balance;
        }
        if (!hasSufficientBalance(balance, amount)) {
            System.out.println("Insufficient balance. Max withdrawal: Rs."
                    + (balance - MINIMUM_BALANCE));
            return balance;
        }
        balance -= amount;
        System.out.println("Withdrawn Rs." + amount + " | New Balance: Rs." + balance);
        return balance;
    }

    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    public static boolean hasSufficientBalance(double balance, double amount) {
        return amount <= (balance - MINIMUM_BALANCE);
    }
}
```

**Notice the improvement:**
- `main` is now short and readable — it just handles the menu flow
- Each method does **one thing**
- Validation is **reusable** — `isValidAmount` is used by both deposit and withdraw
- Adding a new feature (e.g., transfer) means adding one new method — no need to touch existing code

---

## Exercises

### Exercise 1: Variables & Operators (Warm-up)

**Problem**: Write a program that:
1. Declares variables for two accounts (holder name, account number, balance for each)
2. Prints which account has a higher balance
3. Calculates and prints the total balance across both accounts

<details>
<summary><strong>Solution</strong></summary>

```java
public class CompareAccounts {
    public static void main(String[] args) {
        String holder1 = "Ravi";
        long accNo1 = 1001L;
        double balance1 = 50000;

        String holder2 = "Priya";
        long accNo2 = 1002L;
        double balance2 = 75000;

        double total = balance1 + balance2;
        System.out.println("Total balance: Rs." + total);

        if (balance1 > balance2) {
            System.out.println(holder1 + " has a higher balance.");
        } else if (balance2 > balance1) {
            System.out.println(holder2 + " has a higher balance.");
        } else {
            System.out.println("Both have equal balance.");
        }
    }
}
```
</details>

---

### Exercise 2: Control Flow — Transaction Categorizer

**Problem**: Write a program that takes a transaction amount (using Scanner) and prints:
- Below Rs.1000 → `"Micro transaction"`
- Rs.1000 to Rs.50000 → `"Standard transaction"`
- Above Rs.50000 → `"High-value transaction (requires approval)"`

Also validate that the amount is positive.

<details>
<summary><strong>Solution</strong></summary>

```java
import java.util.Scanner;

public class TransactionCategory {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter transaction amount: ");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount.");
        } else if (amount < 1000) {
            System.out.println("Micro transaction");
        } else if (amount <= 50000) {
            System.out.println("Standard transaction");
        } else {
            System.out.println("High-value transaction (requires approval)");
        }

        sc.close();
    }
}
```
</details>

---

### Exercise 3: Loops — Transaction Summary

**Problem**: Ask the user how many transactions they want to enter. Then for each, read the amount. At the end, print:
- Total of all transactions
- Average transaction amount
- Highest transaction
- Lowest transaction

<details>
<summary><strong>Solution</strong></summary>

```java
import java.util.Scanner;

public class TransactionSummary {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("How many transactions? ");
        int n = sc.nextInt();

        double total = 0, max = Double.MIN_VALUE, min = Double.MAX_VALUE;

        for (int i = 1; i <= n; i++) {
            System.out.print("Transaction #" + i + " amount: ");
            double amount = sc.nextDouble();
            total += amount;
            if (amount > max) max = amount;
            if (amount < min) min = amount;
        }

        System.out.println("\n--- Summary ---");
        System.out.println("Total   : Rs." + total);
        System.out.println("Average : Rs." + (total / n));
        System.out.println("Highest : Rs." + max);
        System.out.println("Lowest  : Rs." + min);

        sc.close();
    }
}
```
</details>

---

### Exercise 4: Methods — Enhance the BankApp

**Problem**: Add these to the BankApp:
1. A `transfer(double fromBalance, double amount)` method — validates amount and sufficient balance, returns new balance after deduction.
2. An overloaded `deposit(double balance, double amount, String remark)` that prints the remark before depositing.
3. Add menu option 5 for "Transfer" in the main loop.

<details>
<summary><strong>Solution</strong></summary>

```java
// Add these methods to BankApp:

public static double transfer(double fromBalance, double amount) {
    if (!isValidAmount(amount)) {
        System.out.println("Invalid transfer amount.");
        return fromBalance;
    }
    if (!hasSufficientBalance(fromBalance, amount)) {
        System.out.println("Insufficient balance for transfer.");
        return fromBalance;
    }
    fromBalance -= amount;
    System.out.println("Transferred Rs." + amount + " | Remaining Balance: Rs." + fromBalance);
    return fromBalance;
}

// Overloaded deposit with remark
public static double deposit(double balance, double amount, String remark) {
    System.out.println("Remark: " + remark);
    return deposit(balance, amount);   // reuses the existing deposit method
}

// In main's switch, add:
// case 5:
//     System.out.print("Enter transfer amount: ");
//     double transferAmt = sc.nextDouble();
//     balance = transfer(balance, transferAmt);
//     break;
```
</details>

---

### Exercise 5: Challenge — Loan EMI Calculator

**Problem**: Build a standalone loan calculator using methods:
- `calculateEMI(double principal, double annualRate, int months)` — returns monthly EMI
  - Formula: `EMI = principal * monthlyRate * (1 + monthlyRate)^months / ((1 + monthlyRate)^months - 1)`
  - Where `monthlyRate = annualRate / 12 / 100`
- `isEligible(double salary, double emi)` — returns `true` if EMI is less than 40% of salary
- Menu: user enters principal, rate, tenure, and salary. Show EMI and eligibility.

<details>
<summary><strong>Solution</strong></summary>

```java
import java.util.Scanner;

public class LoanCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter loan amount (principal): ");
        double principal = sc.nextDouble();

        System.out.print("Enter annual interest rate (%): ");
        double rate = sc.nextDouble();

        System.out.print("Enter tenure (months): ");
        int months = sc.nextInt();

        System.out.print("Enter monthly salary: ");
        double salary = sc.nextDouble();

        double emi = calculateEMI(principal, rate, months);
        System.out.printf("Monthly EMI: Rs.%.2f%n", emi);

        if (isEligible(salary, emi)) {
            System.out.println("You are ELIGIBLE for this loan.");
        } else {
            System.out.printf("NOT ELIGIBLE. EMI (Rs.%.2f) exceeds 40%% of salary (Rs.%.2f).%n",
                    emi, salary * 0.4);
        }

        sc.close();
    }

    public static double calculateEMI(double principal, double annualRate, int months) {
        double monthlyRate = annualRate / 12 / 100;
        double factor = Math.pow(1 + monthlyRate, months);
        return principal * monthlyRate * factor / (factor - 1);
    }

    public static boolean isEligible(double salary, double emi) {
        return emi < salary * 0.4;
    }
}
```
</details>

---

## Quick Quiz

1. What does JVM stand for, and what does it do?
2. What happens if you name the file `bankapp.java` but the class is `BankApp`?
3. What is the output of `int x = (int) 7.9;`?
4. What is the difference between `=` and `==`?
5. When should you use `do-while` instead of `while`?
6. After calling `sc.nextInt()`, why might `sc.nextLine()` return an empty string?
7. What is a method signature?
8. What does "pass-by-value" mean for primitives?
9. Can two methods have the same name in the same class? Under what condition?
10. What is the scope of a variable declared inside a `for` loop?

<details>
<summary><strong>Answers</strong></summary>

1. **Java Virtual Machine** — it reads bytecode and executes it on the current platform.
2. **Compilation error** — file name must exactly match the public class name (case-sensitive).
3. **7** — explicit casting truncates the decimal, it does not round.
4. `=` is assignment (sets a value), `==` is comparison (checks equality).
5. When the loop body must execute **at least once** (e.g., showing a menu before checking the exit condition).
6. Because `nextInt()` reads only the number and leaves the newline character (`\n`) in the buffer. The next `nextLine()` reads that leftover newline.
7. The method **name + parameter types** (e.g., `withdraw(double, double)`). It uniquely identifies a method.
8. A **copy** of the value is passed to the method. Changing the copy inside the method does not affect the original variable.
9. **Yes** — this is called method overloading. The parameter lists must differ (in type, number, or order).
10. Only inside that `for` loop's `{ }` block. It cannot be accessed after the loop ends.

</details>

---

## What We Built Today

| Step | What Changed | Topics Used |
|------|-------------|-------------|
| Step 1 | Declared account variables, printed them | Variables, data types, String concatenation |
| Step 2 | Added menu with do-while + switch | Loops, switch, control flow |
| Step 3 | Added Scanner for user input + validations | Scanner, if/else, operators |
| Final | Refactored everything into methods | Methods, parameters, return values, validation, scope |

## What's Next (Day 2 Preview)

Right now we have **one account** stored in loose variables. What if we need 100 accounts? We can't create `balance1`, `balance2`, ... `balance100`.

Tomorrow we'll learn **Object-Oriented Programming** — we'll turn our account into a reusable **class** (a blueprint), and create as many account **objects** as we want from it.
