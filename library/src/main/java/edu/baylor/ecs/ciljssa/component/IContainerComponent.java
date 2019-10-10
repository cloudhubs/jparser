package edu.baylor.ecs.ciljssa.component;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import edu.baylor.ecs.ciljssa.component.impl.MethodInfoComponent;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;

import java.util.List;

public interface IContainerComponent extends IComponent {

    ClassOrInterfaceDeclaration getCls();
    void setCls(ClassOrInterfaceDeclaration cls);
    CompilationUnit getAnalysisUnit();
    void setAnalysisUnit(CompilationUnit unit);
    List<MethodDeclaration> getMethodDeclarations();
    void setMethodDeclarations(List<MethodDeclaration> list);
    ClassOrInterface getClassOrInterface();
    void setClassOrInterface(ClassOrInterface c);
    String getInstanceName();
    void setInstanceName(String n);
    List<MethodInfoComponent> getMethods();
    void setMethods(List<MethodInfoComponent> list);

}
