package com.TAFrameworkJAVA.support;

import java.util.HashMap;
import java.util.Map;

import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.StdoutLogHandler;

public class VisualValidations  {

	public static Eyes applitoolLib(String key, String oSType)
	{
			// Initialize the eyes SDK and set your private API key.
			Eyes eyes = new Eyes();
		    eyes.setApiKey(key);
		    eyes.setLogHandler(new StdoutLogHandler(true));
		    eyes.setHostOS(oSType);
		    return eyes;
	}
	public static HashMap screenerMethod(String key, String oSType)
			{
				Map screener = new HashMap();
			    screener.put("name","My Test");
			    screener.put("resolution","1280x1024");
			    screener.put("apiKey", "<your-api-key>");
			    screener.put("group", "<your-group-id>");
			    return screener;
	}
}
