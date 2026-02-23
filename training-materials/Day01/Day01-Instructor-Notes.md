# Day 1 — Programming Fundamentals & Functions

## Instructor Guide

> **Duration**: Full day
> **Case Study**: Banking Application (we start building it today)
> **Goal by end of day**: Trainees can write a menu-driven banking app using methods

---

## Part A: Programming Fundamentals (Topics 1–6)

---

### Topic 1: Java Platform Overview

#### Key Points (5 min)

- **JDK** = JRE + development tools (javac, jar, javadoc). This is what developers install.
- **JRE** = JVM + standard libraries. This is what end-users need to run Java apps.
- **JVM** = The engine that runs bytecode. Platform-specific (different JVM for Windows/Linux/Mac).
- **Flow**: `.java` → (javac) → `.class` (bytecode) → (JVM) → machine code
- **"Write Once, Run Anywhere"** — bytecode is portable, JVM handles platform differences.
- **Class loading** — JVM loads `.class` files into memory on demand (just mention, don't deep-dive).

#### Teaching Tip

> Draw the JDK ⊃ JRE ⊃ JVM nesting diagram on the board. Have trainees verify their JDK installation with `java -version` and `javac -version`.

#### Quick Check (ask verbally)

1. If I only have JRE installed, can I compile Java code? *(No — need JDK)*
2. Is bytecode platform-specific or platform-independent? *(Independent)*
3. Which component is platform-specific? *(JVM)*

---

### Topic 2: Java Program Structure

#### Key Points (10 min)

- Every Java program needs at least **one class**.
- Execution starts at `public static void main(String[] args)`.
- File name **must match** the public class name (case-sensitive).
- **Packages** = folders that organize classes. Declared with `package` keyword at top.
- **Naming conventions**:
  - Classes: `PascalCase` (e.g., `BankAccount`)
  - Methods/variables: `camelCase` (e.g., `accountBalance`)
  - Packages: `lowercase` (e.g., `com.bank.app`)
  - Constants: `UPPER_SNAKE_CASE` (e.g., `MAX_BALANCE`)

#### Micro Example

```java
package com.training.day01;

public class HelloBank {
    public static void main(String[] args) {
        System.out.println("Welcome to Simple Bank!");
    }
}
```

#### Teaching Tip

> Walk through compiling and running from the terminal:
> ```
> javac -d . HelloBank.java
> java com.training.day01.HelloBank
> ```
> Explain what `-d .` does (creates package folders).

---

### Topic 3: Variables & Data Types

#### Key Points (15 min)

- **Primitive types** (8): `byte`, `short`, `int`, `long`, `float`, `double`, `char`, `boolean`
- **Non-primitive / Reference types**: `String`, arrays, any class/object
- Key difference: primitives hold the **value**, references hold the **address**.
- **Default values** matter for instance variables (covered in Day 2), not for local variables.
  - `int` → 0, `double` → 0.0, `boolean` → false, `String` → null
- **Local variables MUST be initialized** before use — compiler will reject otherwise.

| Type | Size | Range/Example |
|------|------|--------------|
| `int` | 4 bytes | -2B to 2B |
| `long` | 8 bytes | Use `L` suffix: `100000L` |
| `double` | 8 bytes | `3.14`, `99.99` |
| `boolean` | 1 bit | `true` / `false` |
| `char` | 2 bytes | `'A'`, `'\n'` |
| `String` | varies | `"Hello"` (reference type) |

#### Type Casting

- **Implicit (widening)**: `int` → `long` → `double` (safe, automatic)
- **Explicit (narrowing)**: `double` → `int` (may lose data, must cast manually)

```java
int a = 100;
double b = a;          // implicit: 100.0

double x = 9.99;
int y = (int) x;       // explicit: 9 (truncated, not rounded)
```

#### Case Study Step 1 — Declare account variables

```java
public class BankApp {
    public static void main(String[] args) {
        // Account details
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

> **Talking point**: Ask trainees why `accountNumber` is `long` and not `int`. (Real account numbers can exceed int range.)

---

### Topic 4: Operators

#### Key Points (10 min)

Cover with banking context — no need for abstract examples:

| Category | Operators | Banking Example |
|----------|-----------|----------------|
| Arithmetic | `+  -  *  /  %` | `balance + depositAmount` |
| Relational | `==  !=  >  <  >=  <=` | `balance >= minimumBalance` |
| Logical | `&&  \|\|  !` | `isActive && balance > 0` |
| Assignment | `=  +=  -=  *=  /=` | `balance += deposit` |
| Increment/Decrement | `++  --` | `transactionCount++` |

#### Micro Example

```java
double balance = 50000.00;
double deposit = 5000.00;
double withdrawal = 60000.00;

balance += deposit;   // 55000.00
System.out.println("After deposit: " + balance);

boolean canWithdraw = balance >= withdrawal;  // false
System.out.println("Can withdraw " + withdrawal + "? " + canWithdraw);

int transactionCount = 0;
transactionCount++;   // 1
```

#### Common Pitfall

> `==` vs `.equals()` for Strings — briefly mention, full explanation in Day 2 (reference vs value).
> ```java
> String s1 = "hello";
> String s2 = new String("hello");
> System.out.println(s1 == s2);       // false (different references)
> System.out.println(s1.equals(s2));  // true (same content)
> ```

---

### Topic 5: Control Statements

#### Key Points (20 min)

**if / if-else / else-if** — Use for branching decisions.

#### Case Study Step 2 — Withdrawal validation

```java
double balance = 50000.00;
double withdrawal = 60000.00;
double minimumBalance = 1000.00;

if (withdrawal <= 0) {
    System.out.println("Invalid amount.");
} else if (withdrawal > balance - minimumBalance) {
    System.out.println("Insufficient balance. Available: " + (balance - minimumBalance));
} else {
    balance -= withdrawal;
    System.out.println("Withdrawal successful. New balance: " + balance);
}
```

> **Talking point**: Why do we check `balance - minimumBalance` and not just `balance`? (Minimum balance requirement — real banking rule.)

**switch** — Good for menu-driven flow.

```java
int choice = 2;

switch (choice) {
    case 1:
        System.out.println("Check Balance");
        break;
    case 2:
        System.out.println("Deposit");
        break;
    case 3:
        System.out.println("Withdraw");
        break;
    case 4:
        System.out.println("Exit");
        break;
    default:
        System.out.println("Invalid choice");
}
```

**Loops** — `for`, `while`, `do-while`

- `for` → known number of iterations
- `while` → unknown, check-first
- `do-while` → unknown, execute-first (menu loops!)

#### Case Study Step 3 — Menu loop

```java
// Using do-while for menu — always shows at least once
int choice;
do {
    System.out.println("\n--- Simple Bank ---");
    System.out.println("1. Check Balance");
    System.out.println("2. Deposit");
    System.out.println("3. Withdraw");
    System.out.println("4. Exit");
    System.out.print("Enter choice: ");
    choice = 4; // hardcoded for now, Scanner comes next

    switch (choice) {
        case 1: System.out.println("Balance: " + balance); break;
        case 2: System.out.println("Deposit - coming soon"); break;
        case 3: System.out.println("Withdraw - coming soon"); break;
        case 4: System.out.println("Thank you!"); break;
        default: System.out.println("Invalid choice");
    }
} while (choice != 4);
```

**break & continue**

```java
// Print first 5 transactions, skip cancelled ones
for (int i = 1; i <= 10; i++) {
    if (i == 3) continue;  // skip transaction #3 (cancelled)
    if (i > 5) break;      // stop after 5
    System.out.println("Transaction #" + i);
}
// Output: 1, 2, 4, 5
```

---

### Topic 6: Input Handling

#### Key Points (10 min)

- `Scanner` class — `java.util.Scanner`, reads from `System.in`
- Common methods: `nextInt()`, `nextDouble()`, `nextLine()`, `next()`
- **Gotcha**: `nextInt()` / `nextDouble()` leave a newline in the buffer. Call `nextLine()` after to consume it.
- **Command line arguments**: `args` array in `main`. All values are `String`, must parse if needed.

#### Micro Example

```java
import java.util.Scanner;

Scanner sc = new Scanner(System.in);

System.out.print("Enter amount: ");
double amount = sc.nextDouble();

System.out.print("Enter name: ");
sc.nextLine();                    // consume leftover newline
String name = sc.nextLine();

System.out.println(name + " deposited " + amount);
```

#### Case Study Step 4 — Full interactive BankApp (Part A finale)

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
                        System.out.println("Deposited Rs." + deposit + " | New Balance: Rs." + balance);
                    }
                    break;

                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawal = sc.nextDouble();
                    if (withdrawal <= 0) {
                        System.out.println("Invalid amount.");
                    } else if (withdrawal > balance - minimumBalance) {
                        System.out.println("Insufficient balance. Max withdrawal: Rs." + (balance - minimumBalance));
                    } else {
                        balance -= withdrawal;
                        System.out.println("Withdrawn Rs." + withdrawal + " | New Balance: Rs." + balance);
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

> **This is the Part A milestone.** Have trainees type and run this. Make sure everyone has it working before moving to Part B.

---

## Part B: Functions & Modular Programming (Topics 7–13)

> **Transition**: "The BankApp works, but all logic is crammed in main. What if we need 20 operations? Let's organize with methods."

---

### Topic 7: Method Basics

#### Key Points (10 min)

- A **method** = a named, reusable block of code.
- **Declaration** = access modifier + return type + name + parameters
- **Signature** = method name + parameter types (this is what makes a method unique)
- **Calling** = `methodName(arguments);`

```
accessModifier returnType methodName(parameterList) {
    // method body
    return value;  // if non-void
}
```

#### Micro Example

```java
public static void greet() {
    System.out.println("Welcome to Simple Bank!");
}

// Call it
greet();
```

---

### Topic 8: Parameters and Arguments

#### Key Points (10 min)

- **Parameter** = variable in the method declaration (placeholder)
- **Argument** = actual value passed when calling
- Java is **pass-by-value** for primitives (a copy is passed — original unchanged)
- Order matters: `deposit(accountNumber, amount)` ≠ `deposit(amount, accountNumber)`

#### Micro Example

```java
public static void printReceipt(long accountNo, double amount, String type) {
    System.out.println("--- Receipt ---");
    System.out.println("Account : " + accountNo);
    System.out.println("Type    : " + type);
    System.out.println("Amount  : Rs." + amount);
}

// Call — arguments must match parameter order and type
printReceipt(1001L, 5000.00, "DEPOSIT");
```

#### Pass-by-value demo

```java
public static void tryToChange(double bal) {
    bal = 0;  // only changes the local copy
    System.out.println("Inside method: " + bal);   // 0.0
}

double balance = 50000;
tryToChange(balance);
System.out.println("Outside method: " + balance);  // 50000.0 (unchanged)
```

---

### Topic 9: Return Values

#### Key Points (10 min)

- **void** — method does something but returns nothing (e.g., print a receipt)
- **non-void** — method computes and returns a result (e.g., calculate interest)
- Return type must match what's actually returned.
- A method can return **boolean** for validation checks — very clean pattern.

#### Micro Example

```java
// Returns computed value
public static double calculateInterest(double balance, double rate) {
    return balance * rate / 100;
}

// Returns boolean for validation
public static boolean isValidAmount(double amount) {
    return amount > 0;
}

// Usage
double interest = calculateInterest(50000, 4.5);  // 2250.0
boolean valid = isValidAmount(-100);               // false
```

---

### Topic 10: Validation Logic Using Methods

#### Key Points (5 min)

- Extract validation into dedicated methods — keeps main logic clean.
- Return `boolean` and let the caller decide what to do.

#### Case Study Step 5 — Validation methods

```java
public static boolean isValidAmount(double amount) {
    return amount > 0;
}

public static boolean hasSufficientBalance(double balance, double withdrawal, double minBalance) {
    return withdrawal <= (balance - minBalance);
}
```

---

### Topic 11: Static vs Non-Static Methods

#### Key Points (10 min)

- **static** methods belong to the **class**, called via `ClassName.method()` or directly in the same class.
- **non-static** methods belong to an **object** — need an instance. (Day 2 topic, just introduce.)
- Right now (Day 1), all our methods are `static` because we're working inside `main` (which is static).
- **Rule**: A static method can only directly call other static methods.

#### Teaching Tip

> "Today, everything is static. Tomorrow when we learn OOP, we'll understand when and why to drop static. For now, think of static as 'utility/helper' methods."

---

### Topic 12: Method Overloading (Intro)

#### Key Points (5 min)

- Same method **name**, different **parameter list** (type, number, or order).
- Return type alone does NOT distinguish overloaded methods.
- Real use: `System.out.println()` is overloaded — accepts `int`, `double`, `String`, etc.

#### Micro Example

```java
// Overloaded deposit methods
public static double deposit(double balance, double amount) {
    return balance + amount;
}

public static double deposit(double balance, double amount, String description) {
    System.out.println("Note: " + description);
    return balance + amount;
}

// Both calls work
balance = deposit(balance, 5000);
balance = deposit(balance, 10000, "Salary credit");
```

---

### Topic 13: Scope of Variables

#### Key Points (5 min)

- **Local variables** — declared inside a method/block. Only accessible there.
- **Method parameters** — local to that method.
- A variable declared inside a `for` loop is not accessible outside it.

```java
public static void demo() {
    int x = 10;              // accessible in entire method

    for (int i = 0; i < 5; i++) {
        int temp = i * x;    // accessible only inside this loop
    }

    // System.out.println(i);     // ERROR — i not in scope
    // System.out.println(temp);  // ERROR — temp not in scope
}
```

---

## Case Study Step 6 — Refactored BankApp with Methods (Part B Finale)

> **This is the Day 1 final version.** Show the before (Step 4) and after (Step 6) side-by-side.

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

    // --- Display methods ---

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

    // --- Transaction methods ---

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
            System.out.println("Insufficient balance. Max withdrawal: Rs." + (balance - MINIMUM_BALANCE));
            return balance;
        }
        balance -= amount;
        System.out.println("Withdrawn Rs." + amount + " | New Balance: Rs." + balance);
        return balance;
    }

    // --- Validation methods ---

    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    public static boolean hasSufficientBalance(double balance, double amount) {
        return amount <= (balance - MINIMUM_BALANCE);
    }
}
```

> **Teaching moment**: Compare this to Step 4. Same behavior, but now:
> - `main` is a clean orchestrator
> - Each method has one job
> - Validation is reusable
> - Easy to add new features (e.g., transfer) without touching existing methods

---

## Day 1 Exercises

### Exercise 1: Warm-up (Topics 1–4)
**Problem**: Write a program that declares variables for two accounts and prints which one has a higher balance. Use all operator types.

### Exercise 2: Control Flow (Topic 5)
**Problem**: Write a program that takes a transaction amount and categorizes it:
- Below Rs.1000 → "Micro transaction"
- Rs.1000 to Rs.50000 → "Standard transaction"
- Above Rs.50000 → "High-value transaction (requires approval)"

### Exercise 3: Loop Practice (Topics 5–6)
**Problem**: Using Scanner, take N transaction amounts from the user and print the total, average, max, and min.

### Exercise 4: Methods (Topics 7–13)
**Problem**: Add these features to the BankApp:
1. A `transfer(double fromBalance, double amount)` method that validates and returns the new balance.
2. An overloaded `deposit(double balance, double amount, String remark)` that prints the remark.
3. A `printMiniStatement(...)` method that takes 3 recent amounts and prints them.

### Exercise 5: Full Challenge
**Problem**: Build a simple **Loan Calculator** using methods:
- `calculateEMI(double principal, double rate, int months)` → returns monthly EMI
- `isEligible(double salary, double emi)` → returns true if EMI < 40% of salary
- Menu-driven: user enters loan details, program shows EMI and eligibility.

---

## Day 1 Quiz (10 questions — use at end of day or as assessment)

1. What does JVM stand for and what is its role?
2. What happens if you name the file `bankapp.java` but the class is `BankApp`?
3. What is the default value of an uninitialized local `int` variable?
4. What is the output of: `int x = (int) 7.9;`?
5. What is the difference between `=` and `==`?
6. When would you use `do-while` instead of `while`?
7. What is the Scanner gotcha after `nextInt()`?
8. What is a method signature?
9. Can two methods have the same name? Under what condition?
10. What is the scope of a variable declared inside a `for` loop?

---

## Day 1 Summary — What We Built

| Step | What Changed | Topics Covered |
|------|-------------|----------------|
| Step 1 | Declared account variables, printed them | Variables, data types |
| Step 2 | Added withdrawal validation with if/else | Operators, control flow |
| Step 3 | Added menu loop with switch | Loops, switch |
| Step 4 | Added Scanner for user input | Input handling |
| Step 5 | Extracted validation methods | Methods, boolean returns |
| Step 6 | Fully refactored with methods | All method topics, scope |

> **Preview for Day 2**: "Right now we have one account with loose variables. What if we need 100 accounts? We can't create 100 `balance` variables. Tomorrow we'll solve this with Object-Oriented Programming — turning our account into a reusable blueprint."
