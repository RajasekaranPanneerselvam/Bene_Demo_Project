package afta.src.test.java.com.AFTA.test;

import java.nio.file.Paths;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import afta.src.main.java.com.TAFrameworkJAVA.pages.SecurityCodeAndOCRTestPage;
import afta.src.test.java.com.TAFrameworkJAVA.support.BaseTest;
import afta.src.test.java.com.TAFrameworkJAVA.support.DataProviderUtils;
import afta.src.test.java.com.TAFrameworkJAVA.support.EmailReport;
import afta.src.test.java.com.TAFrameworkJAVA.support.EnvironmentPropertiesReader;
import afta.src.test.java.com.TAFrameworkJAVA.support.Log;
import afta.src.test.java.com.TAFrameworkJAVA.support.OCRTextReader;
import afta.src.test.java.com.TAFrameworkJAVA.support.QRCodeAndBARCodeReaderWriter;
import afta.src.test.java.com.TAFrameworkJAVA.support.WebDriverFactory;
import afta.src.test.java.utils.testlink.TestLinkAPIClient;

@Listeners(EmailReport.class)
public class SecurityCodeAndOCRTests extends BaseTest {

	String notes = null;
	String testLinkResult = null;
	TestLinkAPIClient api = null;
	protected String status = null;
	EnvironmentPropertiesReader environmentPropertiesReader;
	private String workbookName = "testdata\\data\\SecurityCodeAndOCRTests.xls";
	private String sheetName = "SecurityCodeAndOCRTests";	

	@Test(description = "Read the text from different type of files.", dataProviderClass = DataProviderUtils.class, dataProvider = "parallelTestDataProvider")
	public void readTextFromFiles(String browser) throws Exception {

		//** Loading the test data from excel using the test case id */
		HashMap<String, String> testData = initTestData(workbookName, sheetName);
		String expectedTextInImage = testData.get("ExpectedTextInImage");
		String filePath = Paths.get("").toAbsolutePath().toString()+ "\\src\\main\\resources\\OCRtestdata\\" + testData.get("FileName");
		
		Log.testCaseInfo(testData);
		Log.testCaseInfo(testData.get("Description"));

		try {
			// Read the text from image file.
			String textInFile = OCRTextReader.CaptureTextFromFiles(filePath);
			
			Log.assertThat(textInFile.toUpperCase().contains(expectedTextInImage.toUpperCase()), 
					"Text from the image has expected content, image content : " + textInFile , 
					". Text from the image does not have expected content, image content : " + textInFile);
			
			Log.testCaseResult();
		}
		catch(Exception ex) {
			Log.exception(ex);
		}
		finally {
			Log.endTestCase();
		}
	}
	
