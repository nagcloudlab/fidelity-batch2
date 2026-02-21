# Day 10 — Data Structures and HTML Assessment

## Assessment Paper (Instructor Copy — with Answer Key)

> **Duration**: ~4 hours
> **Total Marks**: 120
> **Coverage**: Days 5–9 (Data Structures, File Handling, REST API & Spring Boot, HTML & CSS)
> **Instructions**: Answer all questions. Coding sections must be compilable Java. Web page sections must render correctly in a browser.

---

## Section A: Multiple Choice (20 marks)

*Choose the single best answer. 1 mark each.*

---

**Q1.** A Stack follows which principle?

a) FIFO — First In, First Out
b) LIFO — Last In, First Out
c) Random access
d) Priority-based

---

**Q2.** What does `stack.peek()` do?

a) Removes and returns the top element
b) Returns the top element without removing it
c) Returns the bottom element
d) Returns the stack size

---

**Q3.** What does `queue.poll()` return when the queue is empty?

a) Throws `EmptyQueueException`
b) Returns 0
c) Returns `null`
d) Returns the last element added

---

**Q4.** Which data structure is best for an "undo" feature?

a) ArrayList
b) Queue
c) Stack
d) HashMap

---

**Q5.** What is the main difference between `ArrayList` and `LinkedList` for insertion at the beginning?

a) ArrayList is faster (O(1))
b) LinkedList is faster (O(1)) because it just updates node pointers
c) They have the same performance
d) Neither supports insertion at the beginning

---

**Q6.** What does `BufferedReader.readLine()` return at the end of a file?

a) An empty string `""`
b) The string `"EOF"`
c) `null`
d) Throws `EndOfFileException`

---

**Q7.** What interface must a class implement to be saved using `ObjectOutputStream`?

a) `Saveable`
b) `Persistable`
c) `Serializable`
d) `Writable`

---

**Q8.** What does the `transient` keyword do?

a) Makes a field thread-safe
b) Excludes the field from serialization
c) Makes a field immutable
d) Makes a field accessible from other packages

---

**Q9.** What is the advantage of try-with-resources over try-finally?

a) It catches more types of exceptions
b) It automatically closes resources when the block exits
c) It runs faster
d) It doesn't require a catch block

---

**Q10.** What does REST stand for?

a) Representational State Transfer
b) Remote Execution Service Technology
c) Reliable Server Transport
d) Resource Exchange Standard Template

---

**Q11.** Which HTTP method is used to create a new resource?

a) GET
b) POST
c) PUT
d) DELETE

---

**Q12.** What status code should a REST API return when a resource is successfully created?

a) 200 OK
b) 201 Created
c) 204 No Content
d) 301 Moved Permanently

---

**Q13.** What is the difference between `@PathVariable` and `@RequestParam`?

a) `@PathVariable` reads query params; `@RequestParam` reads URL path segments
b) `@PathVariable` extracts from URL path (`/accounts/{id}`); `@RequestParam` extracts query params (`?name=ravi`)
c) They are interchangeable
d) `@PathVariable` is for POST only; `@RequestParam` is for GET only

---

**Q14.** What does `@RequestBody` do in a Spring Boot controller?

a) Returns the response body as a string
b) Converts the JSON request body into a Java object
c) Sets the HTTP status code
d) Reads a file from the request

---

**Q15.** Why does the Account REST model need a no-arg (default) constructor?

a) Spring Boot requires it for dependency injection
b) Jackson needs it to create an empty object before populating fields from JSON
c) It's required by the Java language specification
d) It prevents null pointer exceptions

---

**Q16.** In HTML, what is the difference between `<div>` and `<span>`?

a) `<div>` is inline; `<span>` is block-level
b) `<div>` is block-level (takes full width); `<span>` is inline (stays in the text flow)
c) They are identical
d) `<div>` is only for text; `<span>` is only for images

---

**Q17.** What does the `action` attribute in an HTML `<form>` specify?

a) The JavaScript function to call
b) The CSS class to apply
c) The URL where form data is sent
d) The name of the form

---

**Q18.** What is the difference between radio buttons and checkboxes?

a) Radio: multiple selections; Checkbox: single selection
b) Radio: single selection from a group; Checkbox: multiple independent selections
c) They are the same thing with different styling
d) Radio: for text input; Checkbox: for boolean values

---

**Q19.** In CSS, what does `border-collapse: collapse` do on a table?

