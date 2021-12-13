package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class Screenshot {

    public void takeScreenshot(WebDriver driver, String className, String methodName, String name){
        TakesScreenshot ts = (TakesScreenshot) driver;
        File file = ts.getScreenshotAs(OutputType.FILE);

        String directory = new File("src\\FailureScreenshot").getAbsolutePath();
        File path = new File(directory);

        if(!path.exists()){
            new File(directory).mkdir();
        }

        try{
            FileUtils.copyFile(file, new File("src\\FailureScreenshot" + "\\" + className + "\\" + methodName + "\\" + name + ".png"));
        } catch (Exception e) {
            System.out.println("Exception on screenshot method: " + e.getMessage());;
        }
    }

}
