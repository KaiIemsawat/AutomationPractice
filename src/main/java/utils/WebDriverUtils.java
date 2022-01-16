package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class WebDriverUtils {
    //singleton WebDriver
    private static WebDriver driver = null;

    private WebDriverUtils() {
    }

    public static WebDriver getDriver() {
        if (driver == null)
            initializeDriver();
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.close();
            driver = null;
        }
    }

    private static void initializeDriver() {
        if (ConfigReader.readProperty("runInSaucelabs").equals("true")) {
            initializeRemoteDriver();
        } else {
            initializeLocalDriver();
        }
    }


    private static void initializeLocalDriver() {
        switch (ConfigReader.readProperty("browser")) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "ie":
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
        }
//        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    static String browserType = ConfigReader.readProperty("browser");
    static String browserVersion = ConfigReader.readProperty("browserVersion");
    static String os = ConfigReader.readProperty("operatingSystem");
    static String saucelabURL = "https://oauth-g9mailbox-720f7:b56fb272-7c5c-4dda-8e57-3abf29d72f57@ondemand.us-west-1.saucelabs.com:443/wd/hub";

    public static WebDriver initializeRemoteDriver() {

        try {
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("browserName", browserType);
            capabilities.setCapability("version", browserVersion);
            capabilities.setCapability("platform", os);
            driver = new RemoteWebDriver(new URL(saucelabURL), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }

}