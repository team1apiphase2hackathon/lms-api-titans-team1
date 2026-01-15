Feature: Program Batch module for LMS API 

@Post
Scenario Outline: Check if Admin is able to create batch with valid batch name and description
Given Admin create POST request with valid "<batch_name>" format and "<batch_description>"
When Admin sends POST request to create program batch
Then Admin receives created status with response body

Examples:
| batch_name | batch_description |
| SDETTeam311 | abcd     |
#| SDETTeam012 | ProgramBatchDescription12 |
#| SDETTeam013 | TestBatch11  |

@Post
Scenario: Check if Admin is able to create batch with invalid input
Given Admin create POST request with invalid input for "<scenario>" from excel sheet
When Admin sends POST request to create program batch
Then Admin receives expected status code with error message

Examples:
|      scenario       |
| CreateBatch_NoAuth  |
| CreateBatch_Existing_Batch |
| CreateBatch_Missing_Mandatory_Fields |
| CreateBatch_invalid_endpoint |
| CreateBatch_invalid_BatchDescription |
| CreateBatch_invalid_BatchName |
| CreateBatch_invalid_BatchNoOfClasses |
| CreateBatch_invalid_BatchNameLength |
| CreateBatch_invalid_BatchStatus  |
| CreateBatch_invalid_ProgramId  |
| CreateBatch_inactive_programId |

@Post
Scenario: Check if Admin is able to create batch with existing batch name 
Given Admin create POST request with existing batch name
When Admin sends POST request to create program batch
Then Admin receives expected status code with error message

@Post
Scenario: Check if Admin is able to create batch with missing mandatary fields
Given Admin create POST request with missing mandataory fields
When Admin sends POST request to create program batch
Then Admin receives expected status code with error message

@Post
Scenario: Check if Admin is able to create batch with invalid endpoint
Given Admin create POST request with invalid endpoint
When Admin sends POST request to create program batch
Then Admin receives expected status code with error message

@Post
Scenario: Check if Admin is able to create batch with missing additional fields
Given Admin create POST request with missing additional fields
When Admin sends POST request to create program batch
Then Admin receives created status with response body

@Post
Scenario: Check if Admin is able to create batch with invalid batchName in request body 
Given Admin create POST request with invalid batchName in request body
When Admin sends POST request to create program batch
Then Admin receives expected status code with error message

@Post
Scenario: Check if Admin is able to create batch with invalid batchDescription in request body 
Given Admin create POST request with invalid batchDescription in request body
When Admin sends POST request to create program batch
Then Admin receives expected status code with error message

@Post
Scenario: Check if Admin is able to create batch with invalid batchNoOfClasses in request body 
Given Admin create POST request with invalid batchNoOfClasses in request body
When Admin sends POST request to create program batch
Then Admin receives expected status code with error message

@Post
Scenario: Check if Admin is able to create batch with invalid batchName length in request body 
Given Admin create POST request with invalid batchName length in request body
When Admin sends POST request to create program batch
Then Admin receives expected status code with error message

@Post
Scenario: Check if Admin is able to create batch with invalid batchStatus in request body 
Given Admin create POST request with invalid batchStatus in request body
When Admin sends POST request to create program batch
Then Admin receives expected status code with error message

@Post
Scenario: Check if Admin is able to create batch with invalid programId in request body 
Given Admin create POST request with invalid programId in request body
When Admin sends POST request to create program batch
Then Admin receives expected status code with error message

@Post
Scenario: Check if Admin is able to create batch with inactive programId 
Given Admin create POST request with inactive program Id
When Admin sends POST request to create program batch
Then Admin receives expected status code with error message

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
Scenario: Check if Admin is able to retrieve batch by batchId with invalid input 
Given Admin create GET request by BatchId with invalid input for "<scenario>" from excel sheet
When Admin sends GET request to retrieve the batch
Then Admin receives expected status code with error message
Examples:
|     scenario   |
|  GetBatchById_NoAuth  |
| GetBatchById_invalidBatchId |
| GetBatchById_Invalid_Endpoint |

@GetByBatchName
Scenario: Check if Admin is able to retrieve batch with valid batch name 
Given Admin create GET request to retrieve batch with valid batch name
When Admin sends GET request to retrieve the batch
Then Admin receives success code with GET response body having given batch name

@GetByBatchName
Scenario: Check if Admin is able to retrieve batch by BatchName with invalid input 
Given Admin create GET request by BatchName with invalid input for "<scenario>" from excel sheet
When Admin sends GET request to retrieve the batch
Then Admin receives expected status code with error message
Examples:
|    scenario   |
|  GetBatchByName_NoAuth |
|  GetBatchByName_Invalid_BatchName |
|  GetBatchByName_Invalid_Endpoint  |

@GetByProgramId
Scenario: Check if Admin is able to retrieve batch with valid programId 
Given Admin create GET request to retrieve batch with valid programId
When Admin sends GET request to retrieve the batch
Then Admin receives success code with GET response body having given programId

@GetByProgramId
Scenario: Check if Admin is able to retrieve batch by programId with invalid input 
Given Admin create GET request by programId with invalid input for scenario "<scenario>" from excel sheet
When Admin sends GET request to retrieve the batch
Then Admin receives expected status code with error message

Examples:
|    scenario     |
|  GetBatchByProgram_Invalid_ProgramId |
|  GetBatchByProgram_NoAuth |
|  GetBatchByProgram_Invalid_Endpoint |

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
Scenario: Check if Admin is able to update batch with invalid input 
Given Admin create PUT request with invalid input for each "<scenario>" from excel sheet
When Admin sends PUT request to update the batch
Then Admin receives expected status code with error message

Examples:
|           scenario            |
|  PutBatchById_NoAuth          |
|  PutBatchById_Invalid_BatchId |
|  PutBatchById_Invalid_Endpoint |
|  PutBatchById_Missing_Mandatory_Fields |
|  PutBatchById_Invalid_BatchStatus  |
|  PutBatchById_Invalid_NoOfClasses  |
|  PutBatchById_Invalid_ProgramId    |
|  PutBatchById_Invalid_BatchName    |
|  PutBatchById_Invalid_BatchNameLength |
|  PutBatchById_Deleted_programId    |



