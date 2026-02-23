# Day 7 — File Handling

## Student Handout

> **What you'll build today**: Save accounts and transactions to files so your banking data survives after the program exits. You'll learn both CSV (text-based) and serialization (binary) approaches.

---

## 1. Why File Handling?

**The problem**: Run the BankApp, create accounts, deposit money, exit. Run again — everything is gone.

| | In-Memory (Collections) | Files | Database (Day 12) |
|---|---|---|---|
| Speed | Very fast | Moderate | Moderate |
| Persists after exit? | No | **Yes** | Yes |
| Human-readable? | N/A | Yes (CSV/text) | Via queries |
| Concurrent access? | Single program | Limited | Yes |

Today we add file persistence. Our BankApp will **save on exit** and **load on startup**.

---

## 2. Java File I/O Overview

### The File class

```java
import java.io.File;

File file = new File("data/accounts.csv");

System.out.println("Exists: " + file.exists());         // false (first run)
System.out.println("Absolute: " + file.getAbsolutePath()); // full path
System.out.println("Is file: " + file.isFile());
System.out.println("Size: " + file.length() + " bytes");

// Create the directory if it doesn't exist
File dir = new File("data");
if (!dir.exists()) {
    dir.mkdirs();    // creates parent directories too
}
```

### Paths

- **Absolute**: `/home/user/project/data/accounts.csv` — full path from root
- **Relative**: `data/accounts.csv` — relative to your project directory

---

## 3. Byte Streams vs Character Streams

Java has two families of I/O:

| | Byte Streams | Character Streams |
|---|---|---|
| Unit | Raw bytes | Characters (text) |
| Base classes | `InputStream` / `OutputStream` | `Reader` / `Writer` |
| Use for | Binary files (images, serialized objects) | Text files (CSV, logs) |

For our banking app:
- **CSV files** → Character streams (Reader/Writer)
- **Serialized objects** → Byte streams (InputStream/OutputStream)

### Basic character stream example

```java
import java.io.FileWriter;
import java.io.FileReader;

// Write text
FileWriter writer = new FileWriter("test.txt");
writer.write("Hello, Bank!\n");
writer.close();

// Read text (one character at a time — slow!)
FileReader reader = new FileReader("test.txt");
int ch;
while ((ch = reader.read()) != -1) {
    System.out.print((char) ch);
}
reader.close();
```

Problem: `FileReader.read()` reads **one character at a time** — very slow for large files. Solution: **Buffered streams**.

---

## 4. Buffered Streams

**BufferedReader / BufferedWriter** add an internal buffer (8KB by default). Instead of reading/writing one character, they process chunks.

**Key method**: `readLine()` — reads a complete line as String. Returns `null` at end of file.

### Writing with BufferedWriter

```java
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
    writer.write("Line 1");
    writer.newLine();          // platform-appropriate line ending
    writer.write("Line 2");
    writer.newLine();
}   // automatically closed
```

### Reading with BufferedReader

```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

try (BufferedReader reader = new BufferedReader(new FileReader("output.txt"))) {
    String line;
    while ((line = reader.readLine()) != null) {   // null = end of file
        System.out.println(line);
    }
}   // automatically closed
```

### Banking App — Step 1: Save accounts to CSV

CSV (Comma-Separated Values) is a simple text format that any program can read:

```
accountNumber,holderName,balance,type,interestRate,overdraftLimit
1001,Ravi Kumar,50000.00,SAVINGS,4.5,0
1002,Priya Sharma,30000.00,CURRENT,0,10000.0
```

**Writing the CSV:**

```java
public static void saveAccountsCSV(String filePath) {
    // Ensure the data directory exists
    File parent = new File(filePath).getParentFile();
    if (parent != null && !parent.exists()) {
        parent.mkdirs();
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        // Header row
        writer.write("accountNumber,holderName,balance,type,interestRate,overdraftLimit");
        writer.newLine();

        // Data rows
        for (Account acc : accounts) {
            StringBuilder sb = new StringBuilder();
            sb.append(acc.getAccountNumber()).append(",");
            sb.append(acc.getHolderName()).append(",");
            sb.append(String.format("%.2f", acc.getBalance())).append(",");

            if (acc instanceof SavingsAccount) {
                SavingsAccount sa = (SavingsAccount) acc;
                sb.append("SAVINGS,");
                sb.append(sa.getInterestRate()).append(",");
                sb.append("0");
            } else if (acc instanceof CurrentAccount) {
                CurrentAccount ca = (CurrentAccount) acc;
                sb.append("CURRENT,");
                sb.append("0,");
                sb.append(ca.getOverdraftLimit());
            }

            writer.write(sb.toString());
            writer.newLine();
        }

        System.out.println("Saved " + accounts.size() + " accounts to " + filePath);
    } catch (IOException e) {
        System.out.println("Error saving: " + e.getMessage());
    }
}
```

