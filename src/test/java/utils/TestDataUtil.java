package utils;

import config.ConfigReader;
import endpoints.AuthEndpoints;
import pojo.UserLogin;

public class TestDataUtil {

	 private static String token;


	    public static synchronized String getToken() {

	        if (token == null) {

	            System.out.println("🔐 Token not found. Logging in automatically...");

	            UserLogin validPayload = new UserLogin(
	                    ConfigReader.get("login.username"),
	                    ConfigReader.get("login.password")
	            );

	            token = AuthEndpoints.login(validPayload)
	                    .then()
	                    .statusCode(200)
	                    .extract()
	                    .path("token");
	        }
	        return token;
	    }

	    public static void setToken(String authToken) {
	        token = authToken;
	    }

	    public static void clearToken() {
	        token = null;
	    }
	    
}
