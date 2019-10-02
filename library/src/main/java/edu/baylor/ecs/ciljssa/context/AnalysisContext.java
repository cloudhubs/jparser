package edu.baylor.ecs.ciljssa.context;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.baylor.ecs.ciljssa.wrappers.ClassInterfaceWrapper;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.Arrays;
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
    public AnalysisContext filterByClass(String name) {
        setClassNames(Arrays.stream(this.classNames).filter(x -> x.equals(name)).toArray(String[]::new));
        this.setClassesAndInterfaces(Arrays.stream(this.getClassesAndInterfaces())
                .filter(x -> x.getInstanceName().equals(name)).toArray(ClassInterfaceWrapper[]::new));
        this.interfaceNames = new String[0];
        return this;
    }
}