### Banking App — Step 2: Load accounts from CSV

```java
public static void loadAccountsCSV(String filePath) {
    File file = new File(filePath);
    if (!file.exists()) {
        System.out.println("No saved data found. Starting fresh.");
        return;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String header = reader.readLine();   // skip header row
        String line;
        int count = 0;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(",");
            if (parts.length < 6) continue;

            long accNo = Long.parseLong(parts[0].trim());
            String name = parts[1].trim();
            double balance = Double.parseDouble(parts[2].trim());
            String type = parts[3].trim();
            double rate = Double.parseDouble(parts[4].trim());
            double limit = Double.parseDouble(parts[5].trim());

            Account acc;
            if (type.equals("SAVINGS")) {
                acc = new SavingsAccount(name, accNo, balance, rate);
            } else {
                acc = new CurrentAccount(name, accNo, balance, limit);
            }

            addAccount(acc);
            count++;
        }

        System.out.println("Loaded " + count + " accounts from " + filePath);
    } catch (IOException e) {
        System.out.println("Error loading: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.out.println("Error parsing data: " + e.getMessage());
    }
}
```

**Usage in main:**

```java
// On startup — load saved data
loadAccountsCSV("data/accounts.csv");
if (accounts.isEmpty()) {
    // First run — add sample data
    addAccount(new SavingsAccount("Ravi Kumar", 1001L, 50000, 4.5));
    addAccount(new CurrentAccount("Priya Sharma", 1002L, 30000, 10000));
}

// ... menu loop ...

// On exit — save data
saveAccountsCSV("data/accounts.csv");
System.out.println("Data saved. Goodbye!");
```

### Banking App — Step 3: Save transactions to CSV

```java
public static void saveTransactionsCSV(String filePath) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        writer.write("timestamp,type,amount,accountNumber,balanceBefore,balanceAfter");
        writer.newLine();

        for (Transaction txn : allTransactions) {
            writer.write(String.format("%s,%s,%.2f,%d,%.2f,%.2f",
                    txn.getTimestamp(), txn.getType(), txn.getAmount(),
                    txn.getAccountNumber(), txn.getBalanceBefore(), txn.getBalanceAfter()));
            writer.newLine();
        }

        System.out.println("Saved " + allTransactions.size() + " transactions.");
    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
    }
}
```

---

## 5. Object Serialization

**Serialization** = convert an object into bytes (save to file).
**Deserialization** = read bytes back into an object.

```
Object ──→ ObjectOutputStream ──→ file.dat (bytes)
file.dat (bytes) ──→ ObjectInputStream ──→ Object
```

### Step 1: Make your class Serializable

Add `implements Serializable` to every class you want to save:

```java
import java.io.Serializable;

public abstract class Account implements Serializable {
    private static final long serialVersionUID = 1L;   // version control
    // ... everything else stays the same ...
}

public class SavingsAccount extends Account {
    private static final long serialVersionUID = 1L;
    // ...
}

public class CurrentAccount extends Account {
    private static final long serialVersionUID = 1L;
    // ...
}
```

- `Serializable` is a **marker interface** — no methods to implement.
- `serialVersionUID` = a version number. If you change the class structure later, this helps Java detect incompatibility.

### Step 2: Save with ObjectOutputStream

```java
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public static void saveAccountsSerialized(String filePath) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
        oos.writeObject(accounts);    // saves the ENTIRE list in one call!
        System.out.println("Serialized " + accounts.size() + " accounts.");
    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
    }
}
```

### Step 3: Load with ObjectInputStream

