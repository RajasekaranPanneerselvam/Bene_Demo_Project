package afta.src.main.java.com.TAFrameworkJAVA.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import afta.src.test.java.com.TAFrameworkJAVA.support.BrowserActions;
import afta.src.test.java.com.TAFrameworkJAVA.support.OCRTextReader;
import afta.src.test.java.com.TAFrameworkJAVA.support.QRCodeAndBARCodeReaderWriter;
import afta.src.test.java.com.TAFrameworkJAVA.support.Utils;

public class SecurityCodeAndOCRTestPage extends LoadableComponent<SecurityCodeAndOCRTestPage> {

	WebDriver driver;
	String url = null;
	private boolean isPageLoaded;

	@FindBy(css = "img[src='/PMO/chrome/common/Prism.jpg']")
	WebElement imageInWeb;	

	@FindBy(css = "img[src*='QR_code_for_mobile_English_Wikipedia.svg.png']")
	WebElement QRCodeInWeb;	
	
	@FindBy(css = "img[class=' size-full wp-image-32009 alignright")
	WebElement BARCodeInWeb;
	
	@Override
	protected void isLoaded() throws Error {
		if (!isPageLoaded) {
			Assert.fail();
		}
		//if(!Utils.waitForElement(driver, lnkLogIn))		
			//Log.fail("Failed to load 'Login' page", driver);
	}

	@Override
	protected void load() {
		isPageLoaded = true;
		if(url != null)
			driver.get(url);
		Utils.waitForPageLoad(driver);		
	}

	/**
	 * Wrapper to scroll to the particular element and read the text from image.
	 * 
	 * @return imageTextFromWebElement
	 *  		  : Returns the text retrieved from the image.
	 */
	public String GetTheTextFromImage() throws Exception {
		// Scroll to the particular web element.
		BrowserActions.scrollToWebElement(driver, imageInWeb);
		
		// Get the text in the image.
		String imageTextFromWebElement = OCRTextReader.CaptureTextFromImageInWebElement(imageInWeb);
		
		// Returns the text retrieved from the image.
		return imageTextFromWebElement;
	}
	
	/**
	 * Wrapper to process the security code and read the content from it.
	 * 
	 * @param codeType
	 *            : BarCode/QRCode.
	 * @return textFromSecurityCodes
	 *  		  : Text that is decoded from security code.
	 */
	public String GetTheTextFromSecurityCodes(String typeOfCode) throws Exception {
		
		WebElement securityCode;

		if(typeOfCode.toUpperCase().equals("BARCODE"))
			securityCode = BARCodeInWeb;
		else
			securityCode = QRCodeInWeb;
			
		// Scroll to the particular web element.		
		BrowserActions.scrollToWebElement(driver, securityCode);
		
		// Get the content in the SecurityCode.
		String textFromSecurityCodes = 
				QRCodeAndBARCodeReaderWriter.DecodeTextFromSecurityCodesInWebElement(securityCode);
		
		// Text that is decoded from security code.
		return textFromSecurityCodes;
	}

	public SecurityCodeAndOCRTestPage(WebDriver driver) {
		this.driver = driver;
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, Utils.maxElementWait);
		PageFactory.initElements(finder, this);
	}

	public SecurityCodeAndOCRTestPage(WebDriver driver, String url) {
		this.url = url;
		this.driver = driver;
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, Utils.maxElementWait);
		PageFactory.initElements(finder, this);
	}

}
