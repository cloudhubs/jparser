package org.seer.ciljssa.context;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AnalysisResultsContext {

    private String path;

    @JsonProperty(value = "result")
    private ArrayList<AnalysisContext> context;
    private AnalysisRequestContext request;

    private int httpResult;

    public AnalysisResultsContext(AnalysisContext context, AnalysisRequestContext request) {
        this.context = new ArrayList<>();
        this.context.add(context);
        this.request = request;
    }

    public AnalysisResultsContext(ArrayList<AnalysisContext> contexts, AnalysisRequestContext request) {
        this.context = contexts;
        this.request = request;
    }

    public AnalysisResultsContext() {
        this.httpResult = 500;
    }

    public AnalysisResultsContext setStatus(int status) {
        httpResult = status;
        return this;
    }

}
