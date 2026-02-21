# Day 13 — Basics of DevOps

## Student Handout

> **What you'll learn today**: How to manage code with Git, automate builds with CI/CD, and package applications with Docker. We'll apply these DevOps practices to our banking app.

---

# Module 1: DevOps Culture & Principles

---

## 1. What is DevOps?

**DevOps** = **Dev**elopment + **Op**erations — a culture and set of practices that brings these two teams together to deliver software faster and more reliably.

### The problem DevOps solves

```
TRADITIONAL (Silos)                   DEVOPS (Collaboration)
┌──────────┐   ┌──────────┐          ┌─────────────────────┐
│   DEV    │   │   OPS    │          │    DEV + OPS        │
│          │ → │          │          │    Work together     │
│ "It works│   │"It broke │          │    Shared ownership  │
│  on my   │   │ in prod" │          │    Automated pipeline│
│  machine"│   │          │          │                      │
└──────────┘   └──────────┘          └─────────────────────┘
```

### DevOps goals

| Goal | What it means |
|------|-------------|
| **Faster releases** | Ship features in hours, not weeks |
| **Stability** | Fewer production failures, faster recovery |
| **Automation** | Machines do repetitive work (build, test, deploy) |
| **Collaboration** | Dev and Ops share responsibility |

---

## 2. DevOps Lifecycle

```
     Plan → Code → Build → Test → Release → Deploy → Operate → Monitor
       │                                                          │
       └──────────────────── Feedback ───────────────────────────┘
```

| Phase | What happens | Tool examples |
|-------|-------------|--------------|
| **Plan** | Define requirements | Jira, Trello |
| **Code** | Write code, version control | Git, GitHub |
| **Build** | Compile, resolve dependencies | Maven, Gradle |
| **Test** | Run automated tests | JUnit, Selenium |
| **Release** | Version and approve | GitHub Releases |
| **Deploy** | Push to servers | Docker, Kubernetes |
| **Operate** | Run in production | AWS, Azure |
| **Monitor** | Track health, errors | Prometheus, Grafana |

We've been doing Plan → Code manually for 12 days. Today we automate the Build → Test → Deploy part.

---

## 3. CALMS Framework

DevOps culture is built on five pillars:

| Pillar | Meaning | Example |
|--------|---------|---------|
| **C**ulture | Shared responsibility, no blame | Dev + Ops both own uptime |
| **A**utomation | Automate everything repetitive | CI/CD pipelines build and test automatically |
| **L**ean | Small batches, eliminate waste | Deploy small changes frequently |
| **M**easurement | Track metrics, data-driven decisions | Deployment frequency, failure rate |
| **S**haring | Share knowledge and tools | Shared dashboards, documentation, post-mortems |

### DevOps vs Agile vs CI/CD

| | Agile | DevOps | CI/CD |
|---|---|---|---|
| Focus | Development process | Dev + Ops collaboration | Automation pipeline |
| Scope | How we plan and code | How we deliver end-to-end | How we build, test, deploy |
| Key practice | Sprints, standups | Automation, monitoring | Pipeline as code |
| Relationship | Foundation | Builds on Agile | A practice within DevOps |

---

# Module 2: Version Control with Git

---

## 4. Why Version Control?

Without version control:
- "I changed something and now it's broken — I can't go back"
- "Who changed this file? When? Why?"
- "Two of us edited the same file — whose changes win?"

**Version control** tracks every change, by whom, and when. You can go back to any point in history.

### Centralized vs Distributed

| | Centralized (SVN) | Distributed (Git) |
|---|---|---|
| Architecture | One central server | Every developer has full history |
| Offline work | Not possible | Full functionality offline |
| Speed | Slow (network needed) | Fast (local operations) |
| Modern usage | Legacy | **Standard** — Git dominates |

---

## 5. Git Architecture

Git has three local areas and one remote:

```
┌───────────────┐    git add     ┌──────────────┐   git commit   ┌────────────────┐
│ Working Tree   │ ────────────→ │ Staging Area  │ ────────────→ │ Local Repository│
│ (your files)   │               │ (index)       │               │ (.git folder)   │
└───────────────┘               └──────────────┘               └───────┬────────┘
                                                                        │ git push
                                                                        ▼
                                                               ┌────────────────┐
                                                               │ Remote (GitHub) │
                                                               │                 │
                                                               │ git pull ↓      │
                                                               └────────────────┘
```

