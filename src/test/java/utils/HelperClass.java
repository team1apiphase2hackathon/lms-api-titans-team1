package utils;

import http.ApiRequest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class HelperClass {

	//clean up method for negative test 
	public static void deleteIfCreated(Response response, RequestSpecification requestSpec, String endpointTemplate) {
        int statusCode = response.getStatusCode();
        if (statusCode == 201) {
            String userId = response.jsonPath().getString("user.userId");
            String deleteEndpoint = endpointTemplate.replace("{userID}", userId);
            Response deleteResponse = ApiRequest.sendRequest(requestSpec, "DELETE", deleteEndpoint);
            System.out.println("DELETE Response Status: " + deleteResponse.getStatusCode());
        }
	}
        
}
