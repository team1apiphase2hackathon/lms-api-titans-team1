package endpoints;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import requestPojo.UserLogin;

public class LoginSendRequest {

	 public static Response login(UserLogin payload) {

	        return given()
	                .contentType("application/json")
	                .body(payload)
	                .log().all()
	        .when()
	                .post("/login")
	         .then()
	   	         .log().ifValidationFails()
	   	         .log().all()
	   	         .extract()
	   	         .response();
	    }
	 
	 
}
