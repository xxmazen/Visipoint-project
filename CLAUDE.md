# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Selenium-based test automation framework for the Visipoint platform, covering authentication workflows (login, sign-up, password recovery). Built with Java 24, TestNG, and Maven.

## Commands

```bash
# Run all tests
mvn test

# Run a specific test class
mvn test -Dtest=loginExecution.login
mvn test -Dtest=signupExecution.signUP

# Run tests by group
mvn test -Dgroups="Smoke testing"
mvn test -Dgroups="Regression testing"

# Build and compile
mvn clean compile
mvn clean install

# Generate Surefire HTML report (target/site/surefire-report.html)
mvn surefire-report:report
```

## Architecture

The framework uses **Page Object Model (POM)** with a factory-based driver layer.

### Layers

**Base (`BaseTest/Test_Base.java`)** — All POM and test classes extend this. Provides:
- WebDriver lifecycle (init, quit)
- Wait strategies: `waitForElementPresent()`, `waitForElementVisible()`, `waitForElementClickable()` with `DEFAULT_WAIT_TIME = 5s`
- JavaScript helpers: `jsClick()`, `deleteElement()` (removes blocking overlays), `scrollToElement()`, `hideElement()`/`showElement()`

**Driver Factory (`drivers/`)** — `AbstractDrive` defines the interface; `ChromeFactory`, `FireFoxFactory`, and `EdgeFactroy` are concrete implementations selected at runtime. All drivers start maximized, use `EAGER` page load strategy, and disable automation detection.

**POM Layer (`src/main/java/`)**
- `LoginPOM/Login_Page.java` — Login form and forgot-password flow
- `SignUpPOM/Sign_Up_Page.java` — Two distinct sign-up workflows: Visipoint Passport and Company Dashboard

**Test Execution (`src/test/java/`)**
- `loginExecution/Login.java` — Login test cases
- `signupExecution/signUP.java` — Sign-up test cases

POM methods return `this` for method chaining (e.g., `.login("email", "pass").assertLoggedIn("expected")`).

### Configuration Files

| File | Purpose |
|------|---------|
| `testng.xml` | Suite config, groups, parallel execution settings |
| `src/main/resources/log4j2.xml` | Async logging; `visipoint-all.log`, `visipoint-errors.log`, `visipoint-app.log` (daily rolling) |
| `src/main/resources/META-INF.services/org.testng.ITestNGListener` | Registers `Listeners.CustomListener` via ServiceLoader |
| `.github/workflows/test.yml` | CI: runs full suite + smoke tests on Java 17 & 21 matrix, daily at 9 AM UTC |

### Base URL

Tests target `https://www.visipoint.me/login` by default.
