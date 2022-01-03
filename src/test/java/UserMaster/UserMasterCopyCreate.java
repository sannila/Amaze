package UserMaster;

import Common.CommonWebDrivers;
import Common.InputFields;
import Login.Login;
import base.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.ConfigFileReader;
import utils.Highlighter;
import utils.Screenshot;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.Locale;

public class UserMasterCopyCreate extends Config {

    //    Elements in PageFactory
    @FindBy(xpath = "//*[text()='User']")
    WebElement userMaster_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"email\"]")
    WebElement email_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"userName\"]")
    WebElement userName_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"password\"]")
    WebElement password_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"confirmPassword\"]")
    WebElement confirmPassword_ele;

    @FindBy(className = "create_btn")
    WebElement createBtn_ele;

    @FindBy(className = "cancel_btn")
    WebElement cancelBtn_ele;

    @FindBy(xpath = "//*[@col-id=\"email_1\"]")
    WebElement colId_email_ele;

    @FindBy(className = "btn-delete-yes")
    WebElement delete_yes_ele;

    static WebDriverWait wait;
    static Config config = new Config();
    static CommonWebDrivers commonWebDrivers = new CommonWebDrivers();
    static ConfigFileReader configFileReader = new ConfigFileReader("src/main/resources/BackOffice/backofficeValidationMessages.properties");
    static Screenshot screenshot = new Screenshot();
    static Login login = new Login();
    static InputFields inputFields = new InputFields();
    static Highlighter highlighter = new Highlighter();
    static Faker faker = new Faker(new Locale("en-US"));

    static String emailID;

    public UserMasterCopyCreate() {
        config.info(config.dateTime(), "\n****Initializing User Master Copy & Create Test Cases****");
        for (int i = 0; i < config.ranFor.length; i++) {
            config.ranFor[0] = "User Master Copy";
        }
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 30);
        emailID = config.getEmailId();
    }

    public void navigate_to_backOffice(String url) throws InterruptedException {
        wait.until(ExpectedConditions.invisibilityOf(commonWebDrivers.hexaLoader()));
//        wait.until(ExpectedConditions.elementToBeClickable(commonWebDrivers.toggle_sidebar_right()));
        config.info(config.dateTime(), "Opening the left menu by clicking side toggle");
        Thread.sleep(5000);

        commonWebDrivers.toggle_sidebar().click();
        config.info(config.dateTime(), "Navigating to Back office by clicking Back office menu");
        wait.until(ExpectedConditions.invisibilityOf(commonWebDrivers.hexaLoader()));
        Thread.sleep(5000);
        if (commonWebDrivers.backOffice_menu().isDisplayed()) {

            commonWebDrivers.backOffice_menu().click();
        } else {
            commonWebDrivers.toggle_sidebar().click();
            commonWebDrivers.backOffice_menu().click();
        }
        wait.until(ExpectedConditions.urlContains(url + configFileReader.getPropertyValue("backOfficeLandingPage")));
//        commonWebDrivers.toggle_sidebar_left().click();
        config.info(config.dateTime(), "Checking for the back office landing page url");
        try {
            Assert.assertEquals(driver.getCurrentUrl(), (url + configFileReader.getPropertyValue("backOfficeLandingPage")));
        } catch (AssertionError e) {
            screenshot.takeScreenshot(driver, "UserMaster", "Navigate to user master", "backofficeLandingPageUrl");
            config.fatal(config.dateTime(), "Back Office landing page url is not proper: " + e.getMessage());
        }
    }

    /**
     * Navigating to the user master
     *
     * @throws InterruptedException
     */
    public void navigate_to_userMaster() throws InterruptedException {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("hex_loader")));
        wait.until(ExpectedConditions.elementToBeClickable(commonWebDrivers.organization_nav()));

        config.info(config.dateTime(), "Moving mouse to the Organization on header navigation");
        new Actions(driver).moveToElement(commonWebDrivers.organization_nav()).perform();
        Thread.sleep(2000);

        config.info(config.dateTime(), "Moving mouse to the User link in organization navigation menu");
        wait.until(ExpectedConditions.elementToBeClickable(userMaster_ele));
        new Actions(driver).moveToElement(userMaster_ele).perform();

        config.info(config.dateTime(), "Clicking the User master link");
        userMaster_ele.click();
        Thread.sleep(5000);

