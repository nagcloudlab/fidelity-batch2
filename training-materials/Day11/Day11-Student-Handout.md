# Day 11 — Java + HTML Integration & SQL Fundamentals

## Student Handout

> **What you'll build today**:
> - **Part A**: Connect your banking HTML pages to the Spring Boot REST API using JavaScript — forms will actually create accounts and tables will display live data
> - **Part B**: Design SQL database tables for the banking app and learn to query data with SQL

---

# Part A: Java + HTML Integration

---

## 1. The Missing Piece

We have all the parts — now we connect them:

| Day | What we built | Role |
|-----|--------------|------|
| Day 8 | REST API (Spring Boot) | Back-end — serves data |
| Day 9 | HTML + CSS pages | Front-end — displays UI |
| **Day 11** | **JavaScript (fetch)** | **Connects front-end to back-end** |

```
┌─────────────┐     HTTP + JSON      ┌─────────────────┐
│  BROWSER     │ ←─────────────────→ │  SPRING BOOT     │
│              │                      │                   │
│  HTML (UI)   │                      │  @RestController  │
│  CSS (style) │                      │  @Service         │
│  JS (logic)  │                      │  Account model    │
└─────────────┘                      └─────────────────┘
```

**JavaScript** is the messenger — it:
1. Reads data from HTML form fields
2. Sends HTTP requests to the API (using `fetch()`)
3. Receives JSON responses
4. Updates the HTML page with the results

---

## 2. JavaScript Basics (Just Enough)

We only need a few JavaScript concepts to connect our pages.

### Adding JavaScript to a page

```html
<!-- External file (recommended) — place at end of <body> -->
<script src="app.js"></script>
```

### Getting elements and reading values

```javascript
// Get an element by its ID
const form = document.getElementById("createForm");
const nameInput = document.getElementById("name");

// Read the value the user typed
const name = nameInput.value;                    // "Ravi Kumar"
const balance = parseFloat(balanceInput.value);  // 50000.0
```

### Listening for events

```javascript
// When the form is submitted...
form.addEventListener("submit", function(event) {
    event.preventDefault();    // CRITICAL: stop the page from reloading
    // ... handle the submission with fetch() ...
});
```

`event.preventDefault()` is essential — without it, the browser reloads the page (traditional form behavior), and our JavaScript never runs.

### Updating the page

```javascript
// Change text content
document.getElementById("message").textContent = "Account created!";

// Change styling
document.getElementById("message").style.color = "green";

// Set inner HTML (for structured content)
tbody.innerHTML = "<tr><td>1001</td><td>Ravi</td></tr>";
```

---

## 3. The fetch() API

`fetch()` is how JavaScript sends HTTP requests — just like Postman or curl, but from code.

### GET request — read data

```javascript
fetch("http://localhost:8080/api/accounts")
    .then(response => response.json())      // parse JSON response
    .then(data => {
        console.log(data);                  // array of account objects
    })
    .catch(error => {
        console.error("Error:", error);
    });
```

### POST request — create data

```javascript
fetch("http://localhost:8080/api/accounts", {
    method: "POST",
    headers: {
        "Content-Type": "application/json"
    },
    body: JSON.stringify({
        accountNumber: 1004,
        holderName: "Sneha Reddy",
        balance: 100000,
        type: "CURRENT"
    })
})
.then(response => {
    if (response.status === 201) {
        return response.json();
    } else {
        throw new Error("Failed to create account");
    }
})
.then(account => {
    console.log("Created:", account);
})
.catch(error => {
    console.error("Error:", error);
});
```

### DELETE request

```javascript
fetch("http://localhost:8080/api/accounts/1001", {
    method: "DELETE"
})
.then(response => {
    if (response.status === 204) {
        console.log("Deleted successfully");
    }
});
```

### Comparison with tools you already know

