package org.seer.ciljssa.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.seer.ciljssa.support.ClassInterfaceWrapper;

import java.io.File;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AnalysisContext {

    private boolean succeeded = false;

    private AnalysisRequestContext requestContext;
    private String language;

    @JsonIgnore
    private File sourceFile;
    @JsonProperty(value = "file_name")
    private String stringifiedFile;

    private String[] classNames;
    private String[] interfaceNames;

    @JsonProperty(value = "declarations")
    private ClassInterfaceWrapper[] classesAndInterfaces;

    public AnalysisContext(AnalysisRequestContext requestContext, ArrayList<ClassOrInterfaceDeclaration> classOrInterfaces){
        this.requestContext = requestContext;
        this.classesAndInterfaces = generateClassesAndInterfaces(classOrInterfaces);
        this.sourceFile = new File(requestContext.getFilepath());
        this.language = requestContext.getLanguage();
        this.stringifiedFile = getFileName(requestContext.getFilepath());
        this.classNames = createClassNames();
        this.interfaceNames = createInterfaceNames();
        //TODO: Nab the file name and language via Javaparser.
    }

    private String getFileName(String file) {
        String out = file;
        out = out.substring(file.lastIndexOf('/') + 1, file.length());
        return out;
    }

    private String[] createClassNames() {
        String[] output = new String[0];
        return output;
    }

    private String[] createInterfaceNames() {
        String[] output = new String[0];
        return output;
    }

    private ClassInterfaceWrapper[] generateClassesAndInterfaces(ArrayList<ClassOrInterfaceDeclaration> classOrInterfaces) {
        ArrayList<ClassInterfaceWrapper> clsList = new ArrayList<>();
        for(ClassOrInterfaceDeclaration cls : classOrInterfaces) {
            clsList.add(new ClassInterfaceWrapper(cls));
        }
        return clsList.toArray(new ClassInterfaceWrapper[0]);
    }
}

