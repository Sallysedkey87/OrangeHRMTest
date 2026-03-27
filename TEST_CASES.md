# Test Case Documentation - OrangeHRM Login Tests

## Test Suite Information
- **Application**: OrangeHRM
- **Module**: Login
- **Test Type**: Functional, Security
- **Automation Tool**: Selenium WebDriver with Java
- **Test Framework**: TestNG

---

## Test Cases

### TC-001: Valid Login Test
**Objective**: Verify user can successfully login with valid credentials

**Preconditions**:
- Application is accessible
- Valid credentials are available

**Test Steps**:
1. Navigate to login page
2. Enter valid username: "Admin"
3. Enter valid password: "admin123"
4. Click Login button

**Expected Result**:
- User is redirected to Dashboard
- Dashboard header is visible
- User dropdown menu is displayed

**Priority**: High
**Test Data**: 
- Username: Admin
- Password: admin123

---

### TC-002: Invalid Username Test
**Objective**: Verify system shows error message for invalid username

**Test Steps**:
1. Navigate to login page
2. Enter invalid username: "InvalidUser123"
3. Enter valid password: "admin123"
4. Click Login button

**Expected Result**:
- Error message "Invalid credentials" is displayed
- User remains on login page
- Login unsuccessful

**Priority**: High

---

### TC-003: Invalid Password Test
**Objective**: Verify system shows error message for invalid password

**Test Steps**:
1. Navigate to login page
2. Enter valid username: "Admin"
3. Enter invalid password: "WrongPassword123"
4. Click Login button

**Expected Result**:
- Error message "Invalid credentials" is displayed
- User remains on login page
- Login unsuccessful

**Priority**: High

---

### TC-004: Blank Credentials Test
**Objective**: Verify system validates empty username and password fields

**Test Steps**:
1. Navigate to login page
2. Leave username field blank
3. Leave password field blank
4. Click Login button

**Expected Result**:
- "Required" validation message appears for both fields
- Login button click does not proceed
- User remains on login page

**Priority**: High

---

### TC-005: Blank Username Test
**Objective**: Verify system validates empty username field

**Test Steps**:
1. Navigate to login page
2. Leave username field blank
3. Enter valid password: "admin123"
4. Click Login button

**Expected Result**:
- "Required" validation message appears for username field
- Login does not proceed
- User remains on login page

**Priority**: Medium

---

### TC-006: Blank Password Test
**Objective**: Verify system validates empty password field

**Test Steps**:
1. Navigate to login page
2. Enter valid username: "Admin"
3. Leave password field blank
4. Click Login button

**Expected Result**:
- "Required" validation message appears for password field
- Login does not proceed
- User remains on login page

**Priority**: Medium

---

### TC-007: Special Characters in Username Test
**Objective**: Verify system handles special characters in username

**Test Steps**:
1. Navigate to login page
2. Enter username with special characters: "Admin@#$%"
3. Enter valid password: "admin123"
4. Click Login button

**Expected Result**:
- Error message "Invalid credentials" is displayed
- Login unsuccessful
- System handles special characters gracefully

**Priority**: Medium

---

### TC-008: SQL Injection Security Test
**Objective**: Verify system is protected against SQL injection attacks

**Test Steps**:
1. Navigate to login page
2. Enter SQL injection string in username: "' OR '1'='1"
3. Enter SQL injection string in password: "' OR '1'='1"
4. Click Login button

**Expected Result**:
- SQL injection attempt fails
- Login unsuccessful
- No database error messages exposed
- System security maintained

**Priority**: Critical
**Test Type**: Security

---

### TC-009: Case Sensitive Username Test
**Objective**: Verify username field is case sensitive

**Test Steps**:
1. Navigate to login page
2. Enter username in lowercase: "admin" (instead of "Admin")
3. Enter valid password: "admin123"
4. Click Login button

**Expected Result**:
- Error message "Invalid credentials" is displayed
- Login unsuccessful
- Username validation is case-sensitive

**Priority**: Medium

---

### TC-010: Forgot Password Link Test
**Objective**: Verify forgot password link is present and visible

**Test Steps**:
1. Navigate to login page
2. Check for "Forgot your password?" link

**Expected Result**:
- "Forgot your password?" link is visible
- Link is clickable
- UI element is present on login page

**Priority**: Low

---

## Test Execution Summary

| Test Case ID | Test Case Name | Priority | Status |
|--------------|----------------|----------|--------|
| TC-001 | Valid Login | High | ✅ Automated |
| TC-002 | Invalid Username | High | ✅ Automated |
| TC-003 | Invalid Password | High | ✅ Automated |
| TC-004 | Blank Credentials | High | ✅ Automated |
| TC-005 | Blank Username | Medium | ✅ Automated |
| TC-006 | Blank Password | Medium | ✅ Automated |
| TC-007 | Special Characters | Medium | ✅ Automated |
| TC-008 | SQL Injection | Critical | ✅ Automated |
| TC-009 | Case Sensitive | Medium | ✅ Automated |
| TC-010 | Forgot Password Link | Low | ✅ Automated |

---

## Test Coverage

### Functional Testing
- ✅ Valid login flow
- ✅ Invalid credentials handling
- ✅ Field validation (required fields)
- ✅ Special characters handling
- ✅ Case sensitivity
- ✅ UI element presence

### Security Testing
- ✅ SQL injection prevention
- ✅ Input sanitization

### Negative Testing
- ✅ Invalid username
- ✅ Invalid password
- ✅ Empty fields
- ✅ Special characters
- ✅ Case variations

---

## Test Environment

| Component | Details |
|-----------|---------|
| Application URL | https://opensource-demo.orangehrmlive.com/web/index.php/auth/login |
| Browser | Chrome (configurable) |
| OS | macOS, Windows, Linux |
| Test Data | config.properties |
| Reporting | ExtentReports, TestNG Reports |

---

## Defect Summary

*Note: This section should be updated after test execution with any defects found*

| Defect ID | Test Case | Description | Severity | Status |
|-----------|-----------|-------------|----------|--------|
| - | - | - | - | - |

---

## Test Metrics

- **Total Test Cases**: 10
- **Automated**: 10 (100%)
- **High Priority**: 4
- **Medium Priority**: 5
- **Low Priority**: 1
- **Security Tests**: 1

---

## Traceability Matrix

| Requirement | Test Cases | Coverage |
|-------------|------------|----------|
| User Login | TC-001 | ✅ |
| Login Validation | TC-002, TC-003 | ✅ |
| Field Validation | TC-004, TC-005, TC-006 | ✅ |
| Security | TC-008 | ✅ |
| Input Handling | TC-007, TC-009 | ✅ |
| UI Elements | TC-010 | ✅ |

---

## Test Execution Instructions

1. Ensure prerequisites are met (Java 11+, Maven)
2. Run: `mvn clean test`
3. Review reports in `test-output/ExtentReport.html`
4. Check screenshots for failed tests in `screenshots/` folder
5. Document any defects found
6. Update test case status

---

## Maintenance Notes

- **Last Updated**: February 2026
- **Framework Version**: 1.0.0
- **Next Review Date**: As needed when application changes
- **Contact**: Automation Team

---

## Notes
- Test data is configured in `config.properties`
- Screenshots are automatically captured on test failure
- Tests can run in parallel (configure in testng.xml)
- Cross-browser testing supported (Chrome, Firefox, Edge)
