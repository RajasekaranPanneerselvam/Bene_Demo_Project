package afta.src.test.java.com.AFTA.test;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ScreenerVisualTests {
//	Map screener = new HashMap();
//    screener.put("name","My Test");
//    screener.put("resolution","1280x1024");
//    screener.put("apiKey", "<your-api-key>");
//    screener.put("group", "<your-group-id>");
    DesiredCapabilities capabilities = DesiredCapabilities.firefox();
    capabilities.setCapability("screener", screener);
    WebDriver driver = new RemoteWebDriver(new URL("https://hub.screener.io/wd/hub"),
         capabilities);
    driver.get("https://screener.io");
    ((JavascriptExecutor) driver).executeScript("/*@screener.snapshot*/", "Home");
    driver.quit();
}
