# Day 13 — Basics of DevOps

## Instructor Guide

> **Duration**: Full day (~6 hrs)
> **Pre-requisite**: Day 12 — complete banking app (Java + Spring Boot + JDBC + HTML). No DevOps experience needed.
> **Case Study**: Apply DevOps practices to the banking app — Git version control, CI pipeline with GitHub Actions, Docker containerization
> **Goal by end of day**: Trainees understand the DevOps lifecycle, can use basic Git workflows, understand CI/CD pipelines, and can write a simple Dockerfile

---

## Day Structure

| Module | Topic | Duration | Key Concepts |
|--------|-------|----------|-------------|
| 1 | DevOps Culture & Principles | ~45 min | What/why DevOps, lifecycle, CALMS |
| 2 | Version Control with Git | ~2 hrs | Git architecture, commands, branching |
| 3 | Continuous Integration (CI) | ~1.5 hrs | CI concepts, GitHub Actions, pipelines |
| 4 | Containerization with Docker | ~1.5 hrs | Containers vs VMs, Dockerfile, images |

---

# Module 1: Introduction to DevOps & Culture

---

## Topic 1: What is DevOps?

#### Key Points (15 min)

- **DevOps** = Development + Operations — a culture and set of practices that brings these two teams together
- Traditional problem: Dev builds software → "throws it over the wall" → Ops deploys and maintains it → blame game when things break

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

#### DevOps goals

| Goal | What it means | Banking app example |
|------|-------------|---------------------|
| **Faster releases** | Ship features quickly | Deploy new account type in hours, not weeks |
| **Stability** | Fewer production failures | Automated tests catch bugs before deploy |
| **Automation** | Reduce manual work | Build, test, deploy with one command |
| **Collaboration** | Dev and Ops work together | Shared responsibility for uptime |

---

## Topic 2: DevOps Lifecycle

#### Key Points (15 min)

```
     Plan → Code → Build → Test → Release → Deploy → Operate → Monitor
       │                                                          │
       └──────────────────── Feedback ───────────────────────────┘
```

| Phase | Activity | Tool (example) |
|-------|----------|---------------|
| **Plan** | Requirements, stories | Jira, Trello |
| **Code** | Write code, version control | Git, GitHub |
| **Build** | Compile, package | Maven, Gradle |
| **Test** | Run automated tests | JUnit, Selenium |
| **Release** | Version, approve | GitHub Releases |
| **Deploy** | Push to servers | Docker, Kubernetes |
| **Operate** | Run in production | AWS, Azure |
| **Monitor** | Track health, errors | Prometheus, Grafana |

> **Teaching moment**: "We've been doing Plan → Code manually for 12 days. Today we automate the rest — Build, Test, Deploy."

---

## Topic 3: DevOps Culture — CALMS

#### Key Points (10 min)

| Pillar | Meaning | Example |
|--------|---------|---------|
| **C**ulture | Shared responsibility, no blame | Dev + Ops both own uptime |
| **A**utomation | Automate repetitive tasks | CI/CD pipelines |
| **L**ean | Eliminate waste, small batches | Deploy small changes frequently |
| **M**easurement | Track metrics, data-driven | Deployment frequency, error rate |
| **S**haring | Share knowledge, tools, responsibility | Shared dashboards, post-mortems |

#### DevOps vs Agile vs CI/CD

| | Agile | DevOps | CI/CD |
|---|---|---|---|
| Focus | Development process | Dev + Ops collaboration | Automation pipeline |
| Scope | How we plan and code | How we deliver end-to-end | How we build, test, deploy |
| Key practice | Sprints, standups | Automation, monitoring | Pipeline as code |
| Relationship | DevOps builds ON Agile | Extends Agile to operations | A DevOps practice |

> "Agile says 'build the right thing quickly.' DevOps says 'deliver it to users reliably.' CI/CD is the machine that makes it happen."

---

# Module 2: Version Control & Collaboration (Git)

---

## Topic 4: Why Version Control?

#### Key Points (10 min)

Without version control:
- "I made changes and now it's broken — I can't get back to the working version"
- "Who changed this file? When? Why?"
- "I need to work on a new feature but I can't break the main code"

