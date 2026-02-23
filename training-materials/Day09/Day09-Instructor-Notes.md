# Day 9 — HTML Basics & CSS

## Instructor Guide

> **Duration**: 4 hours
> **Pre-requisite**: Day 8 REST API (Spring Boot). No HTML/CSS experience needed.
> **Case Study**: Build front-end pages for the Banking REST API — account list table, create account form, deposit/withdraw forms, all styled with CSS
> **Goal by end of day**: Trainees can create HTML pages with forms, tables, and input controls, style them with CSS, and understand how front-end connects to a REST back-end

---

## Important Setup Note

> Trainees only need:
> 1. A **text editor** — VS Code (recommended), Sublime Text, or even Notepad
> 2. A **web browser** — Chrome or Firefox (with Developer Tools)
> 3. No server needed — we open HTML files directly in the browser
>
> **VS Code tip**: Install "Live Server" extension — it auto-refreshes the browser when you save.

---

## Topic 1: Role of HTML in Web Applications

#### Key Points (10 min)

- **HTML** = HyperText Markup Language — the language browsers understand
- Every web page you see is HTML rendered by the browser
- **Front-end vs Back-end**:
  - **Back-end** (Day 8): Java/Spring Boot — handles data, logic, API
  - **Front-end** (Today): HTML/CSS — what the user sees and interacts with
  - They communicate via HTTP (the REST API we built yesterday)

#### The big picture

```
┌────────────────┐     HTTP Request      ┌────────────────────┐
│   FRONT-END    │ ──────────────────→   │    BACK-END        │
│   (Browser)    │                        │   (Spring Boot)    │
│                │     HTTP Response      │                    │
│   HTML + CSS   │ ←──────────────────   │   REST API (JSON)  │
│   (+ JS later) │                        │   AccountService   │
└────────────────┘                        └────────────────────┘
     Day 9                                     Day 8
```

> **Teaching moment**: "Yesterday we built the kitchen (API). Today we build the restaurant — the menu, the tables, the ordering forms. Customers (users) interact with the restaurant (HTML), which communicates with the kitchen (API)."

#### Verbal Quiz

- "When you open google.com, what does the browser receive?" → HTML (+ CSS + JS)
- "Where does the data come from?" → A server (back-end)
- "Can HTML do calculations or access a database?" → No, it's just structure/display

---

## Topic 2: Basic HTML Structure

#### Key Points (15 min)

> **Live code this** — have trainees type along.

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Simple Bank</title>
</head>
<body>
    <h1>Welcome to Simple Bank</h1>
    <p>Your trusted banking partner.</p>
</body>
</html>
```

Walk through each part:

| Element | Purpose |
|---------|---------|
| `<!DOCTYPE html>` | Tells browser: "This is HTML5" |
| `<html>` | Root element — everything lives inside |
| `<head>` | Metadata — title, character set, links to CSS (not visible on page) |
| `<title>` | Text shown in the browser tab |
| `<body>` | Visible content — what users see |

#### Key HTML rules

- Tags come in **pairs**: `<h1>...</h1>` (opening + closing)
- Some tags are **self-closing**: `<br>`, `<img>`, `<input>`, `<hr>`
- Tags can be **nested**: `<div><p>text</p></div>`
- HTML is **not case-sensitive**, but convention is **lowercase**
- **Indentation** doesn't affect rendering but makes code readable

#### Teaching Tip

> Have trainees save as `index.html` and double-click to open in browser. Show them "View Page Source" (Ctrl+U) and Developer Tools (F12) right away. These are essential tools.

---

## Topic 3: Common HTML Tags

#### Key Points (20 min)

> Build incrementally — keep adding to the same `bank.html` file.

#### Headings

```html
<h1>Simple Bank</h1>          <!-- Main heading — use only once -->
<h2>Our Services</h2>         <!-- Section heading -->
<h3>Savings Account</h3>      <!-- Sub-section -->
<!-- h4, h5, h6 exist but rarely needed -->
```

> "h1 is the title of the page, h2 are chapter titles, h3 are sub-chapters. Don't use h1 for everything just because it's big — we control size with CSS."

#### Paragraphs and text

```html
<p>Open a savings account with just Rs.1000 minimum balance.</p>
<p>Our current accounts offer <strong>overdraft facilities</strong>
   up to <em>Rs.50,000</em>.</p>

