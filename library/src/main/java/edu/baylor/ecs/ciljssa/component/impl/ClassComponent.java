package edu.baylor.ecs.ciljssa.component.impl;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import edu.baylor.ecs.ciljssa.component.IContainerComponent;
import edu.baylor.ecs.ciljssa.component.InstanceType;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ClassComponent implements IContainerComponent {

    private Long id;

    @JsonIgnore
    private ClassOrInterfaceDeclaration cls;
    @JsonIgnore
    private CompilationUnit analysisUnit;
    @JsonIgnore
    private List<MethodDeclaration> methodDeclarations;
    @JsonIgnore
    private String rawSource;
    @JsonIgnore
    private final InstanceType instanceType = InstanceType.CLASSCOMPONENT;

    @JsonProperty
    private String path;
    @JsonProperty(value = "declaration_type")
    private ClassOrInterface classOrInterface;
    @JsonProperty(value = "name")
    private String instanceName;
    private List<MethodInfoComponent> methods;
    private List<MethodInfoComponent> constructors;
    private List<AnnotationComponent> annotations;

    @Override
    public ClassOrInterfaceDeclaration getCls() {
        return cls;
    }

    @Override
    public List<MethodDeclaration> getMethodDeclarations() {
        return methodDeclarations;
    }

    @Override
    public ClassOrInterface getClassOrInterface() {
        return classOrInterface;
    }

    @Override
    public String getPathToComponent() {
        return path;
    }

    @Override
    public String getPackageName() {
        if (analysisUnit.getPackageDeclaration().isPresent()) {
            return analysisUnit.getPackageDeclaration().get().getNameAsString();
        } else {
            return "NA";
        }
    }

    @Override
    public String getSourceAsString() {
        return this.rawSource;
    }

    @Override
    public String getInstanceName() {
        return instanceName;
    }

    @Override
    public InstanceType getInstanceType() {
        return this.instanceType;
    }

    @Override
    public List<MethodInfoComponent> getMethods() {
        return methods;
    }


}
