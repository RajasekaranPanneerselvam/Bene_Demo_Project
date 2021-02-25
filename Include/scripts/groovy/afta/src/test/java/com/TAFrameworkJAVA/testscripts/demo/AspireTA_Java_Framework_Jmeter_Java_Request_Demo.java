package com.TAFrameworkJAVA.testscripts.demo;

import java.io.Serializable;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.openqa.selenium.WebDriver;

import com.TAFrameworkJAVA.pages.Test;
import com.TAFrameworkJAVA.support.JmeterWebDriverFactory;

public class AspireTA_Java_Framework_Jmeter_Java_Request_Demo extends AbstractJavaSamplerClient implements Serializable {

	private static final long serialVersionUID = 1L;

	// set up default arguments for the JMeter GUI
	@Override
	public Arguments getDefaultParameters() {
		Arguments defaultParameters = new Arguments();
		defaultParameters.addArgument("webSite", "http://www.flipkart.com_flipkart");
		return defaultParameters;
	}

	@SuppressWarnings("deprecation")
	public SampleResult runTest(JavaSamplerContext context) {

		// pull parameters
		String webSite = context.getParameter("webSite");

		// Get the web driver instance
		final WebDriver driver = JmeterWebDriverFactory.get("chrome_windows");
		String site = webSite.split("_")[0];

		SampleResult result = new SampleResult();
		result.sampleStart(); // start stopwatch

		try {

			
			SampleResult child1 = new SampleResult();
			child1.sampleStart();
		    
			driver.get(site);
		    
		    
		    
		    child1.sampleEnd();
		    child1.setSuccessful(true);
		    child1.setResponseMessage("Click action performed action");
		    child1.setResponseCodeOK(); // 200 code
			
		    result.addSubResult(child1);			
			
			Test ts = new Test();
			SampleResult child = new SampleResult();
		    child.sampleStart();
		    ts.search(driver);
		    child.sampleEnd();
		    child.setSuccessful(true);
		    child.setResponseMessage("Click action performed action");
		    child.setResponseCodeOK(); // 200 code
			
		    result.addSubResult(child);
			
			driver.quit();
			
			result.sampleEnd(); // stop stopwatch
			result.setSuccessful(true);
			result.setResponseMessage("Successfully performed action");
			result.setResponseCodeOK(); // 200 code

		}// try
		catch (Exception e) {
			result.sampleEnd(); // stop stopwatch
            result.setSuccessful( false );
            result.setResponseMessage( "Exception: " + e );
 
            // get stack trace as a String to return as document data
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            e.printStackTrace( new java.io.PrintWriter( stringWriter ) );
            result.setResponseData( stringWriter.toString() );
            result.setDataType( org.apache.jmeter.samplers.SampleResult.TEXT );
            result.setResponseCode( "500" );
		}

		return result;

	}

}// AspireTA_Java_Framework_Jmeter_Java_Request_Demo