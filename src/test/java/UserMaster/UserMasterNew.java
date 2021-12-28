package UserMaster;

import CRUD.CRUDFunction;
import Common.CommonWebDrivers;
import Common.InputFields;
import Login.Login;
import base.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
import utils.POSData;
import utils.Screenshot;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.Locale;

public class UserMasterNew extends Config {

    //    Elements in PageFactory
    @FindBy(xpath = "//*[text()='User']")
    WebElement userMaster_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"firstName\"]")
    WebElement firstName_ele;

    @FindBy(className = "fname_error")
    WebElement firstName_error_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"middleName\"]")
    WebElement middleName_ele;

    @FindBy(className = "mname_error")
    WebElement middleName_error_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"lastName\"]")
    WebElement lastName_ele;

    @FindBy(className = "lname_error")
    WebElement lName_error_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"displayName\"]")
    WebElement displayName_ele;

    @FindBy(className = "dname_error")
    WebElement dname_error_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"employeeId\"]")
    WebElement employeeID_ele;

    @FindBy(className = "employeeID_error")
    WebElement employeeID_error_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"email\"]")
    WebElement email_ele;

    @FindBy(className = "email_error")
    WebElement email_error_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"countryCode\"]")
    WebElement countryCode_ele;

    @FindBy(className = "ccode_error")
    WebElement ccode_error_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"phone\"]")
    WebElement phone_ele;

    @FindBy(className = "phone_error")
    WebElement phone_error_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"extension\"]")
    WebElement extension_ele;

    @FindBy(className = "extension_error")
    WebElement extension_error_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"userName\"]")
    WebElement userName_ele;

    @FindBy(className = "userName_error")
    WebElement userName_error_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"password\"]")
    WebElement password_ele;

    @FindBy(className = "password_error")
    WebElement password_error_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"confirmPassword\"]")
    WebElement confirmPassword_ele;

    @FindBy(className = "confirmPassword_error")
    WebElement confirmPassword_error_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"changePasswordOnNextLogin\"]")
    WebElement changePasswordOnNextLogin_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"organizationId\"]")
    WebElement organizationId_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"roleId\"]")
    WebElement roleId_ele;

    @FindBy(className = "role_error")
    WebElement role_error_ele;

    @FindBy(className = "//*[@role=\"option\"]")
    WebElement role_list_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"departmentId\"]")
    WebElement departmentId_ele;

    @FindBy(xpath = "//*[@formcontrolname=\"designation\"]")
    WebElement designation_ele;

    @FindBy(className = "mat-error")
    WebElement matError_ele;

    @FindBy(className = "create_btn")
    WebElement createBtn_ele;

    @FindBy(className = "cancel_btn")
    WebElement cancelBtn_ele;

    @FindBy(className = "role_btn")
    WebElement addRoleBtn_ele;

    @FindBy(className = "role_reset")
    WebElement roleResetBtn_ele;

    //    objects
    static Config config = new Config();
    static CommonWebDrivers commonWebDrivers = new CommonWebDrivers();
    static Screenshot screenshot = new Screenshot();
    static Highlighter highlighter = new Highlighter();
    static ConfigFileReader configFileReader = new ConfigFileReader("src/main/resources/BackOffice/backofficeValidationMessages.properties");
    POSData posData = new POSData();
    Faker faker = new Faker(new Locale("en-US"));
    InputFields inputFields = new InputFields();
    public static String email;
    static String orgName;

    static CRUDFunction crud = new CRUDFunction();

    //    driver wait object
    static WebDriverWait wait;

    public UserMasterNew() {
        config.info(config.dateTime(), "\n****Initializing User Master Test Cases****");
        email = config.getEmailId();
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 30);

