#!/bin/bash

echo "=============================================="
echo "🔧 Lombok Compilation Error - Fix Script"
echo "=============================================="
echo ""

echo "Problem: Lombok annotation processor conflict"
echo "Solution: Exclude Lombok + rebuild project"
echo ""

# Step 1: Clean project
echo "Step 1/4: Cleaning project..."
rm -rf target/
echo "✅ Project cleaned"
echo ""

# Step 2: Clean WebDriverManager from Maven cache
echo "Step 2/4: Clearing WebDriverManager cache..."
rm -rf ~/.m2/repository/io/github/bonigarcia/webdrivermanager
echo "✅ Cache cleared"
echo ""

# Step 3: Rebuild project
echo "Step 3/4: Rebuilding project with Lombok exclusion..."
mvn clean install -U -DskipTests

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Build successful!"
    echo ""
else
    echo ""
    echo "❌ Build failed!"
    echo ""
    echo "Try alternative solution:"
    echo "1. Switch to BaseTestWithSeleniumManager in LoginTest.java"
    echo "2. Check LOMBOK_FIX.md for detailed troubleshooting"
    exit 1
fi

# Step 4: Run tests
echo "Step 4/4: Running tests..."
echo "=============================================="
echo ""
mvn test

echo ""
echo "=============================================="
if [ $? -eq 0 ]; then
    echo "✅ SUCCESS! All tests completed."
    echo "📊 Report: test-output/ExtentReport.html"
else
    echo "❌ Tests had failures (check report for details)"
    echo "📊 Report: test-output/ExtentReport.html"
fi
echo "=============================================="