<strong>Bold text</strong>     <!-- semantic: important -->
<em>Italic text</em>           <!-- semantic: emphasis -->
<br>                            <!-- line break (self-closing) -->
<hr>                            <!-- horizontal rule/line -->
```

#### div and span

```html
<!-- div = block-level container (takes full width, starts new line) -->
<div>
    <h2>Account Details</h2>
    <p>Account Number: 1001</p>
    <p>Balance: Rs.50,000</p>
</div>

<!-- span = inline container (stays in the flow of text) -->
<p>Your balance is <span style="color: green;">Rs.50,000</span></p>
```

> **Teaching moment**: "div is like a cardboard box — it groups things together. span is like a highlighter — it marks a piece of text within a line."

#### Lists

```html
<!-- Unordered list (bullet points) -->
<ul>
    <li>Savings Account</li>
    <li>Current Account</li>
    <li>Fixed Deposit</li>
</ul>

<!-- Ordered list (numbered) -->
<ol>
    <li>Fill application form</li>
    <li>Submit documents</li>
    <li>Receive account number</li>
</ol>
```

#### Links and images

```html
<a href="https://www.rbi.org.in">Reserve Bank of India</a>
<a href="accounts.html">View Accounts</a>    <!-- link to another page -->

<img src="bank-logo.png" alt="Simple Bank Logo" width="200">
```

#### Case Study Step 1 — Bank home page

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Simple Bank</title>
</head>
<body>
    <h1>Simple Bank</h1>
    <p>Your trusted banking partner since 2024.</p>

    <hr>

    <h2>Quick Links</h2>
    <ul>
        <li><a href="accounts.html">View All Accounts</a></li>
        <li><a href="create-account.html">Open New Account</a></li>
        <li><a href="transaction.html">Deposit / Withdraw</a></li>
    </ul>

    <hr>
    <p><em>Simple Bank &copy; 2024</em></p>
</body>
</html>
```

---

## Topic 4: HTML Tables

#### Key Points (20 min)

Tables are essential for displaying data — perfect for our account list.

#### Table structure

```
<table>
├── <thead>         ← header section
│   └── <tr>        ← table row
│       ├── <th>    ← header cell (bold, centered by default)
│       └── <th>
├── <tbody>         ← body section
│   └── <tr>        ← table row
│       ├── <td>    ← data cell
│       └── <td>
└── </table>
```

#### Case Study Step 2 — Account list table

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Accounts — Simple Bank</title>
</head>
<body>
    <h1>All Accounts</h1>

    <table border="1">
        <thead>
            <tr>
                <th>Account No.</th>
                <th>Holder Name</th>
                <th>Type</th>
                <th>Balance (Rs.)</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>1001</td>
                <td>Ravi Kumar</td>
                <td>SAVINGS</td>
                <td>50,000.00</td>
                <td>Active</td>
            </tr>
            <tr>
                <td>1002</td>
                <td>Priya Sharma</td>
                <td>CURRENT</td>
                <td>30,000.00</td>
                <td>Active</td>
            </tr>
            <tr>
                <td>1003</td>
                <td>Amit Verma</td>
                <td>SAVINGS</td>
                <td>75,000.00</td>
                <td>Active</td>
            </tr>
        </tbody>
    </table>

    <p><a href="index.html">← Back to Home</a></p>
</body>
</html>
```

> **Note**: `border="1"` is the quick way to see table borders. We'll replace this with proper CSS styling later.

#### Verbal Quiz

- "What's the difference between `<th>` and `<td>`?" → th = header cell (bold), td = data cell
- "What does `<thead>` do?" → Groups header rows (semantic, helps screen readers and CSS)
- "Can a table row have both th and td?" → Technically yes, but unusual

---

## Topic 5: HTML Forms

#### Key Points (30 min)

> **This is the most important topic today** — forms are how users send data to the server.

#### Form basics

```html
<form action="/api/accounts" method="POST">
    <!-- input controls go here -->
    <button type="submit">Submit</button>
</form>
```

| Attribute | Purpose |
|-----------|---------|
| `action` | URL where form data is sent (our REST API endpoint) |
| `method` | HTTP method — `GET` (search/filter) or `POST` (create/modify) |

> "Remember from Day 8: POST creates, GET retrieves. Forms use the same HTTP methods."

#### GET vs POST in forms

| | GET | POST |
|---|---|---|
| Data location | Appended to URL as query string | In request body (hidden) |
| Visibility | Visible in URL bar | Not visible in URL |
| Bookmarkable | Yes | No |
| Use for | Search, filter, read | Create, update, sensitive data |
| Example | `/accounts/search?name=ravi` | Creating a new account |

---

## Topic 6: Input Controls

#### Key Points (30 min)

> Build the create-account form incrementally, adding one control type at a time.

#### Text, Number, Email, Password

```html
<label for="name">Account Holder:</label>
<input type="text" id="name" name="holderName" placeholder="Enter full name" required>