//        wait.until(ExpectedConditions.invisibilityOf(commonWebDrivers.gridLoader()));
    }

    public void copy_user() {
        config.info(config.dateTime(), "Clicking copy of the 1st user in the grid list");
        List<WebElement> copy_list = driver.findElements(By.xpath("//button[@mattooltip=\"copy\"]"));

        try {
            Assert.assertEquals(inputFields.isButtonEnabled(copy_list.get(0)), "Enabled");
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, copy_list.get(0));
            screenshot.takeScreenshot(driver, "UserMasterCopyCreate", "copy_user", "Copy User");
            config.fatal(config.dateTime(), "Copy button is disabled for user: " + e.getMessage());
            highlighter.clearHighLighter(driver, copy_list.get(0));
        }
        copy_list.get(0).click();
    }

    public void createButtonsStatus(String status) {
        config.info(config.dateTime(), "Validating create button's default status");

        try {
            Assert.assertEquals(inputFields.isButtonEnabled(createBtn_ele), status);
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, createBtn_ele);
            screenshot.takeScreenshot(driver, "UserMasterCopyCreate", "createButtonsStatus", "Create button");
            config.fatal(config.dateTime(), "Create button should be " + status + " on page load: " + e.getMessage());
            highlighter.clearHighLighter(driver, createBtn_ele);
        }
    }

    public void cancelButtonStatus() {
        config.info(config.dateTime(), "Validating the Cancel button's default status");
        System.out.println("Cancel button: " + inputFields.isButtonEnabled(cancelBtn_ele));

        try {
            Assert.assertEquals(inputFields.isButtonEnabled(cancelBtn_ele), "Enabled");
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, cancelBtn_ele);
            screenshot.takeScreenshot(driver, "UserMasterCopyCreate", "cancelButtonStatus", "Cancel button");
            config.fatal(config.dateTime(), "Cancel button should be enabled on page load: " + e.getMessage());
            highlighter.clearHighLighter(driver, cancelBtn_ele);
        }
    }

    public void email() {
        inputFields.inputField_with_validData(email_ele, "Email", emailID, "Email");
    }

    public void userName() {
        String userName_input = faker.name().firstName();
        inputFields.inputField_with_validData(userName_ele, "User Name", userName_input, "userName");
    }

    String password_value = "Password@123";

    public void password() {
        inputFields.inputField_with_validData(password_ele, "Password", password_value, "Password");
    }

    public void confirmPassword() {
        inputFields.inputField_with_validData(confirmPassword_ele, "Confirm Password", password_value, "Confirm Password");
    }

    public void createUser() throws InterruptedException {
        config.info(config.dateTime(), "Clicking the Create button on valid data");
        createBtn_ele.click();
        Thread.sleep(3000);
        try {
            Assert.assertEquals(commonWebDrivers.getSnackBar().getText().replace("\nDismiss", ""), configFileReader.getPropertyValue("successSnackBar"));
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, commonWebDrivers.getSnackBar());
            screenshot.takeScreenshot(driver, "UserMasterCopyCreate", "createUser", "Create User success message");
            config.fatal(config.dateTime(), "Snack bar message is not proper while copy & creating an user: " + e.getMessage());
            highlighter.clearHighLighter(driver, commonWebDrivers.getSnackBar());
        }
    }

    public void onCopyCreateSuccess() {
        wait.until(ExpectedConditions.invisibilityOf(commonWebDrivers.gridLoader()));
        config.info(config.dateTime(), "Created user should be listed in the grid list");
        List<WebElement> email_colId_list = driver.findElements(By.xpath("//*[@col-id=\"email_1\"]"));
        System.out.println("Created user: " + email_colId_list.get(1).getText());
        System.out.println("Actual EmailID: " + emailID);
        try {
            Assert.assertEquals(email_colId_list.get(1).getText(), emailID);
        } catch (AssertionError e) {
            screenshot.takeScreenshot(driver, "UserMasterCopyCreate", "onCopyCreateSuccess", "Copy Create Grid");
            config.fatal(config.dateTime(), "Created user is not listed in the user master grid: " + e.getMessage());
        }
    }

    public void deleteUser() throws InterruptedException {
        Thread.sleep(5000);
        config.info(config.dateTime(), "Delete the created user from the list");
        driver.findElement(By.className("grid_search")).sendKeys(emailID);
        List<WebElement> email_colId_list = driver.findElements(By.xpath("//*[@col-id=\"email_1\"]"));
        List<WebElement> delete_button_list = driver.findElements(By.xpath("//*[@mattooltip=\"delete\"]"));

        config.info(config.dateTime(), "Created user found in the user grid list.");
        delete_button_list.get(0).click();
        Thread.sleep(5000);
        boolean isDeletePop = delete_yes_ele.isDisplayed();
        if (isDeletePop) {
            delete_yes_ele.click();
            Assert.assertEquals(commonWebDrivers.getSnackBar().getText().replace("\nDismiss", ""), configFileReader.getPropertyValue("deleteSnackBar"));
            Thread.sleep(5000);
        } else {
            screenshot.takeScreenshot(driver, "UserMasterCopyCreate", "deleteUser", "Delete User");
            config.fatal(config.dateTime(), "Delete confirmation popup is not shown for user delete function");
        }

        wait.until(ExpectedConditions.invisibilityOf(commonWebDrivers.hexaLoader()));
        driver.findElement(By.className("grid_search")).sendKeys(emailID);
        if (delete_button_list.size() < 0) {
            config.fatal(config.dateTime(), "Email id not deleted" + email_colId_list.get(1).getText());
        }

    }


    public void userMasterCopyCreate_Test(String url, String username, String password) throws InterruptedException {
        if (driver.getCurrentUrl().equals(url)) {
            login.valid_login(username, password, url);
        }
        wait.until(ExpectedConditions.invisibilityOf(commonWebDrivers.hexaLoader()));
//        if (driver.getCurrentUrl() == (url + configFileReader.getPropertyValue("posDashboard"))) {
        navigate_to_backOffice(url);
        navigate_to_userMaster();
//        }

        copy_user();
        createButtonsStatus("Disabled");
        cancelButtonStatus();
        email();
        userName();
        password();
        confirmPassword();
        createButtonsStatus("Enabled");
        createUser();
        onCopyCreateSuccess();
        deleteUser();
    }
}
