package edu.baylor.ecs.ciljssa.factory.container.impl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.impl.AnnotationComponent;
import edu.baylor.ecs.ciljssa.component.impl.MethodInfoComponent;
import edu.baylor.ecs.ciljssa.component.impl.ModuleComponent;
import edu.baylor.ecs.ciljssa.factory.container.AbstractContainerFactory;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;
import edu.baylor.ecs.ciljssa.component.impl.ClassComponent;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClassComponentFactory extends AbstractContainerFactory {

    public final ClassOrInterface TYPE = ClassOrInterface.CLASS;

    private ModuleComponent parent;

    public ClassComponentFactory(ModuleComponent parent) {
        this.parent = parent;
    }

    @Override
    public Component createComponent(ClassOrInterfaceDeclaration cls, CompilationUnit unit) {
        ClassComponent output = new ClassComponent();
        List<Component> methods = createMethods(cls, output);
        List<Component> constructors = createConstructors(cls, output);
        List<AnnotationComponent> annotations = initAnnotations(output, cls);
        List<ClassComponent> subClasses = createSubClasses(cls);
        output.setAnalysisUnit(unit);
        output.setAnnotations(annotations);
        output.setClassOrInterface(ClassOrInterface.CLASS);
        output.setCls(cls);
        output.setCompilationUnit(unit);
        output.setConstructors(constructors);
        output.setId(getId());
        output.setInstanceName(cls.getName().asString());
        output.setInstanceType(InstanceType.CLASSCOMPONENT);
        output.setMethods(methods);
        output.setMethodDeclarations(cls.getMethods());
        output.setPackageName("N/A"); // TODO: Set package name
        output.setParentComponent(parent);
        output.setSubComponents(createMetaSubComponentAsList(output, methods, constructors, annotations, subClasses));
        output.setStereotype(createStereotype(cls));
        return output;
    }

    private List<ClassComponent> createSubClasses(ClassOrInterfaceDeclaration cls) {
        // TODO
        return null;
    }

}