a) Hides all borders
b) Makes borders thicker
c) Merges adjacent cell borders into a single border
d) Rounds the border corners

---

**Q20.** What is the CSS box model order from inside to outside?

a) Margin → Border → Padding → Content
b) Content → Border → Padding → Margin
c) Content → Padding → Border → Margin
d) Padding → Content → Border → Margin

---

### Section A — Answer Key

| Q | Answer | Explanation |
|---|--------|-------------|
| 1 | b | Stack = LIFO — Last In, First Out |
| 2 | b | `peek()` looks at the top without removing; `pop()` removes |
| 3 | c | `poll()` returns null safely; `remove()` would throw |
| 4 | c | Undo reverses the most recent action first = LIFO = Stack |
| 5 | b | LinkedList updates pointers O(1); ArrayList shifts all elements O(n) |
| 6 | c | Returns `null` to signal end of file |
| 7 | c | `Serializable` is the marker interface for Java serialization |
| 8 | b | `transient` fields are skipped during serialization; get defaults on deserialization |
| 9 | b | try-with-resources auto-closes anything implementing `AutoCloseable` |
| 10 | a | REpresentational State Transfer |
| 11 | b | POST creates a new resource |
| 12 | b | 201 Created — includes the created resource in the response body |
| 13 | b | PathVariable = URL path segment; RequestParam = query string parameter |
| 14 | b | Jackson deserializes JSON body into a Java object |
| 15 | b | Jackson creates empty object with no-arg constructor, then calls setters |
| 16 | b | `<div>` is block (new line, full width); `<span>` is inline (within text) |
| 17 | c | `action` = the URL endpoint where the form submission is sent |
| 18 | b | Radio = one choice from group (same name); Checkbox = independent toggles |
| 19 | c | Without it, adjacent cells each render their own border (double lines) |
| 20 | c | Content → Padding → Border → Margin (inside out) |

---

## Section B: Short Answer (30 marks)

*Answer in 2–4 sentences. 3 marks each.*

---

**Q1.** Why is Stack the right data structure for undo operations in our banking app?

**Q2.** What is the difference between `Queue.poll()` and `Queue.remove()`? When would you use each?

**Q3.** Explain the difference between in-memory data (Collections) and persistent data (Files). Why did we add file handling to the banking app?

**Q4.** What is the difference between CSV and Serialization for saving data? When would you choose each?

**Q5.** What does `serialVersionUID` do? What happens if you change a class but not the UID?

**Q6.** Explain the difference between GET and POST HTTP methods. Give a banking app example for each.

**Q7.** What is the role of `@Service` and `@RestController` in Spring Boot? Why do we separate them?

**Q8.** How does Jackson convert a Java object to JSON? What naming convention does it follow?

**Q9.** What is the difference between HTML and CSS? Give an analogy.

**Q10.** In CSS, what is the difference between a class selector (`.card`) and an ID selector (`#header`)? Which should you prefer for styling?

---

### Section B — Answer Key

**Q1.**
Undo must reverse the **most recent** action first (LIFO order). Stack's `pop()` gives the last pushed item, which is the last transaction. We stored `balanceBefore` in each Transaction so we can restore the balance when undoing. Queue (FIFO) would undo the oldest action first, which is incorrect.

**Q2.**
Both retrieve and remove the head of the queue. `poll()` returns **null** if the queue is empty — safe to use. `remove()` throws **NoSuchElementException** if the queue is empty. Use `poll()` in production code to avoid exceptions; use `remove()` when you are certain the queue is non-empty.

**Q3.**
**In-memory** data (ArrayList, HashMap) exists only while the program runs — it's lost when the program exits. **Persistent** data is saved to files (or databases) and survives program restarts. We added file handling so that accounts and transactions are saved when the user exits and loaded automatically on the next startup.

**Q4.**
**CSV** is human-readable text — you can open it in Notepad or Excel. It requires manual parsing (split, parse). Good for data exchange with other systems or languages. **Serialization** saves Java objects as binary — fast and automatic (one line: `writeObject`), but Java-only and unreadable by humans. Choose CSV for reports/portability, serialization for quick Java-only persistence.

**Q5.**
`serialVersionUID` is a version number for the class. During deserialization, Java compares the stored UID with the class's UID. If they match, it proceeds. If they don't (e.g., you added a field), Java throws `InvalidClassException`. You should update the UID when you make incompatible changes to the class structure.

