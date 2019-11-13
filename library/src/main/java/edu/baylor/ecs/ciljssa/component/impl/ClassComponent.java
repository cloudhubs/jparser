package edu.baylor.ecs.ciljssa.component.impl;

import com.fasterxml.jackson.annotation.*;
import com.github.javaparser.ast.CompilationUnit;
import edu.baylor.ecs.ciljssa.component.ClassOrInterfaceComponent;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import edu.baylor.ecs.ciljssa.visitor.IComponentVisitor;
import lombok.Data;

import java.util.List;

/**
 * this.subComponents is a MetaSubComponent. When you use .getSubComponents() on a Class or Interface component,
 * You must also use getAnnotations() or getMethods() to specify which subcomponent you desire.
 */
@Data
//@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ClassComponent extends ClassOrInterfaceComponent {

    @JsonIgnore
    protected CompilationUnit compilationUnit;
    private List<Component> constructors;
    private List<ClassField> classFields;

    public ClassComponent() {
        this.instanceType = InstanceType.CLASSCOMPONENT;
    }

    /**
     * Does the same thing as getInstanceName() however this is more intuitive for users.
     * @return
     */
    @JsonIgnore
    public String getClassName() {
        return this.instanceName;
    }

    @Override
    public void accept(IComponentVisitor visitor) {

    }
}
