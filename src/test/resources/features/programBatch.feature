Feature: Program Batch module for LMS API 

Scenario: Check if Admin is able to create batch with valid batch name and description
Given Admin create POST request with valid "<batch_name>" format and "<batch_description>"
When Admin sends POST request to create program batch
Then Admin receives created status with response body

Examples:
| batch_name | batch_description |
| SDETTeam001 | abcd     |
| SDETTeam002 | ProgramBatchDescription12 |
| SDETTeam003 | TestBatch11  |

Scenario: Check if Admin is able to create batch with valid request body and without authorization
Given Admin create POST request with valid request body and no authorization
When Admin sends POST request to create program batch
Then Admin receives expected status code with error message

Scenario: Check if Admin is able to create batch with existing batch name 
Given Admin create POST request with existing batch name
When Admin sends POST request to create program batch
Then Admin receives expected status code with error message

Scenario: Check if Admin is able to create batch with missing mandatary fields
Given Admin create POST request with missing mandataory fields
When Admin sends POST request to create program batch
Then Admin receives expected status code with error message

Scenario: Check if Admin is able to create batch with invalid endpoint
Given Admin create POST request with invalid endpoint
When Admin sends POST request to create program batch
Then Admin receives expected status code with error message

Scenario: Check if Admin is able to create batch with missing additional fields
Given Admin create POST request with missing additional fields
When Admin sends POST request to create program batch
Then Admin receives created status with response body

Scenario: Check if Admin is able to create batch with invalid data in request body 
Given Admin create POST request with invalid data in request body
When Admin sends POST request to create program batch
Then Admin receives expected status code with error message

Scenario: Check if Admin is able to create batch with inactive programId 
Given Admin create POST request with inactive program Id
When Admin sends POST request to create program batch
Then Admin receives expected status code with error message
