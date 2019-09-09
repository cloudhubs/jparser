package org.seer.ciljssa;

import org.seer.ciljssa.context.AnalysisContext;
import org.seer.ciljssa.context.AnalysisRequestContext;
import org.seer.ciljssa.context.AnalysisResultsContext;
import org.seer.ciljssa.services.AnalysisService;
import org.seer.ciljssa.services.DirectoryService;
import org.seer.ciljssa.services.RetreivalService;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
public class SourceSecController {


    private AnalysisService analysisService;
    private RetreivalService retreivalService;
    private DirectoryService directoryService;

    public SourceSecController(){
        analysisService = new AnalysisService();
        retreivalService = new RetreivalService();
        directoryService = new DirectoryService();
    }

    @RequestMapping("/handshake")
    public String handshake(){
        return "Greetings from Source Code Security Controller";
    }

    @PostMapping(value = "/analyze/getcontext")
    public @ResponseBody AnalysisResultsContext basicAnaalysis(@RequestBody AnalysisRequestContext requestContext){
        AnalysisContext context = retreivalService.retrieveContextFromPath(requestContext.getFilepath(), requestContext);
        AnalysisResultsContext result = new AnalysisResultsContext();

        // TODO: This doesn't actually catch a file that can't be found.
        result.addAnalysisContext(context);
        result.setRequest(requestContext);
        result.setPath(requestContext.getFilepath()); // Redundant code if override setRequest to automate this
        return result;
    }

    @PostMapping(value = "/analyze/directory/getcontext")
    public @ResponseBody AnalysisResultsContext getAllClassesInDirectoryFiles(@RequestBody AnalysisRequestContext requestContext){
        ArrayList<File> files = new ArrayList<>();
        ArrayList<AnalysisContext> contexts = new ArrayList<>();
        AnalysisResultsContext result = new AnalysisResultsContext();
        try {
            files = directoryService.getFilesFromDirectory(requestContext.getFilepath());
            contexts = retreivalService.retrieveContextFromFiles(files, requestContext);
        } catch (NotDirectoryException e) {
            e.printStackTrace();
        }
        result.setContexts(contexts);
        result.setPath(requestContext.getFilepath());
        result.setRequest(requestContext);
        return result;
    }

    private AnalysisResultsContext handleResult(AnalysisResultsContext context) {

    }

}
