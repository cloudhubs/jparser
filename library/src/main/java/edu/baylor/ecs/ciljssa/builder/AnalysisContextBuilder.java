package edu.baylor.ecs.ciljssa.builder;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.baylor.ecs.ciljssa.context.AnalysisContext;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;
import edu.baylor.ecs.ciljssa.wrappers.ComponentWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AnalysisContextBuilder {

    private CompilationUnit unit;

    private File sourceFile;
    private String fileName;
    private String filePath;
    private List<String> classNames;
    private List<String> interfaceNames;
    private boolean succeeded = false;
    private List<ComponentWrapper> classesAndInterfaces;
    private List<ClassOrInterfaceDeclaration> classOrInterfaceDeclarations;

    public AnalysisContextBuilder(File file) {
        this.sourceFile = file;
    }

    public AnalysisContext build() {
        this.unit = generateCompilationUnit();
        this.classOrInterfaceDeclarations = generateClassOrInterfaceDeclarations();
        this.classesAndInterfaces = generateClassesAndInterfaces();
        this.interfaceNames = createInterfaceNames();
        this.classNames = createClassNames();

        AnalysisContext context = new AnalysisContext();

        context.setClassOrInterfaceDeclarations(this.classOrInterfaceDeclarations);
        context.setClassesAndInterfaces(this.classesAndInterfaces); // Must be run first
        context.setSucceeded(this.classesAndInterfaces.size() != 0);
        context.setInterfaceNames(this.interfaceNames);
        context.setFilePath(this.sourceFile.getPath());
        context.setFileName(this.sourceFile.getName());
        context.setClassNames(this.classNames);
        context.setSourceFile(this.sourceFile);
        context.setAnalysisUnit(this.unit);
        return new AnalysisContext();
    }

    public AnalysisContextBuilder withFilePath(String fp) {
        this.filePath = fp;
        return this;
    }

    public AnalysisContextBuilder withFileName(String fn) {
        this.fileName = fn;
        return this;
    }

    private List<String> createClassNames() {
        List<String> output = new ArrayList<>(); //TODO: Best Practice
        for (ComponentWrapper classesAndInterface : this.classesAndInterfaces) {
            if (classesAndInterface.getClassOrInterface().equals(ClassOrInterface.CLASS)) {
                output.add(classesAndInterface.getInstanceName());
            }
        }
        return output;
    }

    private List<String> createInterfaceNames() {
        List<String> output = new ArrayList<>();
        for (ComponentWrapper classesAndInterface : this.classesAndInterfaces) {
            if (classesAndInterface.getClassOrInterface().equals(ClassOrInterface.INTERFACE)) {
                output.add(classesAndInterface.getInstanceName());
            }
        }
        return output;
    }

    private List<ComponentWrapper> generateClassesAndInterfaces() {
        List<ClassOrInterfaceDeclaration> classOrInterfaces = new ArrayList<>();
        this.unit.accept(new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                super.visit(n, arg);
                classOrInterfaces.add(n);
            }
        }, null);
        List<ComponentWrapper> clsList = new ArrayList<>();
        for(ClassOrInterfaceDeclaration cls : classOrInterfaces) {
            ComponentBuilder builder = new ComponentBuilder(cls);
            clsList.add(builder.build());
        }
        this.classOrInterfaceDeclarations = classOrInterfaces;
        return clsList;
    }

    private CompilationUnit generateCompilationUnit() {
        JavaParser parser = new JavaParser();
        CompilationUnit unit = null;
        try {
            unit = parser.parse(sourceFile).getResult().get();
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // TODO: Logger
        }
        return unit;
    }

    private List<ClassOrInterfaceDeclaration> generateClassOrInterfaceDeclarations() {
        List<ClassOrInterfaceDeclaration> output = new ArrayList<>();
        unit.accept(new VoidVisitorAdapter<Object>(){
            @Override
            public void visit(ClassOrInterfaceDeclaration n, Object arg){
                super.visit(n, arg);
                output.add(n);
            }
        }, null);
        return output;
    }

}
