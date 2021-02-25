import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.ie.InternetExplorerDriver;

class TestListener {
	/**
	 * Executes before every test case starts.
	 * @param testCaseContext related information of the executed test case.
	 */
	@BeforeTestCase
	def sampleBeforeTestCase(TestCaseContext testCaseContext) {
		println testCaseContext.getTestCaseId()
		println testCaseContext.getTestCaseVariables()
		
		
		if(GlobalVariable.browser == "chrome")
			{
			String pathToChromerDriver = "C:\\Users\\raja.panneerselvam\\Desktop\\Katalon_Studio_Windows_64-7.9.1\\configuration\\resources\\drivers\\chromedriver_win32\\chromedriver.exe"
			System.setProperty("webdriver.chrome.driver", pathToChromerDriver)
			
			
			Map<String, Object> prefs = new HashMap<String, Object>();
			//prefs.put("download.default_directory", "C:\\Users\\raja.panneerselvam\\Downloads");
			
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--incognito");
				options.addArguments("--start-maximized");
				options.addArguments("--disable-popup-blocking");
				options.addArguments("--disable-extensions");
				options.addArguments("--test-type");
				
				prefs.put("profile.default_content_settings.popups", 0)
				prefs.put("download.prompt_for_download", "false")
				prefs.put("download.default_directory", System.getProperty("user.dir") + "/Imp Files")
				
				options.setExperimentalOption("prefs", prefs);
				
			// Create an object of desired capabilities class with Chrome driver
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			
			options.merge(capabilities);
			
			WebDriver driver = new ChromeDriver(capabilities);
			DriverFactory.changeWebDriver(driver)
			
			}
			
			
			else if(GlobalVariable.browser == "ie")
			{
					
				System.setProperty("webdriver.ie.driver", "C:\\Users\\raja.panneerselvam\\Desktop\\Katalon_Studio_Windows_64-7.9.1\\configuration\\resources\\drivers\\iedriver_win64\\IEDriverServer.exe");
				
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				
			  capabilities.setCapability(CapabilityType.BROWSER_NAME, "internet explorer");
			  capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			 	
			   //it is used to initialize the IE driver
			   WebDriver driver = new InternetExplorerDriver(capabilities);
			   DriverFactory.changeWebDriver(driver)
			}
			
			
			else if(GlobalVariable.browser == "firefox")
			{
				
				System.setProperty("webdriver.gecko.driver", "C:\\Users\\raja.panneerselvam\\Desktop\\Katalon_Studio_Windows_64-7.9.1\\configuration\\resources\\drivers\\firefox_win64\\geckodriver.exe");
				String downloadFilepath = System.getProperty("user.dir") + "/Imp Files";
				FirefoxProfile profile= new FirefoxProfile();
				profile.setAcceptUntrustedCertificates(true);
				profile.setAssumeUntrustedCertificateIssuer(true);
				 
				FirefoxOptions options = new FirefoxOptions();
				options.setAcceptInsecureCerts(true);
				options.addPreference("browser.download.folderList", 2);
				profile.setPreference("browser.download.dir", downloadFilepath);
				profile.setPreference("browser.download.defaultFolder",downloadFilepath);
				
				options.addPreference("browser.helperApps.alwaysAsk.force", false);
				options.addPreference("browser.download.manager.showWhenStarting", false);
				options.addPreference("browser.helperApps.neverAsk.saveToDisk","multipart/x-zip,application/zip,application/x-zip-compressed,application/x-compressed,application/msword,application/csv,text/csv,image/png ,image/jpeg, application/pdf, text/html,text/plain,  application/excel, application/vnd.ms-excel, application/x-excel, application/x-msexcel, application/octet-stream");
				
				WebDriver driver = new FirefoxDriver(options);
				
				DriverFactory.changeWebDriver(driver)
				
				WebUI.maximizeWindow()
						
			}
	}

	/**
	 * Executes after every test case ends.
	 * @param testCaseContext related information of the executed test case.
	 */
	@AfterTestCase
	def sampleAfterTestCase(TestCaseContext testCaseContext) {
		println testCaseContext.getTestCaseId()
		println testCaseContext.getTestCaseStatus()
		
		WebUI.closeBrowser()
	}

	/**
	 * Executes before every test suite starts.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@BeforeTestSuite
	def sampleBeforeTestSuite(TestSuiteContext testSuiteContext) {
		println testSuiteContext.getTestSuiteId()
	}

	/**
	 * Executes after every test suite ends.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@AfterTestSuite
	def sampleAfterTestSuite(TestSuiteContext testSuiteContext) {
		println testSuiteContext.getTestSuiteId()
	}
}