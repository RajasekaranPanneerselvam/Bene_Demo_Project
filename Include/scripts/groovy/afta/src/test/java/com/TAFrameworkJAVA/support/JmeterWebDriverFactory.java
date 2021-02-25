package com.TAFrameworkJAVA.support;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;

/**
 * WebdriverFactory class used to get a web driver instance, depends on the user requirement as driverHost, driverPort and browserName we adding the desiredCapabilities and other static action
 * initialized here and some methods used to retrieve the Hub and node information. It also consists page wait load for images/frames/document
 */

public class JmeterWebDriverFactory {

	static URL hubURL;
	static String driverHost;
	static String driverPort;
	static String browserName;
	static String deviceName;

	static ChromeOptions opt = new ChromeOptions();
	static FirefoxProfile fp = new FirefoxProfile();
	static DesiredCapabilities firefoxCapabilities = DesiredCapabilities.firefox();
	static DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
	static DesiredCapabilities safariCapabilities = DesiredCapabilities.safari();
	static DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();

	public static int maxPageLoadWait = 180;
	public static ExpectedCondition <Boolean> documentLoad;
	public static ExpectedCondition <Boolean> framesLoad;
	public static ExpectedCondition <Boolean> imagesLoad;

	static {
		documentLoad = new ExpectedCondition <Boolean>() {
			public final Boolean apply(final WebDriver driver) {
				final JavascriptExecutor js = (JavascriptExecutor) driver;
				boolean docReadyState = false;
				try {
					docReadyState = (Boolean) js.executeScript("return (function() { if (document.readyState != 'complete') {  return false; } if (window.jQuery != null && window.jQuery != undefined && window.jQuery.active) { return false;} if (window.jQuery != null && window.jQuery != undefined && window.jQuery.ajax != null && window.jQuery.ajax != undefined && window.jQuery.ajax.active) {return false;}  if (window.angular != null && angular.element(document).injector() != null && angular.element(document).injector().get('$http').pendingRequests.length) return false; return true;})();");
				}
				catch (WebDriverException e) {
					docReadyState = true;
				}
				return docReadyState;

			}
		};

		imagesLoad = new ExpectedCondition <Boolean>() {
			public final Boolean apply(final WebDriver driver) {
				boolean docReadyState = true;
				try {
					JavascriptExecutor js;
					List <WebElement> images = driver.findElements(By.cssSelector("img[src]"));
					for (int i = 0; i < images.size(); i++) {
						try {
							js = (JavascriptExecutor) driver;
							docReadyState = docReadyState && (Boolean) js.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", images.get(i));
							if (!docReadyState) {
								break;
							}
						}
						catch (StaleElementReferenceException e) {
							images = driver.findElements(By.cssSelector("img[src]"));
							i--;
							continue;
						}
						catch (WebDriverException e) {

							// setting the true value if any exception arise
							// Ex:: inside frame or switching to new windows or
							// switching to new frames
							docReadyState = true;
						}
					}
				}
				catch (WebDriverException e) {
					docReadyState = true;
				}
				return docReadyState;
			}
		};

		framesLoad = new ExpectedCondition <Boolean>() {
			public final Boolean apply(final WebDriver driver) {
				boolean docReadyState = true;
				try {
					JavascriptExecutor js;
					List <WebElement> frames = driver.findElements(By.cssSelector("iframe[style*='hidden']"));
					for (WebElement frame : frames) {
						try {
							driver.switchTo().defaultContent();
							driver.switchTo().frame(frame);
							js = (JavascriptExecutor) driver;
							docReadyState = docReadyState && (Boolean) js.executeScript("return (document.readyState==\"complete\")");
							driver.switchTo().defaultContent();
							if (!docReadyState) {
								break;
							}
						}
						catch (WebDriverException e) {
							docReadyState = true;
						}
					}
				}
				catch (WebDriverException e) {
					docReadyState = true;
				}
				finally {
					driver.switchTo().defaultContent();
				}
				return docReadyState;
			}
		};

		driverPort = "4444";
		maxPageLoadWait = 90;
		driverHost = "localhost";

		opt.addArguments("--ignore-certificate-errors");
		opt.addArguments("--disable-bundled-ppapi-flash");
		opt.addArguments("--disable-extensions");
		opt.addArguments("--disable-web-security");
		opt.addArguments("--always-authorize-plugins");
		opt.addArguments("--allow-running-insecure-content");
		opt.addArguments("--test-type");
		opt.addArguments("--enable-npapi");
		chromeCapabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);

