package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddUserPage extends BasePage {

    // Locators
    private By addUserHeader = By.xpath("//h6[text()='Add User']");
    
    // Form fields
    private By userRoleDropdown = By.xpath("//label[text()='User Role']/../following-sibling::div//div[@class='oxd-select-text-input']");
    private By employeeNameInput = By.xpath("//label[text()='Employee Name']/../following-sibling::div//input");
    private By employeeNameDropdownOption = By.xpath("//div[@role='listbox']//span");
    private By statusDropdown = By.xpath("//label[text()='Status']/../following-sibling::div//div[@class='oxd-select-text-input']");
    private By usernameInput = By.xpath("//label[text()='Username']/../following-sibling::div//input");
    private By passwordInput = By.xpath("//label[text()='Password']/../following-sibling::div//input");
    private By confirmPasswordInput = By.xpath("//label[text()='Confirm Password']/../following-sibling::div//input");
    
    // Buttons
    private By saveButton = By.xpath("//button[@type='submit']");
    private By cancelButton = By.xpath("//button[normalize-space()='Cancel']");
    
    // Error messages
    private By usernameError = By.xpath("//label[text()='Username']/ancestor::div[contains(@class, 'oxd-input-group')]//span[contains(@class, 'oxd-input-field-error-message')]");
    private By passwordError = By.xpath("//label[text()='Password']/ancestor::div[contains(@class, 'oxd-input-group')]//span[contains(@class, 'oxd-input-field-error-message')]");
    private By confirmPasswordError = By.xpath("//label[text()='Confirm Password']/ancestor::div[contains(@class, 'oxd-input-group')]//span[contains(@class, 'oxd-input-field-error-message')]");
    private By employeeNameError = By.xpath("//label[text()='Employee Name']/ancestor::div[contains(@class, 'oxd-input-group')]//span[contains(@class, 'oxd-input-field-error-message')]");

    public AddUserPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAddUserPageDisplayed() {
        return isElementDisplayed(addUserHeader);
    }

    public void selectUserRole(String role) {
        click(userRoleDropdown);
        By option = By.xpath("//div[@role='listbox']//span[text()='" + role + "']");
        click(option);
    }

    public void enterEmployeeName(String employeeName) {
        type(employeeNameInput, employeeName);
        waitForElementToBeVisible(employeeNameDropdownOption);
        click(employeeNameDropdownOption); // Selects the first matching option
    }
    
    public void enterEmployeeNameWithoutSelection(String employeeName) {
        type(employeeNameInput, employeeName);
    }

    public void selectStatus(String status) {
        click(statusDropdown);
        By option = By.xpath("//div[@role='listbox']//span[text()='" + status + "']");
        click(option);
    }

    public void enterUsername(String username) {
        type(usernameInput, username);
    }

    public void enterPassword(String password) {
        type(passwordInput, password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        type(confirmPasswordInput, confirmPassword);
    }

    public void clickSave() {
        click(saveButton);
        waitForPageToLoad();
    }

    public void clickCancel() {
        click(cancelButton);
        waitForPageToLoad();
    }
    
    public String getUsernameError() {
        return getText(usernameError);
    }
    
    public String getPasswordError() {
        // Wait briefly for validation to trigger
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        return getText(passwordError);
    }
    
    public String getConfirmPasswordError() {
        // Wait briefly for validation to trigger
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        return getText(confirmPasswordError);
    }
    
    public String getEmployeeNameError() {
        // Wait briefly for validation to trigger
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        return getText(employeeNameError);
    }
    
    public boolean isPasswordMasked() {
        WebElement pwdField = driver.findElement(passwordInput);
        return pwdField.getAttribute("type").equals("password");
    }
}
