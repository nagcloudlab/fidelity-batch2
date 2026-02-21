# Day 12 — JDBC Integration

## Student Handout

> **What you'll build today**: Replace the in-memory storage (ArrayList + HashMap) in your banking app with a real database using JDBC. Your REST API will store accounts and transactions in an H2 database that persists data and supports SQL queries.

---

## 1. Why JDBC?

Our banking app's data layer has evolved:

| Day | Storage | Problem |
|-----|---------|---------|
| Day 1-5 | In-memory (ArrayList + HashMap) | Lost on exit |
| Day 7 | Files (CSV, Serialization) | Hard to query, no concurrency |
| Day 8 | Still in-memory (REST API wrapper) | Still lost on restart |
| **Day 12** | **Database (via JDBC)** | **Persists, queryable, concurrent** |

**JDBC** (Java Database Connectivity) is the standard Java API for communicating with databases. It's the bridge between your Java code and SQL:

```
Java Code                    JDBC                     Database
──────────                ──────────                ──────────
AccountService      →     Connection           →    accounts table
  .deposit()              PreparedStatement          UPDATE balance
  .getAll()               ResultSet             ←   rows of data
```

---

## 2. JDBC Architecture

### The four key components

| Component | Class/Interface | Purpose |
|-----------|----------------|---------|
| **Driver** | `java.sql.Driver` | Translates JDBC calls to database protocol |
| **Connection** | `java.sql.Connection` | An active session with the database |
| **PreparedStatement** | `java.sql.PreparedStatement` | Sends SQL queries with parameters |
| **ResultSet** | `java.sql.ResultSet` | Holds the rows returned by SELECT |

```
┌──────────────────────────────────┐
│ Java Application                  │
│   DriverManager.getConnection()   │
│   PreparedStatement ps = ...      │
│   ResultSet rs = ps.executeQuery()│
└──────────────┬───────────────────┘
               │ JDBC API (java.sql.*)
               ▼
┌──────────────────────────────────┐
│ JDBC Driver (H2 / MySQL / etc.)  │
└──────────────┬───────────────────┘
               │ Database protocol
               ▼
┌──────────────────────────────────┐
│ Database (H2 / MySQL / PostgreSQL)│
└──────────────────────────────────┘
```

### JDBC URL

The URL tells Java which database to connect to:

```
jdbc:h2:mem:bankdb                        ← H2 in-memory
jdbc:mysql://localhost:3306/bankdb        ← MySQL
jdbc:postgresql://localhost:5432/bankdb   ← PostgreSQL
```

For training, we use **H2** (in-memory, zero installation):

```xml
<!-- pom.xml dependency -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

---

## 3. JDBC Workflow

Every JDBC operation follows this pattern:

```
1. Get a Connection
2. Create a PreparedStatement with SQL
3. Set parameters (if any)
4. Execute the statement
5. Process the results (for SELECT)
6. Close resources (automatic with try-with-resources)
```

### Complete example

```java
import java.sql.*;

public class JdbcDemo {
    public static void main(String[] args) {
        String url = "jdbc:h2:mem:bankdb";
        String user = "sa";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            // Create table
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("""
                    CREATE TABLE accounts (
                        account_number BIGINT PRIMARY KEY,
                        holder_name VARCHAR(100) NOT NULL,
                        balance DOUBLE DEFAULT 0,
                        type VARCHAR(20) NOT NULL,
                        active BOOLEAN DEFAULT TRUE
                    )
                """);
                System.out.println("Table created.");
            }

            // Insert an account
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO accounts (account_number, holder_name, balance, type) VALUES (?, ?, ?, ?)")) {
                ps.setLong(1, 1001);
                ps.setString(2, "Ravi Kumar");
                ps.setDouble(3, 50000);
                ps.setString(4, "SAVINGS");
                ps.executeUpdate();
                System.out.println("Inserted Ravi.");
            }

            // Query all accounts
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM accounts")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    System.out.println(
                        rs.getLong("account_number") + " | " +
                        rs.getString("holder_name") + " | " +
                        rs.getDouble("balance")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
