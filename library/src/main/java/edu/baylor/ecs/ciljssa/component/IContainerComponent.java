package edu.baylor.ecs.ciljssa.component;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import edu.baylor.ecs.ciljssa.component.impl.MethodInfoComponent;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;
import edu.baylor.ecs.ciljssa.model.ContainerStereotype;

import java.util.List;

public interface IContainerComponent extends IComponent {

    ContainerStereotype getContainerStereotype();
    void setContainerStereotype(ContainerStereotype stereotype);
    List<IComponent> getSubComponents();
    void setSubComponents(List<IComponent> subComponents);
    CompilationUnit getCompilationUnit();
    void setCompilationUnit(CompilationUnit unit);
    List<MethodDeclaration> getMethodDeclarations();
    void setMethodDeclarations(List<MethodDeclaration> list);
    List<MethodInfoComponent> getMethods();
    void setMethods(List<MethodInfoComponent> list);

}
