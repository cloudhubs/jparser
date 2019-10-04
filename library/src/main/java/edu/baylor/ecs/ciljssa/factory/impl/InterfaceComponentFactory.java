package edu.baylor.ecs.ciljssa.factory.impl;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import edu.baylor.ecs.ciljssa.factory.AbstractComponentFactory;
import edu.baylor.ecs.ciljssa.factory.IComponentFactory;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;
import edu.baylor.ecs.ciljssa.wrappers.IComponent;
import edu.baylor.ecs.ciljssa.wrappers.InterfaceComponent;

public class InterfaceComponentFactory extends AbstractComponentFactory implements IComponentFactory {

    public ClassOrInterfaceDeclaration cls;

    private final ClassOrInterface type = ClassOrInterface.CLASS;

    public InterfaceComponentFactory(ClassOrInterfaceDeclaration cls) {
        this.cls = cls;
    }

    @Override
    public IComponent createComponent() {
        IComponent output = new InterfaceComponent();

        return output;
    }
}
