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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Iterator;


public class generic_methods {

	//------------Yet to find solid solution for Internet Explorer Download prompt (OS Level popup) ----------- //
		
	//------------Please update path to driver (\\Katalon_Studio_Windows_64-7.9.1\\configuration\\resources\\drivers) in TestListener class ------------ //
	
	//------------Please update the hard code value (int rowCount = 1;) in writeExcel function ------------- //
	
	
	//This method will fetch record from text file by row/record index
	@Keyword
	public String read_record_from_text_file_by_index(int index)
	{
		File f=new File(System.getProperty("user.dir") + "/Imp Files/text_file.txt");
		Scanner sc= new Scanner(f);
		
		int i=0;
		while(sc.hasNext())
		{
			if (i == index)
				return sc.nextLine();
				
			else
				sc.nextLine();
			i++;
		}
		return null;
	}	
	
	//This method will return record which matches the given (column) unique id  in a record
	@Keyword
	public String[] read_record_by_unique_key(char symbol, String key)
	{
		File f=new File(System.getProperty("user.dir") + "/Imp Files/text_file.txt");
		Scanner sc= new Scanner(f);
		
		String str;
		String[] res
		while(sc.hasNext())
		{
			
			str = sc.nextLine()
			res = str.split("["+symbol+"]", 0);
			for(String myStr: res) {
			   if(myStr==key)
				 return res;
			}
					
		}
		return null;
	}
	
	//This method will return value for a given Key from JSON file
	@Keyword
	public void getJsonValue(String jsonPath, String key) {
		String result_value = "";	
		FileReader reader = new FileReader(jsonPath)
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(reader);
		JSONObject jsonObj = new JSONObject(obj);
		getKey(jsonObj, key)
	}

	public static void parseObject(JSONObject json, String key) {
		System.out.println(json.get(key));
	}

	public static void getKey(JSONObject json, String key) {

		
		boolean exists = json.has(key);
		
		Iterator<?> keys;
		String nextKeys;
		if (!exists) {
			keys = json.keys();
			while (keys.hasNext()) {
				nextKeys = (String) keys.next();
				try {

					if (json.get(nextKeys) instanceof JSONObject) {

						if (exists == false) {
							getKey(json.getJSONObject(nextKeys), key);
						}

					} else if (json.get(nextKeys) instanceof JSONArray) {
						JSONArray jsonarray = json.getJSONArray(nextKeys);
						for (int i = 0; i < jsonarray.length(); i++) {
							String jsonarrayString = jsonarray.get(i).toString();
							JSONObject innerJSOn = new JSONObject(jsonarrayString);

							if (exists == false) {
								getKey(innerJSOn, key);
							}

						}

					}

				} catch (Exception e) {
				}

			}

		} else {
			 parseObject(json, key);
		}

		
	}
	
	//User will be navigated to the specified URL in browser
	@Keyword
	public void navigate_to_url(String Url)
	{
		WebUI.navigateToUrl(Url)
	}

	//This method takes screenshot of particular element and store it in the 'Screenshots' directory
	@Keyword
	public void takeWebElementScreenshot(TestObject object) {
		WebElement element = WebUiCommonHelper.findWebElement(object, GlobalVariable.wait_for_element)
		WebDriver driver = DriverFactory.getWebDriver();
		Screenshot screenshot = new AShot().takeScreenshot(driver,element)
		ImageIO.write(screenshot.getImage(), "PNG", new File(System.getProperty("user.dir") + "/Screenshots/ElementScreenshot.png"))
	}

	//This method will compare and return true if it finds difference between the actual and expected images
	@Keyword
	public boolean compareImages(TestObject object) {
		WebElement element = WebUiCommonHelper.findWebElement(object, GlobalVariable.wait_for_element)
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

		WebElement sourceElement = WebUiCommonHelper.findWebElement(source,GlobalVariable.wait_for_element);
		WebElement destElement = WebUiCommonHelper.findWebElement(destination, GlobalVariable.wait_for_element);

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
		WebElement element = WebUiCommonHelper.findWebElement(object, GlobalVariable.wait_for_element)
		WebUI.executeJavaScript('arguments[0].click()', Arrays.asList(element))
	}

