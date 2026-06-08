# ⚡ Quick Fix Cheat Sheet

## Problem 1: SLF4J Warnings
```
ERROR: SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder"
```

**ONE LINE FIX:**
Add this to `pom.xml`:
```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.13</version>
</dependency>
```

✅ **DONE** - Run `mvn clean compile`

---

## Problem 2: Null WebDriver Error
```
ERROR: java.lang.NullPointerException: Cannot invoke because "this.driver" is null
```

**TWO LINE FIX in SignUpTestCases.java:**

Find this:
```java
@AfterMethod(alwaysRun = true)
public void tearDown() {
    //  closeBrowser();
}
```

Replace with:
```java
@AfterMethod(alwaysRun = true)
public void tearDown() {
    closeBrowser();
}
```

✅ **DONE** - Uncomment `closeBrowser()` call

---

## Problem 3: Missing wait() Method
```
ERROR: Cannot find symbol - method wait(...)
```

**ADD THIS to SignUpTestCases.java:**
```java
public void wait(By by, int duration) {
    new WebDriverWait(driver, Duration.ofSeconds(duration))
            .until(ExpectedConditions.visibilityOfElementLocated(by));
}
```

✅ **DONE** - Method now available

---

## Verification Command
```bash
mvn clean test-compile
```

Expected output:
```
[INFO] BUILD SUCCESS
```

---

## deleteElement() Usage

Remove element at runtime:
```java
private final By blockingLabel = By.xpath("(//*[@class='custom-control-label'])[2]");

@Test
public void myTest() {
    deleteElement(blockingLabel);  // Element removed from DOM
    Clicking(nextElement);         // Now clickable
}
```

---

## Status: ✅ ALL FIXED

| Issue | Status | File |
|-------|--------|------|
| SLF4J Warnings | ✅ FIXED | pom.xml |
| Null Driver | ✅ FIXED | SignUpTestCases.java |
| Missing Method | ✅ FIXED | SignUpTestCases.java |
| Compilation | ✅ SUCCESS | All files |


