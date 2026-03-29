package com.orangehrm.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utils.ConfigReader;
import com.orangehrm.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for OrangeHRM Login functionality
 * Contains various test cases to validate login scenarios
 */
public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
    }

    @Test(priority = 1, description = "Verify login with valid credentials")
    public void testLoginWithValidCredentials() {
        ExtentTest test = extent.createTest("Valid Login Test", "Verify user can login with valid credentials");
        ExtentReportManager.setExtentTest(test);

        try {
            test.log(Status.INFO, "Navigating to login page");
            //Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page is not displayed");

            test.log(Status.INFO, "Entering valid username: " + ConfigReader.getValidUsername());
            loginPage.enterUsername(ConfigReader.getValidUsername());

            test.log(Status.INFO, "Entering valid password");
            loginPage.enterPassword(ConfigReader.getValidPassword());

            test.log(Status.INFO, "Clicking login button");
            loginPage.clickLoginButton();

            test.log(Status.INFO, "Verifying dashboard is displayed");
            Assert.assertTrue(dashboardPage.isLoginSuccessful(), "Login was not successful - Dashboard not displayed");

            test.log(Status.PASS, "Login successful with valid credentials");
        } catch (Throwable e) {
            test.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 2, description = "Verify login with invalid username")
    public void testLoginWithInvalidUsername() {
        ExtentTest test = extent.createTest("Invalid Username Test", "Verify error message with invalid username");
        ExtentReportManager.setExtentTest(test);

        try {
            test.log(Status.INFO, "Entering invalid username");
            loginPage.enterUsername("InvalidUser123");

            test.log(Status.INFO, "Entering valid password");
            loginPage.enterPassword(ConfigReader.getValidPassword());

            test.log(Status.INFO, "Clicking login button");
            loginPage.clickLoginButton();

            test.log(Status.INFO, "Verifying error message is displayed");
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed");

            String errorMsg = loginPage.getErrorMessage();
            test.log(Status.INFO, "Error message: " + errorMsg);
            Assert.assertTrue(errorMsg.contains("Invalid credentials"), "Error message text is incorrect");

            test.log(Status.PASS, "Error message displayed correctly for invalid username");
        } catch (Throwable e) {
            test.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 3, description = "Verify login with invalid password")
    public void testLoginWithInvalidPassword() {
        ExtentTest test = extent.createTest("Invalid Password Test", "Verify error message with invalid password");
        ExtentReportManager.setExtentTest(test);

        try {
            test.log(Status.INFO, "Entering valid username");
            loginPage.enterUsername(ConfigReader.getValidUsername());

            test.log(Status.INFO, "Entering invalid password");
            loginPage.enterPassword("WrongPassword123");

            test.log(Status.INFO, "Clicking login button");
            loginPage.clickLoginButton();

            test.log(Status.INFO, "Verifying error message is displayed");
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed");

            String errorMsg = loginPage.getErrorMessage();
            test.log(Status.INFO, "Error message: " + errorMsg);
            Assert.assertTrue(errorMsg.contains("Invalid credentials"), "Error message text is incorrect");

            test.log(Status.PASS, "Error message displayed correctly for invalid password");
        } catch (Throwable e) {
            test.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 4, description = "Verify login with blank username and password")
    public void testLoginWithBlankCredentials() {
        ExtentTest test = extent.createTest("Blank Credentials Test", "Verify error message with blank username and password");
        ExtentReportManager.setExtentTest(test);

        try {
            test.log(Status.INFO, "Leaving username and password blank");
            test.log(Status.INFO, "Clicking login button");
            loginPage.clickLoginButton();

            test.log(Status.INFO, "Verifying required field error messages are displayed");
            Assert.assertTrue(loginPage.isRequiredFieldMessageDisplayed(), "Required field message is not displayed");

            test.log(Status.PASS, "Required field validation working correctly");
        } catch (Throwable e) {
            test.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 5, description = "Verify login with blank username")
    public void testLoginWithBlankUsername() {
        ExtentTest test = extent.createTest("Blank Username Test", "Verify error message with blank username");
        ExtentReportManager.setExtentTest(test);

        try {
            test.log(Status.INFO, "Leaving username blank");
            test.log(Status.INFO, "Entering password");
            loginPage.enterPassword(ConfigReader.getValidPassword());

            test.log(Status.INFO, "Clicking login button");
            loginPage.clickLoginButton();

            test.log(Status.INFO, "Verifying username required error is displayed");
            Assert.assertTrue(loginPage.isRequiredFieldMessageDisplayed(), "Username required error is not displayed");

            test.log(Status.PASS, "Username required validation working correctly");
        } catch (Throwable e) {
            test.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 6, description = "Verify login with blank password")
    public void testLoginWithBlankPassword() {
        ExtentTest test = extent.createTest("Blank Password Test", "Verify error message with blank password");
        ExtentReportManager.setExtentTest(test);

        try {
            test.log(Status.INFO, "Entering username");
            loginPage.enterUsername(ConfigReader.getValidUsername());

            test.log(Status.INFO, "Leaving password blank");
            test.log(Status.INFO, "Clicking login button");
            loginPage.clickLoginButton();

            test.log(Status.INFO, "Verifying password required error is displayed");
            Assert.assertTrue(loginPage.isRequiredFieldMessageDisplayed(), "Password required error is not displayed");

            test.log(Status.PASS, "Password required validation working correctly");
        } catch (Throwable e) {
            test.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 7, description = "Verify login with special characters in username")
    public void testLoginWithSpecialCharactersInUsername() {
        ExtentTest test = extent.createTest("Special Characters Username Test", "Verify login with special characters in username");
        ExtentReportManager.setExtentTest(test);

        try {
            test.log(Status.INFO, "Entering username with special characters");
            loginPage.enterUsername("Admin@#$%");

            test.log(Status.INFO, "Entering password");
            loginPage.enterPassword(ConfigReader.getValidPassword());

            test.log(Status.INFO, "Clicking login button");
            loginPage.clickLoginButton();

            test.log(Status.INFO, "Verifying error message is displayed");
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed");

            test.log(Status.PASS, "System correctly rejects special characters in username");
        } catch (Throwable e) {
            test.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 8, description = "Verify login with SQL injection attempt")
    public void testLoginWithSQLInjection() {
        ExtentTest test = extent.createTest("SQL Injection Test", "Verify system is protected against SQL injection");
        ExtentReportManager.setExtentTest(test);

        try {
            test.log(Status.INFO, "Entering SQL injection string in username");
            loginPage.enterUsername("' OR '1'='1");

            test.log(Status.INFO, "Entering SQL injection string in password");
            loginPage.enterPassword("' OR '1'='1");

            test.log(Status.INFO, "Clicking login button");
            loginPage.clickLoginButton();

            test.log(Status.INFO, "Verifying system rejects SQL injection attempt");
            Assert.assertFalse(dashboardPage.isLoginSuccessful(), "Security vulnerability: SQL injection succeeded");

            test.log(Status.PASS, "System is protected against SQL injection");
        } catch (Throwable e) {
            test.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 9, description = "Verify login with lowercase username")
    public void testLoginWithLowercaseUsername() {
        ExtentTest test = extent.createTest("Lowercase Username Login Test", "Verify username comparison is case-insensitive");
        ExtentReportManager.setExtentTest(test);

        try {
            test.log(Status.INFO, "Entering username in lowercase");
            loginPage.enterUsername("admin"); // lowercase instead of Admin

            test.log(Status.INFO, "Entering valid password");
            loginPage.enterPassword(ConfigReader.getValidPassword());

            test.log(Status.INFO, "Clicking login button");
            loginPage.clickLoginButton();

            test.log(Status.INFO, "Verifying login is successful with lowercase username");
            Assert.assertTrue(dashboardPage.isLoginSuccessful(),
                    "Lowercase username was not accepted");

            test.log(Status.PASS, "Username matching is case-insensitive as expected");
        } catch (Throwable e) {
            test.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 10, description = "Verify forgot password link is present")
    public void testForgotPasswordLinkPresent() {
        ExtentTest test = extent.createTest("Forgot Password Link Test", "Verify forgot password link is displayed");
        ExtentReportManager.setExtentTest(test);

        try {
            test.log(Status.INFO, "Checking if forgot password link is displayed");
            Assert.assertTrue(loginPage.isForgotPasswordLinkDisplayed(), "Forgot password link is not displayed");

            test.log(Status.PASS, "Forgot password link is present on login page");
        } catch (Throwable e) {
            test.log(Status.FAIL, "Test failed: " + e.getMessage());
            throw e;
        }
    }
}
