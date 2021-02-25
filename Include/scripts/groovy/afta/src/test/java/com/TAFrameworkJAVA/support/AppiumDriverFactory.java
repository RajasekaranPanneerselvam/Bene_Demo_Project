package com.TAFrameworkJAVA.support;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

/**
 * AppiumDriverFactory class used to get a app driver instance, depends on the
 * user requirement as app we adding the desiredCapabilities initialized here
 */

public class AppiumDriverFactory {

	private static Logger logger = LoggerFactory.getLogger(AppiumDriverFactory.class);
	private static EnvironmentPropertiesReader configProperty = EnvironmentPropertiesReader.getInstance();
	private static String platform;
	private static String platformName;
	private static List<String> udidList;
	private static Map<String, Boolean> deviceBuffer;
	private static Map<String, AppiumDriver<MobileElement>> driverBuffer;
	private static final int SECONDS = 60;
	private static final int TOTAL_TIMES;
	static String xpathSpinner = "//UIAApplication[1]/UIAWindow[1]/UIAActivityIndicator[1]";
	public static ExpectedCondition<Boolean> appPageLoad;
	private static By allSpinners = By.cssSelector(xpathSpinner);
	public static int maxPageLoadWait = 90;
	private static String testName = null;
	//private static CommandPrompt commandPrompt = new CommandPrompt();

