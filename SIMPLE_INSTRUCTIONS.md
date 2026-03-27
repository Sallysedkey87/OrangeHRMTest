# 🚀 OrangeHRM Test Automation Framework - Simple Instructions

## 📋 Table of Contents
1. [Quick Start](#quick-start)
2. [What Was Built](#what-was-built)
3. [How to Run Tests](#how-to-run-tests)
4. [Project Structure](#project-structure)
5. [Configuration](#configuration)
6. [Common Issues & Solutions](#common-issues--solutions)
7. [Adding New Tests](#adding-new-tests)

---

## 🎯 Quick Start

### Prerequisites
- Java 11 or higher installed
- Maven installed
- Chrome browser installed
- Internet connection

### Run Tests (3 Commands)
```bash
cd /Users/sallysedky/Downloads/OrangeHRMTest
mvn clean install -DskipTests
mvn test
```

### View Report
```bash
open test-output/ExtentReport.html
```

---

## 📦 What Was Built

This is a complete test automation framework with:

### ✅ Framework Components
- **Page Object Model (POM)** - Organized, maintainable code
- **10 Login Test Cases** - Comprehensive test coverage
- **ExtentReports** - Beautiful HTML test reports
- **Screenshot on Failure** - Automatic screenshot capture
- **Cross-Browser Support** - Chrome, Firefox, Edge
- **Configurable Settings** - Easy configuration via properties file

### ✅ Test Cases Implemented
1. ✅ Valid login with correct credentials
2. ❌ Invalid username
3. ❌ Invalid password
4. ❌ Blank credentials (both fields empty)
5. ❌ Blank username only
6. ❌ Blank password only
7. ❌ Special characters in username
8. 🔒 SQL injection security test
9. ❌ Case-sensitive username check
10. ✅ Forgot password link verification

---

## 🎮 How to Run Tests

### Option 1: Run All Tests
```bash
cd /Users/sallysedky/Downloads/OrangeHRMTest
mvn test
```

### Option 2: Run Single Test
```bash
mvn test -Dtest=LoginTest#testLoginWithValidCredentials
```

### Option 3: Run in Headless Mode (No Browser UI)
```bash
# Edit config.properties first: headless=true
mvn test
```

### Option 4: Use Automated Scripts

**Test Report Script:**
```bash
./test-report.sh
```
This runs a test and automatically opens the report.

**Fix Scripts (If Errors Occur):**
```bash
./fix-lombok.sh          # Fix compilation errors
./fix-chromedriver.sh    # Fix ChromeDriver version issues
```

---

## 📁 Project Structure

```
OrangeHRMTest/
│
├── src/main/java/com/orangehrm/
│   ├── pages/              # Page Object classes
│   │   ├── BasePage.java          # Common page methods
│   │   ├── LoginPage.java         # Login page elements & actions
│   │   └── DashboardPage.java     # Dashboard page verification
│   │
│   └── utils/              # Utility classes
│       ├── ConfigReader.java      # Read configuration
│       ├── ScreenshotUtil.java    # Capture screenshots
│       └── ExtentReportManager.java # Report management
│
├── src/test/java/com/orangehrm/
│   ├── base/               # Base test setup
│   │   ├── BaseTest.java              # WebDriver setup/teardown
│   │   └── BaseTestWithSeleniumManager.java # Alternative (no WebDriverManager)
│   │
│   └── tests/              # Test classes
│       └── LoginTest.java         # 10 login test cases
│
├── src/test/resources/
│   └── config.properties   # Configuration file
│
├── pom.xml                 # Maven dependencies
├── testng.xml              # TestNG suite configuration
│
├── test-output/            # Generated after test run
│   └── ExtentReport.html   # Your test report
│
└── screenshots/            # Generated on test failure
    └── testName_timestamp.png
```

---

## ⚙️ Configuration

### Edit Configuration File
**Location:** `src/test/resources/config.properties`

```properties
# Application URL
app.url=https://opensource-demo.orangehrmlive.com/web/index.php/auth/login

# Browser Settings
browser=chrome          # Options: chrome, firefox, edge
headless=false         # Set to true for no browser UI

# Test Credentials
valid.username=Admin
valid.password=admin123

# Timeouts (in seconds)
implicit.wait=15
explicit.wait=30
page.load.timeout=60

# Screenshot Settings
screenshot.path=./screenshots/
screenshot.on.failure=true

# Report Location
report.path=test-output/ExtentReport.html
```

### Change Browser
```properties
browser=firefox   # Switch to Firefox
browser=edge      # Switch to Edge
```

### Run in Headless Mode
```properties
headless=true     # No browser window opens
```

---

## 🐛 Common Issues & Solutions

### Issue 1: Compilation Errors (Lombok)
**Error:** `java.lang.IllegalAccessError: class lombok.javac.apt.LombokProcessor`

**Solution:**
```bash
./fix-lombok.sh
```
Or manually:
```bash
rm -rf target/ ~/.m2/repository/io/github/bonigarcia/webdrivermanager
mvn clean install -DskipTests
```

### Issue 2: ChromeDriver Version Mismatch
**Error:** `This version of ChromeDriver only supports Chrome version 114`

**Solution:**
```bash
./fix-chromedriver.sh
```
Or manually:
```bash
rm -rf ~/.cache/selenium
mvn clean install -U -DskipTests
mvn test
```

### Issue 3: Renderer Timeout
**Error:** `Timed out receiving message from renderer`

**Solution:** Already fixed! But if still occurs:
```bash
# Close all Chrome windows first
pkill -9 "Google Chrome"
mvn clean test
```

Or switch to Firefox:
```properties
# Edit config.properties
browser=firefox
```

### Issue 4: Report Not Generated
**Error:** Can't find ExtentReport.html

**Solution:**
```bash
./test-report.sh
```
Or manually:
```bash
mvn clean test
ls -la test-output/ExtentReport.html
open test-output/ExtentReport.html
```

### Issue 5: Tests Are Slow
**Solution:** Use headless mode:
```properties
# Edit config.properties
headless=true
```

---

## ➕ Adding New Tests

### Step 1: Create Test Method in LoginTest.java

```java
@Test(priority = 11, description = "Your test description")
public void testYourNewTest() {
    ExtentTest test = extent.createTest("Test Name", "Test Description");
    ExtentReportManager.setExtentTest(test);
    
    try {
        test.log(Status.INFO, "Step 1 description");
        // Your test code here
        
        test.log(Status.INFO, "Step 2 description");
        // More test code
        
        test.log(Status.PASS, "Test passed successfully");
    } catch (Exception e) {
        test.log(Status.FAIL, "Test failed: " + e.getMessage());
        throw e;
    }
}
```

### Step 2: Run Your New Test
```bash
mvn test -Dtest=LoginTest#testYourNewTest
```

---

## 📊 Understanding the Report

After running tests, open `test-output/ExtentReport.html`:

### Report Sections:
1. **Dashboard** - Pass/Fail statistics with charts
2. **Test Details** - Each test case with logs
3. **Timeline** - When tests ran
4. **System Info** - Browser, OS, environment details
5. **Screenshots** - Attached to failed tests

### Report Colors:
- 🟢 **Green** - Test passed
- 🔴 **Red** - Test failed
- ⚠️ **Yellow** - Test skipped
- 🔵 **Blue** - Info/Debug logs

---

## 🎓 Framework Features Explained

### 1. Page Object Model (POM)
- **What:** Separates page elements from test logic
- **Why:** Easy to maintain, reusable code
- **Example:** `LoginPage.java` contains all login page elements

### 2. ExtentReports
- **What:** Beautiful HTML test reports
- **Why:** Easy to understand test results
- **Location:** `test-output/ExtentReport.html`

### 3. Screenshot on Failure
- **What:** Automatic screenshot when test fails
- **Why:** Easy debugging of failures
- **Location:** `screenshots/` folder

### 4. Configuration Management
- **What:** Centralized configuration in properties file
- **Why:** Change settings without code changes
- **Location:** `config.properties`

### 5. Automatic Page Load Wait
- **What:** Waits for page to be fully loaded
- **Why:** Prevents timing issues, stable tests
- **Where:** Built into `LoginPage` constructor

### 6. Cross-Browser Support
- **What:** Run tests on Chrome, Firefox, or Edge
- **Why:** Test on multiple browsers
- **How:** Change `browser=chrome` in config.properties

---

## 📖 Documentation Files

| File | Description |
|------|-------------|
| **README.md** | Complete framework documentation |
| **QUICKSTART.md** | Quick start guide |
| **TEST_CASES.md** | Detailed test case documentation |
| **SIMPLE_INSTRUCTIONS.md** | This file - simple instructions |
| **TROUBLESHOOTING.md** | General troubleshooting |
| **LOMBOK_FIX.md** | Fix Lombok compilation errors |
| **CHROMEDRIVER_FIX.md** | Fix ChromeDriver version issues |
| **RENDERER_TIMEOUT_FIX.md** | Fix renderer timeout errors |
| **REPORT_FIX.md** | Fix report generation issues |
| **PAGE_LOAD_WAIT_IMPLEMENTATION.md** | Page load wait details |

---

## 🔧 Maintenance Commands

### Clean Everything
```bash
mvn clean
rm -rf test-output/ screenshots/
```

### Update Dependencies
```bash
mvn clean install -U -DskipTests
```

### Clear Driver Cache
```bash
rm -rf ~/.cache/selenium
rm -rf ~/.m2/repository/.cache/wdm
```

### Rebuild Project
```bash
mvn clean compile
```

---

## 💡 Best Practices

### ✅ Do's
- ✅ Run `mvn clean test` for full test runs
- ✅ Check ExtentReport.html after each run
- ✅ Use descriptive test names
- ✅ Add logs to tests using `test.log(Status.INFO, "message")`
- ✅ Keep config.properties updated
- ✅ Close other Chrome windows before running tests

### ❌ Don'ts
- ❌ Don't modify BaseTest.java unless necessary
- ❌ Don't hardcode values in tests (use config.properties)
- ❌ Don't run multiple test sessions simultaneously
- ❌ Don't commit test-output/ or screenshots/ folders

---

## 🎯 Quick Command Reference

```bash
# Run all tests
mvn test

# Run single test
mvn test -Dtest=LoginTest#testLoginWithValidCredentials

# Compile only
mvn clean compile

# Install dependencies
mvn clean install -DskipTests

# View report
open test-output/ExtentReport.html

# Clear cache
rm -rf ~/.cache/selenium && mvn clean test

# Fix compilation
./fix-lombok.sh

# Fix ChromeDriver
./fix-chromedriver.sh

# Test with report
./test-report.sh
```

---

## 📞 Need Help?

### Check These First:
1. ✅ Is Java 11+ installed? `java -version`
2. ✅ Is Maven installed? `mvn --version`
3. ✅ Is Chrome installed? `ls /Applications/ | grep Chrome`
4. ✅ Is internet working? `ping google.com`

### Common Solutions:
- **Compilation errors** → Run `./fix-lombok.sh`
- **ChromeDriver errors** → Run `./fix-chromedriver.sh`
- **Report not found** → Run `./test-report.sh`
- **Tests are slow** → Set `headless=true`
- **Chrome issues** → Switch to `browser=firefox`

### Documentation:
- Read the appropriate .md file for detailed help
- All issues have been documented with solutions

---

## ✅ Success Checklist

After setup, verify everything works:

- [ ] Run `mvn clean compile` - should see "BUILD SUCCESS"
- [ ] Run `mvn test -Dtest=LoginTest#testLoginWithValidCredentials`
- [ ] Chrome should open and run the test
- [ ] Test should pass (green in console)
- [ ] Report should be generated: `test-output/ExtentReport.html`
- [ ] Report should show 1 passed test
- [ ] Screenshot folder should be empty (test passed, no failure)

If all checked ✅ - Your framework is working perfectly!

---

## 🎉 Summary

**You now have:**
- ✅ Complete test automation framework
- ✅ 10 comprehensive login test cases
- ✅ Beautiful HTML reports with screenshots
- ✅ Cross-browser support (Chrome, Firefox, Edge)
- ✅ Automatic page load waits
- ✅ Easy configuration management
- ✅ Comprehensive documentation
- ✅ Fix scripts for common issues

**To run tests:**
```bash
mvn test
```

**To view report:**
```bash
open test-output/ExtentReport.html
```

---

## 📅 Framework Info

- **Created:** February 2026
- **Framework Version:** 1.0
- **Java Version:** 11+
- **Selenium Version:** 4.18.0
- **TestNG Version:** 7.9.0
- **Application:** OrangeHRM (Demo)

---

**Happy Testing! 🚀**

All instructions, fixes, and documentation are ready for you to use!
