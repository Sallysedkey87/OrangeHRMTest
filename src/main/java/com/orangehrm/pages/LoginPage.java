package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object Model for OrangeHRM Login Page
 * Contains all elements and actions related to the login page
 */
public class LoginPage extends BasePage {

    // Locators
    private By usernameField = By.name("username");
    private By passwordField = By.name("password");
    private By loginButton = By.xpath("//button[@type='submit']");
    private By errorMessage = By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']");
    private By requiredFieldMessage = By.xpath("//span[@class='oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message']");
    private By loginPanel = By.xpath("//div[@class='orangehrm-login-branding']");
    private By forgotPasswordLink = By.xpath("//p[@class='oxd-text oxd-text--p orangehrm-login-forgot-header']");
    private By usernameRequiredError = By.xpath("(//span[@class='oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message'])[1]");
    private By passwordRequiredError = By.xpath("(//span[@class='oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message'])[2]");

    // Constructor
    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
        // Wait for username input instead of full document readiness
        // because third-party resources can delay readyState in CI/headless.
        try {
            waitForElementToBeVisible(usernameField);
        } catch (Exception e) {
            System.out.println("Warning: Login panel not immediately visible: " + e.getMessage());
        }
    }

    // Page Actions
    public void enterUsername(String username) {
        type(usernameField, username);
    }

    public void enterPassword(String password) {
        type(passwordField, password);
    }

    public void clickLoginButton() {
        click(loginButton);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isRequiredFieldMessageDisplayed() {
        return isElementDisplayed(requiredFieldMessage);
    }

    public String getRequiredFieldMessage() {
        return getText(requiredFieldMessage);
    }

    public boolean isUsernameRequiredErrorDisplayed() {
        return isElementDisplayed(usernameRequiredError);
    }

    public boolean isPasswordRequiredErrorDisplayed() {
        return isElementDisplayed(passwordRequiredError);
    }

    public String getUsernameRequiredError() {
        return getText(usernameRequiredError);
    }

    public String getPasswordRequiredError() {
        return getText(passwordRequiredError);
    }

    public boolean isLoginPageDisplayed() {
        return isElementDisplayed(loginPanel);
    }

    public boolean isForgotPasswordLinkDisplayed() {
        return isElementDisplayed(forgotPasswordLink);
    }

    public void clearUsername() {
        driver.findElement(usernameField).clear();
    }

    public void clearPassword() {
        driver.findElement(passwordField).clear();
    }
}
