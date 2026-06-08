# How to Delete/Remove Elements at Runtime - QC Guide

## Method: deleteElement()

The `deleteElement()` method in your test class uses **JavaScript execution** to remove DOM elements dynamically during test execution.

### How It Works

```java
public void deleteElement(By by) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].remove();", driver.findElement(by));
}
```

### Breakdown

1. **Cast WebDriver to JavascriptExecutor**
   ```java
   JavascriptExecutor js = (JavascriptExecutor) driver;
   ```
   This allows you to execute JavaScript in the browser context.

2. **Execute remove() JavaScript**
   ```java
   js.executeScript("arguments[0].remove();", driver.findElement(by));
   ```
   - `arguments[0]` refers to the first parameter passed (the element)
   - `.remove()` is a JavaScript method that removes the element from the DOM

### Usage Examples in Your Tests

#### Example 1: Remove Label Element Before Clicking Radio Button
```java
@Test(priority = 11)
public void verifyFunctionalityWhenUserEnterValidPhoneNumber() {
    Clicking(ForgotPasswordLink);
    deleteElement(LabelElement);  // Removes the label element from DOM
    Clicking(PhoneNumberRadioButton);  // Now can click the radio button
    wait(PhoneNumberField, 10);
    Enter(PhoneNumberField, "12345678912");
    Clicking(SendButton);
    // ... assertions
}
```

### Alternative Ways to Remove Elements

#### Method 1: Hide Element with CSS
```java
public void hideElement(By by) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].style.display='none';", driver.findElement(by));
}
```

#### Method 2: Make Element Invisible
```java
public void makeElementInvisible(By by) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].style.visibility='hidden';", driver.findElement(by));
}
```

#### Method 3: Change Element's Opacity
```java
public void fadeOutElement(By by) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].style.opacity='0';", driver.findElement(by));
}
```

#### Method 4: Remove Element by Parent
```java
public void removeElementByParent(By by) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].parentNode.removeChild(arguments[0]);", driver.findElement(by));
}
```

### When to Use Each Method

| Method | Use Case |
|--------|----------|
| `deleteElement()` | Element blocking interaction - completely remove from DOM |
| `hideElement()` | Element obscuring view - hide with display:none |
| `makeElementInvisible()` | Element not visually needed - keep in DOM but invisible |
| `fadeOutElement()` | Gradual removal effect - fade out gradually |
| `removeElementByParent()` | Remove element safely via parent node |

### Important Considerations

⚠️ **Watch Out For:**
1. **Stale Element References** - Element removed from DOM, references become invalid
2. **Page Structure Changes** - Removing elements may affect other elements
3. **JavaScript Execution** - Requires JavaScript enabled in browser
4. **Timing Issues** - Element must exist before removal

### Best Practices

✅ **DO:**
- Wait for element to be present before removing
- Remove only elements that are blocking interactions
- Document why elements need to be removed in comments
- Use explicit waits after removal

❌ **DON'T:**
- Remove critical structural elements
- Remove elements without understanding the impact
- Rely on element removal as primary test strategy
- Remove elements that should be tested

### Enhanced deleteElement() with Error Handling

```java
public void deleteElement(By by) {
    try {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Wait for element to be present
        wait(by, 5);
        WebElement element = driver.findElement(by);
        js.executeScript("arguments[0].remove();", element);
        System.out.println("Element successfully removed: " + by);
    } catch (NoSuchElementException e) {
        System.out.println("Element not found to remove: " + by);
    } catch (Exception e) {
        System.out.println("Error removing element: " + e.getMessage());
    }
}
```

### Your Implementation in Tests

In your test file, you're using it to remove label elements that obstruct radio button clicks:

```java
@Test(priority = 11)
public void verifyFunctionalityWhenUserEnterValidPhoneNumber() {
    Clicking(ForgotPasswordLink);
    deleteElement(LabelElement);  // Remove label blocking interaction
    Clicking(PhoneNumberRadioButton);  // Can now click the button
    // Test continues...
}
```

This is a valid test scenario when the UI element is blocking user interaction during testing.

