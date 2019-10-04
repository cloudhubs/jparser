package edu.baylor.ecs.ciljssa.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.baylor.ecs.ciljssa.app.context.AnalysisResultsContext;


public class OkResponse extends BaseResponse {

    @JsonProperty(value = "results")
    private AnalysisResultsContext context;

    public OkResponse(AnalysisResultsContext context) {
        super();
        this.setResponseType(ResponseType.SUCCESS);
        this.context = context;
    }

    public OkResponse(AnalysisResultsContext context, ResponseCode code) {
        super(code, null, ResponseType.SUCCESS);
        this.context = context;
    }

    @Override
    public AnalysisResultsContext get() {
        return context;
    }
}
