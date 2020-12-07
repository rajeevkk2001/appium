package Tests;

import Pages.HomePage;
import Pages.LocationServicePage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.lang.Object;

public class Test2 extends BaseTest {

    @DataProvider(name = "testHomepageData")
    public Object[] test1homepage(){
        Object[] testData = commonUtils.dataProvider("select * from Search where Execution = 'Y' ");
        return testData;
    }
    @Test(dataProvider = "testHomepageData")
    public void testHomePage(HashMap<String,String> testData) throws IOException, InterruptedException {
        logger = reporter.createTest("Test2","test2 Desc")
                .assignAuthor("RKK").assignCategory("Mobile");
        new LocationServicePage()
                .checkLocationPopUp();
        System.out.println(testData.get("userName"));
        HomePage homePage =  new HomePage(logger);
        homePage.checkOEMLinkUnAuth();
        homePage.searchProdut();

    }


    @Test
    public void testHomePage2() throws IOException, InterruptedException {
        logger = reporter.createTest("Test3","test3 Desc")
                .assignAuthor("RKK").assignCategory("Mobile");
        new LocationServicePage()
                .checkLocationPopUp();
        HomePage homePage =  new HomePage(logger);
        homePage.checkOEMLinkUnAuth();
        homePage.searchProdut();

    }

}