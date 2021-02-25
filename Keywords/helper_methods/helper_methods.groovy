package helper_methods

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import genericMethods.generic_methods
import internal.GlobalVariable

public class helper_methods {

	//This method will be used for login into BenefitHub application
	@Keyword
	public void loginToApplication() {
		(new generic_methods()).wait_until_element_visible(findTestObject('Object Repository/file_upload/Page_BenefitHub/button_Guest'))
		(new generic_methods()).click_element(findTestObject('Login_UserInfoEdit_Logout/Page_BenefitHub/button_Guest'))
		(new generic_methods()).click_element(findTestObject('Login_UserInfoEdit_Logout/Page_BenefitHub/a_Login'))
		(new generic_methods()).setTextbox(findTestObject('Object Repository/file_upload/Page_BenefitHub/input_Login_Username'), GlobalVariable.admin_username)
		(new generic_methods()).setPassword(findTestObject('Object Repository/file_upload/Page_BenefitHub/input_Required_Password'), GlobalVariable.admin_password)
		(new generic_methods()).click_element(findTestObject('file_upload/Page_BenefitHub/button_Submit'))
	}
}
