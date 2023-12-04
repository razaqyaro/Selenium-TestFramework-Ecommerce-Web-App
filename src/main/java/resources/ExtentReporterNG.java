package resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG
{
    public static ExtentReports getReportObject()
    {
// ExtendReports , ExtendSparkReporter
        String path = System.getProperty("user.dir")+"\\reports\\index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Web automation Results");
        reporter.config().setDocumentTitle("RAZZY Test Results");

        ExtentReports extentReport = new ExtentReports();
        extentReport = new ExtentReports();
        extentReport.attachReporter(reporter);
        extentReport.setSystemInfo("Tester", "Abdul-Razak");

        return extentReport;
    }
}
