package org.seer.ciljssa.context;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.seer.ciljssa.support.ClassInterfaceWrapper;

import java.io.File;
import java.util.ArrayList;

@Data
@NoArgsConstructor
public class AnalysisContext {

    private boolean succeeded = false;

    private AnalysisRequestContext requestContext;
    private String language;

    private File sourceFile;
    private String stringifiedFile;

    private String[] classNames;
    private String[] interfaceNames;

    @JsonProperty(value = "declarations")
    private ClassInterfaceWrapper[] classesAndInterfaces;

    public AnalysisContext(AnalysisRequestContext requestContext) {
        this.requestContext = requestContext;
        this.classesAndInterfaces = new ClassInterfaceWrapper[0];
    }

    public AnalysisContext(AnalysisRequestContext requestContext, ArrayList<ClassOrInterfaceDeclaration> classOrInterfaces){
        this.requestContext = requestContext;
        this.classesAndInterfaces = generateClassesAndInterfaces(classOrInterfaces);
        //TODO: Nab the file name and language via Javaparser.
    }

    public ClassInterfaceWrapper[] generateClassesAndInterfaces(ArrayList<ClassOrInterfaceDeclaration> classOrInterfaces) {
        ArrayList<ClassInterfaceWrapper> clsList = new ArrayList<>();
        for(ClassOrInterfaceDeclaration cls : classOrInterfaces) {
            clsList.add(new ClassInterfaceWrapper(cls));
        }
        return clsList.toArray(new ClassInterfaceWrapper[0]);
    }
}

