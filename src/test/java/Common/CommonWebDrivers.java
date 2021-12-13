package Common;

import base.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CommonWebDrivers extends Config {

    /**
     * Element of Left menu toggle button
     * @return
     */
    public static WebElement toggle_sidebar() {
        return driver.findElement(By.className("toggle-sidebar-folded"));
    }

    /**
     * Element of Back office link in the left menu
     * @return
     */
    public static WebElement backOffice_menu() {
        return driver.findElement(By.xpath("//*[text()=' Back Office ']"));
    }

    /**
     * Element for organization navigation link in the back office header
     * @return
     */
    public static WebElement organization_nav(){
        return driver.findElement(By.xpath("//*[text()='Organization']"));
    }

    /**
     * Element for the show filter button of the list page
     * @return
     */
    public static WebElement showFilterButton(){
        return driver.findElement(By.xpath("//*[text()=' Show Filter ']"));
    }

    public static String snackBarMessage(){
        String message = driver.findElement(By.xpath("//simple-snack-bar//span[text()=\"Incorrect username or password.\"]")).getText();
        return message;
    }

}
