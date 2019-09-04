package org.seer.ciljssa.context;

import lombok.Data;

@Data
public class AnalysisResultsContext {

    private AnalysisContext context;
    private AnalysisRequestContext request;

    private int httpResult;

    public AnalysisResultsContext(AnalysisContext context, AnalysisRequestContext request) {
        this.context = context;
        this.request = request;
    }

    public AnalysisResultsContext() {
        this.httpResult = 500;
    }

    public AnalysisResultsContext setStatus(int status) {
        httpResult = status;
        return this;
    }

}
