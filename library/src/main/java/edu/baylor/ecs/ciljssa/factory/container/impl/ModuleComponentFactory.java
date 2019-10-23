package edu.baylor.ecs.ciljssa.factory.container.impl;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.baylor.ecs.ciljssa.builder.ModuleBuilder;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.ContainerComponent;
import edu.baylor.ecs.ciljssa.component.impl.ClassComponent;
import edu.baylor.ecs.ciljssa.component.impl.DirectoryComponent;
import edu.baylor.ecs.ciljssa.component.impl.InterfaceComponent;
import edu.baylor.ecs.ciljssa.component.impl.ModuleComponent;
import edu.baylor.ecs.ciljssa.factory.container.AbstractContainerFactory;
import edu.baylor.ecs.ciljssa.factory.container.ComponentFactoryProducer;
import edu.baylor.ecs.ciljssa.factory.container.IContainerFactory;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import edu.baylor.ecs.ciljssa.model.ModuleStereotype;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleComponentFactory extends AbstractContainerFactory {

    /**
     * Returns null because Modules have no class or interface declaration
     * @param cls
     * @param unit
     * @return
     */
    @Override
    @Deprecated
    public Component createComponent(ClassOrInterfaceDeclaration cls, CompilationUnit unit) {
        return null;
    }

    public ModuleComponent createComponent(DirectoryComponent dir, ModuleComponent parent) {
        ModuleComponent module = new ModuleComponent();
        List<ClassOrInterfaceDeclaration> allClassOrInterfaces = new ArrayList<>();
        List<Component> allCOIComponents = new ArrayList<>();
        for (File f : dir.getFiles()) {
            CompilationUnit unit = createCompilationUnit(f);
            List<ClassOrInterfaceDeclaration> cls = createClassOrInterfaceDeclarations(unit);
            allClassOrInterfaces.addAll(cls);
            allCOIComponents.addAll(createClassesAndInterfaces(unit, cls));
        }
        List<ClassComponent> ccd = allCOIComponents.stream()
                .filter(x -> x instanceof ClassComponent)
                .map(x -> (ClassComponent) x).collect(Collectors.toList());
        List<InterfaceComponent> icd = allCOIComponents.stream()
                .filter(x -> x instanceof InterfaceComponent)
                .map(x -> (InterfaceComponent)x).collect(Collectors.toList());
        module.setParent(parent);
        module.setInstanceType(InstanceType.MODULECOMPONENT);
        module.setPath(dir.getPath());
        module.setPackageName(dir.getPackageName());
        module.setInstanceName(dir.getPackageName()+"::ModuleComponent"); //TODO: Perhaps not this
        module.setClassOrInterfaceDeclarations(allClassOrInterfaces);
        module.setClassesAndInterfaces(allCOIComponents);
        module.setClasses(ccd);
        module.setInterfaces(icd);
        module.setClassNames(createClassNames(allClassOrInterfaces));
        module.setInterfaceNames(createInterfaceNames(allClassOrInterfaces));
        module.setModuleStereotype(ModuleStereotype.FABRICATED);
        module.setMethods(extractMethodComponents(allCOIComponents));
        module.setMethodDeclarations(extractMethods(allCOIComponents));
        return module;
    }

    private List<MethodDeclaration> extractMethods(List<Component> cois) {
        List<MethodDeclaration> allmethods = new ArrayList<>();
        for (Component e : cois) {
            if (e.getInstanceType() == InstanceType.CLASSCOMPONENT
                    || e.getInstanceType() == InstanceType.INTERFACECOMPONENT){
                allmethods.addAll(((ContainerComponent)e).getMethodDeclarations());
            }
        }
        return allmethods;
    }

    private List<Component> extractMethodComponents(List<Component> cois) {
        List<Component> allmethods = new ArrayList<>();
        for (Component e : cois) {
            if (e.getInstanceType() == InstanceType.CLASSCOMPONENT
                    || e.getInstanceType() == InstanceType.INTERFACECOMPONENT){
                allmethods.addAll(((ContainerComponent) e).getMethods());
            }
        }
        return allmethods;
    }

    private CompilationUnit createCompilationUnit(File file) {
        JavaParser parser = new JavaParser();
        CompilationUnit unit = null;
        try {
            unit = parser.parse(file).getResult().get();
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // TODO: Logger
        }
        return unit;
    }

    private List<String> createClassNames(List<ClassOrInterfaceDeclaration> cls) {
        List<String> output = new ArrayList<>(); // TODO: Best Practice
        for (ClassOrInterfaceDeclaration c : cls) {
            if (!c.isInterface()) {
                output.add(c.getNameAsString());
            }
        }
        return output;
    }

    private List<String> createInterfaceNames(List<ClassOrInterfaceDeclaration> cls) {
        List<String> output = new ArrayList<>();
        for (ClassOrInterfaceDeclaration c : cls) {
            if (c.isInterface()) {
                output.add(c.getNameAsString());
            }
        }
        return output;
    }

    //TODO: Module
    private List<Component> createClassesAndInterfaces(CompilationUnit unit, List<ClassOrInterfaceDeclaration> classOrInterfaces) {
        List<Component> clsList = new ArrayList<>();
        for(ClassOrInterfaceDeclaration cls : classOrInterfaces) {
            ClassOrInterface type = cls.isInterface() ? ClassOrInterface.INTERFACE : ClassOrInterface.CLASS;
            AbstractContainerFactory factory = ComponentFactoryProducer.getFactory(type, null); //TODO: Could make as singletons and return reference
            Component coi = factory.createComponent(cls, unit);
            clsList.add(coi);
        }
        return clsList;
    }

    private List<ClassOrInterfaceDeclaration> createClassOrInterfaceDeclarations(CompilationUnit unit) {
        List<ClassOrInterfaceDeclaration> output = new ArrayList<>();
        unit.accept(new VoidVisitorAdapter<Object>(){
            @Override
            public void visit(ClassOrInterfaceDeclaration n, Object arg){
                super.visit(n, arg);
                output.add(n);
            }
        }, null);
        return output;
    }


}
