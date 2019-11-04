package edu.baylor.ecs.ciljssa.context;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AnalysisContext extends JSSAContext {

    public AnalysisContext filterByClass(String name) {
        setClassNames(this.classNames.stream().filter(x -> x.equals(name)).collect(Collectors.toList()));
        this.setClassesAndInterfaces(this.getClassesAndInterfaces().stream()
                .filter(x -> x.getInstanceName().equals(name)).collect(Collectors.toList()));
        return this;
    }

}