```

### Key execute methods

| Method | Use for | Returns |
|--------|---------|---------|
| `ps.executeQuery()` | SELECT (reading data) | `ResultSet` (rows of data) |
| `ps.executeUpdate()` | INSERT, UPDATE, DELETE (modifying data) | `int` (number of rows affected) |
| `stmt.execute()` | CREATE TABLE, DROP, DDL | `boolean` |

### Reading from ResultSet

```java
ResultSet rs = ps.executeQuery();

while (rs.next()) {                              // move to next row; false = no more rows
    long accNo = rs.getLong("account_number");    // by column name (preferred)
    String name = rs.getString("holder_name");
    double balance = rs.getDouble("balance");
    boolean active = rs.getBoolean("active");
}
```

`ResultSet` is like a cursor — `next()` advances to the next row. It returns `false` when there are no more rows.

---

## 4. PreparedStatement vs Statement

### The SQL injection problem

```java
// DANGEROUS — Never do this!
String name = userInput;    // User enters: ' OR '1'='1
String sql = "SELECT * FROM accounts WHERE holder_name = '" + name + "'";
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(sql);

// Actual SQL that runs:
// SELECT * FROM accounts WHERE holder_name = '' OR '1'='1'
// This returns ALL accounts — a security breach!
```

### The safe way: PreparedStatement

```java
// SAFE — parameters are escaped automatically
String sql = "SELECT * FROM accounts WHERE holder_name = ?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, userInput);    // ? is replaced safely — special characters are escaped
ResultSet rs = ps.executeQuery();
```

### Comparison

| | Statement | PreparedStatement |
|---|---|---|
| SQL injection | **Vulnerable** | **Safe** (auto-escapes parameters) |
| Performance | Parsed every time | Parsed once, reused |
| Parameters | String concatenation (messy, risky) | `?` placeholders + type-safe setters |
| Rule | **Never use with user input** | **Always use this** |

### Parameter indices are 1-based

```java
// SQL:    INSERT INTO accounts (account_number, holder_name, balance, type) VALUES (?, ?, ?, ?)
// Index:                                                                            1  2  3  4

ps.setLong(1, 1001);           // first ?
ps.setString(2, "Ravi Kumar"); // second ?
ps.setDouble(3, 50000);        // third ?
ps.setString(4, "SAVINGS");    // fourth ?
```

---

## 5. Banking App with JDBC

### Step 1: DatabaseManager (connection helper)

```java
import java.sql.*;

public class DatabaseManager {

