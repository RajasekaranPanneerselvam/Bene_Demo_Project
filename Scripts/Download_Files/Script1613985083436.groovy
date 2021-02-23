import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.webui.common.*
import org.openqa.selenium.*
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.*;

WebUI.openBrowser('')

WebUI.maximizeWindow()
/*
WebUI.navigateToUrl("https://www.html5webtemplates.co.uk/templates/")
WebElement element = WebUiCommonHelper.findWebElement(findTestObject('file_upload/download_link'), 30)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(element))


WebUI.navigateToUrl("https://filesamples.com/formats/txt")
WebElement element = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/file_upload/txt_download_link'), 30)
WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(element))
*/

WebUI.navigateToUrl("https://file-examples.com/index.php/text-files-and-archives-download/")

WebUI.scrollToElement(findTestObject('Object Repository/file_upload/search_box_object'), 30)
WebUI.click(findTestObject('Object Repository/file_upload/csv_file_down_link'))

File folder = new File("C:\\Users\\raja.panneerselvam\\Downloads")
List name_of_files = Arrays.asList(folder.list())
println(name_of_files)
if(name_of_files.contains("file_example_CSV_5000.csv")) {
	println("File is downloaded")
}
else {
	println("File dowbload is failed")
}
