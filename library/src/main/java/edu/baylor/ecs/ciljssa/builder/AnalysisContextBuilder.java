package edu.baylor.ecs.ciljssa.builder;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import edu.baylor.ecs.ciljssa.context.AnalysisContext;
import edu.baylor.ecs.ciljssa.wrappers.IComponent;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.List;

@NoArgsConstructor
public class AnalysisContextBuilder {

    private CompilationUnit unit;
    private File sourceFile;
    private String fileName;
    private String filePath;
    private List<String> classNames;
    private List<String> interfaceNames;
    private boolean succeeded = false;
    private List<IComponent> classesAndInterfaces;
    private List<ClassOrInterfaceDeclaration> classOrInterfaceDeclarations;

    public AnalysisContext build() {
        AnalysisContext context = new AnalysisContext();
        context.setClassOrInterfaceDeclarations(this.classOrInterfaceDeclarations);
        context.setClassesAndInterfaces(this.classesAndInterfaces); // Must be run first
        context.setSucceeded(this.succeeded);
        context.setInterfaceNames(this.interfaceNames);
        context.setFilePath(this.filePath);
        context.setFileName(this.fileName);
        context.setClassNames(this.classNames);
        context.setSourceFile(this.sourceFile);
        context.setAnalysisUnit(this.unit);
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

    public AnalysisContextBuilder withFilePath(String fp) {
        this.filePath = fp;
        return this;
    }

    public AnalysisContextBuilder withFileName(String fn) {
        this.fileName = fn;
        return this;
    }

}
