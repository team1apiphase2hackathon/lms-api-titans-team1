package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.Map;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import specs.RequestSpecUtil;
import utils.ExcelReader;


public class ProgramBatchStepDef {
	static RequestSpecification requestSpec;
	private Response response;
	private static Map<String,String> data;

	@Given("Admin create request spec with base URL and authentication")
	public void admin_create_request_spec_with_base_url_and_authentication() {
		requestSpec = RequestSpecUtil.getRequestSpec();
	}
	
	@Given("Admin create POST request with valid batch name format")
	public void admin_create_post_request_with_valid_batch_name_format() throws IOException {
		data = ExcelReader.readExcelData("Batch", "CreateBatch_Valid_batchName");
        System.out.println("-------------------------------------");
        System.out.println(data.get("Body"));
        requestSpec = given()
        			.spec(requestSpec)
        			.basePath(data.get("Endpoint"))
        			.body(data.get("Body"));			
	}

	@When("Admin sends HTTPS request with valid endpoint")
	public void admin_sends_https_request_with_valid_endpoint() {
	    response = requestSpec.when().post().then().log().all().extract().response();
	}

	@Then("Admin receives success code with response body")
	public void admin_receives_success_code_with_response_body() {
		response.then().statusCode(Integer.parseInt(data.get("ExpectedStatusCode")));
	}


}
