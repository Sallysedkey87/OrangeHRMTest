package com.orangehrm.base;

import com.aventstack.extentreports.ExtentReports;
import com.orangehrm.utils.ConfigReader;
import com.orangehrm.utils.ExtentReportManager;
import com.orangehrm.utils.ScreenshotUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
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
 * Base Test class that all test classes should extend
 * Handles WebDriver initialization, configuration, and teardown
 */
public class BaseTest {
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
                WebDriverManager.chromedriver().clearDriverCache().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    // In headless mode, EAGER strategy prevents timeouts waiting for external resources
                    chromeOptions.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.EAGER);
                } else {
                    // Set page load strategy to reduce timeout
                    chromeOptions.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.NORMAL);
                }

                // Window and display settings
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--window-size=1920,1080");

                // Stability and performance settings
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--remote-allow-origins=*");

                // Fix renderer timeout issues
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--disable-infobars");
                chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                chromeOptions.addArguments("--disable-browser-side-navigation");

                // Performance improvements
                chromeOptions.addArguments("--disable-web-security");
                chromeOptions.addArguments("--allow-insecure-localhost");
                chromeOptions.addArguments("--ignore-certificate-errors");

                // Increase timeout for renderer
                chromeOptions.addArguments("--timeout=60000");

                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
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
        System.out.println("========================================");
        System.out.println("Flushing ExtentReports...");
        if (extent != null) {
            extent.flush();
            System.out.println("ExtentReports flushed successfully!");
            System.out.println("Report location: " + ConfigReader.getReportPath());
        } else {
            System.out.println("WARNING: extent is null, report not flushed!");
        }
        System.out.println("========================================");
    }
}