**Q6.**
**GET** retrieves data without modifying anything — it's idempotent and safe. Example: `GET /api/accounts/1001` fetches Ravi's account. **POST** creates or modifies data — it's non-idempotent. Example: `POST /api/accounts` creates a new account. GET data appears in the URL; POST data is in the request body.

**Q7.**
`@Service` marks a class as business logic — it contains the rules (validation, calculations, data access). `@RestController` marks a class as an HTTP endpoint handler — it receives requests and returns responses. We separate them for **separation of concerns** — the controller handles HTTP, the service handles logic. This makes both easier to test and maintain.

**Q8.**
Jackson reads the **getter methods** of a Java object. It strips "get" and lowercases the first letter: `getHolderName()` → `"holderName"`, `getBalance()` → `"balance"`. For booleans, it strips "is": `isActive()` → `"active"`. No getter means the field is not included in the JSON output. A no-arg constructor + setters are needed for deserialization (JSON → Java).

**Q9.**
**HTML** defines the **structure and content** of a web page — headings, paragraphs, tables, forms, links. **CSS** controls the **presentation and appearance** — colors, fonts, spacing, layout. Analogy: HTML is the **skeleton** (bones and organs) of a building; CSS is the **decoration** (paint, furniture, lighting). You need HTML to have content; CSS makes it look good.

**Q10.**
**Class selector** (`.card`) matches **all elements** with that class — it's reusable across many elements on the page. **ID selector** (`#header`) matches the **one element** with that unique ID. You should **prefer classes** for styling because they're reusable and have lower specificity (easier to override). IDs should be reserved for unique elements that JavaScript needs to target.

---

## Section C: Code Reading & Debugging (25 marks)

*Read the code carefully and answer the questions. 5 marks each.*

---

### Q1. What is the output?

```java
Stack<String> stack = new Stack<>();
stack.push("A");
stack.push("B");
stack.push("C");
stack.push("D");

Queue<String> queue = new LinkedList<>();
while (!stack.isEmpty()) {
    queue.offer(stack.pop());
}

while (!queue.isEmpty()) {
    System.out.print(queue.poll() + " ");
}
```

---

### Q2. Find and fix the bug:

```java
public static void saveToCSV(List<Account> accounts, String filePath) {
    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
    writer.write("accountNumber,holderName,balance");
    writer.newLine();

    for (Account acc : accounts) {
        writer.write(acc.getAccountNumber() + "," + acc.getHolderName() + "," + acc.getBalance());
        writer.newLine();
    }
}
```

---

### Q3. What HTTP status code should be returned for each scenario?

Match each scenario to the correct status code:

| Scenario | Status Code? |
|----------|-------------|
| Account #1001 retrieved successfully | ___ |
| New account created | ___ |
| Account #9999 not found | ___ |
| Deposit with negative amount | ___ |
| Account deleted successfully (no body to return) | ___ |

Choose from: `200`, `201`, `204`, `400`, `404`

---

### Q4. Find the TWO bugs in this Spring Boot controller method:

```java
@GetMapping("/{accountNumber}")
public Account getAccount(@RequestParam long accountNumber) {
    Account acc = accountService.getByAccountNumber(accountNumber);
    return acc;
}
```

---

### Q5. What does this HTML render? Identify ONE error.

```html
<table>
    <thead>
        <tr>
            <td>Name</td>
            <td>Balance</td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <th>Ravi Kumar</th>
            <th>50000</th>
        </tr>
    </tbody>
</table>
```

---

### Section C — Answer Key

**Q1.** Output: `D C B A`

Stack pops in LIFO order: D, C, B, A. These are offered to the queue in that order. Queue polls in FIFO order: D, C, B, A. So the stack reversal is transferred to the queue, and the output is D C B A.

The net effect: Stack reverses the original push order (A→B→C→D becomes D→C→B→A), then Queue preserves that order.

(2 marks for correct output, 3 marks for explanation)

**Q2.** Two bugs:

**Bug 1 — No exception handling**: `FileWriter` throws `IOException`, but there's no try-catch or throws declaration.

**Bug 2 — Resource not closed**: If an exception occurs mid-write, the writer is never closed (no `close()` call, no try-with-resources).

Fix:
```java
public static void saveToCSV(List<Account> accounts, String filePath) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        writer.write("accountNumber,holderName,balance");
        writer.newLine();

        for (Account acc : accounts) {
            writer.write(acc.getAccountNumber() + "," + acc.getHolderName() + "," + acc.getBalance());
            writer.newLine();
        }
    }
}
```

