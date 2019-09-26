package edu.baylor.ecs.ciljssa.app;

import edu.baylor.ecs.ciljssa.context.AnalysisContext;
import edu.baylor.ecs.ciljssa.context.AnalysisRequestContext;
import edu.baylor.ecs.ciljssa.context.AnalysisResultsContext;
import edu.baylor.ecs.ciljssa.app.response.ResponseOk;
import edu.baylor.ecs.ciljssa.app.response.IHandledResponse;
import edu.baylor.ecs.ciljssa.app.response.ResponseBad;
import edu.baylor.ecs.ciljssa.app.services.AnalysisService;
import edu.baylor.ecs.ciljssa.app.services.DirectoryService;
import edu.baylor.ecs.ciljssa.app.services.RetreivalService;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;

// TODO: Catch IllegalStateException for propper error handling. Unnecessary on correct code, but good practice
//       - consumes and produces tags for all mappings
//       - service annotations

@RestController
public class SourceSecController {


    private AnalysisService analysisService;
    private RetreivalService retreivalService;
    private DirectoryService directoryService;

    public SourceSecController(){
        analysisService = new AnalysisService(); // never used
        retreivalService = new RetreivalService();
        directoryService = new DirectoryService();
    }

    @RequestMapping("/handshake")
    public String handshake(){
        return "Greetings from Source Code Security Controller";
    }

    @PostMapping(value = "/analyze")
    public @ResponseBody IHandledResponse basicAnalysis(@RequestBody AnalysisRequestContext requestContext){
        AnalysisContext context = new AnalysisContext();
        try {
            context = retreivalService.retrieveContextFromPath(requestContext.getFilepath(), requestContext);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException handled in SourcesSecController.");
        }
        AnalysisResultsContext result = new AnalysisResultsContext();
        result.addAnalysisContext(context);
        result.setRequest(requestContext);
        result.setPath(requestContext.getFilepath()); // Redundant code if override setRequest to automate this
        return handleResult(result);
    }

    @PostMapping("/analyze/class")
    public @ResponseBody IHandledResponse analyzeClassFromFile(@RequestParam String name,
                                                               @RequestBody AnalysisRequestContext requestContext) {
        AnalysisContext context = new AnalysisContext();
        try {
            context = retreivalService.retrieveContextFromPath(requestContext.getFilepath(), requestContext);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException handled in SourcesSecController.");
        }
        AnalysisResultsContext result = new AnalysisResultsContext();
        context.filterByClass(name);
        result.addAnalysisContext(context);
        result.setRequest(requestContext);
        result.setPath(requestContext.getFilepath());
        return handleResult(result);
    }

    @PostMapping(value = "/analyze/directory")
    public @ResponseBody IHandledResponse getAllInDirectory(@RequestBody AnalysisRequestContext requestContext) {
        List<AnalysisContext> contexts = new ArrayList<>();
        AnalysisResultsContext result = new AnalysisResultsContext();
        try {
            List<File> files = directoryService.getFilesFromDirectory(requestContext.getFilepath());
            contexts = retreivalService.retrieveContextFromFiles(files, requestContext);
        } catch (NotDirectoryException e) {
            System.out.println("NotDirectoryException handled in SourceSecController.");
        }
        result.setContexts(contexts);
        result.setPath(requestContext.getFilepath());
        result.setRequest(requestContext);
        return handleResult(result);
    }

    @PostMapping(value = "/analyze/directory/smart")
    public @ResponseBody IHandledResponse getAllInDirectorySmart(@RequestBody AnalysisRequestContext requestContext) {
        List<AnalysisContext> contexts = new ArrayList<>();
        AnalysisResultsContext result = new AnalysisResultsContext();
        try {
            List<File> files = directoryService.getFilesFromDirectorySmart(requestContext.getFilepath());
            contexts = retreivalService.retrieveContextFromFiles(files, requestContext);
        } catch (NotDirectoryException e) {
            System.out.println("NotDirectoryException handled in SourceSecController.");
        }
        result.setContexts(contexts);
        result.setPath(requestContext.getFilepath());
        result.setRequest(requestContext);
        return handleResult(result);
    }

    private IHandledResponse handleResult(AnalysisResultsContext context) {
        if (context.succeeded()) {
            ResponseOk response = new ResponseOk(context);
            response.setHttpStatus(200);
            return response;
        } else {
            String[] message = new String[2];
            if (context.getFailedContexts() > 1) {
                message[0] = "There were multiple failed context requests. Failed requests: ";
                message[0] += context.getFailedContexts();
            } else if (context.getFailedContexts() == 1) {
                message[0] = "There was a failed context request.";
            } else if (context.getContexts().size() == 0) {
                message[0] = "The list of contexts failed to initialize.";
            }
            message[1] = "Check your request for typos and verify the path provided is a valid file path / directory.";
            ResponseBad response = new ResponseBad(context);
            response.setHttpStatus(500);
            response.setErrorMessage(message);
            return response;
        }
    }

}
