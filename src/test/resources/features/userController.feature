#Author: Mathumathi

@userController
Feature: User Controller

  @post
  Scenario: Check if admin able to create a new admin with valid endpoint and request body 
    When Admin sends HTTPS POST Request and  request Body with mandatory and additional fields                                                                                                                                                 
    Then Admin receives 201 Created Status with response body
   
    @post
   Scenario: Check if admin able to create a admin with valid endpoint and request body and existing values in phone no
    When Admin sends HTTPS POST Request and  request Body with mandatory and additional fields with existing phone number                               
    Then Admin receives 400 Bad Request Status with message and boolean success details

   @post
   Scenario: Check if admin able to create a admin missing mandatory fields in request body
    When Admin sends HTTPS POST Request and request Body with missing mandatory fields
    Then Admin receives 400 Bad Request Status with message and boolean success details
    
    @get
  Scenario: Check if admin able to retrieve all admin with valid LMS API
    When Admin sends GET Request for the LMS API All admin endpoint
    Then Admin receives 200 OK Status with response body

 @get
	 Scenario: Check if admin able to retrieve a admin with valid admin ID
    When Admin sends GET Request for the LMS API endpoint with valid admin ID
    Then Admin receives 200 OK Status with response body
    
     @get
   Scenario: Check if admin able to retrieve a admin with invalid admin ID
    When Admin sends GET Request for the LMS API endpoint with invalid admin ID
    Then Admin receives 404 Not Found Status with message and boolean success details
    
     @get
   Scenario: Check if admin able to retrieve a admin with valid LMS API
    When Admin sends GET Request for the LMS API All Staff endpoint
    Then Admin receives 200 OK Status with response body
    
     @get
   Scenario: Check if admin able to retrieve a admin with valid LMS API
    When Admin sends GET Request for the LMS API admin Roles endpoint
    Then Admin receives 200 OK Status with response body
    
    @put
   Scenario: Check if admin able to update a admin with valid admin ID and request body
    When Admin sends HTTPS PUT Request and  request Body with mandatory and additional fields and valid adminID                                 
    Then Admin receives 200 OK Status with response body
    
    @put
   Scenario: Check if admin able to update a admin with invalid admin Id and request body
    When Admin sends HTTPS PUT Request and  request Body with mandatory and additional fields and invalid adminID         
    Then Admin receives 404 Not Found Status with message and boolean success details
    
    @put
   Scenario: Check if admin able to update a admin with valid admin ID and missing mandatory fields request body
    When Admin sends HTTPS PUT Request  and request Body with missing mandatory fields
    Then Admin receives 400 Bad Request Status with message and boolean success details
    
    @put
   Scenario: Check if admin able to update a admin with valid admin Id and request body
    When Admin sends HTTPS PUT Request and request Body with mandatory fields and valid adminID and Mandatory Role ID and Role status                                                           
    Then Admin receives 200 OK Status with response body
    
    @put
   Scenario: Check if admin able to update a admin with invalid admin Id and request body
    When Admin sends HTTPS PUT Request and request Body with mandatory fields and invalid adminID and Mandatory Role ID and Role status                                                              
    Then Admin receives 200 OK Status with response body
    
    @put
   Scenario: Check if admin able to update a admin with valid admin Id and request body (missing field)
    When Admin sends HTTPS PUT Request and request Body with missing mandatory fields and Mandatory Role ID and Role status                                                               
    Then Admin receives 404 Not Found Status with message and boolean success details
    
    @put
   Scenario: Check if admin able to assign admin to program / batch with valid admin Id and request body
    When Admin sends HTTPS PUT Request and request Body with mandatory fields and valid adminID and Mandatory program Id, batch Id ,role id, admin id, admin role program batch status                       
    Then Admin receives 400 Bad Request Status with message and boolean success details
    
    @put
   Scenario: Check if admin able to  assign admin to program / batch with invalid admin Id and request body
    When Admin sends HTTPS PUT Request and request Body with mandatory fields and invalid adminID and Mandatory program Id, batch Id ,role id, admin id, admin role program batch status                          
    Then Admin receives 200 OK Status with response body
    
    @put
   Scenario:  Check if admin able to  assign admin to program / batch with valid admin Id and request body (missing field)
    When Admin sends HTTPS PUT Request and request Body with missing mandatory fields and valid adminID                                              
    Then Admin receives 400 Bad Request Status with message and boolean success details
    
    @delete
   Scenario: Check if admin able to delete a admin with valid admin Id
    When Admin sends DELETE Request for the LMS API endpoint  and  valid admin ID
    Then Admin receives 200 OK Status with response body
    
    @delete
   Scenario: Check if admin able to delete a admin with valid LMS API,invalid admin Id
    When Admin sends DELETE Request for the LMS API endpoint  and  invalid admin ID
    Then Admin receives 404 Not Found Status with message and boolean success details
