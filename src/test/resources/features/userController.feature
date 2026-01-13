#Author: Mathumathi

@userController
Feature: User Controller

Background:
Given Admin has a valid authorization token in user controller

  @post
   Scenario: Check if admin able to create a admin with valid endpoint and request body with only mandatory fields
    When Admin sends HTTPS POST Request and request Body with mandatory fields and admin role                          
    Then Admin receives 201 Created Status with response body
    
    
  @post
  Scenario Outline: Check if admin able to create a new admin with valid endpoint and request body with different roles
    When Admin sends HTTPS POST Request and request Body with mandatory and additional fields with "<roles>"                                                                                                                                           
    Then Admin receives 201 Created Status with response body
    Examples:
    |roles	|
    |AdminRole		|
    |StaffRole		|
    |StudentRole		|
   
    @post @wip
    Scenario Outline: Check if admin able to create a new admin with valid endpoint and request body with different visa status
    When Admin sends HTTPS POST Request and request Body with mandatory and additional fields with diff"<visaStatus>"                                                                                                                                            
    Then Admin receives 201 Created Status with response body
    Examples:
    |visaStatus	|
    |NotSpecifiedVisa	|
    |NAVisa	|
    |GCEADVisa		|
    |H4EADVisa		|
    | H4Visa		|
    |H1BVisa		|
    | CanadaEADVisa|
    |IndianCitizenVisa	|
    | USCitizenVisa		|
    | CanadaCitizen	|
   
    #Scenario Outline: Check if admin able to create a new admin with valid endpoint and request body with different time zones
    #When Admin sends HTTPS POST Request and request Body with mandatory and additional fields with <timeZone>                                                                                                                                             
    #Then Admin receives 201 Created Status with response body
    #Examples:
    #|timeZone	|
    #|PST		|
    #|MST		|
    #|CST		|
    #|EST		|
    #| IST		|
#
    #
    #@post
   #Scenario: Check if admin able to create a admin with valid endpoint and request body and existing values in phone no
    #When Admin sends HTTPS POST Request and request Body with mandatory and additional fields with existing phone number                               
    #Then Admin receives 400 Bad Request Status with message and boolean success details
#
   #@post
   #Scenario: Check if admin able to create a admin with valid endpoint and request body and with existing values in userLoginEmail
    #When Admin sends HTTPS POST Request and request Body with mandatory and additional fields with existing userLoginEmail                               
    #Then Admin receives 201 Created Status with response body
#
   #@post
   #Scenario Outline: Check if admin able to create a admin missing mandatory fields in request body
    #When Admin sends HTTPS POST Request and request Body with missing <mandatoryField>
    #Then Admin receives 400 Bad Request Status with message and boolean success details
    #Examples:
  #| mandatoryField                       |
  #| CreateAdmin_valid_missingFirstName |
  #| CreateAdmin_valid_missingLastName  |
  #| CreateAdmin_valid_missingLocation		|
#	| CreateAdmin_valid_missingTimezone		|
  #| CreateAdmin_valid_missingVisaStatus	|
#	| CreateAdmin_valid_missingRoleId			|
  #| CreateAdmin_valid_missingRoleStatus  |
#	| CreateAdmin_valid_missinguserLogin	|
    #
    #@post
   #Scenario Outline: Check if admin able to create a admin missing additional fields in request body
    #When Admin sends HTTPS POST Request and request Body with missing <additionalField>
    #Then Admin receives 201 Created Status with response body
    #Examples:
  #| additionalField                       |
  #| CreateAdmin_valid_missinguserComments |
  #| CreateAdmin_valid_missinguserEduPg  |
   #| CreateAdmin_valid_missinguserEduUg  |
  #| CreateAdmin_valid_missinguserLinkedinUrl		|
#	| CreateAdmin_valid_missinguserMiddleName		|
  #| CreateAdmin_valid_missinguserPhoneNumber	|
#
#	
    #@get
  #Scenario: Check if admin able to retrieve all admin with valid LMS API
    #When Admin sends GET Request for the LMS API All admin endpoint
    #Then Admin receives 200 OK Status with response body
