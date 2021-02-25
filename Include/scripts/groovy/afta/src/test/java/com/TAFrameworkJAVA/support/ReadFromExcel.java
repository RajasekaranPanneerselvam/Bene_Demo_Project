package com.TAFrameworkJAVA.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

public class ReadFromExcel {

	/**
	 * initiateExcelConnection function to establish an initial connection with a work sheet
	 * 
	 * @param workSheet
	 *            (String)
	 * @param doFilePathMapping
	 *            (boolean)
	 * @param workBookName
	 *            (String)
	 * @return HSSFSheet (Work sheet)
	 * 
	 */
	public HSSFSheet initiateExcelConnection(String workSheet, String workBookName, boolean doFilePathMapping) {

		HSSFSheet sheet = null;

		try {

			String filePath = "";
			if (doFilePathMapping)
				filePath = ".\\src\\main\\resources\\" + workBookName;
			else
				filePath = workBookName;

			filePath = filePath.replace("\\", File.separator);
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filePath));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			sheet = wb.getSheet(workSheet);

		}

		catch (RuntimeException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		return sheet;
	}

	/**
	 * findRowColumnCount function to establish an initial connection with a work sheet
	 * 
	 * @param sheet
	 *            : Sheet Name
	 * @param rowColumnCount
	 *            (Hashtable)
	 * @return Hashtable (returns row count and column count)
	 * 
	 */
	public Hashtable <String, Integer> findRowColumnCount(HSSFSheet sheet, Hashtable <String, Integer> rowColumnCount) {

		HSSFRow row = null;
		String temp = null;

		int rows = sheet.getPhysicalNumberOfRows();
		int cols = 0;
		int tmp = 0;
		int counter = 0;

		for (int i = 0; i < 10 || i < rows; i++) {

			row = sheet.getRow(i);

			if (row == null)
				continue;

			temp = convertHSSFCellToString(row.getCell(0));

			if (!temp.equals(""))
				counter++;

			tmp = sheet.getRow(i).getPhysicalNumberOfCells();
			if (tmp > cols)
				cols = tmp;

		}

		rowColumnCount.put("RowCount", counter);
		rowColumnCount.put("ColumnCount", cols);
		return rowColumnCount;

	}

	/**
	 * readExcelHeaders function to establish an initial connection with a work sheet
	 * 
	 * @param sheet
	 *            : Sheet Name
	 * @param excelHeaders
	 *            : Excel Headers List
	 * @param rowColumnCount
	 *            : Row and Column Count
	 * @return: Hashtable (Having Header column values)
	 */
	public Hashtable <String, Integer> readExcelHeaders(HSSFSheet sheet, Hashtable <String, Integer> excelHeaders, Hashtable <String, Integer> rowColumnCount) {

		HSSFRow row = null;
		HSSFCell cell = null;

		for (int r = 0; r < rowColumnCount.get("RowCount"); r++) {

			row = sheet.getRow(r);

			if (row == null)
				continue;

			for (int c = 0; c < rowColumnCount.get("ColumnCount"); c++) {

				cell = row.getCell(c);
				if (cell != null)
					excelHeaders.put(cell.toString(), c);
			}

			break;
		}

		return excelHeaders;
	}

	/**
	 * function will convert the HSSFCell type value to its equivalent string value
	 * 
	 * @param cell
	 *            : HSSFCell value
	 * @return String
	 */
	public String convertHSSFCellToString(HSSFCell cell) {

		String cellValue = "";

		if (cell != null)
			cellValue = cell.toString().trim();

		return cellValue;

	}

	public String evaluateAndReturnCellValue(HSSFSheet sheet, String cellRange) {

		String val = "";
		FormulaEvaluator evaluator = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();
		CellReference ref = new CellReference(cellRange);
		HSSFRow row = sheet.getRow(ref.getRow());

		if (row == null)
			return val;

		HSSFCell cell = row.getCell((int) ref.getCol());
		CellValue cellValue = evaluator.evaluate(cell);
		return cellValue.getStringValue();

	}

	public void setCellValue(HSSFSheet sheet, String cellRange, String value) {

		CellReference ref = new CellReference(cellRange);
		HSSFRow row = sheet.getRow(ref.getRow());
		HSSFCell cell = row.getCell((int) ref.getCol());
		cell.setCellValue(value);

	}

	public static List <List <HSSFCell>> getData_Template_Report(HSSFSheet sheet, Integer startColumn) {
		List <List <HSSFCell>> sheetData = new ArrayList <List <HSSFCell>>();
		FileInputStream fis = null;
		try {

			// Iterator<Row> rows = sheet.rowIterator();
			int iow = sheet.getPhysicalNumberOfRows();
			for (int i = 0; i < iow; i++) {

				Row r = sheet.getRow(i);
				if (r != null) {
					int lastColumn = r.getLastCellNum();
					System.out.println(lastColumn);
					List <HSSFCell> data = new ArrayList <HSSFCell>();

					for (int cn = startColumn; cn < lastColumn; cn++) {
						Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
						if (c == null) {
							HSSFCell celln = (HSSFCell) r.getCell(cn);
							// celln = (HSSFCell) castCellType(celln);
							data.add(celln);

						}
						else {

							HSSFCell celln = (HSSFCell) r.getCell(cn); // This getcell function is returning 1564.23% to celln = 15.6423
							// celln = (HSSFCell) castCellType(celln);
							data.add(celln);
						}
					}
					if (data.isEmpty()) {
						System.out.println("list is empty so not adding");
					}
					else {
						sheetData.add(data);
					}

				}
			}
		}
		catch (Exception e) {
			// MyPropsLogger.getLogger().error(
			// "Exception in getData_Template_Report: " + e.getMessage(), e);
		}
		catch (OutOfMemoryError ex) {
			// MyPropsLogger.getLogger().error(
			// "Exception in getData_Template_Report: " + ex.getMessage(), ex);
		}
		finally {
			if (fis != null) {
				try {
					fis.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sheetData;
	}

}