| Postman | curl | JavaScript fetch() |
|---------|------|--------------------|
| Enter URL | `curl URL` | `fetch(URL)` |
| Choose method | `-X POST` | `method: "POST"` |
| Set header | (automatic) | `headers: {"Content-Type": "application/json"}` |
| Body (JSON) | `-d '{...}'` | `body: JSON.stringify({...})` |
| Click Send | Press Enter | `.then(response => ...)` |
| See response | Terminal output | `response.json()` → data |

---

## 4. CORS — The Browser Security Error

When your HTML page (opened as a file or on a different port) calls the Spring Boot API, the browser blocks it:

```
Access to fetch at 'http://localhost:8080/api/accounts' has been blocked by CORS policy
```

**CORS** (Cross-Origin Resource Sharing) is a browser security feature that prevents one website from calling another website's API without permission.

**Fix** — add `@CrossOrigin` to your Spring Boot controller:

```java
@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")    // Allow requests from any origin (dev only!)
public class AccountController {
    // ...
}
```

---

## 5. Connecting the Create Account Form

### The complete flow

```
1. User fills form: "Account: 1004, Name: Sneha, Balance: 100000, Type: CURRENT"
2. User clicks "Create Account"
3. JavaScript intercepts (preventDefault)
4. JavaScript collects form values → builds JSON object
5. fetch() sends POST /api/accounts with JSON body
6. Spring Boot creates the account → returns 201 + account JSON
7. JavaScript shows "Account created!" message on the page
```

### Updated create-account.html

Add `id="createForm"` to the form, remove `action` and `method`, and add a message div and script:

```html
<form id="createForm">
    <div class="form-group">
        <label for="accNo">Account Number</label>
        <input type="number" id="accNo" name="accountNumber"
               min="1000" max="9999" placeholder="e.g. 1004" required>
    </div>

    <div class="form-group">
        <label for="name">Account Holder Name</label>
        <input type="text" id="name" name="holderName"
               placeholder="Enter full name" required>
    </div>

    <div class="form-group">
        <label for="balance">Initial Deposit (Rs.)</label>
        <input type="number" id="balance" name="balance"
               min="0" step="0.01" value="0" required>
    </div>

    <div class="form-group radio-group">
        <label>Account Type</label><br>
        <label>
            <input type="radio" name="type" value="SAVINGS" checked> Savings
        </label>
        <label>
            <input type="radio" name="type" value="CURRENT"> Current
        </label>
    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-primary">Create Account</button>
        <button type="reset" class="btn btn-secondary">Clear</button>
    </div>
</form>

<div id="message" style="margin-top: 15px; font-weight: bold;"></div>

<script src="create-account.js"></script>
```

### create-account.js

```javascript
const API_BASE = "http://localhost:8080/api/accounts";

document.getElementById("createForm").addEventListener("submit", function(event) {
    event.preventDefault();

    // Collect form data into a JavaScript object
    const account = {
        accountNumber: parseInt(document.getElementById("accNo").value),
        holderName: document.getElementById("name").value,
        balance: parseFloat(document.getElementById("balance").value),
        type: document.querySelector('input[name="type"]:checked').value
    };

    // Send to the REST API
    fetch(API_BASE, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(account)
    })
    .then(response => {
        if (response.status === 201) {
            return response.json();
        } else if (response.status === 409) {
            throw new Error("Account number already exists!");
        } else {
            return response.json().then(err => {
                throw new Error(err.error || "Failed to create account");
            });
        }
    })
    .then(created => {
        showMessage("Account #" + created.accountNumber + " created successfully!", "green");
        document.getElementById("createForm").reset();
    })
    .catch(error => {
        showMessage(error.message, "red");
    });
});

function showMessage(text, color) {
    const msg = document.getElementById("message");
    msg.textContent = text;
    msg.style.color = color;
}
```

### How the data maps

```
HTML form field                  JavaScript object        Java object (via Jackson)
──────────────────              ────────────────────     ─────────────────────────
<input id="accNo" value="1004"> accountNumber: 1004  →  account.setAccountNumber(1004)
<input id="name" value="Sneha"> holderName: "Sneha"  →  account.setHolderName("Sneha")
<input id="balance" value="100000"> balance: 100000  →  account.setBalance(100000)
<input name="type" value="CURRENT"> type: "CURRENT"  →  account.setType("CURRENT")
```

