# OrangeHRM Test Automation Framework

## Overview
This is a comprehensive test automation framework for OrangeHRM application, built using Selenium WebDriver, TestNG, and Java with Page Object Model (POM) design pattern.

## Technologies Used
- **Java 11** - Programming language
- **Selenium WebDriver 4.18.0** - Web automation
- **TestNG 7.9.0** - Test framework
- **Maven** - Build and dependency management
- **WebDriverManager 5.6.3** - Automatic driver management
- **ExtentReports 5.1.1** - Test reporting
- **Apache POI 5.2.5** - Excel data handling

## Framework Architecture

### Design Pattern
- **Page Object Model (POM)** - Separates page elements and test logic
- **Page Factory** - For element initialization
- **Data-Driven Testing** - Test data stored in config.properties

### Project Structure
```
OrangeHRMTest/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/orangehrm/
│   │           ├── pages/           # Page Object classes
│   │           │   ├── BasePage.java
│   │           │   ├── LoginPage.java
│   │           │   └── DashboardPage.java
│   │           └── utils/           # Utility classes
│   │               ├── ConfigReader.java
│   │               ├── ScreenshotUtil.java
│   │               └── ExtentReportManager.java
│   └── test/
│       ├── java/
│       │   └── com/orangehrm/
│       │       ├── base/            # Base test class
│       │       │   └── BaseTest.java
│       │       └── tests/           # Test classes
│       │           └── LoginTest.java
│       └── resources/
│           └── config.properties    # Configuration file
├── pom.xml                          # Maven dependencies
├── testng.xml                       # TestNG suite configuration
└── README.md                        # This file
```

## Test Cases Covered

### Login Test Scenarios
1. ✅ **Valid Login** - Login with correct username and password
2. ✅ **Invalid Username** - Login with wrong username
3. ✅ **Invalid Password** - Login with wrong password
4. ✅ **Blank Credentials** - Login without entering any credentials
5. ✅ **Blank Username** - Login with only password
6. ✅ **Blank Password** - Login with only username
7. ✅ **Special Characters** - Login with special characters in username
8. ✅ **SQL Injection** - Security test for SQL injection
9. ✅ **Case Sensitivity** - Test username case sensitivity
10. ✅ **Forgot Password Link** - Verify forgot password link is present

## Prerequisites
- Java JDK 11 or higher
- Maven 3.6 or higher
- Chrome browser (or Firefox/Edge)

## Setup Instructions

### 1. Clone the Repository
```bash
cd /Users/sallysedky/Downloads/OrangeHRMTest
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Configure Test Data
Edit `src/test/resources/config.properties` to customize:
- Browser type (chrome/firefox/edge)
- Application URL
- Test credentials
- Timeouts and wait times

### 4. Run Tests

#### Run all tests
```bash
mvn test
```

#### Run specific test class
```bash
mvn test -Dtest=LoginTest
```

#### Run tests using TestNG XML
```bash
mvn test -DsuiteXmlFile=testng.xml
```

#### Run in headless mode
Change `headless=true` in config.properties

## Reports

### ExtentReports
- Location: `test-output/ExtentReport.html`
- Open in browser to view detailed test results
- Includes screenshots for failed tests

### TestNG Reports
- Location: `test-output/index.html`
- Default TestNG HTML report

### Screenshots
- Location: `screenshots/`
- Automatically captured on test failure
- Named with test name and timestamp

## Configuration

### config.properties
```properties
# Application URL
app.url=https://opensource-demo.orangehrmlive.com/web/index.php/auth/login

# Browser Configuration
browser=chrome
headless=false

# Test Credentials
valid.username=Admin
valid.password=admin123

# Timeouts (in seconds)
implicit.wait=10
explicit.wait=20
page.load.timeout=30
```

## Key Features

### 1. **Robust Wait Strategies**
- Implicit and explicit waits
- Custom wait methods in BasePage

### 2. **Screenshot on Failure**
- Automatic screenshot capture
- Attached to ExtentReports

### 3. **Detailed Logging**
- Step-by-step logging in reports
- Console logging for debugging

### 4. **Cross-Browser Support**
- Chrome, Firefox, Edge
- Automatic driver management

### 5. **Reusable Components**
- BasePage with common methods
- BaseTest with setup/teardown
- Utility classes for common operations

### 6. **Easy Maintenance**
- Page Object Model
- Centralized configuration
- Modular design

## Extending the Framework

### Add New Page Object
1. Create new class extending `BasePage`
2. Define locators as private By objects
3. Create methods for page actions

### Add New Test
1. Create test class extending `BaseTest`
2. Initialize page objects in `@BeforeMethod`
3. Write test methods with `@Test` annotation

### Add Test Data
- Add to `config.properties` for simple data
- Create Excel files for data-driven testing

## Troubleshooting

### Common Issues

**Issue**: WebDriver not found
- **Solution**: WebDriverManager handles this automatically, ensure internet connection

**Issue**: Element not found
- **Solution**: Increase wait times in config.properties

**Issue**: Tests fail on first run
- **Solution**: Check internet connection and application availability

## Best Practices Implemented

✅ Page Object Model design pattern
✅ DRY (Don't Repeat Yourself) principle
✅ Proper wait strategies
✅ Exception handling
✅ Meaningful test names
✅ Detailed reporting
✅ Screenshot on failure
✅ Configuration management
✅ Parallel execution support (configurable)

## Author
Automation Test Engineer

## Version
1.0.0

## Last Updated
February 2026
