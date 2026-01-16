package stepDefinitions;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import http.ApiRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import specs.RequestSpecUtil;
import specs.ResponseSpecUtil;
import utils.ExcelReader;
import utils.GlobalTestData;
import utils.HelperClass;

public class UserControllerStepDef extends GlobalTestData {

	private Response response;
	private Map<String, String> data;
	private RequestSpecification requestSpec;

	@Given("Admin has a valid authorization token in user controller")
	public void admin_has_a_valid_authoization_token_in_user_controller() {
		requestSpec = RequestSpecUtil.getRequestSpec();
		RequestSpecUtil.logScenarioName("USER CONTROLLER MODULE LOGS");
	}

//POST REQUEST 

	@When("Admin sends HTTPS POST Request and request Body with mandatory fields and admin role")
	public void admin_sends_https_post_request_and_request_body_with_mandatory_fields_and_admin_role()
			throws Exception {
		data = ExcelReader.readExcelData("User", "CreateAdmin_valid_mandatory");
		requestSpec = requestSpec.body(data.get("Body"));
		response = ApiRequest.sendRequest(requestSpec, "POST", data.get("Endpoint"));
	}

	@When("Admin sends HTTPS POST Request and request Body with multiple roles")
	public void admin_sends_https_post_request_and_request_body_with_multiple_roles() throws Exception {
		data = ExcelReader.readExcelData("User", "CreateAdmin_valid_mulitpleRoles");
		requestSpec = requestSpec.body(data.get("Body"));
		response = ApiRequest.sendRequest(requestSpec, "POST", data.get("Endpoint"));
		// UserId storage
		String TuserId = response.jsonPath().getString("user.userId");
		userId = TuserId;
	}

