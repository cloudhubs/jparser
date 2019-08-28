package org.seer.ciljssa;

import org.seer.ciljssa.context.AnalysisContext;
import org.seer.ciljssa.context.AnalysisRequestContext;
import org.seer.ciljssa.context.AnalysisResultsContext;
import org.seer.ciljssa.services.AnalysisService;
import org.seer.ciljssa.services.RetreivalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SourceSecController {

    @Autowired
    private AnalysisService analysisService;

    @Autowired
    private RetreivalService retreivalService;

    @RequestMapping("/handshake")
    public String handshake(){
        return "Greetings from Source Code Security Controller";
    }

    @RequestMapping(value = "/analyze/basic_file", method = RequestMethod.PUT)
    public AnalysisResultsContext basicAnaalysis(@RequestBody AnalysisRequestContext requestContext){
        AnalysisContext context = retreivalService.retreiveFileFromPath(requestContext.getFilepath());
        //context = analysisService.basicAnalysis(context);
        return new AnalysisResultsContext(200);
    }

    @RequestMapping(value = "/analyze/directory/all_classes", method = RequestMethod.POST)
    public AnalysisResultsContext getAllClassesInDirectoryFiles(@RequestBody AnalysisRequestContext requestContext){
        AnalysisContext context = analysisService.getAllClassNames(requestContext.getFilepath());
        return new AnalysisResultsContext(200);
    }

}
