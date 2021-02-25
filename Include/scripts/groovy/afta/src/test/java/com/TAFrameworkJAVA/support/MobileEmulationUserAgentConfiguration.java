package com.TAFrameworkJAVA.support;

import java.util.HashMap;

/**
 * Class to set the Mobile emulation configuration on chrome browser
 * 
 * @author mani.sundaram
 * @see <a href=
 *      "http://www.webapps-online.com/online-tools/user-agent-strings/dv">User
 *      Agent String</a> for more info on the available user agent string.
 * @see <a href=
 *      "https://sites.google.com/a/chromium.org/chromedriver/mobile-emulation">
 *      Mobile emulation on chrome</a> for more info on chrome mobile emulation.
 * 
 */
public class MobileEmulationUserAgentConfiguration {

	public static final String SM_GALAXY_TAB4_AND5_1_LANDSCAPE = "galaxy_tab4_android5.1_chrome_landscape(1280*800)";
	public static final String SM_GALAXY_TAB4_AND5_1_PORTRAIT = "galaxy_tab4_android5.1_chrome_portrait(800*1280)";

	public static final String APPLE_IPAD4_IOS9_LANDSCAPE = "ipad4_ios9_safari_landscape(1024*768)";
	public static final String APPLE_IPAD4_IOS9_PORTRAIT = "ipad4_ios9_safari_portrait(768*1024)";

	@SuppressWarnings("serial")
	private final HashMap<String, String> samsung_galaxy_tab_5_1_landscape = new HashMap<String, String>() {
		{
			put("userAgent",
					"Mozilla/5.0 (Linux; Android 5.1.1; SM-T335 Build/LMY47X) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.83 Safari/537.36");
			put("width", "1280");
			put("height", "800");
			put("pixelRatio", "2");
		}
	};

	@SuppressWarnings("serial")
	private final HashMap<String, String> samsung_galaxy_tab_5_1_portrait = new HashMap<String, String>() {
		{
			put("userAgent",
					"Mozilla/5.0 (Linux; Android 5.1.1; SM-T335 Build/LMY47X) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.83 Safari/537.36");
			put("width", "800");
			put("height", "1280");
			put("pixelRatio", "2");
		}
	};

	@SuppressWarnings("serial")
	private final HashMap<String, String> apple_ipad4_ios9_landscape = new HashMap<String, String>() {
		{
			put("userAgent",
					"Mozilla/5.0 (iPad; CPU OS 9_0 like Mac OS X) AppleWebKit/601.1.16 (KHTML, like Gecko) Version/8.0 Mobile/13A171a Safari/600.1.4");
			put("width", "1024");
			put("height", "768");
			put("pixelRatio", "2");
		}
	};

	@SuppressWarnings("serial")
	private final HashMap<String, String> apple_ipad4_ios9_portrait = new HashMap<String, String>() {
		{
			put("userAgent",
					"Mozilla/5.0 (iPad; CPU OS 9_0 like Mac OS X) AppleWebKit/601.1.16 (KHTML, like Gecko) Version/8.0 Mobile/13A171a Safari/600.1.4");
			put("width", "768");
			put("height", "1024");
			put("pixelRatio", "2");
		}
	};

	/**
	 * To storing the all the devices configurations
	 *
	 * @return userAgentData - have mobile emulation configuration data(user
	 *         agent, width, height and pixelRatio)
	 */
	public HashMap<String, HashMap<String, String>> setUserAgentConfigurationValue() {

		HashMap<String, HashMap<String, String>> userAgentData = new HashMap<String, HashMap<String, String>>();

		userAgentData.put(SM_GALAXY_TAB4_AND5_1_LANDSCAPE, samsung_galaxy_tab_5_1_landscape);
		userAgentData.put(SM_GALAXY_TAB4_AND5_1_PORTRAIT, samsung_galaxy_tab_5_1_portrait);

		userAgentData.put(APPLE_IPAD4_IOS9_LANDSCAPE, apple_ipad4_ios9_landscape);
		userAgentData.put(APPLE_IPAD4_IOS9_PORTRAIT, apple_ipad4_ios9_portrait);

		return userAgentData;
	}

