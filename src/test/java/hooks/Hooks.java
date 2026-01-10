package hooks;

import config.ConfigReader;
import endpoints.LoginSendRequest;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import utils.TestDataUtil;

public class Hooks {

	 @Before(order = 0)
	    public void setupBaseUri() {
	        RestAssured.baseURI = ConfigReader.get("base.url");
	    }
	
}
