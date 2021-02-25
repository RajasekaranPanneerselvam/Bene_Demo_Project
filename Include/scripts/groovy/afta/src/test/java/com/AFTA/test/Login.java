package afta.src.test.java.com.AFTA.test;

/**
 * @author indhu.kanagaraj
 */

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import afta.src.main.java.com.TAFrameworkJAVA.pages.LoginPage;
import afta.src.test.java.com.TAFrameworkJAVA.support.BaseTest;
import afta.src.test.java.com.TAFrameworkJAVA.support.DataProviderUtils;
import afta.src.test.java.com.TAFrameworkJAVA.support.EmailReport;
import afta.src.test.java.com.TAFrameworkJAVA.support.EnvironmentPropertiesReader;
import afta.src.test.java.com.TAFrameworkJAVA.support.Log;
import afta.src.test.java.com.TAFrameworkJAVA.support.WebDriverFactory;
import afta.src.test.java.utils.testlink.TestLinkAPIClient;

@Listeners(EmailReport.class)
public class Login extends BaseTest {

	String notes = null;
	String testLinkResult = null;
	TestLinkAPIClient api = null;
	protected String status = null;
	EnvironmentPropertiesReader environmentPropertiesReader;
	private String workbookName = "testdata\\data\\Login.xls";
	private String sheetName = "Login";

	@Test(description = "Verify Login page of Intranet", dataProviderClass = DataProviderUtils.class, dataProvider = "parallelTestDataProvider")
	public void verifyLogin(String browser) throws Exception {

		//** Loading the test data from excel using the test case id */
		HashMap<String, String> testData = initTestData(workbookName, sheetName);
		String username = testData.get("Username");
		String password = testData.get("Password");

		LoginPage loginPage = null;
		// Get the web driver instance
		final WebDriver driver = WebDriverFactory.get(browser);

		String url = webSite.split("_")[0];
		String stakeHolderName = webSite.split("_")[1];

		Log.testCaseInfo(testData);
		Log.testCaseInfo(testData.get("Description") + "[" + browser + " || " + stakeHolderName.toUpperCase() + " ]", "Aspire TA Framework Java " + stakeHolderName.toUpperCase(), "Regression", "Aspire Systems");

		try {
			loginPage = new LoginPage(driver, url).get();
			Log.assertThat(loginPage != null, "Succesfully navigated to Intranet 'Login' page",
					"Failed to navigate to intranet 'Login' page", driver);

			Log.assertThat(loginPage.clickLogInLnk(), "Clicked 'Login' link and 'Login' popup displayed", 
					"Unable to click 'Login' link", driver);

			loginPage.enterUsername(username);
			Log.message("Entered username : " + username, driver);

			loginPage.enterPassword(password);
			Log.message("Entered password", driver);

			Log.assertThat(loginPage.clickLoginBtn(), "Clicked 'Login' button and navigated to 'Home' page", 
					"Failed to load 'Home' page on clicking 'Login' button", driver);

			Log.assertThat(loginPage.logoutFromApplication(), "Successfully logged out from the application", 
					"Failed to log out from the application", driver);

			Log.testCaseResult();
		}
		catch(Exception ex) {
			Log.exception(ex, driver);
		}
		finally {
			Log.endTestCase();
			driver.quit();
		}
	}

	@Test(description = "Search aspirian and verify", dataProviderClass = DataProviderUtils.class, dataProvider = "parallelTestDataProvider")
	public void searchAspirianAndVerify(String browser) throws Exception {

		//** Loading the test data from excel using the test case id */
		HashMap<String, String> testData = initTestData(workbookName, sheetName);
		String aspirian = testData.get("SearchName");

		LoginPage loginPage = null;
		// Get the web driver instance
		final WebDriver driver = WebDriverFactory.get(browser);

		String url = webSite.split("_")[0];
		String stakeHolderName = webSite.split("_")[1];

		Log.testCaseInfo(testData);
		Log.testCaseInfo(testData.get("Description") + "[" + browser + " || " + stakeHolderName.toUpperCase() + " ]", "Aspire TA Framework Java " + stakeHolderName.toUpperCase(), "Regression", "Aspire Systems");

		try {
			loginPage = new LoginPage(driver, url).get();
			Log.assertThat(loginPage != null, "Succesfully navigated to Intranet 'Login' page",
					"Failed to navigate to intranet 'Login' page", driver);

			loginPage.enterAspirianNameInSearch(aspirian);
			Log.message("Entered Aspirian name : " + aspirian, driver);

			loginPage.clickSearchIcon();
			Log.message("Clicked 'Search' icon", driver);

			Log.message("Username displayed for the given search : " + loginPage.getSearchUsername(), driver);

			Log.testCaseResult();
		}
		catch(Exception ex) {
			Log.exception(ex, driver);
		}
		finally {
			Log.endTestCase();
			driver.quit();
		}
	}
}
