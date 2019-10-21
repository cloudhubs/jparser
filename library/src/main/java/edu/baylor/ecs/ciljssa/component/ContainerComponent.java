package edu.baylor.ecs.ciljssa.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import edu.baylor.ecs.ciljssa.component.impl.MethodInfoComponent;
import edu.baylor.ecs.ciljssa.model.ContainerStereotype;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class ContainerComponent extends Component {

    @JsonIgnore
    protected CompilationUnit compilationUnit;
    protected ContainerStereotype stereotype;
    protected List<MethodDeclaration> methodDeclarations;
    protected List<MethodInfoComponent> methods;

}
