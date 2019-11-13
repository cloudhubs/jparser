package edu.baylor.ecs.ciljssa.app.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import edu.baylor.ecs.ciljssa.component.context.AnalysisContext;
import edu.baylor.ecs.ciljssa.component.context.JSSAContext;
import edu.baylor.ecs.ciljssa.visitor.IComponentVisitor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AnalysisResultsContext extends JSSAContext {

    private String path;

    private List<AnalysisContext> contexts;

    @JsonIgnore
    private int failedContexts = 0;

    private RequestContext request;

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

    @Override
    public void accept(IComponentVisitor visitor) {

    }
}