| Area | What it is | Analogy |
|------|-----------|---------|
| **Working Tree** | Your actual files on disk | Your desk |
| **Staging Area** | Files marked for next commit | A loading dock |
| **Local Repo** | Full commit history on your machine | Your personal archive |
| **Remote** | GitHub/GitLab server | The company archive |

The staging area lets you choose WHICH changes to include in a commit. You don't have to commit everything at once.

---

## 6. Essential Git Commands

### First-time setup

```bash
git config --global user.name "Your Name"
git config --global user.email "your@email.com"
```

### Starting a project

```bash
# Initialize a new repo
git init

# Or clone an existing one
git clone https://github.com/username/bank-api.git
```

### The daily workflow

```bash
# 1. Check what's changed
git status

# 2. Stage files for commit
git add Account.java                   # stage one file
git add src/main/java/                 # stage a directory
git add .                              # stage everything

# 3. Commit with a descriptive message
git commit -m "Add deposit endpoint to AccountController"

# 4. Push to remote (GitHub)
git push origin main

# 5. Pull latest changes from team
git pull origin main
```

### Viewing history

```bash
# Compact log (one line per commit)
git log --oneline

# See what changed in a commit
git show abc1234

# See unstaged changes
git diff

# See staged changes
git diff --staged
```

### Quick reference

| Command | What it does |
|---------|-------------|
| `git init` | Create a new repo |
| `git clone URL` | Copy a remote repo |
| `git status` | Show changed/staged files |
| `git add FILE` | Stage a file |
| `git commit -m "msg"` | Create a commit |
| `git push origin main` | Upload to remote |
| `git pull origin main` | Download from remote |
| `git log --oneline` | View commit history |
| `git diff` | Show unstaged changes |

---

## 7. Branching and Merging

### Why branches?

Branches let you work on features without affecting the main (stable) code. Multiple developers can work in parallel.

```
main:    ●───●───●───────────●───●
                  \         /
feature:           ●───●───●
                 (add transfer endpoint)
```

### Branch commands

```bash
# Create and switch to a new branch
git checkout -b feature/transfer-endpoint

# See all branches (* = current)
git branch

# Switch to an existing branch
git checkout main

# Merge feature into main
git checkout main
git merge feature/transfer-endpoint

# Delete branch after merge
git branch -d feature/transfer-endpoint
```

### Feature branch workflow (how real teams work)

```bash
# 1. Start from latest main
git checkout main
git pull origin main

# 2. Create a feature branch
git checkout -b feature/transfer-endpoint

# 3. Work on the feature (edit, add, commit — multiple times)
git add .
git commit -m "Add transfer method to AccountService"
git add .
git commit -m "Add transfer endpoint to AccountController"

# 4. Push the feature branch to GitHub
git push -u origin feature/transfer-endpoint

# 5. On GitHub: Create a Pull Request (PR)
#    - Team reviews the code
#    - CI pipeline runs tests
#    - Approved → Merge into main

# 6. Clean up locally
git checkout main
git pull origin main
git branch -d feature/transfer-endpoint
```

**Rule**: Never commit directly to `main`. Always use feature branches + Pull Requests.

### GitHub / GitLab

| Feature | Purpose |
|---------|---------|
| **Repository** | Host your Git repo in the cloud |
| **Pull Request** (GitHub) / **Merge Request** (GitLab) | Request to merge a branch — includes code review |
| **Issues** | Track bugs and features |
| **Actions** (GitHub) / **CI** (GitLab) | Automated build and test pipelines |

---

# Module 3: Continuous Integration (CI)

---

## 8. What is CI?

**Continuous Integration** = automatically build and test code every time someone pushes a change.

```
Developer pushes code → CI server detects → Builds project → Runs tests → Reports result
                                                                           ✅ Pass or ❌ Fail
```

### Without CI vs With CI

| Without CI | With CI |
|-----------|---------|
| Developer works for weeks, then pushes | Developer pushes daily |
| Merge conflicts, "integration hell" | Small merges, conflicts caught early |
| Bugs found in production | Bugs caught in minutes |
| "Works on my machine" | Works in CI = works everywhere |

### CI pipeline stages

```
┌──────────┐    ┌──────────┐    ┌──────────┐    ┌──────────┐
│  BUILD    │ →  │   TEST   │ →  │ PACKAGE  │ →  │  DEPLOY  │
│ mvn       │    │ mvn test │    │ mvn      │    │ docker   │
│ compile   │    │          │    │ package  │    │ push     │
└──────────┘    └──────────┘    └──────────┘    └──────────┘
```

