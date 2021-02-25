package utils.testlink;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Logger;

/**
 * Does most the TestLink integration 1. Connection 2. Get TestCases 3. Report result
 */
public class TestLinkProcessor {
	private static final String BUILD_NOTES = "Automation build";

	private static final Logger LOGGER = Logger.getLogger(TestLinkProcessor.class);

	private static TestLinkAPIClient testLinkApiClient = null;

	private static Hashtable <String, ArrayList <String>> testCases;

	/**
	 * Establish a connection with given TestLink serverURL and developerKey
	 * 
	 * @param serverURL
	 * @param developerKey
	 */

	private TestLinkProcessor() {

	}

	public static void connectTo(String serverURL, String developerKey) {
		LOGGER.info("----------------------------------------------------------------------------------------");
		if (testLinkApiClient == null) {
			LOGGER.info("Establishing connection with TestLink...");
			LOGGER.info("Server URL : " + serverURL);
			LOGGER.info("Dev Key used : " + developerKey);
			testLinkApiClient = new TestLinkAPIClient(developerKey, serverURL, true);
		}

		if (testLinkApiClient != null && testLinkApiClient.isConnected()) {
			LOGGER.info("Connection with TestLink established !!");
		}
		else {
			LOGGER.error("Error in creating connection with TestLink: " + testLinkApiClient.getConnectErrorMsg());
		}
		LOGGER.info("----------------------------------------------------------------------------------------");
	}

	public static boolean isConnected() {
		return testLinkApiClient != null && testLinkApiClient.isConnected();
	}

	/**
	 * Returns the testcases per given data
	 * 
	 * @param tProjectName
	 *            : TestLink Project Name
	 * @param tPlanName
	 *            : Project Plan
	 * @param executionType
	 *            : Execution Type
	 * @param executionTypeNum
	 *            : Execution Type Number
	 * @param externalId
	 *            : External ID
	 * @return: HashTable
	 */
	public static Hashtable <String, ArrayList <String>> getTestCases(String tProjectName, String tPlanName, String executionType, String executionTypeNum, String externalId) {
		int tProjectId;
		int tPlanId;

		if (testCases == null && isConnected()) {
			testCases = new Hashtable <String, ArrayList <String>>();
			try {
				tProjectId = TestLinkAPIHelper.getProjectID(testLinkApiClient, tProjectName);
				tPlanId = TestLinkAPIHelper.getPlanID(testLinkApiClient, tProjectId, tPlanName);
				testCases = TestLinkHelper.getAutomationTestCases(testLinkApiClient, tProjectName, tPlanId, executionType, executionTypeNum, externalId);

				LOGGER.info("Automation enabled testcases are fetched from TestLink successfully !!");
			}
			catch (TestLinkAPIException exception) {
				LOGGER.error("Error in fetching testcases from TestLink: ", exception);
			}
		}

		return testCases;
	}

	/**
	 * Report the result of testcase to TestLink
	 * 
	 * @param projectName
	 *            : Project Name
	 * @param testPlanName
	 *            : Project Plan
	 * @param testCaseNameOrVisibleID
	 *            : TestCase ID
	 * @param buildName
	 *            : Build Name
	 * @param execNotes
	 *            : Execution Notes
	 * @param testResultStatus
	 *            : Test Result Status
	 */
	public static void reportTestResult(String projectName, String testPlanName, String testCaseNameOrVisibleID, String buildName, String execNotes, String testResultStatus) {
		try {
			Integer buildID = testLinkApiClient.createBuildIfNotExists(projectName, testPlanName, buildName, BUILD_NOTES);
			testLinkApiClient.reportTestCaseResult(projectName, testPlanName, testCaseNameOrVisibleID, buildID, execNotes, testResultStatus);
		}
		catch (TestLinkAPIException exception) {
			LOGGER.error("Error in reporting testcase result to TestLink: ", exception);
		}
	}
}
