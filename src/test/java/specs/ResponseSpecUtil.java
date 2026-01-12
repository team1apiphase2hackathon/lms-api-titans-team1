package specs;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpecUtil {

    public static ResponseSpecification status(Integer code) {
        return new ResponseSpecBuilder()
                .expectStatusCode(code)
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();
    }


}
