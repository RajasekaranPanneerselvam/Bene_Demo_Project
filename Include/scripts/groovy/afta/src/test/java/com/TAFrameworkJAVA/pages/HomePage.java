package afta.src.test.java.com.TAFrameworkJAVA.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import afta.src.test.java.com.TAFrameworkJAVA.support.BrowserActions;
import afta.src.test.java.com.TAFrameworkJAVA.support.Log;
import afta.src.test.java.com.TAFrameworkJAVA.support.Utils;


/**
 * 
 * @author harish.subramani
 * 
 */
public class HomePage extends LoadableComponent <HomePage> {

	private String appURL;
	private WebDriver driver;
	private boolean isPageLoaded;

	/**********************************************************************************************
	 ********************************* WebElements of Home Page ***********************************
	 **********************************************************************************************/

	@CacheLookup
	@FindBy(css = "#lst-ib")
	WebElement txtSearch;

	@CacheLookup
	//@FindBy(css = "input[value='SEARCH'], input[type='submit'], .header-form-search button")
	//@FindBy(css="#mb-j-search-field")
	@FindBy(css="div.sbico")
	WebElement btnSearch;

	@CacheLookup
	@FindBy(css = "div#marketorial-close")
	WebElement btnClosePopup;

	/**********************************************************************************************
	 ********************************* WebElements of Home Page - Ends ****************************
	 **********************************************************************************************/

	/**
	 * constructor of the class
	 * 
	 * @param driver
	 *            : Webdriver
	 * 
	 * @param url
	 *            : UAT URL
	 */
	public HomePage(WebDriver driver, String url) {
		appURL = url;
		this.driver = driver;
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, Utils.maxElementWait);
		PageFactory.initElements(finder, this);
	}// HomePage

	@Override
	protected void isLoaded() {

		
		if (!isPageLoaded) {
			Assert.fail();
		}

		Utils.waitForPageLoad(driver);		

		if (isPageLoaded && !(Utils.waitForElement(driver, txtSearch))) {
			Log.fail("Home Page did not open up. Site might be down.", driver);
		}
		
		//searchProduct("Dress");

	}// isLoaded

	@Override
	protected void load() {

		isPageLoaded = true;
		driver.get(appURL);
		
		Utils.waitForPageLoad(driver);

		/*try {
			Utils.switchWindows(driver, "Home Page", "title", "false");
		}
		catch (Exception e) {
		}*/

		//Utils.waitForElement(driver, btnSearch);

	}// load

	public void searchProduct(String textToSearch) {
		
		//final long startTime = StopWatch.startTime();
		BrowserActions.typeOnTextField(txtSearch, textToSearch, driver, "Search Box");
		BrowserActions.clickOnButton(btnSearch, driver, "Search");
		Utils.waitForPageLoad(driver);		
		
	//	Log.event("Searched the provided product!", StopWatch.elapsedTime(startTime));
		
	}//searchProduct

}// HomePage