	/**
	 * To get the user agent string from device name
	 * 
	 * @param deviceName
	 *            - device name which going to perform a mobile emulation on
	 *            chrome
	 * @return dataToBeReturned- device width
	 */
	public String getUserAgent(String deviceName) {
		String dataToBeReturned = null;
		HashMap<String, HashMap<String, String>> getUserAgent = setUserAgentConfigurationValue();
		dataToBeReturned = hasDeviceName(deviceName) ? (String) getUserAgent.get(deviceName).get("userAgent") : null;
		return dataToBeReturned;
	}

	/**
	 * To get the device width string from device name
	 * 
	 * @param deviceName
	 *            - device name which going to perform a mobile emulation on
	 *            chrome
	 * @return
	 */
	public String getDeviceWidth(String deviceName) {
		String dataToBeReturned = null;
		HashMap<String, HashMap<String, String>> getDeviceWidth = setUserAgentConfigurationValue();
		dataToBeReturned = hasDeviceName(deviceName) ? (String) getDeviceWidth.get(deviceName).get("width") : null;
		return dataToBeReturned;
	}

	/**
	 * To get the device height string from device name
	 * 
	 * @param deviceName
	 *            - device name which going to perform a mobile emulation on
	 *            chrome
	 * @return dataToBeReturned- device height
	 */
	public String getDeviceHeight(String deviceName) {
		String dataToBeReturned = null;
		HashMap<String, HashMap<String, String>> getDeviceHeight = setUserAgentConfigurationValue();
		dataToBeReturned = hasDeviceName(deviceName) ? (String) getDeviceHeight.get(deviceName).get("height") : null;
		return dataToBeReturned;
	}

	/**
	 * To get the device pixel ratio string from device name
	 * 
	 * @param deviceName
	 *            - device name which going to perform a mobile emulation on
	 *            chrome
	 * @return dataToBeReturned - device pixel ratio
	 */
	public String getDevicePixelRatio(String deviceName) {
		String dataToBeReturned = null;
		HashMap<String, HashMap<String, String>> getDevicePixelRatio = setUserAgentConfigurationValue();
		dataToBeReturned = hasDeviceName(deviceName) ? (String) getDevicePixelRatio.get(deviceName).get("pixelRatio")
				: null;
		return dataToBeReturned;
	}

	/**
	 * To check the device name present in the set up key hash map
	 * 
	 * @param deviceName
	 *            - device name which going to perform a mobile emulation on
	 *            chrome
	 * @return boolean value - if device name in the set up key will return
	 *         true, otherwise false
	 */
	private boolean hasDeviceName(String deviceName) {
		HashMap<String, HashMap<String, String>> hasDeviceName = setUserAgentConfigurationValue();
		return hasDeviceName.containsKey(deviceName);
	}

	/**
	 * To get the device name from mobile emulation attributes
	 * 
	 * @param userAgent
	 *            - mapped user agent string with device name
	 * @param pixelRatio
	 *            - mapped pixel ratio with device name
	 * @param width
	 *            - mapped width with device name
	 * @param height
	 *            - mapped height with device name
	 * @return dataToBeReturned - device name mapped with user agent, pixel
	 *         ratio, width and height
	 */
	public String getDeviceNameFromMobileEmulation(String userAgent, String pixelRatio, String width, String height) {
		String dataToBeReturned = null;
		boolean found = false;
		HashMap<String, HashMap<String, String>> getDeviceData = setUserAgentConfigurationValue();
		for (Object usKey : getDeviceData.keySet()) {
			if (getDeviceData.get(usKey).get("userAgent").equals(userAgent)
					&& getDeviceData.get(usKey).get("pixelRatio").equals(pixelRatio)
					&& getDeviceData.get(usKey).get("width").equals(width)
					&& getDeviceData.get(usKey).get("height").equals(height)) {
				dataToBeReturned = (String) usKey;
				found = true;
			}
		}
		if (!found) {
			dataToBeReturned = null;
		}
		return dataToBeReturned;
	}

}
