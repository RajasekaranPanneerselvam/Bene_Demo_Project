package com.TAFrameworkJAVA.support;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.SkipException;

/**
 * Wrapper for Selenium WebDriver actions which will be performed on browser
 * 
 * Wrappers are provided with exception handling which throws Skip Exception on occurrence of NoSuchElementException
 * 
 * @author harish.subramani
 * 
 */
public class BrowserActions {

	/**
	 * Wrapper to type a text in browser text field
	 * 
	 * @param txt
	 *            : WebElement of the Text Field
	 * @param txtToType
	 *            : Text to type [String]
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 */
	public static void typeOnTextField(WebElement txt, String txtToType, WebDriver driver, String elementDescription) {

		if (!Utils.waitForElement(driver, txt, 1))
			throw new SkipException(elementDescription + " field not found in page!!");

		try {
			txt.clear();
			txt.click();
			txt.sendKeys(txtToType);
		}
		catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " field not found in page!!");

		}

	}// typeOnTextField

	/**
	 * Wrapper to type a text in browser text field
	 * 
	 * @param txt
	 *            : String Input (CSS Locator)
	 * @param txtToType
	 *            : Text to type [String]
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 */
	public static void typeOnTextField(String txt, String txtToType, WebDriver driver, String elementDescription) {

		WebElement element = driver.findElement(By.cssSelector(txt));
		if (!Utils.waitForElement(driver, element, 1))
			throw new SkipException(elementDescription + " field not found in page!!");

		try {
			element.clear();
			element.click();
			element.sendKeys(txtToType);
		}
		catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " field not found in page!!");

		}

	}// typeOnTextField

	/**
	 * Wrapper to click on button/text/radio/checkbox in browser
	 * 
	 * @param btn
	 *            : WebElement of the Button Field
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 */
	public static void clickOnButton(WebElement btn, WebDriver driver, String elementDescription) {

		if (!Utils.waitForElement(driver, btn, 5))
			throw new SkipException(elementDescription + " not found in page!!");

		try {
			btn.click();
		}
		catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " not found in page!!");
		}

	}// clickOnButton

	/**
	 * Wrapper to click on button/text/radio/checkbox in browser
	 * 
	 * @param btn
	 *            : String Input (CSS Locator) [of the Button Field]
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 */
	public static void clickOnButton(String btn, WebDriver driver, String elementDescription) {

		WebElement element = driver.findElement(By.cssSelector(btn));
		if (!Utils.waitForElement(driver, element, 1))
			throw new SkipException(elementDescription + " not found in page!!");

		try {
			element.click();
		}
		catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " not found in page!!");
		}

	}// clickOnButton

	/**
	 * Wrapper to check element visibility
	 * 
	 * @param driver
	 *            : WebDriver Instance
	 * @param cssSelectorForWebElement
	 *            : CSS Selector of the WebElement which visibility to check in String format
	 * @return: Boolean form - True if element visible/ False if element not visible
	 */
	public static boolean elementDisplayed(WebDriver driver, String cssSelectorForWebElement) {

		boolean displayed = false;

		try {
			displayed = driver.findElement(By.cssSelector(cssSelectorForWebElement)).isDisplayed();
		}
		catch (NoSuchElementException e) {
		}

		return displayed;

	}// elementDisplayed

	/**
	 * Wrapper to get a text from the provided WebElement
	 * 
	 * @param driver
	 *            : WebDriver Instance
	 * @param fromWhichTxtShldExtract
	 *            : WebElement from which text to be extract in String format
	 * @return: String - text from web element
	 * @param elementDescription
	 *            : Description about the WebElement
	 */
	public static String getText(WebDriver driver, WebElement fromWhichTxtShldExtract, String elementDescription) {

		String textFromHTMLAttribute = "";

		try {
			textFromHTMLAttribute = fromWhichTxtShldExtract.getText();

			if (textFromHTMLAttribute.isEmpty())
				textFromHTMLAttribute = fromWhichTxtShldExtract.getAttribute("textContent");

		}
		catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " not found in page!!");
		}

		return textFromHTMLAttribute;

	}// getText

	/**
	 * Wrapper to get a text from the provided WebElement
	 * 
	 * @param driver
	 *            : WebDriver Instance
	 * @param fromWhichTxtShldExtract
	 *            : String Input (CSS Locator) [from which text to be extract in String format]
	 * @return: String - text from web element
	 * @param elementDescription
	 *            : Description about the WebElement
	 */
	public static String getText(WebDriver driver, String fromWhichTxtShldExtract, String elementDescription) {

		String textFromHTMLAttribute = "";
		WebElement element = driver.findElement(By.cssSelector(fromWhichTxtShldExtract));

		try {
			textFromHTMLAttribute = element.getText();

			if (textFromHTMLAttribute.isEmpty())
				textFromHTMLAttribute = element.getAttribute("textContent");

		}
		catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " not found in page!!");
		}

		return textFromHTMLAttribute;

	}// getText

	/**
	 * Wrapper to get a text from the provided WebElement's Attribute
	 * 
	 * @param driver
	 *            : WebDriver Instance
	 * @param fromWhichTxtShldExtract
	 *            : WebElement from which text to be extract in String format
	 * @param attributeName
	 *            : Attribute Name from which text should be extracted like "style, class, value,..."
	 * @return: String - text from web element
	 * @param elementDescription
	 *            : Description about the WebElement
	 */
	public static String getTextFromAttribute(WebDriver driver, WebElement fromWhichTxtShldExtract, String attributeName, String elementDescription) {

		String textFromHTMLAttribute = "";

		try {
			textFromHTMLAttribute = fromWhichTxtShldExtract.getAttribute(attributeName);
		}
		catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " not found in page!!");
		}

		return textFromHTMLAttribute;

	}// getTextFromAttribute

	/**
	 * Wrapper to get a text from the provided WebElement's Attribute
	 * 
	 * @param driver
	 *            : WebDriver Instance
	 * @param fromWhichTxtShldExtract
	 *            : String Input (CSS Locator) [from which text to be extract in String format]
	 * @param attributeName
	 *            : Attribute Name from which text should be extracted like "style, class, value,..."
	 * @return: String - text from web element
	 * @param elementDescription
	 *            : Description about the WebElement
	 */
	public static String getTextFromAttribute(WebDriver driver, String fromWhichTxtShldExtract, String attributeName, String elementDescription) {

		String textFromHTMLAttribute = "";
		WebElement element = driver.findElement(By.cssSelector(fromWhichTxtShldExtract));

		try {
			textFromHTMLAttribute = element.getAttribute(attributeName);
		}
		catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " not found in page!!");
		}

		return textFromHTMLAttribute;

	}// getTextFromAttribute

	/**
	 * Wrapper to select option from combobox in browser
	 * 
	 * @param btn
	 *            : WebElement of the combobox Field
	 * 
	 * @param optToSelect
	 *            : option to select from combobox
	 * 
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 */
	public static void selectFromComboBox(WebElement btn, String optToSelect, WebDriver driver, String elementDescription) {

		if (!Utils.waitForElement(driver, btn, 1))
			throw new SkipException(elementDescription + " not found in page!!");

		try {
			Select selectBox = new Select(btn);
			selectBox.selectByValue(optToSelect);
		}
		catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " not found in page!!");
		}

	}// selectFromComboBox

	/**
	 * Wrapper to select option from combobox in browser
	 * 
	 * @param btn
	 *            : String Input (CSS Locator) [of the ComboBox Field]
	 * 
	 * @param optToSelect
	 *            : option to select from combobox
	 * 
	 * @param driver
	 *            : WebDriver Instances
	 * @param elementDescription
	 *            : Description about the WebElement
	 */
	public static void selectFromComboBox(String btn, String optToSelect, WebDriver driver, String elementDescription) {

		WebElement element = driver.findElement(By.cssSelector(btn));
		if (!Utils.waitForElement(driver, element, 1))
			throw new SkipException(elementDescription + " not found in page!!");

		try {
			Select selectBox = new Select(element);
			selectBox.selectByValue(optToSelect);
		}
		catch (NoSuchElementException e) {
			throw new SkipException(elementDescription + " not found in page!!");
		}

	}// selectFromComboBox

	/**
	 * Wrapper to scroll to a web element to make it visible in the screen 
	 *  
	 * @param driver
	 *            : WebDriver Instances
	 * @param webElement
	 *            : WebElement Instance
	 */	
	public static void scrollToWebElement(WebDriver driver, WebElement webElement) {

        // Create an instance of JavascriptExecutor Class.
		JavascriptExecutor javaScript = (JavascriptExecutor) driver;
		
		// scroll to particular element
		javaScript.executeScript("arguments[0].scrollIntoView(true);", webElement);

	}
}// BrowserActions