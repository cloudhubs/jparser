package edu.baylor.ecs.ciljssa.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;
import lombok.Data;

import java.util.List;

public interface IComponent {

    String getPathToComponent();
    String getPackageName();
    String getSourceAsString();
    String getInstanceName();
    InstanceType getInstanceType();

}