	//This method will make the specified element visible on screen so as to interact with it.
	@Keyword
	public void scroll_to_element(TestObject object)
	{
		WebUI.scrollToElement(object, GlobalVariable.wait_for_element, FailureHandling.STOP_ON_FAILURE)
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
		WebUI.switchToFrame(object, GlobalVariable.wait_for_element, FailureHandling.STOP_ON_FAILURE)
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
		WebUI.waitForElementVisible(object, GlobalVariable.wait_for_element, FailureHandling.STOP_ONGlobalVariable.wait_for_elementILURE)
	}

	//This method is used to wait for a particular element to be present for the specified maximum wait time
	@Keyword
	public void wait_until_element_present(TestObject object)
	{
		WebUI.waitForElementPresent(object, GlobalVariable.wait_for_element, FailureHandling.STOP_ON_FAILURE)
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
	public void switch_To_Window_Url(String url)
	{
		WebUI.switchToWindowUrl(url, FailureHandling.STOP_ON_FAILURE)
	}

	//This method will verify if the file gets downloaded in the specified location
	@Keyword
	public void verifyFileDownload(String filePath) {
		File folder = new File(filePath)
		List all_files = Arrays.asList(folder.list())
		if(all_files.contains("file_example_CSV_5000.csv")) {
			Assert.assertTrue(true, "File is downloaded")
		}
		else {
			Assert.fail("File download is failed")
		}
	}

	//This method is used to write the specified data into Excel sheet
	@Keyword
	public void writeExcel(String filePath,String fileName,String sheetName,String[] dataToWrite) throws IOException{
		File file =    new File(filePath+"\\"+fileName);
		FileInputStream inputStream = new FileInputStream(file);
		Workbook Workbook = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));
		if(fileExtensionName.equals(".xlsx")){
			Workbook = new XSSFWorkbook(inputStream);
		}
		else if(fileExtensionName.equals(".xls")){
			Workbook = new HSSFWorkbook(inputStream);
		}
		Sheet sheet = Workbook.getSheetAt(0);
		println (sheet.getLastRowNum()); // This returns 1048575 (Max limit) But it should return row count for available number of records
		println (sheet.getFirstRowNum()); // This returns 0
		//Instead of below hardcoded value we should use "int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();"
		int rowCount = 1;
		//Get the first row from the sheet
		Row row = sheet.getRow(1);
		Row newRow = sheet.createRow(rowCount+1);
		for(int j = 0; j < row.getLastCellNum(); j++){
			Cell cell = newRow.createCell(j);
			cell.setCellValue(dataToWrite[j]);
		}
		inputStream.close();
		FileOutputStream outputStream = new FileOutputStream(file);
		Workbook.write(outputStream);
		outputStream.close();
	}

	//This method is used to read data from Excel sheet
	@Keyword
	public void readExcel(String filePath,String fileName,String sheetName) throws IOException{
		DataFormatter objDefaultFormat = new DataFormatter();
		FormulaEvaluator objFormulaEvaluator;
		try {
			File file =    new File(filePath+"\\"+fileName);
			FileInputStream inputStream = new FileInputStream(file);
			Workbook Workbook = null;
			String fileExtensionName = fileName.substring(fileName.indexOf("."));
			if(fileExtensionName.equals(".xlsx")){
				Workbook = new XSSFWorkbook(inputStream);
				objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) Workbook);
			}
			else if(fileExtensionName.equals(".xls")){
				Workbook = new HSSFWorkbook(inputStream);
				objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) Workbook);
			}
			Sheet Sheet = Workbook.getSheetAt(0);
			int rowCount = Sheet.getLastRowNum()-Sheet.getFirstRowNum();
			for (int i = 0; i < rowCount+1; i++) {
				Row row = Sheet.getRow(i);
				int noOfCells = row.getLastCellNum();
				for (int j = 0; j <  noOfCells; j++) {
					Cell cellValue = row.getCell(j);
					objFormulaEvaluator.evaluate(cellValue); // This will evaluate the cell, And any type of cell will return string value
					String cellValueStr = objDefaultFormat.formatCellValue(cellValue,objFormulaEvaluator);
					System.out.print(cellValueStr+"|| ");
				}
				System.out.println();
			}
		}
		catch(NullPointerException e)
		{
		}
		finally {
		}
	}


}