If any stage fails, the pipeline **stops** and the team is notified.

### CI tools

| Tool | Type | Key Feature |
|------|------|-------------|
| **GitHub Actions** | Cloud, built into GitHub | YAML workflows, free for public repos |
| **Jenkins** | Self-hosted | Highly configurable, plugin ecosystem |
| **GitLab CI** | Built into GitLab | Integrated with merge requests |

---

## 9. GitHub Actions — Building a CI Pipeline

GitHub Actions uses YAML files in `.github/workflows/` to define pipelines.

### Banking App CI Pipeline

Create `.github/workflows/ci.yml`:

```yaml
name: Banking API CI

# When to run this pipeline
on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

# What to do
jobs:
  build:
    runs-on: ubuntu-latest

    steps:
    # Step 1: Check out the code from the repo
    - name: Checkout code
      uses: actions/checkout@v4

    # Step 2: Install Java 17
    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'

    # Step 3: Compile the project
    - name: Build with Maven
      run: mvn compile

    # Step 4: Run all tests
    - name: Run tests
      run: mvn test

    # Step 5: Create the deployable JAR
    - name: Package
      run: mvn package -DskipTests
```

### Understanding the YAML

| Section | Purpose |
|---------|---------|
| `name` | Display name of the workflow |
| `on` | Trigger: when does this pipeline run? |
| `jobs` | One or more jobs to execute |
| `runs-on` | What machine to use (Ubuntu Linux VM) |
| `steps` | Sequential actions within a job |
| `uses` | Pre-built action from the GitHub marketplace |
| `run` | Shell command to execute |

### Pipeline triggers

| Trigger | When it runs | Use for |
|---------|-------------|---------|
| `push` to main | Every push to main branch | Build + test |
| `pull_request` to main | When a PR is created/updated | Test before merging |
| `schedule` (cron) | On a schedule | Nightly builds |
| `workflow_dispatch` | Manual trigger from GitHub UI | On-demand deployments |

After pushing this file to GitHub, go to the **Actions** tab to see the pipeline run.

---

# Module 4: Containerization with Docker

---

## 10. The Problem Containers Solve

```
Developer's laptop:          Production server:
  Java 17                      Java 11 (different!)
  macOS                        Linux
  H2 database                  MySQL
  Works perfectly              Breaks!

  "It works on my machine!" → "It's broken in production!"
```

**Solution**: Package EVERYTHING (code + Java runtime + dependencies + config) into a **container** that runs identically everywhere.

---

## 11. Containers vs Virtual Machines

```
Virtual Machine                    Container
┌─────────────────┐               ┌─────────────────┐
│ App A │ App B    │               │ App A │ App B    │
│───────│──────────│               │───────│──────────│
│ OS A  │ OS B     │               │ Bins  │ Bins     │
│───────│──────────│               │ Libs  │ Libs     │
│   Hypervisor     │               │   Docker Engine  │
│──────────────────│               │──────────────────│
│  Host OS         │               │  Host OS         │
└──────────────────┘               └──────────────────┘
```

| | Virtual Machine | Container |
|---|---|---|
| Size | GBs (includes full OS) | MBs (app + libraries only) |
| Startup time | Minutes | Seconds |
| Isolation | Full OS isolation | Process-level isolation |
| Resource usage | Heavy | Lightweight |
| Portability | Less portable | Runs anywhere Docker runs |

A VM is like a **whole house** (foundation, walls, plumbing). A container is like an **apartment** — shared building infrastructure, but your own isolated space.

---

## 12. Docker Concepts

### Architecture

```
┌──────────────────────────────────────┐
│ Docker Client (CLI)                   │
│   docker build, docker run, docker ps │
└──────────────┬───────────────────────┘
               │
               ▼
┌──────────────────────────────────────┐
│ Docker Engine (daemon)                │
│   Manages images, containers, volumes │
└──────────────┬───────────────────────┘
               │
               ▼
┌──────────────────────────────────────┐
│ Docker Registry (Docker Hub)          │
│   Stores and shares images            │
└──────────────────────────────────────┘
```

### Key concepts

| Concept | What it is | Analogy |
|---------|-----------|---------|
| **Image** | A read-only template with app + dependencies | A recipe |
| **Container** | A running instance of an image | A dish made from the recipe |
| **Dockerfile** | Instructions to build an image | The recipe steps |
| **Registry** | Storage for images (Docker Hub) | A cookbook library |

