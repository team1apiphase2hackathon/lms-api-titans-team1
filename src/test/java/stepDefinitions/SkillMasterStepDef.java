package stepDefinitions;

import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.Map;

import endpoints.ApiRequest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import specs.ResponseSpecUtil;
import utils.ExcelReader;
import utils.TestDataUtil;

public class SkillMasterStepDef {

	 private Response response;	 
	 private Map<String,String> data;
	 

	//POST REQUEST

	@When("Admin sends HTTPS POST Request and  request Body with mandatory")
	public void admin_sends_https_post_request_and_request_body_with_mandatory() throws Exception {
      data = ExcelReader.readExcelData("Skill", "CreateSkill_Valid_NonExistingValues");
      response = ApiRequest.sendRequest("POST",data.get("Body"), data.get("Endpoint"));
      String skillName = response.jsonPath().getString("skillName"); 
      TestDataUtil.setSkillName(skillName);
      System.out.println("----------------skill name---------------------");
      System.out.println(TestDataUtil.getSkillName());
	}
	@Then("Admin receives {int} Created Status with response body.")
	public void admin_receives_created_status_with_response_body(Integer num) {
		 response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num));
	}
	
	@When("Admin sends HTTPS POST Request and  request Body with mandatory and existing values")
	public void admin_sends_https_post_request_and_request_body_with_mandatory_and_existing_values() throws Exception {
		 data = ExcelReader.readExcelData("Skill", "CreateSkill_Valid_ExistingValues");   
	     response = ApiRequest.sendRequest("POST",data.get("Body"), data.get("Endpoint"));
	}
	@Then("Admin receives {int} Bad Request Status with message {string}")
	public void admin_receives_bad_request_status_with_message(Integer code, String message) {
	response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(code)).body("message", equalTo("cannot create skillMaster , since already exists"));
	}
	
	@When("Admin sends HTTPS POST Request and  request Body with some mandatory fields missing")
	public void admin_sends_https_post_request_and_request_body_with_some_mandatory_fields_missing() throws Exception {
		 data = ExcelReader.readExcelData("Skill", "CreateSkill_Valid_MissingValues");    
	     response = ApiRequest.sendRequest("POST",data.get("Body"), data.get("Endpoint"));
	}
	@Then("Admin receives {int} Error")
	public void admin_receives_error(Integer num) {
		 response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num));
	}
	
	//GET REQUEST
	
	@When("Admin sends HTTPS GET Request")
	public void admin_sends_https_get_request() throws Exception {
		 data = ExcelReader.readExcelData("Skill", "GetALL_SkillMaster");    
	     response = ApiRequest.sendRequest("GET",null,data.get("Endpoint"));
	}
	@Then("Admin receives {int} Status with response body")
	public void admin_receives_status_with_response_body(Integer num) {
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num));
	}
	
	@When("Admin sends HTTPS GET Request with SkillMasterName")
	public void admin_sends_https_get_request_with_skill_master_name() throws Exception {
		 data = ExcelReader.readExcelData("Skill", "GetSkill_Valid");    
		 String endpoint = data.get("Endpoint").replace("{skillMasterName}", TestDataUtil.getSkillName());
	     response = ApiRequest.sendRequest("GET",null,endpoint);
	}
	
	@When("Admin sends HTTPS GET Request with invalid SkillMasterName")
	public void admin_sends_https_get_request_with_invalid_skill_master_name() throws Exception {
		 data = ExcelReader.readExcelData("Skill", "GetSkill_InValid");    
	     response = ApiRequest.sendRequest("GET",null,data.get("Endpoint"));
	}
	
	@Then("Admin receives {int} Not Found Status with error message and success as {string}")
	public void admin_receives_not_found_status_with_error_message_as_success_as(Integer code, String successParam) throws Exception {
		 data = ExcelReader.readExcelData("Skill", "GetSkill_InValid");   
		 String endpoint = data.get("Endpoint");
		String skillName = endpoint.substring(endpoint.lastIndexOf("/") + 1);
		    String expectedMessage = "skill with id" + skillName + "not found";
		    boolean expectedSuccess = Boolean.parseBoolean(successParam);
	response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(code)).body("message", equalTo(expectedMessage)).body("success", equalTo(expectedSuccess));
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
