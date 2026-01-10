#Author: Mathumathi

@skill
Feature: Post

Background:
Given Admin Authoization to Bearer token

  @postSkill
  Scenario: Check if admin able to create a New Skill Master with valid endpoint and request body (non existing values)
    Given Admin creates POST Request for the LMS API endpoint
    When Admin sends HTTPS Request and  request Body with mandatory  
    Then Admin receives 201 Created Status with response body.    