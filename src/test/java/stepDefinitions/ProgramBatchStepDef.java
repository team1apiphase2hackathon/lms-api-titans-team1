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
import requestPojo.CreateBatchRequest;
import requestPojo.PutBatchRequest;
import responsePojo.CreateBatchResponse;
import responsePojo.PutBatchResponse;
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
        CreateBatchRequest batchData = mapper.readValue(data.get("Body"), CreateBatchRequest.class);
        batchData.setBatchName(batch_name);
        batchData.setBatchDescription(batch_description);
        batchData.setProgramId(programId);
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
		
		CreateBatchResponse batchResponse = response.as(CreateBatchResponse.class);
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

	@Given("Admin create POST request with invalid input for {string} from excel sheet")
	public void admin_create_post_request_with_invalid_input_for_from_excel_sheet(String scenario) throws IOException {
		data = ExcelReader.readExcelData("Batch", scenario);
		if (scenario.contains("NoAuth")) {
			requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpecWithoutAuth())
                .basePath(data.get("Endpoint"))
                .body(data.get("Body")); 
		}
		else {
			requestSpec = given()
	                .spec(RequestSpecUtil.getRequestSpec())
	                .basePath(data.get("Endpoint"))
	                .body(data.get("Body")); 
		}
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

	@Given("Admin create GET request by BatchId with invalid input for {string} from excel sheet")
	public void admin_create_get_request_by_batch_id_with_invalid_input_for_from_excel_sheet(String scenario) throws IOException {
		data = ExcelReader.readExcelData("Batch", scenario);
		if (scenario.contains("NoAuth")) {
			requestSpec = given()
	                .spec(RequestSpecUtil.getRequestSpecWithoutAuth())
	                .pathParam("batchId", batchId)
	                .basePath(data.get("Endpoint"));
		}
		else if (scenario.contains("invalidBatchId")) {
			requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .basePath(data.get("Endpoint"));
		}
		else {
			requestSpec = given()
	                .spec(RequestSpecUtil.getRequestSpec())
	                .pathParam("batchId", batchId)
	                .basePath(data.get("Endpoint"));
		}
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
	
	@Given("Admin create GET request by BatchName with invalid input for {string} from excel sheet")
	public void admin_create_get_request_by_batch_name_with_invalid_input_for_from_excel_sheet(String scenario) throws IOException {
		data = ExcelReader.readExcelData("Batch", scenario);
		if (scenario.contains("NoAuth")) {
			requestSpec = given()
	                .spec(RequestSpecUtil.getRequestSpecWithoutAuth())
	                .pathParam("batchName", batchName)
	                .basePath(data.get("Endpoint"));
		}
		else if (scenario.contains("Invalid_BatchName")) {
			requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .basePath(data.get("Endpoint"));
		}
		else {
			requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .pathParam("batchName", batchName)
                .basePath(data.get("Endpoint"));
		}
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

	@Given("Admin create GET request by programId with invalid input for scenario {string} from excel sheet")
	public void admin_create_get_request_by_program_id_with_invalid_input_for_scenario_from_excel_sheet(String scenario) throws IOException {
		data = ExcelReader.readExcelData("Batch", scenario);
		if (scenario.contains("NoAuth")) {
			requestSpec = given()
	                .spec(RequestSpecUtil.getRequestSpecWithoutAuth())
	                .pathParam("programId", programId)
	                .basePath(data.get("Endpoint"));
		}
		else if (scenario.contains("Invalid_ProgramId")) {
			requestSpec = given()
	                .spec(RequestSpecUtil.getRequestSpec())
	                .basePath(data.get("Endpoint"));
		}
		else {
			requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .pathParam("programId", programId)
                .basePath(data.get("Endpoint"));
		}
	}

	//PUT Request to update batch
	@Given("Admin create PUT request to update batch with valid batchId for scenario {string}")
	public void admin_create_put_request_to_update_batch_with_valid_batch_id_for_scenario(String scenario) throws IOException {
		data = ExcelReader.readExcelData("Batch", scenario);
		ObjectMapper mapper = new ObjectMapper();
        PutBatchRequest batchData = mapper.readValue(data.get("Body"), PutBatchRequest.class);
        if (!scenario.contains("UpdateBatchName")) {
        	batchData.setBatchName(batchName);
        }
        batchData.setProgramId(programIdList.get(programIdList.size()-1));
        batchData.setProgramName(programName);
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .pathParam("batchId", batchId)
                .basePath(data.get("Endpoint"))
                .body(batchData);
		scenarioContext.setContext("BATCH_NAME", batchData.getBatchName());
		scenarioContext.setContext("BATCH_STATUS", batchData.getBatchStatus());
		scenarioContext.setContext("BATCH_NOOFCLASSES", batchData.getBatchNoOfClasses());
		scenarioContext.setContext("PROGRAM_ID", batchData.getProgramId());
	}

	@When("Admin sends PUT request to update the batch")
	public void admin_sends_put_request_to_update_the_batch() {
		response = requestSpec.when().log().all().put();
	}

	@Then("Admin received success code with updated ProgramId in response")
	public void admin_received_success_code_with_updated_program_id_in_response() {
		int expectedStatusCode = Integer.parseInt(data.get("ExpectedStatusCode"));
		response.then().log().all()
				.statusCode(expectedStatusCode)
				.body(matchesJsonSchemaInClasspath("schemas/batch/PutBatchByIdResponseSchema.json"));
		
		PutBatchResponse batchResponse = response.getBody().as(PutBatchResponse.class);
		programId = (int) scenarioContext.getContext("PROGRAM_ID");
		Assert.assertEquals(batchResponse.getProgramId(), programId);
	}
	
	@Then("Admin received success code with updated batchName in response")
	public void admin_received_success_code_with_updated_batch_name_in_response() {
		int expectedStatusCode = Integer.parseInt(data.get("ExpectedStatusCode"));
		response.then().log().all()
				.statusCode(expectedStatusCode)
				.body(matchesJsonSchemaInClasspath("schemas/batch/PutBatchByIdResponseSchema.json"));
		
		PutBatchResponse batchResponse = response.getBody().as(PutBatchResponse.class);
		batchName = (String)scenarioContext.getContext("BATCH_NAME");
		Assert.assertEquals(batchResponse.getBatchName(), batchName);
	}
	
	@Then("Admin received success code with updated batchStatus in response")
	public void admin_received_success_code_with_updated_batch_status_in_response() {
		int expectedStatusCode = Integer.parseInt(data.get("ExpectedStatusCode"));
		response.then().log().all()
				.statusCode(expectedStatusCode)
				.body(matchesJsonSchemaInClasspath("schemas/batch/PutBatchByIdResponseSchema.json"));
		
		PutBatchResponse batchResponse = response.getBody().as(PutBatchResponse.class);
		Assert.assertEquals(batchResponse.getBatchStatus(), (String)scenarioContext.getContext("BATCH_STATUS"));
	}
	
	@Then("Admin received success code with updated batchNoOfClasses in response")
	public void admin_received_success_code_with_updated_batch_no_of_classes_in_response() {
		int expectedStatusCode = Integer.parseInt(data.get("ExpectedStatusCode"));
		response.then().log().all()
				.statusCode(expectedStatusCode)
				.body(matchesJsonSchemaInClasspath("schemas/batch/PutBatchByIdResponseSchema.json"));
		
		PutBatchResponse batchResponse = response.getBody().as(PutBatchResponse.class);
		Assert.assertEquals(batchResponse.getBatchNoOfClasses(), (int)scenarioContext.getContext("BATCH_NOOFCLASSES"));
	}
	
	@Given("Admin create PUT request with invalid input for each {string} from excel sheet")
	public void admin_create_put_request_with_invalid_input_for_each_from_excel_sheet(String scenario) throws IOException {
		data = ExcelReader.readExcelData("Batch", scenario);
		if (scenario.contains("Invalid_BatchId")) {
			requestSpec = given()
	                .spec(RequestSpecUtil.getRequestSpec())
	                .basePath(data.get("Endpoint"))
	                .body(data.get("Body"));
		}
		else if (scenario.contains("NoAuth")) {
			requestSpec = given()
	                .spec(RequestSpecUtil.getRequestSpecWithoutAuth())
	                .pathParam("batchId", batchId)
	                .basePath(data.get("Endpoint"))
	                .body(data.get("Body"));
		}
		else {
			requestSpec = given()
					.spec(RequestSpecUtil.getRequestSpec())
					.pathParam("batchId", batchId)
					.basePath(data.get("Endpoint"))
					.body(data.get("Body"));
		}
	}
	
	@Given("Admin create DELETE request by BatchId with invalid input for scenario {string} from excel sheet")
	public void admin_create_delete_request_by_batch_id_with_invalid_input_for_scenario_from_excel_sheet(String scenario) throws IOException {
		data = ExcelReader.readExcelData("Batch", scenario);
		if (scenario.contains("NoAuth")) {
			requestSpec = given()
	                .spec(RequestSpecUtil.getRequestSpecWithoutAuth())
	                .pathParam("batchId", batchId)
	                .basePath(data.get("Endpoint"));
		}
		else if (scenario.contains("Invalid_BatchId")) {
			requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .basePath(data.get("Endpoint"));
		}
		else {
			requestSpec = given()
	                .spec(RequestSpecUtil.getRequestSpec())
	                .pathParam("batchId", batchId)
	                .basePath(data.get("Endpoint"));
		}
	}

	@When("Admin sends DELETE request to delete the batch")
	public void admin_sends_delete_request_to_delete_the_batch() {
		response = requestSpec.when().log().all().delete();
	}
	
	@Given("Admin create DELETE request with valid batchId")
	public void admin_create_delete_request_with_valid_batch_id() throws IOException {
		data = ExcelReader.readExcelData("Batch", "DeleteBatchById_Valid_BatchId");
		requestSpec = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .pathParam("batchId", batchId)
                .basePath(data.get("Endpoint"));
 
	}

	@Then("Admin receives success code with deleted message")
	public void admin_receives_success_code_with_deleted_message() {
		
	    Assert.assertTrue(response.asString().contains(data.get("ExpectedMessage")), "Expected message doesn't match");
	}

	@When("Admin sends GET request to retrieve deleted batch with Id")
	public void admin_sends_get_request_to_retrieve_deleted_batch_with_id() throws IOException {
		data = ExcelReader.readExcelData("Batch", "GetBatchById_Deleted_BatchId");
		response = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .pathParam("batchId", batchId)
                .basePath(data.get("Endpoint"))
                .when().log().all().get();
	}
	
	@Then("Admin receives success code with GET response body for deleted batch")
	public void admin_receives_success_code_with_get_response_body_for_deleted_batch() {
		int expectedStatusCode = Integer.parseInt(data.get("ExpectedStatusCode"));
		response.then().log().all()
				.statusCode(expectedStatusCode);
		JsonPath json = response.jsonPath();
		Assert.assertEquals(json.getInt("batchId"), batchId, "Batch Id doesn't match");
		Assert.assertEquals(json.getString("batchName"), batchName, "Batch Name doesn't match");
		Assert.assertEquals(json.getString("batchStatus"), "Inactive");
		
	}

	@When("Admin sends GET request to retrieve deleted batch with name")
	public void admin_sends_get_request_to_retrieve_deleted_batch_with_name() throws IOException {
		data = ExcelReader.readExcelData("Batch", "GetBatchByName_Deleted_BatchId");
		response = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .pathParam("batchName", batchName)
                .basePath(data.get("Endpoint"))
                .when().log().all().get();
	}
	
	@When("Admin sends PUT request to update deleted batch status")
	public void admin_sends_put_request_to_update_deleted_batch_status() throws IOException {
		data = ExcelReader.readExcelData("Batch", "PutBatchById_Deleted_BatchId");
		ObjectMapper mapper = new ObjectMapper();
        PutBatchRequest batchData = mapper.readValue(data.get("Body"), PutBatchRequest.class);
        batchData.setBatchName(batchName);
        batchData.setProgramId(programId);
        batchData.setProgramName(programName);
		response = given()
                .spec(RequestSpecUtil.getRequestSpec())
                .pathParam("batchId", batchId)
                .basePath(data.get("Endpoint"))
                .body(batchData)
                .when().log().all().put();
	}

	@Then("Admin receives success code with Active batch status in the response body")
	public void admin_receives_success_code_with_active_batch_status_in_the_response_body() {
		int expectedStatusCode = Integer.parseInt(data.get("ExpectedStatusCode"));
		response.then().log().all()
				.statusCode(expectedStatusCode)
				.body(matchesJsonSchemaInClasspath("schemas/batch/PutBatchByIdResponseSchema.json"));
		
		PutBatchResponse batchResponse = response.getBody().as(PutBatchResponse.class);
		Assert.assertEquals(batchResponse.getBatchStatus(), "Active");
	}


}
