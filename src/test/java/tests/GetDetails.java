package tests;

import java.util.Set;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utilities.APIUtility;
import utilities.Resources;
public class GetDetails {
	public static Logger logger= LogManager.getLogger(GetDetails.class.getName());
	public APIUtility apiUtility;
	public Response response;
	
	public static String name="";
	@BeforeTest
	public void setup() {
		apiUtility = new APIUtility();
		
	}
	
	@Test(testName = "Validate the API Get Response for Twitter")
	public void TC01_getStatusOFTweet() throws InterruptedException {
		
		response = apiUtility.getRequest(Resources.getTweet);
		JsonPath jspath=APIUtility.rawToJson(response);
		logger.info(jspath.getString("text"));
		Assert.assertTrue(apiUtility.validateRequestStatus(response,200),"Data Could not be fetched");
		
	}
	

	@Test(testName = "Validate the API Post Response for creation of Tweet")
	public void TC02_creationOfTweet() {
		
		response = apiUtility.postRequest(Resources.postTweet,"");
		JsonPath jspath=APIUtility.rawToJson(response);
		logger.info(jspath.getString("text"));
		Assert.assertTrue(apiUtility.validateRequestStatus(response,200),"Data Could not be fetched");
	}
	
	@Test(testName = "Validate the API Delete Response for an existing tweet")
	public void TC03_deletionOfTweet() {
		response = apiUtility.postRequest(Resources.postTweet,"");
		JsonPath jspath=APIUtility.rawToJson(response);
		logger.info(jspath.getString("text"));
		Assert.assertTrue(apiUtility.validateRequestStatus(response,200),"Data Could not be fetched");
		String id=jspath.getString("id");
		String resource=Resources.deleteTweet.replace("id", id);
		response=apiUtility.deleteTweetRequest(resource);
		Assert.assertTrue(apiUtility.validateRequestStatus(response,200),"Data not correct");
		
		
	}
}