    private static final String URL = "jdbc:h2:mem:bankdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Create accounts table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS accounts (
                    account_number BIGINT PRIMARY KEY,
                    holder_name VARCHAR(100) NOT NULL,
                    balance DOUBLE DEFAULT 0,
                    type VARCHAR(20) NOT NULL,
                    active BOOLEAN DEFAULT TRUE
                )
            """);

            // Create transactions table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS transactions (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    type VARCHAR(20) NOT NULL,
                    amount DOUBLE NOT NULL,
                    account_number BIGINT NOT NULL,
                    balance_before DOUBLE,
                    balance_after DOUBLE,
                    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (account_number) REFERENCES accounts(account_number)
                )
            """);

            // Insert sample data if tables are empty
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM accounts");
            rs.next();
            if (rs.getInt(1) == 0) {
                stmt.executeUpdate("""
                    INSERT INTO accounts (account_number, holder_name, balance, type) VALUES
                    (1001, 'Ravi Kumar', 50000, 'SAVINGS'),
                    (1002, 'Priya Sharma', 30000, 'CURRENT'),
                    (1003, 'Amit Verma', 75000, 'SAVINGS')
                """);
                System.out.println("Sample data loaded.");
            }

            System.out.println("Database initialized.");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }
}
```

**Note**: `DB_CLOSE_DELAY=-1` keeps the H2 in-memory database alive as long as the JVM runs. Without it, the database is destroyed when the last connection closes.

### Step 2: AccountRepository (JDBC CRUD operations)

```java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {

    // ──── CREATE ────

    public Account insert(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (account_number, holder_name, balance, type) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, account.getAccountNumber());
            ps.setString(2, account.getHolderName());
            ps.setDouble(3, account.getBalance());
            ps.setString(4, account.getType());
            ps.executeUpdate();
            return account;
        }
    }

    // ──── READ (all) ────

    public List<Account> findAll() throws SQLException {
        String sql = "SELECT * FROM accounts ORDER BY account_number";
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                accounts.add(mapRowToAccount(rs));
            }
        }
        return accounts;
    }

    // ──── READ (by account number) ────

    public Account findByAccountNumber(long accountNumber) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, accountNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRowToAccount(rs);
            }
            return null;    // not found
        }
    }

    // ──── UPDATE (balance only) ────

    public void updateBalance(long accountNumber, double newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newBalance);
            ps.setLong(2, accountNumber);
            ps.executeUpdate();
        }
    }

    // ──── UPDATE (full) ────

    public Account update(long accountNumber, Account updated) throws SQLException {
        String sql = "UPDATE accounts SET holder_name = ?, type = ? WHERE account_number = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, updated.getHolderName());
            ps.setString(2, updated.getType());
            ps.setLong(3, accountNumber);

            int rows = ps.executeUpdate();
            if (rows == 0) return null;    // not found
            return findByAccountNumber(accountNumber);
        }
    }

    // ──── DELETE ────

    public boolean delete(long accountNumber) throws SQLException {
        String sql = "DELETE FROM accounts WHERE account_number = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, accountNumber);
            return ps.executeUpdate() > 0;    // true if a row was deleted
        }
    }

    // ──── SEARCH (by name) ────

    public List<Account> searchByName(String keyword) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE LOWER(holder_name) LIKE ?";
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.toLowerCase() + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                accounts.add(mapRowToAccount(rs));
            }
        }
        return accounts;
    }

    // ──── FILTER (by type) ────

    public List<Account> filterByType(String type) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE UPPER(type) = ?";
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, type.toUpperCase());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                accounts.add(mapRowToAccount(rs));
            }
        }
        return accounts;
    }

    // ──── HELPER ────

    private Account mapRowToAccount(ResultSet rs) throws SQLException {
        Account acc = new Account(
            rs.getLong("account_number"),
            rs.getString("holder_name"),
            rs.getDouble("balance"),
            rs.getString("type")
        );
        acc.setActive(rs.getBoolean("active"));
        return acc;
    }
}
```

### The pattern in every method

```
1. Define SQL with ? placeholders
2. try-with-resources: Connection + PreparedStatement
3. Set parameters: ps.setXxx(index, value)
4. Execute:
   - SELECT → executeQuery() → process ResultSet
   - INSERT/UPDATE/DELETE → executeUpdate() → check rows affected
5. Resources auto-closed
```

### Step 3: Updated AccountService

The service now delegates to the repository. The controller doesn't change at all.

```java
@Service
public class AccountService {

    private final AccountRepository repository = new AccountRepository();

    public AccountService() {
        DatabaseManager.initializeDatabase();
    }

    public List<Account> getAllAccounts() {
        try {
            return repository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching accounts", e);
        }
    }

    public Account getByAccountNumber(long accountNumber) {
        try {
            return repository.findByAccountNumber(accountNumber);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching account", e);
        }
    }

    public Account addAccount(Account account) {
        try {
            if (repository.findByAccountNumber(account.getAccountNumber()) != null) {
                return null;    // duplicate
            }
            account.setActive(true);
            return repository.insert(account);
        } catch (SQLException e) {
            throw new RuntimeException("Error creating account", e);
        }
    }

