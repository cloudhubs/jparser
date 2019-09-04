package org.seer.ciljssa.context;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.seer.ciljssa.support.ClassInterfaceWrapper;

import java.io.File;
import java.util.ArrayList;

@Data
@NoArgsConstructor
public class AnalysisContext {

    private AnalysisRequestContext requestContext;
    private String language;

    private File sourceFile;
    private String stringifiedFile;

    private String[] classNames;
    private String[] interfaceNames;
    private String[] constructors;

    public AnalysisContext(AnalysisRequestContext requestContext){
        this.requestContext = requestContext;
        //TODO: Nab the file name and language via Javaparser.
    }

    public void setClassesAndInterfaces(ArrayList<ClassOrInterfaceDeclaration> classOrInterfaces) {
        ArrayList<ClassInterfaceWrapper> clsList = new ArrayList<>();
        for(ClassOrInterfaceDeclaration cls : classOrInterfaces) {
            clsList.add(new ClassInterfaceWrapper(cls));
        }
    }
}
