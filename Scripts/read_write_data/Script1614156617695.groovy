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

//Create an array with the data in the same order in which you expect to be filled in excel file
String[] valueToWrite = [id, name,pass];
		
//Call read file method of the class to read data
CustomKeywords.'genericMethods.generic_methods.readExcel'(System.getProperty("user.dir")+"\\Imp Files","ExportExcel.xlsx","Sheet1");

//Write the file using file name, sheet name and the data to be filled
CustomKeywords.'genericMethods.generic_methods.writeExcel'(System.getProperty("user.dir")+"\\Imp Files","ExportExcel.xlsx","Sheet1",valueToWrite);


CustomKeywords.'genericMethods.generic_methods.readExcel'(System.getProperty("user.dir")+"\\Imp Files","ExportExcel.xlsx","Sheet1");
