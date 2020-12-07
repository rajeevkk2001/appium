package ApplicationManager;

import Utils.ConfigReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AppManager {
    public final static int SHORT_TIMEOUT = 5;
    public final static int LONG_TIMEOUT = 30;

   private static Platform platform = null;
   private static AndroidDriver<AndroidElement> androidDriver;
   private static IOSDriver<IOSElement> iOSDriver;
   private static WebDriverWait waitDriver;

   public static Platform getPlatform() {
        if (platform != null) {
            return platform;
        }
        throw new RuntimeException("You must call AppManager.startApp() before using the platform");
    }

    public static AndroidDriver getAppiumDriver() {
        if (platform == Platform.ANDROID && androidDriver != null) {
            return androidDriver;
        }

        if (platform == Platform.IOS && iOSDriver != null) {
          //  return iOSDriver;
        }

        throw new RuntimeException("You must call AppManager.startApp() before using the driver");
    }

    public static WebDriverWait getWaitDriver() {
        if ((platform == Platform.ANDROID && androidDriver != null)
                || (platform == Platform.IOS && iOSDriver != null)) {
            return waitDriver;
        }

        throw new RuntimeException("You must call AppManager.startApp() before using the waitDriver");
    }

    public synchronized static void setUp(String index)  {
       // stopApp();
        String username =null;
        String accessKey=null; ;
        String app  =null;
        String envPlatform = ConfigReader.getPlatform();
        if (envPlatform == null) {
            throw new RuntimeException("The 'PLATFORM'  variable is not set in config.properties");
        } else if (envPlatform.equals("android")) {
            platform = Platform.ANDROID;
        } else if (envPlatform.equals("ios") || envPlatform.equals("ios-simulator")) {
            platform = Platform.IOS;
        } else {
            throw new RuntimeException("Platform not supported: " + envPlatform);
        }

        JSONParser parser = new JSONParser();
        JSONObject config = null;
        try {
            config = (JSONObject) parser.parse(new FileReader("src/test/resources/testConfig/capability.conf.json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        JSONArray envs = (JSONArray) config.get("environments");
        Map<String, String> envCapabilities = (Map<String, String>) envs.get(Integer.parseInt(index));
        Iterator it = envCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
        }

        Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
        it = commonCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(capabilities.getCapability(pair.getKey().toString()) == null){
                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }
        }

        if(username == null) {
            username = (String) config.get("username");
        }

        if(accessKey == null) {
            accessKey = (String) config.get("access_key");
        }


        if(app != null && !app.isEmpty()) {
            capabilities.setCapability("app", app);
        }

        switch(platform){
                case ANDROID:
                    try {
                        String server =  "https://"+username+":"+accessKey+"@"+config.get("server")+"/wd/hub";
                        System.out.println(server);

                        androidDriver = new AndroidDriver(new URL(server), capabilities);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    androidDriver.manage().timeouts().implicitlyWait(SHORT_TIMEOUT, TimeUnit.SECONDS);
                    waitDriver = new WebDriverWait(androidDriver, LONG_TIMEOUT);
                break;
            case IOS:
                try {
                    iOSDriver = new IOSDriver(new URL("http://"+username+":"+accessKey+"@"+config.get("server")+"/wd/hub"), capabilities);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                iOSDriver.manage().timeouts().implicitlyWait(SHORT_TIMEOUT, TimeUnit.SECONDS);
                waitDriver = new WebDriverWait(iOSDriver, LONG_TIMEOUT);

                break;
        }
    }

   /* public static void stopApp() {
        if (androidDriver != null) {
            androidDriver.quit();
            androidDriver = null;
        }
        if (iOSDriver != null) {
            iOSDriver.quit();
            iOSDriver = null;
        }

        waitDriver = null;
    }
*/

}
