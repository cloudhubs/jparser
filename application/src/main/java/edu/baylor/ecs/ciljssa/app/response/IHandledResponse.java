package edu.baylor.ecs.ciljssa.app.response;

import edu.baylor.ecs.ciljssa.context.AnalysisResultsContext;

public interface IHandledResponse {

    AnalysisResultsContext getResultsContext();
    boolean isOkResponse();
    void setHttpStatus(int status);
    int getHttpStatus();

}
