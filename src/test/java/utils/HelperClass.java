package utils;

import http.ApiRequest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class HelperClass {

	public static void deleteIfCreated(Response response, RequestSpecification requestSpec, String endpointTemplate) {
        int statusCode = response.getStatusCode();
        System.out.println("POST Response Status: " + statusCode);

        if (statusCode == 201) {
            // Extract userId from POST response
            String userId = response.jsonPath().getString("user.userId");
            System.out.println("Created User ID: " + userId);

            // Prepare DELETE endpoint
            String deleteEndpoint = endpointTemplate.replace("{userID}", userId);

            // Call DELETE request
            Response deleteResponse = ApiRequest.sendRequest(requestSpec, "DELETE", deleteEndpoint);
            System.out.println("DELETE Response Status: " + deleteResponse.getStatusCode());
        }
	}
        
}
