# Day 10 — Data Structures and HTML Assessment

## Assessment Paper

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

## Section D: Practical Assignments (45 marks)

*Write complete, compilable code. You may use an IDE and browser.*

---

### D1. File Persistence for Employee Data (15 marks)

Build a small employee management system with file persistence:

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

---

### D2. REST API — Product Catalog (15 marks)

Build a Spring Boot REST API for a product catalog:

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

---

### D3. Banking Portal — Web Page Creation (15 marks)

Create a set of styled HTML pages for a banking portal:

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

---

## Bonus Questions (optional, 10 extra marks)

*Attempt if you finish early.*

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

*End of Assessment*
