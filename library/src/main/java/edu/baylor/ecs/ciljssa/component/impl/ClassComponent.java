package edu.baylor.ecs.ciljssa.component.impl;

import com.fasterxml.jackson.annotation.*;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import edu.baylor.ecs.ciljssa.component.ClassOrInterfaceComponent;
import edu.baylor.ecs.ciljssa.component.IComponent;
import edu.baylor.ecs.ciljssa.component.IContainerComponent;
import edu.baylor.ecs.ciljssa.model.ContainerStereotype;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * this.subComponents is a MetaSubComponent. When you use .getSubComponents() on a Class or Interface component,
 * You must also use getAnnotations() or getMethods() to specify which subcomponent you desire.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ClassComponent extends ClassOrInterfaceComponent {

    private List<MethodInfoComponent> constructors;

    public ClassComponent() {
        this.instanceType = InstanceType.CLASSCOMPONENT;
    }

    @Override
    public String getPackageName() {
        if (analysisUnit.getPackageDeclaration().isPresent()) {
            return analysisUnit.getPackageDeclaration().get().getNameAsString();
        } else {
            return "NA";
        }
    }

}
