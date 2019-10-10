package edu.baylor.ecs.ciljssa.factory.container.impl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import edu.baylor.ecs.ciljssa.factory.container.AbstractComponentFactory;
import edu.baylor.ecs.ciljssa.factory.container.IComponentFactory;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;
import edu.baylor.ecs.ciljssa.component.IComponent;
import edu.baylor.ecs.ciljssa.component.impl.InterfaceComponent;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InterfaceComponentFactory extends AbstractComponentFactory implements IComponentFactory {

    public final ClassOrInterface TYPE = ClassOrInterface.INTERFACE;

    @Override
    public IComponent createComponent(ClassOrInterfaceDeclaration cls, CompilationUnit unit) {
        InterfaceComponent output = new InterfaceComponent();
        output.setCls(cls);
        output.setAnnotations(initAnnotations(cls));
        output.setMethods(createMethods(cls, output));
        output.setInstanceName(cls.getName().asString());
        output.setAnalysisUnit(unit);
        output.setClassOrInterface(ClassOrInterface.INTERFACE);
        output.setMethodDeclarations(cls.getMethods());
        output.setId(getId());
        return output;
    }

}
