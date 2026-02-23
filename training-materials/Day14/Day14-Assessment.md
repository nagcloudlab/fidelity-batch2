# Day 14 — SQL and Networks Assessment

## Assessment Paper (Instructor Copy — with Answer Key)

> **Duration**: ~4 hours
> **Total Marks**: 120
> **Coverage**: Days 11–13 (Java + HTML Integration / HTTP & Networks, SQL Fundamentals, JDBC Integration, Basics of DevOps)
> **Instructions**: Answer all questions. SQL sections must use valid SQL syntax. Java code must be compilable. DevOps sections must use valid command syntax.

---

## Section A: Multiple Choice (20 marks)

*Choose the single best answer. 1 mark each.*

---

**Q1.** What does `fetch()` return in JavaScript?

a) A JSON object
b) A Promise
c) An HTML element
d) A string

---

**Q2.** What must you call on a `fetch()` response to extract the JSON body?

a) `response.text()`
b) `response.json()`
c) `response.body()`
d) `response.getData()`

---

**Q3.** What does `event.preventDefault()` do when called inside a form's submit handler?

a) Prevents the JavaScript from running
b) Prevents the browser from sending the form via its default HTML action (page refresh)
c) Prevents CSS from loading
d) Prevents the server from responding

---

**Q4.** Why does the browser block a `fetch()` from `http://localhost:63342` to `http://localhost:8080`?

a) The server is down
b) The JavaScript has a syntax error
c) Same-origin policy — the ports are different, so it's a cross-origin request
d) JSON is not supported across servers

---

**Q5.** What Spring Boot annotation fixes CORS errors?

a) `@RequestBody`
b) `@CrossOrigin`
c) `@PathVariable`
d) `@ResponseBody`

---

**Q6.** In a relational database, what uniquely identifies each row in a table?

a) Foreign key
b) Index
c) Primary key
d) Column name

---

**Q7.** What does a FOREIGN KEY do?

a) Encrypts the column data
b) Creates a reference from one table to another table's primary key
c) Makes a column unique
d) Prevents null values

---

**Q8.** Which SQL statement retrieves data from a table?

a) INSERT
b) UPDATE
c) DELETE
d) SELECT

---

**Q9.** What does this SQL return? `SELECT COUNT(*) FROM accounts WHERE account_type = 'SAVINGS';`

a) All savings accounts with full details
b) The number of savings accounts
c) The total balance of savings accounts
d) The account numbers of savings accounts

---

**Q10.** Which SQL clause sorts results?

a) GROUP BY
b) WHERE
c) ORDER BY
d) HAVING

---

**Q11.** What does JDBC stand for?

a) Java Data Base Connector
b) Java Database Connectivity
c) Java Direct Bridge Connection
d) Java Data Binding Component

---

**Q12.** Which JDBC class represents an active session with the database?

a) `Driver`
b) `Connection`
c) `ResultSet`
d) `PreparedStatement`

---

**Q13.** What is the correct way to set a parameter in a PreparedStatement?

a) `ps.setString(0, "Ravi");` — 0-based index
b) `ps.setString(1, "Ravi");` — 1-based index
c) `ps.setString("name", "Ravi");` — by column name
d) `ps.set(1, "Ravi");` — generic setter

---

**Q14.** Which method do you call on a PreparedStatement for an INSERT query?

a) `executeQuery()`
b) `executeUpdate()`
c) `executeInsert()`
d) `execute()`

---

**Q15.** What is the main advantage of PreparedStatement over Statement?

a) It runs faster for one-time queries
b) It prevents SQL injection by separating SQL from data
c) It doesn't require a Connection
d) It automatically creates tables

---

**Q16.** What does DevOps combine?

a) Design and Operations
b) Development and Operations
c) Database and Optimization
d) Deployment and Orchestration

---

**Q17.** In Git, what does `git add .` do?

a) Creates a new repository
b) Pushes changes to GitHub
c) Stages all modified and new files for the next commit
d) Commits all changes

---

**Q18.** What is the correct order of Git operations to save and share changes?

a) commit → add → push
b) push → add → commit
c) add → commit → push
d) add → push → commit

---

**Q19.** In a Dockerfile, what does the `FROM` instruction do?

a) Copies files into the container
b) Specifies the base image to build upon
c) Exposes a port
d) Runs a command inside the container

---

**Q20.** What is the difference between a Docker image and a container?

a) They are the same thing
b) An image is a running instance; a container is a blueprint
c) An image is a blueprint (read-only); a container is a running instance of that image
d) An image is for Linux only; a container is cross-platform

---

### Section A — Answer Key