(2.5 marks per bug)

**Q3.**

| Scenario | Status Code |
|----------|-------------|
| Account #1001 retrieved successfully | **200 OK** |
| New account created | **201 Created** |
| Account #9999 not found | **404 Not Found** |
| Deposit with negative amount | **400 Bad Request** |
| Account deleted successfully (no body) | **204 No Content** |

(1 mark each)

**Q4.** Two bugs:

**Bug 1 — Wrong annotation**: `@RequestParam` should be `@PathVariable`. The URL pattern `/{accountNumber}` has a path variable, not a query parameter.

**Bug 2 — No null check**: If the account doesn't exist, `getByAccountNumber()` returns `null`, and the method returns `null` (which Spring converts to a 200 with empty body). Should return 404.

Fix:
```java
@GetMapping("/{accountNumber}")
public ResponseEntity<Account> getAccount(@PathVariable long accountNumber) {
    Account acc = accountService.getByAccountNumber(accountNumber);
    if (acc == null) {
        return ResponseEntity.notFound().build();    // 404
    }
    return ResponseEntity.ok(acc);                   // 200
}
```

(2.5 marks per bug)

**Q5.**

The table renders with "Name" and "Balance" as header cells, and "Ravi Kumar" and "50000" as data cells — BUT the tags are swapped.

**Error**: `<td>` is used in `<thead>` (should be `<th>`) and `<th>` is used in `<tbody>` (should be `<td>`). The header cells should use `<th>` (bold, centered by default) and data cells should use `<td>`.

Fix:
```html
<thead>
    <tr>
        <th>Name</th>        <!-- was <td> -->
        <th>Balance</th>     <!-- was <td> -->
    </tr>
</thead>
<tbody>
    <tr>
        <td>Ravi Kumar</td>  <!-- was <th> -->
        <td>50000</td>       <!-- was <th> -->
    </tr>
</tbody>
```

(2 marks for identifying the tag swap, 3 marks for correct fix)

---

## Section D: Practical Assignments (45 marks)

*Write complete, compilable code. You may use an IDE and browser.*

---

### D1. File Persistence for Employee Data (15 marks)

Build a small employee management system with file persistence:

**Requirements:**

1. Create an `Employee` class with: `id` (int), `name` (String), `department` (String), `salary` (double). Include constructor, getters, and `toString()`.

2. Implement **CSV save and load**:
   - `saveEmployeesCSV(List<Employee> employees, String filePath)` — save to CSV with header row
   - `loadEmployeesCSV(String filePath)` — read CSV and return `List<Employee>`
   - Use try-with-resources for all file operations

3. Implement **Serialization save and load**:
   - Make `Employee` implement `Serializable`
   - `serializeEmployees(List<Employee> employees, String filePath)` — save using `ObjectOutputStream`
   - `deserializeEmployees(String filePath)` — load using `ObjectInputStream`

4. In `main`:
   - Create 4 employees
   - Save to both CSV and serialized format
   - Load from CSV and print
   - Load from serialized and print
   - Compare: print file sizes of both

**Grading rubric:**
- Employee class with Serializable: 2 marks
- CSV save with try-with-resources: 3 marks
- CSV load with proper parsing: 3 marks
- Serialization save/load: 3 marks
- Main method demonstrating both approaches: 2 marks
- Clean code and error handling: 2 marks

---

### D2. REST API — Product Catalog (15 marks)

Build a Spring Boot REST API for a product catalog:

**Requirements:**

1. Create `Product` model with: `id` (int), `name` (String), `category` (String), `price` (double), `inStock` (boolean). Include default constructor, parameterized constructor, and all getters/setters.

2. Create `ProductService` with:
   - Pre-load 4 sample products
   - `getAllProducts()` — return all products
   - `getById(int id)` — return product or null
   - `addProduct(Product p)` — add if ID not duplicate, return product or null
   - `deleteProduct(int id)` — remove and return true/false
   - `searchByCategory(String category)` — case-insensitive match
   - `filterByPriceRange(double min, double max)` — return matching products

3. Create `ProductController` with proper endpoints:
   - `GET /api/products` — list all
   - `GET /api/products/{id}` — get one (404 if not found)
   - `POST /api/products` — create (409 if duplicate, 201 if created)
   - `DELETE /api/products/{id}` — delete (404 if not found, 204 if deleted)
   - `GET /api/products/search?category=electronics` — search by category
   - `GET /api/products?minPrice=100&maxPrice=500` — filter by price range

