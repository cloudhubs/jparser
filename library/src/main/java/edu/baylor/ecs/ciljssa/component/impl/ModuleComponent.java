package edu.baylor.ecs.ciljssa.component.impl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import edu.baylor.ecs.ciljssa.component.IComponent;
import edu.baylor.ecs.ciljssa.component.IContainerComponent;
import edu.baylor.ecs.ciljssa.model.ContainerStereotype;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import edu.baylor.ecs.ciljssa.model.ModuleStereotype;
import lombok.Data;

import java.util.List;

@Data
public class ModuleComponent implements IContainerComponent {

    private List<IComponent> subComponents; // Classes/Interfaces
    private CompilationUnit unit;

    private ContainerStereotype stereotype;
    private ModuleStereotype moduleStereotype;
    private List<MethodInfoComponent> allMethods; // TODO: INCLUDE CONSTRUCTORS
    private List<MethodDeclaration> allMethodDeclarations;
    private String instanceName;
    private InstanceType instanceType;
    private IComponent parentComponent;
    private String packageName;
    private String path;

    public ModuleComponent() {
        this.stereotype = ContainerStereotype.MODULE;
    }

    @Override
    public ContainerStereotype getContainerStereotype() {
        return stereotype;
    }

    @Override
    public void setContainerStereotype(ContainerStereotype stereotype) {
        this.stereotype = stereotype;
    }

    @Override
    public List<IComponent> getSubComponents() {
        return subComponents;
    }

    @Override
    public void setSubComponents(List<IComponent> subComponents) {
        this.subComponents = subComponents;
    }

    @Override
    public CompilationUnit getCompilationUnit() {
        return unit;
    }

    @Override
    public void setCompilationUnit(CompilationUnit unit) {
        this.unit = unit;
    }

    @Override
    public List<MethodDeclaration> getMethodDeclarations() {
        return this.allMethodDeclarations;
    }

    @Override
    public void setMethodDeclarations(List<MethodDeclaration> list) {
        this.allMethodDeclarations = list;
    }

    @Override
    public List<MethodInfoComponent> getMethods() {
        return this.allMethods;
    }

    @Override
    public void setMethods(List<MethodInfoComponent> list) {
        this.allMethods = list;
    }

    @Override
    public String getPathToComponent() {
        return null;
    }

    @Override
    public void setPathToComponent(String path) {
        this.path = path;
    }

    @Override
    public String getPackageName() {
        return null;
    }

    @Override
    public void setPackageName(String name) {
        this.packageName = name;
    }

    @Override
    public String getInstanceName() {
        return this.instanceName;
    }

    @Override
    public void setInstanceName(String name) {
        this.instanceName = name;
    }

    @Override
    public InstanceType getInstanceType() {
        return this.instanceType;
    }

    @Override
    public void setInstanceType(InstanceType type) {
        this.instanceType = type;
    }

    @Override
    public IComponent getParentComponent() {
        return this.parentComponent;
    }

    @Override
    public void setParentComponent(IComponent component) {
        this.parentComponent = component;
    }
}