```java
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.File;

@SuppressWarnings("unchecked")
public static List<Account> loadAccountsSerialized(String filePath) {
    File file = new File(filePath);
    if (!file.exists()) {
        System.out.println("No serialized data found.");
        return new ArrayList<>();
    }

    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
        List<Account> loaded = (List<Account>) ois.readObject();
        System.out.println("Deserialized " + loaded.size() + " accounts.");
        return loaded;
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error: " + e.getMessage());
        return new ArrayList<>();
    }
}
```

**Note**: `readObject()` returns `Object` — you must cast it. The `@SuppressWarnings("unchecked")` silences the compiler warning about the cast.

### CSV vs Serialization

| | CSV | Serialization |
|---|---|---|
| Human-readable? | Yes — open in Notepad/Excel | No — binary gibberish |
| Other languages? | Yes — any language reads CSV | No — Java only |
| Code complexity | Manual parsing (split, parse) | One line: `writeObject` / `readObject` |
| File size | Usually smaller | Can be larger (metadata) |
| Schema changes | Fragile — column order matters | `serialVersionUID` helps |
| Best for | Reports, data exchange | Quick save/load within Java |

---

## 6. transient Keyword

**`transient`** marks a field to be **excluded** from serialization.

```java
public class User implements Serializable {
    private String username;
    private transient String password;    // NOT saved — security!
    private transient int sessionCount;   // NOT saved — temporary
}
```

When deserialized:
- `username` = "ravi" (saved and restored)
- `password` = `null` (transient → gets default value)
- `sessionCount` = `0` (transient → gets default value)

**Use transient when:**
- The field is sensitive (passwords, tokens)
- The field is temporary/computed (cache, session data)
- The field's type is not Serializable (e.g., `Scanner`, database connections)

---

## 7. Exception Handling in File Operations

File I/O can fail for many reasons: file not found, permission denied, disk full, file locked by another program.

### try-with-resources (recommended)

Java 7 introduced **try-with-resources** — resources declared in the `try(...)` are **automatically closed** when the block exits, even if an exception occurs.

```java
// The resource (reader) is auto-closed — no finally block needed
try (BufferedReader reader = new BufferedReader(new FileReader("data.csv"))) {
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
}
// reader is closed here automatically
```

### Multiple resources:

```java
try (
    BufferedReader reader = new BufferedReader(new FileReader("input.csv"));
    BufferedWriter writer = new BufferedWriter(new FileWriter("output.csv"))
) {
    String line;
    while ((line = reader.readLine()) != null) {
        writer.write(line.toUpperCase());
        writer.newLine();
    }
}   // BOTH reader and writer are automatically closed
```

### Without try-with-resources (old way — avoid):

```java
BufferedReader reader = null;
try {
    reader = new BufferedReader(new FileReader("data.csv"));
    // process...
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
} finally {
    if (reader != null) {
        try {
            reader.close();    // closing can ALSO throw!
        } catch (IOException e) {
            // swallow
        }
    }
}
```

The old way is verbose and error-prone. Always use try-with-resources.

---

## Complete Day 7 Code

### FileManager.java (new utility class)

