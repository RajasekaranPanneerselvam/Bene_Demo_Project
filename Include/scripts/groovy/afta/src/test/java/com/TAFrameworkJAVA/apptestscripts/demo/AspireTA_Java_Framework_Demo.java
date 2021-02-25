package afta.src.test.java.com.TAFrameworkJAVA.apptestscripts.demo;

import java.util.HashMap;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

//import com.relevantcodes.extentreports.ExtentTest;

import afta.src.test.java.com.TAFrameworkJAVA.apppages.HomePage;
import afta.src.test.java.com.TAFrameworkJAVA.support.AppiumDriverFactory;
import afta.src.test.java.com.TAFrameworkJAVA.support.BaseTest;
import afta.src.test.java.com.TAFrameworkJAVA.support.DataProviderUtils;
import afta.src.test.java.com.TAFrameworkJAVA.support.EmailReport;
import afta.src.test.java.com.TAFrameworkJAVA.support.EnvironmentPropertiesReader;
import afta.src.test.java.com.TAFrameworkJAVA.support.Log;
import afta.src.test.java.utils.testlink.TestLinkAPIClient;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

@Listeners(EmailReport.class)
public class AspireTA_Java_Framework_Demo extends BaseTest {

	String notes = null;
	String testLinkResult = null;
	TestLinkAPIClient api = null;
	protected String status = null;
	EnvironmentPropertiesReader environmentPropertiesReader;
	private String workbookName = "testdata\\data\\Regression_PoC.xls";
	private String sheetName = "Demo";

	@Test(description = "Search product in flipkart", dataProviderClass = DataProviderUtils.class, dataProvider = "parallelTestDataProvider")
	public void tcTAFrameworkJAVADemo01(String browser) throws Exception {
		
		//** Loading the test data from excel using the test case id */
		HashMap<String, String> testData = initTestData(workbookName, sheetName);
		String searchKeyword= testData.get("SearchKey");
		
		
		// Get the web driver instance
		AppiumDriver<MobileElement> driver = AppiumDriverFactory.get();
		
		String site = webSite.split("_")[0];
		String stakeHolderName = webSite.split("_")[1];
		
		//ExtentTest extentedReport = Log.testCaseInfo(testData.get("Description") + "[" + browser + " || " + stakeHolderName.toUpperCase() + " ]", "Aspire TA Framework Java " + stakeHolderName.toUpperCase(), "Regression", "Aspire Systems");

		try {
			HomePage homePage = new HomePage(driver);
			//Log.messageExtentReport("Step 1. Navigated to '" + stakeHolderName + "' Home Page!", extentedReport);

			homePage.searchProduct("text");
			//Log.messageWithExtentScreenshot("Search result for "+searchKeyword, driver, extentedReport, true);

			//Log.testCaseResultExtentReport(extentedReport);

		}// try
		catch (Exception e) {
			Log.exception(e, driver);
		}// catch
		finally {
			Log.endTestCase();
			driver.quit();
		}// finally

	}// tcTAFrameworkJAVADemo01
}// AspireTA_Java_Framework_Demo