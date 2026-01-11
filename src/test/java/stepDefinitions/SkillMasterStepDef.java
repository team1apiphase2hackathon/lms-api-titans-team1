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
	 

	//POST REQUEST
	 
	@Given("Admin creates POST Request for the LMS API endpoint")
	public void admin_creates_post_request_for_the_lms_api_endpoint() {
	  
	}
	@When("Admin sends HTTPS POST Request and  request Body with mandatory")
	public void admin_sends_https_post_request_and_request_body_with_mandatory() throws Exception {
      data = ExcelReader.readExcelData("Skill", "CreateSkill_Valid_NonExisting");
        System.out.println("-------------------------------------");
        System.out.println(data.get("Body"));     
        response = SkillSendRequest.createSkill(data.get("Body"), data.get("Endpoint"));
	}
	@Then("Admin receives {int} Created Status with response body.")
	public void admin_receives_created_status_with_response_body(Integer int1) {
		 response.then().statusCode(Integer.parseInt(data.get("ExpectedStatusCode")));
	}
	
	@When("Admin sends HTTPS POST Request and  request Body with mandatory and existing values")
	public void admin_sends_https_post_request_and_request_body_with_mandatory_and_existing_values() {


	}
	
	@Then("Admin receives {int} Bad Request Status with message cannot create skillMaster , since already exists")
	public void admin_receives_bad_request_status_with_message_cannot_create_skill_master_since_already_exists(Integer int1) {

		
	}
	
	@When("Admin sends HTTPS POST Request and  request Body with some mandatory fields missing")
	public void admin_sends_https_post_request_and_request_body_with_some_mandatory_fields_missing() {

	}
	
	@Then("Admin receives {int} Error")
	public void admin_receives_error(Integer int1) {

	}
	
	//GET REQUEST
	
	@When("Admin sends HTTPS GET Request")
	public void admin_sends_https_get_request() {
	   
	}
	
	@Then("Admin receives {int} Status with response body\\(showing all the list of skills)")
	public void admin_receives_status_with_response_body_showing_all_the_list_of_skills(Integer int1) {
	   
	}
	
	@When("Admin sends HTTPS GET Request with invalid SkillMasterName")
	public void admin_sends_https_get_request_with_invalid_skill_master_name() {
	   
	}
	
	@Then("Admin receives {int} Not Found Status with message as {string}, success as {string}")
	public void admin_receives_not_found_status_with_message_as_success_as(Integer int1, String string, String string2) {
	   
	}

	@When("Admin sends HTTPS GET Request with SkillMasterName")
	public void admin_sends_https_get_request_with_skill_master_name() {
	  
	}
	
	@Then("Admin receives {int} Status with response body")
	public void admin_receives_status_with_response_body(Integer int1) {
	    
	}
	
	
	//PUT REQUEST
	@When("Admin sends HTTPS PUT Request and  request Body with mandatory with wrong skillID")
	public void admin_sends_https_put_request_and_request_body_with_mandatory_with_wrong_skill_id() {
	  
	}
	
	@Then("Admin receives {int} Bad Request with error as {string}")
	public void admin_receives_bad_request_with_error_as(Integer int1, String string) {
	
	}
	
	@When("Admin sends HTTPS PUT Request and  request Body with mandatory")
	public void admin_sends_https_put_request_and_request_body_with_mandatory() {
	  
	}
	
	@Then("Admin receives {int} Status with updated response body.")
	public void admin_receives_status_with_updated_response_body(Integer int1) {
	 
	}

	
	
	//DELETE REQUEST
	
	@Then("Admin receives {int} Status")
	public void admin_receives_status(Integer int1) {
	 
	}
	
	@When("Admin sends HTTPS DELETE Request")
	public void admin_sends_https_delete_request() {
	   
	}
	
	@Then("Admin receives {int} Error with response body {string}")
	public void admin_receives_error_with_response_body(Integer int1, String string) {
	   
	}
	
	
	
	
	
	
	

	
}
