package edu.baylor.ecs.ciljssa.app.services;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.baylor.ecs.ciljssa.context.AnalysisContext;
import edu.baylor.ecs.ciljssa.context.AnalysisRequestContext;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Service
public class RetreivalService {

    public AnalysisContext retrieveContextFromPath(String path, AnalysisRequestContext requestContext) throws FileNotFoundException {
        File file = new File(path);
        return retrieveContextFromFile(file, requestContext);
    }

    public List<AnalysisContext> retrieveContextFromFiles(List<File> files, AnalysisRequestContext requestContext) {
        JavaParser parser = new JavaParser();
        List<AnalysisContext> contexts = new ArrayList<>();
        for (File file : files) {
            List<ClassOrInterfaceDeclaration> classOrInterfaces = new ArrayList<>();
            CompilationUnit unit = new CompilationUnit();
            try {
                unit = parser.parse(file).getResult().get();
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
            AnalysisContext context = new AnalysisContext(unit);
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
        List<ClassOrInterfaceDeclaration> classOrInterfaces = new ArrayList<>();
        CompilationUnit unit = parser.parse(file).getResult().get();
        unit.accept(new VoidVisitorAdapter<Object>(){
            @Override
            public void visit(ClassOrInterfaceDeclaration n, Object arg){
                super.visit(n, arg);
                classOrInterfaces.add(n);
            }
        }, null);
        AnalysisContext context = new AnalysisContext(unit);
        context.setFileName(file.getName());
        context.setFilePath(file.getPath());
        if (context.getClassesAndInterfaces().length > 0) {
            context.setSucceeded(true);
        }
        return context;
    }

}