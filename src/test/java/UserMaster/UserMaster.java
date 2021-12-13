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

public class UserMaster extends Config {

    @FindBy(xpath = "//*[text()='User']")
    WebElement userMaster;

    Config config = new Config();
    static CommonWebDrivers commonWebDrivers = new CommonWebDrivers();
    static WebDriverWait wait;

    public UserMaster(){
        log.info("\n****Initializing User Master Test Cases****");
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 30);
    }

    public void navigate_to_userMaster() throws InterruptedException {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("hex_loader")));
        config.info(config.dateTime(), "Opening the left menu by clicking side toggle");
        commonWebDrivers.toggle_sidebar().click();

        config.info(config.dateTime(), "Navigating to Back office by clicking Back office menu");
        commonWebDrivers.backOffice_menu().click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("hex_loader")));

        config.info(config.dateTime(), "Moving mouse to the Organization on header navigation");
        new Actions(driver).moveToElement(commonWebDrivers.organization_nav()).perform();
        Thread.sleep(2000);

        config.info(config.dateTime(), "Moving mouse to ghe User link in organization navigation menu");
        new Actions(driver).moveToElement(userMaster).perform();

        config.info(config.dateTime(), "Clicking the User master link");
        userMaster.click();
        Thread.sleep(5000);
    }


    public void userMaster_test(String url, String username, String password) throws InterruptedException {
        if(driver.getCurrentUrl().equals(url)){
            Login login = new Login();
            login.valid_login(username, password, url);
        }
        Thread.sleep(5000);
        navigate_to_userMaster();
    }
}
