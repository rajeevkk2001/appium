package Tests;

import Pages.HomePage;
import Pages.LocationServicePage;
import org.testng.annotations.Test;
import java.io.IOException;

public class HomePageTest extends BaseTest {

    @Test
    public void testHomePage() throws IOException, InterruptedException {
        logger = reporter.createTest("Test1","test Desc")
        .assignAuthor("RKK").assignCategory("Mobile");
        new LocationServicePage()
                .checkLocationPopUp();
        HomePage homePage =  new HomePage(logger);
        homePage.checkOEMLinkUnAuth();
        homePage.searchProdut();

    }
    @Test
    public void testHomePage1() throws IOException, InterruptedException {
        logger = reporter.createTest("Test4","test4 Desc")
                .assignAuthor("RKK").assignCategory("Mobile");
        new LocationServicePage()
                .checkLocationPopUp();
        HomePage homePage =  new HomePage(logger);
        homePage.checkOEMLinkUnAuth();
        homePage.searchProdut();

    }

}