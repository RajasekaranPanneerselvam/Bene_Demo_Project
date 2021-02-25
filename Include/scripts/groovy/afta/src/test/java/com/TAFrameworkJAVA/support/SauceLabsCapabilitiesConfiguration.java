package com.TAFrameworkJAVA.support;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Class to set the DesiredCapabilties when working with SauceLabs.
 * 
 * @author mani.sundaram
 * @see <a
 *      href="https://docs.saucelabs.com/reference/test-configuration/">SauceLab
 *      config docs</a> for more info on the available properties.
 * 
 */
public class SauceLabsCapabilitiesConfiguration {
	private String browserName;
	private String platform;
	private String screenResolution;
	private String browserVersion;
	private String testName;
	private String build;
	private String deviceOrientation;
	private String preRun;
	private String seleniumVersion;
	private String iedriverVersion;
	private String chromedriverVersion;
	private String maxTestDuration;
	private String commandTimeout;
	private String idleTimeout;
	private String deviceName;

	private boolean recordVideo = true;
	private boolean recordNetwork = true;
	private boolean recordSnapshot = true;
	private boolean videoUploadOnPass = true;
	private boolean autoAcceptAlerts = true;

	private static MobileEmulationUserAgentConfiguration mobEmuUA = new MobileEmulationUserAgentConfiguration();

	public SauceLabsCapabilitiesConfiguration(String testName, String build, String browserName, String browserVersion, String platform, String screenResolution) {
		this.testName = testName;
		this.build = build;
		this.browserName = browserName;
		this.platform = platform;
		this.browserVersion = browserVersion;
		this.screenResolution = screenResolution;

	}

	public SauceLabsCapabilitiesConfiguration(String testName, String build) {
		this.testName = testName;
		this.build = build;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}
	
