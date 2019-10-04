package edu.baylor.ecs.ciljssa.factory.component.impl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import edu.baylor.ecs.ciljssa.factory.component.AbstractComponentFactory;
import edu.baylor.ecs.ciljssa.factory.component.IComponentFactory;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;
import edu.baylor.ecs.ciljssa.wrappers.ClassComponent;
import edu.baylor.ecs.ciljssa.wrappers.IComponent;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClassComponentFactory extends AbstractComponentFactory implements IComponentFactory  {

    public final ClassOrInterface TYPE = ClassOrInterface.CLASS;

    @Override
    public IComponent createComponent(ClassOrInterfaceDeclaration cls, CompilationUnit unit) {
        ClassComponent output = new ClassComponent();
        output.setCls(cls);
        output.setComponentAnnotations(initAnnotations(cls));
        output.setMethods(createMethods(cls, output));
        output.setConstructors(createConstructors(cls, output));
        output.setInstanceName(cls.getName().asString());
        output.setAnalysisUnit(unit);
        output.setClassOrInterface(ClassOrInterface.CLASS);
        output.setMethodDeclarations(cls.getMethods());
        return output;
    }

}
