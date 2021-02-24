package genericMethods

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import javax.imageio.ImageIO

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import java.awt.image.BufferedImage;


import ru.yandex.qatools.ashot.comparison.ImageDiffer
import ru.yandex.qatools.ashot.comparison.ImageDiff
import groovy.ui.SystemOutputInterceptor

import com.kms.katalon.core.webui.common.*;
import com.kms.katalon.core.webui.driver.DriverFactory
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.FormulaEvaluator
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.openqa.selenium.*
import org.testng.Assert

import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.Screenshot

import internal.GlobalVariable

public class generic_methods {

	
	@Keyword
	public void verifyFileDownload(String filePath)
	{		
		File folder = new File(filePath)
		List all_files = Arrays.asList(folder.list())
		if(all_files.contains("file_example_CSV_5000.csv")) {
			Assert.assertTrue(true, "File is downloaded")
		}
		else {
			Assert.fail("File download is failed")
		}	
	}
	
	@Keyword
	public void writeExcel(String filePath,String fileName,String sheetName,String[] dataToWrite) throws IOException{

		//Create an object of File class to open xlsx file

		File file =    new File(filePath+"\\"+fileName);

		//Create an object of FileInputStream class to read excel file

		FileInputStream inputStream = new FileInputStream(file);

		Workbook Workbook = null;

		//Find the file extension by splitting  file name in substring and getting only extension name

		String fileExtensionName = fileName.substring(fileName.indexOf("."));

		//Check condition if the file is xlsx file

		if(fileExtensionName.equals(".xlsx")){

			//If it is xlsx file then create object of XSSFWorkbook class

			Workbook = new XSSFWorkbook(inputStream);

		}

		//Check condition if the file is xls file

		else if(fileExtensionName.equals(".xls")){

			//If it is xls file then create object of XSSFWorkbook class

			Workbook = new HSSFWorkbook(inputStream);

		}

		//Read excel sheet by sheet name
		Sheet sheet = Workbook.getSheetAt(0);

		//Get the current count of rows in excel file

		println (sheet.getLastRowNum()); // This returns 1048575 (Max limit) But it should return row count for available number of records
		println (sheet.getFirstRowNum()); // This returns 0
		//Instead of below hardcoded value we should use "int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();"
		int rowCount = 1;
		//Get the first row from the sheet
		Row row = sheet.getRow(1);

		//Create a new row and append it at last of sheet

		Row newRow = sheet.createRow(rowCount+1);

		//Create a loop over the cell of newly created Row

		for(int j = 0; j < row.getLastCellNum(); j++){

			//Fill data in row

			Cell cell = newRow.createCell(j);

			cell.setCellValue(dataToWrite[j]);

		}

		//Close input stream

		inputStream.close();

		//Create an object of FileOutputStream class to create write data in excel file

		FileOutputStream outputStream = new FileOutputStream(file);

		//write data in the excel file

		Workbook.write(outputStream);

		//close output stream

		outputStream.close();

	}


	@Keyword
	public void readExcel(String filePath,String fileName,String sheetName) throws IOException{

		DataFormatter objDefaultFormat = new DataFormatter();
		FormulaEvaluator objFormulaEvaluator;

		//Create an object of File class to open xlsx file
		try {
			File file =    new File(filePath+"\\"+fileName);

			//Create an object of FileInputStream class to read excel file

			FileInputStream inputStream = new FileInputStream(file);

			Workbook Workbook = null;

			//Find the file extension by splitting file name in substring  and getting only extension name

			String fileExtensionName = fileName.substring(fileName.indexOf("."));

			//Check condition if the file is xlsx file

			if(fileExtensionName.equals(".xlsx")){

				//If it is xlsx file then create object of XSSFWorkbook class

				Workbook = new XSSFWorkbook(inputStream);

				objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) Workbook);
			}

			//Check condition if the file is xls file