```java
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    // ==================== CSV ====================

    public static void saveAccountsCSV(List<Account> accounts, String filePath) {
        ensureDirectoryExists(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("accountNumber,holderName,balance,type,interestRate,overdraftLimit");
            writer.newLine();

            for (Account acc : accounts) {
                StringBuilder sb = new StringBuilder();
                sb.append(acc.getAccountNumber()).append(",");
                sb.append(acc.getHolderName()).append(",");
                sb.append(String.format("%.2f", acc.getBalance())).append(",");

                if (acc instanceof SavingsAccount) {
                    sb.append("SAVINGS,").append(((SavingsAccount) acc).getInterestRate()).append(",0");
                } else if (acc instanceof CurrentAccount) {
                    sb.append("CURRENT,0,").append(((CurrentAccount) acc).getOverdraftLimit());
                }

                writer.write(sb.toString());
                writer.newLine();
            }
            System.out.println("Saved " + accounts.size() + " accounts to " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving CSV: " + e.getMessage());
        }
    }

    public static List<Account> loadAccountsCSV(String filePath) {
        List<Account> loaded = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("No saved data found at " + filePath);
            return loaded;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();   // skip header
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 6) continue;

                long accNo = Long.parseLong(parts[0].trim());
                String name = parts[1].trim();
                double balance = Double.parseDouble(parts[2].trim());
                String type = parts[3].trim();
                double rate = Double.parseDouble(parts[4].trim());
                double limit = Double.parseDouble(parts[5].trim());

                if (type.equals("SAVINGS")) {
                    loaded.add(new SavingsAccount(name, accNo, balance, rate));
                } else if (type.equals("CURRENT")) {
                    loaded.add(new CurrentAccount(name, accNo, balance, limit));
                }
            }
            System.out.println("Loaded " + loaded.size() + " accounts from " + filePath);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading CSV: " + e.getMessage());
        }
        return loaded;
    }

    public static void saveTransactionsCSV(List<Transaction> transactions, String filePath) {
        ensureDirectoryExists(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("timestamp,type,amount,accountNumber,balanceBefore,balanceAfter");
            writer.newLine();

            for (Transaction txn : transactions) {
                writer.write(String.format("%s,%s,%.2f,%d,%.2f,%.2f",
                        txn.getTimestamp(), txn.getType(), txn.getAmount(),
                        txn.getAccountNumber(), txn.getBalanceBefore(), txn.getBalanceAfter()));
                writer.newLine();
            }
            System.out.println("Saved " + transactions.size() + " transactions to " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    // ==================== SERIALIZATION ====================

    public static void serializeAccounts(List<Account> accounts, String filePath) {
        ensureDirectoryExists(filePath);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(accounts);
            System.out.println("Serialized " + accounts.size() + " accounts to " + filePath);
        } catch (IOException e) {
            System.out.println("Error serializing: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Account> deserializeAccounts(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("No serialized data at " + filePath);
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            List<Account> loaded = (List<Account>) ois.readObject();
            System.out.println("Deserialized " + loaded.size() + " accounts.");
            return loaded;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error deserializing: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // ==================== HELPERS ====================

    private static void ensureDirectoryExists(String filePath) {
        File parent = new File(filePath).getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }
}
```

### Make classes Serializable

Add to **Account.java**, **SavingsAccount.java**, **CurrentAccount.java**, and **Transaction.java**:

```java
import java.io.Serializable;

// Account.java
public abstract class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    // ... rest unchanged ...
}

// SavingsAccount.java
public class SavingsAccount extends Account {
    private static final long serialVersionUID = 1L;
    // ... rest unchanged ...
}

// CurrentAccount.java
public class CurrentAccount extends Account {
    private static final long serialVersionUID = 1L;
    // ... rest unchanged ...
}

// Transaction.java
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    // ... rest unchanged ...
}
```

### Updated BankApp.java — auto save/load

```java
public class BankApp {

    static final String ACCOUNTS_CSV = "data/accounts.csv";
    static final String TRANSACTIONS_CSV = "data/transactions.csv";

    // ... existing fields ...

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // === LOAD ON STARTUP ===
        List<Account> loaded = FileManager.loadAccountsCSV(ACCOUNTS_CSV);
        if (loaded.isEmpty()) {
            System.out.println("No saved data. Loading sample accounts...");
            addAccount(new SavingsAccount("Ravi Kumar", 1001L, 50000, 4.5));
            addAccount(new CurrentAccount("Priya Sharma", 1002L, 30000, 10000));
            addAccount(new SavingsAccount("Amit Verma", 1003L, 75000, 5.0));
            addAccount(new CurrentAccount("Sneha Reddy", 1004L, 120000, 25000));
        } else {
            for (Account acc : loaded) {
                addAccount(acc);
            }
        }

        int choice = 0;
        do {
            displayMenu();
            try { choice = sc.nextInt(); }
            catch (InputMismatchException e) { System.out.println("Enter a number."); sc.nextLine(); continue; }

            switch (choice) {
                // ... existing cases 1-9 ...

                case 10:
                    // Manual save
                    FileManager.saveAccountsCSV(accounts, ACCOUNTS_CSV);
                    FileManager.saveTransactionsCSV(allTransactions, TRANSACTIONS_CSV);
                    break;

                case 11:
                    // === SAVE ON EXIT ===
                    FileManager.saveAccountsCSV(accounts, ACCOUNTS_CSV);
                    FileManager.saveTransactionsCSV(allTransactions, TRANSACTIONS_CSV);
                    System.out.println("Data saved. Thank you for banking with us!");
                    break;

                default: System.out.println("Invalid choice.");
            }
        } while (choice != 11);

        sc.close();
    }

    static void displayMenu() {
        System.out.println("\n===== BANK APPLICATION (Day 7) =====");
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
        System.out.println("10. Save Data");
        System.out.println("11. Exit");
        System.out.print("Enter choice: ");
    }

    // ... rest of methods unchanged ...
}
```

