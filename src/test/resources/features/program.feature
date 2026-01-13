Feature: Program


  Scenario: Check if admin is able to create a Program with Authorization
    Given Admin sends POST request to create program for LMS
    When Admin create POST request with request body
    Then Admin receives 201 created status code with response body

  Scenario Outline: Verify if admin is able to create a Program
    Given Admin has a valid authorization token set
    When Admin sends POST request to create program with different payload for <"testcaseName"> from dataSheet
    Then Admin verifies the response payload with expected output from the data sheet

    Examples:
      |			testcaseName				|
      |CreateProgram_with_Invalid_Endpoint	|
      |CreateProgram_with_InvalidMethod		|
      |CreateProgram_with_ProgramName_LessThan_3_Characters		|
      |CreateProgram_with_ProgramName_LessThan_25_Characters		|
      |CreateProgram_with_ProgramDescription_LessThan_3_Characters		|
      |CreateProgram_with_ProgramDescription_LessThan_25_Characters		|
      |CreateProgram_with_Missing_MandatoryField_ProgramName		|
      |CreateProgram_with_Missing_MandatoryField_ProgramStatus		|
      |CreateProgram_with_Valid_ProgramDesc_ProgramName_Active_Status		|
      |CreateProgram_with_InActive_Status		|
      |CreateProgram_with_Existing_ProgramName				|


  Scenario Outline: Verify if admin is able to GET Program by ProgramId

    When Admin sends GET request to get program with different payload for <"testcaseName"> from dataSheet
    Then Admin verifies the response payload with expected output from the data sheet for Get Program

    Examples:
      |			testcaseName				|
      |	GetProgramById_with_Invalid_BaseURI		|
      |	GetProgramById_with_Invalid_BasePath	|
      |	GetProgramById_with_Invalid_Method		|
      |	GetProgramById_with_Invalid_ProgramId	|
      |	GetProgramById_with_Valid_ProgramId		|

  Scenario Outline: Verify if admin is able to GET All Programs

    When Admin sends GET request to get all programs with different payload for <"testcaseName"> from dataSheet
    Then Admin verifies the response payload with expected output from the data sheet for Get All Programs

    Examples:
      |			testcaseName				|
      |	GetPrograms_with_Invalid_BaseURI	|
      |	GetPrograms_with_Invalid_BasePath	|
      |	GetPrograms_with_Invalid_Method		|
      |	Get_All_Programs					|


  Scenario Outline: Verify if admin is able to UPDATE a ProgramByProgramid using PUT method

    When Admin sends PUT request to update programById with payload for <"testcaseName"> using dataSheet
    Then Admin verifies the response payload with expected output from the data sheet for Update Program ByProgramId

    Examples:
      |			testcaseName				|
      |UpdateProgramById_with_Invalid_Endpoint	|
      |UpdateProgramById_with_InvalidMethod		|
      |UpdateProgramById_with_ProgramName_LessThan_3_Characters		|
      |UpdateProgramById_with_ProgramName_LessThan_25_Characters		|
      |UpdateProgramById_with_ProgramDescription_LessThan_3_Characters		|
      |UpdateProgramById_with_ProgramDescription_LessThan_25_Characters		|
      |UpdateProgramById_with_Missing_MandatoryField_ProgramName		|
      |UpdateProgramById_with_Missing_MandatoryField_ProgramStatus		|
      |	UpdateProgramById_ProgramID_InActive_Status				|
      |	UpdateProgramById_ProgramID_Active_Status				|
      |UpdateProgramById_with_Valid_ProgramDesc_ProgramName_Active_Status		|
      |UpdateProgramById_ProgramID_InActive_Status				|
      |UpdateProgramById_with_SpecialCharacters				|
      |UpdateProgramById_with_Valid_Inputs				|

  Scenario Outline: Verify if admin is able to UPDATE a ProgramByProgramName using PUT method

    When Admin sends PUT request to update programByName with payload for <"testcaseName"> using dataSheet
    Then Admin verifies the response payload with expected output from the data sheet for Update Program ByProgramName

    Examples:
      |			testcaseName				|
      |	UpdateProgramByName_with_Invalid_Endpoint	|
      |	UpdateProgramByName_with_InvalidMethod		|
      |	UpdateProgramByName_with_ProgramName_LessThan_3_Characters		|
      |	UpdateProgramByName_with_ProgramName_LessThan_25_Characters		|
      |	UpdateProgramByName_with_ProgramDescription_LessThan_3_Characters		|
      |	UpdateProgramByName_with_ProgramDescription_LessThan_25_Characters		|
      |	UpdateProgramByIName_with_Missing_MandatoryField_ProgramName		|
      |	UpdateProgramByName_with_Missing_MandatoryField_ProgramStatus		|
      |	UpdateProgramByName_with_SpecialCharacters				|
      |	UpdateProgramByName_to_InActive_Status				|
      |	UpdateProgramByName_to_Active_Status				|
      |	UpdateProgramByName_with_Valid_ProgramDesc_ProgramName_Active_Status		|


  Scenario Outline: Verify if admin is able to DELETE a ProgramByProgramName using DELETE method

    When Admin sends DELETE request to delete programByName with payload for <"testcaseName"> using dataSheet
    Then Admin verifies the response payload with expected output from the data sheet for Delete Program ByProgramName

    Examples:

      |			testcaseName				|
      |	DeleteProgramByName_with_Invalid_BasePath	|
      |	DeleteProgramByName_with_Invalid_Method		|
      |	DeleteProgramByName_with_Invalid_ProgramName	|
      |	DeleteProgramByName_with_valid_ProgramName	|

  Scenario Outline: Verify if admin is able to DELETE a ProgramByProgramId using DELETE method

    When Admin sends DELETE request to delete programById with payload for "<testcaseName>" using dataSheet
    Then Admin verifies the response payload with expected output from the data sheet for Delete Program ByProgramId

    Examples:
      |			testcaseName				|
      |	DeleteProgramById_with_Invalid_BasePath	|
      |	DeleteProgramById_with_Invalid_Method		|
      |	DeleteProgramById_with_Invalid_ProgramName	|
      |	DeleteProgramById_with_valid_ProgramName	|


  Scenario Outline:
  Verify if admin is able to access the endPoints without token
    Given Admin bearer token in set to empty
    When admin send HTTP method to access endpoint for testcase "<testcaseName>" without authorization token
    Then Admin verifies the response status code with expected output from data sheet

    Examples:

      |	testcaseName	|
      |	CreateProgram	|
      |	GetProgramById	|
      |	GetAllPrograms	|
      |	UpdateProgramById	|
      |	UpdateProgramByName |
      |	DeleteProgramByName	|
      |	DeleteProgramById	|

  Scenario Outline:
  Verify if admin is able to access the endPoints with invalid token
    Given Admin bearer token in set to invalid
    When admin send HTTP method to access endpoint for testcase "<testcaseName>" with invalid authorization token
    Then Admin verifies the response status code with expected output from data sheet

    Examples:

      |	testcaseName	|
      |	CreateProgram	|
      |	GetProgramById	|
      |	GetAllPrograms	|
      |	UpdateProgramById	|
      |	UpdateProgramByName |
      |	DeleteProgramByName	|
      |	DeleteProgramById	|










