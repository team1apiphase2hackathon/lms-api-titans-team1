package endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import requestPojo.ProgramRequest;

public class ProgramSendRequest {

    public static ProgramRequest createProgramParseData(String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
         ProgramRequest createProgramParseData =mapper.readValue(body, ProgramRequest.class );
        return createProgramParseData;
    }
    }

