package edu.baylor.ecs.ciljssa.app;

import edu.baylor.ecs.ciljssa.app.response.BadResponse;
import edu.baylor.ecs.ciljssa.app.response.BaseResponse;
import edu.baylor.ecs.ciljssa.app.response.OkResponse;
import edu.baylor.ecs.ciljssa.app.response.ResponseCode;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.impl.DirectoryComponent;
import edu.baylor.ecs.ciljssa.component.impl.ModuleComponent;
import edu.baylor.ecs.ciljssa.context.AnalysisContext;
import edu.baylor.ecs.ciljssa.app.context.RequestContext;
import edu.baylor.ecs.ciljssa.app.context.AnalysisResultsContext;
import edu.baylor.ecs.ciljssa.app.services.DirectoryService;
import edu.baylor.ecs.ciljssa.app.services.RetreivalService;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/directorygraph")
    public @ResponseBody Component directoryGraph(@RequestBody RequestContext requestContext) {
        Component root = retreivalService.retreiveDirectoryGraphFromPath(requestContext.getFilepath());
        return root;
    }

    @PostMapping(value = "/modulegraph")
    public @ResponseBody List<ModuleComponent> moduleGraph(@RequestBody RequestContext requestContext) {
         List<ModuleComponent> modules = retreivalService.retreiveModuleGraph((DirectoryComponent)
                 retreivalService.retreiveDirectoryGraphFromPath(requestContext.getFilepath()));
         return modules;
    }

    @PostMapping(value = "/analysis")
    public @ResponseBody AnalysisContext analysis(@RequestBody RequestContext requestContext) {
        AnalysisContext ctx = retreivalService.retreiveAnalysisContextFromGraph(
                (DirectoryComponent) retreivalService
                        .retreiveDirectoryGraphFromPath(requestContext.getFilepath()));
        return ctx;
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
