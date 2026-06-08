# 🎯 Visipoint QC - Test Automation Framework

A comprehensive Selenium-based test automation framework for Visipoint login and sign-up functionality using TestNG and Maven.

---

## 📋 Table of Contents

1. [Project Overview](#project-overview)
2. [Prerequisites](#prerequisites)
3. [Installation & Setup](#installation--setup)
4. [Project Structure](#project-structure)
5. [Running Tests](#running-tests)
6. [Configuration Files](#configuration-files)
7. [Test Classes](#test-classes)
8. [Helper Methods & Utilities](#helper-methods--utilities)
9. [Logging](#logging)
10. [CI/CD Integration](#cicd-integration)
11. [Troubleshooting](#troubleshooting)
12. [Best Practices](#best-practices)

---

## 📌 Project Overview

### What is Visipoint QC?
Visipoint QC is a quality control automation framework that tests:
- **Login functionality** - User authentication with valid/invalid credentials
- **Sign-up workflow** - User registration with Visipoint Passport
- **Password recovery** - Forgot password functionality via email/SMS
- **Form validation** - Input validation for email, phone, and other fields

### Technology Stack
```
Framework:    TestNG 7.11.0
Language:     Java 24
Browser:      Chrome (via Selenium WebDriver 4.35.0)
Build Tool:   Apache Maven 3.9.11
Logging:      SLF4J 2.0.13 + Log4j 2.25.3
```

### Key Features
✅ Comprehensive test coverage for login/signup  
✅ Runtime element deletion for blocking overlays  
✅ Advanced wait strategies (implicit, explicit, fluent)  
✅ Custom listener for test reporting  
✅ CI/CD integration ready  
✅ Proper resource cleanup with @AfterMethod  
✅ Detailed logging with Log4j2  

---

## 🛠️ Prerequisites

### System Requirements
- Windows 10+ / macOS / Linux
- Java Development Kit (JDK) 17 or higher
- Apache Maven 3.6+
- Google Chrome browser
- ChromeDriver (auto-downloaded by selenium-webdriver)

### Software Installation

**Java JDK:**
```bash
# Check if Java is installed
java -version

# Download from https://adoptium.net/ or https://www.oracle.com/java/
```

**Maven:**
```bash
# Check if Maven is installed
mvn -version

# Download from https://maven.apache.org/download.cgi
```

**Chrome:**
- Download from https://www.google.com/chrome/

---

## 📦 Installation & Setup

### 1. Clone the Project
```bash
git clone <repository-url>
cd Visipoint
```

### 2. Install Dependencies
```bash
# Download all Maven dependencies
mvn clean install

# Or just compile
mvn clean compile
```

### 3. Verify Installation
```bash
# Compile test classes
mvn test-compile

# Expected output: BUILD SUCCESS
```

### 4. (Optional) Configure IDE
**IntelliJ IDEA:**
1. File → Open → Select project folder
2. Maven → Reload projects
3. Run → Edit Configurations → Add TestNG configuration

**Eclipse:**
1. File → Import → Maven → Existing Maven Projects
2. Select project folder
3. Right-click project → Maven → Update Project

---

## 📁 Project Structure

```
Visipoint/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/example/
│   │   │       └── Main.java
│   │   └── resources/
│   │       └── log4j2.xml          ✅ Logging configuration
│   └── test/
│       └── java/
│           ├── Listeners/
│           │   └── CustomListeners.java     (Test listeners)
│           └── Visipoint/
│               ├── BaseTest.java             ✅ Base class with utilities
│               ├── loginTestCases.java       (Login tests)
│               ├── SignUpTestCases.java      (Signup tests)
│               └── [Other test classes]
├── testng.xml                       ✅ TestNG configuration
├── pom.xml                          ✅ Maven configuration
├── .github/
│   └── workflows/
│       └── test.yml                 ✅ GitHub Actions CI/CD
└── logs/                            (Generated at runtime)
    └── visipoint-app.log
```

---

## 🧪 Running Tests

### Run All Tests
```bash
# Run all tests
mvn test

# With detailed output
mvn test -X

# Skip test failures (continue execution)
mvn test -DskipFailingTests
```

### Run Specific Test Suite
```bash
# Using testng.xml
mvn test -Dsuites=testng.xml

# Run only smoke tests
mvn test -Dgroups="Smoke testing"

# Run only regression tests
mvn test -Dgroups="Regression testing"
```

### Run Specific Test Class
```bash
# Run loginTestCases only
mvn test -Dtest=loginTestCases

# Run specific test method
mvn test -Dtest=loginTestCases#VerifyLoginWithValidCredentials
```

### Run Tests in Parallel
```bash
# Run tests in parallel (configured in testng.xml)
mvn test -DthreadCount=2
```

### Generate Test Report
```bash
# Generate HTML test report
mvn surefire-report:report

# Report location: target/site/surefire-report.html
```

---

## ⚙️ Configuration Files

### 1. pom.xml - Maven Configuration
**Location:** `pom.xml`

Controls dependencies, Java version, and build configuration.

**Key Dependencies:**
```xml
<!-- Selenium WebDriver -->
<groupId>org.seleniumhq.selenium</groupId>
<artifactId>selenium-java</artifactId>
<version>4.35.0</version>

<!-- TestNG Framework -->
<groupId>org.testng</groupId>
<artifactId>testng</artifactId>
<version>7.11.0</version>

<!-- SLF4J + Log4j -->
<groupId>org.slf4j</groupId>
<artifactId>slf4j-api</artifactId>
<version>2.0.13</version>
```

### 2. testng.xml - TestNG Configuration
**Location:** `testng.xml`

Defines test suites, groups, parallel execution, and listeners.

**Features:**
- Multiple test suites (Smoke, Regression, Full)
- Test grouping (Smoke testing, Regression testing)
- Parallel execution (2 threads)
- Custom listener (CustomListeners)

**Usage:**
```bash
# Run with testng.xml
mvn test -Dsuites=testng.xml
```

### 3. log4j2.xml - Logging Configuration
**Location:** `src/main/resources/log4j2.xml`

Configures logging levels, appenders, and output formats.

**Features:**
- Console output (INFO level)
- File appenders (all logs + error logs)
- Rolling file appender (daily rotation)
- Async appender (better performance)

**Output Files:**
```
logs/
├── visipoint-all.log       (All logs)
├── visipoint-errors.log    (Errors only)
└── visipoint-app.log       (Daily rolled)
```

---

## 🧬 Test Classes

### 1. BaseTest.java ✅ NEW
**Location:** `src/test/java/Visipoint/BaseTest.java`

Base class providing common functionality for all tests.

**Key Features:**
- WebDriver initialization and cleanup
- Wait utilities (implicit, explicit, fluent)
- User interaction methods (click, type, submit)
- DOM manipulation (delete, hide, show elements)
- Screenshot capture
- Logging utilities
- Element validation

**Usage:**
```java
public class MyTestClass extends BaseTest {
    @Test
    public void myTest() {
        navigateToURL("https://example.com");
        enterText(By.id("username"), "user");
        click(By.id("submit"));
        String message = getElementText(By.id("result"));
        assert message.contains("Success");
    }
}
```

### 2. loginTestCases.java
**Location:** `src/test/java/Visipoint/loginTestCases.java`

Tests for login functionality:
- Valid credentials login
- Invalid email/password
- Empty fields validation
- Forgot password workflow
- Phone number recovery
- Form validation

**Test Methods:** 16 total

### 3. SignUpTestCases.java
**Location:** `src/test/java/Visipoint/SignUpTestCases.java`

Tests for sign-up functionality:
- Visipoint Passport signup navigation
- Valid data submission
- Email validation
- Form field requirements

**Test Methods:** 4+ (extensible)

### 4. CustomListeners.java
**Location:** `src/test/java/Listeners/CustomListeners.java`

Custom TestNG listeners:
- Test method invocation tracking
- Test retry logic
- Failure reporting

---

## 🔧 Helper Methods & Utilities

### Wait Methods
```java
// Wait for element visibility
waitForElementVisible(By locator, int seconds);

// Wait for element clickable
waitForElementClickable(By locator, int seconds);

// Wait for element invisibility
waitForElementInvisible(By locator, int seconds);

// Fluent wait with polling
fluentWait(By locator, int timeout, int polling);
```

### User Interaction
```java
// Enter text
enterText(By locator, String text);

// Click element
click(By locator);

// JavaScript click (for overlays)
jsClick(By locator);

// Get text
String text = getElementText(By locator);

// Check if enabled
boolean enabled = isElementEnabled(By locator);

// Check if displayed
boolean visible = isElementDisplayed(By locator);
```

### DOM Manipulation
```java
// Delete element from DOM
deleteElement(By locator);

// Hide element
hideElement(By locator);

// Show element
showElement(By locator);

// Scroll to element
scrollToElement(By locator);
```

### Utilities
```java
// Take screenshot
String path = takeScreenshot("filename");

// Navigate
navigateToURL(String url);

// Get current URL
String url = getCurrentURL();

// Get page title
String title = getPageTitle();

// Refresh page
refreshPage();

// Sleep
sleep(1000);  // 1 second
```

---

## 📝 Logging

### How Logging Works

1. **Console Output** (INFO level and above)
   ```
   [INFO] 2026-03-11 18:00:00.123 [main] - Test Setup Complete
   ```

2. **File Appenders**
   ```
   logs/visipoint-all.log     → All logs
   logs/visipoint-errors.log  → Error logs only
   logs/visipoint-app.log     → Rolled daily
   ```

### Logging in Tests

```java
@Test
public void myTest() {
    logInfo("Test started");
    click(loginButton);
    logDebug("Login button clicked");
    sleep(2000);
    if (!driver.getTitle().contains("Dashboard")) {
        logError("Login failed - wrong page");
    }
    logInfo("Test completed");
}
```

### Log Levels

```
DEBUG   - Detailed information for debugging
INFO    - General information flow
WARN    - Warning messages
ERROR   - Error messages
```

---

## 🔄 CI/CD Integration

### GitHub Actions Setup

**File:** `.github/workflows/test.yml`

**Features:**
- Runs on push to main/develop
- Daily scheduled runs (9 AM UTC)
- Tests on Java 17 and 21
- Artifact upload for reports
- Smoke test subset

**Example Workflow:**
```
1. Checkout code
2. Setup Java
3. Build with Maven
4. Run tests
5. Generate report
6. Upload artifacts
```

**View Results:**
1. Go to GitHub repository
2. Click Actions tab
3. Select workflow run
4. Check logs and artifacts

### Running Locally (GitHub Actions Simulation)

```bash
# Install act (GitHub Actions local runner)
# https://github.com/nektos/act

# Run workflow locally
act -j test
```

---

## 🆘 Troubleshooting

### SLF4J Warnings
**Problem:** `SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder"`

**Solution:**
```bash
# Clear Maven cache and update
mvn clean install -U
```

### WebDriver Not Found
**Problem:** `ChromeDriver executable not found`

**Solution:**
```bash
# Selenium auto-downloads ChromeDriver
mvn clean compile
```

### Tests Timing Out
**Problem:** `TimeoutException` on wait operations

**Solutions:**
1. Increase wait time:
   ```java
   waitForElementVisible(element, 15);  // 15 seconds
   ```

2. Check network connectivity

3. Verify element locator:
   ```bash
   # In browser console
   document.querySelector("css-selector")
   ```

### Null WebDriver Error
**Problem:** `NullPointerException: "this.driver" is null`

**Solution:**
```java
@AfterMethod(alwaysRun = true)
public void tearDown() {
    if (driver != null) {
        driver.quit();
    }
}
```

### Element Not Visible Error
**Problem:** `Element not visible exception`

**Solutions:**
1. Add wait:
   ```java
   waitForElementVisible(element, 10);
   click(element);
   ```

2. Scroll to element:
   ```java
   scrollToElement(element);
   click(element);
   ```

3. Remove overlay:
   ```java
   deleteElement(overlayElement);
   click(element);
   ```

---

## 📚 Best Practices

### 1. Test Structure
```java
@Test(priority = 1, groups = {"Smoke testing"})
public void descriptiveTestName() {
    // 1. Setup data
    String email = "test@example.com";
    
    // 2. Execute test steps
    click(loginButton);
    enterText(emailField, email);
    
    // 3. Assert results
    Assert.assertEquals(driver.getTitle(), "Dashboard");
    
    // Cleanup is automatic via @AfterMethod
}
```

### 2. Use Base Class Methods
```java
// ✅ GOOD - Uses wait
waitForElementVisible(element, 10);
click(element);

// ❌ BAD - No wait
click(element);  // May fail intermittently
```

### 3. Meaningful Assertions
```java
// ✅ GOOD
Assert.assertEquals(
    actualText, 
    "Expected error message",
    "Should display validation error on invalid email"
);

// ❌ BAD
Assert.assertTrue(isDisplayed);
```

### 4. Handle Synchronization
```java
// ✅ GOOD - Wait for state change
waitForElementInvisible(loadingSpinner, 10);

// ❌ BAD - No wait for loading
click(saveButton);
click(nextButton);  // May fail if loading isn't complete
```

### 5. Logging
```java
// ✅ GOOD - Informative logs
logInfo("Navigating to login page");
click(loginButton);
logInfo("Login button clicked successfully");

// ❌ BAD - No logging context
click(element);
```

### 6. Element Locators
```java
// ✅ GOOD - Specific, stable locators
By loginButton = By.id("login-btn");
By errorMessage = By.cssSelector("[role='alert']");

// ❌ BAD - Brittle locators
By element = By.xpath("//button[1]");
By text = By.xpath("//*[contains(text(), 'Click')]");
```

### 7. Test Isolation
```java
// ✅ GOOD - Each test is independent
@BeforeMethod
public void setUp() {
    initializeDriver();
}

@AfterMethod
public void tearDown() {
    if (driver != null) driver.quit();
}

// ❌ BAD - Tests depend on each other
public class Tests {
    static WebDriver driver;  // Shared between tests
}
```

---

## 🚀 Advanced Usage

### Custom Test Data
```java
public class TestData {
    public static final String VALID_EMAIL = "test@example.com";
    public static final String VALID_PASSWORD = "Password123!";
    public static final String INVALID_EMAIL = "invalid-email";
}

// In test
@Test
public void loginTest() {
    enterText(emailField, TestData.VALID_EMAIL);
    enterText(passwordField, TestData.VALID_PASSWORD);
}
```

### Page Object Model
```java
public class LoginPOM.LoginPage {
    private WebDriver driver;
    
    private By emailField = By.id("email");
    private By loginButton = By.id("login-btn");
    
    public LoginPOM.LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    
    public void login(String email, String password) {
        // Interactions here
    }
}

// In test
@Test
public void loginTest() {
    LoginPOM.LoginPage page = new LoginPOM.LoginPage(driver);
    page.login("email@example.com", "password");
}
```

### Parameterized Tests
```java
@DataProvider
public Object[][] loginData() {
    return new Object[][] {
        { "valid@email.com", "Password123", true },
        { "invalid@email", "password", false },
        { "", "", false }
    };
}

@Test(dataProvider = "loginData")
public void testLogin(String email, String password, boolean shouldSucceed) {
    // Test implementation
}
```

---

## 📞 Support & Resources

### Documentation Files
- `MASTER_REFERENCE.md` - Quick reference guide
- `DELETE_ELEMENT_ADVANCED_GUIDE.md` - Element deletion guide
- `COMPREHENSIVE_FIX_REPORT.md` - Detailed fix report

### External Resources
- [Selenium Documentation](https://www.selenium.dev/)
- [TestNG Documentation](https://testng.org/)
- [Maven Guide](https://maven.apache.org/guides/)
- [Log4j 2 Manual](https://logging.apache.org/log4j/2.x/)

---

## 📊 Project Statistics

```
Total Test Cases:       16+ (expandable)
Test Coverage:          Login & Sign-up workflows
Framework:              TestNG + Selenium
Build Time:             ~2-3 seconds
Average Test Duration:  2-5 seconds per test
CI/CD Ready:            Yes (GitHub Actions)
```

---

## ✅ Verification Checklist

Before running tests:
- [ ] Java JDK installed (`java -version`)
- [ ] Maven installed (`mvn -version`)
- [ ] Chrome browser installed
- [ ] Dependencies downloaded (`mvn clean install`)
- [ ] Project compiles (`mvn test-compile`)
- [ ] Tests runnable (`mvn test`)

---

## 🎯 Quick Commands

```bash
# Build & Compile
mvn clean compile
mvn test-compile

# Run Tests
mvn test
mvn test -Dgroups="Smoke testing"
mvn test -Dtest=loginTestCases

# Generate Reports
mvn surefire-report:report

# View Logs
tail -f logs/visipoint-app.log

# Package Project
mvn clean package

# Deploy
mvn deploy
```

---

**Version:** 1.0  
**Last Updated:** March 11, 2026  
**Status:** ✅ Production Ready  
**Maintainer:** QC Team

---

*For detailed technical information, see MASTER_REFERENCE.md*