The JavaScript JSON key names must match the Java setter names (holderName → setHolderName).

---

## 6. Loading Live Data into a Table

Replace hardcoded table rows with live data from the API.

### Updated accounts.html

Change the `<tbody>` to have an ID and be empty:

```html
<tbody id="accountsBody">
    <!-- Rows will be populated by JavaScript -->
</tbody>

<!-- Add at the end of the body -->
<script src="accounts.js"></script>
```

### accounts.js

```javascript
const API_BASE = "http://localhost:8080/api/accounts";

// Load accounts when the page opens
window.addEventListener("DOMContentLoaded", loadAccounts);

function loadAccounts() {
    fetch(API_BASE)
        .then(response => response.json())
        .then(accounts => {
            const tbody = document.getElementById("accountsBody");
            tbody.innerHTML = "";    // clear existing rows

            if (accounts.length === 0) {
                tbody.innerHTML = '<tr><td colspan="6" style="text-align:center;">No accounts found</td></tr>';
                return;
            }

            accounts.forEach(acc => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${acc.accountNumber}</td>
                    <td>${acc.holderName}</td>
                    <td>${acc.type}</td>
                    <td>${acc.balance.toLocaleString("en-IN", {minimumFractionDigits: 2})}</td>
                    <td>
                        <span class="badge badge-${acc.active ? 'active' : 'inactive'}">
                            ${acc.active ? 'Active' : 'Inactive'}
                        </span>
                    </td>
                    <td>
                        <button class="btn btn-danger btn-sm"
                                onclick="deleteAccount(${acc.accountNumber})">Delete</button>
                    </td>
                `;
                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error("Error loading accounts:", error);
        });
}

