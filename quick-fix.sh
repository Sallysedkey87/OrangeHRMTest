#!/bin/bash
# Quick Fix for ChromeDriver Version Error
# Run this script if you get: "This version of ChromeDriver only supports Chrome version XXX"

echo "================================================"
echo "🔧 ChromeDriver Version Error - Quick Fix"
echo "================================================"
echo ""

# Step 1: Clear cache
echo "Step 1/3: Clearing WebDriverManager cache..."
rm -rf ~/.cache/selenium
rm -rf ~/.m2/repository/.cache/wdm
echo "✅ Cache cleared!"
echo ""

# Step 2: Update dependencies
echo "Step 2/3: Updating Maven dependencies..."
mvn clean install -U -DskipTests
if [ $? -ne 0 ]; then
    echo "❌ Failed to update dependencies"
    echo "Try running manually: mvn clean install -U -DskipTests"
    exit 1
fi
echo "✅ Dependencies updated!"
echo ""

# Step 3: Run tests
echo "Step 3/3: Running tests..."
echo "================================================"
echo ""
mvn test

echo ""
echo "================================================"
if [ $? -eq 0 ]; then
    echo "✅ SUCCESS! Tests completed."
    echo "📊 Report: test-output/ExtentReport.html"
else
    echo "❌ Tests failed. Check error messages above."
    echo ""
    echo "Alternative solutions:"
    echo "1. Switch to Firefox: Edit config.properties, set browser=firefox"
    echo "2. Use Selenium Manager: Change LoginTest to extend BaseTestWithSeleniumManager"
    echo "3. Check CHROMEDRIVER_FIX.md for detailed troubleshooting"
fi
echo "================================================"
