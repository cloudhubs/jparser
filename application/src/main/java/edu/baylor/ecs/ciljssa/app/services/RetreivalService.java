package edu.baylor.ecs.ciljssa.app.services;

import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.impl.DirectoryComponent;
import edu.baylor.ecs.ciljssa.component.impl.ModuleComponent;
import edu.baylor.ecs.ciljssa.context.AnalysisContext;
import edu.baylor.ecs.ciljssa.app.context.RequestContext;
import edu.baylor.ecs.ciljssa.factory.context.AnalysisContextFactory;
import edu.baylor.ecs.ciljssa.factory.directory.DirectoryFactory;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class RetreivalService {

    private AnalysisContextFactory factory;

    public RetreivalService() {
        this.factory = new AnalysisContextFactory();
    }

//    public AnalysisContext retrieveContextFromPath(RequestContext requestContext) {
//        File file = new File(requestContext.getFilepath());
//        return retrieveContextFromFile(file);
//    }

    public Component retreiveDirectoryGraphFromPath(String path) {
        DirectoryFactory factory = new DirectoryFactory();
        return factory.createDirectoryGraph(path);
    }

    public List<ModuleComponent> retreiveModuleGraph(DirectoryComponent doc) {
        return factory.createModulesFromDirectory(doc, null);
    }

//    public List<AnalysisContext> retrieveContextFromFiles(List<File> files, RequestContext requestContext) {
//        //Change to modules
//        List<AnalysisContext> contexts = new ArrayList<>();
//        //Put this functionality inside factory
//
//        // factory.createAnalysisContextFromDirectory();
//        // factory.createAnalysisContextFromFile();
//        for (File file : files) {
//            AnalysisContext context = factory.createAnalysisContextFromFile(file);
//            contexts.add(context);
//        }
//        return contexts;
//    }
//
//    private AnalysisContext retrieveContextFromFile(File file) {
//        return factory.createAnalysisContextFromFile(file);
//    }

}