//        JsonNode data_Count = crud.userGetAPI("2021Dec27_15_34_25@kmitsolutions.com");
    }


    public void navigate_to_backOffice(String url) throws InterruptedException {
        wait.until(ExpectedConditions.invisibilityOf(commonWebDrivers.hexaLoader()));
//        wait.until(ExpectedConditions.elementToBeClickable(commonWebDrivers.toggle_sidebar_right()));
        config.info(config.dateTime(), "Opening the left menu by clicking side toggle");
        Thread.sleep(5000);

        if (commonWebDrivers.toggle_sidebar_right().isDisplayed()) {
            commonWebDrivers.toggle_sidebar_right().click();
        } else {
            commonWebDrivers.toggle_sidebar_left().click();
        }

        config.info(config.dateTime(), "Navigating to Back office by clicking Back office menu");
        commonWebDrivers.backOffice_menu().click();

        wait.until(ExpectedConditions.invisibilityOf(commonWebDrivers.hexaLoader()));
        commonWebDrivers.toggle_sidebar_left().click();
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
            screenshot.takeScreenshot(driver, "UserMaster", "checkAddNewButton", "addNewButton");
            config.fatal(config.dateTime(), "User master add new button not clickable on page load: " + e.getMessage());
            highlighter.clearHighLighter(driver, commonWebDrivers.addNewButton());
        }

        config.info(config.dateTime(), "Checking for add new button text");
        try {
            Assert.assertEquals(buttonName, configFileReader.getPropertyValue("addNewUserButton"));
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, commonWebDrivers.addNewButton());
            screenshot.takeScreenshot(driver, "UserMaster", "checkAddNewButton", "addNewButton");
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
            Assert.assertEquals(commonWebDrivers.h2().getText(), configFileReader.getPropertyValue("h2_new"));
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, commonWebDrivers.h2());
            screenshot.takeScreenshot(driver, "UserMaster", "navigateToNewUserScreen", "Page Heading");
            config.fatal(config.dateTime(), "New user page title is not expected: " + e.getMessage());
            highlighter.clearHighLighter(driver, commonWebDrivers.h2());
        }
    }

