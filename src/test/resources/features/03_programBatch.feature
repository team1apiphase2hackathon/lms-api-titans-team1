@Batch
Feature: Program Batch module for LMS API

  @Post
  Scenario Outline: Check if Admin is able to create batch with valid batch name and description
    Given Admin create POST request with valid data for "<scenario>" from excel sheet
    When Admin sends POST request to create program batch
    Then Admin receives created status with response body

    Examples: 
      | scenario                                  |
      | CreateBatch_Valid_batchName               |
      | CreateBatch_Valid_batchName_minLen        |
      | CreateBatch_Valid_batchDescription_minLen |
      | CreateBatch_Valid_batchDescription_maxLen |

  @e2e @Post
  Scenario Outline: Check if Admin is able to create batch with invalid input
    Given Admin create POST request with invalid input for "<scenario>" from excel sheet
    When Admin sends POST request to create program batch
    Then Admin receives expected status code with error message

    Examples: 
      | scenario                             |
      | CreateBatch_NoAuth                   |
      | CreateBatch_Existing_Batch           |
      | CreateBatch_Missing_Mandatory_Fields |
      | CreateBatch_invalid_endpoint         |
      | CreateBatch_invalid_BatchDescription |
      | CreateBatch_invalid_BatchName        |
      | CreateBatch_invalid_BatchNoOfClasses |
      | CreateBatch_invalid_BatchNameLength  |
      | CreateBatch_invalid_BatchStatus      |
      | CreateBatch_invalid_ProgramId        |
      | CreateBatch_inactive_programId       |

  @GetAll
  Scenario: Check if Admin is able to retrieve all program batches from LMS API
    Given Admin create GET request with valid endpoint
    When Admin sends GET request to retrieve all batches
    Then Admin receives success code with response body

  @GetAll
  Scenario: Check if Admin is able to retrieve all batches with invalid endpoint
    Given Admin create GET request with invalid endpoint
    When Admin sends GET request to retrieve all batches
    Then Admin receives expected status code with error message

  @GetAll
  Scenario: Check if Admin is able to retrieve all batches with no authentication
    Given Admin create GET request with no authentication
    When Admin sends GET request to retrieve all batches
    Then Admin receives expected status code with error message

  @GetByBatchId
  Scenario: Check if Admin is able to retrieve batch with valid batchId
    Given Admin create GET request with valid batchId
    When Admin sends GET request to retrieve the batch
    Then Admin receives success code with GET response body

  @GetByBatchId
  Scenario Outline: Check if Admin is able to retrieve batch by batchId with invalid input
    Given Admin create GET request by BatchId with invalid input for "<scenario>" from excel sheet
    When Admin sends GET request to retrieve the batch
    Then Admin receives expected status code with error message

    Examples: 
      | scenario                      |
      | GetBatchById_NoAuth           |
      | GetBatchById_invalidBatchId   |
      | GetBatchById_Invalid_Endpoint |

  @GetByBatchName
  Scenario: Check if Admin is able to retrieve batch with valid batch name
    Given Admin create GET request to retrieve batch with valid batch name
    When Admin sends GET request to retrieve the batch
    Then Admin receives success code with GET response body having given batch name

  @GetByBatchName
  Scenario Outline: Check if Admin is able to retrieve batch by BatchName with invalid input
    Given Admin create GET request by BatchName with invalid input for "<scenario>" from excel sheet
    When Admin sends GET request to retrieve the batch
    Then Admin receives expected status code with error message

    Examples: 
      | scenario                         |
      | GetBatchByName_NoAuth            |
      | GetBatchByName_Invalid_BatchName |
      | GetBatchByName_Invalid_Endpoint  |

  @GetByProgramId
  Scenario: Check if Admin is able to retrieve batch with valid programId
    Given Admin create GET request to retrieve batch with valid programId
    When Admin sends GET request to retrieve the batch
    Then Admin receives success code with GET response body having given programId

  @GetByProgramId
  Scenario Outline: Check if Admin is able to retrieve batch by programId with invalid input
    Given Admin create GET request by programId with invalid input for scenario "<scenario>" from excel sheet
    When Admin sends GET request to retrieve the batch
    Then Admin receives expected status code with error message

    Examples: 
      | scenario                            |
      | GetBatchByProgram_Invalid_ProgramId |
      | GetBatchByProgram_NoAuth            |
      | GetBatchByProgram_Invalid_Endpoint  |

  @PutBatchByBatchId
  Scenario: Check if Admin is able to update batch with valid batch Id to update programId
    Given Admin create PUT request to update batch with valid batchId for scenario "PutBatchById_Valid_BatchId_UpdateProgram"
    When Admin sends PUT request to update the batch
    Then Admin received success code with updated ProgramId in response

  @PutBatchByBatchId
  Scenario: Check if Admin is able to update batch with valid batch Id to update batchname
    Given Admin create PUT request to update batch with valid batchId for scenario "PutBatchById_Valid_BatchId_UpdateBatchName"
    When Admin sends PUT request to update the batch
    Then Admin received success code with updated batchName in response

  @PutBatchByBatchId
  Scenario: Check if Admin is able to update batch with valid batch Id to update batchStatus
    Given Admin create PUT request to update batch with valid batchId for scenario "PutBatchById_Valid_BatchId_UpdateBatchStatus"
    When Admin sends PUT request to update the batch
    Then Admin received success code with updated batchStatus in response

  @PutBatchByBatchId
  Scenario: Check if Admin is able to update batch with valid batch Id to update batchNoOfClasses
    Given Admin create PUT request to update batch with valid batchId for scenario "PutBatchById_Valid_BatchId_UpdateNoOfClasses"
    When Admin sends PUT request to update the batch
    Then Admin received success code with updated batchNoOfClasses in response

  @PutBatchByBatchId
  Scenario: Check if Admin is able to update batch with valid batchId and missing additional fields
    Given Admin create PUT request to update batch with valid batchId for scenario "PutBatchById_Missing_Additional_Fields"
    When Admin sends PUT request to update the batch
    Then Admin receives expected status code with error message

  @PutBatchByBatchId
  Scenario Outline: Check if Admin is able to update batch with invalid input
    Given Admin create PUT request with invalid input for each "<scenario>" from excel sheet
    When Admin sends PUT request to update the batch
    Then Admin receives expected status code with error message

    Examples: 
      | scenario                              |
      | PutBatchById_NoAuth                   |
      | PutBatchById_Invalid_BatchId          |
      | PutBatchById_Invalid_Endpoint         |
      | PutBatchById_Missing_Mandatory_Fields |
      | PutBatchById_Invalid_BatchStatus      |
      | PutBatchById_Invalid_NoOfClasses      |
      | PutBatchById_Invalid_ProgramId        |
      | PutBatchById_Invalid_BatchName        |
      | PutBatchById_Invalid_BatchNameLength  |
      | PutBatchById_Deleted_programId        |

  @DeleteBatchById
  Scenario Outline: Check if Admin is able to delete batch by batchId with invalid input
    Given Admin create DELETE request by BatchId with invalid input for scenario "<scenario>" from excel sheet
    When Admin sends DELETE request to delete the batch
    Then Admin receives expected status code with error message

    Examples: 
      | scenario                         |
      | DeleteBatchById_NoAuth           |
      | DeleteBatchById_Invalid_Endpoint |
      | DeleteBatchById_Invalid_BatchId  |

  @DeleteBatchById
  Scenario: Check if Admin is able to delete batchById with valid BatchId
    Given Admin create DELETE request with valid batchId
    When Admin sends DELETE request to delete the batch
    Then Admin receives success code with deleted message

  @DeleteBatchById
  Scenario: Check if Admin is able to get batchById after batch is deleted
    When Admin sends GET request to retrieve deleted batch with Id
    Then Admin receives success code with GET response body for deleted batch

  @DeleteBatchById
  Scenario: Check if Admin is able to get batchByName after batch is deleted
    When Admin sends GET request to retrieve deleted batch with name
    Then Admin receives success code with GET response body for deleted batch

  @DeleteBatchById
  Scenario: Check if Admin is able to update deleted batchbyId
    When Admin sends PUT request to update deleted batch status
    Then Admin receives success code with Active batch status in the response body