	public String getBrowserVersion() {
		return browserVersion;
	}
	
	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}
	
	public void setSeleniumVersion(String seleniumVersion) {
		this.seleniumVersion = seleniumVersion;
	}

	public void setIeDriverVersion(String iedriverVersion) {
		this.iedriverVersion = iedriverVersion;
	}

	public void setChromeDriverVersion(String chromedriverVersion) {
		this.chromedriverVersion = chromedriverVersion;
	}

	public void setMaxTestDuration(String maxTestDuration) {
		this.maxTestDuration = maxTestDuration;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getScreenResolution() {
		return screenResolution;
	}

	public void setScreenResolution(String screenResolution) {
		this.screenResolution = screenResolution;
	}

	public boolean isRecordVideo() {
		return recordVideo;
	}

	public void setRecordVideo(boolean recordVideo) {
		this.recordVideo = recordVideo;
	}

	public boolean isRecordNetwork() {
		return recordNetwork;
	}

	public void setRecordNetwork(boolean recordNetwork) {
		this.recordNetwork = recordNetwork;
	}

	public boolean isRecordSnapshot() {
		return recordSnapshot;
	}

	public void setRecordSnapshot(boolean recordSnapshot) {
		this.recordSnapshot = recordSnapshot;
	}

	public boolean isVideoUploadOnPass() {
		return videoUploadOnPass;
	}

	public void setVideoUploadOnPass(boolean videoUploadOnPass) {
		this.videoUploadOnPass = videoUploadOnPass;
	}

	/* To handle and avoid unexpected alerts in iOS device */
	public void setautoAcceptAlerts(boolean autoAcceptAlerts) {
		this.autoAcceptAlerts = autoAcceptAlerts;
	}

	public String getDeviceOrientation() {
		return deviceOrientation;
	}

	public void setDeviceOrientation(String deviceOrientation) {
		this.deviceOrientation = deviceOrientation;
	}

	public String getPreRun() {
		return preRun;
	}

	public void setPreRun(String preRun) {
		this.preRun = preRun;
	}

	public String getCommandTimeout() {
		return commandTimeout;
	}

	public void setCommandTimeout(String commandTimeout) {
		this.commandTimeout = commandTimeout;
	}

	public String getIdleTimeout() {
		return idleTimeout;
	}

	public void setIdleTimeout(String idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public DesiredCapabilities getDesiredCapabilities() {		
		DesiredCapabilities caps = new DesiredCapabilities();
		// Adding IE browser specific capabilities to sauce labs 
		if (System.getProperty("SELENIUM_DRIVER") != null && System.getProperty("SELENIUM_DRIVER").contains("internet explorer") ){
			 caps = DesiredCapabilities.internetExplorer();
			 caps.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
			 caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			 caps.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
			 caps.setCapability("ignoreProtectedModeSettings", true);
			 caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			 caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		}	
		
		caps.setCapability("name", testName);
		caps.setCapability("build", build);
		caps.setCapability("recordScreenshots", recordSnapshot);
		caps.setCapability("recordVideo", recordVideo);
		caps.setCapability("videoUploadOnPass", videoUploadOnPass);
		caps.setCapability("autoAcceptAlerts", autoAcceptAlerts);

		if (browserName != null) {
			caps.setBrowserName(browserName);			
		}
		
		if (platform != null) {
			caps.setCapability(CapabilityType.PLATFORM, platform);
		}	
		
		
		if (browserVersion != null) {
			caps.setVersion(browserVersion);
		}
		if (screenResolution != null) {
			caps.setCapability("screenResolution", screenResolution);
		}
		if (deviceOrientation != null) {
			caps.setCapability("deviceOrientation", deviceOrientation);
		}
		if (preRun != null) {
			caps.setCapability("prerun", preRun);
		}

		// To overriding the desired capabilities values depends upon the
		// version values, if we didn't mention then sauce labs will
		// take default values depends on the sauce lab settings like lower
		// version

		/* updating the selenium version */
		if (seleniumVersion != null) {
			caps.setCapability("seleniumVersion", seleniumVersion);
		}
		/* updating the ie server driver version */
		if (iedriverVersion != null) {
			caps.setCapability("iedriverVersion", iedriverVersion);
		}

		/* updating the chrome server driver version */
		if (chromedriverVersion != null) {
			caps.setCapability("chromedriverVersion", chromedriverVersion);
		}

		/* updating the test duration */
		if (maxTestDuration != null) {
			caps.setCapability("maxDuration", maxTestDuration);
		}

		if (commandTimeout != null) {
			caps.setCapability("commandTimeout", commandTimeout);
		}

		if (idleTimeout != null) {
			caps.setCapability("idleTimeout", idleTimeout);
		}

		if (deviceName != null) {
			caps.setCapability("deviceName", deviceName);
		}
		return caps;
	}

	/**
	 * To storing chrome mobile emulation configurations(width, height,
	 * pixelRatio and with sauce lab capabilities) and
	 * returning the capabilities
	 * 
	 * <p>
	 * if required feasible result then set relevant parameter from
	 * MobileEmulationUserAgentConfiguration()
	 * 
	 * @param caps
	 *            with sauce lab capabilities
	 * @param deviceName
	 *            name of the device needs to set user agent configuration
	 * @param userAgent
	 *            to passing the user agent string value depends on device
	 * @return uaCaps consist of sauce lab and user agent capabilities
	 */
	public DesiredCapabilities getUserAgentDesiredCapabilities(SauceLabsCapabilitiesConfiguration caps, String deviceName, String userAgent) {

		Map<String, Object> deviceMetrics = new HashMap<String, Object>();
		Map<String, Object> mobileEmulation = new HashMap<String, Object>();
		
		int width = 0;
		int height = 0;
		Double pixRatio = null;

		width = Integer.valueOf(mobEmuUA.getDeviceWidth(deviceName));
		height = Integer.valueOf(mobEmuUA.getDeviceHeight(deviceName));
		pixRatio = Double.valueOf(mobEmuUA.getDevicePixelRatio(deviceName));		

		deviceMetrics.put("width", width);
		deviceMetrics.put("height", height);
		deviceMetrics.put("pixelRatio", pixRatio);
		mobileEmulation.put("deviceMetrics", deviceMetrics);
		mobileEmulation.put("userAgent", userAgent);
		Log.event("mobileEmulation settings::==> " + mobileEmulation);

		DesiredCapabilities uaCaps = DesiredCapabilities.chrome();
		ChromeOptions opt = new ChromeOptions();
		opt.setExperimentalOption("mobileEmulation", mobileEmulation);
		uaCaps = caps.setChromeOption(opt);
		return uaCaps;
	}

	/**
	 * Adding sauce lab capabilities to user agent configuration and returning
	 * the both capabilities
	 * 
	 * @param options
	 *            ChromeOptions
	 * @return sauceWithChromeCab returning the sauce lab capabilities with
	 *         chrome mobile emulation capabilities
	 */
	public DesiredCapabilities setChromeOption(ChromeOptions options) {
		DesiredCapabilities sauceWithChromeCab = this.getDesiredCapabilities();
		sauceWithChromeCab.setCapability(ChromeOptions.CAPABILITY, options);
		return sauceWithChromeCab;
	}
}