	static {
		platformName = configProperty.hasProperty("platformName") ? configProperty.getProperty("platformName") : "platform-not-specified";
		TOTAL_TIMES = configProperty.hasProperty("DevicePollingCount") ? Integer.valueOf(configProperty.getProperty("DevicePollingCount")) : 3;
		driverBuffer = new HashMap<String, AppiumDriver<MobileElement>>();
		deviceBuffer = new HashMap<String, Boolean>();

		udidList = new ArrayList<String>();
		if (configProperty.hasProperty("udidList")) {
			udidList.addAll(Arrays.asList(configProperty.getProperty("udidList").split(",")));
		}
		if (configProperty.hasProperty("udid") && !udidList.contains(configProperty.getProperty("udid"))) {
			udidList.add(configProperty.getProperty("udid"));
		}
		if (configProperty.hasProperty("udid2") && !udidList.contains(configProperty.getProperty("udid2"))) {
			udidList.add(configProperty.getProperty("udid2"));
		}

		for(int i = 0 ; i <udidList.size(); i++){
			if (StringUtils.isNotBlank(udidList.get(i))) {
				deviceBuffer.put(udidList.get(i), true);
				driverBuffer.put(udidList.get(i), null);
			}
		}
		/*udidList.forEach(udid -> {
			if (StringUtils.isNotBlank(udid)) {
				deviceBuffer.put(udid, true);
				driverBuffer.put(udid, null);
			}
		});*/

		appPageLoad = new ExpectedCondition<Boolean>() {
			public final Boolean apply(final WebDriver driver) {
				List<WebElement> spinners = driver.findElements(allSpinners);
				for (WebElement spinner : spinners) {
					try {
						if (spinner.isDisplayed()) {
							return false;
						}
					} catch (NoSuchElementException ex) {
						ex.printStackTrace();
					}
				}
				// To wait click events to trigger
				try {
					Thread.sleep(250);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				spinners = driver.findElements(allSpinners);
				for (WebElement spinner : spinners) {
					try {
						if (spinner.isDisplayed()) {
							return false;
						}
					} catch (NoSuchElementException ex) {
						ex.printStackTrace();
					}
				}
				return true;
			}
		};
	}

	/**
	 * To get a new instance of app driver using default parameters
	 * 
	 * @return driver
	 */
	public static AppiumDriver<MobileElement> get() {
		Throwable t = new Throwable();
		testName=t.getStackTrace()[1].getMethodName();
		return get(null);
	}

	/**
	 * To get a new instance of app driver using a particular udid/devicename
	 * from config.properties file (say udid2/devicename2)
	 * 
	 * @return driver
	 */
	public static AppiumDriver<MobileElement> getAnotherDevice() {
		if (configProperty.hasProperty("udid")) {
			return get(configProperty.getProperty("udid"));
		} else {
			throw new RuntimeException("udid is not mentioned in config.properties file");
		}
	}

	/**
	 * To get a new instance of app driver using a particular udid
	 * 
	 * @return driver
	 */
	public static AppiumDriver<MobileElement> get(String udid) {

		AppiumDriver<MobileElement> driver = null;
		try {
			driver = getAppiumDriver(udid);
		} catch (Exception e) {
			logger.error("Could not create a driver session. Trying again...");
			driver = getAppiumDriver(udid);
		} finally {
			// Update start time of the tests once free slot is assigned - Just
			// for reporting purpose
			try {
				Field f = Reporter.getCurrentTestResult().getClass().getDeclaredField("m_startMillis");
				f.setAccessible(true);
				f.setLong(Reporter.getCurrentTestResult(), Calendar.getInstance().getTime().getTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return driver;
	}

	/**
	 * To set desired capabilities using configured parameters
	 * 
	 * @param udid
	 *            - udid to get a particular device/ blank or null to get any
	 *            available device
	 * @return capabilities
	 */
	private static DesiredCapabilities getDesiredCapabilities(String udid) {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		String appPath = null;
		capabilities.setCapability("platformName", configProperty.getProperty("platformName"));
		capabilities.setCapability("platformVersion", configProperty.getProperty("platformVersion"));

		if(configProperty.getProperty("runSauceLabFromLocal").equalsIgnoreCase("true")&&
				configProperty.getProperty("runMobileApp").equalsIgnoreCase("true")){
			capabilities.setCapability("app", configProperty.getProperty("apppathforsaucelab"));
			capabilities.setCapability("deviceName", configProperty.getProperty("udidList"));
			capabilities.setCapability("appiumVersion", configProperty.getProperty("appiumVersion"));
			capabilities.setCapability("name", testName);
			Log.message("Launching app in sauce labs");
		}else{
			if (StringUtils.isNotBlank(udid)) {
				if (isDeviceAvailable(udid)) {
					capabilities.setCapability("udid", udid);
					capabilities.setCapability("deviceName", udid);
				} else {
					throw new RuntimeException(udid + " not available after waiting for " + TOTAL_TIMES + " minutes");
				}
			} else {
				udid = getAvailableDeviceUDID();
				if (StringUtils.isBlank(udid)) {
					throw new RuntimeException("No device available after waiting for " + TOTAL_TIMES + " minutes");
				}
				capabilities.setCapability("udid", udid);
				capabilities.setCapability("deviceName", udid);
			}
			logger.info("Device udid : " + udid);

			if(configProperty.getProperty("platform").equalsIgnoreCase("win")) {
				capabilities.setCapability("platformVersion", udid);
			}

			if ("mobileweb".equalsIgnoreCase(configProperty.getProperty("appType"))) {
				capabilities.setCapability("app", configProperty.getProperty("browser"));
			} else if ("Win".equalsIgnoreCase(platform)) {
				capabilities.setCapability("app", configProperty.getProperty("WinAppPackage"));
			} else if (configProperty.hasProperty("appPathiOS")) {
				capabilities.setCapability("app", configProperty.getProperty("appPathiOS"));
			} else {
				appPath = getAppAbsoultePath();
				capabilities.setCapability("app", appPath);
			}    	
		}

		return capabilities;
	}

	/**
	 * Method to get absolute path of app (ipa or app / apk)
	 * 
	 * @return absolute path of app
	 */
	private static String getAppAbsoultePath() {
		File classpathRoot = new File(System.getProperty("user.dir"));
		logger.info("App path should be:" + classpathRoot + "/app");
		File appDir = new File(classpathRoot, "/app");
		String fileName = "";
		File folder = new File("app");
		File[] listOfFiles = folder.listFiles();;
		for (File listFile : listOfFiles) {
			String fileExtension = FilenameUtils.getExtension(listFile.getAbsolutePath());
			if (null != platformName && platformName.equalsIgnoreCase("iOS") && (fileExtension.equals("ipa") || fileExtension.equals("app"))) {
				fileName = listFile.getName();
				break;
			} else if (null != platformName && platformName.trim().equalsIgnoreCase("Android") && fileExtension.equals("apk")) {
				fileName = listFile.getName();
				break;
			}
		}
		if (null != fileName && !fileName.isEmpty()) {
			File app = new File(appDir, fileName);
			String appName = app.getAbsolutePath();
			return appName;
		}
		return null;
	}

	/**
	 * To wait for given time until a random device becomes available
	 * 
	 * @return udid of the device when it is available
	 */
	private static String getAvailableDeviceUDID() {
		String udid = null;
		int maxTry = 0;
		while (udid == null && maxTry++ < TOTAL_TIMES) {
			clearStaleDriverSessions();
			synchronized (deviceBuffer) {
				for (String deviceudid : deviceBuffer.keySet()) {
					if (deviceBuffer.get(deviceudid)) {
						udid = deviceudid;
						deviceBuffer.put(deviceudid, false);
						break;
					}
				}
			}
			if (udid == null) {
				try {
					logger.info(Reporter.getCurrentTestResult().getName() + " says -> No devices available now. Waiting for 1 minute...");
					TimeUnit.SECONDS.sleep(SECONDS);
				} catch (InterruptedException e) {
					logger.error("Unable to get udid: " + e.getMessage());
				}
			}
		}
		return udid;
	}

	/**
	 * To wait for given time until the device with given udid become available
	 * 
	 * @param udid
	 *            - udid of the device
	 * @return true when the device is available
	 */
	private static boolean isDeviceAvailable(String udid) {
		boolean isFree = false;
		int maxTry = 0;
		while (!isFree && maxTry++ < TOTAL_TIMES) {
			clearStaleDriverSessions();
			synchronized (deviceBuffer) {
				if (deviceBuffer.get(udid)) {
					deviceBuffer.put(udid, false);
					isFree = true;
				}
			}
			if (!isFree) {
				try {
					logger.info(Reporter.getCurrentTestResult().getName() + " says -> " + udid + " is not available now. Waiting for 1 minute...");
					TimeUnit.SECONDS.sleep(SECONDS);
				} catch (InterruptedException e) {
					logger.error("Unable to get udid: " + e.getMessage());
				}
			}
		}
		return isFree;
	}

	/**
	 * To quit the appium driver and update the device buffer and driver buffer
	 * 
	 * @param driver
	 */
	public static void quitDriver(AppiumDriver<MobileElement> driver) {
		if (driver != null) {
			String udid = "";
			if(null != driver.getCapabilities().getCapability("udid")) {
				udid = driver.getCapabilities().getCapability("udid").toString();
				synchronized (deviceBuffer) {
					driver.quit();
					deviceBuffer.put(udid, true);
					driverBuffer.put(udid, null);
				}
			} else {
				udid = driver.getCapabilities().getCapability("platformVersion").toString();
				synchronized (deviceBuffer) {
					driver.quit();
					deviceBuffer.put(udid, true);
					driverBuffer.put(udid, null);
				}
			}
			driver.quit();
		} else {
			logger.error("Driver is null");
		}
	}

	/**
	 * To create an Appium Driver session with given capabilities.
	 * 
	 * @param udid
	 *            - udid to get a particular device/ blank or null to get any
	 *            available device
	 * @return AppiumDriver instance
	 */

	private static AppiumDriver<MobileElement> getAppiumDriver(String udid) {
		AppiumDriver<MobileElement> driver = null;

		DesiredCapabilities capabilities = new DesiredCapabilities();
		if(configProperty.getProperty("runSauceLabFromLocal").equalsIgnoreCase("true")){
			String appiumURL = "https://" + configProperty.getProperty("sauceUserName") + ":" + configProperty.getProperty("sauceAuthKey") + "@ondemand.saucelabs.com:443/wd/hub";
			try {
				capabilities = getDesiredCapabilities(udid);
				Log.message(appiumURL);
				driver = new AndroidDriver<MobileElement>(new URL(appiumURL), capabilities);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (System.getProperty("appium.screenshots.dir") != null) {
			try {
				logger.info("Initialize the Appium driver for AWS");
				driver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else {
			logger.info("Initialize the Appium driver for local");
			try {
				String appiumURL = "http://" + configProperty.getProperty("host") + ":" + configProperty.getProperty("port") + "/wd/hub";
				capabilities = getDesiredCapabilities(udid);
				udid = capabilities.getCapability("udid").toString();

				switch (platformName.toLowerCase()) {
				case "android":
					driver = new AndroidDriver<MobileElement>(new URL(appiumURL), capabilities);
					break;
				case "ios":
					driver = new IOSDriver<MobileElement>(new URL(appiumURL), capabilities);
					break;
				case "win":
					appiumURL = "http://" + udid + ":" + configProperty.getProperty("port");
					driver = new IOSDriver<MobileElement>(new URL(appiumURL), capabilities);
					break;
				default:
					logger.error("Unable to load platform property. Platform is set to " + platform);
					break;
				}

				synchronized (deviceBuffer) {
					deviceBuffer.put(udid, false);
					driverBuffer.put(udid, driver);
				}
			} catch (Exception e) {
				synchronized (deviceBuffer) {
					if (capabilities.getCapability("udid") != null && deviceBuffer.containsKey(capabilities.getCapability("udid").toString())) {
						deviceBuffer.put(capabilities.getCapability("udid").toString(), true);
						driverBuffer.put(capabilities.getCapability("udid").toString(), null);
					}
				}
				logger.error("Unable to create driver session with given URL and DesiredCapabilities : " + e.getMessage());
				throw new RuntimeException("Unable to create driver session with given URL and DesiredCapabilities : " + e.getMessage());
			}
		}
		return driver;
	}

	/**
	 * To clear the stale driver sessions and to free the udid of the devices in
	 * the driverBuffer and the deviceBuffer respectively
	 */
	private static void clearStaleDriverSessions() {
		synchronized (deviceBuffer) {
			for (String udid : deviceBuffer.keySet()) {
				try {
					AppiumDriver<MobileElement> driver = driverBuffer.get(udid);
					if (driver != null) {
						driver.getSessionDetails().toString();
					}
				} catch (Exception e) {
					deviceBuffer.put(udid, true);
					driverBuffer.put(udid, null);
				}
			}
		}
	}

	
	
	/**
	 * To get the active platform name. Returns platformName value.
	 * 
	 * @param platformName
	 *                    - platformName of the device
	 */
	public static String getPlatformName(){
		return platformName;
	}
	
	
	/**
	 * To get the device name and version (Works only when the iOS device is
	 * connected to the same MAC machine where the code gets executed)
	 * 
	 * @param udid
	 *            - udid of the device
	 */
	/*public static String getIOSDeviceInfo(String udid) {
		String deviceNameAndVersion = "";
		try {
			String deviceName = commandPrompt.runCommand("/usr/local/bin/idevicename --udid " + udid).replace("\\W", "_").trim();
			String deviceVersion = commandPrompt.runNestedCommand("/usr/local/bin/ideviceinfo --udid " + udid + " | grep ProductVersion").split(":")[1].replace("\n", "").trim();
			deviceNameAndVersion = deviceName + "_" + deviceVersion;
		} catch (Exception e) {
			logger.error("Unable to get device details " + e.getMessage());
		}
		return deviceNameAndVersion;
	}*/
}
