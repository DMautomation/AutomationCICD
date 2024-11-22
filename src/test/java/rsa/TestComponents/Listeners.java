package rsa.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import rsa.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {
	ExtentTest test;
	ExtentReports extent = ExtentReporterNG.getReportObject();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	
	
	@Override
	public void onTestStart(ITestResult result) {
		// System.out.println("Test Started: " + result.getMethod().getMethodName());
		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);//Unique Thread Id (Error ValidationTest()-->test)
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// System.out.println("Test Passed: " + result.getName());
		//test.log(Status.PASS, "Test Passed"); //Replace by below line to implement ThreadSafe
		extentTest.get().log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// System.out.println("Test Failed: " + result.getName());
		test.log(Status.FAIL, "Test Failed");
		//test.fail(result.getThrowable()); //Replace by below line to implement ThreadSafe
		extentTest.get().fail(result.getThrowable());//
		
		//***IMPORTANT-- to have access to driver of the current class field 
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
					.get(result.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ScreenShot-->Attach it to the report
		String filePath = null;
		try {
			filePath = getScreenshot(result.getMethod().getMethodName(), driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//test.addScreenCaptureFromPath(filePath, result.getMethod().getMethodName()); //Replace by below line to implement ThreadSafe
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// System.out.println("Test Skipped: " + result.getName());

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// System.out.println("Test Failed within success percentage: " +
		// result.getName());

	}

	@Override
	public void onStart(ITestContext context) {
		// System.out.println("Test Suite Started: " + context.getName());

	}

	@Override
	public void onFinish(ITestContext context) {
		// System.out.println("Test Suite Finished: " + context.getName());
		extent.flush();
	}

}
