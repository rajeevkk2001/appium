package Pages;

import ApplicationManager.PlatformQuery;
import Utils.DriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;
import static org.junit.Assert.assertTrue;


public class LocationServicePage extends BasePage {
    DriverUtils commonUtils = new DriverUtils();

    public LocationServicePage() {
    }

    @Override
    public PlatformQuery trait() {
        return new PlatformQuery()
                .setAndroid(By.xpath("//*[@class='android.widget.LinearLayout']"))
                .setiOS(By.id("Login"));
    }

    public LocationServicePage checkLocationPopUp()  {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String locationPopUpXpath = "//*[@class='android.widget.LinearLayout']";

        if(commonUtils.isElementPresent(By.xpath(locationPopUpXpath),"Location popup")){
            WebElement permissionMsg = driver.findElementByXPath(locationPopUpXpath);
            if(permissionMsg.isEnabled()) System.out.println(" Location popup is enabled");
            assertTrue (permissionMsg.isEnabled());
        }
        commonUtils.switchtoNativeAppContext();
        if(commonUtils.isElementPresent(By.xpath(locationPopUpXpath),"Location popup")){
            WebElement locationPopUp = driver.findElementByXPath(locationPopUpXpath);
            assertTrue (locationPopUp.isDisplayed());
        }else{
            System.out.println(" Not able to locate the element - Location popup");
        }

       String locationMessageXpath = "//*[@text='This app may want to access your location all the time, even when you’re not using the app. Allow in settings.']";
       List<WebElement> location = driver.findElements(By.xpath(locationMessageXpath));
       if(location.size()==0) System.out.println(" Location Access links not displayed");
       if(location.size()==0) return this;
       if(commonUtils.isElementPresent(By.xpath(locationMessageXpath),"Location Message")) {
           WebElement locationMsg = driver.findElement(By.xpath(locationMessageXpath));
           System.out.println( " Location Message displayed is "+locationMsg.getText());
       }else{
           System.out.println(" Not able to locate the element - Location Message");
       }

        String whileUisngAppXpath = "//*[@resource-id='com.android.permissioncontroller:id/permission_allow_foreground_only_button']";
        if(commonUtils.waitForElement(By.xpath(whileUisngAppXpath))){
            WebElement detailedMsg = driver.findElement(By.xpath("//*[@text='While using the app']"));
            System.out.println(detailedMsg.getText());
            if(commonUtils.elementClickable(detailedMsg)){
                System.out.println("detailedMsg element is clickable");
            }else{
                System.out.println("detailedMsg element is NOT clickable");
            }

            if(commonUtils.elementEnabled(detailedMsg)){
                System.out.println("detailedMsg element is enabled");
            }else{
                System.out.println("detailedMsg element is NOT enabled");
            }

            detailedMsg.click();
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }else{
            System.out.println("Unable to locate -timeout- while using");
        }

        return this;
    }


}
