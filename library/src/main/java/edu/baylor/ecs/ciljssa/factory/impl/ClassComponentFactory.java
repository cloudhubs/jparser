package edu.baylor.ecs.ciljssa.factory.impl;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import edu.baylor.ecs.ciljssa.factory.AbstractComponentFactory;
import edu.baylor.ecs.ciljssa.factory.IComponentFactory;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;
import edu.baylor.ecs.ciljssa.wrappers.ClassComponent;
import edu.baylor.ecs.ciljssa.wrappers.IComponent;
import edu.baylor.ecs.ciljssa.wrappers.MethodInfoWrapper;

import java.util.List;

public class ClassComponentFactory extends AbstractComponentFactory implements IComponentFactory  {

    private ClassOrInterfaceDeclaration cls;

    private final ClassOrInterface type = ClassOrInterface.CLASS;

    public ClassComponentFactory(ClassOrInterfaceDeclaration cls) {
        this.cls = cls;
    }

    @Override
    public IComponent createComponent() {
        IComponent output = new ClassComponent();
        output.setCls(cls);
        output.setComponentAnnotations(initAnnotations(cls));
        output.setMethods(createMethods(cls, output));
        ((ClassComponent) output).setConstructors(createConstructors(cls, output));
        return output;
    }

}
