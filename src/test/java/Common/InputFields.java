package Common;


import base.Config;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.ConfigFileReader;
import utils.Highlighter;
import utils.Screenshot;

public class InputFields extends Config {

    @FindBy(className = "mat-error")
    WebElement matError_ele;

    WebDriverWait wait;
    static Config config = new Config();
    Screenshot screenshot = new Screenshot();
    Highlighter highlighter = new Highlighter();
    ConfigFileReader configFileReader = new ConfigFileReader("src/main/resources/BackOffice/backofficeValidationMessages.properties");


    public InputFields(){
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 30);
    }

    /**
     * below methods to validate the input field
     */

    /**
     * with valid data
     *
     * @param ele            // WebElement of the input field
     * @param inputFieldName // field name
     * @param validData      // proper data for the input field
     */
    public void inputField_with_validData(WebElement ele, String inputFieldName, String validData, String methodName) {
        config.info(config.dateTime(), "Validating the " + inputFieldName + "with valid data");
        ele.clear();
        ele.sendKeys(validData);
        ele.sendKeys(Keys.TAB);

        config.info(config.dateTime(), "Valid data send to the " + inputFieldName + " input field: " + validData);
        Boolean isErrorDisplayed = matError_ele.isDisplayed();
        try {
            Assert.assertEquals(isErrorDisplayed, Boolean.FALSE);
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, ele);
            highlighter.setHighLighter(driver, matError_ele);
            screenshot.takeScreenshot(driver, getClassName(), inputFieldName, (methodName + "validData"));
            config.fatal(config.dateTime(), inputFieldName + " should not validate with valid data: " + validData);
            highlighter.clearHighLighter(driver, ele);
            highlighter.clearHighLighter(driver, matError_ele);
        }
    }

    /**
     * with empty data for mandatory fields
     *
     * @param ele            // WebElement of the input field
     * @param inputFieldName // field name
     * @param propertyKey    // key value for validation message from property file
     */
    public void inputField_with_emptyData(WebElement ele, String inputFieldName, String propertyKey, String methodName) {
        config.info(config.dateTime(), "Validating the " + inputFieldName + " with empty data");
        ele.clear();
        ele.sendKeys(Keys.TAB);
        try {
            Assert.assertEquals(matError_ele.getText(), propertyKey);
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, ele);
            highlighter.setHighLighter(driver, matError_ele);
            screenshot.takeScreenshot(driver, getClassName(), inputFieldName, (methodName + "_emptyData"));
            config.fatal(config.dateTime(), inputFieldName + " input field validation is not proper for given data: " + e.getMessage());
            highlighter.clearHighLighter(driver, ele);
            highlighter.clearHighLighter(driver, matError_ele);
        }
    }

    /**
     * with invalid data
     *
     * @param ele            // WebElement of the input field
     * @param inputFieldName // field name
     * @param propertyKey    // key value for validation message from property file
     */
    public void inputField_with_invalidData(WebElement ele, String inputFieldName, String propertyKey, String methodName) {
        config.info(config.dateTime(), "Validating the " + inputFieldName + " with invalid data");
        int j = 0;
        for (int i = 1; i < 35; i++) {
            String data = posData.getSheet("InvalidCharacters", i, j);
            config.info(config.dateTime(), "Validating the " + inputFieldName + " input field with " + data);
            ele.clear();
            ele.sendKeys(data);
            ele.sendKeys(Keys.TAB);

            try {
                Assert.assertEquals(matError_ele.getText(), configFileReader.getPropertyValue(propertyKey));
            } catch (AssertionError e) {
                highlighter.setHighLighter(driver, ele);
                highlighter.setHighLighter(driver, matError_ele);
                screenshot.takeScreenshot(driver, getClassName(), inputFieldName, (methodName + "_" + i));
                config.fatal(config.dateTime(), inputFieldName + " input field validation is not proper for given data: " + e.getMessage());
                highlighter.clearHighLighter(driver, ele);
                highlighter.clearHighLighter(driver, matError_ele);
            }
        }
    }

    public String getClassName() {
        return this.getClass().getSimpleName();
    }
}
