package edu.baylor.ecs.ciljssa.component.impl;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ClassComponent extends ClassOrInterfaceComponent implements IContainerComponent {

    private List<MethodInfoComponent> constructors;

    public ClassComponent() {
        this.instanceType = InstanceType.CLASSCOMPONENT;
    }

    public List<MethodInfoComponent> getConstructors() {
        return this.constructors;
    }

    public void setConstructors(List<MethodInfoComponent> list) {
        this.constructors = list;
    }

    public ClassOrInterfaceDeclaration getCls() {
        return cls;
    }

    @Override
    public ContainerStereotype getContainerStereotype() {
        return this.stereotype;
    }

    @Override
    public void setContainerStereotype(ContainerStereotype stereotype) {
        this.stereotype = stereotype;
    }

    @Override
    public List<IComponent> getSubComponents() {
        List<IComponent> output = new ArrayList<>();
        output.add(this.metaSubComponent);
        return output;
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
        return methodDeclarations;
    }

    @Override
    public void setMethodDeclarations(List<MethodDeclaration> list) {
        this.methodDeclarations = list;
    }

    public ClassOrInterface getClassOrInterface() {
        return this.classOrInterface;
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
        if (analysisUnit.getPackageDeclaration().isPresent()) {
            return analysisUnit.getPackageDeclaration().get().getNameAsString();
        } else {
            return "NA";
        }
    }

    @Override
    public void setPackageName(String name) {
        this.packageName = name;
    }

    public String getSourceAsString() {
        return this.rawSource;
    }

    @Override
    public String getInstanceName() {
        return instanceName;
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
    public List<MethodInfoComponent> getMethods() {
        return methods;
    }

    @Override
    public void setMethods(List<MethodInfoComponent> list) {
        this.methods = list;
    }

}
