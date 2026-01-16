Feature: Data Clean up

  @userController
  Scenario: Check if admin able to delete a admin with valid user
    Given Admin has a valid authorization token in user controller
    When Admin sends DELETE Request for the LMS API endpoint  and  valid user
    Then Admin receives 200 OK Status with response body

  @Batch
  Scenario: Check if Admin is able to delete batchById with valid BatchId
    When Admin sends DELETE request to delete all batches created
    Then Admin verifies all batches has been deleted

  @Program
  Scenario Outline: Verify if admin is able to DELETE a ProgramByProgramId using DELETE method
    Given Admin has a valid authorization token set
    When Admin sends DELETE request to delete all programs
    Then Admin verifies the all the deleted programs

    Examples: 
      | ScenarioName                             |
      | DeleteProgramById_with_valid_ProgramName |

  @Logout
  Scenario Outline: Verify Logout functionality with Auth
    Given Admin has the test data for "<ScenarioName>" from Excel with Bearer Token
    When Admin sends a GET request for Logout
    Then Admin should receive the status code matches with Expected statuscode

    Examples: 
      | ScenarioName           |
      | Logout_Valid           |
      | Logout_InvalidBaseURL  |
      | Logout_InvalidEndpoint |
