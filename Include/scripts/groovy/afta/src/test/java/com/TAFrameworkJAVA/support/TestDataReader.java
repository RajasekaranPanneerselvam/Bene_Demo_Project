package com.TAFrameworkJAVA.support;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.opencsv.CSVReader;

public class TestDataReader {

	private String workBookName;
	private String testCaseId;
	@SuppressWarnings("unused")
	private boolean doFilePathMapping;
	private HashMap<String, String> data;

	public TestDataReader() {
	}

	public TestDataReader(String xlWorkBook) {
		this.workBookName = xlWorkBook;
	}

	public TestDataReader(String xlWorkBook, String tcID) {
		this.workBookName = xlWorkBook;
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

	/**
	 * Inits the test data.
	 *
	 * @param workbook the workbook
	 * @return HashMap<String, String>
	 */
	public static HashMap<String, String> initTestData(String workbook){
		/** Loading the test data from csv file using the test case id */
		TestDataReader testData = new TestDataReader();
		testData.setWorkBookName(workbook);
		testData.setFilePathMapping(true);
		
		Throwable t = new Throwable();
		String testCaseId = t.getStackTrace()[1].getMethodName();
		testData.setTestCaseId(testCaseId);
		return testData.readData();
	}
	
	//Returns the first instance of the test data being searched
	public HashMap<String, String> readData(){
		HashMap<String, String> testData = new HashMap<String, String>();
		
		try{
		CSVReader datareader = new CSVReader(new FileReader(".\\src\\main\\resources\\"+ workBookName));
		
		List<String[]> data = datareader.readAll();
		String[] header = data.get(0);
		String[] matchingData = new String[header.length];
		for(String[] d: data){
			if(d[0].equals(testCaseId)){
				matchingData = d;
				break;
			}
		}
		
		for(int i=0; i < header.length; i++){
			testData.put(header[i], matchingData[i]);
		}
		
		datareader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return testData;
	}
	
	/**
	 * Inits the test data.
	 *
	 * @param workbook the workbook
	 * @return List<HashMap<String, String>>
	 */
	public List<HashMap<String, String>> initMultipleTestData(String workbook, String testCaseId){
		/** Loading the test data from csv file using the test case id */
		TestDataReader testData = new TestDataReader();
		testData.setWorkBookName(workbook);
		testData.setFilePathMapping(true);
		
		//String testCaseId = t.getStackTrace()[1].getMethodName();
		testData.setTestCaseId(testCaseId);
		return testData.readMultipleData();
	}

	//Returns all the instances of the test data being searched
	public List<HashMap<String, String>> readMultipleData(){
		List<HashMap<String, String>> dataToBeReturned = new ArrayList<HashMap<String, String>>();
		
		
		try{
		CSVReader datareader = new CSVReader(new FileReader(".\\src\\main\\resources\\"+ workBookName));
		
		List<String[]> data = datareader.readAll();
		String[] header = data.get(0);
		String[] matchingData = new String[header.length];
		int index = 0;
		for(String[] d: data){
			if(d[0].equals(testCaseId)){
				matchingData = d;
				HashMap<String, String> testData = new HashMap<String, String>();
				for(int i=0; i < header.length; i++){
					testData.put(header[i], matchingData[i]);
				}
				dataToBeReturned.add(index, testData);
				index++;
			}
		}
		
		
		datareader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return dataToBeReturned;
	}
	
	
	public static List<String[]> readAllData(String filePath) throws IOException{
		CSVReader datareader = new CSVReader(new FileReader(filePath));
		List<String[]> testData = datareader.readAll();
		datareader.close();
		return testData;
	}
	
}

