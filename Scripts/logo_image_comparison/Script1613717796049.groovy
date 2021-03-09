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
import org.openqa.selenium.Keys
import org.testng.Assert


CustomKeywords.'genericMethods.generic_methods.test_info'("Navigating to URL")
CustomKeywords.'genericMethods.generic_methods.navigate_to_url'(GlobalVariable.G_url);


CustomKeywords.'genericMethods.generic_methods.test_info'("Logging into application")
CustomKeywords.'helper_methods.helper_methods.loginToApplication'();

CustomKeywords.'genericMethods.generic_methods.test_pass'("Logged in successfully")

CustomKeywords.'genericMethods.generic_methods.wait_until_element_visible'(findTestObject('Login_UserInfoEdit_Logout/Page_BenefitHub/button_Guest'))


CustomKeywords.'genericMethods.generic_methods.test_info'("Validating the Home page LOGO")

CustomKeywords.'genericMethods.generic_methods.wait_until_element_visible'(findTestObject('Images/Homepage_Logo'))

CustomKeywords.'genericMethods.generic_methods.takeWebElementScreenshot'(findTestObject('Images/Homepage_Logo'))

Assert.assertFalse(CustomKeywords.'genericMethods.generic_methods.compareImages'(findTestObject('Images/Homepage_Logo')))

CustomKeywords.'genericMethods.generic_methods.test_pass'("Image Logo is as expected")

