package stepDefinitions;

import endpoints.SkillEndpoints;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import pojo.SkillRequest;
import specs.ResponseSpecUtil;

public class Skill {

	 private Response response;
	 
	@Given("Admin Authoization to Bearer token")
	public void admin_authoization_to_bearer_token() {
	  
	}
	@Given("Admin creates POST Request for the LMS API endpoint")
	public void admin_creates_post_request_for_the_lms_api_endpoint() {
	  
	}
	@When("Admin sends HTTPS Request and  request Body with mandatory")
	public void admin_sends_https_request_and_request_body_with_mandatory() {

        SkillRequest request =
                new SkillRequest("Team1_Skill_" + System.currentTimeMillis());

        response = SkillEndpoints.createSkill(request);
	}
	@Then("Admin receives {int} Created Status with response body.")
	public void admin_receives_created_status_with_response_body(Integer int1) {
		 response.then().spec(ResponseSpecUtil.status201());
	}
	
}
