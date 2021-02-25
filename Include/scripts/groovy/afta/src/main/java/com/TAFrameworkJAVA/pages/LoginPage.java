package afta.src.main.java.com.TAFrameworkJAVA.pages;

/**
 * @author indhu.kanagaraj
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import afta.src.test.java.com.TAFrameworkJAVA.support.BrowserActions;
import afta.src.test.java.com.TAFrameworkJAVA.support.Log;
import afta.src.test.java.com.TAFrameworkJAVA.support.Utils;

public class LoginPage extends LoadableComponent<LoginPage> {

	WebDriver driver;
	String url = null;
	private boolean isPageLoaded;

	@FindBy(partialLinkText = "Log In")
	WebElement lnkLogIn;	

	@FindBy(id = "edit-name")
	WebElement txtUsername;

	@FindBy(id = "edit-pass")
	WebElement txtPassword;

	@FindBy(id = "edit-submit--2")
	WebElement btnLogIn;

	@FindBy(linkText = "IDM/PMS")
	WebElement lnkIdmPms;

	@FindBy(id = "loginCheck")
	WebElement lnkLoginName;

	@FindBy(className = "icon-signout")
	WebElement lnkLogout;

	@FindBy(id = "searchtextbox")
	WebElement txtSearchAspirian;

	@FindBy(id = "searchbtn")
	WebElement iconSearch;

	@FindBy(xpath = "//div[contains(@class,'searchname')]/b/a")
	WebElement lblSearchUsername;

	@Override
	protected void isLoaded() throws Error {
		if (!isPageLoaded) {
			Assert.fail();
		}
		if(!Utils.waitForElement(driver, lnkLogIn))		
			Log.fail("Failed to load 'Login' page", driver);
	}

	@Override
	protected void load() {
		isPageLoaded = true;
		if(url != null)
			driver.get(url);
		Utils.waitForPageLoad(driver);		
	}

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, Utils.maxElementWait);
		PageFactory.initElements(finder, this);
	}

	public LoginPage(WebDriver driver, String url) {
		this.url = url;
		this.driver = driver;
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, Utils.maxElementWait);
		PageFactory.initElements(finder, this);
	}

	public Boolean clickLogInLnk() {
		if(Utils.waitForElement(driver, lnkLogIn)) {
			BrowserActions.clickOnButton(lnkLogIn, driver, "Login link");
			return Utils.waitForElement(driver, txtUsername, 3);
		}
		return false;
	}

	public void enterUsername(String username) {
		BrowserActions.typeOnTextField(txtUsername, username, driver, "Username");
	}

	public void enterPassword(String password) {
		BrowserActions.typeOnTextField(txtPassword, password, driver, "Password");
	}

	public Boolean clickLoginBtn() {
		BrowserActions.clickOnButton(btnLogIn, driver, "Login button");
		return Utils.waitForElement(driver, lnkIdmPms, 5);
	}

	public Boolean logoutFromApplication() {
		Actions builder = new Actions(driver);
		Action action = builder
				.moveToElement(lnkLoginName)
				.click(lnkLoginName)
				.moveToElement(lnkLogout)
				.click(lnkLogout)
				.build();
		action.perform();

		Utils.waitForPageLoad(driver, 5);
		return Utils.waitForElement(driver, lnkLogIn, 5);
	}

	public void enterAspirianNameInSearch(String aspirianName) {
		BrowserActions.typeOnTextField(txtSearchAspirian, aspirianName, driver, "Search aspirian");
	}

	public void clickSearchIcon() {
		BrowserActions.clickOnButton(iconSearch, driver, "Search icon");
	}

	public String getSearchUsername() {
		if(Utils.waitForElement(driver, lblSearchUsername, 5))
			return lblSearchUsername.getText().trim();
		return null;
	}
}
