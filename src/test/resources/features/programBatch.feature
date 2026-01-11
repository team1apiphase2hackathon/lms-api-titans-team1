Feature: Program Batch module for LMS API 

Background:
Given Admin create request spec with base URL and authentication

Scenario: Check if Admin is able to create batch with valid name
Given Admin create POST request with valid batch name format
When Admin sends HTTPS request with valid endpoint
Then Admin receives success code with response body

