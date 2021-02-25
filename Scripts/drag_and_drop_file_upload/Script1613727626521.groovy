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

CustomKeywords.'genericMethods.generic_methods.navigate_to_url'(GlobalVariable.G_url)

CustomKeywords.'helper_methods.helper_methods.loginToApplication'();

CustomKeywords.'genericMethods.generic_methods.click_hidden_element'(findTestObject('Object Repository/file_upload/Page_BenefitHub/i_tag_edit_locator'))
WebDriver driver = DriverFactory.getWebDriver()

CustomKeywords.'genericMethods.generic_methods.dragAndDropToElement'(driver, findTestObject('file_upload/drag_and_drop_locator', [('object') : 'Purchase Program']), findTestObject('file_upload/drag_and_drop_locator', [('object') : 'Smart Enroll']))
CustomKeywords.'genericMethods.generic_methods.click_element'(findTestObject('file_upload/Page_BenefitHub/button_Save'))
CustomKeywords.'genericMethods.generic_methods.click_element'(findTestObject('file_upload/Page_BenefitHub/a_Edit'))

CustomKeywords.'genericMethods.generic_methods.click_element'(findTestObject('file_upload/Page_BenefitHub/span_Add Image'))

CustomKeywords.'genericMethods.generic_methods.switch_To_Frame'(findTestObject('file_upload/frame_locator'))

CustomKeywords.'genericMethods.generic_methods.wait_until_element_visible'(findTestObject('file_upload/h1_header', [('title') : 'Content Library']))

CustomKeywords.'genericMethods.generic_methods.click_element'(findTestObject('file_upload/file_upload_button'))

CustomKeywords.'genericMethods.generic_methods.wait_until_element_visible'(findTestObject('file_upload/h1_header', [('title') : 'Upload to Content Library']))

CustomKeywords.'genericMethods.generic_methods.upload_file'(findTestObject('file_upload/Page_BenefitHub/content Library_dropzone'), 'C:\\Users\\raja.panneerselvam\\Documents\\saraswathi.png')

WebUI.delay(5)
CustomKeywords.'genericMethods.generic_methods.wait_until_element_visible'(findTestObject('file_upload/h1_header', [('title') : 'Content Library']))

CustomKeywords.'genericMethods.generic_methods.wait_until_element_visible'(findTestObject('file_upload/Page_BenefitHub/search_file_locator/search_file', [('file_name') : 'saraswathi.png']))