<label for="accNo">Account Number:</label>
<input type="number" id="accNo" name="accountNumber" min="1000" max="9999" required>

<label for="email">Email:</label>
<input type="email" id="email" name="email" placeholder="ravi@example.com">

<label for="pin">PIN:</label>
<input type="password" id="pin" name="pin" maxlength="4">
```

> **Always use `<label>`** — it improves accessibility and clicking the label focuses the input.

#### Radio buttons

```html
<p>Account Type:</p>
<label>
    <input type="radio" name="type" value="SAVINGS" checked> Savings
</label>
<label>
    <input type="radio" name="type" value="CURRENT"> Current
</label>
```

> "Radio buttons with the same `name` form a group — only one can be selected."

#### Checkboxes

```html
<label>
    <input type="checkbox" name="smsAlerts" value="true"> Enable SMS Alerts
</label>
<label>
    <input type="checkbox" name="emailAlerts" value="true" checked> Enable Email Alerts
</label>
```

#### Select dropdown

```html
<label for="branch">Branch:</label>
<select id="branch" name="branch">
    <option value="">-- Select Branch --</option>
    <option value="MG_ROAD">MG Road, Bangalore</option>
    <option value="ANNA_NAGAR">Anna Nagar, Chennai</option>
    <option value="BANDRA">Bandra, Mumbai</option>
</select>
```

#### Textarea

```html
<label for="address">Address:</label>
<textarea id="address" name="address" rows="3" cols="40" placeholder="Enter address"></textarea>
```

#### Buttons

```html
<button type="submit">Create Account</button>
<button type="reset">Clear Form</button>
<button type="button">Cancel</button>  <!-- does nothing by default -->
```

#### Case Study Step 3 — Create Account form

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Open Account — Simple Bank</title>
</head>
<body>
    <h1>Open New Account</h1>

    <form action="/api/accounts" method="POST">
        <p>
            <label for="accNo">Account Number:</label><br>
            <input type="number" id="accNo" name="accountNumber"
                   min="1000" max="9999" required>
        </p>

        <p>
            <label for="name">Account Holder Name:</label><br>
            <input type="text" id="name" name="holderName"
                   placeholder="Enter full name" required>
        </p>

        <p>
            <label for="balance">Initial Deposit (Rs.):</label><br>
            <input type="number" id="balance" name="balance"
                   min="0" step="0.01" value="0" required>
        </p>

        <p>
            <label>Account Type:</label><br>
            <label>
                <input type="radio" name="type" value="SAVINGS" checked> Savings
            </label>
            <label>
                <input type="radio" name="type" value="CURRENT"> Current
            </label>
        </p>

        <p>
            <button type="submit">Create Account</button>
            <button type="reset">Clear</button>
        </p>
    </form>

    <p><a href="index.html">← Back to Home</a></p>
</body>
</html>
```

#### Case Study Step 4 — Transaction form

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Transaction — Simple Bank</title>
</head>
<body>
    <h1>Deposit / Withdraw</h1>

    <form>
        <p>
            <label for="accNo">Account Number:</label><br>
            <input type="number" id="accNo" name="accountNumber"
                   min="1000" max="9999" required>
        </p>

        <p>
            <label>Transaction Type:</label><br>
            <label>
                <input type="radio" name="txnType" value="deposit" checked> Deposit
            </label>
            <label>
                <input type="radio" name="txnType" value="withdraw"> Withdraw
            </label>
        </p>

        <p>
            <label for="amount">Amount (Rs.):</label><br>
            <input type="number" id="amount" name="amount"
                   min="1" step="0.01" required>
        </p>

        <p>
            <button type="submit">Submit Transaction</button>
        </p>
    </form>

    <p><a href="index.html">← Back to Home</a></p>
</body>
</html>
```

> **Teaching moment**: "This form won't actually work yet — it needs JavaScript (Day 11) to call our REST API. Today we're building the visual structure. Think of it as building a car body before adding the engine."

---

## Topic 7: CSS Basics

#### Key Points (20 min)

- **CSS** = Cascading Style Sheets — controls how HTML looks
- HTML = structure (what), CSS = presentation (how it looks)
- Without CSS, pages look like plain text documents from the 1990s

#### Three ways to add CSS

```html
<!-- 1. Inline (on the element) — avoid for large projects -->
<h1 style="color: blue; font-size: 24px;">Simple Bank</h1>

