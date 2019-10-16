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

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class InterfaceComponent extends ClassOrInterfaceComponent implements IContainerComponent {

    public InterfaceComponent() {
        this.classOrInterface = ClassOrInterface.INTERFACE;
    }

    public ClassOrInterfaceDeclaration getCls() {
        return this.cls;
    }

    @Override
    public String getPathToComponent() {
        return this.path;
    }

    @Override
    public void setPathToComponent(String path) {
        this.path = path;
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
        return null; // Do nothing
    }

    @Override
    @Deprecated
    public void setSubComponents(List<IComponent> subComponents) {
        this.metaSubComponent = (MetaSubComponent) subComponents.get(0);
    }

    public void setSubComponents(MetaSubComponent comp) {
        this.metaSubComponent = comp;
    }

    @Override
    public CompilationUnit getCompilationUnit() {
        return this.analysisUnit;
    }

    @Override
    public void setCompilationUnit(CompilationUnit unit) {
        this.analysisUnit = unit;
    }

    @Override
    public List<MethodDeclaration> getMethodDeclarations() {
        return this.methodDeclarations;
    }

    @Override
    public void setMethodDeclarations(List<MethodDeclaration> list) {
        this.methodDeclarations = list;
    }

    @Override
    public List<MethodInfoComponent> getMethods() {
        return this.methods;
    }

    @Override
    public void setMethods(List<MethodInfoComponent> list) {
        this.methods = list;
    }
}
