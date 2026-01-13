package stepDefinitions;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.hamcrest.Matchers;
import org.hamcrest.core.Every;
import org.testng.Assert;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import requestPojo.ProgramBatchRequest;
import responsePojo.ProgramBatchResponse;
import specs.RequestSpecUtil;
import specs.ResponseSpecUtil;
import utils.ExcelReader;
import utils.GlobalTestData;
import utils.ScenarioContext;


public class ProgramBatchStepDef extends GlobalTestData{
	private static RequestSpecification requestSpec;
	private Response response;
	private static Map<String,String> data;
	ScenarioContext scenarioContext;
	
	public ProgramBatchStepDef(ScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;
	}
	
	//Create Batch
	@Given("Admin create POST request with valid {string} format and {string}")
	public void admin_create_post_request_with_valid_format_and(String batch_name, String batch_description) throws IOException {
		data = ExcelReader.readExcelData("Batch", "CreateBatch_Valid_Request");
		ObjectMapper mapper = new ObjectMapper();
        ProgramBatchRequest batchData = mapper.readValue(data.get("Body"), ProgramBatchRequest.class);
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
		response = requestSpec.when().log().all().post();
	}

	@Then("Admin receives created status with response body")
	public void admin_receives_created_status_with_response_body()  {
		response.then().log().all()
					.statusCode(Integer.parseInt(data.get("ExpectedStatusCode")))
					.body(matchesJsonSchemaInClasspath("schemas/batch/CreateBatchResponseSchema.json"));
		
		ProgramBatchResponse batchResponse = response.as(ProgramBatchResponse.class);
		if (batchIds.size() == 0) {
			batchId = batchResponse.getBatchId();
			batchName = batchResponse.getBatchName();
		}
		batchIds.add(batchResponse.getBatchId());
		String expectedBatchName = (String)scenarioContext.getContext("BATCH_NAME");
		Assert.assertEquals(batchResponse.getBatchName(), expectedBatchName, "Batch Name doesn't match in response");
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

	//Get ALL Batches
	@Given("Admin create GET request with valid endpoint")
	public void admin_create_get_request_with_valid_endpoint() throws IOException {
		data = ExcelReader.readExcelData("Batch", "GetALLBatches_Valid");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .basePath(data.get("Endpoint"));
	}

	@When("Admin sends GET request to retrieve all batches")
	public void admin_sends_get_request_to_retrieve_all_batches() {
		response = requestSpec.when().log().all().get();
	}

	@Then("Admin receives success code with response body")
	public void admin_receives_success_code_with_response_body() {
		int expectedStatusCode = Integer.parseInt(data.get("ExpectedStatusCode"));
		int batchIdCount = 0;
		response.then().log().all()
			.statusCode(expectedStatusCode)
			.body(matchesJsonSchemaInClasspath("schemas/batch/GetAllBatchesResponseSchema.json"))
			.body("", Matchers.instanceOf(List.class))
			.body("size()", Matchers.greaterThan(0));
		JsonPath json = response.jsonPath();
		List<Map<String, Object>> array = json.getList("$");
		for (int i = 0; i < array.size(); i++) {
			Map<String, Object> element = array.get(i);
			int resBatch = (Integer)element.get("batchId");
			if (batchIds.contains(resBatch)) {
				batchIdCount++;
			}
		}
		Assert.assertEquals(batchIdCount, batchIds.size()); 
	}

	@Given("Admin create GET request with invalid endpoint")
	public void admin_create_get_request_with_invalid_endpoint() throws IOException {
		data = ExcelReader.readExcelData("Batch", "GetALLBatches_InvalidEndpoint");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .basePath(data.get("Endpoint"));
	}
	
	@Given("Admin create GET request with no authentication")
	public void admin_create_get_request_with_no_authentication() throws IOException {
		data = ExcelReader.readExcelData("Batch", "GetALLBatches_NoAuth");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpecWithoutAuth())
                .basePath(data.get("Endpoint"));
	}
	
	//Get Batch by batchId
	@Given("Admin create GET request with valid batchId")
	public void admin_create_get_request_with_valid_batch_id() throws IOException {
		data = ExcelReader.readExcelData("Batch", "GetBatchById_Valid_BatchId");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .pathParam("batchId", batchId)
                .basePath(data.get("Endpoint"));
	}

	@When("Admin sends GET request to retrieve the batch")
	public void admin_sends_get_request_to_retrieve_the_batch() {
		response = requestSpec.when().log().all().get();
	}

	@Then("Admin receives success code with GET response body")
	public void admin_receives_success_code_with_get_response_body() {
		int expectedStatusCode = Integer.parseInt(data.get("ExpectedStatusCode"));
		response.then().log().all()
				.statusCode(expectedStatusCode)
				.body(matchesJsonSchemaInClasspath("schemas/batch/GetBatchByIdResponseSchema.json"));
		JsonPath json = response.jsonPath();
		Assert.assertEquals(json.getInt("batchId"), batchId, "Batch Id doesn't match");
	}