An image is like a **class**. A container is like an **object**. You can create many containers from one image.

---

## 13. Dockerfile

A Dockerfile contains instructions to build an image.

### Banking App Dockerfile

```dockerfile
# 1. Start from a base image that has Java 17
FROM eclipse-temurin:17-jdk-alpine

# 2. Set working directory inside the container
WORKDIR /app

# 3. Copy the JAR file from your machine into the container
COPY target/bank-api-0.0.1-SNAPSHOT.jar app.jar

# 4. Document which port the app uses
EXPOSE 8080

# 5. Command to run when the container starts
CMD ["java", "-jar", "app.jar"]
```

| Instruction | Purpose |
|------------|---------|
| `FROM` | Base image (Java 17 on Alpine Linux — small footprint) |
| `WORKDIR` | Set working directory inside container |
| `COPY` | Copy file from host machine into container |
| `EXPOSE` | Document which port the app listens on |
| `CMD` | Command that runs when the container starts |

### Image build and run lifecycle

```
  Dockerfile          docker build         Image              docker run          Container
  ──────────         ────────────         ──────             ──────────          ─────────
  FROM java:17   →   Builds layers   →   bank-api:1.0   →  Creates and    →   Running app
  COPY app.jar       (build once)        (reusable)         runs instance      on port 8080
  CMD java -jar                          (read-only)        (writeable)
```

---

## 14. Docker Commands

### Build and run

```bash
# First, build the Spring Boot JAR
mvn clean package -DskipTests

# Build a Docker image (-t = tag/name)
docker build -t bank-api:1.0 .

# Run a container from the image
docker run -p 8080:8080 bank-api:1.0

# Run in background (-d = detached, --name = give it a name)
docker run -d -p 8080:8080 --name bank-app bank-api:1.0

# Test it
curl http://localhost:8080/api/accounts
```

### Manage containers

```bash
# List running containers
docker ps

# List all containers (including stopped)
docker ps -a

# Stop a container
docker stop bank-app

# Start a stopped container
docker start bank-app

# View container logs
docker logs bank-app

# Remove a container (must be stopped first)
docker rm bank-app
```

### Manage images

```bash
# List images
docker images

# Remove an image
docker rmi bank-api:1.0
```

### Complete workflow

```bash
# 1. Build the application
mvn clean package -DskipTests

# 2. Build the Docker image
docker build -t bank-api:1.0 .

# 3. Run the container
docker run -d -p 8080:8080 --name bank-app bank-api:1.0

# 4. Verify it's running
docker ps
curl http://localhost:8080/api/accounts

# 5. View logs
docker logs bank-app

# 6. Stop and clean up
docker stop bank-app
docker rm bank-app
```

### Port mapping: `-p 8080:8080`

```
     Host Machine               Container
     ────────────               ─────────
     localhost:8080  ──────→    :8080 (Spring Boot)

-p HOST_PORT:CONTAINER_PORT
```

The first port is on YOUR machine. The second is inside the container. They don't have to be the same — `-p 9090:8080` would make the app available at `localhost:9090`.

---

## Exercises

### Exercise 1: Git Basics

**Problem**: Initialize a Git repo for the banking project and make 3 meaningful commits:
1. First commit: the model classes
2. Second commit: the service layer
3. Third commit: the controller and REST endpoints

View the log to see your commit history.

<details>
<summary><strong>Solution</strong></summary>

```bash
cd bank-api
git init

# Commit 1: Models
git add src/main/java/com/bank/api/model/
git commit -m "Add Account model with getters/setters for REST"

# Commit 2: Service
git add src/main/java/com/bank/api/service/
git commit -m "Add AccountService with CRUD operations and JDBC"

# Commit 3: Controller
git add src/main/java/com/bank/api/controller/
git commit -m "Add AccountController with REST endpoints"

# View history
git log --oneline
# Output:
# a3b4c5d Add AccountController with REST endpoints
# f6g7h8i Add AccountService with CRUD operations and JDBC
# j9k0l1m Add Account model with getters/setters for REST
```
</details>

---

### Exercise 2: Feature Branch Workflow

**Problem**: Create a feature branch `feature/account-summary`, add a summary endpoint that returns total accounts and total balance, commit, and describe the Pull Request process.

<details>
<summary><strong>Solution</strong></summary>

