package edu.baylor.ecs.ciljssa.factory.container.impl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.impl.*;
import edu.baylor.ecs.ciljssa.factory.container.AbstractContainerFactory;
import edu.baylor.ecs.ciljssa.factory.container.IContainerFactory;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class InterfaceComponentFactory extends AbstractContainerFactory {

    public final ClassOrInterface TYPE = ClassOrInterface.INTERFACE;

    private ModuleComponent parent;

    public InterfaceComponentFactory(ModuleComponent module) {
        this.parent = module;
    }

    @Override
    public Component createComponent(ClassOrInterfaceDeclaration cls, CompilationUnit unit) {
        InterfaceComponent output = new InterfaceComponent();
        List<Component> methods = createMethods(cls, output);
        List<AnnotationComponent> annotations = initAnnotations(output, cls);
        output.setAnalysisUnit(unit);
        output.setAnnotations(annotations);
        output.setClassOrInterface(ClassOrInterface.CLASS);
        output.setCls(cls);
        output.setCompilationUnit(unit);
        output.setId(getId());
        output.setInstanceName(cls.getName().asString());
        output.setInstanceType(InstanceType.CLASSCOMPONENT);
        output.setMethods(methods);
        output.setMethodDeclarations(cls.getMethods());
        output.setPackageName("N/A"); // TODO: Set package name
        output.setParentComponent(parent);
        output.setSubComponents(createMetaSubComponentAsList(output, methods, null, annotations, null));
        output.setStereotype(createStereotype(cls));
        return output;
    }

}