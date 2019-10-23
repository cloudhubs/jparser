package edu.baylor.ecs.ciljssa.component.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.ContainerComponent;
import edu.baylor.ecs.ciljssa.model.ContainerStereotype;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import edu.baylor.ecs.ciljssa.model.ModuleStereotype;
import lombok.Data;

import java.io.File;
import java.util.List;

@Data
public class ModuleComponent extends ContainerComponent {

    @JsonIgnore
    private List<ClassOrInterfaceDeclaration> classOrInterfaceDeclarations;
    @JsonIgnore
    private List<MethodDeclaration> methodDeclarations;

    private ModuleStereotype moduleStereotype;
    private List<ModuleComponent> subModules;

    @JsonProperty(value = "class_names")
    private List<String> classNames;
    @JsonProperty(value = "interface_names")
    private List<String> interfaceNames;

    @JsonProperty(value = "containers")
    private List<Component> classesAndInterfaces;
    private List<ClassComponent> classes;
    private List<InterfaceComponent> interfaces;


    // TODO: INCLUDE CONSTRUCTORS IN "methods"

    public ModuleComponent() {
        this.stereotype = ContainerStereotype.MODULE;
    }

    /**
     * Makes this Module one which has only other
     * @param list
     */
    public void setSubModules(List<ModuleComponent> list) {

    }

}
