# Selenium Automation Framework — Code Experience & Knowledge Base

**Repo:** `D:\Visipoint` (this framework repo — Selenium/TestNG/Java 24/Maven, POM pattern)
**Target under test:** `https://www.visipoint.me/login` (Login + Sign-Up flows)
**Last Reviewed:** 2026-08-12
**Reviewer:** automation-qa-tester subagent (read-only code walkthrough, no browser run)
**Scope covered:** `Test_Base`, driver factory layer, `Login_Page`/`login.java`, `Sign_Up_Page`/`signUP.java`, `testng.xml`, `log4j2.xml`, TestNG listener, CI workflow, `PropertiesReader`, `ScreenShotUtils`, `AllureUtils`, `pom.xml` dependencies, `allure.properties`

> Read this file before doing engineering work on this automation framework (new POM classes, new tests, Allure/CI changes, bug fixes to the framework itself). It captures architecture, established patterns, and known defects in the framework code — distinct from the `D:\Visipoint md files\<Module>` docs, which cover manual/exploratory testing of the live Visipoint Cloud Dashboard app.

---

## Architecture

**Layer flow:**
`AbstractDrive` (interface, `setupDriver(): WebDriver`) → `ChromeFactory` / `FireFoxFactory` / `EdgeFactroy` (concrete Selenium option builders) → `Test_Base` (wraps a `WebDriver` in `wait`/`js` fields; exposes waits, JS helpers, logging) → POM classes (`Login_Page`, `Sign_Up_Page`, `PassportPage`, all `extends Test_Base`, locators as `By` fields, actions `return this`) → TestNG test classes (`login`, `signUP`, `PassportPage`, under `src/test/java/...Execution`) that build the driver in `@BeforeMethod`, chain POM calls ending in an `is...` assertion, and tear down in `@AfterMethod`.

Reporting/logging: `allure-testng` + `aspectjweaver` (woven via surefire `argLine`) drive Allure; `Listeners.InvokedListeners` (registered via `META-INF/services/org.testng.ITestNGListener`) hooks `onTestStart`/`afterInvocation`; Log4j2 async config writes to `logs/visipoint-{all,errors,app}.log`.

**Note:** `PassportPage` (POM + execution class, referenced in `testng.xml`) exists in the repo but was **not** reviewed in the 2026-08-12 session — read it before relying on this doc for Passport-flow work.

---

## Key Patterns (and why they exist)

- **Fluent POM chaining** — `new Login_Page(driver).Login(...).isLoggedIn(...)` — keeps tests readable as a sequence of user actions + one assertion; each `@Step`-annotated action becomes an Allure report step (where `@Step` is actually applied — see gaps below).
- **Centralized waits in `Test_Base`** — `waitForElementPresent/Visible/Clickable`, all keyed off `DEFAULT_WAIT_TIME=5`, plus a `FluentWait` factory — mean no test/POM code calls raw `WebDriverWait` directly. One place to tune timeout behavior.
- **`jsClick`** — scrolls into view, tries a real click, falls back to `arguments[0].click()` on failure, retries once on `StaleElementReferenceException`. Defensive pattern for elements present but not reliably clickable (overlays, animations).
- **`clickRadioButton`** — goes further than `jsClick`: forces `visibility`, checks `offsetParent`/bounding-rect via JS, waits for the `checked` property post-click, retries 3x on staleness. Written in direct response to a flaky radio control on the login/forgot-password page — don't simplify this method without understanding why it's this defensive.
- **`EAGER` page-load strategy + explicit waits everywhere** — deliberate speed/reliability tradeoff: don't block on full page load, gate every interaction on the specific element being ready instead.

---

## Known Defects in the Framework Code (as of 2026-08-12)

These are bugs in the **automation code itself**, not the app under test. Verify still-present before starting related work; fix or flag if touching these areas.

1. **Allure results wiped every single test, not once per suite.** `InvokedListeners.onTestStart` calls `AllureUtils.clearAllureResults()`, which does `FileUtils.deleteQuietly(new File("allureTest-output/allure-results"))` — this runs per-test-method, not per-suite. In `signUP.java` (33 tests) or `login.java` (16 tests), each new test wipes out the previous tests' Allure result JSON/attachments. Only the last-run test's results survive a full-suite run. **Fix direction:** this should be suite-scoped (`@BeforeSuite` or `ISuiteListener.onStart`), not per-test.

