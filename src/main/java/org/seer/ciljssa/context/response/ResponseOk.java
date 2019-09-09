package org.seer.ciljssa.context.response;

import org.seer.ciljssa.context.AnalysisResultsContext;

public class ResponseOk implements IHandledResponse {

    AnalysisResultsContext resultsContext;

    public ResponseOk(AnalysisResultsContext context) {
        this.resultsContext = context;

    }

    @Override
    public AnalysisResultsContext getResultContext() {
        return resultsContext;
    }

    @Override
    public boolean isOkResponse() {
        return false;
    }

    @Override
    public void setHttpStatus(int status) {

    }

    @Override
    public int getHttpStatus() {
        return 0;
    }
}
