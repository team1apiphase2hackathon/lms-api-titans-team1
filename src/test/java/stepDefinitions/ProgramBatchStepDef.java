package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.Map;

import org.apache.poi.util.StringUtil;
import org.testng.Assert;

import endpoints.ProgramBatchUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import requestPojo.ProgramBatchRequest;
import responsePojo.ProgramBatchResponse;
import specs.RequestSpecUtil;
import specs.ResponseSpecUtil;
import utils.ExcelReader;
import utils.ScenarioContext;


public class ProgramBatchStepDef {
	private static RequestSpecification requestSpec;
	private Response response;
	private static Map<String,String> data;
	private static ProgramBatchRequest batchData;
	ScenarioContext scenarioContext;
	
	public ProgramBatchStepDef(ScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	@Given("Admin create POST request with valid {string} format and {string}")
	public void admin_create_post_request_with_valid_format_and(String batch_name, String batch_description) throws IOException {
		data = ExcelReader.readExcelData("Batch", "CreateBatch_Valid_Request");
        batchData = ProgramBatchUtil.createBatchParseData(data.get("Body"));
        batchData.setBatchName(batch_name);
        batchData.setBatchDescription(batch_description);
        requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .basePath(data.get("Endpoint"))
                .body(batchData);  
        scenarioContext.setContext("BATCH_NAME", batch_name);
	}


	@When("Admin sends POST request to create program batch")
	public void admin_sends_post_request_to_create_program_batch() {
		response = requestSpec.when().post().then().log().all().extract().response();
	}

	@Then("Admin receives created status with response body")
	public void admin_receives_created_status_with_response_body()  {
		response.then().log().all().statusCode(Integer.parseInt(data.get("ExpectedStatusCode")));
		ProgramBatchResponse batchResponse = response.as(ProgramBatchResponse.class);
		String actualBatchName = batchResponse.getBatchName();
		String expectedBatchName = (String)scenarioContext.getContext("BATCH_NAME");
		Assert.assertEquals(actualBatchName, expectedBatchName, "Batch Name doesn't match in response");;
	}
	
	@Then("Admin receives expected status code with error message")
	public void admin_receives_expected_status_code_with_error_message() {
		response.then().log().all().statusCode(Integer.parseInt(data.get("ExpectedStatusCode")));
		String message = ResponseSpecUtil.getResponseMessage(response);
		String expectedMessage = (String)data.get("ExpectedMessage");
		System.out.println(message);
		if (message != null && !message.isBlank()) {
			Assert.assertTrue(message.contains(expectedMessage), "Error message doesn't match");
		}
	}

	@Given("Admin create POST request with valid request body and no authorization")
	public void admin_create_post_request_with_valid_request_body_and_no_authorization() throws IOException {
		data = ExcelReader.readExcelData("Batch", "CreateBatch_NoAuth");
        requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpecWithoutAuth())
                .basePath(data.get("Endpoint"))
                .body(data.get("Body")); 
	}

	@Given("Admin create POST request with existing batch name")
	public void admin_create_post_request_with_existing_batch_name() throws IOException {
		data = ExcelReader.readExcelData("Batch", "CreateBatch_Existing_Batch");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .basePath(data.get("Endpoint"))
                .body(data.get("Body")); 
	}

	@Given("Admin create POST request with missing mandataory fields")
	public void admin_create_post_request_with_missing_mandataory_fields() throws IOException {
		data = ExcelReader.readExcelData("Batch", "CreateBatch_Missing_Mandatory_Fields");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .basePath(data.get("Endpoint"))
                .body(data.get("Body")); 
	}

	@Given("Admin create POST request with invalid endpoint")
	public void admin_create_post_request_with_invalid_endpoint() throws IOException {
		data = ExcelReader.readExcelData("Batch", "CreateBatch_invalid_endpoint");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .basePath(data.get("Endpoint"))
                .body(data.get("Body")); 
	}

	@Given("Admin create POST request with missing additional fields")
	public void admin_create_post_request_with_missing_additional_fields() throws IOException {
		data = ExcelReader.readExcelData("Batch", "CreateBatch_Missing_Additional_Fields");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .basePath(data.get("Endpoint"))
                .body(data.get("Body")); 
	}

	@Given("Admin create POST request with invalid data in request body")
	public void admin_create_post_request_with_invalid_data_in_request_body() throws IOException {
		data = ExcelReader.readExcelData("Batch", "CreateBatch_invalid_data");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .basePath(data.get("Endpoint"))
                .body(data.get("Body")); 
	}

	@Given("Admin create POST request with inactive program Id")
	public void admin_create_post_request_with_inactive_program_id() throws IOException {
		data = ExcelReader.readExcelData("Batch", "CreateBatch_inactive_programId");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .basePath(data.get("Endpoint"))
                .body(data.get("Body")); 
	}


}