| Q | Answer | Explanation |
|---|--------|-------------|
| 1 | b | `fetch()` returns a Promise that resolves to a Response object |
| 2 | b | `.json()` parses the response body as JSON (also returns a Promise) |
| 3 | b | Without it, the browser submits the form via HTML `action`, causing a page refresh |
| 4 | c | Different ports = different origins; browser enforces same-origin policy |
| 5 | b | `@CrossOrigin(origins = "*")` tells Spring Boot to allow cross-origin requests |
| 6 | c | Primary key uniquely identifies each row (e.g., `account_number`) |
| 7 | b | FK creates a link between two tables (e.g., `transactions.account_number` → `accounts.account_number`) |
| 8 | d | SELECT retrieves/reads data; INSERT creates, UPDATE modifies, DELETE removes |
| 9 | b | `COUNT(*)` returns the count of matching rows, not the rows themselves |
| 10 | c | ORDER BY sorts (ASC/DESC); GROUP BY groups for aggregation; WHERE filters |
| 11 | b | Java Database Connectivity — the standard API for database access in Java |
| 12 | b | Connection represents an active database session obtained via `DriverManager.getConnection()` |
| 13 | b | JDBC parameters are 1-based: first `?` is index 1, second is index 2, etc. |
| 14 | b | `executeUpdate()` for INSERT/UPDATE/DELETE (returns affected row count); `executeQuery()` for SELECT |
| 15 | b | PreparedStatement uses `?` placeholders — the database treats parameters as data, never as SQL code |
| 16 | b | DevOps = Development + Operations working together |
| 17 | c | `git add .` stages all changes in the current directory for the next commit |
| 18 | c | Stage → Commit (local) → Push (remote): add → commit → push |
| 19 | b | FROM sets the base image (e.g., `FROM eclipse-temurin:17-jdk-alpine`) |
| 20 | c | Image = recipe/blueprint (immutable); Container = running instance (has state) |

---

## Section B: Short Answer (30 marks)

*Answer in 2–4 sentences. 3 marks each.*

---

**Q1.** Explain the role of JavaScript `fetch()` in connecting the banking app's HTML pages to the Spring Boot REST API. What three things does the JavaScript code do?

**Q2.** What is CORS (Cross-Origin Resource Sharing)? Why did we need to add `@CrossOrigin` to our banking app's controller?

**Q3.** What is the difference between a PRIMARY KEY and a FOREIGN KEY? Give an example from the banking app's database tables.

**Q4.** Write the SQL query to find all accounts with a balance greater than 50,000, sorted by balance in descending order.

**Q5.** Explain the difference between `executeQuery()` and `executeUpdate()` in JDBC. When do you use each?

**Q6.** Why is PreparedStatement preferred over Statement? What security vulnerability does Statement have?

**Q7.** What is the purpose of `try-with-resources` when using JDBC Connection and PreparedStatement?

**Q8.** Explain the DevOps lifecycle. Name at least 5 of the 8 phases in order.

**Q9.** What is the difference between `git commit` and `git push`? Where do the changes go in each case?

**Q10.** Explain the difference between a Docker image and a Docker container. How do you create each?

---

### Section B — Answer Key

**Q1.**
JavaScript `fetch()` is the bridge between the browser (HTML pages) and the server (Spring Boot API). The JavaScript code does three things: (1) **Reads data** from HTML form fields using `document.getElementById()`, (2) **Sends HTTP requests** to the REST API using `fetch()` with the appropriate method (GET/POST/DELETE) and JSON body, (3) **Updates the HTML page** with the response data — e.g., building a table of accounts dynamically from JSON.

**Q2.**
CORS is a browser security mechanism that blocks web pages from making requests to a different origin (different protocol, domain, or port). Our banking HTML pages are served from one port (e.g., `localhost:63342` via the IDE) while the Spring Boot API runs on another port (`localhost:8080`). These are different origins, so the browser blocks the request. Adding `@CrossOrigin(origins = "*")` to the controller tells Spring Boot to include the `Access-Control-Allow-Origin` header, which the browser accepts.

**Q3.**
A **PRIMARY KEY** uniquely identifies each row in a table — no duplicates, no nulls. Example: `account_number` in the `accounts` table. A **FOREIGN KEY** is a column that references the primary key of another table, creating a relationship. Example: `account_number` in the `transactions` table references `account_number` in the `accounts` table, linking each transaction to its account (one-to-many relationship).

**Q4.**
```sql
SELECT * FROM accounts
WHERE balance > 50000
ORDER BY balance DESC;
```
(1 mark for correct SELECT, 1 mark for WHERE condition, 1 mark for ORDER BY DESC)

**Q5.**
`executeQuery()` is used for **SELECT** statements — it returns a `ResultSet` containing the rows that match the query. `executeUpdate()` is used for **INSERT, UPDATE, and DELETE** statements — it returns an `int` representing the number of rows affected. Using the wrong method causes an error: `executeQuery()` on an INSERT will throw an exception.

