package org.seer.ciljssa.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AnalysisResultsContext {

    private String path;

    private List<AnalysisContext> contexts;

    @JsonIgnore
    private int failedContexts = 0;

    private AnalysisRequestContext request;
    //TODO: Language in here not in context

    public AnalysisResultsContext() {
        this.contexts = new ArrayList<>();
    }

    public void addAnalysisContext(AnalysisContext context) {
        this.contexts.add(context);
        if (!context.isSucceeded()) {
            this.failedContexts++;
        }
    }

    public void setContexts(List<AnalysisContext> ctx) {
        this.contexts = ctx;
        this.failedContexts = 0;
        for (AnalysisContext c : ctx) {
            if (!c.isSucceeded()) this.failedContexts++;
        }
    }

    public boolean succeeded() {
        if (contexts.size() == 0) return false;
        for (AnalysisContext ctx : contexts) {
            if (!ctx.isSucceeded()) return false;
        }
        return true;
    }

}
