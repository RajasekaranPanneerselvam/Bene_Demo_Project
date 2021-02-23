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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.common.*
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions as Actions

WebUI.openBrowser('')

WebUI.maximizeWindow()



WebUI.navigateToUrl(GlobalVariable.G_url)

WebUI.click(findTestObject('Object Repository/file_upload/Page_BenefitHub/button_Guest'))

WebUI.click(findTestObject('Object Repository/file_upload/Page_BenefitHub/a_Login'))

WebUI.setText(findTestObject('Object Repository/file_upload/Page_BenefitHub/input_Login_Username'), 'BHadmin1@BenefitHub.com')

WebUI.setEncryptedText(findTestObject('Object Repository/file_upload/Page_BenefitHub/input_Required_Password'), '1gLi+6BLCLgxTfN7dyJwjg==')

WebUI.click(findTestObject('file_upload/Page_BenefitHub/button_Submit'))

WebElement element = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/file_upload/Page_BenefitHub/i_tag_edit_locator'), 
    30)

WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(element))

WebDriver driver = DriverFactory.getWebDriver()

WebElement sourceElement = WebUiCommonHelper.findWebElement(findTestObject('file_upload/drag_and_drop_locator', [('object') : 'Purchase Program']), 
    30)

WebElement destElement = WebUiCommonHelper.findWebElement(findTestObject('file_upload/drag_and_drop_locator', [('object') : 'Smart Enroll']), 
    30)

java_script = (((((((('var src=arguments[0],tgt=arguments[1];var dataTransfer={dropEffe' + 'ct:\'\',effectAllowed:\'all\',files:[],items:{},types:[],setData:fun') + 
'ction(format,data){this.items[format]=data;this.types.append(for') + 'mat);},getData:function(format){return this.items[format];},clea') + 
'rData:function(format){}};var emit=function(event,target){var ev') + 't=document.createEvent(\'Event\');evt.initEvent(event,true,false);') + 
'evt.dataTransfer=dataTransfer;target.dispatchEvent(evt);};emit(\'') + 'dragstart\',src);emit(\'dragenter\',tgt);emit(\'dragover\',tgt);emit(') + 
'\'drop\',tgt);emit(\'dragend\',src);')

    ((driver) as JavascriptExecutor).executeScript(java_script, sourceElement, destElement)

WebUI.click(findTestObject('file_upload/Page_BenefitHub/button_Save'))

/*
Actions builder = new Actions(driver)
builder.clickAndHold(sourceElement).perform()
builder.moveToElement(destElement).perform()
WebUI.delay(3)
builder.release(destElement).perform()
*/
WebUI.click(findTestObject('file_upload/Page_BenefitHub/a_Edit'))

WebUI.click(findTestObject('file_upload/Page_BenefitHub/span_Add Image'))

WebUI.switchToFrame(findTestObject('file_upload/frame_locator'), 50, FailureHandling.STOP_ON_FAILURE)

WebUI.waitForElementVisible(findTestObject('file_upload/h1_header', [('title') : 'Content Library']), 50)

WebUI.click(findTestObject('file_upload/file_upload_button'))

WebUI.waitForElementVisible(findTestObject('file_upload/h1_header', [('title') : 'Upload to Content Library']), 50)

WebUI.uploadFileWithDragAndDrop(findTestObject('file_upload/Page_BenefitHub/content Library_dropzone'), 'C:\\Users\\raja.panneerselvam\\Documents\\polo1.png')

println('uploaded successfully')

WebUI.delay(5)

WebUI.waitForElementVisible(findTestObject('file_upload/h1_header', [('title') : 'Content Library']), 50)

WebUI.waitForElementPresent(findTestObject('file_upload/Page_BenefitHub/search_file_locator/search_file', [('file_name') : 'polo1.png']), 
    50)

WebUI.switchToDefaultContent()

