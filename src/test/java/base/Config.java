package base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.Log;
import utils.POSData;
import utils.Screenshot;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Listeners(utils.Listener.class)
public class Config {
    public static WebDriver driver;
    public static Screenshot clickImage = new Screenshot();
    public static String customer_name;
    public static Logger log = Logger.getLogger(Config.class);
    public static POSData posData = new POSData();

    /**
     * this method will run once the first
     */
    @BeforeSuite(alwaysRun = true)
    @Parameters({"browser", "url"})
    public void openDriver(String browser, String url) {
        System.out.println("User Name: " + posData.getSheet("Login", 1, 0));
        PropertyConfigurator.configure("src/main/resources/log4j.properties");

        startTestCase("Amaze Test Suite", dateTime());

        String filePath = "src\\FailureScreenshot";
        File file = new File(filePath);
        deleteDir(file);
        file.delete();

        selectBrowser(browser);

        /**
         * this will wait for 30 seconds to find the element
         */
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        /**
         * get the url from testng.xml parameter
         */
        driver.get(url);

        /**
         * maximize the browser size
         */
        driver.manage().window().maximize();
    }


    /**
     * AfterSuite will run once at last
     */
    @AfterSuite(alwaysRun = true)
    public void closeDriver() {
        /**
         * this will close the active WebDriver instance
         */

//        driver.close();

        /**
         * this will close all the active WebDriver instance
         */
        driver.quit();
        endTestCase("Amaze Test Suite");
    }


    /**
     * this will run on every method of test
     * if test case is failure it takes a screenshot
     */
//    @AfterMethod(alwaysRun = true)
    public void failureScreenshot(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            log.warn("Test status: " + result.getStatus());
            log.info("Check the failure screenshot in: src\\FailureScreenshot");
            String methodName = result.getName();
            String className = result.getTestClass().getRealClass().getSimpleName();
            clickImage.takeScreenshot(driver, className, methodName, dateTime());
//            System.out.println("Class Name: " + className + "\n" + "Method Name: " + methodName + "\nDate: " + dateTime());
        }
    }


    public String dateTime() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy_MM_dd HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        String custom_name = df.format(now);
        return custom_name;
    }

    /**
     * This function gets the browser name from testng.xml and opens the desired browser
     */
    public void selectBrowser(String browser) {
        info(dateTime(), ("Getting the " + browser + " browser from xml"));
        switch (browser) {
            case "chrome":
                try {
//                    System.setProperty("webdriver.chrome.driver", "D:\\AmazeWorkSpace\\Selenium\\WebDrivers\\chrome\\chromedriver.exe");
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    info(dateTime(), "open the chrome driver");
                } catch (Exception e) {
                    fatal(dateTime(), ("Exception while opening chrome driver: " + e.getMessage()));
                }
                break;
            case "firefox":
                try {
//                    System.setProperty("webdriver.gecko.driver", "D:\\AmazeWorkSpace\\Selenium\\WebDrivers\\firefox\\geckodriver.exe");
                    WebDriverManager.firefoxdriver().browserVersion("94.0.2").setup();
                    driver = new FirefoxDriver();
                    info(dateTime(), "open the firefox driver");
                } catch (Exception e) {
                    fatal(dateTime(), ("Exception while opening firefox driver: " + e.getMessage()));
                }
                break;
            case "safari":
                try {
                    WebDriverManager.safaridriver().setup();
                    driver = new SafariDriver();
                    info(dateTime(), "open the Safari driver");
                } catch (Exception e) {
                    fatal(dateTime(), ("Exception while opening Safari driver: " + e.getMessage()));
                }
            default:
                break;
        }
    }

    public void deleteDir(File file) {

        if (file.exists()) {
            for (File subFiles : file.listFiles()) {
                if (subFiles.isDirectory()) {
                    deleteDir(subFiles);
                }
                subFiles.delete();
            }
        }
    }


    // This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite
    public static void startTestCase(String sTestCaseName, String dateTime) {
        log.info("\n\n****************************************************************************************");
        log.info("****************************************************************************************");
        log.info("$$$$$$$$$$$$$$$$$$$$$             " + sTestCaseName + "               $$$$$$$$$$$$$$$$$$$$$$$$$");
        log.info("$$$$$$$$$$$$$$$$$$$$$             " + dateTime + "               $$$$$$$$$$$$$$$$$$$$$$$$$");
        log.info("****************************************************************************************");
        log.info("****************************************************************************************\n\n");
    }

    //    This is to print log for the ending of the test case
    public static void endTestCase(String sTestCaseName) {
        log.info("\n\nXXXXXXXXXXXXXXXXXXXXXXX             " + "-E---N---D-" + "             XXXXXXXXXXXXXXXXXXXXXX");
        log.info("X");
        log.info("X");
        log.info("X");
        log.info("X\n\n");
    }

    //    methods to be called
    public static void info(String dateTime, String message) {
        log.info("Info " + dateTime + ": " + message);
    }

    public static void warn(String dateTime, String message) {
        log.warn("\nWarn " + dateTime + ": " + message + "\n");
    }

    public static void fatal(String dateTime, String message) {
        log.fatal("\nFatal " + dateTime + ": " + message + "\n");
    }

    public static void debug(String dateTime, String message) {
        log.debug("Debug " + dateTime + ": " + message + "\n");
    }

}
