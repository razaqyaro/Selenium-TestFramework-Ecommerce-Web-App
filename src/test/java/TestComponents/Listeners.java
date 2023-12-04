package TestComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import resources.ExtentReporterNG;

import java.io.IOException;

public class Listeners extends BaseTest implements ITestListener
{
    ExtentReports extent = ExtentReporterNG.getReportObject();
    ExtentTest test;
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
    public void onTestStart(ITestResult result)
    {
       test = extent.createTest(result.getMethod().getMethodName());
       extentTest.set(test);  // unique thread ID
    }

    public void onTestSuccess(ITestResult result)
    {
        test.log(Status.PASS, "Test Passed");
    }

    public void onTestFailure(ITestResult result)
    {
        extentTest.get().fail(result.getThrowable());
        test.fail(result.getThrowable());
        try
        {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        String filePath = null;
        try
        {
             filePath = getScreenshot(result.getMethod().getMethodName(), driver);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
       test.addScreenCaptureFromBase64String(filePath, result.getMethod().getMethodName());
    }

    public void onTestSkipped(ITestResult result) {
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onTestFailedWithTimeout(ITestResult result) {
        this.onTestFailure(result);
    }

    public void onStart(ITestContext context) {
    }

    public void onFinish(ITestContext context)
    {
        extent.flush();
    }
}
