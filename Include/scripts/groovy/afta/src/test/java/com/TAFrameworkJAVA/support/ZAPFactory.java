/*package com.TAFrameworkJAVA.support;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.xml.XmlTest;
import org.zaproxy.clientapi.core.Alert;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiMain;
import org.zaproxy.clientapi.gen.Spider;

*//**
 * Helper class to interact with ZAP API calls
 * 
 * @author harish.subramani
 * 
 *//*
public class ZAPFactory {

	ITestContext context;
	// private final WebDriver driver;
	private final String doSecurityTest;
	XmlTest xmlTestNG = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest();

	*//**
	 * ZAPFactory Constructor
	 * 
	 * @param driver
	 *            : WebDriver Instance
	 * @param doSecurityTest
	 *            : Do Security or not
	 *//*
	public ZAPFactory(WebDriver driver, String doSecurityTest) {
		// this.driver = driver;
		this.doSecurityTest = doSecurityTest;
	}// ZAPFactory

	*//**
	 * Start ZAP exe from your local installed folder
	 *//*
	public void startZAP() throws Exception {

		if (doSecurityTest.equals("true")) {

			try {

				String[] command = { "CMD", "/C", xmlTestNG.getParameter("ZAP_Location") + "ZAP.exe" };
				ProcessBuilder proc = new ProcessBuilder(command);
				proc.directory(new File(xmlTestNG.getParameter("ZAP_Location")));
				Process p = proc.start();
				p.waitFor();
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				OutputStreamWriter oStream = new OutputStreamWriter(p.getOutputStream());
				oStream.write("process where name='ZAP.exe'");
				oStream.flush();
				oStream.close();
				String line;
				while ((line = input.readLine()) != null) {
					// kludge to tell when ZAP is started and ready
					if (line.contains("INFO") && line.contains("org.parosproxy.paros.control.Control") && line.contains("New Session")) {
						input.close();
						break;
					}
				}

				Log.event("ZAP has started successfully.");
			}// try
			catch (Exception ex) {
				throw new Exception("ZAP was unable to start.");
			}// catch

		}// if

	}// startZAP

	*//**
	 * Stop ZAP session and close ZAP tool
	 * 
	 * @param zapaddr
	 *            : ZAP Address
	 * @param zapport
	 *            : ZAP Port
	 *//*
	public void stopZAP(String zapaddr, int zapport) {

		if (doSecurityTest.equals("true"))
			ClientApiMain.main(new String[] { "stop", "zapaddr=" + zapaddr, "zapport=" + zapport });

	}// stopZAP

	*//**
	 * Start ZAP session and close ZAP tool
	 * 
	 * @param zapaddr
	 *            : ZAP Address
	 * @param zapport
	 *            : ZAP Port
	 *//*
	public void startSession(String zapaddr, int zapport) {

		if (doSecurityTest.equals("true")) {
			ClientApiMain.main(new String[] { "newSession", "zapaddr=" + zapaddr, "zapport=" + zapport });
			Log.event("session started");
			Log.event("Session started successfully.");
		}// if

	}// startSession

	*//**
	 * Save the ZAP execution session in XML format
	 * 
	 * @param api
	 *            : ZAP Client API
	 * @param fileName
	 *            : XML Filename to be saved as
	 * @throws Exception
	 *             : Throws custom exception
	 *//*
	public void saveSessionXML(ClientApi api, String fileName) throws Exception {

		if (doSecurityTest.equals("true")) {

			try {

				System.out.println("XML report output");
				String alerts_report = new String(api.core.xmlreport());

				BufferedWriter out = null;
				try {
					out = new BufferedWriter(new FileWriter("out.html"));
					out.write(alerts_report);
				}
				catch (IOException e) {
					throw new RuntimeException(e);
				}
				finally {
					if (out != null) {
						try {
							out.close();
						}
						catch (IOException e) {
						}
					}
				}

			}// try
			catch (Exception ex) {
				throw new Exception("Error saving session.");
			}// catch

		}// if

	}// saveSessionXML

	*//**
	 * Save ZAP execution session in HTML format
	 * 
	 * @param api
	 *            : ZAP Client API
	 * @param fileName
	 *            : HTML Filename to be saved as
	 *//*
	public void saveSessionHTML(ClientApi api, String fileName) {

		if (doSecurityTest.equals("true")) {

			WebDriver driver = WebDriverFactory.get("zap_windows");
			driver.get("http://zap/OTHER/core/other/htmlreport/");
			Utils.waitForPageLoad(driver);
			String pageSource = driver.getPageSource();
			writeInFile(System.getProperty("user.dir") + "/test-output/Security Scan Report/" + fileName + ".html", pageSource);
			driver.close();

		}// if

		Log.event("Report saved to disk!");

	}// saveSessionHTML

	*//**
	 * Save ZAP execution session
	 * 
	 * @param api
	 *            : ZAP Client API
	 * @param fileName
	 *            : XML Filename to be saved as
	 * @throws Exception
	 *             : Throws custom exception
	 *//*
	public void saveSession(ClientApi api, String fileName) throws Exception {

		if (doSecurityTest.equals("true")) {

			try {
				String path = xmlTestNG.getParameter("SAVE_SESSION_DIRECTORY") + fileName + ".session";
				api.core.saveSession(path, "true");
				Log.event("Session save successful (" + path + ").");
			}// try
			catch (Exception ex) {
				throw new Exception("Error saving session.");
			}// catch

		}// if

	}// saveSession

	*//**
	 * Do Active Scan
	 * 
	 * @param api
	 *            : ZAP Client API
	 * @param ZAP_URI_PORT
	 *            : ZAP URI Port
	 * @param breakBy
	 *            : Break ZAP Active Scan when reaches this execution status
	 * @throws Exception
	 *             : Throws Custom Exception
	 *//*
	public void initiateActiveScan(ClientApi api, String ZAP_URI_PORT, String breakBy) throws Exception {

		if (doSecurityTest.equals("true")) {

			try {
				Log.event("Active scan starting...");
				api.ascan.scan(ZAP_URI_PORT, null, null);
				// To see when scan is done - Currently am not sure how to work with the ApiRepsonse Object
				while (api.ascan.status().toString(0).contains(breakBy) == false) {
					Log.event("Active scan progress: " + api.ascan.status().toString(0));
					try {
						Thread.sleep(15000); // basically printing status every 15 seconds
					}
					catch (InterruptedException e) {
					}
				}
				Log.event("progress: " + api.ascan.status().toString(0));
			}// try
			catch (Exception ex) {
				throw new Exception("Active Scan Failed - see console for details!");
			}// catch

		}// if

	}// initiateActiveScan

	*//**
	 * Do Spider Scan
	 * 
	 * @param api
	 *            : ZAP Client API
	 * @param ZAP_URI_PORT
	 *            : ZAP URI Port
	 * @throws Exception
	 *             : Throws Custom Exception
	 *//*
	public void initiateSpiderScan(ClientApi api, String ZAP_URI_PORT) throws Exception {

		if (doSecurityTest.equals("true")) {

			try {
				Log.event("Spider scan starting...");
				Spider spider = new Spider(api);
				spider.scan(ZAP_URI_PORT);

				// To see when spider has completed - currently am not sure how to use the ApiResponse Object
				while (spider.status().toString(0).contains("100") == false) {
					Log.event("progress: " + spider.status().toString(0));
					try {
						Thread.sleep(5000); // basically printing status every 5 seconds
					}
					catch (InterruptedException e) {
					}
				}
			}// try
			catch (Exception ex) {
				throw new Exception("Spider Failed - see console for details!");
			}// catch

		}// if

	}// initiateSpiderScan

	*//**
	 * Validate whether scan has any error
	 * 
	 * @param api
	 *            : ZAP Client API
	 * @return: List of errors found in scan
	 *//*
	public String checkErrors(ClientApi api) {

		String errors = "";
		if (doSecurityTest.equals("true")) {
			List <Alert> ignoreAlerts = new ArrayList <>(2);
			// ignoreAlerts.add(new Alert("Cookie set without HttpOnly flag", null, Risk.Low, Reliability.Warning, null, null) {});
			// ignoreAlerts.add(new Alert(null, null, Risk.Low, Reliability.Warning, null, null));
			// ignoreAlerts.add(new Alert(null, null, Risk.Informational, Reliability.Warning, null, null));
			try {
				Log.event("Checking Alerts...");
				api.checkAlerts(ignoreAlerts, null);

			}// try
			catch (Exception ex) {
				Log.event(ex.getMessage());
				errors = ex.getMessage();
			}// catch

		}// if
		return errors;

	}// checkErrors

	*//**
	 * Save the HTML ZAP Report
	 * 
	 * @param sFileName
	 *            : File Name
	 * @param sTextToWrite
	 *            : HTML Text
	 *//*
	public static void writeInFile(String sFileName, String sTextToWrite) {
		{
			FileWriter outFile;
			try {
				outFile = new FileWriter(sFileName);
				PrintWriter out = new PrintWriter(outFile);
				out.print(sTextToWrite);
				out.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}// writeInFile

}// ZAPFactory
*/