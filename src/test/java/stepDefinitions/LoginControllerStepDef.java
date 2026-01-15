package stepDefinitions;

import java.io.IOException;
import java.util.Map;
import org.testng.Assert;
import static io.restassured.RestAssured.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import specs.RequestSpecUtil;
import specs.ResponseSpecUtil;
import utils.ExcelReader;
import utils.GlobalTestData;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class LoginControllerStepDef extends GlobalTestData {

	private Map<String, String> testData;
	private Response response;
	private RequestSpecification requestSpec;

	@Given("Admin has the test data for {string} from Excel with No Auth")
	public void admin_has_the_test_data_for_from_excel_with_no_auth(String scenarioName) throws IOException {
		testData = ExcelReader.readExcelData("AdminLogin", scenarioName);
		RequestSpecUtil.logScenarioName(scenarioName);
		requestSpec = given().spec(RequestSpecUtil.getRequestSpecWithoutAuth()).body(testData.get("Body"));
	}

	@When("Admin sends the post request for User Sign In")
	public void admin_sends_the_post_request_for_user_sign_in() {
		String endpoint = testData.get("Endpoint");
		if (testData.get("ScenarioName").contains("Postrequest_InvalidContenttype")) {
			requestSpec.contentType("text/plain");
		}
		response = requestSpec.when().post(endpoint);
	}

	@Then("Admin should receive the status code as defined in Excel")
	public void admin_should_receive_the_status_code_as_defined_in_excel() {
		int expectedStatusCode = Integer.parseInt(testData.get("ExpectedStatusCode"));
		String currentScenario = testData.get("ScenarioName");
		// Basic status code validation
		response.then().spec(ResponseSpecUtil.status(expectedStatusCode));
		// Content type validation
		String contentType = response.getHeader("Content-Type");

		if (contentType != null && contentType.contains("application/json")) {
			// Performance Check (Ensure response is under 2 seconds)
			response.then().time(lessThan(2000L));
			if (response.getStatusCode() == 200) {
				if (currentScenario != null && currentScenario.trim().equals("Postrequest_Valid credential")) {
					response.then().assertThat()
							.body(matchesJsonSchemaInClasspath("schemas/Login/PostUserSignInResponseSchema.json"));

					String capturedToken = response.jsonPath().getString("token");
					if (capturedToken != null) {
						token = capturedToken;
					}
				}

			}
		} else {
			System.out.println("Skipping JSON validations for Scenario: " + currentScenario);
		}
	}

	@Then("the response should match the expected validation message from Excel")
	public void the_response_should_match_the_expected_validation_message_from_excel() {
		String expectedMsg = testData.get("Expectedmessage");
		String actualBody = response.getBody().asString();
		Assert.assertTrue(actualBody.contains(expectedMsg),
				"\nExpected to find: [" + expectedMsg + "] \nBut returned: [" + actualBody + "]");
	}

	@Given("Admin has the test data for {string} from Excel with Bearer Token")
	public void admin_has_the_test_data_for_from_excel_with_bearer_token(String scenarioName) throws IOException {
		testData = ExcelReader.readExcelData("AdminLogin", scenarioName);
		RequestSpecUtil.logScenarioName(scenarioName);

		if (scenarioName.equalsIgnoreCase("LogoutTokenInvalidation")) {
			String excelToken = testData.get("Token");

			requestSpec = given().spec(RequestSpecUtil.getRequestSpecWithCustomToken(excelToken))
					.body(testData.get("Body"));
		} else {
			requestSpec = given().spec(RequestSpecUtil.getRequestSpec()).body(testData.get("Body"));
		}
	}

	@When("Admin sends a GET request instead of POST for SignIn InvalidMethod")
	public void admin_sends_a_get_request_instead_of_post_for_sign_in_invalid_method() {
		String endpoint = testData.get("Endpoint");
		response = requestSpec.when().get(endpoint);
	}

	@Then("Admin should receive the status code matches with Expected statuscode")
	public void admin_should_receive_the_status_code_matches_with_expected_statuscode() {
		int expected = Integer.parseInt(testData.get("ExpectedStatusCode"));

		int actual = response.getStatusCode();

		Assert.assertEquals(actual, expected, "Status Code Mismatch! Expected " + expected + " but got " + actual);
	}

	@When("Admin sends the post request for ForgotPassword")
	public void admin_sends_the_post_request_for_forgot_password() {
		String endpoint = testData.get("Endpoint");
		String scenario = testData.get("ScenarioName");
		if (testData.get("ScenarioName").contains("ForgotPassword_InvalidContentType")) {
			requestSpec.contentType("text/plain");
		}
		response = requestSpec.when().post(endpoint);
		if (response.getStatusCode() == 200 && scenario.contains("Valid")) {
			response.then().assertThat()
					.body(matchesJsonSchemaInClasspath("schemas/Login/PostForgotPasswordResponseSchema.json"));
		}
	}

	@Then("the response should match the expected validation message")
	public void the_response_should_match_the_expected_validation_message() {
		String expectedMsg = testData.get("Expectedmessage");
		String actualBody = response.getBody().asString();
		Assert.assertTrue(actualBody.contains(expectedMsg),
				"\nExpected to find: [" + expectedMsg + "] \nBut returned: [" + actualBody + "]");
	}

	@When("Admin sends a POST request with Authorization for ResetPassword")
	public void admin_sends_a_post_request_with_authorization_for_reset_password() {
		String endpoint = testData.get("Endpoint");
		String scenario = testData.get("ScenarioName");
		if (testData.get("ScenarioName").contains("ResetPassword_InvalidContentType")) {
			requestSpec.contentType("text/plain");
		}
		response = requestSpec.when().post(endpoint);
		if (response.getStatusCode() == 200 && scenario.contains("Valid")) {
			response.then().assertThat()
					.body(matchesJsonSchemaInClasspath("schemas/Login/PostResetPasswordResponseSchema.json"));
			System.out.println("✅ Reset Password Schema Validated");
		}

	}

	@When("Admin sends the post request for ResetPasswordNoAuth")
	public void admin_sends_the_post_request_for_reset_password_no_auth() {
		String endpoint = testData.get("Endpoint");
		response = requestSpec.when().post(endpoint);

	}

	@When("Admin sends a GET request for Reset Password ResetPasswordInvalidMethod")
	public void admin_sends_a_get_request_for_reset_password_reset_password_invalid_method() {
		String endpoint = testData.get("Endpoint");
		response = requestSpec.when().get(endpoint);
	}

	@When("Admin sends a GET request for Logout")
	public void admin_sends_a_get_request_for_logout() {
		String endpoint = testData.get("Endpoint");

		response = requestSpec.when().get(endpoint);

	}

	@When("Admin sends a POST request instead of GET for logoutInvalidMethod")
	public void admin_sends_a_post_request_instead_of_get_for_logout_invalid_method() {
		String endpoint = testData.get("Endpoint");
		response = requestSpec.when().post(endpoint);
	}

	@When("Admin sends a GET request for Logout noauth")
	public void admin_sends_a_get_request_for_logout_noauth() {
		String endpoint = testData.get("Endpoint");
		response = requestSpec.when().get(endpoint);

	}

}