4. Use proper HTTP status codes and `ResponseEntity`

**Grading rubric:**
- Product model with correct constructors/getters/setters: 2 marks
- ProductService with all methods: 4 marks
- ProductController with correct annotations: 4 marks
- Proper status codes (201, 204, 404, 409): 3 marks
- Search and filter endpoints working: 2 marks

---

### D3. Banking Portal — Web Page Creation (15 marks)

Create a set of styled HTML pages for a banking portal:

**Requirements:**

1. **Home page** (`index.html`) — 4 marks:
   - Bank name heading with a styled header bar
   - Navigation links to other pages
   - Two summary cards side by side: "Total Accounts" and "Total Balance"
   - Footer with copyright

2. **Account List** (`accounts.html`) — 4 marks:
   - Same header and navigation as home page
   - A table with at least 4 accounts showing: Account No., Holder Name, Type, Balance, Status
   - Table should have: header row styling, zebra-striped rows, hover effect
   - An "Open New Account" button/link below the table

3. **New Account Form** (`create-account.html`) — 4 marks:
   - Same header and navigation
   - A form with: Account Number (number input), Holder Name (text input), Initial Deposit (number input), Account Type (radio: Savings/Current), Branch (dropdown with 3 options)
   - Submit and Reset buttons
   - All inputs should have labels and be required

4. **Shared Stylesheet** (`styles.css`) — 3 marks:
   - All pages use the same external CSS file
   - Consistent color theme across all pages
   - Styled navigation bar, table, form inputs, and buttons
   - Use classes (not IDs) for styling

**Grading rubric:**
- HTML structure correct on all pages: 3 marks
- Table with proper thead/tbody/th/td: 2 marks
- Form with correct input types and labels: 3 marks
- CSS external file linked to all pages: 1 mark
- Visual quality — looks like a real application: 3 marks
- Navigation works between pages: 1 mark
- Responsive classes and consistent styling: 2 marks

---

## Bonus Questions (optional, 10 extra marks)

*For trainees who finish early.*

### Bonus 1 (5 marks): Transaction Report with File I/O + Stack

Write a program that:
1. Creates 5 transactions (mix of deposits and withdrawals) for an account starting with Rs.50,000
2. Push each onto a Stack
3. Write a `generateReport(String filePath)` method that:
   - Pops all transactions from the stack (reverse chronological order)
   - Writes them to a formatted text file that looks like a bank statement:

```
═══════════════════════════════════════════
        SIMPLE BANK — TRANSACTION REPORT
═══════════════════════════════════════════
Account Number : 1001
Report Date    : 2026-02-21

─────────────────────────────────────────
#   Type        Amount      Balance
─────────────────────────────────────────
5   WITHDRAW     5,000.00    58,000.00
4   DEPOSIT     10,000.00    63,000.00
3   WITHDRAW     2,000.00    53,000.00
2   DEPOSIT      8,000.00    55,000.00
1   DEPOSIT      7,000.00    47,000.00
─────────────────────────────────────────
Opening Balance: Rs.50,000.00
Closing Balance: Rs.58,000.00
═══════════════════════════════════════════
```

### Bonus 2 (5 marks): REST API Error Handling Page

Create an `error.html` page that displays different error messages styled as alert cards:
- A green card: "Account created successfully!"
- A yellow card: "Warning: Low balance!"
- A red card: "Error: Account not found"
- Each card should have an icon area (just text like "[OK]", "[!]", "[X]"), a title, and a description
- Use only CSS (no JavaScript) to style the cards with appropriate colors, borders, and rounded corners

---

### Bonus Answers

**Bonus 1:** (Model solution)

