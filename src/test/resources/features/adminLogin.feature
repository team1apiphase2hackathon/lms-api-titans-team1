Feature: Verify User Login Controller

  @SignInTests @DataDriven
  Scenario Outline: Verify User Sign In security and validation rules
    Given Admin has the test data for "<ScenarioName>" from Excel with No Auth
    When Admin sends the post request for User Sign In
    Then Admin should receive the status code as defined in Excel
    Then the response should match the expected validation message

    Examples: 
      | ScenarioName                     |
      | Postrequest_Valid credential     |
      | Postrequest_InvalidBaseURL       |
      | Postrequest_InvalidContenttype   |
      | Postrequest_InvalidEndpoint      |
      | Postrequest_SpecialCharsEmail    |
      | Postrequest_SpecialCharsPassword |
      | Postrequest_NumbersEmail         |
      | Postrequest_NumbersPassword      |
      | Postrequest_NullPassword         |
      | Postrequest_NullEmail            |
      | Postrequest_NullBody             |

  @InvalidMethod
  Scenario: Verify login with Invalid Method
    Given Admin has the test data for "Postrequest_Invalidmethod" from Excel with No Auth
    When Admin sends a GET request instead of POST for SignIn InvalidMethod
    Then Admin should receive the status code matches with Expected statuscode

  @ForgotPassword @DataDriven
  Scenario Outline: Verify Forgot Password functionality
    Given Admin has the test data for "<ScenarioName>" from Excel with No Auth
    When Admin sends the post request for ForgotPassword
    Then Admin should receive the status code as defined in Excel
    Then the response should match the expected validation message

    Examples: 
      | ScenarioName                      |
      | ForgotPassword_ValidCredential    |
      | ForgotPassword_InvalidEndpoint    |
      | ForgotPassword_SpecialCharsEmail  |
      | ForgotPassword_InvalidAdminEmail  |
      | ForgotPassword_NullBody           |
      | ForgotPassword_InvalidContentType |

  @Logout @DataDriven
  Scenario Outline: Verify Logout functionality with Auth
    Given Admin has the test data for "<ScenarioName>" from Excel with Bearer Token
    When Admin sends a GET request for Logout
    Then Admin should receive the status code matches with Expected statuscode

    Examples: 
      | ScenarioName           |
      | Logout_Valid           |
      | Logout_InvalidBaseURL  |
      | Logout_InvalidEndpoint |

  @Logout @InvalidMethod
  Scenario: Verify logout with Invalid Method
    Given Admin has the test data for "Logout_InvalidMethod" from Excel with Bearer Token
    When Admin sends a POST request instead of GET for logoutInvalidMethod
    Then Admin should receive the status code matches with Expected statuscode

  @Logout @NoAuth
  Scenario: Verify Logout functionality without Auth
    Given Admin has the test data for "Logout_NoAuth" from Excel with No Auth
    When Admin sends a GET request for Logout noauth
    Then Admin should receive the status code matches with Expected statuscode

  @ResetPassword @DataDriven
  Scenario Outline: Verify Reset Password functionality
    Given Admin has the test data for "<ScenarioName>" from Excel with Bearer Token
    When Admin sends a POST request with Authorization for ResetPassword 
    Then Admin should receive the status code matches with Expected statuscode

    Examples: 
      | ScenarioName                     |
      | ResetPassword_Valid              |
      | ResetPassword_SpecialChars       |
      | ResetPassword_InvalidBaseURL     |
      | ResetPassword_InvalidEndpoint    |
      | ResetPassword_InvalidContentType |
      | ResetPassword_InvalidEmail       |

  @ResetPassword @NoAuth
  Scenario: Verify login with old password after reset
    Given Admin has the test data for "ResetPassword_LoginOldPassword" from Excel with No Auth
    When Admin sends the post request for ResetPasswordNoAuth
    Then Admin should receive the status code matches with Expected statuscode

  @ResetPassword @InvalidMethod
  Scenario: Verify reset password with Invalid Method
    Given Admin has the test data for "ResetPassword_InvalidMethod" from Excel with Bearer Token
    When Admin sends a GET request for Reset Password ResetPasswordInvalidMethod
    Then Admin should receive the status code matches with Expected statuscode