package org.seer.ciljssa.services;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.seer.ciljssa.context.AnalysisContext;
import org.seer.ciljssa.context.AnalysisRequestContext;
import org.seer.ciljssa.context.AnalysisResultsContext;


@Data
@NoArgsConstructor
public class AnalysisService {

    public AnalysisContext basicAnalysis(AnalysisRequestContext requestContext){
        AnalysisContext results = new AnalysisContext(requestContext);

        return results;
    }

    // TODO: Currently close to being able to read all class names from a directory. Need to wrap into a context.
    public AnalysisContext getAllClassNames(String filepath) {

        return new AnalysisContext();
    }

    public AnalysisResultsContext analyzeContext(AnalysisContext context) {
        AnalysisResultsContext results = new AnalysisResultsContext();
        return results;
    }
}
