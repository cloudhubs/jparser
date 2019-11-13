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

    public AnalysisContext retreiveAnalysisContextFromGraph(DirectoryComponent doc) {
        AnalysisContext context = factory.createAnalysisContextFromDirectoryGraph(doc);
        return context;
    }

    public Component retreiveDirectoryGraphFromPath(String path) {
        DirectoryFactory factory = new DirectoryFactory();
        return factory.createDirectoryGraph(path);
    }

    public List<ModuleComponent> retreiveModuleGraph(DirectoryComponent doc) {
        return factory.createModulesFromDirectory(doc);
    }

    public List<AnalysisContext> retrieveContextFromFiles(List<File> files, RequestContext requestContext) {
        //Change to modules
        List<AnalysisContext> contexts = new ArrayList<>();
        //Put this functionality inside factory

        // factory.createAnalysisContextFromDirectory();
        // factory.createAnalysisContextFromFile();
        for (File file : files) {
            DirectoryFactory directoryFactory = new DirectoryFactory();
            DirectoryComponent root = (DirectoryComponent) directoryFactory.createDirectoryGraphOfFile(file);
            AnalysisContext context = factory.createAnalysisContextFromDirectoryGraph(root);
            contexts.add(context);
        }
        return contexts;
    }
//
    public AnalysisContext retrieveContextFromFile(File file) {
        return factory.createAnalysisContextFromFile(file);
    }

}
