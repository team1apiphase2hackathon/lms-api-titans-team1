#Author: Mathumathi

@skill
Feature: Skill Master

Background:
Given Admin Authoization to Bearer token

   @wip 

  Scenario: Check if admin able to create a New Skill Master with valid endpoint and request body (non existing values)
    Given Admin creates POST Request for the LMS API endpoint
    When Admin sends HTTPS Request and  request Body with mandatory 
    Then Admin receives 201 Created Status with response body.    
   

   Scenario: Check if admin able to create a New Skill Master with valid endpoint and request body (existing values)
   Given Admin creates POST Request for the LMS API endpoint
   When Admin sends HTTPS Request and  request Body with mandatory and existing values
   Then Admin receives 400 Bad Request Status with message cannot create skillMaster , since already exists
   
   #Scenario: Check if admin able to create a New Skill Master with valid endpoint and request body (missing some mandatory fields)
   #Given Admin creates POST Request for the LMS API endpoint
   #When Admin sends HTTPS Request and  request Body with some mandatory fields missing   
   #Then Admin receives 500 Error
   #
   
   #
   #Scenario: 
   #Given 
   #When 
   #Then 
   #
   #Scenario: 
   #Given 
   #When 
   #Then 
   #
   #Scenario: 
   #Given 
   #When 
   #Then 
   #
   #Scenario: 
   #Given 
   #When 
   #Then 
   #
   #Scenario: 
   #Given 
   #When 
   #Then 