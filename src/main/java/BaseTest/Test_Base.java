package BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Test_Base {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    protected final String baseURL = "https://www.visipoint.me/login";
    protected final int DEFAULT_WAIT_TIME = 5;

    public Test_Base(WebDriver driver) {
        if (driver == null) {
            logError("WARNING: WebDriver is null in Test_Base constructor");
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));
        this.js = (JavascriptExecutor) driver;
    }

    // ============= LOGGING UTILITIES =============

    public void logInfo(String message) {
        System.out.println("[INFO] " + LocalDateTime.now() + " - " + message);
    }

    public void logDebug(String message) {
        System.out.println("[DEBUG] " + LocalDateTime.now() + " - " + message);
    }

    public void logError(String message) {
        System.err.println("[ERROR] " + LocalDateTime.now() + " - " + message);
    }

    public void logWarning(String message) {
        System.err.println("[WARN] " + LocalDateTime.now() + " - " + message);
    }

    // ============= UTILITY METHODS =============

    public String getBaseUrl() {
       try {
              driver.get(baseURL);
         } catch (Exception e) {
              logError("Failed to navigate to base URL: " + e.getMessage());
              throw e;
       }
         logInfo("Navigated to base URL: " + baseURL);
         return baseURL;
    }
    public String getPageTitle() {
        String title = driver.getTitle();
        logDebug("Page title: " + title);
        return title;
    }

    public String getCurrentURL() {
        String url = driver.getCurrentUrl();
        logDebug("Current URL: " + url);
        return url;
    }

    public void navigateToURL(String url) {
        try {
            driver.navigate().to(url);
            logInfo("Navigated to: " + url);
        } catch (Exception e) {
            logError("Failed to navigate to " + url + ": " + e.getMessage());
            throw e;
        }
    }
    public void selectFromDropdown(By dropdownLocator, By optionLocator) {

        try {
            waitForElementClickable(dropdownLocator, DEFAULT_WAIT_TIME);
            driver.findElement(dropdownLocator).click();
            waitForElementClickable(optionLocator, DEFAULT_WAIT_TIME);
            driver.findElement(optionLocator).click();
            String optionText = driver.findElement(optionLocator).getText();
            logInfo("Selected '" + optionText + "' from dropdown.");
        } catch (TimeoutException | NoSuchElementException | ElementClickInterceptedException e) {
            logError("Failed to select option from dropdown: " + e.getMessage());
            throw e;
        }
    }

    public void navigateBack() {
        try {
            driver.navigate().back();
            logInfo("Navigated back");
        } catch (Exception e) {
            logError("Failed to navigate back: " + e.getMessage());
            throw e;
        }
    }

    public void navigateForward() {
        try {
            driver.navigate().forward();
            logInfo("Navigated forward");
        } catch (Exception e) {
            logError("Failed to navigate forward: " + e.getMessage());
            throw e;
        }
    }

    public String getWindowHandle() {
        String handle = driver.getWindowHandle();
        logDebug("Current window handle: " + handle);
        return handle;
    }

    public void refreshPage() {
        try {
            driver.navigate().refresh();
            logInfo("Page refreshed");
        } catch (Exception e) {
            logError("Failed to refresh page: " + e.getMessage());
            throw e;
        }
    }

    // ============= DOM MANIPULATION =============

    public void isLoggedIn(String expectedURL) {
        Assert.assertEquals(getCurrentURL(), expectedURL, "User should be logged in and navigated to the expected URL");
    }

    public boolean isElementEnabled(By locator) {
        try {
            boolean enabled = driver.findElement(locator).isEnabled();
            logDebug("Element " + locator + " enabled: " + enabled);
            return enabled;
        } catch (Exception e) {
            logError("Failed to check if element is enabled " + locator + ": " + e.getMessage());
            return false;
        }
    }

    public boolean isElementDisplayed(By locator) {
        try {
            boolean displayed = driver.findElement(locator).isDisplayed();
            logDebug("Element " + locator + " displayed: " + displayed);
            return displayed;
        } catch (NoSuchElementException e) {
            logDebug("Element " + locator + " not found");
            return false;
        }
    }

    // ============= JS INTERACTION UTILITIES =============

    public void deleteElement(By locator) {
        try {
            js.executeScript("arguments[0].remove();", driver.findElement(locator));
            logInfo("Deleted element from DOM: " + locator);
        } catch (Exception e) {
            logError("Failed to delete element " + locator + ": " + e.getMessage());
            throw e;
        }
    }

    public void hideElement(By locator) {
        try {
            js.executeScript("arguments[0].style.display='none';", driver.findElement(locator));
            logInfo("Hidden element: " + locator);
        } catch (Exception e) {
            logError("Failed to hide element " + locator + ": " + e.getMessage());
            throw e;
        }
    }

    public void showElement(By locator) {
        try {
            js.executeScript("arguments[0].style.display='block';", driver.findElement(locator));
            logInfo("Shown element: " + locator);
        } catch (Exception e) {
            logError("Failed to show element " + locator + ": " + e.getMessage());
            throw e;
        }
    }

    public void scrollToElement(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            logInfo("Scrolled to element: " + locator);
        } catch (Exception e) {
            logError("Failed to scroll to element " + locator + ": " + e.getMessage());
            throw e;
        }
    }

    public void jsClick(By locator) {
        int retries = 2;

        while (retries-- > 0) {
            try {
                WebElement element = wait.until(
                        ExpectedConditions.presenceOfElementLocated(locator)
                );

                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView({block:'center'});", element);

                try {
                    wait.until(ExpectedConditions.elementToBeClickable(locator));
                    element.click();
                    logInfo("Regular click success: " + locator);
                    return;

                } catch (Exception e) {
                    ((JavascriptExecutor) driver)
                            .executeScript("arguments[0].click();", element);
                    logInfo("Fallback JS click: " + locator);
                    return;
                }

            } catch (StaleElementReferenceException e) {
                logDebug("Stale element detected, retrying click...");
            }
        }

        throw new RuntimeException("Failed to click: " + locator);
    }

    public String takeScreenshot(String filename) {
        if (!(driver instanceof TakesScreenshot)) {
            throw new UnsupportedOperationException("WebDriver does not support screenshots");
        }
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String filepath = "screenshots/" + filename + "_" + timestamp + ".png";
            File screenshotFile = new File(filepath);
            if (!screenshotFile.getParentFile().mkdirs() && !screenshotFile.getParentFile().exists()) {
                throw new RuntimeException("Failed to create screenshot directory: " + screenshotFile.getParent());
            }
            org.apache.commons.io.FileUtils.copyFile(screenshot, screenshotFile);
            logInfo("Screenshot saved: " + filepath);
            return filepath;
        } catch (Exception e) {
            logError("Failed to take screenshot: " + e.getMessage());
            throw new RuntimeException("Screenshot failed", e);
        }
    }

    // ============= USER INTERACTION UTILITIES =============

    public void enterText(By locator, String text) {
        try {
            waitForElementVisible(locator, DEFAULT_WAIT_TIME);
            WebElement element = driver.findElement(locator);
            element.clear();
            element.sendKeys(text);
            logInfo("Entered text: " + text + " in " + locator);
        } catch (Exception e) {
            logError("Failed to enter text in " + locator + ": " + e.getMessage());
            throw e;
        }
    }

    public void click(By locator) {
        try {
            waitForElementClickable(locator, DEFAULT_WAIT_TIME);
            driver.findElement(locator).click();
            logInfo("Clicked on element: " + locator);
        } catch (Exception e) {
            logError("Failed to click on element " + locator + ": " + e.getMessage());
            throw e;
        }
    }

    public void clickRadioButton(By locator) {
        int retries = 3;
        Exception lastException = null;

        while (retries > 0) {
            try {
                waitForElementPresent(locator, DEFAULT_WAIT_TIME);
                WebElement element = driver.findElement(locator);

                String jsScript = "var elem = arguments[0]; " + "var parent = elem.parentElement; " + "elem.scrollIntoView(true); " + "elem.style.visibility = 'visible'; " + "if (parent) { parent.style.visibility = 'visible'; } " + "return elem.offsetParent !== null;";

                Boolean isVisible = (Boolean) js.executeScript(jsScript, element);

                if (isVisible == null || !isVisible) {
                    throw new ElementNotInteractableException("Element not visible in viewport");
                }

                wait.until(d -> {
                    try {
                        return (Boolean) js.executeScript("return arguments[0].getBoundingClientRect().bottom > 0 && " + "arguments[0].getBoundingClientRect().top < window.innerHeight;", element);
                    } catch (StaleElementReferenceException e) {
                        return false;
                    }
                });

                element.click();

                wait.until(d -> {
                    try {
                        return (Boolean) js.executeScript("return arguments[0].checked;", element);
                    } catch (StaleElementReferenceException e) {
                        return false;
                    }
                });

                logInfo("Radio button clicked successfully on element: " + locator);
                return;

            } catch (StaleElementReferenceException e) {
                retries--;
                lastException = e;
                logDebug("Stale element detected, retrying radio button click... (" + retries + " retries left)");
            } catch (Exception e) {
                logError("Failed to click radio button " + locator + ": " + e.getMessage());
                throw e;
            }
        }

        logError("Failed to click radio button after retries: " + lastException.getMessage());
        throw new RuntimeException("Radio button became stale after multiple retries", lastException);
    }
    public void Clear (By locator) {
        try {
            waitForElementVisible(locator, DEFAULT_WAIT_TIME);
            driver.findElement(locator).clear();
            logInfo("Cleared text from element: " + locator);
        } catch (Exception e) {
            logError("Failed to clear text from element " + locator + ": " + e.getMessage());
            throw e;
        }
    }
    public String getElementText(By locator) {
        try {
            waitForElementVisible(locator, DEFAULT_WAIT_TIME);
            String text = driver.findElement(locator).getText();
            logDebug("Retrieved text: " + text + " from " + locator);
            return text;
        } catch (Exception e) {
            logError("Failed to get text from element " + locator + ": " + e.getMessage());
            throw e;
        }
    }

    // ============= WAIT UTILITIES =============

    public void waitForElementPresent(By locator, int seconds) {
        try {
            WebDriverWait w = (seconds == DEFAULT_WAIT_TIME) ? wait : new WebDriverWait(driver, Duration.ofSeconds(seconds));
            w.until(ExpectedConditions.presenceOfElementLocated(locator));
            logDebug("Element present in DOM: " + locator);
        } catch (TimeoutException e) {
            logError("Timeout waiting for element presence: " + locator + " - " + e.getMessage());
            throw e;
        }
    }

    public void waitForElementVisible(By locator, int seconds) {
        try {
            WebDriverWait w = (seconds == DEFAULT_WAIT_TIME) ? wait : new WebDriverWait(driver, Duration.ofSeconds(seconds));
            w.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logDebug("Element visible: " + locator);
        } catch (TimeoutException e) {
            logError("Timeout waiting for element visibility: " + locator + " - " + e.getMessage());
            throw e;
        }
    }

    public void waitForElementClickable(By locator, int seconds) {
        try {
            WebDriverWait w = (seconds == DEFAULT_WAIT_TIME) ? wait : new WebDriverWait(driver, Duration.ofSeconds(seconds));
            w.until(ExpectedConditions.elementToBeClickable(locator));
            logDebug("Element clickable: " + locator);
        } catch (TimeoutException e) {
            logError("Timeout waiting for element clickable: " + locator + " - " + e.getMessage());
            throw e;
        }
    }

    public FluentWait<WebDriver> fluentWait(int timeoutSeconds, int pollingInterval) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofMillis(pollingInterval))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    public void closeBrowser() {
        if (driver != null) {
            try {
                driver.quit();
                logInfo("WebDriver closed successfully");
            } catch (Exception e) {
                logError("Error closing WebDriver: " + e.getMessage());
            }
        }
    }
}
