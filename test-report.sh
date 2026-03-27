#!/bin/bash

echo "=============================================="
echo "🧪 Test Report Generation Script"
echo "=============================================="
echo ""

cd /Users/sallysedky/Downloads/OrangeHRMTest

# Clean previous reports
echo "Step 1: Cleaning old reports..."
rm -rf test-output/
rm -rf screenshots/
echo "✅ Cleaned"
echo ""

# Compile
echo "Step 2: Compiling project..."
mvn clean compile -DskipTests
if [ $? -ne 0 ]; then
    echo "❌ Compilation failed!"
    exit 1
fi
echo "✅ Compiled"
echo ""

# Run a single test
echo "Step 3: Running test (this will open Chrome)..."
echo "=============================================="
mvn test -Dtest=LoginTest#testLoginWithValidCredentials
TEST_RESULT=$?
echo "=============================================="
echo ""

# Check if report was created
echo "Step 4: Checking for report..."
if [ -f "test-output/ExtentReport.html" ]; then
    echo "✅ SUCCESS! Report found at:"
    echo "   $(pwd)/test-output/ExtentReport.html"
    echo ""
    echo "📊 Opening report in browser..."
    open test-output/ExtentReport.html
else
    echo "❌ Report not found!"
    echo ""
    echo "Checking what files were created:"
    ls -la test-output/ 2>/dev/null || echo "test-output directory does not exist"
    echo ""
    echo "Checking for any HTML files:"
    find . -name "*.html" -type f 2>/dev/null || echo "No HTML files found"
fi

echo ""
echo "=============================================="
if [ $TEST_RESULT -eq 0 ]; then
    echo "✅ Test completed successfully"
else
    echo "⚠️  Test had issues (check logs above)"
fi
echo "=============================================="
