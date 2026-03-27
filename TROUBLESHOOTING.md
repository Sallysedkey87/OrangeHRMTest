# Compilation Error Troubleshooting Guide

## ✅ Issues Fixed

1. **ExtentReports Dependency**: Changed artifact ID from `extent-reports` to `extentreports`
2. **Version Updated**: Using ExtentReports 5.0.9 (stable version)
3. **Added import**: Added `java.io.File` import to ExtentReportManager
4. **Directory creation**: Added code to create report directory if it doesn't exist

## 🔧 How to Build the Project

### Option 1: Using the Build Script
```bash
cd /Users/sallysedky/Downloads/OrangeHRMTest
./build.sh
```

### Option 2: Using Maven Directly
```bash
cd /Users/sallysedky/Downloads/OrangeHRMTest

# Clean and compile
mvn clean compile

# Or clean, compile and install
mvn clean install -DskipTests
```

### Option 3: Using IntelliJ IDEA
1. Right-click on `pom.xml`
2. Select **Maven** → **Reload Project**
3. Go to **Build** → **Build Project** (or press Cmd+F9)

## 🐛 Common Compilation Errors & Solutions

### Error: "Cannot resolve symbol ExtentReports"
**Solution**: 
```bash
mvn clean install -U
```
The `-U` flag forces Maven to update dependencies.

### Error: "package com.aventstack.extentreports does not exist"
**Solution**: Delete the Maven cache and rebuild
```bash
rm -rf ~/.m2/repository/com/aventstack/extentreports
mvn clean install
```

### Error: "Source option 11 is no longer supported"
**Solution**: Check your Java version
```bash
java -version
```
You need Java 11 or higher. If you have a newer version, update pom.xml:
```xml
<maven.compiler.source>17</maven.compiler.source>
<maven.compiler.target>17</maven.compiler.target>
```

### Error: "Unable to download dependencies"
**Solutions**:
1. Check your internet connection
2. Check if you're behind a proxy
3. Clear Maven cache:
```bash
rm -rf ~/.m2/repository
mvn clean install
```

### Error: "JAVA_HOME not set"
**Solution**: Set JAVA_HOME environment variable
```bash
# Check Java location
/usr/libexec/java_home

# Add to ~/.zshrc or ~/.bash_profile
export JAVA_HOME=$(/usr/libexec/java_home)
export PATH=$JAVA_HOME/bin:$PATH

# Reload shell
source ~/.zshrc
```

## 📋 Pre-requisites Checklist

- [ ] Java 11 or higher installed (`java -version`)
- [ ] Maven 3.6+ installed (`mvn --version`)
- [ ] Internet connection (to download dependencies)
- [ ] JAVA_HOME environment variable set

## 🔍 Verify Your Setup

### 1. Check Java
```bash
java -version
# Should show: java version "11" or higher
```

### 2. Check Maven
```bash
mvn --version
# Should show: Apache Maven 3.x.x
```

### 3. Check JAVA_HOME
```bash
echo $JAVA_HOME
# Should show path to Java installation
```

### 4. Test Compilation
```bash
cd /Users/sallysedky/Downloads/OrangeHRMTest
mvn clean compile
```

If successful, you'll see:
```
[INFO] BUILD SUCCESS
```

## 📦 Maven Dependencies Status

All dependencies in pom.xml are verified and available:

| Dependency | Version | Status |
|------------|---------|--------|
| selenium-java | 4.18.0 | ✅ Available |
| testng | 7.9.0 | ✅ Available |
| webdrivermanager | 5.6.3 | ✅ Available |
| extentreports | 5.0.9 | ✅ Available |
| poi | 5.2.5 | ✅ Available |
| poi-ooxml | 5.2.5 | ✅ Available |
| commons-io | 2.15.1 | ✅ Available |

## 🚀 Quick Fix Commands

### Complete Clean and Rebuild
```bash
cd /Users/sallysedky/Downloads/OrangeHRMTest
mvn clean install -U -DskipTests
```

### Force Download All Dependencies
```bash
mvn dependency:purge-local-repository
mvn clean install -DskipTests
```

### Validate Dependencies
```bash
mvn dependency:tree
```

## 📝 IntelliJ IDEA Specific

### Reimport Maven Project
1. Open **Maven** tool window (View → Tool Windows → Maven)
2. Click **Reload All Maven Projects** (circular arrow icon)
3. Wait for indexing to complete

### Invalidate Caches
1. Go to **File → Invalidate Caches**
2. Select **Invalidate and Restart**

### Mark Directories
Ensure directories are marked correctly:
- `src/main/java` → Sources Root (blue folder)
- `src/test/java` → Test Sources Root (green folder)
- `src/test/resources` → Test Resources Root (green folder with icon)

## 🆘 Still Having Issues?

If you're still seeing compilation errors, please provide:

1. **Exact error message** from the console/IDE
2. **Java version**: Run `java -version`
3. **Maven version**: Run `mvn --version`
4. **Operating System**: macOS version

Then I can provide specific help!

## ✅ What's Fixed in the Current Version

1. ✅ Correct ExtentReports artifact ID
2. ✅ Compatible ExtentReports version (5.0.9)
3. ✅ All imports are correct
4. ✅ Directory creation for reports
5. ✅ All dependencies are available in Maven Central
6. ✅ Proper TestNG scope
7. ✅ Maven Surefire plugin configured correctly

---

**Your project should now compile successfully!** 🎉

Run: `./build.sh` or `mvn clean compile`
