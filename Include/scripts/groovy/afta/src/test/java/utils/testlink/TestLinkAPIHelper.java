package utils.testlink;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;



/**
 * This class contains a collection of helper methods that can be used to compliment the TestLink API available methods.
 * 
 */
public class TestLinkAPIHelper implements TestLinkAPIConst {

	/**
	 * API Helper class
	 */
	private TestLinkAPIHelper() {

	}

	/**
	 * Get the project identifier by test project name.
	 * 
	 * @param apiClient
	 *            : Api Client
	 * @param projectName
	 *            : Project Name
	 * @return: Project ID
	 * @throws TestLinkAPIException
	 *             : API Exception
	 */
	public static Integer getProjectID(TestLinkAPIClient apiClient, String projectName) throws TestLinkAPIException {
		Map <String, Object> data = getProjectInfo(apiClient, projectName);
		return getIdentifier(data);
	}

	/**
	 * Get the project information
	 * 
	 * @param apiClient
	 *            : API
	 * @param projectName
	 *            : Project Name
	 * @return: Map of String and Object
	 * @throws TestLinkAPIException
	 *             : API Exception
	 */
	public static Map <String, Object> getProjectInfo(TestLinkAPIClient apiClient, String projectName) throws TestLinkAPIException {
		TestLinkAPIResults results = apiClient.getProjects();
		for (int i = 0; i < results.size(); i++) {
			Object data = results.getValueByName(i, API_RESULT_NAME);
			if (data != null) {
				if (projectName.equals(data.toString())) { return results.getData(i); }
			}
		}
		return null;
	}

	/**
	 * Get the project info by project ID
	 * 
	 * @param apiClient
	 *            : API
	 * @param projectID
	 *            : Project ID
	 * @return: Map of String and Object
	 * @throws TestLinkAPIException
	 *             : API Exception
	 */
	public static Map <String, Object> getProjectInfo(TestLinkAPIClient apiClient, Integer projectID) throws TestLinkAPIException {
		TestLinkAPIResults results = apiClient.getProjects();
		for (int i = 0; i < results.size(); i++) {
			Object data = results.getValueByName(i, API_RESULT_IDENTIFIER);
			if (data != null) {
				Integer identifier = new Integer(data.toString());
				if (projectID.compareTo(identifier) == 0) { return results.getData(i); }
			}
		}
		return null;
	}

	public static String getKeyValue(TestLinkAPIClient apiClient, String projectName, String Key) throws TestLinkAPIException {
		Map <String, Object> results = getProjectInfo(apiClient, projectName);
		return getKeyValue(results, Key);
	}

	/**
	 * Get the suite identifier by test project name and test suite name
	 * 
	 * @param apiClient
	 *            : API
	 * @param projectName
	 *            : Project Name
	 * @param suiteName
	 *            : Suite Name
	 * @return: Suite ID
	 * @throws TestLinkAPIException
	 *             : API Exception
	 */
	public static Integer getSuiteID(TestLinkAPIClient apiClient, String projectName, String suiteName) throws TestLinkAPIException {
		Integer projectID = getProjectID(apiClient, projectName);
		return getSuiteID(apiClient, projectID, suiteName);
	}

	public static Map <String, Object> getSuiteInfo(TestLinkAPIClient apiClient, String projectName, String suiteName) throws TestLinkAPIException {
		Integer projectID = getProjectID(apiClient, projectName);
		return getSuiteInfo(apiClient, projectID, suiteName);
	}

	/**
	 * Get the suite identifier by test project id and test suite name
	 * 
	 * @param apiClient
	 *            : API
	 * @param projectID
	 *            : Project ID
	 * @param suiteName
	 *            : Suite Name
	 * @return: Suite ID
	 * @throws TestLinkAPIException
	 *             : API Exception
	 */
	public static Integer getSuiteID(TestLinkAPIClient apiClient, Integer projectID, String suiteName) throws TestLinkAPIException {
		Map <String, Object> data = getSuiteInfo(apiClient, projectID, suiteName);
		return getIdentifier(data);
	}

	/**
	 * Get the suite record information by test project id and test suite name
	 * 
	 * @param apiClient
	 *            : API
	 * @param projectID
	 *            : Project ID
	 * @param suiteName
	 *            : Suite Name
	 * @return: Map of String and Object
	 * @throws TestLinkAPIException
	 *             : API Exception
	 */
	public static Map <String, Object> getSuiteInfo(TestLinkAPIClient apiClient, Integer projectID, String suiteName) throws TestLinkAPIException {
		TestLinkAPIResults results = apiClient.getFirstLevelTestSuitesForTestProject(projectID);
		for (int i = 0; i < results.size(); i++) {
			Object data = results.getValueByName(i, API_RESULT_NAME);
			if (data != null) {
				if (suiteName.equals(data.toString())) { return results.getData(i); }
			}
		}
		return null;
	}

