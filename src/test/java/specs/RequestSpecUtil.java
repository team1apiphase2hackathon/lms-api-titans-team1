
package specs;

import config.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import utils.TestDataUtil;

public class RequestSpecUtil {



    public static RequestSpecification getRequestSpec() {

        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.get("base.url"))
                .addHeader("Authorization", "Bearer " + TestDataUtil.getToken())
                .addHeader("Content-Type", "application/json")
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();
    }
    
 
    public static RequestSpecification getRequestSpecWithoutAuth() {

        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.get("base.url"))
                .addHeader("Content-Type", "application/json")
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();
    }
    
}

