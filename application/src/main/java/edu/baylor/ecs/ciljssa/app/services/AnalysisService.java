package edu.baylor.ecs.ciljssa.app.services;

import edu.baylor.ecs.ciljssa.context.AnalysisContext;
import edu.baylor.ecs.ciljssa.context.AnalysisRequestContext;
import edu.baylor.ecs.ciljssa.context.AnalysisResultsContext;
import lombok.Data;
import lombok.NoArgsConstructor;


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