**Version control** tracks every change, who made it, and when. You can go back to any point in history.

#### Centralized vs Distributed

| | Centralized (SVN) | Distributed (Git) |
|---|---|---|
| Architecture | One central server | Every developer has full copy |
| Offline work | Not possible | Full functionality offline |
| Speed | Slow (network for every operation) | Fast (local operations) |
| Modern usage | Legacy | **Standard** — Git dominates |

---

## Topic 5: Git Architecture

#### Key Points (15 min)

```
┌───────────────┐    git add     ┌──────────────┐   git commit   ┌────────────────┐
│ Working Tree   │ ────────────→ │ Staging Area  │ ────────────→ │ Local Repository│
│ (your files)   │               │ (index)       │               │ (.git folder)   │
└───────────────┘               └──────────────┘               └───────┬────────┘
                                                                        │ git push
                                                                        ▼
                                                               ┌────────────────┐
                                                               │ Remote (GitHub) │
                                                               └────────────────┘
```

| Area | What it is | Purpose |
|------|-----------|---------|
| **Working Tree** | Your actual files on disk | Where you edit code |
| **Staging Area** | Files marked for next commit | Choose what to commit |
| **Local Repo** | Full history on your machine | All commits stored locally |
| **Remote** | GitHub/GitLab server | Share code with team |

> "The staging area is like a loading dock. You put boxes (changed files) on the dock (staging), then the truck (commit) takes them all to the warehouse (repository) at once."

---

## Topic 6: Git Commands — Hands-on

#### Key Points (40 min)

> **Live demo** — have trainees type along. Use the banking project.

#### Setup

```bash
# Configure identity (one-time)
git config --global user.name "Your Name"
git config --global user.email "your@email.com"

# Initialize a new repo
cd bank-api
git init

# Or clone an existing repo
git clone https://github.com/username/bank-api.git
```

#### The basic workflow

```bash
# 1. Check status — see what's changed
git status

# 2. Stage specific files
git add src/main/java/com/bank/api/model/Account.java
git add src/main/java/com/bank/api/service/AccountService.java

# Or stage all changes
git add .

# 3. Commit with a message
git commit -m "Add Account model and service layer"

# 4. Push to remote
git push origin main

# 5. Pull latest changes from team
git pull origin main
```

#### Viewing history

```bash
# See commit log
git log --oneline

# See what changed in a specific commit
git show abc1234

# See unstaged changes
git diff

# See staged changes
git diff --staged
```

#### Case Study — Initialize the banking project

```bash
cd bank-api
git init
git add .
git commit -m "Initial commit: Spring Boot banking API with JDBC"

# Create GitHub repo, then:
git remote add origin https://github.com/username/bank-api.git
git push -u origin main
```

---

## Topic 7: Branching and Merging

#### Key Points (25 min)

#### Why branches?

- Work on features without affecting the main (stable) code
- Multiple developers can work in parallel
- Features are merged when ready and tested

```
main:    ●───●───●───────────●───●
                  \         /
feature:           ●───●───●
                 (add transfer endpoint)
```

#### Branch commands

```bash
# Create and switch to a new branch
git checkout -b feature/transfer-endpoint

# See all branches
git branch

# Switch branches
git checkout main

# Merge feature into main
git checkout main
git merge feature/transfer-endpoint

# Delete branch after merge
git branch -d feature/transfer-endpoint
```

#### Feature branch workflow

```bash
# 1. Create feature branch from main
git checkout main
git pull origin main
git checkout -b feature/transfer-endpoint

# 2. Work on the feature
# ... edit files ...
git add .
git commit -m "Add transfer endpoint to AccountController"
git commit -m "Add transfer method to AccountService"

# 3. Push feature branch to remote
git push -u origin feature/transfer-endpoint

# 4. Create Pull Request on GitHub (review + merge)

# 5. After merge, clean up
git checkout main
git pull origin main
git branch -d feature/transfer-endpoint
```

> **Teaching moment**: "In real teams, you NEVER commit directly to main. You create a feature branch, work on it, push it, create a Pull Request for code review, and only merge after approval."

#### GitHub/GitLab