	public static String getCaseVisibleID(TestLinkAPIClient apiClient, String projectName, String caseName) throws TestLinkAPIException {
		Map <String, Object> projectInfo = getProjectInfo(apiClient, projectName);
		Integer projectID = getIdentifier(projectInfo);
		Integer caseID = getCaseIDByName(apiClient, projectID, caseName);
		Map <String, Object> caseInfo = getTestCaseInfo(apiClient, projectID, caseID);
		Object prefix = projectInfo.get(API_RESULT_PREFIX);
		Object externalID = caseInfo.get(API_RESULT_TC_EXTERNAL_ID);
		return prefix.toString() + '-' + externalID.toString();
	}

	public static Integer getTestCaseID(TestLinkAPIClient apiClient, Integer projectID, Integer suiteID, String testCaseNameOrVisibleID) throws TestLinkAPIException {
		Integer caseID = null;
		try {
			caseID = getCaseIDByName(apiClient, projectID, testCaseNameOrVisibleID, suiteID);
		}
		catch (Exception e) {
			caseID = null;
		}

		if (caseID == null) {
			try {
				caseID = TestLinkAPIHelper.getCaseIDByVisibleID(apiClient, projectID, suiteID, testCaseNameOrVisibleID);
			}
			catch (Exception ee) {
				return null;
			}
		}
		return caseID;
	}

	/**
	 * Get the test case identifier for a case name within a project.
	 * 
	 * @param apiClient
	 *            : API
	 * @param projectID
	 *            : Project ID
	 * @param caseName
	 *            : TC name
	 * @return: Case ID
	 * @throws TestLinkAPIException
	 *             : API Exception
	 */
	public static Integer getCaseIDByName(TestLinkAPIClient apiClient, Integer projectID, String caseName) throws TestLinkAPIException {
		ArrayList <Map <String, Object>> cases = new ArrayList <Map <String, Object>>();
		TestLinkAPIResults results = apiClient.getFirstLevelTestSuitesForTestProject(projectID);
		for (int i = 0; i < results.size(); i++) {
			Object id = results.getValueByName(i, API_RESULT_IDENTIFIER);
			if (id != null) {
				addAllMatchingCases(apiClient, cases, projectID, new Integer(id.toString()), caseName, null, false);
			}
		}
		Map <String, Object> data = getLatestVersionCaseID(cases);
		return getIdentifier(data);
	}

	/**
	 * Get the test case identifier for a case name within a project.
	 * 
	 * @param apiClient
	 *            : API Client
	 * @param projectID
	 *            : Project ID
	 * @param caseName
	 *            : Case Name
	 * @param SuiteID
	 *            : Suite ID
	 * @return: Integer
	 * @throws TestLinkAPIException
	 *             : API Exception
	 */
	public static Integer getCaseIDByName(TestLinkAPIClient apiClient, Integer projectID, String caseName, Integer SuiteID) throws TestLinkAPIException {
		TestLinkAPIResults results = apiClient.getCasesForTestSuite(projectID, SuiteID);
		Map <String, Object> data = null;
		for (int i = 0; i < results.size(); i++) {
			Object data1 = results.getValueByName(i, API_RESULT_NAME);
			if (data1 != null) {
				if (caseName.equals(data1.toString())) {
					data = results.getData(i);
					return getIdentifier(data);
				}
			}
		}
		return getIdentifier(data);
	}

	/**
	 * Get the a test case identifier by test project id, suite id and test case name.
	 * 
	 * @param apiClient
	 *            : API
	 * @param projectID
	 *            : Project ID
	 * @param suiteID
	 *            : Suite ID
	 * @param caseName
	 *            : TC Name
	 * @return: Case ID
	 * @throws TestLinkAPIException
	 *             : API Exception
	 */
	public static Integer getCaseIDByName(TestLinkAPIClient apiClient, Integer projectID, Integer suiteID, String caseName) throws TestLinkAPIException {
		ArrayList <Map <String, Object>> cases = new ArrayList <Map <String, Object>>();
		addAllMatchingCases(apiClient, cases, projectID, suiteID, caseName, null, false);
		Map <String, Object> data = getLatestVersionCaseID(cases);
		return getIdentifier(data);
	}

