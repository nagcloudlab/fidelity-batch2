# Day 6 — Java and Algorithms Assessment

## Instructor Guide

> **Duration**: Full day
> **Coverage**: Days 1–5 (Programming Fundamentals, OOP, Exception Handling, Collections, Data Structures, Searching Algorithms)
> **Format**: Written assessment + Coding assignment
> **Suggested split**: Section A-C written (~1.5 hrs) + Section D coding (~2 hrs) + review (~30 min)

---

## Assessment Structure

| Section | Type | Questions | Marks | Time |
|---------|------|-----------|-------|------|
| A | Multiple Choice | 20 | 20 | ~20 min |
| B | Short Answer | 10 | 30 | ~30 min |
| C | Code Reading / Debugging | 5 | 25 | ~30 min |
| D | Coding Assignments | 3 | 45 | ~2 hrs |
| **Total** | | **38** | **120** | ~3.5 hrs |

---

## Grading Guidelines

| Grade | Percentage | Interpretation |
|-------|-----------|---------------|
| Excellent | 85–100% | Solid understanding, ready for next module |
| Good | 70–84% | Minor gaps, brief review needed |
| Needs Improvement | 50–69% | Significant gaps, targeted revision on weak areas |
| Below Expectations | <50% | Requires one-on-one support before proceeding |

---

## Conducting the Assessment

### Before
- Remind trainees this is a learning checkpoint, not a pass/fail exam
- Allow them to use their own Day 1–5 code as reference (open notes)
- No internet search allowed for written sections
- Coding section: IDE allowed, no copy-paste from training materials

### During
- Sections A–C: Written (paper or online form)
- Section D: On their machines, submit `.java` files
- If a trainee finishes early, they can attempt bonus questions

### After
- Review answers as a class — this is a teaching opportunity
- Focus on commonly missed questions
- Use Section D submissions to identify who needs extra help

---

## Common Weak Areas to Watch For

| Concept | Common Mistake | What to Reinforce |
|---------|---------------|-------------------|
| `==` vs `.equals()` | Using `==` for String comparison | Reference vs value equality |
| Method overriding vs overloading | Confusing the two | Same params (override) vs different params (overload) |
| `private` field access | Trying to access from outside class | Encapsulation, getters/setters |
| Stack empty check | Calling `pop()` without `isEmpty()` | Always guard with isEmpty() |
| `finally` block | Thinking it only runs on exceptions | It ALWAYS runs |
| Abstract class instantiation | Trying `new Account()` | Abstract = blueprint only |
| HashMap `get()` returns null | Not checking for null | Always handle the null case |
| Binary search pre-condition | Forgetting data must be sorted | Sort first, then search |

---

## Answer Key

Provided after each section in the Student Assessment paper below. Keep the answer key separate when distributing to trainees.

---

## Post-Assessment Review Plan

After grading, spend 30 minutes reviewing:

1. **Top 5 most-missed questions** — go through them on the board
2. **Section D common issues** — show a model solution, compare with common mistakes
3. **Preview Day 7** — "Now that we've solidified Java fundamentals, we'll start persisting data to files. No more losing everything when the program exits."