- **GitHub**: Host Git repos, Pull Requests, Issues, Actions (CI/CD)
- **GitLab**: Similar — Merge Requests, Issues, GitLab CI
- Both provide: web-based code viewing, collaboration, CI/CD, project management

---

# Module 3: Continuous Integration (CI)

---

## Topic 8: What is CI?

#### Key Points (15 min)

**Continuous Integration** = automatically build and test code every time someone pushes a change.

```
Developer pushes code → CI server detects → Builds the project → Runs tests → Reports result
                                                                                ✅ Pass or ❌ Fail
```

#### Without CI

```
Developer 1 works for 2 weeks → pushes code → CONFLICTS with Developer 2
→ "Integration hell" → days of fixing → bugs discovered late
```

#### With CI

```
Developer pushes daily → CI builds immediately → tests run → failures caught in minutes
→ Small fixes → always working code on main
```

#### CI benefits

| Benefit | How |
|---------|-----|
| Catch bugs early | Automated tests run on every push |
| Avoid "works on my machine" | CI builds in a clean environment |
| Fast feedback | Know within minutes if your change breaks something |
| Always deployable | Main branch is always in a working state |

---

## Topic 9: CI Pipeline Concepts

#### Key Points (15 min)

A **pipeline** is a series of automated steps:

```
┌──────────┐    ┌──────────┐    ┌──────────┐    ┌──────────┐
│  BUILD    │ →  │   TEST   │ →  │ PACKAGE  │ →  │  DEPLOY  │
│           │    │          │    │          │    │          │
│ mvn       │    │ mvn test │    │ mvn      │    │ docker   │
│ compile   │    │          │    │ package  │    │ push     │
└──────────┘    └──────────┘    └──────────┘    └──────────┘
```

If any stage fails, the pipeline stops and the team is notified.

#### CI tools overview

| Tool | Type | Used by |
|------|------|---------|
| **GitHub Actions** | Cloud CI/CD built into GitHub | Modern projects |
| **Jenkins** | Self-hosted, plugin-based | Enterprise |
| **GitLab CI** | Built into GitLab | GitLab users |
| **CircleCI** | Cloud CI/CD | Startups |
| **Travis CI** | Cloud CI/CD | Open source |

---

## Topic 10: GitHub Actions — Hands-on

#### Key Points (30 min)

> **Build this live** — create a CI pipeline for the banking app.

#### How GitHub Actions works

- Workflows are defined in `.github/workflows/` as YAML files
- **Trigger**: When does it run? (push, pull request, schedule)
- **Jobs**: What does it do? (build, test)
- **Steps**: Individual commands within a job

#### Case Study — CI for the banking app

Create `.github/workflows/ci.yml`:

```yaml
name: Banking API CI

# When to run
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
    # 1. Check out the code
    - name: Checkout code
      uses: actions/checkout@v4

    # 2. Set up Java
    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'

    # 3. Build the project
    - name: Build with Maven
      run: mvn compile

    # 4. Run tests
    - name: Run tests
      run: mvn test

    # 5. Package as JAR
    - name: Package
      run: mvn package -DskipTests
```

#### Walk through the YAML

```yaml
name: Banking API CI           # Display name of the workflow
on:                            # Trigger conditions
  push:
    branches: [ main ]         # Run on push to main
  pull_request:
    branches: [ main ]         # Run on PR to main

jobs:
  build:                       # Job name
    runs-on: ubuntu-latest     # Machine type (Linux VM)

    steps:                     # Sequential steps
    - uses: actions/checkout@v4      # Pre-built action: clone the repo
    - uses: actions/setup-java@v4    # Pre-built action: install Java
    - run: mvn compile               # Shell command: compile
    - run: mvn test                  # Shell command: run tests
    - run: mvn package -DskipTests   # Shell command: create JAR
```

> **Demo**: Push this file to GitHub → go to Actions tab → see the pipeline run → green checkmark.

#### Pipeline stages and triggers

| Trigger | When | Use for |
|---------|------|---------|
| `push` to main | Every direct push | Build + test |
| `pull_request` to main | When PR is opened/updated | Review + test before merge |
| `schedule` | Cron schedule | Nightly builds |
| `workflow_dispatch` | Manual trigger | On-demand deployments |

