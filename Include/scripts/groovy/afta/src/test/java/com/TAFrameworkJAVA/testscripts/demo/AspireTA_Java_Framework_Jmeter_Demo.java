package afta.src.test.java.com.TAFrameworkJAVA.testscripts.demo;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

//import afta.src.test.java.com.TAFrameworkJAVA.apppages.HomePage;
import afta.src.test.java.com.TAFrameworkJAVA.support.EnvironmentPropertiesReader;
import afta.src.test.java.com.TAFrameworkJAVA.support.JmeterWebDriverFactory;
import afta.src.test.java.com.TAFrameworkJAVA.support.Log;
import afta.src.test.java.com.TAFrameworkJAVA.support.TestDataExtractor;
import afta.src.test.java.com.TAFrameworkJAVA.pages.HomePage;

public class AspireTA_Java_Framework_Jmeter_Demo {

	private static String xltestDataWorkBook;
	private static String xltestDataWorkSheet;
	EnvironmentPropertiesReader environmentPropertiesReader;
	private String webSite = "http://www.flipkart.com_flipkart";
	
	@Test
	public void tcTAFrameworkJAVADemo01() throws Exception {

		AspireTA_Java_Framework_Jmeter_Demo.xltestDataWorkBook = "testdata\\data\\Regression_PoC.xls";
		AspireTA_Java_Framework_Jmeter_Demo.xltestDataWorkSheet = "Demo";

		// Get the web driver instance
		final WebDriver driver = JmeterWebDriverFactory.get("chrome_windows");
		final TestDataExtractor testData = new TestDataExtractor();

		// Loading the test data from excel using the test case id
		testData.setWorkBookName(xltestDataWorkBook);
		testData.setWorkSheet(xltestDataWorkSheet);
		testData.setFilePathMapping(true);
		testData.setTestCaseId(Thread.currentThread().getStackTrace()[1].getMethodName().toUpperCase());
		testData.readData();

		String site = webSite.split("_")[0];
		String stakeHolderName = webSite.split("_")[1];

		try {

			HomePage homePage = new HomePage(driver, site).get();
			Log.message("Step 1. Navigated to '" + stakeHolderName + "' Home Page!");

			homePage.searchProduct(testData.get("SearchKey"));
			Log.message("Search Result", driver, true);

			Log.testCaseResult();

		}// try
		catch (Exception e) {
			Log.exception(e, driver);
		}// catch
		finally {
			Log.endTestCase();
			driver.quit();
		}// finally

	}// tcTAFrameworkJAVADemo01
	
}// AspireTA_Java_Framework_Jmeter_Demo