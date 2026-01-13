package stepDefinitions;

import java.io.IOException;
import java.util.Map;

import endpoints.ProgramSendRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import requestPojo.ProgramRequest;
import responsePojo.ProgramResponse;
import specs.RequestSpecUtil;
import utils.ExcelReader;
import utils.ScenarioContext;

import static io.restassured.RestAssured.*;

public class ProgramStepDef {


    private static RequestSpecification requestSpec;
    private Map<String, String> data;
    private Response response;
    private static ProgramRequest programInput;
    ScenarioContext scenarioContext;

    @Given("Admin sends POST request to create program for LMS")
    public void admin_sends_post_request_to_create_program_for_lms() {

    }

    @When("Admin create POST request with request body")
    public void admin_create_post_request_with_request_body() {

    }

    @Then("Admin receives {int} created status code with response body")
    public void admin_receives_created_status_code_with_response_body(Integer int1) {

    }


    @Given("Admin has a valid authorization token set")
    public void admin_has_a_valid_authorization_token_set() {
        requestSpec = RequestSpecUtil.getRequestSpec();
    }
    @When("Admin sends POST request to create program with different payload for <{string}> from dataSheet")
    public void admin_sends_post_request_to_create_program_with_different_payload_for_from_data_sheet(String testcaseName) throws IOException {
         data = ExcelReader.readExcelData("Program", testcaseName);
         programInput = ProgramSendRequest.createProgramParseData(data.get("body"));
         requestSpec= given().spec(requestSpec)
                 .basePath(data.get("Endpoint"))
                 .body(programInput);
        // String ProgramDescription  = programInput.getProgramDescription();

    }

    @Then("Admin verifies the response payload with expected output from the data sheet")
    public void admin_verifies_the_response_payload_with_expected_output_from_the_data_sheet() {

//        response = requestSpec.when().post().then().log().all().extract().response();;
//        int expectedStatus = Integer.parseInt(data.get("ExpectedStatusCode"));
//        response.then().statusCode(expectedStatus).log().ifValidationFails();
//        ProgramResponse batchResponse = response.as(ProgramResponse.class);

    }


    @When("Admin sends GET request to get program with different payload for <{string}> from dataSheet")
    public void admin_sends_get_request_to_get_program_with_different_payload_for_from_data_sheet(String string) {

    }

    @Then("Admin verifies the response payload with expected output from the data sheet for Get Program")
    public void admin_verifies_the_response_payload_with_expected_output_from_the_data_sheet_for_get_program() {

    }

    @When("Admin sends GET request to get all programs with different payload for <{string}> from dataSheet")
    public void admin_sends_get_request_to_get_all_programs_with_different_payload_for_from_data_sheet(String string) {

    }

    @Then("Admin verifies the response payload with expected output from the data sheet for Get All Programs")
    public void admin_verifies_the_response_payload_with_expected_output_from_the_data_sheet_for_get_all_programs() {

    }

    @When("Admin sends PUT request to update programById with payload for <{string}> using dataSheet")
    public void admin_sends_put_request_to_update_program_by_id_with_payload_for_using_data_sheet(String string) {

    }

    @Then("Admin verifies the response payload with expected output from the data sheet for Update Program ByProgramId")
    public void admin_verifies_the_response_payload_with_expected_output_from_the_data_sheet_for_update_program_by_program_id() {

    }

    @When("Admin sends PUT request to update programByName with payload for <{string}> using dataSheet")
    public void admin_sends_put_request_to_update_program_by_name_with_payload_for_using_data_sheet(String string) {

    }

    @Then("Admin verifies the response payload with expected output from the data sheet for Update Program ByProgramName")
    public void admin_verifies_the_response_payload_with_expected_output_from_the_data_sheet_for_update_program_by_program_name() {

    }

    @When("Admin sends DELETE request to delete programByName with payload for <{string}> using dataSheet")
    public void admin_sends_delete_request_to_delete_program_by_name_with_payload_for_using_data_sheet(String string) {

    }

    @Then("Admin verifies the response payload with expected output from the data sheet for Delete Program ByProgramName")
    public void admin_verifies_the_response_payload_with_expected_output_from_the_data_sheet_for_delete_program_by_program_name() {

    }

    @When("Admin sends DELETE request to delete programById with payload for {string} using dataSheet")
    public void admin_sends_delete_request_to_delete_program_by_id_with_payload_for_using_data_sheet(String string) {

    }

    @Then("Admin verifies the response payload with expected output from the data sheet for Delete Program ByProgramId")
    public void admin_verifies_the_response_payload_with_expected_output_from_the_data_sheet_for_delete_program_by_program_id() {

    }


    @Given("Admin bearer token in set to empty")
    public void admin_bearer_token_in_set_to_empty() {

    }


    @When("admin send HTTP method to access endpoint for testcase {string} without authorization token")
    public void admin_send_http_method_to_access_endpoint_for_testcase_without_authorization_token(String string) {

    }

    @Then("Admin verifies the response status code with expected output from data sheet")
    public void admin_verifies_the_response_status_code_with_expected_output_from_data_sheet() {

    }


    @Given("Admin bearer token in set to invalid")
    public void admin_bearer_token_in_set_to_invalid() {

    }

    @When("admin send HTTP method to access endpoint for testcase {string} with invalid authorization token")
    public void admin_send_http_method_to_access_endpoint_for_testcase_with_invalid_authorization_token(String string) {

    }


}
