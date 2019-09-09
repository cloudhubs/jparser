package org.seer.ciljssa.context.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.seer.ciljssa.context.AnalysisResultsContext;

public class ResponseOk implements IHandledResponse {

    @JsonProperty(value = "results")
    private AnalysisResultsContext resultsContext;
    @JsonProperty(value = "http_response")
    int httpStatus;

    public ResponseOk(AnalysisResultsContext context) {
        this.resultsContext = context;
    }

    @Override
    public AnalysisResultsContext getResultsContext() {
        return resultsContext;
    }

    @Override
    public boolean isOkResponse() {
        return true;
    }

    @Override
    public void setHttpStatus(int status) {
        this.httpStatus = status;
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }
}
