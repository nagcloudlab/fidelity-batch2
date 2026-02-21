# Day 8 — REST API & Spring Boot

## Instructor Guide

> **Duration**: Full day
> **Pre-requisite**: Day 7 BankApp (OOP + Collections + File I/O). No Spring experience needed.
> **Case Study**: Expose banking operations as REST API endpoints using Spring Boot
> **Goal by end of day**: Trainees can create a Spring Boot project, build CRUD REST endpoints, handle path/query params, and test with Postman/curl

---

## Important Setup Note

> Before class begins, ensure all trainees have:
> 1. **JDK 17+** installed
> 2. **IDE**: IntelliJ IDEA (preferred) or VS Code with Java extensions
> 3. **Postman** installed (or they can use curl)
> 4. **Internet access** for downloading Spring Boot dependencies (Maven/Gradle)
>
> Generate the starter project at [start.spring.io](https://start.spring.io) ahead of time as a backup in case of network issues.

---

## Topic 1: REST Architecture Basics

#### Key Points (15 min)

- **REST** = REpresentational State Transfer — an architectural style for building web APIs.
- Not a protocol (like SOAP) — it's a set of **constraints/guidelines**.
- **Core ideas**:
  - Everything is a **resource** (Account, Transaction, Customer)
  - Each resource has a **URI** (Uniform Resource Identifier): `/api/accounts/1001`
  - Clients interact with **representations** of resources (JSON, XML)
  - Communication is **stateless** — each request contains everything the server needs
  - **Client-server** separation — front-end and back-end are independent

#### REST vs SOAP (quick)

| | REST | SOAP |
|---|---|---|
| Format | JSON (lightweight) | XML (verbose) |
| Protocol | HTTP | Any (HTTP, SMTP, etc.) |
| Style | Architectural guidelines | Strict protocol/standard |
| Simplicity | Simple, flexible | Complex, rigid |
| Modern usage | Dominant for web APIs | Legacy/enterprise |

> "99% of modern web APIs are REST. That's what we'll build."

#### Resources and URIs

| Resource | URI | Meaning |
|----------|-----|---------|
| All accounts | `GET /api/accounts` | List all accounts |
| Specific account | `GET /api/accounts/1001` | Get account #1001 |
| Create account | `POST /api/accounts` | Create a new account |
| Update account | `PUT /api/accounts/1001` | Update account #1001 |
| Delete account | `DELETE /api/accounts/1001` | Delete account #1001 |

#### Idempotent vs Non-idempotent

- **Idempotent**: Calling it multiple times gives the same result. `GET`, `PUT`, `DELETE` are idempotent.
- **Non-idempotent**: Each call may produce a different result. `POST` is non-idempotent (each call creates a new resource).

#### Teaching Tip

> "Think of REST like a restaurant. The menu (API docs) lists what's available. You (client) send an order (request) to the kitchen (server). The kitchen sends back food (response). You don't know HOW the kitchen makes it — you just get what you asked for."

---

## Topic 2: HTTP Essentials for REST

#### Key Points (15 min)

#### HTTP Methods

| Method | Purpose | Idempotent | Banking Example |
|--------|---------|-----------|----------------|
| `GET` | Read / retrieve | Yes | Get account details |
| `POST` | Create new | No | Create new account |
| `PUT` | Update (full replace) | Yes | Update entire account |
| `PATCH` | Update (partial) | Yes | Update just the name |
| `DELETE` | Remove | Yes | Close account |

#### HTTP Status Codes

| Range | Category | Common Codes |
|-------|----------|-------------|
| 2xx | Success | `200 OK`, `201 Created`, `204 No Content` |
| 4xx | Client error | `400 Bad Request`, `404 Not Found`, `409 Conflict` |
| 5xx | Server error | `500 Internal Server Error` |

| Code | When to use | Banking example |
|------|------------|----------------|
| `200 OK` | Successful read/update | Account retrieved successfully |
| `201 Created` | Resource created | New account created |
| `204 No Content` | Success, nothing to return | Account deleted |
| `400 Bad Request` | Invalid input | Negative deposit amount |
| `404 Not Found` | Resource doesn't exist | Account #9999 not found |
| `409 Conflict` | Business rule violation | Insufficient balance |

#### Request/Response Structure

```
Request:
┌─────────────────────────────────────┐
│ POST /api/accounts HTTP/1.1         │  ← method + URI
│ Content-Type: application/json      │  ← headers
│ Host: localhost:8080                │
│                                     │
│ {                                   │  ← body (JSON)
│   "holderName": "Ravi Kumar",       │
│   "accountNumber": 1001,            │
│   "balance": 50000,                 │
│   "type": "SAVINGS",                │
│   "interestRate": 4.5               │
│ }                                   │
└─────────────────────────────────────┘

Response:
┌─────────────────────────────────────┐
│ HTTP/1.1 201 Created                │  ← status code
│ Content-Type: application/json      │  ← headers
│                                     │
│ {                                   │  ← body (JSON)
│   "accountNumber": 1001,            │
│   "holderName": "Ravi Kumar",       │
│   "balance": 50000.0,               │
│   "type": "SAVINGS"                 │
│ }                                   │
└─────────────────────────────────────┘
```

#### JSON as REST payload

```json
{
  "accountNumber": 1001,
  "holderName": "Ravi Kumar",
  "balance": 50000.00,
  "type": "SAVINGS",
  "active": true
}
```

- JSON = JavaScript Object Notation — lightweight key-value format
- Spring Boot automatically converts Java objects ↔ JSON (using Jackson library)

---

## Topic 3: Spring Boot REST Introduction

#### Key Points (20 min)

- **Spring Boot** = a framework that makes building Java web apps fast and easy.
- **Why Spring Boot**:
  - Auto-configuration — sensible defaults, minimal setup
  - Embedded server (Tomcat) — no need to install a separate server
  - Starter dependencies — one dependency pulls in everything you need
  - Production-ready — logging, health checks, metrics built-in

#### Project Structure

```
bank-api/
├── src/
│   ├── main/
│   │   ├── java/com/bank/api/
│   │   │   ├── BankApiApplication.java     ← entry point
│   │   │   ├── controller/
│   │   │   │   └── AccountController.java  ← REST endpoints
│   │   │   ├── model/
│   │   │   │   ├── Account.java            ← data model
│   │   │   │   ├── SavingsAccount.java
│   │   │   │   └── CurrentAccount.java
│   │   │   └── service/
│   │   │       └── AccountService.java     ← business logic
│   │   └── resources/
│   │       └── application.properties      ← config
│   └── test/
├── pom.xml                                  ← Maven dependencies
```

#### Hands-on: Create the project

> **Option A** — Spring Initializr (recommended):
> 1. Go to [start.spring.io](https://start.spring.io)
> 2. Settings:
>    - Project: Maven
>    - Language: Java
>    - Spring Boot: 3.x (latest stable)
>    - Group: `com.bank`
>    - Artifact: `bank-api`
>    - Packaging: Jar
>    - Java: 17
> 3. Dependencies: **Spring Web**
> 4. Click Generate → download → extract → open in IDE

> **Option B** — If internet is limited, have the zip ready on a shared drive.

#### First endpoint — Hello World

```java
package com.bank.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Welcome to Bank API!";
    }
}
```

Run the app → Open browser → `http://localhost:8080/hello` → See "Welcome to Bank API!"

> **Teaching moment**: "One class, one annotation, one method — and you have a working web API. Spring Boot handles the server, routing, and HTTP. You focus on business logic."

#### Test with curl:

```bash
curl http://localhost:8080/hello
```

---

## Topic 4: REST Controllers

#### Key Points (30 min)

#### Annotations Overview

| Annotation | Purpose |
|-----------|---------|
| `@RestController` | Marks a class as a REST controller (combines `@Controller` + `@ResponseBody`) |
| `@RequestMapping("/api/accounts")` | Base URL path for all endpoints in this controller |
| `@GetMapping` | Handles GET requests |
| `@PostMapping` | Handles POST requests |
| `@PutMapping` | Handles PUT requests |
| `@DeleteMapping` | Handles DELETE requests |
| `@PathVariable` | Extracts value from URL path: `/accounts/{id}` |
| `@RequestParam` | Extracts query parameter: `/accounts?type=SAVINGS` |
| `@RequestBody` | Extracts JSON body and converts to Java object |

#### Case Study Step 1 — Account model (simplified for REST)

```java
package com.bank.api.model;

public class Account {
    private long accountNumber;
    private String holderName;
    private double balance;
    private String type;         // "SAVINGS" or "CURRENT"
    private boolean active;

    // Default constructor (required by Jackson for JSON deserialization)
    public Account() {}

    public Account(long accountNumber, String holderName, double balance, String type) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
        this.type = type;
        this.active = true;
    }

    // Getters and setters (ALL needed for JSON conversion)
    public long getAccountNumber() { return accountNumber; }
    public void setAccountNumber(long accountNumber) { this.accountNumber = accountNumber; }

    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
```

> **Note**: For REST APIs, we use a simpler flat model (not the abstract hierarchy from Day 2). The `type` field distinguishes account types. In a real app, you'd use DTOs (Data Transfer Objects) to decouple the API model from the internal domain model.

#### Case Study Step 2 — AccountService (business logic)

```java
package com.bank.api.service;

import com.bank.api.model.Account;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    private List<Account> accounts = new ArrayList<>();
    private Map<Long, Account> accountMap = new HashMap<>();

    public AccountService() {
        // Pre-load sample data
        addAccount(new Account(1001L, "Ravi Kumar", 50000, "SAVINGS"));
        addAccount(new Account(1002L, "Priya Sharma", 30000, "CURRENT"));
        addAccount(new Account(1003L, "Amit Verma", 75000, "SAVINGS"));
    }

    public List<Account> getAllAccounts() {
        return accounts;
    }

    public Account getByAccountNumber(long accountNumber) {
        return accountMap.get(accountNumber);
    }

    public Account addAccount(Account account) {
        if (accountMap.containsKey(account.getAccountNumber())) {
            return null;    // duplicate
        }
        account.setActive(true);
        accounts.add(account);
        accountMap.put(account.getAccountNumber(), account);
        return account;
    }

    public Account updateAccount(long accountNumber, Account updated) {
        Account existing = accountMap.get(accountNumber);
        if (existing == null) return null;

        existing.setHolderName(updated.getHolderName());
        existing.setType(updated.getType());
        return existing;
    }

    public boolean deleteAccount(long accountNumber) {
        Account acc = accountMap.remove(accountNumber);
        if (acc == null) return false;
        accounts.remove(acc);
        return true;
    }

    public Account deposit(long accountNumber, double amount) {
        Account acc = accountMap.get(accountNumber);
        if (acc == null) return null;
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        acc.setBalance(acc.getBalance() + amount);
        return acc;
    }

    public Account withdraw(long accountNumber, double amount) {
        Account acc = accountMap.get(accountNumber);
        if (acc == null) return null;
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (amount > acc.getBalance()) throw new IllegalArgumentException("Insufficient balance");
        acc.setBalance(acc.getBalance() - amount);
        return acc;
    }

    public List<Account> searchByName(String keyword) {
        List<Account> results = new ArrayList<>();
        for (Account acc : accounts) {
            if (acc.getHolderName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(acc);
            }
        }
        return results;
    }

    public List<Account> filterByType(String type) {
        List<Account> results = new ArrayList<>();
        for (Account acc : accounts) {
            if (acc.getType().equalsIgnoreCase(type)) {
                results.add(acc);
            }
        }
        return results;
    }
}
```

#### Case Study Step 3 — AccountController (REST endpoints)

```java
package com.bank.api.controller;

import com.bank.api.model.Account;
import com.bank.api.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // GET /api/accounts — list all
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // GET /api/accounts/1001 — get by account number
    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable long accountNumber) {
        Account acc = accountService.getByAccountNumber(accountNumber);
        if (acc == null) {
            return ResponseEntity.notFound().build();    // 404
        }
        return ResponseEntity.ok(acc);                   // 200
    }

    // POST /api/accounts — create new
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        Account created = accountService.addAccount(account);
        if (created == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Account #" + account.getAccountNumber() + " already exists"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(created);    // 201
    }

    // PUT /api/accounts/1001 — update
    @PutMapping("/{accountNumber}")
    public ResponseEntity<?> updateAccount(@PathVariable long accountNumber,
                                            @RequestBody Account account) {
        Account updated = accountService.updateAccount(accountNumber, account);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/accounts/1001 — delete
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<?> deleteAccount(@PathVariable long accountNumber) {
        boolean deleted = accountService.deleteAccount(accountNumber);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();    // 204
    }

    // POST /api/accounts/1001/deposit?amount=5000
    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<?> deposit(@PathVariable long accountNumber,
                                      @RequestParam double amount) {
        try {
            Account acc = accountService.deposit(accountNumber, amount);
            if (acc == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(acc);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // POST /api/accounts/1001/withdraw?amount=2000
    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable long accountNumber,
                                       @RequestParam double amount) {
        try {
            Account acc = accountService.withdraw(accountNumber, amount);
            if (acc == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(acc);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // GET /api/accounts/search?name=kumar
    @GetMapping("/search")
    public List<Account> searchByName(@RequestParam String name) {
        return accountService.searchByName(name);
    }

    // GET /api/accounts?type=SAVINGS (query param filtering)
    @GetMapping(params = "type")
    public List<Account> filterByType(@RequestParam String type) {
        return accountService.filterByType(type);
    }
}
```

---

## Topic 5: Response Handling

#### Key Points (15 min)

#### POJO → JSON (automatic)

```java
// Return a Java object — Spring auto-converts to JSON
@GetMapping("/{id}")
public Account getAccount(@PathVariable long id) {
    return accountService.getByAccountNumber(id);
}
// Response body: {"accountNumber":1001,"holderName":"Ravi Kumar",...}
```

> Spring uses **Jackson** library to convert. It reads getters to determine JSON field names. `getHolderName()` → `"holderName"` in JSON.

#### ResponseEntity — full control

```java
// Control status code, headers, and body
@GetMapping("/{id}")
public ResponseEntity<Account> getAccount(@PathVariable long id) {
    Account acc = accountService.getByAccountNumber(id);
    if (acc == null) {
        return ResponseEntity.notFound().build();       // 404, no body
    }
    return ResponseEntity.ok(acc);                      // 200, with body
}

// 201 Created with body
return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);

// 204 No Content
return ResponseEntity.noContent().build();

// 400 Bad Request with error message
return ResponseEntity.badRequest().body(Map.of("error", "Invalid amount"));
```

---

## Testing with Postman / curl

#### Postman walkthrough (demo in class)

1. **GET all accounts**: `GET http://localhost:8080/api/accounts`
2. **GET one**: `GET http://localhost:8080/api/accounts/1001`
3. **CREATE**: `POST http://localhost:8080/api/accounts` with JSON body
4. **DEPOSIT**: `POST http://localhost:8080/api/accounts/1001/deposit?amount=5000`
5. **WITHDRAW**: `POST http://localhost:8080/api/accounts/1001/withdraw?amount=2000`
6. **SEARCH**: `GET http://localhost:8080/api/accounts/search?name=kumar`
7. **DELETE**: `DELETE http://localhost:8080/api/accounts/1001`

#### curl equivalents

```bash
# GET all
curl http://localhost:8080/api/accounts

# GET one
curl http://localhost:8080/api/accounts/1001

# CREATE (POST with JSON body)
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountNumber":1004,"holderName":"Sneha Reddy","balance":100000,"type":"CURRENT"}'

# DEPOSIT
curl -X POST "http://localhost:8080/api/accounts/1001/deposit?amount=5000"

# WITHDRAW
curl -X POST "http://localhost:8080/api/accounts/1001/withdraw?amount=2000"

# SEARCH by name
curl "http://localhost:8080/api/accounts/search?name=kumar"

# FILTER by type
curl "http://localhost:8080/api/accounts?type=SAVINGS"

# DELETE
curl -X DELETE http://localhost:8080/api/accounts/1001

# UPDATE (PUT)
curl -X PUT http://localhost:8080/api/accounts/1002 \
  -H "Content-Type: application/json" \
  -d '{"holderName":"Priya Sharma Updated","type":"CURRENT"}'
```

---

## Day 8 Exercises

### Exercise 1: Transfer Endpoint
**Problem**: Add `POST /api/accounts/transfer?from=1001&to=1002&amount=5000` that transfers money between accounts. Return both updated accounts in the response.

### Exercise 2: Validation and Error Responses
**Problem**: Add proper validation to the `createAccount` endpoint:
- holderName must not be empty
- accountNumber must be positive
- balance must be >= 0
- type must be "SAVINGS" or "CURRENT"
Return `400 Bad Request` with a JSON error listing all validation failures.

### Exercise 3: Account Summary Endpoint
**Problem**: Add `GET /api/accounts/summary` that returns:
```json
{
  "totalAccounts": 4,
  "totalBalance": 275000.00,
  "averageBalance": 68750.00,
  "savingsCount": 2,
  "currentCount": 2
}
```

### Exercise 4: Transaction Endpoints
**Problem**: Add a `Transaction` model and endpoints:
- `GET /api/accounts/{accNo}/transactions` — list transactions for an account
- `GET /api/transactions` — list all transactions
- Record transactions automatically on deposit/withdraw

### Exercise 5: Full Challenge — Complete Banking API
**Problem**: Build a full API with these additional endpoints:
- `PATCH /api/accounts/{accNo}` — partial update (only fields provided)
- `POST /api/accounts/{accNo}/deactivate` — deactivate account
- `POST /api/accounts/{accNo}/activate` — reactivate account
- `GET /api/accounts?minBalance=50000&maxBalance=100000` — filter by balance range

---

## Day 8 Quiz (8 questions)

1. What does REST stand for?
2. Which HTTP method is used to create a new resource?
3. What status code should you return when a resource is created?
4. What is the difference between `@PathVariable` and `@RequestParam`?
5. What annotation makes a class a REST controller in Spring?
6. What does `@RequestBody` do?
7. What is the difference between `PUT` and `PATCH`?
8. Why do we need a default (no-arg) constructor in the Account model for REST?

---

## Day 8 Summary

| Step | What we built | Key Annotations |
|------|--------------|----------------|
| REST concepts | Understood resources, URIs, HTTP methods, status codes | — |
| Project setup | Created Spring Boot project with Spring Web | `@SpringBootApplication` |
| Account model | Flat POJO with getters/setters | — |
| AccountService | Business logic (in-memory) | `@Service` |
| AccountController | CRUD endpoints | `@RestController`, `@GetMapping`, `@PostMapping`, etc. |
| Path params | `/accounts/{accountNumber}` | `@PathVariable` |
| Query params | `/accounts/search?name=kumar` | `@RequestParam` |
| Request body | POST/PUT with JSON | `@RequestBody` |
| Response control | Status codes, error bodies | `ResponseEntity` |

### Console App vs REST API

| | Console App (Days 1-7) | REST API (Day 8) |
|---|---|---|
| User interface | Terminal menu | HTTP endpoints |
| Client | Single user at keyboard | Any HTTP client (browser, Postman, mobile app) |
| Data format | Text output | JSON |
| Access | Local only | Network accessible |
| Multiple users | No | Yes (stateless) |

> **Preview for Day 9**: "We have a REST API backend. But how do users interact with it? They won't use Postman! Tomorrow we build the front-end — HTML pages with forms to create accounts, make deposits, and view balances."
