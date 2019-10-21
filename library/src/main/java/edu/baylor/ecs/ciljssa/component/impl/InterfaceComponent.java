package edu.baylor.ecs.ciljssa.component.impl;

import com.fasterxml.jackson.annotation.*;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import edu.baylor.ecs.ciljssa.component.ClassOrInterfaceComponent;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.ContainerComponent;
import edu.baylor.ecs.ciljssa.component.IComponent;
import edu.baylor.ecs.ciljssa.component.IContainerComponent;
import edu.baylor.ecs.ciljssa.model.ContainerStereotype;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class InterfaceComponent extends ClassOrInterfaceComponent {

    public InterfaceComponent() {
        this.classOrInterface = ClassOrInterface.INTERFACE;
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

}