**Q6.**
**Statement** builds SQL by string concatenation: `"SELECT * FROM accounts WHERE name = '" + userInput + "'"`. If `userInput` is `' OR 1=1 --`, the query becomes `SELECT * FROM accounts WHERE name = '' OR 1=1 --'` — returning all rows (SQL injection). **PreparedStatement** uses `?` placeholders: `SELECT * FROM accounts WHERE name = ?`. The database treats the parameter as a data value, never as SQL code, making injection impossible.

**Q7.**
JDBC Connection and PreparedStatement hold database resources (network connections, memory). If not closed properly, these resources leak — eventually the database runs out of connections. `try-with-resources` guarantees that resources are closed when the block exits, even if an exception occurs. Example: `try (Connection conn = DriverManager.getConnection(...); PreparedStatement ps = conn.prepareStatement(...))` — both are auto-closed.

**Q8.**
The DevOps lifecycle is a continuous loop:
**Plan** → **Code** → **Build** → **Test** → **Release** → **Deploy** → **Operate** → **Monitor** → (feedback loop back to Plan).
Each phase feeds into the next, with monitoring and feedback driving improvements. In our banking app: we Plan features, Code in Java, Build with Maven, Test with JUnit, Release as a JAR, Deploy via Docker, Operate the container, and Monitor logs.

**Q9.**
`git commit` saves staged changes to the **local repository** on your machine. The commit exists only on your computer — no one else can see it. `git push` uploads your local commits to the **remote repository** (e.g., GitHub). After pushing, teammates can see your changes by pulling. The order is always: `git add` (stage) → `git commit` (save locally) → `git push` (share remotely).

**Q10.**
A Docker **image** is a read-only blueprint that contains everything needed to run an application — code, runtime, libraries, configuration. You create it with `docker build -t image-name .` using a Dockerfile. A Docker **container** is a running instance of an image — it has its own isolated filesystem, processes, and network. You create it with `docker run image-name`. One image can spawn many containers. Image = recipe; Container = dish made from the recipe.

---

## Section C: Code Reading & Debugging (25 marks)

*Read the code carefully and answer the questions. 5 marks each.*

---

### Q1. What does this JavaScript code do? Identify ONE bug.

```javascript
document.getElementById("createForm").addEventListener("submit", function(event) {
    const account = {
        accountNumber: document.getElementById("accNo").value,
        holderName: document.getElementById("name").value,
        balance: document.getElementById("deposit").value,
        accountType: document.querySelector('input[name="type"]:checked').value
    };

    fetch("http://localhost:8080/api/accounts", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(account)
    })
    .then(response => response.json())
    .then(data => {
        document.getElementById("result").textContent = "Account created: " + data.holderName;
    });
});
```

---

### Q2. Write the SQL output for these queries.

Given this `accounts` table:

| account_number | holder_name | account_type | balance |
|---------------|-------------|-------------|---------|
| 1001 | Ravi Kumar | SAVINGS | 50000 |
| 1002 | Priya Shah | CURRENT | 120000 |
| 1003 | Amit Verma | SAVINGS | 30000 |
| 1004 | Neha Gupta | SAVINGS | 75000 |
| 1005 | Karan Patel | CURRENT | 45000 |

**Query A:**
```sql
SELECT account_type, COUNT(*) AS total, AVG(balance) AS avg_bal
FROM accounts
GROUP BY account_type;
```

**Query B:**
```sql
SELECT holder_name, balance FROM accounts
WHERE balance > 40000
ORDER BY balance DESC;
```

---

### Q3. Find and fix TWO bugs in this JDBC code:

```java
public Account findByAccountNumber(long accountNumber) {
    String sql = "SELECT * FROM accounts WHERE account_number = " + accountNumber;
    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Account acc = new Account();
            acc.setAccountNumber(rs.getLong("account_number"));
            acc.setHolderName(rs.getString("holder_name"));
            acc.setBalance(rs.getDouble("balance"));
            return acc;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
```

---

### Q4. What Git commands would you use for each scenario?

Match each scenario to the correct command(s):

| Scenario | Command(s)? |
|----------|------------|
| Create a new feature branch called `add-login` | ___ |
| Stage all modified files | ___ |
| Save staged changes with a message | ___ |
| Upload local commits to GitHub | ___ |
| Download latest changes from GitHub and merge | ___ |

Choose from: `git add .`, `git commit -m "message"`, `git push`, `git pull`, `git checkout -b add-login`

---

### Q5. Find TWO errors in this Dockerfile:

```dockerfile
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/banking-app.jar /app/app.jar

EXPOSE 8080

RUN java -jar app.jar
```

---

### Section C — Answer Key

**Q1.** The code listens for the form's submit event, collects form field values into a JavaScript object, sends a POST request with the JSON body to the banking API, and displays the created account's holder name.

