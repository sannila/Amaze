package utils;

import org.testng.*;

public class Listener implements ITestListener, ISuiteListener {
    @Override
    public void onStart(ISuite iSuite) {
        System.out.println("Name of the suite starts: " + iSuite.getName());
    }

    @Override
    public void onFinish(ISuite iSuite) {
        System.out.println("Name of the suite Finished: " + iSuite.getName());
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
}