2. **Screenshot capture is silently broken.** `InvokedListeners.afterInvocation` calls `ScreenShotUtils.captureScreenshot(null, method.getTestMethod().getMethodName() + ".png")` — passes `null` as the `WebDriver`. The method casts that to `TakesScreenshot` and calls `.getScreenshotAs(...)`, which NPEs; the exception is caught internally and only logged to stderr. **No screenshot has ever actually attached to an Allure report from this listener.** `Test_Base.takeScreenshot(String)` is a separate, correctly-implemented method that nothing currently calls from the listener — likely the intended fix is wiring the listener to pass the real driver instance through to this method.

3. **`smoke-tests` CI job likely selects zero tests.** No test method in `login.java` or `signUP.java` carries a TestNG `groups` attribute, but `.github/workflows/test.yml` and `testng.xml` reference `-Dgroups="Smoke testing"` / `"Regression testing"`. Until tests are tagged, that CI job runs against an empty selection. Also note: the workflow step has `continue-on-error: true`, so CI reports green even if every test errors out at driver setup — don't trust a green CI run as proof tests executed.

4. **CI has no headless/browser install step.** `ubuntu-latest` runner with no Chrome/Chromium install and no `--headless=new` flag in `ChromeFactory.options()`. Selenium Manager can fetch a matching binary at runtime, but a non-headless launch on a display-less runner is unreliable — verify actual CI test execution before assuming the matrix (Java 17 & 21) is exercising real browser runs.

5. **`ChromeFactory.getDriver()` is dead code.** Sets `webdriver.chrome.driver` to a hardcoded `src/main/resources/drivers/chromedriver.exe` Windows path, but `setupDriver()` never calls it (Selenium Manager resolves the driver automatically in the actually-used path). Safe to delete, or wire up if a pinned-driver-version need arises — but the hardcoded Windows path would break on CI as-is.

---

## Consistency Gaps Between Login and Sign-Up Sides

The Login POM/test pair follows the framework's own stated conventions; the Sign-Up pair does not. Treat `Login_Page`/`login.java` as the reference implementation when writing new POM/test code.

| Aspect | Login side | Sign-Up side |
|---|---|---|
| Allure `@Step` on POM actions | Yes, on all methods | **None** — zero `@Step` annotations across ~35 methods in `Sign_Up_Page` |
| Allure `@Feature`/`@Story`/`@Severity` on tests | Yes, on all 16 tests | **None** on any of the 33 tests |
| Test data source | `PropertiesReader.getProperty(...)` → `src/main/resources/test-data/data.properties` | **Hardcoded inline** (e.g. literal emails/names/phones repeated across all 33 tests) |
| Locator field style | `private final By` | package-private, non-`final` `By` |

**Implication:** the Allure report for sign-up currently shows raw method names with no step breakdown and no severity filtering — a real gap against the framework's convention, worth closing before adding more sign-up tests on top of it.

---

## Other Naming/Structural Notes

- `EdgeFactroy` — typo in the class name itself (also flagged in `CLAUDE.md`). Don't "fix" casually — it's referenced elsewhere; treat any rename as a deliberate refactor, not a drive-by.
- `FireFoxFactory` exists but is **unused** by both test classes — only Chrome and Edge are actually exercised.
- `FirefoxOptions`/`EdgeOptions` reuse Chromium-style CLI flags (`--start-maximized`, `--disable-infobars`) — Edge is Chromium-based so these mostly work; Firefox is not, so several flags are silently ignored by geckodriver if `FireFoxFactory` is ever wired into a real test run.
- Method-name casing is inconsistent (`Login`, `Clear`, `EmptyFields` capitalized like class names vs. otherwise-camelCase convention).

---

## Current Test Coverage

- **Login** (`login.java`, 16 tests): valid login; invalid email/password/both; empty credentials; forgot-password link navigation; forgot-password by email (valid/invalid/empty); forgot-password by phone (valid/invalid-chars/mixed-chars/non-existent/empty); radio-button mutual-disable checks; OTP message.
- **Sign-up** (`signUP.java`, 33 tests): both Visipoint Passport and Company Dashboard flows — valid/invalid email, existing email, partial-field combinations (name-only, email-only, phone-only, pairwise combinations) driving enabled/disabled button assertions, valid/invalid phone formats, header title checks, login-link navigation, company flow's step-2 company-name field + next-button gating.
- **Passport** (`PassportPageExecution.PassportPage`) — referenced in `testng.xml`, not yet reviewed for this doc.

**Notably absent:** session/logout tests, "remember me" coverage, cross-browser runs (Firefox factory unused), negative-path coverage for company-passport step 2 beyond company-name-empty, and TestNG group tags on any test method (see CI defect #3 above).

---

## What Was NOT Reviewed (2026-08-12 session)

- `PassportPage` POM and execution class
- Any POM/test files beyond Login and Sign-Up, if they exist
- Actual test execution (this was a read-only code walkthrough, not a `mvn test` run)
