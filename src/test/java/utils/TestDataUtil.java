package utils;

import config.ConfigReader;
import endpoints.LoginSendRequest;
import pojo.UserLogin;

public class TestDataUtil {

	 private static String token;
	  private static String skillName;
	  
	  
	  public static void setSkillName(String name) {
	        skillName = name;
	    }

	    public static String getSkillName() {
	        return skillName;
	    }
	    

	    public static synchronized String getToken() {

	        if (token == null) {

	            System.out.println("🔐 Token not found. Logging in automatically...");

	            UserLogin validPayload = new UserLogin(
	                    ConfigReader.get("login.username"),
	                    ConfigReader.get("login.password")
	            );

	            token = LoginSendRequest.login(validPayload)
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
