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
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--window-size=1920,1080");
                    // Keep headless config minimal to avoid Chromium crashes
                    chromeOptions.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.NORMAL);
                } else {
                    // Set page load strategy to reduce timeout
                    chromeOptions.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.NORMAL);
                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--window-size=1920,1080");
                }

                // Stability and performance settings
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--remote-allow-origins=*");

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
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless=new");
                    edgeOptions.addArguments("--no-sandbox");
                    edgeOptions.addArguments("--disable-dev-shm-usage");
                    edgeOptions.addArguments("--window-size=1920,1080");
                }
                edgeOptions.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.NORMAL);
                try {
                    // Prefer local/runner-provided driver first (avoids network dependency in CI)
                    driver = new EdgeDriver(edgeOptions);
                } catch (Exception e) {
                    // Fallback for local machines where driver is not preinstalled
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver(edgeOptions);
                }
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
        try {
            driver.get(ConfigReader.getAppUrl());
        } catch (Exception e) {
            System.out.println("Warning: Initial navigation failed or timed out: " + e.getMessage());
        }
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        // Capture screenshot on failure
        if (result.getStatus() == ITestResult.FAILURE && ConfigReader.isScreenshotOnFailure()) {
            try {
                String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());
                if (screenshotPath != null && ExtentReportManager.getExtentTest() != null) {
                    try {
                        ExtentReportManager.getExtentTest().fail("Screenshot on failure")
                            .addScreenCaptureFromPath(screenshotPath);
                    } catch (Exception e) {
                        System.out.println("Failed to attach screenshot to report: " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                System.out.println("Failed to capture screenshot (window may be closed): " + e.getMessage());
            }
        }

        // Close browser
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.out.println("Failed to quit driver cleanly: " + e.getMessage());
            }
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