```java
import java.io.*;
import java.util.*;

public class TransactionReport {

    static class Transaction {
        int number;
        String type;
        double amount;
        double balanceAfter;

        Transaction(int number, String type, double amount, double balanceAfter) {
            this.number = number;
            this.type = type;
            this.amount = amount;
            this.balanceAfter = balanceAfter;
        }
    }

    public static void main(String[] args) throws IOException {
        Stack<Transaction> stack = new Stack<>();
        double balance = 50000;
        int txnNo = 0;

        // 5 transactions
        double[] amounts = {7000, 8000, -2000, 10000, -5000};
        for (double amt : amounts) {
            txnNo++;
            balance += amt;
            String type = amt > 0 ? "DEPOSIT" : "WITHDRAW";
            stack.push(new Transaction(txnNo, type, Math.abs(amt), balance));
        }

        generateReport(stack, 1001, 50000, balance, "data/report.txt");
    }

    public static void generateReport(Stack<Transaction> stack, long accNo,
                                        double openBal, double closeBal, String filePath) throws IOException {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(filePath))) {
            String sep = "=".repeat(45);
            String line = "-".repeat(45);

            w.write(sep); w.newLine();
            w.write("        SIMPLE BANK — TRANSACTION REPORT"); w.newLine();
            w.write(sep); w.newLine();
            w.write("Account Number : " + accNo); w.newLine();
            w.write("Report Date    : " + java.time.LocalDate.now()); w.newLine();
            w.newLine();
            w.write(line); w.newLine();
            w.write(String.format("%-4s %-12s %12s %12s", "#", "Type", "Amount", "Balance"));
            w.newLine();
            w.write(line); w.newLine();

            while (!stack.isEmpty()) {
                Transaction t = stack.pop();
                w.write(String.format("%-4d %-12s %,12.2f %,12.2f",
                        t.number, t.type, t.amount, t.balanceAfter));
                w.newLine();
            }

            w.write(line); w.newLine();
            w.write(String.format("Opening Balance: Rs.%,.2f", openBal)); w.newLine();
            w.write(String.format("Closing Balance: Rs.%,.2f", closeBal)); w.newLine();
            w.write(sep); w.newLine();
        }
        System.out.println("Report generated.");
    }
}
```

**Bonus 2:** (Model solution)

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Notifications — Simple Bank</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f5f6fa;
            padding: 30px;
            max-width: 600px;
            margin: 0 auto;
        }
        h1 { color: #1a5276; }
        .alert {
            display: flex;
            align-items: center;
            padding: 15px 20px;
            border-radius: 8px;
            margin-bottom: 15px;
            border-left: 5px solid;
        }
        .alert-icon {
            font-size: 24px;
            font-weight: bold;
            margin-right: 15px;
            min-width: 40px;
            text-align: center;
        }
        .alert-content h3 { margin: 0 0 5px 0; }
        .alert-content p { margin: 0; font-size: 14px; }

        .alert-success {
            background-color: #d4efdf;
            border-color: #27ae60;
            color: #1e7e34;
        }
        .alert-warning {
            background-color: #fef9e7;
            border-color: #f1c40f;
            color: #856404;
        }
        .alert-danger {
            background-color: #fadbd8;
            border-color: #e74c3c;
            color: #a71d2a;
        }
    </style>
</head>
<body>
    <h1>Notifications</h1>

    <div class="alert alert-success">
        <div class="alert-icon">[OK]</div>
        <div class="alert-content">
            <h3>Success</h3>
            <p>Account created successfully!</p>
        </div>
    </div>

    <div class="alert alert-warning">
        <div class="alert-icon">[!]</div>
        <div class="alert-content">
            <h3>Warning</h3>
            <p>Low balance! Your account balance is below Rs.1,000.</p>
        </div>
    </div>

    <div class="alert alert-danger">
        <div class="alert-icon">[X]</div>
        <div class="alert-content">
            <h3>Error</h3>
            <p>Account not found. Please check the account number.</p>
        </div>
    </div>
</body>
</html>
```

---

## Assessment Summary Checklist (for instructor)

After grading, tally how trainees performed per topic area:

| Topic Area | Related Questions | Class Average |
|-----------|------------------|---------------|
| Stack / Queue / LinkedList (Data Structures) | A1-A5, B1-B2, C1 | ___% |
| File I/O (CSV, Streams, try-with-resources) | A6, A9, B3, C2, D1 | ___% |
| Serialization (ObjectStream, transient) | A7-A8, B4-B5, D1 | ___% |
| REST Architecture (HTTP, methods, status) | A10-A12, B6, C3 | ___% |
| Spring Boot (annotations, controller, service) | A13-A15, B7-B8, C4, D2 | ___% |
| HTML (structure, tables, forms, inputs) | A16-A18, B9, C5, D3 | ___% |
| CSS (selectors, box model, styling) | A19-A20, B10, D3 | ___% |

Use this to decide which topics need revision before proceeding to Day 11.
