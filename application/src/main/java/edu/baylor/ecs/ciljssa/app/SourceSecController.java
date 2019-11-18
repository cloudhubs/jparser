package edu.baylor.ecs.ciljssa.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.baylor.ecs.ciljssa.app.response.BadResponse;
import edu.baylor.ecs.ciljssa.app.response.BaseResponse;
import edu.baylor.ecs.ciljssa.app.response.OkResponse;
import edu.baylor.ecs.ciljssa.app.response.ResponseCode;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.impl.ClassComponent;
import edu.baylor.ecs.ciljssa.component.impl.DirectoryComponent;
import edu.baylor.ecs.ciljssa.component.context.AnalysisContext;
import edu.baylor.ecs.ciljssa.app.context.RequestContext;
import edu.baylor.ecs.ciljssa.app.context.AnalysisResultsContext;
import edu.baylor.ecs.ciljssa.app.services.DirectoryService;
import edu.baylor.ecs.ciljssa.app.services.RetreivalService;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO: Catch IllegalStateException for propper error handling. Unnecessary on correct code, but good practice
//       - consumes and produces tags for all mappings
//       - service annotations

@RestController
public class SourceSecController {

    private RetreivalService retreivalService;
    private DirectoryService directoryService;

    public SourceSecController(){
        retreivalService = new RetreivalService();
        directoryService = new DirectoryService();
    }

    @RequestMapping("/handshake")
    public String handshake(){
        return "Greetings from Source Code Security Controller";
    }

    @RequestMapping("/analysis/file")
    public @ResponseBody AnalysisContext analysisOfFile(@RequestBody RequestContext requestContext) {
        return (AnalysisContext) retreivalService.retrieveContextFromFile(new File(requestContext.getFilepath()));
    }

    @RequestMapping("/directorygraph")
    public @ResponseBody Component directoryGraph(@RequestBody RequestContext requestContext) {
        Component root = retreivalService.retreiveDirectoryGraphFromPath(requestContext.getFilepath());
        return root;
    }

    @PostMapping(value = "/analysis")
    public @ResponseBody AnalysisContext analysis(@RequestBody RequestContext requestContext) {
        AnalysisContext ctx = (AnalysisContext) retreivalService.retreiveAnalysisContextFromGraph(
                (DirectoryComponent) retreivalService
                        .retreiveDirectoryGraphFromPath(requestContext.getFilepath()));
        return ctx;
    }

    @PostMapping(value = "/analysis/rest")
    public @ResponseBody List<Component> analysisRest(@RequestBody RequestContext requestContext) {
        Component directoryGraph = retreivalService.retreiveDirectoryGraphFromPath(requestContext.getFilepath());
        AnalysisContext context =
                (AnalysisContext) retreivalService.retreiveAnalysisContextFromGraph((DirectoryComponent) directoryGraph);
        List<Component> restClasses = new ArrayList<>();
        for (ClassComponent e : context.getClasses()) {
            for (Component a : e.getAnnotations()) {
                if (a.asAnnotationComponent().getAsString().contains("RestController")) {
                    restClasses.add(e);
                }
            }
        }
        return restClasses;
    }

    @PostMapping(value = "/analysistofile")
    public void analysisToFile(@RequestBody RequestContext requestContext) throws IOException {
        AnalysisContext ctx = (AnalysisContext) retreivalService.retreiveAnalysisContextFromGraph(
                (DirectoryComponent) retreivalService
                        .retreiveDirectoryGraphFromPath(requestContext.getFilepath()));
//        File file = new File("activemq-results.json");
//        FileWriter writer = new FileWriter(file);
//        long start = System.currentTimeMillis();
//        for (String record: records) {
//            writer.write(record);
//        }
//        writer.flush();
//        writer.close();
//        long end = System.currentTimeMillis();
//        System.out.println((end - start) / 1000f + " seconds");
//        write(ctx)
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("/Users/simmonsjo/Documents/ciljssa-testing/activemq-results.json"), ctx);
    }

    /**
     * Handles the result and determines if the result has succeeded or not.
     * @param context The results context to wrap into a result
     * @param code The resulting response code that should be attributed to the response if it succeeded
     * @return A response message
     */
    private BaseResponse handleResult(AnalysisResultsContext context, ResponseCode code) {
        BaseResponse response;
        if (context.succeeded()) {
            response = new OkResponse(context, code);
            return response;
        } else {
            ResponseCode responseCode = ResponseCode.INTERNAL_ERROR;
            String message = "";
            if (context.getFailedContexts() > 1) {
                message = "There were multiple failed context requests. Failed request count: ";
                message += context.getFailedContexts();
            } else if (context.getFailedContexts() == 1) {
                message = "There was a failed context request.";
            } else if (context.getContexts().size() == 0) {
                message = "The list of contexts failed to initialize.";
                responseCode = ResponseCode.NOT_FOUND;
            }
            message += "\nCheck your request for typos and verify the path provided is a valid file path / directory.";
            response = new BadResponse(message, responseCode);
            return response;
        }
    }

}
