package edu.baylor.ecs.jparser.component.impl;

import com.fasterxml.jackson.annotation.*;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import edu.baylor.ecs.jparser.component.ClassOrInterfaceComponent;
import edu.baylor.ecs.jparser.model.ContainerType;
import edu.baylor.ecs.jparser.visitor.IComponentVisitor;
import lombok.Data;

@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class InterfaceComponent extends ClassOrInterfaceComponent {

    @JsonIgnore
    protected CompilationUnit compilationUnit;

    public InterfaceComponent() {
        this.containerType = ContainerType.INTERFACE;
    }

    public ClassOrInterfaceDeclaration getCls() {
        return this.cls;
    }

    @Override
    public String getPackageName() {
        if (this.analysisUnit.getPackageDeclaration().isPresent()) {
            return this.analysisUnit.getPackageDeclaration().get().getNameAsString();
        } else {
            return "NA";
        }
    }

    @Override
    public void accept(IComponentVisitor visitor) {

    }
}