<!-- 2. Internal (in <head>) — OK for single-page demos -->
<head>
    <style>
        h1 { color: blue; }
        p  { font-size: 14px; }
    </style>
</head>

<!-- 3. External file (BEST — separate concerns) -->
<head>
    <link rel="stylesheet" href="styles.css">
</head>
```

> "Always use external CSS files in real projects. It keeps your HTML clean and lets multiple pages share the same styles."

#### CSS syntax

```css
selector {
    property: value;
    property: value;
}

/* Example */
h1 {
    color: #1a5276;
    font-size: 28px;
    text-align: center;
}
```

---

## Topic 8: CSS Selectors

#### Key Points (20 min)

| Selector | Syntax | Matches | Specificity |
|----------|--------|---------|-------------|
| Element | `h1` | All `<h1>` elements | Low |
| Class | `.card` | All elements with `class="card"` | Medium |
| ID | `#header` | The one element with `id="header"` | High |
| Descendant | `table td` | All `<td>` inside a `<table>` | Depends |
| Group | `h1, h2, h3` | All h1, h2, and h3 elements | Depends |

#### Using classes

```html
<div class="account-card">
    <h3>Ravi Kumar</h3>
    <p class="balance positive">Rs.50,000</p>
</div>
<div class="account-card">
    <h3>Priya Sharma</h3>
    <p class="balance negative">Rs.-5,000</p>
</div>
```

```css
.account-card {
    border: 1px solid #ccc;
    padding: 15px;
    margin: 10px 0;
}

.balance { font-weight: bold; font-size: 18px; }
.positive { color: green; }
.negative { color: red; }
```

> **Rule**: Use **classes** for styling (reusable). Use **IDs** for unique elements (one per page). Never use IDs for styling in real projects.

#### Verbal Quiz

