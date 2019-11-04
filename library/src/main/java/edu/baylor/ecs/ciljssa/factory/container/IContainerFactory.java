package edu.baylor.ecs.ciljssa.factory.container;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import edu.baylor.ecs.ciljssa.component.Component;

public interface IContainerFactory {

    Component createComponent(ClassOrInterfaceDeclaration cls, CompilationUnit unit);

}