function deleteAccount(accNo) {
    if (!confirm("Are you sure you want to delete account #" + accNo + "?")) return;

    fetch(API_BASE + "/" + accNo, { method: "DELETE" })
        .then(response => {
            if (response.status === 204) {
                loadAccounts();    // refresh the table
            } else {
                alert("Failed to delete account");
            }
        })
        .catch(error => {
            console.error("Error:", error);
        });
}
```

### Test it

1. Start the Spring Boot app (`mvn spring-boot:run`)
2. Open `accounts.html` in browser — see 3 pre-loaded accounts from the API
3. Open `create-account.html` → create account #1004
4. Go back to `accounts.html` — see 4 accounts now
5. Click "Delete" on one — it disappears

---

## 7. Separation of Layers

Our full-stack application now has clear layers:

```
┌─────────────────────────────────────────────────────────────┐
│ PRESENTATION LAYER (Browser)                                 │
│   HTML    — structure (what's on the page)                   │
│   CSS     — styling (how it looks)                           │
│   JS      — behavior (form handling, API calls, DOM updates) │
└──────────────────────────┬──────────────────────────────────┘
                           │ HTTP requests (JSON)
┌──────────────────────────▼──────────────────────────────────┐
│ CONTROLLER LAYER        @RestController                      │
│   Receives HTTP requests, delegates to service, returns HTTP │
│   AccountController.java                                     │
└──────────────────────────┬──────────────────────────────────┘
                           │ Java method calls
┌──────────────────────────▼──────────────────────────────────┐
│ SERVICE LAYER           @Service                             │
│   Business logic — validation, rules, calculations           │
│   AccountService.java                                        │
└──────────────────────────┬──────────────────────────────────┘
                           │ Data access
┌──────────────────────────▼──────────────────────────────────┐
│ DATA LAYER              (in-memory now; database on Day 12)  │
│   ArrayList + HashMap                                        │
│   → JDBC + SQL database (coming tomorrow)                    │
└─────────────────────────────────────────────────────────────┘
```

Each layer has **one job**. If you want to change how data is stored (files → database), you only change the data layer. The controller and front-end don't change.

---

# Part B: SQL Fundamentals

---

## 8. Why Databases?

Our banking app currently stores data in memory (ArrayList + HashMap). Files (Day 7) added persistence. But files have limitations:

| | Files (Day 7) | Database |
|---|---|---|
| Query data | Load entire file, loop through | `SELECT * FROM accounts WHERE balance > 50000` |
| Multiple users | Risky — file locking issues | Built-in concurrency control |
| Relationships | Manual — track IDs yourself | Foreign keys enforce relationships |
| Data integrity | Code must enforce rules | Database constraints (NOT NULL, UNIQUE) |
| Scalability | Slow for large datasets | Optimized with indexes |

A **database** is an organized collection of data stored on disk. An **RDBMS** (Relational Database Management System) stores data in **tables** with rows and columns.

Examples: MySQL, PostgreSQL, Oracle, SQLite, H2

### For training: H2 Database

H2 is an in-memory Java database — no installation needed. Add to your Spring Boot `pom.xml`:

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

Add to `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:bankdb
spring.datasource.driver-class-name=org.h2.Driver
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

Access the H2 console at: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:bankdb`)

---

## 9. Tables, Rows, and Columns

A table looks like a spreadsheet:

```
TABLE: accounts
┌─────────────────┬────────────────┬──────────┬──────────┬────────┐
│ account_number   │ holder_name    │ balance  │ type     │ active │
│ (BIGINT, PK)     │ (VARCHAR)      │ (DOUBLE) │ (VARCHAR)│ (BOOL) │
├─────────────────┼────────────────┼──────────┼──────────┼────────┤
│ 1001             │ Ravi Kumar     │ 50000.00 │ SAVINGS  │ true   │
│ 1002             │ Priya Sharma   │ 30000.00 │ CURRENT  │ true   │
│ 1003             │ Amit Verma     │ 75000.00 │ SAVINGS  │ true   │
└─────────────────┴────────────────┴──────────┴──────────┴────────┘
```

| Term | Meaning | Banking Example |
|------|---------|----------------|
| **Table** | A collection of related data | `accounts`, `transactions` |
| **Row** (record) | One entry in the table | One account (Ravi, 1001, 50000) |
| **Column** (field) | A property/attribute | `holder_name`, `balance` |
| **Data type** | What kind of data a column holds | BIGINT, VARCHAR, DOUBLE |

### Common SQL data types

| SQL Type | Java Equivalent | Use for |
|----------|----------------|---------|
| `INT` / `BIGINT` | `int` / `long` | Account numbers, IDs |
| `VARCHAR(100)` | `String` | Names, descriptions (max 100 chars) |
| `DOUBLE` / `DECIMAL` | `double` | Balances, amounts |
| `BOOLEAN` | `boolean` | Active/inactive flags |
| `TIMESTAMP` | `LocalDateTime` | Transaction dates |

---

## 10. Primary Key and Foreign Key

### Primary Key (PK)

**Uniquely identifies** each row in a table. Rules: cannot be NULL, must be unique.

In our banking app: `account_number` is the PK of the `accounts` table — no two accounts can have the same number.

### Foreign Key (FK)

**Links** a row in one table to a row in another. Enforces **referential integrity** — you can't create a transaction for a non-existent account.

In our banking app: `account_number` in the `transactions` table is an FK pointing to the `accounts` table.

### Creating the tables

```sql
CREATE TABLE accounts (
    account_number BIGINT PRIMARY KEY,
    holder_name VARCHAR(100) NOT NULL,
    balance DOUBLE DEFAULT 0,
    type VARCHAR(20) NOT NULL,
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    amount DOUBLE NOT NULL,
    account_number BIGINT NOT NULL,
    balance_before DOUBLE,
    balance_after DOUBLE,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_number) REFERENCES accounts(account_number)
);
```

### Relationship: One-to-Many

```
accounts (1) ────────< (many) transactions

