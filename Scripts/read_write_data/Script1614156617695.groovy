import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import java.util.List;
import java.util.Scanner;
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


CustomKeywords.'genericMethods.generic_methods.readExcel'(System.getProperty("user.dir")+"\\Imp Files","ExportExcel.xlsx","Sheet1");

String key = GlobalVariable.text_file_fetch_key;
String[] result = CustomKeywords.'genericMethods.generic_methods.read_record_by_unique_key'((char)GlobalVariable.text_file_delimiter, key);
println(result)

CustomKeywords.'genericMethods.generic_methods.getJsonValue'(System.getProperty("user.dir") + "\\Imp Files\\employee.json", "address");
CustomKeywords.'genericMethods.generic_methods.writeExcel'(System.getProperty("user.dir")+"\\Imp Files","ExportExcel.xlsx","Sheet1",result);
CustomKeywords.'genericMethods.generic_methods.readExcel'(System.getProperty("user.dir")+"\\Imp Files","ExportExcel.xlsx","Sheet1");


String[] valueToWrite = [id,name,pass];
CustomKeywords.'genericMethods.generic_methods.readExcel'(System.getProperty("user.dir")+"\\Imp Files","ExportExcel.xlsx","Sheet1");
CustomKeywords.'genericMethods.generic_methods.writeExcel'(System.getProperty("user.dir")+"\\Imp Files","ExportExcel.xlsx","Sheet1",valueToWrite);
CustomKeywords.'genericMethods.generic_methods.readExcel'(System.getProperty("user.dir")+"\\Imp Files","ExportExcel.xlsx","Sheet1");

String recordHeader = CustomKeywords.'genericMethods.generic_methods.read_record_from_text_file_by_index'(0);
println(recordHeader)

String record2 = CustomKeywords.'genericMethods.generic_methods.read_record_from_text_file_by_index'(2);
println(record2)




