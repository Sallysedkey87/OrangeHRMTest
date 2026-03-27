#!/bin/bash

echo "========================================="
echo "OrangeHRM Test Framework - Build Script"
echo "========================================="
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null
then
    echo "❌ ERROR: Maven is not installed!"
    echo "Please install Maven from: https://maven.apache.org/download.cgi"
    echo "Or use Homebrew: brew install maven"
    exit 1
fi

echo "✅ Maven found: $(mvn --version | head -1)"
echo ""

# Clean previous builds
echo "🧹 Cleaning previous builds..."
mvn clean

echo ""
echo "📦 Downloading dependencies and compiling..."
mvn compile

# Check if compilation was successful
if [ $? -eq 0 ]; then
    echo ""
    echo "✅ BUILD SUCCESSFUL!"
    echo ""
    echo "Next steps:"
    echo "  1. Run tests: mvn test"
    echo "  2. View report: open test-output/ExtentReport.html"
else
    echo ""
    echo "❌ BUILD FAILED!"
    echo "Please check the error messages above."
    exit 1
fi