One account can have MANY transactions.
The FK in transactions points to the PK in accounts.
```

| Constraint | Keyword | Purpose |
|-----------|---------|---------|
| Primary Key | `PRIMARY KEY` | Unique row identifier, cannot be null |
| Not Null | `NOT NULL` | Field must have a value |
| Default | `DEFAULT 0` | Value if none is provided |
| Auto Increment | `AUTO_INCREMENT` | Database assigns the next number |
| Foreign Key | `FOREIGN KEY ... REFERENCES` | Links to another table's PK |

---

## 11. CRUD Operations

CRUD = Create, Read, Update, Delete — the four basic database operations.

### INSERT — Create

```sql
-- Insert a single account
INSERT INTO accounts (account_number, holder_name, balance, type)
VALUES (1001, 'Ravi Kumar', 50000, 'SAVINGS');

-- Insert multiple accounts at once
INSERT INTO accounts (account_number, holder_name, balance, type) VALUES
(1002, 'Priya Sharma', 30000, 'CURRENT'),
(1003, 'Amit Verma', 75000, 'SAVINGS'),
(1004, 'Sneha Reddy', 120000, 'CURRENT');

-- Insert transactions
INSERT INTO transactions (type, amount, account_number, balance_before, balance_after) VALUES
('DEPOSIT', 5000, 1001, 50000, 55000),
('WITHDRAW', 2000, 1001, 55000, 53000),
('DEPOSIT', 10000, 1002, 30000, 40000);
```

### SELECT — Read

```sql
-- Get all accounts
SELECT * FROM accounts;

-- Get specific columns only
SELECT account_number, holder_name, balance FROM accounts;

-- Filter with WHERE
SELECT * FROM accounts WHERE type = 'SAVINGS';
SELECT * FROM accounts WHERE balance > 50000;

-- Pattern matching with LIKE (% = any characters)
SELECT * FROM accounts WHERE holder_name LIKE '%Kumar%';

-- Sort results
SELECT * FROM accounts ORDER BY balance DESC;    -- highest first
SELECT * FROM accounts ORDER BY holder_name ASC;  -- A-Z

-- Multiple conditions
SELECT * FROM accounts WHERE type = 'SAVINGS' AND balance > 40000;
SELECT * FROM accounts WHERE type = 'SAVINGS' OR balance > 100000;

-- Aggregate functions
SELECT COUNT(*) FROM accounts;                           -- how many accounts
SELECT SUM(balance) FROM accounts;                       -- total balance
SELECT AVG(balance) FROM accounts;                       -- average balance
SELECT MAX(balance) FROM accounts;                       -- highest balance
SELECT MIN(balance) FROM accounts;                       -- lowest balance

-- Group by
SELECT type, COUNT(*) AS count, SUM(balance) AS total
FROM accounts
GROUP BY type;
-- Result:
-- type     | count | total
-- SAVINGS  | 2     | 125000
-- CURRENT  | 2     | 150000

-- Transactions for a specific account
SELECT * FROM transactions WHERE account_number = 1001 ORDER BY timestamp DESC;
```

### UPDATE — Modify

```sql
-- Update balance after a deposit
UPDATE accounts SET balance = 55000 WHERE account_number = 1001;

-- Deactivate an account
UPDATE accounts SET active = FALSE WHERE account_number = 1003;

-- Update multiple columns
UPDATE accounts SET holder_name = 'Ravi K.', type = 'CURRENT'
WHERE account_number = 1001;
```

**WARNING**: Always use `WHERE` with `UPDATE`! Without it, ALL rows are updated:
```sql
-- DANGEROUS: deactivates EVERY account!
UPDATE accounts SET active = FALSE;
```

### DELETE — Remove

```sql
-- Delete a specific account
DELETE FROM accounts WHERE account_number = 1004;

