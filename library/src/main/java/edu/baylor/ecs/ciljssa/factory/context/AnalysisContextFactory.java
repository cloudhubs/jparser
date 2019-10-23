package edu.baylor.ecs.ciljssa.factory.context;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.baylor.ecs.ciljssa.builder.AnalysisContextBuilder;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.impl.DirectoryComponent;
import edu.baylor.ecs.ciljssa.component.impl.ModuleComponent;
import edu.baylor.ecs.ciljssa.context.AnalysisContext;
import edu.baylor.ecs.ciljssa.factory.container.AbstractContainerFactory;
import edu.baylor.ecs.ciljssa.factory.container.ComponentFactoryProducer;
import edu.baylor.ecs.ciljssa.factory.container.impl.ModuleComponentFactory;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AnalysisContextFactory {

    private ModuleComponentFactory moduleFactory;

    public AnalysisContextFactory() {
        this.moduleFactory = new ModuleComponentFactory();
    }

    /**
     * TODO: Create modules via module factory, inside module factory create classes, then methods etc.
     *       Then, populate module and system specific fields upward.
     * @param //file
     * @return
     */
//    public AnalysisContext createAnalysisContextFromFile(File file) {
//        CompilationUnit unit = createCompilationUnit(file);
//        List<ClassOrInterfaceDeclaration> cls = createClassOrInterfaceDeclarations(unit);
//        AnalysisContext context = new AnalysisContextBuilder()
//                .withModules(createModules(cls))
//                .withSourceFile(file)
//                .withClassNames(createClassNames(cls))
//                .withClassesAndInterfaces(createClassesAndInterfaces(unit))
//                .withInterfaceNames(createInterfaceNames(cls))
//                .withClassOrInterfaceDeclarations(cls)
//                .isSucceeded()
//                .build();
//        return context;
//    }

    public AnalysisContext createAnalysisContextFromDirectoryGraph(DirectoryComponent root) {
        List<ModuleComponent> modules = new ArrayList<>();
        modules = createModulesFromDirectory(root, null);
        AnalysisContext context = new AnalysisContextBuilder().withModules(modules).build();

        return context;
    }

    public List<ModuleComponent> createModulesFromDirectory(DirectoryComponent doc, ModuleComponent parent) {
        List<ModuleComponent> list = new ArrayList<>();
        ModuleComponent module = new ModuleComponent();
        if (doc.getNumFiles() > 0) {
            // create parent directory
            module = moduleFactory.createComponent(doc, parent);
            module.setParent(parent);
            list.add(module);
        }
        for (DirectoryComponent e : doc.getSubDirectories()) {
            List<ModuleComponent> comps = createModulesFromDirectory(e, module);
            if (!comps.isEmpty()) {
                module.setSubModules(comps);
                list.addAll(comps);
            }
        }
        return list;
    }

    private List<ModuleComponent> createModules(List<ClassOrInterfaceDeclaration> cls) {
        List<ModuleComponent> modules = new ArrayList<>();
        return modules;
    }

}
