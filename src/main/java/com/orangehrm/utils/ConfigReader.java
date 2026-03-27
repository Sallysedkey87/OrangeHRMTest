package com.orangehrm.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class to read configuration properties from config.properties file
 */
public class ConfigReader {
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH);
            properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config.properties file");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getAppUrl() {
        return properties.getProperty("app.url");
    }

    public static String getBrowser() {
        return properties.getProperty("browser");
    }

    public static boolean isHeadless() {
        String headlessEnv = System.getenv("HEADLESS");
        if (headlessEnv != null && !headlessEnv.isBlank()) {
            return Boolean.parseBoolean(headlessEnv);
        }
        if ("true".equalsIgnoreCase(System.getenv("CI"))) {
            return true;
        }
        return Boolean.parseBoolean(properties.getProperty("headless"));
    }

    public static int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicit.wait"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicit.wait"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(properties.getProperty("page.load.timeout"));
    }

    public static String getValidUsername() {
        return properties.getProperty("valid.username");
    }

    public static String getValidPassword() {
        return properties.getProperty("valid.password");
    }

    public static String getScreenshotPath() {
        return properties.getProperty("screenshot.path");
    }

    public static boolean isScreenshotOnFailure() {
        return Boolean.parseBoolean(properties.getProperty("screenshot.on.failure"));
    }

    public static String getReportPath() {
        return properties.getProperty("report.path");
    }
}
