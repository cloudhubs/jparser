package edu.baylor.ecs.ciljssa.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.baylor.ecs.ciljssa.context.AnalysisResultsContext;
import lombok.Data;

@Data
public class ResponseBad extends BaseResponse implements IHandledResponse {

    @JsonProperty(value = "results")
    private AnalysisResultsContext resultsContext;
    @JsonProperty(value = "http_status")
    private int httpStatus;
    @JsonProperty(value = "error_message")
    private String[] message;

    public ResponseBad(AnalysisResultsContext context) {
        this.resultsContext = context;
    }

    public ResponseBad(){};

    public void setErrorMessage(String[] message) {
        this.message = message;
    }

    @Override
    public AnalysisResultsContext getResultsContext() {
        return resultsContext;
    }

    @Override
    public boolean isOkResponse() {
        return false;
    }

    @Override
    public void setHttpStatus(int status) {
        this.httpStatus = status;
    }

    @Override
    public int getHttpStatus() {
        return this.httpStatus;
    }
}
