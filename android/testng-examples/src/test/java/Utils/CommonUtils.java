package Utils;

import ApplicationManager.AppManager;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class CommonUtils {
    AppiumDriver driver;
    public CommonUtils(){
        driver = AppManager.getAppiumDriver();
    }
    public MediaEntityModelProvider takeScreenShot() {
        MediaEntityModelProvider mediaEntityModelProvider = null;
        try {
            mediaEntityModelProvider = MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot((AndroidDriver) driver)).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaEntityModelProvider;
    }



    private  String getScreenshot(AndroidDriver driver) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File Dest = new File(Constants.screenShotPath + System.currentTimeMillis() + ".png");
        String errflpath = Dest.getAbsolutePath();
        FileUtils.copyFile(scrFile, Dest);
        return errflpath;
    }

    public static Object[] dataProvider(String query) {
        System.out.println(" inside data provider");
        try {
            ArrayList<LinkedHashMap<String, String>> testDataSheet;
            System.out.println("Query is --->" + query  );
            testDataSheet = readExcel(query );
            Object[] testData = new Object[testDataSheet.size()];
            for (int i = 0; i < testDataSheet.size(); i++) {
                testData[i] = testDataSheet.get(i);
            }

            return  testData;
        } catch (Exception e) {
            System.out.println("Error while reading test data "+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<LinkedHashMap<String, String>> readExcel(String query) {
        Fillo fillo = new Fillo();
        Connection connection = null;
        try {
            connection = fillo.getConnection(Constants.testDataPath);
        } catch (FilloException e) {
            e.printStackTrace();
        }
        ArrayList<LinkedHashMap<String, String>> rows = new ArrayList<>();
        ArrayList<String> cols;
        try {
            Recordset recordSet = connection.executeQuery(query);
            cols = recordSet.getFieldNames();
            while (recordSet.next()) {
                LinkedHashMap<String, String> row = new LinkedHashMap<>();
                for (String colName : cols) {
                    try {
                        String val = recordSet.getField(colName);
                        if (val != null)
                            row.put(colName, val);
                        else
                            row.put(colName, null);
                    } catch (Exception e) {

                    }
                }
                rows.add(row);
            }

        } catch (Exception e) {
            if (e.getMessage().equals("No records found")) e.printStackTrace();
        }
        connection.close();
        fillo = null;
        return rows;
    }
}