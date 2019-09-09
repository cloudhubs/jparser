package org.seer.ciljssa.context.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.seer.ciljssa.context.AnalysisResultsContext;

public interface IHandledResponse {

    AnalysisResultsContext getResultsContext();
    boolean isOkResponse();
    void setHttpStatus(int status);
    int getHttpStatus();

}