    public Account deposit(long accountNumber, double amount) {
        try {
            Account acc = repository.findByAccountNumber(accountNumber);
            if (acc == null) return null;
            if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");

            double newBalance = acc.getBalance() + amount;
            repository.updateBalance(accountNumber, newBalance);
            acc.setBalance(newBalance);
            return acc;
        } catch (SQLException e) {
            throw new RuntimeException("Error depositing", e);
        }
    }

    public Account withdraw(long accountNumber, double amount) {
        try {
            Account acc = repository.findByAccountNumber(accountNumber);
            if (acc == null) return null;
            if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
            if (amount > acc.getBalance()) throw new IllegalArgumentException("Insufficient balance");

            double newBalance = acc.getBalance() - amount;
            repository.updateBalance(accountNumber, newBalance);
            acc.setBalance(newBalance);
            return acc;
        } catch (SQLException e) {
            throw new RuntimeException("Error withdrawing", e);
        }
    }

    public Account updateAccount(long accountNumber, Account updated) {
        try {
            return repository.update(accountNumber, updated);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating account", e);
        }
    }

    public boolean deleteAccount(long accountNumber) {
        try {
            return repository.delete(accountNumber);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting account", e);
        }
    }

    public List<Account> searchByName(String keyword) {
        try {
            return repository.searchByName(keyword);
        } catch (SQLException e) {
            throw new RuntimeException("Error searching", e);
        }
    }

    public List<Account> filterByType(String type) {
        try {
            return repository.filterByType(type);
        } catch (SQLException e) {
            throw new RuntimeException("Error filtering", e);
        }
    }
}
```

### What changed?

| Layer | Before (Day 8) | After (Day 12) | Changed? |
|-------|----------------|-----------------|----------|
| HTML/JS (front-end) | fetch() → API | fetch() → API | **No change** |
| AccountController | @RestController endpoints | Same endpoints | **No change** |
| AccountService | Used ArrayList + HashMap | Delegates to repository | **Minor changes** |
| AccountRepository | Did not exist | JDBC CRUD operations | **New** |
| DatabaseManager | Did not exist | Connection + initialization | **New** |
| Database | Did not exist | H2 in-memory | **New** |

The controller and front-end are completely untouched. That's the benefit of layered architecture — you can swap the data layer without affecting the rest.

---

## Exercises

### Exercise 1: Transaction Repository

**Problem**: Create a `TransactionRepository` with these JDBC methods:
- `insert(Transaction txn)` — insert a transaction into the transactions table
- `findByAccountNumber(long accNo)` — find all transactions for an account, ordered by timestamp (newest first)
- `findAll()` — list all transactions

Then update `AccountService.deposit()` and `withdraw()` to automatically record a transaction.

<details>
<summary><strong>Solution</strong></summary>

```java
public class TransactionRepository {