//    public void getOrganizationName(String username, String password) throws InterruptedException {
//        System.out.println("getOrganizationName" + crud.getOrgName(username, password));
//        commonWebDrivers.getUserName().click();
//        Thread.sleep(2000);
//        orgName = commonWebDrivers.getOrganizationName().getText();
//        Thread.sleep(2000);
//        commonWebDrivers.getOrganizationName().click();
//        Thread.sleep(2000);
//    }

    public void createButtonsStatus() {
        config.info(config.dateTime(), "Validating create button's default status");
        System.out.println("Create button: " + inputFields.isButtonEnabled(createBtn_ele));

        try {
            Assert.assertEquals(inputFields.isButtonEnabled(createBtn_ele), "Disabled");
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, createBtn_ele);
            screenshot.takeScreenshot(driver, "UserMaster", "createButtonsStatus", "Create button");
            config.fatal(config.dateTime(), "Create button should be disabled on page load: " + e.getMessage());
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
            screenshot.takeScreenshot(driver, "UserMaster", "cancelButtonStatus", "Cancel button");
            config.fatal(config.dateTime(), "Cancel button should be enabled on page load: " + e.getMessage());
            highlighter.clearHighLighter(driver, cancelBtn_ele);
        }
    }

    public void addRoleButtonStatus() {
        config.info(config.dateTime(), "Validating Add Role button's default status");
        System.out.println("Add Role button: " + inputFields.isButtonEnabled(addRoleBtn_ele));

        try {
            Assert.assertEquals(inputFields.isButtonEnabled(addRoleBtn_ele), "Disabled");
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, addRoleBtn_ele);
            screenshot.takeScreenshot(driver, "UserMaster", "addRoleButtonStatus", "Add Role button");
            config.fatal(config.dateTime(), "Add Role button should be disabled on page load: " + e.getMessage());
            highlighter.clearHighLighter(driver, addRoleBtn_ele);
        }
    }

    public void resetRoleButtonStatus() {
        config.info(config.dateTime(), "Validating Reset Role button's default status");
        System.out.println("Reset Role button: " + inputFields.isButtonEnabled(roleResetBtn_ele));

        try {
            Assert.assertEquals(inputFields.isButtonEnabled(roleResetBtn_ele), "Enabled");
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, roleResetBtn_ele);
            screenshot.takeScreenshot(driver, "UserMaster", "resetRoleButtonStatus", "Reset Role button");
            config.fatal(config.dateTime(), "Reset Role button should be enabled on page load: " + e.getMessage());
            highlighter.clearHighLighter(driver, roleResetBtn_ele);
        }
    }


    String fName = faker.name().firstName();

    public void firstName() throws InterruptedException {
        config.info(config.dateTime(), "Validating the first name input field");

        inputFields.inputField_with_emptyData(firstName_ele, createBtn_ele, firstName_error_ele, "First Name", "firstNameEmpty", "firstName");
//        inputFields.inputField_with_invalidData(firstName_ele, createBtn_ele, "First Name", "invalidField", "firstName");
        inputFields.inputField_with_validData(firstName_ele, "First Name", fName, "firstName");
    }

    public void middleName() {
        config.info(config.dateTime(), "Validating the middle name input field");

//        inputFields.inputField_with_invalidData(middleName_ele, createBtn_ele, middleName_error_ele, "Middle Name", "invalidField", "middleName");

        String mName = ".KMIT";
        inputFields.inputField_with_validData(middleName_ele, "Middle Name", mName, "middleName");
    }

    public void lastName() throws InterruptedException {
        config.info(config.dateTime(), "Validating the last name input field");
        inputFields.inputField_with_emptyData(lastName_ele, createBtn_ele, lName_error_ele, "Last Name", "lastNameEmpty", "lastName");
//        inputFields.inputField_with_invalidData(lastName_ele, createBtn_ele, lName_error_ele "Last Name", "invalidField", "lastName");

        String lName = faker.name().lastName();
        inputFields.inputField_with_validData(lastName_ele, "Last Name", lName, "lastName");
    }

    public void displayName() throws InterruptedException {
        config.info(config.dateTime(), "Validating the Display name input field");
//        inputFields.inputField_with_invalidData(displayName_ele, createBtn_ele, dname_error_ele, "Display Name", "invalidField", "displayName");
        inputFields.inputField_with_validData(displayName_ele, "Display Name", fName, "displayName");
    }

    public void employeeID() {
        config.info(config.dateTime(), "Validating Employee ID input field");
        String idNumber = faker.idNumber().ssnValid();
//        inputFields.inputField_with_invalidData(employeeID_ele, createBtn_ele, employeeID_error_ele, "Employee ID", "invalidField", "employeeId");
        inputFields.inputField_with_validData(employeeID_ele, "Employee ID", idNumber, "employeeId");
    }

    public void email_id() throws InterruptedException {
        config.info(config.dateTime(), "Validating Email input field");
        inputFields.inputField_with_emptyData(email_ele, createBtn_ele, email_error_ele, "Email", "emailEmpty", "email");
//        inputFields.inputField_with_invalidData(email_ele, createBtn_ele, email_error_ele, "Email", "invalidField", "email_id");
        inputFields.inputField_with_validData(email_ele, "Email", email, "email_id");
    }

    public void country_code() throws InterruptedException {
        config.info(config.dateTime(), "Validating Country code input field");
        String countyCode = "1";
        inputFields.inputField_with_emptyData(countryCode_ele, createBtn_ele, ccode_error_ele, "Country Code", "countryCodeEmpty", "country_code");
        inputFields.inputField_with_invalidData(countryCode_ele, createBtn_ele, ccode_error_ele, "Country Code", "countryCodeInvalid", "country_code");
        inputFields.inputField_with_validData(countryCode_ele, "Country Code", countyCode, "country_code");
    }

    public void phoneNumber() throws InterruptedException {
        config.info(config.dateTime(), "Validating Phone Number input field");
        String phone = "1234567890";
        inputFields.inputField_with_emptyData(phone_ele, createBtn_ele, phone_error_ele, "Phone", "phoneNumberEmpty", "phoneNumber");
//        inputFields.inputField_with_invalidData(phone_ele, createBtn_ele, phone_error_ele, "Phone", "invalidField", "phoneNumber");
        inputFields.inputField_with_validData(phone_ele, "Phone", phone, "phoneNumber");
    }

    public void extension() {
        config.info(config.dateTime(), "Validating Extension input field");
        String extension_input = "123";
//        inputFields.inputField_with_invalidData(extension_ele, createBtn_ele, extension_error_ele, "Extension", "invalidExtension", "extension");
        inputFields.inputField_with_validData(extension_ele, "Extension", extension_input, "extension");
    }

    public void userName() throws InterruptedException {
        config.info(config.dateTime(), "Validating User Name input field");
        inputFields.inputField_with_emptyData(userName_ele, createBtn_ele, userName_error_ele, "User Name", "userNameRequired", "userName");
        inputFields.inputField_with_invalidData(userName_ele, createBtn_ele, userName_error_ele, "User Name", "userNameInvalid", "userName");
        inputFields.inputField_with_validData(userName_ele, "User Name", fName, "userName");
    }


    String valid_password = "Password@123";

    public void password() throws InterruptedException {
        config.info(config.dateTime(), "Validating Password input field");
        inputFields.inputField_with_emptyData(password_ele, createBtn_ele, password_error_ele, "Password", "passwordEmpty", "password");

        String pwd_length = "qwertyu";
        password_ele.clear();
        password_ele.sendKeys(pwd_length);
        password_ele.sendKeys(Keys.TAB);

        try {
            Assert.assertEquals(matError_ele.getText(), configFileReader.getPropertyValue("passwordPatternInvalid"));
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, password_ele);
            highlighter.setHighLighter(driver, matError_ele);
            screenshot.takeScreenshot(driver, this.getClass().getSimpleName(), "Password", "passwordLength");
            config.fatal(config.dateTime(), "Password" + " validation is not proper for given password pattern: " + pwd_length + " " + e.getMessage());
            highlighter.clearHighLighter(driver, password_ele);
            highlighter.clearHighLighter(driver, matError_ele);
        }

        invalidPassword(password_ele, password_error_ele, "passwordPatternInvalid", "password", "Password");

        inputFields.inputField_with_validData(password_ele, "Password", valid_password, "password");
    }

    public void confirmPassword() throws InterruptedException {
        config.info(config.dateTime(), "Validating the Confirm Password input field");

        inputFields.inputField_with_emptyData(confirmPassword_ele, createBtn_ele, confirmPassword_error_ele, "Confirm Password", "confirmPasswordEmpty", "confirmPassword");

        invalidPassword(confirmPassword_ele, confirmPassword_error_ele, "confirmPasswordInvalid", "confirmPassword", "ConfirmPassword");

        inputFields.inputField_with_validData(confirmPassword_ele, "Confirm Password", valid_password, "confirmPassword");
    }

    public void changePasswordOnNextLogin() {
        config.info(config.dateTime(), "validating the change password on next login check box");

        try {
            Assert.assertEquals(String.valueOf(inputFields.checkBox_isChecked(changePasswordOnNextLogin_ele)), String.valueOf(Boolean.FALSE));
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, changePasswordOnNextLogin_ele);
            screenshot.takeScreenshot(driver, "UserMaster", "changePasswordOnNextLogin", "changePasswordOnNextLogin");
            config.fatal(config.dateTime(), "changePasswordOnNextLogin check box should not be selected on page load: " + e.getMessage());
            highlighter.clearHighLighter(driver, changePasswordOnNextLogin_ele);
        }
    }

    public void organization(String username, String password) throws InterruptedException {
        config.info(config.dateTime(), "Validating the organization drop down field");

        String selected_organization = organizationId_ele.getText();
        System.out.println("selected_organization: " + selected_organization);
        System.out.println("getOrganizationName" + crud.getOrgName(username, password));
        if (selected_organization == "") {
            highlighter.setHighLighter(driver, organizationId_ele);
            screenshot.takeScreenshot(driver, "UserMaster", "organization", "organization");
            config.fatal(config.dateTime(), "Organization should be selected on load.");
            highlighter.clearHighLighter(driver, organizationId_ele);
        } else {
            try {
                Assert.assertEquals(selected_organization, crud.getOrgName(username, password));
            } catch (AssertionError e) {
                highlighter.setHighLighter(driver, organizationId_ele);
                screenshot.takeScreenshot(driver, "UserMaster", "organization", "organization");
                config.fatal(config.dateTime(), "Default Organization should be match with the user's organization.");
                highlighter.clearHighLighter(driver, organizationId_ele);
            }
        }

    }

    public void role() throws InterruptedException {
        config.info(config.dateTime(), "Validating the Role drop down field");
        System.out.println("Entering into role");
        roleId_ele.sendKeys(Keys.TAB);
        System.out.println("Tab selected for role");
        try {
            System.out.println("Checking for role validation message");
            Assert.assertEquals(role_error_ele, configFileReader.getPropertyValue("roleEmpty"));
        } catch (NoSuchElementException e) {
            System.out.println("No such element");
            config.fatal(config.dateTime(), "Validation message is not found when role drop down is not selected: " + e.getMessage());
        } catch (AssertionError e) {
            System.out.println("AssertionError");
            highlighter.setHighLighter(driver, roleId_ele);
//            highlighter.setHighLighter(driver, role_error_ele);
            screenshot.takeScreenshot(driver, "UserMaster", "role", "role");
            config.fatal(config.dateTime(), "Role should be validated when no role selected: " + e.getMessage());
            highlighter.clearHighLighter(driver, roleId_ele);
//            highlighter.clearHighLighter(driver, role_error_ele);
        }

        System.out.println("Before clicking role drop down");
        roleId_ele.click();
        Thread.sleep(1000);
        System.out.println("After clicking role drop down");
        List<WebElement> role_list = driver.findElements(By.xpath("//*[@role=\"option\"]"));
        if (role_list.size() == 0) {
            System.out.println("No Role in the list");
            highlighter.setHighLighter(driver, roleId_ele);
            screenshot.takeScreenshot(driver, "UserMaster", "role", "role");
            config.fatal(config.dateTime(), "Role list is empty to select");
            highlighter.clearHighLighter(driver, roleId_ele);
        } else {
            System.out.println("Selecting the role from the list: " + role_list.get(0).getText());
            role_list.get(0).click();
        }
    }

    public void department() throws InterruptedException {
        config.info(config.dateTime(), "Validating the Department drop down field");
        departmentId_ele.click();
        Thread.sleep(1000);
        List<WebElement> department_list = driver.findElements(By.xpath("//*[@role=\"option\"]"));
        if (department_list.size() != 0) {
            department_list.get(0).click();
        } else {
            config.warn(config.dateTime(), "Department List is not found in the list of department");
        }
    }

    public void designation() throws InterruptedException {
        config.info(config.dateTime(), "Validating the Designation input field");
        inputFields.inputField_with_validData(designation_ele, "Designation", "Test Designation", "designation");
    }

    public void addRoleToList() throws InterruptedException {
        config.info(config.dateTime(), "Adding the role to the list and verifying the same");
        addRoleBtn_ele.click();
        Thread.sleep(2000);

        List<WebElement> roleList = driver.findElements(By.className("role_table_list"));
        if (roleList.size() < 0) {
            config.fatal(config.dateTime(), "Added role is not showing in the list.");
        } else {
            config.info(config.dateTime(), "Verified that the added role is listed in the user role list.");
        }
    }

    public void createButton_onValidData() {
        config.info(config.dateTime(), "Validating Create button after valid data");

        try {
            Assert.assertEquals(inputFields.isButtonEnabled(createBtn_ele), "Enabled");
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, createBtn_ele);
            screenshot.takeScreenshot(driver, "UserMaster", "createButton_onValidData", "Create button");
            config.info(config.dateTime(), "Create button should enabled after providing valid data");
            highlighter.clearHighLighter(driver, createBtn_ele);
        }

        createBtn_ele.click();
    }

    public void checkForSuccessMessage(String username, String password) {
        try {
            Assert.assertEquals(commonWebDrivers.getSnackBar().getText().replace("\nDismiss", ""), configFileReader.getPropertyValue("successSnackBar"));
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot(driver, "UserMaster", "checkForSuccessMessage", "checkForSuccessMessage");
            config.info(config.dateTime(), "Success message is not found after user creation");
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, commonWebDrivers.getSnackBar());
            screenshot.takeScreenshot(driver, "UserMaster", "checkForSuccessMessage", "checkForSuccessMessage");
            config.fatal(config.dateTime(), "Snack bar message is not proper after user creation: " + e.getMessage());
            highlighter.clearHighLighter(driver, commonWebDrivers.getSnackBar());
        }

