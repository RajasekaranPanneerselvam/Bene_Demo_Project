package com.TAFrameworkJAVA.support;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.xmlbeans.XmlException;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestRunContext;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestStep;
import com.eviware.soapui.model.testsuite.TestStepResult;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.support.SoapUIException;

/**
 * Soap UI
 */
public class SOAP_UI {

	/**
	 * Call the SOAP TestCases through this method
	 * 
	 * @param projectFilePath
	 *            : Path where you placed the SOAP Project
	 * @param testSuiteName
	 *            : Suite Name
	 * @param testCaseName
	 *            : Test Case Name which need to be execute
	 * @param testStepName
	 *            : Test Step Name
	 * @param parametersForSoapCall
	 *            : HashMap with a list of parameters that need to be passed for the specified TC in form of key,value pair
	 * @throws IOException
	 *             : Throws SOAP exeception
	 * @throws SoapUIException
	 *             : Throws SOAP exeception
	 * @throws XmlException
	 *             : Throws SOAP exeception
	 */
	@SuppressWarnings("rawtypes")
	public void soapCall(String projectFilePath, String testSuiteName, String testCaseName, String testStepName, HashMap <Integer, String> parametersForSoapCall) throws IOException, SoapUIException, XmlException {

		WsdlProject wsdlProject = new WsdlProject(projectFilePath);
		TestSuite testSuite = wsdlProject.getTestSuiteByName(testSuiteName);
		TestCase testCase = testSuite.getTestCaseByName(testCaseName);

		/* Display content using Iterator */
		Set set = parametersForSoapCall.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			testCase.setPropertyValue(mentry.getKey().toString(), mentry.getValue().toString());
		}

		TestStep testStep = testCase.getTestStepByName(testStepName);
		TestCaseRunner testRunner = new com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner((WsdlTestCase) testCase, null);

		WsdlTestRunContext testStepContext = new com.eviware.soapui.impl.wsdl.testcase.WsdlTestRunContext(testStep);
		// WsdlTestRequestStepResult testStepResult=(WsdlTestRequestStepResult) testStep.run(testRunner, testStepContext);

		TestStepResult testStepResult = testStep.run(testRunner, testStepContext);
		String callStatus = testStepResult.getStatus().toString().toLowerCase();
		Log.event("SOAP Call Execution Status: " + callStatus + ".");

		wsdlProject.release();

	}// soapCall

}// SOAP_UI