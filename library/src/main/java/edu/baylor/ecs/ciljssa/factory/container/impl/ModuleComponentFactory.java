package edu.baylor.ecs.ciljssa.factory.container.impl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.factory.container.AbstractComponentFactory;
import edu.baylor.ecs.ciljssa.factory.container.IContainerFactory;

public class ModuleComponentFactory extends AbstractComponentFactory implements IContainerFactory {

    @Override
    public Component createComponent(ClassOrInterfaceDeclaration cls, CompilationUnit unit) {
        return null;
    }
}
