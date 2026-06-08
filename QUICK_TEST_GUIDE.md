# Quick Testing Guide

## Step 1: Update Dependencies
```powershell
cd D:\Visipoint
mvn clean install
```

## Step 2: Verify Chrome Installation
- Chrome browser must be installed
- Current setup tested with Chrome v145
- WebDriver will automatically manage ChromeDriver

## Step 3: Run Tests

### Run Login Tests
```powershell
mvn test -Dtest=Login
```

### Run SignUp Tests
```powershell
mvn test -Dtest=signUP
```

### Run REST API Tests
```powershell
mvn test -Dtest=REST
```

### Run All Tests
```powershell
mvn test
```

## What Changed - Quick Summary

| Issue | Fix |
|-------|-----|
| WebDriver null | Added proper initialization with error handling |
| SSL errors | Added `--ignore-certificate-errors` to Chrome options |
| Missing credentials | Added `Enter(Email, email)` and `Enter(Password, password)` calls |
| CDP warning | Added selenium-devtools-v145 dependency |
| Test setup | Improved setUp/tearDown with BeforeMethod/AfterMethod |

## Chrome Options Applied
The following Chrome options are now configured for all browser tests:
- `--start-maximized` - Maximize browser window
- `--ignore-certificate-errors` - Ignore SSL certificate errors
- `--ignore-ssl-errors` - Ignore SSL protocol errors
- `--disable-blink-features=AutomationControlled` - Hide selenium automation detection
- `--no-sandbox` - Disable sandbox for testing
- `--disable-dev-shm-usage` - Disable shared memory usage

## Test Credentials
Located in `src/main/resources/config.properties`:
- Email: test@visipoint.me
- Password: TestPassword123

## Troubleshooting

### Still getting WebDriver null?
1. Check that setUp() completes without exceptions
2. Verify Chrome is installed and accessible
3. Check logs for SSL or connection errors

### SSL errors persist?
1. Verify internet connection
2. Try accessing https://www.visipoint.me manually in Chrome
3. Check firewall/proxy settings

### Tests fail immediately?
1. Run `mvn clean install` to update all dependencies
2. Check that testBase is properly initialized in setUp()
3. Verify test credentials are correct

## Expected Test Flow

1. ✅ setUp() initializes Chrome with WebDriver
2. ✅ SSL options applied to bypass certificate errors
3. ✅ Navigate to https://www.visipoint.me/login
4. ✅ Enter test credentials in form fields
5. ✅ Click login/register buttons
6. ✅ Assertions validate expected behavior
7. ✅ tearDown() properly closes browser

---
All fixes have been applied. Tests should now run without the null driver or SSL errors.

