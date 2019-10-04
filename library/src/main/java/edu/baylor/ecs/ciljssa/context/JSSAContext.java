package edu.baylor.ecs.ciljssa.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import edu.baylor.ecs.ciljssa.wrappers.ComponentWrapper;
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

    @JsonProperty(value = "succeeded")
    protected boolean succeeded = false;
    @JsonProperty(value = "file_name")
    protected String fileName;
    @JsonProperty(value = "file_path")
    protected String filePath;
    @JsonProperty(value = "classes")
    protected List<String> classNames;
    @JsonProperty(value = "interfaces")
    protected List<String> interfaceNames;
    @JsonProperty(value = "declarations")
    protected List<ComponentWrapper> classesAndInterfaces;

}