---

# Module 4: Containerization & Environment Consistency

---

## Topic 11: The Problem Containers Solve

#### Key Points (10 min)

The classic problem:

```
Developer's machine:       Production server:
  Java 17                    Java 11 (different!)
  macOS                      Linux
  H2 database                MySQL
  Port 8080 free             Port 8080 taken

  "It works on my machine!" → "It's broken in production!"
```

**Solution**: Package EVERYTHING (code + runtime + dependencies + config) into a **container** that runs identically everywhere.

---

## Topic 12: Containers vs Virtual Machines

#### Key Points (10 min)

```
Virtual Machine                    Container
┌─────────────────┐               ┌─────────────────┐
│ App A │ App B    │               │ App A │ App B    │
│───────│──────────│               │───────│──────────│
│ OS A  │ OS B     │               │ Bins  │ Bins     │
│───────│──────────│               │ Libs  │ Libs     │
│   Hypervisor     │               │   Container Engine│
│──────────────────│               │ (Docker)          │
│  Host OS         │               │──────────────────│
│──────────────────│               │  Host OS         │
│  Hardware        │               │──────────────────│
└──────────────────┘               │  Hardware        │
                                   └──────────────────┘
```

| | Virtual Machine | Container |
|---|---|---|
| Size | GBs (full OS) | MBs (just app + libs) |
| Startup | Minutes | Seconds |
| Isolation | Full (own OS) | Process-level |
| Resource usage | Heavy | Lightweight |
| Use case | Different OS needed | Same OS, different apps |

> "A VM is like a whole house (foundation, walls, plumbing). A container is like an apartment — shared building, but your own space."

---

## Topic 13: Docker Fundamentals

#### Key Points (20 min)

#### Docker architecture

```
┌──────────────────────────────────────────────┐
│ Docker Client (CLI)                           │
│   docker build, docker run, docker push       │
└──────────────┬───────────────────────────────┘
               │
               ▼
┌──────────────────────────────────────────────┐
│ Docker Engine (daemon)                        │
│   Manages images, containers, networks        │
└──────────────┬───────────────────────────────┘
               │
               ▼
┌──────────────────────────────────────────────┐
│ Docker Registry (Docker Hub)                  │
│   Stores and distributes images               │
└──────────────────────────────────────────────┘
```

#### Key concepts

| Concept | What it is | Analogy |
|---------|-----------|---------|
| **Image** | A template/blueprint (read-only) | A recipe |
| **Container** | A running instance of an image | A dish made from the recipe |
| **Dockerfile** | Instructions to build an image | The recipe steps |
| **Registry** | Storage for images (Docker Hub) | A cookbook library |

> "An image is like a class. A container is like an object. You can create many containers from one image."

---

## Topic 14: Dockerfile and Docker Commands

#### Key Points (25 min)

#### Case Study — Dockerfile for the banking app

```dockerfile
# 1. Start from a base image with Java
FROM eclipse-temurin:17-jdk-alpine

# 2. Set the working directory inside the container
WORKDIR /app

# 3. Copy the built JAR file into the container
COPY target/bank-api-0.0.1-SNAPSHOT.jar app.jar

# 4. Expose the port the app runs on
EXPOSE 8080

# 5. Command to run the application
CMD ["java", "-jar", "app.jar"]
```

Walk through each line:

| Instruction | Purpose |
|------------|---------|
| `FROM` | Base image — starting point (Java 17 on Alpine Linux) |
| `WORKDIR` | Create and set working directory inside container |
| `COPY` | Copy file from host into container |
| `EXPOSE` | Document which port the app uses (informational) |
| `CMD` | Command to run when the container starts |

#### Docker commands

```bash
# Build an image from the Dockerfile
docker build -t bank-api:1.0 .

# List images
docker images

# Run a container from the image
docker run -p 8080:8080 bank-api:1.0

# Run in background (detached)
docker run -d -p 8080:8080 --name bank-app bank-api:1.0

# List running containers
docker ps

# Stop a container
docker stop bank-app

# Remove a container
docker rm bank-app

# View container logs
docker logs bank-app
```

