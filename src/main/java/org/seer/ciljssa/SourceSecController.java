package org.seer.ciljssa;

import org.seer.ciljssa.context.AnalysisContext;
import org.seer.ciljssa.context.AnalysisRequestContext;
import org.seer.ciljssa.context.AnalysisResultsContext;
import org.seer.ciljssa.services.AnalysisService;
import org.seer.ciljssa.services.RetreivalService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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
        AnalysisContext context = retreivalService.retrieveFileFromPath(requestContext.getFilepath(), requestContext);
        AnalysisResultsContext result;
        // TODO: This doesn't actually catch a file that can't be found.
        if(context.isSucceeded()) {
            result = new AnalysisResultsContext(context, requestContext);
            result.setHttpResult(200);
        } else {
            result = new AnalysisResultsContext(new AnalysisContext(), requestContext);
            result.setHttpResult(500);
        }
        return result;
    }

    @RequestMapping(value = "/analyze/directory/all_classes", method = RequestMethod.POST)
    public AnalysisResultsContext getAllClassesInDirectoryFiles(@RequestBody AnalysisRequestContext requestContext){
        AnalysisContext context = analysisService.getAllClassNames(requestContext.getFilepath());
        AnalysisResultsContext result;
        if(context.isSucceeded()) {
            result = new AnalysisResultsContext(context, requestContext);
            result.setHttpResult(200);
        } else {
            result = new AnalysisResultsContext(new AnalysisContext(), requestContext);
            result.setHttpResult(500);
        }
        return result;
    }

}
