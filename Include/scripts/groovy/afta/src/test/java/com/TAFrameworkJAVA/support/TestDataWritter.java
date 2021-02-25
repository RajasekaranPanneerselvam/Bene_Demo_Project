package com.TAFrameworkJAVA.support;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class TestDataWritter { 

	private String workBookName;
	private String workSheet;
	private String testCaseId;
	private boolean doFilePathMapping;
	private Hashtable<String, Integer> excelHeaders = new Hashtable<String, Integer>();
	private Hashtable<String, Integer> excelrRowColumnCount = new Hashtable<String, Integer>();

	public TestDataWritter() {
	}

	public TestDataWritter(String xlWorkBook, String xlWorkSheet) {
		this.workBookName = xlWorkBook;
		this.workSheet = xlWorkSheet;
	}

	public TestDataWritter(String xlWorkBook, String xlWorkSheet, String tcID) {
		this.workBookName = xlWorkBook;
		this.workSheet = xlWorkSheet;
		this.testCaseId = tcID;
	}

	public String getWorkBookName() {
		return workBookName;
	}

	public void setWorkBookName(String workBookName) {
		this.workBookName = workBookName;
	}

	public void setFilePathMapping(boolean doFilePathMapping) {
		this.doFilePathMapping = doFilePathMapping;
	}

	public String getWorkSheet() {
		return workSheet;
	}

	public void setWorkSheet(String workSheet) {
		this.workSheet = workSheet;
	}

	public String getTestCaseId() {
		return testCaseId;
	}

	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
	}


	public static boolean initBRDWrite(String workBookName, String workSheetName,String key, String valueToOverRide){
		/** Loading the test data from excel using the test case id */
		TestDataWritter testData = new TestDataWritter();
		testData.setWorkBookName(workBookName);
		testData.setWorkSheet(workSheetName);
		testData.setFilePathMapping(true);
		
		return testData.writeBrdDataToExcel(key, valueToOverRide);
	}
	
	/**
     * Function To Write Data To Excel
     * @param workBookName
     * @param workSheetName
     * @param sectionName
     * @param rowPossition
     * @param colomnPossition
     * @param valueToOverRide
     * @return Boolean value
     */
	public Boolean writeBrdDataToExcel(String key, String valueToOverRide){
		ReadFromExcel readTestData = new ReadFromExcel();
		
		try {
			String filePath = "";
			if (doFilePathMapping)
				filePath = ".\\src\\main\\resources\\" + workBookName;
			else
				filePath = workBookName;
			
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filePath));
			HSSFWorkbook workbook = new HSSFWorkbook(fs);
	        HSSFSheet sheet = workbook.getSheet(workSheet);
	        HSSFRow row = null;
	        int rowNumber = -1;
			HSSFCell cell = null;
	        excelrRowColumnCount = readTestData.findRowColumnCount(sheet, excelrRowColumnCount);
	        excelHeaders = readTestData.readExcelHeaders(sheet, excelHeaders, excelrRowColumnCount); 
	        
	        for (int r = 0; r < excelrRowColumnCount.get("RowCount"); r++) {
	        	row = sheet.getRow(r);  
		        if(readTestData.convertHSSFCellToString(row.getCell(excelHeaders.get("BRD_Number"))).equals(key)){
		        	rowNumber = row.getRowNum();
		        	break;
		        }
	        } 
	        if(rowNumber != (-1)){
	        	cell = sheet.getRow(rowNumber).getCell(excelHeaders.get("BRD_Status"));
                cell.setCellValue(valueToOverRide);

	        }
	        FileOutputStream outputStream = new FileOutputStream(filePath);
	            workbook.write(outputStream);
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}


	public static boolean initWrite(String workBookName, String workSheetName,String keyString, String key,String valueString,  String valueToOverRide){
		/** Loading the test data from excel using the test case id */
		TestDataWritter testData = new TestDataWritter();
		testData.setWorkBookName(workBookName);
		testData.setWorkSheet(workSheetName);
		testData.setFilePathMapping(true);
		
		return testData.writeDataToExcel(keyString, key, valueString, valueToOverRide);
	}
	
	public Boolean writeDataToExcel(String keyString, String key,String valueString,  String valueToOverRide){
		ReadFromExcel readTestData = new ReadFromExcel();
		
		try {
			String filePath = "";
			if (doFilePathMapping)
				filePath = ".\\src\\main\\resources\\" + workBookName;
			else
				filePath = workBookName;
			
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filePath));
			HSSFWorkbook workbook = new HSSFWorkbook(fs);
	        HSSFSheet sheet = workbook.getSheet(workSheet);
	        HSSFRow row = null;
	        int rowNumber = -1;
			HSSFCell cell = null;
	        excelrRowColumnCount = readTestData.findRowColumnCount(sheet, excelrRowColumnCount);
	        excelHeaders = readTestData.readExcelHeaders(sheet, excelHeaders, excelrRowColumnCount); 
	        
	        for (int r = 0; r < excelrRowColumnCount.get("RowCount"); r++) {
	        	row = sheet.getRow(r);  
		        if(readTestData.convertHSSFCellToString(row.getCell(excelHeaders.get(keyString))).equals(key)){
		        	rowNumber = row.getRowNum();
		        	break;
		        }
	        } 
	        if(rowNumber != (-1)){
	        	cell = sheet.getRow(rowNumber).getCell(excelHeaders.get(valueString));
                cell.setCellValue(valueToOverRide);

	        }
	        FileOutputStream outputStream = new FileOutputStream(filePath);
	            workbook.write(outputStream);
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}