#### Image build and run lifecycle

```
Dockerfile                 Image                    Container(s)
──────────                ──────                   ────────────
FROM java:17    →  docker build  →  bank-api:1.0  →  docker run  →  Running app
COPY app.jar       (build once)     (reusable)       (run many)      on port 8080
CMD java -jar
```

#### Complete workflow for the banking app

```bash
# 1. Build the Spring Boot JAR
mvn clean package -DskipTests

# 2. Build the Docker image
docker build -t bank-api:1.0 .

# 3. Run the container
docker run -d -p 8080:8080 --name bank-app bank-api:1.0

# 4. Test it
curl http://localhost:8080/api/accounts

# 5. Stop and clean up
docker stop bank-app
docker rm bank-app
```

> **Teaching moment**: "This container can run on ANY machine with Docker — your laptop, a colleague's laptop, a cloud server. Same behavior everywhere. No more 'works on my machine.'"

---

## Day 13 Exercises

### Exercise 1: Git Basics
**Problem**: Initialize a Git repo for the banking project, make 3 meaningful commits (each adding/changing a feature), and view the log.

### Exercise 2: Feature Branch Workflow
**Problem**: Create a feature branch `feature/account-summary`, add a summary endpoint, commit, push, and describe how you would create a Pull Request.

### Exercise 3: GitHub Actions Pipeline
**Problem**: Write a GitHub Actions workflow that: checks out code, sets up Java 17, builds with Maven, and runs tests. Save it as `.github/workflows/ci.yml`.

### Exercise 4: Dockerfile
**Problem**: Write a Dockerfile for the banking app. Build the image, run the container, and test with curl. Document the steps.

### Exercise 5: Full DevOps Challenge
**Problem**: Create a complete DevOps setup:
1. Git repo with meaningful commit history (at least 5 commits)
2. `.github/workflows/ci.yml` with build + test stages
3. `Dockerfile` for containerization
4. A `README.md` with build/run instructions

---

## Day 13 Quiz (10 questions)

1. What does DevOps stand for? What problem does it solve?
2. Name the 8 phases of the DevOps lifecycle.
3. What does CALMS stand for?
4. What is the difference between centralized and distributed version control?
5. What are the three areas in Git (working tree, staging, repository)?
6. What is the purpose of a feature branch?
7. What is Continuous Integration? Why is it important?
8. What is the difference between a Docker image and a container?
9. What does each line in a Dockerfile do: `FROM`, `COPY`, `EXPOSE`, `CMD`?
10. What problem does containerization solve?

---

## Day 13 Summary

### Module 1 — DevOps Culture

| Concept | Key Takeaway |
|---------|-------------|
| DevOps | Dev + Ops collaboration, shared ownership |
| Lifecycle | Plan → Code → Build → Test → Release → Deploy → Operate → Monitor |
| CALMS | Culture, Automation, Lean, Measurement, Sharing |
| vs Agile | DevOps extends Agile to operations |

### Module 2 — Git

| Concept | Key Commands |
|---------|-------------|
| Init/Clone | `git init`, `git clone` |
| Basic workflow | `git add`, `git commit`, `git push`, `git pull` |
| Branching | `git checkout -b`, `git merge`, `git branch -d` |
| Feature workflow | Branch → Commit → Push → Pull Request → Merge |

### Module 3 — CI/CD

| Concept | Key Takeaway |
|---------|-------------|
| CI | Auto build + test on every push |
| Pipeline | Build → Test → Package → Deploy |
| GitHub Actions | YAML workflow in `.github/workflows/` |
| Triggers | push, pull_request, schedule |

### Module 4 — Docker

| Concept | Key Takeaway |
|---------|-------------|
| Container vs VM | Lightweight, fast, portable vs full OS |
| Image | Read-only template (like a class) |
| Container | Running instance (like an object) |
| Dockerfile | Instructions to build an image |
| Key commands | `docker build`, `docker run`, `docker ps`, `docker stop` |

> **Preview for Day 14**: "Tomorrow is SQL and Networks Assessment — covering everything from SQL queries (Day 11-12) and DevOps concepts (today)."
