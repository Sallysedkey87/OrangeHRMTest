package com.orangehrm.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.AddUserPage;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.SystemUsersPage;
import com.orangehrm.utils.ConfigReader;
import com.orangehrm.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

public class AdminAddUserTest extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private SystemUsersPage systemUsersPage;
    private AddUserPage addUserPage;

    @BeforeMethod
    public void setupPagesAndLogin() {
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        systemUsersPage = new SystemUsersPage(driver);
        addUserPage = new AddUserPage(driver);

        // Login with Admin credentials before each test
        loginPage.login(ConfigReader.getValidUsername(), ConfigReader.getValidPassword());
        Assert.assertTrue(dashboardPage.isLoginSuccessful(), "Login failed");
    }

    @Test(priority = 1, description = "Verify navigation to Add User page")
    public void testNavigateToAddUserPage() {
        ExtentTest test = extent.createTest("Navigate to Add User Page", "Verify Admin can navigate to Add User screen");
        ExtentReportManager.setExtentTest(test);

        test.log(Status.INFO, "Navigating to Admin > System Users");
        systemUsersPage.navigateToAdmin();
        Assert.assertTrue(systemUsersPage.isSystemUsersPageDisplayed(), "System Users page not displayed");

        test.log(Status.INFO, "Clicking Add button");
        systemUsersPage.clickAddButton();
        
        test.log(Status.INFO, "Verifying Add User page is displayed");
        Assert.assertTrue(addUserPage.isAddUserPageDisplayed(), "Add User page not displayed");
    }

    @Test(priority = 2, description = "Verify Add User with valid data")
    public void testAddUserWithValidData() {
        ExtentTest test = extent.createTest("Add User with Valid Data", "Verify Admin can create a new user successfully");
        ExtentReportManager.setExtentTest(test);

        systemUsersPage.navigateToAdmin();
        systemUsersPage.clickAddButton();

        String uniqueUsername = "testuser_" + UUID.randomUUID().toString().substring(0, 8);
        
        test.log(Status.INFO, "Filling Add User form");
        addUserPage.selectUserRole("ESS");
        addUserPage.enterEmployeeName("a"); // Assuming 'a' will return some results in demo site
        addUserPage.selectStatus("Enabled");
        addUserPage.enterUsername(uniqueUsername);
        addUserPage.enterPassword("Password@123");
        addUserPage.enterConfirmPassword("Password@123");

        test.log(Status.INFO, "Clicking Save button");
        addUserPage.clickSave();

        test.log(Status.INFO, "Verifying success message and redirection");
        Assert.assertTrue(systemUsersPage.isSuccessToastDisplayed(), "Success toast message not displayed");
        Assert.assertTrue(systemUsersPage.isSystemUsersPageDisplayed(), "Not redirected to System Users page");
    }

    @Test(priority = 3, description = "Verify validation for invalid employee name")
    public void testInvalidEmployeeNameValidation() {
        ExtentTest test = extent.createTest("Invalid Employee Name Validation", "Verify error when employee name does not exist");
        ExtentReportManager.setExtentTest(test);

        systemUsersPage.navigateToAdmin();
        systemUsersPage.clickAddButton();

        test.log(Status.INFO, "Entering invalid employee name");
        addUserPage.enterEmployeeNameWithoutSelection("InvalidEmployeeName123");
        
        test.log(Status.INFO, "Clicking Save to trigger validation");
        addUserPage.clickSave();

        test.log(Status.INFO, "Verifying error message");
        String error = addUserPage.getEmployeeNameError();
        Assert.assertEquals(error, "Invalid", "Employee name validation error incorrect");
    }

    @Test(priority = 4, description = "Verify password complexity and match validation")
    public void testPasswordValidation() {
        ExtentTest test = extent.createTest("Password Validation", "Verify password complexity and confirm password match");
        ExtentReportManager.setExtentTest(test);

        systemUsersPage.navigateToAdmin();
        systemUsersPage.clickAddButton();

        test.log(Status.INFO, "Entering simple password to test complexity");
        addUserPage.enterPassword("123");
        
        String pwdError = addUserPage.getPasswordError();
        Assert.assertTrue(pwdError.contains("Should have at least"), "Password complexity error not displayed");

        test.log(Status.INFO, "Entering mismatched confirm password");
        addUserPage.enterPassword("ValidPassword@123");
        addUserPage.enterConfirmPassword("DifferentPassword@123");
        
        String confirmError = addUserPage.getConfirmPasswordError();
        Assert.assertEquals(confirmError, "Passwords do not match", "Password match error incorrect");
    }

    @Test(priority = 5, description = "Verify Cancel action discards changes")
    public void testCancelAction() {
        ExtentTest test = extent.createTest("Cancel Action", "Verify clicking Cancel returns to System Users list without saving");
        ExtentReportManager.setExtentTest(test);

        systemUsersPage.navigateToAdmin();
        systemUsersPage.clickAddButton();

        test.log(Status.INFO, "Entering some data");
        addUserPage.enterUsername("cancelleduser");

        test.log(Status.INFO, "Clicking Cancel button");
        addUserPage.clickCancel();

        test.log(Status.INFO, "Verifying redirection to System Users page");
        Assert.assertTrue(systemUsersPage.isSystemUsersPageDisplayed(), "Not redirected to System Users page after cancel");
    }
    
    @Test(priority = 6, description = "Verify sensitive data is masked")
    public void testPasswordMasking() {
        ExtentTest test = extent.createTest("Password Masking", "Verify password fields are masked");
        ExtentReportManager.setExtentTest(test);

        systemUsersPage.navigateToAdmin();
        systemUsersPage.clickAddButton();

        test.log(Status.INFO, "Verifying password field type is 'password'");
        Assert.assertTrue(addUserPage.isPasswordMasked(), "Password field is not masked");
    }
}
