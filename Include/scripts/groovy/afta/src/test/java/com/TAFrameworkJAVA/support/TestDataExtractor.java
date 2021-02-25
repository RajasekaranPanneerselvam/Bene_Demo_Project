package com.TAFrameworkJAVA.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.testng.Assert;

public class TestDataExtractor {

	private String workBookName;
	private String workSheet;
	private String testCaseId;
	private boolean doFilePathMapping;
	private HashMap <String, String> data;

	public TestDataExtractor() {
	}

	public TestDataExtractor(String xlWorkBook, String xlWorkSheet) {
		this.workBookName = xlWorkBook;
		this.workSheet = xlWorkSheet;
	}

	public TestDataExtractor(String xlWorkBook, String xlWorkSheet, String tcID) {
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

	public String get(String key) {

		if (data.isEmpty())
			readData();
		return data.get(key);

	}

	private Hashtable <String, Integer> excelHeaders = new Hashtable <String, Integer>();
	private Hashtable <String, Integer> excelrRowColumnCount = new Hashtable <String, Integer>();

	/**
	 * Fetch Data from Excel
	 * 
	 * @return: Data from Excel as HashMap format
	 */
	public HashMap <String, String> readData() {

		HashMap <String, String> testData = new HashMap <String, String>();
		com.TAFrameworkJAVA.support.ReadFromExcel readTestData = new com.TAFrameworkJAVA.support.ReadFromExcel();
		boolean isDataFound = false;
		testCaseId = testCaseId != null ? testCaseId.trim() : "";
		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell cell = null;

		try {

			sheet = readTestData.initiateExcelConnection(workSheet, workBookName, doFilePathMapping); // to initiate a connection to an excel sheet
			excelrRowColumnCount = readTestData.findRowColumnCount(sheet, excelrRowColumnCount); // find number of rows and columns
			excelHeaders = readTestData.readExcelHeaders(sheet, excelHeaders, excelrRowColumnCount); // to find excel header fields

			for (int r = 0; r < excelrRowColumnCount.get("RowCount"); r++) {

				row = sheet.getRow(r);
				if (row == null)
					continue;

				for (int c = 0; c < excelrRowColumnCount.get("ColumnCount"); c++) {

					if (row.getCell(excelHeaders.get("TestID")) == null)
						break;

					cell = row.getCell(excelHeaders.get("TestID"));

					if (!readTestData.convertHSSFCellToString(cell).toString().equalsIgnoreCase(testCaseId))
						continue;

					isDataFound = true;

					for (String key : excelHeaders.keySet()) {
						testData.put(key, readTestData.convertHSSFCellToString(row.getCell(excelHeaders.get(key))));
					}

					break;

				}

				if (isDataFound)
					break;

			}

			if (!isDataFound)
				Assert.fail("\nTest Data not found in test data sheet for Test Case Id  : " + testCaseId);

		}
		catch (RuntimeException e) {
			Assert.fail("Error During Execution; Execution Failed More details " + e);
			e.printStackTrace();
		}

		data = testData;
		return testData;
	}

	public List <HashMap <String, String>> readAllData() {

		List <HashMap <String, String>> dataList = new ArrayList <HashMap <String, String>>();
		com.TAFrameworkJAVA.support.ReadFromExcel readTestData = new com.TAFrameworkJAVA.support.ReadFromExcel();
		HSSFSheet sheet = null;
		HSSFRow row = null;

		try {

			sheet = readTestData.initiateExcelConnection(workSheet, workBookName, doFilePathMapping); // to initiate a connection to an excel sheet
			excelrRowColumnCount = readTestData.findRowColumnCount(sheet, excelrRowColumnCount); // find number of rows and columns
			excelHeaders = readTestData.readExcelHeaders(sheet, excelHeaders, excelrRowColumnCount); // to find excel header fields

			for (int r = 1; r < excelrRowColumnCount.get("RowCount"); r++) {

				row = sheet.getRow(r);
				if (row == null)
					continue;
				HashMap <String, String> testData = new HashMap <String, String>();
				for (int c = 0; c < excelrRowColumnCount.get("ColumnCount"); c++) {

					for (String key : excelHeaders.keySet()) {
						testData.put(key, readTestData.convertHSSFCellToString(row.getCell(excelHeaders.get(key))));
					}

				}

				dataList.add(testData);
			}
		}
		catch (RuntimeException e) {
			Assert.fail("Error During Execution; Execution Failed More details " + e);
			e.printStackTrace();
		}

		return dataList;
	}

}
