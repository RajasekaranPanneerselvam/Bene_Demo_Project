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

import com.kms.katalon.core.webui.common.*;
import org.openqa.selenium.WebElement;

WebUI.navigateToUrl('https://aspireautomationp.benefithub.info/')

CustomKeywords.'genericMethods.generic_methods.loginToApplication'()

WebUI.waitForElementVisible(findTestObject('Login_UserInfoEdit_Logout/Page_BenefitHub/display_name', ['display_name' : 'Aspire QATEST']), 60)

WebUI.scrollToElement(findTestObject('Deal_Edits/Page_BenefitHub/see_all_link_of_specific_container', ['container_name' : 'Discounts & Rewards']), 30)

WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/see_all_link_of_specific_container', ['container_name' : 'Discounts & Rewards']))

WebUI.click(findTestObject('Object Repository/Deal_Edits/Page_BenefitHub/shop_by_category_dropdown'))

//WebUI.click(findTestObject('Deal_Edits/Page_BenefitHub/shop_by_category_submenu_selection', ['selection' : 'Travel']))

WebElement element = WebUiCommonHelper.findWebElement(findTestObject('Deal_Edits/Page_BenefitHub/shop_by_category_submenu_selection', ['selection' : 'Travel']),30)
WebUI.executeJavaScript("arguments[0].click()", Arrays.asList(element))

WebUI.waitForElementNotVisible(findTestObject('Object Repository/Deal_Edits/Page_BenefitHub/Spinner'), 150, FailureHandling.STOP_ON_FAILURE)
