package edu.baylor.ecs.ciljssa.component.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.ContainerComponent;
import edu.baylor.ecs.ciljssa.model.ContainerStereotype;
import edu.baylor.ecs.ciljssa.model.ModuleStereotype;
import edu.baylor.ecs.ciljssa.visitor.IComponentVisitor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Subcomponents should contain sub-modules
 */
@Data
public class ModuleComponent extends ContainerComponent {

    @JsonIgnore
    private String language;
    @JsonIgnore
    private List<ClassOrInterfaceDeclaration> classOrInterfaceDeclarations;
    @JsonIgnore
    private List<ModuleComponent> subModules;

    private ModuleStereotype moduleStereotype;

    @JsonProperty(value = "class_names")
    private List<String> classNames;
    @JsonProperty(value = "interface_names")
    private List<String> interfaceNames;
    @JsonProperty(value = "method_names")
    private List<String> methodNames;

    @JsonProperty(value = "containers")
    private List<Component> classesAndInterfaces;
    private List<ClassComponent> classes;
    private List<InterfaceComponent> interfaces;

    public ModuleComponent() {
        this.stereotype = ContainerStereotype.MODULE;
    }

    /**
     * Makes this Module one which has only other
     * @param list
     */
    public void setSubModules(List<ModuleComponent> list) {
        this.subModules = list;
        this.subComponents = list.stream().map(x -> (Component) x).collect(Collectors.toList());
    }

    @Override
    public void addSubComponent(Component sub) {
        if (this.subComponents == null)
            this.subComponents = new ArrayList<>();
        if (this.subModules == null)
            this.subModules = new ArrayList<>();
        this.subComponents.add(sub);
        this.subModules.add((ModuleComponent) sub);
    }

    @Override
    public void setSubComponents(List<Component> list) {
        this.subComponents = list;
        this.subModules = list.stream().map(x -> (ModuleComponent) x).collect(Collectors.toList());
    }

    @Override
    public void accept(IComponentVisitor visitor) {
        visitor.visit(this);
        for (Component e : this.subComponents) {
            e.accept(visitor);
        }
    }
}

