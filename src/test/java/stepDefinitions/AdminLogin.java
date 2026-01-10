package stepDefinitions;

import config.ConfigReader;
import endpoints.AuthEndpoints;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import pojo.UserLogin;
import utils.TestDataUtil;

public class AdminLogin {

	  private Response response;
	  
	@Given("admin sets No Auth")
	public void admin_sets_no_auth() {
	
	}
	@Given("Admin creaes request with valid credentials")
	public void admin_creaes_request_with_valid_credentials() {
		 UserLogin loginPayload = new UserLogin(ConfigReader.get("login.username"), ConfigReader.get("login.password"));
	        response = AuthEndpoints.login(loginPayload);
	}
	@When("Admin calls Post Https method  with valid endpoint")
	public void admin_calls_post_https_method_with_valid_endpoint() {
	 
	}
	@Then("Admin receives {int} ok with auto generated token")
	public void admin_receives_ok_with_auto_generated_token(Integer statusCode) {
		   response.then().statusCode(statusCode);

	        String token = response.jsonPath().getString("token");
	        TestDataUtil.setToken(token);

	        System.out.println("🔐 TOKEN GENERATED FROM LOGIN FEATURE");
	}
	
	
}