	@Then("Admin receives {int} Created Status with response body and schema validation")
	public void admin_receives_created_status_with_response_body_and_schema_validation(Integer num) throws Exception {
		// POST - Status code and schema validation
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num))
				.body(matchesJsonSchemaInClasspath("schemas/UserController/CreateUserResponseSchema.json"));
		data = ExcelReader.readExcelData("User", "CreateAdmin_valid_mulitpleRoles");
		String requestbody = data.get("Body");
		JsonPath requestJson = new JsonPath(requestbody);
		JsonPath responseJson = response.jsonPath();
		// Response data validation
		Assert.assertEquals(responseJson.getString("user.userFirstName"), requestJson.getString("userFirstName"),
				"userFirstName mismatch");
		Assert.assertEquals(responseJson.getString("user.userLastName"), requestJson.getString("userLastName"),
				"userLastName mismatch");
		Assert.assertEquals(responseJson.getString("user.userPhoneNumber"), requestJson.getString("userPhoneNumber"),
				"userPhoneNumber mismatch");
		Assert.assertEquals(responseJson.getString("user.userLocation"), requestJson.getString("userLocation"),
				"userLocation mismatch");
		Assert.assertEquals(responseJson.getString("user.userTimeZone"), requestJson.getString("userTimeZone"),
				"userTimeZone mismatch");
		Assert.assertEquals(responseJson.getString("user.userVisaStatus"), requestJson.getString("userVisaStatus"),
				"userVisaStatus mismatch");
		Assert.assertEquals(responseJson.getString("user.userLoginEmail"),
				requestJson.getString("userLogin.userLoginEmail"), "userLoginEmail mismatch");
		List<Map<String, String>> expectedRoles = requestJson.getList("userRoleMaps");
		List<Map<String, String>> actualRoles = responseJson.getList("roles");
		Assert.assertEqualsNoOrder(actualRoles.toArray(), expectedRoles.toArray());

	}

	@Then("Admin receives {int} Created Status with response body")
	public void admin_receives_created_status_with_response_body(Integer num) {
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num));
	}

	@When("Admin sends HTTPS POST Request and request Body with mandatory and additional fields with {string}")
	public void admin_sends_https_post_request_and_request_body_with_mandatory_and_additional_fields_with(String roles)
			throws Exception {
		data = ExcelReader.readExcelData("User", roles);
		requestSpec = requestSpec.body(data.get("Body"));
		response = ApiRequest.sendRequest(requestSpec, "POST", data.get("Endpoint"));
		if (roles.contentEquals("CreateUserWithAdminRole")) {
			// Admin user storage
			String AdminUser = response.jsonPath().getString("user.userId");
			AdminUserId = AdminUser;
		} else if (roles.contentEquals("CreateUserWithStaffRole")) {
			// Staff user storage
			String StaffUser = response.jsonPath().getString("user.userId");
			StaffUserId = StaffUser;
		} else {
			// Student user storage
			String StudentUser = response.jsonPath().getString("user.userId");
			StudentUserId = StudentUser;
		}

	}

	@When("Admin sends HTTPS POST Request and request Body with mandatory and additional fields with diff{string}")
	public void admin_sends_https_post_request_and_request_body_with_mandatory_and_additional_fields_with_diff(
			String visaStatus) throws Exception {
		data = ExcelReader.readExcelData("User", visaStatus);
		requestSpec = requestSpec.body(data.get("Body"));
		response = ApiRequest.sendRequest(requestSpec, "POST", data.get("Endpoint"));
		HelperClass.deleteIfCreated(response, requestSpec, "/users/{userID}");

	}

	@When("Admin sends HTTPS POST Request and request Body with mandatory and additional fields with different {string}")
	public void admin_sends_https_post_request_and_request_body_with_mandatory_and_additional_fields_with_different(
			String timeZone) throws Exception {
		data = ExcelReader.readExcelData("User", timeZone);
		requestSpec = requestSpec.body(data.get("Body"));
		response = ApiRequest.sendRequest(requestSpec, "POST", data.get("Endpoint"));
		HelperClass.deleteIfCreated(response, requestSpec, "/users/{userID}");

	}

	@When("Admin sends HTTPS POST Request and request Body with mandatory and additional fields with existing phone number")
	public void admin_sends_https_post_request_and_request_body_with_mandatory_and_additional_fields_with_existing_phone_number()
			throws Exception {
		data = ExcelReader.readExcelData("User", "CreateAdmin_valid_existingPhoneNumber");
		requestSpec = requestSpec.body(data.get("Body"));
		response = ApiRequest.sendRequest(requestSpec, "POST", data.get("Endpoint"));
	}

	@Then("Admin receives {int} Bad Request Status with phone number existing message")
	public void admin_receives_bad_request_status_with_phone_number_existing_message(Integer num) throws Exception {
		data = ExcelReader.readExcelData("User", "CreateAdmin_valid_existingPhoneNumber");
		String body = data.get("Body");
		JsonPath jsonPath = new JsonPath(body);
		String phoneNumber = jsonPath.getString("userPhoneNumber");
		String expectedMessage = "Failed to create new User as phone number " + phoneNumber + " already exists !!";
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num)).body("message",
				equalTo(expectedMessage));

	}

	@When("Admin sends HTTPS POST Request and request Body with mandatory and additional fields with existing userLoginEmail")
	public void admin_sends_https_post_request_and_request_body_with_mandatory_and_additional_fields_with_existing_user_login_email()
			throws Exception {
		data = ExcelReader.readExcelData("User", "CreateAdmin_valid_existingLoginEmailID");
		requestSpec = requestSpec.body(data.get("Body"));
		response = ApiRequest.sendRequest(requestSpec, "POST", data.get("Endpoint"));
	}

	@When("Admin sends HTTPS POST Request and request Body with missing mandatory {string}")
	public void admin_sends_https_post_request_and_request_body_with_missing_mandatory(String mandatoryField)
			throws Exception {
		data = ExcelReader.readExcelData("User", mandatoryField);
		requestSpec = requestSpec.body(data.get("Body"));
		response = ApiRequest.sendRequest(requestSpec, "POST", data.get("Endpoint"));
		HelperClass.deleteIfCreated(response, requestSpec, "/users/{userID}");

	}

	@Then("Admin receives {int} Bad Request Status with message")
	public void admin_receives_bad_request_status_with_message(Integer num) throws Exception {
		String expectedMessage = data.get("ExpectedStatusMessage");
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num)).body("message",
				equalTo(expectedMessage));
	}

	@When("Admin sends HTTPS POST Request and request Body with missing additional {string}")
	public void admin_sends_https_post_request_and_request_body_with_missing_additional(String additionalField)
			throws Exception {
		data = ExcelReader.readExcelData("User", additionalField);
		requestSpec = requestSpec.body(data.get("Body"));
		response = ApiRequest.sendRequest(requestSpec, "POST", data.get("Endpoint"));
		HelperClass.deleteIfCreated(response, requestSpec, "/users/{userID}");

	}

	@Then("Admin receives {int} OK Status with response body")
	public void admin_receives_ok_status_with_response_body(Integer num) {
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num));
	}

	// GET REQUEST

	@When("Admin sends GET Request for the LMS API All users endpoint")
	public void admin_sends_get_request_for_the_lms_api_all_users_endpoint() throws Exception {
		data = ExcelReader.readExcelData("User", "Get_AllUsers");
		response = ApiRequest.sendRequest(requestSpec, "GET", data.get("Endpoint"));
	}

	@When("Admin sends GET Request for the LMS API All active users endpoint")
	public void admin_sends_get_request_for_the_lms_api_all_active_users_endpoint() throws Exception {
		data = ExcelReader.readExcelData("User", "Get_All_ActiveUsers");
		response = ApiRequest.sendRequest(requestSpec, "GET", data.get("Endpoint"));
	}

	@When("Admin sends GET Request for the LMS API")
	public void admin_sends_get_request_for_the_lms_api() throws Exception {
		data = ExcelReader.readExcelData("User", "Get_Emails_All_ActiveUsers");
		response = ApiRequest.sendRequest(requestSpec, "GET", data.get("Endpoint"));
	}

	@When("Admin sends GET Request for the LMS API admin Roles endpoint")
	public void admin_sends_get_request_for_the_lms_api_admin_roles_endpoint() throws Exception {
		data = ExcelReader.readExcelData("User", "Get_AllRoles");
		response = ApiRequest.sendRequest(requestSpec, "GET", data.get("Endpoint"));
	}

	@When("Admin sends GET Request for the LMS API endpoint with valid {string}")
	public void admin_sends_get_request_for_the_lms_api_endpoint_with_valid(String user) throws Exception {
		data = ExcelReader.readExcelData("User", user);
		if (user.contentEquals("GetAdminId")) {
			String endpoint = data.get("Endpoint").replace("{userId}", AdminUserId);
			response = ApiRequest.sendRequest(requestSpec, "GET", endpoint);
		} else if (user.contentEquals("GetStaffId")) {
			String endpoint = data.get("Endpoint").replace("{userId}", StaffUserId);
			response = ApiRequest.sendRequest(requestSpec, "GET", endpoint);
		} else {
			String endpoint = data.get("Endpoint").replace("{userId}", StudentUserId);
			response = ApiRequest.sendRequest(requestSpec, "GET", endpoint);
		}

	}

	@When("Admin sends GET Request for the LMS API endpoint with invalid user ID")
	public void admin_sends_get_request_for_the_lms_api_endpoint_with_invalid_user_id() throws Exception {
		data = ExcelReader.readExcelData("User", "GetInvalidUserId");
		response = ApiRequest.sendRequest(requestSpec, "GET", data.get("Endpoint"));
	}

	@When("Admin sends GET Request for the LMS API all users with roles")
	public void admin_sends_get_request_for_the_lms_api_all_users_with_roles() throws Exception {
		data = ExcelReader.readExcelData("User", "Get_AllUsers_Roles");
		response = ApiRequest.sendRequest(requestSpec, "GET", data.get("Endpoint"));
	}

	@When("Admin sends GET Request for the LMS API all users count for {string}")
	public void admin_sends_get_request_for_the_lms_api_all_users_count_for(String user) throws Exception {
		data = ExcelReader.readExcelData("User", user);
		response = ApiRequest.sendRequest(requestSpec, "GET", data.get("Endpoint"));
	}

	@When("Admin sends GET Request for the LMS API user by program batchId")
	public void admin_sends_get_request_for_the_lms_api_user_by_program_batchid() throws Exception {
		data = ExcelReader.readExcelData("User", "Get_User_by_Program_Batches");
		String endpoint = data.get("Endpoint").replace("{batchId}", String.valueOf(batchId));
		response = ApiRequest.sendRequest(requestSpec, "GET", endpoint);
	}

	@Then("Admin receives {int} OK Status with batchid response body")
	public void admin_receives_ok_status_with_batchid_response_body(Integer num) {
	response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num));
		JsonPath responseJson = response.jsonPath();
		//user - batch validation
		Assert.assertEquals(responseJson.getString("userId[0]"), userId,"user not linked to batchid mismatch");
	}
	
	@When("Admin sends GET Request for the LMS API user by program")
	public void admin_sends_get_request_for_the_lms_api_user_by_program() throws Exception {
		data = ExcelReader.readExcelData("User", "Get_User_by_Program");
		String endpoint = data.get("Endpoint").replace("{programId}", String.valueOf(programId));
		response = ApiRequest.sendRequest(requestSpec, "GET", endpoint);
	}

	@Then("Admin receives {int} OK Status with programid response body")
	public void admin_receives_ok_status_with_programid_response_body(Integer num) {
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num));
		JsonPath responseJson = response.jsonPath();
		//user - program validation
		Assert.assertEquals(responseJson.getString("userId[0]"), userId,"user not linked to program mismatch");
	}
	
	@When("Admin sends GET Request for the LMS API {string}")
	public void admin_sends_get_request_for_the_lms_api(String roleId) throws Exception {
		data = ExcelReader.readExcelData("User", "Gets_Users_by_roleId");
		String endpoint = data.get("Endpoint").replace("{roleId}", roleId);
		response = ApiRequest.sendRequest(requestSpec, "GET", endpoint);
	}

	@When("Admin sends GET Request for the LMS API user by roleId v2")
	public void admin_sends_get_request_for_the_lms_api_user_by_roleid_v2() throws Exception {
		data = ExcelReader.readExcelData("User", "Gets_Users_by_roleId_v2");
		response = ApiRequest.sendRequest(requestSpec, "GET", data.get("Endpoint"));
	}

	@When("Admin sends GET Request for the LMS API batchId by userId")
	public void admin_sends_get_request_for_the_lms_api_batchid_by_userid() throws Exception {
		data = ExcelReader.readExcelData("User", "Get_Batch_by_UserId");
		String endpoint = data.get("Endpoint").replace("{userId}", AdminUserId);
		response = ApiRequest.sendRequest(requestSpec, "GET", endpoint);
	}

	@When("Admin sends GET Request for the LMS API user details by Id")
	public void admin_sends_get_request_for_the_lms_api_user_details_by_id() throws Exception {
		data = ExcelReader.readExcelData("User", "Get_User_Details_by_Id");
		String endpoint = data.get("Endpoint").replace("{id}", AdminUserId);
		response = ApiRequest.sendRequest(requestSpec, "GET", endpoint);
	}

	@Then("Admin receives {int} Not Found Status with message")
	public void admin_receives_not_found_status_with_message(Integer num) throws Exception {
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num));
	}

	// PUT REQUEST

	@When("Admin sends HTTPS PUT Request and  request Body with mandatory and additional fields and valid adminID")
	public void admin_sends_https_put_request_and_request_body_with_mandatory_and_additional_fields_and_valid_admin_id()
			throws Exception {
		data = ExcelReader.readExcelData("User", "UpdateAdmin_Valid");
		requestSpec = requestSpec.body(data.get("Body"));
		String endpoint = data.get("Endpoint").replace("{userId}", userId);
		response = ApiRequest.sendRequest(requestSpec, "PUT", endpoint);

	}

	@Then("Admin receives {int} OK Status with updated response")
	public void admin_receives_ok_status_with_updated_response(Integer num) throws Exception {
		data = ExcelReader.readExcelData("User", "UpdateAdmin_Valid");
		String requestbody = data.get("Body");
		JsonPath requestJson = new JsonPath(requestbody);
		JsonPath responseJson = response.jsonPath();
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num));
		Map<String, Object> requestMap = requestJson.getMap("");
		for (Map.Entry<String, Object> entry : requestMap.entrySet()) {
			String key = entry.getKey();
			if (key.equalsIgnoreCase("userId"))
				continue;
			String expectedValue = String.valueOf(entry.getValue());
			String actualValue = String.valueOf(responseJson.getString(key));
			Assert.assertEquals(actualValue, expectedValue, key + " did not update correctly");
		}

	}

	@When("Admin sends HTTPS PUT Request and  request Body with mandatory and additional fields and invalid adminID")
	public void admin_sends_https_put_request_and_request_body_with_mandatory_and_additional_fields_and_invalid_admin_id()
			throws Exception {
		data = ExcelReader.readExcelData("User", "UpdateAdmin_InValid");
		requestSpec = requestSpec.body(data.get("Body"));
		response = ApiRequest.sendRequest(requestSpec, "PUT", data.get("Endpoint"));

	}

	@When("Admin sends HTTPS PUT Request and request Body with missing {string} fields")
	public void admin_sends_https_put_request_and_request_body_with_missing(String mandatoryFields) throws Exception {
		data = ExcelReader.readExcelData("User", mandatoryFields);
		requestSpec = requestSpec.body(data.get("Body"));
		String endpoint = data.get("Endpoint").replace("{userId}", userId);
		response = ApiRequest.sendRequest(requestSpec, "PUT", endpoint);
	}

	@Then("Admin receives {int} Bad Request Status with missing message")
	public void admin_receives_bad_request_status_with_missing_message(Integer num) throws Exception {
		String expectedMessage = data.get("ExpectedStatusMessage").trim();
		String actualMessage = response.jsonPath().getString("message").trim();
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num));
		Assert.assertEquals(actualMessage, expectedMessage, "Error message mismatch");
	}

	@When("Admin sends HTTPS PUT Request and request Body with only mandatory fields and valid adminID")
	public void admin_sends_https_put_request_and_request_body_with_only_mandatory_fields_and_valid_admin_id()
			throws Exception {
		data = ExcelReader.readExcelData("User", "UpdateAdmin_valid_OnlyMandatory");
		requestSpec = requestSpec.body(data.get("Body"));
		String endpoint = data.get("Endpoint").replace("{userId}", userId);
		response = ApiRequest.sendRequest(requestSpec, "PUT", endpoint);

	}

	@Then("Admin receives {int} OK Status")
	public void admin_receives_ok_status(Integer num) throws Exception {
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num));
	}

	@When("Admin sends HTTPS PUT Request and request Body with mandatory fields and valid adminID and Mandatory Role ID and Role status")
	public void admin_sends_https_put_request_and_request_body_with_mandatory_fields_and_valid_admin_id_and_mandatory_role_id_and_role_status()
			throws Exception {
		data = ExcelReader.readExcelData("User", "UpdateAdminRoleStatus_Valid");
		requestSpec = requestSpec.body(data.get("Body"));
		String endpoint = data.get("Endpoint").replace("{userId}", userId);
		response = ApiRequest.sendRequest(requestSpec, "PUT", endpoint);
	}

	@When("Admin sends HTTPS PUT Request and request Body with mandatory fields and invalid adminID and Mandatory Role ID and Role status")
	public void admin_sends_https_put_request_and_request_body_with_mandatory_fields_and_invalid_admin_id_and_mandatory_role_id_and_role_status()
			throws Exception {
		data = ExcelReader.readExcelData("User", "UpdateAdminRoleStatus_InValid");
		requestSpec = requestSpec.body(data.get("Body"));
		response = ApiRequest.sendRequest(requestSpec, "PUT", data.get("Endpoint"));
	}

	@When("Admin sends HTTPS PUT Request and request Body with missing role {string}")
	public void admin_sends_https_put_request_and_request_body_with_missing_role(String roleFields) throws Exception {
		data = ExcelReader.readExcelData("User", roleFields);
		requestSpec = requestSpec.body(data.get("Body"));
		String endpoint = data.get("Endpoint").replace("{userId}", userId);
		response = ApiRequest.sendRequest(requestSpec, "PUT", endpoint);
	}

	@Then("Admin receives {int} Bad Request Status with missing role field message")
	public void admin_receives_bad_request_status_with_missing_role_field_message(Integer num) throws Exception {
		String expectedMessage = data.get("ExpectedStatusMessage");
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num)).body("message",
				equalTo(expectedMessage));
	}

	@When("Admin sends HTTPS PUT Request and request Body with mandatory fields and valid adminID and Mandatory program Id, batch Id ,role id, admin id, admin role program batch status")
	public void admin_sends_https_put_request_and_request_body_with_mandatory_fields_and_valid_admin_id_and_mandatory_program_id_batch_id_role_id_admin_id_admin_role_program_batch_status()
			throws Exception {
		data = ExcelReader.readExcelData("User", "UpdateAdminProgramBatch_Valid");
		String body = data.get("Body");
		body = body.replace("{programIdd}", String.valueOf(programId)).replace("{batchIdd}", String.valueOf(batchId));
		requestSpec = requestSpec.body(body);
		String endpoint = data.get("Endpoint").replace("{userId}", userId);
		response = ApiRequest.sendRequest(requestSpec, "PUT", endpoint);
	}

	@When("Admin sends HTTPS PUT Request and request Body with mandatory fields and invalid adminID and Mandatory program Id, batch Id ,role id, admin id, admin role program batch status")
	public void admin_sends_https_put_request_and_request_body_with_mandatory_fields_and_invalid_admin_id_and_mandatory_program_id_batch_id_role_id_admin_id_admin_role_program_batch_status()
			throws Exception {
		data = ExcelReader.readExcelData("User", "UpdateAdminProgramBatch_InValid");
		String body = data.get("Body");
		body = body.replace("{programIdd}", String.valueOf(programId)).replace("{batchIdd}", String.valueOf(batchId));
		requestSpec = requestSpec.body(body);
		response = ApiRequest.sendRequest(requestSpec, "PUT", data.get("Endpoint"));
	}

	@Then("Admin receives {int} OK Status with response body and message")
	public void admin_receives_ok_status_with_response_body_and_message(Integer num) {
		String expectedMessage = data.get("ExpectedStatusMessage").replace("U000", userId);
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num)).body("message",
				equalTo(expectedMessage));
	}

	@When("Admin sends HTTPS PUT Request and request Body with missing mandatory fields program batch {string}")
	public void admin_sends_https_put_request_and_request_body_with_missing_mandatory_fields_program_batch(
			String fields) throws Exception {
		data = ExcelReader.readExcelData("User", fields);
		String body = data.get("Body");
		body = body.replace("{programIdd}", String.valueOf(programId)).replace("{batchIdd}", String.valueOf(batchId));
		requestSpec = requestSpec.body(body);
		String endpoint = data.get("Endpoint").replace("{userId}", userId);
		response = ApiRequest.sendRequest(requestSpec, "PUT", endpoint);
	}

	@Then("Admin receives {int} Bad Request Status with message details")
	public void admin_receives_bad_request_status_with_missing_details(Integer num) throws Exception {
		String expectedMessage = data.get("ExpectedStatusMessage").trim();
		String actualMessage = response.jsonPath().getString("message").trim();
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num));
		Assert.assertEquals(actualMessage, expectedMessage, "Error message mismatch");
	}

	@When("Admin sends HTTPS PUT Request and request Body with login email and valid adminID")
	public void admin_sends_https_put_request_and_request_body_with_login_email_and_valid_adminid() throws Exception {
		data = ExcelReader.readExcelData("User", "UpdateAdminLoginEmail_Valid");
		requestSpec = requestSpec.body(data.get("Body"));
		String endpoint = data.get("Endpoint").replace("{userId}", userId);
		response = ApiRequest.sendRequest(requestSpec, "PUT", endpoint);
	}

	@Then("Admin receives {int} OK Status with response body and updated message")
	public void admin_receives_ok_status_with_response_body_and_updated_message(Integer num) {
		String expectedMessage = data.get("ExpectedStatusMessage").replace("U000", userId).trim();
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num));
		String actualMessage = response.asString().trim();
		Assert.assertEquals(actualMessage, expectedMessage, "Response message mismatch");

	}

	@When("Admin sends HTTPS PUT Request and request Body with login email and invalid adminID")
	public void admin_sends_https_put_request_and_request_body_with_login_email_and_invalid_admin_id()
			throws Exception {
		data = ExcelReader.readExcelData("User", "UpdateAdminLoginEmail_InValid");
		requestSpec = requestSpec.body(data.get("Body"));
		response = ApiRequest.sendRequest(requestSpec, "PUT", data.get("Endpoint"));
	}

	@When("Admin sends HTTPS PUT Request and request Body with missing fields {string}")
	public void admin_sends_https_put_request_and_request_body_with_missing_fields(String loginEmailFields)
			throws Exception {
		data = ExcelReader.readExcelData("User", loginEmailFields);
		requestSpec = requestSpec.body(data.get("Body"));
		String endpoint = data.get("Endpoint").replace("{userId}", userId);
		response = ApiRequest.sendRequest(requestSpec, "PUT", endpoint);
	}

	// DELETE REQUEST

	@When("Admin sends DELETE Request for the LMS API endpoint  and  valid user")
	public void admin_sends_delete_request_for_the_lms_api_endpoint_and_valid_user() throws IOException {
		data = ExcelReader.readExcelData("User", "DeleteAdmin_validUser");
		String endpoint = data.get("Endpoint").replace("{userID}", userId);
		response = ApiRequest.sendRequest(requestSpec, "DELETE", endpoint);
	}

	@When("Admin sends DELETE Request for the LMS API endpoint  and  valid admin user")
	public void admin_sends_delete_request_for_the_lms_api_endpoint_and_valid_admin_user() throws IOException {
		data = ExcelReader.readExcelData("User", "DeleteAdmin_validAdminID");
		String endpoint = data.get("Endpoint").replace("{userID}", AdminUserId);
		response = ApiRequest.sendRequest(requestSpec, "DELETE", endpoint);
	}

	@When("Admin sends DELETE Request for the LMS API endpoint  and  valid staff user")
	public void admin_sends_delete_request_for_the_lms_api_endpoint_and_valid_staff_user() throws IOException {
		data = ExcelReader.readExcelData("User", "DeleteAdmin_validStaffUserID");
		String endpoint = data.get("Endpoint").replace("{userID}", StaffUserId);
		response = ApiRequest.sendRequest(requestSpec, "DELETE", endpoint);
	}

	@When("Admin sends DELETE Request for the LMS API endpoint  and  valid student user")
	public void admin_sends_delete_request_for_the_lms_api_endpoint_and_valid_student_user() throws IOException {
		data = ExcelReader.readExcelData("User", "DeleteAdmin_validStudentUserID");
		String endpoint = data.get("Endpoint").replace("{userID}", StudentUserId);
		response = ApiRequest.sendRequest(requestSpec, "DELETE", endpoint);
	}

	@When("Admin sends DELETE Request for the LMS API endpoint  and  invalid admin ID")
	public void admin_sends_delete_request_for_the_lms_api_endpoint_and_invalid_admin_id() throws Exception {
		data = ExcelReader.readExcelData("User", "DeleteAdmin_InValidAdminID");
		requestSpec = requestSpec.body(data.get("Body"));
		response = ApiRequest.sendRequest(requestSpec, "DELETE", data.get("Endpoint"));
	}

	@Then("Admin receives {int} Not Found Status with not found message")
	public void admin_receives_not_found_status_with_not_found_message(Integer num) throws Exception {
		data = ExcelReader.readExcelData("User", "DeleteAdmin_InValidAdminID");
		String endpoint = data.get("Endpoint");
		String invalidUserID = endpoint.substring(endpoint.lastIndexOf("/") + 1);
		String expectedMessage = "UserID: " + invalidUserID + " does not exist ";
		response.then().log().ifValidationFails().spec(ResponseSpecUtil.status(num)).body("message",
				equalTo(expectedMessage));

	}

}