	@Given("Admin create GET request with invalid batchId")
	public void admin_create_get_request_with_invalid_batch_id() throws IOException {
		data = ExcelReader.readExcelData("Batch", "GetBatchById_invalidBatchId");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .pathParam("batchId", "999999")
                .basePath(data.get("Endpoint"));
	}
	
	@Given("Admin create GET request to retrieve batch with invalid endpoint")
	public void admin_create_get_request_to_retrieve_batch_with_invalid_endpoint() throws IOException {
		data = ExcelReader.readExcelData("Batch", "GetBatchById_Invalid_Endpoint");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .pathParam("batchId", batchId)
                .basePath(data.get("Endpoint"));
	}
	
	@Given("Admin create GET request to retrieve batch without authorization")
	public void admin_create_get_request_to_retrieve_batch_without_authorization() throws IOException {
		data = ExcelReader.readExcelData("Batch", "GetBatchById_NoAuth");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpecWithoutAuth())
                .pathParam("batchId", batchId)
                .basePath(data.get("Endpoint"));
	}
	
	//Get Batch By Batch Name
	@Given("Admin create GET request to retrieve batch with valid batch name")
	public void admin_create_get_request_to_retrieve_batch_with_valid_batch_name() throws IOException {
		data = ExcelReader.readExcelData("Batch", "GetBatchByName_Valid_BatchName");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .pathParam("batchName", batchName)
                .basePath(data.get("Endpoint"));
	}
	
	@Then("Admin receives success code with GET response body having given batch name")
	public void admin_receives_success_code_with_get_response_body_having_given_batch_name() {
		int expectedStatusCode = Integer.parseInt(data.get("ExpectedStatusCode"));
		response.then().log().all()
				.statusCode(expectedStatusCode)
				.body(matchesJsonSchemaInClasspath("schemas/batch/GetBatchByNameResponseSchema.json"));
		JsonPath json = response.jsonPath();
		Assert.assertEquals(json.getString("batchName"), batchName, "Batch Name doesn't match");
	}
	
	@Given("Admin create GET request to retrieve batch with invalid batch name")
	public void admin_create_get_request_to_retrieve_batch_with_invalid_batch_name() throws IOException {
		data = ExcelReader.readExcelData("Batch", "GetBatchByName_Invalid_BatchName");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .pathParam("batchName", "SDET")
                .basePath(data.get("Endpoint"));
	}
	
	@Given("Admin create GET request to retrieve batch by batch name without auth")
	public void admin_create_get_request_to_retrieve_batch_by_batch_name_without_auth() throws IOException {
		data = ExcelReader.readExcelData("Batch", "GetBatchByName_NoAuth");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpecWithoutAuth())
                .pathParam("batchName", batchName)
                .basePath(data.get("Endpoint"));
	}
	
	@Given("Admin create GET request to retrieve batch by batch name with invalid endpoint")
	public void admin_create_get_request_to_retrieve_batch_by_batch_name_with_invalid_endpoint() throws IOException {
		data = ExcelReader.readExcelData("Batch", "GetBatchByName_Invalid_Endpoint");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .pathParam("batchName", batchName)
                .basePath(data.get("Endpoint"));
	}
	
	//Get Batch By ProgramId
	@Given("Admin create GET request to retrieve batch with valid programId")
	public void admin_create_get_request_to_retrieve_batch_with_valid_program_id() throws IOException {
		data = ExcelReader.readExcelData("Batch", "GetBatchByProgram_Valid_ProgramId");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .pathParam("programId", programId)
                .basePath(data.get("Endpoint"));
	}

	@Then("Admin receives success code with GET response body having given programId")
	public void admin_receives_success_code_with_get_response_body_having_given_program_id() {
		int expectedStatusCode = Integer.parseInt(data.get("ExpectedStatusCode"));
		response.then().log().all()
				.statusCode(expectedStatusCode)
				.body(matchesJsonSchemaInClasspath("schemas/batch/GetBatchByProgramResponseSchema.json"))
				.body("", Matchers.instanceOf(List.class))
				.body("size()", Matchers.greaterThan(0))
				.body("programId", Every.everyItem(Matchers.equalTo(programId)));		
	}

	@Given("Admin create GET request to retrieve batch with invalid programId")
	public void admin_create_get_request_to_retrieve_batch_with_invalid_program_id() throws IOException {
		data = ExcelReader.readExcelData("Batch", "GetBatchByProgram_Invalid_ProgramId");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .pathParam("programId", 100)
                .basePath(data.get("Endpoint"));
	}

	@Given("Admin create GET request to retrieve batch by programId without auth")
	public void admin_create_get_request_to_retrieve_batch_by_program_id_without_auth() throws IOException {
		data = ExcelReader.readExcelData("Batch", "GetBatchByProgram_NoAuth");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpecWithoutAuth())
                .pathParam("programId", programId)
                .basePath(data.get("Endpoint"));
	}

	@Given("Admin create GET request to retrieve batch by programId with invalid endpoint")
	public void admin_create_get_request_to_retrieve_batch_by_program_id_with_invalid_endpoint() throws IOException {
		data = ExcelReader.readExcelData("Batch", "GetBatchByProgram_Invalid_Endpoint");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .pathParam("programId", programId)
                .basePath(data.get("Endpoint"));
	}


}
