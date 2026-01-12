package stepDefinitions;

import static org.hamcrest.Matchers.equalTo;

import java.util.Map;

import endpoints.ApiRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import specs.RequestSpecUtil;
import specs.ResponseSpecUtil;
import utils.ExcelReader;
import utils.ExternalTestDataStore;

public class SkillMasterStepDef {

	
	 private Response response;	 
	 private Map<String,String> data;
	 private RequestSpecification requestSpec;

	
	 
	 @Given("Admin has a valid authorization token")
	 public void admin_has_a_valid_authoization_token() {
		 requestSpec = RequestSpecUtil.getRequestSpec();
	 }

	 
	//POST REQUEST

	@When("Admin sends HTTPS POST Request and  request Body with mandatory")
	public void admin_sends_https_post_request_and_request_body_with_mandatory() throws Exception {
      data = ExcelReader.readExcelData("Skill", "CreateSkill_Valid_NonExistingValues");
      requestSpec = requestSpec.body(data.get("Body"));
      response = ApiRequest.sendRequest(requestSpec,"POST", data.get("Endpoint"));
      String skillName =response.jsonPath().getString("skillName");
      ExternalTestDataStore.put("skillName", skillName);
      String skillID =response.jsonPath().getString("skillId");
      ExternalTestDataStore.put("skillId", skillID);
   }
	@Then("Admin receives {int} Created Status with response body.")
	public void admin_receives_created_status_with_response_body(Integer num) {
		String expectedskillName = ExternalTestDataStore.get("skillName");
		 response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num)).body("skillName", equalTo(expectedskillName));
	}
	
	@When("Admin sends HTTPS POST Request and  request Body with mandatory and existing values")
	public void admin_sends_https_post_request_and_request_body_with_mandatory_and_existing_values() throws Exception {
		 data = ExcelReader.readExcelData("Skill", "CreateSkill_Valid_ExistingValues");   
		 requestSpec = requestSpec.body(data.get("Body"));
	     response = ApiRequest.sendRequest(requestSpec,"POST", data.get("Endpoint"));
	}
	@Then("Admin receives {int} Bad Request Status with message {string}")
	public void admin_receives_bad_request_status_with_message(Integer code, String message) {
	response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(code)).body("message", equalTo(message));
	}
	
	@When("Admin sends HTTPS POST Request and  request Body with some mandatory fields missing")
	public void admin_sends_https_post_request_and_request_body_with_some_mandatory_fields_missing() throws Exception {
		 data = ExcelReader.readExcelData("Skill", "CreateSkill_Valid_MissingValues"); 
		 requestSpec = requestSpec.body(data.get("Body"));
		  response = ApiRequest.sendRequest(requestSpec,"POST", data.get("Endpoint"));
	}
	@Then("Admin receives {int} Error")
	public void admin_receives_error(Integer num) {
		 response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num));
	}
	
	//GET REQUEST
	
	@When("Admin sends HTTPS GET Request")
	public void admin_sends_https_get_request() throws Exception {
		 data = ExcelReader.readExcelData("Skill", "GetALL_SkillMaster");    
	     response = ApiRequest.sendRequest(requestSpec,"GET",data.get("Endpoint"));
	}
	@Then("Admin receives {int} Status with response body")
	public void admin_receives_status_with_response_body(Integer num) {
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num));
	}
	
	@When("Admin sends HTTPS GET Request with SkillMasterName")
	public void admin_sends_https_get_request_with_skill_master_name() throws Exception {
		 data = ExcelReader.readExcelData("Skill", "GetSkill_Valid");  
		 String skillName = ExternalTestDataStore.get("skillName");
		if (skillName == null) {
			        throw new IllegalStateException("Skill name not found in external store");
			    }
		 String endpoint = data.get("Endpoint").replace("{skillMasterName}", skillName);
		 response = ApiRequest.sendRequest(requestSpec,"GET",endpoint);
	}
	
	@When("Admin sends HTTPS GET Request with invalid SkillMasterName")
	public void admin_sends_https_get_request_with_invalid_skill_master_name() throws Exception {
		 data = ExcelReader.readExcelData("Skill", "GetSkill_InValid");    
		 response = ApiRequest.sendRequest(requestSpec,"GET",data.get("Endpoint"));
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
	
	@When("Admin sends HTTPS PUT Request and  request Body with mandatory")
	public void admin_sends_https_put_request_and_request_body_with_mandatory() throws Exception {
		 data = ExcelReader.readExcelData("Skill", "UpdateSkill_Valid");   
		 requestSpec = requestSpec.body(data.get("Body"));
		 String skillId = ExternalTestDataStore.get("skillId");
		 String endpoint = data.get("Endpoint").replace("{skillId}", skillId);
	     response = ApiRequest.sendRequest(requestSpec,"PUT",endpoint);
	     String updatedskillName =response.jsonPath().getString("skillName");
	      ExternalTestDataStore.put("updatedskillName", updatedskillName);
	}
	
	@Then("Admin receives {int} Status with updated response body.")
	public void admin_receives_status_with_updated_response_body(Integer num) {
		String expectedSkillName = ExternalTestDataStore.get("updatedskillName");
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num)).body("skillName", equalTo(expectedSkillName));
	}

	@When("Admin sends HTTPS PUT Request and  request Body with mandatory with wrong skillID")
	public void admin_sends_https_put_request_and_request_body_with_mandatory_with_wrong_skill_id() throws Exception {
		 data = ExcelReader.readExcelData("Skill", "UpdateSkill_InValid");   
		 requestSpec = requestSpec.body(data.get("Body"));
	     response = ApiRequest.sendRequest(requestSpec,"PUT", data.get("Endpoint"));
	}
	
	@Then("Admin receives {int} Bad Request with error as {string}")
	public void admin_receives_bad_request_with_error_as(Integer code, String message) {
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(code)).body("message", equalTo(message));
	}
	
	
	//DELETE REQUEST
	
	@When("Admin sends HTTPS DELETE Request")
	public void admin_sends_https_delete_request() throws Exception {
		 data = ExcelReader.readExcelData("Skill", "DeleteSkill_Valid");   
		 String skillID = ExternalTestDataStore.get("skillId");
		 if (skillID == null) {
			        throw new IllegalStateException("Skill name not found in external store");
			    }
		 String endpoint = data.get("Endpoint").replace("{skillId}", skillID);
		 response = ApiRequest.sendRequest(requestSpec,"DELETE",endpoint);
	}
	
	
	@Then("Admin receives {int} Status")
	public void admin_receives_status(Integer num) {
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num));
	}
	
	@When("Admin sends HTTPS DELETE Request invalid skillID")
	public void admin_sends_https_delete_request_invalid_skillid() throws Exception {
		 data = ExcelReader.readExcelData("Skill", "DeleteSkill_InValid");    
		 response = ApiRequest.sendRequest(requestSpec,"DELETE",data.get("Endpoint"));
	}
	
	
	@Then("Admin receives {int} Error with response body {string}")
	public void admin_receives_error_with_response_body(Integer code, String message) {
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(code)).body("message", equalTo(message));
	}
	
	
	
	
	
	
	

	
}
