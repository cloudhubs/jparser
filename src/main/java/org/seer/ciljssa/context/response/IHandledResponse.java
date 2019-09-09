package org.seer.ciljssa.context.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.seer.ciljssa.context.AnalysisResultsContext;

public interface IHandledResponse {

    @JsonProperty(value = "http_response")
    int httpStatus = 500;

    AnalysisResultsContext getResultContext();
    boolean isOkResponse();
    void setHttpStatus(int status);
    int getHttpStatus();

}
