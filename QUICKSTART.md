# Quick Start Guide - OrangeHRM Test Automation Framework

## 🚀 Get Started in 3 Steps

### Step 1: Install Maven Dependencies
```bash
mvn clean install -DskipTests
```
This will download all required dependencies (Selenium, TestNG, etc.)

### Step 2: Run the Tests
```bash
mvn test
```

### Step 3: View the Report
Open the report file in your browser:
```bash
open test-output/ExtentReport.html
```

---

## 📊 What Gets Tested

The framework includes **10 comprehensive login test cases**:

1. ✅ Valid login with correct credentials
2. ❌ Invalid username
3. ❌ Invalid password  
4. ❌ Empty credentials (both fields blank)
5. ❌ Blank username only
6. ❌ Blank password only
7. ❌ Special characters in username
8. 🔒 SQL injection attempt (security test)
9. ❌ Case-sensitive username check
10. ✅ Forgot password link verification

---

## 🎯 Test Execution Options

### Run all tests
```bash
mvn test
```

### Run specific test
```bash
mvn test -Dtest=LoginTest#testLoginWithValidCredentials
```

### Run with custom browser (edit config.properties first)
```properties
browser=chrome    # or firefox, edge
headless=false    # set to true for headless mode
```

### Run in headless mode (no browser UI)
1. Edit `src/test/resources/config.properties`
2. Change `headless=false` to `headless=true`
3. Run: `mvn test`

---

## 📁 Key Files to Know

| File | Purpose |
|------|---------|
| `pom.xml` | Maven dependencies |
| `testng.xml` | Test suite configuration |
| `config.properties` | Test configuration (URL, credentials, browser) |
| `LoginTest.java` | All login test cases |
| `LoginPage.java` | Page object for login page |
| `DashboardPage.java` | Page object for dashboard page |

---

## 🔧 Customization

### Change Test URL
Edit `src/test/resources/config.properties`:
```properties
app.url=https://your-app-url.com
```

### Change Browser
Edit `src/test/resources/config.properties`:
```properties
browser=chrome    # Options: chrome, firefox, edge
```

### Change Timeouts
Edit `src/test/resources/config.properties`:
```properties
implicit.wait=10
explicit.wait=20
page.load.timeout=30
```

### Update Test Credentials
Edit `src/test/resources/config.properties`:
```properties
valid.username=YourUsername
valid.password=YourPassword
```

---

## 📸 Screenshots

- Automatically captured on test failure
- Saved in: `screenshots/` folder
- Named with test name and timestamp
- Attached to HTML report

---

## 📈 Reports

### ExtentReports (Recommended)
- **Location**: `test-output/ExtentReport.html`
- Beautiful, detailed HTML report
- Includes screenshots
- Shows test execution timeline
- Pass/Fail statistics

### TestNG Reports
- **Location**: `test-output/index.html`
- Standard TestNG HTML report

---

## 🐛 Troubleshooting

### Tests won't run?
```bash
# Clean and rebuild
mvn clean install

# Verify Java version (needs Java 11+)
java -version
```

### Browser doesn't open?
- Check internet connection (WebDriverManager needs to download drivers)
- Ensure Chrome is installed (or change browser in config.properties)

### Element not found errors?
- Increase wait times in `config.properties`
- Application might be slow/down

### Permission denied?
```bash
# On Mac/Linux, make sure you have execute permissions
chmod +x mvnw
```

---

## 🎓 Framework Features

✅ **Page Object Model** - Maintainable and scalable
✅ **Automatic Screenshots** - On test failure
✅ **Detailed Reports** - ExtentReports with logs
✅ **Cross-Browser** - Chrome, Firefox, Edge
✅ **Configurable** - Easy configuration via properties file
✅ **Auto Driver Management** - No manual driver setup needed
✅ **Wait Strategies** - Smart waits for stable tests
✅ **Logging** - Step-by-step test execution logs

---

## 📞 Need Help?

Check the detailed README.md file for:
- Complete framework architecture
- How to add new tests
- How to add new page objects
- Best practices
- Troubleshooting guide

---

## ✨ Pro Tips

1. **Run tests in headless mode** for faster execution
2. **Check ExtentReport.html** for detailed test results
3. **Review screenshots** when tests fail
4. **Keep config.properties updated** with correct test data
5. **Use TestNG XML** to organize test suites

---

Happy Testing! 🎉
