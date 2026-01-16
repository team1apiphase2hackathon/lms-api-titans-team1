@programmodule
Feature: Program

  @Program @e2e
  Scenario Outline: Verify if admin is able to create a Program
    Given Admin has a valid authorization token set
    When Admin sends POST request to create program with different payload for "<ScenarioName>" from dataSheet
    Then Admin verifies the response payload with expected output from the data sheet

    Examples: 
      | ScenarioName                                                    |
      | Create_NewProgram                                               |
      | CreateProgram_with_Invalid_Endpoint                             |
      | CreateProgram_with_InvalidMethod                                |
      | CreateProgram_with_ProgramName_LessThan_4_Characters            |
      | CreateProgram_with_ProgramName_GreaterThan_25_Characters        |
      | CreateProgram_with_ProgramDescription_LessThan_4_Characters     |
      | CreateProgram_with_ProgramDescription_GreaterThan_25_Characters |
      | CreateProgram_with_Missing_MandatoryField_ProgramName           |
      | CreateProgram_with_Missing_MandatoryField_ProgramStatus         |
      | CreateProgram_with_Special Characters_in_ProgramName            |
      | CreateProgram_with_space_in_the_start and end_of_ProgramName    |
      | CreateProgram_with_Valid_ProgramDesc_ProgramName_Active_Status  |
      | CreateProgram_with_Active_Status                                |
      | CreateProgram_with_Existing_ProgramName                         |
      | CreateProgram_with_Valid_ProgramDesc_ProgramName_Active_Status  |
      | CreateProgram_with_Valid_ProgramDesc_ProgramName_Active_Status  |

  @Program
  Scenario Outline: Verify if admin is able to GET Program by ProgramId
    Given Admin has a valid authorization token set
    When Admin sends GET request to get program with different payload for "<ScenarioName>" from dataSheet
    Then Admin verifies the response payload with expected output from the data sheet for Get Program

    Scenarios: 
      | ScenarioName                          |
      | GetProgramById_with_Invalid_Endpoint  |
      | GetProgramById_with_Invalid_Method    |
      | GetProgramById_with_Invalid_ProgramId |
      | GetProgramById_with_Valid_ProgramId   |

  @Program
  Scenario Outline: Verify if admin is able to GET All Programs
    Given Admin has a valid authorization token set
    When Admin sends GET request to get all programs with different payload for "<ScenarioName>" from dataSheet
    Then Admin verifies the response payload with expected output from the data sheet for Get All Programs

    Examples: 
      | ScenarioName                      |
      | GetPrograms_with_Invalid_Endpoint |
      | GetPrograms_with_Invalid_Method   |
      | Get_All_Programs                  |

  @Program
  Scenario Outline: Verify if admin is able to get All Programs with users
    Given Admin has a valid authorization token set
    When Admin sends GET request to get all programs with users for "<ScenarioName>" from dataSheet
    Then Admin verifies the response payload with expected output from the data sheet for Get All Programs with Users

    Examples: 
      | ScenarioName                                  |
      | GetAllProgramsWithUsers_with_Invalid_Endpoint |
      | GetPrograms_with_Invalid_Method               |
      | GetAllProgramsWithUsers_with_Valid_Endpoint   |

  @Program
  Scenario Outline: Verify if admin is able to UPDATE a ProgramByProgramId using PUT method
    Given Admin has a valid authorization token set
    When Admin sends PUT request to update programById with payload for "<ScenarioName>" using dataSheet
    Then Admin verifies the response payload with expected output from the data sheet for Update Program ByProgramId

    Examples: 
      | ScenarioName                                                       |
      | UpdateProgramById_with_Invalid_Endpoint                            |
      | UpdateProgramById_with_InvalidMethod                               |
      | UpdateProgramById_with_InvalidProgramId                            |
      | UpdateProgramById_with_ProgramName_LessThan_4_Characters           |
      | UpdateProgramById_with_ProgramName_LessThan_25_Characters          |
      | UpdateProgramById_with_ProgramDescription_LessThan_4_Characters    |
      | UpdateProgramById_with_ProgramDescription_LessThan_25_Characters   |
      | UpdateProgramById_with_Missing_MandatoryField_ProgramName          |
      | UpdateProgramById_with_Missing_MandatoryField_ProgramStatus        |
      | UpdateProgramById_ProgramID_InActive_Status                        |
      | UpdateProgramById_ProgramID_Active_Status                          |
      | UpdateProgramById_with_existing_ProgramName                        |
      | UpdateProgramById_with_SpecialCharacters                           |
      | UpdateProgramById_with_Valid_ProgramDesc_ProgramName_Active_Status |

  @Program
  Scenario Outline: Verify if admin is able to UPDATE a ProgramByProgramName using PUT method
    Given Admin has a valid authorization token set
    When Admin sends PUT request to update programByName with payload for "<ScenarioName>" using dataSheet
    Then Admin verifies the response payload with expected output from the data sheet for Update Program ByProgramName

    Examples: 
      | ScenarioName                                                         |
      | UpdateProgramByName_with_Invalid_Endpoint                            |
      | UpdateProgramByName_with_InvalidMethod                               |
      | UpdateProgramByName_with_ProgramName_LessThan_4_Characters           |
      | UpdateProgramByName_with_ProgramName_LessThan_25_Characters          |
      | UpdateProgramByName_with_ProgramDescription_LessThan_4_Characters    |
      | UpdateProgramByName_with_ProgramDescription_LessThan_25_Characters   |
      | UpdateProgramByIName_with_Missing_MandatoryField_ProgramName         |
      | UpdateProgramByName_with_Missing_MandatoryField_ProgramStatus        |
      | UpdateProgramByName_with_SpecialCharacters                           |
      | UpdateProgramByName_to_InActive_Status                               |
      | UpdateProgramByName_to_Active_Status                                 |
      | UpdateProgramByName_with_Valid_ProgramDesc_ProgramName_Active_Status |

  @Program
  Scenario Outline: Verify if admin is able to DELETE a ProgramByProgramName using DELETE method
    Given Admin has a valid authorization token set
    When Admin sends DELETE request to delete programByName with payload for "<ScenarioName>" using dataSheet
    Then Admin verifies the response payload with expected output from the data sheet for Delete Program ByProgramName

    Examples: 
      | ScenarioName                                 |
      | DeleteProgramByName_with_Invalid_EndPoint    |
      | DeleteProgramByName_with_Invalid_Method      |
      | DeleteProgramByName_with_Invalid_ProgramName |
      | DeleteProgramByName_with_valid_ProgramName   |

  @Program
  Scenario Outline: Verify if admin is able to DELETE a ProgramByProgramId using DELETE method
    Given Admin has a valid authorization token set
    When Admin sends DELETE request to delete programById with payload for "<ScenarioName>" using dataSheet
    Then Admin verifies the response payload with expected output from the data sheet for Delete Program ByProgramId

    Examples: 
      | ScenarioName                               |
      | DeleteProgramById_with_Invalid_EndPoint    |
      | DeleteProgramById_with_Invalid_Method      |
      | DeleteProgramById_with_Invalid_ProgramName |
      | DeleteProgramById_with_valid_ProgramName   |

  @Program
  Scenario Outline: 
    Verify if admin is able to access the endPoints without token

    Given Admin bearer token in set to empty
    When admin send HTTP method to access endpoint for testcase "<ScenarioName>" without authorization token
    Then Admin verifies the response status code with expected output from data sheet

    Examples: 
      | ScenarioName            |
      | CreateProgram           |
      | GetProgramById          |
      | GetAllPrograms          |
      | GetAllProgramsWithUsers |
      | UpdateProgramById       |
      | UpdateProgramByName     |
      | DeleteProgramByName     |
      | DeleteProgramById       |

  @Program
  Scenario Outline: 
    Verify if admin is able to access the endPoints with invalid token

    Given Admin bearer token in set to invalid
    When admin send HTTP method to access endpoint for testcase "<ScenarioName>" with invalid authorization token
    Then Admin verifies the response status code with expected output from data sheet

    Examples: 
      | ScenarioName            |
      | CreateProgram           |
      | GetProgramById          |
      | GetAllPrograms          |
      | GetAllProgramsWithUsers |
      | UpdateProgramById       |
      | UpdateProgramByName     |
      | DeleteProgramByName     |
      | DeleteProgramById       |
