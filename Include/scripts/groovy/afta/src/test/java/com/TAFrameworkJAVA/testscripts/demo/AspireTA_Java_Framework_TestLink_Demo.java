package com.TAFrameworkJAVA.testscripts.demo;

import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import utils.testlink.MapToTestLink;
import utils.testlink.TestLinkAPIClient;
import utils.testlink.TestLinkAPIException;
import utils.testlink.TestLinkAPIResults;

import com.TAFrameworkJAVA.pages.HomePage;
import com.TAFrameworkJAVA.support.*;
import com.relevantcodes.extentreports.ExtentTest;

@Listeners(EmailReport.class)
public class AspireTA_Java_Framework_TestLink_Demo extends BaseTest {

	String notes = null;
	String testLinkResult = null;
	TestLinkAPIClient api = null;
	protected String status = null;
	EnvironmentPropertiesReader environmentPropertiesReader;
	private String workbookName = "testdata\\data\\Regression_PoC.xls";
	private String sheetName = "Demo";

	@Test(description = "Yet to describe site", dataProviderClass = DataProviderUtils.class, dataProvider = "parallelTestDataProvider")
	@MapToTestLink(testCaseID = "sample_tc")
	public void tcTAFrameworkJAVADemo01(String browser) throws Exception {
		
		//** Loading the test data from excel using the test case id */
		HashMap<String, String> testData = initTestData(workbookName, sheetName);
		
		// Get the web driver instance
		final WebDriver driver = WebDriverFactory.get(browser);
		
		String site = webSite.split("_")[0];
		String stakeHolderName = webSite.split("_")[1];

		ExtentTest extentedReport = Log.testCaseInfo(testData.get("Description") + "[" + browser + " || " + stakeHolderName.toUpperCase() + " ]", "Aspire TA Framework Java " + stakeHolderName.toUpperCase(), "Regression", "Aspire Systems");

		try {

			HomePage homePage = new HomePage(driver, site).get();
			Log.messageExtentReport("Step 1. Navigated to '" + stakeHolderName + "' Home Page!", extentedReport);

			homePage.searchProduct(testData.get("SearchKey"));
			Log.messageWithExtentScreenshot("Search Result", driver, extentedReport, true);

			Log.testCaseResultExtentReport(extentedReport);

		}// try
		catch (Exception e) {
			Log.exception(e, driver);
		}// catch
		finally {
			Log.endTestCase();
			driver.quit();
		}// finally

	}// tcTAFrameworkJAVADemo01

	@AfterMethod(alwaysRun = true)
	public final void tearDown(ITestResult result) throws IOException, TestLinkAPIException {

		status = "PASS";
		environmentPropertiesReader = new EnvironmentPropertiesReader();
		MapToTestLink mapToTestLink = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(MapToTestLink.class);

		if (Config.TESTLINKUPDATE) {
			if (mapToTestLink != null) {
				String testCase = mapToTestLink.testCaseID();
				if (result.isSuccess()) {
					testLinkResult = TestLinkAPIResults.TEST_PASSED;
					notes = "Executed successfully";

					// String buildID = environmentPropertiesReader.getTestBuildId() + RandomStringUtils.randomNumeric(2).toString();
					api = new TestLinkAPIClient(environmentPropertiesReader.getTestLinkDevKey(), environmentPropertiesReader.getTestlinkURL());
					// api.createBuild(environmentPropertiesReader.getTestProject(), environmentPropertiesReader.getTestPlan(), buildID, "Creating new Build");
					api.reportTestCaseResult(environmentPropertiesReader.getTestProject(), environmentPropertiesReader.getTestPlan(), environmentPropertiesReader.getTestSuiteID(), testCase, notes, testLinkResult, environmentPropertiesReader.getTestBuildId());
				}
				else {
					testLinkResult = TestLinkAPIResults.TEST_FAILED;
					notes = "Execution Failed";
					api = new TestLinkAPIClient(environmentPropertiesReader.getTestLinkDevKey(), environmentPropertiesReader.getTestlinkURL());
					api.reportTestCaseResult(environmentPropertiesReader.getTestProject(), environmentPropertiesReader.getTestPlan(), environmentPropertiesReader.getTestSuiteID(), testCase, notes, testLinkResult, environmentPropertiesReader.getTestBuildId());
				}
			}
		}
	}// tearDown

}// AspireTA_Java_Framework_Demo