package endpoints;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import specs.RequestSpecUtil;

public class ApiRequest {

	public static Response sendRequest(String method, String endpoint, String payload) {
	    RequestSpecification spec = RequestSpecUtil.getRequestSpec();

	    switch (method.toUpperCase()) {
        case "POST":
        case "PUT":
            if (payload != null) {
                spec = spec.body(payload);
            }
            return given(spec).when().request(method, endpoint).then().log().ifValidationFails().extract().response();
        case "GET":
        case "DELETE":
            return given(spec).when().request(method, endpoint).then().log().ifValidationFails().extract().response();
        default:
            throw new IllegalArgumentException("Unsupported method: " + method);
	    }
	}
}
