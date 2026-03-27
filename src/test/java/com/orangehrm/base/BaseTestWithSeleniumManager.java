package com.orangehrm.base;

import com.aventstack.extentreports.ExtentReports;
import com.orangehrm.utils.ConfigReader;
import com.orangehrm.utils.ExtentReportManager;
import com.orangehrm.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.Duration;

/**
 * Alternative Base Test class using Selenium Manager (no WebDriverManager dependency)
 * Selenium 4.6+ includes built-in driver management
 */
public class BaseTestWithSeleniumManager {
    protected WebDriver driver;
    protected static ExtentReports extent;

    @BeforeSuite
    public void setupReport() {
        extent = ExtentReportManager.createInstance();
    }

    @BeforeMethod
    public void setup() {
        String browser = ConfigReader.getBrowser().toLowerCase();
        boolean headless = ConfigReader.isHeadless();

        switch (browser) {
            case "chrome":
                // Selenium Manager will automatically download the correct ChromeDriver
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                }
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless");
                }
                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        // Set timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getPageLoadTimeout()));

        // Maximize window if not headless
        if (!headless) {
            driver.manage().window().maximize();
        }

        // Navigate to application URL
        driver.get(ConfigReader.getAppUrl());
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        // Capture screenshot on failure
        if (result.getStatus() == ITestResult.FAILURE && ConfigReader.isScreenshotOnFailure()) {
            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());
            if (screenshotPath != null && ExtentReportManager.getExtentTest() != null) {
                try {
                    ExtentReportManager.getExtentTest().fail("Screenshot on failure")
                        .addScreenCaptureFromPath(screenshotPath);
                } catch (Exception e) {
                    System.out.println("Failed to attach screenshot to report: " + e.getMessage());
                }
            }
        }

        // Close browser
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
