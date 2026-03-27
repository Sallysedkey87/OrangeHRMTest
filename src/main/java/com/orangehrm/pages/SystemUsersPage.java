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

    public SystemUsersPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToAdmin() {
        click(adminMenu);
        waitForPageToLoad();
    }

    public boolean isSystemUsersPageDisplayed() {
        return isElementDisplayed(systemUsersHeader);
    }

    public void clickAddButton() {
        click(addButton);
        waitForPageToLoad();
    }

    public boolean isSuccessToastDisplayed() {
        return isElementDisplayed(successToast);
    }
    
    public String getSuccessToastMessage() {
        return getText(successToast);
    }
}
