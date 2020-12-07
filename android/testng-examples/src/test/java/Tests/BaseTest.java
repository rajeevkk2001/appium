package Tests;

import ApplicationManager.AppManager;
import ApplicationManager.Platform;
//import com.microsoft.appcenter.appium.Factory;
import Utils.CommonUtils;
import Utils.Constants;
import Utils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public  class  BaseTest {
    public boolean onAndroid;
    public boolean oniOS;
    public AndroidDriver driver;
    public WebDriverWait wait;
    public static  ExtentTest logger;
    public  ExtentReports reporter;
    public  CommonUtils commonUtils;
    @BeforeSuite
    public void clearReport(){

    }
    @BeforeMethod(alwaysRun=true)
    @Parameters(value={"deviceIndex"})
    public synchronized void setUp(String deviceIndex)  {
        AppManager.setUp(deviceIndex);
        onAndroid = AppManager.getPlatform() == Platform.ANDROID;
        oniOS = AppManager.getPlatform() == Platform.IOS;
        driver = (AndroidDriver) AppManager.getAppiumDriver();
        wait = AppManager.getWaitDriver();
    }
    @BeforeClass
    public synchronized void beforeClass(){
      this.reporter = ExtentManager.createInstance();
    }
   /* @BeforeTest
    public void beforeEachTest() {

    }*/
   @AfterMethod(alwaysRun=true)
   public synchronized void tearDown() throws Exception {
       driver.quit();
   }
    @AfterClass
    public synchronized  void endTest()
    {
        reporter.flush();
    }
}