```bash
# Create feature branch
git checkout -b feature/account-summary

# Add the summary endpoint to AccountController:
# @GetMapping("/summary")
# public Map<String, Object> getSummary() { ... }

# Stage and commit
git add src/main/java/com/bank/api/controller/AccountController.java
git add src/main/java/com/bank/api/service/AccountService.java
git commit -m "Add account summary endpoint with total count and balance"

# Push to remote
git push -u origin feature/account-summary

# Pull Request process:
# 1. Go to GitHub → repository → "Compare & pull request"
# 2. Write a description: "Adds GET /api/accounts/summary endpoint"
# 3. Request reviewers
# 4. CI pipeline runs automatically (tests must pass)
# 5. Reviewer approves
# 6. Click "Merge pull request"
# 7. Delete the feature branch on GitHub

# Clean up locally
git checkout main
git pull origin main
git branch -d feature/account-summary
```
</details>

---

### Exercise 3: GitHub Actions Pipeline

**Problem**: Write a GitHub Actions workflow that builds and tests the banking app. The pipeline should: check out code, set up Java 17, compile with Maven, and run tests.

<details>
<summary><strong>Solution</strong></summary>

Create `.github/workflows/ci.yml`:

```yaml
name: Banking API CI

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build-and-test:
    runs-on: ubuntu-latest

    steps:
    - name: Checkout code
      uses: actions/checkout@v4

    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'
        cache: maven

    - name: Build
      run: mvn compile

    - name: Run tests
      run: mvn test

    - name: Package JAR
      run: mvn package -DskipTests

    - name: Upload JAR artifact
      uses: actions/upload-artifact@v4
      with:
        name: bank-api-jar
        path: target/*.jar
```

Push and check the Actions tab on GitHub to see the pipeline run.
</details>

---

### Exercise 4: Dockerfile

**Problem**: Write a Dockerfile for the banking app, build the image, run the container, and verify with curl.

<details>
<summary><strong>Solution</strong></summary>

Create `Dockerfile` in the project root:

```dockerfile
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/bank-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
```

Commands:

```bash
# Build the JAR first
mvn clean package -DskipTests

# Build Docker image
docker build -t bank-api:1.0 .

# Run container
docker run -d -p 8080:8080 --name bank-app bank-api:1.0

# Test
curl http://localhost:8080/api/accounts
# Expected: JSON array of accounts

# Check logs
docker logs bank-app

# Clean up
docker stop bank-app
docker rm bank-app
```
</details>

---

### Exercise 5: Full DevOps Setup (Challenge)

**Problem**: Create a complete DevOps setup for the banking app:
1. Git repo with at least 5 meaningful commits
2. `.github/workflows/ci.yml` with build + test
3. `Dockerfile` for containerization
4. `README.md` with setup instructions

<details>
<summary><strong>Solution</strong></summary>

**README.md:**

```markdown
# Bank API

A REST API for banking operations built with Spring Boot and JDBC.

## Prerequisites
- Java 17+
- Maven 3.8+
- Docker (optional, for containerized deployment)

## Build & Run (Local)
```bash
mvn clean package
java -jar target/bank-api-0.0.1-SNAPSHOT.jar
```

## Build & Run (Docker)
```bash
mvn clean package -DskipTests
docker build -t bank-api:1.0 .
docker run -d -p 8080:8080 --name bank-app bank-api:1.0
```

## API Endpoints
| Method | URL | Description |
|--------|-----|-------------|
| GET | /api/accounts | List all accounts |
| GET | /api/accounts/{id} | Get one account |
| POST | /api/accounts | Create account |
| PUT | /api/accounts/{id} | Update account |
| DELETE | /api/accounts/{id} | Delete account |
| POST | /api/accounts/{id}/deposit?amount=X | Deposit |
| POST | /api/accounts/{id}/withdraw?amount=X | Withdraw |

## CI/CD
Automated with GitHub Actions. On every push to main:
1. Compile
2. Run tests
3. Package JAR
```

**Git commits:**

```bash
git init
git add src/main/java/com/bank/api/model/
git commit -m "Add Account model"

git add src/main/java/com/bank/api/service/ src/main/java/com/bank/api/repository/
git commit -m "Add AccountService and AccountRepository with JDBC"

git add src/main/java/com/bank/api/controller/
git commit -m "Add REST controller with CRUD endpoints"

git add .github/workflows/ci.yml
git commit -m "Add GitHub Actions CI pipeline"

git add Dockerfile README.md
git commit -m "Add Dockerfile and project documentation"
```
</details>

