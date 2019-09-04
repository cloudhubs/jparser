package org.seer.ciljssa;

import org.seer.ciljssa.context.AnalysisContext;
import org.seer.ciljssa.context.AnalysisRequestContext;
import org.seer.ciljssa.context.AnalysisResultsContext;
import org.seer.ciljssa.services.AnalysisService;
import org.seer.ciljssa.services.RetreivalService;
import org.springframework.web.bind.annotation.*;

@RestController
public class SourceSecController {


    private AnalysisService analysisService;
    private RetreivalService retreivalService;

    public SourceSecController(){
        analysisService = new AnalysisService();
        retreivalService = new RetreivalService();
    }

    @RequestMapping("/handshake")
    public String handshake(){
        return "Greetings from Source Code Security Controller";
    }

    @PostMapping(value = "/analyze/basic_file")
    public @ResponseBody AnalysisResultsContext basicAnaalysis(@RequestBody AnalysisRequestContext requestContext){
        AnalysisContext context = retreivalService.retrieveFileFromPath(requestContext.getFilepath());

        AnalysisResultsContext results = analysisService.analyzeContext(context);
        return new AnalysisResultsContext();
    }

    @RequestMapping(value = "/analyze/directory/all_classes", method = RequestMethod.POST)
    public AnalysisResultsContext getAllClassesInDirectoryFiles(@RequestBody AnalysisRequestContext requestContext){
        AnalysisContext context = analysisService.getAllClassNames(requestContext.getFilepath());
        return new AnalysisResultsContext(context, requestContext);
    }

}
