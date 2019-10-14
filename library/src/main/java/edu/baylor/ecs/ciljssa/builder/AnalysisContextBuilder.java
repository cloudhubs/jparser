package edu.baylor.ecs.ciljssa.builder;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import edu.baylor.ecs.ciljssa.component.impl.ModuleComponent;
import edu.baylor.ecs.ciljssa.context.AnalysisContext;
import edu.baylor.ecs.ciljssa.component.IComponent;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class AnalysisContextBuilder {

    private CompilationUnit unit;
    private File sourceFile;
    private String filePath;
    private List<String> classNames;
    private List<String> interfaceNames;
    private boolean succeeded = false;
    private List<IComponent> classesAndInterfaces;
    private List<ClassOrInterfaceDeclaration> classOrInterfaceDeclarations;
    private List<MethodDeclaration> methodDeclarations;
    private List<IComponent> methods;
    private List<ModuleComponent> modules;

    public AnalysisContext build() {
        AnalysisContext context = new AnalysisContext();
        context.setClassOrInterfaceDeclarations(this.classOrInterfaceDeclarations);
        context.setClassesAndInterfaces(this.classesAndInterfaces); // Must be run first
        context.setSucceeded(this.succeeded);
        context.setInterfaceNames(this.interfaceNames);
        context.setFilePath(this.filePath);
        context.setClassNames(this.classNames);
        context.setSourceFile(this.sourceFile);
        context.setAnalysisUnit(this.unit);
        context.setMethodDeclarations(this.methodDeclarations);
        context.setMethods(this.methods);
        context.setModules(this.modules);
        return context;
    }

    public AnalysisContextBuilder withClassNames(List<String> classNames) {
        this.classNames = classNames;
        return this;
    }

    public AnalysisContextBuilder withInterfaceNames(List<String> interfaceNames) {
        this.interfaceNames = interfaceNames;
        return this;
    }

    public AnalysisContextBuilder withCompilationUnit(CompilationUnit unit) {
        this.unit = unit;
        return this;
    }

    public AnalysisContextBuilder isSucceeded() {
        this.succeeded = true;
        return this;
    }

    public AnalysisContextBuilder withClassesAndInterfaces(List<IComponent> cls) {
        this.classesAndInterfaces = cls;
        return this;
    }

    public AnalysisContextBuilder withClassOrInterfaceDeclarations(List<ClassOrInterfaceDeclaration> cls) {
        this.classOrInterfaceDeclarations = cls;
        return this;
    }

    public AnalysisContextBuilder withSourceFile(File source) {
        this.sourceFile = source;
        return this;
    }

    public AnalysisContextBuilder withModules(List<ModuleComponent> modules) {
        this.modules = modules;
        return this;
    }

    public AnalysisContextBuilder withMethods(List<IComponent> methods) {
        this.methods = methods.stream().filter(x -> x.getInstanceType().equals(InstanceType.METHODCOMPONENT))
                .collect(Collectors.toList()); // Only allow those which are definitively methods.
        return this;
    }

    public AnalysisContextBuilder withMethodDeclarations(List<MethodDeclaration> methods) {
        this.methodDeclarations = methods;
        return this;
    }

}
