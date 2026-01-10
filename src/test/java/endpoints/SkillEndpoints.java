package endpoints;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import pojo.SkillRequest;
import specs.RequestSpecUtil;

public class SkillEndpoints {

	  public static Response createSkill(SkillRequest payload) {

	        return given()
	                .spec(RequestSpecUtil.getRequestSpec())
	                .body(payload)
	        .when()
	                .post("/saveSkillMaster")
	         .then()
	         .log().ifValidationFails()
	         .log().all()
	         .extract()
	         .response();
	    }
	  
}