**Bug: Missing `event.preventDefault()`**. Without this call at the start of the handler, the browser will submit the form via its default HTML action — causing a page refresh. The `fetch()` call will be cancelled because the page navigates away before it completes.

Fix — add as the first line inside the handler:
```javascript
event.preventDefault();
```

(2 marks for describing what the code does, 3 marks for identifying the bug and fix)

**Q2.**

**Query A output:**

| account_type | total | avg_bal |
|-------------|-------|---------|
| SAVINGS | 3 | 51666.67 |
| CURRENT | 2 | 82500.00 |

SAVINGS: 3 accounts, avg = (50000 + 30000 + 75000) / 3 = 51666.67
CURRENT: 2 accounts, avg = (120000 + 45000) / 2 = 82500.00

**Query B output:**

| holder_name | balance |
|-------------|---------|
| Priya Shah | 120000 |
| Neha Gupta | 75000 |
| Ravi Kumar | 50000 |
| Karan Patel | 45000 |

Filters balance > 40000 (Amit Verma's 30000 is excluded), then sorts DESC.

(2.5 marks per query — partial credit for correct rows but wrong order)

**Q3.** Two bugs:

**Bug 1 — SQL injection vulnerability**: The query uses string concatenation (`"... = " + accountNumber`) instead of a PreparedStatement parameter placeholder (`?`). Even though `accountNumber` is a `long` (limiting the risk), this is bad practice and inconsistent with using PreparedStatement.

Fix:
```java
String sql = "SELECT * FROM accounts WHERE account_number = ?";
// ...
ps.setLong(1, accountNumber);
```

**Bug 2 — ResultSet not closed**: `ResultSet rs` is created inside the try block but is NOT in the try-with-resources declaration. If an exception occurs while reading results, the ResultSet won't be auto-closed.

Fix — include ResultSet in a nested try-with-resources, or close it in a finally block:
```java
try (Connection conn = DatabaseManager.getConnection();
     PreparedStatement ps = conn.prepareStatement(sql)) {
    ps.setLong(1, accountNumber);
    try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            // ... map to Account
        }
    }
}
```

(2.5 marks per bug)

**Q4.**

| Scenario | Command(s) |
|----------|-----------|
| Create a new feature branch called `add-login` | **`git checkout -b add-login`** |
| Stage all modified files | **`git add .`** |
| Save staged changes with a message | **`git commit -m "message"`** |
| Upload local commits to GitHub | **`git push`** |
| Download latest changes from GitHub and merge | **`git pull`** |

(1 mark each)

**Q5.** Two errors:

**Error 1 — `RUN` should be `CMD` (or `ENTRYPOINT`)**: `RUN java -jar app.jar` executes the command **during the image build**, not when the container starts. The build process would hang trying to run the Spring Boot server. It should be `CMD ["java", "-jar", "app.jar"]` which specifies the command to run when a container is started.

**Error 2** (related): Using `RUN` means the JAR runs at build time. If the app needs a database or external service, the build will fail. `CMD` defers execution to container start time.

Fix:
```dockerfile
CMD ["java", "-jar", "app.jar"]
```

Note: If students identify that `EXPOSE` doesn't actually publish the port (you still need `docker run -p 8080:8080`), give partial credit — EXPOSE is informational/documentation, not a bug.

(2.5 marks per error)

---

## Section D: Practical Assignments (45 marks)

*Write complete, working code. You may use an IDE, SQL console, and terminal.*

---

### D1. SQL Queries — Banking Database (15 marks)

Given the following two tables have been created and populated:

```sql
CREATE TABLE accounts (
    account_number BIGINT PRIMARY KEY,
    holder_name VARCHAR(100) NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    balance DOUBLE DEFAULT 0,
    created_date DATE DEFAULT CURRENT_DATE
);

CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_number BIGINT NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    amount DOUBLE NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_number) REFERENCES accounts(account_number)
);
```

**Sample data:**

```sql
INSERT INTO accounts VALUES (1001, 'Ravi Kumar', 'SAVINGS', 50000, '2026-01-15');
INSERT INTO accounts VALUES (1002, 'Priya Shah', 'CURRENT', 120000, '2026-01-20');
INSERT INTO accounts VALUES (1003, 'Amit Verma', 'SAVINGS', 30000, '2026-02-01');
INSERT INTO accounts VALUES (1004, 'Neha Gupta', 'SAVINGS', 75000, '2026-02-05');
INSERT INTO accounts VALUES (1005, 'Karan Patel', 'CURRENT', 45000, '2026-02-10');

INSERT INTO transactions (account_number, transaction_type, amount) VALUES (1001, 'DEPOSIT', 10000);
INSERT INTO transactions (account_number, transaction_type, amount) VALUES (1001, 'WITHDRAW', 5000);
INSERT INTO transactions (account_number, transaction_type, amount) VALUES (1002, 'DEPOSIT', 20000);
INSERT INTO transactions (account_number, transaction_type, amount) VALUES (1003, 'WITHDRAW', 3000);
INSERT INTO transactions (account_number, transaction_type, amount) VALUES (1004, 'DEPOSIT', 15000);
INSERT INTO transactions (account_number, transaction_type, amount) VALUES (1001, 'DEPOSIT', 7000);
INSERT INTO transactions (account_number, transaction_type, amount) VALUES (1005, 'WITHDRAW', 10000);
INSERT INTO transactions (account_number, transaction_type, amount) VALUES (1002, 'WITHDRAW', 8000);
```

**Write SQL queries for each requirement:**

1. **(2 marks)** List all savings accounts sorted by balance (highest first).

2. **(2 marks)** Find the total balance across all accounts and the average balance per account type.

3. **(3 marks)** List all transactions for account 1001 with the holder's name (requires JOIN).

4. **(3 marks)** Find each account's total deposit amount and total withdrawal amount (requires GROUP BY with CASE or multiple queries).

5. **(2 marks)** Find accounts that have never made a withdrawal.

6. **(3 marks)** Create a view or query that shows a summary: account number, holder name, number of transactions, and total transaction amount for each account.

---

### D1 — Answer Key

**1.** List all savings accounts sorted by balance (highest first):
```sql
SELECT * FROM accounts
WHERE account_type = 'SAVINGS'
ORDER BY balance DESC;
```

**2.** Total balance and average by type:
```sql
-- Total balance across all accounts:
SELECT SUM(balance) AS total_balance FROM accounts;

-- Average balance per account type:
SELECT account_type, AVG(balance) AS avg_balance
FROM accounts
GROUP BY account_type;
```

**3.** Transactions for account 1001 with holder name (JOIN):
```sql
SELECT a.holder_name, t.transaction_id, t.transaction_type, t.amount, t.transaction_date
FROM transactions t
JOIN accounts a ON t.account_number = a.account_number
WHERE t.account_number = 1001;
```

**4.** Total deposits and withdrawals per account:
```sql
SELECT
    a.account_number,
    a.holder_name,
    SUM(CASE WHEN t.transaction_type = 'DEPOSIT' THEN t.amount ELSE 0 END) AS total_deposits,
    SUM(CASE WHEN t.transaction_type = 'WITHDRAW' THEN t.amount ELSE 0 END) AS total_withdrawals
FROM accounts a
LEFT JOIN transactions t ON a.account_number = t.account_number
GROUP BY a.account_number, a.holder_name;
```

Alternative (simpler, two separate queries):
```sql
SELECT account_number, SUM(amount) AS total_deposits
FROM transactions WHERE transaction_type = 'DEPOSIT'
GROUP BY account_number;

SELECT account_number, SUM(amount) AS total_withdrawals
FROM transactions WHERE transaction_type = 'WITHDRAW'
GROUP BY account_number;
```

(Full marks for either approach)

**5.** Accounts that have never made a withdrawal:
```sql
SELECT * FROM accounts
WHERE account_number NOT IN (
    SELECT account_number FROM transactions WHERE transaction_type = 'WITHDRAW'
);
```

Result: Account 1004 (Neha Gupta) — only has a DEPOSIT.

**6.** Account summary with transaction count and total:
```sql
SELECT
    a.account_number,
    a.holder_name,
    COUNT(t.transaction_id) AS num_transactions,
    COALESCE(SUM(t.amount), 0) AS total_amount
FROM accounts a
LEFT JOIN transactions t ON a.account_number = t.account_number
GROUP BY a.account_number, a.holder_name
ORDER BY a.account_number;
```

(LEFT JOIN ensures accounts with zero transactions still appear)

---

### D2. JDBC — Transaction Repository (15 marks)

Build a `TransactionRepository` class that manages transactions in the database using JDBC.

**Requirements:**

1. Create a `Transaction` model class with: `transactionId` (int), `accountNumber` (long), `transactionType` (String), `amount` (double), `transactionDate` (String). Include default constructor, parameterized constructor, getters, setters, and `toString()`.

2. Create `TransactionRepository` with these methods:
   - `insert(Transaction t)` — inserts a new transaction using PreparedStatement
   - `findByAccountNumber(long accountNumber)` — returns `List<Transaction>` for an account
   - `findAll()` — returns all transactions
   - `getTotalByType(long accountNumber, String type)` — returns the sum of amounts for a given account and transaction type (DEPOSIT or WITHDRAW)
   - `deleteByAccountNumber(long accountNumber)` — deletes all transactions for an account

3. All methods must:
   - Use `PreparedStatement` (not Statement)
   - Use `try-with-resources` for all database resources
   - Handle `SQLException` properly

4. Write a `main` method that:
   - Inserts 3 test transactions
   - Retrieves and prints transactions for one account
   - Prints total deposits for that account
   - Deletes transactions for an account
   - Prints remaining transactions

**Grading rubric:**
- Transaction model class: 2 marks
- insert() with PreparedStatement: 2 marks
- findByAccountNumber() with parameter binding: 3 marks
- findAll() correctly mapping ResultSet to List: 2 marks
- getTotalByType() with SUM query: 3 marks
- deleteByAccountNumber(): 1 mark
- try-with-resources throughout: 1 mark
- Main method demonstrating all operations: 1 mark

---

### D2 — Answer Key (Model Solution)

```java
import java.io.Serializable;

public class Transaction implements Serializable {
    private int transactionId;
    private long accountNumber;
    private String transactionType;
    private double amount;
    private String transactionDate;

    public Transaction() {}

    public Transaction(long accountNumber, String transactionType, double amount) {
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    // Getters and Setters
    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }
    public long getAccountNumber() { return accountNumber; }
    public void setAccountNumber(long accountNumber) { this.accountNumber = accountNumber; }
    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getTransactionDate() { return transactionDate; }
    public void setTransactionDate(String transactionDate) { this.transactionDate = transactionDate; }

    @Override
    public String toString() {
        return String.format("Transaction{id=%d, account=%d, type=%s, amount=%.2f, date=%s}",
                transactionId, accountNumber, transactionType, amount, transactionDate);
    }
}
```

```java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    // ── INSERT ────────────────────────────────────────────
    public void insert(Transaction t) {
        String sql = "INSERT INTO transactions (account_number, transaction_type, amount) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, t.getAccountNumber());
            ps.setString(2, t.getTransactionType());
            ps.setDouble(3, t.getAmount());
            ps.executeUpdate();
            System.out.println("Transaction inserted: " + t.getTransactionType() + " Rs." + t.getAmount());
        } catch (SQLException e) {
            System.err.println("Insert failed: " + e.getMessage());
        }
    }

    // ── FIND BY ACCOUNT NUMBER ────────────────────────────
    public List<Transaction> findByAccountNumber(long accountNumber) {
        String sql = "SELECT * FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC";
        List<Transaction> transactions = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, accountNumber);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapRowToTransaction(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Find failed: " + e.getMessage());
        }
        return transactions;
    }

    // ── FIND ALL ──────────────────────────────────────────
    public List<Transaction> findAll() {
        String sql = "SELECT * FROM transactions ORDER BY transaction_date DESC";
        List<Transaction> transactions = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapRowToTransaction(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("FindAll failed: " + e.getMessage());
        }
        return transactions;
    }

    // ── GET TOTAL BY TYPE ─────────────────────────────────
    public double getTotalByType(long accountNumber, String type) {
        String sql = "SELECT COALESCE(SUM(amount), 0) AS total FROM transactions WHERE account_number = ? AND transaction_type = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, accountNumber);
            ps.setString(2, type);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        } catch (SQLException e) {
            System.err.println("GetTotal failed: " + e.getMessage());
        }
        return 0;
    }

    // ── DELETE BY ACCOUNT NUMBER ──────────────────────────
    public int deleteByAccountNumber(long accountNumber) {
        String sql = "DELETE FROM transactions WHERE account_number = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, accountNumber);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Delete failed: " + e.getMessage());
        }
        return 0;
    }

    // ── HELPER ────────────────────────────────────────────
    private Transaction mapRowToTransaction(ResultSet rs) throws SQLException {
        Transaction t = new Transaction();
        t.setTransactionId(rs.getInt("transaction_id"));
        t.setAccountNumber(rs.getLong("account_number"));
        t.setTransactionType(rs.getString("transaction_type"));
        t.setAmount(rs.getDouble("amount"));
        t.setTransactionDate(rs.getString("transaction_date"));
        return t;
    }

    // ── MAIN (DEMO) ──────────────────────────────────────
    public static void main(String[] args) {
        DatabaseManager.initializeDatabase(); // creates tables + sample data

        TransactionRepository repo = new TransactionRepository();

        // Insert 3 test transactions
        repo.insert(new Transaction(1001, "DEPOSIT", 5000));
        repo.insert(new Transaction(1001, "WITHDRAW", 2000));
        repo.insert(new Transaction(1002, "DEPOSIT", 15000));

        // Retrieve transactions for account 1001
        System.out.println("\n--- Transactions for Account 1001 ---");
        List<Transaction> txns = repo.findByAccountNumber(1001);
        txns.forEach(System.out::println);

        // Total deposits for account 1001
        double totalDeposits = repo.getTotalByType(1001, "DEPOSIT");
        System.out.println("\nTotal deposits for 1001: Rs." + totalDeposits);

        // Delete transactions for account 1002
        int deleted = repo.deleteByAccountNumber(1002);
        System.out.println("\nDeleted " + deleted + " transactions for account 1002");

        // Print remaining
        System.out.println("\n--- All Remaining Transactions ---");
        repo.findAll().forEach(System.out::println);
    }
}
```

---

### D3. DevOps — Containerize the Banking App (15 marks)

Perform the following DevOps tasks for the banking application:

**Part A: Git Workflow (5 marks)**

Write the exact Git commands for the following workflow:

1. Initialize a new Git repository
2. Create a `.gitignore` file that excludes: `target/`, `.idea/`, `*.class`, `.DS_Store`
3. Stage all files
4. Create an initial commit
5. Create a new branch called `feature/transaction-history`
6. Switch to that branch
7. (Assume you make changes here) Stage and commit the changes with the message "Add transaction history endpoint"
8. Switch back to main
9. Merge the feature branch into main

**Part B: Dockerfile (5 marks)**

Write a complete Dockerfile for the banking Spring Boot application that:
- Uses `eclipse-temurin:17-jdk-alpine` as the base image
- Sets the working directory to `/app`
- Copies the JAR file from `target/banking-app-1.0.jar`
- Exposes port 8080
- Runs the application when the container starts

Then write the Docker commands to:
- Build the image with tag `simple-bank:1.0`
- Run a container named `bank-app` mapping port 8080
- View the running container's logs
- Stop and remove the container

**Part C: CI Pipeline (5 marks)**

Write a GitHub Actions workflow file (`.github/workflows/ci.yml`) that:
- Triggers on push to `main` and on pull requests to `main`
- Runs on `ubuntu-latest`
- Has these steps:
  1. Checkout the code
  2. Set up Java 17
  3. Compile the project with Maven
  4. Run tests with Maven
  5. Build the JAR package with Maven

---

### D3 — Answer Key

**Part A: Git Workflow**

```bash
# 1. Initialize repository
git init

# 2. Create .gitignore
# (Write the file with the specified contents)

# 3. Stage all files
git add .

# 4. Initial commit
git commit -m "Initial commit: banking application"

# 5-6. Create and switch to feature branch
git checkout -b feature/transaction-history

# 7. (After making changes) Stage and commit
git add .
git commit -m "Add transaction history endpoint"

# 8. Switch back to main
git checkout main

# 9. Merge feature branch
git merge feature/transaction-history
```

(0.5 marks per correct command, 0.5 marks for the complete .gitignore contents)

**Part B: Dockerfile and commands**

```dockerfile
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/banking-app-1.0.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
```

```bash
# Build the image
docker build -t simple-bank:1.0 .

# Run the container
docker run -d --name bank-app -p 8080:8080 simple-bank:1.0

# View logs
docker logs bank-app

# Stop and remove
docker stop bank-app
docker rm bank-app
```

(2.5 marks for Dockerfile, 2.5 marks for docker commands)

**Part C: GitHub Actions CI workflow**

```yaml
name: Banking App CI

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up Java 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Compile
        run: mvn compile

      - name: Run tests
        run: mvn test

      - name: Build package
        run: mvn package -DskipTests
```

(1 mark for triggers, 1 mark for runs-on, 1 mark for checkout + Java setup, 1 mark for compile + test, 1 mark for package)

---

## Bonus Questions (optional, 10 extra marks)

*For trainees who finish early.*

### Bonus 1 (5 marks): Full-Stack Data Flow Trace

Trace the complete journey of a "Create Account" request from the browser to the database and back. For each step, state:
- **Where** (browser / network / controller / service / repository / database)
- **What happens**
- **What data format** is used (HTML form / JSON / Java object / SQL / ResultSet)

Start from: "User fills in the form and clicks Submit"
End at: "User sees success message on the page"

---

### Bonus 2 (5 marks): Advanced SQL — Bank Report

Write a single SQL query that produces this report:

```
┌─────────────────────────────────────────────────────────────────┐
│ holder_name  │ type    │ balance │ txn_count │ net_flow │ status│
├─────────────────────────────────────────────────────────────────┤
│ Priya Shah   │ CURRENT │ 120000  │ 2         │ 12000    │ HIGH  │
│ Neha Gupta   │ SAVINGS │ 75000   │ 1         │ 15000    │ HIGH  │
│ Ravi Kumar   │ SAVINGS │ 50000   │ 3         │ 12000    │ MEDIUM│
│ Karan Patel  │ CURRENT │ 45000   │ 1         │ -10000   │ MEDIUM│
│ Amit Verma   │ SAVINGS │ 30000   │ 1         │ -3000    │ LOW   │
└─────────────────────────────────────────────────────────────────┘
```

Where:
- `txn_count` = total number of transactions for the account
- `net_flow` = SUM of deposits - SUM of withdrawals (deposits are positive, withdrawals are negative)
- `status` = 'HIGH' if balance >= 60000, 'MEDIUM' if balance >= 40000, 'LOW' otherwise
- Sorted by balance descending

---

### Bonus Answers

**Bonus 1: Full-Stack Data Flow Trace**

| Step | Where | What Happens | Data Format |
|------|-------|-------------|-------------|
| 1 | **Browser** | User fills form fields (name, balance, type) and clicks Submit | HTML form values |
| 2 | **Browser (JavaScript)** | `event.preventDefault()` stops default submission. JS reads field values with `getElementById().value`, creates a JavaScript object | JS object |
| 3 | **Browser (JavaScript)** | `JSON.stringify(account)` converts JS object to JSON string | JSON string |
| 4 | **Browser (JavaScript)** | `fetch("http://localhost:8080/api/accounts", { method: "POST", body: jsonString })` sends HTTP request | HTTP POST + JSON body |
| 5 | **Network** | HTTP request travels from browser to Spring Boot server | HTTP protocol |
| 6 | **Controller** | `@PostMapping` method receives request. `@RequestBody Account account` — Jackson deserializes JSON into a Java `Account` object | JSON → Java object |
| 7 | **Service** | `accountService.createAccount(account)` — validates data, assigns account number | Java object |
| 8 | **Repository** | `accountRepository.insert(account)` — creates PreparedStatement with `?` parameters, calls `setLong()`, `setString()`, `setDouble()` | Java object → SQL parameters |
| 9 | **Database** | `INSERT INTO accounts VALUES (?, ?, ?, ?)` executes — row is saved | SQL / table row |
| 10 | **Repository → Service → Controller** | Returns the saved Account object up the chain | Java object |
| 11 | **Controller** | `ResponseEntity.status(201).body(account)` — Jackson serializes Account to JSON. Spring sends HTTP 201 response | Java object → JSON |
| 12 | **Network** | HTTP response travels back to browser | HTTP protocol |
| 13 | **Browser (JavaScript)** | `response.json()` parses JSON into a JS object. `.then(data => ...)` updates the DOM: `textContent = "Account created: " + data.holderName` | JSON → JS object → DOM text |
| 14 | **Browser** | User sees "Account created: Ravi Kumar" on the page | Rendered HTML |

(0.5 marks per correct step, max 5 marks)

**Bonus 2: Advanced SQL Query**

```sql
SELECT
    a.holder_name,
    a.account_type AS type,
    a.balance,
    COUNT(t.transaction_id) AS txn_count,
    COALESCE(
        SUM(CASE
            WHEN t.transaction_type = 'DEPOSIT' THEN t.amount
            WHEN t.transaction_type = 'WITHDRAW' THEN -t.amount
            ELSE 0
        END),
        0
    ) AS net_flow,
    CASE
        WHEN a.balance >= 60000 THEN 'HIGH'
        WHEN a.balance >= 40000 THEN 'MEDIUM'
        ELSE 'LOW'
    END AS status
FROM accounts a
LEFT JOIN transactions t ON a.account_number = t.account_number
GROUP BY a.account_number, a.holder_name, a.account_type, a.balance
ORDER BY a.balance DESC;
```

Key points:
- `LEFT JOIN` to include accounts with no transactions
- `CASE WHEN` inside `SUM()` to calculate net flow (deposits positive, withdrawals negative)
- `CASE WHEN` for status classification
- `COALESCE(..., 0)` to handle NULL for accounts with no transactions
- `GROUP BY` all non-aggregated columns

(2 marks for correct JOIN + GROUP BY, 1 mark for net_flow calculation, 1 mark for status CASE, 1 mark for correct output)

---

## Assessment Summary Checklist (for instructor)

After grading, tally how trainees performed per topic area:

| Topic Area | Related Questions | Class Average |
|-----------|------------------|---------------|
| JavaScript fetch() & DOM | A1-A3, B1, C1 | ___% |
| HTTP / CORS / Networks | A4-A5, B2, Bonus 1 | ___% |
| SQL Fundamentals (SELECT, WHERE, ORDER BY) | A8-A10, B4, C2, D1.1-D1.2 | ___% |
| SQL Joins & Aggregation | A6-A7, B3, D1.3-D1.6 | ___% |
| JDBC Architecture & Workflow | A11-A12, B5-B7, C3, D2 | ___% |
| PreparedStatement & SQL Injection | A13-A15, B6, C3 | ___% |
| DevOps Culture & Lifecycle | A16, B8 | ___% |
| Git Commands & Workflow | A17-A18, B9, C4, D3-A | ___% |
| Docker (Dockerfile, commands) | A19-A20, B10, C5, D3-B | ___% |
| CI/CD (GitHub Actions) | D3-C | ___% |

Use this to decide which topics need revision before the final assessment (Day 15).
