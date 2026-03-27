package com.orangehrm.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

/**
 * Utility class for ExtentReports configuration and management
 */
public class ExtentReportManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static ExtentReports createInstance() {
        String reportPath = ConfigReader.getReportPath();

        System.out.println("========================================");
        System.out.println("Creating ExtentReports instance");
        System.out.println("Report Path: " + reportPath);

        // Create report directory if it doesn't exist
        File reportFile = new File(reportPath);
        File reportDir = reportFile.getParentFile();

        if (reportDir != null && !reportDir.exists()) {
            boolean created = reportDir.mkdirs();
            System.out.println("Report directory created: " + reportDir.getAbsolutePath() + " - Success: " + created);
        } else if (reportDir != null) {
            System.out.println("Report directory already exists: " + reportDir.getAbsolutePath());
        }

        System.out.println("Full report file path: " + reportFile.getAbsolutePath());

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

        sparkReporter.config().setDocumentTitle("OrangeHRM Test Automation Report");
        sparkReporter.config().setReportName("Login Test Cases Report");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setTimeStampFormat("dd-MM-yyyy HH:mm:ss");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "OrangeHRM");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tester", "Automation Team");
        extent.setSystemInfo("Browser", ConfigReader.getBrowser());
        extent.setSystemInfo("OS", System.getProperty("os.name"));

        System.out.println("ExtentReports instance created successfully");
        System.out.println("========================================");

        return extent;
    }

    public static ExtentReports getExtent() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    public static void setExtentTest(ExtentTest test) {
        extentTest.set(test);
    }

    public static ExtentTest getExtentTest() {
        return extentTest.get();
    }

    public static void removeExtentTest() {
        extentTest.remove();
    }
}
