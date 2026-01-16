package stepDefinitions;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;

import httpRequest.ProgramSendRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.Assert;
import requestPojo.ProgramRequest;
import responsePojo.ProgramResponse;
import specs.RequestSpecUtil;
import utils.ExcelReader;
import utils.GlobalTestData;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.assertTrue;

public class ProgramStepDef extends GlobalTestData {
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
        String dataSheetTestname = data.get("ScenarioName");
        String body = data.get("Body");

        if (data != null) {
            String dataSheetTestName = data.get("ScenarioName");
            if (scenarioNameFromFeature.equalsIgnoreCase(dataSheetTestName)) {
                programInput = ProgramSendRequest.createProgramParseData(data.get("Body"));

                requestSpec = given()
                        .spec(requestSpec)
                        .body(programInput);

                String httpMethod = data.get("Method");
                String endPoint = data.get("Endpoint");

                response = requestSpec.log().all().
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
        response.then().log().all().statusCode(expectedStatus);
        if (expectedStatus == 201) {
            //Validate schema
            response.then().log().all().assertThat().body(matchesJsonSchemaInClasspath("schemas/Program/CreateProgramResponseSchema.json"));

            //Map response to POJO
            ProgramResponse actualResponse = response.as(ProgramResponse.class);

            String desc = actualResponse.getProgramDescription();
            String programName = actualResponse.getProgramName();
            GlobalTestData.programName = programName;
            GlobalTestData.programNameList.add(programName);

            if (GlobalTestData.programId == 0) {
                GlobalTestData.programId = actualResponse.getProgramId();
            } else {
                GlobalTestData.programIdList.add(actualResponse.getProgramId());
            }

            Assert.assertEquals(actualResponse.getProgramDescription(), programInput.getProgramDescription(), "ProgramDescription is not matching");
            Assert.assertEquals(actualResponse.getProgramName(), programInput.getProgramName(), "ProgramName is not matching");
            int createdProgramId = actualResponse.getProgramId();
            assertTrue(createdProgramId > 0, "ProgramId should not be negative value");
        } else {
            switch (expectedStatus) {

                case 400:
                    String badReq = response.jsonPath().getString("message");
                    Assert.assertNotNull(badReq, "Bad request ");
                    break;
                case 404:
                    System.out.println("Endpoint not found as expected.");
                    break;
                case 405:
                    String methodNotAllowed = response.jsonPath().getString("error");
                    Assert.assertEquals(methodNotAllowed, "Method Not Allowed");
                    break;
                default:
                    break;
            }
        }
    }

    @When("Admin sends GET request to get program with different payload for {string} from dataSheet")
    public void admin_sends_get_request_to_get_program_with_different_payload_for_from_data_sheet(String scenarioNameFromFeature) throws IOException {
        String scenario = scenarioNameFromFeature;
        data = ExcelReader.readExcelData("Program", scenario);
        if (data != null) {
            String dataSheetTestname = data.get("ScenarioName");

            if (scenario.equalsIgnoreCase(dataSheetTestname)) {

                requestSpec = given()
                        .spec(requestSpec);

                String httpMethod = data.get("Method");
                String endPoint = data.get("Endpoint");
                if (endPoint.contains("{programId}")) {
                    endPoint = endPoint.replace("{programId}", String.valueOf(GlobalTestData.programId));
                }
                response = requestSpec.log().all().
                        when().
                        request(httpMethod, endPoint).
                        then().log().all().extract().response();

            }
        } else {
            throw new RuntimeException("Test data not found for: " + scenario);
        }
    }

    @Then("Admin verifies the response payload with expected output from the data sheet for Get Program")
    public void admin_verifies_the_response_payload_with_expected_output_from_the_data_sheet_for_get_program() {
        int expectedStatus = Integer.parseInt(data.get("ExpectedStatusCode"));
        String expectedMessage = data.get("ExpectedMessage");
        response.then().log().all().statusCode(expectedStatus);
        if (expectedStatus == 200) {
            response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/Program/GetProgramByProgramIdResponseSchema.json"));
            // Assert that the ID in the response matches what we stored
            int actualId = response.jsonPath().getInt("programId");
            Assert.assertEquals(actualId, GlobalTestData.programId);
        } else if (expectedStatus == 404) {
            String actualMessage = response.jsonPath().getString("errorMessage");
            response.then().statusLine(Matchers.containsString(expectedMessage));
            System.out.println("Verified 404: Received expected error message.");
        }
    }

    @When("Admin sends GET request to get all programs with different payload for {string} from dataSheet")
    public void admin_sends_get_request_to_get_all_programs_with_different_payload_for_from_data_sheet(String scenarioNameFromFeature) throws IOException {

        String scenarioName = scenarioNameFromFeature;
        data = ExcelReader.readExcelData("Program", scenarioName);

        if (data != null) {
            String dataSheetTestname = data.get("ScenarioName");

            if (scenarioNameFromFeature.equalsIgnoreCase(dataSheetTestname)) {
                requestSpec = given()
                        .spec(requestSpec);

                String httpMethod = data.get("Method");
                String endPoint = data.get("Endpoint");

                response = requestSpec.log().all().
                        when().
                        request(httpMethod, endPoint).
                        then().log().all().extract().response();
            }
        } else {
            throw new RuntimeException("Test data not found for: " + scenarioNameFromFeature);
        }
    }

    @Then("Admin verifies the response payload with expected output from the data sheet for Get All Programs")
    public void admin_verifies_the_response_payload_with_expected_output_from_the_data_sheet_for_get_all_programs() {
        int expectedStatusCode = Integer.parseInt(data.get("ExpectedStatusCode"));
        int programIdCount = 0;
        response.then().log().all()
                .statusCode(expectedStatusCode)
                .body(matchesJsonSchemaInClasspath("schemas/Program/GetAllProgramsResponseSchema.json"))
                .body("", Matchers.instanceOf(List.class))
                .body("size()", Matchers.greaterThan(0));
        JsonPath json = response.jsonPath();
        List<Map<String, Object>> array = json.getList("$");
        for (int i = 0; i < array.size(); i++) {
            Map<String, Object> element = array.get(i);
            int resProgram = (Integer) element.get("programId");
            if (programIdList.contains(resProgram)) {
                programIdCount++;
            }
        }
        Assert.assertEquals(programIdCount, programIdList.size());
    }


    @When("Admin sends GET request to get all programs with users for {string} from dataSheet")
    public void admin_sends_get_request_to_get_all_programs_with_users_for_from_data_sheet(String scenarioNameFromFeature) throws IOException {

        String scenarioName = scenarioNameFromFeature;
        data = ExcelReader.readExcelData("Program", scenarioName);

        if (data != null) {
            String dataSheetTestname = data.get("ScenarioName");

            if (scenarioNameFromFeature.equalsIgnoreCase(dataSheetTestname)) {
                requestSpec = given()
                        .spec(requestSpec);
                String httpMethod = data.get("Method");
                String endPoint = data.get("Endpoint");
                response = requestSpec.log().all().
                        when().
                        request(httpMethod, endPoint).
                        then().log().all().extract().response();
            }
        } else {
            throw new RuntimeException("Test data not found for: " + scenarioNameFromFeature);
        }
    }
    @Then("Admin verifies the response payload with expected output from the data sheet for Get All Programs with Users")
    public void admin_verifies_the_response_payload_with_expected_output_from_the_data_sheet_for_get_all_programs_with_users() {
        response.then().log().all();
        int expectedStatus = Integer.parseInt(data.get("ExpectedStatusCode"));
        Assert.assertEquals(response.getStatusCode(), expectedStatus, "Status code mismatch!");

    }


    @When("Admin sends PUT request to update programById with payload for {string} using dataSheet")
    public void admin_sends_put_request_to_update_program_by_id_with_payload_for_using_data_sheet(String scenario) throws IOException {
        String scenarioName = scenario;
        data = ExcelReader.readExcelData("Program", scenarioName);
        if (data != null) {
            String dataSheetTestname = data.get("ScenarioName");
            String body = data.get("Body");
            if (scenarioName.equalsIgnoreCase(dataSheetTestname)) {
                programInput = ProgramSendRequest.createProgramParseData(data.get("Body"));
                requestSpec = given()
                        .spec(requestSpec)
                        .body(programInput);

                String httpMethod = data.get("Method");
                String endPoint = data.get("Endpoint");
                if (endPoint.contains("{programId}")) {
                    if (GlobalTestData.programIdList == null || GlobalTestData.programIdList.isEmpty()) {
                        Assert.fail("Test stopped: No Program IDs available in GlobalTestData.");
                        int lastIndex = GlobalTestData.programIdList.size() - 1;
                        int programId = GlobalTestData.programIdList.get(lastIndex);
                    endPoint = endPoint.replace("{programId}", String.valueOf(programId));
                }  }
                response = requestSpec.log().all().
                        when().
                        request(httpMethod, endPoint).
                        then().log().all().extract().response();
            }
        }
    }

    @Then("Admin verifies the response payload with expected output from the data sheet for Update Program ByProgramId")
    public void admin_verifies_the_response_payload_with_expected_output_from_the_data_sheet_for_update_program_by_program_id() {
        int expectedStatus = Integer.parseInt(data.get("ExpectedStatusCode"));
        String expectedMessage = data.get("ExpectedMessage");

        response.then().log().all()
                .statusCode(expectedStatus)
                .statusLine(Matchers.containsString(expectedMessage));

        if (expectedStatus == 200) {

            ProgramResponse actualResponse = response.as(ProgramResponse.class);
            response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/Program/UpdateProgramByProgramId.json"));
            response.then().body("programId", Matchers.equalTo(GlobalTestData.programIdList.get(programIdList.size() - 1)));
            Assert.assertEquals(actualResponse.getProgramName(), programInput.getProgramName(), "Program Name is not matching after update.");
            Assert.assertEquals(actualResponse.getProgramStatus(), programInput.getprogramStatus(), "Program Status is not matching after update.");
            Assert.assertNotNull(actualResponse.getCreationTime(), "Creation Time should not be null after update.");
            Assert.assertNotNull(actualResponse.getLastModTime(), "Last Modification Time should not be null after update.");
        } else {
            switch (expectedStatus) {

                case 400:
                    String badReq = response.jsonPath().getString("message");
                    Assert.assertNotNull(badReq, "Bad request ");
                    break;
                case 404:
                    System.out.println("Endpoint not found as expected.");
                    break;
                case 405:
                    String methodNotAllowed = response.jsonPath().getString("error");
                    Assert.assertEquals(methodNotAllowed, "Method Not Allowed");
                    break;
                default:
                    break;
            }
        }
    }

    @When("Admin sends PUT request to update programByName with payload for {string} using dataSheet")
    public void admin_sends_put_request_to_update_program_by_name_with_payload_for_using_data_sheet(String scenario) throws IOException {
        String scenarioName = scenario;
        data = ExcelReader.readExcelData("Program", scenarioName);
        if (data != null) {
            String dataSheetTestname = data.get("ScenarioName");
            String body = data.get("Body");

            if (scenarioName.equalsIgnoreCase(dataSheetTestname)) {
                programInput = ProgramSendRequest.createProgramParseData(data.get("Body"));
                requestSpec = given()
                        .spec(requestSpec)
                        .body(programInput);

                String httpMethod = data.get("Method");
                String endPoint = data.get("Endpoint");
                if (endPoint.contains("{programName}")) {
                    if (GlobalTestData.programNameList == null || GlobalTestData.programNameList.isEmpty()) {
                        Assert.fail("Test Aborted: No Program Names available to perform the PUT request.");
                    } else {
                        int lastIndex = GlobalTestData.programNameList.size() - 1;
                        String programName = GlobalTestData.programNameList.get(lastIndex);
                        // Replace the placeholder in the endpoint
                        endPoint = endPoint.replace("{programName}", programName);
                    }
                }
                response = requestSpec.
                        when().log().all().
                        request(httpMethod, endPoint).
                        then().log().all().extract().response();
            }
        }
    }

    @Then("Admin verifies the response payload with expected output from the data sheet for Update Program ByProgramName")
    public void admin_verifies_the_response_payload_with_expected_output_from_the_data_sheet_for_update_program_by_program_name() {
            response.then().log().all();
        int expectedStatus = Integer.parseInt(data.get("ExpectedStatusCode"));
        String expectedMessage = data.get("ExpectedMessage");
        if (expectedStatus == 200) {
            ProgramResponse actualResponse = response.as(ProgramResponse.class);
            response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/Program/UpdateProgramByProgramName.json"));

            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), expectedStatus, "Status code is matched");
            assertTrue(response.getStatusLine().contains(expectedMessage), "Status line message is matched");
            Assert.assertEquals(actualResponse.getProgramDescription(), programInput.getProgramDescription(), "Program Description is not matching after update.");
            Assert.assertEquals(actualResponse.getProgramStatus(), programInput.getprogramStatus(), "Program Status is not matching after update.");
            Assert.assertNotNull(actualResponse.getCreationTime(), "Creation Time should not be null after update.");
            Assert.assertNotNull(actualResponse.getLastModTime(), "Last Modification Time should not be null after update.");
        } else {
            switch (expectedStatus) {

                case 400:
                    String badReq = response.jsonPath().getString("message");
                    Assert.assertNotNull(badReq, "Bad request ");
                    break;
                case 404:
                    System.out.println("Endpoint not found as expected.");
                    break;
                case 405:
                    String methodNotAllowed = response.jsonPath().getString("error");
                    Assert.assertEquals(methodNotAllowed, "Method Not Allowed");
                    break;
                default:
                    break;
            }
        }
    }

    @When("Admin sends DELETE request to delete programByName with payload for {string} using dataSheet")
    public void admin_sends_delete_request_to_delete_program_by_name_with_payload_for_using_data_sheet(String scenario) throws IOException {
        String scenarioName = scenario;
        data = ExcelReader.readExcelData("Program", scenarioName);
        System.out.println(scenarioName);
        if (data != null) {
            String dataSheetTestname = data.get("ScenarioName");
            if (scenarioName.equalsIgnoreCase(dataSheetTestname)) {
                requestSpec = given()
                        .spec(requestSpec);

                String httpMethod = data.get("Method");
                String endPoint = data.get("Endpoint");
                if (endPoint.contains("{programName}")) {
                    if (GlobalTestData.programNameList == null || GlobalTestData.programNameList.isEmpty()) {
                        Assert.fail("Test Aborted: No Program Names available to perform the PUT request.");
                    } else {
                        int lastIndex = GlobalTestData.programNameList.size() - 1;
                        String programName = GlobalTestData.programNameList.get(lastIndex);
                        // Replace the placeholder in the endpoint
                        endPoint = endPoint.replace("{programName}", programName);
                    }
                }
                response = requestSpec.log().all().
                        when().
                        request(httpMethod, endPoint).
                        then().log().all().extract().response();
            }
        }
    }

    @Then("Admin verifies the response payload with expected output from the data sheet for Delete Program ByProgramName")
    public void admin_verifies_the_response_payload_with_expected_output_from_the_data_sheet_for_delete_program_by_program_name() {
        int expectedStatus = Integer.parseInt(data.get("ExpectedStatusCode"));
        response.then().log().all()
                .statusCode(expectedStatus);
        Assert.assertEquals(response.getStatusCode(), expectedStatus, "Status code is not matching");
        if (expectedStatus == 200) {

            JsonPath js = response.jsonPath();
            String actualSuccessMessage = js.getString("status");
            String actualMessage = response.jsonPath().getString("message");
            Assert.assertEquals(response.jsonPath().getString("programStatus"), "Inactive", "Program status should be Inactive after deletion");
            Assert.assertEquals(actualSuccessMessage, "success", "Status in output is not as expected");
        } else {

            if (expectedStatus == 400 || expectedStatus == 404 || expectedStatus == 405) {

                        response.then().log().ifValidationFails();            }
        }
    }

    @When("Admin sends DELETE request to delete programById with payload for {string} using dataSheet")
    public void admin_sends_delete_request_to_delete_program_by_id_with_payload_for_using_data_sheet(String scenario) throws IOException {
        String scenarioName = scenario;
        data = ExcelReader.readExcelData("Program", scenarioName);
        if (data != null) {
            String dataSheetTestname = data.get("ScenarioName");
            if (scenarioName.equalsIgnoreCase(dataSheetTestname)) {
                requestSpec = given()
                        .spec(requestSpec)
                        .body(programInput);
                String httpMethod = data.get("Method");
                String endPoint = data.get("Endpoint");
                if (endPoint.contains("{programId}")) {
                    if (GlobalTestData.programIdList == null || GlobalTestData.programIdList.isEmpty()) {
                        Assert.fail("Test failed: No Program ID available to delete.");
                    } else {
                        int lastIndex = GlobalTestData.programIdList.size() - 1;
                        String programId = String.valueOf(GlobalTestData.programIdList.get(lastIndex));
                        endPoint = endPoint.replace("{programId}", programId);
                    }
                }
                response = requestSpec.log().all().
                        when().
                        request(httpMethod, endPoint).
                        then().log().all().extract().response();

            }
        }
    }

    @Then("Admin verifies the response payload with expected output from the data sheet for Delete Program ByProgramId")
    public void admin_verifies_the_response_payload_with_expected_output_from_the_data_sheet_for_delete_program_by_program_id() {
        response.then().log().all();
        int expectedStatus = Integer.parseInt(data.get("ExpectedStatusCode"));
        int actualStatus = response.getStatusCode();
        Assert.assertEquals(response.getStatusCode(), expectedStatus, "Status code is not matching");
        if (expectedStatus == 200) {
            String expectedMessage = "Message: Program Name-{" + programId + "} is deleted Successfully!";
            JsonPath js = response.jsonPath();
             GlobalTestData.deletedProgramId = js.getInt("programId");
            String actualSuccessMessage = js.getString("status");
            String actualMessage = response.jsonPath().getString("message");
            Assert.assertEquals(response.jsonPath().getString("programStatus"), "Inactive", "Program status should be Inactive after deletion");
            Assert.assertEquals(expectedMessage, actualMessage, "Actual and expected message message in output is not as expected");
            Assert.assertEquals(actualSuccessMessage, "success", "Status in output is not as expected");
        } else if (expectedStatus == 400 || expectedStatus == 404 || expectedStatus == 405) {
            response.then().log().ifValidationFails();
        }
    }

    @Given("Admin bearer token in set to empty")
    public void admin_bearer_token_in_set_to_empty() {
        requestSpec = RequestSpecUtil.getRequestSpecWithoutAuth();
    }

    @When("admin send HTTP method to access endpoint for testcase {string} without authorization token")
    public void admin_send_http_method_to_access_endpoint_for_testcase_without_authorization_token(String scenario) throws IOException {
        String scenarioName = scenario;
        data = ExcelReader.readExcelData("Program", scenarioName);
        if (data != null) {
            String dataSheetTestname = data.get("ScenarioName");
            if (scenarioName.equalsIgnoreCase(dataSheetTestname)) {
                programInput = ProgramSendRequest.createProgramParseData(data.get("Body"));
                requestSpec = given()
                        .spec(requestSpec)
                        .body(programInput);

                String httpMethod = data.get("Method");
                String endPoint = data.get("Endpoint");
                if (endPoint.contains("{programName}")) {
                    endPoint = endPoint.replace("{programName}", GlobalTestData.programName);
                }
                response = requestSpec.
                        when().log().all().
                        request(httpMethod, endPoint).
                        then().log().all().extract().response();
            }
        }
    }

    @Then("Admin verifies the response status code with expected output from data sheet")
    public void admin_verifies_the_response_status_code_with_expected_output_from_data_sheet() {
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), Integer.parseInt(data.get("ExpectedStatusCode")), "Status code is not matching");
    }

    @Given("Admin bearer token in set to invalid")
    public void admin_bearer_token_in_set_to_invalid() {
        requestSpec = RequestSpecUtil.getRequestSpecInvalidAuth();
    }

    @When("admin send HTTP method to access endpoint for testcase {string} with invalid authorization token")
    public void admin_send_http_method_to_access_endpoint_for_testcase_with_invalid_authorization_token(String scenarioName) throws IOException {
        data = ExcelReader.readExcelData("Program", scenarioName);
        if (data != null) {
            String dataSheetTestname = data.get("ScenarioName");
            System.out.println("Executing Scenario: " + dataSheetTestname);
            if (scenarioName.equalsIgnoreCase(dataSheetTestname)) {
                programInput = ProgramSendRequest.createProgramParseData(data.get("Body"));
                requestSpec = given()
                        .spec(requestSpec)
                        .body(programInput);
                String httpMethod = data.get("Method");
                String endPoint = data.get("Endpoint");
                if (endPoint.contains("{programName}")) {
                    endPoint = endPoint.replace("{programName}", GlobalTestData.programName);
                }
                response = requestSpec.
                        when().log().all().
                        request(httpMethod, endPoint).
                        then().log().all().extract().response();
            }
        }
    }
}