package edu.baylor.ecs.ciljssa.factory.context;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import edu.baylor.ecs.ciljssa.builder.AnalysisContextBuilder;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.impl.ClassComponent;
import edu.baylor.ecs.ciljssa.component.impl.DirectoryComponent;
import edu.baylor.ecs.ciljssa.component.impl.InterfaceComponent;
import edu.baylor.ecs.ciljssa.component.impl.ModuleComponent;
import edu.baylor.ecs.ciljssa.context.AnalysisContext;

import edu.baylor.ecs.ciljssa.factory.container.impl.ClassComponentFactory;
import edu.baylor.ecs.ciljssa.factory.container.impl.InterfaceComponentFactory;
import edu.baylor.ecs.ciljssa.factory.container.impl.ModuleComponentFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalysisContextFactory {

    private ModuleComponentFactory moduleFactory;

    /**
     * After creating the new context factory, reset all the factory ID incrementors
     */
    public AnalysisContextFactory() {
        this.moduleFactory = (ModuleComponentFactory) ModuleComponentFactory.getInstance();
        this.moduleFactory.resetIdEnumerator();
        ClassComponentFactory.getInstance().resetIdEnumerator();
        InterfaceComponentFactory.getInstance().resetIdEnumerator();

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
                .collect(Collectors.toMap(p -> p, ModuleComponent::getPackageName, (p1, p2)->p1)); // Merge
        List<Component> cls = modules.stream().map(ModuleComponent::getClassesAndInterfaces)
                .flatMap(List::stream).collect(Collectors.toList());
        List<ClassOrInterfaceDeclaration> clsd = modules.stream().map(ModuleComponent::getClassOrInterfaceDeclarations)
                .flatMap(List::stream).collect(Collectors.toList());
        List<MethodDeclaration> mds = modules.stream().map(ModuleComponent::getMethodDeclarations)
                .flatMap(List::stream).collect(Collectors.toList());
        List<Component> methods = modules.stream().map(ModuleComponent::getMethods)
                .flatMap(List::stream).collect(Collectors.toList());

        return new AnalysisContextBuilder()
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
    }

    public List<ModuleComponent> createModulesFromDirectory(DirectoryComponent doc) {
        return createModulesFromDirectory(doc, null);
    }

    private List<ModuleComponent> createModulesFromDirectory(DirectoryComponent doc, ModuleComponent parent) {
        List<ModuleComponent> list = new ArrayList<>();
        ModuleComponent module = new ModuleComponent();
        if (doc.getNumFiles() > 0) {
            // create parent directory
            module = moduleFactory.createComponent(parent, doc);
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
