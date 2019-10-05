package edu.baylor.ecs.ciljssa.app.services;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.baylor.ecs.ciljssa.builder.AnalysisContextBuilder;
import edu.baylor.ecs.ciljssa.context.AnalysisContext;
import edu.baylor.ecs.ciljssa.app.context.RequestContext;
import edu.baylor.ecs.ciljssa.factory.context.AnalysisContextFactory;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class RetreivalService {

    private AnalysisContextFactory factory;

    public RetreivalService() {
        this.factory = new AnalysisContextFactory();
    }

    public AnalysisContext retrieveContextFromPath(RequestContext requestContext) {
        File file = new File(requestContext.getFilepath());
        return retrieveContextFromFile(file);
    }

    public List<AnalysisContext> retrieveContextFromFiles(List<File> files, RequestContext requestContext) {
        List<AnalysisContext> contexts = new ArrayList<>();
        for (File file : files) {
            AnalysisContext context = factory.createAnalysisContextFromFile(file);
            contexts.add(context);
        }
        return contexts;
    }

    private AnalysisContext retrieveContextFromFile(File file) {
        return factory.createAnalysisContextFromFile(file);
    }

}
