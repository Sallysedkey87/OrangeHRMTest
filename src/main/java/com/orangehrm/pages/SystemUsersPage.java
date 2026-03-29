package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SystemUsersPage extends BasePage {

    // Locators
    private By adminMenu = By.xpath("//span[text()='Admin']/parent::a");
    private By addButton = By.xpath("//button[normalize-space()='Add']");
    private By systemUsersHeader = By.xpath("//h5[text()='System Users']");
    private By successToast = By.xpath("//div[@id='oxd-toaster_1']//p[contains(@class, 'oxd-text--toast-message')]");
    
    // Search Locators
    private By searchUsernameInput = By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input");
    private By searchButton = By.xpath("//button[@type='submit' and normalize-space()='Search']");
    private By resetButton = By.xpath("//button[@type='button' and normalize-space()='Reset']");
    private By recordsFoundText = By.xpath("//div[contains(@class, 'orangehrm-horizontal-padding')]//span[contains(text(), 'Record')]");
    private By noRecordsFoundText = By.xpath("//div[contains(@class, 'orangehrm-horizontal-padding')]//span[contains(text(), 'No Records Found')] | //span[text()='No Records Found']");
    private By userTableRows = By.xpath("//div[@class='oxd-table-body']/div[contains(@class, 'oxd-table-card')]");
    private By usernameColumnInTable = By.xpath(".//div[@role='cell'][2]/div");

    public SystemUsersPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToAdmin() {
        click(adminMenu);
        waitForPageToLoad();
    }

    public boolean isSystemUsersPageDisplayed() {
        try {
            return waitForElementToBeVisible(systemUsersHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickAddButton() {
        click(addButton);
        waitForPageToLoad();
    }

    public boolean isSuccessToastDisplayed() {
        try {
            return waitForElementToBeVisible(successToast).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getSuccessToastMessage() {
        return getText(successToast);
    }

    // Search Methods
    public void enterSearchUsername(String username) {
        type(searchUsernameInput, username);
    }

    public void clickSearchButton() {
        click(searchButton);
        // Wait for the table to reload or show "No Records Found"
        try {
            Thread.sleep(4000); // Increased static wait as OrangeHRM uses React state updates that are hard to catch with explicit waits alone
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickResetButton() {
        click(resetButton);
        try {
            Thread.sleep(3000); // Increased static wait
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getSearchUsernameInputValue() {
        return waitForElementToBeVisible(searchUsernameInput).getAttribute("value");
    }

    public boolean isNoRecordsFoundMessageDisplayed() {
        try {
            return waitForElementToBeVisible(noRecordsFoundText).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getNumberOfRecordsDisplayed() {
        try {
            // Wait for at least one row to be visible or the no records message to be visible
            try {
                wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(userTableRows),
                    ExpectedConditions.visibilityOfElementLocated(noRecordsFoundText)
                ));
            } catch (Exception e) {
                // Ignore timeout, just count what's there
            }
            return driver.findElements(userTableRows).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean areAllDisplayedUsernamesMatching(String expectedUsername, boolean exactMatch) {
        int rowCount = getNumberOfRecordsDisplayed();
        if (rowCount == 0) return false;

        java.util.List<WebElement> rows = driver.findElements(userTableRows);
        for (WebElement row : rows) {
            String actualUsername = row.findElement(usernameColumnInTable).getText().trim();
            if (exactMatch) {
                if (!actualUsername.equalsIgnoreCase(expectedUsername)) {
                    return false;
                }
            } else {
                if (!actualUsername.toLowerCase().contains(expectedUsername.toLowerCase())) {
                    return false;
                }
            }
        }
        return true;
    }
}