---

## Quick Quiz

1. What does DevOps stand for? What problem does it solve?
2. Name the 8 phases of the DevOps lifecycle.
3. What does CALMS stand for?
4. What is the difference between centralized and distributed version control?
5. What are the three areas in Git (working tree, staging, repository)?
6. What is the purpose of a feature branch?
7. What is Continuous Integration? Why is it important?
8. What is the difference between a Docker image and a container?
9. What does each Dockerfile instruction do: `FROM`, `COPY`, `EXPOSE`, `CMD`?
10. What problem does containerization solve?

<details>
<summary><strong>Answers</strong></summary>

1. **Development + Operations.** It solves the silo problem — Dev and Ops teams working separately, leading to slow releases, blame games, and "works on my machine" issues. DevOps creates shared ownership and automated delivery.

2. **Plan → Code → Build → Test → Release → Deploy → Operate → Monitor.** It's a continuous cycle — monitoring feeds back into planning.

3. **Culture** (shared responsibility), **Automation** (automate repetitive tasks), **Lean** (small batches, eliminate waste), **Measurement** (track metrics), **Sharing** (knowledge sharing across teams).

4. **Centralized** (SVN): one central server, can't work offline, every operation needs the network. **Distributed** (Git): every developer has the full repository history, works offline, fast local operations. Git is the standard today.

5. **Working Tree** — your actual files on disk where you edit. **Staging Area** — files marked for the next commit (`git add`). **Repository** — full commit history stored in `.git` folder (`git commit`).

6. A **feature branch** isolates new work from the main (stable) code. You can develop, test, and review a feature without affecting `main`. When ready, you merge via a Pull Request. This enables parallel development and code review.

7. **CI** = automatically building and testing code on every push. Important because: catches bugs immediately (not weeks later), prevents integration conflicts, ensures `main` always works, gives fast feedback. "If it hurts, do it more often."

8. An **image** is a read-only template containing the app, runtime, and dependencies (like a class). A **container** is a running instance of an image (like an object). You build an image once and can create many containers from it.

9. **`FROM`** — specifies the base image to start from (e.g., Java 17). **`COPY`** — copies files from the host into the image. **`EXPOSE`** — documents which port the app uses (informational). **`CMD`** — the command that runs when a container starts.

10. **"Works on my machine" problem.** Different environments (dev, staging, production) have different OS versions, Java versions, configurations. Containers package EVERYTHING together — code, runtime, dependencies, config — so the app runs identically everywhere Docker is installed.
</details>

---

## What We Built Today

### Module 1 — DevOps Culture

| Concept | Key Takeaway |
|---------|-------------|
| DevOps | Dev + Ops collaboration, shared ownership, automation |
| Lifecycle | Plan → Code → Build → Test → Release → Deploy → Operate → Monitor |
| CALMS | Culture, Automation, Lean, Measurement, Sharing |

### Module 2 — Git

| Concept | Key Commands |
|---------|-------------|
| Init/Clone | `git init`, `git clone URL` |
| Daily workflow | `git add`, `git commit -m`, `git push`, `git pull` |
| Branching | `git checkout -b`, `git merge`, `git branch -d` |
| Team workflow | Feature branch → Commits → Push → Pull Request → Review → Merge |

### Module 3 — CI/CD

| Concept | Key Takeaway |
|---------|-------------|
| CI | Automatic build + test on every push |
| GitHub Actions | YAML workflow in `.github/workflows/ci.yml` |
| Pipeline | Build → Test → Package (→ Deploy) |

### Module 4 — Docker

| Concept | Key Takeaway |
|---------|-------------|
| Container vs VM | Lightweight, fast, portable |
| Dockerfile | `FROM` → `COPY` → `EXPOSE` → `CMD` |
| Key commands | `docker build`, `docker run`, `docker ps`, `docker stop` |

### Full DevOps picture for the banking app

```
Developer pushes code to GitHub
    ↓
GitHub Actions CI pipeline runs:
    ├── Checkout code
    ├── Set up Java 17
    ├── mvn compile (build)
    ├── mvn test (test)
    └── mvn package (create JAR)
    ↓
Docker builds image from JAR + Dockerfile
    ↓
Container runs on any machine
    ↓
Users access the banking app
```

## What's Next (Day 14 Preview)

Tomorrow is **SQL and Networks Assessment** — covering SQL queries (Day 11-12), JDBC concepts (Day 12), and DevOps fundamentals (today).
