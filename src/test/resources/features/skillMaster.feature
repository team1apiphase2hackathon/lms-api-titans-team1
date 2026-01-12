#Author: Mathumathi

@skill
Feature: Skill Master

Background:
Given Admin has a valid authorization token

  @post 
  Scenario: Check if admin able to create a New Skill Master with valid endpoint and request body (non existing values)
    When Admin sends HTTPS POST Request and  request Body with mandatory 
    Then Admin receives 201 Created Status with response body.    
   
	 @post
   Scenario: Check if admin able to create a New Skill Master with valid endpoint and request body (existing values)
   When Admin sends HTTPS POST Request and  request Body with mandatory and existing values
   Then Admin receives 400 Bad Request Status with message "cannot create skillMaster , since already exists"
   
   @post
   Scenario: Check if admin able to create a New Skill Master with valid endpoint and request body (missing some mandatory fields)
   When Admin sends HTTPS POST Request and  request Body with some mandatory fields missing   
   Then Admin receives 500 Error
   
   @getAll
   Scenario: Check if admin able to get all  Skill Master with valid endpoint 
   When Admin sends HTTPS GET Request 
   Then Admin receives 200 Status with response body
   
   @get
   Scenario: Check if admin able to get Skill Master Name with valid endpoint 
   When Admin sends HTTPS GET Request with SkillMasterName
   Then Admin receives 200 Status with response body 
   
   @get
   Scenario: Check if admin able to get Skill Master Name with invalid endpoint 
   When Admin sends HTTPS GET Request with invalid SkillMasterName
   Then Admin receives 404 Not Found Status with error message and success as "false"
   
   @put
   Scenario: Check if admin able to update New Skill Master with valid endpoint and request body
   When Admin sends HTTPS PUT Request and  request Body with mandatory  
   Then Admin receives 200 Status with updated response body.    
   
   @put
   Scenario: Check if admin able to update New Skill Master with invalid endpoint and request body
   When Admin sends HTTPS PUT Request and  request Body with mandatory with wrong skillID
   Then Admin receives 404 Not Found Status with error message
   
   @delete
   Scenario: Check if admin able to Delete  Skill ID  with valid endpoint 
   When Admin sends HTTPS DELETE Request 
   Then Admin receives 200 Status 
   
   @delete
   Scenario: Check if admin able to Delete  Skill ID  with invalid endpoint 
   When Admin sends HTTPS DELETE Request invalid skillID
   Then Admin receives 404 Error with response body "no record found with skillId"
  