### Test it!

| Step | Action | What to verify |
|------|--------|---------------|
| 1 | First run | "No saved data. Loading sample accounts..." |
| 2 | Deposit Rs.5000 to Ravi | Balance changes to 55000 |
| 3 | Choose "Exit" (option 11) | "Data saved" message, check `data/accounts.csv` exists |
| 4 | Open `data/accounts.csv` in a text editor | See CSV with updated balance |
| 5 | Run the app again | "Loaded 4 accounts" — Ravi's balance is 55000! |

---

## Exercises

### Exercise 1: Append Transactions

**Problem**: The current `saveTransactionsCSV` overwrites the file each time. Modify it to **append** new transactions. Hint: `new FileWriter(path, true)` opens in append mode. Handle the header: only write it if the file doesn't exist yet.

<details>
<summary><strong>Solution</strong></summary>

```java
public static void appendTransactionsCSV(List<Transaction> newTransactions, String filePath) {
    ensureDirectoryExists(filePath);
    File file = new File(filePath);
    boolean writeHeader = !file.exists() || file.length() == 0;

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
        if (writeHeader) {
            writer.write("timestamp,type,amount,accountNumber,balanceBefore,balanceAfter");
            writer.newLine();
        }
        for (Transaction txn : newTransactions) {
            writer.write(String.format("%s,%s,%.2f,%d,%.2f,%.2f",
                    txn.getTimestamp(), txn.getType(), txn.getAmount(),
                    txn.getAccountNumber(), txn.getBalanceBefore(), txn.getBalanceAfter()));
            writer.newLine();
        }
        System.out.println("Appended " + newTransactions.size() + " transactions.");
    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
    }
}
```
</details>

---

### Exercise 2: Load Transactions from CSV

**Problem**: Write `loadTransactionsCSV(String filePath)` that reads the transactions CSV and returns a `List<Transaction>`. Also rebuild the `transactionsByAccount` map.

<details>
<summary><strong>Solution</strong></summary>

```java
public static List<Transaction> loadTransactionsCSV(String filePath) {
    List<Transaction> loaded = new ArrayList<>();
    File file = new File(filePath);
    if (!file.exists()) return loaded;

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        reader.readLine();   // skip header
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(",");
            if (parts.length < 6) continue;

            String timestamp = parts[0].trim();
            String type = parts[1].trim();
            double amount = Double.parseDouble(parts[2].trim());
            long accNo = Long.parseLong(parts[3].trim());
            double balBefore = Double.parseDouble(parts[4].trim());
            double balAfter = Double.parseDouble(parts[5].trim());

            Transaction txn = new Transaction(type, amount, accNo, balBefore, balAfter);
            loaded.add(txn);
        }
        System.out.println("Loaded " + loaded.size() + " transactions.");
    } catch (IOException | NumberFormatException e) {
        System.out.println("Error: " + e.getMessage());
    }
    return loaded;
}

// Rebuild the map:
// for (Transaction txn : loadedTransactions) {
//     transactionsByAccount
//         .computeIfAbsent(txn.getAccountNumber(), k -> new ArrayList<>())
//         .add(txn);
// }
```
</details>

---

### Exercise 3: Export Account Statement

**Problem**: Write `exportStatement(long accountNumber, String filePath)` that creates a text file like a real bank statement:

```
═══════════════════════════════════════════
          SIMPLE BANK — ACCOUNT STATEMENT
═══════════════════════════════════════════
Account Number : 1001
Account Holder : Ravi Kumar
Statement Date : 2026-02-21

───────────────────────────────────────────
Date          Type        Amount    Balance
───────────────────────────────────────────
2026-02-21    DEPOSIT     5000.00   55000.00
2026-02-21    WITHDRAW    2000.00   53000.00
───────────────────────────────────────────
Closing Balance: Rs.53000.00
═══════════════════════════════════════════
```

<details>
<summary><strong>Solution</strong></summary>

