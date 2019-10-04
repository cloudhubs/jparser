package edu.baylor.ecs.ciljssa.context;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.stream.Collectors;
//TODO: For friday, what would be good:
//      - Annotations all information regarding them in result
//      - Specifications on some sample queries?
//      - Query to get body from a MethodDeclaration inside a context
//      - Tidying and best practice
//      - Deal with other TODOs
//      - Handle as many exceptions and errors as possible
//      - What else?


//TODO: This class should have methods for analysis and producing various results.
//          The analysis service should only call methods created here, so any new analysis I need can
//          be made into part of the maven library.
@Data
@EqualsAndHashCode
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AnalysisContext extends JSSAContext {
    //TODO: Change to return classesandinterfaces
    public AnalysisContext filterByClass(String name) {
        setClassNames(this.classNames.stream().filter(x -> x.equals(name)).collect(Collectors.toList()));
        this.setClassesAndInterfaces(this.getClassesAndInterfaces().stream()
                .filter(x -> x.getInstanceName().equals(name)).collect(Collectors.toList()));
        //this.interfaceNames = new String[0];
        return this;
    }
}