		try {
			hubURL = new URL("http://" + driverHost + ":" + driverPort + "/wd/hub");
		}
		catch (MalformedURLException e) {
			// e.printStackTrace();
		}

	}

	/**
	 * Method to get instance of web driver using default parameters
	 * 
	 * @return driver
	 */
	public static WebDriver get() {
		browserName = System.getProperty("browserName") != null ? System.getProperty("browserName") : Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browserName").toLowerCase();
		return get(browserName);
	}

	/**
	 * Webdriver to get the web driver with browser name and platform and setting the desired capabilities for browsers
	 * 
	 * @param browserWithPlatform
	 *            : Browser With Platform
	 * @return driver
	 */
	public static WebDriver get(String browserWithPlatform) {

		WebDriver driver = null;
		String browser = null, platform = null;
		// long startTime = StopWatch.startTime();

		if (browserWithPlatform.contains("_")) {
			browser = browserWithPlatform.split("_")[0].toLowerCase().trim();
			platform = browserWithPlatform.split("_")[1].toUpperCase().trim();
		}
		else {
			platform = "ANY";
		}

		try {
			if ("phantom".equalsIgnoreCase(browser)) {

				File file = new File("C:/Users/harish.subramani/Documents/My Received Files/phantomjs-2.1.1-windows/phantomjs-2.1.1-windows/bin/phantomjs.exe");
				System.setProperty("phantomjs.binary.path", file.getAbsolutePath());

				DesiredCapabilities dCaps = new DesiredCapabilities();
				dCaps.setJavascriptEnabled(true);
				dCaps.setCapability("takesScreenshot", false);
//				driver = new PhantomJSDriver(dCaps);

			}
			else if ("chrome".equalsIgnoreCase(browser)) {
				chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, opt);
				driver = new RemoteWebDriver(hubURL, chromeCapabilities);

			}
			else if ("iexplorer".equalsIgnoreCase(browser)) {
				ieCapabilities.setCapability("enablePersistentHover", false);
				ieCapabilities.setCapability("ignoreZoomSetting", true);
				ieCapabilities.setCapability("nativeEvents", false);
				ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				driver = new RemoteWebDriver(hubURL, ieCapabilities);
			}
			else if ("safari".equalsIgnoreCase(browser)) {
				driver = new RemoteWebDriver(hubURL, safariCapabilities);

			}
			else if ("zap".equalsIgnoreCase(browser)) {
				chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, opt);
				driver = new RemoteWebDriver(hubURL, chromeCapabilities);
			}
			else {
				synchronized (JmeterWebDriverFactory.class) {
					firefoxCapabilities.setCapability("unexpectedAlertBehaviour", "ignore");
					driver = new RemoteWebDriver(hubURL, firefoxCapabilities);
				}
				driver.manage().timeouts().pageLoadTimeout(maxPageLoadWait, TimeUnit.SECONDS);
			}
			Assert.assertNotNull(driver, "Driver did not intialize...\n Please check if hub is running / configuration settings are corect.");

			if (!"ANDROID".equalsIgnoreCase(platform)) {
				driver.manage().window().maximize();
			}
		}
		catch (UnreachableBrowserException e) {
			e.printStackTrace();
			throw new SkipException("Hub is not started or down.");
		}
		catch (WebDriverException e) {

			try {
				if (driver != null) {
					driver.quit();
				}
			}
			catch (Exception e1) {
				e.printStackTrace();
			}

			if (e.getMessage().toLowerCase().contains("error forwarding the new session empty pool of vm for setup")) {
				throw new SkipException("Node is not started or down.");
			}
			else if (e.getMessage().toLowerCase().contains("error forwarding the new session empty pool of vm for setup") || e.getMessage().toLowerCase().contains("cannot get automation extension") || e.getMessage().toLowerCase().contains("chrome not reachable")) {
				Log.message("&emsp;<b> --- Re-tried as browser crashed </b>");
				try {
					driver.quit();
				}
				catch (WebDriverException e1) {
					e.printStackTrace();
				}
				driver = get();
			}
			else {
				throw e;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Exception encountered in getDriver Method." + e.getMessage().toString());
		}

		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		return driver;

	}

}
