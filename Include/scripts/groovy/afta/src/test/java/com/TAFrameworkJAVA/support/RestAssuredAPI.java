package com.TAFrameworkJAVA.support;

import java.io.File;
import java.util.Map;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.ConnectionConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.response.Response;

/**
 * @author mani.sundaram
 *
 */
public class RestAssuredAPI {


	/**setBaseURL method sets baseURL
	 * @param baseURL
	 *              as url string
	 */
	public static void setBaseURL(String baseURL)
	{
		try
		{
			if(!baseURL.isEmpty()||!baseURL.contains(null))
			{
				RestAssured.baseURI = baseURL;
			}}catch (NullPointerException e) {
				Log.message("Base URL is not set : - "+e);
			}	
	}

	/**
	 * Returns response of GET API method execution
	 * 
	 * @param baseURL
	 *              as base url
	 * @param headers
	 *              as Map
	 * @param username
	 *              as authentication username for basic/digest based/ can be left blank
	 * @param password
	 *              as authentication password for basic/digest based/ can be left blank
	 * @param contentType
	 *              as content can be of type text,json,xml
	 * @param url
	 *              as service url
	 * @return Response of GET command
	 */
	public static Response GET(String baseURL,Map<String, String> headers,String username,String password,String contentType, String url) 
	{
		setBaseURL(baseURL);
		Response resp = RestAssured.given()
				.auth().basic(username, password)
				.headers(headers)
				.contentType(contentType).get(url);

		Log.message("Send GET Request");		
		Log.message("URL \n" + url);
		Log.message("Status Code \n" + resp.getStatusCode());		
		Log.message("Time taken to get response is \n" + resp.getTime()+" milli second");
		return resp;
	}

	/**
	 * Returns response of POST API method execution
	 * 
	 * @param baseURL
	 *              as base url
	 * @param headers
	 *              as Map
	 * @param username
	 *              as authentication username for basic/digest based/ can be left blank
	 * @param password
	 *              as authentication password for basic/digest based/ can be left blank
	 * @param contentType
	 *              as content can be of type text,json,xml
	 * @param body
	 *            as content which can be sent via post request
	 * @param url
	 *              as service url
	 * @return Response of POST command
	 */
	public static Response POST(String baseURL,Map<String, String> headers,String username,String password,String contentType, String body, String url) {
		setBaseURL(baseURL);
		Response resp = RestAssured.given()
				.auth().basic(username, password)
				.headers(headers)
				.contentType(contentType)				
				.body(body)
				.post(url)
				.andReturn();
		Log.message("Send POST command");
		Log.message("URL \n" + url);
		Log.message("Status Code \n" + resp.getStatusCode());
		Log.message("Time taken to get response is \n" + resp.getTime()+" milli second");
		return resp;
	}


	/**
	 * The Publisher APIs require File to be uploaded, so the given API is
	 * specific to POST Publisher APIs
	 * @param baseURL
	 *              as base url
	 * @param headers
	 *              as Map
	 * @param username
	 *              as authentication username for basic/digest based/ can be left blank
	 * @param password
	 *              as authentication password for basic/digest based/ can be left blank
	 * @param contentType
	 *              as content can be of type text,json,xml
	 * @param path
	 *            as path of content
	 * @param url
	 *              as service url
	 * @return Response of API execution
	 * @throws Exception 
	 */
	public static Response POSTPublisher(String baseURL,Map<String, String> headers,String username,String password,
			String contentType, String path, String url) throws Exception {
		setBaseURL(baseURL);
		Response resp = null;
		try {
			File file = new File(path);
			resp = RestAssured.given().headers(headers)
					.auth().basic(username, password)
					.headers(headers)
					.multiPart("files", file).contentType(contentType)
					.when().post(url);
			Log.message("Send POST Publish command");
			Log.message("URL \n" + url);
			Log.message("Time taken to get response is \n" + resp.getTime()+" milli second");
		} catch (Exception e) {
			Log.exception(e);
			Log.message("something went wrong while running POSTPublisher");
		}

		return resp;
	}


	/**
	 * Returns response of PUT API method execution
	 * @param baseURL
	 *              as base url
	 * @param headers
	 *              as Map
	 * @param username
	 *              as authentication username for basic/digest based/ can be left blank
	 * @param password
	 *              as authentication password for basic/digest based/ can be left blank
	 * @param contentType
	 *              as content can be of type text,json,xml
	 * @param body
	 *              as content
	 * @param url
	 *              as service url
	 * @return
	 *             as response
	 */
	public static Response PUT(String baseURL,Map<String, String> headers,String username,String password,
			String contentType, String body, String url) {
		setBaseURL(baseURL);
		Response resp = RestAssured.given()
				.headers(headers)
				.auth().basic(username, password)
				.contentType(contentType)
				.body(body)
				.put(url)
				.andReturn();
		Log.message("Send PUT command");
		Log.message("URL \n" + url);
		Log.message("Time taken to get response is \n" + resp.getTime()+" milli second");

		return resp;

	}




	/**
	 * Returns response of DELETE API method execution
	 * @param baseURL
	 *              as base url
	 * @param headers
	 *              as Map
	 * @param username
	 *              as authentication username for basic/digest based/ can be left blank
	 * @param password
	 *              as authentication password for basic/digest based/ can be left blank
	 * @param contentType
	 *              as content can be of type text,json,xml
	 * @param url
	 *              as service
	 * @return
	 *             as response
	 */
	public static Response DELETE(String baseURL,Map<String, String> headers,String username,String password,
			String contentType, String url) {
		setBaseURL(baseURL);
		Response resp = RestAssured.given().headers(headers)
				.delete(url);

		Log.message("running DELETE command");
		Log.message("URL \n" + url);
		Log.message("Header \n" + resp.getHeaders().toString());
		Log.message("Response body \n" + resp.getBody().toString());
		Log.message("Time taken to get response is \n" + resp.getTime()+" milli second");

		return resp;

	}

	/**Returns response of POST API method execution
	 * @param baseURL
	 *             as base url
	 * @param headers
	 *             as Map
	 * @param inputData
	 *             as form parameters
	 * @param url
	 *             as service
	 * @return
	 *             as response
	 */
	public static Response POST(String baseURL,Map<String, String> headers,
			Map<String, String> inputData, String url) {
		setBaseURL(baseURL);
		Response resp = RestAssured.given().headers(headers)
				.formParams(inputData).post(url).andReturn();
		Log.message("running POST command");
		Log.message("URL \n" + url);
		Log.message("Header \n" + resp.getHeaders().toString());
		Log.message("Response body \n" + resp.getBody().toString());
		Log.message("Time taken to get response is \n" + resp.getTime()+" milli second");

		return resp;

	}	

	/**closeConnection method would be closing idle Connection
	 * 
	 */
	public static void closeConnection()
	{
		RestAssured.config = RestAssuredConfig.newConfig().connectionConfig(ConnectionConfig.connectionConfig().closeIdleConnectionsAfterEachResponse());
	}

}