			else if(fileExtensionName.equals(".xls")){

				//If it is xls file then create object of HSSFWorkbook class

				Workbook = new HSSFWorkbook(inputStream);
				objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) Workbook);

			}

			//Read sheet inside the workbook by its name

			Sheet Sheet = Workbook.getSheetAt(0);

			//Find number of rows in excel file

			int rowCount = Sheet.getLastRowNum()-Sheet.getFirstRowNum();

			//Create a loop over all the rows of excel file to read it

			for (int i = 0; i < rowCount+1; i++) { //i < rowCount+1

				Row row = Sheet.getRow(i);

				//Create a loop to print cell values in a row
				int noOfCells = row.getLastCellNum();
				for (int j = 0; j <  noOfCells; j++) {
					Cell cellValue = row.getCell(j);
					objFormulaEvaluator.evaluate(cellValue); // This will evaluate the cell, And any type of cell will return string value
					String cellValueStr = objDefaultFormat.formatCellValue(cellValue,objFormulaEvaluator);
					System.out.print(cellValueStr+"|| ");
				}

				System.out.println();
				//Cannot invoke method getLastCellNum() on null object
			}
		}
		catch(NullPointerException e)
		{

		}
		finally {

		}

	}

	//User will be navigated to the specified URL in browser
	@Keyword
	public void navigate_to_url(String url)
	{
		WebUI.navigateToUrl(GlobalVariable.G_url)
	}

	//This method takes screenshot of particular element and store it in the 'Screenshots' directory
	@Keyword
	public void takeWebElementScreenshot(TestObject object) {
		WebElement element = WebUiCommonHelper.findWebElement(object, 30)
		WebDriver driver = DriverFactory.getWebDriver();
		Screenshot screenshot = new AShot().takeScreenshot(driver,element)
		ImageIO.write(screenshot.getImage(), "PNG", new File(System.getProperty("user.dir") + "/Screenshots/ElementScreenshot.png"))
	}

	//This method will compare and return true if it finds difference between the actual and expected images
	@Keyword
	public boolean compareImages(TestObject object) {
		WebElement element = WebUiCommonHelper.findWebElement(object, 30)
		WebDriver driver = DriverFactory.getWebDriver();

		BufferedImage expectedImage = ImageIO.read(new File(System.getProperty("user.dir") + "\\Screenshots\\ElementScreenshot.png"));
		Screenshot actuallogoImage = new AShot().takeScreenshot(driver,element)
		BufferedImage actual_image = actuallogoImage.getImage();

		ImageDiffer imgDiffer = new ImageDiffer();
		ImageDiff diff  = imgDiffer.makeDiff(expectedImage, actual_image)

		return diff.hasDiff()
	}

	//This method will set the given text in the textbox
	@Keyword
	public void setTextbox(TestObject object, String value)
	{
		WebUI.setText(object, value, FailureHandling.STOP_ON_FAILURE)
	}

	//This method will set the given encrypted password in the textbox
	@Keyword
	public void setPassword(TestObject object, String value)
	{
		WebUI.setEncryptedText(object, value, FailureHandling.STOP_ON_FAILURE)
	}


	//This method will drag source element and drops at target element
	@Keyword
	public void dragAndDropToElement(WebDriver driver, TestObject source, TestObject destination)
	{

		WebElement sourceElement = WebUiCommonHelper.findWebElement(source,30);
		WebElement destElement = WebUiCommonHelper.findWebElement(destination, 30);

		((driver) as JavascriptExecutor).executeScript((((((((('var src=arguments[0],tgt=arguments[1];var dataTransfer={dropEffe' + 'ct:\'\',effectAllowed:\'all\',files:[],items:{},types:[],setData:fun') +
				'ction(format,data){this.items[format]=data;this.types.append(for') + 'mat);},getData:function(format){return this.items[format];},clea') +
				'rData:function(format){}};var emit=function(event,target){var ev') + 't=document.createEvent(\'Event\');evt.initEvent(event,true,false);') +
				'evt.dataTransfer=dataTransfer;target.dispatchEvent(evt);};emit(\'') + 'dragstart\',src);emit(\'dragenter\',tgt);emit(\'dragover\',tgt);emit(') +
				'\'drop\',tgt);emit(\'dragend\',src);'), sourceElement, destElement)

	}

	//This method helps to click on hidden element
	@Keyword
	public void click_hidden_element(TestObject object)
	{
		WebElement element = WebUiCommonHelper.findWebElement(object, 50)
		WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(element))
	}

	//This method will make the specified element visible on screen so as to interact with it.
	@Keyword
	public void scroll_to_element(TestObject object)
	{
		WebUI.scrollToElement(object, 50, FailureHandling.STOP_ON_FAILURE)
	}

	//This method is used to click on the specified element
	@Keyword
	public void click_element(TestObject object)
	{
		WebUI.click(object, FailureHandling.STOP_ON_FAILURE)
	}

	//This method is used to switch to a particular frame so that to interact with elements which are present inside that frame
	@Keyword
	public void switch_To_Frame(TestObject object)
	{
		WebUI.switchToFrame(object, 50, FailureHandling.STOP_ON_FAILURE)
	}

	//This method will be used for login into BenefitHub application
	@Keyword
	public void loginToApplication() {
		wait_until_element_visible(findTestObject('Object Repository/file_upload/Page_BenefitHub/button_Guest'));
		click_element(findTestObject('Login_UserInfoEdit_Logout/Page_BenefitHub/button_Guest'))
		click_element(findTestObject('Login_UserInfoEdit_Logout/Page_BenefitHub/a_Login'))
		setTextbox(findTestObject('Object Repository/file_upload/Page_BenefitHub/input_Login_Username'), GlobalVariable.admin_username)
		setPassword(findTestObject('Object Repository/file_upload/Page_BenefitHub/input_Required_Password'), GlobalVariable.admin_password)
		click_element(findTestObject('file_upload/Page_BenefitHub/button_Submit'))
	}

	//This method is used to upload files from OS level browsing pop up
	@Keyword
	public void upload_file(TestObject object, String location)
	{
		WebUI.uploadFileWithDragAndDrop(object, location)
	}

	//This method is used to wait for a particular element to become visible until the specified maximum wait time
	@Keyword
	public void wait_until_element_visible(TestObject object)
	{
		WebUI.waitForElementVisible(object, 50, FailureHandling.STOP_ON_FAILURE)
	}

	//This method is used to wait for a particular element to be present for the specified maximum wait time
	@Keyword
	public void wait_until_element_present(TestObject object)
	{
		WebUI.waitForElementPresent(object, 50, FailureHandling.STOP_ON_FAILURE)
	}

	//This method is used to switch to a window with respect to the index of all the opened windows
	@Keyword
	public void switch_to_window_by_index(int index)
	{
		WebUI.switchToWindowIndex(index, FailureHandling.STOP_ON_FAILURE)
	}

	//This method is used to switch to a window with respect to the given page title
	@Keyword
	public void switch_to_window_by_title(String title)
	{
		WebUI.switchToWindowTitle(title, FailureHandling.STOP_ON_FAILURE)
	}

	//This method is used to switch to a window with respect to the given page URL
	@Keyword
	public void switch_To_Window_Ttl(String url)
	{
		WebUI.switchToWindowUrl(url, FailureHandling.STOP_ON_FAILURE)
	}


}