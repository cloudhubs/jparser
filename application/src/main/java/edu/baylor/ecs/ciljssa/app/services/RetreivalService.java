package edu.baylor.ecs.ciljssa.app.services;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.baylor.ecs.ciljssa.builder.AnalysisContextBuilder;
import edu.baylor.ecs.ciljssa.context.AnalysisContext;
import edu.baylor.ecs.ciljssa.app.context.RequestContext;
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

    public AnalysisContext retrieveContextFromPath(RequestContext requestContext) {
        File file = new File(requestContext.getFilepath());
        return retrieveContextFromFile(file);
    }

    public List<AnalysisContext> retrieveContextFromFiles(List<File> files, RequestContext requestContext) {
        List<AnalysisContext> contexts = new ArrayList<>();
        for (File file : files) {
            AnalysisContext context = new AnalysisContextBuilder(file).build();
            contexts.add(context);
        }
        return contexts;
    }

    private AnalysisContext retrieveContextFromFile(File file) {
        return new AnalysisContextBuilder(file).build();
    }

}