//        int orgId = Integer.parseInt(crud.getOrgID(username, password));
//
//        try {
//            Assert.assertEquals(crud.getUserAPI(email, orgId).contains(email), true);
//            config.info(config.dateTime(), "Data found in user table for created user with given emailID: " + email);
//        } catch (AssertionError e){
//            screenshot.takeScreenshot(driver, "UserMaster", "checkForSuccessMessage", "CreateNewUser");
//            config.fatal(config.dateTime(), "Data not found in user table for created user with given emailID: " + e.getMessage());
//        }
    }


    public void userMaster_test(String url, String username, String password) throws InterruptedException {
        if (driver.getCurrentUrl().equals(url)) {
            Login login = new Login();
            login.valid_login(username, password, url);
        }
//        Thread.sleep(5000);
//        getOrganizationName(username, password);
        navigate_to_backOffice(url);
        navigate_to_userMaster();
        checkAddNewButton();
        navigateToNewUserScreen();
        createButtonsStatus();
        cancelButtonStatus();
        addRoleButtonStatus();
        resetRoleButtonStatus();
        firstName();
        middleName();
        lastName();
        displayName();
        employeeID();
        email_id();
        country_code();
        phoneNumber();
        extension();
        userName();
        password();
        confirmPassword();
        changePasswordOnNextLogin();
        organization(username, password);
        role();
        department();
        designation();
        addRoleToList();
        createButton_onValidData();
        checkForSuccessMessage(username, password);
    }


    public void invalidPassword(WebElement ele, WebElement error_ele, String propertyKey, String methodName, String name) {
        String password_invalid_pattern[] = {"password", "Password", "Password0", "Password@", "password1", "password@", "password@1"};
        for (int i = 0; i < password_invalid_pattern.length; i++) {
            ele.clear();
            ele.sendKeys(password_invalid_pattern[i]);
            ele.sendKeys(Keys.TAB);
            try {
                Assert.assertEquals(error_ele.getText(), configFileReader.getPropertyValue(propertyKey));
            } catch (AssertionError e) {
                highlighter.setHighLighter(driver, ele);
                highlighter.setHighLighter(driver, error_ele);
                screenshot.takeScreenshot(driver, this.getClass().getSimpleName(), methodName, name);
                config.fatal(config.dateTime(), name + " validation is not proper for given password pattern: " + password_invalid_pattern[i] + " " + e.getMessage());
                highlighter.clearHighLighter(driver, ele);
                highlighter.clearHighLighter(driver, error_ele);
            }
        }

    }


}
