package stepDefinitions;

import java.io.IOException;
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
import utils.HelperClass;

public class UserControllerStepDef {
	
	 private Response response;	 
	 private Map<String,String> data;
	 private RequestSpecification requestSpec;

	 @Given("Admin has a valid authorization token in user controller")
	 public void admin_has_a_valid_authoization_token_in_user_controller() {
		 requestSpec = RequestSpecUtil.getRequestSpec();
	 }

	 //userId

	 
//POST REQUEST 

	@When("Admin sends HTTPS POST Request and request Body with mandatory fields and admin role")
	public void admin_sends_https_post_request_and_request_body_with_mandatory_fields_and_admin_role() throws Exception {
		data = ExcelReader.readExcelData("User", "CreateAdmin_valid_mandatory");
	      requestSpec = requestSpec.body(data.get("Body"));
	      response = ApiRequest.sendRequest(requestSpec,"POST", data.get("Endpoint"));
	      String userId =response.jsonPath().getString("user.userId");
	      ExternalTestDataStore.put("userId", userId);
	}
	@Then("Admin receives {int} Created Status with response body")
	public void admin_receives_created_status_with_response_body(Integer num) {
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num));
	}

	
	@When("Admin sends HTTPS POST Request and request Body with mandatory and additional fields with {string}")
    public void admin_sends_https_post_request_and_request_body_with_mandatory_and_additional_fields_with(String roles) throws Exception {
		data = ExcelReader.readExcelData("User", roles);
	      requestSpec = requestSpec.body(data.get("Body"));
	      response = ApiRequest.sendRequest(requestSpec,"POST", data.get("Endpoint"));
	      HelperClass.deleteIfCreated(response, requestSpec, "/users/{userID}");
	      
    }

	  
	@When("Admin sends HTTPS POST Request and request Body with mandatory and additional fields with diff{string}")
    public void admin_sends_https_post_request_and_request_body_with_mandatory_and_additional_fields_with_diff(String visaStatus) throws Exception {
		data = ExcelReader.readExcelData("User", visaStatus);
	      requestSpec = requestSpec.body(data.get("Body"));
	      response = ApiRequest.sendRequest(requestSpec,"POST", data.get("Endpoint"));
	      HelperClass.deleteIfCreated(response, requestSpec, "/users/{userID}");
	      
    }
    
	@When("Admin sends HTTPS POST Request and  request Body with mandatory and additional fields with existing phone number")
	public void admin_sends_https_post_request_and_request_body_with_mandatory_and_additional_fields_with_existing_phone_number() {
	   
	}

	
	@When("Admin sends HTTPS POST Request and request Body with missing mandatory fields")
	public void admin_sends_https_post_request_and_request_body_with_missing_mandatory_fields() {
	   
	}
	@Then("Admin receives {int} Bad Request Status with message and boolean success details")
	public void admin_receives_bad_request_status_with_message_and_boolean_success_details(Integer int1) {
	  
	}

	
	//GET REQUEST 
	
	@When("Admin sends GET Request for the LMS API All admin endpoint")
	public void admin_sends_get_request_for_the_lms_api_all_admin_endpoint() {
	 
	}
	
	@Then("Admin receives {int} OK Status with response body")
	public void admin_receives_ok_status_with_response_body(Integer num) {
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num));
	}
	
	@When("Admin sends GET Request for the LMS API All Staff endpoint")
	public void admin_sends_get_request_for_the_lms_api_all_staff_endpoint() {
	  
	}

	@When("Admin sends GET Request for the LMS API endpoint with valid admin ID")
	public void admin_sends_get_request_for_the_lms_api_endpoint_with_valid_admin_id() {
	
	}

	
	@When("Admin sends GET Request for the LMS API admin Roles endpoint")
	public void admin_sends_get_request_for_the_lms_api_admin_roles_endpoint() {
	
	}

	@When("Admin sends GET Request for the LMS API endpoint with invalid admin ID")
	public void admin_sends_get_request_for_the_lms_api_endpoint_with_invalid_admin_id() {
	  
	}
	@Then("Admin receives {int} Not Found Status with message and boolean success details")
	public void admin_receives_not_found_status_with_message_and_boolean_success_details(Integer int1) {
	
	}
	
	//PUT REQUEST
	
	@When("Admin sends HTTPS PUT Request  and request Body with missing mandatory fields")
	public void admin_sends_https_put_request_and_request_body_with_missing_mandatory_fields() {
	   
	}

	
	@When("Admin sends HTTPS PUT Request and request Body with missing mandatory fields and Mandatory Role ID and Role status")
	public void admin_sends_https_put_request_and_request_body_with_missing_mandatory_fields_and_mandatory_role_id_and_role_status() {
	   
	}

	
	@When("Admin sends HTTPS PUT Request and request Body with missing mandatory fields and valid adminID")
	public void admin_sends_https_put_request_and_request_body_with_missing_mandatory_fields_and_valid_admin_id() {
	    
	}

	
	@When("Admin sends HTTPS PUT Request and request Body with mandatory fields and invalid adminID and Mandatory program Id, batch Id ,role id, admin id, admin role program batch status")
	public void admin_sends_https_put_request_and_request_body_with_mandatory_fields_and_invalid_admin_id_and_mandatory_program_id_batch_id_role_id_admin_id_admin_role_program_batch_status() {
	  
	}

	
	@When("Admin sends HTTPS PUT Request and request Body with mandatory fields and valid adminID and Mandatory program Id, batch Id ,role id, admin id, admin role program batch status")
	public void admin_sends_https_put_request_and_request_body_with_mandatory_fields_and_valid_admin_id_and_mandatory_program_id_batch_id_role_id_admin_id_admin_role_program_batch_status() {
	    
	}
	
	@When("Admin sends HTTPS PUT Request and  request Body with mandatory and additional fields and valid adminID")
	public void admin_sends_https_put_request_and_request_body_with_mandatory_and_additional_fields_and_valid_admin_id() {
	
	}
	
	@When("Admin sends HTTPS PUT Request and  request Body with mandatory and additional fields and invalid adminID")
	public void admin_sends_https_put_request_and_request_body_with_mandatory_and_additional_fields_and_invalid_admin_id() {
	  
	}
	
	@When("Admin sends HTTPS PUT Request and request Body with mandatory fields and valid adminID and Mandatory Role ID and Role status")
	public void admin_sends_https_put_request_and_request_body_with_mandatory_fields_and_valid_admin_id_and_mandatory_role_id_and_role_status() {
	   
	}

	
	@When("Admin sends HTTPS PUT Request and request Body with mandatory fields and invalid adminID and Mandatory Role ID and Role status")
	public void admin_sends_https_put_request_and_request_body_with_mandatory_fields_and_invalid_admin_id_and_mandatory_role_id_and_role_status() {
	   
	}
	
	
	//DELETE REQUEST
	
	@When("Admin sends DELETE Request for the LMS API endpoint  and  invalid admin ID")
	public void admin_sends_delete_request_for_the_lms_api_endpoint_and_invalid_admin_id() throws Exception {
		
	}

	
	@When("Admin sends DELETE Request for the LMS API endpoint  and  valid admin ID")
	public void admin_sends_delete_request_for_the_lms_api_endpoint_and_valid_admin_id() throws IOException {
		 data = ExcelReader.readExcelData("User", "DeleteAdmin_validAdminID");  
		 String userId = ExternalTestDataStore.get("userId");
		 String endpoint = data.get("Endpoint").replace("{userID}", userId);
		 response = ApiRequest.sendRequest(requestSpec,"DELETE",endpoint);
	}

	
}
