Feature: Program Batch module for LMS API 

@Post
Scenario: Check if Admin is able to create batch with valid batch name and description
Given Admin create POST request with valid "<batch_name>" format and "<batch_description>"
When Admin sends POST request to create program batch
Then Admin receives created status with response body

Examples:
| batch_name | batch_description |
| SDETTeam311 | abcd     |
#| SDETTeam012 | ProgramBatchDescription12 |
#| SDETTeam013 | TestBatch11  |

@Post
Scenario: Check if Admin is able to create batch with valid request body and without authorization
Given Admin create POST request with valid request body and no authorization
When Admin sends POST request to create program batch
Then Admin receives expected status code with error message

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
Scenario: Check if Admin is able to create batch with invalid data in request body 
Given Admin create POST request with invalid data in request body
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
Scenario: Check if Admin is able to retrieve batch with invalid batchId 
Given Admin create GET request with invalid batchId
When Admin sends GET request to retrieve the batch
Then Admin receives expected status code with error message

@GetByBatchId
Scenario: Check if Admin is able to retrieve batch with invalid endpoint 
Given Admin create GET request to retrieve batch with invalid endpoint
When Admin sends GET request to retrieve the batch
Then Admin receives expected status code with error message

@GetByBatchId
Scenario: Check if Admin is able to retrieve batch without authorization 
Given Admin create GET request to retrieve batch without authorization
When Admin sends GET request to retrieve the batch
Then Admin receives expected status code with error message

@GetByBatchName
Scenario: Check if Admin is able to retrieve batch with valid batch name 
Given Admin create GET request to retrieve batch with valid batch name
When Admin sends GET request to retrieve the batch
Then Admin receives success code with GET response body having given batch name

@GetByBatchName
Scenario: Check if Admin is able to retrieve batch with invalid batch name 
Given Admin create GET request to retrieve batch with invalid batch name
When Admin sends GET request to retrieve the batch
Then Admin receives expected status code with error message

@GetByBatchName
Scenario: Check if Admin is able to retrieve batch with batch name without authorization 
Given Admin create GET request to retrieve batch by batch name without auth
When Admin sends GET request to retrieve the batch
Then Admin receives expected status code with error message

@GetByBatchName
Scenario: Check if Admin is able to retrieve batch with batch name and invalid endpoint 
Given Admin create GET request to retrieve batch by batch name with invalid endpoint
When Admin sends GET request to retrieve the batch
Then Admin receives expected status code with error message

@GetByProgramId
Scenario: Check if Admin is able to retrieve batch with valid programId 
Given Admin create GET request to retrieve batch with valid programId
When Admin sends GET request to retrieve the batch
Then Admin receives success code with GET response body having given programId

@GetByProgramId
Scenario: Check if Admin is able to retrieve batch with invalid programId 
Given Admin create GET request to retrieve batch with invalid programId
When Admin sends GET request to retrieve the batch
Then Admin receives expected status code with error message

@GetByProgramId
Scenario: Check if Admin is able to retrieve batch with programId without authorization 
Given Admin create GET request to retrieve batch by programId without auth
When Admin sends GET request to retrieve the batch
Then Admin receives expected status code with error message

@GetByProgramId
Scenario: Check if Admin is able to retrieve batch with programId and invalid endpoint 
Given Admin create GET request to retrieve batch by programId with invalid endpoint
When Admin sends GET request to retrieve the batch
Then Admin receives expected status code with error message
