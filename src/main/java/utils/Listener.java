package utils;

import org.testng.*;

public class Listener implements ITestListener, ISuiteListener {
    @Override
    public void onStart(ISuite iSuite) {
        System.out.println("* * * * * * * * * * * * Name of the suite starts: " + iSuite.getName() + " * * * * * * * * * * * * ");
    }

    @Override
    public void onFinish(ISuite iSuite) {
        System.out.println("* * * * * * * * Name of the suite Finished: " + iSuite.getName() + " * * * * * * * * ");
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("* * * * * * * * Name of the test start: "+ iTestResult.getName() + " * * * * * * * * ");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("* * * * * * * * Name of the test success: "+ iTestResult.getName() + " * * * * * * * * ");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("* * * * * * * * Name of the test failure: "+ iTestResult.getName());
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("* * * * * * * * Name of the test skipped: "+ iTestResult.getName() + " * * * * * * * *  ");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("* * * * On start: "+ iTestContext.getName() + " * * * * ");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("* * * * On Finish: "+ iTestContext.getName() + " * * * * ");
    }
}
