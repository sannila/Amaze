package Common;

import base.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CommonWebDrivers extends Config {

    /**
     * Element of loader
     */
    public static WebElement hexaLoader(){
        return driver.findElement(By.id("hex_loader"));
    }


    /**
     * Element of Left menu toggle button
     *
     */
    public static WebElement toggle_sidebar() {
        return driver.findElement(By.className("toggle-sidebar-folded"));
    }

    public static WebElement toggle_sidebar_2(){ return driver.findElement(By.xpath("//*[text()='chevron_right']"));}
    public static WebElement toggle_sidebar_left(){return driver.findElement(By.xpath("//*[text()='chevron_left']"));}
    /**
     * Element of Back office link in the left menu
     *
     */
    public static WebElement backOffice_menu() {
        return driver.findElement(By.xpath("//*[text()=' Back Office ']"));
    }

    /**
     * Element for organization navigation link in the back office header
     *
     */
    public static WebElement organization_nav(){
        return driver.findElement(By.xpath("//*[text()='Organization']"));
    }

    /**
     * Element for the show filter button of the list page
     *
     */
    public static WebElement showFilterButton(){
        return driver.findElement(By.xpath("//*[text()=' Show Filter ']"));
    }

    public static String snackBarMessage(){
        String message = driver.findElement(By.xpath("//simple-snack-bar//span[text()=\"Incorrect username or password.\"]")).getText();
        return message;
    }


    public WebElement getSnackBar(){
        return driver.findElement(By.className("snackBar-message"));
    }

    public static WebElement addNewButton(){
        return driver.findElement(By.className("addNewButton"));
    }

    public static WebElement h2(){
        return driver.findElement(By.className("h2"));
    }

    public static WebElement getUserName(){
        return driver.findElement(By.className("username"));
    }

    public static WebElement getOrganizationName(){
        return driver.findElement(By.className("organization_class"));
    }



}
