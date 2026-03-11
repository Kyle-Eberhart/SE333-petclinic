# Kyle Eberhart -- SE333 Final Project

## AI-Assisted Test Generation Agent for Spring Petclinic

This project was developed for **SE333 – Software Testing** as the final project on **AI-assisted software agents and automated testing workflows**.

The objective of the project is to explore how a prompt-driven AI agent can assist with software testing by automatically generating tests, executing them, analyzing code coverage, and iteratively improving the test suite.

The application under test is **Spring Petclinic**, a Java Spring Boot application used as a realistic testing target.

Rather than manually writing every test case, this project demonstrates how an **AI-assisted testing workflow** can:

* generate test cases
* execute tests
* analyze coverage results
* identify gaps
* iteratively improve test coverage

The goal is not simply to produce more tests, but to create a **feedback-driven testing process**.

---

# Project Objectives

The goals of this project are to:

1. Build a prompt-driven AI testing agent.
2. Automatically generate and execute unit tests.
3. Analyze coverage results using **JaCoCo**.
4. Iteratively improve test coverage.
5. Document the process and insights gained from AI-assisted development.

---

# Application Under Test

This project analyzes the **Spring Petclinic** application.

Spring Petclinic is an open-source Spring Boot sample application that models a veterinary clinic system. It contains domain models, controllers, and validation logic, making it suitable for exploring automated testing techniques.

To keep the experiment focused and manageable, testing was constrained to the following classes:

* `Owner`
* `Pet`
* `Visit`
* `OwnerController`

Limiting scope allowed the agent to focus on deeper coverage improvements instead of generating shallow tests across the entire codebase.

---

# Repository Structure

```
.
├── .github/
│   └── prompts/
│       └── tester.prompt.md
│
├── docs/
│   ├── coverage-iteration-log.md
│   ├── demo-script.md
│   └── reflection-notes.md
│
├── src/
│   ├── main/
│   └── test/
│
├── pom.xml
└── README.md
```

### Key Components

| Path                               | Purpose                                                      |
| ---------------------------------- | ------------------------------------------------------------ |
| `.github/prompts/tester.prompt.md` | Prompt configuration defining the AI testing agent behavior  |
| `docs/coverage-iteration-log.md`   | Tracks improvements made across multiple test iterations     |
| `docs/demo-script.md`              | Notes used for the final project demonstration               |
| `docs/reflection-notes.md`         | Reflection notes used to produce the final reflection report |
| `src/test/`                        | Generated and refined test cases                             |
| `pom.xml`                          | Maven configuration for building and running tests           |

---

# Technical Stack

* **Language:** Java
* **Framework:** Spring Boot
* **Build Tool:** Maven
* **Coverage Tool:** JaCoCo
* **Agent Workflow:** Prompt-based testing agent
* **IDE / Environment:** VS Code / Cursor
* **Version Control:** Git / GitHub

---

# System Architecture

The project follows an **iterative test improvement cycle** driven by coverage feedback.

Workflow:

```
AI Agent Prompt
      ↓
Generate Tests
      ↓
Run Tests (mvn test)
      ↓
Analyze Coverage (JaCoCo)
      ↓
Identify Untested Paths
      ↓
Generate Additional Tests
      ↓
Repeat
```

This approach simulates an **AI-assisted software testing workflow** where coverage data informs future testing decisions.

---

# MCP Tool / Agent Documentation

The testing workflow is controlled by a **prompt-driven agent configuration**.

## Agent Prompt File

```
.github/prompts/tester.prompt.md
```

The prompt defines:

* how tests are generated
* how tests are executed
* how coverage is analyzed
* how improvements are made

### Agent Responsibilities

The agent performs the following steps:

1. Generate test cases for the selected classes.
2. Execute the test suite using Maven.
3. Inspect coverage results produced by JaCoCo.
4. Identify untested code paths.
5. Generate additional tests targeting those gaps.
6. Repeat until coverage improves.

### Inputs

* source code under test
* existing test suite
* Maven build results
* JaCoCo coverage report

### Outputs

* additional test cases
* coverage improvements
* refined testing strategy

---

# Installation & Configuration

## Prerequisites

Ensure the following tools are installed:

* Java 11+
* Maven 3.6+
* Git
* VS Code or Cursor

---

## Clone the Repository

```
git clone <repository-url>
cd <repository-name>
```

---

## Build the Project

```
mvn clean install
```

---

## Run Tests

```
mvn test
```

---

## Generate Coverage Report

After running tests, JaCoCo will generate coverage results.

Coverage reports are available at:

```
target/site/jacoco/index.html
```

Machine-readable coverage data can be found at:

```
target/site/jacoco/jacoco.xml
```

---

# Coverage Improvement Process

The test suite was improved through **multiple iterations**.

Each iteration followed this process:

1. Run tests
2. inspect coverage report
3. identify untested lines or branches
4. generate additional test cases
5. re-run tests and review coverage again

Examples of improvements include:

* validation error handling
* branch coverage in controller logic
* edge case inputs
* null and empty state handling

Detailed iteration notes are documented in:

```
docs/coverage-iteration-log.md
```

---

# Troubleshooting / FAQ

### Tests fail to run

Verify Java and Maven installations:

```
java -version
mvn -version
```

---

### JaCoCo report not generated

Run:

```
mvn clean test
```

Then verify the report exists under:

```
target/site/jacoco
```

---

### AI-generated tests fail to compile

AI-generated tests may sometimes make incorrect assumptions about method behavior. In those cases manual review and correction is required.

---

# Reflection

## Introduction

The goal of this project was to investigate how AI agents can assist with automated software testing by generating tests, executing them, and improving them based on coverage feedback.

Rather than manually writing every test case, the agent workflow allows coverage data to guide the testing process.

---

## Methodology

Testing was limited to a focused subset of classes to reduce scope complexity and improve testing quality.

The agent workflow repeatedly:

1. generated tests
2. executed tests
3. inspected coverage results
4. generated additional tests

This allowed testing improvements to emerge through **iterative refinement rather than single-pass generation**.

---

## Results and Discussion

Initial AI-generated tests were effective at covering simple success cases but often missed:

* validation errors
* edge cases
* branch-specific controller behavior

Later iterations improved coverage by focusing specifically on those missed paths.

Coverage analysis proved to be a useful feedback signal for directing future test generation.

---

## Insights on AI-Assisted Development

AI proved useful for:

* generating test skeletons quickly
* suggesting additional edge cases
* accelerating repetitive testing tasks

However, human oversight remained important for:

* validating test correctness
* refining testing scope
* debugging incorrect assumptions

This project demonstrates that AI can be a valuable **testing assistant**, but it still benefits from human supervision.

---

## Future Enhancements

Potential future improvements include:

* automatic parsing of JaCoCo coverage reports through MCP tools
* tighter integration with Git automation
* mutation testing integration (PIT)
* broader class coverage across the entire application
* automated reporting dashboards for coverage improvements

---

# Conclusion

This project demonstrates how AI-assisted workflows can improve the software testing process when combined with structured prompts, coverage feedback, and iterative refinement.

Rather than replacing traditional testing practices, AI tools can enhance developer productivity and help identify gaps more efficiently.

---

# Upstream Project

This project uses **Spring Petclinic** as the system under test.

Spring Petclinic is an open-source reference application maintained by the Spring team that demonstrates best practices for building Spring Boot applications.
