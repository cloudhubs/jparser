package edu.baylor.ecs.ciljssa.factory.component;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import edu.baylor.ecs.ciljssa.wrappers.IComponent;

public interface IComponentFactory {

    IComponent createComponent(ClassOrInterfaceDeclaration cls, CompilationUnit unit);

}
