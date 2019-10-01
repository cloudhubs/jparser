package edu.baylor.ecs.ciljssa.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.baylor.ecs.ciljssa.context.AnalysisResultsContext;
import lombok.Data;

public class BadResponse extends BaseResponse {

    public BadResponse() {
        super(ResponseCode.INTERNAL_ERROR, null, ResponseType.FAIL);
    }

    public BadResponse(String response) {
        super(ResponseCode.INTERNAL_ERROR, response, ResponseType.FAIL);
    }

    public BadResponse(String response, ResponseCode code) {
        super(code, response, ResponseType.FAIL);
    }

    @Override
    public AnalysisResultsContext get() {
        return null;
    }

}
