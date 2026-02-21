# Day 10 — Data Structures and HTML Assessment

## Instructor Guide

> **Duration**: Full day
> **Coverage**: Days 5–9 (Data Structures, File Handling, REST API & Spring Boot, HTML & CSS)
> **Format**: Written assessment + Web page creation practical
> **Suggested split**: Section A-C written (~1.5 hrs) + Section D coding/web creation (~2.5 hrs) + review (~30 min)

---

## Assessment Structure

| Section | Type | Questions | Marks | Time |
|---------|------|-----------|-------|------|
| A | Multiple Choice | 20 | 20 | ~20 min |
| B | Short Answer | 10 | 30 | ~30 min |
| C | Code Reading / Debugging | 5 | 25 | ~30 min |
| D | Practical — Coding + Web Page Creation | 3 | 45 | ~2.5 hrs |
| **Total** | | **38** | **120** | ~4 hrs |

---

## Grading Guidelines

| Grade | Percentage | Interpretation |
|-------|-----------|---------------|
| Excellent | 85–100% | Solid understanding, ready for integration topics |
| Good | 70–84% | Minor gaps, brief review needed |
| Needs Improvement | 50–69% | Significant gaps, targeted revision on weak areas |
| Below Expectations | <50% | Requires one-on-one support before proceeding |

---

## Conducting the Assessment

### Before
- Remind trainees this is a learning checkpoint, not a pass/fail exam
- Allow open notes (their own Day 5–9 code and handouts)
- No internet search for written sections (A–C)
- Practical section (D): IDE + browser allowed, no copy-paste from training materials

### During
- Sections A–C: Written (paper or online form)
- Section D1-D2: On their machines — submit `.java` files
- Section D3: Web page creation — submit `.html` and `.css` files
- If a trainee finishes early, they can attempt bonus questions

### After
- Review answers as a class — this is a teaching opportunity
- Focus on commonly missed questions
- Use Section D3 (web page creation) for quick visual review — show good examples on screen
- Preview Day 11: "We have a REST API and HTML pages — tomorrow we connect them with JavaScript"

---

## Common Weak Areas to Watch For

| Concept | Common Mistake | What to Reinforce |
|---------|---------------|-------------------|
| Stack vs Queue | Confusing LIFO and FIFO | Stack = undo (last action first); Queue = waiting line |
| `readLine()` returns null | Forgetting EOF check | `while ((line = reader.readLine()) != null)` |
| try-with-resources | Not understanding auto-close | Resource declared in `try()` is auto-closed |
| Serializable | Forgetting to implement interface | All classes in the object graph must be Serializable |
| `transient` | Not understanding default values | transient fields get null/0/false on deserialization |
| HTTP methods | Using GET for create/update | GET = read, POST = create, PUT = update, DELETE = remove |
| @PathVariable vs @RequestParam | Confusing the two | Path = `/accounts/{id}`, Query = `/accounts?type=X` |
| ResponseEntity | Always returning 200 | Use 201 Created, 404 Not Found, 400 Bad Request |
| HTML form `action` | Not understanding where data goes | action = URL to send form data to |
| CSS class vs ID | Using IDs for styling | Classes = reusable styling; IDs = unique elements |
| `border-collapse` | Tables with double borders | `collapse` merges adjacent borders |
| Box model | Confusing margin and padding | Margin = outside; Padding = inside |

---

## Answer Key

Provided in the Assessment paper (instructor copy). Keep the answer key separate when distributing to trainees.

---

## Post-Assessment Review Plan

After grading, spend 30 minutes reviewing:

1. **Top 5 most-missed questions** — walk through on the board
2. **Section D3 showcase** — display 2-3 best web page submissions on screen
3. **Common D1/D2 code issues** — show model solutions
4. **Preview Day 11** — "We have a backend (REST API) and a frontend (HTML). Tomorrow we add JavaScript to connect them — forms will actually create accounts and tables will display live data."
