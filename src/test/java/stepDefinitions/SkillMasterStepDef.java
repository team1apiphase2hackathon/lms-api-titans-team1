package stepDefinitions;

import java.io.IOException;
import java.util.Map;

import endpoints.SkillSendRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import specs.ResponseSpecUtil;
import utils.ExcelReader;

public class SkillMasterStepDef {

	 private Response response;
	 
	 
	 private Map<String,String> data;
	 
	@Given("Admin Authoization to Bearer token")
	public void admin_authoization_to_bearer_token() {
	  
	}
	@Given("Admin creates POST Request for the LMS API endpoint")
	public void admin_creates_post_request_for_the_lms_api_endpoint() {
	  
	}
	@When("Admin sends HTTPS Request and  request Body with mandatory")
	public void admin_sends_https_request_and_request_body_with_mandatory() throws Exception {

//        SkillRequest request =
//                new SkillRequest("Team1_Skill_" + System.currentTimeMillis());

        data = ExcelReader.readExcelData("Skill", "CreateSkill_Valid_NonExisting");
        System.out.println("-------------------------------------");
        System.out.println(data.get("Body"));
        
        response = SkillSendRequest.createSkill(data.get("Body"), data.get("Endpoint"));
	}
	@Then("Admin receives {int} Created Status with response body.")
	public void admin_receives_created_status_with_response_body(Integer int1) {
		 response.then().statusCode(Integer.parseInt(data.get("ExpectedStatusCode")));
	}
	
	
	
	
	
	
	
	
	
	
	@When("Admin sends HTTPS Request and  request Body with mandatory and existing values")
	public void admin_sends_https_request_and_request_body_with_mandatory_and_existing_values() {

//		  SkillRequest request =
//	                new SkillRequest("Team1_Skill_1768082841751");
//
//	        response = SkillEndpoints.createSkill(request);
	}
	
	@Then("Admin receives {int} Bad Request Status with message cannot create skillMaster , since already exists")
	public void admin_receives_bad_request_status_with_message_cannot_create_skill_master_since_already_exists(Integer int1) {
//		response.then().spec(ResponseSpecUtil.status400());
		
	}
	
}
