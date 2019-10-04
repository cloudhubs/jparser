package edu.baylor.ecs.ciljssa.wrappers;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;

import java.util.List;

public interface IComponent {

    ClassOrInterfaceDeclaration getCls();
    void setCls(ClassOrInterfaceDeclaration cls);
//    CompilationUnit getAnalysisUnit();
//    void setAnalysisUnit(CompilationUnit unit);
    List<MethodDeclaration> getMethodDeclarations();
    void setMethodDeclarations(List<MethodDeclaration> list);
    ClassOrInterface getClassOrInterface();
    void setClassOrInterface(ClassOrInterface c);
    String getInstanceName();
    void setInstanceName(String n);
    List<MethodInfoWrapper> getMethods();
    void setMethods(List<MethodInfoWrapper> list);
    List<AnnotationWrapper> getComponentAnnotations();
    void setComponentAnnotations(List<AnnotationWrapper> list);

}
