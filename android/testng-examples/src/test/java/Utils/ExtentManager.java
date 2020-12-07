package Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static Utils.Constants.reportPath;

public class ExtentManager {


        private static ExtentReports extent;
         DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        final static String dateTimeString = new SimpleDateFormat("MMddhhmm").format(new Date());
        public static final String PATH = System.getProperty("user.dir");
       /* public static ExtentReports getInstance() {
            if (extent == null)
                createInstance();
            return extent;
        }*/


        public static ExtentReports createInstance() {
            new File(reportPath);

            try {
                FileUtils.cleanDirectory(new File(Constants.screenShotPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String testType = "Regression";
            String environment = "QA";

            ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportPath);
            htmlReporter.loadXMLConfig(new File(Constants.extentConfigPath));

            htmlReporter.config().setChartVisibilityOnOpen(false);
            htmlReporter.config()
                    .setJS("$('document').ready(function(){$('ul.side-nav li:nth-child(2)').trigger('click');});");
            htmlReporter.config().setCSS(".featherlight-image {width:100% !important; height:100% !important;}");
            htmlReporter.config().setTheme(Theme.DARK);
            htmlReporter.config().setDocumentTitle(
                    ("LennoxPros :: Mobile Automation Execution Report - " ));

            htmlReporter.config().setReportName(("LennoxPros " )
                    + " Automation Test Report - Test Type: <span class='label blue darken-3'>" + testType
                    + "</span> - Environment: <span class='label blue darken-3'>" + environment + "</span>");
            // chart location - top, bottom
            htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);


            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);

            return extent;
        }



    }