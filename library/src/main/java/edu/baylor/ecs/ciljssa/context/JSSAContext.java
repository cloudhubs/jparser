package edu.baylor.ecs.ciljssa.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import edu.baylor.ecs.ciljssa.component.IComponent;
import edu.baylor.ecs.ciljssa.component.impl.ModuleComponent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

@Data
@NoArgsConstructor
public abstract class JSSAContext {

    @JsonIgnore
    protected File sourceFile;
    @JsonIgnore
    protected CompilationUnit analysisUnit;
    @JsonIgnore
    protected List<ClassOrInterfaceDeclaration> classOrInterfaceDeclarations;
    @JsonIgnore
    protected List<MethodDeclaration> methodDeclarations;

    @JsonProperty(value = "succeeded")
    protected boolean succeeded = false;
    @JsonProperty(value = "root_path")
    protected String filePath;

    @JsonProperty(value = "class_names")
    protected List<String> classNames;
    @JsonProperty(value = "interface_names")
    protected List<String> interfaceNames;

    @JsonProperty(value = "containers")
    protected List<IComponent> classesAndInterfaces;
    protected List<ModuleComponent> modules;
    protected List<IComponent> methods;

}
