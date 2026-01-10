package stepDefinitions;

import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;

public class AdminLogin {

	@Test
	public void getMethod() {
		given().log().all().baseUri("https://api.restful-api.dev/objects").when().get().then().statusCode(200).log().all();
	}


	
	
}
