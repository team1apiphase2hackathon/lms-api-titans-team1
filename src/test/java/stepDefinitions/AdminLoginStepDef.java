package stepDefinitions;

import java.io.IOException;
import java.util.Map;
import org.testng.Assert;
import static io.restassured.RestAssured.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import specs.RequestSpecUtil;
import utils.ExcelReader;
import utils.TestDataUtil;

public class AdminLoginStepDef {

	private Map<String, String> testData;
	private Response response;
	private RequestSpecification requestSpec;

	@Given("Admin has the test data for {string} from Excel with No Auth")
	public void admin_has_the_test_data_for_from_excel_with_no_auth(String scenarioName) throws IOException {
		testData = ExcelReader.readExcelData("AdminLogin", scenarioName);

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

		response.then().log().all().statusCode(expectedStatusCode);

		if (response.getStatusCode() == 200) {
			String capturedToken = response.jsonPath().getString("token");
			if (capturedToken != null) {
				TestDataUtil.setToken(capturedToken);
			}
		}

	}

	@Then("the response should match the expected validation message from Excel")
	public void the_response_should_match_the_expected_validation_message_from_excel() {
		String expectedMsg = testData.get("Expectedmessage");
		String actualBody = response.getBody().asString();
		Assert.assertTrue(actualBody.contains(expectedMsg),
				"\nExpected to find: [" + expectedMsg + "] \nBut returned: [" + actualBody + "]");
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

}