	@Test(description = "Read the text from image which is a web element.", dataProviderClass = DataProviderUtils.class, dataProvider = "parallelTestDataProvider")
	public void readTextFromImageInWebElement(String browser) throws Exception {

		//** Loading the test data from excel using the test case id */
		HashMap<String, String> testData = initTestData(workbookName, sheetName);
		String expectedTextInImage = testData.get("ExpectedTextInImage");
		String imageUrl = testData.get("URL");
		
		SecurityCodeAndOCRTestPage webPage = null;
		// Get the web driver instance
		final WebDriver driver = WebDriverFactory.get(browser);
		
		Log.testCaseInfo(testData);
		Log.testCaseInfo(testData.get("Description"));

		try {
			// Navigate to the web url.
			webPage = new SecurityCodeAndOCRTestPage(driver, imageUrl).get();
				
			// Get the text from the image which is a web element.
			String imageTextFromWebElement = webPage.GetTheTextFromImage();									
			
			Thread.sleep(5000);
			
			Log.assertThat(imageTextFromWebElement.toUpperCase().contains(expectedTextInImage.toUpperCase()), 
					"The image has the expected text content in it, image content : " + imageTextFromWebElement, 
					"The content of the image does not have the expected text '" + "'" + expectedTextInImage + 
					", actual image content : " + imageTextFromWebElement, driver);
			
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
		
	@Test(description = "Generate QR code and verify the content.", dataProviderClass = DataProviderUtils.class, dataProvider = "parallelTestDataProvider")
	public void generateQRCodeAndVerify(String browser) throws Exception {

		//** Loading the test data from excel using the test case id */
		HashMap<String, String> testData = initTestData(workbookName, sheetName);
		String securityCodeContent = testData.get("ExpectedTextInSecurityCode");
		String typeOfCode = testData.get("TypeOfSecurityCode");
		String filePath = Paths.get("").toAbsolutePath().toString()+ "\\src\\main\\resources\\OCRtestdata\\" + testData.get("FileName");
		
		Log.testCaseInfo(testData);
		Log.testCaseInfo(testData.get("Description"));

		try {
			
			// Generate QR code as save it in a file.
			QRCodeAndBARCodeReaderWriter.GenerateQRCODEorBARCODE(securityCodeContent, filePath, typeOfCode, 300, 300);
			
			// Read the content of the QR code from the file.
			String textInSecurityCode = QRCodeAndBARCodeReaderWriter.DecodeTextFromSecurityCodesInFile(filePath);

			Log.assertThat(securityCodeContent.equals(textInSecurityCode), 
					typeOfCode + " is created successfully and the content is : " + textInSecurityCode, 
					". Error in generating the "+ typeOfCode + " and the content is : " + textInSecurityCode);
			
			Log.testCaseResult();
		}
		catch(Exception ex) {
			Log.exception(ex);
		}
		finally {
			Log.endTestCase();
		}
	}

	@Test(description = "Generate Bar code and verify the content.", dataProviderClass = DataProviderUtils.class, dataProvider = "parallelTestDataProvider")
	public void generateBarCodeAndVerify(String browser) throws Exception {

		//** Loading the test data from excel using the test case id */
		HashMap<String, String> testData = initTestData(workbookName, sheetName);
		String securityCodeContent = testData.get("ExpectedTextInSecurityCode");
		String typeOfCode = testData.get("TypeOfSecurityCode");
		String filePath = Paths.get("").toAbsolutePath().toString()+ "\\src\\main\\resources\\OCRtestdata\\" + testData.get("FileName");
		
		Log.testCaseInfo(testData);
		Log.testCaseInfo(testData.get("Description"));

		try {
			
			// Generate Bar code as save it in a file.
			QRCodeAndBARCodeReaderWriter.GenerateQRCODEorBARCODE(securityCodeContent, filePath, typeOfCode, 300, 300);
			
			// Read the content of the Bar code from the file.
			String textInSecurityCode = QRCodeAndBARCodeReaderWriter.DecodeTextFromSecurityCodesInFile(filePath);

			Log.assertThat(securityCodeContent.equals(textInSecurityCode), 
					typeOfCode + " is created successfully and the content is : " + textInSecurityCode, 
					". Error in generating the "+ typeOfCode + " and the content is : " + textInSecurityCode);
			
			Log.testCaseResult();
		}
		catch(Exception ex) {
			Log.exception(ex);
		}
		finally {
			Log.endTestCase();
		}
	}
	
	@Test(description = "Read the text from QR Code which is a web element.", dataProviderClass = DataProviderUtils.class, dataProvider = "parallelTestDataProvider")
	public void readContentFromQRCodeInWebElement(String browser) throws Exception {

		//** Loading the test data from excel using the test case id */
		HashMap<String, String> testData = initTestData(workbookName, sheetName);
		String expectedTextInSecurityCode = testData.get("ExpectedTextInSecurityCode");
		String typeOfCode = testData.get("TypeOfSecurityCode");
		String imageUrl = testData.get("URL");
		
		SecurityCodeAndOCRTestPage webPage = null;
		// Get the web driver instance
		final WebDriver driver = WebDriverFactory.get(browser);
		
		Log.testCaseInfo(testData);
		Log.testCaseInfo(testData.get("Description"));

		try {
			// Navigate to the web url.
			webPage = new SecurityCodeAndOCRTestPage(driver, imageUrl).get();
				
			// Get the text from the image which is a web element.
			String textFromSecurityCode = webPage.GetTheTextFromSecurityCodes(typeOfCode);									
					
			Thread.sleep(5000);
			
			Log.assertThat(textFromSecurityCode.toUpperCase().contains(expectedTextInSecurityCode.toUpperCase()), 
					"The security code has the expected text content in it, actual content : " + textFromSecurityCode, 
					"The content of the security code does not have the expected text '" + "'" + expectedTextInSecurityCode + 
					", actual content : " + textFromSecurityCode, driver);
			
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
	
	@Test(description = "Read the text from Bar Code which is a web element.", dataProviderClass = DataProviderUtils.class, dataProvider = "parallelTestDataProvider")
	public void readContentFromBARCodeInWebElement(String browser) throws Exception {

		//** Loading the test data from excel using the test case id */
		HashMap<String, String> testData = initTestData(workbookName, sheetName);
		String expectedTextInSecurityCode = testData.get("ExpectedTextInSecurityCode").toString();
		String typeOfCode = testData.get("TypeOfSecurityCode");
		String imageUrl = testData.get("URL");
		
		SecurityCodeAndOCRTestPage webPage = null;
		// Get the web driver instance
		final WebDriver driver = WebDriverFactory.get(browser);
		
		Log.testCaseInfo(testData);
		Log.testCaseInfo(testData.get("Description"));

		try {
			// Navigate to the web url.
			webPage = new SecurityCodeAndOCRTestPage(driver, imageUrl).get();
				
			// Get the text from the image which is a web element.
			String textFromSecurityCode = webPage.GetTheTextFromSecurityCodes(typeOfCode);									
					
			Thread.sleep(5000);
			
			Log.assertThat(textFromSecurityCode.toUpperCase().contains(expectedTextInSecurityCode.toUpperCase()), 
					"The security code has the expected text content in it, actual content : " + textFromSecurityCode, 
					"The content of the security code does not have the expected text '" + "expectedTextInSecurityCode'" +  
					", actual content : " + textFromSecurityCode, driver);
			
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