```java
public static void exportStatement(long accountNumber, String filePath) {
    Account acc = accountMap.get(accountNumber);
    if (acc == null) {
        System.out.println("Account not found.");
        return;
    }

    List<Transaction> txns = transactionsByAccount.getOrDefault(accountNumber, new ArrayList<>());

    try (BufferedWriter w = new BufferedWriter(new FileWriter(filePath))) {
        String sep = "═".repeat(50);
        String line = "─".repeat(50);

        w.write(sep); w.newLine();
        w.write("          SIMPLE BANK — ACCOUNT STATEMENT"); w.newLine();
        w.write(sep); w.newLine();
        w.write("Account Number : " + acc.getAccountNumber()); w.newLine();
        w.write("Account Holder : " + acc.getHolderName()); w.newLine();
        w.write("Statement Date : " + java.time.LocalDate.now()); w.newLine();
        w.newLine();
        w.write(line); w.newLine();
        w.write(String.format("%-14s %-12s %10s %10s", "Date", "Type", "Amount", "Balance"));
        w.newLine();
        w.write(line); w.newLine();

        for (Transaction txn : txns) {
            String date = txn.getTimestamp().substring(0, 10);
            w.write(String.format("%-14s %-12s %10.2f %10.2f",
                    date, txn.getType(), txn.getAmount(), txn.getBalanceAfter()));
            w.newLine();
        }

        w.write(line); w.newLine();
        w.write(String.format("Closing Balance: Rs.%.2f", acc.getBalance())); w.newLine();
        w.write(sep); w.newLine();

        System.out.println("Statement exported to " + filePath);
    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
    }
}
```
</details>

---

### Exercise 4: CSV vs Serialization Comparison

**Problem**: Save the same 4 accounts using both methods. Then compare:

1. File size of `accounts.csv` vs `accounts.dat`
2. Open both in a text editor — what do you see?
3. Which required more code to implement?
4. Add a comment block at the top of your test class with your observations

<details>
<summary><strong>Solution</strong></summary>

```java
public static void compareFormats() {
    // Save both ways
    FileManager.saveAccountsCSV(accounts, "data/accounts.csv");
    FileManager.serializeAccounts(accounts, "data/accounts.dat");

    // Compare sizes
    File csvFile = new File("data/accounts.csv");
    File datFile = new File("data/accounts.dat");

    System.out.println("\n=== FORMAT COMPARISON ===");
    System.out.println("CSV file size: " + csvFile.length() + " bytes");
    System.out.println("DAT file size: " + datFile.length() + " bytes");
    System.out.println();
    System.out.println("CSV: Human-readable, any language can parse, manual parsing code");
    System.out.println("DAT: Binary (not readable), Java-only, one line to save/load");
}

/*
 * OBSERVATIONS:
 * - CSV is typically smaller (text vs binary with metadata)
 * - CSV is readable in any text editor or spreadsheet
 * - Serialization (.dat) is unreadable binary
 * - Serialization requires much less code (writeObject/readObject)
 * - CSV is portable across languages; .dat is Java-only
 * - CSV requires manual parsing (split, parse); serialization is automatic
 * - For data exchange with other systems → CSV
 * - For quick Java-only persistence → Serialization
 */
```
</details>

---

### Exercise 5: Auto-Backup (Challenge)

**Problem**: Add auto-backup functionality:
- After every 5 transactions, automatically save a backup to `data/backups/backup_<timestamp>.csv`
- Keep only the 3 most recent backups (delete older ones)
- On startup, if `accounts.csv` is missing or empty, offer to load from the latest backup

<details>
<summary><strong>Solution</strong></summary>

