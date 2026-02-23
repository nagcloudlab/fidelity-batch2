# Day 14 — SQL and Networks Assessment

## Instructor Guide

> **Duration**: Full day
> **Coverage**: Days 11–13 (Java + HTML Integration / HTTP & Networks, SQL Fundamentals, JDBC Integration, Basics of DevOps)
> **Format**: Written assessment + Practical coding + DevOps tasks
> **Suggested split**: Section A-C written (~1.5 hrs) + Section D practical (~2.5 hrs) + review (~30 min)

---

## Assessment Structure

| Section | Type | Questions | Marks | Time |
|---------|------|-----------|-------|------|
| A | Multiple Choice | 20 | 20 | ~20 min |
| B | Short Answer | 10 | 30 | ~30 min |
| C | Code Reading / Debugging | 5 | 25 | ~30 min |
| D | Practical — SQL + JDBC + DevOps | 3 | 45 | ~2.5 hrs |
| **Total** | | **38** | **120** | ~4 hrs |

---

## Grading Guidelines

| Grade | Percentage | Interpretation |
|-------|-----------|---------------|
| Excellent | 85–100% | Strong understanding, ready for final assessment |
| Good | 70–84% | Minor gaps, brief review on weak areas |
| Needs Improvement | 50–69% | Significant gaps, targeted revision needed |
| Below Expectations | <50% | Requires one-on-one support before final assessment |

---

## Conducting the Assessment

### Before
- Remind trainees this is a learning checkpoint, not a pass/fail exam
- Allow open notes (their own Day 11–13 code and handouts)
- No internet search for written sections (A–C)
- Practical section (D): IDE + terminal allowed, no copy-paste from training materials
- Ensure H2 database is available on trainee machines (or they can use any SQL-compatible DB)

### During
- Sections A–C: Written (paper or online form)
- Section D1: SQL queries — can use H2 console or any SQL client
- Section D2: JDBC coding — submit `.java` files
- Section D3: DevOps tasks — submit Dockerfile, `.yml` pipeline, and git command log
- If a trainee finishes early, they can attempt bonus questions

### After
- Review answers as a class — this is a teaching opportunity
- Focus on commonly missed questions
- Live-demo the Section D model solutions
- Preview Day 15: "Tomorrow is the final comprehensive assessment covering everything from Day 1 to Day 13"

---

## Common Weak Areas to Watch For

| Concept | Common Mistake | What to Reinforce |
|---------|---------------|-------------------|
| `fetch()` syntax | Forgetting `method`, `headers`, or `body` | fetch requires an options object for POST |
| `JSON.stringify()` | Sending raw JS object instead of JSON string | POST body must be stringified |
| `event.preventDefault()` | Form submitting via HTML action instead of JS | Without it, the page refreshes and fetch is cancelled |
| CORS | Not understanding why browser blocks requests | Same-origin policy; `@CrossOrigin` on the controller |
| PRIMARY KEY vs FOREIGN KEY | Confusing the two | PK = unique row identity; FK = reference to another table's PK |
| SQL WHERE clause | Forgetting quotes on strings | `WHERE name = 'Ravi'` (strings need quotes) |
| PreparedStatement `?` index | Using 0-based index | JDBC parameters are 1-based: `setString(1, value)` |
| `executeQuery()` vs `executeUpdate()` | Using wrong method | SELECT → `executeQuery()` (returns ResultSet); INSERT/UPDATE/DELETE → `executeUpdate()` (returns int) |
| SQL injection | Not understanding the risk | Show the `' OR 1=1 --` example; PreparedStatement prevents it |
| Git add vs commit | Committing without staging | `git add` stages; `git commit` saves to local repo |
| Dockerfile COPY | Wrong path or file not found | COPY is relative to build context (where you run `docker build`) |
| GitHub Actions YAML | Indentation errors | YAML is whitespace-sensitive; use 2-space indentation |

---

## Answer Key

Provided in the Assessment paper (instructor copy). Keep the answer key separate when distributing to trainees.

---

## Post-Assessment Review Plan

After grading, spend 30 minutes reviewing:

1. **Top 5 most-missed questions** — walk through on the board
2. **Live SQL demo** — show correct queries for Section D1 on H2 console
3. **JDBC code walkthrough** — display model solution for D2 and explain each JDBC step
4. **Git + Docker demo** — show the commands from D3 in a terminal
5. **Preview Day 15** — "Tomorrow is the final assessment covering all 15 days — Java fundamentals through DevOps. Focus your revision tonight on any weak areas from today."
