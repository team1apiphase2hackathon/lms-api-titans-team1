package endpoints;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

import requestPojo.ProgramBatchRequest;
import responsePojo.ProgramBatchResponse;

public class ProgramBatchUtil {
	
	public static ProgramBatchRequest createBatchParseData(String payload) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ProgramBatchRequest createBatchData = mapper.readValue(payload, ProgramBatchRequest.class);
        return createBatchData;
	}
	
	public static void validateCreateBatchResponse(ProgramBatchResponse responseData) {
		
	}
}
