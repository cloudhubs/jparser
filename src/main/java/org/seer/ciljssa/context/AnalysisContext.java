package org.seer.ciljssa.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.seer.ciljssa.support.ClassInterfaceWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class AnalysisContext {

    private boolean succeeded = false;

    @JsonIgnore
    private File sourceFile;
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

    public AnalysisContext(ArrayList<ClassOrInterfaceDeclaration> classOrInterfaces){
        this.classesAndInterfaces = generateClassesAndInterfaces(classOrInterfaces);
        this.classNames = createClassNames();
        this.interfaceNames = createInterfaceNames();
        //TODO: Nab the file name and language via Javaparser.
    }

    private String[] createClassNames() {
        ArrayList<String> output = new ArrayList<>();
        for (ClassInterfaceWrapper classesAndInterface : this.classesAndInterfaces) {
            if (classesAndInterface.isClass()) {
                output.add(classesAndInterface.getInstanceName());
            }
        }
        return output.toArray(new String[0]);
    }

    private String[] createInterfaceNames() {
        ArrayList<String> output = new ArrayList<>();
        for (ClassInterfaceWrapper classesAndInterface : this.classesAndInterfaces) {
            if (classesAndInterface.isInterface()) {
                output.add(classesAndInterface.getInstanceName());
            }
        }
        return output.toArray(new String[0]);
    }

    private ClassInterfaceWrapper[] generateClassesAndInterfaces(ArrayList<ClassOrInterfaceDeclaration> classOrInterfaces) {
        ArrayList<ClassInterfaceWrapper> clsList = new ArrayList<>();
        for(ClassOrInterfaceDeclaration cls : classOrInterfaces) {
            clsList.add(new ClassInterfaceWrapper(cls));
        }
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

