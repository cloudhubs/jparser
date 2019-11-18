package edu.baylor.ecs.ciljssa.app.services;

import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.impl.DirectoryComponent;
import edu.baylor.ecs.ciljssa.component.context.AnalysisContext;
import edu.baylor.ecs.ciljssa.app.context.RequestContext;
import edu.baylor.ecs.ciljssa.factory.container.AbstractContainerFactory;
import edu.baylor.ecs.ciljssa.factory.container.ComponentFactoryProducer;
import edu.baylor.ecs.ciljssa.factory.container.impl.ModuleComponentFactory;
import edu.baylor.ecs.ciljssa.factory.context.AnalysisContextFactory;
import edu.baylor.ecs.ciljssa.factory.directory.DirectoryFactory;
import edu.baylor.ecs.ciljssa.model.ContainerType;
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

    public Component retreiveAnalysisContextFromGraph(DirectoryComponent doc) {
        Component context = factory.createAnalysisContextFromDirectoryGraph(doc);
        return context;
    }

    public Component retreiveDirectoryGraphFromPath(String path) {
        DirectoryFactory factory = new DirectoryFactory();
        return factory.createDirectoryGraph(path);
    }

    public List<Component> retrieveContextFromFiles(List<File> files, RequestContext requestContext) {
        List<Component> contexts = new ArrayList<>();
        for (File file : files) {
            DirectoryFactory directoryFactory = new DirectoryFactory();
            DirectoryComponent root = (DirectoryComponent) directoryFactory.createDirectoryGraphOfFile(file);
            Component context = factory.createAnalysisContextFromDirectoryGraph(root);
            contexts.add(context);
        }
        return contexts;
    }
//
    public Component retrieveContextFromFile(File file) {
        return factory.createAnalysisContextFromFile(file);
    }

}
