package UserMaster;

import CRUD.CRUDFunction;
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

import java.util.List;

public class UserMasterEditUpdate extends Config {

    //    Elements in PageFactory
    @FindBy(xpath = "//*[text()='User']")
    WebElement userMaster_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"email\"]")
    WebElement email_ele;

    @FindBy(className = "update_btn")
    WebElement updateBtn_ele;

    static Config config = new Config();
    static WebDriverWait wait;
    static CommonWebDrivers commonWebDrivers = new CommonWebDrivers();
    static ConfigFileReader configFileReader = new ConfigFileReader("src/main/resources/BackOffice/backofficeValidationMessages.properties");
    static Screenshot screenshot = new Screenshot();
    static Login login = new Login();
    static InputFields inputFields = new InputFields();
    static Highlighter highlighter = new Highlighter();
    static CRUDFunction crud = new CRUDFunction();

    static String editEmail;

    public UserMasterEditUpdate() {
        config.info(config.dateTime(), "\n****Initializing User Master Edit & Update Test Cases****");
        for (int i = 0; i < config.ranFor.length; i++) {
            config.ranFor[0] = "User Master Edit";
        }
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 30);

    }


    public void navigate_to_backOffice(String url) throws InterruptedException {
        wait.until(ExpectedConditions.invisibilityOf(commonWebDrivers.hexaLoader()));
//        wait.until(ExpectedConditions.elementToBeClickable(commonWebDrivers.toggle_sidebar_2()));
        config.info(config.dateTime(), "Opening the left menu by clicking side toggle");
        Thread.sleep(5000);

//        try {
//            commonWebDrivers.toggle_sidebar_right().click();
//        } catch (NoSuchElementException e){
//            commonWebDrivers.toggle_sidebar_left().click();
//        }

        commonWebDrivers.toggle_sidebar().click();
        config.info(config.dateTime(), "Navigating to Back office by clicking Back office menu");
        wait.until(ExpectedConditions.invisibilityOf(commonWebDrivers.hexaLoader()));
        Thread.sleep(5000);
        if(commonWebDrivers.backOffice_menu().isDisplayed()){

            commonWebDrivers.backOffice_menu().click();
        } else {
            commonWebDrivers.toggle_sidebar().click();
            commonWebDrivers.backOffice_menu().click();
        }
//        wait.until(ExpectedConditions.elementToBeClickable(commonWebDrivers.toggle_sidebar_left()));
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
    }

    public void editUser() {
        config.info(config.dateTime(), "Clicking for the 1st user of the list in user master");

        List<WebElement> userList_ele = driver.findElements(By.xpath("//button[@mattooltip=\"edit\"]"));
        if (userList_ele.size() <= 0) {
            screenshot.takeScreenshot(driver, "UserMasterEditUpdate", "editUser", "Edit User");
            config.fatal(config.dateTime(), "User list is not available in user master table.");
        } else {
            if (!userList_ele.get(0).isEnabled()) {
                highlighter.setHighLighter(driver, userList_ele.get(0));
                screenshot.takeScreenshot(driver, "UserMasterEditUpdate", "editUser", "Edit User");
                config.fatal(config.dateTime(), "Edit button is not enabled in user Master");
                highlighter.clearHighLighter(driver, userList_ele.get(0));
            } else {
                userList_ele.get(0).click();
            }
        }
    }

    public void checkPageHeader() {
        config.info(config.dateTime(), "Validating the page title of User Edit master");
        wait.until(ExpectedConditions.invisibilityOf(commonWebDrivers.hexaLoader()));
        try {
            Assert.assertEquals(commonWebDrivers.h2().getText(), configFileReader.getPropertyValue("h2_edit"));
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, commonWebDrivers.h2());
            screenshot.takeScreenshot(driver, "UserMasterEditUpdate", "checkPageHeader", "User Edit Page Header");
            config.fatal(config.dateTime(), "User Edit master page header is not proper: " + e.getMessage());
            highlighter.clearHighLighter(driver, commonWebDrivers.h2());
        }

//        editEmail = email_ele.getText();
    }

    public void updateButtonValidation() {
        config.info(config.dateTime(), "Validating the update button on user edit function");

        try {
            Assert.assertEquals(inputFields.isButtonEnabled(updateBtn_ele), "Enabled");
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, updateBtn_ele);
            screenshot.takeScreenshot(driver, "UserMasterEditUpdate", "updateButtonValidation", "User Edit Update Button");
            config.fatal(config.dateTime(), "Update button is not enabled on User edit function: " + e.getMessage());
            highlighter.clearHighLighter(driver, updateBtn_ele);
        }
    }

    public void updateUser(String username, String password) {
        config.info(config.dateTime(), "Validating the update button functionality in user edit");
        updateBtn_ele.click();

        try {
            Assert.assertEquals(commonWebDrivers.getSnackBar().getText().replace("\nDismiss", ""), configFileReader.getPropertyValue("updateSnackBar"));
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, commonWebDrivers.getSnackBar());
            screenshot.takeScreenshot(driver, "UserMasterEditUpdate", "updateUser", "User Update Button");
            config.fatal(config.dateTime(), "Snack bar message is not proper after user update: " + e.getMessage());
            highlighter.clearHighLighter(driver, commonWebDrivers.getSnackBar());
        }

//        int orgId = Integer.parseInt(crud.getOrgID(username, password));
//        System.out.println("Edited EmailId: " + editEmail);
//        try {
//            Assert.assertEquals(crud.getUserAPI(editEmail, orgId).contains(editEmail), true);
//            config.info(config.dateTime(), "Data found in user table for created user with given emailID: " + editEmail);
//        } catch (AssertionError e) {
//            screenshot.takeScreenshot(driver, "UserMaster", "checkForSuccessMessage", "CreateNewUser");
//            config.fatal(config.dateTime(), "Data not found in user table for created user with given emailID: " + e.getMessage());
//        }
    }


    public void userMasterEdit_test(String url, String username, String password) throws InterruptedException {
        if (driver.getCurrentUrl().equals(url)) {
            login.valid_login(username, password, url);
        }

//        if (driver.getCurrentUrl() == (url + configFileReader.getPropertyValue("posDashboard"))) {
            navigate_to_backOffice(url);
            navigate_to_userMaster();
//        }

        editUser();
        checkPageHeader();
        updateButtonValidation();
        updateUser(username, password);
    }
}
