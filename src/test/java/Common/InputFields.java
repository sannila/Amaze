package Common;


import base.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.ConfigFileReader;
import utils.Highlighter;
import utils.Screenshot;

import javax.lang.model.element.Element;

public class InputFields extends Config {

//    @FindBy(className = "mat-error")
//    WebElement matError_ele;

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
        config.info(config.dateTime(), "Validating the " + inputFieldName + " with valid data");
        ele.clear();
        ele.sendKeys(validData);
        ele.sendKeys(Keys.TAB);

        config.info(config.dateTime(), "Valid data send to the " + inputFieldName + " input field: " + validData);
//        Boolean isErrorDisplayed = isElementPresent(matError_ele);
//        try {
//            Assert.assertEquals(isErrorDisplayed, Boolean.FALSE);
//        } catch (AssertionError e) {
//            highlighter.setHighLighter(driver, ele);
//            highlighter.setHighLighter(driver, matError_ele);
//            screenshot.takeScreenshot(driver, getClassName(), inputFieldName, (methodName + "validData"));
//            config.fatal(config.dateTime(), inputFieldName + " should not validate with valid data: " + validData + " " + e.getMessage());
//            highlighter.clearHighLighter(driver, ele);
//            highlighter.clearHighLighter(driver, matError_ele);
//        } catch (NoSuchElementException e){
//            System.out.println("");
//        }
    }

    public String isButtonEnabled(WebElement btn_ele){
        if(btn_ele.isEnabled()){
            return "Enabled";
        } else {
            return "Disabled";
        }
    }

    public boolean isElementPresent(WebElement ele){
        try {
            ele.isDisplayed();
            return true;
        } catch (NoSuchElementException e){
            return false;
        }
    }

    /**
     * with empty data for mandatory fields
     *
     * @param ele            // WebElement of the input field
     * @param inputFieldName // field name
     * @param propertyKey    // key value for validation message from property file
     */
    public void inputField_with_emptyData(WebElement ele, WebElement btn, WebElement err_ele, String inputFieldName, String propertyKey, String methodName) {
        config.info(config.dateTime(), "Validating the " + inputFieldName + " with empty data");
        ele.clear();
        ele.sendKeys(Keys.TAB);
//        btn.click();
        try {
            Assert.assertEquals(err_ele.getText(), configFileReader.getPropertyValue(propertyKey));
        } catch (NoSuchElementException e) {
            screenshot.takeScreenshot(driver, getClassName(), inputFieldName, (methodName + "_emptyData"));
            config.fatal(config.dateTime(), inputFieldName + " validation is not found for empty data: " + e.getMessage());
        } catch (AssertionError e) {
            highlighter.setHighLighter(driver, ele);
            highlighter.setHighLighter(driver, err_ele);
            screenshot.takeScreenshot(driver, getClassName(), inputFieldName, (methodName + "_emptyData"));
            config.fatal(config.dateTime(), inputFieldName + " input field validation is not proper for empty field: " + e.getMessage());
            highlighter.clearHighLighter(driver, ele);
            highlighter.clearHighLighter(driver, err_ele);
        }
    }

    /**
     * with invalid data
     *
     * @param ele            // WebElement of the input field
     * @param inputFieldName // field name
     * @param propertyKey    // key value for validation message from property file
     */
    public void inputField_with_invalidData(WebElement ele, WebElement btn, WebElement err_ele, String inputFieldName, String propertyKey, String methodName) {
        config.info(config.dateTime(), "Validating the " + inputFieldName + " with invalid data");
        int j = 0;
        for (int i = 1; i < 35; i++) {
            String data = posData.getSheet("InvalidCharacters", i, j);
            config.info(config.dateTime(), "Validating the " + inputFieldName + " input field with " + data);
            ele.clear();
            ele.sendKeys(data);
            ele.sendKeys(Keys.TAB);
//            btn.click();

            try {
                Assert.assertEquals(err_ele.getText(), configFileReader.getPropertyValue(propertyKey));
            } catch (NoSuchElementException e) {
                screenshot.takeScreenshot(driver, getClassName(), inputFieldName, (methodName + "_invalidData"));
                config.fatal(config.dateTime(), inputFieldName + " validation is not found for invalid data: " + e.getMessage());
            }  catch (AssertionError e) {
                highlighter.setHighLighter(driver, ele);
                highlighter.setHighLighter(driver, err_ele);
                screenshot.takeScreenshot(driver, getClassName(), inputFieldName, (methodName + "_" + i));
                config.fatal(config.dateTime(), inputFieldName + " input field validation is not proper for given data: " + e.getMessage());
                highlighter.clearHighLighter(driver, ele);
                highlighter.clearHighLighter(driver, err_ele);
            }
        }
    }

    public boolean checkBox_isChecked(WebElement ele){
         if(ele.isSelected()){
             return true;
         } else {
             return false;
         }
    }

    public String getClassName() {
        return this.getClass().getSimpleName();
    }
}
