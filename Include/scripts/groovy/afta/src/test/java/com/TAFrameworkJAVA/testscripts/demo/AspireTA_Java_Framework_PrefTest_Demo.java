package com.TAFrameworkJAVA.testscripts.demo;

import java.util.HashMap;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.TAFrameworkJAVA.pages.HomePage;
import com.TAFrameworkJAVA.support.BaseTest;
import com.TAFrameworkJAVA.support.DataProviderUtils;
import com.TAFrameworkJAVA.support.EmailReport;
import com.TAFrameworkJAVA.support.Log;
import com.TAFrameworkJAVA.support.WebDriverFactory;
import com.relevantcodes.extentreports.ExtentTest;

import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import utils.testlink.TestLinkAPIClient;

@Listeners(EmailReport.class)
public class AspireTA_Java_Framework_PrefTest_Demo extends BaseTest {

	String notes = null;
	String testLinkResult = null;
	TestLinkAPIClient api = null;
	protected String status = null;	
	private String workbookName = "testdata\\data\\Regression_PoC.xls";
	private String sheetName = "Demo";
	BrowserMobProxyServer browserMobProxy;
	Proxy proxy;
	
	@BeforeMethod
	public void beforeMethod() {
		browserMobProxy = new BrowserMobProxyServer();
		browserMobProxy.start();
		proxy = ClientUtil.createSeleniumProxy(browserMobProxy);
	}

	@Test(description = "Search product in flipkart", dataProviderClass = DataProviderUtils.class, dataProvider = "parallelTestDataProvider")
	public void tcTAFrameworkPrefTestDemo01(String browser) throws Exception {
		
		//** Loading the test data from excel using the test case id */
		HashMap<String, String> testData = initTestData(workbookName, sheetName);
		String searchKeyword= testData.get("SearchKey");		
		
		// Get the web driver instance
		final WebDriver driver = WebDriverFactory.get(browser,proxy);
		browserMobProxy.newHar();
		
		String site = webSite.split("_")[0];
		String stakeHolderName = webSite.split("_")[1];

		ExtentTest extentedReport = Log.testCaseInfo(testData.get("Description") + "[" + browser + " || " + stakeHolderName.toUpperCase() + " ]", "Aspire TA Framework Java " + stakeHolderName.toUpperCase(), "Regression", "Aspire Systems");

		try {

			HomePage homePage = new HomePage(driver, site).get();
			Log.messageExtentReport("Step 1. Navigated to '" + stakeHolderName + "' Home Page!", extentedReport);

			homePage.searchProduct(searchKeyword);
			Log.messageWithExtentScreenshot("Search result for "+searchKeyword, driver, extentedReport, true);

			Log.testCaseResultExtentReport(extentedReport);

		}// try
		catch (Exception e) {
			Log.exception(e, driver);
		}// catch
		finally {			
			//WebDriverFactory.printHarData(browserMobProxy.getHar());
			browserMobProxy.stop();
			Log.endTestCase();
			driver.quit();
		}// finally

	}// tcTAFrameworkJAVADemo01
}// AspireTA_Java_Framework_Demo