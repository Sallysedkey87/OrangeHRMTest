package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base Page class that all page objects should extend
 * Contains common methods used across all pages
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    protected WebElement waitForElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForElementToBeInvisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        waitForElementToBeClickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement element = waitForElementToBeVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitForElementToBeVisible(locator).getText();
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Wait for the page to be completely loaded (DOM ready state = complete)
     */
    protected void waitForPageToLoad() {
        try {
            // Wait for the document to be ready, but with a shorter timeout (15s instead of 30s)
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            shortWait.until(driver -> {
                try {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    Object readyState = js.executeScript("return document.readyState");
                    return readyState != null && readyState.toString().equals("complete");
                } catch (Exception ex) {
                    return false;
                }
            });
        } catch (Exception e) {
            // Don't fail the test immediately, let the specific element waits handle failures
            System.out.println("Warning: waitForPageToLoad timed out or failed: " + e.getMessage());
        }
    }

    /**
     * Wait for the page to be loaded and a specific element to be visible
     * @param locator The element locator to wait for
     */
    protected void waitForPageToLoad(By locator) {
        waitForPageToLoad();
        waitForElementToBeVisible(locator);
    }

    /**
     * Wait for jQuery to complete (if jQuery is used on the page)
     */
    protected void waitForJQueryToLoad() {
        wait.until(driver -> {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (Boolean) js.executeScript("return (typeof jQuery !== 'undefined') ? jQuery.active === 0 : true");
        });
    }
}
