package Utils;

import ApplicationManager.AppManager;
import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

public class DriverUtils {
    private boolean onAndroid;
    private boolean oniOS;
    AppiumDriver driver;

    public DriverUtils(){
         driver = AppManager.getAppiumDriver();
    }


    public  void switchtoAlert(){
         try{
            Alert a = new WebDriverWait(driver, 20).until(ExpectedConditions.alertIsPresent());
            if(a!=null){
                System.out.println("Alert is present");
                driver.switchTo().alert().accept();

            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Alert isn't present!!");

        }
    }

    public  void switchtoWebViewContext() {
        Set<String> contexts = driver.getContextHandles();
        System.out.println(contexts);
        for (String context : contexts) {
                if (!context.equals("NATIVE_APP")) {
                driver.context((String) contexts.toArray()[1]);
                break;
            }
        }
    }

    public  void switchtoNativeAppContext() {
        Set<String> contexts = driver.getContextHandles();
        for (String context : contexts) {
            System.out.println(contexts);
            if (!context.equals("NATIVE_APP")) {
                driver.context("NATIVE_APP");
                break;
            }
        }
    }

    public boolean isElementPresent(By by, String elementName) {
        try {
            driver.findElement(by);
            Assert.assertTrue("Verified the presence of the element "+elementName,true);
            return true;
        } catch (NoSuchElementException e) {
            Assert.assertTrue("Unable to locate the element "+elementName,false);
            return false;
        }

    }

    public boolean waitForElement(By by){
        try {
            new WebDriverWait(driver, 25).until(ExpectedConditions.presenceOfElementLocated(by));
            return true;
        }catch (Exception e){
            e.printStackTrace();
          return false;
        }
    }


    public boolean elementClickable(WebElement element) {
        return Boolean.valueOf(element.getAttribute("clickable"));

    }
    public boolean elementEnabled(WebElement element) {
        return Boolean.valueOf(element.getAttribute("enabled"));

    }

}
