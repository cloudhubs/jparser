package edu.baylor.ecs.ciljssa.context;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.baylor.ecs.ciljssa.wrappers.ClassInterfaceWrapper;w
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;

//TODO: For friday, what would be good:
//      - Annotations all information regarding them in result
//      - Specifications on some sample queries?
//      - Query to get body from a MethodDeclaration inside a context
//      - Tidying and best practice
//      - Deal with other TODOs
//      - Handle as many exceptions and errors as possible
//      - What else?

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AnalysisContext {


    @JsonIgnore
    private File sourceFile;
    @JsonIgnore
    private CompilationUnit analysisUnit;
    @JsonIgnore
    private List<ClassOrInterfaceDeclaration> classOrInterfaceDeclarations;

    private boolean succeeded = false;
    @JsonProperty(value = "file_name")
    private String fileName;
    @JsonProperty(value = "file_path")
    private String filePath;
    @JsonProperty(value = "classes")
    private String[] classNames;
    @JsonProperty(value = "interfaces")
    private String[] interfaceNames;

    @JsonProperty(value = "declarations")
    private ClassInterfaceWrapper[] classesAndInterfaces;

    public AnalysisContext(CompilationUnit unit) {
        this.analysisUnit = unit;
        this.classesAndInterfaces = generateClassesAndInterfaces();
        this.classNames = createClassNames();
        this.interfaceNames = createInterfaceNames();
    }

    private String[] createClassNames() {
        List<String> output = new ArrayList<>(); //TODO: Best Practice
        for (ClassInterfaceWrapper classesAndInterface : this.classesAndInterfaces) {
            if (classesAndInterface.isClass()) {
                output.add(classesAndInterface.getInstanceName());
            }
        }
        return output.toArray(new String[0]);
    }

    private String[] createInterfaceNames() {
        List<String> output = new ArrayList<>();
        for (ClassInterfaceWrapper classesAndInterface : this.classesAndInterfaces) {
            if (classesAndInterface.isInterface()) {
                output.add(classesAndInterface.getInstanceName());
            }
        }
        return output.toArray(new String[0]);
    }

    private ClassInterfaceWrapper[] generateClassesAndInterfaces() {
        List<ClassOrInterfaceDeclaration> classOrInterfaces = new ArrayList<>();
        this.analysisUnit.accept(new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                super.visit(n, arg);
                classOrInterfaces.add(n);
            }
        }, null);
        List<ClassInterfaceWrapper> clsList = new ArrayList<>();
        for(ClassOrInterfaceDeclaration cls : classOrInterfaces) {
            clsList.add(new ClassInterfaceWrapper(cls, this.analysisUnit));
        }
        this.classOrInterfaceDeclarations = classOrInterfaces;
        return clsList.toArray(new ClassInterfaceWrapper[0]);
    }

    public AnalysisContext filterByClass(String name) {
        setClassNames(Arrays.stream(this.classNames).filter(x -> x.equals(name)).toArray(String[]::new));
        this.setClassesAndInterfaces(Arrays.stream(this.getClassesAndInterfaces())
                .filter(x -> x.getInstanceName().equals(name)).toArray(ClassInterfaceWrapper[]::new));
        this.interfaceNames = new String[0];
        return this;
    }
}
