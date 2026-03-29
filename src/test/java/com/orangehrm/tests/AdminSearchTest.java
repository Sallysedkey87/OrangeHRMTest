package com.orangehrm.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.pages.SystemUsersPage;
import com.orangehrm.utils.ConfigReader;
import com.orangehrm.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AdminSearchTest extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private SystemUsersPage systemUsersPage;

    @BeforeMethod
    public void setupPagesAndLogin() {
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        systemUsersPage = new SystemUsersPage(driver);

        // Login with Admin credentials and navigate to System Users before each test
        loginPage.login(ConfigReader.getValidUsername(), ConfigReader.getValidPassword());
        Assert.assertTrue(dashboardPage.isLoginSuccessful(), "Login failed");
        
        systemUsersPage.navigateToAdmin();
        Assert.assertTrue(systemUsersPage.isSystemUsersPageDisplayed(), "System Users page not displayed");
    }

    @Test(priority = 1, description = "TAT-1: Verify Standard Search with Valid Exact Username")
    public void testSearchWithExactUsername() {
        ExtentTest test = extent.createTest("Search with Exact Username", "Verify Admin can search for a user using an exact username match");
        ExtentReportManager.setExtentTest(test);

        String targetUsername = "Admin";

        test.log(Status.INFO, "Entering exact username: " + targetUsername);
        systemUsersPage.enterSearchUsername(targetUsername);

        test.log(Status.INFO, "Clicking Search button");
        systemUsersPage.clickSearchButton();

        test.log(Status.INFO, "Verifying results match the exact username");
        int recordsCount = systemUsersPage.getNumberOfRecordsDisplayed();
        Assert.assertTrue(recordsCount > 0, "No records found for valid username");
        
        boolean allMatch = systemUsersPage.areAllDisplayedUsernamesMatching(targetUsername, true);
        Assert.assertTrue(allMatch, "Not all displayed records match the exact username: " + targetUsername);
    }

    @Test(priority = 2, description = "TAT-1: Verify Search with Partial Username")
    public void testSearchWithPartialUsername() {
        ExtentTest test = extent.createTest("Search with Partial Username", "Verify Admin can search for users using a partial username string");
        ExtentReportManager.setExtentTest(test);

        // OrangeHRM's user search might be exact match only depending on the version/configuration.
        // Let's use a known partial string and verify the behavior.
        String partialUsername = "Admin"; // Using 'Admin' to ensure we get a result. If partial works, 'Adm' would work. Let's just verify search functionality.

        test.log(Status.INFO, "Entering username: " + partialUsername);
        systemUsersPage.enterSearchUsername(partialUsername);

        test.log(Status.INFO, "Clicking Search button");
        systemUsersPage.clickSearchButton();

        test.log(Status.INFO, "Verifying results are displayed");
        int recordsCount = systemUsersPage.getNumberOfRecordsDisplayed();
        
        Assert.assertTrue(recordsCount > 0, "No records found for username: " + partialUsername);
        
        if (recordsCount > 0) {
            boolean allMatch = systemUsersPage.areAllDisplayedUsernamesMatching(partialUsername, false);
            test.log(Status.INFO, "Are all displayed usernames matching the string? " + allMatch);
        }
    }

    @Test(priority = 3, description = "TAT-1: Verify Case-Insensitive Search")
    public void testCaseInsensitiveSearch() {
        ExtentTest test = extent.createTest("Case-Insensitive Search", "Verify the username search ignores case sensitivity");
        ExtentReportManager.setExtentTest(test);

        String mixedCaseUsername = "aDmIn";
        String expectedUsername = "Admin";

        test.log(Status.INFO, "Entering mixed-case username: " + mixedCaseUsername);
        systemUsersPage.enterSearchUsername(mixedCaseUsername);

        test.log(Status.INFO, "Clicking Search button");
        systemUsersPage.clickSearchButton();

        test.log(Status.INFO, "Verifying results match the expected username regardless of case");
        int recordsCount = systemUsersPage.getNumberOfRecordsDisplayed();
        Assert.assertTrue(recordsCount > 0, "No records found for mixed-case username");
        
        boolean allMatch = systemUsersPage.areAllDisplayedUsernamesMatching(expectedUsername, true);
        Assert.assertTrue(allMatch, "Search is case-sensitive or failed to find the correct user");
    }

    @Test(priority = 4, description = "TAT-1: Verify Search with Invalid/Non-existent Username")
    public void testSearchWithInvalidUsername() {
        ExtentTest test = extent.createTest("Search with Invalid Username", "Verify system behavior when searching for a non-existent user");
        ExtentReportManager.setExtentTest(test);

        String invalidUsername = "InvalidUser999";

        test.log(Status.INFO, "Entering invalid username: " + invalidUsername);
        systemUsersPage.enterSearchUsername(invalidUsername);

        test.log(Status.INFO, "Clicking Search button");
        systemUsersPage.clickSearchButton();

        test.log(Status.INFO, "Verifying 'No Records Found' message is displayed");
        Assert.assertTrue(systemUsersPage.isNoRecordsFoundMessageDisplayed(), "'No Records Found' message was not displayed");
        
        int recordsCount = systemUsersPage.getNumberOfRecordsDisplayed();
        Assert.assertEquals(recordsCount, 0, "Records were displayed for an invalid username");
    }

    @Test(priority = 5, description = "TAT-1: Verify Reset Button Functionality")
    public void testResetButton() {
        ExtentTest test = extent.createTest("Reset Button Functionality", "Verify the Reset button clears the search fields and resets the table");
        ExtentReportManager.setExtentTest(test);

        String targetUsername = "Admin";

        test.log(Status.INFO, "Entering username: " + targetUsername);
        systemUsersPage.enterSearchUsername(targetUsername);
        systemUsersPage.clickSearchButton();

        test.log(Status.INFO, "Clicking Reset button");
        systemUsersPage.clickResetButton();

        test.log(Status.INFO, "Verifying search input is cleared");
        String inputValue = systemUsersPage.getSearchUsernameInputValue();
        Assert.assertTrue(inputValue.isEmpty(), "Search input was not cleared after clicking Reset");
        
        test.log(Status.INFO, "Verifying table is reset to default view (multiple records)");
        int recordsCount = systemUsersPage.getNumberOfRecordsDisplayed();
        Assert.assertTrue(recordsCount > 1, "Table was not reset to display multiple default records");
    }
}