package org.seer.ciljssa.services;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.seer.ciljssa.context.AnalysisContext;
import org.seer.ciljssa.context.AnalysisRequestContext;
import org.seer.ciljssa.context.AnalysisResultsContext;


@Data
@NoArgsConstructor
public class AnalysisService {

    //TODO: This service seems to just kind of wrap other methods. Put the actual analysis in this

    public AnalysisContext basicAnalysis(AnalysisRequestContext requestContext){
        AnalysisContext results = new AnalysisContext();
        return results;
    }

    public AnalysisContext getAllClassNames(String filepath) {
        return new AnalysisContext();
    }

    public AnalysisResultsContext analyzeContext(AnalysisContext context) {
        AnalysisResultsContext results = new AnalysisResultsContext();
        return results;
    }
}
