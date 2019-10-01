package edu.baylor.ecs.ciljssa.app.services;

import edu.baylor.ecs.ciljssa.context.AnalysisContext;
import edu.baylor.ecs.ciljssa.app.context.RequestContext;
import edu.baylor.ecs.ciljssa.app.context.AnalysisResultsContext;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;


@Data
@NoArgsConstructor
@Service
public class AnalysisService {

    //TODO: This service seems to just kind of wrap other methods. Put the actual analysis in this

    public AnalysisContext basicAnalysis(RequestContext requestContext){
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
