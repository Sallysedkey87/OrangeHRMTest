# Quick Commands to Generate ExtentReport

## Run All Tests and Generate Report
```bash
cd /Users/sallysedky/Downloads/OrangeHRMTest
mvn clean test
open test-output/ExtentReport.html
```

## Run Single Test (Faster)
```bash
cd /Users/sallysedky/Downloads/OrangeHRMTest
mvn test -Dtest=LoginTest#testLoginWithValidCredentials
open test-output/ExtentReport.html
```

## Use the Automated Script
```bash
cd /Users/sallysedky/Downloads/OrangeHRMTest
./test-report.sh
```

## Check if Report Exists
```bash
ls -la /Users/sallysedky/Downloads/OrangeHRMTest/test-output/ExtentReport.html
```

## View Report Location
```bash
echo "Report is at: $(pwd)/test-output/ExtentReport.html"
```
