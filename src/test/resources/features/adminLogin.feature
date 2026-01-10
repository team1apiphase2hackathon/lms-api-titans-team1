#Author: Remya

@login
Feature: Post request 

Background:
Given admin sets No Auth

  @postlogin
  Scenario: Check if admin able to generate token with valid credential
    Given Admin creaes request with valid credentials
    When Admin calls Post Https method  with valid endpoint
    Then Admin receives 200 ok with auto generated token   
