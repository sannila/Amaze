package UserMaster;

import Common.CommonWebDrivers;
import Common.InputFields;
import Login.Login;
import base.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.ConfigFileReader;
import utils.Highlighter;
import utils.POSData;
import utils.Screenshot;
import com.github.javafaker.Faker;

import java.util.Locale;

public class UserMaster extends Config {

    //    Elements
    @FindBy(xpath = "//*[text()='User']")
    WebElement userMaster_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"firstName\"]")
    WebElement firstName_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"middleName\"]")
    WebElement middleName_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"lastName\"]")
    WebElement lastName_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"displayName\"]")
    WebElement displayName_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"employeeId\"]")
    WebElement employeeID_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"email\"]")
    WebElement email_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"countryCode\"]")
    WebElement countryCode_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"phone\"]")
    WebElement phone_ele;

    //    objects
    static Config config = new Config();
    static CommonWebDrivers commonWebDrivers = new CommonWebDrivers();
    static Screenshot screenshot = new Screenshot();
    static Highlighter highlighter = new Highlighter();
    static ConfigFileReader configFileReader = new ConfigFileReader("src/main/resources/BackOffice/backofficeValidationMessages.properties");
    POSData posData = new POSData();
    Faker faker = new Faker(new Locale("en-US"));
    InputFields inputFields = new InputFields();

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
        wait.until(ExpectedConditions.invisibilityOf(commonWebDrivers.hexaLoader()));
        wait.until(ExpectedConditions.elementToBeClickable(commonWebDrivers.toggle_sidebar_2()));
        config.info(config.dateTime(), "Opening the left menu by clicking side toggle");
        Thread.sleep(5000);
        commonWebDrivers.toggle_sidebar().click();

        config.info(config.dateTime(), "Navigating to Back office by clicking Back office menu");
        commonWebDrivers.backOffice_menu().click();

        wait.until(ExpectedConditions.invisibilityOf(commonWebDrivers.hexaLoader()));
        Thread.sleep(2000);
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
        wait.until(ExpectedConditions.elementToBeClickable(userMaster_ele));
        new Actions(driver).moveToElement(userMaster_ele).perform();

        config.info(config.dateTime(), "Clicking the User master link");
        userMaster_ele.click();
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

    public void navigateToNewUserScreen() {
        commonWebDrivers.addNewButton().click();
        config.info(config.dateTime(), "Navigating to new user screen");
        wait.until(ExpectedConditions.invisibilityOf(commonWebDrivers.hexaLoader()));

        config.info(config.dateTime(), "Checking for new user page title");
        try {
            Assert.assertEquals(commonWebDrivers.h2().getText(), configFileReader.getPropertyValue("h2"));
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, commonWebDrivers.h2());
            screenshot.takeScreenshot(driver, "User Master", "New User Page", "Page Heading");
            config.fatal(config.dateTime(), "New user page title is not expected: " + e.getMessage());
            highlighter.clearHighLighter(driver, commonWebDrivers.h2());
        }
    }


    String fName = faker.name().firstName();

    public void firstName() {
        config.info(config.dateTime(), "Validating the first name input field");

        inputFields.inputField_with_emptyData(firstName_ele, "First Name", "firstNameEmpty", "firstName");
        inputFields.inputField_with_invalidData(firstName_ele, "First Name", "invalidField", "firstName");
        inputFields.inputField_with_validData(firstName_ele, "First Name", fName, "firstName");
    }

    public void middleName() {
        config.info(config.dateTime(), "Validating the middle name input field");

        inputFields.inputField_with_invalidData(middleName_ele, "Middle Name", "invalidField", "middleName");

        String mName = ".KMIT";
        inputFields.inputField_with_validData(middleName_ele, "Middle Name", mName, "middleName");
    }

    public void lastName() {
        config.info(config.dateTime(), "Validating the last name input field");
        inputFields.inputField_with_emptyData(lastName_ele, "Last Name", "lastNameEmpty", "lastName");
        inputFields.inputField_with_invalidData(lastName_ele, "Last Name", "invalidField", "lastName");

        String lName = faker.name().lastName();
        inputFields.inputField_with_validData(lastName_ele, "Last Name", lName, "lastName");
    }

    public void displayName() throws InterruptedException {
        config.info(config.dateTime(), "Validating the Display name input field");
        inputFields.inputField_with_invalidData(displayName_ele, "Display Name", "invalidField", "displayName");
        inputFields.inputField_with_validData(displayName_ele, "Display Name", fName, "displayName");
    }

    public void employeeID(){
        config.info(config.dateTime(), "Validating Employee ID input field");
        String idNumber = faker.idNumber().ssnValid();
        inputFields.inputField_with_invalidData(employeeID_ele, "Employee ID", "invalidField", "employeeId");
        inputFields.inputField_with_validData(employeeID_ele, "Employee ID", idNumber, "employeeId");
    }

    public void email_id(){
        config.info(config.dateTime(), "Validating Email input field");
        String emailID = faker.internet().emailAddress();
        inputFields.inputField_with_emptyData(email_ele, "Email", "emailEmpty", "email");
        inputFields.inputField_with_invalidData(email_ele, "Email", "invalidField", "email_id");
        inputFields.inputField_with_validData(email_ele, "Email", emailID, "email_id");
    }

    public void country_code(){
        config.info(config.dateTime(), "Validating Country code input field");
        String countyCode = faker.country().currencyCode();
        inputFields.inputField_with_emptyData(countryCode_ele, "Country Code", "countryCodeBlank", "country_code");
        inputFields.inputField_with_invalidData(countryCode_ele, "Country Code", "countryCodeInvalid", "country_code");
        inputFields.inputField_with_validData(countryCode_ele, "Country Code", countyCode, "country_code");
    }

    public void phoneNumber(){
        config.info(config.dateTime(), "Validating Phone Number input field");
        String phone = faker.phoneNumber().phoneNumber();
        inputFields.inputField_with_emptyData(phone_ele, "Phone", "phoneNumberEmpty", "phoneNumber");
        inputFields.inputField_with_invalidData(phone_ele, "Phone", "invalidField", "phoneNumber");
        inputFields.inputField_with_validData(phone_ele, "Phone", phone, "phoneNumber");
    }

    public void userMaster_test(String url, String username, String password) throws InterruptedException {
        if (driver.getCurrentUrl().equals(url)) {
            Login login = new Login();
            login.valid_login(username, password, url);
        }
//        Thread.sleep(5000);
        navigate_to_userMaster(url);
        checkAddNewButton();
        navigateToNewUserScreen();
        firstName();
        middleName();
        lastName();
        displayName();
        employeeID();
        email_id();
        country_code();
        phoneNumber();
    }
}
