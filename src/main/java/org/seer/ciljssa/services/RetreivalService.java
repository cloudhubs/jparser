package org.seer.ciljssa.services;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.seer.ciljssa.context.AnalysisContext;
import org.seer.ciljssa.context.AnalysisRequestContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

@Data
@NoArgsConstructor
public class RetreivalService {

    public AnalysisContext retrieveContextFromPath(String path, AnalysisRequestContext requestContext) throws FileNotFoundException {
        File file = new File(path);
        return retrieveContextFromFile(file, requestContext);
    }

    public ArrayList<AnalysisContext> retrieveContextFromFiles(ArrayList<File> files, AnalysisRequestContext requestContext) {
        JavaParser parser = new JavaParser();
        ArrayList<AnalysisContext> contexts = new ArrayList<>();
        for (File file : files) {
            ArrayList<ClassOrInterfaceDeclaration> classOrInterfaces = new ArrayList<>();
            try {
                CompilationUnit unit = parser.parse(file).getResult().get();
                unit.accept(new VoidVisitorAdapter<Object>(){
                    @Override
                    public void visit(ClassOrInterfaceDeclaration n, Object arg){
                        super.visit(n, arg);
                        classOrInterfaces.add(n);
                    }
                }, null);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
            AnalysisContext context = new AnalysisContext(classOrInterfaces);
            context.setFileName(file.getName());
            context.setFilePath(file.getPath());
            if (context.getClassesAndInterfaces().length > 0) {
                context.setSucceeded(true);
            }
            contexts.add(context);
        }
        return contexts;
    }

    private AnalysisContext retrieveContextFromFile(File file, AnalysisRequestContext requestContext) throws FileNotFoundException {
        JavaParser parser = new JavaParser();
        ArrayList<ClassOrInterfaceDeclaration> classOrInterfaces = new ArrayList<>();
        CompilationUnit unit = parser.parse(file).getResult().get();
        unit.accept(new VoidVisitorAdapter<Object>(){
            @Override
            public void visit(ClassOrInterfaceDeclaration n, Object arg){
                super.visit(n, arg);
                classOrInterfaces.add(n);
            }
        }, null);
        AnalysisContext context = new AnalysisContext(classOrInterfaces);
        context.setFileName(file.getName());
        context.setFilePath(file.getPath());
        if (context.getClassesAndInterfaces().length > 0) {
            context.setSucceeded(true);
        }
        return context;
    }

}
