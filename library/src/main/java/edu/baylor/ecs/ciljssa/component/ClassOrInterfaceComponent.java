package edu.baylor.ecs.ciljssa.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import edu.baylor.ecs.ciljssa.component.impl.AnnotationComponent;
import edu.baylor.ecs.ciljssa.component.impl.MetaSubComponent;
import edu.baylor.ecs.ciljssa.component.impl.MethodInfoComponent;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;
import edu.baylor.ecs.ciljssa.model.ContainerStereotype;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import lombok.Data;

import java.util.List;

@Data
public abstract class ClassOrInterfaceComponent extends ContainerComponent {

    @JsonIgnore
    protected Component parentComponent;
    @JsonIgnore
    protected MetaSubComponent metaSubComponent;

    @JsonIgnore
    protected ClassOrInterfaceDeclaration cls;
    @JsonIgnore
    protected CompilationUnit analysisUnit;
    @JsonIgnore
    protected List<MethodDeclaration> methodDeclarations;
    @JsonIgnore
    protected String rawSource;
    @JsonIgnore
    protected InstanceType instanceType;

    @JsonProperty
    protected String path;
    @JsonProperty(value = "declaration_type")
    protected ClassOrInterface classOrInterface;
    @JsonProperty(value = "name")
    protected String instanceName;
    protected List<AnnotationComponent> annotations;
    protected ContainerStereotype stereotype;

}
