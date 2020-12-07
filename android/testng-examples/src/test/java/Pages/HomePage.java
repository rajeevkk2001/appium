package Pages;

import ApplicationManager.PlatformQuery;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.util.List;

public class HomePage  extends BasePage {

    public  ExtentTest logger;
    public HomePage(ExtentTest logger){
        this.logger = logger;
    }
    @Override
    public PlatformQuery trait() {
        return new PlatformQuery()
                .setAndroid(By.xpath("//*[@text='LennoxPros Logo']"))
                .setiOS(By.id("Login"));
    }

    public void searchProdut() throws IOException {
        logger.log(Status.PASS, "Searching for the product ");
         new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='android.widget.TextView']")));
        driver.findElement(By.xpath("//*[@class='android.webkit.WebView']"));
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='LennoxPros Logo']")));
        List<WebElement> element = driver.findElements(By.xpath("//*[@class='android.widget.EditText']"));

        if( element.size()==1 ){
            logger.log(Status.PASS,  "Search Text is verified",commonUtils.takeScreenShot());
             System.out.println(element.get(0).isEnabled());
            element.get(0).click();
            element.get(0).clear();

            logger.log(Status.PASS,   "Clearing the Search text",commonUtils.takeScreenShot());
            element.get(0).sendKeys("83M00");

            logger.log(Status.PASS,   "Entered the catalog number 83M00",commonUtils.takeScreenShot());

        }
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='Search'][@class='android.view.View']")));
        driver.findElement(By.xpath("//*[@class='android.widget.Button'][@index='1']")).click();
        logger.log(Status.PASS,  "Clicked on the search button",commonUtils.takeScreenShot());
        try {
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement prodName = driver.findElement(By.xpath("//*[@text='Lennox LB-109531A SureLight Ignition Control Replacement Kit']"));
        if(!prodName.getText().isEmpty()){
            logger.log(Status.PASS,  "PDP page is displayed",commonUtils.takeScreenShot());
        }else{
            logger.log(Status.FAIL,  "PDP page Verification Failed displayed",commonUtils.takeScreenShot());
        }
    }



    public void checkOEMLinkUnAuth() throws IOException, InterruptedException {
        //*[@text='OEM Repair Parts']
        //*[@text='Warranty Lookup']
        //*[@contentDescription='Document Library']
        //*[@contentDescription='AHRI/HRAI Matchup']
        //*[@text='Cross Reference']
        //*[@text='Error Code Lookup']


        WebElement oemLink =  driver
                .findElementByAndroidUIAutomator("new UiScrollable("
                        + "new UiSelector()).scrollIntoView("
                        + "new UiSelector().text(\"Quick Order\"));");
        logger.log(Status.PASS,  "Scrolled to Quick Order",commonUtils.takeScreenShot());
        Thread.sleep(2000);
         oemLink.click();
        logger.log(Status.PASS,  "Clicked on OEM Link",commonUtils.takeScreenShot());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.log(Status.PASS,  "Navigated to  Signin page",commonUtils.takeScreenShot());
    }

}
