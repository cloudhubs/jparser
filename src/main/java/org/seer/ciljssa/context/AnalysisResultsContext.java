package org.seer.ciljssa.context;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AnalysisResultsContext {

    private String path;

    @JsonProperty(value = "result")
    private ArrayList<AnalysisContext> contexts;
    @JsonProperty(value = "failed_contexts")
    private int failedContexts;

    private AnalysisRequestContext request;
    //TODO: Language in here not in context

    public AnalysisResultsContext() {
        this.contexts = new ArrayList<>();
    }

    public void addAnalysisContext(AnalysisContext context) {
        this.contexts.add(context);
    }

}
