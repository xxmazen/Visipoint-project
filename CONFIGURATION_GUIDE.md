# Configuration Management - property.basepath

## Overview
The `property.basepath` is now configured in the **`config.properties`** file located at:
```
src/main/resources/config.properties
```

## Location & File Structure

### Configuration File
**Path:** `D:\Visipoint\src\main\resources\config.properties`

### ConfigReader Utility
**Path:** `D:\Visipoint\src\test\java\Visipoint\ConfigReader.java`
- Reads properties from `config.properties`
- Provides convenient getter methods
- Handles null checks and defaults

### BaseTest Integration
**Path:** `D:\Visipoint\src\test\java\Visipoint\BaseTest.java`
- Now uses `ConfigReader.getBasePath()` instead of hardcoded URL
- Configuration is loaded dynamically

## Using property.basepath

### Current Value
```properties
property.basepath=https://www.visipoint.me/login
```

### How to Use in Code

#### Option 1: In BaseTest (Automatic)
```java
protected final String baseURL = ConfigReader.getBasePath();
```

#### Option 2: Direct Usage
```java
String url = ConfigReader.getBasePath();
driver.get(url);
```

#### Option 3: With Fallback
```java
String url = ConfigReader.getProperty("property.basepath", "https://www.visipoint.me/login");
```

## Available Properties in config.properties

### Application Properties
```properties
property.basepath=https://www.visipoint.me/login
property.basepath.staging=https://staging.visipoint.me/login
property.basepath.dev=https://dev.visipoint.me/login
```

### Browser Configuration
```properties
browser.name=chrome
browser.headless=false
browser.maximize=true
```

### Wait Times
```properties
wait.implicit=5
wait.explicit=5
wait.fluent=10
```

### Test Data
```properties
test.email=test@visipoint.me
test.password=TestPassword123
test.phone=12345678901
```

## ConfigReader Methods

### Basic Methods
```java
// Get string property
ConfigReader.getProperty("property.basepath")

// Get with default
ConfigReader.getProperty("key", "default")

// Get integer
ConfigReader.getIntProperty("wait.implicit", 5)

// Get boolean
ConfigReader.getBooleanProperty("browser.headless", false)
```

### Convenience Methods
```java
ConfigReader.getBasePath()          // Base URL
ConfigReader.getExplicitWait()      // Wait time
ConfigReader.getTestEmail()         // Test credentials
ConfigReader.getTestPassword()
ConfigReader.getTestPhone()
ConfigReader.getEnvironment()       // Current environment
ConfigReader.getPollingInterval()   // Polling interval
```

## Example Usage in Tests

```java
public class loginTestCases extends BaseTest {
    
    @Test
    public void testLogin() {
        // Base URL is automatically loaded from config
        // driver.get(baseURL); - Already done in initializeDriver()
        
        // Get test credentials from config
        String email = ConfigReader.getTestEmail();
        String password = ConfigReader.getTestPassword();
        
        // Use credentials for test
        Enter(emailField, email);
        Enter(passwordField, password);
        click(submitButton);
    }
}
```

## Changing Configuration

### Edit the config.properties file:
```ini
# Change base URL
property.basepath=https://new-domain.com/login

# Change wait time
wait.explicit=10

# Change environment
environment=staging
```

### Changes take effect automatically on next test run

## Environment-Specific Configuration

To use different configurations per environment:

### Option 1: Update property value manually
```properties
# For local testing
environment=local
property.basepath=http://localhost:3000/login

# For production testing
environment=production
property.basepath=https://www.visipoint.me/login
```

### Option 2: Use environment-specific files (Advanced)
Create separate property files:
- `config-local.properties`
- `config-staging.properties`
- `config-prod.properties`

Update ConfigReader to load based on environment variable:
```java
String env = System.getenv("TEST_ENV") != null ? 
    System.getenv("TEST_ENV") : "local";
String configPath = "src/main/resources/config-" + env + ".properties";
```

## Best Practices

✅ **Do:**
- Store all configuration in `config.properties`
- Use `ConfigReader` to access properties
- Keep sensitive data in separate config files (not in git)
- Use meaningful property names
- Document all new properties

❌ **Don't:**
- Hardcode URLs or credentials in test files
- Use System.getProperty() directly
- Mix different configuration approaches
- Store passwords in plain text (use encryption in production)

## Files Modified

1. **Created:** `src/main/resources/config.properties`
   - All application configuration
   
2. **Created:** `src/test/java/Visipoint/ConfigReader.java`
   - Configuration reader utility
   
3. **Updated:** `src/test/java/Visipoint/BaseTest.java`
   - Uses `ConfigReader.getBasePath()` instead of hardcoded URL

## Troubleshooting

### Issue: ConfigReader not loading properties
**Solution:** Ensure `config.properties` is in `src/main/resources/` directory

### Issue: Property returns null
**Solution:** Check property name matches exactly in `config.properties`

### Issue: Wrong value being used
**Solution:** Check environment-specific overrides or default values in ConfigReader

---

**Last Updated:** March 12, 2026

