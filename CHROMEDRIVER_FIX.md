# ChromeDriver Version Mismatch - Complete Fix Guide

## 🔴 Error You're Seeing

```
SessionNotCreated: Could not start a new session. Response code 500. 
Message: session not created: This version of ChromeDriver only supports Chrome version 114
```

## 🎯 Root Cause

Your Chrome browser version doesn't match the ChromeDriver version that WebDriverManager downloaded. This happens when:
1. Chrome browser auto-updates
2. WebDriverManager cache has old driver version
3. Network issues during driver download

---

## ✅ SOLUTIONS (Try in Order)

### Solution 1: Clear Driver Cache and Rebuild (RECOMMENDED)

Run the automated fix script:
```bash
cd /Users/sallysedky/Downloads/OrangeHRMTest
./fix-chromedriver.sh
```

This script will:
- ✅ Clear WebDriverManager cache
- ✅ Update Maven dependencies
- ✅ Download the correct ChromeDriver for your Chrome version

---

### Solution 2: Manual Cache Clear

If the script doesn't work, manually clear the cache:

```bash
# Clear WebDriverManager cache
rm -rf ~/.cache/selenium
rm -rf ~/.m2/repository/.cache/wdm

# Rebuild project
cd /Users/sallysedky/Downloads/OrangeHRMTest
mvn clean install -U -DskipTests

# Run tests
mvn test
```

---

### Solution 3: Use Selenium Manager (NO WebDriverManager)

Selenium 4.6+ has built-in driver management that's more reliable.

**Option A: Modify LoginTest.java to use the new BaseTest**

Change this line in `LoginTest.java`:
```java
// FROM:
public class LoginTest extends BaseTest {

// TO:
public class LoginTest extends BaseTestWithSeleniumManager {
```

**Option B: Replace the entire BaseTest.java**

I've created an updated `BaseTest.java` - you can use either approach.

---

### Solution 4: Check Your Chrome Version

```bash
# Check Chrome version
/Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome --version
```

If Chrome is too old or too new, WebDriverManager might have issues. Make sure Chrome is up to date:
- Go to Chrome → Settings → About Chrome → Update if available

---

### Solution 5: Specify Exact ChromeDriver Version

If you know your Chrome version, you can force a specific driver version:

**In BaseTest.java, change:**
```java
// FROM:
WebDriverManager.chromedriver().clearDriverCache().setup();

// TO:
WebDriverManager.chromedriver().driverVersion("131.0.6778.85").setup();
```

**Find the right version:**
1. Check your Chrome version (e.g., 131.0.6778.108)
2. Find matching driver at: https://googlechromelabs.github.io/chrome-for-testing/
3. Use that version number

---

## 🔧 What I've Already Fixed

### 1. ✅ Updated BaseTest.java
Added these improvements:
```java
// Clear cache before setup
WebDriverManager.chromedriver().clearDriverCache().setup();

// Better Chrome options for compatibility
chromeOptions.addArguments("--headless=new");  // New headless mode
chromeOptions.addArguments("--disable-dev-shm-usage");
chromeOptions.addArguments("--no-sandbox");
chromeOptions.addArguments("--disable-gpu");
chromeOptions.addArguments("--remote-allow-origins=*");
```

### 2. ✅ Updated WebDriverManager Version
Changed from `5.6.3` to `5.7.0` in pom.xml for better version detection.

### 3. ✅ Created Fix Script
Created `fix-chromedriver.sh` to automate the fix process.

### 4. ✅ Created Alternative BaseTest
Created `BaseTestWithSeleniumManager.java` that uses Selenium's built-in driver management (more reliable).

---

## 🚀 Quick Fix (Run These Commands)

```bash
cd /Users/sallysedky/Downloads/OrangeHRMTest

# Clear cache
rm -rf ~/.cache/selenium
rm -rf ~/.m2/repository/.cache/wdm

# Update dependencies
mvn clean install -U -DskipTests

# Run tests
mvn test
```

---

## 🔍 Verify Everything Works

### 1. Check Chrome Version
```bash
/Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome --version
```

### 2. Check WebDriverManager Cache
```bash
ls -la ~/.cache/selenium/
```

### 3. Run a Single Test
```bash
mvn test -Dtest=LoginTest#testLoginWithValidCredentials
```

---

## 🆘 Alternative: Use Firefox Instead

If Chrome continues to have issues, switch to Firefox:

### 1. Install Firefox
Download from: https://www.mozilla.org/firefox/

### 2. Update config.properties
```properties
browser=firefox
```

### 3. Run Tests
```bash
mvn test
```

Firefox + GeckoDriver tends to have fewer version compatibility issues.

---

## 📋 Troubleshooting Checklist

- [ ] Cleared WebDriverManager cache (`~/.cache/selenium`)
- [ ] Cleared Maven cache for WebDriverManager
- [ ] Updated Chrome to latest version
- [ ] Ran `mvn clean install -U`
- [ ] Internet connection is working (for driver download)
- [ ] No VPN/Proxy blocking driver download
- [ ] Checked Chrome version matches available drivers

---

## 💡 Prevention Tips

### Option 1: Pin Chrome Version
Disable Chrome auto-updates temporarily during test development.

### Option 2: Use Docker
Run tests in a Docker container with fixed Chrome version.

### Option 3: Use Selenium Manager
Switch to `BaseTestWithSeleniumManager` which handles versions better.

### Option 4: Regular Cache Clearing
Add cache clearing to your CI/CD pipeline:
```bash
rm -rf ~/.cache/selenium
```

---

## 🎯 Recommended Approach Going Forward

**Use Selenium Manager (Built into Selenium 4.6+)**

1. Open `LoginTest.java`
2. Change the extends clause:
```java
public class LoginTest extends BaseTestWithSeleniumManager {
```

3. Run tests:
```bash
mvn test
```

Selenium Manager is more reliable because:
- ✅ Automatically detects browser version
- ✅ Downloads correct driver
- ✅ No external dependencies
- ✅ Better caching
- ✅ Maintained by Selenium team

---

## 📞 Still Not Working?

If none of these solutions work, provide:

1. **Your Chrome version:**
   ```bash
   /Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome --version
   ```

2. **Check if driver was downloaded:**
   ```bash
   ls -la ~/.cache/selenium/
   ```

3. **Full error message** from `mvn test`

4. **Java version:**
   ```bash
   java -version
   ```

---

## ✅ Summary of Changes Made

| File | Change | Purpose |
|------|--------|---------|
| BaseTest.java | Added `.clearDriverCache()` | Clear old drivers before setup |
| BaseTest.java | Added Chrome options | Better compatibility |
| pom.xml | Updated WebDriverManager to 5.7.0 | Better version detection |
| fix-chromedriver.sh | New script | Automate cache clearing |
| BaseTestWithSeleniumManager.java | New class | Alternative using Selenium Manager |
| CHROMEDRIVER_FIX.md | This doc | Complete troubleshooting guide |

---

## 🚀 TL;DR - Quick Fix

```bash
cd /Users/sallysedky/Downloads/OrangeHRMTest
rm -rf ~/.cache/selenium
mvn clean install -U -DskipTests
mvn test
```

**That should fix it!** 🎉
