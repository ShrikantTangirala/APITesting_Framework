package utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import io.restassured.RestAssured;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import reporting.ReadProperties;



public class APIUtility {
	
	private String BASE_URI;
	private String CONSUMER_KEY;
	private String CONSUMER_TOKEN;
	private String ACCESS_TOKEN;
	private String SECRET_TOKEN;
	private String GET_COUNT;
	private String SET_TWEET;
	String token;
	private RequestSpecification HTTP_REQUEST;
	
	
	public static Logger logger= LogManager.getLogger(APIUtility.class.getName());
	
	private  ReadProperties rp = new ReadProperties();
	public APIUtility() {
		 this.BASE_URI = rp.getApplicationProperty("baseurl"); 	
		 this.CONSUMER_KEY = rp.getApplicationProperty("consumerKey");
		 this.CONSUMER_TOKEN = rp.getApplicationProperty("consumerToken");
		 this.ACCESS_TOKEN = rp.getApplicationProperty("accessToken");
		 this.SECRET_TOKEN = rp.getApplicationProperty("secretToken");
		 this.GET_COUNT = rp.getApplicationProperty("getCount");
		 this.SET_TWEET = rp.getApplicationProperty("setTweet");
		this.HTTP_REQUEST = authentication();
	}
	
	
	private RequestSpecification authentication() 
	{
		RestAssured.baseURI = BASE_URI;
		//String password = CipherUtility.decrypt(ENCRYPTED_PASSWORD);
		Map<String,String> headers = new HashMap<String,String>();
		
		return RestAssured.given().headers(headers);
	}
	
	public Response getRequest(String inputURI) {
		logger.info("Request URI:" + BASE_URI+inputURI);
		Response response = HTTP_REQUEST.given().auth().oauth(CONSUMER_KEY,CONSUMER_TOKEN, ACCESS_TOKEN, SECRET_TOKEN).
				queryParam("count", GET_COUNT).when().get(inputURI).then().extract().response();
		
		
		logger.info(response.asString()+"This is the response");
		return response;
		
	}
	
	public Response postRequest(String inputURI,String jsonBody) {
		Random rnd = new Random();
		int randomNum = 100000 + rnd.nextInt(900000);
		HTTP_REQUEST = HTTP_REQUEST.given().auth().oauth(CONSUMER_KEY,CONSUMER_TOKEN, ACCESS_TOKEN, SECRET_TOKEN).
				queryParam("status", randomNum+SET_TWEET+randomNum);
		HTTP_REQUEST = HTTP_REQUEST.header("Content-Type","application/json");
		logger.info("Body of the request is"+jsonBody);
		HTTP_REQUEST = HTTP_REQUEST.body(jsonBody);
		Response response = HTTP_REQUEST.post(inputURI).then().extract().response();
		logger.info(response.asString()+"This is the response");
		return response;
	}
	
public Response deleteRequest(String inputURI) {
		
	
	
		HTTP_REQUEST = HTTP_REQUEST.given().auth().oauth(CONSUMER_KEY,CONSUMER_TOKEN, ACCESS_TOKEN, SECRET_TOKEN);
		
		Response response = HTTP_REQUEST.delete(inputURI).then().extract().response();
		logger.info(response.asString()+"This is the response");
		return response;
	}
	

public Response deleteTweetRequest(String inputURI) {
	
	HTTP_REQUEST = HTTP_REQUEST.given().auth().oauth(CONSUMER_KEY,CONSUMER_TOKEN, ACCESS_TOKEN, SECRET_TOKEN);
	
	Response response = HTTP_REQUEST.post(inputURI).then().extract().response();
	logger.info(response.asString()+"This is the response");
	return response;
}
	
	
	public Response putRequest(String inputURI,String jsonBody) {

		HTTP_REQUEST = HTTP_REQUEST.given().auth().oauth(CONSUMER_KEY,CONSUMER_TOKEN, ACCESS_TOKEN, SECRET_TOKEN).
				queryParam("status", "Diwali");
		HTTP_REQUEST = HTTP_REQUEST.header("Content-Type","application/json");
		logger.info("Body of the request is"+jsonBody);
		HTTP_REQUEST = HTTP_REQUEST.body(jsonBody);
		Response response = HTTP_REQUEST.put(inputURI).then().extract().response();
		logger.info(response.asString()+"This is the response");
		return response;

	}
	
	public boolean validateResponse(Response response, String key, String value) {
		JsonPath jsonPathEvaluator = response.jsonPath();
		
		String actualValue = jsonPathEvaluator.getString(key);
		return value.equalsIgnoreCase(actualValue);
		
	}
	
	public boolean validateRequestStatus(Response response, int expectedStatus) {
		int actualStatus = response.getStatusCode();
		if(actualStatus!=expectedStatus)
			{
				logger.error("Actaul Status :" + actualStatus);
			}
		else
		{
			logger.info("Actual status and expected Status are equal"+actualStatus);
		}
		return expectedStatus == actualStatus;
		
	}
	
	public boolean validateNumericResponse(Response response,String key,String value) {
		JsonPath jsonPathEvaluator = response.jsonPath();
		int actualValue = jsonPathEvaluator.get(key);
		return String.valueOf(actualValue).trim().equalsIgnoreCase(value);
	}
	
	public String getSessionkey()
	{
		RestAssured.baseURI="http://localhost:8080";
		Response res=RestAssured.given().header("Content-Type","application/json").body("").when().post("").then().statusCode(200).extract().response();
		JsonPath jsonPath=APIUtility.rawToJson(res);
		String sessionId=jsonPath.getString("session.value");
		return sessionId;
		
	}
	
	public static JsonPath rawToJson(Response res)
	{
		String response=res.asString();
		JsonPath x=new JsonPath(response);
		return x;
	}
	
	public static XmlPath rawToXML(Response res)
	{
		String response=res.asString();
		XmlPath x=new XmlPath(response);
		return x;
	}
	
}