- "What's the difference between `.card` and `#card`?" → class vs ID
- "Can two elements have the same class?" → Yes (that's the point)
- "Can two elements have the same ID?" → No (IDs are unique)

---

## Topic 9: CSS Properties

#### Key Points (25 min)

> Demo each property by applying it to the banking pages.

#### Text & font

```css
body {
    font-family: 'Segoe UI', Arial, sans-serif;
    font-size: 14px;
    color: #333;
    line-height: 1.6;
}

h1 {
    color: #1a5276;
    font-size: 28px;
    font-weight: bold;
    text-align: center;
    text-transform: uppercase;
    letter-spacing: 2px;
}
```

#### Colors & backgrounds

```css
/* Color formats */
color: red;                     /* named */
color: #1a5276;                 /* hex */
color: rgb(26, 82, 118);       /* rgb */

/* Backgrounds */
body {
    background-color: #f5f6fa;
}

.header {
    background-color: #1a5276;
    color: white;
    padding: 20px;
}
```

#### Box model (important!)

```
┌─────────────── margin ────────────────┐
│  ┌──────────── border ─────────────┐  │
│  │  ┌──────── padding ─────────┐   │  │
│  │  │                           │   │  │
│  │  │        CONTENT            │   │  │
│  │  │    (text, image, etc.)    │   │  │
│  │  │                           │   │  │
│  │  └───────────────────────────┘   │  │
│  └──────────────────────────────────┘  │
└────────────────────────────────────────┘
```

```css
.account-card {
    margin: 10px;               /* outside space */
    border: 1px solid #ddd;     /* visible edge */
    padding: 15px;              /* inside space */
    width: 300px;
}
```

> **Open DevTools (F12)** → Inspect an element → Show the box model visualization. This is the #1 debugging tool for layout issues.

#### Table styling

```css
table {
    width: 100%;
    border-collapse: collapse;      /* removes double borders */
}

th, td {
    border: 1px solid #ddd;
    padding: 10px 15px;
    text-align: left;
}

th {
    background-color: #1a5276;
    color: white;
}

tbody tr:nth-child(even) {
    background-color: #f2f2f2;      /* zebra stripes */
}

tbody tr:hover {
    background-color: #d5e8f0;      /* highlight on hover */
}
```

#### Form styling

```css
input, select, textarea {
    width: 100%;
    padding: 8px 12px;
    margin: 5px 0 15px 0;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 14px;
}

input:focus, select:focus, textarea:focus {
    border-color: #1a5276;
    outline: none;
    box-shadow: 0 0 4px rgba(26, 82, 118, 0.3);
}

button[type="submit"] {
    background-color: #1a5276;
    color: white;
    padding: 10px 25px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
}

button[type="submit"]:hover {
    background-color: #154360;
}
```

---

## Topic 10: Complete Styled Banking Pages

#### Key Points (30 min)

> This is the big payoff — combine everything into a polished banking front-end.

#### Case Study Step 5 — styles.css (shared stylesheet)

```css
/* === RESET & BASE === */
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    font-size: 14px;
    color: #333;
    line-height: 1.6;
    background-color: #f5f6fa;
}

/* === HEADER === */
.header {
    background-color: #1a5276;
    color: white;
    padding: 15px 30px;
    text-align: center;
}

.header h1 {
    font-size: 24px;
    letter-spacing: 1px;
}

/* === NAVIGATION === */
.nav {
    background-color: #154360;
    padding: 10px 30px;
    text-align: center;
}

.nav a {
    color: #aed6f1;
    text-decoration: none;
    margin: 0 15px;
    font-size: 14px;
}

.nav a:hover {
    color: white;
    text-decoration: underline;
}

/* === MAIN CONTENT === */
.container {
    max-width: 900px;
    margin: 20px auto;
    padding: 20px;
    background-color: white;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

h2 {
    color: #1a5276;
    margin-bottom: 15px;
    border-bottom: 2px solid #aed6f1;
    padding-bottom: 5px;
}

/* === TABLE === */
table {
    width: 100%;
    border-collapse: collapse;
    margin: 15px 0;
}

th, td {
    padding: 10px 15px;
    text-align: left;
    border: 1px solid #ddd;
}

th {
    background-color: #1a5276;
    color: white;
    font-weight: normal;
}

tbody tr:nth-child(even) {
    background-color: #f9f9f9;
}

tbody tr:hover {
    background-color: #eaf2f8;
}

/* === FORMS === */
.form-group {
    margin-bottom: 15px;
}

.form-group label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
    color: #1a5276;
}

.form-group input,
.form-group select,
.form-group textarea {
    width: 100%;
    padding: 8px 12px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 14px;
}

.form-group input:focus,
.form-group select:focus {
    border-color: #1a5276;
    outline: none;
    box-shadow: 0 0 4px rgba(26, 82, 118, 0.3);
}

.radio-group label {
    display: inline;
    font-weight: normal;
    margin-right: 20px;
}

/* === BUTTONS === */
.btn {
    padding: 10px 25px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    margin-right: 10px;
}

.btn-primary {
    background-color: #1a5276;
    color: white;
}

.btn-primary:hover {
    background-color: #154360;
}

.btn-secondary {
    background-color: #95a5a6;
    color: white;
}

.btn-secondary:hover {
    background-color: #7f8c8d;
}

.btn-danger {
    background-color: #e74c3c;
    color: white;
}

.btn-danger:hover {
    background-color: #c0392b;
}

/* === STATUS BADGES === */
.badge {
    padding: 3px 10px;
    border-radius: 12px;
    font-size: 12px;
    font-weight: bold;
}

.badge-active {
    background-color: #d4efdf;
    color: #27ae60;
}

.badge-inactive {
    background-color: #fadbd8;
    color: #e74c3c;
}

/* === FOOTER === */
.footer {
    text-align: center;
    padding: 15px;
    color: #999;
    font-size: 12px;
    margin-top: 30px;
}
```

#### Case Study Step 6 — Styled account list page

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accounts — Simple Bank</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="header">
        <h1>Simple Bank</h1>
    </div>
    <div class="nav">
        <a href="index.html">Home</a>
        <a href="accounts.html">Accounts</a>
        <a href="create-account.html">Open Account</a>
        <a href="transaction.html">Transactions</a>
    </div>

    <div class="container">
        <h2>All Accounts</h2>

        <table>
            <thead>
                <tr>
                    <th>Account No.</th>
                    <th>Holder Name</th>
                    <th>Type</th>
                    <th>Balance (Rs.)</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>1001</td>
                    <td>Ravi Kumar</td>
                    <td>SAVINGS</td>
                    <td>50,000.00</td>
                    <td><span class="badge badge-active">Active</span></td>
                </tr>
                <tr>
                    <td>1002</td>
                    <td>Priya Sharma</td>
                    <td>CURRENT</td>
                    <td>30,000.00</td>
                    <td><span class="badge badge-active">Active</span></td>
                </tr>
                <tr>
                    <td>1003</td>
                    <td>Amit Verma</td>
                    <td>SAVINGS</td>
                    <td>75,000.00</td>
                    <td><span class="badge badge-active">Active</span></td>
                </tr>
            </tbody>
        </table>

        <p>
            <a href="create-account.html" class="btn btn-primary">+ Open New Account</a>
        </p>
    </div>

    <div class="footer">
        Simple Bank &copy; 2024 | Training Project
    </div>
</body>
</html>
```

#### Case Study Step 7 — Styled create account form

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Open Account — Simple Bank</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="header">
        <h1>Simple Bank</h1>
    </div>
    <div class="nav">
        <a href="index.html">Home</a>
        <a href="accounts.html">Accounts</a>
        <a href="create-account.html">Open Account</a>
        <a href="transaction.html">Transactions</a>
    </div>

    <div class="container">
        <h2>Open New Account</h2>

        <form action="/api/accounts" method="POST">
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
    </div>

    <div class="footer">
        Simple Bank &copy; 2024 | Training Project
    </div>
</body>
</html>
```

> **Teaching moment**: "Look at the difference between the unstyled and styled versions. Same HTML structure — just CSS makes it professional. CSS is what separates a student project from a production app."

---

## Day 9 Exercises

### Exercise 1: Transaction Form Page
**Problem**: Create a styled `transaction.html` page with a form for deposit/withdraw. Include: account number input, transaction type (radio: Deposit/Withdraw), amount input, and a submit button. Use the shared `styles.css`.

### Exercise 2: Account Detail Page
**Problem**: Create `account-detail.html` that shows a single account's information in a card-style layout (not a table). Include: account number, holder name, type, balance, status, and a list of recent transactions.

### Exercise 3: Search Form with Results Table
**Problem**: Create `search.html` with:
- A search form (text input + submit button) at the top
- A results table below showing matching accounts
- Style the search input to be wider than standard inputs (use a custom class)

### Exercise 4: Responsive Navigation
**Problem**: Modify the navigation bar so that links are displayed as a horizontal list using CSS. Add a hover effect that changes the background color of each link. Add `class="active"` to the current page's nav link and style it differently.

### Exercise 5: Complete Banking Portal (Challenge)
**Problem**: Create a complete set of banking pages:
- `index.html` — home/dashboard with summary cards (Total Accounts, Total Balance)
- `accounts.html` — account list table with action buttons per row (View, Delete)
- `create-account.html` — account creation form
- `transaction.html` — deposit/withdraw form
- `styles.css` — shared stylesheet
All pages should share the same header, navigation, and footer.

---

## Day 9 Quiz (8 questions)

1. What is the difference between HTML and CSS?
2. What tag is used for the largest heading?
3. What is the difference between `<div>` and `<span>`?
4. What does the `action` attribute in a `<form>` specify?
5. What is the difference between radio buttons and checkboxes?
6. Name three ways to include CSS in an HTML page. Which is preferred?
7. What is the difference between a class selector (`.card`) and an ID selector (`#header`)?
8. What does `border-collapse: collapse` do on a table?

---

## Day 9 Summary

| Step | What we built | Key Concepts |
|------|--------------|-------------|
| Step 1 | Bank home page | HTML structure, headings, lists, links |
| Step 2 | Account list table | `<table>`, `<thead>`, `<tbody>`, `<tr>`, `<th>`, `<td>` |
| Step 3 | Create account form | `<form>`, `<input>`, radio buttons, `<label>` |
| Step 4 | Transaction form | Form controls, input types |
| Step 5 | Shared stylesheet | CSS selectors, properties, external CSS |
| Step 6 | Styled accounts page | Table styling, zebra stripes, hover effects |
| Step 7 | Styled create form | Form styling, focus states, buttons |

### File structure

```
bank-frontend/
├── index.html              ← home page
├── accounts.html           ← account list (table)
├── create-account.html     ← new account form
├── transaction.html        ← deposit/withdraw form
└── styles.css              ← shared stylesheet
```

### How it connects to the backend

```
Today (Day 9):        HTML + CSS pages (static — no data flow)
Day 10:               Assessment
Day 11:               JavaScript connects forms to REST API (Day 8)
                      HTML form → JS fetch() → REST API → JSON response → update page
```

> **Preview for Day 10**: "Tomorrow is assessment day — Data Structures and HTML assessment, plus you'll create web pages to demonstrate what you learned today."