    public void insert(String type, double amount, long accountNumber,
                       double balBefore, double balAfter) throws SQLException {
        String sql = """
            INSERT INTO transactions (type, amount, account_number, balance_before, balance_after)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, type);
            ps.setDouble(2, amount);
            ps.setLong(3, accountNumber);
            ps.setDouble(4, balBefore);
            ps.setDouble(5, balAfter);
            ps.executeUpdate();
        }
    }

    public List<Map<String, Object>> findByAccountNumber(long accountNumber) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY timestamp DESC";
        List<Map<String, Object>> transactions = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, accountNumber);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> txn = new HashMap<>();
                txn.put("id", rs.getLong("id"));
                txn.put("type", rs.getString("type"));
                txn.put("amount", rs.getDouble("amount"));
                txn.put("accountNumber", rs.getLong("account_number"));
                txn.put("balanceBefore", rs.getDouble("balance_before"));
                txn.put("balanceAfter", rs.getDouble("balance_after"));
                txn.put("timestamp", rs.getTimestamp("timestamp").toString());
                transactions.add(txn);
            }
        }
        return transactions;
    }
}
```

**Updated deposit in AccountService:**

```java
private final TransactionRepository txnRepo = new TransactionRepository();

public Account deposit(long accountNumber, double amount) {
    try {
        Account acc = repository.findByAccountNumber(accountNumber);
        if (acc == null) return null;
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");

        double before = acc.getBalance();
        double after = before + amount;
        repository.updateBalance(accountNumber, after);
        txnRepo.insert("DEPOSIT", amount, accountNumber, before, after);

        acc.setBalance(after);
        return acc;
    } catch (SQLException e) {
        throw new RuntimeException("Error depositing", e);
    }
}
```
</details>

---

### Exercise 2: Account Exists Check

**Problem**: Write a JDBC method `boolean accountExists(long accNo)` that uses `SELECT COUNT(*) FROM accounts WHERE account_number = ?` and returns true/false. This is more efficient than `findByAccountNumber()` when you only need to check existence (not fetch all columns).

<details>
<summary><strong>Solution</strong></summary>

```java
public boolean accountExists(long accountNumber) throws SQLException {
    String sql = "SELECT COUNT(*) FROM accounts WHERE account_number = ?";

    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setLong(1, accountNumber);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }
}
```

**Usage in addAccount:**

```java
public Account addAccount(Account account) {
    try {
        if (repository.accountExists(account.getAccountNumber())) {
            return null;    // duplicate — without fetching the whole account
        }
        account.setActive(true);
        return repository.insert(account);
    } catch (SQLException e) {
        throw new RuntimeException("Error creating account", e);
    }
}
```
</details>

---

### Exercise 3: Batch Insert

**Problem**: Write a method `insertBatch(List<Account> accounts)` that inserts multiple accounts in one database round-trip using `addBatch()` and `executeBatch()`.

<details>
<summary><strong>Solution</strong></summary>

```java
public int insertBatch(List<Account> accounts) throws SQLException {
    String sql = "INSERT INTO accounts (account_number, holder_name, balance, type) VALUES (?, ?, ?, ?)";

    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        for (Account acc : accounts) {
            ps.setLong(1, acc.getAccountNumber());
            ps.setString(2, acc.getHolderName());
            ps.setDouble(3, acc.getBalance());
            ps.setString(4, acc.getType());
            ps.addBatch();    // add to batch (doesn't execute yet)
        }

        int[] results = ps.executeBatch();    // execute all at once
        return results.length;
    }
}
```

**Why batch?** If you have 100 accounts, individual inserts make 100 round-trips to the database. Batch insert makes just 1 round-trip. Much faster.
</details>

---

### Exercise 4: SQL Injection Demo

**Problem**: Write two versions of `findByName(String name)`:
1. **Unsafe** version using `Statement` + string concatenation
2. **Safe** version using `PreparedStatement`

Test with the input: `' OR '1'='1`

<details>
<summary><strong>Solution</strong></summary>

```java
// UNSAFE — vulnerable to SQL injection
public List<Account> findByNameUnsafe(String name) throws SQLException {
    String sql = "SELECT * FROM accounts WHERE holder_name = '" + name + "'";
    // If name = "' OR '1'='1"
    // SQL becomes: SELECT * FROM accounts WHERE holder_name = '' OR '1'='1'
    // This returns ALL accounts!

    List<Account> accounts = new ArrayList<>();
    try (Connection conn = DatabaseManager.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            accounts.add(mapRowToAccount(rs));
        }
    }
    System.out.println("UNSAFE: Found " + accounts.size() + " accounts");
    return accounts;
}

// SAFE — PreparedStatement escapes the input
public List<Account> findByNameSafe(String name) throws SQLException {
    String sql = "SELECT * FROM accounts WHERE holder_name = ?";

    List<Account> accounts = new ArrayList<>();
    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, name);    // "' OR '1'='1" is treated as a literal string
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            accounts.add(mapRowToAccount(rs));
        }
    }
    System.out.println("SAFE: Found " + accounts.size() + " accounts");
    return accounts;
}

// Test:
// findByNameUnsafe("' OR '1'='1");  → returns ALL accounts (BREACH!)
// findByNameSafe("' OR '1'='1");    → returns 0 accounts (correct)
```
</details>

---

## Quick Quiz

1. What does JDBC stand for?
2. Name the four main JDBC components (classes/interfaces).
3. What is the difference between `executeQuery()` and `executeUpdate()`?
4. Why should you always use PreparedStatement instead of Statement?
5. What does `rs.next()` return when there are no more rows?
6. Are JDBC parameter indices 0-based or 1-based?
7. Why do we use try-with-resources for Connection, PreparedStatement, and ResultSet?
8. What changed in AccountController when we switched from in-memory to JDBC?

<details>
<summary><strong>Answers</strong></summary>

1. **Java Database Connectivity** — the standard Java API for communicating with relational databases.

2. **(1) Driver** — translates JDBC calls to database protocol. **(2) Connection** — active session with the database. **(3) PreparedStatement** — sends parameterized SQL queries. **(4) ResultSet** — holds rows returned by SELECT queries.

3. **`executeQuery()`** is for SELECT statements — it returns a `ResultSet` with the rows. **`executeUpdate()`** is for INSERT, UPDATE, DELETE — it returns an `int` (number of rows affected). Using the wrong one causes an error.

4. **PreparedStatement prevents SQL injection** — parameters are automatically escaped. It's also faster (parsed once, reused) and cleaner (no string concatenation). There is never a valid reason to use Statement with user-provided data.

5. **`false`** — this is how you detect the end of results: `while (rs.next()) { ... }`. When `next()` returns false, the loop ends.

6. **1-based**. The first `?` is index 1, not 0. `ps.setString(1, "Ravi")` sets the first parameter.

7. **Connection**, **PreparedStatement**, and **ResultSet** all hold database resources (network sockets, memory). If not closed properly, you get **resource leaks** — eventually the database runs out of connections. Try-with-resources guarantees they are closed even if an exception occurs.

8. **Nothing changed.** The controller remained identical because it only talks to the service layer. The service handles the switch from in-memory to JDBC internally. This is the benefit of **separation of concerns** — changing one layer doesn't affect others.
</details>

---

## What We Built Today

| Step | What we built | Key Classes |
|------|--------------|-------------|
| DatabaseManager | Connection helper + table initialization | `DriverManager`, `Connection`, `Statement` |
| AccountRepository | All CRUD operations with JDBC | `PreparedStatement`, `ResultSet` |
| Updated AccountService | Delegates to repository | Same public API, new internal implementation |
| Security | SQL injection prevention | PreparedStatement with `?` placeholders |

### Architecture with JDBC

```
Browser (HTML/JS)  →  AccountController  →  AccountService  →  AccountRepository  →  Database
                      @RestController       @Service            JDBC calls           H2
                      (unchanged)           (delegates)         (new)                (new)
```

### Full-stack banking app — complete!

```
Day 1-5:   Java logic (OOP, exceptions, collections, data structures)
Day 7:     File persistence (CSV, serialization)
Day 8:     REST API (Spring Boot)
Day 9:     HTML + CSS front-end
Day 11:    JavaScript integration + SQL schema
Day 12:    JDBC — Java ↔ Database connection
```

We now have a fully working full-stack application:
- **Front-end**: HTML + CSS + JavaScript
- **Back-end**: Spring Boot REST API
- **Database**: H2 with JDBC

## What's Next (Day 13 Preview)

The app works — but how do we manage the code, build it automatically, and deploy it? Tomorrow we learn **DevOps basics**: Git for version control, CI/CD for automated builds, and Docker for consistent deployments.
