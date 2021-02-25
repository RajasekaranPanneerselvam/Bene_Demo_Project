package afta.src.test.java.com.TAFrameworkJAVA.apppages;


import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import afta.src.test.java.com.TAFrameworkJAVA.support.Log;
import afta.src.test.java.com.TAFrameworkJAVA.support.Utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;


/**
 * 
 * @author harish.subramani
 * 
 */
public class HomePage extends LoadableComponent <HomePage> {


	AppiumDriver<MobileElement> driver;
	private boolean isPageLoaded;

	/**********************************************************************************************
	 ********************************* WebElements of Home Page ***********************************
	 **********************************************************************************************/

	
	@iOSFindBy(id = "btnSecond")
	@AndroidFindBy(id = "btnSecond")
	MobileElement goButton;

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
	public HomePage(AppiumDriver<MobileElement> driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}// HomePage

	@Override
	protected void isLoaded() {

		
		if (!isPageLoaded) {
			Assert.fail();
		}

		Utils.waitForPageLoad(driver);		

		if (!(goButton.isDisplayed())) {
			Log.fail("Home Page did not open up. Site might be down.", driver);
		}
		
		//searchProduct("Dress");

	}// isLoaded

	@Override
	protected void load() {
		isPageLoaded = true;
		Utils.waitForPageLoad(driver);

	}// load

	public void searchProduct(String textToSearch) {
		goButton.click();
		
	}//searchProduct

}// HomePage
