package edu.baylor.ecs.ciljssa.factory.context;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.baylor.ecs.ciljssa.builder.AnalysisContextBuilder;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.impl.ClassComponent;
import edu.baylor.ecs.ciljssa.component.impl.DirectoryComponent;
import edu.baylor.ecs.ciljssa.component.impl.InterfaceComponent;
import edu.baylor.ecs.ciljssa.component.impl.MethodInfoComponent;
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
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AnalysisContextFactory {

    private ModuleComponentFactory moduleFactory;

    public AnalysisContextFactory() {
        this.moduleFactory = new ModuleComponentFactory();
    }

    public AnalysisContext createAnalysisContextFromDirectoryGraph(DirectoryComponent root) {
        List<ModuleComponent> modules = createModulesFromDirectory(root, null);
        List<String> classNames = modules.stream().map(ModuleComponent::getClassNames)
                .flatMap(List::stream).collect(Collectors.toList());
        List<String> interfaceNames = modules.stream().map(ModuleComponent::getInterfaceNames)
                .flatMap(List::stream).collect(Collectors.toList());
        List<ClassComponent> classes = modules.stream().map(ModuleComponent::getClasses)
                .flatMap(List::stream).collect(Collectors.toList());
        List<InterfaceComponent> interfaces = modules.stream().map(ModuleComponent::getInterfaces)
                .flatMap(List::stream).collect(Collectors.toList());
        Map<ModuleComponent, String> packageMap = modules.stream()
                .collect(Collectors.toMap(p -> p, ModuleComponent::getPackageName));
        List<Component> cls = modules.stream().map(ModuleComponent::getClassesAndInterfaces)
                .flatMap(List::stream).collect(Collectors.toList());
        List<ClassOrInterfaceDeclaration> clsd = modules.stream().map(ModuleComponent::getClassOrInterfaceDeclarations)
                .flatMap(List::stream).collect(Collectors.toList());
        List<MethodDeclaration> mds = modules.stream().map(ModuleComponent::getMethodDeclarations)
                .flatMap(List::stream).collect(Collectors.toList());
        List<Component> methods = modules.stream().map(ModuleComponent::getMethods)
                .flatMap(List::stream).collect(Collectors.toList());

        AnalysisContext context = new AnalysisContextBuilder()
                .withModules(modules)
                .withClassNames(classNames)
                .withClassesAndInterfaces(cls)
                .withClassOrInterfaceDeclarations(clsd)
                .withInterfaceNames(interfaceNames)
                .withMethodDeclarations(mds)
                .withMethods(methods)
                .withRootPath(root.getPath())
                .withDirectoryGraph(root)
                .withPackageMap(packageMap)
                .withClasses(classes)
                .withInterfaces(interfaces)
                .build();

        return context;
    }

    public List<ModuleComponent> createModulesFromDirectory(DirectoryComponent doc) {
        return createModulesFromDirectory(doc, null);
    }

    private List<ModuleComponent> createModulesFromDirectory(DirectoryComponent doc, ModuleComponent parent) {
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
