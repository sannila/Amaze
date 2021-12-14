package UserMaster;

import Common.CommonWebDrivers;
import Login.Login;
import base.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigFileReader;
import utils.Highlighter;
import utils.Screenshot;

public class UserMaster extends Config {

    //    Elements
    @FindBy(xpath = "//*[text()='User']")
    WebElement userMaster;

    //    objects
    static Config config = new Config();
    static CommonWebDrivers commonWebDrivers = new CommonWebDrivers();
    static Screenshot screenshot = new Screenshot();
    static Highlighter highlighter = new Highlighter();
    static ConfigFileReader configFileReader = new ConfigFileReader("src/main/resources/BackOffice/backofficeValidationMessages.properties");

    //    driver wait object
    static WebDriverWait wait;

    public UserMaster() {
        log.info("\n****Initializing User Master Test Cases****");
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 30);
    }

    /**
     * Navigating to the user master
     *
     * @throws InterruptedException
     */
    public void navigate_to_userMaster(String url) throws InterruptedException {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("hex_loader")));
        wait.until(ExpectedConditions.elementToBeClickable(commonWebDrivers.toggle_sidebar_2()));
        config.info(config.dateTime(), "Opening the left menu by clicking side toggle");
        commonWebDrivers.toggle_sidebar().click();

        config.info(config.dateTime(), "Navigating to Back office by clicking Back office menu");
        commonWebDrivers.backOffice_menu().click();

        config.info(config.dateTime(), "Checking for the back office landing page url");
        try {
            Assert.assertEquals(driver.getCurrentUrl(), (url + configFileReader.getPropertyValue("backOfficeLandingPage")));
        } catch (AssertionError e) {
            screenshot.takeScreenshot(driver, "UserMaster", "Navigate to user master", "backofficeLandingPageUrl");
            config.fatal(config.dateTime(), "Back Office landing page url is not proper: " + e.getMessage());
        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("hex_loader")));
        wait.until(ExpectedConditions.elementToBeClickable(commonWebDrivers.organization_nav()));

        config.info(config.dateTime(), "Moving mouse to the Organization on header navigation");
        new Actions(driver).moveToElement(commonWebDrivers.organization_nav()).perform();
        Thread.sleep(5000);

        config.info(config.dateTime(), "Moving mouse to the User link in organization navigation menu");
        wait.until(ExpectedConditions.elementToBeClickable(userMaster));
        new Actions(driver).moveToElement(userMaster).perform();

        config.info(config.dateTime(), "Clicking the User master link");
        userMaster.click();
        Thread.sleep(5000);
    }

    /**
     * Checking for the add new button of the user screen
     */
    public void checkAddNewButton() {
        wait.until(ExpectedConditions.elementToBeClickable(commonWebDrivers.addNewButton()));
        config.info(config.dateTime(), "Checking for add new button is clickable on page load");
        String buttonName = commonWebDrivers.addNewButton().getText();
        Boolean isClickable = commonWebDrivers.addNewButton().isEnabled();
        try {
            Assert.assertEquals(isClickable, Boolean.TRUE);
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, commonWebDrivers.addNewButton());
            screenshot.takeScreenshot(driver, "User Master", "Add New Button", "addNewButton");
            config.fatal(config.dateTime(), "User master add new button not clickable on page load: " + e.getMessage());
            highlighter.clearHighLighter(driver, commonWebDrivers.addNewButton());
        }

        config.info(config.dateTime(), "Checking for add new button text");
        try {
            Assert.assertEquals(buttonName, configFileReader.getPropertyValue("addNewUserButton"));
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, commonWebDrivers.addNewButton());
            screenshot.takeScreenshot(driver, "User Master", "Add New Button", "addNewButton");
            config.fatal(config.dateTime(), "Error while checking user master add new button text: " + e.getMessage());
            highlighter.clearHighLighter(driver, commonWebDrivers.addNewButton());
        }
    }


    public void userMaster_test(String url, String username, String password) throws InterruptedException {
        if (driver.getCurrentUrl().equals(url)) {
            Login login = new Login();
            login.valid_login(username, password, url);
        }
//        Thread.sleep(5000);
        navigate_to_userMaster(url);
        checkAddNewButton();
    }
}
