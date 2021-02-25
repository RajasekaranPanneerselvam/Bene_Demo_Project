package com.TAFrameworkJAVA.support;

/**
 * BrowserType is to set the configuration for browsers in the xml file during
 * suite execution
 */
public enum BrowserType {
	None("default"), Chrome("Chrome"), Firefox("firefox"), IE("internet explorer"), Safari("safari"), PhantomJS("phantomJS"), Edge("MicrosoftEdge");

	private String _configuration;

	private BrowserType(String configuration) {
		this._configuration = configuration;
	}

	public String getConfiguration() {
		return this._configuration;
	}

	public static BrowserType fromConfiguration(String configuration) {
		if (configuration == null || configuration.equalsIgnoreCase("chrome") || configuration.equalsIgnoreCase("default")) {
			return BrowserType.Chrome;
		} else if (configuration.equalsIgnoreCase("firefox")) {
			return BrowserType.Firefox;
		} else if (configuration.equalsIgnoreCase("ie")) {
			return BrowserType.IE;
		} else if (configuration.equalsIgnoreCase("safari")) {
			return BrowserType.Safari;
		} else if (configuration.equalsIgnoreCase("phantomJS")) {
			return BrowserType.PhantomJS;
		} else if (configuration.equalsIgnoreCase("edge")) {
			return BrowserType.Edge;
		}
		throw new IllegalArgumentException("There is no value '" + configuration + "' in Enum '" + BrowserType.class.getName() + "'");
	}
}