	public static Integer getCaseIDByVisibleID(TestLinkAPIClient apiClient, Integer projectID, Integer planID, String caseName) throws TestLinkAPIException {
		ArrayList <Map <String, Object>> cases = new ArrayList <Map <String, Object>>();
		Map <String, Object> projectInfo = TestLinkAPIHelper.getProjectInfo(apiClient, projectID);
		if (projectInfo == null) { throw new TestLinkAPIException("The failed to get the project information."); }
		String prefix = (String) projectInfo.get("prefix");
		TestLinkAPIResults results = apiClient.getFirstLevelTestSuitesForTestProject(projectID);
		for (int i = 0; i < results.size(); i++) {
			Object id = results.getValueByName(i, API_RESULT_IDENTIFIER);
			if (id != null) {
				addAllMatchingCases(apiClient, cases, projectID, new Integer(id.toString()), planID, caseName, prefix, true);
			}
		}
		Map <String, Object> data = getLatestVersionCaseID(cases);
		return getInternalIdentifier(data);
	}

	private static void addAllMatchingCases(TestLinkAPIClient apiClient, ArrayList <Map <String, Object>> cases, Integer projectID, Integer suiteID, String casePattern, String prefix, boolean useVisibleID) throws TestLinkAPIException {
		TestLinkAPIResults results = apiClient.getCasesForTestSuite(projectID, suiteID);
		Object id = null;
		for (int i = 0; i < results.size(); i++) {
			Object externalID = results.getValueByName(i, API_RESULT_TC_ALT_EXTERNAL_ID);
			Object name = results.getValueByName(i, API_RESULT_NAME);
			if (externalID != null && name != null) {
				String currentPattern = name.toString();
				if (useVisibleID) {
					currentPattern = prefix.toString() + '-' + externalID.toString();
				}
				if (casePattern.equalsIgnoreCase(currentPattern)) {
					id = results.getValueByName(i, API_RESULT_TC_INTERNAL_ID);
					if (id != null) {
						cases.add(results.getData(i));
					}
				}
			}
		}
	}

	private static void addAllMatchingCases(TestLinkAPIClient apiClient, ArrayList <Map <String, Object>> cases, Integer projectID, Integer suiteID, Integer planID, String casePattern, String prefix, boolean useVisibleID) throws TestLinkAPIException {
		TestLinkAPIResults results = apiClient.getCasesForTestSuite(projectID, suiteID);
		Object id = null;
		for (int i = 0; i < results.size(); i++) {
			Object externalID = results.getValueByName(i, API_RESULT_TC_ALT_EXTERNAL_ID);
			Object name = results.getValueByName(i, API_RESULT_NAME);
			if (externalID != null && name != null) {
				String currentPattern = name.toString();
				if (useVisibleID) {
					currentPattern = prefix + '-' + externalID.toString();
				}
				if (casePattern.equalsIgnoreCase(currentPattern)) {
					id = results.getValueByName(i, API_RESULT_TC_INTERNAL_ID);
					if (id != null) {
						cases.add(results.getData(i));
					}
				}
			}
		}
	}

	/**
	 * Find the matching test case information and add it to the array list passes as a parameter.
	 * 
	 * @param apiClient
	 *            : API
	 * @param projectID
	 *            : Project ID
	 * @param testCaseID
	 *            : TC ID
	 * @return: Map of String and Object
	 * @throws TestLinkAPIException
	 *             : API Exception
	 */
	public static Map <String, Object> getTestCaseInfo(TestLinkAPIClient apiClient, Integer projectID, Integer testCaseID) throws TestLinkAPIException {
		TestLinkAPIResults suites = apiClient.getFirstLevelTestSuitesForTestProject(projectID);
		for (int i = 0; i < suites.size(); i++) {
			Object id = suites.getValueByName(i, API_RESULT_IDENTIFIER);
			if (id != null) {
				Integer suiteID = new Integer(id.toString());
				TestLinkAPIResults cases = apiClient.getCasesForTestSuite(projectID, suiteID);
				for (int c = 0; c < cases.size(); c++) {
					id = cases.getValueByName(c, API_RESULT_IDENTIFIER);
					Integer currentTestCase = new Integer(id.toString());
					if (currentTestCase.compareTo(testCaseID) == 0) { return cases.getData(c); }
				}
			}
		}
		return null;
	}

