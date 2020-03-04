package edu.baylor.ecs.cloudhubs.jparser.factory.container;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import edu.baylor.ecs.cloudhubs.jparser.component.Component;
import edu.baylor.ecs.cloudhubs.jparser.component.impl.ModuleComponent;

public interface IContainerFactory {

    Component createComponent(ModuleComponent parent, ClassOrInterfaceDeclaration cls, CompilationUnit unit);

}
