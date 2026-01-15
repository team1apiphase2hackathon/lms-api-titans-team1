#Author: Mathumathi

@userController
Feature: User Controller

Background:
Given Admin has a valid authorization token in user controller

  @post 
   Scenario: Check if admin able to create a admin with valid endpoint and request body with only mandatory fields
    When Admin sends HTTPS POST Request and request Body with mandatory fields and admin role                          
    Then Admin receives 201 Created Status with response body
    
   @post @e2e
   Scenario: Check if admin able to create a admin with valid endpoint and request body with multiple roles
    When Admin sends HTTPS POST Request and request Body with multiple roles                          
    Then Admin receives 201 Created Status with response body and schema validation
    
  @post
  Scenario Outline: Check if admin able to create a new admin with valid endpoint and request body with different roles
    When Admin sends HTTPS POST Request and request Body with mandatory and additional fields with "<roles>"                                                                                                                                           
    Then Admin receives 201 Created Status with response body
    Examples:
    |roles	|
    |CreateUserWithAdminRole		|
    |CreateUserWithStaffRole		|
    |CreateUserWithStudentRole		|
   
    @post
    Scenario Outline: Check if admin able to create a new admin with valid endpoint and request body with different visa status
    When Admin sends HTTPS POST Request and request Body with mandatory and additional fields with diff"<visaStatus>"                                                                                                                                            
    Then Admin receives 201 Created Status with response body
    Examples:
    |visaStatus	|
    |CreateUserWithNotSpecifiedVisa	|
    |CreateUserWithNAVisa	|
    |CreateUserWithGCEADVisa		|
    |CreateUserWithH4EADVisa		|
    | CreateUserWithH4Visa		|
    |CreateUserWithH1BVisa		|
    | CreateUserWithCanadaEADVisa|
    |CreateUserWithIndianCitizenVisa	|
    | CreateUserWithUSCitizenVisa		|
    | CreateUserWithCanadaCitizen	|
   
   @post
    Scenario Outline: Check if admin able to create a new admin with valid endpoint and request body with different time zones
    When Admin sends HTTPS POST Request and request Body with mandatory and additional fields with different "<timeZone>"                                                                                                                                      
    Then Admin receives 201 Created Status with response body
    Examples:
    |timeZone	|
    |CreateUserWithPST		|
    |CreateUserWithMST		|
    |CreateUserWithCST		|
    |CreateUserWithEST		|
    | CreateUserWithIST		|

    
    @post  
   Scenario: Check if admin able to create a admin with valid endpoint and request body and existing values in phone no
    When Admin sends HTTPS POST Request and request Body with mandatory and additional fields with existing phone number                               
    Then Admin receives 400 Bad Request Status with phone number existing message

   @post 
   Scenario: Check if admin able to create a admin with valid endpoint and request body and with existing values in userLoginEmail
    When Admin sends HTTPS POST Request and request Body with mandatory and additional fields with existing userLoginEmail                               
    Then Admin receives 201 Created Status with response body

   @post 
   Scenario Outline: Check if admin able to create a admin missing mandatory fields in request body
    When Admin sends HTTPS POST Request and request Body with missing mandatory "<mandatoryField>"
    Then Admin receives 400 Bad Request Status with message
    Examples:
  | mandatoryField                       |
  | CreateAdmin_valid_missingFirstName |
  | CreateAdmin_valid_missingLastName  |
  | CreateAdmin_valid_missingLocation		|
	| CreateAdmin_valid_missingTimezone		|
  | CreateAdmin_valid_missingVisaStatus	|
	| CreateAdmin_valid_missingRoleId			|
  | CreateAdmin_valid_missingRoleStatus  |
	| CreateAdmin_valid_missinguserLogin	|
    
    @post
   Scenario Outline: Check if admin able to create a admin missing additional fields in request body
    When Admin sends HTTPS POST Request and request Body with missing additional "<additionalField>"
    Then Admin receives 201 Created Status with response body
    Examples:
  | additionalField                       |
  | CreateAdmin_valid_missinguserComments |
  | CreateAdmin_valid_missinguserEduPg  |
   | CreateAdmin_valid_missinguserEduUg  |
  | CreateAdmin_valid_missinguserLinkedinUrl		|
	| CreateAdmin_valid_missinguserMiddleName		|
  | CreateAdmin_valid_missinguserPhoneNumber	|
  
    @put 
   Scenario: Check if admin able to update a admin with valid admin ID and request body
    When Admin sends HTTPS PUT Request and  request Body with mandatory and additional fields and valid adminID                                 
    Then Admin receives 200 OK Status with updated response
    
    @put 
   Scenario: Check if admin able to update a admin with invalid admin Id and request body
    When Admin sends HTTPS PUT Request and  request Body with mandatory and additional fields and invalid adminID         
    Then Admin receives 404 Not Found Status with message
    
    @put 
   Scenario Outline: Check if admin able to update a admin with valid admin ID and missing mandatory fields request body
    When Admin sends HTTPS PUT Request and request Body with missing "<missingmandatory>" fields
    Then Admin receives 400 Bad Request Status with missing message
    Examples:
  | missingmandatory                       |
  | UpdateAdmin_valid_missingFirstName |
  | UpdateAdmin_valid_missingLastName  |
  | UpdateAdmin_valid_missingPhoneNumber	|
	| UpdateAdmin_valid_missingTimezone		|
  | UpdateAdmin_valid_missingVisaStatus	|

    
       @put 
   Scenario: Check if admin able to update a admin with valid admin ID and only mandatory fields request body
    When Admin sends HTTPS PUT Request and request Body with only mandatory fields and valid adminID                                 
    Then Admin receives 200 OK Status
    
    
    @put 
   Scenario: Check if admin able to update a admin with valid admin Id and request body
    When Admin sends HTTPS PUT Request and request Body with mandatory fields and valid adminID and Mandatory Role ID and Role status                                                           
    Then Admin receives 200 OK Status with response body
    
    @put 
   Scenario: Check if admin able to update a admin with invalid admin Id and request body
    When Admin sends HTTPS PUT Request and request Body with mandatory fields and invalid adminID and Mandatory Role ID and Role status                                                              
    Then Admin receives 404 Not Found Status with message
    
    @put 
   Scenario Outline: Check if admin able to update a admin with valid admin Id and request body (missing field)
    When Admin sends HTTPS PUT Request and request Body with missing role "<roleFields>"                                                               
    Then Admin receives 400 Bad Request Status with missing role field message
    Examples:
    |roleFields|
    |UpdateAdminRoleStatus_Valid_missingRoleId|
    |UpdateAdminRoleStatus_Valid_missingRoleStatus|
    
    @put @e2e
   Scenario: Check if admin able to assign admin to program / batch with valid admin Id and request body
    When Admin sends HTTPS PUT Request and request Body with mandatory fields and valid adminID and Mandatory program Id, batch Id ,role id, admin id, admin role program batch status                       
    Then Admin receives 200 OK Status with response body and message
    
    @put
   Scenario: Check if admin able to  assign admin to program / batch with invalid admin Id and request body
    When Admin sends HTTPS PUT Request and request Body with mandatory fields and invalid adminID and Mandatory program Id, batch Id ,role id, admin id, admin role program batch status                          
    Then Admin receives 404 Not Found Status with message
    
    @put 
   Scenario Outline:  Check if admin able to  assign admin to program / batch with valid admin Id and request body (missing field)
    When Admin sends HTTPS PUT Request and request Body with missing mandatory fields program batch "<fields>"                                              
    Then Admin receives 400 Bad Request Status with message details
    Examples:
    |fields|
    |UpdateAdminProgramBatch_Valid_missingProgramId|
    |UpdateAdminProgramBatch_Valid_missingRoleId|
    |UpdateAdminProgramBatch_Valid_missingBatchId|
    |UpdateAdminProgramBatch_Valid_missinguserRoleStatus|
    
    
     @put
   Scenario: Check if user able to update loginEmail with valid userId
    When Admin sends HTTPS PUT Request and request Body with login email and valid adminID                       
    Then Admin receives 200 OK Status with response body and updated message
    
    @put 
   Scenario: Check if user able to update loginEmail with invalid userId
    When Admin sends HTTPS PUT Request and request Body with login email and invalid adminID
    Then Admin receives 404 Not Found Status with message
    
    @put 
   Scenario Outline:  Check if admin able to update loginEmail with missing field
    When Admin sends HTTPS PUT Request and request Body with missing fields "<loginEmailfields>"                                              
    Then Admin receives 400 Bad Request Status with message details
    Examples:
    |loginEmailfields|
    |UpdateAdminLoginEmail_MissingLoginEmail|
    |UpdateAdminLoginEmail_MissingLoginStatus|
    |UpdateAdminLoginEmail_MissingStatus|
    
   
     @get
  Scenario: Check if admin able to retrieve all users with valid LMS API
    When Admin sends GET Request for the LMS API All users endpoint
    Then Admin receives 200 OK Status with response body

  @get 
  Scenario: Check if admin able to retrieve all active users with valid LMS API
    When Admin sends GET Request for the LMS API All active users endpoint
    Then Admin receives 200 OK Status with response body

  @get 
  Scenario: Check if admin able to retrieve emails of all users with active status
    When Admin sends GET Request for the LMS API 
    Then Admin receives 200 OK Status with response body
    
    
     @get 
   Scenario: Check if admin able to retrieve a admin with valid LMS API
    When Admin sends GET Request for the LMS API admin Roles endpoint
    Then Admin receives 200 OK Status with response body
    
   @get 
	 Scenario Outline: Check if admin able to retrieve a admin with valid userID
    When Admin sends GET Request for the LMS API endpoint with valid "<userID>"
    Then Admin receives 200 OK Status with response body
    Examples:
    |userID|
    |GetAdminId|
    |GetStaffId|
    |GetStudentId|
    
     @get
   Scenario: Check if admin able to retrieve a admin with invalid user ID
    When Admin sends GET Request for the LMS API endpoint with invalid user ID
    Then Admin receives 404 Not Found Status with message
    
     @get
  Scenario: Check if admin able to retrieve all users with roles
    When Admin sends GET Request for the LMS API all users with roles
    Then Admin receives 200 OK Status with response body
    
     @get
  Scenario Outline: Check if admin able to retrieve all active and inactive users count
    When Admin sends GET Request for the LMS API all users count for "<User>"
    Then Admin receives 200 OK Status with response body
   Examples:
    |User|
    |Get_ActiveInactiveUsers_Count|
		|Get_All_ActiveInactiveUsers_Count|
		|Get_All_ActiveInactive_AdminUsers_Count|
		|Get_All_ActiveInactive_StaffUsers_Count|
		|Get_All_ActiveInactive_StudentUsers_Count|

     @get 
  Scenario: Check if admin able to retrieve user by program batchId
    When Admin sends GET Request for the LMS API user by program batchId
    Then Admin receives 200 OK Status with response body
    
       @get
  Scenario: Check if admin able to retrieve user by program
    When Admin sends GET Request for the LMS API user by program
    Then Admin receives 200 OK Status with response body
    
     @get 
   Scenario Outline: Check if admin able to retrieve user by roleId
    When Admin sends GET Request for the LMS API "<roleId>"
    Then Admin receives 200 OK Status with response body
    Examples:
    |roleId|
    |R01|
    |R02|
    |R03|
    
      @get 
  Scenario: Check if admin able to retrieve user by roleId V2
    When Admin sends GET Request for the LMS API user by roleId v2
    Then Admin receives 200 OK Status with response body
    
 
       @get
  Scenario: Check if admin able to retrieve user details by Id
    When Admin sends GET Request for the LMS API user details by Id
    Then Admin receives 200 OK Status with response body
    
    
          @get 
  Scenario: Check if admin able to retrieve batchId by userId
    When Admin sends GET Request for the LMS API batchId by userId
    Then Admin receives 200 OK Status with response body
    
    
 
       @delete 
   Scenario: Check if admin able to delete a admin with valid admin user
    When Admin sends DELETE Request for the LMS API endpoint  and  valid admin user
    Then Admin receives 200 OK Status with response body
    
       @delete 
   Scenario: Check if admin able to delete a admin with valid staff user
    When Admin sends DELETE Request for the LMS API endpoint  and  valid staff user
    Then Admin receives 200 OK Status with response body
    
       @delete 
   Scenario: Check if admin able to delete a admin with valid student user
    When Admin sends DELETE Request for the LMS API endpoint  and  valid student user
    Then Admin receives 200 OK Status with response body
    
    @delete
   Scenario: Check if admin able to delete a admin with valid LMS API,invalid admin Id
    When Admin sends DELETE Request for the LMS API endpoint  and  invalid admin ID
    Then Admin receives 404 Not Found Status with not found message