#
 #@get
#	 Scenario: Check if admin able to retrieve a admin with valid admin ID
    #When Admin sends GET Request for the LMS API endpoint with valid admin ID
    #Then Admin receives 200 OK Status with response body
    #
     #@get
   #Scenario: Check if admin able to retrieve a admin with invalid admin ID
    #When Admin sends GET Request for the LMS API endpoint with invalid admin ID
    #Then Admin receives 404 Not Found Status with message and boolean success details
    #
     #@get
   #Scenario: Check if admin able to retrieve a admin with valid LMS API
    #When Admin sends GET Request for the LMS API All Staff endpoint
    #Then Admin receives 200 OK Status with response body
    #
     #@get
   #Scenario: Check if admin able to retrieve a admin with valid LMS API
    #When Admin sends GET Request for the LMS API admin Roles endpoint
    #Then Admin receives 200 OK Status with response body
    #
    #@put
   #Scenario: Check if admin able to update a admin with valid admin ID and request body
    #When Admin sends HTTPS PUT Request and  request Body with mandatory and additional fields and valid adminID                                 
    #Then Admin receives 200 OK Status with response body
    #
    #@put
   #Scenario: Check if admin able to update a admin with invalid admin Id and request body
    #When Admin sends HTTPS PUT Request and  request Body with mandatory and additional fields and invalid adminID         
    #Then Admin receives 404 Not Found Status with message and boolean success details
    #
    #@put
   #Scenario: Check if admin able to update a admin with valid admin ID and missing mandatory fields request body
    #When Admin sends HTTPS PUT Request  and request Body with missing mandatory fields
    #Then Admin receives 400 Bad Request Status with message and boolean success details
    #
    #@put
   #Scenario: Check if admin able to update a admin with valid admin Id and request body
    #When Admin sends HTTPS PUT Request and request Body with mandatory fields and valid adminID and Mandatory Role ID and Role status                                                           
    #Then Admin receives 200 OK Status with response body
    #
    #@put
   #Scenario: Check if admin able to update a admin with invalid admin Id and request body
    #When Admin sends HTTPS PUT Request and request Body with mandatory fields and invalid adminID and Mandatory Role ID and Role status                                                              
    #Then Admin receives 200 OK Status with response body
    #
    #@put
   #Scenario: Check if admin able to update a admin with valid admin Id and request body (missing field)
    #When Admin sends HTTPS PUT Request and request Body with missing mandatory fields and Mandatory Role ID and Role status                                                               
    #Then Admin receives 404 Not Found Status with message and boolean success details
    #
    #@put
   #Scenario: Check if admin able to assign admin to program / batch with valid admin Id and request body
    #When Admin sends HTTPS PUT Request and request Body with mandatory fields and valid adminID and Mandatory program Id, batch Id ,role id, admin id, admin role program batch status                       
    #Then Admin receives 400 Bad Request Status with message and boolean success details
    #
    #@put
   #Scenario: Check if admin able to  assign admin to program / batch with invalid admin Id and request body
    #When Admin sends HTTPS PUT Request and request Body with mandatory fields and invalid adminID and Mandatory program Id, batch Id ,role id, admin id, admin role program batch status                          
    #Then Admin receives 200 OK Status with response body
    #
    #@put
   #Scenario:  Check if admin able to  assign admin to program / batch with valid admin Id and request body (missing field)
    #When Admin sends HTTPS PUT Request and request Body with missing mandatory fields and valid adminID                                              
    #Then Admin receives 400 Bad Request Status with message and boolean success details
    #
    @delete
   Scenario: Check if admin able to delete a admin with valid admin Id
    When Admin sends DELETE Request for the LMS API endpoint  and  valid admin ID
    Then Admin receives 200 OK Status with response body
    #
    #@delete
   #Scenario: Check if admin able to delete a admin with valid LMS API,invalid admin Id
    #When Admin sends DELETE Request for the LMS API endpoint  and  invalid admin ID
    #Then Admin receives 404 Not Found Status with message and boolean success details
