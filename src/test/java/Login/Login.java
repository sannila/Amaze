package Login;

import Common.CommonWebDrivers;
import base.Config;
import junit.framework.AssertionFailedError;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;
import utils.ConfigFileReader;
import utils.Highlighter;
import utils.Screenshot;

public class Login extends Config {

    @FindBy(className = "login_username")
    WebElement username;

    @FindBy(className = "username_required")
    WebElement usernameRequiredError;

    @FindBy(className = "username_pattern")
    WebElement usernamePatternError;

    @FindBy(className = "login_password")
    WebElement password;

    @FindBy(className = "password_required")
    WebElement passwordRequiredError;

    @FindBy(className = "submit-button")
    WebElement submit_button;

    WebDriverWait wait;
    static Logger log = Logger.getLogger(Login.class);
    Config config = new Config();
    static Screenshot takeScreenshot = new Screenshot();
    static Highlighter highlighter = new Highlighter();
    static CommonWebDrivers commonWebDrivers = new CommonWebDrivers();
    ConfigFileReader configFileReader = new ConfigFileReader("src/main/resources/POS/posValidationMessages.properties");
    public static String organizationName;

    /**
     * login constructor which initiates the PageFactory
     */

    public Login() {
        log.info("\n****Initializing Login Test Cases****");
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 30);
    }

    /**
     * Check for page title
     *
     * @throws InterruptedException
     */
    public void pageTitle() throws InterruptedException {
        config.info(config.dateTime(), "Checking for page title");
        try {
            Assert.assertEquals(driver.getTitle(), configFileReader.getPropertyValue("posTitle"));
        } catch (AssertionError e) {
            takeScreenshot.takeScreenshot(driver, "Login", "pageTitle", "Page Title");
            config.fatal(config.dateTime(), ("Exception while checking page title: " + e.getMessage()));
        }
    }

    /**
     * validate the username with empty data
     */
    public void userName_validation_empty() {
        config.info(config.dateTime(), "Validating user name input field with empty data");

        username.clear();
        username.sendKeys(Keys.TAB);
        config.info(config.dateTime(), "Sending user name value as \"\"");
        try {
            Assert.assertEquals(usernameRequiredError.getText(), configFileReader.getPropertyValue("emptyUserName"));
        } catch (AssertionError e) {

            /**
             * Below method used to highlight the current locator
             */
            highlighter.setHighLighter(driver, usernameRequiredError);

            takeScreenshot.takeScreenshot(driver, "Login", "userName_validation_empty", "userName_validation_empty");
            config.fatal(config.dateTime(), ("Exception while validating user name with empty data: " + e.getMessage()));

            /**
             * Below method used to clear the highlighter from the current element
             */
            highlighter.clearHighLighter(driver, usernameRequiredError);
        }

    }

    /**
     * validate the username with invalid data
     */
    public void userName_validation_pattern() {
        config.info(config.dateTime(), "Validating user name input field with an invalid data");
        String invalid_input = "!@#$";
        username.clear();
        username.sendKeys(invalid_input);
        config.info(config.dateTime(), ("Sending user name input value as " + invalid_input));
        try {
            Assert.assertEquals(usernamePatternError.getText(), configFileReader.getPropertyValue("invalidUseName"));
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, usernamePatternError);
            takeScreenshot.takeScreenshot(driver, "Login", "userName_validation_pattern", "userName_validation_pattern");
            config.fatal(config.dateTime(), ("Exception while validating user name input field with invalid data: " + e.getMessage()));
            highlighter.clearHighLighter(driver, usernamePatternError);
        }
    }

    /**
     * validate the password with empty data
     */
    public void password_validation_empty() {
        config.info(config.dateTime(), "Validating the password input field with an empty date");
        password.clear();
        password.sendKeys(Keys.TAB);
        config.info(config.dateTime(), ("Sending password input value as \"\""));
        try {
            Assert.assertEquals(passwordRequiredError.getText(), configFileReader.getPropertyValue("emptyPassword"));
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, passwordRequiredError);
            takeScreenshot.takeScreenshot(driver, "Login", "password_validation_empty", "password_validation_empty");
            config.fatal(config.dateTime(), ("Exception while validating password input field with empty data: " + e.getMessage()));
            highlighter.clearHighLighter(driver, passwordRequiredError);
        }
    }

    /**
     * validate with invalid username and password
     */
    public void invalid_username_password(String url) throws InterruptedException {
        config.info(config.dateTime(), "Validating user name & password input field with an invalid data");
        String invalid_username = "qwerty";
        String invalid_password = "qwerty";

        username.clear();
        username.sendKeys(invalid_username);
        config.info(config.dateTime(), ("Sending user name as: " + invalid_username));

        password.clear();
        password.sendKeys(invalid_password);
        config.info(config.dateTime(), ("Sending user name as: " + invalid_password));

        submit_button.click();
        config.info(config.dateTime(), "Clicking Login button");

        Thread.sleep(5000);
        wait.until(ExpectedConditions.urlContains(url));
        try {
            Assert.assertEquals(driver.getCurrentUrl(), url);
            Assert.assertEquals(commonWebDrivers.getSnackBar().getText(), configFileReader.getPropertyValue("invalidPassword"));
        } catch (AssertionError e) {
            takeScreenshot.takeScreenshot(driver, "Login", "invalid_username_password", "invalid_username_password");
            config.fatal(config.dateTime(), ("Exception while validating username and password with invalid data: " + e.getMessage()));
        }
    }

    /**
     * validate with valid username and password
     */
    public void valid_login(String userName, String pwd, String url) throws InterruptedException {
        Thread.sleep(5000);
        config.info(config.dateTime(), "Validating user name & password input field with an valid data");
        wait.until(ExpectedConditions.elementToBeClickable(submit_button));

        username.clear();
        username.sendKeys(userName);
        config.info(config.dateTime(), ("Sending user name as: " + userName));

        password.clear();
        password.sendKeys(pwd);
        config.info(config.dateTime(), ("Sending user name as: " + pwd));

        submit_button.click();
        wait.until(ExpectedConditions.urlContains(url + configFileReader.getPropertyValue("dashboard")));

        try {
            Assert.assertEquals(driver.getCurrentUrl(), url + configFileReader.getPropertyValue("dashboard"));
        } catch (Throwable e) {
            takeScreenshot.takeScreenshot(driver, "Login", "valid_login", "valid_login");
            config.fatal(config.dateTime(), ("Exception after valid login: " + e.getMessage()));
        }
    }


    /**
     * gather all above test methods here
     */
    public void login_test(String url, String username, String password) throws InterruptedException {
        pageTitle();
        userName_validation_empty();
        userName_validation_pattern();
        password_validation_empty();
        invalid_username_password(url);
        valid_login(username, password, url);
    }

}
