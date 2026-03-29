package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object Model for OrangeHRM Dashboard Page (After successful login)
 */
public class DashboardPage extends BasePage {

    // Locators
    private By dashboardHeader = By.xpath("//h6[@class='oxd-text oxd-text--h6 oxd-topbar-header-breadcrumb-module']");
    private By userDropdown = By.xpath("//p[@class='oxd-userdropdown-name']");
    private By dashboardMenu = By.xpath("//span[text()='Dashboard']");
    private By sideMenu = By.xpath("//aside[@class='oxd-sidepanel']");

    // Constructor
    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    // Page Actions
    public boolean isDashboardDisplayed() {
        try {
            return waitForElementToBeVisible(dashboardHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getDashboardHeaderText() {
        return getText(dashboardHeader);
    }

    public boolean isUserDropdownDisplayed() {
        try {
            return waitForElementToBeVisible(userDropdown).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSideMenuDisplayed() {
        return isElementDisplayed(sideMenu);
    }

    public boolean isLoginSuccessful() {
        return isDashboardDisplayed() || isUserDropdownDisplayed();
    }
}
