package org.seer.ciljssa.context;

import lombok.Data;

@Data
public class AnalysisResultsContext {

    private AnalysisContext context;

    private int httpCode;

    public AnalysisResultsContext(int httpCode){

    }

}
