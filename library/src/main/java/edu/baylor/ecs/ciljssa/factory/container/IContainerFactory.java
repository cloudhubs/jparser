package edu.baylor.ecs.ciljssa.factory.container;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.impl.ModuleComponent;

public interface IContainerFactory {

    Component createComponent(ModuleComponent parent, ClassOrInterfaceDeclaration cls, CompilationUnit unit);

}