-- Delete all inactive accounts
DELETE FROM accounts WHERE active = FALSE;
```

**WARNING**: Always use `WHERE` with `DELETE`! Without it, ALL rows are deleted:
```sql
-- DANGEROUS: deletes ALL accounts!
DELETE FROM accounts;
```

### CRUD maps to REST

| SQL | REST | HTTP Method | Banking Example |
|-----|------|------------|----------------|
| `INSERT INTO` | Create | `POST /api/accounts` | New account |
| `SELECT` | Read | `GET /api/accounts/1001` | View account |
| `UPDATE` | Update | `PUT /api/accounts/1001` | Modify account |
| `DELETE FROM` | Delete | `DELETE /api/accounts/1001` | Remove account |

On Day 12, we'll connect these SQL operations to our Java code using JDBC.

---

## Exercises

### Exercise 1: Transaction Form Integration

**Problem**: Connect the `transaction.html` form to the REST API using JavaScript. When the user selects "Deposit" and clicks submit, call `POST /api/accounts/{accNo}/deposit?amount=X`. For "Withdraw", call the withdraw endpoint. Show success/error messages.

<details>
<summary><strong>Solution</strong></summary>

**transaction.js:**

```javascript
const API_BASE = "http://localhost:8080/api/accounts";

document.getElementById("txnForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const accNo = document.getElementById("accNo").value;
    const amount = document.getElementById("amount").value;
    const txnType = document.querySelector('input[name="txnType"]:checked').value;

    const url = `${API_BASE}/${accNo}/${txnType}?amount=${amount}`;

    fetch(url, { method: "POST" })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else if (response.status === 404) {
                throw new Error("Account not found");
            } else {
                return response.json().then(err => {
                    throw new Error(err.error || "Transaction failed");
                });
            }
        })
        .then(account => {
            const action = txnType === "deposit" ? "Deposited" : "Withdrawn";
            showMessage(
                `${action} Rs.${parseFloat(amount).toLocaleString()} | New Balance: Rs.${account.balance.toLocaleString()}`,
                "green"
            );
            document.getElementById("txnForm").reset();
        })
        .catch(error => {
            showMessage(error.message, "red");
        });
});

function showMessage(text, color) {
    const msg = document.getElementById("message");
    msg.textContent = text;
    msg.style.color = color;
}
```

**Update transaction.html** — add `id="txnForm"` to the form, remove `action`/`method`, add message div and script link.
</details>

---

### Exercise 2: Search Page Integration

**Problem**: Create a `search.html` page with a search input. When the user types a name and clicks Search, call `GET /api/accounts/search?name=X` and display the results in a table.

<details>
<summary><strong>Solution</strong></summary>

**search.js:**

```javascript
const API_BASE = "http://localhost:8080/api/accounts";

