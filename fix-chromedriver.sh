#!/bin/bash

echo "========================================="
echo "ChromeDriver Version Fix Script"
echo "========================================="
echo ""

# Clear WebDriverManager cache
echo "🧹 Clearing WebDriverManager cache..."
rm -rf ~/.cache/selenium
rm -rf ~/.m2/repository/.cache/wdm

echo "✅ Cache cleared!"
echo ""

# Check Chrome version
echo "🔍 Checking your Chrome version..."
if [ -d "/Applications/Google Chrome.app" ]; then
    CHROME_VERSION=$(/Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome --version 2>/dev/null)
    echo "Chrome installed: $CHROME_VERSION"
else
    echo "⚠️  Chrome not found in default location"
fi

echo ""
echo "📦 Updating Maven dependencies..."
mvn clean install -DskipTests -U

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Dependencies updated successfully!"
    echo ""
    echo "🚀 Now run your tests with: mvn test"
else
    echo ""
    echo "❌ Failed to update dependencies"
    exit 1
fi
