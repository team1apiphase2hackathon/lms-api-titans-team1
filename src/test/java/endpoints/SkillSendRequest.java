package endpoints;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

import specs.RequestSpecUtil;

public class SkillSendRequest {

	  public static Response createSkill(String payload, String endpoint ) {

	        return given()
	                .spec(RequestSpecUtil.getRequestSpec())
	                .body(payload)
	                
	        .when()
	                .post(endpoint)
	         .then()
	         .log().ifValidationFails()
	         .log().all()
	         .extract()
	         .response();
	    }
	  
}