document.getElementById("searchForm").addEventListener("submit", function(event) {
    event.preventDefault();
    const keyword = document.getElementById("searchInput").value;

    fetch(`${API_BASE}/search?name=${encodeURIComponent(keyword)}`)
        .then(response => response.json())
        .then(accounts => {
            const tbody = document.getElementById("resultsBody");
            tbody.innerHTML = "";

            if (accounts.length === 0) {
                tbody.innerHTML = '<tr><td colspan="4" style="text-align:center;">No results found</td></tr>';
                document.getElementById("resultCount").textContent = "0 results";
                return;
            }

            document.getElementById("resultCount").textContent = accounts.length + " result(s)";

            accounts.forEach(acc => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${acc.accountNumber}</td>
                    <td>${acc.holderName}</td>
                    <td>${acc.type}</td>
                    <td>${acc.balance.toLocaleString("en-IN", {minimumFractionDigits: 2})}</td>
                `;
                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error("Search error:", error);
        });
});
```

**search.html** needs: `id="searchForm"` on the form, `id="searchInput"` on the text input, `id="resultsBody"` on the tbody, `id="resultCount"` for showing count.
</details>

---

### Exercise 3: SQL — Account Queries

**Problem**: Write SQL queries for the following. Assume the accounts and transactions tables are created and populated with sample data.

1. Find all savings accounts with balance > Rs.40,000
2. Find the total balance across all accounts
3. Find the account with the highest balance
4. Count how many accounts of each type exist
5. Find all transactions for account #1001, ordered by date (most recent first)

<details>
<summary><strong>Solution</strong></summary>

```sql
-- 1. Savings accounts with balance > 40000
SELECT * FROM accounts WHERE type = 'SAVINGS' AND balance > 40000;

-- 2. Total balance
SELECT SUM(balance) AS total_balance FROM accounts;

-- 3. Account with highest balance
SELECT * FROM accounts ORDER BY balance DESC LIMIT 1;
-- Alternative:
SELECT * FROM accounts WHERE balance = (SELECT MAX(balance) FROM accounts);

-- 4. Count by type
SELECT type, COUNT(*) AS count FROM accounts GROUP BY type;

-- 5. Transactions for account 1001, newest first
SELECT * FROM transactions
WHERE account_number = 1001
ORDER BY timestamp DESC;
```
</details>

---

### Exercise 4: SQL — Transaction Queries

**Problem**: Write SQL queries to:

1. Insert 3 transactions for account #1002: deposit Rs.5000, deposit Rs.8000, withdraw Rs.3000
2. Find the total amount deposited across all accounts
3. Find the total amount withdrawn from account #1001
4. Find accounts that have no transactions

<details>
<summary><strong>Solution</strong></summary>

```sql
-- 1. Insert transactions for account 1002
INSERT INTO transactions (type, amount, account_number, balance_before, balance_after) VALUES
('DEPOSIT', 5000, 1002, 30000, 35000),
('DEPOSIT', 8000, 1002, 35000, 43000),
('WITHDRAW', 3000, 1002, 43000, 40000);

-- 2. Total deposited across all accounts
SELECT SUM(amount) AS total_deposited
FROM transactions
WHERE type = 'DEPOSIT';

-- 3. Total withdrawn from account 1001
SELECT SUM(amount) AS total_withdrawn
FROM transactions
WHERE account_number = 1001 AND type = 'WITHDRAW';

-- 4. Accounts with no transactions (using NOT IN)
SELECT * FROM accounts
WHERE account_number NOT IN (
    SELECT DISTINCT account_number FROM transactions
);

-- Alternative using LEFT JOIN:
SELECT a.*
FROM accounts a
LEFT JOIN transactions t ON a.account_number = t.account_number
WHERE t.id IS NULL;
```
</details>

---

### Exercise 5: Full Integration — Dashboard

**Problem**: Create a `dashboard.html` + `dashboard.js` that on page load:
1. Fetches all accounts from the API
2. Displays total accounts and total balance in summary cards
3. Shows all accounts in a table
4. Has a "Refresh" button that re-fetches everything

<details>
<summary><strong>Solution</strong></summary>

**dashboard.js:**

```javascript
const API_BASE = "http://localhost:8080/api/accounts";

window.addEventListener("DOMContentLoaded", loadDashboard);

function loadDashboard() {
    fetch(API_BASE)
        .then(response => response.json())
        .then(accounts => {
            // Update summary cards
            document.getElementById("totalAccounts").textContent = accounts.length;

            const totalBalance = accounts.reduce((sum, acc) => sum + acc.balance, 0);
            document.getElementById("totalBalance").textContent =
                "Rs." + totalBalance.toLocaleString("en-IN", {minimumFractionDigits: 2});

            // Update table
            const tbody = document.getElementById("accountsBody");
            tbody.innerHTML = "";

            accounts.forEach(acc => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${acc.accountNumber}</td>
                    <td>${acc.holderName}</td>
                    <td>${acc.type}</td>
                    <td>${acc.balance.toLocaleString("en-IN", {minimumFractionDigits: 2})}</td>
                    <td><span class="badge badge-${acc.active ? 'active' : 'inactive'}">
                        ${acc.active ? 'Active' : 'Inactive'}</span></td>
                `;
                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error("Dashboard error:", error);
        });
}

// Refresh button
document.getElementById("refreshBtn").addEventListener("click", loadDashboard);
```

**dashboard.html** needs: summary cards with `id="totalAccounts"` and `id="totalBalance"`, a table with `id="accountsBody"`, and a button with `id="refreshBtn"`.
</details>

---

## Quick Quiz

1. What does `event.preventDefault()` do in a form submit handler?
2. What is `fetch()` and what does it return?
3. Why do we need `JSON.stringify()` when sending data with fetch?
4. What is CORS and how did we fix it?
5. What does RDBMS stand for?
6. What is a primary key? Can it be null?
7. What is a foreign key? What does it enforce?
8. Write a SQL query to find all accounts with balance > 50000.
9. What's the danger of `UPDATE accounts SET active = FALSE;` without a WHERE clause?
10. How does SQL CRUD map to REST HTTP methods?

<details>
<summary><strong>Answers</strong></summary>

1. **`event.preventDefault()`** stops the browser's default form submission behavior (which causes a full page reload). This lets our JavaScript handle the submission instead using `fetch()`.

2. **`fetch()`** is a JavaScript function that sends HTTP requests (like Postman, but from code). It returns a **Promise** — you chain `.then()` to handle the response when it arrives.

3. **`JSON.stringify()`** converts a JavaScript object (`{name: "Ravi"}`) into a JSON string (`'{"name":"Ravi"}'`). HTTP bodies are text, so the object must be serialized to a string before sending.

4. **CORS** (Cross-Origin Resource Sharing) is a browser security feature that blocks requests from one origin (e.g., `file:///`) to another (e.g., `localhost:8080`). We fixed it by adding `@CrossOrigin(origins = "*")` to the Spring Boot controller, telling the server to accept requests from any origin.

5. **Relational Database Management System** — a database system that stores data in tables with rows and columns, supporting relationships between tables.

6. A **primary key** uniquely identifies each row in a table. It **cannot be null** and must be unique. In our banking app, `account_number` is the PK of the accounts table.

7. A **foreign key** is a column that references the primary key of another table. It enforces **referential integrity** — you can't insert a transaction with `account_number = 9999` if account #9999 doesn't exist in the accounts table.

8. `SELECT * FROM accounts WHERE balance > 50000;`

9. Without `WHERE`, the `UPDATE` modifies **every row** in the table. All accounts would be deactivated — not just the intended one. Always use `WHERE` with UPDATE and DELETE.

10. INSERT → POST (Create), SELECT → GET (Read), UPDATE → PUT (Update), DELETE → DELETE (Delete). The concepts are identical; only the syntax differs.
</details>

---

## What We Built Today

### Part A — Integration

| Step | What we built | Key Concepts |
|------|--------------|-------------|
| JavaScript basics | DOM access, event listeners | `getElementById`, `addEventListener` |
| fetch() GET | Load live data into table | `response.json()`, dynamic HTML |
| fetch() POST | Submit form to API | `JSON.stringify`, `Content-Type` header |
| fetch() DELETE | Delete from table | Status code checking, page refresh |
| CORS fix | `@CrossOrigin` annotation | Browser security |
| Form flow | preventDefault → collect → fetch → update | Modern web pattern |

### Part B — SQL

| Step | What we learned | Key SQL |
|------|---------------|---------|
| Tables & types | RDBMS structure | `CREATE TABLE`, data types |
| Primary key | Unique row identity | `PRIMARY KEY`, `NOT NULL` |
| Foreign key | Table relationships | `FOREIGN KEY ... REFERENCES` |
| INSERT | Add rows | `INSERT INTO ... VALUES` |
| SELECT | Query rows | `WHERE`, `ORDER BY`, `GROUP BY`, aggregates |
| UPDATE | Modify rows | `SET ... WHERE` |
| DELETE | Remove rows | `DELETE FROM ... WHERE` |

### Full-stack architecture

```
Day 1-5:    Java console app (business logic)
Day 7:      + File persistence (CSV, serialization)
Day 8:      + REST API (Spring Boot — HTTP endpoints)
Day 9:      + HTML/CSS (front-end pages)
Day 11:     + JavaScript (connects front-end ↔ back-end)
            + SQL (database schema design)
Day 12:     → JDBC (connects Java ↔ database)
```

## What's Next (Day 12 Preview)

We can design SQL tables and write queries. But how does our Java code execute those queries? Tomorrow we learn **JDBC** (Java Database Connectivity) — the bridge between Java and SQL databases. Our `AccountService` will store data in a real database instead of in-memory collections.
