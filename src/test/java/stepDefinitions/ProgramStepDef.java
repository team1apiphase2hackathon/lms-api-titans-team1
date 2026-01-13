package stepDefinitions;

import java.io.IOException;
import java.util.Map;

import endpoints.ProgramSendRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import requestPojo.ProgramRequest;
import responsePojo.ProgramResponse;
import specs.RequestSpecUtil;
import utils.ExcelReader;

import static io.restassured.RestAssured.*;

public class ProgramStepDef {


    private static RequestSpecification requestSpec;
    private Map<String, String> data;
    private Response response;
    private static ProgramRequest programInput;


    @Given("Admin has a valid authorization token set")
    public void admin_has_a_valid_authorization_token_set() {
        requestSpec = RequestSpecUtil.getRequestSpec();
    }
    @When("Admin sends POST request to create program with different payload for {string} from dataSheet")
    public void admin_sends_post_request_to_create_program_with_different_payload_for_from_data_sheet(String scenarioNameFromFeature) throws IOException {

        data = ExcelReader.readExcelData("Program", scenarioNameFromFeature);

        if (data != null) {
            String dataSheetTestname = data.get("ScenarioName");

            if (scenarioNameFromFeature.equalsIgnoreCase(dataSheetTestname)) {

                programInput = ProgramSendRequest.createProgramParseData(data.get("Body"));

                requestSpec = given()
                        .spec(requestSpec)
                        .body(programInput);

                String httpMethod = data.get("Method");
                String endPoint = data.get("Endpoint");

                response = requestSpec.
                        when().
                        request(httpMethod, endPoint).
                        then().log().all().extract().response();
            }
        } else {
            throw new RuntimeException("Test data not found for: " + scenarioNameFromFeature);
        }
    }
    @Then("Admin verifies the response payload with expected output from the data sheet")
    public void admin_verifies_the_response_payload_with_expected_output_from_the_data_sheet() {

           int expectedStatus = Integer.parseInt(data.get("ExpectedStatusCode"));
            response.then().statusCode(expectedStatus);
            if(expectedStatus ==201){
                ProgramResponse actualResponse = response.as(ProgramResponse.class);

                String desc = actualResponse.getProgramDescription();
                String name  = actualResponse.getProgramName();
                int id = actualResponse.getProgramId();
                System.out.println("program Description : " + desc);
                System.out.println("program Name : " + name);
                System.out.println("Id : " + id);


                Assert.assertEquals(actualResponse.getProgramDescription(),programInput.getProgramDescription(), "ProgramDescription is not matching");
                Assert.assertEquals(actualResponse.getProgramName(),programInput.getProgramName(),"ProgramName is not matching");
                int createdProgramId = actualResponse.getProgramId();
                Assert.assertTrue(createdProgramId > 0,  "ProgramId should not be negative value");
            }
            else {
                switch (expectedStatus) {

                    case 400:
                        System.out.println("Response code received from actual response after execution: "+response.getStatusCode());
                        String badReq = response.jsonPath().getString("message");
                    Assert.assertNotNull(badReq, "Bad request ");
                    break;
                    case 404:
                        System.out.println("Response code received from actual response after execution: "+response.getStatusCode());
                        System.out.println("Endpoint not found as expected.");
                        break;
                    case 405:
                        System.out.println("Response code received from actual response after execution: "+response.getStatusCode());
                        String methodNotAllowed = response.jsonPath().getString("error");
                        Assert.assertEquals(methodNotAllowed, "Method Not Allowed");
                        break;
                    default:
                        System.out.println("Response code received from actual response after execution: "+response.getStatusCode());
                        System.out.println("Generic status: " + expectedStatus);
                        break;

                }

            }


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
