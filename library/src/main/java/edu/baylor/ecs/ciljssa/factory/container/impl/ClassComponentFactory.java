package edu.baylor.ecs.ciljssa.factory.container.impl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import edu.baylor.ecs.ciljssa.component.impl.AnnotationComponent;
import edu.baylor.ecs.ciljssa.component.impl.MethodInfoComponent;
import edu.baylor.ecs.ciljssa.component.impl.ModuleComponent;
import edu.baylor.ecs.ciljssa.factory.container.AbstractComponentFactory;
import edu.baylor.ecs.ciljssa.factory.container.IComponentFactory;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;
import edu.baylor.ecs.ciljssa.component.impl.ClassComponent;
import edu.baylor.ecs.ciljssa.component.IComponent;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import lombok.Data;

import java.util.List;

@Data
public class ClassComponentFactory extends AbstractComponentFactory implements IComponentFactory  {

    public final ClassOrInterface TYPE = ClassOrInterface.CLASS;

    private ModuleComponent parent;

    public ClassComponentFactory(ModuleComponent parent) {
        this.parent = parent;
    }

    @Override
    public IComponent createComponent(ClassOrInterfaceDeclaration cls, CompilationUnit unit) {
        ClassComponent output = new ClassComponent();
        List<MethodInfoComponent> methods = createMethods(cls, output);
        List<MethodInfoComponent> constructors = createConstructors(cls, output);
        List<AnnotationComponent> annotations = initAnnotations(cls);
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
        output.setSubComponents(createMetaSubComponent(output, methods, constructors, annotations, subClasses));
        output.setStereotype(createStereotype(cls));
        return output;
    }

    private List<ClassComponent> createSubClasses(ClassOrInterfaceDeclaration cls) {
        // TODO
        return null;
    }

}