```java
static int transactionsSinceBackup = 0;
static final int BACKUP_INTERVAL = 5;
static final int MAX_BACKUPS = 3;
static final String BACKUP_DIR = "data/backups";

// Call after each transaction:
static void checkAutoBackup() {
    transactionsSinceBackup++;
    if (transactionsSinceBackup >= BACKUP_INTERVAL) {
        performBackup();
        transactionsSinceBackup = 0;
    }
}

static void performBackup() {
    File dir = new File(BACKUP_DIR);
    if (!dir.exists()) dir.mkdirs();

    String timestamp = java.time.LocalDateTime.now()
            .toString().replace(":", "-").replace(".", "-");
    String backupPath = BACKUP_DIR + "/backup_" + timestamp + ".csv";

    FileManager.saveAccountsCSV(accounts, backupPath);
    System.out.println("Auto-backup created: " + backupPath);

    cleanOldBackups(dir);
}

static void cleanOldBackups(File dir) {
    File[] backups = dir.listFiles((d, name) -> name.startsWith("backup_") && name.endsWith(".csv"));
    if (backups == null || backups.length <= MAX_BACKUPS) return;

    // Sort by last modified (oldest first)
    java.util.Arrays.sort(backups, (a, b) -> Long.compare(a.lastModified(), b.lastModified()));

    // Delete oldest until only MAX_BACKUPS remain
    for (int i = 0; i < backups.length - MAX_BACKUPS; i++) {
        backups[i].delete();
        System.out.println("Deleted old backup: " + backups[i].getName());
    }
}

static String findLatestBackup() {
    File dir = new File(BACKUP_DIR);
    if (!dir.exists()) return null;

    File[] backups = dir.listFiles((d, name) -> name.startsWith("backup_") && name.endsWith(".csv"));
    if (backups == null || backups.length == 0) return null;

    java.util.Arrays.sort(backups, (a, b) -> Long.compare(b.lastModified(), a.lastModified()));
    return backups[0].getAbsolutePath();
}
```
</details>

---

## Quick Quiz

1. What is the difference between in-memory data and persistent data?
2. What is the difference between byte streams and character streams? When would you use each?
3. Why use BufferedReader instead of FileReader directly?
4. What does `readLine()` return at the end of the file?
5. What interface must a class implement to be serializable?
6. What does the `transient` keyword do? Give an example of when you'd use it.
7. What is the advantage of try-with-resources over try-finally?
8. When would you choose CSV over serialization?

<details>
<summary><strong>Answers</strong></summary>

1. **In-memory** data lives only during program execution (lost on exit). **Persistent** data is saved to disk (files or database) and survives program restart.

2. **Byte streams** (InputStream/OutputStream) handle raw bytes — used for binary files like images or serialized objects. **Character streams** (Reader/Writer) handle text with encoding awareness — used for text files like CSV, config files, logs.

3. FileReader reads **one character at a time** (slow). BufferedReader uses an 8KB internal buffer and provides `readLine()` which reads a full line at once — much more efficient.

4. `null` — this is how you detect the end of the file in a while loop: `while ((line = reader.readLine()) != null)`.

5. **`java.io.Serializable`** — a marker interface with no methods to implement. Just `implements Serializable` is enough.

6. `transient` excludes a field from serialization. When deserialized, transient fields get their default value (null, 0, false). Use for: sensitive data (passwords), non-serializable types (Scanner, connections), or temporary computed values.

7. **try-with-resources** automatically closes resources when the block exits — no manual `close()` in finally, no risk of forgetting. Cleaner, shorter, and handles the case where `close()` itself throws.

8. **CSV**: when data needs to be human-readable, shared with other programs/languages, imported into spreadsheets, or used in reports. **Serialization**: when you need quick Java-only persistence, want to save complex object graphs automatically, or don't need human readability.
</details>

---

## What We Built Today

| Step | Feature | Technique | Key Classes |
|------|---------|-----------|-------------|
| Step 1 | Save accounts to CSV | Buffered character stream | BufferedWriter |
| Step 2 | Load accounts from CSV | Buffered character stream | BufferedReader |
| Step 3 | Save transactions to CSV | Buffered character stream | BufferedWriter |
| Step 4 | Make Account serializable | Marker interface | Serializable |
| Step 5 | Save/load via serialization | Object streams | ObjectOutputStream/InputStream |
| All | Clean resource handling | try-with-resources | AutoCloseable |

### BankApp now persists data!

```
First run:  No saved data → sample accounts loaded → user works → saves on exit
Second run: Loads from CSV → user sees their data from last session → continues working
```

### New file: FileManager.java

A dedicated utility class for all file operations — keeps BankApp clean.

## What's Next (Day 8 Preview)

Files work, but they're limited:
- No way to query ("show all accounts with balance > 50000") without loading everything
- No concurrent access (two programs can't safely use the same file)
- No relationships between data

Tomorrow we start **REST API & Spring Boot** — we'll expose our banking operations as HTTP endpoints that any client (web browser, mobile app, Postman) can call.
