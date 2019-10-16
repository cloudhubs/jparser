package edu.baylor.ecs.ciljssa.factory.context;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import edu.baylor.ecs.ciljssa.builder.AnalysisContextBuilder;
import edu.baylor.ecs.ciljssa.component.impl.ModuleComponent;
import edu.baylor.ecs.ciljssa.context.AnalysisContext;
import edu.baylor.ecs.ciljssa.factory.container.AbstractComponentFactory;
import edu.baylor.ecs.ciljssa.factory.container.impl.ComponentFactoryProducer;
import edu.baylor.ecs.ciljssa.model.ClassOrInterface;
import edu.baylor.ecs.ciljssa.component.IComponent;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class AnalysisContextFactory {

    /**
     * TODO: Create modules via module factory, inside module factory create classes, then methods etc.
     *       Then, populate module and system specific fields upward.
     * @param file
     * @return
     */
    public AnalysisContext createAnalysisContextFromFile(File file) {
        CompilationUnit unit = createCompilationUnit(file);
        List<ClassOrInterfaceDeclaration> cls = createClassOrInterfaceDeclarations(unit);
        AnalysisContext context = new AnalysisContextBuilder()
                .withModules(createModules(cls))
                .withSourceFile(file)
                .withClassNames(createClassNames(cls))
                .withClassesAndInterfaces(createClassesAndInterfaces(unit))
                .withInterfaceNames(createInterfaceNames(cls))
                .withClassOrInterfaceDeclarations(cls)
                .isSucceeded()
                .build();
        return context;
    }

    private List<ModuleComponent> createModules(List<ClassOrInterfaceDeclaration> cls) {
        List<ModuleComponent> modules = new ArrayList<>();
        // TODO: Divvy into directories and then run a module factory on each directory to create corresponding module.
        //       Module for each package with code or module for each directory period?
        //       [METHOD A:]
        //       Module::edu -> Module::edu.baylor
        //       Module::edu.baylor -> Module::edu.baylor.ecs
        //       Module::edu.baylor.ecs -> Module::edu.baylor.ecs.ciljssa
        //       Module::edu.baylor.ecs.ciljssa.builder -> List<IComponent> classes
        //       Module::edu.baylor.ecs.ciljssa.component -> List<IComponent> classes
        //       etc...
        //       [METHOD B:]
        //       Module::edu.baylor.ecs.ciljssa.builder -> List<IComponent> classes
        //       Module::edu.baylor.ecs.ciljssa.component -> List<IComponent> classes
        //       etc...
        return modules;
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
    private List<IComponent> createClassesAndInterfaces(CompilationUnit unit) {
        List<ClassOrInterfaceDeclaration> classOrInterfaces = new ArrayList<>();
        unit.accept(new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                super.visit(n, arg);
                classOrInterfaces.add(n);
            }
        }, null);
        List<IComponent> clsList = new ArrayList<>();
        for(ClassOrInterfaceDeclaration cls : classOrInterfaces) {
            ClassOrInterface type = cls.isInterface() ? ClassOrInterface.INTERFACE : ClassOrInterface.CLASS;
            AbstractComponentFactory factory = ComponentFactoryProducer.getFactory(type, null);
            IComponent coi = factory.createComponent(cls, unit);
            clsList.add(coi);
        }
        return clsList;
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
