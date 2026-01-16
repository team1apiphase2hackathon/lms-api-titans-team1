package httpRequest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiRequest {

	public static Response sendRequest(RequestSpecification spec, String method, String endpoint) {

		return RestAssured.given(spec).when().request(method, endpoint).andReturn();
	}

}