	/**
	 * Get the latest version for the test cases passed in the array. The method assumes these are the results of all cases within a suite or project that matched a specific case name.
	 * 
	 * @param cases
	 * @return: Map of String and Object
	 */
	private static Map <String, Object> getLatestVersionCaseID(ArrayList <Map <String, Object>> cases) {
		int maxVersion = 0;

		// find the max version
		for (int i = 0; i < cases.size(); i++) {
			Map <String, Object> data = cases.get(i);
			Object version = data.get("tcversion_id");
			if (version != null && StringUtils.isNotEmpty(String.valueOf(version))) {
				int cv = new Integer(version.toString()).intValue();
				if (cv > maxVersion) {
					maxVersion = cv;
				}
			}
		}

		// return the max version
		for (int i = 0; i < cases.size(); i++) {
			Map <String, Object> data = cases.get(i);
			Object version = data.get("tcversion_id");
			if (version != null && StringUtils.isNotEmpty(String.valueOf(version))) {
				int cv = new Integer(version.toString()).intValue();
				if (cv == maxVersion) { return data; }
			}
		}
		return null;
	}

	/**
	 * Get the a test plan identifier by test project identifier and test plan name.
	 * 
	 * @param apiClient
	 *            : API Client
	 * @param projectID
	 *            : Project ID
	 * @param planName
	 *            : Plan Name
	 * @return: Integer
	 * @throws TestLinkAPIException
	 *             : API Exception
	 */
	public static Integer getPlanID(TestLinkAPIClient apiClient, Integer projectID, String planName) throws TestLinkAPIException {
		Object id = null;
		Integer planID = null;
		Map <String, Object> planInfo = getPlanInfo(apiClient, projectID, planName);
		if (planInfo != null) {
			id = planInfo.get(API_RESULT_IDENTIFIER);
			if (id != null) {
				planID = new Integer(id.toString());
			}
		}
		return planID;
	}

	/**
	 * Get the project information by project identifier and test plan name.
	 * 
	 * @param apiClient
	 *            : API Client
	 * @param projectID
	 *            : Project ID
	 * @param planName
	 *            : Plan Name
	 * @return: Map String, Object
	 * @throws TestLinkAPIException
	 *             : API Exception
	 */
	public static Map <String, Object> getPlanInfo(TestLinkAPIClient apiClient, Integer projectID, String planName) throws TestLinkAPIException {
		TestLinkAPIResults results = apiClient.getProjectTestPlans(projectID);

		for (int i = 0; i < results.size(); i++) {
			Object data = results.getValueByName(i, API_RESULT_NAME);
			if (data != null) {
				if (planName.equalsIgnoreCase(data.toString())) { return results.getData(i); }
			}
		}
		return null;
	}

	/**
	 * Get the build identifier by test plan id.
	 * 
	 * @param apiClient
	 *            : API Client
	 * @param planID
	 *            : Plan ID
	 * @param buildName
	 *            : Build Name
	 * @return: Integer
	 * @throws TestLinkAPIException
	 *             : API Exception
	 */
	public static Integer getBuildID(TestLinkAPIClient apiClient, Integer planID, String buildName) throws TestLinkAPIException {
		Map <String, Object> data = getBuildInfo(apiClient, planID, buildName);
		return getIdentifier(data);
	}

	/**
	 * Get the build information by plan id and build name
	 * 
	 * @param apiClient
	 *            : API Client
	 * @param planID
	 *            : Plan ID
	 * @param buildName
	 *            : Build Name
	 * @return: Map String, Object
	 * @throws TestLinkAPIException
	 *             : API Exception
	 */
	public static Map <String, Object> getBuildInfo(TestLinkAPIClient apiClient, Integer planID, String buildName) throws TestLinkAPIException {
		TestLinkAPIResults results = apiClient.getBuildsForTestPlan(planID);
		for (int i = 0; i < results.size(); i++) {
			Object data = results.getValueByName(i, API_RESULT_NAME);
			if (data != null) {
				if (StringUtils.equalsIgnoreCase(buildName, String.valueOf(data))) { return results.getData(i); }
			}
		}
		return null;
	}

	/*
	 * Private methods
	 */
	private static Integer getIdentifier(Map <String, Object> data) {
		Integer identifier = null;
		Object id = null;
		if (data == null) { return identifier; }
		id = data.get(API_RESULT_IDENTIFIER);
		if (id != null) {
			identifier = new Integer(id.toString());
		}
		return identifier;
	}

	private static Integer getInternalIdentifier(Map <String, Object> data) {
		Integer identifier = null;
		Object id = null;
		if (data == null) { return identifier; }
		id = data.get(API_RESULT_TC_INTERNAL_ID);
		if (id != null) {
			identifier = new Integer(id.toString());
		}
		return identifier;
	}

	private static String getKeyValue(Map <String, Object> data, String key) {
		String Value = null;
		Object id = null;
		if (data == null) { return Value; }
		id = data.get(key);
		if (id != null) {
			Value = id.toString();
		}
		return Value;
	}
}
