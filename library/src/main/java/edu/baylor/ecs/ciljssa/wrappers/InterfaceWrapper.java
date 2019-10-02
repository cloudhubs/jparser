package edu.baylor.ecs.ciljssa.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class InterfaceWrapper implements ComponentWrapper {

    @JsonIgnore
    private ClassOrInterfaceDeclaration cls;
    @JsonIgnore
    private CompilationUnit analysisUnit;
    @JsonIgnore
    private List<MethodDeclaration> methodDeclarations;

    @JsonProperty(value = "declaration_type")
    private ClassOrInterface classOrInterface;
    @JsonProperty(value = "name")
    private String instanceName;
    private List<MethodInfoWrapper> methods;
    private List<AnnotationWrapper> componentAnnotations;

    @Override
    public ClassOrInterfaceDeclaration getCls() {
        return cls;
    }

    @Override
    public CompilationUnit getAnalysisUnit() {
        return analysisUnit;
    }

    @Override
    public List<MethodDeclaration> getMethodDeclarations() {
        return methodDeclarations;
    }

    @Override
    public ClassOrInterface getClassOrInterface() {
        return ClassOrInterface.INTERFACE;
    }

    @Override
    public String getInstanceName() {
        return instanceName;
    }

    @Override
    public List<MethodInfoWrapper> getMethods() {
        return methods;
    }

    @Override
    public List<AnnotationWrapper> getComponentAnnotations() {
        return this.componentAnnotations;
    }

    @Override
    public void setComponentAnnotations(List<AnnotationWrapper> list) {
        this.componentAnnotations = list;
    }
}
