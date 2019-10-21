package edu.baylor.ecs.ciljssa.component.impl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.ContainerComponent;
import edu.baylor.ecs.ciljssa.component.IComponent;
import edu.baylor.ecs.ciljssa.component.IContainerComponent;
import edu.baylor.ecs.ciljssa.model.ContainerStereotype;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import edu.baylor.ecs.ciljssa.model.ModuleStereotype;
import lombok.Data;

import java.util.List;

@Data
public class ModuleComponent extends ContainerComponent {

    private ModuleStereotype moduleStereotype;
    // TODO: INCLUDE CONSTRUCTORS IN "methods"

    public ModuleComponent() {
        this.stereotype = ContainerStereotype.MODULE;
    }

}
